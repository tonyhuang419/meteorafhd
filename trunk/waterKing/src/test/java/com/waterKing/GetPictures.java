package com.waterKing;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.exam.ExamBaseTest;
import com.waterking.service.ICommonService;
import com.waterking.utils.DownloadUtil;


@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class GetPictures extends ExamBaseTest {

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		commonService;


	@Test
	public void testBoardDetail() {
		String urlArray[];
		long len = 1000;
		long min = 0;
		ExecutorService	exec = Executors.newFixedThreadPool(10);
		while(min<1040000){
			List<String> list = commonService.listHql("select b.pictureDetail " +
					"from BoardDetail b where b.pictureNum>0 and b.id > "+min+" and b.id<= "+ (min+len) ,null);
			for(String strArray:list){
				urlArray = strArray.split("\\*\\*\\*");
				for(int i=0;i<urlArray.length;i++){
//					System.out.println(urlArray[i]);
					exec.execute(new DownloadUtil(urlArray[i]));
				}
			}
			min+=len;
		}
		exec.shutdown();
	}

}


