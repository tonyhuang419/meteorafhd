#http://joy2everyone.iteye.com/blog/949561

from tornado.httpclient import AsyncHTTPClient
import logging
import tornado.httpserver
import tornado.ioloop
import tornado.web
import tornado.gen



class AsyGenHandler(tornado.web.RequestHandler):
   
    @tornado.web.asynchronous
    @tornado.gen.engine
    def get(self):
        client = tornado.httpclient.AsyncHTTPClient()
        response = yield tornado.gen.Task(client.fetch, "http://www.baidu.com/")
        self.write("Hello World")
#        print(response.body)
        self.write(response.body.decode('gb2312').encode('utf-8'))
        self.finish()
            
            
class AsyncMainHandler(tornado.web.RequestHandler):
    def get(self):
        '''
        The below is only a testing which will be exected to kill time,
        and then we can find the asynchronous effect from the front page.
        '''
        for i in range(1, 100000):
            print "kill time"
        self.write("hello")

class MainHandler(tornado.web.RequestHandler):

    @tornado.web.asynchronous
    def get(self):
        http = tornado.httpclient.AsyncHTTPClient()
        http.fetch("http://localhost:8083/async-sync-test/", callback=self._test_callback)
        self.write("Hello to the Tornado world! ")
        '''
        Flushes the current output buffer to the network.
        '''
        self.flush()

    '''
    _test_callback is a callback function used for the processing of the response from the async request
    '''
    def _test_callback(self, response):
        self.write(response.body)
        '''
        refer the offical document, we are responsible for the invocation of the finish function in the async case.
        '''
        self.finish()

settings = {
    #"debug": True,
}

application = tornado.web.Application([
    (r"/", MainHandler),
    (r"/async-sync-test/", AsyncMainHandler),
    (r"/asygen/", AsyGenHandler),
], **settings)

if __name__ == "__main__":

    http_server = tornado.httpserver.HTTPServer(application)
    http_server.listen(8083)
    tornado.ioloop.IOLoop.instance().start()

