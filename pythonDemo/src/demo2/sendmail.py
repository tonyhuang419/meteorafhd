#!/usr/bin python
# -*- coding: utf-8 -*-

from base64 import b64encode
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import smtplib
import time


mail_host="smtp.sina.com"
mail_user="js_ctrl"
mail_pass="xx"
mail_postfix="sina.com"
mailto_list=[]

logfile = 'log.txt'
def initlog():
    import logging
    logger = logging.getLogger()
    hdlr = logging.FileHandler(logfile)
    console = logging.StreamHandler()
    formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
    hdlr.setFormatter(formatter)
    logger.addHandler(hdlr)
    logger.addHandler(console)
    logger.setLevel(logging.NOTSET)
    return logger
logger = initlog()

def getMailList():
    try:
        f = open('maillist.txt', 'r')
        for line in f:
            line = line.split()
            if len(line)>0:
                mailto_list.extend(line)
        f.close() 
    except Exception as ex: 
        logger.info ('\nSome error/exception occurred.')
        logger.info ( ex );
        # here, we are not exiting the program
    finally:
        logger.info ('email list has be read')

def b64_utf8(data):
    """encode into base64."""
    return '=?utf-8?B?%s?=' % b64encode(data)


def send_mail(to,sub,text,html):
    me=mail_user+"<"+mail_user+"@"+mail_postfix+">"
    msg = MIMEMultipart('alternative')
    part1 = MIMEText(text, 'plain', 'utf-8')
    part2 = MIMEText(html, 'html', 'utf-8')
    msg.attach(part1)
    msg.attach(part2)
    msg['Subject'] = b64_utf8(sub)
    msg['From'] = me
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
        s.connect(mail_host)
        s.login(mail_user,mail_pass)
        s.sendmail(me, to, msg.as_string())
        s.close()
        logger.info ( "发送成功 %s" % (to))
        return True
    except Exception, e:
        logger.info( "发送失败")
        logger.info( str(e))
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
    getMailList()
    for to in mailto_list:
        send_mail(to,sub,'',html_content)
        time.sleep(2)
    
        