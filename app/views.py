import os
import tempfile

from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework.parsers import MultiPartParser, FormParser
from rest_framework import status
from rest_framework.renderers import JSONRenderer
from tensorflow.keras.models import load_model
from django.conf import settings
from shutil import copyfileobj
from django.shortcuts import redirect
from django.http import HttpResponseRedirect, HttpResponse, Http404

from .serializer import FileUploadSerializer
from .utils import *
import tensorflow_hub as hub
from pydub import AudioSegment


# Create your views here.
class PredictEndPoint(APIView):
    parser_classes = (MultiPartParser, FormParser)
    model = load_model(os.path.join(settings.MEDIA_ROOT, 'model_v3.5.h5'))
    inceptionv3 = hub.KerasLayer('https://www.kaggle.com/models/google/inception-v3/TensorFlow2/feature-vector/2')
    data_dict = {3: 'lapar', 1: 'bersendawa atau sendawa', 2: 'tidak nyaman', 0: 'sakit perut', 4: 'lelah atau letih'}
    renderer_classes = [JSONRenderer, ]
    allowed_file_extension = ['mp3', 'wav', 'mp4', 'm4a']

    def get(self, *args, **kwargs):
        return HttpResponseRedirect('https://criby.app')

    def post(self, *args, **kwargs):
        file_serializer = FileUploadSerializer(data=self.request.data)
        if file_serializer.is_valid():
            uploaded_file = self.request.FILES['file']
            ext_name: str = uploaded_file.name.split('.')[-1]

            if ext_name in self.allowed_file_extension:
                audio = AudioSegment.from_file(uploaded_file)
                with tempfile.NamedTemporaryFile(delete=False, suffix='.wav') as temp_file:
                    audio.export(temp_file.name, format='wav')
                    temp_filename = temp_file.name
            else:
                msg_list = [f".{x}" for x in self.allowed_file_extension]
                msg = ' or '.join(msg_list)
                return Response({'error': f"File format extension not supported! Only {msg} are supported!"},
                                status=status.HTTP_403_FORBIDDEN)

            try:
                data = load_data(temp_filename, self.inceptionv3)

                pred = self.model.predict(data)
                prediction = np.argmax(pred)
                confidence = np.max(pred)
                confidence = 100.0 if confidence * 100 > 1.0 else confidence
                pred_result = self.data_dict[prediction]

            finally:
                if os.path.exists(temp_filename):
                    os.remove(temp_filename)

            save_dir = os.path.join(settings.MEDIA_ROOT, 'result', str(prediction))
            if not os.path.exists(save_dir):
                os.mkdir(save_dir)

            save_path = os.path.join(save_dir, uploaded_file.name)
            with open(save_path, 'wb+') as destination:
                copyfileobj(uploaded_file, destination)

            return Response(data={'predictions': pred_result, 'confidence': confidence},
                            status=status.HTTP_200_OK)

        return Response({'error': file_serializer.errors['file']}, status=status.HTTP_400_BAD_REQUEST)


def goto_domain(request):
    return redirect('https://criby.app')


def download_file(request):
    file_path = os.path.join(settings.STATICFILES_DIRS[0], 'apk', 'name.apk')
    if os.path.exists(file_path):
        with open(file_path, 'rb') as fp:
            response = HttpResponse(fp.read(), content_type='application/file')
            response['Content-Disposition'] = f"inline; filename={os.path.basename(file_path)}"
            return response
    raise Http404
