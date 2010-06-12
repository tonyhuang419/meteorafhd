package demo;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.cassandra.service.Cassandra;
import org.apache.cassandra.service.Column;
import org.apache.cassandra.service.ColumnOrSuperColumn;
import org.apache.cassandra.service.ColumnParent;
import org.apache.cassandra.service.ColumnPath;
import org.apache.cassandra.service.InvalidRequestException;
import org.apache.cassandra.service.NotFoundException;
import org.apache.cassandra.service.SlicePredicate;
import org.apache.cassandra.service.SliceRange;
import org.apache.cassandra.service.TimedOutException;
import org.apache.cassandra.service.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;


/**
 * http://ahuaxuan.javaeye.com/blog/603107
 */
public class Demo {
    static Cassandra.Client cassandraClient;


    private static void init() throws TTransportException {
        String server = "10.58.12.103";
        int port = 9160;

        /* ����ָ��cassandra server�ĵ�ַ */
        TTransport socket = new TSocket(server, port);
        System.out.println(" connected to " + server + ":" + port + ".");


        /* ָ��ͨ��Э��Ϊ��������Э�� */
        TBinaryProtocol binaryProtocol = new TBinaryProtocol(socket, false, false);
        cassandraClient = new Cassandra.Client(binaryProtocol);


        /* ����ͨ������ */
        socket.open();
    }


    public static void main(String[] args) throws TException, TimedOutException, InvalidRequestException, UnavailableException, NotFoundException, UnsupportedEncodingException {
        /* ��ʼ������ */
        init();


        /* ѡ����Ҫ������Keyspaces�� �����������ݿ�ı� */
        String keyspace= "Keyspace1";
        String rowX = "row001";
        String rowY = "row002";
        String rowZ = "row003";


        /* ����һ��column path */
        ColumnPath name = new ColumnPath("Standard1", null, "name".getBytes());
        ColumnPath add = new ColumnPath("Standard1", null, "add".getBytes());
        ColumnPath temp = new ColumnPath("Standard1", null, "temp".getBytes());


        /* ִ�в��������ָ��keysapce, row, col, ���������ݣ� ������������һ����timestamp�� ����һ����consistency_level
          * timestamp������������һ���Ա�֤�ģ� ��consistency_level�������������ݷֲ��Ĳ��ԣ�ǰ�ߵ�����������bigtable, ���ߵ�����������dynamo
          */
        cassandraClient.insert(keyspace, rowX, name, "jack".getBytes(), 7, 1);
        cassandraClient.insert(keyspace, rowX, add,  "NY".getBytes(), 7, 1);
        
        cassandraClient.insert(keyspace, rowY, name, "jackx".getBytes(), 7, 1);
        cassandraClient.insert(keyspace, rowY, add,  "NYx".getBytes(), 7, 1);
        
        cassandraClient.insert(keyspace, rowZ, name, "jacky".getBytes(), 7, 1);
        cassandraClient.insert(keyspace, rowZ, add,  "NYy".getBytes(), 7, 1);
        cassandraClient.insert(keyspace, rowZ, temp,  "temp".getBytes(), 7, 1);

        /* ȡ���ո�����ȥ��ֵ��ȡֵ�����̺Ͳ�����������ƣ�Ҳ��Ҫָ��keyspace, row, col, ���һ��������consistency_level */
        Column column = cassandraClient.get(keyspace, rowX, name, 1).column;
//        System.out.println("read row " + rowX);
//        System.out.println("column name" + new String(column.name));
//        System.out.println("column value" + ":" + new String(column.value));
//        System.out.println("column timestamp" + ":" + (column.timestamp));
//        System.out.println("======================");
//        column = cassandraClient.get(keyspace, rowX, add, 1).column;
//        System.out.println("read row " + rowX);
//        System.out.println("column name" + new String(column.name));
//        System.out.println("column value" + ":" + new String(column.value));
//        System.out.println("column timestamp" + ":" + (column.timestamp));
//        System.out.println("======================");
//        column = cassandraClient.get(keyspace, rowY, name, 1).column;
//        System.out.println("read row " + rowY);
//        System.out.println("column name" + new String(column.name));
//        System.out.println("column value" + ":" + new String(column.value));
//        System.out.println("column timestamp" + ":" + (column.timestamp));
//        System.out.println("======================");
//        column = cassandraClient.get(keyspace, rowY, add, 1).column;
//        System.out.println("read row " + rowY);
//        System.out.println("column name" + new String(column.name));
//        System.out.println("column value" + ":" + new String(column.value));
//        System.out.println("column timestamp" + ":" + (column.timestamp));
//        System.out.println("======================");
        ColumnParent cp = new ColumnParent("Standard1", null);
        System.out.println(cassandraClient.get_count(keyspace, rowZ, cp, 1));
        System.out.println("======================");
        SlicePredicate predicate = new SlicePredicate(null, new SliceRange(new byte[0], new byte[0], false, 10));
        List<ColumnOrSuperColumn> results = cassandraClient.get_slice(keyspace, rowZ, cp, predicate, 1);
        for (ColumnOrSuperColumn result : results){
            column = result.column;
            System.out.println(new String(column.name, "UTF-8") + " -> " + new String(column.value, "UTF-8"));
        }
    }
}

