# coding=UTF-8

import unittest

class TruthTest(unittest.TestCase):
    def testTrue(self):
        assert True == 1

    def testFalse(self):
        assert False == 0

if __name__ == '__main__':
    unittest.main()