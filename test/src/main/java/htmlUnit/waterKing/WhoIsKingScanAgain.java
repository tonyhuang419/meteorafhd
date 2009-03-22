package htmlUnit.waterKing;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.WebClient;


/**
 * check oardDetail，finish not finsh
 */
public class WhoIsKingScanAgain {

	protected Log logger = LogFactory.getLog(this.getClass());

	public void scanAgain( WebClient webClient ,User user ){
		WaterService ws = new WaterService();

		List<Board> boardList =  ws.doGetNotFinishBoardDetailList();
		ExecutorService exec = Executors.newFixedThreadPool(9);
		for(int j=boardList.size()-1;j>0; j--){
			Board b = boardList.get(j);
			if(user.getReadLevel() >= b.getReadLevel() 
					&& b.getIsVote() == false
					&& b.getId() > 3716){
				b.setEndPage((long)Math.ceil(b.getLastScanFloor().doubleValue() / user.getPageNum()));
				exec.execute(new FixedScan(webClient,user ,b ));
			}
		}
		exec.shutdown();
	}

	public static void main(String[] args){
		User user = new User("非法_用户","happyamiga",100 , 10);
		WebClient webClient = new WaterKingTools().login(user.getUsername(), user.getPassword());
		WhoIsKingScanAgain wa = new WhoIsKingScanAgain();
		wa.scanAgain( webClient , user);
	}
}


class FixedScan implements Runnable{
	
	private WebClient  webClient;
	private Board board;
	private User user;
	private ScanTools scanTools = new ScanTools();
	
	public FixedScan(WebClient webClient ,User user , Board board ){
		this.user = user;
		this.webClient = webClient;
		this.board = board;
	}
	
	public void run(){
//		System.out.println(board.getTopicUrl());
		scanTools.scanBoardDetail(webClient, board, user);
	}
	
	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}

