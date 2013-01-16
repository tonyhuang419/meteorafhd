# -*- coding: utf-8 -*-
#!/usr/bin/env python

#http://www.rabbitmq.com/tutorials/tutorial-two-python.html
#http://www.01happy.com/python-rabbitmq-work-queues/

import pika

connection = pika.BlockingConnection(pika.ConnectionParameters( host='localhost'))
channel = connection.channel()
channel.queue_declare(queue='hellox', durable=True)
print ' [*] Waiting for messages. To exit press CTRL+C'

#def callback(ch, method, properties, body):
#    print " [x] Received %r" % (body,)

import time
def callback(ch, method, properties, body):
    print "Received %r" % (body,)
    time.sleep( body.count('.') )
    print "Done"

channel.basic_qos(prefetch_count=1)
channel.basic_consume(callback, queue='hello', no_ack=True)
channel.start_consuming()