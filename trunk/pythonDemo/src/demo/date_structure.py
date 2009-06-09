# This is my shopping list
shoplist = ['apple', 'mango', 'carrot', 'banana']

print ('I have', len(shoplist),'items to purchase.')

print ('These items are:',) # Notice the comma at end of the line
for item in shoplist:
    print(item,)

print ('\nI also have to buy rice.')
shoplist.append('rice')
print ('My shopping list is now', shoplist)

print ('I will sort my list now')
shoplist.sort()
print ('Sorted shopping list is', shoplist)

print ('The first item I will buy is', shoplist[0])
olditem = shoplist[0]
del shoplist[0]
print ('I bought the', olditem)
print ('My shopping list is now', shoplist)

print ('-------------------------------------------')
zoo = ('wolf', 'elephant', 'penguin')
print ('Number of animals in the zoo is', len(zoo))

new_zoo = ('monkey', 'dolphin', zoo)
print ('Number of animals in the new zoo is', len(new_zoo))
print ('All animals in new zoo are', new_zoo)
print ('Animals brought from old zoo are', new_zoo[2])
print ('Last animal brought from old zoo is', new_zoo[2][2] )

print ('------------------MAP----------------------')
# 'ab' is short for 'a'ddress'b'ook
ab = { 'Swaroop'   : 'swaroopch@byteofpython.info',
       'Larry'     : 'larry@wall.org',
       'Matsumoto' : 'matz@ruby-lang.org',
       'Spammer'   : 'spammer@hotmail.com' }

print ("Swaroop's address is %s" % ab['Swaroop'])

# Adding a key/value pair
ab['Guido'] = 'guido@python.org'

# Deleting a key/value pair
del ab['Spammer']

print ('\nThere are %d contacts in the address-book\n' % len(ab))
for name, address in ab.items():
    print ('Contact %s at %s' % (name, address))

if 'Guido' in ab: # OR ab.has_key('Guido')
    print ("\nGuido's address is %s" % ab['Guido'] )
    
    
print ('----------------------------------------')
shoplist = ['apple', 'mango', 'carrot', 'banana']

# Indexing or 'Subscription' operation
print ('Item 0 is', shoplist[0])
print ('Item 1 is', shoplist[1])
print ('Item 2 is', shoplist[2])
print ('Item 3 is', shoplist[3])
print ('Item -1 is', shoplist[-1])
print ('Item -2 is', shoplist[-2])

# Slicing on a list
print ('Item 1 to 3 is', shoplist[1:3])
print ('Item 2 to end is', shoplist[2:])
print ('Item 1 to -1 is', shoplist[1:-1])
print ('Item start to end is', shoplist[:])

# Slicing on a string
name = 'swaroop'
print ('characters 1 to 3 is', name[1:3])
print ('characters 2 to end is', name[2:])
print ('characters 1 to -1 is', name[1:-1])
print ('characters start to end is', name[:])

strx = "123456";
print (strx[1:3])

print('--------------------------------')
name = 'Swaroop' # This is a string object 

if name.startswith('Swa'):
    print ('Yes, the string starts with "Swa"')

if 'a' in name:
    print ('Yes, it contains the string "a"')

if name.find('war') != -1:
    print ('Yes, it contains the string "war"')

delimiter = '_*_'
mylist = ['Brazil', 'Russia', 'India', 'China']
print (delimiter.join(mylist) )
