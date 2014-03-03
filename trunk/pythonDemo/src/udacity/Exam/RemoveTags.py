# Question 4: Remove Tags

# When we add our words to the index, we don't really want to include
# html tags such as <body>, <head>, <table>, <a href="..."> and so on.

# Write a procedure, remove_tags, that takes as input a string and returns
# a list of words, in order, with the tags removed. Tags are defined to be
# strings surrounded by < >. Words are separated by whitespace or tags. 
# You may assume the input does not include any unclosed tags, that is,  
# there will be no '<' without a following '>'.

def remove_tags(strX):
    returnList = []
    inTage = False
    wordStart = 0
    for i in range(len(strX)):
        if strX[i] == '<':
            inTage = True
            word =  strX[wordStart:i].strip()
            if len(word) > 0:
                returnList.append(word)
        elif strX[i] == '>':
            inTage = False
            wordStart = i+1
        elif i==len(strX)-1 and inTage == False:
            word =  strX[wordStart:i+1].strip()
            returnList.append(word)
        else:
            if inTage == False and strX[i]==' ' :
                word =  strX[wordStart:i].strip()
                if len(word) > 0:
                    returnList.append(word)
                wordStart = i
    return returnList         

print remove_tags('''<h1>Title</h1><p>This is a
                    <a href="http://www.udacity.com">link</a>.<p>''')
#>>> ['Title','This','is','a','link','.']

print remove_tags('''<table cellpadding='3'>
                     <tr><td>Hello</td><td>World!</td></tr>
                     </table>''')
#>>> ['Hello','World!']

print remove_tags("<hello><goodbye>")
#>>> []

print remove_tags("This is plain text.")
#>>> ['This', 'is', 'plain', 'text.']

print remove_tags("This <i>line</i> has <em>lots</em> of <b>tags</b>.")

print remove_tags("A <img src='here.img' alt='nothing'>picture</a>, a cat and a mouse!")