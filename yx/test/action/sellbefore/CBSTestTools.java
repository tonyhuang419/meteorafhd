package mytest.action.sellbefore;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;

import com.baoz.yx.entity.ContractBeforeSell;

public class CBSTestTools extends TestCase{
	static public void compareInfo(ContractBeforeSell targetCBS){
		ContractBeforeSell orgCBS  = getContractBeforeSell(null);		
		assertEquals(orgCBS.getBidDate(),targetCBS.getBidDate());
		assertEquals(orgCBS.getBidSum(),targetCBS.getBidSum());
		assertEquals(new Long(-1L),targetCBS.getById());
		assertEquals(orgCBS.getCompeteInfo(),targetCBS.getCompeteInfo());
		assertEquals(orgCBS.getCustomerDepartment(),targetCBS.getCustomerDepartment());
		assertEquals(orgCBS.getCustomerId(),targetCBS.getCustomerId());
		assertEquals(orgCBS.getCustomerLinkManPhone(),targetCBS.getCustomerLinkManPhone());
		assertEquals(orgCBS.getCustomerProjectTypeId(),targetCBS.getCustomerProjectTypeId());
		assertEquals(orgCBS.getDescProjectFollow(),targetCBS.getDescProjectFollow());
		assertEquals(orgCBS.getDutyDepartmentId(),targetCBS.getDutyDepartmentId());
		assertEquals(orgCBS.getEmployeeId(),targetCBS.getEmployeeId());
		assertEquals(orgCBS.getEstimateProjectDate(),targetCBS.getEstimateProjectDate());
		assertEquals(orgCBS.getEstimateSignDate(),targetCBS.getEstimateSignDate());
		assertEquals(orgCBS.getItemRespeoplePhone(),targetCBS.getItemRespeoplePhone());
		assertEquals(orgCBS.getItemTraceResult(),targetCBS.getItemTraceResult());
		assertEquals(orgCBS.getItemTraceResultReason(),targetCBS.getItemTraceResultReason());
		assertEquals(orgCBS.getLinkManId(),targetCBS.getLinkManId());
		assertEquals(orgCBS.getMainProjectContent(),targetCBS.getMainProjectContent());
		assertEquals(orgCBS.getOwnFactory(),targetCBS.getOwnFactory());
		assertEquals(orgCBS.getOwnSum(),targetCBS.getOwnSum());
		assertEquals(orgCBS.getProjectDate(),targetCBS.getProjectDate());
		assertEquals(orgCBS.getProjectManId(),targetCBS.getProjectManId());
		assertEquals(orgCBS.getProjectName(),targetCBS.getProjectName());
		assertEquals(orgCBS.getProjectNameX(),targetCBS.getProjectNameX());
		assertEquals(orgCBS.getProjectStateFollowId(),targetCBS.getProjectStateFollowId());
		assertEquals(orgCBS.getProjectStateId(),targetCBS.getProjectStateId());
		assertEquals(orgCBS.getProjectStateSelect(),targetCBS.getProjectStateSelect());
		assertEquals(orgCBS.getProjectSum(),targetCBS.getProjectSum());
		assertEquals(orgCBS.getProjectSummary(),targetCBS.getProjectSummary());
		assertEquals(orgCBS.getRemark(),targetCBS.getRemark());
		assertEquals(orgCBS.getImportantItem(),targetCBS.getImportantItem());
		assertEquals(orgCBS.getPartyAProId(),targetCBS.getPartyAProId());
		assertEquals("1" , targetCBS.getIs_active());
		assertNotNull(targetCBS.getUpdateBy());
	}


	@SuppressWarnings("deprecation")
	static public ContractBeforeSell getContractBeforeSell(ContractBeforeSell cbs){
		if(cbs==null){
			cbs = new ContractBeforeSell();
		}
		cbs.setBidDate(new Date(107,10,12));
		cbs.setBidSum(20000d);
		cbs.setCompeteInfo("参与竞争厂家情况概述");
		cbs.setCustomerDepartment("客户联系人部门");
		cbs.setCustomerId(5001L);
		cbs.setCustomerLinkManPhone("setCustomerLinkManPhone");
		cbs.setCustomerProjectTypeId("2");
		cbs.setDescProjectFollow("项目跟踪情况说明");
		cbs.setDutyDepartmentId("M0");
		cbs.setEmployeeId(-1L);
		cbs.setEstimateProjectDate(new Date(107,10,13));
		cbs.setEstimateSignDate(new Date(107,10,14));
		cbs.setItemRespeoplePhone("12345");
		cbs.setItemTraceResult("trace");
		cbs.setItemTraceResultReason("reason");
		cbs.setLinkManId(1994L);
		cbs.setMainProjectContent("mainProjectConten");
		cbs.setOwnFactory("ownFactory");
		cbs.setOwnSum(30000d);
		cbs.setProjectDate(new Date(107,10,12));
		cbs.setProjectManId("12344");
		cbs.setProjectName("projectName");
		cbs.setProjectNameX("工程概工程概工程概");
		cbs.setProjectStateFollowId("D1");
		cbs.setProjectStateId("4");
		cbs.setProjectStateSelect("2");
		cbs.setProjectSum(50000d);
		cbs.setProjectSummary("工程概工程概");
		cbs.setRemark("remark");
		cbs.setPartyAProId("partyAProId");
		cbs.setImportantItem(true);
		return cbs;
	}
	
	static HttpServletRequest 	mockHttpServletRequest;
	static public HttpServletRequest mockRequestForSaveSBSSelect(String state){
		final String stateX = state;
		Mockery context = new Mockery();
		mockHttpServletRequest  = context.mock(HttpServletRequest.class); 
		context.checking ( new Expectations(){  {	
			allowing (mockHttpServletRequest).getParameter("comeFrom");
			if( "mod".equals(stateX) ){	
				will(returnValue("mod"));  
			}
			else{		
				will(returnValue("null"));  
			}
		}
		});
		return mockHttpServletRequest;
	}
}
