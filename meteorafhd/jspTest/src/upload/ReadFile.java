package upload;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ReadFile implements java.io.Serializable{
	private String fileName,fileExtName;
	private BufferedReader bf;
	private File file;
	
	public ReadFile(){
		
	}
	public ReadFile(String path){
		file = new File(path);
	}
///////////////////////////////////////////////////////////////
	public String  getExtension() {
		getFileName();
		if ((fileName != null) && (fileName.length() > 0)) {
			int i = fileName.lastIndexOf('.');

			if ((i > 0) && (i < (fileName.length() - 1))) {
				fileExtName =  fileName.substring(i + 1);
				return fileExtName;
			}
		}
		return "not found";
	}	
	public String  getFileName(){
		fileName = file.getName();
		return fileName;
	}

	private void readToBuf(){
		try{
			bf = new BufferedReader(new FileReader(file));	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
///////////////////////////////////////////////////////////////
	public String readToString(){
		String temp,info="";
		readToBuf();
		try{
			while( (temp = bf.readLine())!=null){
				info =info + temp +"\r\n";
			}
			return info;
		}catch(Exception e){
			e.printStackTrace();	
		}
		return "fail read";
	}
	public void out(){
		String temp;
		readToBuf();
		try{
			while( (temp = bf.readLine())!=null){
				System.out.println(temp);
			}
		}catch(Exception e){
			e.printStackTrace();	
		}
	}

	public void copyFile(){
		fileName = this.getFileName();
		String path = "d:\\"+ fileName;
		try{
			File newFile = new File(path);
			BufferedWriter bw = new BufferedWriter(new FileWriter(newFile));
			bw.write(readToString());
			bw.flush();
			bw.close();		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void test(){
		System.out.println("xxxxxxx");
	}
}

//public class ReadFile {
//	public static void main(String[] args) {
//		ReadFiles rf = new ReadFiles("C:\\新建 文本文档.txt");
//		//System.out.println(rf.getFileName());
//		//System.out.println(rf.getExtension());
//		//rf.out();
//		//rf.copyFile();
//	}
//}
