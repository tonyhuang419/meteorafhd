#coding=utf-8

# http://luozhaoyu.iteye.com/blog/1512300
class Propt(object):

    def __init__(self):
        # self.__a2 = 'a2'
        # self._a1 = 'a1'
        # self.a = 'a'
        self._field1 = 'field1'

    @property
    def field1(self):
        print("field1 got")
        return self._field1
    
    @field1.setter
    def field1(self, val):
        print("field1 set")
        self._field1 = val


# http://www.the5fire.net/python-property-staticmethod-classmethod.html
class MyClass(object):
    def __init__(self):
        print 'init'
        self._name = 'the5fire'

    @staticmethod
    def static_method():
        print 'This is a static method!'

    @classmethod
    def class_method(cls):
        print 'This is a class method',cls
        print 'visit the property of the class:',cls.name
        print 'visit the static method of the class:',cls.static_method()
        instance = cls()
        print 'visit the normal method  of the class:',instance.test()


    def test(self):
        print 'call test'

    @property
    def name(self):
        return self._name

    @name.setter
    def name(self,val):
        return self._name

if __name__ == '__main__':
  
    p = Propt()
    print(p.field1)
    p.field1 = 'a'

    print '##############################'

    MyClass.static_method()
    MyClass.class_method()
    mc = MyClass()
    print mc.name
    # mc._name = 'huyang'
    mc.name = 'huyang'

a = '1,2,3,4,5'
b = a.split(',')
print type(b)