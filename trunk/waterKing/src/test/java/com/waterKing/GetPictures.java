package com.waterKing;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.exam.ExamBaseTest;
import com.waterking.entity.BoardDetail;
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
		while(min<1000){
			List<BoardDetail> boardDetailList = commonService.listHql(" from BoardDetail b where b.pictureNum>0 and b.id > "+min+" and b.id<= "+ (min+len) ,null);
			for(BoardDetail bd:boardDetailList){
				urlArray = bd.getPictureDetail().split("\\*\\*\\*");
				for(int i=0;i<urlArray.length;i++){
					downUrl = urlArray[i];
					if(downUrl.indexOf("taisha")!=-1){
						System.out.println("now download : "+downUrl);
						new DownloadUtil().downFile(downUrl , bd.getTopic());
					}
				}
			}
			min+=len;
		}
	}
}




