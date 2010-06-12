package demo;
import org.apache.cassandra.service.Cassandra;
import org.apache.cassandra.service.Column;
import org.apache.cassandra.service.ColumnPath;
import org.apache.cassandra.service.InvalidRequestException;
import org.apache.cassandra.service.NotFoundException;
import org.apache.cassandra.service.TimedOutException;
import org.apache.cassandra.service.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;


public class Demo2 {

	static Cassandra.Client cassandraClient;

	private static void init() throws TTransportException {
		String server = "localhost";
		int port = 9160;

		/* 首先指定cassandra server的地址 */
		TTransport socket = new TSocket(server, port);
		System.out.println(" connected to " + server + ":" + port + ".");


		/* 指定通信协议为二进制流协议 */
		TBinaryProtocol binaryProtocol = new TBinaryProtocol(socket, false, false);
		cassandraClient = new Cassandra.Client(binaryProtocol);


		/* 建立通信连接 */
		socket.open();
	}


	public static void main(String[] args) throws TException, TimedOutException, InvalidRequestException, UnavailableException, NotFoundException {
		init();
		String table = "Keyspace1";
		String row = "row007";
		ColumnPath col = new ColumnPath("Standard1", null, "ahuaxuan".getBytes());
		ColumnPath col2 = new ColumnPath("Standard2", null, "ahuaxuan".getBytes());


		cassandraClient.insert(table, row, col, " first cassandra sample of ahuaxuan".getBytes(), 4, 1);
		cassandraClient.insert(table, row, col2, " second cassandra sample of ahuaxuan ".getBytes(), 5, 1);
		Column column = cassandraClient.get(table, row, col, 1).column;
		Column column2 = cassandraClient.get(table, row, col2, 1).column;


		System.out.println("read row " + row);
		System.out.println("column name: " + new String(column.name));
		System.out.println("column value" + ": " + new String(column.value));
		System.out.println("column timestamp" + ":" + (column.timestamp));


		System.out.println("\r\nread row " + row);
		System.out.println("column name: " + new String(column2.name));
		System.out.println("column value" + ": " + new String(column2.value));
		System.out.println("column timestamp" + ": " + (column2.timestamp));
	} 
}
