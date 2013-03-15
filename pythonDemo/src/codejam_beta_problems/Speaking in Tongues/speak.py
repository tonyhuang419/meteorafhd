# -*- coding: utf-8 -*-
#!/usr/bin/python2.6

#https://code.google.com/codejam/contest/1460488/dashboard#s=p0
exp = 'zq ejp mysljylc kd kxveddknmc re jsicpdrysi rbcpc ypc rtcsra dkh wyfrepkym veddknkmkrkcd de kr kd eoya kw aej tysr re ujdr lkgc jv'
exp1x = 'qz our language is impossible to understand there are twenty six factorial possibilities so it is okay if you want to just give up'
dictX = dict(zip( list(exp),list(exp1x) ))

def speak():
    for case in range(input()):
        raw_str = raw_input()
        result = []
        for i in raw_str:
            result.append(dictX.get(i))
        print ('Case #%d: %s') % (case + 1 , ''.join(result))
            

if __name__ == '__main__':
    speak()