#!/usr/bin python
# -*- coding: utf-8 -*-

'''
2维数组数字查找，行列递增排序
算法，因为是排序的，所以不用穷举查找，找不到就跳过
'''
numArr=[[1,2,8,9],
        [2,4,9,12],
        [4,7,10,13],
        [6,8,11,15]]


def findNum(num):
    for x in numArr:
        for y in x:
            print y
            if num==y:
                return True
            elif num<y:
                break;
    return False
            
print findNum(5)
print findNum(7) 
