# coding=UTF-8


def log(func): 
    def wrapper(*args, **kw): 
        print(args)
        print(kw)
        print 'enter', func.__name__ 
        func(*args, **kw) 
        print 'exit', func.__name__ 
    wrapper.__name__ = func.__name__ 
    # wrapper.__globals__ = func.__globals__ 
    return wrapper 

@log 
def test(a, b): 
    pass 


test('a','b')
