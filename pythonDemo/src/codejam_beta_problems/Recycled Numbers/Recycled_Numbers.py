# -*- coding: utf-8 -*-
#!/usr/bin/python2.6

#https://code.google.com/codejam/contest/1460488/dashboard#s=p2
#http://www.cnblogs.com/practice/

FILE_PATH_IN = 'C-small-practice.in'
FILE_PATH_OUT = 'C-small-practice.out'

def shiftDigit(num_str):
    num_str_len = len(num_str)
    i_str = num_str[num_str_len-1] + num_str[0:num_str_len-1]
    return i_str

def readFile():
    file = open ( FILE_PATH_IN, 'r' ) 
    data = file.readlines()
    amount = int(data[0])
    content = data[1:]
    return [s.strip() for s in content]


def recycle():
    file_object = open(FILE_PATH_OUT, 'w')
    for i, content in enumerate(readFile()):
        ret=0
        min, max = content.split()
        min = int(min)
        max = int(max)
        for j in range( int(min) ,int(max) ):
            tmpNum = j
            while True:
                tmpNum = shiftDigit(str(tmpNum))
                tmpNumInt = int(tmpNum)
                if j == tmpNumInt:
                    break;
                elif tmpNumInt > j and tmpNumInt>=min and tmpNumInt<=max:
                    ret+=1
        print ret
        out_str =  'Case #%d: %s\n' % ( i+1 , ret )
        file_object.write(out_str)
        
    file_object.close()
       


if __name__ == '__main__':
    recycle()

     
