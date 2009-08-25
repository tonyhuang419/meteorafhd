package gcodeJam.alienNumbers;


import java.util.List;

import org.apache.commons.lang.StringUtils;

public class MyAlienNumber {

	public void analyze() throws Exception{
		List<String[]> ls = UtilTools.alienNumberReadFile("src/main/java/gcodeJam/alienNumbers/A-large-practice.in");
		int i=0;
		for(String[] strArr:ls){
			if(StringUtils.isNotBlank(strArr[0]) 
					&& StringUtils.isNotBlank(strArr[1])
					&& StringUtils.isNotBlank(strArr[2] )){
				System.out.println("Case #"+ ++i +": "+ this.compute(strArr[0], strArr[1] , strArr[2] ));
			}
		}
	}
	
	public String compute(String alienNum, String inputSystem, String outputSystem) {  
		int[] numInputSystem = new int[300];  
		for(int i = 0;i < inputSystem.length();i ++) {  
			numInputSystem[inputSystem.charAt(i)] = i;  
		}  
		
		int x = 0;  

		for(int i = 0;i < alienNum.length();i ++) {  
			x = (x * inputSystem.length()) + numInputSystem[alienNum.charAt(i)];
		} 
		//Decimal Number  
//		System.out.println(x);  
		
		StringBuffer output = new StringBuffer();           
		while(x != 0) {  
			StringBuffer temp = new StringBuffer();  
			output = temp.append(outputSystem.charAt(x % outputSystem.length())).append(output);  
			x /= outputSystem.length();  
		} 
//		System.out.println(output);
		return output.toString();
	}
	
	public static void main(String[] args) throws Exception{
//		new MyAlienNumber().compute("0D", "D>asC0", "a,$X}(F3z9");
		new MyAlienNumber().analyze();
	}
	
}
