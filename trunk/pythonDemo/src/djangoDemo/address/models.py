#coding=utf-8
from django.db import models

## http://dikar.javaeye.com/blog/306086

# Create your models here.
class Address(models.Model):
    name = models.CharField('姓名', max_length=6, unique=True)
    gender = models.CharField('性别', choices=(('M', '男'), ('F', '女')),  max_length=1 )
    telphone = models.CharField('电话', max_length=20)
    mobile = models.CharField('手机', max_length=11)
   
    def __unicode__(self):
        return self.name
       
    class Meta:
        ordering = ['name']

from django.contrib import admin

class AddressAdmin(admin.ModelAdmin):
    model=Address
    radio_fields={'gender':admin.VERTICAL}

    
admin.site.register(Address,AddressAdmin) 