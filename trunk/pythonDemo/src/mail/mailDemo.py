# coding=UTF-8

import smtplib
fromaddr = 'meteorafhd@gmail.com'
toaddrs  = 'meteorafhd@gmail.com'
msg = 'Enter you message here'
username = 'meteorafhd@gmail.com'
password = 'lewei0425'
server = smtplib.SMTP('smtp.gmail.com:587')
server.starttls()
server.login(username,password)
server.sendmail(fromaddr, toaddrs, msg)
server.quit()