'''
Created on 2011-7-5

@author: FHDone
'''

from blog.models import Article
from django.http import HttpResponseRedirect, HttpResponse
from django.views.generic.list_detail import object_list

def search(request):
    name = request.REQUEST['search']
    if name:
        extra_lookup_kwargs = {'name__icontains':name}
        extra_context = {'searchvalue':name}
        return object_list(request, Article.objects.filter(content__contains=name),
            paginate_by=2, extra_context=extra_context)
    else:
        return HttpResponseRedirect('/blog/')

def input(request):
    input = request.REQUEST["input"]
    return HttpResponse('You input is "%s"' % input)

