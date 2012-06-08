# coding=UTF-8
'''
Created on May 7, 2012

@author: wle
'''

def getFileList(filePath):
    print 'read file start'
    menuList=[]
    try:
        for line in open(filePath):      
#            menuList.append(line.split()[0])
            menuList.append(line)
    except Exception as ex: 
        print ( ex )
#    finally:
#        pass
    print 'read file success'
    return menuList

def delCopy(menuList):
    newMenuList=[]
    for line in menuList:
        if line not in newMenuList:
            newMenuList.append(line)
        else:
            print '%s del' % (line)
    print 'scan file finished, found copies %d' % ( len(menuList) - len(newMenuList) )
    return newMenuList
    
def writeListToNewFile(menuList):
    newMeunStr='\n'.join(menuList)
    f = file('newList.txt', 'w') 
    f.write(newMeunStr) 
    f.close()
    print 'new file has generated'

def cleanMp3List(filePath):
    menuList = getFileList(filePath);
#    print menuList
#    print delCopy(menuList)
    newMenuList = delCopy(menuList)
    writeListToNewFile(newMenuList)
    
if __name__ == '__main__':
    cleanMp3List('C:\\x.txt');

