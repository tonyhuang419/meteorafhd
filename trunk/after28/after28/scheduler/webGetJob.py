# coding=UTF-8
#!/usr/bin python
'''
Created on 2011-7-20

@author: FHDone
'''

from after28.utils.webget.gewara import signin as gSignin
from after28.utils.webget.xiami import signin as xSignin
from django.utils.log import logger
import thread
import time


def webSignin():
    logger.info('###############gewara signin###############')
    gSignin()
    logger.info('###############xiami signin###############')
    xSignin()
   
   
    
def webGetJob():
    while True:
        thread.start_new_thread(webSignin,()) 
        time.sleep( 86400/2 )
