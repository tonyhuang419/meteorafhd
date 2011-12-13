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

def sendmail_2():
    sub='美国江森自控安防系统产品资料' 
    html_content = '''<HTML><HEAD><META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=GB2312"></HEAD><BODY>
    <div class="gmail_quote"><div style="line-height:1.7;color:#000000;font-size:14px;font-family:arial">
    <div>您好！<br>&nbsp;&nbsp;美国江森自控上海总代理，现诚招一级代理，欢迎有资源的工程商、集成商、经销商加盟，请看附件！<br><br>&nbsp;</div>
    <div><div style="color:rgb(0,0,0);line-height:1.7;font-family:arial;font-size:14px">
    <div style="color:rgb(0,0,0);line-height:1.7;font-family:arial;font-size:14px"><div><div>
    <div>顺颂商祺！</div>
    <div>于卓琼</div>
    <div>市场总监</div>
    <div>T: 021-64512509&nbsp; C:13501693190&nbsp; E: <a href="mailto:shvirjin@163.com" target="_blank">shvirjin@163.com</a></div>
    <div><a href="http://blog.sina.com.cn/secsung" target="_blank">http://blog.sina.com.cn/secsung</a></div>
    <div>
    <div><span style="line-height:21px;font-family:Simsun">A more confortable, safe and sustainable world</span></div>
    <div>-----------------------------------------------<br style="line-height:21px;font-family:Simsun">
    <span style="line-height:21px;font-family:Simsun">江森自控上海总代理</span></div>
    <div><span style="line-height:21px;font-family:Simsun">19:24:09 2011-12-12</span></div></div></div></div></div></div>
    <div style="background:rgb(222,232,242);padding:4px;clear:both;font-family:verdana,Arial,Helvetica,sans-serif;margin-top:10px;margin-bottom:15px">
    <div style="padding:4px 8px 8px;line-height:16px;font-size:14px"><b>从网易163邮箱发来的超大附件</b></div><div style="background:rgb(255,255,255);padding:4px">
    <div style="padding:6px 4px;min-height:36px;clear:both">
    <div style="width:36px;float:left">
    <a href="http://fs.163.com/fs/display/?p=X-NETEASE-HUGE-ATTACHMENT&amp;file=Xt9C6OiQlThxcHl1OlqSAGfB6-TbYqzD4opBeoPvVB3BFOqda4CzUN7XQirmy137pNzMQzZJ8Zofu3eiHEgiTA&amp;title=%E6%B1%9F%E6%A3%AE%E8%B5%84%E6%96%99" target="_blank">
    <img src="cid:ico-bfile-6.gif"  border="0"></a></div>
    <div><div style="padding:0px;line-height:14px;font-size:12px">
    <a style="color:rgb(0,0,0);text-decoration:none" href="http://fs.163.com/fs/display/?p=X-NETEASE-HUGE-ATTACHMENT&amp;file=Xt9C6OiQlThxcHl1OlqSAGfB6-TbYqzD4opBeoPvVB3BFOqda4CzUN7XQirmy137pNzMQzZJ8Zofu3eiHEgiTA&amp;title=%E6%B1%9F%E6%A3%AE%E8%B5%84%E6%96%99" target="_blank">江森产品.rar</a>                                    <span style="color:rgb(187,187,187)"> (60.57M, 2011年12月27日 19:39 到期)</span> </div>
     <div style="padding:4px 0px;line-height:14px;font-size:12px"> <a href="http://fs.163.com/fs/display/?p=X-NETEASE-HUGE-ATTACHMENT&amp;file=Xt9C6OiQlThxcHl1OlqSAGfB6-TbYqzD4opBeoPvVB3BFOqda4CzUN7XQirmy137pNzMQzZJ8Zofu3eiHEgiTA&amp;title=%E6%B1%9F%E6%A3%AE%E8%B5%84%E6%96%99" target="_blank">下载</a>                                </div>
    </div></div></div></div>
    <br><br><span title="neteasefooter"><span></span></span></div></div><br><br><span title="neteasefooter"><span></span></span></div><br>
    </BODY></HTML>    ''' 
    mailto_list=utils.getMailList('maillist.txt')  
    userList = utils.getUser('mailuser.properties')
    userSize = len(userList)
    for to in mailto_list:
        user=userList[random.randint(0,userSize-1)];
        c_logger.info ( "%s start send %s" % (user.username,to))
        sendmail.send_mail( user.host , user.username , user.password ,  to, sub ,'' ,html_content,['ico-bfile-6.gif'])
        time.sleep(random.randint(1,3))
    c_logger.info( "finish")   
    
if __name__ == '__main__':
    sendmail_2()
    
    