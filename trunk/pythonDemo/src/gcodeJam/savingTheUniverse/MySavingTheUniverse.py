'''
Saving The Universe 
'''

def analyze(searchList , targetList):
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
 
 count=0;
 i=0;
 tempSet = set();
 for item in targetList:
    i+=1;
    #print(item)
    tempSet.add(item);
#    print(len(tempSet));
    if len(tempSet) == searchLen  :
        print(item);
        count += 1;
        print(count);
        tempSet.clear();
    elif i==len(targetList):
        for s in tempSet:
          searchSet.remove(s);
        for s in searchSet:
            print(s+'-');
        
    


searchList = ['Yeehaw', 'NSM', 'Dont Ask', 'B9' , 'Googol'];
targetList = ['Yeehaw', 'Yeehaw', 'Googol', 'B9' ,'Googol' ,'NSM' ,'B9' ,'NSM','Dont Ask','Googol'];
analyze( searchList , targetList ) 

print('---------------')

searchList = ['Yeehaw', 'NSM', 'Dont Ask', 'B9' , 'Googol'];
targetList = ['Googol', 'Dont Ask', 'NSM', 'NSM' ,'Yeehaw' ,'Yeehaw' ,'Googol' ];
analyze( searchList , targetList ) 


