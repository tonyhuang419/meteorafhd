# coding=UTF-8
#!/usr/bin python

import xlrd

FILE_PATH='t2.xls'
REPRESENTATIVE = 1   #1: ignore REPRESENTATIVE

file = xlrd.open_workbook(FILE_PATH)
sheetCtx = file.sheet_by_index(0)
nrows = sheetCtx.nrows
ncols = sheetCtx.ncols

representativeStart = ncols-8
representativeEnd = ncols-5

print nrows
print ncols

ids = ''

for i in range(0,nrows):
    sql = ''
    for j in range(0,ncols):
        if REPRESENTATIVE==1 and j>=representativeStart and j<=representativeEnd:
            pass
        else:
            sql += (str)(sheetCtx.cell_value(i,j))
    ids += (str)(sheetCtx.cell_value(i,43)) +','
    print sql
print ids