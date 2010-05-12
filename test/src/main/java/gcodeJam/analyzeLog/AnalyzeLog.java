package gcodeJam.analyzeLog;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * 宝钢国旅差旅项目用
 */
public class AnalyzeLog {

	public static void main(String[] args) {
		try {
			InputStream input = new FileInputStream("src/main/java/gcodeJam/analyzeLog/server.log");
			PrintStream output = new PrintStream("src/main/java/gcodeJam/analyzeLog/serverOut.log");
			Scanner scan = new Scanner(input);
			while(scan.hasNext()){
				String str = scan.nextLine();
				if(str.indexOf(".do")!=-1){
					System.out.println(str);
					output.printf("%s\n",str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
