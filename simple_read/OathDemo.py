#!/usr/bin/python 
#-*- coding: utf-8 -*-
 
#-------------------------------------------------------
#Var
#-------------------------------------------------------
###AppInfo
APP_KEY = "*"
APP_SECRET = "*"
#-------------------------------------------------------
 
from weibopy import OAuthHandler, oauth, WeibopError
 
###���Ӧ�õ�token
auth = OAuthHandler(APP_KEY, APP_SECRET)
###����û�OAuth��¼��url
auth_url = auth.get_authorization_url()
print "Please Auth: "+auth_url
verifier = raw_input("PIN: ").strip()
access_token = auth.get_access_token(verifier)
###���access_token
 
###����΢��
api = API(auth)
status = api.update_status(status="Hello World")
 
###���access_token��,�Ժ�����ͨ��
###auth.set_access_token(access_token.key, access_token.secret)
###�����Ȩ.