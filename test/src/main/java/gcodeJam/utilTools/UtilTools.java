package gcodeJam.utilTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class UtilTools {

	public static List<String> readFile(String filePath) throws Exception{
		List<String> list = new ArrayList<String>();
		File  file  =  new  File(filePath); 
		FileReader  fr  =  new  FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String str = br.readLine();
		//		str = str.trim();
		list.add(str);
		while (str != null){
			str = br.readLine();
			list.add(str);
		}
		br.close();
		fr.close();
		return list;
	}

	public static List<String[]> fileParse(String filePath , int spaceNum ) throws Exception{
		List<String[]> listArr = new ArrayList<String[]>();
		List<String> list = UtilTools.readFile(filePath);
		for(String str : list ){
			if( (str!=null && str.indexOf(" ")==-1) || str == null){
				continue;
			}
			String[] strArr = new String[spaceNum];
			String temp = str;
			int loaction;
			for(int i=0; i<spaceNum; i++){
				loaction = temp.indexOf(" ");
				if(loaction!=-1){
					strArr[i] = temp.substring(0 , loaction );
					temp = temp.substring(loaction+1 , temp.length());
				}
				else{
					strArr[spaceNum-1] = temp;
				}
			}
			listArr.add(strArr);
		}
		return listArr;
	}

	public static List<List<String[]>> fileParse2(String filePath , int spaceNum ) throws Exception{
		List<List<String[]>> listStrArr = new ArrayList<List<String[]>>();
		List<String> list = UtilTools.readFile(filePath);
		List<String[]> listArr = new ArrayList<String[]>();
		int readLine = 0;
		for(String str : list ){
			readLine++;
			if( readLine == 0 ){
				continue;
			}
			if( (str!=null && str.indexOf(" ")==-1) || str == null){
				if(listArr.size()>0){
					listStrArr.add(listArr);
				}
				listArr = new ArrayList<String[]>();
				continue;
			}
			String[] strArr = new String[spaceNum];
			String temp = str;
			int loaction;
			for(int i=0; i<spaceNum; i++){
				loaction = temp.indexOf(" ");
				if(loaction!=-1){
					strArr[i] = temp.substring(0 , loaction );
					temp = temp.substring(loaction+1 , temp.length());
				}
				else{
					strArr[spaceNum-1] = temp;
				}
			}
			listArr.add(strArr);
		}
		return listStrArr;
	}


	public static void main(String[] args){
		try {
			//			UtilTools.readFile("src/main/java/gcodeJam/alienNumbers/A-large-practice.in");
			List<String[]> ls = UtilTools.fileParse("src/main/java/gcodeJam/alienNumbers/A-large-practice.in" , 2);
			for(String[] strArr : ls){
				for(String str : strArr ){
					System.out.println(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
