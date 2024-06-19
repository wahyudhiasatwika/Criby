from django.urls import path
from .views import *

urlpatterns = [
    path('', goto_domain, name='goto-domain'),
    path('predict', PredictEndPoint.as_view(), name='predict'),
    path('download', download_file, name='download-file')
]
