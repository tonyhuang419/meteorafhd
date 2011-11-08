# coding=UTF-8
#!/usr/bin python

import xlrd
import xlwt
import re 
from xlutils.copy import copy  

def procRow( sh , nrow ):
    col=0
    cell_value = sh.cell_value(nrow,0)
    listNum =  re.findall(r'\d{11}',cell_value)
    #print type(listnum)
    #print cell_value
    #row_data = sh.row_values(i)
    #row_list.append(row_data)
    
    for num in listNum:
        #print type(i)
        col+=1
        ctype = 1 # 类型 0 empty,1 string, 2 number, 3 date, 4 boolean, 5 error
        xf = 0 # 扩展的格式化 (默认是0)
        sh.put_cell(nrow, col, ctype, num, xf)
        #print i,cell_value    
    
    
if __name__ == '__main__':
    fname = "Book1.xls"
    bk = xlrd.open_workbook(fname , formatting_info=True) 
    sheetNum = bk.nsheets
    #print shxrange
    #print bk.sheet_names()
    for n in range(0,sheetNum):
        sh = bk.sheet_by_index(n)
        nrows = sh.nrows
        ncols = sh.ncols
        print '------------'
        print sh.nrows
        print sh.ncols
    
        for i in range(0,nrows):
            procRow(sh,i)
    
        print '------------'
        print sh.nrows
        print sh.ncols
    
    
    wb = copy(bk)
    #for n in range(0,sheetNum):
    #    ws = wb.get_sheet(n)
    #    ws.write(0, 0, 'changed!')
    wb.save('1.xls')



