package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

class FilesBinary{
	String fileName,fileExtName;
	BufferedInputStream bis;
	File file;
	int fileLength;
	public FilesBinary(String path){
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
			bis = new BufferedInputStream(new FileInputStream(file));	
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
///////////////////////////////////////////////////////////////
	public byte[] readToByte(){	
		byte[] b = null;
		readToBuf();
		try{
			fileLength = (int)file.length();
			b  = new byte[fileLength];
			bis.read(b);
			bis.close();
		}catch(Exception e){
			e.printStackTrace();	
		}
		return b;
	}	
	public void out(){
		byte[] b = null;
		readToBuf();
		try{
			fileLength = (int)file.length();
			b  = new byte[fileLength];
			bis.read(b);
			bis.close();
			for(int i=0;i<b.length;i++)
				System.out.println(b[i]);
		}catch(Exception e){
			e.printStackTrace();	
		}
	}
	public void copyFile(){
		fileName = this.getFileName();
		String path = "d:\\"+ fileName;
		try{
			File newFile = new File(path);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
			bos.write(readToByte());
			bos.flush();
			bos.close();		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

public class FileBinary {
	public static void main(String[] args) {
		FilesBinary rf = new FilesBinary("C:\\1.jpg");
		//System.out.println(rf.getFileName());
		//System.out.println(rf.getExtension());
		//rf.readToByte();
		//rf.out();
		rf.copyFile();
	}
}
