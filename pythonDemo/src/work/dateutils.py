# coding=UTF-8
#!/usr/bin python

import datetime

def dateStrToFisDate(dateStr):
#    print dateStr[6:11]
#    print dateStr[0:2]
#    print dateStr[3:5]
    d =  datetime.datetime( (int)(dateStr[6:11]) , (int)(dateStr[0:2]), (int)(dateStr[3:5]))
    return addMonths(d,8)

def addMonths(d,x):
    newmonth = ((( d.month - 1) + x ) % 12 ) + 1
    newyear  = d.year + ((( d.month - 1) + x ) // 12 ) 
    return datetime.date( newyear, newmonth, d.day)

def addDay(d,x):
    return d + datetime.timedelta(days=x)

if __name__ == '__main__':
    print dateStrToFisDate('10/03/2012')
    x = '1000'
    y=1000.00
    print (float)(x) ==(float)(y)
    
    print addDay( datetime.date(2014,2,28) , 1)

