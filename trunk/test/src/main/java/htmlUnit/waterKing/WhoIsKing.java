package htmlUnit.waterKing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;

public class WhoIsKing {

	protected Log logger = LogFactory.getLog(this.getClass());


	public static void main(String[] args){
		User user = new User("非法_用户","happyamiga",100 , 20);
		List<WebClient> webClientList = new ArrayList<WebClient>();
		List<ExecutorService> execList = new ArrayList<ExecutorService>();
		for(int i=0;i<Units.threadSize;i++){
			webClientList.add(  new WaterKingTools().login(user.getUsername(), user.getPassword()) );
			execList.add(Executors.newSingleThreadExecutor());
		}
		int page = Tools.getScanPage();
		while(page > 0){
			int s=0;
			for(ExecutorService exec : execList){
				exec.execute(new UserThread( webClientList.get(s++) ,user , page--));
			}
		}
		for(ExecutorService exec : execList){
			exec.shutdown();
		}
	}
}

class UserThread implements Runnable{
	protected Log logger = LogFactory.getLog(this.getClass());

	private WebClient  webClient;
	private User user;
	private int page;


	public UserThread( WebClient webClient,User user , int  page){
		this.webClient = webClient;
		this.user = user;
		this.page = page;
	}


	public void run() {
		logger.info(user.getUsername()+": sacn page "+page);
		new ScanTools().scanBoard(webClient, page+"" , user);
		Tools.getScanPage();
	}

}



