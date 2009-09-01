'''
Saving The Universe 
'''

def analyze(searchList , targetList):
    searchSet = set(searchList);
    
    count=0;
    tempSet = set();
    for i in range(len(targetList)):
        tempSet.add(targetList[i]);
        if len(tempSet) == len(searchSet) :
            count += 1;
            i -= 1;
            tempSet.clear();
    return count;

searchList = ['1', '2', '3', '4' , '5'];
targetList = ['1', '2', '3', '4' ,'5' ,'1' ,'2' ,'3','4','5'];
print(analyze( searchList , targetList )) 
searchList = ['Yeehaw', 'NSM', 'Dont Ask', 'B9' , 'Googol'];
targetList = ['Yeehaw', 'Yeehaw', 'Googol', 'B9' ,'Googol' ,'NSM' ,'B9','NSM','Dont Ask','Googol' ];
print(analyze( searchList , targetList )) 
searchList = ['Yeehaw', 'NSM', 'Dont Ask', 'B9' , 'Googol'];
targetList = ['Googol', 'Dont Ask', 'NSM', 'NSM' ,'Yeehaw' ,'Yeehaw' ,'Googol' ];
print(analyze( searchList , targetList )) 


f = open('A-small-practice.in');
#print(f);
sign=0;
seq=0;
expNum = f.readline();
expNum = int(expNum);
for i in range(0, expNum):
    if sign==0:
     listX = list();
     listY = list();
    num = f.readline();
#    print(num)
    num = int(num);
#    print(str(num)+'-------------');
    for j in range(0, num):
        line = f.readline();
        if sign==0:
            listX.append(line);
        if sign==1:
            listY.append(line);
#        print(line+"~~~");
    if sign==0:
        sign = 1;
    else:
        sign = 0;
        count = analyze(listX,listY);
        seq +=1;
        print('Case #'+str(seq)+': '+str(count));
f.close();


