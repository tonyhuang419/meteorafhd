package waterKingCovToNoSql.test;

import java.util.Date;

import waterKingCovToNoSql.entity.Board;
import waterKingCovToNoSql.service.IBoardService;
import waterKingCovToNoSql.service.IWaterService;
import waterKingCovToNoSql.service.impl.BoardService;
import waterKingCovToNoSql.service.impl.WaterService;


public class TestBoardService {

	public void testInsertBoard(){
		IBoardService cs =  new BoardService();
		Board b = getMockBoard(55000L);
		cs.insertBoard(b);
//		getMockBoard(10000L);
//		cs.insertBoard(b);
//		getMockBoard(50001L);
//		cs.insertBoard(b);
	}


	private Board getMockBoard(Long id){
		Board b = new Board();
		b.setId(id);
		b.setIssueDate(new Date());
		b.setIsVote(true);
		b.setTopic("Ö÷Ìâ");
		return b;
	}

	private void copyMySQLToCassandra( ){
		IWaterService ws = new WaterService();
		int min = 0;
		int len = 500;
		while(min<=52000){ //51990
			ws.copyMySQLToCassandra(min , min+len);
			min = min+len;
		}
	}

	public static void main(String[] args) {
		TestBoardService tc = new TestBoardService();
//		tc.testInsertBoard();
//		tc.copyMySQLToCassandra();

	}

}
