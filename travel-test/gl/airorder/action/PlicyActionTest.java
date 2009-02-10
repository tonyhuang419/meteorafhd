package test.gl.airorder.action;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import servletunit.struts.MockStrutsTestCase;
import test.gl.mock.TestTools;
import test.gl.service.ICommonService;

import com.baosight.baosteel.tourism.gl.airorder.common.model.TourismAircomPlicy;
import com.baosight.core.spring.BeanFactory;

public class PlicyActionTest extends MockStrutsTestCase {

	String airComName = "中国南方航空公司";
	String airComCode = "CZ";
	String airLineStart = "CGQ";
	String airLineEnd = "PEK";
	String airlineType = "CZ";
	String berthCode = "F";
	String plicyTimeStart = "20090107";
	String plicyTimeEnd = "20090130";
	String plicyPrice = "999.99";


	public void setUp()throws Exception { 
		super.setUp(); 
//		System.out.println(this.getClass().getResource("/").toString());
		BeanFactory.setAppcontext(new ClassPathXmlApplicationContext("spring/TestUseApplicationContext.xml"));
		BeanFactory.setInitialized(true);
	}

	public void tearDown() throws Exception {
		super.tearDown(); 
	}

	public PlicyActionTest(String testName) { 
		super(testName); 
	}

	public void testExecute(){ 
		setConfigFile("/WEB-INF/struts-config.xml,/WEB-INF/struts-config-citycode.xml,/WEB-INF/struts-config-shengbo.xml,/WEB-INF/struts-config-zhangyanlong.xml,/WEB-INF/struts-config-wangsx.xml,/WEB-INF/struts-config-zhangnan.xml,/WEB-INF/struts-config-wangsx.xml,/WEB-INF/struts-config-xusheng.xml,/WEB-INF/struts-config-jies.xml,/WEB-INF/struts-config-gl-airorder.xml");
		setRequestPathInfo("/gl/airorder/plicy/PlicyAction.do");
		addRequestParameter("functionCode","submitnew");
		addRequestParameter("airComName",airComName);
		addRequestParameter("airComCode",airComCode);
		addRequestParameter("airLineStart",airLineStart);
		addRequestParameter("airLineEnd",airLineEnd);
		addRequestParameter("airlineType",airlineType);
		addRequestParameter("berthCode",berthCode);
		addRequestParameter("plicyTimeStart",plicyTimeStart);
		addRequestParameter("plicyTimeEnd",plicyTimeEnd);
		addRequestParameter("plicyPrice",plicyPrice);
		getSession().setAttribute("USER", TestTools.doMakeAUSER() );
		actionPerform();
		verifyForward("enter");
//		HttpSessionSimulator
	}

	public void testValidate() {
		ICommonService cs = (ICommonService)BeanFactory.findBean("commonService");
		Long tid = (Long)cs.queryList("select max(t.id) from TourismAircomPlicy t ").get(0);
		TourismAircomPlicy t = (TourismAircomPlicy)cs.load(TourismAircomPlicy.class, tid);
		assertEquals(airComName, t.getAirComName());
		assertEquals(airComCode, t.getAirComCode());
		assertEquals(airLineStart, t.getAirLineStart());
		assertEquals(airLineEnd, t.getAirLineEnd());
		assertEquals(airlineType, t.getAirlineType());
		assertEquals(berthCode, t.getBerthCode());
		assertEquals(plicyTimeStart, t.getPlicyTimeStart());
		assertEquals(plicyTimeEnd, t.getPlicyTimeEnd());
		assertEquals(plicyPrice, t.getPlicyPrice());
		cs.delete(t);
	}

}