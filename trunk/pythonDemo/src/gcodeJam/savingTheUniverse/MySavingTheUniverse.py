'''
Saving The Universe 
'''

def analyze(searchList , targetList):
 if len(targetList)==0:
  return 0;
 else:
  searchSet = set(searchList);
 #print(len(searchSet))
  targetSet = set(targetList);
 #print(len(targetSet))
 
  searchLen = len(searchSet);
 
 #searchList.sort();  
 #for item in searchList:
   # print(item)
 #print('--');
 #targetList.sort();
 
  count=1;
  i=0;
  tempSet = set();
  for item in targetList:
    i+=1;
    #print(item)
    tempSet.add(item);
#    print(len(tempSet));
    if len(tempSet) == searchLen  :
#        print(item);
        count += 1;
        if i==len(targetList):
           return count;
#        print(count);
        tempSet.clear();
    elif i==len(targetList):
         return count;
#        for s in tempSet:
#          searchSet.remove(s);
#        for s in searchSet:
#            print(s+'-');
        

#searchList = ['Yeehaw', 'NSM', 'Dont Ask', 'B9' , 'Googol'];
#targetList = ['Yeehaw', 'Yeehaw', 'Googol', 'B9' ,'Googol' ,'NSM' ,'B9' ,'NSM','Dont Ask','Googol'];
#analyze( searchList , targetList ) 
#
#print('---------------')
#
#searchList = ['Yeehaw', 'NSM', 'Dont Ask', 'B9' , 'Googol'];
#targetList = ['Googol', 'Dont Ask', 'NSM', 'NSM' ,'Yeehaw' ,'Yeehaw' ,'Googol' ];
#analyze( searchList , targetList ) 

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


