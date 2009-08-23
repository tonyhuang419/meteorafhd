package gcodeJam;

public class CodeJam1 {
	public static void convertSystem(char[] alienNum, char[] inputSystem, char[] outputSystem) {  
		int inputSize = inputSystem.length;  
		int outputSize = outputSystem.length;  

		int[] numInputSystem = new int[300];  
		for(int i = 0;i < inputSize;i ++) {  
			numInputSystem[inputSystem[i]] = i;  
		}  

		int x = 0;  

		for(int i = 0;i < alienNum.length;i ++) {  
			x = (x * inputSize) + numInputSystem[alienNum[i]];  
		}  

		//Decimal Number  
		System.out.println(x);  

		StringBuilder output = new StringBuilder();           
		while(x != 0) {  
			StringBuilder temp = new StringBuilder();  
			output = temp.append(outputSystem[x % outputSize]).append(output);  
			x /= outputSize;  
		}  

		System.out.println("Case 1: " + output.toString());  
	}  

	public static void main(String[] args) {  
		char[] alienNum = "CODE".toCharArray();  
		char[] inputSystem = "O!CDE?".toCharArray();  
		char[] outputSystem = "A?JM!.".toCharArray();  

		//      char[] alienNum = "2".toCharArray();  
		//      char[] inputSystem = "0123456789".toCharArray();  
		//      char[] outputSystem = "01".toCharArray();  

		//      char[] alienNum = "10".toCharArray();  
		//      char[] inputSystem = "01".toCharArray();  
		//      char[] outputSystem = "0123456789".toCharArray();  

		CodeJam1.convertSystem(alienNum, inputSystem, outputSystem);  
	}  
}  
