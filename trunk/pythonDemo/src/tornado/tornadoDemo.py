# coding=UTF-8

from tornado import template
import os
import tornado.ioloop
import tornado.web

def getTemplatePath( ):
    return os.path.join(os.path.dirname(__file__) + "/template")

def add(x, y):
    return x + y


class MainHandler(tornado.web.RequestHandler):
    def get(self):
#        self.write("Hello, world")
        loader = template.Loader( getTemplatePath() )
        returnHtml =  loader.load("base.html").generate( message="Hello World",add=add )
        self.write(returnHtml)
        
    #modify get to post
    def write_error(self, status_code, **kwargs):
        self.write("Gosh darnit, user! You caused a %d error." % status_code)

class StoryHandler(tornado.web.RequestHandler):
    def get(self, story_id):
        who = self.get_argument('username', 'Anonymous')
        
        print self.map_by_first_letter(who)
        
        returnStr = 'Hello %s , You requested the story %s.' % ( who , story_id )
        self.write( returnStr )


#class RedirectDemo(tornado.web.RedirectHandler):
#    def get(self):
#        self.write({"url": "/"}) 

settings = {
    "static_path": os.path.join(os.path.dirname(__file__), "static"),
}
        
application = tornado.web.Application([
    (r"/", MainHandler),
    (r"/redirectDemo", tornado.web.RedirectHandler, {"url": "/"}),
    (r"/story/([0-9]+)", StoryHandler),
], **settings)

if __name__ == "__main__":
    application.listen(8888)
    tornado.ioloop.IOLoop.instance().start()
    
