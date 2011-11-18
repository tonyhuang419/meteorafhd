package recursion;

public class Binarysearch {

	public int binarySearch(int arr[] , int key ){
		int beg = 0;
		int end = arr.length-1;
		while( beg <= end ){
			int mid = (beg + end) >>> 1;
			if( arr[mid] == key  )
				return mid;
			else if( arr[mid] < key )
				beg = mid+1;
			else
				end = mid-1;
		}
		return -1;
	}
	
	
	public static void main(String[] args) {
		int[] arr  = new int[] {1,2,3,4,5,6,7,8,9};
		System.out.println(new Binarysearch().binarySearch(arr, 4 ) );
	}

}
