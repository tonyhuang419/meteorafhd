package com.cheatkx;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class VFriend extends BaseKaixin{


	public static void main(String[] args) {
		VFriend v = new VFriend();
		Login login = new Login();
		HttpClient client = login.login(urlStr,port,protocal);
		if( client != null ){
//			v.eat(client);
//			v.drink(client);
//			v.idel(client);
			v.test(client);
		}
		else{
		}
		login.logout(client,urlStr,port,protocal);	
	}


	private void eat( HttpClient client  ){
		String methodUrl = "/app/app.php?aid=1068";
		HttpMethod method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
			String context = Tools.printInfo(method);
			g_verify = Tools.getVerify(context);			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}

		String puid = "3327550";
		methodUrl = "/trueman/aj_status.php?verify="+g_verify+"&uid="+puid+"&action=feed&r=0.8321581315249205";
		System.out.println(methodUrl);
		method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
			String context = Tools.printInfo(method);
			logger.info(context);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}	
	}
	
	private void drink( HttpClient client  ){
		String methodUrl = "/app/app.php?aid=1068";
		HttpMethod method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
			String context = Tools.printInfo(method);
			g_verify = Tools.getVerify(context);			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}


		String puid = "3327550";
		methodUrl = "/trueman/aj_status.php?verify="+g_verify+"&uid="+puid+"&action=drink&r=0.8321581315249205";
		System.out.println(methodUrl);
		method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
			String context = Tools.printInfo(method);
			logger.info(context);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}	
	}
	
	private void idel( HttpClient client  ){
		String methodUrl = "/app/app.php?aid=1068";
		HttpMethod method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
			String context = Tools.printInfo(method);
			g_verify = Tools.getVerify(context);			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}


		String puid = "3327550";
		methodUrl = "/trueman/aj_status.php?verify="+g_verify+"&uid="+puid+"&action=commonidle&r=0.8321581315249205";
		System.out.println(methodUrl);
		method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
			String context = Tools.printInfo(method);
			logger.info(context);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}	
	}
	

	private void test( HttpClient client  ){
		String methodUrl = "/app/app.php?aid=1068";
		HttpMethod method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
			String context = Tools.printInfo(method);
			g_verify = Tools.getVerify(context);			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}


		String puid = "3327550";
		methodUrl = "/trueman/aj_status.php?verify="+g_verify+"&uid="+puid+"&type=2&r=0.28030232107266784";
		System.out.println(methodUrl);
		method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
			String context = Tools.printInfo(method);
			logger.info(context);
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}	
	}



}
