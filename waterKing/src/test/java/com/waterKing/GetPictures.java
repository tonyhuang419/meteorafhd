package com.waterKing;

import java.util.List;

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
		String downUrl;
		long len = 1000;
		long min = 0;
		while(min<1040000){
			List<Object[]> boardDetailList = commonService.listHql(" select b.topic , b.postId  ,b.pictureDetail ,b.id" +
					" from BoardDetail b where b.pictureNum>0 and b.id > "+min+" and b.id<= "+ (min+len) ,null);
			for(Object obj[]:boardDetailList){
				urlArray = obj[2].toString().split("\\*\\*\\*");
				for(int i=0;i<urlArray.length;i++){
					downUrl = urlArray[i];
					if(downUrl.indexOf("taisha")!=-1){
						System.out.println("now download : "+downUrl);
						System.out.println("board detail id :" + obj[3]);
						new DownloadUtil().downFile(downUrl , obj[1].toString() , obj[0].toString());
					}
				}
			}
			min+=len;
		}
	}
}




