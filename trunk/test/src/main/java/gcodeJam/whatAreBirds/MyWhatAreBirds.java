package gcodeJam.whatAreBirds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MyWhatAreBirds {

	public static void main(String[] args) throws FileNotFoundException {
		int minIsBirdH=1000000;
		int maxIsBirdH=0;
		int minIsBirdW=1000000;
		int maxIsBirdW=0;
		int[] hh = new int[1000000];
		int[] ww = new int[1000000];

		File  file  =  new  File("src/main/java/gcodeJam/whatAreBirds/test.in");
		FileReader  fr  =  new  FileReader(file);
		Scanner scanner = new Scanner(fr);
		int caseNum = scanner.nextInt();
		for(int i=0;i<caseNum;i++){
			int count=i+1;
			System.out.println("Case #"+count+":");
			int num = scanner.nextInt();
			int cur=0;
			for(int j=0;j<num;j++){
				int h = scanner.nextInt();
				int w = scanner.nextInt();
				String s = scanner.next();
				if( scanner.hasNext("\\D+")){
					s = s + scanner.next();
				}
				//System.out.println(h+"/"+w+"/"+s);
				if("BIRD".equals(s)){
					minIsBirdH = Math.min(h, minIsBirdH);
					maxIsBirdH = Math.max(h, maxIsBirdH);
					minIsBirdW = Math.min(w, minIsBirdW);
					maxIsBirdW = Math.max(w, maxIsBirdW);
				}
				else{
					hh[cur]=h;
					ww[cur]=w;
					++cur;
				}
			}

			num = scanner.nextInt();
			for(int j=0;j<num;j++){
				int h = scanner.nextInt();
				int w = scanner.nextInt();
				//System.out.println(h+"/"+w);
				if( h>=minIsBirdH && h<=maxIsBirdH && w>=minIsBirdW && w<=maxIsBirdW ){
					System.out.println("BIRD");
				}
				else {
					int f = 1;
					for (int k = 0; k < cur && f==1; k ++) {
						int f2 = 1;
						if (hh[k] < minIsBirdH && h > hh[k] )
							f2 = 0;
						if (hh[k] > maxIsBirdH && h < hh[k] )
							f2 = 0;
						if (ww[k] < minIsBirdW && w > ww[k] )
							f2 = 0;
						if (ww[k] > maxIsBirdW && w < ww[k] )
							f2 = 0;
						if (f2==1)
							f = 0;
					}
					if (f==1)
						System.out.println("UNKNOWN");
					else
						System.out.println("NOT BIRD");
				}

			}
		}
	}

}
