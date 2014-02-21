# Double Gold Star

# Khayyam Triangle

# The French mathematician, Blaise Pascal, who built a mechanical computer in
# the 17th century, studied a pattern of numbers now commonly known in parts of
# the world as Pascal's Triangle (it was also previously studied by many Indian,
# Chinese, and Persian mathematicians, and is known by different names in other
# parts of the world).

# The pattern is shown below:

#                    1
#                   1 1
#                  1 2 1
#                 1 3 3 1
#                1 4 6 4 1
#                   ...

# Each number is the sum of the number above it to the left and the number above
# it to the right (any missing numbers are counted as 0).

# Define a procedure, triangle(n), that takes a number n as its input, and
# returns a list of the first n rows in the triangle. Each element of the
# returned list should be a list of the numbers at the corresponding row in the
# triangle.


# def triangle(n):
#     triangleList = []
#     for i in range(0,n+1):
#         if i == 1:
#             triangleList.append([1])
#         elif i == 2:
#             triangleList.append([1,1])
#         elif i>2:
#             newList = triangleList[-1][:] + [1]
#             for j in range(1 , len(newList)-1 ):
#                 newList[j] = triangleList[-1][j-1] + triangleList[-1][j]
#             triangleList.append( newList )
#     return triangleList
           
# def triangle(n):
#     ret = [[1], [1,1]]                          #initialize with first 2 rows
#     while len(ret) < n:                         #until we have all rows we want
#         new = [1] * (len(ret[-1])+1)            #create new row with just 1s
#         for i in range(1, len(new)-1):      
#             new[i] = ret[-1][i-1] + ret[-1][i]  #modify newly created row as sums
#         ret.append(new)                         # of previous row
#     return ret[0:n]                             #return partial list, if n==0 or n==1


# def triangle(n):
#     triangleList = []
#     for i in range (0,n): 
#         nList = [ (fact(i)/(fact(j)*fact(i-j))) for j in range (0,i+1) ]
#         triangleList.append(nList)
#     return triangleList
# 
# def fact(n , acc=1):
#     if n==0:
#         return acc
#     else:
#         return fact(n-1 , acc*n)


def triangle(n):
    """Prints out n rows of Pascal's triangle.
    It returns False for failure and True for success."""
    row = [1]
    k = [0]
    for x in range(max(n,0)):
        print row
        row=[l+r for l,r in zip(row+k,k+row)]
  

#For example:
print triangle(0)
#>>> []

print triangle(1)
#>>> [[1]]

print triangle(2)
#>> [[1], [1, 1]]

print triangle(3)
#>>> [[1], [1, 1], [1, 2, 1]]

print triangle(6)
#>>> [[1], [1, 1], [1, 2, 1], [1, 3, 3, 1], [1, 4, 6, 4, 1], [1, 5, 10, 10, 5, 1]]
