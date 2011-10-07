# coding=UTF-8
#!/usr/bin python

from after28.scheduler.sendMailJob import mailJobToday
from django.conf.urls.defaults import patterns, include, url
from django.contrib import admin
from scheduler.webGetJob import webSigninJob
import settings

#import django_cron
#django_cron.autodiscover()
#mailJobToday()
#webSigninJob()
    
# Uncomment the next two lines to enable the admin:
admin.autodiscover()

# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'after28.views.home', name='home'),
    # url(r'^after28/', include('after28.foo.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    # url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # url(r'^admin/', include(admin.site.urls)),
    url(r'^admin/', include(admin.site.urls)),
    
    (r'^$', 'after28.mail_info.firstPage.index' ),
    
    (r'^site_media/(?P<path>.*)$', 'django.views.static.serve',
        {'document_root': settings.STATIC_PATH}),
                       
    (r'^mail_info/', include('after28.mail_info.urls')),
    (r'^simple_read/', include('after28.simpleread.urls')),
)
