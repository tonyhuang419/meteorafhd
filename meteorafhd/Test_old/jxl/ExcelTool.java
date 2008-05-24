package jxl;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import jxl.format.UnderlineStyle;
import jxl.write.WritableFont;

public class ExcelTool {
	public void read(File file,int row,int column){
		try
		{
			//����Workbook����, ֻ��Workbook����
			//ֱ�Ӵӱ����ļ�����Workbook
			//������������Workbook
			InputStream is = new FileInputStream(file);
			jxl.Workbook rwb = Workbook.getWorkbook(is);

			Sheet rs = rwb.getSheet(0);
			System.out.println("rows:"+rs.getRows());
			System.out.println("columns:"+rs.getColumns());
			for(int i=0;i<column;i++){
				for(int j=0;j<row;j++){
					Cell c = rs.getCell(i,j);
					String s = c.getContents();
					System.out.print(s +":" + c.getType() + "   ");
				}
				System.out.println();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void create(String filename){
		try
		{
			//����Workbook����, ֻ��Workbook����
			//Method 1��������д���Excel������
			jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(new File(filename));
			//Method 2����WritableWorkbookֱ��д�뵽�����
			/*
		    OutputStream os = new FileOutputStream(targetfile);
		    jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
			 */

			//����Excel������
			jxl.write.WritableSheet ws = wwb.createSheet("Test Sheet 1", 0);

			//1.���Label����
			jxl.write.Label labelC = new jxl.write.Label(0, 0, "This is a Label cell");
			ws.addCell(labelC);
			
			//��Ӵ�������Formatting�Ķ���
			jxl.write.WritableFont wf = new jxl.write.WritableFont(WritableFont.TIMES, 18, WritableFont.BOLD, true);
			jxl.write.WritableCellFormat wcfF = new jxl.write.WritableCellFormat(wf);
			jxl.write.Label labelCF = new jxl.write.Label(1, 0, "This is a Label Cell", wcfF);
			ws.addCell(labelCF);
			
			//��Ӵ���������ɫFormatting�Ķ���
			jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.RED);
			jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc);
			jxl.write.Label labelCFC = new jxl.write.Label(2, 0, "This is a Label Cell", wcfFC);
			ws.addCell(labelCFC);
			
			//2.���Number����
			jxl.write.Number labelN = new jxl.write.Number(0, 1, 3.1415926);
			ws.addCell(labelN);
			
			//��Ӵ���formatting��Number����
			jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#.##");
			jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf);
			jxl.write.Number labelNF = new jxl.write.Number(1, 1, 3.1415926, wcfN);
			ws.addCell(labelNF);
			
			//3.���Boolean����
			jxl.write.Boolean labelB = new jxl.write.Boolean(0, 2, false);
			ws.addCell(labelB);
			
			//4.���DateTime����
			jxl.write.DateTime labelDT = new jxl.write.DateTime(0, 3, new java.util.Date());
			ws.addCell(labelDT);
			
			//��Ӵ���formatting��DateFormat����
			jxl.write.DateFormat df = new jxl.write.DateFormat("dd MM yyyy hh:mm:ss");
			jxl.write.WritableCellFormat wcfDF = new jxl.write.WritableCellFormat(df);
			jxl.write.DateTime labelDTF = new jxl.write.DateTime(1, 3, new java.util.Date(), wcfDF);
			ws.addCell(labelDTF);
			
			//д��Exel������
			wwb.write();
			//�ر�Excel����������
			wwb.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}