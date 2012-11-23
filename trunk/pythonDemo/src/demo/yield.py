# coding=UTF-8


#http://www.pythonclub.org/python-basic/yield
#生成全排列
def perm(items, n=None):
    if n is None:
        n = len(items)
    for i in range(len(items)):
        v = items[i:i+1]
        if n == 1:
            yield v
        else:
            rest = items[:i] + items[i+1:]
            for p in perm(rest, n-1):
                yield v + p
 
#生成组合
def comb(items, n=None):
    if n is None:
        n = len(items)    
    for i in range(len(items)):
        v = items[i:i+1]
        if n == 1:
            yield v
        else:
            rest = items[i+1:]
            for c in comb(rest, n-1):
                yield v + c
 
a = perm('abc')
for b in a:
    print b
    break
print '-'*20
for b in a:
    print b 
    
#http://www.jb51.net/article/15717.htm
def h():
    print 'Wen Chuan',
    m = yield 5 # Fighting!
    print m
    d = yield 12
    print 'We are together!'

c = h()
for i in c:
    print i         
    