package net_home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class Test {
	public static void main(String[] args)  {
		Test t = new Test();

//		String str = t.urlRead();
//		if(str==null){
//			System.out.println("error happen");
//		}
//		else{
//			System.out.println(str);
//		}
		
		t.urlPost();
	}


	public void test(){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println(br.readLine());
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}


	public String urlRead(){
		StringBuffer sb = new StringBuffer("");
		String str = null;
		try{
			URL url = new URL("http://www.baidu.com");
			URLConnection connection = url.openConnection();
			InputStream in = connection.getInputStream();
//			byte[] data = new byte[1024];
			BufferedReader br = new BufferedReader(new InputStreamReader(in),1024);
			while( (str = br.readLine()) !=null){
//				System.out.println(str);
				sb.append(str+"\n");
			}
			in.close();
			return sb.toString();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		return null;
	}

	public void urlPost(){
		String sTmp="http://www.kaixin001.com/login/login.php";
		try{
			URL url=new URL(sTmp);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true) ;
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			out.write("url=%2F&email=fhdone@gmail.com&password=happyamiga");//这里组织域信息
			out.flush();
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String line = null;
			StringBuffer content= new StringBuffer();
			while((line = in.readLine()) != null){//line为返回值，这就可以判断是否成功、
				content.append(line);
				System.out.println(line);
			}
			in.close() ;
			in = null;
			url = null;
			String msg = content.toString();
			System.err.println(msg);            
		}catch(IOException ex){
			ex.printStackTrace();
		}catch(Exception e){
			System.out.println("错误：");
			System.out.println(e.getStackTrace());
		}
	}

}
