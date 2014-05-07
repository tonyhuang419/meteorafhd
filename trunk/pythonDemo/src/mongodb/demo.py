# -*- coding: utf-8 -*-

import pymongo

# http://blog.csdn.net/daillo/article/details/7030910
conn = pymongo.Connection() # 连接本机数据库
# conn = pymongo.Connection(host="192.168.1.202") # 连接指定IP的数据库
db = conn.local # 进入指定名称的数据库
users = db.users  # 获取数据库里的 users 集合
users = db['users'] # 获取数据库里的 users 集合,也可以用字典来获取

u = dict(name = "user1", age = 23)
db.users.insert(u) # 将数据插入到 users 集合

u = dict(name = "user2", age = 24)
db.users.save(u) # 用 save 也可以插入

for u in db.users.find(): # 查不到时返回 None
    u['age'] += 3
    db.users.save(u)
    
for u in db.users.find(): # 查不到时返回 None
    print u
    print 
#     db.users.remove( u["_id"] ) 