package htmlUnit.waterKing;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

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
			System.out.print(StringUtils.rightPad("ID", 20));
			System.out.print(StringUtils.rightPad("回复次数",20));
			System.out.println(StringUtils.rightPad("主题数量",20));
			while (rs.next()){
				System.out.print(StringUtils.rightPad(rs.getString(1),20));
				System.out.print(StringUtils.rightPad(String.valueOf(rs.getLong(2)),20));
				System.out.println(StringUtils.rightPad(String.valueOf(rs.getLong(3)),20));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void mostReadId(){
		String sql ="select starter, sum(readNum) ,count(`topic`) from water_king " +
		"where 1  group by starter  order by sum(`readNum`) desc limit 0,50";
		ResultSet rs =  waterService.query(sql);
		try {
			System.out.print(StringUtils.rightPad("ID",20));
			System.out.print(StringUtils.rightPad("查看次数",20));
			System.out.println(StringUtils.rightPad("主题数量",20));
			while (rs.next()){
				System.out.print(StringUtils.rightPad(rs.getString(1),20));
				System.out.print(StringUtils.rightPad(String.valueOf(rs.getLong(2)),20));
				System.out.println(StringUtils.rightPad(String.valueOf(rs.getLong(3)),20));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void mostRepliedTopic(){
		String sql ="select `topic` ,`starter` ,`replyNum` from water_king  order by replyNum desc limit 0,50";
		ResultSet rs =  waterService.query(sql);
		try {
			System.out.print(StringUtils.rightPad("主题",100));
			System.out.print(StringUtils.rightPad("发帖人",20));
			System.out.println(StringUtils.rightPad("回复",20));
			while (rs.next()){
				System.out.print(StringUtils.rightPad(rs.getString(1),100));
				System.out.print(StringUtils.rightPad(rs.getString(2),20));
				System.out.println(StringUtils.rightPad(String.valueOf(rs.getLong(3)),20));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void mostReadTopic(){
		String sql ="select `topic` ,`starter` ,`readNum` from water_king  order by readNum desc limit 0,50";
		ResultSet rs =  waterService.query(sql);
		try {
			System.out.print(StringUtils.rightPad("主题",100));
			System.out.print(StringUtils.rightPad("发帖人",20));
			System.out.println(StringUtils.rightPad("查看",20));
			while (rs.next()){
				System.out.print(StringUtils.rightPad(rs.getString(1),100));
				System.out.print(StringUtils.rightPad(rs.getString(2),20));
				System.out.println(StringUtils.rightPad(String.valueOf(rs.getLong(3)),20));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	public static void main(String[] args) {
		Analyze analyze = new Analyze();
//		analyze.totalTopicNum();
//		analyze.totalIdNum();
//		analyze.mostRepliedId();
//		analyze.mostReadId();
//		analyze.mostRepliedTopic();
		analyze.mostReadTopic();
	}

}
