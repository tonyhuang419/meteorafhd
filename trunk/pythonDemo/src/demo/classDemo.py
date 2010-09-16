class Person:
    def __init__(self, name):
        self.name = name
    def sayHi(self):
        print ('Hello, how are you? '+self.name)
    def delegeateX(self):
        self.action()

class Dj(Person):
    def __init__(self, name):
        Person.__init__(self, name)
#    def sayHi(self):
#        print(self.name)
    def sayHi(self):
        Person.sayHi(self)
    def delegeateX(self):
        print('implements abstract method')

p = Person('jack');
#print (p)
p.sayHi()

d = Dj(p.name)
d.sayHi()

print('------------------')
d.name = 'sub'
p.sayHi()
d.sayHi()
print('------------------')
p.name ='super'
p.sayHi()
d.sayHi()
print('------------------')

d.delegeateX()

d.sex = 'male'
print(d.sex)
