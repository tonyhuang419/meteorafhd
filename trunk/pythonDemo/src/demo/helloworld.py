# coding=UTF-8
import random

"i am comment"

s = '''
i am comment
but i am String too
don't recommend this to be comment
"add text"'''
print(s)

print (  dir("s") )
print (  help("s".index) )

print (  ord('\n') )  #返回ASCII码
print ("hello world");
print ( random.random());

#从1234中随机获取一个
print ( random.choice( [1,2,3,4]) );


print (3.1415*2);
print (3+2);
print (3-2);
print (3*2);
print (3/2);
print ('xxx'+'yy');
print(3**3);
print(type(11));
print(type('xxx'));

print( "ABCD".isalpha() )
print( "1234ABCD".isalpha() )

varx = 23;
vary = str(varx) + 'x';
print(vary)
print( vary.find('23') )
print(vary.upper())

for c in vary:
    print(c);
    if ( c == '2'):
        print ("c==2");

str1 = "xx";
str2 = "xx";
print(str1==str2);

if (varx == 1) :
    print("11111");
elif (varx==23):
    print("2222");
    print(len("xxxxxxxxx"))
    print(len("我"))
    