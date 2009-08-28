#!/usr/bin/python2.5
for case in range(input()):
  x1, y1, x2, y2, x3, y3 = map(int, raw_input().split())
  if (x1 - x2) * (y1 - y3) == (x1 - x3) * (y1 - y2):
    w1, w2 = 'not', 'a'
  else:
    s1 = (x1 - x2) ** 2 + (y1 - y2) ** 2
    s2 = (x1 - x3) ** 2 + (y1 - y3) ** 2
    s3 = (x2 - x3) ** 2 + (y2 - y3) ** 2
    s1, s2, s3 = sorted((s1, s2, s3))
    w1 = 'isosceles' if s2 in (s1, s3) else 'scalene'
    if s1 + s2 > s3: w2 = 'acute'
    elif s1 + s2 < s3: w2 = 'obtuse'
    else: w2 = 'right'
  print ('Case #%s: %s %s triangle') % (case + 1, w1, w2)
