import helloworld;

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

 

