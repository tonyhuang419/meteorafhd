 package groovy

import groovy.util.GroovyTestCase
import org.testng.annotations.*
import org.testng.TestNG
import org.testng.TestListenerAdapter
import static org.testng.AssertJUnit.*;
import groovy.mock.interceptor.MockFor;

public class TesCalculate extends GroovyTestCase{

	/**
	* Main entry point to run <code>TesCalculate</code> as
	* simple Groovy class
	*/
	public static void main(String[] args){
		def testng = new TestNG()
		testng.setTestClasses(TesCalculate)
		testng.addListener(new TestListenerAdapter())
		testng.run()
	}
	

	 void testAdd1(){
		 def mock = new MockFor(Calculate);
		 mock.demand.add(1..2){a,b-> 5};
		 mock.use{
			 def cal = new Calculate();
//			 cal.add(3,2);
			 assert cal.add(3,2)== 5; 
			 assert cal.add(3,1)== 5;  //look, mock has effect
		 } 		 
	}
	 
	 void testAdd2(){
		def cal = new Calculate();
		assert cal.add(3,2)==5; 		 
	}
	 
	 void testAdd3(){
		def cal = new Calculate();
		assert cal.add(3,1)==5; 		//this test will failed 
	}

}