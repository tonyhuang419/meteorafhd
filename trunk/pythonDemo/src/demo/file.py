#!/usr/bin/python
# Filename: using_file.py

poem = '''\
Programming is fun
When the work is done
if you wanna make your work also fun:
        use Python!
'''
try:
    f = file('poem.txt', 'w') # open for 'w'riting
    f.write(poem) # write text to file
    f.close() # close the file
    
    f = file('poem.txt')
    # if no mode is specified, 'r'ead mode is assumed by default
    while True:
        line = f.readline()
        print( len(line) );
        if len(line) == 0: # Zero length indicates EOF
           # can get null when finish the file
           # line = f.readline()
           # print (line+".....")
           # line = f.readline()
           # print (line+".....")
            break
        print (line,)
        # Notice comma to avoid automatic newline added by Python
    f.close() # close the file 
except Exception as ex: 
    print ('\nSome error/exception occurred.')
    print ( ex );
    # here, we are not exiting the program
finally:
     print ('finally')

print ('Done') 
