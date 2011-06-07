# coding:UTF-8


import os,sys,threading

# print( os.listdir("C:\\") )

print( os.stat("c:\\config.sys").st_size )

# print ( os.walk("f:\\pic\\2011T") )

'''
for root,dirs,files in os.walk("f:\\pic\\2011T"):
    print( root,dirs ,files )
'''

# print(sys.argv[0])  # get args when program execute

# print( unicode( '哎。。。' ,'utf8').encode('utf8') )


class ThreadPrint(threading.Thread):
    def __init__(self):
        threading.Thread.__init__(self)
    def run(self):
        print 'thread:'+ self.getName()
        
for i in range(2):
    t = ThreadPrint();
    t.start(); 
    
    
    