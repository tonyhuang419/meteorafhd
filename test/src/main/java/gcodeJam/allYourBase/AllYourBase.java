package gcodeJam.allYourBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class AllYourBase {

	public long solve(char[] input){
		int value[] = new int[256];
		int vused;

		for (char c = 'a'; c <= 'z'; c++) {
			value[c] = 0;
		}
		for (char c = '0'; c <= '9'; c++) {
			value[c] = 0;
		}
		vused = 0;

		for (int i = 0; i < input.length; i++) {
			if (value[input[i]] == 0) {
				value[input[i]] = ++vused;
			}
		}
		if (vused <= 1) {
			vused = 2;
		}
		long ans = 0;
		for (int i = 0; i < input.length; i++) {
			long tmp = value[input[i]];
			if (tmp == 1) { } 
			else if (tmp == 2) {
				tmp = 0;
			}
			else {
				tmp--;
			}
			ans = ans*vused + tmp;
		}
		return ans;
	}


	public static void main(String[] args) throws FileNotFoundException {
		AllYourBase all = new AllYourBase();
		File  file  =  new  File("src/main/java/gcodeJam/allYourBase/test.in");
		FileReader  fr  =  new  FileReader(file);
		Scanner scanner = new Scanner(fr);
		int caseNum = scanner.nextInt();
		for(int i=0;i<caseNum;i++){
			System.out.println(all.solve(scanner.next().toCharArray()));
		}
	}

}
