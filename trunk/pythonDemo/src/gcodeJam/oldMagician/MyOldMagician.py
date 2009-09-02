'''
OldMagician.py
'''

def analyze( white , black ):
    blackZero = black%2;
    blackToWhite = black/2;
    sumWhite = white + int(blackToWhite);
    if ( blackZero==1 ):
        return "BLACK";
    elif ( blackZero==0 and sumWhite%2==0 ):
        return "WHITE";
    else:
        return "UNKNOWN";
    

print( analyze( 3 , 1) )
print( analyze( 3 , 6) )

f = open('A-large-practice.in');
expNum = f.readline();
expNum = int(expNum);
for i in range(0, expNum):
    line = f.readline();
    items = line.split(' ');
    result = analyze(int(items[0]),int(items[1]));
    print( 'Case #'+str(i+1)+': '+ result);
f.close();