# class A(object):  #��ʽ�ࣺ������� order:D C B A  
#class A():          #��ʽ�ࣺ�������  order:D B A C 
class A():          
    attr = 'A'

class B(A):
#     attr = 'B'
    pass

class C(A):
    attr = 'C'

class D(B,C):
#    attr = 'D'
    pass

x = D();

print ( x.attr ) 
