# -*- coding: utf8 -*-
import sys  
reload(sys)  
sys.setdefaultencoding('utf-8')  

#http://blog.ez2learn.com/2010/07/17/talk-about-coroutine-and-gevent/

def foo():
    for i in range(10):
        # 丟資料並且把主控權交給呼叫者
        yield i
        print u'foo: 主控又回到我手上了，打我阿笨蛋'

bar = foo()
# 執行coroutine
print bar.next()
print u'main: 現在主控權在我們手上，做點雜事'
print 'main:hello baby!'
# 回到剛才foo這個coroutine中斷的地方繼續執行
print bar.next()
print bar.next()


a = range(0,100)
print type(a)
print a
print a[0], a[1] 
a = xrange(0,100)
print type(a)
print a
print a[0], a[1] 