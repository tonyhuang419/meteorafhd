# coding=UTF-8
#!/usr/bin python

from django.shortcuts import render_to_response
from django.utils.log import logger
from simpleread import WebOAuthHandler
from weibopy.api import API
from weibopy.auth import OAuthHandler

class Tool():
    def getAtt(self, obj , key):
        try:
            return obj.__getattribute__(key)
        except Exception, e:
            print e
            return ''

        
tool = Tool()

class weibogetInfo():
    pass
    
        
def weiboget(request):
    access_token = request.session['oauth_access_token']
    print access_token
    auth = OAuthHandler(WebOAuthHandler.consumer_key, WebOAuthHandler.consumer_secret)
    auth.access_token = access_token
    api=API(auth)
   
    timeline = api.friends_timeline(count=100, page=1)
    
    weiboMap = {}
    ids = ''
    for line in timeline:
        info = weibogetInfo()
        #wid = line.__getattribute__("id")
        wid = tool.getAtt(line , 'id')
        info.timeline = line
        weiboMap[wid] = info
        ids = '%s,%s' % (ids, wid )
    counts = api.counts(ids)
    for count in counts:
        wid = count.__getattribute__("id")
        if weiboMap.has_key(wid):
            info = weiboMap[wid]
            #info.comment = count.__getattribute__("comments")
            info.comment = tool.getAtt(count , 'comments')
            #info.rt = count.__getattribute__("rt")
            info.rt = tool.getAtt(count , 'rt')
    for k, v in weiboMap.items():
        #user = v.timeline.__getattribute__("user").name
        user = tool.getAtt(v.timeline , 'user').name
        #text = v.timeline.__getattribute__("text")
        text = tool.getAtt(v.timeline , 'text')
        comment = v.comment
        rt = v.rt
        print "%s:%s,%s,%s" % (user, text ,comment , rt)          
    return render_to_response('simplereadget.html')


