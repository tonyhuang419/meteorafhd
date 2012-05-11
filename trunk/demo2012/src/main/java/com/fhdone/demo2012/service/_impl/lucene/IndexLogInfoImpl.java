package com.fhdone.demo2012.service._impl.lucene;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fhdone.demo2012.dao.demo.UserLogDao;
import com.fhdone.demo2012.entity.UserLog;
import com.fhdone.demo2012.service.luncene.IndexLogInfoService;
import com.fhdone.demo2012.utils.lucene.LuceneUtils;
import com.fhdone.demo2012.utils.lucene.StringUtilsX;

@Service("indexLogInfoService")
public class IndexLogInfoImpl implements IndexLogInfoService {

	private Logger logger = LoggerFactory.getLogger(IndexLogInfoImpl.class);  

	@Autowired  
	public UserLogDao userLogDao;  

	public boolean indexLoginfo( ) throws Exception{
		Long maxId = userLogDao.getUserLogByMaxId();
		logger.info(maxId.toString());
		Long beg = new Long(0);
		Long end = new Long(0);
		while(end<maxId){
			beg=end;
			end+=2000;
			Map<String,Long> paras = new HashMap<String,Long>();
			paras.put("id1", beg+1 );
			paras.put("id2", end );
			List<UserLog> ul = userLogDao.getUserLogsByTwoId(paras);
			if( 0!=ul.size()){
				logger.info("{}" , ul.size());
				logger.info("{}~{}",beg,end);
				try {
					logger.info( "indexed:"+index(ul) );
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}

	private int index( List<UserLog> userLogList ) throws Exception {
		IndexWriter writer = LuceneUtils.getWriter();
		indexUserLog(writer, userLogList);
//		int numIndexed = writer.maxDoc();
		int numIndexed = writer.numDocs();
//		writer.optimize();
		writer.close();
		return numIndexed;
	}

	private void indexUserLog(IndexWriter writer, List<UserLog> userLogList ) throws IOException {
		for(UserLog u:userLogList){
			writer.deleteDocuments(new Term("id",u.getId().toString()));
			Document doc = new Document();
			if(StringUtilsX.isNotBlank(u.getId().toString())){
				doc.add(new Field("id", u.getId().toString(), Field.Store.YES, Field.Index.ANALYZED));
			}
			if(StringUtilsX.isNotBlank(u.getCompanyCd())){
				doc.add(new Field("companyCd", u.getCompanyCd(), Field.Store.YES, Field.Index.ANALYZED));
			}
			if(StringUtilsX.isNotBlank(u.getUserCd())){
				doc.add(new Field("userCd", u.getUserCd(), Field.Store.YES, Field.Index.ANALYZED));
			}
			if(StringUtilsX.isNotBlank(u.getActionName())){
				doc.add(new Field("actionName", u.getActionName(), Field.Store.YES, Field.Index.ANALYZED));
			}
			if( u.getOperationTime()!=null){
				doc.add(new Field("operationTime", u.getOperationTime().toString(), Field.Store.YES, Field.Index.ANALYZED));
			}
			if(StringUtilsX.isNotBlank(u.getParameterInfo())){
				doc.add(new Field("parameterInfo", u.getParameterInfo(), Field.Store.YES, Field.Index.ANALYZED));
			}
			writer.addDocument(doc);
		}
	}	

}
