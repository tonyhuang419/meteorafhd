#!/usr/bin python
# -*- coding: utf-8 -*-

from distutils.core import setup
import py2exe
import sys
from email.mime.image import MIMEImage
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
import random
import smtplib
import time
import utils

#http://ihipop.info/wap/index-wap2.php?p=2102
options = {"py2exe":
              { "compressed": 1,
                "optimize": 2,
                "bundle_files": 1,
              }
           }

setup( version = "0.1.0",
       zipfile=None,
      # options = options,
       console=["sendmail_2.py","sendmail.py","utils.py"])