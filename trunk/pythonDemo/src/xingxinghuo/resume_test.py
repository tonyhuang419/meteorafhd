# coding=UTF-8

'''
 Please programme a function to count the result of (1-2+3-4+5-....+n)
 *[1+(1/2)+(1/2)2 +(1/2)3 +(1/2)4 +...+(1/2)n ], n is a input parameter
 (Any programming language is ok). Please analyze the time complexity of your algorithm.
''' 
def sum(n):
    sum1 = 0
    sum2 = 0
    sqar = 1
    for i in range(1,n+1):
        if i%2==1:
            sum1 = sum1+i
        else:
            sum1 = sum1-i
        if i>1:
            sqar = sqar*0.5
        sum2 = sum2+sqar
        #print 'sum %f %f' % (sum1,sum2)
    return sum1*sum2

print sum(3)

#time complexity:Θ(n)
#algorithm:I have used the simplest solution,but it exist better solution
#exmaple: (1-2+3-4+5-....+n)
#odd numbers 1,3,5,7,9,the sum is (1+9)*2+5
#even numbers 0,2,4,6,8,the sum is (0+8)*2+4
#so (1-2+3-4+5-....+n) 's time complexity Θ(n) can be decrease to Θ(n)/2
        