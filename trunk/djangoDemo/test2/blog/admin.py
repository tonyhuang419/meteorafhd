# coding=UTF-8
'''
Created on 2011-6-30

@author: FHDone
'''

from blog.models import Article, Category, Author
from django.contrib import admin

admin.site.register(Category)
admin.site.register(Article)
admin.site.register(Author)