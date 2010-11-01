#coding=utf-8

from django.conf.urls.defaults import *
from djangoDemo.address.models import Address

info_dict = {
#    'model': Address,
    'queryset': Address.objects.all(),
}
urlpatterns = patterns('',
    (r'^/?$', 'django.views.generic.list_detail.object_list', info_dict),
)
