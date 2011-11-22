# coding=UTF-8
#!/usr/bin python

from django.shortcuts import render_to_response
from django.utils.log import logger
from mail_info.forms import MailInfoForm
from mail_info.models import MailInfo
from utils import dateutils, emailutil
import datetime

def index(request):
    return render_to_response('after28.html')

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
            mCreate.hasBeSured = '0'
            mCreate.save()
            emailutil.sendCreateSureMail(mCreate)
            responseInfo = '%s 提交成功，请查收邮件并确认。' % str(email)
            return render_to_response('firstPage.html',{'show': responseInfo })
        else:
            #该邮件地址存在发送信息，给客户邮件确认修改
            logger.info('has exist send info,update it')
            for mUpdate in emailList:
                mUpdate.validateStrUpdate = dateutils.getMD5Time()
                #mUpdate.modifyDate = datetime.datetime.now()
                #logger.info(request.POST['sendDate'])
                #logger.info(request.POST['lastDate'])
                #logger.info(request.POST['cycle'])
                #logger.info(request.POST['predays'])
                mUpdate.save()
                emailutil.sendUpdateSureMail(request,mUpdate)
                responseInfo = '%s 曾今提交过哦，如果有修改，请查收邮件并重新确认。  ' % str(email)
            return render_to_response('firstPage.html',{'show': responseInfo })
    else:
        return render_to_response('firstPage.html',{'show':'对不起，我们暂时不支持GET方式提交。'})
        
def hasExist(email):
    list = MailInfo.objects.filter(email=email,hasSent='0')
    return list
