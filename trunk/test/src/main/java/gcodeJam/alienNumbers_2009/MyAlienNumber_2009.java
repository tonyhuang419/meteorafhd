package gcodeJam.alienNumbers_2009;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;



public class MyAlienNumber_2009 {

	public String parseString(String str){
		List<String> parenthesisList = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		String sign="end";
		boolean wildcard=true;
		StringBuffer  parenthesisTemp = new StringBuffer();
		for(int i=0;i<str.length();i++){
			if( str.charAt(i)=='(' ){
				sign = "start";
				wildcard = false;
				parenthesisTemp = new StringBuffer();
				continue;
			}
			else if (  str.charAt(i)==')' ){
				sign = "end";
				parenthesisList.add(parenthesisTemp.toString());
				continue;
			}
			if("start".equals(sign)){
				parenthesisTemp.append(str.charAt(i) );
				if(wildcard==false){
					sb.append("*");
					wildcard = true;
				}
			}
			else if("end".equals(sign)){
				sb.append( str.charAt(i) );
			}
		}
		
//		System.out.println(sb.toString());
//		System.out.println("=====================");
//		for(String s:parenthesisList){
//			System.out.println(s);
//		}
		
		for(String s:parenthesisList){
			s = "["+s+"]";
			int index = sb.indexOf("*");
			sb.replace(index, index+1, s);
		}
//		System.out.println(sb);
		return sb.toString();
	}
	
	public int complete(List<String> list , String pattern){
		int count = 0;
		for(String str:list){
			if (  str.matches(pattern) ) {
				count++;
			}
		}
		return count;
	}



	public static void main(String[] args) throws Exception{
		MyAlienNumber_2009 an = new MyAlienNumber_2009();
//		an.parseString("bbb(acb)vvv(bc)zzz(ca)");
		
		File  file  =  new  File("src/main/java/gcodeJam/alienNumbers_2009/A-large.in");
		FileReader  fr  =  new  FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		String[] s = line.split(" ");
		int d = new Integer(s[1]);
		int n = new Integer(s[2]);
		List<String> list = new ArrayList<String>();
		for(int i=0;i<d;i++){
			list.add(br.readLine());
		}
		for(int i=0;i<n;i++){
			line = br.readLine();
			String pattern = an.parseString(line);
			int x = i+1;
			System.out.println("Case #"+x+": "+an.complete(list, pattern));
		}
		
	}

}
