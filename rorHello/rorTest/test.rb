def test()
    print "foo" + "soo\n";
    print "foo" * 2 +"\n";
    
    x= " abc"
  print "variable x is: #{x}";
  
  str = "abcde";
  print(str[0,1] +"\n") ;
  print(str[-1,1] +"\n");
  print(str[0..3]+"\n");
  
  puts 2>>1 ;
  puts 2<<1 ;
end


def regExp(str)   
    puts (str=~/^a.*z$/ ) != nil
end



def guess()
words = ['foobar', 'baz', 'quux']
secret = words[rand(3)]
print "guess? "
while guess = STDIN.gets  
    guess.chop!  
    if guess == secret
       print "You win!\n"    
       break  
    else    
       print "Sorry, you lose.\n"  
    end  
    print "guess? "
  end
  print "The word was ", secret, ".\n"
end


def ary()
  ary = [1, 2, "3"];
  puts ary[0];
  print ary[0..2];

  ary = ary + ["foo", "bar"]
  print ary[0..4];
  
  ary = ary * 2;
  puts ary[0..9];
  
  str = ary.join(":")
  puts str  
  
  ary2 = str.split(":")
  print ary2[0..9];
  puts
  print ary2[0..9];
   
 end
 
 def tesMap()
  h = {1 => 2, "2" => "4"}
  puts h[1];
  puts h["2"];
  h.delete 1;
  puts h[1];  #will output "nil"
end

tesMap();
#ary();
#regExp(STDIN.gets);
#test();
=begin
guess();
=end