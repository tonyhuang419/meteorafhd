package com.cheatkx;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

public class VFriend extends BaseKaixin{
	

	public static void main(String[] args) {
		new VFriend().vMain();
	}

	private void vMain(){
		Login login = new Login();
		HttpClient client = login.login(urlStr,port,protocal);
		String action ;
		if( client != null ){
			action = "dance";
			this.play(client, action);
			action = "tita";
			this.play(client, action);
		}
		else{
		}
		login.logout(client,urlStr,port,protocal);	
	}
	
	
	private void play( HttpClient client ,String _action ){
		String methodUrl = "/app/app.php?aid=1068";
		HttpMethod method = new GetMethod(methodUrl);
		try{
			client.executeMethod(method);
			String context = Tools.printInfo(method);
			g_verify = Tools.getVerify(context);			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}

		methodUrl = "/trueman/aj_status.php?verify="+g_verify+"&uid="+uid+"&action="+_action+"&r=0.2864026427268982";
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
