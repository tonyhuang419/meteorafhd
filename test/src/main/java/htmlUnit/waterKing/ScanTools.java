package htmlUnit.waterKing;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;

public class ScanTools {

	protected Log logger = LogFactory.getLog(this.getClass());
	
	/**
	 * need Addrress like this ： http://bbs.taisha.org/forum-74-1.html
	 */
	private String waterUrlPrefix = "http://bbs.taisha.org/forum-74-";
	private String waterUrlSuffix = ".html";
	private WaterKingTools waterKingTools = new WaterKingTools(); 
	private WaterService waterService = new WaterService();
	
	synchronized public void scan(WebClient webClient ,String baseUrl ){
		String url;
		List<HtmlTableBody> listHtmlTableBody;
		List<Board> listBoard;
		url= waterUrlPrefix + baseUrl +  waterUrlSuffix;
		listHtmlTableBody = waterKingTools.doGetHtmlTable(webClient, url);
		listBoard = waterKingTools.doGetWaterList(listHtmlTableBody);
		logger.info("size:"+listBoard.size());
		//	waterService.saveBoardList(listBoard); temp comment
		try {
			waterService.closeConnection();
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
