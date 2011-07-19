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
    

print(getMD5Time())
print(datetime.datetime.now())
