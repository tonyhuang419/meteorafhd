package gcodeJam.WelcomeToCodeJam;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.lang.StringUtils;


public class MyWelcomeToCodeJam {

	public static final String wel = "welcome to code jam";

	public int complute(String str){
		int[] count = new int[20];
		count[0] = 1;
		for(int i = 0; i < str.length(); i++){
			for(int j = wel.length()-1; j >= 0; j--){
				if(wel.charAt(j) == str.charAt(i)){
					count[j+1] = (count[j+1] + count[j])%10000;
				}
			}
		}
		return count[19];
	}


	public static void main(String[] args) throws Exception{
		MyWelcomeToCodeJam cj = new MyWelcomeToCodeJam();

		File  file  =  new  File("src/main/java/gcodeJam/WelcomeToCodeJam/C-small-practice.in");
		FileReader  fr  =  new  FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		int count = new Integer(line);
		for(int i=0;i<count;i++){
			line = br.readLine();
			int result = cj.complute(line);
			int x = i+1;
			System.out.println("Case #"+x+": "+StringUtils.leftPad(result+"", 4, "0"));
		}

	}

}
