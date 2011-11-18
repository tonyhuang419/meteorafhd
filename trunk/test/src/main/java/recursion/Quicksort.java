package recursion;

import java.util.Arrays;

public class Quicksort {

	public void swap( int[] arr, int i , int j){
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	public void qsort1(int[] arr , int beg , int end ){
		if( end > beg ){
			int pivot = arr[end];
			int index = beg;
			for( int i=beg; i<end; ++i ){
				if (  arr[i] < pivot ){
					swap( arr , index++ , i );
				}
			}
			swap( arr , index , end );
			qsort1( arr , beg , index-1 );
			qsort1( arr , index+1 , end );
		}
	}

	public void runQSort(int[] arr){
		qsort1(arr,0,arr.length-1);
		System.out.println(Arrays.toString(arr));
	}

	public static void main(String[] args) {
		int[] arr  = new int[] {1,2,9,7,6,5,3,8};
		new Quicksort().runQSort( arr );

	}

}
