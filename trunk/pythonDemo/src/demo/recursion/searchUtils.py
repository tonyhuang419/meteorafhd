
def binarySearch( arr , key ):
    beg = 0
    end = len(arr) -1
    while beg <= end:
        mid = (beg+end)>>1
        if arr[mid] == key :  return mid
        elif arr[mid] < key : beg = mid+1
        elif arr[mid] > key : end = mid-1
    return -key

def bubblSearch( arrs ):
    for i in range(len(arrs)):
            j = i
            for j in range(len(arrs)):
                if arrs[i] < arrs[j] :
                    temp = arrs[i]
                    arrs[i] = arrs[j]
                    arrs[j] = temp  
    
def qsort(L):
    if not L: return []
    return qsort([x for x in L[1:] if x< L[0]]) + L[0:1] + \
           qsort([x for x in L[1:] if x>=L[0]])


if __name__ ==  "__main__":
    arr = [1,2,3,4,9,6,7,8,0]
    #print ( qsort(arr) )
    print ( bubblSearch(arr) )  
    print(arr)  
    #print ( binarySearch(arr, 5) )  
        
