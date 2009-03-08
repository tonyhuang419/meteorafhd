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
	
	
	public void scan(){
		String loginName = "非法_用户";
		String password = "happyamiga";
		String url;
		List<HtmlTableBody> listHtmlTableBody;
		List<Board> listBoard;
		WaterKingTools waterKingTools = new WaterKingTools(); 
		WaterService waterService = new WaterService();
		WebClient  webClient  = waterKingTools.login(loginName, password);
		for(int i=1000;i>=1;i--){
			url= waterUrlPrefix + i +  waterUrlSuffix;
			logger.info(url);
			listHtmlTableBody = waterKingTools.doGetHtmlTable(webClient, url);
			listBoard = waterKingTools.doGetWaterList(listHtmlTableBody);
			logger.info("size:"+listBoard.size());
			waterService.saveBoardList(listBoard);
		}
		try {
			waterService.closeConnection();
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args){
		new WhoIsKing().scan();
	}
	
}




	




