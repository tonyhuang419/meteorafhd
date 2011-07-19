# coding=UTF-8
# discard

#导入smtplib和MIMEText
from django.utils.log import logger
from email.mime.text import MIMEText
from utils.threadpool import ThreadPool, makeRequests, NoResultsPending
import smtplib
import time


#设置服务器，用户名、口令以及邮箱的后缀
mail_host="smtp.gmail.com"
mail_user="meteorafhd"
mail_pass="*****"
mail_postfix="gmail.com"
mail_port=587

smtp = None
def smtpConnect():
    global smtp 
    print('init smtpConnect')
    logger.info('init smtpConnect')
    smtp = smtplib.SMTP(mail_host, mail_port )
    smtp.set_debuglevel(1)
    smtp.ehlo()
    smtp.starttls()
    smtp.ehlo()
    smtp.login(mail_user,mail_pass)

def send_mail( mailto, sub, content , reconnect ):
    global smtp 
    me=mail_user+"<"+mail_user+"@"+mail_postfix+">"
    msg = MIMEText(content,'html')
    msg['Subject'] = sub
    msg['From'] = me
    msg['To'] = mailto
    try:
        if( not smtp  or reconnect ):
            smtpConnect()
        #s = smtplib.SMTP(mail_host, mail_port )
        #s.set_debuglevel(1)
        #s.ehlo()
        #s.starttls()
        #s.ehlo()
        smtp.sendmail(me, mailto, msg.as_string())
        logger.info('%s , %s' % (mailto  , msg.as_string()) )
        #smtp.close()
        return True
    except Exception, e:
        print(e)
        logger.info(e)
        return False

def send_mail_tt( mailto,sub,content ):
    if send_mail( mailto , sub , content  ,False):
        return True
    else:
        return send_mail( mailto , sub , content ,True)



def print_result(request, result):
    if result:
        print "**** Result from request #%s: %r" % (request.requestID, 'success send mail')
    else:
        print "**** Result from request #%s: %r" % (request.requestID, 'fail send mail')


sendMailPool = ThreadPool(1) 
def put_mail_into_pull( mailto, sub, content ):
    global sendMailPool
    args = [((mailto,sub,content), {})  ]
    print(args)
    reqs = makeRequests( send_mail_tt , args , print_result )
    for req in reqs:
        print(type(req))
        sendMailPool.putRequest( req )
        print "Work request #%s added." % req.requestID
    while True:
        try:
            sendMailPool.poll()
        except KeyboardInterrupt:
            print "**** Interrupted!"
            break
        except NoResultsPending:
            print "**** No pending results."
            break
    if sendMailPool.dismissedWorkers:
        print "Joining all dismissed worker threads..."
        sendMailPool.joinAllDismissedWorkers()

   
if __name__ == '__main__':
    put_mail_into_pull( "meteorafhd0425@gmail.com","subject1","content1" )
    time.sleep(1)
    print('---------------')
    put_mail_into_pull( "meteorafhd0425@gmail.com","subject2","content2" )
    time.sleep(1)
    put_mail_into_pull( "meteorafhd0425@gmail.com","subject3","content3" )
    time.sleep(1)
    put_mail_into_pull( "meteorafhd0425@gmail.com","subject4","content4" )
    time.sleep(1)
    put_mail_into_pull( "meteorafhd0425@gmail.com","subject5","content5" )
    #if send_mail_tt("meteorafhd0425@gmail.com","subject","content"):
    #   print "success send mail"
    #else:
    #     print "fail send mail"


