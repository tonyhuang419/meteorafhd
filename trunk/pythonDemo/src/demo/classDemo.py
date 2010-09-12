class Person:
    def __init__(self, name):
        self.name = name
    def sayHi(self):
        print ('Hello, how are you? '+self.name)

class Dj(Person):
    def __init__(self, name):
        Person.__init__(self, name)
#    def sayHi(self):
#        print(self.name)
    def sayHi(self):
        print('...')
        Person.sayHi(self)

p = Person('jack');
#print (p)
p.sayHi()

d = Dj(p.name)
d.sayHi()

d.sex = 'male'
print(d.sex)
