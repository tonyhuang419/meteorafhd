#!/usr/bin python
# -*- coding: utf-8 -*-

'''
一只青蛙一次可以跳上 1 级台阶，也可以跳上2 级。
算法：计算1...n中能够有几个2，然后加起来就行了

一只青蛙一次可以跳上1级台阶，也可以跳上2 级……它也可以跳上n 级
建立在第一题的算法之上，稍微做点调整就行了
'''
def getStepFromOneTwo(n):
    sumStep=1 
    stepTwo=n/2
    rem=n%2
    for i in range(stepTwo+1):
        if i==0:continue
        if rem==0 and i*2==n:
            return sumStep+1
        sumStep+=n-i
    return sumStep
print getStepFromOneTwo(6)

def getStepFromAny(n):
    sumStep=0
    for i in range(n+1):
        if i==0:continue
        elif i==1:sumStep+=1;continue
        elif i==n:return sumStep+1
        print sumStep
        stepN=n/i
        rem=n%i
        for j in range(stepN+1):
            if j==0:continue
            if rem==0 and j*2==n:
                sumStep+=1
                break
            if j>0:
                sumStep+=n-j
    return sumStep+1
print getStepFromAny(4)           



