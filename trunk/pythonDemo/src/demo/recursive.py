# coding=UTF-8

def fact(n):
    if n==1:
        return 1
    else:
        return n * fact(n-1)

def factTail(n , acc=1):
    if n==1:
        return acc
    else:
        return factTail(n-1 , acc*n)

print fact(5)
print factTail(5)




# 1 1 2 3 5 8 11
def fibonacc(n):
    if n<3:
        return 1
    else:
        return fibonacc(n-1) + fibonacc(n-2)
    
def fibonaccTail(n ,cur=0 , next=1):
    if n==0:
        return cur
    else:
        return fibonaccTail(n-1 ,next , cur + next )    
   
import time

# begin = time.time()   
print fibonacc(5)   #so slower
# end = time.time()
# print end-beginn

# begin = time.time())
print fibonaccTail(5)
# end = time.time()
# print end-begin  
