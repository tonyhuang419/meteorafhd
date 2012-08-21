# coding=UTF-8

# http://blog.csdn.net/yueguanghaidao/article/details/7485345
from sqlalchemy import create_engine
from sqlalchemy import MetaData, Column, Table, ForeignKey
from sqlalchemy import Integer, String

engine = create_engine('sqlite:///tutorial.db',echo=True)
metadata = MetaData(bind=engine)
users_table = Table('users', metadata,
                    Column('id', Integer, primary_key=True),
                    Column('name', String(40)),
                    Column('age', Integer),
                    Column('password', String),
                    )
addresses_table = Table('addresses', metadata,
                        Column('id', Integer, primary_key=True),
                        Column('user_id', None, ForeignKey('users.id')),
                        Column('email_address', String, nullable=False)
                        )

def create():
    # create tables in database
    metadata.create_all()

def insert():
    # create an Insert object
    ins = users_table.insert()
    # add values to the Insert object
    new_user = ins.values(name="Joe", age=20, password="pass")
     
    # create a database connection
    conn = engine.connect()
    # add user to database by executing SQL
    conn.execute(new_user)

def select():
    from sqlalchemy.sql import select
    s = select([users_table])
    result = s.execute()
    for row in result:
        print row
    
    conn = engine.connect()
    s = select([users_table.c.name, users_table.c.age])  
    result = conn.execute(s)  
    for row in result:  
        print row

if __name__ == "__main__":
#    create()
#    insert()
    select()
    
    
    
