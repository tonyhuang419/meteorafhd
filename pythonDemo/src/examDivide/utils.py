# coding=UTF-8
#!/usr/bin python

def colNameToNum(name):
    pow = 1
    colNum = 0
    for letter in name[::-1]:
            colNum += (int(letter, 36) -9) * pow
            pow *= 26
    return colNum - 1 

# 确保所有课程都存在开闭卷信息
def checkStuInfo(stuInfoList , examStyleMap ):
    for s in stuInfoList:
        if not examStyleMap.has_key(s.lesson):
            print u'%s 没有”开闭卷信息“，请补充' % s.lesson
            return False
        else:
#             print examStyleMap[s.lesson]
            if u'开卷' in examStyleMap[s.lesson]:
                s.examStyle = 1
            else:
                s.examStyle = 0
            s.examStyleOrg = examStyleMap[s.lesson]
    return True

def mapStuInfoByAttr( stuInfoList , attr ):
    stuInfoMap = {}
    for s in stuInfoList:
        attrContent = getattr(s, attr)
        if not stuInfoMap.has_key(attrContent):
            stuList = []
            stuList.append(s)
            stuInfoMap[attrContent] = stuList
        else:
            stuList = stuInfoMap[attrContent]
            stuList.append(s)
    return stuInfoMap
    
if __name__ == '__main__': 
    print colNameToNum('A')
    print colNameToNum('Z')
    print colNameToNum('AA')
    print colNameToNum('AZ')
    
