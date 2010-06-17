package waterKingCovToNoSql.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.cassandra.service.Column;
import org.apache.cassandra.service.ColumnOrSuperColumn;
import org.apache.cassandra.service.ColumnParent;
import org.apache.cassandra.service.ColumnPath;
import org.apache.cassandra.service.KeySlice;
import org.apache.cassandra.service.SlicePredicate;
import org.apache.cassandra.service.SliceRange;
import org.apache.cassandra.service.Cassandra.Client;

import waterKingCovToNoSql.service.ICommonService;
import waterKingCovToNoSql.tools.UtilTools;

public class CommonService implements ICommonService {

	@Override
	public void readRow( Object obj  , String rowName , String key){ 
		Client client = UtilTools.getCassandraClient();
		ColumnParent cp = new ColumnParent(rowName, null);
		int size =  UtilTools.getColumnPath( obj , "board" ).size();
		SlicePredicate predicate = new SlicePredicate(null, new SliceRange(new byte[0], new byte[0], false, size));
		try{
			List<ColumnOrSuperColumn> results = client.get_slice(UtilTools.getKeyspace(), key , cp, predicate, 1);
			for (ColumnOrSuperColumn result : results){
				Column column = result.column;
				System.out.println(new String(column.name, "UTF-8") + " -> " + new String(column.value, "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void readRowsByRange( Object obj  , String rowName , String startKey , String endKey){
		Client client = UtilTools.getCassandraClient();
		ColumnParent cp = new ColumnParent(rowName, null);
		int size =  UtilTools.getColumnPath( obj , "board" ).size();
		SlicePredicate predicate = new SlicePredicate(null, new SliceRange(new byte[0], new byte[0], false, size));
		try{
			List<KeySlice> results =   client.get_range_slice(UtilTools.getKeyspace() , cp , predicate , startKey , endKey , size ,  1 );
			for (KeySlice result : results){
				List<ColumnOrSuperColumn>  colunms = result.columns;
				for (ColumnOrSuperColumn colunm  : colunms){
					Column column = colunm.column;
					System.out.println(new String(column.name, "UTF-8") + " -> " + new String(column.value, "UTF-8"));
				}
				System.out.println("============");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int countRow(String rowName){
		Client client = UtilTools.getCassandraClient();
		ColumnParent cp = new ColumnParent(rowName, null);
		try{
			return client.get_count(UtilTools.getKeyspace(), "1", cp, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public boolean insert( String ksp, String key ,
			long timestamp, int consistency_level , Map<ColumnPath,String> map ){
		Client client = UtilTools.getCassandraClient();
		try{
			for(ColumnPath _cp : map.keySet()){
				String _value = map.get(_cp);
				client.insert(UtilTools.getKeyspace(), key, _cp, _value.getBytes("UTF-8"), 1, 1);
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}


}
