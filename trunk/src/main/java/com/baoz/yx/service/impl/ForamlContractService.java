package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.SelfProduction;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ContractStateTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

@Service("ForamlContractService")
@Transactional
public class ForamlContractService implements IForamlContractService {

	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;

	@Autowired
	@Qualifier("userService")
	private IUserService userService;

//	@Autowired
//	@Qualifier("invoiceService")
//	private IInvoiceService invoiceService;


	protected Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 按系统号获取合同主体信息
	 */
	public ContractMainInfo loadContractMainInfo(long conSysID) {
		Object obj = commonDao.load(ContractMainInfo.class, conSysID);
		ContractMainInfo cmiTemp = null;
		if(obj!=null){
			try{
				cmiTemp = (ContractMainInfo)obj;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return cmiTemp;
	}

	/**
	 * 按系统号获取合同项目主体信息(非变更信息)
	 */
	@SuppressWarnings("unchecked")
	public List<ContractItemMaininfo> loadContractItemMainInfo(long conMainID) {
		List<ContractItemMaininfo> cimiList= null;
		cimiList = commonDao.list("from ContractItemMaininfo c " +
				" where   c.contractMainInfo = ?", conMainID);
		return cimiList;
	}

	/**
	 * 按项目系统号获取合同项目号
	 */
	@SuppressWarnings("unchecked")
	public  String getItemNo(long conItemSn) {
//		List<ContractItemMaininfo> cimiList= null;
		String itemSn = (String)commonDao.uniqueResult("select c.conItemId from ContractItemMaininfo c " +
				" where   c.conItemMinfoSid = ?", conItemSn);
		return itemSn;
	}


	/**
	 * 按项目主体信息系统号 获取 合同项目内容信息
	 */
	@SuppressWarnings("unchecked")
	public List<ContractItemInfo> loadContractItemInfo(long cimiSysID){
		List<ContractItemInfo> ciiList = null;
		ciiList = commonDao.list("from ContractItemInfo c " +
				" where  c.contractItemMaininfo = ?", cimiSysID);
		return ciiList;
	}

	/**
	 * 按合同系统号 获取 合同项目阶段信息
	 */
	@SuppressWarnings("unchecked")
	public List<ContractItemStage> loadContractItemStageInfo(long conSysID){
		List<ContractItemStage> cis = null;
		cis = commonDao.list("from ContractItemStage c " +
				" where   c.contractMainSid = ?", conSysID);
		return cis;
	}

	/**
	 * 按合同系统号 获取 合同其它信息
	 */
	public ContractOtherInfo loadContractOtherInfo(long conSysID){
		ContractOtherInfo coi = null;
		coi = (ContractOtherInfo)commonDao.uniqueResult("from ContractOtherInfo c where " +
				"   c.contractMainSid = ?", conSysID);
		return coi;
	}

	/**
	 * 按合同系统号 获取 合同申购采购信息
	 */
	@SuppressWarnings("unchecked")
	public List<ApplyMessage> loadContractApplyInfo(long conSysID){
		List<ApplyMessage> am = null;
		am = commonDao.list("from ApplyMessage a where a.mainId = ? order by a.applyDate " , conSysID);
		return am;	
	}

	/**

	 * 按合同系统号 获取 合同实际开票计划
	 */
//	@SuppressWarnings("unchecked")
//	public List<RealContractBillandRecePlan> loadRealContractBillpro(long conSysID){	
//	List<RealContractBillandRecePlan> rcbp = null;
//	rcbp = commonDao.list("from RealContractBillandRecePlan r where r.conMainInfoSid = ?", conSysID);
//	return rcbp;	
//	}

	/**
	 * abandon method	
	 * 按合同系统号 获取 合同实际收款计划
	 */
//	@SuppressWarnings("unchecked")
//	public List<RealContractRecepro> loadRealContractRecepro(long conSysID){
//	List<RealContractRecepro> rcrp = null;
//	rcrp = commonDao.list("from RealContractRecepro r where r.conMainInfoSid = ?", conSysID);		
//	return rcrp;	

//	}

	/**
	 * 按合同系统号 获取 合同实际开票和收款计划
	 */
	@SuppressWarnings("unchecked")
	public List<RealContractBillandRecePlan> loadRealContractBillandRecePlan(long conSysID){
		List<RealContractBillandRecePlan> planList = null;
		planList = commonDao.list("from RealContractBillandRecePlan r " +
				"where  r.conMainInfoSid = ?" +
				"order by r.conItemStage,r.contractItemMaininfo" , conSysID);
		//"rcbp.conMainInfoSid = ?  and rcrp.conMainInfoSid = ?  and " +      //合同主体号
		//"rcbp.conItemStage = rcrp.conItemStage  and " +						//阶段相同
		//"rcbp.contractItemMaininfo = rcrp.contractItemMaininfo  and " +     //部门相同
		//"rcbp.conItemInfoSid = rcrp.conItemInfoSid  and " +	             	//性质相同
		//"rcbp.initContractBillpro = rcrp.initContractRecepro  and " +
		//logger.info(""+proList.getClass().getName());
		return planList;
	}

	/**
	 *
	 * abandon method
	 * 返回开票和收款计划列表，避免在Action中过多的逻辑处理 
	 */
//	public List<RealBillReceProBean> getRBCPBean(long conSysId){
//	List<RealBillReceProBean> rbcpbList = new ArrayList<RealBillReceProBean>();  //实际开票和收款计划信息列表
//	RealBillReceProBean rbcpBean = new RealBillReceProBean();    //实际开票和收款计划信息
//	RealContractBillandRecePlan rcbp = null;	 //实际合同开票计划信息
//	RealContractRecepro rcrp = null;   	 //实际合同收款计划信息

//	List list= this.loadBillandRecePro(conSysId);
//	System.out.println("========================"+list.size()+"=========================");
//	Iterator it=list.iterator();	
//	while(it.hasNext()){
//	Object[] obj = (Object[])it.next();
//	rcbp = (RealContractBillandRecePlan)obj[0];
//	rcrp = (RealContractRecepro)obj[1];  //我靠。。。这个好像没啥用啊（实际收款计划列信息.....不过还是先留着吧）

//	//负责部门，通过项目主体信息号获取
//	rbcpBean.setItemResDept("负责部门");
//	//rcbp.getContractItemMaininfo();     //to be continue

//	//收款和开票阶段名
//	rbcpBean.setStageName("收款和开票阶段名");         //to be continue
//	//rcbp.getConItemStage();

//	//开票性质
//	rbcpBean.setBillType("开票性质");			//to be continue
//	//rcbp.getBillNature();

//	//发票类型
//	rbcpBean.setBillType("发票类型");         //to be continue
//	//rcbp.getBillType();

//	//基准
//	rbcpBean.setBase(rcbp.getBase()+"");
//	//rcbp.isBase();

//	//实际计划开票时间
//	rbcpBean.setRealPredBillDate(rcbp.getRealPredBillDate());
//	//rcbp.getRealPredBillDate().toString();

//	//开票金额
//	rbcpBean.setConItemAmountWithTax(rcbp.getRealBillAmount());
//	//rcbp.getRealBillAmount();

//	//开票确定收入标志
//	rbcpBean.setBillSureSign(rcbp.isBillSureSign());
//	//rcbp.isBillSureSign();

//	//统一开票
//	rbcpBean.setUniteBill(rcbp.isUniteBill());
//	//rcbp.isUniteBill();

//	//丢进列表
//	rbcpbList.add(rbcpBean);
//	}
//	return rbcpbList;
//	}


	/**
	 * 按合同系统号 获取 合同开票、收据信息
	 */
	@SuppressWarnings("unchecked")
	public List<InvoiceInfo> loadInvoiceInfo(long conSysID){
		List<InvoiceInfo> conInvoiceInfoList = null;
		conInvoiceInfoList = commonDao.list("from InvoiceInfo i where  i.contractMainSid = ? order by i.invoiceDate ", conSysID);		
		return conInvoiceInfoList;
	}


	/**
	 * 按合同开票、收据明细系统号获取 合同收款信息
	 */
	@SuppressWarnings("unchecked")
	public List<ReveInfo> loadReceInfo(long invoiceInfoSid){
		List<ReveInfo> reveList = commonDao.list("from ReveInfo r where r.billSid = ?  order by r.amountTime " , invoiceInfoSid);		
		return reveList;	
	}


	/**
	 * 按合同一堆开票信息，获取一堆收款信息
	 */
	@SuppressWarnings("unchecked")
	public List<List<ReveInfo>> loadReceInfoList (List<InvoiceInfo> conInvoiceInfoList){
		List<List<ReveInfo>>   conReveInfoList = null;
		if(conInvoiceInfoList!=null && conInvoiceInfoList.size()>0){
			conReveInfoList = new ArrayList<List<ReveInfo>>();
			InvoiceInfo ii = null;            //发票、收据信息
			List<ReveInfo> riList = null;	   //收款信息
			long billsid = -1;      //开票收据系统号
			Iterator<InvoiceInfo> it = conInvoiceInfoList.iterator();
			//循环发票、获取系统号
			while(it.hasNext()){
				ii = it.next();
				billsid = ii.getId();
				//获取系统号，搜索对应的收款信息
				riList = this.loadReceInfo(billsid);
				conReveInfoList.add(riList);
			}
		}
		return conReveInfoList;
	}



	/**
	 * 按合同系统号获取 合同自有产品信息
	 */
	@SuppressWarnings("unchecked")
	public List<ContractOwnProduce> loadContractOwnProduce(long conSysID){
		List<ContractOwnProduce> contractOwnProduceList = commonDao.list("from ContractOwnProduce c " +
				" where   c.conMinfo = ?  " , conSysID);		
		return contractOwnProduceList;	
	}



	/**
	 * 按合同系统号获取 合同外协信息
	 */
	@SuppressWarnings("unchecked")
	public List<AssistanceContract> loadContractAssistanceContract(long conSysID){
		List<AssistanceContract> conAssistanceContract = commonDao.list("from AssistanceContract a " +
				" where a.contractId = ?  order by a.contractDate " , conSysID);		
		return conAssistanceContract;		
	}

	/**
	 * 按合同外协系统号获取 合同外协支付信息
	 */
	@SuppressWarnings("unchecked")
	public List<AssistancePayInfo> loadAssistancePayInfo(long acSysID){
		List<AssistancePayInfo>  assistancePayInfoList = commonDao.list("from AssistancePayInfo a " +
				" where a.assistanceId = ?  order by a.applyDate" , acSysID);
		return assistancePayInfoList;
	}

	/**
	 * put in a mass assistance info,push out a mass assistance pay information
	 */
	public List<List<AssistancePayInfo>> loadAssistancePayInfoList(List<AssistanceContract> acList){
		List<List<AssistancePayInfo>> acpiList = null;
		if(acList != null && acList.size()>0){
			acpiList  = new ArrayList<List<AssistancePayInfo>>();

			AssistanceContract ac = null;			  //assistance information
			List<AssistancePayInfo> apis = null;	   //assistance payinfo List
			long acSid = -1;      //assistance contract sid
			Iterator<AssistanceContract> it = acList.iterator();
			//loop assistance info,get sid
			while(it.hasNext()){
				ac = it.next();
				acSid = ac.getId();
				//get sid，find out corresponding assistance pay info
				apis = this.loadAssistancePayInfo(acSid);
				acpiList.add(apis);
			}	
		}
		return acpiList;
	}



	/**
	 * 按合同合同项目阶段号返回阶段名称
	 * @see com.baoz.yx.service.IForamlContractService#getItemStageName(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public String getItemStageName(long itemStageSysId){
		String itemStageName = null;
		itemStageName = (String)commonDao.uniqueResult("select c.ItemStageName from ContractItemStage c " +
				" where  c.conIStageSid = ?", itemStageSysId);
		return itemStageName;
	}

	/**
	 * 按合同项目主体序号返回 负责部门名
	 */
	public String getResDeptName(long conItemMinfoSid){
		String resDeptName = null;
		resDeptName = (String)commonDao.uniqueResult("select c.itemResDept from ContractItemMaininfo c " +
				" where  c.conItemMinfoSid = ?", conItemMinfoSid);
		return resDeptName;
	}


	/**
	 * 按自有产品信息系统号 获取产品名称
	 */
	public String getContractOwnProduceName(long ownProductSysId) {
		SelfProduction sp=(SelfProduction)commonDao.load(SelfProduction.class, ownProductSysId);
		return sp.getRegisterName();
	}

	/**
	 *  abandon method
	 * 按合同开票、收据明细系统号获取  合同发票、收款信息
	 */
//	@SuppressWarnings("unchecked")
//	public List<Object[]> loadBillReceInfo(long invoiceInfoSid){
//	//从收款表和收款表来查找，收款表.发票收据明细系统号 == 收款表.发票收据明细系统号
//	List<Object[]> objList = commonDao.list("from InvoiceInfo i, ReveInfo r where  r.billSid = i.id and r.billSid = ?  " , invoiceInfoSid);		
//	return objList;	
//	}

	/**
	 * 按开票申请系统号获取开票内容
	 */
	public String getInvoiceContent(long applyInvoiceId){
		String invoiceContent = null;
		invoiceContent = (String)commonDao.uniqueResult("select a.billContent from ApplyBill a " +
				" where a.billApplyId = ?", applyInvoiceId);		
		return invoiceContent;
	}



	/**
	 * 开票申请初始时 false 已签、 true 未签（开票申请系统号）
	 * 2008年7月14日14:34:53
	 */
	public boolean applyHasNoCon(Long applyBillSid){
		boolean sign = false;
		sign =(Boolean)commonDao.uniqueResult("select a.initIsNoContract" +
				"  from ApplyBill a where " +
				"a.billApplyId = ?", applyBillSid);
		return sign;
	}



	/**
	 * 按开票申请系统号获取开票性质
	 */
	@SuppressWarnings("unchecked")
	public String getInvoiceNature(long applyInvoiceId){
		String invoiceNature = null;
		String invoiceNatureId = null;

		invoiceNatureId = (String)commonDao.uniqueResult("select a.billNature from ApplyBill a " +
				" where a.billApplyId = ?", applyInvoiceId);		


		if(this.applyHasNoCon(applyInvoiceId)){
			invoiceNature = typeManageService.getYXTypeManage(1022L,invoiceNatureId).getTypeName();
		}
		else{
			invoiceNature = typeManageService.getYXTypeManage(1012L,invoiceNatureId).getTypeName();
		}

//		确定已签申请，未签
//		invoiceNatureId = (String)commonDao.uniqueResult("select a.billNature from ApplyBill a " +
//		" where a.billApplyId = ?", applyInvoiceId);
//		invoiceNature = typeManageService.getYXTypeManage(1012L,invoiceNatureId).getTypeName();
		return invoiceNature;


//		all abandon（存在未签、已签开票2种情况）
//		StringBuffer invoiceNature = new StringBuffer(); //开票性质字符串
//		Boolean is_no_contract = null;                   //是否未签合同开票申请

//		/**
//		* 1 然后按开票申请系统号获得是否未签申请（开票申请系统号，开票申请表）
//		* 2 按申请系统号去相应表获得开票性质（是否未签申请，未签开票内容表、开票申请表）
//		* 
//		* 3.1 如果是已签开票申请，首先通过关联表（开票申请开票计划关联表），找到实际开票计划系统号
//		* 4 按开票计划系统号找到开票性质
//		* 
//		* 3.2 如果是未签申请，从未签信息表获得开票性质
//		*/

//		//获得是否未签申请......true未签 false已签
//		is_no_contract = (Boolean)commonDao.uniqueResult("select a.isNoContract from ApplyBill a where a.billApplyId = ?", applyInvoiceId);


//		//已签申请
//		if (is_no_contract == false){
//		logger.info("==============exist contract bill apply==========");
//		//获得实际开票计划系统号 from BillandProRelaion
//		List<Long> ls = commonDao.list("select b.realContractBillandRecePlan from BillandProRelaion b " +
//		"where  b.applyBill = ?", applyInvoiceId);

//		Iterator<Long> it = ls.iterator();
//		//循环实际开票计划系统号
//		while(it.hasNext()){
//		//通过实际开票计划系统号获取开票性质
//		String bn = (String)commonDao.uniqueResult("select r.billNature from RealContractBillandRecePlan r " +
//		"where  r.realConBillproSid = ?", it.next());
//		logger.info("==========bill nature........." + bn);
//		invoiceNature.append(bn);
//		if(it.hasNext()){
//		invoiceNature.append("/");
//		}
//		}
//		}	
//		//未签申请
//		else if(is_no_contract == true){
////		logger.info("no exist contract bill apply");
////		List<NoContractApplyBillItemInfo> noContractApplyBillItemInfoList = null;
////		NoContractApplyBillItemInfo ntemp = null;
////		//通过开票申请系统号 获得未签信息列表
////		noContractApplyBillItemInfoList = commonDao.list("from NoContractApplyBillItemInfo n " +
////		"where  n.applyBillSid = ?", applyInvoiceId);

////		Iterator<NoContractApplyBillItemInfo> it = noContractApplyBillItemInfoList.iterator();
////		//遍历未签信息列表，获得开票性质
////		while(it.hasNext()){
////		ntemp = (NoContractApplyBillItemInfo)it.next();
////		invoiceNature.append(ntemp.getBillNature());
////		if(it.hasNext()){
////		invoiceNature.append("/");
////		}
////		}
//		}
//		else{
//		invoiceNature.append("error");
//		}
//		return invoiceNature.toString();	
//		}
	}


	/**
	 * 按客户系统号 获取客户名称
	 */
	public String getCustomerName(long customerSysID){
		String customerName = null;
		customerName = (String)commonDao.uniqueResult("select c.name from YXClientCode c " +
				" where c.id = ?", customerSysID);
		return customerName;
	}


	/**
	 * 按联系人系统号 获取联系人名称
	 */
	public String getClientLinkManName(long linkManSysId){
		String linkManName = null;
		linkManName = (String)commonDao.uniqueResult("select c.name from YXLinkMan c " +
				" where c.id = ?", linkManSysId);
		return linkManName;
	}

	/**
	 * 按合同主体系统号，获取项目数
	 */
	public Long getItemNum(long conSysID){
		Long itemNum = null;
		itemNum = (Long)commonDao.uniqueResult("select count(*) from ContractItemMaininfo c " +
				" where  c.contractMainInfo = ?", conSysID);
		return itemNum;
	}

	/**
	 * 按合同主体系统号，获取阶段数
	 */
	public Long getStageNum(long conSysID){
		Long getStageNum = null;
		getStageNum = (Long)commonDao.uniqueResult("select count(*) from ContractItemStage c " +
				" where  c.contractMainSid = ?", conSysID);
		return getStageNum;
	}


	/**
	 * 按合同状态序号，获取合同状态名
	 */
	public String covConStateSnToName(long conStateNum){
		String contractName = null;
		contractName = ContractStateTool.getContractStateSnToName((int)conStateNum);
		return contractName;
	}


	/**
	 * 搜索销售员所有合同客户索引...正式合同
	 * SQLExp:select count(*), c.id, c.NAME  from yx_client   c ,yx_con_main_info   con   where c.id = con.con_customer group by c.id ,c.name
	 * 搜索结果：1 客户合同数量[0]  2 客户系统号ID[1] 3 客户合同名称[2] 
	 */
	private List<Object[]>  clientList;	
	@SuppressWarnings("unchecked")
	public List<Object[]> loadSaleContractCustomerIndex(Long eid){	
		clientList = commonDao.list("select count(*) , cd.id, cd.name from YXClientCode cd , ContractMainInfo cmi " +
				" where cd.id = cmi.conCustomer and cmi.conState > 3 and  cmi.saleMan = " + eid +
		" group by cd.id, cd.name");
		return clientList;
	}

	/***
	 * 按客户分类搜索合同，销售员过滤, 注：此方法调用了上面的方法来获取客户索引,和下面的方法获取客户合同列表
	 */
	public  Map<Long ,List<String[]>> conMap;
	@SuppressWarnings("unchecked")
	public Map<Long ,List<String[]>> loadSaleContractGroupByCustomer(Long eid){
		//调用上面的方法获取客户ID索引
		List<Object[]> lo = this.loadSaleContractCustomerIndex(eid);

		List<String[]> ls = null;     //客户合同列表
		conMap = new HashMap<Long ,List<String[]>>();
		for(Object[] o:lo){
			logger.info(Long.valueOf(o[1].toString()));
			//遍历索引，获取客户合同列表。。。注:正式合同
			ls = this.loadConNameByCid(Long.valueOf(o[1].toString()));
			conMap.put(Long.valueOf(o[1].toString()) , ls);
		}
		return conMap;
	} 

	/***
	 * 按客户系统号，搜索合同。。。。正式合同
	 */
	@SuppressWarnings("unchecked")
	public List<String[]> loadConNameByCid(Long cid){
		List<String[]> conNameList = new ArrayList<String[]>();
		conNameList = commonDao.list("select cmi.conMainInfoSid ,cmi.conName from ContractMainInfo cmi " +
				"where cmi.conState>3 and cmi.conCustomer = ? ", cid);	
		return conNameList;
	}

	/**
	 * 通过登陆id，获取Employee
	 */
	public Employee getEmployee(Long sid){
		return (Employee)commonDao.uniqueResult("from Employee e where e.id=?",sid );
	}

	/**
	 * 通过登陆id，获取员工名
	 */
	public String getName(Long sid){
		Employee  e =  this.getEmployee(sid);
		return e.getName();
	}


	/**
	 * 通过登陆id，获取员工部门
	 */
	public String getDept(Long sid){
		Employee  e =  this.getEmployee(sid);
		return userService.getDepartment(e).getOrganizationName();
	}

	/**
	 * 按供应商代码，获得供应商名
	 */
	public String getSupplyName(long supplysid){
		String supplyName = null;
		supplyName = (String)commonDao.uniqueResult("select s.supplierName from SupplierInfo s where s.supplierid = ?", supplysid);
		return supplyName;
	}


	/**
	 * 按实际开票计划系统号 获取 合同实际开票计划
	 */
	public List<RealContractBillandRecePlan> loadRealContractBillpro(String rcplansid[]){
		RealContractBillandRecePlan rcplan = null;
		List<RealContractBillandRecePlan> rclist = new ArrayList<RealContractBillandRecePlan>();

		for(int i=0;i < rcplansid.length;i++){
			rcplan = (RealContractBillandRecePlan)commonDao.uniqueResult("from RealContractBillandRecePlan r " +
					"where  r.realConBillproSid = ? ", Long.parseLong(rcplansid[i]));
			rclist.add(rcplan);
		}
		return rclist;
	}

	/**
	 * 按合同主体信息系统号，获取客户系统号
	 */
	public Long getCustomerFromConSid(long consysid){
		Long cid = null;
		cid = (Long)commonDao.uniqueResult("select c.conCustomer from ContractMainInfo c " +
				" where c.conMainInfoSid = ?",consysid);
		return cid;
	}

	/**
	 * 按客户系统号 获取客户
	 */
	public YXClientCode getCustomer(long customerSysID){
		YXClientCode  client = (YXClientCode)commonDao.uniqueResult("from YXClientCode c " +
				" where c.id = ?", customerSysID);
		return client;
	}

	/**
	 * 按合同主体系统号 获取合同名称
	 */
	public String getConName(long conMainSid){
		return (String)commonDao.uniqueResult("select c.conName  from ContractMainInfo c where c.conMainInfoSid=?",conMainSid );
	}

	/**
	 * 按合同主体系统号 获取合同号
	 */
	public String getConSn(long conMainSid){
		return (String)commonDao.uniqueResult("select c.conId  from ContractMainInfo c where c.conMainInfoSid=?",conMainSid );
	}

	/**
	 * 按实际计划，获取开票性质、票据类型、基准
	 */
	public Object[] getRCListInfo(long rcplansid){
		Object[] info = null;
		info = (Object[])commonDao.uniqueResult("select r.billNature,r.billType,r.base  " +
				"from RealContractBillandRecePlan r where  r.realConBillproSid=?",rcplansid );
		return info;
	}

	/**
	 * 按合同主体信息系统号，检查合同是否全额开票
	 * 0 未开 1存在收据 2部分未开票 3除尾款外全额开票 4全额开票
	 * 
	 * 算法：
	 * 未开：不存在开票申请即可断定
	 * 存在收据：找收据
	 * 部分未开票：开票总金额对比
	 * -----------------除尾款外全额开票  预留
	 * 全额开票...不说了
	 * 
	 */
	public Long getAllTicket(long consid){

		if( this.existApplyBill(consid) ){
			//存在开票申请.......先判断是否存在发票，如果没有，检查收据
			if (this.existInvoiceF(consid) ){
				//exist invoice ,check sum
				if(this.allInvoice(consid)){
					return 4L;
				}
				else{
					//没有全额开票         //...............尾款判断因该在这里
					return  2L;			
				}
			}
			else{
				//not exist invoice ,check  receipt
				if(this.existInvoice(consid)){
					return 1L;
				}
				else{
					return 0L;
				}
			}		
		}
		else{
			//not exist apply bill
			return 0L;
		}
	}

	/**
	 * 按合同主体信息系统号，检查合同是否全额收款
	 * 0 未收款 1部分收款  2除尾款外全额到款   3全额到款
	 * 
	 * 算法：
	 * 未收款，不存在开票申请即可断定
	 * 部分收款，金额对比   
	 * ---------------------除尾款外全额到款 预留
	 * 全额到款....
	 */
	public Long getAllMoney(long consid){
		Double receAmount = 0d;   //到款总金额
		if( this.existApplyBill(consid) ){
			//存在开票申请.......先判断是否存在收款
			if(this.allRece(consid)){
				//全额到款
				return 3L;
			}
			else{
				receAmount = this.stateReceAmount(consid);
				if( receAmount!= null && receAmount > 0  ){
					//存在到款      	//...............尾款判断因该在这里
					return 1L;
				}
				else{
					return 0L;
				}
			}
		}
		else{
			//not exist apply bill
			return 0L;
		}
	}


	/**
	 * 合同是否存在开票申请
	 */
	@SuppressWarnings("unchecked")
	public boolean existApplyBill(Long consid){
		logger.info(consid);
		boolean sign = false;
		List<ApplyBill> ab = null;
		ab = commonDao.list("from ApplyBill ab where ab.contractMainInfo = ? ", consid);
		if(ab!=null && ab.size()>0){
			logger.info(ab.size());
			sign = true;
		}
		return sign;
	}





	/**
	 * 合同是否存在发票
	 */
	@SuppressWarnings("unchecked")
	public boolean existInvoiceF(Long consid){
		logger.info(consid);
		boolean sign = false;	
		List<InvoiceInfo> invoice = null;
		invoice = commonDao.list("from InvoiceInfo i where i.contractMainSid = ? and " +
				" i.type <> ?", consid ,this.getReciptSn());
		if(invoice!=null && invoice.size()>0){
			sign = true;
			logger.info(invoice.size());
		}
		return sign;
	}

	/**
	 * 统计开票申请总额（含税金额，不包括收据）
	 * select sum(a.bill_amount_tax) from yx_apply_bill a where a.fk_con_main_info_sid = 1 and a.fk_bill_nature <>4
	 */
	public Double statApplyBillAmount(Long consid){
		Double amount = 0d;
		amount = (Double)commonDao.uniqueResult("select sum(a.billAmountTax) from  ApplyBill a where  " +
				" a.contractMainInfo = ?   and  a.billNature <> ? ", consid , this.getReciptSn());
		return amount;
	}

	/**
	 * 统计发票总额（不包括收据）
	 * select sum(y.invoice_amount) from yx_invoice_info y where y.fk_con_main_info_sid=1 and y.type <>4
	 */
	public Double stateInvoiceAmount(Long consid){	
		Double amoutInvoice = 0d;
		amoutInvoice = (Double)commonDao.uniqueResult("select sum(i.invoiceAmount) from  InvoiceInfo i where  " +
				" i.contractMainSid = ?   and  i.type <> ? ", consid , this.getReciptSn());
		logger.info("开票总额（不包括收据）：" + amoutInvoice);
		return amoutInvoice;	
	}


	/**
	 * 按合同主体系统号，统计到款总额...收据不带...
	 * select sum(r.amount) from yx_reve_info r,yx_invoice_info i 
	 *	where r.bill_sid = i.invoice_info_id  and i.fk_con_main_info_sid = 1 and i.type <> 4
	 */
	public Double stateReceAmount(Long consid){
		Double receAmount = 0d;
		receAmount = (Double)commonDao.uniqueResult("select sum(r.amount)  from  ReveInfo r,InvoiceInfo i " +
				" where r.billSid = i.id  and i.contractMainSid = ? and i.type <> ? ", consid , this.getReciptSn());
		logger.info("收款总额：" + receAmount);
		return receAmount;
	}

	/**
	 * 获取"收据"标识
	 */
	public String getReciptSn(){
		//select t.type_small from yx_type_manage t where t.type_name='收据'
		String s = "";
		s = (String)commonDao.uniqueResult("select t.typeSmall from YXTypeManage t " +
				" where t.typeName = ?","收据");
		logger.info(s);
		return s;
	}


	/**
	 * 按合同主体系统号，获取开票申请List，不是收据
	 */
	@SuppressWarnings("unchecked")
	public List<ApplyBill> getApplyBillList(Long consid){
		List<ApplyBill> abList = null;
		abList = commonDao.list("from ApplyBill ab where ab.billNature <> ? and ab.contractMainInfo = ?", this.getReciptSn(),consid);
		return abList;
	}



	/**
	 * 按合同主体系统号，获取发票List，不是收据
	 * 获取所有发票，排除收据
	 */
	@SuppressWarnings("unchecked")
	public List<InvoiceInfo> getInvoiceInfoList(Long consid){
		List<InvoiceInfo> iiList = null;
		iiList = commonDao.list("from InvoiceInfo i where i.type <> ? and   i.contractMainSid = ? ",this.getReciptSn(), consid);
		return iiList;
	}

	/**
	 * 按合同主体系统号，是否存在剩余收据
	 */
	public boolean existReceipt(Long consid){
		//boolean sign = false;
		List<InvoiceInfo> iiList = this.getReceiptInfoList(consid);
		InvoiceInfo i = null;
		Long invoiceId = 0L;	//发票系统号
		Double iamount = 0d;	//发票总额
		Double receAmount = 0d;  //关联收据总额
		//遍历收据，判定金额
		Iterator<InvoiceInfo> it = iiList.iterator();
		while(it.hasNext()){
			i = it.next();
			invoiceId = i.getId();
			iamount = i.getInvoiceAmount();
			logger.info("发票总额"+iamount);
			//找出一个金额，然后去收据关联表对比
			//select sum(t.relate_amount) from yx_relation_amount t where t.invoice_to_inovice = 3	
			receAmount = (Double)commonDao.uniqueResult("select sum(r.relateAmount) from RelationAmount r where r.invoiceToInvoice = ?", invoiceId);
			logger.info("已转金额:"+receAmount);
			if(receAmount!=null){
				if (iamount > receAmount){
					return false;
				}
			}
		}
		return true;
	}



	/**
	 * 合同是否存在收据
	 */
	@SuppressWarnings("unchecked")
	public boolean existInvoice(Long consid){
		logger.info(consid);
		boolean sign = false;

		List<InvoiceInfo> invoice = null;
		invoice = commonDao.list("from InvoiceInfo i where i.contractMainSid = ? and " +
				" i.type = ?", consid ,this.getReciptSn());
		if(invoice!=null && invoice.size()>0){
			sign = true;
			logger.info(invoice.size());
		}
		return sign;
	}


	/**
	 * 按合同主体系统号，所搜合同所有收据
	 */
	@SuppressWarnings("unchecked")
	public List<InvoiceInfo>  getReceiptInfoList(Long consid){
		List<InvoiceInfo> iiList = null;
		iiList = commonDao.list("from InvoiceInfo i where i.type = ? and   i.contractMainSid = ? ",this.getReciptSn(), consid);
		return iiList;	
	}


	/***
	 * 按合同主体系统号，修改合同状态
	 */
	public void  modifyConState(Long consid, Long conState){
		ContractMainInfo cmi = this.loadContractMainInfo(consid);
		cmi.setConState(conState);	
	}


	/***
	 * 更新合同主体信息
	 */
	public  void  updateConMainInfo(ContractMainInfo cmi){
		commonDao.update(cmi);
	}

	/**
	 * 按合同主体系统号，是否全额开票（不包括收据）
	 */
	public boolean allInvoice(Long consid){
		boolean sign = false;

		Double billAmount = this.stateInvoiceAmount(consid);
		Double conAmount = this.getConTaxAmout(consid);

		logger.info("合同总额" + conAmount);
		logger.info("开票总额（不包含收据）：" + billAmount);

		if(conAmount!= null && billAmount != null){
			if(billAmount.equals(conAmount) ){
				sign = true;
			}
		}
		return sign;
	}


	/**
	 * 按合同主体系统号，是否全额收款(到款)
	 */
	public boolean allRece(Long consid){
		boolean sign = false;
		Double receAmount = this.stateReceAmount(consid);
		Double conAmount = this.getConTaxAmout(consid);

		logger.info("合同总额" + conAmount);
		logger.info("收款总额：" + receAmount);

		if(conAmount!=null && receAmount!=null){
			if(receAmount.equals(conAmount)){
				sign = true;
			}
		}
		return sign;
	}


	/**
	 * 按合同主体系统号，获取合同总金额（含税价）
	 */
	public Double getConTaxAmout(Long consid){
		Double amount = 0d;
		ContractMainInfo cmi = this.loadContractMainInfo(consid);
		amount = cmi.getConTaxTamount();	
		return amount;
	}


	/**
	 * 按合同主体系统号， 找到合同所有外协
	 */
	@SuppressWarnings("unchecked")
	public List<AssistanceContract> getAllAssistance(Long consid){
		List<AssistanceContract> assList = null;
		assList =  commonDao.list("from AssistanceContract a where a.contractId = ?  ", consid);
		logger.info("共有外协："+assList.size());
		return assList;
	}



	/**
	 * 按合同主体系统号， 合同外协是否全部结束
	 */
	public boolean allAssistanceOver(Long consid){
		boolean sign = false;
		List<AssistanceContract> assList = null;
		assList = this.getAllAssistance(consid);
		for(AssistanceContract a:assList){
			logger.info("外协系统号："+a.getAssistanceId());
			logger.info("外协状态："+a.getAssistanceType());
			if(a.getAssistanceType().equals("5")){
				sign = true;
			}
		}
		return sign;
	}



	/**
	 * 翻译开票状态
	 */
	public String tranBillStateToName(int billState){
		String billStateName = "";
		billStateName = ContractStateTool.getContractBillState(billState);			
		return 	billStateName;
	}


	/**
	 * 翻译收款状态
	 */
	public String tranReceStateToName(int receState){
		String recStateName = "";
		recStateName = ContractStateTool.getContractReceState(receState);	
		return 	recStateName;		
	}



	/**
	 * 按合同主体系统号，返回合同开票状态名
	 */
	public String getContractBillStateName(Long consid){
		String billStateName = "";
		Long st = this.getAllTicket(consid);
		billStateName = this.tranBillStateToName(Integer.parseInt(st.toString()));
		return billStateName;
	}


	/**
	 * 按合同主体系统号，返回合同开票收款名
	 */
	public String getContractReceStateName(Long consid){
		String reveStateName = "";
		Long st = this.getAllMoney(consid);
		reveStateName = this.tranReceStateToName(Integer.parseInt(st.toString()));
		return reveStateName;

	}


//	/**
//	* 按合同主体系统号，查找是否需要工程经济
//	* 001 开工报告
//	* 010 实物交接
//	* 100 竣工验收
//	*/
//	public String needPE(Long consid){
//	StringBuffer npe = new StringBuffer("");
//	boolean needPerativeReport = (Boolean)commonDao.uniqueResult("select c.needPerativeReport" +
//	" from ContractOtherInfo c where c.contractMainSid = ?  ", consid);
//	logger.info("开工报告："+ needPerativeReport);

//	if(needPerativeReport){
//	npe.append(1);
//	}
//	else{
//	npe.append(0);
//	}

//	boolean needRecivedThing = (Boolean)commonDao.uniqueResult("select c.needRecivedThing" +
//	" from ContractOtherInfo c where c.contractMainSid = ?  ", consid);
//	logger.info("实物交接："+ needRecivedThing);

//	if(needRecivedThing){
//	npe.append(1);
//	}
//	else{
//	npe.append(0);
//	}

//	boolean needFinallyReport = (Boolean)commonDao.uniqueResult("select c.needFinallyReport" +
//	" from ContractOtherInfo c where c.contractMainSid = ?  ", consid);
//	logger.info("竣工验收："+ needFinallyReport);

//	if(needFinallyReport){
//	npe.append(1);
//	}
//	else{
//	npe.append(0);
//	}

//	logger.info(npe);

//	return npe.toString();
//	}


//	/**
//	* 按合同主体系统号，返回工程经济是否全部完成
//	*/
//	public boolean allPEOver(Long consid){
//	StringBuffer sign =  new StringBuffer("");
//	String needPE = this.needPE(consid);
//	Date t = null;

//	//需要开工报告
//	if( "1".equals(needPE.substring(0, 1))){
//	logger.info("需要开工报告");
//	t = (Date)commonDao.uniqueResult("select c.perativeReport" +
//	" from ContractOtherInfo c where c.contractMainSid = ?  ", consid);
//	logger.info("开工报告日期:" + t);
//	if(t==null){
//	sign.append(0);
//	}
//	else{
//	sign.append(1);
//	}
//	}
//	else{
//	sign.append(1);
//	}
//	//需要实物交接
//	if( "1".equals(needPE.substring(1, 2))){
//	logger.info("需要实物交接");
//	t = (Date)commonDao.uniqueResult("select c.recivedThing" +
//	" from ContractOtherInfo c where c.contractMainSid = ?  ", consid);
//	logger.info("实物交接日期:" + t);
//	if(t==null){
//	sign.append(0);
//	}
//	else{
//	sign.append(1);
//	}
//	}
//	else{
//	sign.append(1);
//	}

//	//需要竣工验收
//	if(  "1".equals(needPE.substring(2, 3))){
//	logger.info("需要竣工验收");
//	t = (Date)commonDao.uniqueResult("select c.finallyReport" +
//	" from ContractOtherInfo c where c.contractMainSid = ?  ", consid);
//	logger.info("竣工验收日期:" + t);
//	if(t==null){
//	sign.append(0);
//	}
//	else{
//	sign.append(1);
//	}
//	}
//	else{
//	sign.append(1);
//	}

//	if(sign.toString().equals("111")){
//	return true;
//	}

//	return false;
//	}


	/**
	 * 按合同主体系统号，返回项目成本确认
	 */
	public boolean allItemCostSure(Long consid){
		boolean sign = true;
		List<ContractItemMaininfo> cimiList = this.loadContractItemMainInfo(consid);
		logger.info("存在项目数："+cimiList.size());
		for(ContractItemMaininfo cimi:cimiList){
			logger.info("项目成本确认状态："+ cimi.getConItemCostSure());
			if( cimi.getConItemCostSure().equals(1L) 
					||  cimi.getConItemCostSure().equals(2L)
					|| cimi.getConItemCostSure().equals(4L)  ){
				sign = false;
			}
		}
		return sign;
	}

	/**
	 * 按项目负责人系统号，返回项目负责人
	 */
	public String getChargeManName(Long id){
		String name = "";
		name = (String)commonDao.uniqueResult("select y.name" +
				" from YXChargeMan y where y.id = ?  ", id);
		return name;
	}

	/***
	 * 按条件返回合同主体信息.................//需要修改，限定“是否属于变更信息”
	 */
	@SuppressWarnings("unchecked")
	public PageInfo queryContractMainInfo( 
			String start_date,          
			String end_date,
			int conStateSn,
			Long saleId, 
			Long customerId, 
			String groupId,
			String  conType,
			String conSn,
			String finalAccount,
			String  contractType,
			PageInfo info){ 

		StringBuffer queryHql = new StringBuffer("select cmi,yc,orgTree,emp " +
				"from ContractMainInfo cmi, YXClientCode yc,OrganizationTree orgTree,Employee emp " +
		" where cmi.saleMan = emp.id and cmi.conCustomer = yc.id and emp.position=orgTree.id");
//////	-------

		if(start_date != null){
			start_date = start_date.trim();
		}
		if(end_date != null){
			end_date = end_date.trim();
		}
		if(conType != null){
			conType = conType.trim();
		}
		if(groupId != null){
			groupId = groupId.trim();
		}
		if(conSn != null){
			conSn = conSn.trim();
		}
		if(finalAccount != null){
			finalAccount = finalAccount.trim();
		}
		if(contractType != null){
			contractType = contractType.trim();
		}

		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			saleId = user.getUser().getId();
		}
		if (groupId != null && !"".equals(groupId)) {
			queryHql.append(" and orgTree.organizationCode like '").append(groupId+"%'");
		}

		if( saleId!=null && !saleId.equals("")){
			queryHql.append(" and cmi.saleMan = " + saleId );
		}

		if( customerId!=null && !customerId.equals("") ){
			queryHql.append(" and cmi.conCustomer = " + customerId ) ;
		}

		if( conType!=null && !conType.equals("") ){
			queryHql.append(" and cmi.conType = " + conType ) ;
		}

		if( conSn!=null && !conSn.equals("") ){
			queryHql.append(" and cmi.conId like  '%" + conSn +"%'") ;
		}

		if( finalAccount!=null && !finalAccount.equals("")){
			queryHql.append(" and cmi.FinalAccount  = " + finalAccount) ;
		}

		if( contractType!=null && !contractType.equals("")){
			queryHql.append(" and cmi.ContractType  = " + contractType) ;
		}

		if(conStateSn!=-1){		
			queryHql.append(" and cmi.conState = " + conStateSn);		
		}
		else{
			queryHql.append(" and cmi.conState > 3" );
		}	

//		//最大、最小金额判断
//		if(greater!=null && lesser!=null){
//		amount_hql = " and c.conTaxTamount between "+ greater+" and "+lesser ;
//		}
//		else if(greater!=null && lesser==null){
//		amount_hql = " and c.conTaxTamount > "+greater ;
//		}
//		else if(greater==null && lesser!=null){
//		amount_hql = " and c.conTaxTamount < "+lesser ;
//		}
//		queryHql = new StringBuffer(queryHql.append(amount_hql));

		if(start_date!=null && !start_date.equals("") ){
			queryHql.append(" and  cmi.conSignDate >= to_date('" + start_date  + "','yyyy-mm-dd')");
		}
		if(end_date!=null && !end_date.equals("") ){
			queryHql.append(" and  cmi.conSignDate <= to_date('" + end_date  + "','yyyy-mm-dd')");
		}
		queryHql.append(" order by cmi.conMainInfoSid desc ");
		logger.info(queryHql);
		info = queryService.listQueryResult(queryHql.toString(),info); 

		return info;
	}




}