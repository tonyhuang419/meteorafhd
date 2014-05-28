# -*- coding: utf-8 -*-

import codecs
import pymongo
import guestInfo

fileCode = 'UTF-8'
files = ['D:\\down\\2000W\\200W-400W.csv']

def readFiles( db ):
    cur=0
    for filePath in files:
        filePath = unicode( filePath, "UTF-8" ) 
        msgs  =  codecs.open( filePath, 'r', fileCode )
        for line in msgs.readlines(): 
            info = line.split(',')
            if len(info) > 10 and info[4]>5 and info[5]=='F' \
                and info[6]>'19800000' :
#                 print  info[0].decode("utf-8")
#                 print  info[7].decode("utf-8")
                
                g = guestInfo.genGuestInfo( info[0],info[5]
                                           ,info[4],info[6]
                                           ,info[7] )
                db.guests.save(g)
                cur = cur+1
                if cur > 100000:
                    break
                


if __name__ == '__main__':
    conn = pymongo.Connection() 
    db = conn.local 
    readFiles( db )
    print db.guests.count()   
        
