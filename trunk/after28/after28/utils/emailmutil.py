# coding=UTF-8
#!/usr/bin python

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


 
 
 

create_mail_template = '''   
<html>
  <head></head>
  <body>  
       欢迎使用After128，点击下面的链接确认邮件地址
     <br/><br/>
     <a href="http://192.168.1.1/mail_info/sure_info_create?email=%s&key=%s">http://192.168.1.1/mail_info/sure_info_create?email=%s&key=%s</a>
   </body>
</html>
'''
def sendCreateSureMail(mi):
    sendinfo = create_mail_template % ( str(mi.email), str(mi.validateStr) , 
                                        str(mi.email), str(mi.validateStr) )
    send_mail(  "After28邮件确认", sendinfo ,   [mi.email] )


update_mail_template = '''   
<html>
  <head></head>
  <body>  
       欢迎使用After128，点击下面的链接确认提醒日期修改为%s
     <br/><br/>
     <a href="http://192.168.1.1/mail_info/sure_info_update?email=%s&key=%s&lastDate=%s&cycle=%s&predays=%s" />
     http://192.168.1.1/mail_info/sure_info_update?email=%s&key=%s&lastDate=%s&cycle=%s&predays=%s</a>
   </body>
</html>
'''
def sendUpdateSureMail(request , mi):
    lastDate = str(request.POST['lastDate'])
    cycle = str(request.POST['cycle'])
    predays = str(request.POST['predays'])
    sendDate = str(request.POST['sendDate'])
    sendinfo = update_mail_template % ( sendDate ,  str(mi.email), str(mi.validateStrUpdate), lastDate  , cycle , predays , 
                                       str(mi.email), str(mi.validateStrUpdate), lastDate  , cycle , predays )
    send_mail(  "After28邮件确认", sendinfo ,   [mi.email] )



def test_mail():
    print('--------')
    send_mail( "subject" ,"<html><body><h1>hello</h1><body></html>", 
                ["meteorafhd0425@gmail.com"] )
    print('ok')
    return "ok"


    
    