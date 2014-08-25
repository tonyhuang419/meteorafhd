# coding=UTF-8
#!/usr/bin python

import xlrd
import os
import utils
import stuInfo
import classroom

STU_INFO_FILE_PATH   = unicode('files//097圆梦深圳考生名单.xls','utf8')
CLASS_ROOM_FILE_PATH = unicode('files//桃源中学本学期学生人数(增加座椅数量).xls','utf8')
EXAM_STYLE_INFO_PATH = unicode('files//097圆梦深圳考场安排 (开卷闭卷).xls','utf8')

def loadSheet(filePath):
    file = xlrd.open_workbook(filePath)
    sheetCtx = file.sheet_by_index(0)
    return sheetCtx

def loadStuInfo():
    stuList=[]
    sheetCtx = loadSheet(STU_INFO_FILE_PATH)
    nrows = sheetCtx.nrows
    for i in range(1,nrows):
        stuNo  = (str)(sheetCtx.cell_value(i, utils.colNameToNum('A') ))
        examNo = sheetCtx.cell_value(i, utils.colNameToNum('C') )
        examSeq = sheetCtx.cell_value(i, utils.colNameToNum('E') )
        lesson= sheetCtx.cell_value(i, utils.colNameToNum('F') )
        if '0' not in examSeq:
#             print '%s,%s,%s,%s' % ( stuNo , examNo , examSeq , lesson )
            stu = stuInfo.Stu(  stuNo, examNo ,examSeq , lesson )
            stuList.append(stu)
    return stuList
             
def loadClassRoomInfo():
    classroomList = []
    sheetCtx = loadSheet(CLASS_ROOM_FILE_PATH)
    nrows = sheetCtx.nrows
    for i in range(4,nrows):
        roomNo = (sheetCtx.cell_value(i,utils.colNameToNum('B') ))
        if not (sheetCtx.cell_value(i, utils.colNameToNum('H') )) :
            continue
        roomMax = (long)(sheetCtx.cell_value(i, utils.colNameToNum('H') ))
        room = classroom.Classroom( roomNo  ,roomMax  ,roomMax )
        classroomList.append(room)      
    return classroomList
#         print  '%s,%s' % ( roomNo ,roomMax )

def loadExamStyleInfo():
    examStyleMap = {}
    sheetCtx = loadSheet(EXAM_STYLE_INFO_PATH)
    nrows = sheetCtx.nrows
    for i in range(1,nrows):
        lesson = sheetCtx.cell_value(i, utils.colNameToNum('D') )
        if not sheetCtx.cell_value(i, utils.colNameToNum('M') ):
            continue
        examStyle = sheetCtx.cell_value(i, utils.colNameToNum('M') )
        if not examStyleMap.has_key(lesson):
            examStyleMap[lesson] = examStyle
    return examStyleMap
#         print  '%s,%s' % ( lesson  ,examStyle )
        
        
if __name__ == '__main__':  
    stuList = loadStuInfo()
#     for s in stuList:
#         print u'%s' % s

    examStyleMap = loadExamStyleInfo()  
#     for k,v in examStyleMap.iteritems(): 
#         print "%s,%s" % ( k,v ) 
    
    if not utils.checkStuInfo(stuList, examStyleMap):
        exit()
    
    classroomList = loadClassRoomInfo()  
#     for room in classroomList: 
#         print "%s" % room
        
    mapByExamSeq = utils.mapStuInfoByAttr(stuList, 'examSeq')
    
    # 循环场次
    for examSeq in sorted(mapByExamSeq.keys()):
        stuInfoListBySeq =  mapByExamSeq[examSeq]
        print u"场次： %s" %  examSeq
#         for stu in stuInfoListBySeq :
#             print u'%s'  % stu
        
        mapByExamNo =  utils.mapStuInfoByAttr(stuInfoListBySeq, 'examNo')
        mapByExamNoList = sorted(mapByExamNo.items(), lambda x, y: cmp( len(x[1]), len(y[1])), reverse=True)
#         for examInfo in mapByExamNoList:
#             print  "%s , %s " %  (  examInfo[0] , len(examInfo[1]))

        result = { }
        import copy
        classroomListCopy = copy.deepcopy(classroomList)
        # 循环考场号
        for examInfo in mapByExamNoList:
            for room  in classroomListCopy: 
                remain = room.remain
                if  len(examInfo[1]) == remain :
                    print u"考场号： %s, 考生数量 ： %s , 开闭卷： %s , 班级：%s , 班级实际数量： %s " %  ( examInfo[0], len(examInfo[1]) ,
                                                                       examInfo[1][0].examStyle ,  room.no  , room.maxSpace ) 
                    room.remain = room.remain -  len(examInfo[1])
                    room.examStyle = examInfo[1][0].examStyle
                    result [ examInfo[0] ] =  room.no
                    break
                
        for examInfo in mapByExamNoList:
            for room  in classroomListCopy: 
                if  examInfo[0] not in result.keys() and room.remain >= len(examInfo[1])  \
                    and (  room.examStyle ==-1 or room.examStyle ==  examInfo[1][0].examStyle ) :
                    print u"考场号： %s, 考生数量 ： %s , 开闭卷： %s , 班级：%s , 班级实际数量： %s " %  ( examInfo[0], len(examInfo[1]) ,
                                                                     examInfo[1][0].examStyle ,  room.no  , room.maxSpace ) 
                    room.remain = room.remain -  len(examInfo[1])
                    room.examStyle = examInfo[1][0].examStyle
                    result [ examInfo[0] ] =  room.no
                    break
#                     print u"考场号： %s, 考生数量 ： %s , 开闭卷： %s  " %  ( examInfo[0], len(examInfo[1]) , examInfo[1][0].examStyle  )  
       
        for room  in classroomListCopy: 
            print u"%s'" % room      
        
        for k,v in result.iteritems(): 
            print "%s,%s" % ( k,v )
        
        

        