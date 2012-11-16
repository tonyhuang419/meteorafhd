# -*- coding: utf-8 -*-

#http://caterpillar.onlyfun.net/Gossip/Python/NewInitDel.html
class Singleton(object):
    __single = None
    def __new__(self):
        if not Singleton.__single:
            Singleton.__single = object.__new__(self)
        return Singleton.__single
        
    def doSomething(self):
        print("do something...XD")

singleton1 = Singleton()
singleton1.doSomething()  # do something...XD

singleton2 = Singleton()
singleton2.doSomething()  # do something...XD

print singleton1
print singleton2
print(singleton1 is singleton2)  # True

#http://www.cnblogs.com/pyzend/archive/2011/05/22.html
class OnlyOne(object):
    class __OnlyOne:
        def __init__(self,arg):
            self.val = arg
        def __str__(self):
            return `self` + self.val
    instance = None
    def __new__(cls,arg):
        if not OnlyOne.instance:
            OnlyOne.instance = OnlyOne.__OnlyOne(arg)
        return OnlyOne.instance
    def __getattr__ (self, name):
        return getattr(self.instance, name)
    def __setattr__ (self, name):
        return setattr(self.instance, name)

x = OnlyOne('sausage') 
print x 
y = OnlyOne('eggs') 
print y 
z = OnlyOne('spam') 
print z 
print x 
print y 