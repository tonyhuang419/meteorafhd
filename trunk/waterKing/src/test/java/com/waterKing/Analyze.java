package com.waterKing;

import java.util.List;

import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.exam.ExamBaseTest;
import com.waterking.entity.BoardDetail;
import com.waterking.service.ICommonService;


@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class Analyze extends ExamBaseTest {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;


	//	@Test
	public void testBoard() {
		Long count = (Long)commonService.uniqueResult("select count(*) from Board b " );
		System.out.println(count);
	}

	//	@Test
	public void testBoardDetail() {
		Long count = (Long)commonService.uniqueResult("select count(*) from BoardDetail b " );
		System.out.println(count);
	}


	@Test
	public void testSearchMessageIndex(){
		Session session = commonService.getSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
//		Transaction tx = fullTextSession.beginTransaction();
		int i=500;
		List<BoardDetail> boardDetailList;
		while(i<1100000){
			boardDetailList = commonService.listHql("from BoardDetail bd where bd.id <" + i,null);
			for (BoardDetail bd : boardDetailList) {
				fullTextSession.index(bd);
			}
			i+=500;
		}
//		tx.commit(); //index is written at commit time   
	}

//	@Test
	public void testSearchMessage(){
		Session session = commonService.getSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);

		QueryParser parser = new QueryParser("postMessage", new ChineseAnalyzer());   
		//Transaction tx = fullTextSession.beginTransaction();   
		try {
			Query query = fullTextSession.createFullTextQuery(parser.parse("蛙泳"),   BoardDetail.class);
			List result = query.list();  
			for (int i = 0; result != null && i < result.size(); i++)   {   
				BoardDetail bd = (BoardDetail) result.get(i);   
				System.out.print("id：" + bd.getPostId()+"|");   
				System.out.println("message：" + bd.getPostMessage());   
			} 
		} catch (ParseException e) {
			e.printStackTrace();
		}    

		//tx.commit();   
		//fullTextSession.close(); 
	}
}


