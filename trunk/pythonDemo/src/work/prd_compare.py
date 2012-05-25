# coding=UTF-8
#!/usr/bin python

import xlrd
import xlwt
from xlutils.copy import copy

PRD_LIST_PATH='D:\\dev\\workspace\\Document\\Filelist of NASAII-PRD.xls'
DEST_FILE_PATH='D:\\work\\Case C0038214 Change Information_Approved.xls'
SHEET_NAME='Web PRD Files list'

def getWebPrdMap():
    prdMap={}
    prdListXls = xlrd.open_workbook(PRD_LIST_PATH , formatting_info=True)
    sheetNum = prdListXls.nsheets
    for n in range(0,sheetNum):
        sheetName = prdListXls.sheet_by_index(n).name
        if  cmp(sheetName,SHEET_NAME) == 0:
            sheetCtx = prdListXls.sheet_by_index(n)
            nrows = sheetCtx.nrows
            for i in range(1,nrows):
                filePath = (str)(sheetCtx.cell_value(i,0))
                if filePath.strip()=='':
                    continue
                fileVersion = (str)(sheetCtx.cell_value(i,2))
                prdMap[filePath.strip()]=fileVersion.strip()
            return prdMap
            
def compareFile():
    destFile = xlrd.open_workbook(DEST_FILE_PATH , formatting_info=True)
    rstFile = copy(destFile)
    compareSheet = rstFile.get_sheet(0)
    
    destSheetCtx = destFile.sheet_by_index(0)    
    nrows = destSheetCtx.nrows
    for i in range(4,nrows):
        filePath = (str)(destSheetCtx.cell_value(i,4))
        if filePath.strip()=='':
            continue
        #tmpList = filePath.split('/')
        #tmpListLen = len(tmpList)
        #filePath = tmpList[tmpListLen-2]+'/'+tmpList[tmpListLen-1]
        prdVersion = (str)(destSheetCtx.cell_value(i,6))
        remotePreVer = getPrdVer(filePath)
        print "%s,\nRemote PRD Version:%s , Local PRD Version:%s" %( filePath, remotePreVer , prdVersion )
        if remotePreVer!=prdVersion:
            compareSheet.write(i, 5, remotePreVer )
    rstFile.save('new.xls')

prdMap = getWebPrdMap()
def getPrdVer(filePath):
    tmpList = filePath.split('/')
    tmpListLen = len(tmpList)
    fileName = tmpList[tmpListLen-2]+'/'+tmpList[tmpListLen-1]
    for k in prdMap.keys():
        if  fileName in k :
            return prdMap[k]

if __name__ == '__main__':
    print 'start compare'
    compareFile()
    print 'end compare'
    
    




