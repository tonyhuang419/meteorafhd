package htmlUnit.waterKing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
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

	String tsUrl = "http://bbs.taisha.org/" ;
	String loginUrl = "http://bbs.taisha.org/index.php";
	String tableId = "forum_74";


	/**
	 * login
	 * @param loginName 用户名
	 * @param password 密码
	 * @return 
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
	 * @return 
	 */
	public List<HtmlTableBody> doGetHtmlTable(WebClient webClient , String waterUrl){
		logger.info(waterUrl);
		HtmlPage page;
		int sign = 5;
		while(sign>0){
			try{
				page = webClient.getPage(waterUrl);
				HtmlTable htmlTable = (HtmlTable)page.getElementById(tableId);
				logger.info("get water area list success: "+ waterUrl );
				//				success = false;
				sign = 5;
				return htmlTable.getBodies();
			}catch(Exception e){
				sign--;
				logger.info("get water area list fail,again" + waterUrl );
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 * 
	 * @param listBody 帖子列表
	 * @return
	 */
	public  List<Board> doGetWaterList(List<HtmlTableBody> listBody){
		if(listBody==null){
			return null;
		}
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

			int size;
			String readLevel;
			String pageStr;
			String[] pages;
			for (int j=0;j<listHTC.size();j++ ) {
				switch (j){
				case 2:

					/**
					 * set topic
					 */
					size = listHTC.get(j).getHtmlElementsByTagName("span").size();
					board.setTopic(listHTC.get(j).getHtmlElementsByTagName("span").get(0).asText());
					board.setTopicUrl(listHTC.get(j).getHtmlElementsByTagName("span").get(0).getHtmlElementsByTagName("a").get(0).getAttribute("href"));


					/**
					 * set  page,just page? i can't sure now (not exist read level)
					 */
					if(size==2){
						board.setRaedLevel(0L);
						//						logger.info( "just page?"+ listHTC.get(j).getHtmlElementsByTagName("span").get(1).asText());
						pageStr = listHTC.get(j).getHtmlElementsByTagName("span").get(1).asText().trim();
						pages = pageStr.split(" ");
						board.setEndPage(new Long(pages[pages.length-1]));
					}

					/**
					 * set read level , page
					 */
					else if(size == 3){
						//						logger.info("read level："+listHTC.get(j).getHtmlElementsByTagName("span").get(1).asText());
						readLevel  = listHTC.get(j).getHtmlElementsByTagName("span").get(1).asText();
						if(StringUtils.isBlank(readLevel)){
							board.setRaedLevel(0L);
						}
						else{
							board.setRaedLevel(new Long(readLevel));
						}

						//						logger.info("page："+listHTC.get(j).getHtmlElementsByTagName("span").get(2).asText());
						pageStr = listHTC.get(j).getHtmlElementsByTagName("span").get(2).asText().trim();
						pages = pageStr.split(" ");
						board.setEndPage(new Long(pages[pages.length-1]));
					}
					else{
						board.setRaedLevel(0L);
						board.setEndPage(1L);
					}
					break;
				case  3:
					board.setStarter(listHTC.get(j).getHtmlElementsByTagName("a").get(0).asText());
					board.setIssueDate(Tools.stringToDate(listHTC.get(j).getHtmlElementsByTagName("em").get(0).asText() , Units.dateFormatDate));
					break;
				case 4:
					if(listHTC.get(j).getHtmlElementsByTagName("strong").get(0).asText().equals("-")){
						board.setReplyNum(0L);
					}
					else{
						board.setReplyNum(new Long(listHTC.get(j).getHtmlElementsByTagName("strong").get(0).asText()));
					}
					if(listHTC.get(j).getHtmlElementsByTagName("em").get(0).asText().equals("-")){
						board.setReadNum(0L);
					}
					else{
						board.setReadNum(new Long(listHTC.get(j).getHtmlElementsByTagName("em").get(0).asText()));
					}
					break;
				}
			}
			board.setLastScanTime(new Date());
			board.setLastScanFloor(0L);
			boardList.add(board);
		}
		//		print info , can be comment
		//		for(Board b:boardList){
		//		logger.info("topic:"+b.getTopic()+"|url:" + b.getTopicUrl() +"|starter:"  + b.getStarter()
		//		+ "|replyNum:" + b.getReplyNum()+ "|issueDate:"  + b.getIssueDate()
		//		+ "|raedLevel:" + b.getRaedLevel()+ "|endPage:" +  b.getEndPage());
		//		}
		return boardList;
	}

	public List<BoardDetail> doGetBoardDetailList( WebClient webClient , String boardPageurl){
		logger.info(boardPageurl);
		HtmlPage page;
		HtmlTable htmlTable;
		List<BoardDetail> boardDetailList = new ArrayList<BoardDetail>();
		BoardDetail boardDetail;
		try{
			page = webClient.getPage(boardPageurl);
			HtmlForm htmlForm = page.getFormByName("modactions");
			List<HtmlElement> listHTMLElement = htmlForm.getHtmlElementsByTagName("table");
			logger.info("have " + listHTMLElement.size() +" floors");
			logger.info("get page detail success: " + boardPageurl );
			for(HtmlElement htmlElement : listHTMLElement){
				boardDetail = new BoardDetail();
				htmlTable = (HtmlTable)htmlElement;

				/**
				 *  floor
				 */
				//				logger.info(htmlTable.getAttribute("id"));
				boardDetail.setFloor(htmlTable.getAttribute("id"));

				/**
				 * postId
				 */
				HtmlTableCell htmlTableCellOne = htmlTable.getBodies().get(0).getRows().get(0).getCells().get(0); 
				//				logger.info(htmlTableCellOne.getHtmlElementsByTagName("a").get(0).asText());
				boardDetail.setPostId(htmlTableCellOne.getHtmlElementsByTagName("a").get(0).asText());


				HtmlTableCell  htmlTableCellTwo  = htmlTable.getBodies().get(0).getRows().get(0).getCells().get(1);
				List<HtmlElement> divHtmlElementList = htmlTableCellTwo.getHtmlElementsByTagName("div");
				String postTime = divHtmlElementList.get(0).asText();
				postTime = postTime.substring(postTime.indexOf("于 ")+2 , postTime.indexOf(" 只"));

				/**
				 * postTime
				 */
				//				logger.info(postTime);
				boardDetail.setPostTime(Tools.stringToDate(postTime , Units.dateFormatTime));

				for(HtmlElement htmlElementDiv:divHtmlElementList){
					if(htmlElementDiv.getAttribute("id").indexOf("postmessage")!=-1){

						/**
						 * postMessage
						 */
						//						logger.info(this.cleanMessage(htmlElementDiv));
						String message = this.cleanMessage(htmlElementDiv);
						boardDetail.setPostMessage(message);
						boardDetail.setPostMessageLength(new Long(message.length()));

						/**
						 * search face
						 */
						List<HtmlElement> faceHtmlElementList  = htmlElementDiv.getHtmlElementsByTagName("img");
						//						logger.info("face num:" + faceHtmlElementList.size() );
						boardDetail.setFaceNum(new Long(faceHtmlElementList.size()));

						StringBuffer faceDetail = new StringBuffer();
						String face;
						for(HtmlElement htmlElementFace: faceHtmlElementList){
							face = htmlElementFace.getAttribute("src");
							faceDetail.append(face.substring(face.indexOf("ault/")+5, face.length())).append("|");
						}
						//						logger.info(faceDetail);
						boardDetail.setFaceDetail(faceDetail.toString());
						boardDetailList.add(boardDetail);
						continue;
					}
				}
			}

			for( BoardDetail bd:  boardDetailList){
				logger.info( "floor:"+bd.getFloor()
						+ "|topic:" + bd.getTopic() 
						+ "|postid:"+bd.getPostId() 
						+ "|message:"+bd.getPostMessage() 
						+ "|postTime:" + bd.getPostTime()
						+ "|faceNum:"+bd.getFaceNum()
						+ "|faceDeatail" + bd.getFaceDetail()
						+ "|messageLength:" + bd.getPostMessageLength());
			}

			return boardDetailList;
		}catch(Exception e){
			logger.info("get page detail list fail,again "  +  boardPageurl );
			e.printStackTrace();
		}
		return null;
	}



	private String cleanMessage(HtmlElement htmlElementDiv){

		/**
		 * clean span
		 */
		while(htmlElementDiv.getHtmlElementsByTagName("span").size()!=0){
			if(htmlElementDiv.getHtmlElementsByTagName("span").get(0).getAttribute("style").indexOf("none")!=-1){
				htmlElementDiv.removeChild("span", 0);
			}
		}

		/**
		 * clean font
		 */
		while(htmlElementDiv.getHtmlElementsByTagName("font").size()!=0){
			if(htmlElementDiv.getHtmlElementsByTagName("font").get(0).getAttribute("style").indexOf("0px")!=-1){
				htmlElementDiv.removeChild("font", 0);
			}
		}
		return htmlElementDiv.asText();
	}





	public static void main(String[] args){

		String waterUrl = "http://bbs.taisha.org/forum-74-991.html";
		WaterKingTools waterKingTools = new WaterKingTools();
		WebClient webClient = waterKingTools.login("非法_用户", "happyamiga");
		//		waterKingTools.login("非法用户xx", "happyamiga");
		List<HtmlTableBody> waterList = waterKingTools.doGetHtmlTable(webClient , waterUrl);
		List<Board> boardList  = waterKingTools.doGetWaterList(waterList);
		//		System.out.println(boardList.size());
		//		for(Board b:boardList){
		//		System.out.print(b.getTopic()+"|");
		//		System.out.print(b.getTopicUrl()+"|");
		//		System.out.print(b.getStarter()+"|");
		//		System.out.print(b.getReplyNum()+"|");
		//		System.out.print(b.getReadNum()+"|");
		//		System.out.println(b.getIssueDate());
		//		}
		//		new WaterService().saveBoardList(boardList);
	}

}
