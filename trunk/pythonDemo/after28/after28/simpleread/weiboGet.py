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
            #print e
            return ''

        
tool = Tool()

class weibogetInfo():
    timeline=None
    commentNum=0
    rtNum=0
    reid=''
    reRtNum=0
    reCommentNum=0
    countSum=0
        
def weiboget(request):
    access_token = request.session['oauth_access_token']
    #logger.info(access_token)
    auth = OAuthHandler(WebOAuthHandler.consumer_key, WebOAuthHandler.consumer_secret)
    auth.access_token = access_token
    api=API(auth)
    
    logger.info('get sina weibo info start')
    timeline = api.friends_timeline(count=50, page=1)
    
    weiboMap={}
    weiboReMap={}
    ids=''
    reids=''
    for line in timeline:
        info = weibogetInfo()
        wid = tool.getAtt(line , 'id')
        info.timeline = line
        weiboMap[wid] = info
        ids = '%s,%s' % (ids, wid )
        if tool.getAtt(line , 'retweeted_status'):
            reid=info.timeline.retweeted_status.id
            info.reid=reid
            reids = '%s,%s' % (reids, reid )
            weiboReMap[reid]=wid

    counts = api.counts(ids)
    recounts = api.counts(reids)
    for count in counts:
        wid = count.__getattribute__("id")
        if weiboMap.has_key(wid):
            info = weiboMap[wid]
            info.commentNum = int(tool.getAtt(count , 'comments'))
            info.rtNum = int(tool.getAtt(count , 'rt'))
            info.countSum = info.commentNum + info.rtNum
    for count in recounts:
        rewid = count.__getattribute__("id")
        wid = weiboReMap[rewid]
        info =  weiboMap[wid]
        info.reCommentNum=int(tool.getAtt(count , 'rt'))
        info.reRtNum=int(tool.getAtt(count , 'rt'))
        info.countSum=info.countSum+info.reCommentNum+info.reRtNum
        
    sortby = request.GET.get('sortby',-1)
    weiboList=None
    if sortby==1:
        weiboList = sorted( weiboMap.iteritems(), key=lambda d:d[1].commentNum , reverse = True )
    elif sortby==2:
        weiboList = sorted( weiboMap.iteritems(), key=lambda d:d[1].rtNum , reverse = True )
    elif sortby==3:
        weiboList = sorted( weiboMap.iteritems(), key=lambda d:d[1].reCommentNum , reverse = True )
    elif sortby==4:
        weiboList = sorted( weiboMap.iteritems(), key=lambda d:d[1].reRtNum , reverse = True )
    else:
        weiboList = sorted( weiboMap.iteritems(), key=lambda d:d[1].countSum , reverse = True )
    
    #for k, v in weiboMap.items():
    '''
    for v in weiboList:
        user = tool.getAtt(v[1].timeline , 'user').name
        text = tool.getAtt(v[1].timeline , 'text')
        #next 2lines for ms-dos
        text = text.encode('gb2312' , 'ignore')
        text = unicode(text,'gb2312')       
        print "%s:%s,%d,%d,%d" % (user, text , v[1].commentNum , v[1].rtNum , v[1].sum )
    '''
    logger.info('get sina weibo info finish')
    return render_to_response('simplereadget.html' , {'weiboList': weiboList })


