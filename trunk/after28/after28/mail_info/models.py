# coding=UTF-8
'''
Created on 2011-6-30

@author: FHDone
'''
from django.db import models

class MailInfo(models.Model):
    
    #email address
    email = models.CharField(max_length=50)
   
    #last period time
    lastDate = models.DateField(null=True)
    
    #next period time , send email or sms
    sendDate = models.DateField(null=True)
    
    #cycle days
    cycle = models.IntegerField(null=True)
   
    #validate string info,use for make sure email address
    validateStr = models.CharField(max_length=200,null=True)

    #(update use)validate string info,use for make sure email address
    validateStrUpdate = models.CharField(max_length=200,null=True)
    
    #mail has sent ,0:not send 1:has sent
    hasSent = models.CharField(max_length=1,null=False)
    
    createDate = models.DateTimeField(null=True)
    modifyDate = models.DateTimeField(null=True)
    
    def __unicode__(self):
        return '%s/%s' % (self.email,self.sendDate)
    class Admin:
        pass

