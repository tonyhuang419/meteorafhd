package htmlUnit.waterKing;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;

public class WhoIsKing {

	protected Log logger = LogFactory.getLog(this.getClass());

	/**
	 * need Addrress like this ： http://bbs.taisha.org/forum-74-1.html
	 */
	private String waterUrlPrefix = "http://bbs.taisha.org/forum-74-";
	private String waterUrlSuffix = ".html";


	public void scan(WebClient webClient ,String baseUrl ){
		String url;
		List<HtmlTableBody> listHtmlTableBody;
		List<Board> listBoard;
		WaterKingTools waterKingTools = new WaterKingTools(); 
		WaterService waterService = new WaterService();

		url= waterUrlPrefix + baseUrl +  waterUrlSuffix;
		listHtmlTableBody = waterKingTools.doGetHtmlTable(webClient, url);
		listBoard = waterKingTools.doGetWaterList(listHtmlTableBody);
		logger.info("size:"+listBoard.size());
		waterService.saveBoardList(listBoard);
		try {
			waterService.closeConnection();
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args){
		UserThread t1 = new UserThread("非法_用户","happyamiga");
		UserThread t2 = new UserThread("MS佳菲猫","lzhouwen");
	}

}

class UserThread implements Runnable{
	protected Log logger = LogFactory.getLog(this.getClass());
	
	Thread t;
	WebClient  webClient;
	WaterKingTools waterKingTools = new WaterKingTools(); 
	String loginName;

	public UserThread(String loginName , String password){
		this.loginName = loginName;
		logger.info("login:"+loginName);
		webClient  = waterKingTools.login(loginName, password);
		t = new Thread(this);
		t.start();
	}

	//synchronized 
	public void run() {
		WhoIsKing w = new WhoIsKing();
		if(loginName.equals("非法_用户")){
			for(int i=425;i>=1;i--){
				if(i%2==1){
					logger.info(loginName+":"+i);
					w.scan(webClient , i+"");
				}
			}
		}else{
			for(int i=498;i>=1;i--){
				if(i%2==0){
					logger.info(loginName+":"+i);
					w.scan(webClient , i+"");
				}
			}
		}
	}
}



