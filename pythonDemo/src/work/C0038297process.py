# coding=UTF-8
#!/usr/bin python

import xlrd
import os
from xlutils.copy import copy

FILE_PATH='ManualPostingDataForPRD20121122.xls'
LINE_OFFSET=2514


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

def loadProcessInfoList():
    print 'start loadProcessInfoList'
    orgInfoList = []
    sheetCtx = loadSheet()
    nrows = sheetCtx.nrows
    for i in range(LINE_OFFSET-1,nrows):
        dateStr = (str)(sheetCtx.cell_value(i,0))
        subcon_id = (long)(sheetCtx.cell_value(i,1))
        vender_code = (str)(sheetCtx.cell_value(i,2))
        bill_no = (str)(sheetCtx.cell_value(i,3))
        total_amount = (float)(sheetCtx.cell_value(i,4))
    #        print '%s,%s,%s,%s,%s' % ( dateStr,subcon_id,vender_code,bill_no,total_amount)
        info = Info(dateStr,subcon_id,vender_code,bill_no,total_amount)
        orgInfoList.append(info)
    print 'count:%d' % len(orgInfoList)
    print 'end loadProcessInfoList'
    return orgInfoList
    
    

def process():
    orgInfoList=loadProcessInfoList()
    print 'size %d' % len(orgInfoList)
#    destFile = xlrd.open_workbook(FILE_PATH , formatting_info=True)
#    rstFile = copy(destFile)
#    processSheet = rstFile.get_sheet(0)
    
    for info in orgInfoList:
        print info.date
        print info.subcon_id
        print info.vender_code
        print info.bill_no
        print info.total_amount
    

if __name__ == '__main__':
    print 'start process'
#    loadOrgInfoList()
#    loadDestInfoList()
    process()
    print 'end process'
    
    



