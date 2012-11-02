#coding=utf-8

def add(op1, op2):
    return op1+op2;
 
def sub(op1, op2):
    return op1-op2;


import unittest 
 
class MathTestCase(unittest.TestCase):
    def setUp(self):
        print 'setup'
        # pass

    def tearDown(self):
        print 'tearDown'
        # pass

    def testAdd(self):
        self.assertEqual(add(4, 5), 9)

    def testSub(self):
        self.assertEqual(sub(8, 5), 3)

    def suite():
        suite = unittest.TestSuite()
        suite.addTest(MathTestCase())
        return suite
 
if __name__ == "__main__":
    unittest.main()