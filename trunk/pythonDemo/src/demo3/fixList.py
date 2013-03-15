# -*- coding: utf-8 -*-
#!/usr/bin/python2.6
import codecs
import operator

filePath='song.txt'
songList=[]
albumSongMap={}

def genSongList(filePath):
    try:
        currentAlbum = ''
#        for line in open(filePath, "r", "utf-8"):
        for line in open(filePath):
            line = line.strip()
            if line and '——' in line :
                currentAlbum = line.replace('——','')
                albumSongMap[currentAlbum]=[]
            elif line:
                songList.append(line)
                albumSongMap.get(currentAlbum).append(line)
    except Exception as ex: 
        print  ex
        
def songListToMap(songList):
    songMap = {}
    for s in songList:
        if songMap.has_key(s):
            songMap[s] = songMap.get(s)+1
        else:
            songMap[s] = 1
    return  songMap  

def getAblumBySong(song):
    albums = ''
    for key, value in albumSongMap.items():
        if song in value:
            if albums<>'':
                albums = '%s,%s' % ( albums , key )
            else:
                albums = key
    return  albums
        
genSongList(filePath)
songMap = songListToMap(songList)
#for key, value in songMap.items():
#    print '%s , %s' % (key,value)

listx = sorted(songMap.iteritems(), key=operator.itemgetter(1),reverse=True)
index = 0
for s in listx:
    index += 1
    print '%d : %s , %s , %s'  % (index , s[0], s[1] , getAblumBySong(s[0]) )






