package junit;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.DatabaseTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

/**
 * @author Sager
 * 
 */
public class DBUnitTest extends DatabaseTestCase {

    public DBUnitTest(String name) {
        super(name);
    }

    /**
     * @see org.dbunit.DatabaseTestCase#getConnection()
     */
    protected IDatabaseConnection getConnection() throws Exception {
        Class driverClass = Class.forName("com.mysql.jdbc.Driver");
        Connection jdbcConnection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/test", "root", "root");
        return new DatabaseConnection(jdbcConnection);
    }

    /**
     * @see org.dbunit.DatabaseTestCase#getDataSet()
     */
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSet(new FileInputStream("junit/dataset.xml"));
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }
    public void testMe() {
        System.out.println("Testing...");
    }
} 


//package junit;
//
//import java.io.File;
//import java.io.FileInputStream;
//
//import junit.framework.TestCase;
//
//import org.dbunit.IDatabaseTester;
//import org.dbunit.JdbcDatabaseTester;
//import org.dbunit.dataset.IDataSet;
//import org.dbunit.dataset.xml.FlatXmlDataSet;
//import org.dbunit.operation.DatabaseOperation;
//public class DBUnitTest extends TestCase {   
//	private IDatabaseTester databaseTester;   
//
//	protected void setUp() throws Exception {   
//		databaseTester = new JdbcDatabaseTester("com.mysql.jdbc.Driver",   
//				"jdbc:mysql://127.0.0.1:3306/test", "root", "root");   
//		IDataSet dataSet = getDataSet();   
//		databaseTester.setDataSet( dataSet );   
//		databaseTester.onSetup();   
//	}   
//	protected IDataSet getDataSet() throws Exception   
//	{   
//		return new FlatXmlDataSet(new FileInputStream(new File("junit/dataset.xml")));   
//	}  
//
//	protected void tearDown() throws Exception   
//	{   
//		databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);   
//		databaseTester.onTearDown();   
//	}   
//} 