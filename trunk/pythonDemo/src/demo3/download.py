#!/usr/bin/python

import urllib2 
import re

SAVE_PATH = 'C:\\'
URL_1 = 'http://www.17jo.com/img/logo122.jpg'
URL_2 = 'http://www.17jo.com/img/logo130.jpg'
p = re.compile('\d+')
 
def getFileName(path):
    index = path.rfind('/')
    return path[index:len(path)]

def getFileNameNum(fileName):
    numArr =  p.findall(fileName)
    return numArr[len(numArr)-1]
    
def getStartUrl(path):
    fileName = getFileName(path)
    fileNum = getFileNameNum(fileName)
    return path[0:path.rfind(fileNum)]

def getEndName(path):
    fileName = getFileName(path)
    fileNum = getFileNameNum(fileName)
    return path[path.rfind(fileNum)+len(fileNum) : len(path)]

    
def download(path , fileName):
    print 'Download: %s'%(path)
    try:
#        proxy_support = urllib2.ProxyHandler({'http':'http://127.0.0.1:8087'})  
#        opener = urllib2.build_opener(proxy_support, urllib2.HTTPHandler)  
#        urllib2.install_opener(opener)  
        f = urllib2.urlopen(path) 
        with open(SAVE_PATH+fileName, "wb") as code:
            code.write(f.read())
    except Exception as e:
        print e



if __name__ == "__main__":
    baseUrl =  getStartUrl(URL_1)
    endUrl = getEndName(URL_1)
    num_1 = int(getFileNameNum(URL_1))
    num_2 = int(getFileNameNum(URL_2))
    step = num_2 - num_1
    for i in range(step+1):
        downloadPath = baseUrl + str(num_1+i) + endUrl
        download (  downloadPath , str(num_1+i) + endUrl )
    
    