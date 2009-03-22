package com.baoz.yx.tools;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author xusheng
 *{@code把用户输入的字符串数组转换成指定格式的字符集合}
 */
public class SplitStr {
	public static List<String> split(String[] str){
		List s=new ArrayList();
		for(int k=0;k<str.length;k++){
			String opStr1=str[k];
			String[] opArr=opStr1.split("-");
			if(opArr.length>1){
				if(!SplitStr.checkDigt(opArr[1])){
					continue;
				}
				int endPoint=Integer.parseInt(opArr[1]);
				String front=opArr[0].substring(0, opArr[0].length()-opArr[1].length());
				String last=opArr[0].substring(opArr[0].length()-opArr[1].length(),opArr[0].length());
				if(!SplitStr.checkDigt(last)){
					continue;
				}
				int startPoint=Integer.parseInt(last);
				String len="";
				for(int j=0;j<opArr[1].length();j++){
					len+="0";
				}
				DecimalFormat df=new DecimalFormat(len);
				for(int i=startPoint;i<=endPoint;i++){
					s.add(front+df.format(i));
				}
			}else{
				s.add(opArr[0]);
			}
		}
		return s;
	}
	public static boolean checkDigt(String str){
		boolean flag=true;
		if(str==null||str.trim().length()==0){
			return false;
		}
		char[] temp=str.toCharArray();
		for(int k=0;k<temp.length;k++){
			if(!(Character.isDigit(temp[k]))){
				flag=false;
				break;
			}
		}
		
		return flag;
		
	}
}
