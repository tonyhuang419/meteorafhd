package com.waterking.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.net.URL;

import com.waterking.entity.BoardDetail;

public class DownloadUtil {

	String baseFilePath = "c:/pic/";

	public void downFile(String urlStr , BoardDetail bd){
		CreateDir.mkdir(baseFilePath+bd.getPostId()+"-"+bd.getTopic());
		baseFilePath = baseFilePath+bd.getPostId()+"-"+bd.getTopic()+"/";
		try {
			URL url = null;
			try {
				url = new URL(urlStr);
			} catch(Exception e) {
				System.out.println("url error");
			}
			String s[] = urlStr.split("/");
			FilterInputStream in = (FilterInputStream) url.openStream();
			File fileOut = new File(baseFilePath+System.currentTimeMillis());
			FileOutputStream out = new FileOutputStream(fileOut+"&&"+s[s.length-1]);
			byte[] bytes = new byte[1024];
			int c;
			while((c = in.read(bytes))!=-1) {
				out.write(bytes,0,c);
			}
			in.close();
			out.close();
		} catch(Exception e) {
			System.out.println("dowload error!");
			e.printStackTrace();
		}
	}

	//	public void run() {
	//		TaiShaTools tool = new TaiShaTools();
	//		HttpClient client = tool.login("e.taisha.org",80,"http");
	//		GetMethod getMethod = new GetMethod(urlStr);
	//		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
	//		try
	//		{
	//			//执行getMethod
	//			int statusCode = client.executeMethod(getMethod);
	//			if (statusCode != HttpStatus.SC_OK){
	//				System.err.println("Method failed: " + getMethod.getStatusLine());
	//			}
	//			//读取内容
	//			byte[] responseBody = getMethod.getResponseBody();
	//			String s[] = urlStr.split("/");
	//			String serverfile = "c:/pic/"+s[s.length-1];
	//			OutputStream serverout = new FileOutputStream(serverfile);
	//
	//			serverout.write(responseBody);   
	//			serverout.flush();   
	//			serverout.close();   
	//
	//			//处理内容
	//			//System.out.println(new String(responseBody));
	////			System.out.println("OK!");
	//		}
	//		catch (HttpException e)
	//		{
	//			//发生致命的异常，可能是协议不对或者返回的内容有问题
	//			System.out.println("Please check your provided http address!");
	//			e.printStackTrace();
	//		}
	//		catch (IOException e)
	//		{
	//			//发生网络异常
	//			e.printStackTrace();
	//		}
	//		finally
	//		{
	//			//释放连接
	//			getMethod.releaseConnection();
	//		}
	//	}



	public static void main(String args[]){
		//		for(int i=0;i<10;i++){
		//			new DownloadUtil("http://www.163.com/images/neteaselogo.gif");
		//		}
//		new DownloadUtil().downFile("http://e.taisha.org/attachments/1_1nSJi7Rg159I.jpg","cc");
	}
}
