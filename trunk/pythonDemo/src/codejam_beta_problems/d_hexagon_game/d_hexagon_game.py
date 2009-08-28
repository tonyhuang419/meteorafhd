#!/usr/bin/python2.5
def getPos(rows, cell):
  row = 0
  while cell > rows[row]:
    cell -= rows[row]
    row += 1
  return row, cell
def update(s1, n1, s2, n2, weight, q, marked, dist, source):
  if dist[s2][n2] is not None and dist[s1][n1] + weight >= dist[s2][n2]: return
  dist[s2][n2] = dist[s1][n1] + weight
  source[s2][n2] = n1
  if s2 < 2 and not marked[s2][n2]:
    q.append((s2, n2))
    marked[s2][n2] = True
def solve(starts, ends, values):
  n = len(starts)
  weights = [[0] * n for i in range(n)]
  for i in range(n):
    row1, col1 = starts[i]
    for j in range(n):
      row2, col2 = ends[j]
      row, c1, c2 = row1, col1, col1
      while row < row2:
        if row < (n - 1) // 2: c2 += 1
        else: c1 -= 1
        row += 1
      while row > row2:
        if row > (n - 1) // 2: c2 += 1
        else: c1 -= 1
        row -= 1
      dist = abs(row1 - row2)
      if c1 > col2: dist += c1 - col2
      if c2 < col2: dist += col2 - c2
      weights[i][j] = dist * values[i]
  match = [None] * n, [None] * n
  for i in range(n):
    q = [(0, j) for j in range(n) if match[0][j] is None]
    marked = [j is None for j in range(n)], [False] * n
    dist = ([0 if match[0][j] is None else None for j in range(n)], [None] * n,
        [None])
    source = [None] * n, [None] * n, [None]
    while q:
      side, node = q[0]
      q = q[1:]
      marked[side][node] = False
      if not side:
        for j in range(n):
          update(0, node, 1, j, weights[node][j], q, marked, dist, source)
      elif match[1][node] is None:
        update(1, node, 2, 0, 0, q, marked, dist, source)
      else:
        backnode = match[1][node]
        update(1, node, 0, backnode, -weights[backnode][node], q, marked,
            dist, source)
    while source[2][0] is not None:
      node2 = source[2][0]
      node1 = source[1][node2]
      source[2][0] = source[0][node1]
      match[0][node1] = node2
      match[1][node2] = node1
  return sum(weights[i][match[0][i]] for i in range(n))
for case in range(input()):
  starts = map(int, raw_input().split())
  values = map(int, raw_input().split())
  small_row, big_row = (len(starts) + 1) // 2, len(starts)
  rows = range(small_row, big_row) + range(big_row, small_row - 1, -1)
  starts = [getPos(rows, cell) for cell in starts]
  ans = None
  for ends in ([(small_row - 1, col + 1) for col in range(big_row)],
      [(row, min(small_row, row + 1)) for row in range(big_row)],
      [(row, min(small_row, big_row - row)) for row in range(big_row)]):
    ans2 = solve(starts, ends, values)
    if ans is None or ans > ans2: ans = ans2
  print ('Case #%s: %s') % (case + 1, ans)
