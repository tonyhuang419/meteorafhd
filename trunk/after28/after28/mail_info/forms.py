# coding=UTF-8
#!/usr/bin python

'''
Created on 2011-7-13

@author: FHDone
'''
from django.forms.models import ModelForm
from mail_info.models import MailInfo

class MailInfoForm(ModelForm):
     
    class Meta:
        model = MailInfo
        #fields = ('name', 'title')
        exclude = ('hasBeSured','validateStr','validateStrUpdate','hasSent',
                   'createDate','modifyDate')
        
