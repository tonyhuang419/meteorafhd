# -*- coding: utf-8 -*-

import codecs
import pymongo
import guestInfo

fileCode = 'UTF-8'
files = ['D:\\down\\2000W\\1-200W.csv']

def readFiles( db ):
    for filePath in files:
        filePath = unicode( filePath, "UTF-8" ) 
        msgs  =  codecs.open( filePath, 'r', fileCode )
        for line in msgs.readlines(): 
            info = line.split(',')
            if len(info) > 10:
#                 print  info[0].decode("utf-8")
#                 print  info[7].decode("utf-8")
                g = guestInfo.genGuestInfo( info[0],info[5]
                                           ,info[4],info[6]
                                           ,info[7] )
                
#                 if not db.guests.find_one({"ctfId":info[4]}):
                db.guests.save(g)


if __name__ == '__main__':
    conn = pymongo.Connection() 
    db = conn.local 
    readFiles( db )
    print db.guests.count()   
        
