package def;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BitmapTest {

	    public static int[] getIntersect(int[] list1, int[] list2) {
	        BitSet bs = new BitSet();
	        Set<Integer> set = new HashSet<Integer>();

	        for (int i = 0; i < list1.length; i++)
	            bs.set(list1[i]);

	        for (int i = 0; i < list2.length; i++)
	            if (bs.get(list2[i]))
	                set.add(list2[i]);

	        int[] ret = new int[set.size()];
	        int count = 0;

	        for (Iterator<Integer> it = set.iterator(); it.hasNext();)
	            ret[count++] = it.next();
	        return ret;
	    }
	    
	    public static void getOrder(int[] list1) {
	        BitSet bs = new BitSet();

	        for (int i = 0; i < list1.length; i++)
	            bs.set(list1[i]);

	        List<Integer> list = new ArrayList<Integer>();
	        for( int i=0;i<bs.length();i++)
	           if( bs.get(i) ) 
	        	   list.add(i);
	           
	        for (Iterator<Integer> it = list.iterator(); it.hasNext();)
	            System.out.println(it.next());
	    }

	    public static void main(String[] args) {
	        int[] list1 = { 1, 3, 5, 7, 9, 13, 18, 20, 300 };
	        int[] list2 = { 2, 3, 6, 7, 10, 11, 18, 21, 3, 300 };
	        
	        System.out.println("order list1");
	        getOrder(list1);
	        System.out.println("order list2");
	        getOrder(list2);
	        
	        int[] list3 = getIntersect(list1, list2);
	        System.out.println(Arrays.toString(list3));
	    }
	

}
