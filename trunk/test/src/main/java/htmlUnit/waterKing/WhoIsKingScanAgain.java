package htmlUnit.waterKing;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;


/**
 * check oardDetail，finish not finsh
 */
public class WhoIsKingScanAgain {

	protected Log logger = LogFactory.getLog(this.getClass());

	private WaterKingTools waterKingTools; 
	private WebClient  webClient;
	private WaterService ws ;
	private List<BoardDetail> boardDetailList;

	public void scanAgain( User user ){
		waterKingTools = new WaterKingTools();
		ws = new WaterService();

		webClient  = waterKingTools.login(user.getUsername(), user.getPassword());
		List<Board> boardList =  ws.doGetNotFinishBoardDetailList();
		int scanFloor;
		for(int j=boardList.size()-1;j>0; j--){
			Board b = boardList.get(j);
			if(user.getReadLevel() >= b.getReadLevel() 
					&& b.getIsVote() == false
					&& b.getId() > 3716){
				b.setEndPage( (long)Math.ceil(b.getLastScanFloor().doubleValue() / user.getPageNum()));
				for(int i=b.getEndPage().intValue() ; i >=1 ; i-- ){
					boardDetailList = waterKingTools.doGetBoardDetailList(user.getUsername(), webClient ,  new Tools().getBoardDetailUrl(b, i) , b ,false);
					ws.saveBoardDetailList(boardDetailList);
					logger.info(user.getUsername() + " save BoardDetailList success , size: " +  b.getTopicUrl()+"size:" + boardDetailList.size() );
					if( i == 1  ){
						scanFloor = 1;
					}else{
						scanFloor = b.getReplyNum().intValue()+1 - user.getPageNum()*i;
					}
					ws.getDao().update(" update BOARD b set b.lastScanFloor =  " 
							+ scanFloor + " where b.topicUrl =  '"  + b.getTopicUrl()+"'");
				}
			}
		}
	}

	public static void main(String[] args){
//		System.out.println(new Date(109,2,21,19,39));
		//System.out.println( (long)Math.ceil(27.0/10));
		User user = new User("非法_用户","happyamiga",100 , 10);
		WhoIsKingScanAgain wa = new WhoIsKingScanAgain();
		wa.scanAgain(user);
	}

}
