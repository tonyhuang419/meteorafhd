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

        /* 首先指定cassandra server的地址 */
        TTransport socket = new TSocket(server, port);
        System.out.println(" connected to " + server + ":" + port + ".");


        /* 指定通信协议为二进制流协议 */
        TBinaryProtocol binaryProtocol = new TBinaryProtocol(socket, false, false);
        cassandraClient = new Cassandra.Client(binaryProtocol);


        /* 建立通信连接 */
        socket.open();
    }


    public static void main(String[] args) throws TException, TimedOutException, InvalidRequestException, UnavailableException, NotFoundException, UnsupportedEncodingException {
        /* 初始化连接 */
        init();


        /* 选择需要操作的Keyspaces， 可以理解成数据库的表 */
        String keyspace= "Keyspace1";
        String rowX = "row001";
        String rowY = "row002";
        String rowZ = "row003";


        /* 创建一个column path */
        ColumnPath name = new ColumnPath("Standard1", null, "name".getBytes());
        ColumnPath add = new ColumnPath("Standard1", null, "add".getBytes());
        ColumnPath temp = new ColumnPath("Standard1", null, "temp".getBytes());


        /* 执行插入操作，指定keysapce, row, col, 和数据内容， 后面两个参数一个是timestamp， 另外一个是consistency_level
          * timestamp是用来做数据一致性保证的， 而consistency_level是用来控制数据分布的策略，前者的理论依据是bigtable, 后者的理论依据是dynamo
          */
        cassandraClient.insert(keyspace, rowX, name, "jack".getBytes(), 7, 1);
        cassandraClient.insert(keyspace, rowX, add,  "NY".getBytes(), 7, 1);
        
        cassandraClient.insert(keyspace, rowY, name, "jackx".getBytes(), 7, 1);
        cassandraClient.insert(keyspace, rowY, add,  "NYx".getBytes(), 7, 1);
        
        cassandraClient.insert(keyspace, rowZ, name, "jacky".getBytes(), 7, 1);
        cassandraClient.insert(keyspace, rowZ, add,  "NYy".getBytes(), 7, 1);
        cassandraClient.insert(keyspace, rowZ, temp,  "temp".getBytes(), 7, 1);

        /* 取出刚刚塞进去的值，取值的流程和插入的流程类似，也需要指定keyspace, row, col, 最后一个参数是consistency_level */
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

