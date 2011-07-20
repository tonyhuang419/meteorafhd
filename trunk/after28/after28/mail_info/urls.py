# coding=UTF-8
#!/usr/bin python

from django.conf.urls.defaults import patterns, include, url

# Uncomment the next two lines to enable the admin:
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
    
    #(r'^$', 'mail_info.firstPage.index'),
    
    (r'^sureInfo/$', 'after28.mail_info.firstPage.mailInfoSave'),
    
    (r'^sure_info_create/$', 'after28.mail_info.sureInfo.sure_info_create'),
    (r'^sure_info_update/$', 'after28.mail_info.sureInfo.sure_info_update'),
    (r'^sure_info_cancel/$', 'after28.mail_info.sureInfo.sure_info_cancel'),
    
)
