# coding=UTF-8
'''
Created on 2011-6-30

@author: FHDone
'''
from django.db import models

class Category(models.Model):
    name = models.CharField(max_length=32)
    def __unicode__(self):
        return self.name
    class Admin:
        pass

class Article(models.Model):
    title         = models.CharField(max_length=64)
    published_at  = models.DateTimeField('date published')
    content       = models.TextField()
    category      = models.ForeignKey(Category)
    def __unicode__(self):
    #print(self.title)
        return self.title
    class Admin:
        pass
    class Meta:
        ordering = ['title','-published_at']

class Author(models.Model):
    name      = models.TextField()
    def __unicode__(self):
        return self.name
    class Admin:
        pass