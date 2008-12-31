package mytest.action.sellbefore;

import java.util.HashMap;
import java.util.Map;

import mytest.mock.MockUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.action.sellbefore.ContractBeforeSellAction;
import com.baoz.yx.action.sellbefore.ShowSellBeforeAction;
import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IBeforeSellContractService;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.service.ISystemService;
import com.opensymphony.xwork2.ActionContext;


public class ShowSellBeforeActionTest extends YingXiaoBaseTest {

	@Autowired
	@Qualifier("eventInfoService")
	private IEventInfoService eventService;

	@Autowired
	@Qualifier("codeGenerateService")
	private ICodeGenerateService 	codeGenerateService;

	@Autowired
	@Qualifier("systemService")
	private ISystemService 		systemService;

	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;
	
	@Autowired
	@Qualifier("beforeSellContractService")
	private IBeforeSellContractService beforeSellContractService;

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.prepareTestInstance();
		super.setDefaultRollback(true);
	}

	@SuppressWarnings("unchecked")
	public void testChangeContract(){
		//build a cbs  ,   copy by TestContractBeforeSellAction
		ContractBeforeSellAction cbsAction = new ContractBeforeSellAction();
		ContractBeforeSell cbs = CBSTestTools.getContractBeforeSell(null);
		cbsAction.setServletRequest(CBSTestTools.mockRequestForSaveSBSSelect("new"));
		MockUser u = new MockUser();
		u.mockAdmin();
		Map sessionMap = new HashMap();
		cbsAction.setEventService(eventService);
		cbsAction.setCodeGenerateService(codeGenerateService);
		cbsAction.setSystemService(systemService);	
		cbsAction.setBeforeSellContractService(beforeSellContractService);	
		cbsAction.setService(service);
		cbsAction.setSelectExit("true");
		cbsAction.setSession(sessionMap);
		cbsAction.setCbs(cbs);
		try{
			cbsAction.saveCBS();
		}catch (Exception e){
			e.printStackTrace();
		}

		Long maxCount = (Long)service.uniqueResult( " select max(c.id) from ContractBeforeSell c ");
		ContractBeforeSell tempC = (ContractBeforeSell)service.load(ContractBeforeSell.class, maxCount);
		CBSTestTools.compareInfo(tempC);

		ShowSellBeforeAction ssba = new ShowSellBeforeAction();
		ssba.setService(service);
		ssba.setId(cbs.getID());
		ActionContext.getContext().setSession(new HashMap());
		try{
			ssba.changeContract();
		}catch (Exception e){
			e.printStackTrace();
		}
		assertEquals(tempC.getProjectStateSelect(), "2");	
		
		ContractMainInfo  tempCmi;
		Long maxCountCmi = (Long)service.uniqueResult( " select max(c.id) from ContractMainInfo c ");
		tempCmi = (ContractMainInfo)service.load(ContractMainInfo.class, maxCountCmi);
		tempC = (ContractBeforeSell)service.load(ContractBeforeSell.class, maxCount);
		this.compareContract(tempC, tempCmi);
	}	
	
	
	private void compareContract(ContractBeforeSell targetCBS , ContractMainInfo cmi ){
		assertEquals( cmi.getPreConSysid() ,  targetCBS.getID());
		assertEquals( cmi.getSaleMan() ,  targetCBS.getEmployeeId());
		assertEquals( cmi.getConName() , targetCBS.getProjectName());
		assertEquals(	cmi.getMainItemDept() , targetCBS.getDutyDepartmentId() );
		assertEquals(cmi.getConCustomer(),targetCBS.getCustomerId());
		assertEquals(cmi.getLinkManId(),targetCBS.getLinkManId());
		assertEquals(cmi.getCustomereventtype(),targetCBS.getCustomerProjectTypeId());
		assertEquals(cmi.getMainItemDept(),targetCBS.getDutyDepartmentId());
		assertEquals(cmi.getConSignDate(),targetCBS.getEstimateSignDate());
		assertEquals(cmi.getConStartDate(),targetCBS.getProjectDate());
		assertEquals(cmi.getConTaxTamount(),targetCBS.getProjectSum());
		assertEquals(cmi.getMainItemPeople(),targetCBS.getProjectManId());
		assertEquals(cmi.getItemCustomer(),targetCBS.getCustomerId());
		assertEquals(cmi.getConState(),new Long(0L));   
		assertEquals(cmi.getFinalAccount(),"0" );
		assertEquals(cmi.getImportFromFile(),Boolean.FALSE);
		assertEquals(cmi.getBaserate(),1D);
		assertEquals(cmi.getCopeck(),"CNY");
		assertEquals(cmi.getStandard(),"1");
		assertEquals(cmi.getOpPeople() , new Long(-1));
		assertEquals(cmi.getContractType(),"1");
		assertNotNull(cmi.getOpTime());
	}
}
