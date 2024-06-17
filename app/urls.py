from django.urls import path
from .views import PredictEndPoint, goto_domain

urlpatterns = [
    path('', goto_domain, name='goto-domain'),
    path('predict', PredictEndPoint.as_view(), name='predict')
]
