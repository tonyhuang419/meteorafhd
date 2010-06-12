package waterKingCovToNoSql.service.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import waterKingCovToNoSql.entity.Board;
import waterKingCovToNoSql.tools.Dao;


public class WaterService {

	protected  Log logger = LogFactory.getLog(this.getClass());

	private Dao dao = new Dao();

	public List<Board> doGetNotFinishBoardDetailList(int min , int max){
		/**
		 * not vote floor and lastScanFloor > 1
		 */
		String sql ="select * from Board b where b.lastScanFloor > 1 and b.isVote = false  " + //and b.skip = false
		" and b.id >" + min+" and b.id <= "+ max + " order by b.id asc";
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



	public ResultSet query(String sql){
		return this.getDao().query(sql);
	}


	public void closeConnection(){
		this.getDao().close();
	}

	public static void main(String args[]){
		WaterService ws = new WaterService();


	}

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}


}
