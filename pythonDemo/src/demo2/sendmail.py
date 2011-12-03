#!/usr/bin python
# -*- coding: utf-8 -*-

import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from email.mime.image import MIMEImage 
from base64 import b64encode


mail_host="smtp.sina.com"
mail_user="js_ctrl"
mail_pass="xx"
mail_postfix="sina.com"
######################

mailto_list=[]
def getMailList():
    try:
        f = open('maillist.txt', 'r')
        for line in f:
            mailto_list.extend(line.split())
        f.close() 
    except Exception as ex: 
        print ('\nSome error/exception occurred.')
        print ( ex );
        # here, we are not exiting the program
    finally:
        print ('email list has be read')

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
        print ( "发送成功 %s" % (to))
        return True
    except Exception, e:
        print "发送失败"
        print str(e)
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
    
        