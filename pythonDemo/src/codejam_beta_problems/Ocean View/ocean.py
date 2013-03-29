from __future__ import print_function, division


#https://code.google.com/codejam/contest/2334486/dashboard#s=p2
from bisect import bisect_left
import sys

infile = open('C-small-practice.in')
outfiles = (sys.stdout, open('C-small-practice.out', 'w'))

def read_in(infile):
    data = infile.readlines()
    amount = int(data[0])
    content = data[2::2]
    assert amount == len(content)
    return [s.strip() for s in content]

def do_output(number, output, outfiles):
    for f in outfiles:
        print('Case #%d: ' % number, output, file=f)

def main():
    for i, content in enumerate(read_in(infile)):
        do_output(i+1, do_task(content), outfiles)

def LongestIncreasingSubsequence(S):
    """LongestIncreasingSubsequence.py

    Find longest increasing subsequence of an input sequence.
    D. Eppstein, April 2004

    Part of PADS (public domain)

    http://www.ics.uci.edu/~eppstein/PADS/LongestIncreasingSubsequence.py

    Find and return longest increasing subsequence of S.
    If multiple increasing subsequences exist, the one that ends
    with the smallest value is preferred, and if multiple
    occurrences of that value can end the sequence, then the
    earliest occurrence is preferred.
    """

    # The main data structures: head[i] is value x from S that
    # terminates a length-i subsequence, and tail[i] is either
    # None (if i=0) or the pair (head[i-1],tail[i-1]) as they
    # existed when x was processed.
    head = []
    tail = [None]

    for x in S:
        i = bisect_left(head,x)
        if i >= len(head):
            head.append(x)
            if i > 0:
                tail.append((head[i-1],tail[i-1]))
        elif head[i] > x:
            head[i] = x
            if i > 0:
                print (tail[i])
                tail[i] = head[i-1],tail[i-1]
                print (tail[i])

    if not head:
        return []

    output = [head[-1]]
    pair = tail[-1]
    print (pair)
    while pair:
        x,pair = pair
        output.append(x)

    output.reverse()
    return output


def do_task(content):
    # Parse input string
    heights = [int(x) for x in content.split()]
    return (len(heights) - len(LongestIncreasingSubsequence(heights)))

if __name__=='__main__':
    main()
