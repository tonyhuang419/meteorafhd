from django.conf.urls.defaults import *
from django.contrib import admin
from django.conf import settings


admin.autodiscover()

urlpatterns = patterns('',
    # Example:
    # (r'^myproj/', include('djangoDemo.apps.foo.urls.foo')),
    (r'^$', 'djangoDemo.helloworld.index'),
    (r'^add/$',  'djangoDemo.add.index'),
    (r'^list/$', 'djangoDemo.list.index'),
    (r'^csv/(?P<filename>\w+)/$', 'djangoDemo.csv_test.output'),
    (r'^login/$', 'djangoDemo.login.login'),
    (r'^logout/$', 'djangoDemo.login.logout'),

    (r'^wiki/$', 'djangoDemo.wiki.views.index'),
    (r'^wiki/(?P<pagename>\w+)/$', 'djangoDemo.wiki.views.index'),
    (r'^wiki/(?P<pagename>\w+)/edit/$', 'djangoDemo.wiki.views.edit'),
    (r'^wiki/(?P<pagename>\w+)/save/$', 'djangoDemo.wiki.views.save'),
    
    (r'^address/', include('djangoDemo.address.urls')),
    (r'^site_media/(?P<path>.*)$', 'django.views.static.serve',
        {'document_root': settings.STATIC_PATH}),


    # Uncomment this for admin:
    (r'^admin/(.*)', admin.site.root),
)