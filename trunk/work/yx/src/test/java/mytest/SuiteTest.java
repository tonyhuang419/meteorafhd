package mytest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import mytest.action.purchase.PurchaseActionTest;
import mytest.action.sellbefore.ContractBeforeSellActionTest;
import mytest.action.sellbefore.ShowSellBeforeActionTest;
import mytest.action.utils.UserUtilsTest;

public class SuiteTest extends TestCase{
	public static Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(UserUtilsTest.class);
		suite.addTestSuite(PurchaseActionTest.class);
		suite.addTestSuite(ContractBeforeSellActionTest.class);
		suite.addTestSuite(ShowSellBeforeActionTest.class);
		return suite;
	}
}
