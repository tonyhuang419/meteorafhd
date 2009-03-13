package htmlUnit.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.net.URL;

public class DownloadUtil {
	
	public static void main(String args[]){
		downloadFile("http://www.163.com/images/neteaselogo.gif", "c:/1");
	}
	
	synchronized static public void downloadFile(String _url,String filePath) {
		try {
			URL url = null;
			try {
				url = new URL(_url);
			} catch(Exception e) {
				System.out.println("this url is error");
			}
			FilterInputStream in=(FilterInputStream) url.openStream();
			File fileOut=new File(filePath);
			FileOutputStream out=new FileOutputStream(fileOut);
			byte[] bytes=new byte[1024];
			int c;
			while((c=in.read(bytes))!=-1) {
				out.write(bytes,0,c);
			}
			in.close();
			out.close();
		} catch(Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}
	} 
}
