package waterKingCovToNoSql.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.cassandra.service.ColumnPath;

import waterKingCovToNoSql.entity.BoardDetail;
import waterKingCovToNoSql.service.IBoardDetatilService;
import waterKingCovToNoSql.service.ICommonService;
import waterKingCovToNoSql.tools.StringUtils;
import waterKingCovToNoSql.tools.UtilTools;

public class BoardDetailService implements IBoardDetatilService {

	@Override
	public void insertBoardDetail( BoardDetail boardDetail ){
		Map<String,ColumnPath> map = UtilTools.getColumnPath(boardDetail,"boardDetail");
		try {
			Map<ColumnPath,String> m = new HashMap<ColumnPath,String>();
			String id = StringUtils.getEmptyString(boardDetail.getId());
			String floor = StringUtils.getEmptyString(boardDetail.getFloor());
			String topic = StringUtils.getEmptyString(boardDetail.getTopic());
			String postId = StringUtils.getEmptyString(boardDetail.getPostId());
			String postTime = StringUtils.getEmptyString(boardDetail.getPostTime());
			String postMessage = StringUtils.getEmptyString(boardDetail.getPostMessage());
			String faceNum = StringUtils.getEmptyString(boardDetail.getFaceNum());
			String faceDetail = StringUtils.getEmptyString(boardDetail.getFaceDetail());
			String pictureNum = StringUtils.getEmptyString(boardDetail.getPictureNum());
			String pictureDetail = StringUtils.getEmptyString(boardDetail.getPictureDetail());
			String postMessageLength = StringUtils.getEmptyString(boardDetail.getPostMessageLength());
			String lastScanTime = StringUtils.getEmptyString(boardDetail.getLastScanTime());
			String lastUpateUser = StringUtils.getEmptyString(boardDetail.getLastUpateUser());
			String floorNum = StringUtils.getEmptyString(boardDetail.getFaceNum());
			m.put(map.get("id"), id.toString());
			m.put(map.get("floor"), floor);
			m.put(map.get("topic"), topic);
			m.put(map.get("postId"), postId);
			m.put(map.get("postTime"), postTime);
			m.put(map.get("postMessage"), postMessage);
			m.put(map.get("faceNum"), faceNum);
			m.put(map.get("faceDetail"), faceDetail);
			m.put(map.get("pictureNum"), pictureNum);
			m.put(map.get("pictureDetail"), pictureDetail);
			m.put(map.get("postMessageLength"), postMessageLength);
			m.put(map.get("lastScanTime"), lastScanTime);
			m.put(map.get("lastUpateUser"), lastUpateUser);
			m.put(map.get("floorNum"), floorNum);
			ICommonService cs = new CommonService();
			cs.insert(UtilTools.getKeyspace(), id, 1, 1, m);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
