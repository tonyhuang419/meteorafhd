package com.baoz.yx.service.impl;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.baoz.yx.service.IForamlContractService;


//该死的测试们需要GBK（中文）

public class TestForamlContractService extends TestCase {

	@SuppressWarnings("unused")
	private ApplicationContext context = null;
	@SuppressWarnings("unused")
	private IForamlContractService foramlContractService  = null;


	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}


	public void tearDown() throws Exception {
		Thread.sleep(2000);
	}

//	public void testForamlContractService(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	ContractMainInfo cmi = foramlContractService.loadContractMainInfo(1);
//	String s = "第一份合同";
//	System.out.println(cmi.getConName());
//	}


//	public void testloadContractItemStageInfo(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<ContractItemStage> cis = null;
//	cis = foramlContractService.loadContractItemStageInfo(1);
//	System.out.println(cis.size());
//	}


//	public void testLoadContractApplyInfo(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<ApplyMessage> am = null;
//	am = foramlContractService.loadContractApplyInfo(1);
//	System.out.println(am.size());
//	}


//	public void testLoadRealContractBillpro(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<RealContractBillRecePlan> rcbpList = null;	 //实际合同开票计划列表
//	List<RealContractRecepro> rcrpList = null;	 //实际合同收款计划列表
//	RealContractBillRecePlan rcbp = null;	 //实际合同开票计划表
//	RealContractRecepro rcrp = null;   	 //实际合同收款计划表


//	rcbpList = foramlContractService.loadRealContractBillpro(1);
//	rcrpList = foramlContractService.loadRealContractRecepro(1);

//	System.out.println("===================实际合同开票计划列表数"+rcbpList.size()+"===================");
//	System.out.println("===================实际合同收款计划列表数"+rcrpList.size()+"===================");
//	Iterator<RealContractBillRecePlan> itbcbp  = rcbpList.iterator();
//	Iterator<RealContractRecepro> itrcrp  = rcrpList.iterator();
//	System.out.println("==================打印开票列表===============");
//	while(itbcbp.hasNext()){
//	rcbp = (RealContractBillRecePlan)itbcbp.next();
//	System.out.print("阶段:"+rcbp.getConItemStage());  //阶段
//	System.out.print("性质:"+rcbp.getBillNature());    //性质
//	System.out.print("类型:"+rcbp.getBillType());    //类型
//	System.out.print("基准:"+rcbp.isBase());    //基准
//	System.out.print("预计日期:"+rcbp.getRealPredBillDate().toString());    //预计日期
//	System.out.print("金额:"+rcbp.getRealBillAmount());    //金额
//	System.out.print("开票确认收款标志:"+rcbp.isBillSureSign());    //开票确认收款标志
//	System.out.print("统一:"+rcbp.isUniteBill());    //统一
//	System.out.println("开票内容:"+rcbp.getBillContent());    //开票内容
//	}
//	System.out.println("==================打印收款列表===============");
//	while(itrcrp.hasNext()){
//	rcrp = (RealContractRecepro)itrcrp.next();
//	System.out.print("阶段:"+rcrp.getConItemStage());   //阶段
//	System.out.print("预计日期:"+rcrp.getRealPredreceDate().toString());   //预计日期
//	System.out.println("预计日期:"+rcrp.getConMainInfoSid());   //预计日期
//	}	


//	System.out.println("==================都打完了，该去睡觉了===============");
//	}



//	public void testLoadRealContractBillpro(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<RealContractBillandRecePlan> rcbpList = null;	 //实际合同开票计划列表
//	List<RealContractRecepro> rcrpList = null;	 //实际合同收款计划列表
//	RealContractBillandRecePlan rcbp = new RealContractBillandRecePlan();	 //实际合同开票计划表
//	RealContractRecepro rcrp = new RealContractRecepro();   	 //实际合同收款计划表


//	List<RealContractBillandRecePlan>  listA = foramlContractService.loadRealContractBillpro(1);
//	System.out.println(listA.size());
//	List<RealContractRecepro> listB = foramlContractService.loadRealContractRecepro(1);
//	System.out.println(listB.size());




//	System.out.println("///////////////////////////////////////");
//	List list= foramlContractService.loadBillandRecePro(1);
//	System.out.println("size is..........."+list.size());
//	System.out.println("///////////////////////////////////////");
//	Iterator it=list.iterator();
//	while(it.hasNext()){
//	Object[] obj = (Object[])it.next();
//	rcbp = (RealContractBillandRecePlan)obj[0];
//	rcrp = (RealContractRecepro)obj[1];

//	System.out.println(".........plan is:............");
//	System.out.print("main id:"+rcbp.getConMainInfoSid()+"\t");   //合同主体号
//	System.out.print("stage id:" +rcbp.getConItemStage()+"\t");     //阶段号
//	System.out.print("item main id:" +rcbp.getContractItemMaininfo()+"\t");     //项目主体
//	System.out.println("item info id:"+rcbp.getConItemInfoSid()+"\t");   //项目信息号

//	//负责部门，通过项目主体信息号获取
//	System.out.print("resdept：" + rcbp.getContractItemMaininfo()+"\t");
//	//收款和开票阶段名
//	System.out.print("stage：" + rcbp.getConItemStage()+"\t");
//	//开票性质
//	System.out.print("bill nature:" + rcbp.getBillNature()+"\t");
//	//发票类型
//	System.out.print("bill type:" + rcbp.getBillType()+"\t");
//	//基准
//	System.out.print("base:" + rcbp.isBase()+"\t");
//	//开票金额
//	System.out.print("amout:" + rcbp.getRealBillAmount()+"\t");
//	//开票确定收入标志
//	System.out.print("sure:"+ rcbp.isBillSureSign()+"\t");
//	//统一开票
//	System.out.print("unite:"+rcbp.isUniteBill()+"\t");



//	System.out.print("main id:"+rcrp.getConMainInfoSid()+"\t");   //合同主体号
//	System.out.print("stage id:" +rcrp.getConItemStage()+"\t");     //阶段号
//	System.out.print("item main id:" +rcrp.getContractItemMaininfo()+"\t");     //项目主体
//	System.out.print("item info id:" +rcrp.getConItemInfoSid()+"\t");  //项目信息号

//	//预计开票时间
//	System.out.print("bill time:"+rcbp.getRealPredBillDate().toString()+"\t");
//	//预计收款时间
//	System.out.println("rece time:"+ rcrp.getRealPredreceDate());


//	}
//	System.out.println(".........彻底打完了...........");
//	}



//	public void testLoadRealContractBillandRecePlan(){
//	RealContractBillandRecePlan r = null;
//	System.out.println("-------------------------------");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<RealContractBillandRecePlan>  list = foramlContractService.loadRealContractBillandRecePlan(1);
//	System.out.println("==================="+list.size()+"================");
//	Iterator it = list.iterator();
//	while(it.hasNext()){
//	r = (RealContractBillandRecePlan)it.next();
//	System.out.println(r.getContractItemMaininfo()+"  "
//	+r.getConItemStage()+"   "
//	+r.getBillContent()+"   "
//	+r.getBillType()+"   ");
//	}
//	System.out.println("-------------------------------");
//	}



//	public void testGetItemStageName(){
//	System.out.println("==========================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	String str = foramlContractService.getItemStageName(1L);
//	System.out.println("==================="+str+"=====================");
//	}


//	public void testGetResDeptName(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	String str = foramlContractService.getResDeptName(1);
//	System.out.println("==================="+str+"=====================");
//	}


//	public void testLoadInvoiceInfo(){
//	System.out.println("===================testLoadInvoiceInfo=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<InvoiceInfo> list = foramlContractService.loadInvoiceInfo(1);
//	System.out.println("==================="+list.size()+"=====================");
//	}


//	public void testGetInvoiceContent(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	String str = foramlContractService.getInvoiceContent(1);
//	System.out.println("==================="+str+"=====================");
//	}



//	public void testGetInvoiceNature(){
//	System.out.println("===================testGetInvoiceNature=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	String str = foramlContractService.getInvoiceNature(2);
//	System.out.println("==================="+str+"=====================");
//	}


//	public void testGetInvoiceNature2(){
//	System.out.println("===================testGetInvoiceNature=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	String str = foramlContractService.getInvoiceNature(2);
//	System.out.println("==================="+str+"=====================");
//	}


//	public void testLoadBillReceInfo(){
//	InvoiceInfo ii = null;
//	ReveInfo ri = null;	
//	Object[] obj = null;
//	System.out.println("===================testLoadReceInfo=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<Object[]> objList = foramlContractService.loadBillReceInfo(1);
//	Iterator<Object[]> it = objList.iterator();
//	while(it.hasNext()){
//	obj = (Object[])it.next();
//	ii = (InvoiceInfo)obj[0];
//	ri = (ReveInfo)obj[1];
//	System.out.print(ii.getInvoiceAmount()+"    ");
//	System.out.println(ri.getAmount());
//	}
//	}


//	public void testLoadReceInfo(){
//	ReveInfo ri = null;	
//	System.out.println("===================testLoadReveInfo=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<ReveInfo> reveList = foramlContractService.loadReceInfo(1);
//	Iterator<ReveInfo> it = reveList.iterator();
//	while(it.hasNext()){
//	ri = (ReveInfo)it.next();
//	System.out.println(ri.getAmount());
//	}
//	}


//	public void testLoadContractOwnProduce(){
//	ContractOwnProduce cop = null;	
//	System.out.println("===================testLoadContractOwnProduce=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<ContractOwnProduce> contractOwnProduce = foramlContractService.loadContractOwnProduce(1);
//	Iterator<ContractOwnProduce> it = contractOwnProduce.iterator();
//	while(it.hasNext()){
//	cop = (ContractOwnProduce)it.next();
//	System.out.println(cop.getConOwnProdPrice());
//	}
//	}


//	public void testGetContractOwnProduceName(){
//	System.out.println("===================testGetContractOwnProduceName=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.getContractOwnProduceName(36));
//	}




//	public void testgetRBCPBean(){
//	RealBillReceProBean r = null;
//	System.out.println("-------------------------------");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<RealBillReceProBean>  list = foramlContractService.getRBCPBean(1L);
//	System.out.println(list.size());
//	Iterator it = list.iterator();
//	while(it.hasNext()){
//	r = (RealBillReceProBean)it.next();
//	System.out.println(r.getStageName());
//	}
//	System.out.println("-------------------------------");
//	}





//	public void testLoadRealContractRecepro(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<RealContractRecepro> rcrp = null;
//	rcrp = foramlContractService.loadRealContractRecepro(1);
//	System.out.println(rcrp.size());
//	}


//	public void testLoadContractAssistanceContract(){
//	System.out.println("===================testLoadContractAssistanceContract=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<AssistanceContract> ac = null;
//	ac = foramlContractService.loadContractAssistanceContract(1);
//	System.out.println(ac.size());
//	}


//	public void testLoadAssistancePayInfo(){
//	System.out.println("===================testLoadAssistancePayInfo=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	List<AssistancePayInfo> ac = null;
//	ac = foramlContractService.loadAssistancePayInfo(1);
//	System.out.println(ac.size());
//	}


//	public void testLoadAssistancePayInfoList(){
//	System.out.println("===================testLoadAssistancePayInfoList=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");

//	List<AssistanceContract> ac = null;
//	ac = foramlContractService.loadContractAssistanceContract(1);


//	List<List<AssistancePayInfo>> apiList = null;
//	apiList = foramlContractService.loadAssistancePayInfoList(ac);
//	System.out.println(apiList.size());
//	}


//	public void testQueryContractMainInfo(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	ContractMainInfo c = null;		
//	List<ContractMainInfo> cmiList = foramlContractService.queryContractMainInfo(
//	null, null, null, null);	

//	Iterator i = cmiList.iterator();
//	Set<String> s = new HashSet();
//	while(i.hasNext()){
//	c = (ContractMainInfo)i.next();
//	s.add(c.getConName());
//	}
//	assertTrue(s.contains("1"));
//	assertTrue(s.contains("2"));
//	assertTrue(s.contains("3"));
//	}


//	public void testLoadRealContractBillpro(){
//	System.out.println("===================testLoadRealContractBillpro=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	String[] ss = {"1","2"};

//	List<RealContractBillandRecePlan> rcplanList = null; 
//	rcplanList = foramlContractService.loadRealContractBillpro(ss);

//	System.out.println(rcplanList.size());
//	}



//	public void testGetRCListInfo(){
//	System.out.println("===================testGetRCListInfo=====================");
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	Object[] o  = (Object[])foramlContractService.getRCListInfo(1);
//	for(Object obj:o){
//	System.out.println(obj);
//	}
//	}

//	public void testExistApplyBill(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.existApplyBill(1L));
//	}

//	public void testExistInvoice(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.existInvoice(1L));
//	}

//	public void testExistInvoiceF(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.existInvoiceF(1L));
//	}

//	public void testStatApplyBillAmount(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.statApplyBillAmount(1L));
//	}

//	public void testStateInvoiceAmount(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.stateInvoiceAmount(1L));
//	}

//	public void testStateReceAmount(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.stateReceAmount(1L));
//	}

//	public void testExistReceipt(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.existReceipt(1L));
//	}

//	public void testModifyConState(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	foramlContractService.modifyConState(1L, 4L );
//	ContractMainInfo cmi = foramlContractService.loadContractMainInfo(1L);
//	SeeBeanFields.travBean(cmi);
//	}


//	public void testAllInvoice(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.allInvoice(1L));
//	}


//	public void testAllRece(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.getAllMoney(1L));
//	}

//	public void testGetAllTicket(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.getAllTicket(1L));
//	}

//	public void testTranBillStateToName(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.tranBillStateToName(1));
//	}


//	public void testTranReceStateToName(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.tranReceStateToName(1));
//	}

//	public void testGetContractBillStateName(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.getContractBillStateName(1L));
//	}

//	public void testGetContractReceStateName(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.getContractReceStateName(1L));
//	}

//	public void testGetAllAssistance(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.getAllAssistance(1L));
//	}


//	public void testAllAssistanceOver(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.allAssistanceOver(1L));
//	}

//	public void testGetItemNo(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.getItemNo(1L));
//	}

//	public void testNeedPE(){
//	foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//	System.out.println(foramlContractService.needPE(1L));
//	}

//	public void testAllPEOver(){
//		foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
//		System.out.println(foramlContractService.allPEOver(1L));
//	}

	
	// 
//	public void testApplyHasCont(){
//		invoiceService = (IForamlContractService)context.getBean("ForamlContractService");
//		System.out.println(foramlContractService.applyHasCon(299L));
//		System.out.println(foramlContractService.applyHasCon(301L));
//	}
	
	public void testAllItemCostSure(){
		foramlContractService = (IForamlContractService)context.getBean("ForamlContractService");
		System.out.println(foramlContractService.allItemCostSure(1L));
	}
	
	
}

