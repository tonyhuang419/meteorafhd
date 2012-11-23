# coding=UTF-8

import pyodbc

cnxn = pyodbc.connect('DRIVER={SQL Server};SERVER=10.47.131.54;DATABASE=nasa2_db_20121119;Trusted_Connection?=yes')
cursor = cnxn.cursor()

cursor.execute("select top 100 m.id,m.date from nasa2.M_CALENDAR m")
rows = cursor.fetchall()
for row in rows:
    print '%s,%s' % (row.id,row.date)


