package waterKingCovToNoSql.test;

import waterKingCovToNoSql.service.ICommonService;
import waterKingCovToNoSql.service.impl.CommonService;


public class TestCommonService {

	public void testReadRow(){
		ICommonService cs =  new CommonService();
		cs.readRow("board" , "1");	
	}
	
	private int testCountRow(){
		ICommonService cs =  new CommonService();
		return cs.countRow("board");
	}
	
	
	public static void main(String[] args) {
		TestCommonService tc = new TestCommonService();
		tc.testReadRow();
		System.out.println(tc.testCountRow());
		
	}
	
}
