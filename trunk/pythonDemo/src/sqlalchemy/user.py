# coding=UTF-8

# http://blog.csdn.net/yueguanghaidao/article/details/7485967
from sqlalchemy import create_engine
from sqlalchemy import Column, MetaData, Table
from sqlalchemy import Integer, String, ForeignKey
from sqlalchemy.orm import mapper, sessionmaker
 
####################################################
class User(object):
    """"""
 
    #----------------------------------------------------------------------
    def __init__(self, name, fullname, password):
        """Constructor"""
        self.name = name
        self.fullname = fullname
        self.password = password
 
    def __repr__(self):
        return "<User('%s','%s', '%s')>" % (self.name, self.fullname, self.password)
 
# create a connection to a sqlite database
# turn echo on to see the auto-generated SQL
engine = create_engine("sqlite:///tutorial.db", echo=True)
 
# this is used to keep track of tables and their attributes
metadata = MetaData()
users_table = Table('users', metadata,
                    Column('user_id', Integer, primary_key=True),
                    Column('name', String),
                    Column('fullname', String),
                    Column('password', String)
                    )
email_table = Table('email', metadata,
                    Column('email_id', Integer, primary_key=True),
                    Column('email_address', String),
                    Column('user_id', Integer, ForeignKey('users.user_id'))
                    )


def create():
    Session = sessionmaker(bind=engine)  
    session = Session()  
    mike_user = User("mike", "Mike Driscoll", "password")  
    session.add(mike_user)
    session.commit()   


def select():
    Session = sessionmaker(bind=engine)  
    session = Session()  
    all_users = session.query(User).all()  
    print all_users  
    
    our_user = session.query(User).filter_by(name='mike').first()  
    print our_user  

if __name__ == "__main__":
    # create the table and tell it to create it in the 
    # database engine that is passed
#    metadata.create_all(engine)
    
    # create a mapping between the users_table and the User class
    mapper(User, users_table)
    
#    create()
    select()
    
    