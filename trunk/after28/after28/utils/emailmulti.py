# coding=UTF-8
'''
Created on 2011-7-18

@author: FHDone
'''

from django.core.mail import EmailMultiAlternatives
from django.utils.log import logger
import threading

class EmailThread(threading.Thread):
    def __init__(self, subject, body,  recipient_list, fail_silently ):
        self.subject = subject
        self.body = body
        self.recipient_list = recipient_list
        self.fail_silently = fail_silently
        self.html = "text/html"
        threading.Thread.__init__(self)
        
    def run (self):
        msg = EmailMultiAlternatives(self.subject, self.body, 'None', self.recipient_list)
        if self.html:
            msg.attach_alternative(self.body,self.html)
        logger.info('send mail to %s----%s' %  ( self.recipient_list, self.body ) )
        msg.send(self.fail_silently)
        
def send_mail(subject, body, recipient_list, fail_silently=False, *args, **kwargs):
        EmailThread(subject, body, recipient_list, fail_silently ).start()


      
def test_mail():
    print('--------')
    send_mail( "subject" ,"<html><body><h1>hello</h1><body></html>", 
                ["meteorafhd0425@gmail.com"] )
    print('ok')
    return "ok"


    
    