#!/usr/bin/python2.4
f_max = [[1], [2, 3], [3, 6, 7]]
def getFMax(d, b):
  if not d or not b: return 0
  if d <= len(f_max):
    row = f_max[d - 1]
    if min(b, d) <= len(row): f = row[min(b, d) - 1]
    else: return -1
  elif b == 1: return d
  elif b == 2: f = d * (d + 1) // 2
  else: return -1
  if f < 2**32: return f
  return -1
def getMin(f, getFMax):
  x, y = 0, f
  while y - x > 1:
    z = (x + y) // 2
    if getFMax(z) == -1 or f <= getFMax(z): y = z
    else: x = z
  return y
def main():
  while len(f_max[-1]) > 2:
    a = f_max[-1]
    b = [a[0]+1] + [a[i]+a[i+1]+1 for i in range(len(a)-1)] + [a[-1]*2+1]
    if b[-1] >= 2**32:
      while b[-1] >= 2**32: b.pop()
      b.append(2**32)
    f_max.append(b)
  for case in range(input()):
    f, d, b = map(int, raw_input().split())
    print ('Case #%s: %s %s %s') % (case + 1, getFMax(d, b),
        getMin(f, lambda d: getFMax(d, b)), getMin(f, lambda b: getFMax(d, b)))
main()