package waterKingCovToNoSql.tools;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.cassandra.service.Cassandra;
import org.apache.cassandra.service.ColumnPath;
import org.apache.cassandra.service.Cassandra.Client;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class UtilTools {

	
	static private Client cassandraClient;
	static private final String keyspace = "WATER_KING";
	static private Map<String,Map<String,ColumnPath>> columnPathMap = new HashMap<String,Map<String,ColumnPath>>();

	
    synchronized public static Client getCassandraClient() {
    	if( cassandraClient!=null){
    		return cassandraClient;
    	}
    	
        String server = "127.0.0.1";
        int port = 9160;
      
        /* 首先指定cassandra server的地址 */
        TTransport socket = new TSocket(server, port);
        System.out.println(" connected to " + server + ":" + port + ".");

        /* 指定通信协议为二进制流协议 */
        TBinaryProtocol binaryProtocol = new TBinaryProtocol(socket, false, false);
        cassandraClient = new Cassandra.Client(binaryProtocol);

        /* 建立通信连接 */
        try{
        	socket.open();
        }catch( TTransportException e ){
        	System.out.println("connect DB happen error");
        	throw new RuntimeException(e);
        }
        return cassandraClient;
    }
    
    public static Map<String,ColumnPath> getColumnPath(Object obj , String cfName ){
    	Map<String,ColumnPath> returnMap = new HashMap<String,ColumnPath>();
    	Class c = obj.getClass();

    	if( columnPathMap.get(cfName)!=null ){
    		return columnPathMap.get(cfName);
    	}
		Field objFields[] = c.getDeclaredFields();      
		int length = objFields.length;
		Field.setAccessible(objFields, true); 
		for(int i = 0;i < length; i++){
			try{
				String colPathName = objFields[i].getName() ;
				System.out.println("create ColumnPath "+colPathName);
				ColumnPath cp = new ColumnPath(cfName, null, colPathName.getBytes());
				returnMap.put(objFields[i].getName() , cp );
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		columnPathMap.put(cfName, returnMap);
    	return returnMap;
    }

	public static String getKeyspace() {
		return keyspace;
	}
	
    
}
