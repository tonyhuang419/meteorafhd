# coding=UTF-8

from tornado import template
import tornado.ioloop
import tornado.web

class MainHandler(tornado.web.RequestHandler):
    def get(self):
#        self.write("Hello, world")
        loader = template.Loader("D:/dev/project/pythonDemo/src/tornado/template/")
        returnHtml =  loader.load("base.html").generate(message="Hello World")
        self.write(returnHtml)

application = tornado.web.Application([
    (r"/", MainHandler),
])

if __name__ == "__main__":
    application.listen(8888)
    tornado.ioloop.IOLoop.instance().start()