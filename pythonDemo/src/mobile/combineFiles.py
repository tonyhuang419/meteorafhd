# -*- coding: utf-8 -*-
import codecs

files = ['s20101106.csv',
         's20101223.csv',
         's20110501.csv']

title = '﻿"信息类型"";""联系人"";""发往"";""来自"";""日期"";""时间"";""文件夹"";""信息"""\n'
title2 = '"信息类型";"联系人";"发往";"来自";"日期";"时间";"文件夹";"信息"\n'
fileCode = 'UTF-16'

def readFiles():
    allMsgs = []
    for filePath in files:
        count=0
        filePath = unicode( filePath, "utf-8" ) 
#        msgs = open ( filePath, 'rb' ) 
        msgs  =  codecs.open( filePath, 'r', fileCode )
        for line in msgs.readlines(): 
            if len(line.strip())>0  \
            and line.find('信息类型') ==-1 and line.find('发往') ==-1 :
                count+=1
                allMsgs.append(line)
        print 'file %s count:%s' % (filePath,count)
    print 'count:%s' % len(allMsgs)
    return allMsgs

def combineMsgs(allMsgs):
    newAllMsgs = []
    for msg in allMsgs:
        if msg in newAllMsgs:
            print 'delete msg:%s' % msg
        else:
            newAllMsgs.append(msg)
    print 'combine finish, count:%s' % len(newAllMsgs)
    return newAllMsgs

def writeToNewFile(newAllMsgs):
#    f=open('new.csv','w')
    f = codecs.open("new.csv", "w", "utf-8") 
    f.write(codecs.BOM_UTF8) 
    f.write(title2)
    for msg in newAllMsgs: 
        f.write(msg)
    f.close()
    print 'write finish'

if __name__ == '__main__':
    allMsgs = readFiles()
    newAllMsgs = combineMsgs(allMsgs)
    writeToNewFile(newAllMsgs)
    
    