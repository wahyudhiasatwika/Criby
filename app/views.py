import os

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

    def get(self, *args, **kwargs):
        return Response()

    def post(self, *args, **kwargs):
        file_serializer = FileUploadSerializer(data=self.request.data)
        if file_serializer.is_valid():
            file = self.request.FILES.get('file')
            data = load_data(file, self.inceptionv3)

            pred = self.model.predict(data)
            prediction = np.argmax(pred)
            conf = np.max(pred)
            return Response(data={'predictions': prediction, 'confidence': conf}, status=status.HTTP_200_OK)
        return Response(file_serializer.errors, status=status.HTTP_400_BAD_REQUEST)
