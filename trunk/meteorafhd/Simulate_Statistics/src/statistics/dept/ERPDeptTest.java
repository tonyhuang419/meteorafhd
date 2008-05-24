package statistics.dept;

import junit.framework.TestCase;

public class ERPDeptTest extends TestCase{
	public void testERPDept1(){
		ERPDept e = new ERPDept(200.00,100.00);
		System.out.println(e.getBalance());
		assertEquals(e.getBalance(),100.0);
	}
	public void testERPDept2(){
		ERPDept e = new ERPDept(200.00,100.00);
		System.out.println(e.getPrecentage());
		assertEquals(e.getPrecentage(),"50.00%");
	}

}
