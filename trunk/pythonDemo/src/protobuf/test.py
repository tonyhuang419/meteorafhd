#-*- coding:utf-8 -*-
import test_pb2
import time

#压缩
test = test_pb2.TestMsg()
test.id=1
test.time=int(time.time())
test.note="asdftest"
print test
test_str = test.SerializeToString()
print test_str

#解压
test1 = test_pb2.TestMsg()
test1.ParseFromString(test_str)
print test1