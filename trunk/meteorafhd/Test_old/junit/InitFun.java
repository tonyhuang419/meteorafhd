package junit;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;


public class InitFun {
	public static Test suite(){
		TestSuite suite = new TestSuite(OldTest.class,CopyOfOldTest.class);
		//TestSuite suite2 = new TestSuite(SubTest.class);
		return suite;	
	}
	
	private static void oneTimeEnvironmentSetUp(){
		System.out.println("Setup");
	}
	
	private static void oneTimeEnvironmentTearDown(){
		System.out.println("TearDown");
	}
	
	public static void main(String args[]) throws Exception{
		oneTimeEnvironmentSetUp();
		TestRunner.run(suite());
		oneTimeEnvironmentTearDown();	
	}
}
