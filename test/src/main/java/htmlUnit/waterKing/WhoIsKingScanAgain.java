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

		List<WebClient>  webClientList = new ArrayList<WebClient>();
		List<ExecutorService> execList = new ArrayList<ExecutorService>();
		List<Board>  boardListTemp = new ArrayList<Board>();

		WaterService ws = new WaterService();
		List<Board> boardList =  ws.doGetNotFinishBoardDetailList();

		User user = new User("非法_用户","happyamiga",100 , 20);
		for(int i=0;i<Units.threadSize;i++){
			webClientList.add(  new WaterKingTools().login(user.getUsername(), user.getPassword()) );
			execList.add(Executors.newSingleThreadExecutor());
		}

		//	for(int j=boardList.size()-1;j>0; j--){
		for(int j=-1;j<boardList.size()-2;){  //循环内部++，需要减1，下表为size-1，故再减1
			for(int k=0; k<Units.threadSize; k++){
				j++;
				if(user.getReadLevel() >= boardList.get(j).getReadLevel() && boardList.get(j).getIsVote() == false ){
					boardListTemp.add( boardList.get(j) );
				}
				else{
					k--;
				}
			}
			int s=-1;
			for(ExecutorService exec : execList){
				s++;
				boardListTemp.get(s).setEndPage((long)Math.ceil( boardListTemp.get(s).getLastScanFloor().doubleValue() / user.getPageNum()));
				exec.execute(new FixedScan(webClientList.get(s),user ,  boardListTemp.get(s) ));
			}
			boardListTemp.clear();

		}
		for(ExecutorService exec : execList){
			exec.shutdown();
		}
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


