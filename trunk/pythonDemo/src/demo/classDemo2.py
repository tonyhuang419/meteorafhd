# coding=UTF-8

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



from operator import * # import all operators 
vec1 = [12, 24] 
vec2 = [2, 3, 4] 
opvec = (add, sub, mul, div)  # using +, -, *, / 
for eachOp in opvec: # loop thru operators 
    for i in vec1: 
        for j in vec2: 
            print '%s(%d, %d) = %d' % \
            (eachOp.__name__, i, j, eachOp(i, j)) 



 