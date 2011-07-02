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
    
print('----------') 
class str2(str):
    def __str__(self):
        return "foo"

x = str2("bar")
print str(x)

class unicode2(unicode):
    def __unicode__(self):
        return u"foo"
x = unicode2(u"bar")
print unicode(x)
    