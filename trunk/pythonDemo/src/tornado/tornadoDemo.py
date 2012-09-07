# coding=UTF-8

from tornado import template
import os
import tornado.ioloop
import tornado.web

def add(x, y):
    return x + y


class MainHandler(tornado.web.RequestHandler):
    def get(self):
#        self.write("Hello, world")
        loader = template.Loader("D:/dev/project/pythonDemo/src/tornado/template/")
        returnHtml =  loader.load("base.html").generate( message="Hello World",add=add )
        self.write(returnHtml)

class StoryHandler(tornado.web.RequestHandler):
    def get(self, story_id):
        self.write("You requested the story " + story_id)


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
    
