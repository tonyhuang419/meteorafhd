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

	private WaterKingTools waterKingTools = new WaterKingTools(); 
	private WaterService waterService = new WaterService();

	//synchronized 
	public void scan(WebClient webClient ,String baseUrl , User user ){
		String url;
		List<HtmlTableBody> listHtmlTableBody;
		List<Board> listBoard;
		url= Units.WATER_URL_PREFIX + baseUrl +  Units.WATER_URL_SUFFIX;
		listHtmlTableBody = waterKingTools.doGetHtmlTable(webClient, url);

		// get a list board
		listBoard = waterKingTools.doGetWaterList(listHtmlTableBody);
		waterService.saveBoardList(listBoard);
		logger.info("save success BoardList");

		// analyze the board
		logger.info("board size:"+listBoard.size());
		List<BoardDetail> boardDetailList;
		int scanFloor;
		for(Board board : listBoard){
			logger.info(board.getTopicUrl() + " hava page " + board.getEndPage() );
			if(user.getReadLevel() >= board.getReadLevel() 
					&& board.getIsVote() == false
					&& !board.getLastScanFloor().equals(1L)){
				for(int i=board.getEndPage().intValue() ; i >=1 ; i-- ){
					boardDetailList = waterKingTools.doGetBoardDetailList( webClient ,  Tools.getBoardDetailUrl(board, i) , board );
					waterService.saveBoardDetailList(boardDetailList);
					logger.info("save success BoardDetailList");
					if( i == 1  ){
						scanFloor = 1;
					}else{
						scanFloor = board.getReplyNum().intValue()-Units.PAGE_NUM;
					}
					waterService.getDao().update(" update BOARD b set b.lastScanFloor =  " 
							+ scanFloor + " where b.topicUrl =  '"  + board.getTopicUrl()+"'");
				}
			}
		}
		waterService.closeConnection();
		//		try {
		//			/**
		//			 * close db , too waste?
		//			 */
		//			waterService.closeConnection();
		//			Thread.sleep(100);
		//		} catch (InterruptedException e) {
		//			e.printStackTrace();
		//		}
	}
}
