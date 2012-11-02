# coding=UTF-8

from tornado import template
import os
import tornado.ioloop
import tornado.web

#def getTemplatePath( ):
#    return os.path.join(os.path.dirname(__file__) + "/template")


def add(x, y):
    return x + y

def substring(s,beg,end):
    return s[beg:end]

class MainHandler(tornado.web.RequestHandler):
    def get(self):
        print 8883
#        self.write("Hello, world")
#        loader = template.Loader( getTemplatePath() )
#        returnHtml =  loader.load("base.html").generate( message="Hello World",add=add,substring=substring )
#        self.write(returnHtml)
#        {% autoescape None %}
        self.render("base.html",message="Hello World<script>alert(1)</script>",add=add,substring=substring)
        
    #modify get to post
    def write_error(self, status_code, **kwargs):
        self.write("Gosh darnit, user! You caused a %d error." % status_code)

#http://127.0.0.1:8888/story/1234?username=fhdone
class StoryHandler(tornado.web.RequestHandler):
    def get(self, story_id):
        who = self.get_argument('username', 'Anonymous')
#        print self.map_by_first_letter(who)
        returnStr = 'Hello %s , You requested the story %s.' % ( who , story_id )
        self.write( returnStr )

#http://127.0.0.1:8888/redirectDemo
class RedirectDemo(tornado.web.RedirectHandler):
    def get(self):
        self.write({"url": "/"}) 




settings = {
#    "static_url_prefix":"/staticx/",
#    "static_path": os.path.join(os.path.dirname(__file__), "staticx"),
    "debug":"true",
    "cookie_secret": "vZewl1YLSvCCJt1/SDovdeL55HKLHkSNh7pCZe/ref0=",
    "xsrf_cookies": True,
    "static_path": os.path.abspath(os.path.join(os.path.dirname(__file__), "../static")),
    "template_path": os.path.abspath(os.path.join(os.path.dirname(__file__), "../template")),
}
        
application = tornado.web.Application([
    (r"/", MainHandler),
    (r"/redirectDemo", tornado.web.RedirectHandler, {"url": "/"}),
#    (r"/static/(.*)", tornado.web.StaticFileHandler, dict(path=settings ['static_path']) ),
#    (r"/staticx/img/(.*)", tornado.web.StaticFileHandler, {"path":"staticx/img"} ),
    (r"/story/([0-9]+)", StoryHandler),
],  **settings)

if __name__ == "__main__":
    application.listen(8883)
    tornado.ioloop.IOLoop.instance().start()
    
