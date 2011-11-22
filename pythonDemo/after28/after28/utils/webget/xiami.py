# coding=UTF-8

import urllib
import urllib2

loginUrl = 'http://www.xiami.com/member/login'
loginSignin = 'http://www.xiami.com/task/signin'
values = {'email' : 'meteorafhd@gmail.com',
          'password' : '*' ,
          'done' : 'http://www.xiami.com',
          'type' : '',
          'submit' : '登 录',
          'autologin' : '1' }

def get_headers():
    headers = [
        ("Host","www.xiami.com"),       
        ("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:6.0) Gecko/20100101 Firefox/6.0"),
        ("Accept","*/*"),
        ("Accept-Language","zh-cn,zh;q=0.5"),
        ("Connection","keep-alive"),
        ("Content-Type","application/x-www-form-urlencoded; charset=UTF-8"),
        ("X-Requested-With","XMLHttpRequest"),
        ("Referer","http://www.xiami.com/"),
        ("Content-Length","0"),
        ("Pragma","no-cache"),
        ("Cache-Control","no-cache")
    ]
    return headers
 
def signin():
    data = urllib.urlencode(values)
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor())  
    res = opener.open( loginUrl , data )
    html = res.read()
    if( html.find('发现') != -1 ):
        print('login success')
        
    #headers = [('Pragma','no-cache'),('Cache-Control','no-cache')]
    #opener.addheaders = headers
    print 'check in'
    opener.addheaders =  get_headers() 
    res = opener.open( loginSignin )
    html = res.read();
    print html
    print 'check in finish'
    res.close()

if __name__ == '__main__':  
    signin();


