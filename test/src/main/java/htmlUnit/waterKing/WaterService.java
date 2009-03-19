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
			" insert into BOARD(topic,topicUrl,starter,issueDate,replyNum, readNum,lastScanTime ,  raedLevel) values (?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, board.getTopic());
			preparedStatement.setString(2, board.getTopicUrl());
			preparedStatement.setString(3, board.getStarter());
			preparedStatement.setTimestamp(4, new java.sql.Timestamp(board.getIssueDate().getTime()));
			preparedStatement.setLong(5, board.getReplyNum());
			preparedStatement.setLong(6, board.getReadNum());
			preparedStatement.setTimestamp(7, new java.sql.Timestamp(board.getLastScanTime().getTime()));
			preparedStatement.setLong(8, board.getRaedLevel());
			preparedStatement.executeUpdate();
		}catch(SQLException sqle){
			System.out.println("save error");
			logger.error("save error: "+ board.getTopic()+"|"+ board.getTopicUrl());
			logger.error("exception: " + sqle.toString());
			sqle.printStackTrace();
		}
	}


	public void saveBoardDetail(BoardDetail boardDetail) {
		Connection con = dao.getCon();
		try{
			PreparedStatement preparedStatement = con.prepareStatement(
					" insert into Board_Detail(floor,topic,postId,postTime,postMessage, faceNum,faceDetail , " +
			"pictureNum , pictureDetail , postMessageLength) values (?,?,?,?,?,?,?,?,?,?)");
			
			preparedStatement.setString(1, boardDetail.getFloor());
			preparedStatement.setString(2, boardDetail.getTopic());
			preparedStatement.setString(3, boardDetail.getPostId());
			preparedStatement.setTimestamp(4, new java.sql.Timestamp(boardDetail.getPostTime().getTime()));
			preparedStatement.setString(5, boardDetail.getPostMessage());
			preparedStatement.setLong(6, boardDetail.getFaceNum());
			preparedStatement.setString(7,boardDetail.getFaceDetail());
			preparedStatement.setLong(8, boardDetail.getPictureNum());
			preparedStatement.setString(9, boardDetail.getPictureDetail());
			preparedStatement.setLong(10, boardDetail.getPostMessageLength());
			
			preparedStatement.executeUpdate();
		}catch(SQLException sqle){
			System.out.println("save error");
			logger.error("save error: "+ boardDetail.getTopic()+"|"+ boardDetail.getFloor()+boardDetail.getPostMessage());
			logger.error("exception: " + sqle.toString());
			sqle.printStackTrace();
		}
	}

	
	public void saveBoardList(List<Board> listBoard){
		for(Board b:listBoard){
			this.saveBoard(b);
		}
	}
	
	
	public void saveBoardDetailList(List<BoardDetail> listBoardDetail){
		for(BoardDetail b:listBoardDetail){
			this.saveBoardDetail(b);
		}
	}

	public ResultSet query(String sql){
		return this.getDao().query(sql);
	}


	public void closeConnection(){
		this.getDao().close();
	}

	public static void main(String args[]){
		WaterService ws = new WaterService();
		
//		Board board = new Board();
//		board.setIssueDate(new Date());
//		board.setLastScanTime(new Date());
//		board.setLastScanFloor(0L);
//		board.setReplyNum(4444L);
//		board.setReadNum(4444L);
//		board.setStarter("3333");
//		board.setTopic("22222");
//		board.setTopicUrl("888");
//		board.setLastScanFloor(10L);
//		board.setRaedLevel(10L);
//		new WaterService().saveBoard(board);
//		System.out.println("add Board success");
		
		
		BoardDetail boardDetail = new BoardDetail();
		boardDetail.setFaceDetail("FaceDetail");
		boardDetail.setFaceNum(12L);
		boardDetail.setFloor("floor");
		boardDetail.setPictureDetail("pictureDetail");
		boardDetail.setPictureNum(3L);
		boardDetail.setPostId("postId");
		boardDetail.setPostMessage("postMessage");
		boardDetail.setPostMessageLength(100L);
		boardDetail.setPostTime(new Date());
		boardDetail.setTopic("topic");
		ws.saveBoardDetail(boardDetail);
		System.out.println("add boardDetail success");
	}

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}


}
