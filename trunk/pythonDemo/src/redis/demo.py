import redis

r = redis.StrictRedis(host='localhost', port=6379, db=0)
print ( r.set('foo', 'bar'))
print ( r.get('foo'))


r.hmset('article', { \
    'title': 'title', \
    'link': 'link', \
    'poster': 'user', \
    'time': 'now', \
    'votes': '1', \
    })
print r.hget ('article','title') 

# r.lpushx('list_1', '1')
# r.lpushx('list_1', '2')
# print ( r.lrange('list_1', 0, -1  ) )

# r.hset('article', 'object_1',  r.lrange('list_1', 0, -1)  )
print( r.hgetall('article') )

# r.zrem('recent:','2', '3','4')
r.zadd('recent:',2, '{10:10,20:20}')
print (r.zrange('recent:',0,-1 , withscores=True))


