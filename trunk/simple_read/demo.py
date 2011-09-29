import time
import thread

def prinfinfo():
    print 'demo'

def recTimer():
    while True:
        thread.start_new_thread(prinfinfo,()) 
        time.sleep(2)
    
    
if __name__ == "__main__":
    recTimer()