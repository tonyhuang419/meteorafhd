package gcodeJam.theNextNumber;

import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.Scanner;

public class MyNextNumber {


	public String compute(String numStr){
		numStr = "0"+numStr;
		int arr[] = new int[10];
		StringBuffer sb = new StringBuffer();
		for(int i=numStr.length()-1; ; i--){
			int c = numStr.charAt(i) - '0';
			++arr[c];
			for(int j=c+1;j<10;++j){
				if (arr[j] > 0) {
					sb = new StringBuffer();
					sb.append(numStr.substring(0, i)).append((char) ('0' + j));
					--arr[j];
					for (int k = 0; k < 10; ++k) {
						while (arr[k] > 0) {
							sb.append((char) ('0' + k));
							--arr[k];
						}
					}
					return sb.toString();
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		MyNextNumber my = new MyNextNumber();
		PrintStream output = System.out;
		output = new PrintStream("src/main/java/gcodeJam/theNextNumber/out.in");
		File  file  =  new  File("src/main/java/gcodeJam/theNextNumber/B-large-practice.in");
		FileReader  fr  =  new  FileReader(file);
		Scanner scanner = new Scanner(fr);
		int caseNum = scanner.nextInt();
		scanner.nextLine();
		for(int i=0;i<caseNum;i++){
			String numStr = scanner.nextLine();
			//output.println(numStr);
			
			output.println("Case #"+(i+1)+": "+new BigInteger(my.compute(numStr)));
		}
		fr.close();
		scanner.close();
	}
}
