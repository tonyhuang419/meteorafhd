# coding=UTF-8

import time
import urllib
import urllib2

loginUrl = 'http://www.gewara.com/check_user.xhtml'
values = {'j_username' : 'meteorafhd@gmail.com',
          'j_password' : '*',
          'rememberMe' : 'true' }

seconds =  int(time.time() * 100)
#loginSignUrl = 'http://www.gewara.com/home/clickGetLoginPoint.xhtml?math=%s&type=bit' % ( seconds )
loginSignUrl = 'http://www.gewara.com/home/clickGetLoginPoint.xhtml?math=%s&type=' % ( seconds )

def get_headers():
    headers = [
        ("Host","www.gewara.com"),       
        ("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:6.0) Gecko/20100101 Firefox/6.0"),
        ("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8"),
        ("Accept-Language","zh-cn,zh;q=0.5"),
        ("Accept-Charset","GB2312,utf-8;q=0.7,*;q=0.7"),
        ("Connection","keep-alive"),
        ("Referer","http://www.gewara.com/login.xhtml")
    ]
    return headers

def signin():
    data = urllib.urlencode(values)
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor()) 
    #opener.open( 'http://www.gewara.com/login.xhtml'  ) 
    #opener.addheaders =  get_headers() 
    opener.open( loginUrl , data )
    #res = opener.open( 'http://www.gewara.com/home/mygewa.xhtml'  )
    res = opener.open( loginSignUrl )
    html = res.read()
    print(html)
    
    
if __name__ == '__main__':  
    signin()
