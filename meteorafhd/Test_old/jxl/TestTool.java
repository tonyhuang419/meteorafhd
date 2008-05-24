package jxl;


public class TestTool {

	public static void main(String[] args) {
		ExcelTool et = new ExcelTool();
		//et.read(new File("1.xls"), 4,3 );
		et.create("f_test.xls");
		
	}
}
