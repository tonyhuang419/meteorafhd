package htmlUnit.waterKing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class WaterService {

	protected  Log logger = LogFactory.getLog(this.getClass());

	private Dao dao = new Dao();

	public void saveBoard(Board board , User user) {
		Connection con = dao.getCon();
		try{
			PreparedStatement preparedStatement = con.prepareStatement(
					" insert into BOARD(topic,topicUrl,starter,issueDate," +
			" replyNum, readNum,lastScanTime , readLevel , isVote , lastScanFloor , lastUpateUser ) values (?,?,?,?,?,?,?,?,?,?,?)");
			preparedStatement.setString(1, board.getTopic());
			preparedStatement.setString(2, board.getTopicUrl());
			preparedStatement.setString(3, board.getStarter());
			preparedStatement.setTimestamp(4, new java.sql.Timestamp(board.getIssueDate().getTime()));
			preparedStatement.setLong(5, board.getReplyNum());
			preparedStatement.setLong(6, board.getReadNum());
			preparedStatement.setTimestamp(7, new java.sql.Timestamp(board.getLastScanTime().getTime()));
			preparedStatement.setLong(8, board.getReadLevel());
			preparedStatement.setBoolean(9, board.getIsVote());
			preparedStatement.setLong(10,board.getLastScanFloor());
			preparedStatement.setString(11,user.getUsername());
			preparedStatement.executeUpdate();
		}catch(SQLException sqle){
			logger.error("save error");
			logger.error("save board error: "+ board.getTopic()+"|"+ board.getTopicUrl());
			logger.error("exception: " + sqle.toString());
			sqle.printStackTrace();
		}
	}


	public void saveBoardDetail(BoardDetail boardDetail , User user) {
		Connection con = dao.getCon();
		try{
			PreparedStatement preparedStatement = con.prepareStatement(
					" insert into Board_Detail(floor,topic,postId,postTime,postMessage, faceNum,faceDetail , " +
			"pictureNum , pictureDetail , postMessageLength ,lastScanTime ,lastUpateUser ) values (?,?,?,?,?,?,?,?,?,?,?,?)");

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
			preparedStatement.setTimestamp(11, new java.sql.Timestamp(System.currentTimeMillis()));
			preparedStatement.setString(12, user.getUsername());
			preparedStatement.executeUpdate();
		}catch(SQLException sqle){
			logger.error("save error");
			logger.error("save board detail error: "+ boardDetail.getTopic()+"|"+ boardDetail.getFloor()+boardDetail.getPostMessage());
			logger.error("exception: " + sqle.toString());
			sqle.printStackTrace();
		}
	}


	public List<Board> doGetNotFinishBoardDetailList(){
		/**
		 * not vote floor and lastScanFloor > 1
		 */
		String sql ="select * from Board b where b.lastScanFloor > 1 and b.isVote = false and b.id>20000 order by b.id asc";
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

	
	public List<BoardDetail> doGetBoardDetail(){
		/**
		 * not vote floor and lastScanFloor > 1
		 */
		String sql =" select * from board_detail bd where bd.id = 151";
		ResultSet rs =  this.query(sql);
		List<BoardDetail> boardDetailList = new ArrayList<BoardDetail>();
		BoardDetail boardDetail;
		try {
			while (rs.next()){
				boardDetail = new BoardDetail();
//				board.setId(rs.getLong(1));
				System.out.println(rs.getString(6));
				boardDetailList.add(boardDetail);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return boardDetailList;
	}
	

	public void saveBoardList(List<Board> listBoard , User user){
		for(Board b:listBoard){
			this.saveBoard(b , user);
		}
	}


	public void saveBoardDetailList(List<BoardDetail> listBoardDetail ,User user){
		for(BoardDetail b:listBoardDetail){
			this.saveBoardDetail(b , user );
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


		//		BoardDetail boardDetail = new BoardDetail();
		//		boardDetail.setFaceDetail("FaceDetail");
		//		boardDetail.setFaceNum(12L);
		//		boardDetail.setFloor("floor");
		//		boardDetail.setPictureDetail("pictureDetail");
		//		boardDetail.setPictureNum(3L);
		//		boardDetail.setPostId("postId");
		//		boardDetail.setPostMessage("postMessage");
		//		boardDetail.setPostMessageLength(100L);
		//		boardDetail.setPostTime(new Date());
		//		boardDetail.setTopic("topic");
		//		ws.saveBoardDetail(boardDetail);
		//		System.out.println("add boardDetail success");

//		System.out.println(ws.doGetNotFinishBoardDetailList().size());
		
		ws.doGetBoardDetail();

	}

	public Dao getDao() {
		return dao;
	}

	public void setDao(Dao dao) {
		this.dao = dao;
	}


}
