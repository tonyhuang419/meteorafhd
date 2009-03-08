package htmlUnit.waterKing;

import htmlUnit.export.TableExport;
import htmlUnit.export.TableExportFactory;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Analyze {

	WaterService waterService = new WaterService();


	public void totalTopicNum(){
		String sql = " SELECT count(*) FROM  water_king WHERE 1  ";
		ResultSet rs =  waterService.query(sql);
		try {
			while (rs.next()){
				System.out.print(rs.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public void totalIdNum(){
		String sql = "select count(*) from " +
		"( SELECT count(*) FROM  water_king WHERE 1 group by starter) a";
		ResultSet rs =  waterService.query(sql);
		try {
			while (rs.next()){
				System.out.print(rs.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void mostRepliedId(){
		String sql ="select starter, sum(replyNum) ,count(`topic`) from water_king " +
		"where 1  group by starter  order by sum(`replyNum`) desc limit 0,50";
		ResultSet rs =  waterService.query(sql);
		try {
			TableExport export = TableExportFactory.createExcelTableExport();
			export.addTitle(new String[]{"ID","回复次数","主题数量"});
			export.setTableName("最多被回复ID");
			while (rs.next()){
				export.addRow(new Object[]{ rs.getString(1) , rs.getLong(2) , rs.getLong(3) });
			}
			export.export(new FileOutputStream("c:/最多被回复ID.xls"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void mostReadId(){
		String sql ="select starter, sum(readNum) ,count(`topic`) from water_king " +
		"where 1  group by starter  order by sum(`readNum`) desc limit 0,50";
		ResultSet rs =  waterService.query(sql);
		try {
			TableExport export = TableExportFactory.createExcelTableExport();
			export.addTitle(new String[]{"ID","查看次数","主题数量"});
			export.setTableName("最多被查看ID");
			while (rs.next()){
				export.addRow(new Object[]{ rs.getString(1) , rs.getLong(2) , rs.getLong(3) });
			}
			export.export(new FileOutputStream("c:/最多被查看ID.xls"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mostRepliedTopic(){
		String sql ="select `topic` ,`starter` ,`replyNum` from water_king  order by replyNum desc limit 0,50";
		ResultSet rs =  waterService.query(sql);
		try {
			TableExport export = TableExportFactory.createExcelTableExport();
			export.addTitle(new String[]{"主题","发帖人","回复"});
			export.setTableName("最多被回复主题");
			while (rs.next()){
				export.addRow(new Object[]{ rs.getString(1) , rs.getString(2) , rs.getLong(3) });
			}
			export.export(new FileOutputStream("c:/最多被回复主题.xls"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mostReadTopic(){
		String sql ="select `topic` ,`starter` ,`readNum` from water_king  order by readNum desc limit 0,50";
		ResultSet rs =  waterService.query(sql);
		try {
			TableExport export = TableExportFactory.createExcelTableExport();
			export.addTitle(new String[]{"主题","发帖人","查看"});
			export.setTableName("最多被查看主题");
			while (rs.next()){
				export.addRow(new Object[]{ rs.getString(1) , rs.getString(2) , rs.getLong(3) });
			}
			export.export(new FileOutputStream("c:/最多被查看主题.xls"));
		} catch (Exception e) {
			e.printStackTrace();
		}


	}	

	public static void main(String[] args) {
		Analyze analyze = new Analyze();
		//		analyze.totalTopicNum();
		//		analyze.totalIdNum();
		analyze.mostRepliedId();
		analyze.mostReadId();
		analyze.mostRepliedTopic();
		analyze.mostReadTopic();
	}

}
