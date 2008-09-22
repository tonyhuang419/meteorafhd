package net_home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Test {
	public static void main(String[] args)  {
		Test t = new Test();

//		String str = t.urlRead();
//		if(str==null){
//		System.out.println("error happen");
//		}
//		else{
//		System.out.println(str);
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
		
		String data = null;
		try{
			data = URLEncoder.encode("url", "UTF-8") + "=" + URLEncoder.encode("/", "UTF-8");
			data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode("fhdone@gmail.com", "UTF-8");
			data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode("happyamiga", "UTF-8");
		}catch(UnsupportedEncodingException uee){
			uee.printStackTrace();
		}

		System.out.println(data);
		
		StringBuffer sb = new StringBuffer("");
		String str = null;
		try{
			URL url = new URL("http://www.kaixin001.com/login/login.php");
			URLConnection connection = url.openConnection();
			InputStream in = connection.getInputStream();
//			byte[] data = new byte[1024];
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"),1024);
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
		String data = null;
		try{
			data =   "url" + "=" + URLEncoder.encode("/", "UTF-8");
			data += "&email"     + "=" + URLEncoder.encode("meteorafhd@yahoo.com.cn", "UTF-8");
			data += "&password" + "=" + URLEncoder.encode("happyamiga", "UTF-8");
		}catch(UnsupportedEncodingException uee){
			uee.printStackTrace();
		}

		System.out.println(data);
		
		String sTmp="http://www.kaixin001.com/login/login.php";

		try{
			URL url=new URL(sTmp);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true) ;
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			out.write(data);//这里组织域信息
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
