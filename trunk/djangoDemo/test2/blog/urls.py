# coding=UTF-8
'''
Created on 2011-6-30

@author: FHDone
'''
from blog.models import *
from datetime import datetime
from django.conf.urls.defaults import *

info_dict = {'queryset': Article.objects.all(),}


c = Category()
c.name='zzz'
#c.save()

a = Article()
a.title='title'
a.published_at=datetime.now()
a.content = 'content'
a.category = c
#a.save();

urlpatterns = patterns('',
    (r'^$', 'django.views.generic.list_detail.object_list',  dict(paginate_by=2, **info_dict)) ,
    (r'^(?P<object_id>\d+)/$', 'django.views.generic.list_detail.object_detail', info_dict),
    (r'^search/$', 'blog.views.search'),
)