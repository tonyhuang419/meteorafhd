class Person:
    def __init__(self, name):
        self.name = name
    def sayHi(self):
        print ('Hello, how are you? '+self.name)
    def delegeateX(self):
        self.action()
    def printMes( self , message):
        print('unbound '+message)
    @staticmethod
    def staticMethod():
        print('i am a static method')

Person.staticMethod()

class Play:
    def play(self ):
        self.action()

class PlayFootball( Play ):      
    def play(self ):
        print('play football')

class decorator:
    print('__ in decorator class ___')
    def __init__(self , func ):
        print('__ init decorate ___')
        self.func = func
    def __call__( self ):
        print('__ method has be decorated ___')
        self.func()
        
@decorator
def x():
    print('--111--') 
x()

@decorator
class decoClass:
    pass

class Dj(Person):
    playFootball = PlayFootball()
    def __init__(self, name):
        Person.__init__(self, name)
#    def sayHi(self):
#        print(self.name)
    def sayHi(self):
        Person.sayHi(self)
#    @decorator , change "self.func()" to "self.func(self)"
    def delegeateX(self):
        print('implements abstract method')

print('---------')
p = Person('jack');
#print (p)
p.sayHi()
p.printMes("....")
printMes = p.printMes
printMes(',,,,')


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
d.playFootball.play()


