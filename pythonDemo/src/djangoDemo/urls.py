from django.conf.urls.defaults import *

urlpatterns = patterns('',
    # Example:
    # (r'^myproj/', include('djangoDemo.apps.foo.urls.foo')),
    (r'^$', 'djangoDemo.helloworld.index'),
    (r'^add/$', 'djangoDemo.add.index'),

    # Uncomment this for admin:
    # (r'^admin/', include('django.contrib.admin.urls')),
)