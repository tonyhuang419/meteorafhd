# -*- coding: utf-8 -*-

import pymongo
import random

# http://blog.csdn.net/daillo/article/details/7030910
conn = pymongo.Connection() # 连接本机数据库
# conn = pymongo.Connection(host="192.168.1.202") # 连接指定IP的数据库
db = conn.local # 进入指定名称的数据库
users = db.users  # 获取数据库里的 users 集合
users = db['users'] # 获取数据库里的 users 集合,也可以用字典来获取

# for i in range(10000):
#     u = dict(name = "user"+str(i), age = int(random.uniform(0, 100)) ) 
#     db.users.save(u) # 用 save 也可以插入
    

# for u in db.users.find(): 
#     print u
#     db.users.remove( u["_id"] ) 
print users.count()   

