# class A(object):  #新式类：宽度优先 order:D C B A  
#class A():          #旧式类：深度优先  order:D B A C 
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
