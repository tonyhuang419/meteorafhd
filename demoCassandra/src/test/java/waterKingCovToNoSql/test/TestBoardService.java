package waterKingCovToNoSql.test;

import java.util.Date;

import waterKingCovToNoSql.entity.Board;
import waterKingCovToNoSql.service.IBoardService;
import waterKingCovToNoSql.service.impl.BoardService;


public class TestBoardService {

	public void testInsertBoard(){
		Board b = getMockBoard(1L);
		IBoardService cs =  new BoardService();
		cs.insertBoard(b);
		getMockBoard(2L);
		cs.insertBoard(b);
		getMockBoard(3L);
		cs.insertBoard(b);
	}
	
	
	private Board getMockBoard(Long id){
		Board b = new Board();
		b.setId(1L);
		b.setIssueDate(new Date());
		b.setIsVote(true);
		b.setTopic("topic");
		return b;
	}
	
	
	public static void main(String[] args) {
		TestBoardService tc = new TestBoardService();
		tc.testInsertBoard();
		
	}
	
}
