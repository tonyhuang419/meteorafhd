def testCase(n)
  case n
    when 1, 2..5
    print "1..5\n"
    when 6..10
    print "6..10\n"
  end
end

testCase(1);
testCase(3);
testCase(7);
testCase(11);

# "===" is express in the interval
puts (2..5) === 1
puts (2..5)  === 3 

