package gcodeJam.multi_base_happiness;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MyHappiness {


	public String change(int x, int y){
		StringBuffer output = new StringBuffer(); 
		while(x != 0) {  
			output = output.append(x%y);
			x /= y;  
		} 
		return output.reverse().toString();
	}

	public int compute(Integer[] iArr , int radix){
		int count = 1;
		int sum = 0;
		while(sum!=1){
			sum=0;
			count++;
			for(int j=0;j<iArr.length;j++){
				sum += iArr[j]*iArr[j];
			}
//			String sumStr = this.change(sum, radix );
			String sumStr = sum+"";
			
			
			iArr = new Integer[sumStr.length()];
			for(int j=0;j<sumStr.length();j++){
				iArr[j] = Integer.valueOf(sumStr.charAt(j)+"");
			}

			boolean breakSign = true;
			for(int j=0;j<sumStr.length();j++){
				if (  sumStr.charAt(j)!='1' && sumStr.charAt(j)!='0'  ){
					breakSign = false;
					break;
				}
			}
			if(breakSign){
				sum = 1;
			}
		}
		return count;
	}


	public static void main(String[] args) throws FileNotFoundException {
		MyHappiness m = new MyHappiness();

		File  file  =  new  File("src/main/java/gcodeJam/multi_base_happiness/test.in");
		FileReader  fr  =  new  FileReader(file);
		Scanner scanner = new Scanner(fr);
		int count = scanner.nextInt();
		scanner.nextLine();
		for(int i=0;i<count;i++){
			String line = scanner.nextLine();
			String[] sArr = line.split(" ");
			int radix = 10;
			if(sArr.length==3){
				radix = Integer.valueOf(sArr[2]);
			}
			Integer[] iArr = new Integer[]{Integer.valueOf(sArr[0]) ,  Integer.valueOf(sArr[1]) };
			if(radix!=10){
				String tempS = sArr[0] + sArr[1];
				iArr = new Integer[tempS.length()];
				for(int j=0;j<tempS.length();j++){
					iArr[j] = Integer.valueOf(tempS.charAt(i)+"");
				}
			}
			System.out.println( m.compute(iArr, radix) );
		}
	}

}
