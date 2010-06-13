package waterKingCovToNoSql.service.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import waterKingCovToNoSql.entity.Board;
import waterKingCovToNoSql.service.IBoardService;
import waterKingCovToNoSql.service.IWaterService;
import waterKingCovToNoSql.tools.Dao;


public class WaterService implements IWaterService {

	protected  Log logger = LogFactory.getLog(this.getClass());

	private Dao dao = new Dao();

	public List<Board> doGeBoard(int min , int max){
		String sql ="select * from Board b where b.id >" + min+" and b.id <= "+ max + " order by b.id asc";
		ResultSet rs =  this.query(sql);
		List<Board> boardList = new ArrayList<Board>();
		Board board;
		try {
			while (rs.next()){
				board = new Board();
				board.setId(rs.getLong(1));
				board.setTopic(rs.getString(2));
				board.setTopicUrl(rs.getString(3));
				board.setStarter(rs.getString(4));
				board.setIssueDate(rs.getDate(5));
				board.setReplyNum(rs.getLong(6));
				board.setReadNum(rs.getLong(7));
				board.setLastScanTime(rs.getDate(8));
				board.setReadLevel(rs.getLong(9));
				board.setIsVote(rs.getBoolean(10));
				board.setLastScanFloor(rs.getLong(11));
				board.setLastUpateUser(rs.getString(12));
				boardList.add(board);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return boardList;
	}


	public void copyMySQLToCassandra( int min , int max ){
		List<Board> bList = this.doGeBoard(min, max);
//		System.out.println(bList.size());
		IBoardService cs =  new BoardService();
		for(  Board b : bList  ){
			cs.insertBoard(b);
		}
		System.out.println("has inserted£º" + min + "-" + max);
	}

	private ResultSet query(String sql){
		return this.getDao().query(sql);
	}


	private void closeConnection(){
		this.getDao().close();
	}

	private Dao getDao() {
		return dao;
	}

	private void setDao(Dao dao) {
		this.dao = dao;
	}


}
