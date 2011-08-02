# coding=UTF-8
#!/usr/bin python

'''
Created on 2011-7-18

@author: FHDone
'''

from django.core.mail import EmailMultiAlternatives
from django.utils.log import logger
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from settings import STATIC_URL
import smtplib
import sys
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
        msg = EmailMultiAlternatives(self.subject, self.body, 'fhdone@after28.com', self.recipient_list)
        if self.html:
            msg.attach_alternative(self.body,self.html)
        logger.info('send mail to %s----%s' %  ( self.recipient_list, self.body ) )
        msg.send(self.fail_silently)  
        
        #sendinfo = MIMEMultipart('alternative')
        #sendinfo['Subject'] = self.subject
        #sendinfo['From'] = 'root@domu-12-31-39-03-28-87.compute-1.internal'
        #sendinfo['To'] = self.recipient_list   
        #msg = MIMEText( self.body  , 'html')
       
        #sendinfo.attach(msg)
        #server = smtplib.SMTP('localhost')
        #server.sendmail("root@domu-12-31-39-03-28-87.compute-1.internal", self.recipient_list , msg.as_string() )
        #server.quit()
        
 
        
        
def send_mail(subject, body, recipient_list, fail_silently=False, *args, **kwargs):
        EmailThread(subject, body, recipient_list, fail_silently ).start()



 
 

create_mail_template = '''   
<html>
  <head></head>
  <body>  
        感谢您使用After28！<br/><br/>点击下面的链接确认邮件地址
     <br/><br/>
     <a href="%s/mail_info/sure_info_create?email=%s&key=%s">
     %s/mail_info/sure_info_create?email=%s&key=%s</a>
     <br/><br/>%s
   </body>
</html>
'''
def sendCreateSureMail(mi):
    email = str(mi.email)
    key = str(mi.validateStr)
    sendinfo = create_mail_template % ( STATIC_URL, email , key , 
                                        STATIC_URL, email , key ,  cancelMailHref(mi) )
    send_mail(  "After28邮件确认", sendinfo ,   [email] )





update_mail_template = '''   
<html>
  <head></head>
  <body>  
       感谢您使用After28！<br/>点击下面的链接提醒日期修改为   %s
     <br/><br/><a href="%s/mail_info/sure_info_update?email=%s&key=%s&lastDate=%s&cycle=%s&predays=%s"/>
     %s/mail_info/sure_info_update?email=%s&key=%s&lastDate=%s&cycle=%s&predays=%s</a>
     <br/><br/>%s
   </body>
</html>
'''
def sendUpdateSureMail(request , mi):
    lastDate = str(request.POST['lastDate'])
    cycle = str(request.POST['cycle'])
    predays = str(request.POST['predays'])
    sendDate = str(request.POST['sendDate'])
    email = str(mi.email)
    validateStrUpdate = str(mi.validateStrUpdate)
    sendinfo = update_mail_template % ( sendDate ,  STATIC_URL , email, validateStrUpdate , lastDate  , cycle , predays , 
                                        STATIC_URL, email , validateStrUpdate , lastDate  , cycle , predays , cancelMailHref(mi) )
    send_mail(  "After28邮件确认", sendinfo ,   [email] )






cancel_mail_template = '''   
    取消邮件发送
  <a href="%s/mail_info/sure_info_cancel?email=%s&key=%s">%s/mail_info/sure_info_cancel?email=%s&key=%s</a>
'''
def cancelMailHref(mi):
    email = str(mi.email)
    key = str(mi.validateStr)
    return cancel_mail_template % ( STATIC_URL, email, key , STATIC_URL,email , key )




send_mail_notify_template = '''   
<html>
  <head></head>
  <body>  
    您好，%s！ 您最亲密的朋友最要来了，记得准备好您的闺蜜哦  ^-^
     <br/><br/>%s
   </body>
</html>
'''
def sendMailNotify(mi):
    email =  str(mi.email)
    sendinfo = send_mail_notify_template % ( email , cancelMailHref(mi) )
    send_mail(  "After28提醒邮件", sendinfo ,   [email] )
    


def test_mail():
    print('--------')
    send_mail( "subject" ,"<html><body><h1>hello</h1><body></html>", 
                ["meteorafhd0425@gmail.com"] )
    print('ok')
    return "ok"
