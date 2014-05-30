# -*- coding: utf-8 -*-

import pymongo

conn = pymongo.Connection() # 连接本机数据库
db = conn.local # 进入指定名称的数据库
guests = db.guests  # 获取数据库里的 users 集合


# print guests.count()  
# i=0
# for u in db.guests.find(): 
#     # print '%s,%s,%s,%s,%s' % ( u['name'] , u['gender'] , u['ctfId'], u['birthday'], u['address'] )
#     i=i+1
#     if i > 100000:
#         break
#     db.guests.remove( u["_id"] )
     

# print u2['address'].encode('utf-8')

print guests.count()   

