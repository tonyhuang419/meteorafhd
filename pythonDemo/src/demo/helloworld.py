# coding=UTF-8
import random
import sys
from decimal import Decimal

"i am comment"

#system
print (  dir("s") )
print (  help("s".index) )

#date type
print(type(11));
print(type('xxx'));

#string
s = '''
i am comment
but i am String too
don't recommend this to be comment
"add text"'''
print(s);

print (  ord('\n') )     #返回ASCII码
print ("hello world");
repr ("hello world");
print( "ABCD".isalpha() )
print( "1234ABCD".isalpha() )
varx = 23;
vary = str(varx) + 'x';
print(vary)
print( vary.find('23') )
print(vary.upper())
str1 = "xx";
str2 = "xx";
print(str1==str2);
print ('xxx'+'yy');

print("...%s...%s"  % ('123',111));
str = '''%(x)s..%(y)s''';
arg ={'x':'11','y':'22'}
print( str  % arg );

#number
print ( random.random());
print ( random.choice( [1,2,3,4]) );  #从1234中随机获取一个
print(True+3);
print(  round(2.225 , 2));
print (3.1415*2);
print (3+2);
print (3-2);
print (3*2);
print (3/2);
print(3**3);
print( long(4));
num = 010
print( num );   #octal literals
print( int(str(num) , 10));
print( Decimal('0.10') + Decimal('0.1') + Decimal('0.1') - Decimal('0.3') );


#loop
for c in vary:
    print(c);
    if ( c == '2'):
        print ("c==2");


#collection
arr = [1,2,3],[4,5,6],[7,8,9,10];
newArr = [row[1]*2 for row in arr];
print( newArr );

newArr2 = [  str(row[1])*2 for row in arr];
print( newArr2 );

mmap = {'a':'1', 'b':'2', 'c':'3'};
mkeys = mmap.keys();
print( mkeys );
mkeys.sort();
print( mkeys );
print( mmap.keys() );


#condition
if (varx == 1) :
    print("11111");
elif (varx==23):
    print("2222");
    print(len("xxxxxxxxx"))
    print(len("我"))
    
# check reference count
sys.getrefcount(1) 
    