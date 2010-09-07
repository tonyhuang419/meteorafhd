# coding=UTF-8
import sys

print(sys.path)

x , y = "1","2"
print( x )
print( y )
[x,y] = [1,2]
print( x )
print( y )  

print( 1 )
print( 2 ),   ##no new line
print( 3 )

if 1<2<3:
    print("yes")
    
if 1<2 and 2<3:
    print("yes")

temp = sys.stdout
print( temp )

sys.stdout = open('c:\\1.txt','a')  
sys.stdout.write("hello")
sys.stdout.close()

sys.stdout = temp
sys.stdout.write("helloToScreen\n")

file = open('c:\\1.txt','a')  
print >> file , 1, 2, 3
file.close()

print(  { 'a':'1','b':'2','c':'3' }['b'] )
branch = { 'a':'1','b':'2','c':'3' }
print( branch.get('b') )

print("------------")
import rawinput
print(rawinput.exit)
rawinput.exit = False
print(rawinput.exit)


def f1():
    x = 88
    def f2():
        print(x)
    f2()
    print("------------")
f1()


