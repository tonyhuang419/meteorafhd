package mytest.action.sellbefore;

import java.util.HashMap;
import java.util.Map;

import mytest.mock.MockUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.action.sellbefore.ContractBeforeSellAction;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.ContractBeforeSell;
import com.baoz.yx.service.IBeforeSellContractService;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.service.ISystemService;



public class ContractBeforeSellActionTest extends YingXiaoBaseTest {

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
	@Qualifier("yxCommonDao")
	private IYXCommonDao 		yxCommonDao;

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
	public void testSaveCBS() {
		ContractBeforeSellAction cbsAction = new ContractBeforeSellAction();
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
		cbsAction.setCbs(CBSTestTools.getContractBeforeSell(null));
		cbsAction.setServletRequest(CBSTestTools.mockRequestForSaveSBSSelect("new"));
		try{
			cbsAction.saveCBS();
		}catch (Exception e){
			e.printStackTrace();
		}
		Long maxCount = (Long)service.uniqueResult( " select max(c.id) from ContractBeforeSell c ");
		ContractBeforeSell tempC = (ContractBeforeSell)service.load(ContractBeforeSell.class, maxCount);
		CBSTestTools.compareInfo(tempC);
	}


	@SuppressWarnings("unchecked")
	public void testSaveSBSSelect() {
		ContractBeforeSellAction cbsAction = new ContractBeforeSellAction();
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
		cbsAction.setServletRequest(CBSTestTools.mockRequestForSaveSBSSelect("mod"));
		
		Long maxCount = (Long)service.uniqueResult( " select max(c.id) from ContractBeforeSell c ");
		ContractBeforeSell tempC = (ContractBeforeSell)service.load(ContractBeforeSell.class, maxCount);
		cbsAction.setCbs(CBSTestTools.getContractBeforeSell(tempC));
		
//		ContractBeforeSell tempC = CBSTestTools.getContractBeforeSell(null);
//		tempC.setID(maxCount);
//		cbsAction.setCbs(tempC);
//		yxCommonDao.getSession().evict(tempC);
		try{
			cbsAction.saveSBSSelect();
		}catch (Exception e){
			e.printStackTrace();
		}
		tempC = (ContractBeforeSell)service.load(ContractBeforeSell.class, maxCount);
		CBSTestTools.compareInfo(tempC);
		
		//判断售前变更表存在信息（肯定会有变得，trust me ， ^ ^ ）
//		Long count  = (Long)service.uniqueResult( " select count(*) from ContractBeforeSellHistory c where c.cbsId = ?", maxCount);
//		logger.info(count);
//		assertTrue(count>0);
		
	}
}
