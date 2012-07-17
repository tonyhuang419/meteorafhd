# coding=UTF-8
from operator import * # import all operators
import os

class DevNull1(object): 
    def __get__(self, obj, typ=None): 
        pass 
    def __set__(self, obj, val): 
        pass

class DevNull2(object): 
    def __get__(self, obj, typ=None): 
        print 'Accessing attribute... ignoring' 
    def __set__(self, obj, val): 
        print 'Attempt to assign %r... ignoring' % (val) 
    
class C1(object): 
    foo = DevNull1()
    
class C2(object): 
    foo = DevNull2() 

    
c1 = C1() 
c1.foo = 'bar' 
print 'c1.foo contains:', c1.foo  

c2 = C2() 
c2.foo = 'bar' 
x = c2.foo 
print 'c2.foo contains:', x  

vec1 = [12, 24] 
vec2 = [2, 3, 4] 
opvec = (add, sub, mul, div)  # using +, -, *, / 
for eachOp in opvec: # loop thru operators 
    for i in vec1: 
        for j in vec2: 
            print '%s(%d, %d) = %d' % \
            (eachOp.__name__, i, j, eachOp(i, j)) 


print 'the total is: %s' % reduce((lambda x,y:x+y),range(10))
print map(lambda x : x * 2,[1,2,3,4])

def sayHello():
    '''sayHello_doc'''
    print ('Hello World!') # block belonging to the function
print sayHello.__name__
print sayHello.func_name
print sayHello.__doc__
print os.getpid()


