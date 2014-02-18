from fabric.api import *

def hello():
    print("Hello world!")

def dirTest():
    with cd('C'):
    	local('dir')
    from fabric.api import green,red
    print (red("This sentence is red, except for " + green("these words, which are green") + "."))

    
