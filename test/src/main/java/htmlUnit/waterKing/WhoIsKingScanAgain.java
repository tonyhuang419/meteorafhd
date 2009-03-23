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

	public static void main(String[] args){
		int len = 500;
		int min = 0;
		
		WaterService ws = new WaterService();
		ExecutorService	exec = Executors.newFixedThreadPool(Units.threadSize);
		while(min<=29993){
			List<Board> boardList =  ws.doGetNotFinishBoardDetailList(min , min+len);
			User user = new User("非法_用户","happyamiga",100 , 20);
			for(int j=0;j<boardList.size();j++){  
				boardList.get(j).setEndPage((long)Math.ceil( boardList.get(j).getLastScanFloor().doubleValue() / user.getPageNum()));
				exec.execute(new FixedScan( new WaterKingTools().login(user.getUsername(), user.getPassword()) ,user ,  boardList.get(j) ));
			}
			min = min+500;
		}
		exec.shutdown();
	}
}


class FixedScan implements Runnable{
	protected Log logger = LogFactory.getLog(this.getClass());
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
		logger.info(board.getTopicUrl());
		scanTools.scanBoardDetail(webClient, board, user);
	}

	public void setWebClient(WebClient webClient) {
		this.webClient = webClient;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}


