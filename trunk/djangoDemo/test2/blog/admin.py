# coding=UTF-8
'''
Created on 2011-6-30

@author: FHDone
'''

from blog.models import Article
from blog.models import Category
from django.contrib import admin

admin.site.register(Category)
admin.site.register(Article)