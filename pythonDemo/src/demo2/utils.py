# coding=UTF-8
#!/usr/bin python

def p(str , num=0):
    print str
    for i in range(num):
        print i,str
        
def readfile(path):
    data = open(path)
    '''
    while True:
        line = data.readline()
        if len(line)==0:
            break
        print line
    '''
    for line in data:
        print line    
    
    data.close( )
    
        
