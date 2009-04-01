package com.waterKing;

import java.io.FileOutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.exam.ExamBaseTest;
import com.waterking.entity.BoardDetail;
import com.waterking.service.ICommonService;
import com.waterking.tools.excel.TableExport;
import com.waterking.tools.excel.TableExportFactory;


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

	//cc's pig
	@Test
	public void testCCsPig() {
		List<BoardDetail> boardDetailList = 
			commonService.listHql(" from BoardDetail bd " +
					"where bd.postId='kouhoutei' and  bd.faceDetail like '%93.gif%' order by bd.postTime desc",null );
		try {
			TableExport export = TableExportFactory.createExcelTableExport();
			export.addTitle(new String[]{"发帖时间","主题", "楼层" , "数量"});
			export.setTableName("cc's pig");
			for ( BoardDetail b:  boardDetailList){
				String s[] = b.getFaceDetail().split("93.gif");
				export.addRow(new Object[]{ 
						b.getPostTime().toString() ,
						b.getTopic() , 
//						b.getPostMessage(),
						b.getFloorNum(),
						s.length-1 });
			}
			export.export(new FileOutputStream("c:/cc's pig.xls"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


