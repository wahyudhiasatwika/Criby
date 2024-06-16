from django.urls import path
from .views import PredictEndPoint

urlpatterns = [
    path('predict', PredictEndPoint.as_view(), name='predict')
]
