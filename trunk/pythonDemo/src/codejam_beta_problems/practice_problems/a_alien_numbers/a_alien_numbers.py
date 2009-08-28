#!/usr/bin/python2.4
def main():
  for case in range(input()):
    alien_number, source, target = raw_input().split()
    digit_map = dict(zip(source, range(len(source))))
    number = 0
    for digit in alien_number:
      number = number * len(source) + digit_map[digit]
    digits = []
    while number:
      digits.append(target[number % len(target)])
      number //= len(target)
    print ('Case #%s: %s') % (case + 1, ''.join(digits[::-1]))
main()