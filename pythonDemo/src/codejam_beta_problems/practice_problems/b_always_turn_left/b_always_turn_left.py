#!/usr/bin/python2.4
def main():
  for case in range(input()):
    paths = raw_input().split()
    row = col = max_row = min_col = max_col = 0
    row_offset, col_offset = 1, 0
    for path in paths:
      for move in path[1:-1]:
        if move == 'L': row_offset, col_offset = -col_offset, row_offset
        elif move == 'R': row_offset, col_offset = col_offset, -row_offset
        else:
          row, col = row + row_offset, col + col_offset
          max_row = max(row, max_row)
          min_col, max_col = min(col, min_col), max(col, max_col)
      row_offset, col_offset = -row_offset, -col_offset
    rows, cols = max_row + 1, max_col - min_col + 1
    maze = [[{(-1, 0): 0, (0, -1): 0, (0, 1): 0, (1, 0): 0}
        for col in range(cols)] for row in range(rows)]
    row, col, row_offset = 0, -min_col, 1
    for path in paths:
      for move in path[1:]:
        if move == 'L': row_offset, col_offset = -col_offset, row_offset
        elif move == 'R': row_offset, col_offset = col_offset, -row_offset
        else:
          maze[row][col][row_offset, col_offset] = 1
          row, col = row + row_offset, col + col_offset
      row_offset, col_offset = -row_offset, -col_offset
      row, col = row + row_offset, col + col_offset
    print ('Case #%s:') % (case + 1)
    for row in maze:
      line = []
      for room in row:
        index = -1
        if room[-1, 0]: index += 1
        if room[1, 0]: index += 2
        if room[0, -1]: index += 4
        if room[0, 1]: index += 8
        line.append('123456789abcdef'[index])
      print ('').join(line)
main()