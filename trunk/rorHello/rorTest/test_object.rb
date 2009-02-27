class Mammal
  def breathe
    print "inhale and exhale\n"
  end
end


class Cat<Mammal
  def speak
    print "Meow\n"
  end
  
  
  def breathe
    puts "Sorry. I'd rather swim."
    fail "xxx"   #will appear red words,haha...the code under this will not execute
  end

end

=begin
tama = Cat.new
tama.breathe
tama.speak
=end

:xx
:$xxx
xx=1
xxx = 2
puts xx;
puts xxx;


