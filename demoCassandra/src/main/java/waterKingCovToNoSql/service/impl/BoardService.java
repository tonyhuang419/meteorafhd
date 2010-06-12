package waterKingCovToNoSql.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.cassandra.service.ColumnPath;

import waterKingCovToNoSql.entity.Board;
import waterKingCovToNoSql.service.IBoardService;
import waterKingCovToNoSql.service.ICommonService;
import waterKingCovToNoSql.tools.StringUtils;
import waterKingCovToNoSql.tools.UtilTools;

public class BoardService implements IBoardService {

	@Override
	public void insertBoard( Board board ){
		Map<String,ColumnPath> map = UtilTools.getColumnPath(board,"board");
		try {
			Map<ColumnPath,String> m = new HashMap<ColumnPath,String>();
			String id = StringUtils.getEmptyString(board.getId());
			String topic = StringUtils.getEmptyString(board.getTopic());
			String topicUrl = StringUtils.getEmptyString(board.getTopicUrl());
			String starter = StringUtils.getEmptyString(board.getStarter());
			String issueDate = StringUtils.getEmptyString(board.getIssueDate());
			String replyNum = StringUtils.getEmptyString(board.getReplyNum());
			String readNum = StringUtils.getEmptyString(board.getReadNum());
			String readLevel = StringUtils.getEmptyString(board.getReadLevel());
			String isVote = StringUtils.getEmptyString(board.getIsVote());
			String lastScanFloor = StringUtils.getEmptyString(board.getLastScanFloor());
			String lastScanTime = StringUtils.getEmptyString(board.getLastScanTime());
			String lastUpateUser = StringUtils.getEmptyString(board.getLastUpateUser());
			String skip = StringUtils.getEmptyString(board.getSkip());
			m.put(map.get("id"), id.toString());
			m.put(map.get("topic"), topic);
			m.put(map.get("topicUrl"), topicUrl);
			m.put(map.get("starter"), starter);
			m.put(map.get("issueDate"), issueDate);
			m.put(map.get("replyNum"), replyNum);
			m.put(map.get("readNum"), readNum);
			m.put(map.get("readLevel"), readLevel);
			m.put(map.get("isVote"), isVote);
			m.put(map.get("lastScanFloor"), lastScanFloor);
			m.put(map.get("lastScanTime"), lastScanTime);
			m.put(map.get("lastUpateUser"), lastUpateUser);
			m.put(map.get("skip"), skip);
			ICommonService cs = new CommonService();
			cs.insert(UtilTools.getKeyspace(), id, 1, 1, m);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
