# coding=UTF-8
#!/usr/bin python

import xlrd
import xlwt
import re 
from xlutils.copy import copy  

def procRow( sh , nrow , shbak ):
    col=0
    cell_value = shbak.cell_value(nrow,0)
    listNum =  re.findall(r'\d{11}',cell_value)
    for num in listNum:
        #print type(i)
        col+=1
        ctype = 1 # 类型 0 empty,1 string, 2 number, 3 date, 4 boolean, 5 error
        xf = 0 # 扩展的格式化 (默认是0)
        #sh.put_cell(nrow, col, ctype, num, xf)
        sh.write(nrow, col, num)
        #print i,cell_value    
    
    
if __name__ == '__main__':
    fname = "info.xls"
    bk = xlrd.open_workbook(fname , formatting_info=True)
    wb = copy(bk)
    sheetNum = bk.nsheets
    for n in range(0,sheetNum):
        shbak = bk.sheet_by_index(n)
        sh = wb.get_sheet(n)
        nrows = shbak.nrows
        #ncols = sh.cols
        for i in range(0,nrows):
            procRow(sh,i,shbak)    
    
    
    #for n in range(0,sheetNum):
    #    ws = wb.get_sheet(n)
    #    ws.write(0, 0, 'changed!')
    wb.save('info_p.xls')
    
    




