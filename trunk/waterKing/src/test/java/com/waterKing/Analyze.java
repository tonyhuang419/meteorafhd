package com.waterKing;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.exam.ExamBaseTest;
import com.waterking.service.ICommonService;


@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class Analyze extends ExamBaseTest {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;


	@Test
	public void testBoard() {
		Long count = (Long)commonService.uniqueResult("select count(*) from Board b " );
		System.out.println(count);
	}

	@Test
	public void testBoardDetail() {
		Long count = (Long)commonService.uniqueResult("select count(*) from BoardDetail b " );
		System.out.println(count);
	}
}


