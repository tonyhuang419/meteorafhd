# coding=UTF-8
#!/usr/bin python
'''
Created on 2011-7-30

@author: FHDone
'''

class CsrfFuckMiddleware(object):
    def process_view(self, request, callback, callback_args, callback_kwargs):
        setattr(request, '_dont_enforce_csrf_checks', True)
