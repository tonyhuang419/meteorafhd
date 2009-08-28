#!/usr/bin/python2.5
def solve(items, values, pos, cost, mem):
  if len(items) == pos: return []
  if (pos, cost) in mem: return mem[pos, cost]
  ans = sorted(solve(items, values, pos + 1, cost, mem) + [items[pos]])
  if cost < values[pos]:
    ans2 = sorted(solve(items, values, pos + 1, values[pos], mem))
    if (len(ans), ans) > (len(ans2), ans2): ans = ans2
  mem[pos, cost] = ans
  return ans
for case in range(input()):
  items = raw_input().split()
  values = map(int, raw_input().split())
  ans = solve(items, values, 0, 0, {})
  print ('Case #%s: %s') % (case + 1, ' '.join(ans))
