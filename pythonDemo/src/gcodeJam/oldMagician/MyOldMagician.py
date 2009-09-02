'''
OldMagician.py
'''

def analyze( white , black ):
    whiteZero = white%2;
    blackZero = black%2;
    
    result = whiteZero ^ 0;
    
    if ( result==1 ):
        return "WHITE";
    elif ( result==0):
        return "BLACK";
    else:
        return "UNKNOWN";
    
print( analyze( 2 , 2) )
print( analyze( 3 , 1) )
print( analyze( 3 , 6) )

#f = open('A-large-practice.in');
#expNum = f.readline();
#expNum = int(expNum);
#for i in range(0, expNum):
#    line = f.readline();
#    items = line.split(' ');
#    result = analyze(int(items[0]),int(items[1]));
#    print( 'Case #'+str(i+1)+': '+ result);
#f.close();