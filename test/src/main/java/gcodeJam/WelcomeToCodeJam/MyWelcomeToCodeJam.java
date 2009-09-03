package gcodeJam.WelcomeToCodeJam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyWelcomeToCodeJam {

	public static String welcome = "welcome";
	public static String to = "to";
	public static String code = "code";
	public static String jam = "jam";

	public boolean isMatch(String str , String refer){
		for(int i=0;i<str.length();i++){
			if( refer.indexOf(str.charAt(i)) ==-1 ){
				return false;
			}
		}
		for(int i=0;i<refer.length();i++){
			int pos = str.indexOf(refer.charAt(i));
			if( pos ==-1 ){
				return false;
			}
			else{
				str = str.substring(0,pos) + str.substring(pos+1,str.length());
			}
		}
		return true;
	}

	public int getWordCount(String str , String refer ){
		int count = 1;
		int diff = str.length() - refer.length();
		for(int i=0 ; i<diff; i++ ){
			count *= 2;
		}
		return count;
	}


	public String parse(String str){
		StringBuffer sb = new StringBuffer();
		String[] sArr = str.split(" ");
		for(String s: sArr){
			if(this.isMatch(s, MyWelcomeToCodeJam.welcome )){
				int count = this.getWordCount(s, MyWelcomeToCodeJam.welcome );
				while(count-- > 0){
					sb.append("w");
				}
			}
			else if(this.isMatch(s, MyWelcomeToCodeJam.to )){
				int count = this.getWordCount(s, MyWelcomeToCodeJam.to );
				while(count-- > 0){
					sb.append("t");
				}
			}
			else if(this.isMatch(s, MyWelcomeToCodeJam.code )){
				int count = this.getWordCount(s, MyWelcomeToCodeJam.code );
				while(count-- > 0){
					sb.append("c");
				}
			}
			else if(this.isMatch(s, MyWelcomeToCodeJam.jam)){
				int count = this.getWordCount(s, MyWelcomeToCodeJam.jam );
				while(count-- > 0){
					sb.append("j");
				}
			}
			else{
				sb.append("b");
			}
		}
//		System.out.println(sb);
		return sb.toString();
	}

	public static void main(String[] args) throws Exception{
		MyWelcomeToCodeJam cj = new MyWelcomeToCodeJam();
		String s = cj.parse("wwelcome xx xx to xx xx code jamm");
		//wtcj
		Pattern p = Pattern.compile(".*w.*t.*c.*j.*");  
		Matcher m1 = p.matcher(s);  
		int count=0;
		while( m1.find() ){  
			count++;
			int gc = m1.groupCount();  
			for(int i = 0; i <= gc; i++) {
				System.out.println(m1.group()); 
			}
		}
		System.out.println(count);
	}

}
