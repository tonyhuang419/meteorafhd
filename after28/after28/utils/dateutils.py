# coding=UTF-8
#!/usr/bin python

'''
Created on 2011-7-15

@author: FHDone
'''

import datetime
import hashlib
import time


def getMD5Time():
    now = time.time()
    return hashlib.md5(str(now)).hexdigest()

# 计算当前时间距离0点时差+300秒
def getNextDayReaminSeconds():
    tm = datetime.date.today() + datetime.timedelta( days = 1 )
    arr = tm.strftime( '%Y,%m,%d'   ).split(',')
    tmT = datetime.datetime( int(arr[0]), int(arr[1]), int(arr[2]))
    return (tmT - datetime.datetime.now()).seconds + 300 
    


#print(getMD5Time())
#print(datetime.datetime.now())

#today = datetime.date.today()
#date = today.strftime('%Y')+'-'+today.strftime('%m')+'-'+today.strftime('%d')
#print(date)
