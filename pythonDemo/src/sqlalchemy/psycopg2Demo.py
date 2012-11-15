# coding=UTF-8

import psycopg2
conn = psycopg2.connect(dbname='test',host='127.0.0.1', user='postgres',port='5432',password='Hello2222')

cur = conn.cursor()
cur.execute("select * from public.users")

print 'fetch one'
print cur.fetchone()

print 'fetch all'
print cur.fetchall()

print 'get all'
for record in cur:
    print record

conn.close()