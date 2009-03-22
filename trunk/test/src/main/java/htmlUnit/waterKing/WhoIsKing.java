package htmlUnit.waterKing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;

public class WhoIsKing {

	protected Log logger = LogFactory.getLog(this.getClass());


	public void scan(WebClient webClient ,String baseUrl ,User user ){
		new ScanTools().scan(webClient, baseUrl , user);
	}


	public static void main(String[] args){
		new UserThread(new User("非法_用户","happyamiga",100 , 10)).getT().start();
		new UserThread(new User("MS佳菲猫","lzhouwen",80,10 )).getT().start();
		new UserThread(new User("3.1415926535897","happyaaa",80 ,10)).getT().start();
		new UserThread(new User("FHDone","happyamiga",90 ,10)).getT().start();
		new UserThread(new User("六月溜冰鞋","lzhouwen",20 ,10)).getT().start();
		new UserThread(new User("中恋","lzhouwen",30 ,10)).getT().start();
	
		new UserThread(new User("非法_用户","happyamiga",100 , 10)).getT().start();
		new UserThread(new User("3.1415926535897","happyaaa",80 ,10)).getT().start();
		new UserThread(new User("FHDone","happyamiga",90 ,10)).getT().start();
	
	}
}

class UserThread implements Runnable{
	protected Log logger = LogFactory.getLog(this.getClass());

	private Thread t;
	private WebClient  webClient;
	private User user;

	private WaterKingTools waterKingTools = new WaterKingTools(); 


	public UserThread(User user){
		this.user = user;
		//		this.loginName = user.getUsername();
		logger.info("login:"+user.getUsername());
		webClient  = waterKingTools.login(user.getUsername(), user.getPassword());
		if(webClient!=null){
			t = new Thread(this , user.getUsername());
		}
	}

	public Thread getT(){
		return t;
	}


	public void run() {
		WhoIsKing w = new WhoIsKing();
		int page = 1000;
		while(page > 0){
			page = new Tools().getScanPage();
			logger.info(user.getUsername()+": sacn page "+page);
			w.scan(webClient , page+"" , user );
		}
	}
}



