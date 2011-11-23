# coding=UTF-8
#!/usr/bin python

import xlrd
import xlwt
import re 
from xlutils.copy import copy  

def procRow( sh , nrow , shbak ):
    cell_value = shbak.cell_value(nrow,0)
   
    #cellphone number
    #listNum =  re.findall(r'\d{11}',cell_value)
    
    '''
    email_re = re.compile(   
    r"(([-!#$%&'*+/=?^_`{}|~0-9A-Z]+(\.[-!#$%&'*+/=?^_`{}|~0-9A-Z]+)*"  # dot-atom   
    r'|^"([\001-\010\013\014\016-\037!#-\[\]-\177]|\\[\001-011\013\014\016-\177])*"' # quoted-string   
    r')@(?:[A-Z0-9]+(?:-*[A-Z0-9]+)*\.)+[A-Z]{2,6})', re.IGNORECASE)  # domain
    #email_re = re.compile(cell_value)
    '''
    #email
    listNum =  re.findall(r'[0-9a-zA-Z]+@[0-9a-zA-Z]+\.[0-9a-zA-Z]+',cell_value)
    strNum=''
    for num in listNum:
        #print type(i)
        strNum = strNum + '\n'+ num
        sh.write(nrow, 1, strNum)
    
if __name__ == '__main__':
    fname = "EMAIL.xls"
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
    
    




