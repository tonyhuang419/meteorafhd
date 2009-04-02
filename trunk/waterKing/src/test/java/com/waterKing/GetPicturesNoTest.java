package com.waterKing;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.waterking.service.ICommonService;
import com.waterking.utils.DownloadUtil;
import com.waterking.utils.Tools;


public class GetPicturesNoTest {


	private ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	private ICommonService 		commonService;

	public void testBoardDetail() {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		commonService = (ICommonService)ctx.getBean("commonService");
		String urlArray[];
		String downUrl;
		long len = 1000;
		long min = Tools.getScanDetailNum();
		ExecutorService	exec = Executors.newFixedThreadPool(3);
		while(min<1040000){
			List<Object[]> boardDetailList = commonService.listHql(" select b.topic , b.postId  ,b.pictureDetail ,b.id" +
					" from BoardDetail b where b.pictureNum>0 and b.id > "+min+" and b.id<= "+ (min+len) ,null);
			for(Object obj[]:boardDetailList){
				urlArray = obj[2].toString().split("\\*\\*\\*");
				for(int i=0;i<urlArray.length;i++){
					downUrl = urlArray[i];
					if(downUrl.indexOf("taisha")!=-1 
							&& downUrl.indexOf(".bmp")==-1
							&& obj[1].toString().indexOf("贴图专")==-1){
						System.out.println("now download : "+downUrl);
						System.out.println("board detail id :" + obj[3]);
						new DownloadUtil().downFile(downUrl , obj[1].toString() , obj[0].toString());
					}
				}
			}

		}
		exec.shutdown();
	}

	public static void main(String args[]) throws Exception{
		new GetPicturesNoTest().testBoardDetail();
	}

}




