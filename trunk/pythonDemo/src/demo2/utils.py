# coding=UTF-8
#!/usr/bin python

import logging

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



if __name__ == '__main__':
    pass