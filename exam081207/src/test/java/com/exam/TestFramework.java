package com.exam;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.exam.entity.Teacher;
import com.exam.service.ICommonService;



public class TestFramework extends ExamBaseTest {


	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;

	
	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.prepareTestInstance();
		super.setDefaultRollback(true);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		List<Teacher> tList = commonService.list("from Teacher");
		System.out.println(tList.size());
	}
	
}


