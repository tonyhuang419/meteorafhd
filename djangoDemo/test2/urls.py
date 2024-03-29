# coding=UTF-8
from django.conf.urls.defaults import patterns, include, url
from django.contrib import admin
import settings

# Uncomment the next two lines to enable the admin:
admin.autodiscover()

urlpatterns = patterns('',
    # Examples:
    # url(r'^$', 'test2.views.home', name='home'),
    # url(r'^test2/', include('test2.foo.urls')),
	(r'^$', 'test2.test.index'),
	
    (r'^blog/', include('test2.blog.urls')),
    #(r'^admin/', include('django.contrib.admin.urls')),

    # Uncomment the admin/doc line below to enable admin documentation:
    #url(r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    url(r'^admin/', include(admin.site.urls)),
	
	(r'^ctu/$', 'test2.test.ctu_list'),
    (r'^csv/(?P<filename>\w+)/$', 'test2.test.download'),
    
	(r'^upload/$', 'test2.test.upload'),
    (r'^login/$', 'test2.test.login'),
    (r'^logout/$', 'test2.test.logout'),
    
    (r'^site_media/(?P<path>.*)$', 'django.views.static.serve',
        {'document_root': settings.STATIC_PATH}),
    (r'^ajax/article_list/$', 'blog.views.input'),                  
)

