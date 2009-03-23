package htmlUnit.waterKing;

import java.util.ArrayList;
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

		int threadSzie = 5;
		List<WebClient>  webClientList = new ArrayList<WebClient>();
		List<Board>  boardListTemp = new ArrayList<Board>();

		WaterService ws = new WaterService();
		List<Board> boardList =  ws.doGetNotFinishBoardDetailList();

		User user1 = new User("非法_用户","happyamiga",100 , 10);
		for(int i=0;i<threadSzie;i++){
			webClientList.add(  new WaterKingTools().login(user1.getUsername(), user1.getPassword()) );
		}

		ExecutorService exec = Executors.newSingleThreadExecutor();
		//		for(int j=boardList.size()-1;j>0; j--){
		for(int j=0;j<boardList.size();){
			for(int i=0;i<threadSzie;i++){
				boardListTemp.add( boardList.get(j++) );
			}
			for(int i=0;i<threadSzie;i++){
				if(user1.getReadLevel() >= boardListTemp.get(i).getReadLevel() && boardListTemp.get(i).getIsVote() == false ){
					boardListTemp.get(i).setEndPage((long)Math.ceil( boardListTemp.get(i).getLastScanFloor().doubleValue() / user1.getPageNum()));
					exec.execute(new FixedScan(webClientList.get(i),user1 ,  boardListTemp.get(i) ));
				}
			}
			boardListTemp.clear();
		}
		exec.shutdown();
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


