from django.urls import path
from .views import PredictEndPoint

urlpatterns = [
    path('', PredictEndPoint.as_view(), name='predict')
]