# coding=UTF-8
#!/usr/bin python

def proc():
    try:
        f = open('t.txt', 'r')
        n = file('n.txt','w')
        i=0
        tmp=''
        for line in f:
            tmp = tmp+line[:-1]+';'
            i=i+1
            if i==15:
                i=0
                n.write(tmp+'\r\n')
                tmp=''
        f.close() 
        n.close()
    except Exception as ex: 
        print ('\nSome error/exception occurred.')
        print ( ex );
        # here, we are not exiting the program
    finally:
         print ('finally')
     
    
if __name__ == '__main__':
   proc()
    
    




