package gcodeJam.whatAreBirds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MyWhatAreBirds {


	public static void main(String[] args) throws FileNotFoundException {
		int minIsBirdH=0;
		int maxIsBirdH=0;
		int minIsBirdW=0;
		int maxIsBirdW=0;
		int maxIsNotBirdH=0;
		int minIsNotBirdH=0;
		int maxIsNotBirdW=0;
		int minIsNotBirdW=0;
		
		File  file  =  new  File("src/main/java/gcodeJam/whatAreBirds/test.in");
		FileReader  fr  =  new  FileReader(file);
		Scanner scanner = new Scanner(fr);
		int caseNum = scanner.nextInt();
		Pattern pattern = Pattern.compile(".*",Pattern.MULTILINE);
		for(int i=0;i<caseNum;i++){
			int num = scanner.nextInt();
			for(int j=0;j<num;j++){
				int h = scanner.nextInt();
				int w = scanner.nextInt();
				String s = scanner.next(pattern);
				System.out.print(h+"/"+w+"/"+s);
			}
			System.out.println("-------");
		}

	}

}
