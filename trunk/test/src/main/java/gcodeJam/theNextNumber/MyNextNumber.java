package gcodeJam.theNextNumber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Scanner;

public class MyNextNumber {


	public int compute(String num){
		int len = num.length();

		int minIndex = 0;
		int min = 0;
		for(int i=len-1;i>=0;i--){
			if ( num.charAt(i)!='0'){
				minIndex = i;
				min = Integer.valueOf(num.charAt(i)+"");
				break;
			}
		}
		

		for(int i=len-2;i>=0;i--){
			int next = Integer.valueOf( num.charAt(i+1)+"" );
			int pre = Integer.valueOf( num.charAt(i)+"" );
			if(next>pre && pre<min){
				String s = num.substring(0,i)
				+min
				+num.substring(i,minIndex)
				+num.substring(minIndex+1,len);
				return Integer.valueOf(s);
			}
			else if(min>pre ){
				min = Integer.valueOf(next);
				minIndex = i+1;
			}
		}
		return Integer.valueOf(num);
	}

	public static void main(String[] args) throws FileNotFoundException {
		MyNextNumber my = new MyNextNumber();
		PrintStream output = System.out;
		output = new PrintStream("src/main/java/gcodeJam/theNextNumber/out.in");
		File  file  =  new  File("src/main/java/gcodeJam/theNextNumber/test.in");
		FileReader  fr  =  new  FileReader(file);
		Scanner scanner = new Scanner(fr);
		int caseNum = scanner.nextInt();
		for(int i=0;i<caseNum;i++){
			String numStr = scanner.nextInt()+"";
			//			output.println(numStr);
			int x= i+1;
			int r = my.compute(numStr);
			int sign=0;
			if(numStr.equals(r+"")){
				for(int j=numStr.length()-2;j>=0;j--){
					int next = Integer.valueOf( numStr.charAt(j+1)+"" );
					int pre = Integer.valueOf( numStr.charAt(j)+"" );
					if(next<pre){
						numStr = numStr.substring(0,j)+"0"+numStr.substring(j,numStr.length());
						output.println("Case #"+x+": "+numStr);
						sign=1;
						break;
					}
				}
				if(sign==0){
					output.println("Case #"+x+": "+numStr+"0");
				}
			}
			else{
				output.println("Case #"+x+": "+r);
			}
		}
	}
}
