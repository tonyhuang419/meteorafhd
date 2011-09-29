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
 
###获得应用的token
auth = OAuthHandler(APP_KEY, APP_SECRET)
###获得用户OAuth登录的url
auth_url = auth.get_authorization_url()
print "Please Auth: "+auth_url
verifier = raw_input("PIN: ").strip()
access_token = auth.get_access_token(verifier)
###获得access_token
 
###发布微博
api = API(auth)
status = api.update_status(status="Hello World")
 
###获得access_token后,以后便可以通过
###auth.set_access_token(access_token.key, access_token.secret)
###完成授权.