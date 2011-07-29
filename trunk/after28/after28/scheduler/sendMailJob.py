# coding=UTF-8
#!/usr/bin python
'''
Created on 2011-7-20

@author: FHDone
'''
from after28.mail_info.models import MailInfo
from after28.utils import dateutils
from after28.utils.dateutils import getNextDayReaminSeconds
from after28.utils.emailutil import sendMailNotify
from django.utils.log import logger
from threading import Timer
import datetime
import threading


lock = threading.Lock() 

def mailJobToday():
    # 计算当前时间距离24点差
    global hasopen
    hasopen = 0
    lock.acquire()
    logger.info(hasopen==0)
    if hasopen == 0:
        logger.info('###############first send mail###############')
        ++hasopen
        Timer( getNextDayReaminSeconds()/24, sendMailEveryDay, ()).start()
        #Timer( 20 , sendMailEveryDay, ()).start()
    lock.release()
    
def mailJobDays():
    #Timer( 10 , sendMailEveryDay, ()).start()
    Timer( 86400/24 , sendMailEveryDay, ()).start()
    
def sendMailEveryDay():
    logger.info('###############schedual send mail###############')
    
    #get today
    today = datetime.date.today()
    date = today.strftime('%Y')+'-'+today.strftime('%m')+'-'+today.strftime('%d')
    logger.info( date )
    list = MailInfo.objects.filter(hasSent='0', hasBeSured='1' ,sendDate__lte=date)
    logger.info('need send mail %s ' % len(list))
    try:
        for m in list:
            logger.info(m.email)
            #send mail
            sendMailNotify(m)
            m.hasSent = '1'
            m.save()
            genNewMailInfo(m)
    except Exception, e:
        #print(e)
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
        
    
    
