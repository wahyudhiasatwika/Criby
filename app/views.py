import os
import tempfile

from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import MultiPartParser, FormParser
from rest_framework import status
from tensorflow.keras.models import load_model
from django.conf import settings

from .serializer import FileUploadSerializer
from .utils import *
import tensorflow_hub as hub


# Create your views here.
class PredictEndPoint(APIView):
    parser_classes = (MultiPartParser, FormParser)
    model = load_model(os.path.join(settings.MEDIA_ROOT, 'model_v2.h5'))
    inceptionv3 = hub.KerasLayer('https://www.kaggle.com/models/google/inception-v3/TensorFlow2/feature-vector/2')
    data_dict = {3: 'hungry', 1: 'burping', 2: 'discomfort', 0: 'belly_pain', 4: 'tired'}

    def get(self, *args, **kwargs):
        return Response()

    def post(self, *args, **kwargs):
        file_serializer = FileUploadSerializer(data=self.request.data)
        if file_serializer.is_valid():
            uploaded_file = self.request.FILES['file']
            with tempfile.NamedTemporaryFile(delete=False) as temp_file:
                temp_file.write(uploaded_file.read())
                temp_filename = temp_file.name

            try:
                data = load_data(temp_filename, self.inceptionv3)

                pred = self.model.predict(data)
                prediction = np.argmax(pred)
                confidence = np.max(pred)
                pred_result = self.data_dict[prediction]

            finally:
                if os.path.exists(temp_filename):
                    os.remove(temp_filename)

            save_dir = os.path.join(settings.MEDIA_ROOT, 'result', pred_result)
            if not os.path.exists(save_dir):
                os.mkdirs(save_dir)
            save_path = os.path.join(save_dir, uploaded_file.name)
            with open(save_path, 'wb+') as destination:
                for x in uploaded_file.chunks():
                    destination.write(x)


            return Response(data={'predictions': pred_result, 'confidence': float(confidence)},
                            status=status.HTTP_200_OK)

        return Response(file_serializer.errors, status=status.HTTP_400_BAD_REQUEST)
