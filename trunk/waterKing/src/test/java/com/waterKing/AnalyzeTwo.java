package com.waterKing;

import java.util.List;

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
		int i=500;
		List<BoardDetail> boardDetailList;
		while(i<1100000){
			Transaction tx = fullTextSession.beginTransaction();
			boardDetailList = session.createQuery("from BoardDetail bd where bd.id <" + i).list();
			for (BoardDetail bd : boardDetailList) {
				fullTextSession.index(bd);
			}
			i+=500;
			tx.commit(); //index is written at commit time   
		}

	}

}


