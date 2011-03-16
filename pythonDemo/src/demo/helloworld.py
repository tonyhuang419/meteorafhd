# coding=UTF-8
from decimal import Decimal
import array
import random
import sys
import time
import re

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
print ('123' in '12345')
print ('456' in '12345')

print("...%s...%s"  % ('123',111));
strX = '''%(x)s..%(y)s''';
arg ={'x':'11','y':'22'};
print( strX  % arg );

x='33';
y='44';
vars()
print( strX  % vars() );

s = '111222111222';
s = s.replace('111', '333');
print(s);

#number
print ( random.random());
print ( random.choice( [1,2,3,4]) );  #从1234中随机获取一个
print(True+3);
print( '%x' % 15 );
print(  round(2.225 , 2));
print (3.1415*2);
print (3+2);
print (3-2);
print (3*2);
print (3/2);
print(3**3);
print( long(4));
num = 010;
print( num );   #octal literals
print( int(str(num) , 10));
print( Decimal('0.10') + Decimal('0.1') + Decimal('0.1') - Decimal('0.3') );

print(type(vary))
print('----------------------------------'+vary)
#loop
for c in vary:
    print(c);
    if ( c == '2'):
        print ("c==2");


#collection
arr = ( 1,2,3,4 )
# arr[0] = 0         TypeError: 'tuple' object does not support item assignment
print( arr[0] )
print( arr ) 


arr = [1,2,3],[4,5,6],[7,8,9,10];
newArr = [row[1]*2 for row in arr];
print( newArr );

newArr2 = [  str(row[1])*2 for row in arr];
print( newArr2 );

str3 = 'x'
arr3 = [str3]*10
arr32 = [[str3]*10]*3  #can't define two-dimension list like this
str3 = 'y'
arr32[0][0] = 'xxx'    
print( arr3 )
print( arr32 )
print('----------------')

mmap = {'a':'1', 'b':'2', 'c':'3'};
print( mmap['a'])
mkeys = mmap.keys();
print( mkeys );
mkeys.sort();
print( mkeys );
print( mmap.keys() );

s = '123'
s = array.array( 'c' ,s )
s.reverse()

print(s.tostring())
print( '123'[ :: -1] )
print( '123'[ -1000:1000] )

s = '123'
print(''.join([s[i] for i in range(len(s)-1,-1,-1)]))


#condition
if (varx == 1     \
    and 1) :    #the backslashes allow continuations
    print("11111");
elif (varx==23):
    print("2222");
    print(len("xxxxxxxxx"))
    print(len("我"))
    
# check reference count
sys.getrefcount(1) 

print( 2 or 3 )   #return left oper and if true  
print( [] or {} ) #else return right oper and(true or false)
print( [] or 3 )
print( 3 or {} )

print( 2 and 3 ) #return left oper and if true                   
print( [] and {} ) #else return right oper and(true or false)
print( [] and 3 )
print( 3 and {} )


L = ['abc'];
L.append(L);
print(L);

i = 0
while  i<1 :
    i += 1;
    print("in while")
    #break
else:
    print("while's else")

print( sum ( [1,2,3,4] ))    

print(time.time())

match = re.match('Hello[ \t]*(.*)world','Hello Python world')
print( match.group(1) )

x = (1)   #an integer
print(x)
x = (1,)  #a tuple containing an integer
print(x)

T = (4,5,6)
T = (1,) + T[1:]
print(T)


L = [1,2];
X = L;
L = L + [3,4]   #make a new object
print(X)
print(L)

X = L
L += [3,4]  #extend object
# print ( X, L )
print X, L 

L.extend([7,8,9])
L[len(L):] = [11,12,13]
print(L)

T1,T2,T3 = (1,2,3),(4,5,6),(7,8,9)
print(zip(T1,T2,T3))
T1,T2,T3 = [1,2,3],[4,5,6],[7,8,9]
print(zip(T1,T2,T3))

x = [1,2,3,4,5]
x = [x+10 for x in x]
print(x)





















