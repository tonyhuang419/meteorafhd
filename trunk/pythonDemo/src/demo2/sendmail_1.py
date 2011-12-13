#!/usr/bin python
# -*- coding: utf-8 -*-

from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from utils import c_logger, f_logger
import random
import sendmail
import smtplib
import time
import utils

def sendmail_1():
    sub='美国江森自控安防系统上海总代理-上海希宸经贸有限公司诚邀垂询' 
    html_content = '''<html><body>
    <b>详情请点击访问 <a 
    href='http://blog.sina.com.cn/secsung'>http://blog.sina.com.cn/secsung</a>
    <br><br>
    <img alt="" src="cid:johnson.jpg" style="border: none;" />
    详情请点击访问 <a 
    href='http://blog.sina.com.cn/secsung'>http://blog.sina.com.cn/secsung</a>
    <br><br><br><br></b>
    ------------------------------------------------<br>
    江森自控上海总代
    </body></html>
    ''' 
    mailto_list=utils.getMailList('maillist.txt')
    userList = utils.getUser('mailuser.properties')
    userSize = len(userList)
    for to in mailto_list:
        user=userList[random.randint(0,userSize-1)];
        c_logger.info ( "%s start send %s" % (user.username,to))
        sendmail.send_mail( user.host , user.username , user.password ,  to, sub ,'' ,html_content,['johnson.jpg'])
        time.sleep(random.randint(1,3))
    c_logger.info( "finish")   
    
if __name__ == '__main__':
    sendmail_1()
    
    