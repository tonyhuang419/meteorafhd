# coding=UTF-8
#!/usr/bin python

import logging
from base64 import b64encode

class user:
    def __init__(self,host,username,password):
        self.host=host
        self.username=username
        self.password=password
    
def p(str , num=0):
    print str
    for i in range(num):
        print i,str
        
def readfile(path):
    data = open(path)
    '''
    while True:
        line = data.readline()
        if len(line)==0:
            break
        print line
    '''
    for line in data:
        print line    
    
    data.close( )
    
def initCommonLog(path):
    logger = logging.getLogger('c')
    hdlr = logging.FileHandler(path)
    console = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
    hdlr.setFormatter(formatter)
    console.setFormatter(formatter)
    logger.addHandler(hdlr)
    logger.addHandler(console)
    logger.setLevel(logging.DEBUG)
    return logger

def initFailLog(path):
    logger = logging.getLogger('f')
    hdlr = logging.FileHandler(path)
    formatter = logging.Formatter('%(message)s')
    hdlr.setFormatter(formatter)
    logger.addHandler(hdlr)
    logger.setLevel(logging.DEBUG)
    return logger    

def readProperties(path):
    pf = open(path)  
    re={}  
    for line in pf:   #遍历文件的每行
        tmp = line.split("=",1)   #以“=”分割每行
        re[tmp[0]] = tmp[1].strip()   #插入字典
    pf.close()
    return re

def getUser(path):
    userlist=[]
    um=readProperties(path)
    u=user(um['host'],um['username'],um['password'])
    userlist.append(u)
    u=user(um['host2'],um['username2'],um['password2'])
    userlist.append(u)
    return userlist

def getMailList(path):
    mailto_list=[]
    try:
        f = open(path, 'r')
        for line in f:
            line = line.strip()
            if len(line)>0:
                mailto_list.append(line)
        f.close() 
    except Exception as ex: 
        c_logger.info ('\nSome error/exception occurred.')
        c_logger.info ( ex );
        # here, we are not exiting the program
    finally:
        c_logger.info ('email list has be read')
    return mailto_list    
        
def b64_utf8(data):
    """encode into base64."""
    return '=?utf-8?B?%s?=' % b64encode(data)

c_logger = initCommonLog('log.txt')
f_logger = initFailLog('log.fail.txt')

if __name__ == '__main__':
    print getUser('mailuser.properties')