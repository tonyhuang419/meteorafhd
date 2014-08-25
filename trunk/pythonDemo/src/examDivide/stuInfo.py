# coding=UTF-8
#!/usr/bin python

class Stu():
    def __init__(self, stuNo, examNo ,examSeq , lesson):
        self.stuNo = stuNo
        self.examNo = examNo
        self.examSeq = examSeq
        self.lesson = lesson
        self.examStyle = -1  #开卷 1 闭卷0
        self.examStyleOrg = -1
    
    def __str__(self):
#         print '%s , %s, %s , %s' % ( self.stuNo, self.examNo , self.examSeq , self.lesson )
        s = u'%s , %s, %s , %s , %s , %s ' % ( self.stuNo, self.examNo , self.examSeq ,
                                               self.lesson , self.examStyle ,self.examStyleOrg )
        return s
        
    
    
    