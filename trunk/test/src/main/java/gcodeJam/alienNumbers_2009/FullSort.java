package gcodeJam.alienNumbers_2009;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FullSort {
	private static int NUM = 3;  

	private static void sort(List datas, List target) {  
		if (target.size() == NUM) {  
			for (Object obj : target)  
				System.out.print(obj);  
			System.out.println();  
			return;  
		}  
		for (int i = 0; i < datas.size(); i++) {  
			List newDatas = new ArrayList(datas);  
			List newTarget = new ArrayList(target);  
			newTarget.add(newDatas.get(i));  
			newDatas.remove(i);  
			sort(newDatas, newTarget);  
		}  
	}  

	public static void main(String[] args) {  
		String[] datas = new String[] { "a", "b", "c", "d" };  
		sort(Arrays.asList(datas), new ArrayList());  
	}  
}
