import helloworld;

print( '---------------------------------' )
print( helloworld.__dict__ )
print( helloworld.__dict__.keys() )
print( '---------------------------------' )

print helloworld.s;
helloworld.s = '*****'
print helloworld.s;
reload(helloworld)
print helloworld.s;
print( '---------------------------------' )

def sayHello():
    print ('Hello World!') # block belonging to the function

sayHello() # call the function 


def factorial(num , defvalue = 1):
    print("def value:"+str(defvalue));
    if(num==1):
        return num;
    elif(num>0):
        sum = factorial(num-1) * num;
        return sum;
    

print(factorial(5));

def feb(num):
    numList = ['1']
    lastNum = 0
    lastNum2 = 1
    sum = 1
    for i in range(num):
        temp = lastNum2
        lastNum2 =  lastNum + lastNum2
        lastNum = temp
        numList.extend(str(lastNum2))
        sum = sum + lastNum2
    print(numList)
    return sum

print feb(4)



def printMax(x, y):
    '''Prints the maximum of two numbers.

    The two values must be integers.'''
    x = int(x) # convert to integers, if possible
    y = int(y)

    if x > y:
        print(x), 'is maximum'
    else:
        print (y), 'is maximum'

printMax(3, 5)
print(printMax.__doc__) 

pass

def intersect( seq1 , seq2 ):
    res = []
    for x in seq1:
        if x in seq2:
            res.append(x)
    return res

print( intersect( "1234" , "2345"  ) )
print( intersect( [1,2,3] , (3,4,5) ) )


def marker(X):
    def action(N):
        return X ** N
    return action;
ff = marker(2);
print( ff(3) )    


def make_repeater(n):
    return lambda s: s*n
twice = make_repeater(2)
print ( twice('word'))
print ( twice(5) )

def nameFunction( a=11 , b=22 , c=33):
    print( a , b , c )
    print( a + b + c)
    print( str(a) + str(b) + str(c)  )
nameFunction( a=1 , c=3 , b=2 )
nameFunction( 1, c=3  )


print('------------------------')
def varArgs( a , b , *args , **args2 ):
    print( str(a) + str(b) + str(args) + str(args2) )
varArgs( 1,2,3,4,5,6,x=1,y=2 )

mapFunction = { 'x' : (  lambda x : x * x ) ,
                'y' : (  lambda y : y ** y ) ,
                'z' : (  lambda z : z ** z + 1 )  }
print(mapFunction)
myX = mapFunction['x'];
myY = mapFunction['y'];
myZ = mapFunction['z'];
print(myX)
print( myX(2) )
print( myY(3) )
print( myZ(3) )


def square(a):
    return a ** 2 

def funcMap( func , datas ):
    res = []
    for x in datas: res.append( func( x ) )
    return res

print( square(3) )
print( funcMap( square , [1,2,3] ) )


def genFun( len ):
    for i in range( len ):
        yield i

for i in genFun( 5 ):
    print( i ) , 
    
print()
print( sorted(  x**2 for x in range(5) ) )
print( sorted( ( x**2 for x in range(5)) , reverse=True ) )

def saver1( x=[] ):
    x.append(1)
    print(x)
    
saver1()
saver1()

def saver2( x=None ):
    if( x==None):
        print("new arr")
        x = []
    x.append(1)
    print(x)
saver2 (   )   
saver2 ( [1]  )
saver2 ( []  )


def getMult():
    '''i am getmult'''
    return 1,2

print('---------------')
print(getMult.__doc__)

x , y = getMult();
z = getMult();
print(x)
print(y)
print(z)
print(type(x))
print(type(z))


def func(data):  
    count = [data]  
    def wrap():  
        count[0] += 1  
        return count[0]  
    return wrap    
a = func(1)  
print(a())
print(a())  


g  = 0;
def xg():
    g = 1;
print(g)  #it's global g, not fun xg's g
    
def func2(a , b=0):
    print( "%s ... %s" % (a,b) )
func2(a=2)

print('--------------')
x = (1,2)
y = (3,4)
map(func2 , x , y )
    
