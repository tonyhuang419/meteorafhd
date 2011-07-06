# coding=UTF-8
from django import template

register = template.Library()

@register.filter(name='mycut')
def mycut(value,arg):
	return value.replace(arg,'......')

#register.filter('mycut', mycut)  
