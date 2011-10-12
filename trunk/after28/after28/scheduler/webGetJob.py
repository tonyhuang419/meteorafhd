# coding=UTF-8
#!/usr/bin python
'''
Created on 2011-7-20

@author: FHDone
'''

from after28.utils.webget.gewara import signin as gSignin
from after28.utils.webget.xiami import signin as xSignin
from after28.utils.dateutils import getNextDayReaminSeconds
from django.utils.log import logger
from threading import Timer
import thread
import threading
import time

lock = threading.Lock() 

def webSigninJob():
    global hasopen
    hasopen = 0
    lock.acquire()
    logger.info(hasopen==0)
    if hasopen == 0:
        logger.info('###############website login###############') 
        ++hasopen
        webSignin
        Timer( getNextDayReaminSeconds(), webGetJob, ()).start()
    lock.release()
     
def webSignin():
    logger.info('###############gewara signin###############')
    gSignin()
    logger.info('###############xiami signin###############')
    xSignin()
    
def webGetJob():
    while True:
        thread.start_new_thread(webSignin,()) 
        time.sleep( 86400/2 )
