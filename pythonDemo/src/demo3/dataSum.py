#!/usr/bin/python

from work.dateutils  import *
import datetime

d = datetime.date(2014,3,8)
sumDay = d.year + d.month + d.day

nextYear = 2015;
d2 = datetime.date(nextYear,1,1)
while d2.year < 2018:
    if d2.year + d2.month + d2.day == sumDay :
        print  d2
d2 = addDay(d2,1)
