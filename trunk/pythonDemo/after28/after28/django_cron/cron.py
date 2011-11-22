'''
Created on 2011-7-20

@author: FHDone
'''
from after28.django_cron.base import Job, cronScheduler
from after28.mail_info.models import MailInfo
from after28.utils import dateutils
from django.utils.datetime_safe import datetime

class  SendMailEveryDay(Job):
        run_every = 20
        def job(self):
            print('##########################')

cronScheduler.register(SendMailEveryDay)

