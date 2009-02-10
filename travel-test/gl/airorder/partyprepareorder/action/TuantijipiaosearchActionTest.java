package test.gl.airorder.partyprepareorder.action;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import servletunit.struts.MockStrutsTestCase;
import test.gl.mock.TestTools;

import com.baosight.baosteel.tourism.common.util.AutoincrementArrayList;
import com.baosight.baosteel.tourism.gl.airorder.common.model.TourismAirTeamPreOrder;
import com.baosight.baosteel.tourism.gl.airorder.partyprepareorder.form.OnBoardingPeopleForm;
import com.baosight.baosteel.tourism.gl.airorder.partyprepareorder.form.PartyprepareorderForm;
import com.baosight.core.spring.BeanFactory;

public class TuantijipiaosearchActionTest extends MockStrutsTestCase {

	public void setUp()throws Exception { 
		super.setUp(); 
		BeanFactory.setAppcontext(new ClassPathXmlApplicationContext("spring/TestUseApplicationContext.xml"));
		BeanFactory.setInitialized(true);
	}

	public void tearDown() throws Exception {
		super.tearDown(); 
	}

	public TuantijipiaosearchActionTest(String testName) { 
		super(testName); 
	}
	
	public void _testTools(){
		List list = this.makeOnBoardPeopleList();
		for( Iterator it = list.iterator(); it.hasNext(); ){
			OnBoardingPeopleForm o = (OnBoardingPeopleForm)it.next();
			System.out.print(o.getLcertificate()+"  ");
			System.out.print(o.getLcertype()+"  ");
			System.out.print(o.getName()+"  ");
			System.out.println(o.getInsurance());
		}
	}

	//test page skip
	public void _testSkip(){ 
		setConfigFile("/WEB-INF/struts-config.xml,/WEB-INF/struts-config-citycode.xml,/WEB-INF/struts-config-shengbo.xml,/WEB-INF/struts-config-zhangyanlong.xml,/WEB-INF/struts-config-wangsx.xml,/WEB-INF/struts-config-zhangnan.xml,/WEB-INF/struts-config-wangsx.xml,/WEB-INF/struts-config-xusheng.xml,/WEB-INF/struts-config-jies.xml,/WEB-INF/struts-config-gl-airorder.xml");
		setRequestPathInfo("/tuantijipiaosearch.do");
		addRequestParameter("functionCode","orderPeopleInfo");
		actionPerform();
		verifyForward("orderPeopleInfo");
	}

	//test Save air team  prepare order
	public void testSavePreOrder(){
		setConfigFile("/WEB-INF/struts-config.xml,/WEB-INF/struts-config-citycode.xml,/WEB-INF/struts-config-shengbo.xml,/WEB-INF/struts-config-zhangyanlong.xml,/WEB-INF/struts-config-wangsx.xml,/WEB-INF/struts-config-zhangnan.xml,/WEB-INF/struts-config-wangsx.xml,/WEB-INF/struts-config-xusheng.xml,/WEB-INF/struts-config-jies.xml,/WEB-INF/struts-config-gl-airorder.xml");
		setRequestPathInfo("/tuantijipiaosearch.do");
		addRequestParameter("functionCode","savePreOrder");

		getSession().setAttribute("pf", this.doMakePartyprepareorderForm());
		getSession().setAttribute("USER", TestTools.doMakeAUSER());
		
		actionPerform();
		verifyForward("savePreOrder");

	}


	private PartyprepareorderForm doMakePartyprepareorderForm(){
		PartyprepareorderForm pf = new PartyprepareorderForm();
		TourismAirTeamPreOrder t = this.makeAPartyprepareorderForm(); 
		pf.setTourismAirTeamPreOrder(t);
		pf.setOnBoardPeople(this.makeOnBoardPeopleList());

		return pf;
	}

	List onBoardPeople = new AutoincrementArrayList(OnBoardingPeopleForm.class); 
	private List makeOnBoardPeopleList(){
		 for(int i=0; i<15;i++){
			 OnBoardingPeopleForm o = new OnBoardingPeopleForm();
			 o.setInsurance( new Long( (long)(Math.random()*10) ));
			 o.setLcertificate( new Long( (long)(Math.random()*10000000)).toString() );
			 o.setLcertype("A");
			 o.setInsurance(new Long(1));
			 o.setName( new Long( (long)(Math.random()*100000)).toString() );
			 onBoardPeople.add(o);
		 }
		
		 return onBoardPeople;
	}
	
	private TourismAirTeamPreOrder makeAPartyprepareorderForm(){
		TourismAirTeamPreOrder t = new TourismAirTeamPreOrder();
		t.setAirway("Airway");
		t.setAirwayno("Airwayno");
		t.setAirwayssc("Airwayssc");
		t.setArea("Area");
		t.setBaoxiao("1");
		t.setBdate("setBdate");
		t.setBoardCertificate("setBoardCertificate");
		t.setBoardCertype("boardCertype");
		t.setBoardInsurance(new Long(2));
		t.setBoardName("boardName");
		t.setBp("bp");
		t.setBport("bport");
		t.setBtime("btime");
		t.setBuildfee(new Double(50));
		t.setChangeflight("changeflight");
		t.setCity("city");
		t.setCompany("company");
		t.setCustomerType("customerType");
		t.setDepartment("department");
		t.setDiscount(new Double(7.6));
		t.setEport("eport");
		t.setEtime("etime");
		t.setExigence("exigence");
		t.setFactpay(new Double(50000));
		t.setFfxs("fx");
		t.setFlighttype("flighttype");
		t.setFlightinfo("flightinfo");
		t.setFullprice(new Double(60000));
		t.setHoldup("holdup");
		t.setHourseno("hourseno");
		t.setInvoice("invoice");
		t.setIslock("0");
		t.setLxrCemail("lxrCemail");
		t.setLxrCfax("lxrCfax");
		t.setLxrCmobil("lxrCmobil");
		t.setLxrCphone("lxrCphone");
		t.setLxrName("lxrName");
		t.setLxrPostcode("lxrPostcode");
		t.setNoticeway("noticeway");
		t.setOffice("office");
		t.setOil(new Double(250));
		t.setOrderCreateOperator("orderCreateOperator");
		t.setOrderCreateTime("orderCreateTime");
		t.setOrderid("orderid");
		t.setOrderLastOperator("orderLastOperator");
		t.setOrderLastTime("orderLastTime");
		t.setOrderstatue("orderstatue");
		t.setOutticketway("outticketway");
		t.setPayment("payment");
		t.setPayway("payway");
		t.setPnr("pnr");
		t.setProvince("province");
		t.setProvince("province");
		t.setPtime("ptime");
		t.setPublicfee("publicfee");
		t.setQkmoney(new Double(500));
		t.setReasonopr("reasonopr");
		t.setRemark("remark");
		t.setReserveDate("reserveDate");
		t.setReticketrole("reticketrole");
		t.setRlmoney(new Double(500));
		t.setRoad("road");
		t.setRoom("room");
		t.setRucop("rucop");
		t.setSailtype("sailtype");
		t.setSeatlv("seatlv");
		t.setSenddate("senddate");
		t.setSender("sender");
		t.setSendremark("sendremark");
		t.setSendstatue("sendstatue");
		t.setSendstatue("sendstatue");
		t.setSendtime("sendtime");
		t.setSendtimephase("sendtimephase");
		t.setSendto("sendto");
		t.setSendway("sendway");
		t.setShouldpay(new Double(10000));
		t.setTicketno("ticketno");
		t.setTickettype("tickettype");
		t.setUpdaterole("updaterole");
		t.setYdrAddress("ydrAddress");
		t.setYdrCb("ydrCb");
		t.setYdrCertificate("ydrCertificate");
		t.setYdrCertype("ydrCertype");
		t.setYdrCompany("ydrCompany");
		t.setYdrDepartment("ydrDepartment");
		t.setYdrEmail("ydrEmail");
		t.setYdrFax("ydrFax");
		t.setYdrId("ydrId");
		t.setYdrMobile("ydrMobile");
		t.setYdrName("ydrName");
		t.setYdrPhone("ydrPhone");
		t.setYdrPid("ydrPid");
		t.setYdrUsertype("ydrUsertype");
		return t;
	}

}
