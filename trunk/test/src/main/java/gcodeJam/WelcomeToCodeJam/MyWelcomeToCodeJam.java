package gcodeJam.WelcomeToCodeJam;

import java.util.HashSet;
import java.util.Set;

public class MyWelcomeToCodeJam {

	public static String welcome = "welcome";
	public static String to = "to";
	public static String code = "code";
	public static String jam = "jam";

	public String toStringWithUniqueChar(String str){
		Set<String> setStr = new HashSet<String>();
		for(int i=0;i<str.length();i++){
			setStr.add(str.charAt(i)+"");
		}
		StringBuffer sb = new StringBuffer();
		for(String s : setStr){
			sb.append(s);
		}
		return sb.toString();
	}

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


	public void parse(String str){
		StringBuffer sb = new StringBuffer();
		String[] sArr = str.split(" ");
		for(String s: sArr){
			if(this.isMatch(s, MyWelcomeToCodeJam.welcome )){
				int count = this.getWordCount(s, MyWelcomeToCodeJam.welcome );
				sb.append("s"+count);
			}
			else if(this.isMatch(s, MyWelcomeToCodeJam.to )){
				int count = this.getWordCount(s, MyWelcomeToCodeJam.to );
				sb.append("t"+count);
			}
			else if(this.isMatch(s, MyWelcomeToCodeJam.code )){
				int count = this.getWordCount(s, MyWelcomeToCodeJam.code );
				sb.append("c"+count);
			}
			else if(this.isMatch(s, MyWelcomeToCodeJam.jam)){
				int count = this.getWordCount(s, MyWelcomeToCodeJam.jam );
				sb.append("j"+count);
			}
			else{
				if (sb.length()>0 && sb.charAt(sb.length()-2)== 'b'){
					int bcount =  new Integer(sb.charAt(sb.length()-1)+"")+1;
					sb.deleteCharAt(sb.length()-1).append(bcount);
				}
				else{
					sb.append("b1");
				}
			}
		}
		System.out.println(sb);
	}

	public static void main(String[] args) throws Exception{
		MyWelcomeToCodeJam cj = new MyWelcomeToCodeJam();
		boolean b = cj.isMatch("otoo", MyWelcomeToCodeJam.to);
		System.out.println(b);
		if(b==true){
			System.out.println(cj.getWordCount("otoo", MyWelcomeToCodeJam.to));
		}
		cj.parse("welcome xx xx to xx xx");
	}

}
