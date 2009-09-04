package gcodeJam.juice;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * not complete
 */
public class MyJuice {

	public static final int MAXN = 5000;
	public static final int TOTAL = 10000;


	public int compute(List<Juice> juiceList){
		int ans = 0;
		for (int a = 0; a <= TOTAL; a++) {
			int[] changes = new int[TOTAL-a+2];
			for (int i = 0; i < juiceList.size(); i++) {
				if ( juiceList.get(i).a <= a 
						&& juiceList.get(i).b + juiceList.get(i).c <= TOTAL - a) {
					changes[juiceList.get(i).b]++;
					changes[TOTAL - a - juiceList.get(i).c + 1]--;
				}
			}
			int cur = 0;
			for (int b = 0; b <= TOTAL - a; b++) {
				cur += changes[b];
				ans = Math.max(ans, cur);
			}
		}
		return ans;
	}


	public static void main(String[] args) throws Exception{
		MyJuice myJuice = new MyJuice();
		File  file  =  new  File("src/main/java/gcodeJam/juice/A-small-practice.in");
		FileReader  fr  =  new  FileReader(file);
		Scanner scanner = new Scanner(fr);
		int count = scanner.nextInt();
		for(int i=0;i<count;i++){
			int peopleNum = scanner.nextInt();
			List<Juice> juiceList = new ArrayList<Juice>();
			for(int j=0;j<peopleNum;j++){
				int a = scanner.nextInt();
				int b = scanner.nextInt();
				int c = scanner.nextInt();
				//System.out.println(a +"/"+b+"/"+c);
				juiceList.add(new Juice(a,b,c));
			}
			int x = i+1;
			System.out.println("Case #"+x+": "+myJuice.compute(juiceList));
		}



	}
}
