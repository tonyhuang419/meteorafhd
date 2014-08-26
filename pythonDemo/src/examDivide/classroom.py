# coding=UTF-8
#!/usr/bin python

class Classroom():
    def __init__(self, no, maxSpace ,remain ):
        self.no = no
        self.maxSpace = maxSpace
        self.remain = remain
        self.examStyle = -1
    
    def __str__(self):
        s = u'%s , 数量：%s, 剩余座位：%s ,开闭卷：%s' % ( self.no, self.maxSpace , self.remain , self.examStyle )
        return s
        
    
    
    