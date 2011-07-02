# coding=UTF-8
from django.http import HttpResponse, Http404, HttpResponseRedirect
from django.shortcuts import render_to_response
from django.template import loader, Context

# import django
# print django.get_version()

ctu_info = []
ctu_info.append( { 'name':'jack','address':'LA'  } )
ctu_info.append( { 'name':'tony','address':'CA' } )
ctu_info.append( { 'name':'moss','address':'NY' } )


ctu_info_t = [
    ('jack', 'LA'),
    ('tony', 'CA')]


#del(ctu_info[0])

def index(request):
    return HttpResponse("Hello, Django.")

def ctu_list(request):
    return render_to_response('ctu_name_list.html',{'list':ctu_info,'list2':ctu_info_t})

def download(request, filename):
    response = HttpResponse(mimetype='text/csv')
    response['Content-Disposition'] = 'attachment; filename=%s.csv' % filename

    t = loader.get_template('csv.html')
    c = Context({
        'data': ctu_info_t,
    })
    response.write(t.render(c))
    return response

def login(request):
    username = ''
    try:
        if request.method == 'POST':
            username = request.POST['username']
        else:
            username = request.GET['username']
        if username:
            request.session['username']=username
            print('login %s' % username)
            return render_to_response('login.html', {'username':username})
        return render_to_response('login.html')
    except KeyError:
        return render_to_response('login.html')


def logout(request):
    try:
        username = request.session['username']
        print('logout %s' % username)
        del request.session['username']
    except KeyError:
        pass
    return HttpResponseRedirect("/login/")

