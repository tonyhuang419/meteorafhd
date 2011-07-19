# coding=UTF-8
#!/usr/bin python

from django.shortcuts import render_to_response
from django.utils.log import logger
from mail_info.forms import MailInfoForm
from mail_info.models import MailInfo
from utils import dateutils
import datetime



failResopnse = '''
    %s 确认失败。请重新确认或到首页重新提交确认。如果还是失败，联系我！
    '''

successResponse = '''
    %s 确认成功。请耐心等候您的闺蜜来访 ^-^
'''

def sure_info_create(request):
    if not request.method == 'GET':
        return render_to_response('sureInfo.html',{'show': '对不起，不支持POST方式确认' })
    else:
        email = request.GET['email']
        if sureMailInfoCreate(request):
            successResponseP = successResponse % str(email)
            return render_to_response('sureInfo.html',{'show': successResponseP })
        else:
            failResopnseP = failResopnse % str(email)
            return render_to_response('sureInfo.html',{'show': failResopnseP })
        
    
def sure_info_update(request):
    if not request.method == 'GET':
        return render_to_response('sureInfo.html',{'show': '对不起，不支持POST方式确认' })
    else:
        email = request.GET['email']
        if sureMailInfoUpdate(request):
            successResponseP = successResponse % str(email)
            return render_to_response('sureInfo.html',{'show': successResponseP })
        else:
            failResopnseP = failResopnse % str(email)
            return render_to_response('sureInfo.html',{'show': failResopnseP })
        
    
def sureMailInfoCreate(request):
    #http://192.168.1.1/mail_info/sure_info?
    #mail=meteorafhd0425@gmail.com&
    #key=5ba0f2cb2c8ed46238652fe9973701bd
    try:
        email = request.GET['email']
        key = request.GET['key']
        if not key:
            return False
    except Exception, e:
        #print(e)
        logger.info(e)
        return False
    list = MailInfo.objects.filter(email=email,validateStr=key,hasSent='0',hasBeSured='0')
    for m in list:
        m.hasBeSured = '1'
        m.validateStr = '-'
        m.save()
        logger.info('%s has be sured' % m.email)
        #print('%s has be sured' % m.email)
    if len(list) > 0:
        return True
    return False
     

def  sureMailInfoUpdate(request):
    #http://192.168.1.1/mail_info/sure_info_update?
    #mail=meteorafhd0425@gmail.com&
    #key=7134723bb9284c1936761d5703069078&
    #lastDate=2011-07-31&
    #cycle=28&
    #predays=2
    try:
        email = request.GET['email']
        key = request.GET['key']
        lastDate = request.GET['lastDate']
        cycle = request.GET['cycle']
        predays = request.GET['predays']
        if (  cycle < int(20) or int(cycle) > 45 or int(predays) < 0 or int(predays) > 3 or not key ):
            return False
    
        day = lastDate.split('-')
        lastDate = datetime.date(int(day[0]),int(day[1]),int(day[2]))
        sendDate = lastDate + datetime.timedelta(days = int(cycle)) - datetime.timedelta(days= int(predays))
        #print(sendDate)
    except Exception, e:
        #print(e)
        logger.info(e)
        return False

    list = MailInfo.objects.filter(email=email,validateStrUpdate=key,hasSent='0')
    #print(list)
    for m in list:
        m.lastDate = lastDate
        m.sendDate = sendDate
        m.cycle = cycle
        m.predays = predays
        m.hasBeSured = '1'
        m.validateStrUpdate = '-'
        m.save()
        logger.info('%s has be update' % m.email)
    if len(list) > 0:
        return True
    return False
    
    
