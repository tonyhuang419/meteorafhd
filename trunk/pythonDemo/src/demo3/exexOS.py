#coding=utf-8

# import os
# print os.system("dir")

# import subprocess
# print subprocess.call(["dir"],shell=True)

# gt_lt_str = "<><><>gt lt str<><><>"
# print gt_lt_str.strip("><")

import re
p = re.compile(r'a')


print p.match('aaaaaaa').group()

print p.findall('aaaaaa')