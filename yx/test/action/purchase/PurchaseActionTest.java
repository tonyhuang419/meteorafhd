package mytest.action.purchase;


import java.util.Date;
import java.util.HashMap;

import mytest.mock.MockUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.action.purchase.PurchaseAction;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.service.IPurService;
import com.baoz.yx.service.IUserService;
import com.opensymphony.xwork2.ActionContext;

public class PurchaseActionTest extends YingXiaoBaseTest {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;

	@Autowired
	@Qualifier("purService")
	private IPurService purService;

	@Autowired
	@Qualifier("userService")
	private IUserService userService;

	 //  创建申购用   "draft" ：初始将保存为草稿状态, "wait"：初始将保存为待确认状态
	String  affirmState = "draft";   

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.prepareTestInstance();
		super.setDefaultRollback(true);
	}

	//保存并修改
	public void testSavePurchase_One(){
		PurchaseAction pAction;
		pAction = this.createNewPurchase();
		try{
			pAction.savePurchase();
		}catch(Exception e){
			e.printStackTrace();
		}
		Long maxCount = (Long)service.uniqueResult( " select max(c.id) from ApplyMessage c ");
		ApplyMessage tempC = (ApplyMessage)service.load(ApplyMessage.class, maxCount);
		//		SeeBeanFields.travBean(tempC);
		this.compareInfo(tempC ,  affirmState );
	}


	//success confirm purchase
	public void testCheckThrough(){
		PurchaseAction pAction;
		pAction = this.createNewPurchase();
		try{
			pAction.savePurchase();
		}catch(Exception e){
			e.printStackTrace();
		}
		Long maxCount = (Long)service.uniqueResult( " select max(c.id) from ApplyMessage c ");
		ApplyMessage tempC = (ApplyMessage)service.load(ApplyMessage.class, maxCount);
		this.compareInfo(tempC ,  affirmState );

		assertNotSame(tempC.getAffirmState(),"2"  );  //初始状态不能为“确认通过”
		pAction.setAmids(tempC.getId().toString());
		ActionContext.getContext().setSession(new HashMap<Object,Object>());
		try{
			pAction.checkThrough();
		}catch(Exception e){
			e.printStackTrace();
		}
		assertEquals(tempC.getAffirmState(),"2"  );
	}

	//withdraw purchase
	public void testCheckBback(){
		PurchaseAction pAction;
		pAction = this.createNewPurchase();
		try{
			pAction.savePurchase();
		}catch(Exception e){
			e.printStackTrace();
		}
		Long maxCount = (Long)service.uniqueResult( " select max(c.id) from ApplyMessage c ");
		ApplyMessage tempC = (ApplyMessage)service.load(ApplyMessage.class, maxCount);
		this.compareInfo(tempC ,  affirmState );

		assertNotSame(tempC.getAffirmState(),"3"  );  //初始状态不能为“确认退回”
		pAction.setAmids(tempC.getId().toString());
		pAction.setReturnReason("退回理由");
		ActionContext.getContext().setSession(new HashMap<Object,Object>());
		try{
			pAction.checkBback();
		}catch(Exception e){
			e.printStackTrace();
		}
		assertEquals(tempC.getAffirmState(),"3"  );
		assertEquals(tempC.getReturnReason(),"退回理由"  );
	}
	
	//purchase submit for confirm
	public void testSubmitPur(){
		PurchaseAction pAction;
		pAction = this.createNewPurchase();
		try{
			pAction.savePurchase();
		}catch(Exception e){
			e.printStackTrace();
		}
		Long maxCount = (Long)service.uniqueResult( " select max(c.id) from ApplyMessage c ");
		ApplyMessage tempC = (ApplyMessage)service.load(ApplyMessage.class, maxCount);
		this.compareInfo(tempC ,  affirmState );
		assertNotSame("The purchase entity's state has be set  \" wait confirm  \" when it be initialized  ",tempC.getAffirmState(),"1"  );  //初始状态不能为“确认退回”
		pAction.setAmids(tempC.getId().toString());
		ActionContext.getContext().setSession(new HashMap<Object,Object>());
		try{
			pAction.submitPru();
		}catch(Exception e){
			e.printStackTrace();
		}
		assertEquals(tempC.getAffirmState(),"1"  );
	}
	
	

	//获取PurchaseAction，调用savePurchase来创建一个申购
	private PurchaseAction createNewPurchase( ){
		PurchaseAction pAction = new PurchaseAction();
		pAction.setService(service);
		pAction.setPurService(purService);
		pAction.setUserService(userService);
		ApplyMessage am = this.getApplyMessageInfo();
		pAction.setAm(am);
		pAction.setOpState("6");
		pAction.setCNameId1(am.getCustomerId());
		pAction.setCNameId2(am.getCustomerId());
		pAction.setProjectName1(am.getProjectName());
		pAction.setProjectName2(am.getProjectName());
		pAction.setIdstate1(am.getInformState());  //pAction.setIdstate2(0);    页面做的很恶心，忘了原因自己去看
		pAction.setState( affirmState );           //保存申购草稿状态 
		return pAction;
	}



	private void compareInfo( ApplyMessage targetAm ,String affirmState ){
		ApplyMessage orgAm = this.getApplyMessageInfo();

		assertEquals(targetAm.getAssignmentId(),orgAm.getAssignmentId());
		assertEquals(targetAm.getCustomerId(),orgAm.getCustomerId());
		assertEquals(targetAm.getOutState(),orgAm.getOutState());
		assertEquals(targetAm.getOutId(),	orgAm.getOutId());
		assertEquals(targetAm.getApplyType(),orgAm.getApplyType());
		//		private Long ticketApplyId, // 开票申请编号
		assertEquals(targetAm.getBugget(),orgAm.getBugget());
		assertEquals(targetAm.getApplyState(),orgAm.getApplyState());
		assertEquals(targetAm.getLinkman(),orgAm.getLinkman());
		assertEquals(targetAm.getApplyContent(),orgAm.getApplyContent());
		assertEquals(targetAm.getRemark(),orgAm.getRemark());
		assertEquals(targetAm.getProjectName(),orgAm.getProjectName());
		assertEquals(targetAm.getInformState(),orgAm.getInformState());
		assertEquals(targetAm.getItemMainId(),orgAm.getItemMainId());

		assertEquals(targetAm.getApplymoney(),orgAm.getApplymoney());			
		//未签申购
		if (orgAm.getApplyState().equals("0")) {  
			assertEquals(targetAm.getEstimateDate(),orgAm.getEstimateDate());
		}

		//已签申购
		else if (orgAm.getApplyState().equals("1")) {  
			assertEquals(targetAm.getMainId(), orgAm.getMainId());		
			assertEquals(targetAm.getEventId(),orgAm.getEventId());
		}

		assertEquals(targetAm.getAffirmState(),this.setAS(affirmState));
		assertEquals(targetAm.getOutState(),"0");
		assertEquals(targetAm.getSellmanId(),new Long(-1L));
		assertEquals(targetAm.getIs_active(),"1");
		assertNotNull(targetAm.getApplyDate());


	}

	//coppy from PruService
	private String setAS(String state){
		String temp="";
		if("draft".equals(state))  //草稿
			temp="0";
		if("wait".equals(state))	//待确认
			temp="1";
		if("through".equals(state)) //确认通过
			temp="2";
		if("back".equals(state)) //确认退回
			temp="3";
		return temp;
	}

	@SuppressWarnings("deprecation")
	private ApplyMessage getApplyMessageInfo(){
		MockUser u = new MockUser();
		u.mockAdmin();

		ApplyMessage am = new ApplyMessage();
		am.setMainId(1000L);
		am.setEventId("111111");
		am.setAssignmentId("asssssNo");
		am.setCustomerId(5001L);
		//		private Long sellmanId; // 销售人员代码
		//		private String applyId; // 申购单号
		am.setApplyDate(new Date(107,11,11));
		am.setOutState("0");
		am.setOutId("12345");		
		am.setEstimateDate(new Date(107,11,12));
		am.setApplyType("1"); 
		//		private Long ticketApplyId; // 开票申请编号
		am.setBugget(new Double(20000));
		am.setApplyState("1"); 
		am.setLinkman("111");
		am.setApplyContent("zzzzzz");
		am.setRemark("rrrrrrrrr");
		am.setProjectName("iiiiiiiiii");
		am.setInformState("0"); 
		am.setApplymoney(220000d);
		am.setItemMainId(1121L);
		return am;
	}

}
