import threading
import time

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
# r.zadd('recent:',2, '{10:10,20:20}')
print (r.zrange('recent:',0,-1 , withscores=True))




def publisher(n):
    time.sleep(1)   
    for i in xrange(n):
        r.publish('channel', i)     
        time.sleep(1)
    
def run_pubsub():
    threading.Thread(target=publisher, args=(3,)).start()
    pubsub = r.pubsub()
    pubsub.subscribe(['channel'])
    count = 0
    for item in pubsub.listen():
        print item       
        count += 1
        if count == 4:
            pubsub.unsubscribe()
        if count == 5:           
            break

# run_pubsub()

# r.delete('sort-input')
print r.rpush('sort-input', 23, 15, 110, 7)


