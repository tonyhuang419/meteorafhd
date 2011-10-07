# coding=UTF-8
#!/usr/bin python

from django.conf.urls.defaults import patterns, include, url

# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    (r'^index/$', 'after28.simpleread.WebOAuthHandler.login'),
    (r'^login_check/$', 'after28.simpleread.WebOAuthHandler.login_check'),
    (r'^weibo_get/$', 'after28.simpleread.weiboGet.weiboget'),
    
)
