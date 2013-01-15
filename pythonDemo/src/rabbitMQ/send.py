# -*- coding: utf-8 -*-
#!/usr/bin/env python

#http://www.rabbitmq.com/tutorials/tutorial-two-python.html
#http://www.01happy.com/python-rabbitmq-work-queues/
#http://stackoverflow.com/questions/13517955/no-handlers-could-be-found-for-logger-pika-adapters-blocking-connection

import pika
import sys

connection = pika.BlockingConnection(pika.ConnectionParameters( host='localhost'))
channel = connection.channel()
#channel.exchange_declare(exchange='hello')  
#channel.queue_declare(queue='hello')
channel.exchange_declare(exchange='hello_durable', durable=True)
channel.basic_publish(exchange='', routing_key='hello', body='Hello World!',
                       properties=pika.BasicProperties(
                         delivery_mode = 2, # make message persistent
                      ))
print " [x] Sent 'Hello World!'"


message = '.'.join(sys.argv[1:]) or "Hello World!"
channel.basic_publish(exchange='',
                      routing_key='hello', 
                      body=message,
                      properties=pika.BasicProperties(
                         delivery_mode = 2, # make message persistent
                      ))
print " [x] Sent %r" % (message,)
connection.close()