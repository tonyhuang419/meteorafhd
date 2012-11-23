# coding=UTF-8
#!/usr/bin python

import xlrd
import os
from xlutils.copy import copy

FILE_PATH='ManualPostingDataForPRD.xls'
ORG_STRAT_ROW=3
DEST_START_ROW=596


class Info:
    def __init__(self,date,subcon_id,vender_code,bill_no,total_amount):
        self.date=date
        self.subcon_id=subcon_id
        self.vender_code=vender_code
        self.bill_no=bill_no
        self.total_amount=total_amount
    
    def equal(self,info):
        return self.date == info.date \
            and self.subcon_id == info.subcon_id \
            and self.vender_code == info.vender_code \
            and self.bill_no == info.bill_no  \
            and self.total_amount == info.total_amount  

def loadSheet():
    file = xlrd.open_workbook(FILE_PATH)
    sheetCtx = file.sheet_by_index(0)
    return sheetCtx

def loadOrgInfoList():
    print 'start loadOrgInfoList'
    orgInfoList = []
    sheetCtx = loadSheet()
    nrows = sheetCtx.nrows
    for i in range(ORG_STRAT_ROW,nrows):
#        exclude done info
        if sheetCtx.cell_value(i,7)=='':
            dateStr = (str)(sheetCtx.cell_value(i,0))
            subcon_id = (long)(sheetCtx.cell_value(i,1))
            vender_code = (str)(sheetCtx.cell_value(i,2))
            bill_no = (str)(sheetCtx.cell_value(i,3))
            total_amount = (float)(sheetCtx.cell_value(i,4))
    #        print '%s,%s,%s,%s,%s' % ( dateStr,subcon_id,vender_code,bill_no,total_amount)
            info = Info(dateStr,subcon_id,vender_code,bill_no,total_amount)
            orgInfoList.append(info)
        else:
            info = Info('','','','','')
    print 'count:%d' % len(orgInfoList)
    print 'end loadOrgInfoList'
    return orgInfoList
    
    
def loadDestInfoList():
    print 'start loadDestInfoList'
    destInfoList = []
    sheetCtx = loadSheet()    
#    nrows = sheetCtx.nrows
    for i in range(DEST_START_ROW,1806):
        dateStr = (str)(sheetCtx.cell_value(i,20))
        subcon_id = (long)(sheetCtx.cell_value(i,21))
        vender_code = (str)(sheetCtx.cell_value(i,22))
        bill_no = (str)(sheetCtx.cell_value(i,23))
        total_amount = (float)(sheetCtx.cell_value(i,24))
        if len(dateStr)>0:
#            print '%s,%s,%s,%s,%s' % ( dateStr,subcon_id,vender_code,bill_no,total_amount)
            info = Info(dateStr,subcon_id,vender_code,bill_no,total_amount)
            destInfoList.append(info)
    print 'count:%d' % len(destInfoList)
    print 'end loadOrgInfoList'
    return destInfoList


def compareList():
    count=0
    orgInfoList=loadOrgInfoList()
    destInfoList=loadDestInfoList()
    destFile = xlrd.open_workbook(FILE_PATH , formatting_info=True)
    rstFile = copy(destFile)
    compareSheet = rstFile.get_sheet(0)
    
    orgRowNum=-1+ORG_STRAT_ROW
    for i in orgInfoList:
        orgRowNum+=1
        mark = 'xxxxx'+str(orgRowNum+1)
        if i.date <> '':
            destRowNum=-1+DEST_START_ROW;
            for j in destInfoList:
                destRowNum+=1
                if i.equal(j):
                    count+=1
                    compareSheet.write(orgRowNum, 8 , i.date )
                    compareSheet.write(orgRowNum, 9 , i.subcon_id )
                    compareSheet.write(orgRowNum, 10 , i.vender_code )
                    compareSheet.write(orgRowNum, 11, i.bill_no )
                    compareSheet.write(orgRowNum, 12, i.total_amount )
                    compareSheet.write(orgRowNum, 13, mark )
                    compareSheet.write(destRowNum,19, mark )
    print 'same count:%d' % count
    rstFile.save('new.xls')
    

if __name__ == '__main__':
    print 'start compare'
#    loadOrgInfoList()
#    loadDestInfoList()
    compareList()
    print 'end compare'
    
    




