# -*- coding: utf-8 -*-
#!/usr/bin/python2.6

#http://code.google.com/codejam/contest/1460488/dashboard#s=p1
#http://united-coders.com/christian-harms/solutions-for-the-google-code-jam-2012-qualify-round/
def maxNotSurprising(n):
    b = n / 3
    #1: 0+0+1
    #2: 0+1+1
    if n%3>0:
        return b+1
    #0+0+0
    return b

def maxSurprising(n):
    b = n / 3
    #2: 0+0+2
    if n%3==2:
        return b+2
    #1: 0+1+0
    if n%3==1:
        return b+1
    #3: 0+1+2
    if n%3==0 and n>0:
        return b+1
    #0: 0+0+0
    return b


def solve(s, p, ti):
    c = 0
    for t in ti:
        maxNot = maxNotSurprising(t)
        maxSur = maxSurprising(t)
        if s>0 and maxSur>maxNot and maxSur>=p and maxNot<p:
            c+=1
            s-=1
        else:
            if maxNot>=p:
                c+=1
    return c

    
def dance():
    for case in range(input()):
        raw_str = raw_input()
        values = [int(x) for x in raw_str.split()]
        print "Case #%d: %d" % (case+1, solve(values[1], values[2], values[3:]))
            

if __name__ == '__main__':
    dance()