package htmlUnit.waterKing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;

public class WhoIsKing {

	protected Log logger = LogFactory.getLog(this.getClass());


	public void scan(WebClient webClient ,String baseUrl ,User user ){
		new ScanTools().scanBoard(webClient, baseUrl , user);
	}


	public static void main(String[] args){
		
//		new UserThread(new User("MS佳菲猫","lzhouwen",80,10 )).getT().start();
//		new UserThread(new User("3.1415926535897","happyaaa",80 ,10)).getT().start();
//		new UserThread(new User("FHDone","happyamiga",90 ,10)).getT().start();
//		new UserThread(new User("六月溜冰鞋","lzhouwen",20 ,10)).getT().start();
//		new UserThread(new User("中恋","lzhouwen",30 ,10)).getT().start();
//	
//		new UserThread(new User("非法_用户","happyamiga",100 , 10)).getT().start();
//		new UserThread(new User("3.1415926535897","happyaaa",80 ,10)).getT().start();
//		new UserThread(new User("FHDone","happyamiga",90 ,10)).getT().start();
		
		User user = new User("非法_用户","happyamiga",100 , 10);
		WebClient webClient  = new WaterKingTools().login(user.getUsername(), user.getPassword());
		int page = 1000;
		ExecutorService exec = Executors.newFixedThreadPool(9);
		while(page > 0){
			page = new Tools().getScanPage();
			exec.execute(new UserThread(webClient,user , page));
		}
		exec.shutdown();
	
	}
}

class UserThread implements Runnable{
	protected Log logger = LogFactory.getLog(this.getClass());

	private WebClient  webClient;
	private Thread t;
	private User user;
	private int page;
	

	public UserThread( WebClient webClient,User user , int  page){
		this.webClient = webClient;
		this.user = user;
		this.page = page;
		logger.info("login:"+user.getUsername());
	}

	public Thread getT(){
		return t;
	}


	public void run() {
		WhoIsKing w = new WhoIsKing();
		logger.info(user.getUsername()+": sacn page "+page);
		System.out.println(page);
		w.scan(webClient, page+"", user);
	}

	public void setPage(int page) {
		this.page = page;
	}
}



