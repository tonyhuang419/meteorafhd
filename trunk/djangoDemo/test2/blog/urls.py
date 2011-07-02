# coding=UTF-8
'''
Created on 2011-6-30

@author: FHDone
'''
from django.conf.urls.defaults import *
from blog.models import Article

info_dict = {'queryset': Article.objects.all(),}

urlpatterns = patterns('',
    (r'^$', 'django.views.generic.list_detail.object_list', info_dict),
    (r'^(?P<object_id>\d+)/$', 'django.views.generic.list_detail.object_detail', info_dict),
)