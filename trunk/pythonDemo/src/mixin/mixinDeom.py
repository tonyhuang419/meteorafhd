# coding=UTF-8
# http://www.cnblogs.com/huazi/archive/2012/05/09/2492041.html

# class foo:
#     pass 

# class foobase:
#     def hello(self):
#         print "hello" 
#         
# obj=foo()         
# foo.__bases__ +=(foobase,)
# obj.hello() 


class foobase:
    def a(self):
        print "hello"

class foo:
    def a(self):
        print "foo"

f=getattr(foobase, "a")
setattr(foo, "a", f.im_func)     #f.im_func会得到真正的函数对象
c=foo()
c.a() 