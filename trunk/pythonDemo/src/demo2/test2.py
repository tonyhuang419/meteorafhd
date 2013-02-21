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

print '--------------------'
u.readfile('test.txt')

print 'a:b:c' .split(':',1)

print (1<2) and 3 or 4

#http://www.52sky.org/41001.html
ip =  '192.168.10.214'
for k,v in zip(ip.split('.'),[('192',0x1000000),('168',0x10000),('10',0x100),('214',1)]):
    print k,v

#首先ip.split('.')得到列表['192','168','10','214']，经过zip一组装，就变成 #接着for循环将各个元组的两项做整数乘法，最后将新列表的值用sum求和，得到结果
f = lambda ip: sum( [ int(k)*v[1] for k, v in zip(ip.split('.'),[('192',0x1000000),('168',0x10000),('10',0x100),('214',1)])])
a = f('192.168.10.214')
print hex(a)



