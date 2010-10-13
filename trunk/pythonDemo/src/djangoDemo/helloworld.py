from django.http import HttpResponse


#http://hi.baidu.com/dushm/blog/item/de11d0582a0be682810a182f.html

def index(request):
    return HttpResponse('Hello, Django!')