package htmlUnit.export;


/**
 * 类TableExportFactory.java的实现描述：导出类的工厂类 
 */
public abstract class TableExportFactory {
	public static TableExport createExcelTableExport(){
		return new ExcelTableExport();
	}
}
