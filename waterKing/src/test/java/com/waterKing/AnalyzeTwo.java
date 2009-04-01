package com.waterKing;

import java.util.List;

import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.junit.Test;

import com.waterking.entity.BoardDetail;


public class AnalyzeTwo  {

	@Test
	public void testSearchMessageIndex(){
		Session session = HibernateUtil.getSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		long len = 50;
		long min = 59150;
		List<BoardDetail> boardDetailList;
		while(min<1040000){
			Transaction tx = fullTextSession.beginTransaction();
			boardDetailList = session.createQuery("from BoardDetail bd where " +
					" bd.id >"+min+" and bd.id <=" +(min+len)).list();
			for (BoardDetail bd : boardDetailList) {
				fullTextSession.index(bd);
			}
			System.out.println(min);
			min+=len;
			tx.commit(); //index is written at commit time   
		}
		fullTextSession.close();
	}


//	@Test
	public void testSearchMessage(){
		Session session = HibernateUtil.getSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);

		QueryParser parser = new QueryParser("postMessage", new ChineseAnalyzer());   
		Transaction tx = fullTextSession.beginTransaction();   
		try {
			Query query = fullTextSession.createFullTextQuery(parser.parse("蛙泳"),   BoardDetail.class);
			List result = query.list();  
			System.out.println(result.size());
			for (int i = 0; result != null && i < result.size(); i++)   {   
				BoardDetail bd = (BoardDetail) result.get(i);   
				System.out.print("id：" + bd.getPostId()+"|");   
				System.out.println("message：" + bd.getPostMessage());   
			} 
		} catch (ParseException e) {
			e.printStackTrace();
		}    
		tx.commit();   
		fullTextSession.close();
	}

}


