# coding=UTF-8
#!/usr/bin python
'''
Created on 2011-7-20

@author: FHDone
'''
from after28.mail_info.models import MailInfo
from after28.utils import dateutils
from after28.utils.dateutils import getNextDayReaminSeconds
from after28.utils.emailmutil import sendMailNotify
from django.utils.log import logger
from threading import Timer
import datetime
import time


def mailJobToday():
    # 计算当前时间距离24点差
    Timer( getNextDayReaminSeconds() , sendMailEveryDay, ()).start()
    #Timer( 60 , sendMailEveryDay, ()).start()
    
def mailJobDays():
    #Timer( 10 , sendMailEveryDay, ()).start()
    Timer( 86400 , sendMailEveryDay, ()).start()
    
def sendMailEveryDay():
    print('###############schedual send mail###############')
    
    #get today
    today = datetime.date.today()
    date = today.strftime('%Y')+'-'+today.strftime('%m')+'-'+today.strftime('%d')
    list = MailInfo.objects.filter(hasSent='0', sendDate__lte=date)
    
    try:
        for m in list:
            print(m.email)
            #send mail
            sendMailNotify(m)
            m.hasSent = '1'
            m.save()
            genNewMailInfo(m)
    except Exception, e:
        print(e)
        logger.info(e)
    finally:    
        mailJobDays(); 
        
def genNewMailInfo(mi):
    m = MailInfo()
    m.email = mi.email
    m.hasBeSured = '1'
    m.cycle = mi.cycle
    m.validateStr = '-'
    m.validateStrUpdate = '-'
    m.validateStr = dateutils.getMD5Time()
    m.createDate = datetime.datetime.now()
    m.hasSent = '0'
    m.lastDate = mi.sendDate
    sendDate = mi.sendDate + datetime.timedelta(days = int(  mi.cycle ))
    m.sendDate = sendDate
    m.save()      
        
    
    
