package htmlUnit.waterKing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class WaterService {

	public void saveBoard(Board board) {
		Connection con = this.getConnection();
		try{
			PreparedStatement preparedStatement = con.prepareStatement(
			" insert into water_king(topic,topicUrl,starter,issueDate,replyNum,lastScanTime) values (?,?,?,?,?,?)");
			preparedStatement.setString(1, board.getTopic());
			preparedStatement.setString(2, board.getTopicUrl());
			preparedStatement.setString(3, board.getStarter());
			preparedStatement.setDate(4, new java.sql.Date(board.getIssueDate().getTime()));
			preparedStatement.setLong(5, board.getReplyNum());
			preparedStatement.setDate(6, new java.sql.Date(board.getLastScanTime().getTime()));
			preparedStatement.executeUpdate();
		}catch(SQLException sqle){
			System.out.println("save error");
			sqle.printStackTrace();
		}
	}

	public void saveBoardList(List<Board> listBoard){
		for(Board b:listBoard){
			this.saveBoard(b);
		}
	}
	

	public Connection getConnection(){
		if(Dao.con!=null){
			return Dao.con;
		}
		else{
			Dao.setConnection();
			return Dao.con;
		}
	}
	
	public void closeConnection(){
		Dao.close();
	}

	public static void main(String args[]){
		Board board = new Board();
		board.setIssueDate(new Date());
		board.setLastScanTime(new Date());
		board.setReplyNum(4444L);
		board.setStarter("3333");
		board.setTopic("22222");
		board.setTopicUrl("111111");
		new WaterService().saveBoard(board);
	}


}
