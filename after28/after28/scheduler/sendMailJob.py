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
import time
import thread


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
        # Timer( getNextDayReaminSeconds()/24, sendMailEveryDay, ()).start()
        Timer( 10 , mailJobDays, ()).start()
    lock.release()
    
def mailJobDays():
    #Timer( 10 , sendMailEveryDay, ()).start()
    while True:
        thread.start_new_thread(sendMailEveryDay,()) 
        time.sleep( 86400/3 )
    
def sendMailEveryDay():
    logger.info('###############schedual send mail###############')
    
    #get today
    today = datetime.date.today()
    date = today.strftime('%Y')+'-'+today.strftime('%m')+'-'+today.strftime('%d')
    logger.info( date )
    listx = MailInfo.objects.filter(hasSent='0', hasBeSured='1' ,sendDate__lte=date)
    logger.info('need send mail %s ' % len(listx))
    try:
        for m in listx:
            logger.info(m.email)
            #send mail
            sendMailNotify(m)
            m.hasSent = '1'
            m.save()
            genNewMailInfo(m)
    except Exception, e:
        #print(e)
        logger.info(e)
        
def genNewMailInfo(mi):
    m = MailInfo()
    m.email = mi.email
    m.hasBeSured = '1'
    m.cycle = mi.cycle
    m.validateStr = mi.validateStr
    m.validateStrUpdate = '-'
    m.createDate = datetime.datetime.now()
    m.hasSent = '0'
    m.lastDate = mi.sendDate
    sendDate = mi.sendDate + datetime.timedelta(days = int(  mi.cycle ))
    m.sendDate = sendDate
    m.save()      
        
    
    
