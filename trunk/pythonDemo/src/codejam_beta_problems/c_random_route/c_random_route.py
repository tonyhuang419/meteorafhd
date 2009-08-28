#!/usr/bin/python2.5
import heapq
for case in range(input()):
  num_roads, start = raw_input().split()
  num_roads = int(num_roads)
  roads = {}
  for road in range(num_roads):
    city1, city2, time = raw_input().split()
    roads.setdefault(city1, []).append((city2, int(time), road))
  ans = [0.0] * num_roads
  dists = {start: [0, start]}
  sources = {start: set()}
  heap = [dists[start]]
  counts = {start: 1}
  odds = {}
  while heap:
    dist, pos = heapq.heappop(heap)
    odds[pos] = [0.0] * num_roads
    for city1, road in sources[pos]:
      odds[pos][road] += float(counts[city1]) / counts[pos]
      for i in range(num_roads):
        odds[pos][i] += odds[city1][i] * counts[city1] / counts[pos]
    for i in range(num_roads): ans[i] += odds[pos][i]
    dirty = False
    for city2, time, road in roads.get(pos, ()):
      if city2 not in dists:
        dists[city2] = [dist + time, city2]
        heap.append(dists[city2])
        counts[city2] = 0
        dirty = True
      elif dists[city2][0] < dist + time: continue
      elif dists[city2][0] > dist + time:
        dists[city2][0] = dist + time
        sources[city2] = set()
        counts[city2] = 0
        dirty = True
      sources.setdefault(city2, set()).add((pos, road))
      counts[city2] += counts[pos]
    if dirty: heapq.heapify(heap)
  print ('Case #%s: %s') % (case + 1,
      ' '.join('%.7f' % (i / (len(odds) - 1)) for i in ans))
