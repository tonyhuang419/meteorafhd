package htmlUnit.waterKing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class WaterService {

	protected  Log logger = LogFactory.getLog(this.getClass());

	private Dao dao = new Dao();

	public void saveBoard(Board board) {
		Connection con = dao.getCon();
		try{
			PreparedStatement preparedStatement = con.prepareStatement(
			" insert into water_king(topic,topicUrl,starter,issueDate,replyNum, readNum,lastScanTime) values (?,?,?,?,?,?,?)");
			preparedStatement.setString(1, board.getTopic());
			preparedStatement.setString(2, board.getTopicUrl());
			preparedStatement.setString(3, board.getStarter());
			preparedStatement.setTimestamp(4, new java.sql.Timestamp(board.getIssueDate().getTime()));
			preparedStatement.setLong(5, board.getReplyNum());
			preparedStatement.setLong(6, board.getReadNum());
			preparedStatement.setTimestamp(7, new java.sql.Timestamp(board.getLastScanTime().getTime()));
			preparedStatement.executeUpdate();
		}catch(SQLException sqle){
			System.out.println("save error");
			logger.error("save error: "+board.getTopic()+"|"+ board.getTopicUrl());
			logger.error("exception: " + sqle.toString());
			sqle.printStackTrace();
		}
	}

	public ResultSet query(String sql){
		return this.getDao().query(sql);
	}


	public void saveBoardList(List<Board> listBoard){
		for(Board b:listBoard){
			this.saveBoard(b);
		}
	}

	public void closeConnection(){
		this.getDao().close();
	}

	public static void main(String args[]){
		Board board = new Board();
		board.setIssueDate(new Date());
		board.setLastScanTime(new Date());
		board.setLastScanFloor(0L);
		board.setReplyNum(4444L);
		board.setReadNum(4444L);
		board.setStarter("3333");
		board.setTopic("22222");
		board.setTopicUrl("888");
		new WaterService().saveBoard(board);
	}

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}


}
