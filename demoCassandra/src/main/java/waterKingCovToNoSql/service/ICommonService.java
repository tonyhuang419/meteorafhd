package waterKingCovToNoSql.service;

import java.util.Map;

import org.apache.cassandra.service.ColumnPath;


public interface ICommonService {

	public void readRow( Object obj  , String rowName , String key);
	
	public void readRowsByRange( Object obj  , String rowName , String startKey , String endKey);
	
	public int countRow(String rowName);
	
	public boolean insert( String ksp, String key ,
			long timestamp, int consistency_level , Map<ColumnPath,String> map );
	
	
}
