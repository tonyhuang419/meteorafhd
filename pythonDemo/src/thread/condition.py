# coding=UTF-8
#!/usr/bin/env python

import threading
import time

class Producer(threading.Thread):
    def run(self):
        lock.acquire()
        global count
        while True:
            if con.acquire(count):
                if count > 1000:
                    con.wait()
                else:
                    count = count+100
                    msg = self.name+' produce 100, count=' + str(count)
                    print msg
                    con.notify()
                con.release()
                time.sleep(1)

class Consumer(threading.Thread):
    def run(self):
        lock2.acquire()
        global count
        while True:
            if con.acquire():
                if count < 100:
                    con.wait()
                else:
                    count = count-3
                    msg = self.name+' consume 3, count='+str(count)
                    print msg
                    con.notify()
                con.release()
                time.sleep(1)

count = 500
con = threading.Condition()
lock = threading.Lock()
lock2 = threading.Lock()

def test():
    for i in range(2):
        p = Producer()
        p.start()
    for i in range(5):
        c = Consumer()
        c.start()
        
if __name__ == '__main__':
    test()