package htmlUnit.waterKing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;

public class WhoIsKing {

	protected Log logger = LogFactory.getLog(this.getClass());

	private ScanTools scanTools = new ScanTools();
	
	public void scan(WebClient webClient ,String baseUrl ){
		scanTools.scan(webClient, baseUrl);
	}


	public static void main(String[] args){
		new UserThread("非法_用户","happyamiga").getT().start();
		new UserThread("MS佳菲猫","lzhouwen").getT().start();
	}

}

class UserThread implements Runnable{
	protected Log logger = LogFactory.getLog(this.getClass());

	private Thread t;
	private WebClient  webClient;
	private String loginName;
	
	private WaterKingTools waterKingTools = new WaterKingTools(); 
	

	public UserThread(String loginName , String password){
		logger.error("sss");
		this.loginName = loginName;
		logger.info("login:"+loginName);
		webClient  = waterKingTools.login(loginName, password);
		t = new Thread(this , loginName);
	}
	
	public Thread getT(){
		return t;
	}
	

	public void run() {
		WhoIsKing w = new WhoIsKing();
		if(loginName.equals("非法_用户")){
			for(int i=425;i>=1;i--){
				if(i%2==1){
					logger.info(loginName+":"+i);
					w.scan(webClient , i+"" );
				}
			}
		}else{
			for(int i=498;i>=1;i--){
				if(i%2==0){
					logger.info(loginName+":"+i);
					w.scan(webClient , i+"" );
				}
			}
		}
	}
}



