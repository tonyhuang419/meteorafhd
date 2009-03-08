package htmlUnit.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


/**
 * 类ExcelTableExport.java的实现描述：导出excel表格
 */
public class ExcelTableExport implements TableExport {

	private List<ExcelRow> exlObject = new ArrayList<ExcelRow>();
	private String sheetName;
	private String[] titleArray;


	public void addTitle(String[] strArray)  {
		titleArray = strArray;
	}

	public void addRow(Object[] row, DataFormat[] dataFormats) {
		ExcelRow er = new ExcelRow(row,dataFormats);
		exlObject.add(er);		
	}

	public void addRow(Object[] row) {
		ExcelRow er = new ExcelRow(row,null);
		exlObject.add(er);
	}

	public void addRows(List<Object[]> rows, DataFormat[] dataFormats) {
		for(Object[] oArrar:rows ){
			ExcelRow er = new ExcelRow(oArrar,dataFormats);
			exlObject.add(er);
		}		
	}

	public void addRows(List<Object[]> rows) {
		for(Object[] oArrar:rows ){
			ExcelRow er = new ExcelRow(oArrar,null);
			exlObject.add(er);
		}		
	}
	
	public void export(String fileName) {
		File file = new File(fileName);
		try {
			this.export(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void export(OutputStream output) {
		WritableWorkbook workbook;
		WritableSheet sheetX;
		if(sheetName == null){
			sheetName = "未定义";
		}
		try {
			workbook = Workbook.createWorkbook(output);
			sheetX = workbook.createSheet(sheetName, 0);

			int rowNum = 0;
			if(titleArray!=null && titleArray.length>0){
				this.addTitleToExl(titleArray , sheetX);
				rowNum = 1;
			}

			for(ExcelRow e : exlObject){
				this.addRowToExl(e,sheetX,rowNum);	
				rowNum++;
			}
			workbook.write();
			workbook.close();

		}catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}



	public String getTableName() {
		return sheetName;	
	}

	public void setTableName(String tableName) {
		sheetName = tableName;	
	}


	private void addTitleToExl(String[] titleArray ,  WritableSheet sheet) throws Exception {
		WritableFont font= new WritableFont(WritableFont.ARIAL,10,WritableFont.BOLD); 
		WritableCellFormat format=new WritableCellFormat(font);
		format.setAlignment(jxl.format.Alignment.CENTRE);
		int i = 0;
		for(String str:titleArray){
			sheet.addCell(new Label(i, 0 , str ,format ));
			i++;
		}
	}

	private void addRowToExl(ExcelRow er , WritableSheet sheet , int rowNum ) throws Exception {
		int colNum = 0;
		Object[] objArray = er.getRowData();
		for(Object obj:objArray){
			if( obj != null){
				if(String.class.isAssignableFrom(obj.getClass())){
					//字符串
					sheet.addCell(new Label(colNum, rowNum , obj.toString()));
				}else if(Long.class.isAssignableFrom(obj.getClass())
					||Integer.class.isAssignableFrom(obj.getClass())){
					//整形
					WritableCellFormat floatFormat = new WritableCellFormat(NumberFormats.INTEGER); 
					jxl.write.Number floatNumber = new jxl.write.Number(colNum, rowNum , ((Number)obj).doubleValue(), floatFormat); 
					sheet.addCell(floatNumber);					
				}else if(Number.class.isAssignableFrom(obj.getClass())){
					//浮点型
					WritableCellFormat floatFormat = new WritableCellFormat(NumberFormats.FLOAT); 
					jxl.write.Number floatNumber = new jxl.write.Number(colNum, rowNum , ((Number)obj).doubleValue(), floatFormat); 
					sheet.addCell(floatNumber);
				}else if(Date.class.isAssignableFrom(obj.getClass())){
					//日期型
					//sheet.addCell(new jxl.write.DateTime(colNum, rowNum , new Date()));//写入后，日期不对，先用string代替
					sheet.addCell(new Label(colNum, rowNum , obj.toString()));
				}else{
					sheet.addCell(new Label(colNum, rowNum , obj.toString()));
				}
			}
			colNum++;
		}
	}


	private class  ExcelRow{
		private Object[] rowData;
		private DataFormat[] df;

		public Object[] getRowData() {
			return rowData;
		}

		public void setRowData(Object[] rowData) {
			this.rowData = rowData;
		}

		public DataFormat[] getDf() {
			return df;
		}

		public void setDf(DataFormat[] df) {
			this.df = df;
		}

		public ExcelRow(Object[] rowData,DataFormat[] df){
			this.rowData = rowData;
			this.df = df;
		}
	}

}
