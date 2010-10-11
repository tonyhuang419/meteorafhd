
import sqlite3

conn = sqlite3.connect('test.db')
c = conn.cursor()

# Create table
# c.execute('''create table stocks
# (date text, trans text, symbol text,
#  qty real, price real)''')

# Insert a row of data
c.execute("""insert into stocks
          values ('2006-01-05','BUY','RHAT',100,35.14)""")

c.execute('delete from  stocks')
c.execute('select * from stocks')  
a=c.fetchall()   
for i in a:  
    print ( i ) 

# Save (commit) the changes
conn.commit()

# We can also close the cursor if we are done with it
c.close()

print('............')

