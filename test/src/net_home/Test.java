package net_home;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class Test {
	public static void main(String[] args)  {
		Test t = new Test();

		String str = t.urlRead();
		if(str==null){
		System.out.println("error happen");
		}
		else{
		System.out.println(str);
		}


//		t.urlPost();
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
			data = "?action=" + URLEncoder.encode("login", "GBK");
			data += "&username=" + URLEncoder.encode("非法_用户", "GBK");
			data += "&password=" + URLEncoder.encode("happyamiga", "GBK");
		}catch(UnsupportedEncodingException uee){
			uee.printStackTrace();
		}

		StringBuffer sb = new StringBuffer("");
		String str = null;
		try{
			URL url = new URL("http://bbs.taisha.org/logging.php"+data);
			URLConnection connection = url.openConnection();
			InputStream in = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in,"GBK"),1024);
			while( (str = br.readLine()) !=null){
				sb.append(str+"\n");
			}
			in.close();
			return sb.toString();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public void urlPost(){

		String sTmp="http://bbs.taisha.org/logging.php";
		String data = null;
		try {
			data = "action=" + URLEncoder.encode("login", "GBK");
			data += "&username=" + URLEncoder.encode("非法_用户", "GBK");
			data += "&password=" + URLEncoder.encode("happyamiga", "GBK");
		} catch (UnsupportedEncodingException uee) {
			uee.printStackTrace();
		}

		System.out.println(data);
		
		try{
			URL url=new URL(sTmp);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "GBK");
			out.write(data);
			out.flush();
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "GBK"));

			String line = null;
			StringBuffer content= new StringBuffer();
			while((line = in.readLine()) != null){
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
