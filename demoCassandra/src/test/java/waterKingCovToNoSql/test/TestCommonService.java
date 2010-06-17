package waterKingCovToNoSql.test;

import waterKingCovToNoSql.entity.Board;
import waterKingCovToNoSql.service.ICommonService;
import waterKingCovToNoSql.service.impl.CommonService;


public class TestCommonService {

	public void testReadRow(){
		ICommonService cs =  new CommonService();
		cs.readRow( new Board() , "board" , "12345");	
	}
	
	private int testCountRow(){
		ICommonService cs =  new CommonService();
		return cs.countRow("board");
	}
	
	private void testReadListRow(){
		ICommonService cs =  new CommonService();
		cs.readRowsByRange( new Board() , "board" , "10000" , "10100");	
	}
	
	public static void main(String[] args) {
		TestCommonService tc = new TestCommonService();
//		tc.testReadRow();
//		System.out.println(tc.testCountRow());
		tc.testReadListRow();
		
	}
	
}
