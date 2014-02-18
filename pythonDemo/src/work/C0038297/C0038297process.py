# coding=UTF-8
#!/usr/bin python

from xlutils.copy import copy
import C0038297_sql_prepare
import work.dateutils as dateutils
import os
import xlrd

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
    print 'rows count:%d' % nrows
    for i in range(LINE_OFFSET-1,nrows):
        # exclude done info
        if sheetCtx.cell_value(i,8)=='':
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
            orgInfoList.append(info)
    print 'count:%d' % len(orgInfoList)
    print 'end loadProcessInfoList'
    return orgInfoList
    

def processInfo(processSheet, cur, info, count, i):
    if (float)(i.amount) == (float)(info.total_amount):
        processSheet.write(cur, 8, 'These billings were not manual posting data, they are web posting data.')
        print count+1
        return True
    return False

def process():
    orgInfoList=loadProcessInfoList()
    destFile = xlrd.open_workbook(FILE_PATH , formatting_info=True)
    rstFile = copy(destFile)
    processSheet = rstFile.get_sheet(0)
    cur=LINE_OFFSET-2
    count=0
    for info in orgInfoList:
        cur+=1
        if info.date!='' :
            #search db by i
    #       2012/4->2013/1
            year = info.date
            fisDate=dateutils.dateStrToFisDate(info.date)
            year = fisDate.year
            month = fisDate.month
            if cmp( info.bill_no , '(null)') <> 0:
                result = C0038297_sql_prepare.sapBuPostingByBillNo(year, month, info.bill_no)
                for i in result:
                    if processInfo(processSheet, cur, info, count, i):
                        count+=1
                        continue
                
                result = C0038297_sql_prepare.sapTimsheetPostingByBillNo(year, month, info.bill_no)
                for i in result:
                    if processInfo(processSheet, cur, info, count, i):
                        count+=1
                        continue
                    
            result = C0038297_sql_prepare.sapBuPostingByVendorCode(year, month, info.vender_code[3:])
            for i in result:
                if processInfo(processSheet, cur, info, count, i):
                    count+=1
                    continue
                
            result = C0038297_sql_prepare.sapTimsheetPostingByVendorCode(year, month, info.vender_code[3:])
            for i in result:
                if processInfo(processSheet, cur, info, count, i):
                    count+=1
                    continue
    print 'finish count:%d' % count
    rstFile.save('new.xls')


if __name__ == '__main__':
    print 'start process'
#    loadOrgInfoList()
#    loadDestInfoList()
    process()
    print 'end process'
    
    




