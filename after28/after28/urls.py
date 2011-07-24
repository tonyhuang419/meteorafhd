# coding=UTF-8
#!/usr/bin python

from after28.scheduler.sendMailJob import mailJobToday
from django.conf.urls.defaults import patterns, include, url
from django.contrib import admin
import settings

#import django_cron
#django_cron.autodiscover()
mailJobToday()
    
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
    url(r'^after28/admin/', include(admin.site.urls)),
    
    (r'^after28/$', 'after28.mail_info.firstPage.index' ),
    
    (r'^after28/site_media/(?P<path>.*)$', 'django.views.static.serve',
        {'document_root': settings.STATIC_PATH}),
                       
    (r'^after28/mail_info/', include('after28.mail_info.urls')),
)
