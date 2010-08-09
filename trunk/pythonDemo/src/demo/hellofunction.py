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

