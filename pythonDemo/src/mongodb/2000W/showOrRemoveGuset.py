# -*- coding: utf-8 -*-

import pymongo

conn = pymongo.Connection() # 连接本机数据库
db = conn.local # 进入指定名称的数据库
guests = db.guests  # 获取数据库里的 users 集合

# for u in db.guests.find(): 
#     print '%s,%s,%s,%s,%s' % ( u['name'] , u['gender'] , u['ctfId'], u['birthday'], u['address'] )
#     db.guests.remove( u["_id"] )
     
# u2 = db.guests.find_one({"ctfId":"310106198410252896"})
# print u2 
     
print guests.count()   

