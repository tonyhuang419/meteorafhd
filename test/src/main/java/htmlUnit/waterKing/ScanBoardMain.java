package htmlUnit.waterKing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScanBoardMain {

	protected Log logger = LogFactory.getLog(this.getClass());


	public static void main(String[] args){
		User user = new User("非法_用户","***",100 , 20);

		ExecutorService exec = Executors.newFixedThreadPool(Units.threadSize);

		int page = Tools.getScanPage();
		while(page > 0){
			exec.execute(new UserThread( user , page--));
		}
		exec.shutdown();
	}
}

class UserThread implements Runnable{
	protected Log logger = LogFactory.getLog(this.getClass());

	private User user;
	private int page;


	public UserThread( User user , int  page){
		this.user = user;
		this.page = page;
	}


	public void run() {
		logger.info(user.getUsername()+": sacn page "+page);
		new ScanTools().scanBoard(new WaterKingTools().login(user.getUsername(), user.getPassword()) , page+"" , user);
		Tools.getScanPage();
	}

}



