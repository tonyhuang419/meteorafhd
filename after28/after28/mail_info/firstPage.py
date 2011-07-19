# coding=UTF-8
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
from django.utils.log import logger
from mail_info.forms import MailInfoForm
from mail_info.models import MailInfo
from utils import dateutils
from utils import emailmulti
import datetime

def index(request):
    return render_to_response('afer28.html')

def mailInfoSave(request):
    form = MailInfoForm(request.POST)
    if request.method == 'POST' and form.is_valid():
        email = request.POST['email']
        emailList = hasExist(email)
        if ( len(emailList) == 0):
            logger.info('save mail info')
            mCreate = form.save(commit=False)
            mCreate.validateStr = dateutils.getMD5Time()
            mCreate.createDate = datetime.datetime.now()
            mCreate.hasSent = '0'
            mCreate.save()
            sendCreateSureMail(mCreate)
            return HttpResponseRedirect('/')
        else:
            #该邮件地址存在发送信息，给客户邮件确认修改
            logger.info('has exist send info,update it')
            for mUpdate in emailList:
                mUpdate.validateStrUpdate = dateutils.getMD5Time()
                mUpdate.modifyDate = datetime.datetime.now()
                #logger.info(request.POST['sendDate'])
                #logger.info(request.POST['lastDate'])
                #logger.info(request.POST['cycle'])
                #logger.info(request.POST['predays'])
                mUpdate.save()
                sendUpdateSureMail(request,mUpdate)
            return HttpResponseRedirect('/')
    else:
        return HttpResponseRedirect('/')
        
def hasExist(email):
    list = MailInfo.objects.filter(email=email,hasSent='0')
    return list

create_mail_template = '''   
<html>
  <head></head>
  <body>  
       欢迎使用After128，点击下面的链接确认邮件地址
     <br/><br/>
     http://192.168.1.1/mail_info/sure_info_create?mail=%s&key=%s
   </body>
</html>
'''
def sendCreateSureMail(mi):
    sendinfo = create_mail_template % ( str(mi.email), str(mi.validateStr))
    emailmulti.send_mail(  "After28邮件确认", sendinfo ,   ["meteorafhd0425@gmail.com"] )


update_mail_template = '''   
<html>
  <head></head>
  <body>  
       欢迎使用After128，点击下面的链接确认提醒日期修改为%s
     <br/><br/>
     http://192.168.1.1/mail_info/sure_info_update?mail=%s&key=%s&lastDate=%s&cycle=%s&predays=%s
   </body>
</html>
'''
def sendUpdateSureMail(request , mi):
    lastDate = str(request.POST['lastDate'])
    cycle = str(request.POST['cycle'])
    predays = str(request.POST['predays'])
    sendDate = str(request.POST['sendDate'])
    sendinfo = update_mail_template % (sendDate ,  str(mi.email), str(mi.validateStrUpdate), lastDate  , cycle , predays )
    emailmulti.send_mail(  "After28邮件确认", sendinfo ,   ["meteorafhd0425@gmail.com"] )



