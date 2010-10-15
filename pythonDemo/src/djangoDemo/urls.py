from django.conf.urls.defaults import *

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


    # Uncomment this for admin:
    # (r'^admin/', include('django.contrib.admin.urls')),
)