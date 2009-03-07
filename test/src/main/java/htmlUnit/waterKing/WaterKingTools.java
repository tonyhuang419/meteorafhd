package htmlUnit.waterKing;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableBody;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class WaterKingTools {

	protected static Log logger = LogFactory.getLog(WaterKingTools.class);

	static String tsUrl = "http://bbs.taisha.org/" ;
	static String loginUrl = "http://bbs.taisha.org/index.php";
	static String waterUrl = "http://bbs.taisha.org/forum-74-1.html";
	static String tableId = "forum_74";


	/**
	 * login
	 * @param loginName 用户名
	 * @param password 密码
	 * @return  webClient
	 */
	public  WebClient login(String loginName , String password){
		WebClient webClient = new WebClient();
		HtmlPage page;
		try{
			page = webClient.getPage(tsUrl);

			//get form
			HtmlForm form = page.getFormByName("login");
			//get login input
			HtmlTextInput loginNameInput = form.getInputByName("username");
			//get password input
			HtmlPasswordInput passwordInput = form.getInputByName("password");

			loginNameInput.setValueAttribute(loginName);
			passwordInput.setValueAttribute(password);

			//get login button 
			HtmlButton button = form.getButtonByName("userlogin");

			page = button.click();
			System.out.println(page.getTitleText());
			if( page.getBody().asText().indexOf("现在将转入登录前页面")!= -1 ){
				logger.info("login success");
				page = webClient.getPage(loginUrl);
				return webClient;
			}
			else{
				logger.info("login fail");
			}
		}catch( Exception e ){
			logger.info("login happen exception");
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * get water list
	 * @param webClient
	 * @return List<HtmlTableBody>
	 */
	public List<HtmlTableBody> doGetHtmlTable(WebClient webClient){
		HtmlPage page;
		try{
			page = webClient.getPage(waterUrl);
			HtmlTable htmlTable = (HtmlTable)page.getElementById(tableId);
			return htmlTable.getBodies();
		}catch(Exception e){
			logger.info("get water area list fail");
			e.printStackTrace();
		}
		return null;
	}


	public  List<Board> doGetWaterList(List<HtmlTableBody> listBody){
		Board board;
		List<Board> boardList = new ArrayList<Board>();
		List<HtmlTableCell> listHTC;
		for (HtmlTableBody body : listBody ) {
			List<HtmlTableRow> rows = body.getRows();
			board = new Board();
			
			/**
			 * every TBODY has one row
			 */
			HtmlTableRow  htmlTableRow = rows.get(0);
			listHTC = htmlTableRow.getCells();
			for (int j=0;j<listHTC.size();j++ ) {
				switch (j){
				case 2:
					board.setTopic(listHTC.get(j).getHtmlElementsByTagName("span").get(0).asText());
					board.setTopicUrl(listHTC.get(j).getHtmlElementsByTagName("span").get(0).getHtmlElementsByTagName("a").get(0).getAttribute("href"));
					break;
				case  3:
					board.setStarter(listHTC.get(j).getHtmlElementsByTagName("a").get(0).asText());
					board.setIssueDate(Tools.stringToDate(listHTC.get(j).getHtmlElementsByTagName("em").get(0).asText()));
					break;
				case 4:
					board.setReplyNum(new Long(listHTC.get(j).getHtmlElementsByTagName("em").get(0).asText()));
					break;
				}
			}
			boardList.add(board);
		}
		return boardList;
	}

	public static void main(String[] args){
		WaterKingTools waterKingTools = new WaterKingTools();
		WebClient webClient = waterKingTools.login("非法_用户", "happyamiga");
		//		waterKingTools.login("非法用户xx", "happyamiga");
		List<HtmlTableBody> waterList = waterKingTools.doGetHtmlTable(webClient);
		List<Board> boardList  = waterKingTools.doGetWaterList(waterList);
//		System.out.println(boardList.size());
		for(Board b:boardList){
			System.out.print(b.getTopic()+"|");
			System.out.print(b.getTopicUrl()+"|");
			System.out.print(b.getStarter()+"|");
			System.out.print(b.getReplyNum()+"|");
			System.out.println(b.getIssueDate());
		}

	}

}
