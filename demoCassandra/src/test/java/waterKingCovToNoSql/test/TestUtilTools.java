package waterKingCovToNoSql.test;

import java.util.Map;

import org.apache.cassandra.service.ColumnPath;

import waterKingCovToNoSql.entity.Board;
import waterKingCovToNoSql.tools.UtilTools;

public class TestUtilTools {

	public void testGetBoardColumnPath(){
		Map<String,ColumnPath> map = UtilTools.getColumnPath(new Board() , "board" );
		 for (Map.Entry<String, ColumnPath> m : map.entrySet()) {
			 System.out.println(m.getKey());
			 System.out.println(m.getValue());
		 }
	}
	
	public static void main(String[] args) {
		TestUtilTools t = new TestUtilTools();
		t.testGetBoardColumnPath();
		t.testGetBoardColumnPath();
	}

}
