# -*- coding: utf-8 -*-
#!/usr/bin/python2.6

#https://code.google.com/codejam/contest/2334486/dashboard#s=p1
import math


FILE_PATH_IN = 'B-small-practice.in'
FILE_PATH_OUT = 'B-small-practice.out'

def readFile():
    file = open ( FILE_PATH_IN, 'r' ) 
    data = file.readlines()
    amount = int(data[0])
    content = data[1:]
    assert amount == len(content)
    return [s.strip() for s in content]


def calcHeigh():
    file_object = open(FILE_PATH_OUT, 'w')
    for i, content in enumerate(readFile()):
#        print '%s,%s' % (i, content)
        sex, mom, dad = content.split()
        child = -5 if sex == 'G' else 5
        
        
        def to_inches(s):
            feet, ins = s.split("'")
            inches = ins.split('"')[0]
            return int(feet) * 12 + int(inches)
        height = (to_inches(mom) + to_inches(dad) + child) / 2.0
        low = math.ceil(height - 4)
        high = math.floor(height + 4)
        
        def formatHeight(h):
            return '%s\'%s\"' %  ( int(h//12), int(h%12) )
        
        out_str =  'Case #%s: %s to %s\n' % ( i+1 , formatHeight(low) , formatHeight(high) )
        print out_str
        file_object.write(out_str)
        
    file_object.close()
       


if __name__ == '__main__':
    calcHeigh()
