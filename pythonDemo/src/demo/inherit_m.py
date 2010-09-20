
class A:
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
# order:D B A C
print ( x.attr ) 
