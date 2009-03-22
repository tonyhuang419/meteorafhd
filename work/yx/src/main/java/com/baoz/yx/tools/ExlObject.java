package com.baoz.yx.tools;

import java.io.File;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

class Stu{
	int age;
	String name;
	public Stu(int age,String name) {
		this.name = name;
		this.age = age;
	}
}

public class ExlObject {
	private WritableWorkbook workbook;
	private WritableSheet sheetName;

	public ExlObject(String fileNameX,String sheetNameX) throws Exception {
		fileNameX = fileNameX + ".xls";
		workbook = Workbook.createWorkbook(new File(fileNameX));
		sheetName = workbook.createSheet(sheetNameX, 0);
	}
	
	public void setTitle(String[] strArray) throws Exception {
		int i = 0;
		for(String str:strArray){
			sheetName.addCell(new Label(i, 0 , str ));
			i++;
		}
	}

	public void addRow(Object[] objArray,int rowNum) throws Exception {
		int colNum = 0;
		for(Object obj:objArray){
			if( obj == null){
				sheetName.addCell(new Label(colNum, rowNum , "" ));
			}
			else{
				sheetName.addCell(new Label(colNum, rowNum , obj.toString() ));
			}
			colNum++;
		}
	}	

	public void addRows(List<Object[]> lObj) throws Exception {
		int rowNum = 1;						//0行未标题列
		for(Object[] objArray: lObj){
			this.addRow(objArray , rowNum);
			rowNum++;
		}
	}

	public void outputExcel() throws Exception{
		workbook.write();
		workbook.close();
	}	

//	public static void main(String[] args) throws Exception {
////	System.out.println("1");
//	ExlObject e = new ExlObject("newtest","s");
//	e.addRow(new Stu(19,"tom")); 
//	e.outputExcel();
//	}

}
