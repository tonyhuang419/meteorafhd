# coding=UTF-8
#!/usr/bin python

#import utils
#from utils import *
import utils as u

list1=['a','b','c',['1','2','3']]
for i in list1:
    if isinstance(i , list):
        print i
        
comment = '''1234556789'''
print comment

u.p('print it' , 3)