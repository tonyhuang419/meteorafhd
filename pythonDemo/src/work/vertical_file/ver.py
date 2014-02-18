# coding=UTF-8
#!/usr/bin python

import xlrd
import xlwt
import datetime
import work.dateutils as dateutils

SRC_PATH='src.xls'
DEST_PATH='dest.xls'

def process():
    srcFile = xlrd.open_workbook(SRC_PATH , formatting_info=True)
    srcSteet = srcFile.sheet_by_index(0)
    nrows = srcSteet.nrows
    ncols = srcSteet.ncols
    
    destFile = xlwt.Workbook()
    hourSheet = destFile.add_sheet('hours',cell_overwrite_ok=True) 
    
    date_format = xlwt.XFStyle()
    date_format.num_format_str = 'dd/mm/yyyy'

    for i in range(0,nrows):
        for j in range(0,ncols):  
            if i==0:
                hourSheet.write( i ,j , srcSteet.cell_value(i,j) )
            else:
                for k in range (0,7) :
                    if j==3:
                        d = dateutils.addDay( datetime.date(*xlrd.xldate_as_tuple( srcSteet.cell_value(i,3) , srcFile.datemode )[:3]) , k  )
                        hourSheet.write( i*7+k-6 ,j , d , date_format)
                    elif j==4:
                        hourSheet.write( i*7+k-6 ,j , srcSteet.cell_value(i,j+k) )
                    else:
                        hourSheet.write( i*7+k-6 ,j , srcSteet.cell_value(i,j) )
            
    destFile.save('dest.xls')

if __name__ == '__main__':
    process()