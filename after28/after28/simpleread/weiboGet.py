# coding=UTF-8
#!/usr/bin python

from django.shortcuts import render_to_response
from django.utils.log import logger
from simpleread import WebOAuthHandler
from weibopy.api import API
from weibopy.auth import OAuthHandler



class weibogetTool():
    def getAtt(self, key):
        try:
            return self.obj.__getattribute__(key)
        except Exception, e:
            print e
            return ''
        
    def getAttValue(self, obj, key):
        try:
            return obj.__getattribute__(key)
        except Exception, e:
            print e
            return ''
        
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
        wid = line.__getattribute__("id")
        info = weibogetTool()
        info.timeline = line
        weiboMap[wid] = info
        
        #info = weibogetTool()
        #info.obj = line
        #user = info.getAtt("user")
        #text = info.getAtt("text")
        #print "friends_timeline---"+ user.name +":"+ text
        ids = '%s,%s' % (ids, wid )
        #comments = api.comments(id= info.getAtt("id"))
        #print len(comments)
        #for comment in comments:
            #info = weibogetTool()
            #info.obj = comment
            #mid = info.getAtt("id")
            #text = info.getAtt("text")
            #print "comments---"+ str(mid) +":"+ text
    counts = api.counts(ids)
    for count in counts:
        wid = count.__getattribute__("id")
        if weiboMap.has_key(wid):
            info = weiboMap[wid]
            info.comment = count.__getattribute__("comments")
            info.rt = count.__getattribute__("rt")
        #info = weibogetTool()
        #info.obj = count
        #mid = info.getAtt("id")
        #comments = info.getAtt("comments")
        #rt = info.getAtt("rt")
        #print "mentions---"+ str(mid) +":"+ str(comments) +":"+ str(rt)
    for k, v in weiboMap.items():
        user = v.timeline.__getattribute__("user").name
        text = v.timeline.__getattribute__("text")
        comment = v.comment
        rt = v.rt
        print "%s:%s,%s,%s" % (user, text ,comment , rt)          
    return render_to_response('simplereadget.html')


