#!/usr/bin python
# -*- coding: utf-8 -*-

from base64 import b64encode
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import smtplib
import time
import utils
import random
import mailuser

c_logger = utils.initCommonLog('log.txt')
f_logger = utils.initFailLog('log.fail.txt')

def getMailList():
    mailto_list=[]
    try:
        f = open('maillist.txt', 'r')
        for line in f:
            line = line.strip()
            if len(line)>0:
                mailto_list.append(line)
        f.close() 
    except Exception as ex: 
        c_logger.info ('\nSome error/exception occurred.')
        c_logger.info ( ex );
        # here, we are not exiting the program
    finally:
        c_logger.info ('email list has be read')
    return mailto_list    
        
def b64_utf8(data):
    """encode into base64."""
    return '=?utf-8?B?%s?=' % b64encode(data)



def send_mail(host , user, password, to, sub, text, html):
    msg = MIMEMultipart('alternative')
    part1 = MIMEText(text, 'plain', 'utf-8')
    part2 = MIMEText(html, 'html', 'utf-8')
    msg.attach(part1)
    msg.attach(part2)
    msg['Subject'] = b64_utf8(sub)
    msg['From'] = user
    msg['To'] = to
    msg.add_header("Disposition-Notification-To","1")
    
    fp = open('johnson.jpg', 'rb')
    msgImage = MIMEImage(fp.read())
    fp.close()
    msgImage.add_header('Content-ID', '<johnson>') 
    msgImage.add_header('Content-Disposition', 'attachment', filename = 'johnson.jpg') 
    msg.attach(msgImage)
        
    try:
        s = smtplib.SMTP()
        s.connect(host)
        s.login(user, password)
        s.sendmail(user, to, msg.as_string())
        s.close()
        c_logger.info ( "%s send success %s" % (user,to))
        return True
    except Exception, e:
        c_logger.info( "%s ------- send fail %s" %  (user,to))
        f_logger.info(to)
        c_logger.info( str(e))
        return False
 
sub='美国江森自控安防系统上海总代理-上海希宸经贸有限公司诚邀垂询' 
 
html_content = '''<html><body>
<b>详情请点击访问 <a 

href='http://blog.sina.com.cn/secsung'>http://blog.sina.com.cn/secsung</a>
<br><br>
<img alt="" src="cid:johnson" style="border: none;" />
详情请点击访问 <a 

href='http://blog.sina.com.cn/secsung'>http://blog.sina.com.cn/secsung</a>
<br><br><br><br></b>
------------------------------------------------<br>
江森自控上海总代
</body></html>
''' 
    
if __name__ == '__main__':
    mailto_list=getMailList()
    userList = mailuser.getUser('mailuser.properties')
    userSize = len(userList)
    for to in mailto_list:
        user=userList[random.randint(0,userSize-1)];
        c_logger.info ( "%s start send %s" % (user.username,to))
        send_mail( user.host , user.username , user.password ,  to, sub ,'' ,html_content)
        time.sleep(random.randint(1,3))
    c_logger.info( "finish")