package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.DateUtil;
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
import com.baoz.yx.entity.contract.ChangingContractMainInfo;
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
import com.baoz.yx.utils.SqlUtils;
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
	public  String getItemNo(long conItemSn) {
		//		List<ContractItemMaininfo> cimiList= null;
		String itemSn = (String)commonDao.uniqueResult("select c.conItemId from ContractItemMaininfo c " +
				" where   c.conItemMinfoSid = ?", conItemSn);
		return itemSn;
	}

	@SuppressWarnings("unchecked")
	public  List<String> getItemNoByConId(long conSid){
		return commonDao.list("select c.conItemId from ContractItemMaininfo c " +
				" where   c.contractMainInfo = ? and  c.conItemId is not null", conSid);	
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
	 * 载入项目号列表，查询用
	 */
	@SuppressWarnings("unchecked")
	public  Map<Long,List<String>> loadItemNoByPageInfo(PageInfo info){
		Map<Long,List<String>> itemNoList = null;
		List<Object[]> oArray =(List<Object[]>)info.getResult();
		ContractMainInfo cmi = null;
		if( oArray!=null && oArray.size()>0 ){
			itemNoList = new HashMap<Long,List<String>>();
			for(Object[] o : oArray){
				cmi = (ContractMainInfo)o[0];
				List<String> x = this.getItemNoByConId(cmi.getConMainInfoSid());
				if(x.size() > 0 && x.get(0) != null ){
					itemNoList.put(cmi.getConMainInfoSid() , x  );
				}
			}
		}
		return itemNoList;
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
		am = commonDao.list("from ApplyMessage a where a.mainId = ? and a.is_active != 0 order by a.id desc " , conSysID);
		return am;	
	}


	/**
	 * 按合同系统号 获取 合同实际开票和收款计划
	 */
	@SuppressWarnings("unchecked")
	public List<RealContractBillandRecePlan> loadRealContractBillandRecePlan(long conSysID){
		List<RealContractBillandRecePlan> planList = null;
		planList = commonDao.list("from RealContractBillandRecePlan r " +
				"where  r.conMainInfoSid = ?" +
				" order by r.realPredBillDate ,   r.conItemStage,r.contractItemMaininfo" , conSysID);
		return planList;
	}


	/**
	 * 按合同系统号 获取 合同实际开票和收款计划、项目信息
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> loadRCPlanAndItem (Long conSysID){
		List<Object[]> planList = null;
		planList = commonDao.list("select  r,c.conItemId from RealContractBillandRecePlan r ,ContractItemMaininfo c " +
				"where  r.conMainInfoSid = ?  and   r.contractItemMaininfo = c.conItemMinfoSid " +
				"order by r.realPredBillDate ,   r.conItemStage,r.contractItemMaininfo" , conSysID);
		return planList;
	}


	/**
	 * 按合同系统号 获取 合同开票、收据信息
	 */
	@SuppressWarnings("unchecked")
	public List<InvoiceInfo> loadInvoiceInfo(long conSysID){
		if( this.existApplyBill(conSysID , 1) ){
			logger.info("存在发票信息");
			List<InvoiceInfo> conInvoiceInfoList = null;
			conInvoiceInfoList = commonDao.list("select i from InvoiceInfo i ,ApplyBill a where  a.contractMainInfo = ? and  " +
					"  i.applyInvoiceId = a.billApplyId order by i.invoiceDate ", conSysID);		 
			return conInvoiceInfoList;
		}
		return null;
	}


	/**
	 * 按合同开票、收据明细系统号获取 合同收款信息
	 */
	@SuppressWarnings("unchecked")
	public List<ReveInfo> loadReceInfo(long invoiceInfoSid){
		if( this.hasData("ReveInfo", "billSid", invoiceInfoSid) ){
			logger.info("发票"+invoiceInfoSid+"存在收款信息");
			List<ReveInfo> reveList = commonDao.list("from ReveInfo r where r.is_active != 0   and r.billSid = ?  order by r.amountTime " , invoiceInfoSid);
			return reveList;	
		}
		return null;
	}


	/**
	 * 按合同一堆开票信息，获取一堆收款信息
	 */
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
				if(riList!=null && riList.size()>0)
					conReveInfoList.add(riList);
			}
		}
		return conReveInfoList;
	}


	/**
	 * 获取一堆收款信息 按合同
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> loadReceInfoListByCon (Long conId){
		String sqlBase = "";
		ContractMainInfo cmi = this.loadContractMainInfo(conId) ;
		List<Object[]> r = null;

		if(cmi.getContractType().equals("1")){
			sqlBase = "select i.conItemId ,r from ContractItemMaininfo i, ReveInfo r where i.conItemMinfoSid = r.billSid" +
			" and r.conMainInfoSid = ? ";
		}
		else{
			sqlBase = "select 1 ,r from ReveInfo r where  r.conMainInfoSid = ?";
		}

		sqlBase+=" order by r.amountTime ";
		r =  commonDao.list(sqlBase , conId);
		return r;
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
	public List<Object[]> loadContractAssistanceContract(long conSysID){
		if( this.hasData("AssistanceContract", "contractId", conSysID) ){
			logger.info("存在外协信息");
			List<Object[]> conAssistanceContract = commonDao.list(" select a,cimi.conItemId,cimi.itemResDept from " +
					" AssistanceContract a,ContractItemMaininfo cimi " +
					" where cimi.conItemMinfoSid = a.conItemMainInfoSid " +
					"and a.is_active!=0 and a.contractId = ?  order by a.id desc " , conSysID);
			return conAssistanceContract;
		}
		else{
			return null;
		}
	}

	/**
	 * 按合同外协系统号获取 合同外协支付信息
	 */
	@SuppressWarnings("unchecked")
	public List<AssistancePayInfo> loadAssistancePayInfo(long acSysID){

		if( this.hasData("AssistancePayInfo", "assistanceId", acSysID) ){
			logger.info("外协"+acSysID+"存在支付信息");
			List<AssistancePayInfo>  assistancePayInfoList = commonDao.list("from AssistancePayInfo a " +
					" where a.assistanceId = ? and a.is_active != 0  order by a.applyDate" , acSysID);
			return assistancePayInfoList;
		}
		else{
			return null;
		}
	}

	/**
	 * put in a mass assistance info,push out a mass assistance pay information
	 */
	public List<List<AssistancePayInfo>> loadAssistancePayInfoList(List<Object[]> acList){
		List<List<AssistancePayInfo>> acpiList = null;
		if(acList != null && acList.size()>0){
			acpiList  = new ArrayList<List<AssistancePayInfo>>();

			AssistanceContract ac = null;			  //assistance information
			List<AssistancePayInfo> apis = null;	   //assistance payinfo List
			long acSid = -1;      //assistance contract sid
			
			List<AssistanceContract> la = new ArrayList<AssistanceContract>();
			for(Object[] o:acList){
				la.add( (AssistanceContract)o[0] );
			}
			
			Iterator<AssistanceContract> it = la.iterator();
			//loop assistance info,get sid
			while(it.hasNext()){
				ac = it.next();
				acSid = ac.getId();
				//get sid，find out corresponding assistance pay info
				apis = this.loadAssistancePayInfo(acSid); 
				if(apis!=null && apis.size()>0)
					acpiList.add(apis);
			}	
		}
		return acpiList;
	}



	/**
	 * 按合同合同项目阶段号返回阶段名称
	 * @see com.baoz.yx.service.IForamlContractService#getItemStageName(java.lang.Long)
	 */
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
		Long stat = null;
		stat =(Long)commonDao.uniqueResult("select a.initIsNoContract" +
				"  from ApplyBill a where " +
				" a.billApplyId = ?", applyBillSid);
		if(stat.equals(0L)){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * 按开票申请系统号获取开票性质
	 */
	public String getInvoiceNature(long applyInvoiceId){
		String invoiceNature = null;
		String invoiceNatureId = null;

		invoiceNatureId = (String)commonDao.uniqueResult("select a.billNature from ApplyBill a " +
				" where a.billApplyId = ?", applyInvoiceId);		

		if(invoiceNatureId!=null){
			invoiceNature = typeManageService.getYXTypeManage(1012L,invoiceNatureId).getTypeName();
		}
		return invoiceNature;
	}


	/**
	 * 按客户系统号 获取客户名称(简称)
	 */
	public String getCustomerName(long customerSysID){
		String customerName = null;
		customerName = (String)commonDao.uniqueResult("select c.name from YXClientCode c " +
				" where c.id = ?", customerSysID);
		return customerName;
	}

	/**
	 * 按客户系统号获取客户名称 (全称)
	 */
	public String getCustomerFullName(long customerSysID){
		String customerName = null;
		customerName = (String)commonDao.uniqueResult("select c.fullName from YXClientCode c " +
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
	 * 
	 * 
	 *  select count(*) , cd.id, cd.name from yx_client cd , yx_con_main_info cmi , 
	 *  yx_organization_tree  orgTree, yx_exployee emp   where cmi.sale_man = emp.id  and cmi.con_customer = cd.id and
	 *	emp.position=orgTree.id and  cd.id = cmi.con_customer and cmi.con_state > 3  and orgTree.Organization_Code like '10%' group
	 *	 by cd.id, cd.name
	 */
	private List<Object[]>  clientList;	
	@SuppressWarnings("unchecked")
	public List<Object[]> loadSaleContractCustomerIndex(Long eid){	

		StringBuffer queryHql = new StringBuffer("select count(*) , cd.id, cd.name from YXClientCode cd , ContractMainInfo cmi ," +
				" OrganizationTree orgTree, Employee emp  " +
				" where cmi.saleMan = emp.id  and cmi.conCustomer = cd.id and emp.position=orgTree.id and " +
		" cd.id = cmi.conCustomer and cmi.conState > 3 and cmi.conState < 10");


		Long saleId = null;
		String groupId = null;

		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			saleId = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		else if(StringUtils.isBlank(groupId)){
			groupId = user.getPosition().getOrganizationCode();			
		}

		if (groupId != null && !"".equals(groupId)) {
			queryHql.append(" and orgTree.organizationCode like '").append(groupId+"%'");
		}

		if( saleId!=null && !saleId.equals("")){
			queryHql.append(" and cmi.saleMan = " + saleId );
		}
		clientList = commonDao.list( queryHql.toString() + " group by cd.id, cd.name" );
		return clientList;
	}

	/***
	 * 按客户分类搜索合同，销售员过滤, 注：此方法调用了上面的方法来获取客户索引,和下面的方法获取客户合同列表
	 */
	public  Map<Long ,List<String[]>> conMap;
	public Map<Long ,List<String[]>> loadSaleContractGroupByCustomer(Long eid){
		//调用上面的方法获取客户ID索引
		List<Object[]> lo = this.loadSaleContractCustomerIndex(eid);

		List<String[]> ls = null;     //客户合同列表
		conMap = new HashMap<Long ,List<String[]>>();
		for(Object[] o:lo){
			//			logger.info(Long.valueOf(o[1].toString()));
			//遍历索引，获取客户合同列表。。。注:正式合同..还有。。。自己的
			ls = this.loadConNameByCidandUid(Long.valueOf(o[1].toString()),eid);
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
				"where cmi.conState>3 and cmi.conState<10 and cmi.conCustomer = ? ", cid);	
		return conNameList;
	}


	/***
	 * 按客户系统号，搜索合同。。。。正式合同..还有自己的
	 */
	@SuppressWarnings("unchecked")
	public List<String[]> loadConNameByCidandUid(Long cid,Long uid){
		List<String[]> conNameList = new ArrayList<String[]>();
		//		conNameList = commonDao.list("select cmi.conMainInfoSid ,cmi.conName from ContractMainInfo cmi " +
		//		"where cmi.conState>3 and cmi.conCustomer = ?  and cmi.saleMan = ?", cid, uid);	

		StringBuffer queryHql = new StringBuffer(" select cmi.conMainInfoSid ,cmi.conName from YXClientCode cd , ContractMainInfo cmi ," +
				" OrganizationTree orgTree, Employee emp  " +
				" where cmi.saleMan = emp.id  and cmi.conCustomer = cd.id and emp.position=orgTree.id and " +
		" cd.id = cmi.conCustomer and cmi.conState > 3 and  cmi.conState < 10 ");

		Long saleId = null;
		String groupId = null;
		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			saleId = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		else if(StringUtils.isBlank(groupId)){
			groupId = user.getPosition().getOrganizationCode();			
		}

		if (groupId != null && !"".equals(groupId)) {
			queryHql.append(" and orgTree.organizationCode like '").append(groupId+"%'");
		}

		if( saleId!=null && !saleId.equals("")){
			queryHql.append(" and cmi.saleMan = " + saleId );
		}		
		conNameList = commonDao.list( queryHql.toString() + " and cmi.conCustomer = ? ", cid );

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

		if( this.existApplyBill(consid,1) ){
			//存在开票申请.......先判断是否存在发票（正式），如果没有，检查收据
			if (this.existInvoiceF(consid) ){
				//exist invoice ,check sum
				if(this.allInvoice(consid,2)){
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
	 * 0 未收款 1部分收款  2除尾款外全额到款   3全额到款.....因为存在收据的关系。。。超额到款作为全额到款来处理
	 * 
	 * 算法：
	 * 未收款，不存在开票申请即可断定
	 * 部分收款，金额对比   
	 * ---------------------除尾款外全额到款 预留
	 * 全额到款....
	 */
	public Long getAllMoney(long consid){
		Double receAmount = 0d;   //到款总金额
		//		if( this.existApplyBill(consid,1) ){
		//存在开票申请.......先判断是否存在收款
		if(this.allRece(consid)){
			//全额到款
			return 3L;
		}
		else{
			receAmount = this.getReceAmountFromConsid(consid);
			if( receAmount!= null && receAmount > 0  ){
				//存在到款      	//...............尾款判断因该在这里
				return 1L;
			}
			else{
				return 0L;
			}
			//			}
		}
		//		else{
		//		//not exist apply bill
		//		return 0L;
		//		}
	}


	/**
	 * 按合同主体系统号， 合同是否存在开票申请(包含收据)
	 * type 1：包含所有票据类型
	 * type 2：不包含收据
	 * type 3: 只是收据
	 */
	public boolean existApplyBill(Long consid , int type){
		String baseHQL = "select count(*) from ApplyBill ab where ab.contractMainInfo = ? ";
		if(type == 1){
			//包含所有票据类型,不扩展HQL
		}
		else if(type == 2){
			//不包含收据，扩展HQL
			baseHQL = baseHQL + " and  ab.billType <> 4 ";
		}
		else if(type == 3){
			baseHQL = baseHQL + " and  ab.billType = 4 ";
		}
		long l  = (Long)commonDao.uniqueResult( baseHQL , consid);
		logger.info("开票申请数量：" + l);
		if(l > 0 ){
			return true;
		}
		return false;
	}


	/**
	 * 合同是否存在发票(不是收据)
	 */
	public boolean existInvoiceF(Long consid){
		//		logger.info(consid);	
		//存在票据类型。。。不包含收据
		if(this.existApplyBill(consid, 2)){	
			long l = (Long)commonDao.uniqueResult(" select count(*) from ApplyBill a, InvoiceInfo i where  " +
					" a.billApplyId = i.applyInvoiceId  and   a.contractMainInfo = ? and a.billType <> 4 " , consid);			 
			if(l>0){
				return true;
			}		
		}
		return false;
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
	 * 统计发票总额
	 * type 1：包含所有票据类型
	 * type 2：不包含收据
	 * type 3: 只有收据
	 * 
	 * 步骤 1：是否存在开票申请
	 *     2：every开票申请确全额开票
	 *    
	 * select sum(i.invoice_amount) from   yx_apply_bill t,yx_invoice_info  i
	 * where   t.apply_bill_sid =  i.apply_invoice_id  
	 * and t.fk_con_main_info_sid = 1  and t.fk_bill_type <> '4'
	 */
	public Double stateInvoiceAmount(Long consid , int type){
		Double amoutInvoice = 0d;
		String baseHQL = "select sum(i.invoiceAmount) from ApplyBill a, InvoiceInfo i where  " +
		" a.billApplyId = i.applyInvoiceId  and " +
		" a.contractMainInfo = ? ";

		//存在开票申请，不包括收据
		if(type == 1){

			//包括收据，全额开票统计。。。不扩展hql
			//检查是否存在开票申请，所有票据类型
			if(this.existApplyBill(consid, 1)){	
				amoutInvoice = (Double)commonDao.uniqueResult(baseHQL , consid );
				logger.info(".....................开票总额（所有票据类型）：" + amoutInvoice);
				//return amoutInvoice;
			}
		}

		//不包含收据，全额开票统计。。。。扩展hql
		else if(type == 2){
			baseHQL = baseHQL + " and  a.billType <> 4 ";

			//不包括收据，全额开票统计。。。扩展hql
			//检查是否存在开票申请，不包括收据
			if(this.existApplyBill(consid, 2)){	
				amoutInvoice = (Double)commonDao.uniqueResult(baseHQL , consid );
				logger.info("....................开票总额（不包括收据）：" + amoutInvoice);
				//return amoutInvoice;
			}		
		}

		else if(type == 3){
			baseHQL = baseHQL + " and  a.billType = 4 ";
			if(this.existApplyBill(consid, 3)){	
				amoutInvoice = (Double)commonDao.uniqueResult(baseHQL , consid );
				logger.info("............................开票总额（只包括收据）：" + amoutInvoice);
				//return amoutInvoice;
			}
		}

		if(amoutInvoice==null){
			amoutInvoice = 0.0;
		}

		return amoutInvoice;


	}


	/**
	 * 按合同主体系统号，统计到款总额...收据带...此方法已废弃，请使用方法“getReceAmountFromConsid”
	 * select sum(r.amount) from yx_reve_info r,yx_invoice_info i 
	 *	where r.bill_sid = i.invoice_info_id  and i.fk_con_main_info_sid = 1 and i.type <> 4
	 */
	public Double stateReceAmount(Long consid){
		Double receAmount = 0d;
		//		receAmount = (Double)commonDao.uniqueResult("select sum(r.amount)  from  ReveInfo r,InvoiceInfo i " +
		//		" where r.billSid = i.id  and r.is_active != 0   and i.contractMainSid = ? and i.type <> ? ", consid , this.getReciptSn());
		//		logger.info("收款总额：" + receAmount);

		//按合同号，找到所有开票申请。。。
		//		this.getApplyBillList(consid, 1);
		//		获取开票信息
		List<InvoiceInfo> conInvoiceInfoList = this.loadInvoiceInfo(consid);
		//遍历开票申请找到收款信息
		List<List<ReveInfo>>   conReveInfoList = null;
		if(conInvoiceInfoList != null && conInvoiceInfoList.size()>0){
			conReveInfoList = this.loadReceInfoList(conInvoiceInfoList);
		}
		//遍历收款信息，计算总额
		if(conReveInfoList!=null && conReveInfoList.size()>0){
			receAmount = this.getReceAmount(conReveInfoList);		
		}
		return receAmount;
	}


	/**
	 * 遍历收款信息，获取收款总额
	 */
	public Double getReceAmount(List<List<ReveInfo>> llr){
		Double amount = 0d;
		for(List<ReveInfo> ll: llr  ){
			if(ll!=null && ll.size()>0){
				for(ReveInfo r:ll){
					amount += r.getAmount();
				}
			}
		}
		return amount;
	}



	/**
	 * 获取"收据"标识
	 */
	public String getReciptSn(){
		//select t.type_small from yx_type_manage t where t.type_name='收据'
		String s = "";
		s = (String)commonDao.uniqueResult("select t.typeSmall from YXTypeManage t " +
				" where t.typeName = ?","收据");
		//		logger.info(s);
		return s;
	}


	/**
	 * 按合同主体系统号，获取开票申请List
	 * type 1：包含所有票据类型
	 * type 2：不包含收据 
	 */
	@SuppressWarnings("unchecked")
	public List<ApplyBill> getApplyBillList(Long consid,int sign){
		List<ApplyBill> abList = null;
		String baseHQL = " from ApplyBill ab where ab.contractMainInfo = ? ";
		if(sign == 1){

		}
		else if (sign == 2){
			baseHQL = baseHQL + "  and ab.billType <> 4 ";
		}
		abList = commonDao.list( baseHQL , consid);	
		return abList;
	}



	/**
	 * 按合同主体系统号，获取发票List，不是收据
	 * 获取所有发票，排除收据
	 */
	@SuppressWarnings("unchecked")
	public List<InvoiceInfo> getInvoiceInfoList(Long consid){
		List<InvoiceInfo> iiList = null;
		iiList = commonDao.list("from InvoiceInfo i where i.type <> 4 and   i.contractMainSid = ? ", consid);
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
	public boolean existInvoice(Long consid){
		//存在票据类型。。。不包含收据
		if(this.existApplyBill(consid, 1)){	
			long l = (Long)commonDao.uniqueResult(" select count(*) from ApplyBill a, InvoiceInfo i where  " +
					" a.billApplyId = i.applyInvoiceId  and   a.contractMainInfo = ? and  a.billType = 4  " , consid);			 
			if(l>0){
				return true;
			}		
		}
		return false;
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
		commonDao.save(cmi);
	}


	/***
	 * 更新合同主体信息
	 */
	public  void  updateConMainInfo(ContractMainInfo cmi){
		commonDao.update(cmi);
	}

	/**
	 * 按合同主体系统号，是否全额开票（不包括收据）
	 * type 1：包含所有票据类型
	 * type 2：不包含收据
	 * 
	 * 步骤1： 是否存在开票申请（不包括收据）....方法调用
	 *    2： 检查是否全额提交开票申请（不包括收据）....方法调用
	 *    3： 所有开票申请全额开票。。。和合同金额对比
	 */
	public boolean allInvoice(Long consid , int type){
		boolean sign = false;

		Double billAmount = this.stateInvoiceAmount(consid , type);   //获取开票全额	
		Double conAmount = this.getConTaxAmout(consid);   //获取合同全额

		//		logger.info("合同总额" + conAmount);
		//		logger.info("开票总额（不包含收据）：" + billAmount);

		if(conAmount!= null && billAmount != null){
			if(billAmount >= conAmount) {   //大于先留着吧
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
		Double receAmount = this.getReceAmountFromConsid(consid);
		Double conAmount = this.getConTaxAmout(consid);

		//		logger.info("合同总额" + conAmount);
		//		logger.info("收款总额：" + receAmount);

		if(conAmount!=null && receAmount!=null){
			if(receAmount >= conAmount){    //大于先留着把
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
		assList =  commonDao.list("from AssistanceContract a where a.is_active !=0  and a.contractId = ?  ", consid);
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
		if(assList.size() == 0 || assList==null){
			sign = true;
		}
		else{
			for(AssistanceContract a:assList){
				logger.info("外协系统号："+a.getAssistanceId());
				logger.info("外协状态："+a.getAssistanceType());
				if(a.getAssistanceType().equals("5")){
					sign = true;
					logger.info("外协完成");
				}
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
	 * 按合同主体系统号，返回合同开票状态
	 */
	public String getContractBillStateName(Long consid){
		String billStateName = "";
		Long st = this.getAllTicket(consid);
		billStateName = this.tranBillStateToName(Integer.parseInt(st.toString()));
		return billStateName;
	}


	/**
	 * 按合同主体系统号，返回合同收款状态
	 */
	public String getContractReceStateName(Long consid){
		String reveStateName = "";
		Long st = this.getAllMoney(consid);
		reveStateName = this.tranReceStateToName(Integer.parseInt(st.toString()));
		return reveStateName;
	}

	/**
	 * 按合同主体系统号，返回外协状态
	 * 
	 * select t.assistance_contract_type  from yx_assistance_contract t  where t.con_main_info_sid = 3069 group by t.assistance_contract_type
	 */
	@SuppressWarnings("unchecked")
	public String getOutSoureStatName(Long consid){
		String resultX = "外协未结束";
		List<String> list = commonDao.list("  select  ac.assistanceContractType  from AssistanceContract ac " +
				" where  ac.contractId = ?  group by  ac.assistanceContractType", consid);
		if(  list==null || list.size() == 0 ){
			resultX =  "无";
		}
		else if (   list.size() == 1){
			if ( list.get(0).equals("5") ){
				resultX =  "外协结束";
			}
		}
		return resultX;
	}

	/**
	 * 获取销售员通过组别
	 */
	@SuppressWarnings("unchecked")
	public List<Employee> getSaleManByGroupId(String gid){
		UserDetail userDetail = UserUtils.getUserDetail();
		if(DepartmentUtils.isTeamLeader(userDetail.getPosition().getOrganizationCode())){
			return  commonDao.list("select e from Employee e,OrganizationTree t where e.position = t.id  " +
					"and not exists( select 1 from Role r , RoleEmployee re where r.id = re.roleId and re.userId = e.id and r.code = '01' ) " +
					"and t.organizationCode like ? and e.is_active = '1'  order by NLSSORT(e.name,'NLS_SORT = SCHINESE_PINYIN_M')   ", gid +"%");
		}else{
			return commonDao.list("select e from Employee e where e.id = ? order by NLSSORT(e.name,'NLS_SORT = SCHINESE_PINYIN_M')  ", userDetail.getUser().getId());
		}
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

		if(sign == true){
			logger.info("项目成本确认成功");
		}
		else{
			logger.info("项目成本确认失败");
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
	 * 按合同主体系统号 count一个实体有没有数据。。。
	 */
	public boolean hasData(String entityName, String sidColumnName , Long conSid){
		Long num = (Long)commonDao.uniqueResult("select count(*)" +
				" from "+ entityName +" e where e."  + sidColumnName  + "= ? ", conSid);
		logger.info(num);
		if( num.equals(0L)){
			return false;
		}
		else{
			return true;
		}
	}




	/**
	 * 按实际合同开票收款计划系统号。。获取计划实例
	 */
	public RealContractBillandRecePlan getRCPlan(String rcpalnSid){
		RealContractBillandRecePlan r =  null;
		r = (RealContractBillandRecePlan)commonDao.uniqueResult("from RealContractBillandRecePlan r where  r.realConBillproSid=?",Long.parseLong(rcpalnSid));
		return r;
	}


	/**
	 * 按实际合同开票收款计划系统号.。。计划的项目号，金额
	 */
	public Map<Long, Double> getRcpalnList(String[] rcpalnSid){
		Map<Long, Double> map = new HashMap<Long, Double>();
		RealContractBillandRecePlan r = null;
		//		Long itemNum;
		Double amount;
		for(int i=0;i<rcpalnSid.length;i++ ){
			r = this.getRCPlan(rcpalnSid[i]);
			//itemNum = Long.parseLong(rcpalnSid[i]);
			amount = new Double(r.getRealBillAmount().toString());
			map.put(Long.parseLong(rcpalnSid[i]), amount);
		}
		return map;
	}



	/**
	 * 计划是否同一个合同
	 * 2008年7月28日11:52:37
	 */
	@SuppressWarnings("unchecked")
	public boolean isOneContract(String[] rcbrps){
		StringBuffer sb = new StringBuffer("");

		for(int i=0;i<rcbrps.length;i++){
			if(i < rcbrps.length-1){
				sb = sb.append("'"+rcbrps[i]+"',");
			}
			else{
				sb = sb.append("'"+rcbrps[i]+"'");
			}
		}		
		List<Long> o = (List)commonDao.list("select count(r.conMainInfoSid) " +
				" from RealContractBillandRecePlan r  where  r.realConBillproSid in ( "+  sb.toString() +" ) group by r.conMainInfoSid");
		if(o.size() > 1){
			return false;
		}
		else{
			return true;
		}
	}

	/**
	 * 按计划系统号，返回是否已提交申请。。不包括收据
	 */
	public boolean hasApply(Long rcpalnSid){		
		long n = (Long)commonDao.uniqueResult( "select count(*) from ApplyBill b ,BillandProRelaion br " +
				" where  b.billApplyId = br.applyBill " +
				" and b.billType != 4   and br.realContractBillandRecePlan = ? ", rcpalnSid );
		logger.info(n);
		if(n>0){
			return true;
		}
		else
			return false;
	}


	/**
	 * 通过合同号，获得到款总额
	 * select sum(r.amount) from yx_apply_bill a ,yx_invoice_info i ,yx_reve_info r
	 *	where a.apply_bill_sid = i.apply_invoice_id 
	 *	and i.invoice_info_id = r.bill_sid
	 *	and i.is_active = 1
	 *	and r.is_active = 1
	 *	and a.fk_con_main_info_sid = 1
	 */
	public Double getReceAmountFromConsid( Long conSid ){
		//		return (Double)commonDao.uniqueResult( "select sum(r.amount) from ApplyBill a , InvoiceInfo i,ReveInfo r " +
		//		" where a.billApplyId = i.applyInvoiceId  " +
		//		" and i.id = r.billSid " +
		//		" and i.is_active = 1" +
		//		" and r.is_active = 1" +
		//		" and a.contractMainInfo = ? ",conSid);
		Double sum = (Double)commonDao.uniqueResult( "select sum(r.amount) from ReveInfo r where  r.conMainInfoSid = ? ",conSid);
		if(sum==null){
			sum = 0.0;
		}	
		return sum;
	}


	/**
	 * 通过实际计划号，返回计划余额。。。未收款金额...收据不带
	 * 
	 * select sum(rv.amount) from yx_reve_info rv where rv.bill_sid in (
	 *	select  i.invoice_info_id from yx_invoice_info i where exists (
	 *  select * from yx_real_con_bc_plan t , yx_billandpro_relaion r , yx_apply_bill a 
	 * where t.real_con_billpro_sid = r.fk_real_con_bcplan_sid and 
	 * r.fk_apply_bill_sid = a.apply_bill_sid 
	 * and i.apply_invoice_id = a.apply_bill_sid  and t.real_con_billpro_sid = 294 		 //---and a.fk_bill_type <>4
	 *	) )
	 */
	public Double getRemainAmountByRealPro( Long realConBillproSid ){
		Double realProAmount = Double.valueOf(commonDao.uniqueResult(" select r.realBillAmount from RealContractBillandRecePlan r " +
				" where r.realConBillproSid = ? ", realConBillproSid  ).toString());
		logger.info("计划金额："+realProAmount);

		Double sum = (Double)commonDao.uniqueResult( " select sum(rv.amount) from ReveInfo rv where rv.billSid in ( "  +
				" select i.id from InvoiceInfo i where exists (" +
				"  from RealContractBillandRecePlan t ,   BillandProRelaion r, ApplyBill a " +
				" where  t.realConBillproSid = r.realContractBillandRecePlan  and  " +
				" r.applyBill = a.billApplyId " +
				"  and i.applyInvoiceId =  a.billApplyId  and a.billType <> 4  and t.realConBillproSid = ? ))   ",realConBillproSid);
		logger.info("已收金额（含收据）："+sum);

		if(sum!=null)
			return realProAmount - sum ;
		else
			return realProAmount;
	}


	/**
	 * 查找getAllFormalContract返回的合同
	 * 检查是否能设置合同状态为建议关闭——8
	 *  全额开票(out收据)、全额收（in收据）、外协全部结束
	 */
	public void doSuggustClose(  ){
		//找到合同
		List<ContractMainInfo> cmiList = this.getAllFormalContract();

		if(cmiList!=null &&  cmiList.size()>0){
			for(ContractMainInfo c:cmiList ){			
				if ( this.allInvoice(c.getConMainInfoSid(),2) 
						&&  this.allRece(c.getConMainInfoSid() )
						&& this.allAssistanceOver(c.getConMainInfoSid() ) ){
					//符合条件，修改之
					this.modConState(c.getConMainInfoSid(),8L);
				}	
			}
		}
	}

	/**
	 * 合同主体系统号，修改合同状态
	 */
	public void modConState( Long conSid , Long conState ){
		commonDao.executeUpdate("update ContractMainInfo c set c.conState = ? where c.conMainInfoSid = ? ", conState , conSid );
	}	

	/**
	 * getAllFormalContract   >3 && <8
	 */
	@SuppressWarnings("unchecked")
	public List<ContractMainInfo> getAllFormalContract( ){
		List<ContractMainInfo> cmiList = commonDao.list( " from ContractMainInfo c where c.conState > 3 and  c.conState < 8" );
		return cmiList;
	}


	/***
	 * 按条件返回合同主体信息.................//需要修改，限定“是否属于变更信息”
	 */
	public PageInfo queryContractMainInfo( 
			String start_date,          
			String end_date,
			int conStateSn,
			Long saleId, 
			Long customerId, 
			String groupId,
			String conType,
			String conSn,
			String conName,
			String finalAccount,
			String contractType,
			String partyAProId,
			String partyAConId,
			String deliveryStartDate,
			String deliveryEndDate,
			Double maxAmount,
			Double minAmount,
			String hasClosed,
			String itemSn,
			PageInfo info
	){ 
		StringBuffer queryHql = new StringBuffer("select cmi,yc,orgTree,emp ," +
				" cmi.billInvoiceAmount ," +  			//开票总额
				" cmi.realArriveAmount ," + //到款总额
				" cmi.billReceiptAmount "+                                         //开收据额
				"from ContractMainInfo cmi , YXClientCode yc,OrganizationTree orgTree,Employee emp " +
				" where cmi.saleMan = emp.id   " +
		" and cmi.conCustomer = yc.id and emp.position=orgTree.id");

		String thql = appendQuerySql(queryHql,start_date,end_date, conStateSn,saleId, 
				customerId, groupId,conType,conSn,conName,finalAccount,contractType,partyAProId,partyAConId,deliveryStartDate,deliveryEndDate,
				maxAmount,minAmount,hasClosed,itemSn);

		//		logger.info(thql);
		info = queryService.listQueryResult(SqlUtils.getCountSql(thql),thql,info); 		
		return info;
	}


	/*	获取页面统计值................
	 *  1 sumContractSum    2  billContractSum   3 receiptContractSum  4  reveContractSum
	 */
	public Double getStatSum(   
			String start_date,
			String end_date,
			int conStateSn,
			Long saleId, 
			Long customerId, 
			String groupId,
			String conType,
			String conSn,
			String conName,
			String finalAccount,
			String contractType,
			String partyAProId,
			String partyAConId,
			String deliveryStartDate,
			String deliveryEndDate,
			Double maxAmount,
			Double minAmount,
			String hasClosed,
			String itemSn,
			int sign ){
		StringBuffer sumContractSumHql = null;
		String thql = null;
		switch(sign){
		case 1:
			sumContractSumHql = new StringBuffer(" select sum(cmi.conTaxTamount) " +
					" from ContractMainInfo cmi, YXClientCode yc,OrganizationTree orgTree,Employee emp " +
			" where  cmi.saleMan = emp.id and cmi.conCustomer = yc.id and emp.position=orgTree.id" ) ;
			thql = appendQuerySql(sumContractSumHql,start_date,end_date, conStateSn,saleId, 
					customerId, groupId,conType,conSn,conName,finalAccount,contractType,partyAProId,partyAConId,deliveryStartDate,deliveryEndDate,
					maxAmount,minAmount,hasClosed,itemSn);		
			return (Double)commonDao.uniqueResult(thql);
		case 2:
			sumContractSumHql = new StringBuffer("  select sum(cmi.billInvoiceAmount) " +
					" from ContractMainInfo cmi, YXClientCode yc,OrganizationTree orgTree,Employee emp " +
			" where   cmi.saleMan = emp.id and cmi.conCustomer = yc.id and emp.position=orgTree.id " ) ; 
			thql = appendQuerySql(sumContractSumHql,start_date,end_date, conStateSn,saleId, 
					customerId, groupId,conType,conSn,conName,finalAccount,contractType,partyAProId,partyAConId,deliveryStartDate,deliveryEndDate ,
					maxAmount,minAmount,hasClosed,itemSn);				
			return (Double)commonDao.uniqueResult(thql);
		case 3 :
			sumContractSumHql = new StringBuffer(" select sum(cmi.billReceiptAmount) "+
					" from ContractMainInfo cmi, YXClientCode yc,OrganizationTree orgTree,Employee emp " +
			"  where cmi.saleMan = emp.id and cmi.conCustomer = yc.id and emp.position=orgTree.id  " 		 ) ;
			thql = appendQuerySql(sumContractSumHql,start_date,end_date, conStateSn,saleId, 
					customerId, groupId,conType,conSn,conName,finalAccount,contractType,partyAProId,partyAConId,deliveryStartDate,deliveryEndDate ,
					maxAmount,minAmount,hasClosed,itemSn);				
			return (Double)commonDao.uniqueResult(thql);
		case 4:
			sumContractSumHql = new StringBuffer(" select sum(cmi.realArriveAmount) " +
					"from ContractMainInfo cmi, YXClientCode yc,OrganizationTree orgTree,Employee emp  " +
			" where   cmi.saleMan = emp.id and cmi.conCustomer = yc.id and emp.position=orgTree.id" ) ;
			thql = appendQuerySql(sumContractSumHql,start_date,end_date, conStateSn,saleId, 
					customerId, groupId,conType,conSn,conName,finalAccount,contractType,partyAProId,partyAConId,deliveryStartDate,deliveryEndDate ,
					maxAmount,minAmount,hasClosed,itemSn);		
			Object temp = commonDao.uniqueResult(thql);
			if(temp == null){
				return 0d;
			}
			else{
				return Double.valueOf(temp.toString());
			}
		default:
			logger.info("error args");
		return -1d;
		}

	}

	synchronized private String appendQuerySql(
			StringBuffer hql,
			String start_date,          
			String end_date,
			int conStateSn,
			Long saleId, 
			Long customerId, 
			String groupId,
			String conType,
			String conSn,
			String conName,
			String finalAccount,
			String contractType,
			String partyAProId,
			String partyAConId,
			String deliveryStartDate,
			String deliveryEndDate,
			Double maxAmount,
			Double minAmount,
			String hasClosed, 
			String itemSn ){

		if(start_date != null){
			start_date = start_date.trim();
		}
		if(end_date != null){
			end_date = end_date.trim();
		}
		if(deliveryStartDate != null){
			deliveryStartDate = deliveryStartDate.trim();
		}
		if(deliveryEndDate != null){
			deliveryEndDate = deliveryEndDate.trim();
		}
		if(conType != null){
			conType = conType.trim();
		}
		if(groupId != null){
			groupId = groupId.trim();
		}
		if(conSn != null){
			conSn = conSn.trim().toUpperCase();
		}
		if(conName != null){
			conName = conName.trim().toUpperCase();
		}
		if(itemSn != null){
			itemSn = itemSn.trim().toUpperCase();
		}
		if(finalAccount != null){
			finalAccount = finalAccount.trim();
		}
		if(contractType != null){
			contractType = contractType.trim();
		}
		if(partyAProId != null){
			partyAProId = partyAProId.trim().toUpperCase();
		}
		if(partyAConId != null){
			partyAConId = partyAConId.trim().toUpperCase();
		}

		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			saleId = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			groupId = user.getPosition().getOrganizationCode();
		}


		if (groupId != null && !"".equals(groupId)) {
			String temp = " and orgTree.organizationCode like '" + groupId+"%'";
			hql.append(temp);	
		}

		if( saleId!=null && !saleId.equals("")){
			String temp = " and cmi.saleMan = " + saleId ; 
			hql.append(temp );
		}

		if( customerId!=null && !customerId.equals("") ){
			String temp =" and cmi.conCustomer = " + customerId ; 
			hql.append( temp) ;
		}

		if( conType!=null && !conType.equals("") ){
			String temp = " and cmi.conType = " + conType ;
			hql.append(temp ) ;
		}

		if( conSn!=null && !conSn.equals("") ){
			String temp = " and upper(cmi.conId) like  '%" + conSn +"%'";
			hql.append(temp) ;
		}

		if( conName!=null && !conName.equals("") ){
			String temp = " and upper(cmi.conName) like  '%" + conName +"%'";
			hql.append(temp) ;
		}

		if( itemSn!=null && !itemSn.equals("") ){
			String temp = " and exists ( select 1 from ContractItemMaininfo cim where cim.contractMainInfo = cmi.conMainInfoSid " +
			" and upper(cim.conItemId) like '%"+ itemSn  +"%' ) ";
			hql.append(temp) ;
		}

		if( finalAccount!=null && !finalAccount.equals("")){
			String temp = " and cmi.FinalAccount  = " + finalAccount ;
			hql.append(temp) ;
		}

		if( contractType!=null && !contractType.equals("")){
			String temp = " and cmi.ContractType  = " + contractType ;
			hql.append(temp) ;
		}
		if( partyAProId!=null && !partyAProId.equals("")){
			String temp = " and upper(cmi.partyAProId) like  '%" + partyAProId +"%'";
			hql.append(temp) ;
		}
		if( partyAConId!=null && !partyAConId.equals("")){
			String temp = " and upper(cmi.partyAConId) like  '%" + partyAConId +"%'";
			hql.append(temp) ;
		}
		if(hasClosed!=null && hasClosed.equals("true")){
			String temp = " and cmi.conState = 10 " ; 
			hql.append(temp);
		}
		else{
			//-2 最初的搜索
			if(conStateSn == -2){	
				String temp = " and cmi.conState > 3 and cmi.conState<10" ; 
				hql.append(temp);
			}
			else{
				if(conStateSn!=-1){		
					String temp = " and cmi.conState = " + conStateSn;
					hql.append(temp);
				}
				else{
					String temp = " and cmi.conState > 3 and cmi.conState < 10" ;
					hql.append(temp );
				}
			}
		}


		if(start_date!=null && !start_date.equals("") ){
			String temp = " and  cmi.conSignDate >= to_date('" + start_date  + "','yyyy-mm-dd')" ;
			hql.append(temp);
		}
		if(end_date!=null && !end_date.equals("") ){
			String temp = " and  cmi.conSignDate < to_date('" + end_date  + "','yyyy-mm-dd')+1" ;
			hql.append(temp);
		}


		if(maxAmount!=null && !maxAmount.equals("") ){
			String temp = " and  cmi.conTaxTamount >= " +  maxAmount;
			hql.append(temp);
		}
		if(minAmount!=null && !minAmount.equals("") ){
			String temp = " and  cmi.conTaxTamount <= " +  minAmount ;
			hql.append(temp);
		}

		if ( (deliveryStartDate!=null && !deliveryStartDate.equals("")) 
				&& (deliveryEndDate!=null && !deliveryEndDate.equals(""))) {
			String temp = " and  exists  ( select 1  from ContractOtherInfo coi where " +
			"  coi.contractMainSid = cmi.conMainInfoSid and" +
			"  coi.conDeliveryDate >= to_date('" + deliveryStartDate  + "','yyyy-mm-dd')" +
			"and coi.conDeliveryDate < to_date('" + deliveryEndDate  + "','yyyy-mm-dd')+1 )" ;
			hql.append(temp);
		}
		else{
			if(deliveryStartDate!=null && !deliveryStartDate.equals("") ){
				String temp = " and  exists  ( select 1  from ContractOtherInfo coi where " +
				"  coi.contractMainSid = cmi.conMainInfoSid and" +
				"  coi.conDeliveryDate >= to_date('" + deliveryStartDate  + "','yyyy-mm-dd')  )" ;
				hql.append(temp);
			}
			if(deliveryEndDate!=null && !deliveryEndDate.equals("") ){
				String temp = " and exists ( select 1  from ContractOtherInfo coi where " +
				" coi.contractMainSid = cmi.conMainInfoSid  and" +
				" coi.conDeliveryDate < to_date('" + deliveryEndDate  + "','yyyy-mm-dd')+1 )" ;
				hql.append(temp);
			}
		}

		hql.append(" order by cmi.activeDate desc ,  cmi.conMainInfoSid ");
		return hql.toString();
	}


	@SuppressWarnings("unchecked")
	public ChangingContractMainInfo getChangingContractMainInfoById(Long id) {
		String hql="from ChangingContractMainInfo mainInfo where mainInfo.conMainInfoSid=?";
		List<ChangingContractMainInfo> list=commonDao.list(hql, id);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<RealContractBillandRecePlan> getToBeUpdatedAccountAgePlan() {
		return commonDao.list("from RealContractBillandRecePlan p where ( p.realArriveAmount is null or p.realArriveAmount < p.realTaxReceAmount ) ");
	}

	public void updateAccountAge(RealContractBillandRecePlan plan) {
		RealContractBillandRecePlan agePlan = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, plan.getRealConBillproSid());
		Date today = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);
		Date realBillDate = getRealBillDate(plan.getRealConBillproSid());
		// 如果是收据的话，看有没关联过发票
		if(plan.getBillType().equals("4")){
			Date relationBillDate = getRelationBillDate(plan.getRealConBillproSid());
			// 如果关联过发票，用发票的实际开票日期
			if(relationBillDate != null){
				realBillDate = relationBillDate;
			}
		}
		if(realBillDate != null){
			//逻辑帐龄
			Date logicBillDay = DateUtils.truncate(plan.getRealPredBillDate(), Calendar.DAY_OF_MONTH);
			int logicDays = DateUtil.getDisDays(today, logicBillDay);
			int logicMonths = 0;
			int remainLogic = logicDays%30;
			if(logicDays >= 0 ){
				logicMonths = logicDays/30;
				if(remainLogic == 0){
					logicMonths = logicMonths - 1;//30天整算0个月
				}
			}else{
				logicMonths = logicDays/30 - 1;
				if(remainLogic == 0){
					logicMonths = logicMonths + 1;//-30天整算1个月
				}
			}
			agePlan.setLogicDayAccountAge(logicDays);
			agePlan.setLogicMonthAccountAge(logicMonths);	
			//////////////////////
			//实际帐龄
			Date realTruncBillDate = DateUtils.truncate(realBillDate, Calendar.DAY_OF_MONTH);
			int realDays = DateUtil.getDisDays(today, realTruncBillDate);
			int realMonths = 0;
			int remainReal = realDays%30;
			if(realDays >= 0 ){
				realMonths = realDays/30;
				if(remainReal == 0){
					realMonths = realMonths - 1;//30天整算0个月
				}
			}else{
				realMonths = realDays/30 - 1;
				if(remainReal == 0){
					realMonths = realMonths + 1;//-30天整算1个月
				}
			}
			agePlan.setRealDayAccountAge(realDays);
			agePlan.setRealMonthAccountAge(realMonths);
		}
		commonDao.update(agePlan);
	}

	/**
	 * 获得开票收款计划的实际开票日期
	 * @param billandRecePlanId
	 * @return
	 */
	private Date getRealBillDate(Long billandRecePlanId){
		Date realDate = (Date) commonDao.uniqueResult(" select min(ii.invoiceDate) from BillandProRelaion br,InvoiceInfo ii where br.applyBill = ii.applyInvoiceId and br.realContractBillandRecePlan = ? ", billandRecePlanId);


		return realDate;
	}
	/**
	 * 获得收据关联的开票的实际开票日期
	 * @param billandRecePlanId
	 * @return
	 */
	private Date getRelationBillDate(Long billandRecePlanId){
		Date realDate = (Date) commonDao.uniqueResult(" select min(ii.invoiceDate) from BillandProRelaion br,InvoiceInfo ii,RelationBillAndReceipt rbr where br.applyBill = ii.applyInvoiceId and br.realContractBillandRecePlan = rbr.billRealId and rbr.receiptRealId = ? ", billandRecePlanId);
		return realDate;
	}


	/**
	 * 获取项目部门和金额from合同主体号
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getItemAmountInfo(Long conMainSid){
		ContractMainInfo cmi = (ContractMainInfo)commonDao.load(ContractMainInfo.class, conMainSid);
		if( cmi.getContractType()!=null && cmi.getContractType().equals("1")){
			return commonDao.list(" select t.itemResDept, sum(i.conItemAmountWithTax) "+
					" from ContractItemMaininfo t, ContractItemInfo i  "+
					" where t.conItemMinfoSid = i.contractItemMaininfo  "+
					"  and t.contractMainInfo = ?  "+
					" group by t.itemResDept" , conMainSid ); 
		}
		else{
			return commonDao.list(" select cmi.mainItemDept , cmi.conTaxTamount "+
					" from ContractMainInfo cmi  "+
					" where cmi.id = ?  "  , conMainSid ); 
		}
	}


	/**
	 * 获取项目部门和金额from合同主体号(变更表)
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getChangeItemAmountInfo(Long conMainSid){
		ContractMainInfo cmi = (ContractMainInfo)commonDao.load(ContractMainInfo.class, conMainSid);
		if(cmi.getContractType()!=null && cmi.getContractType().equals("1")){
			return commonDao.list(" select t.itemResDept, sum(i.conItemAmountWithTax) "+
					" from ChangingContractItemMaininfo t, ChangingContractItemInfo i  "+
					" where t.conItemMinfoSid = i.contractItemMaininfo  "+
					"  and t.contractMainInfo = ?  "+
					" group by t.itemResDept" , conMainSid ); 
		}
		else{
			return commonDao.list(" select cmi.mainItemDept , cmi.conTaxTamount "+
					" from ContractMainInfo cmi  "+
					" where cmi.id = ?  "  , conMainSid ); 
		}
	}

	/**
	 * 获取客户
	 */
	public YXClientCode getYXClientCodeByCid(Long cid){
		return (YXClientCode)commonDao.load(YXClientCode.class, cid);
	}

	/**
	 * 通过登陆id，获取OrganizationCode
	 */
	public String getOrganizationCodeByEid(Long eid){
		String hql = "select o.organizationCode from OrganizationTree o, Employee  e " +
		" where e.position = o.id  and e.id = ? ";
		return (String)commonDao.uniqueResult(hql , eid );
	}
	
	/**
	 * 验证合同号
	 */
	public boolean uniqueConNum(String conNum){
		String hql = "select count(*)  from ContractMainInfo cmi where cmi.conId = ? ";
		Long c = (Long)commonDao.uniqueResult(hql , conNum);
		if(c>0){
			return false;
		}
		else{
			return true;
		}
	}
	
}