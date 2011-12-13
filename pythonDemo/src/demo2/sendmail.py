#!/usr/bin python
# -*- coding: utf-8 -*-

from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import random
import smtplib
import time
import utils
from utils import c_logger, f_logger

def send_mail(host , user, password, to, sub, text, html,attc):
    msg = MIMEMultipart('alternative')
    part1 = MIMEText(text, 'plain', 'utf-8')
    part2 = MIMEText(html, 'html', 'utf-8')
    msg.attach(part1)
    msg.attach(part2)
    msg['Subject'] = utils.b64_utf8(sub)
    msg['From'] = user
    msg['To'] = to
    msg.add_header("Disposition-Notification-To","1")
    
    
    for p in attc:
        fp = open(p, 'rb')
        msgImage = MIMEImage(fp.read())
        fp.close()
        msgImage.add_header('Content-ID', '<'+p+'>') 
        msgImage.add_header('Content-Disposition', 'attachment', filename = p) 
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
 

    
    