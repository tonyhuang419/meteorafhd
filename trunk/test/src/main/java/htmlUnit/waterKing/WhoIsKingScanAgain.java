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
	private ScanTools scanTools;

	public void scanAgain( User user ){
		waterKingTools = new WaterKingTools();
		ws = new WaterService();
		scanTools = new ScanTools();

		webClient  = waterKingTools.login(user.getUsername(), user.getPassword());
		List<Board> boardList =  ws.doGetNotFinishBoardDetailList();
		for(int j=boardList.size()-1;j>0; j--){
			Board b = boardList.get(j);
			if(user.getReadLevel() >= b.getReadLevel() 
					&& b.getIsVote() == false
					&& b.getId() > 3716){
				b.setEndPage( (long)Math.ceil(b.getLastScanFloor().doubleValue() / user.getPageNum()));
				scanTools.scanBoardDetail(webClient, b, user);
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
