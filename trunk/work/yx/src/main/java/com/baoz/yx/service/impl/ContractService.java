package com.baoz.yx.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.ContractMaterialSet;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.MaterialManager;
import com.baoz.yx.entity.SelfProduction;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.bill.BillandProRelaion;
import com.baoz.yx.entity.contract.ChangeRealContractBillandRecePlan;
import com.baoz.yx.entity.contract.ChangingContractItemInfo;
import com.baoz.yx.entity.contract.ChangingContractItemMaininfo;
import com.baoz.yx.entity.contract.ChangingContractItemStage;
import com.baoz.yx.entity.contract.ChangingContractItemStagePlan;
import com.baoz.yx.entity.contract.ChangingContractMainInfo;
import com.baoz.yx.entity.contract.ChangingContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractItemStagePlan;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.entity.contract.MonthlyBillpro;
import com.baoz.yx.entity.contract.MonthlyRecepro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.entity.contract.bak.BakContractItemInfo;
import com.baoz.yx.entity.contract.bak.BakContractItemMaininfo;
import com.baoz.yx.entity.contract.bak.BakContractItemStage;
import com.baoz.yx.entity.contract.bak.BakContractItemStagePlan;
import com.baoz.yx.entity.contract.bak.BakContractMainInfo;
import com.baoz.yx.entity.contract.bak.BakContractMaininfoPart;
import com.baoz.yx.entity.contract.bak.BakRealContractBillandRecePlan;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.INoticeService;
import com.baoz.yx.service.ISequenceService;
import com.baoz.yx.service.ITestService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.TaxChange;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.utils.SequenceKey;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.MyBean;
import common.Logger;

@Service("contractService")
@Transactional
public class ContractService implements IContractService {
	
	private static Logger logger=Logger.getLogger(ContractService.class);

	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao commonDao;

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

	@Autowired
	@Qualifier("noticeService")
	private INoticeService noticeservice;
	
	@Autowired
	@Qualifier("codeGenerateService")
	private ICodeGenerateService codeGenerateService;

	@Autowired
	@Qualifier("contractCommonService")
	private IContractCommonService contractCommonService;
	
	@Autowired
	@Qualifier("sequenceService")
	private ISequenceService sequenceService;
	
	@Autowired
	@Qualifier("testService")
	private ITestService testService;
	

	public IYXCommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(IYXCommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public INoticeService getNoticeservice() {
		return noticeservice;
	}

	public void setNoticeservice(INoticeService noticeservice) {
		this.noticeservice = noticeservice;
	}

	public ICodeGenerateService getCodeGenerateService() {
		return codeGenerateService;
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

	public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}

	public void setContractCommonService(
			IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
	}

	public ISequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}


	// 按系统号获取合同主体信息
	public ContractMainInfo loadContractMainInfo(long conID) {
		Object obj = commonDao.load(ContractMainInfo.class, conID);
		ContractMainInfo ctemp = null;
		if (obj != null) {
			try {
				ctemp = (ContractMainInfo) obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ctemp;
	}

	
	// 按系统号获取合同主体信息
	public ChangingContractMainInfo loadChangeContractMainInfo(long conID) {
		Object obj = commonDao.load(ChangingContractMainInfo.class, conID);
		ChangingContractMainInfo ctemp = null;
		if (obj != null) {
			try {
				ctemp = (ChangingContractMainInfo) obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ctemp;
	}
	
	// 保存合同主题信息
	
	/**
	 * 需要修改判断是否是导入合同
	 */
	@SuppressWarnings("unchecked")
	public ContractMainInfo saveContractMainInfo(ContractMainInfo c) { 
			if(c.getConMainInfoSid()!=null){
				ContractMainInfo cmaininfo = (ContractMainInfo) commonDao.load(ContractMainInfo.class, c.getConMainInfoSid());
				cmaininfo.setOpPeople(UserUtils.getUser().getId());
				cmaininfo.setOpTime(new Date());
			    //保存客户并建立关联
				cmaininfo.setConCustomer(c.getConCustomer());
				//查询关联表
	        	if(c.getConCustomer()!=null){
		        	List<YXOEmployeeClient>  yec = commonDao.list(" from YXOEmployeeClient t where exp.id = ? and  cli.id = ? ",cmaininfo.getSaleMan(),c.getConCustomer());
		        	if(yec.size()==0){
		        		YXOEmployeeClient yxEmployeeClient = new YXOEmployeeClient();
		        		YXClientCode cli = (YXClientCode)commonDao.load(YXClientCode.class, c.getConCustomer());
		        		yxEmployeeClient.setCli(cli);
		        		Employee exp = (Employee)commonDao.load(Employee.class, cmaininfo.getSaleMan());
		        		yxEmployeeClient.setExp(exp);
		        		commonDao.save(yxEmployeeClient);
		        	}
	        	}
	        	
	        	String oldType = cmaininfo.getContractType().toString();
	        	
	        	cmaininfo.setItemCustomer(c.getItemCustomer());
	        	cmaininfo.setBillCustomer(c.getBillCustomer());
	        	cmaininfo.setLinkManId(c.getLinkManId());
	        	cmaininfo.setCustomereventtype(c.getCustomereventtype());
	        	cmaininfo.setPartyAConId(c.getPartyAConId());
	        	cmaininfo.setPartyAProId(c.getPartyAProId());
	        	cmaininfo.setConName(c.getConName());
	        	cmaininfo.setMainItemDept(c.getMainItemDept());
	        	cmaininfo.setFinalAccount(c.getFinalAccount());
	        	cmaininfo.setMainItemPeople(c.getMainItemPeople());
	        	cmaininfo.setContractType(c.getContractType());
	        	cmaininfo.setConType(c.getConType());
	        	cmaininfo.setStandard(c.getStandard());
	        	cmaininfo.setConTaxTamount(c.getConTaxTamount());
	        	cmaininfo.setConNoTaxTamount(c.getConNoTaxTamount());
	        	cmaininfo.setCopeck(c.getCopeck());
	        	cmaininfo.setBaserate(c.getBaserate());
	        	cmaininfo.setConSignDate(c.getConSignDate());
	        	cmaininfo.setConDrawback(c.getConDrawback());
	        	cmaininfo.setConStartDate(c.getConStartDate());
	        	cmaininfo.setConBid(c.getConBid());
	        	cmaininfo.setConEndDate(c.getConEndDate());
	        	cmaininfo.setIntoYearCon(c.getIntoYearCon());
	        	cmaininfo.setBeforeHandConState(c.getBeforeHandConState());
	        	contractCommonService.addChargeManAndDepartment(c.getMainItemDept(),c.getMainItemPeople());
	        	
	        	commonDao.update(cmaininfo);
	        	

	        	
	        	//如果原来是集成类合同 现在还是集成类合同不进行处理
	        	//如果从项目类合同转换成集成类合同
	        	//删除合同所有计划，删除项目，并且重新生成计划
	        	if(c.getContractType().equals("2")&&oldType.equals("1")){
	        		//删除合同计划
	        		List<InitContractBillpro> initBillproList=commonDao.list(" from InitContractBillpro where conMainInfoSid = ? ", c.getConMainInfoSid());
	        		if(initBillproList.size()!=0){
		        		for (InitContractBillpro initContractBillpro : initBillproList) {
							commonDao.delete(initContractBillpro);
						}
	        		}
	        		//删除合同项目
	        		List<ContractItemMaininfo> itemMainInfoList = commonDao.list(" from ContractItemMaininfo where contractMainInfo = ? ", c.getConMainInfoSid());
	        	    if(itemMainInfoList.size()!=0){
	        	    	for (ContractItemMaininfo contractItemMaininfo : itemMainInfoList) {
	        	    		List<ContractItemInfo> iteminfoList = commonDao.list(" from ContractItemInfo where contractItemMaininfo = ? ", contractItemMaininfo.getConItemMinfoSid());
	        	    		if(iteminfoList.size()!=0){
	        	    			for (ContractItemInfo contractItemInfo : iteminfoList) {
									commonDao.delete(contractItemInfo);
								}
	        	    		}
	        	    		commonDao.delete(contractItemMaininfo);
						} 
	        	    }
	        	    //查询出合同阶段并重新生成合同开票收款计划
	        	    List<ContractItemStagePlan> itemStagePlan = commonDao.list(" from ContractItemStagePlan where contractMainSid = ?", c.getConMainInfoSid());
	        	    for (ContractItemStagePlan contractItemStagePlan : itemStagePlan) {
	        			ContractMaininfoPart part = (ContractMaininfoPart) commonDao.load(ContractMaininfoPart.class, contractItemStagePlan.getContractMaininfoPart().getId());
	        	    	GeneratePlanByStageId(c.getConMainInfoSid(),contractItemStagePlan.getContractItemStage().getConIStageSid(),part.getMoneytype(),contractItemStagePlan.getStageAmout());
					}
	        	          	    
	        	}
	        		
				return cmaininfo;
			}else{		
			    c.setSaleMan(UserUtils.getUser().getId());
	        	c.setOpPeople(UserUtils.getUser().getId());
	        	c.setOpTime(new Date());
	        	c.setConModInfo(false);
	        	c.setConState(0L);
	        	c.setConReceState(0L);
	        	c.setConBillState(0L); 
	        	//查询关联表
	        	contractCommonService.addChargeManAndDepartment(c.getMainItemDept(),c.getMainItemPeople());
	        	if(c.getConCustomer()!=null){
		        	List<YXOEmployeeClient>  yec = commonDao.list(" from YXOEmployeeClient t where exp.id = ? and  cli.id = ? ",c.getSaleMan(),c.getConCustomer());
		        	if(yec.size()==0){
		        		YXOEmployeeClient yxEmployeeClient = new YXOEmployeeClient();
		        		YXClientCode cli = (YXClientCode)commonDao.load(YXClientCode.class, c.getConCustomer());
		        		yxEmployeeClient.setCli(cli);
		        		Employee exp = (Employee)commonDao.load(Employee.class, c.getSaleMan());
		        		yxEmployeeClient.setExp(exp);
		        		commonDao.save(yxEmployeeClient);
		        	}
	        	}
	        	commonDao.saveOrUpdate(c);
	        	setMainInfoState(c);
	        	return c;
			}
		
	}

	// 查询项目客户

	public List<YXClientCode> findEventClient(Long id) {
		List<YXOEmployeeClient> lista = commonDao.list(
				"from YXOEmployeeClient where exp.id=?", id);
		List<YXClientCode> yxcc = new ArrayList();
		if (lista.size() != 0) {
			for (int i = 0; i < lista.size(); i++) {
				YXClientCode cc = (YXClientCode) commonDao
						.uniqueResult(
								"from YXClientCode where isEventUnit='1' and is_active='1' and id=?",
								lista.get(i).getCli().getId());
				if (cc != null) {
					yxcc.add(cc);
				}
			}
		}
		System.out.println("项目客户查询成功");
		return yxcc;

	}

	// 查询开票客户
	public List<YXClientCode> findAllClient(Long id) {
		List<YXOEmployeeClient> lista = commonDao.list(
				"from YXOEmployeeClient where exp.id=?", id);
		List<YXClientCode> yxcc = new ArrayList();
		if (lista.size() != 0) {
			for (int i = 0; i < lista.size(); i++) {
				YXClientCode cc = (YXClientCode) commonDao.uniqueResult(
						"from YXClientCode where  is_active='1' and id=?",
						lista.get(i).getCli().getId());
				if (cc != null) {
					yxcc.add(cc);
				}
			}
		}
		System.out.println("开票客户查询成功");
		return yxcc;
	}

	public List<YXLinkMan> findlinkMan(Long id) {
		if (id == null) {
			return new ArrayList();
		}
		List<YXLinkMan> list = commonDao.list(
				"from YXLinkMan where is_active=1 and clientId=? ", id);
		if (list == null) {
			return new ArrayList();
		}
		return list;
	}

	public List<ContractItemMaininfo> findEventByMainInfoId(Long conMainInfoSid) {
		List<ContractItemMaininfo> list = commonDao.list(
				"from ContractItemMaininfo where contractMainInfo=?",
				conMainInfoSid);
		// 初始化延迟加载
		// 可以把ContractItemMaininfo对象循环初始化
		for (ContractItemMaininfo contractItemMaininfo : list) {
			contractItemMaininfo.getContractItemInfolist().size();
		}
		return list;
	}

	public List<ContractItemStage> findItemStageByMainInfoId(Long conMainInfoSid) {
		List list = commonDao.list(
				"from ContractItemStage where contractMainSid=?",
				conMainInfoSid);
		return list;
	}

	public List<ContractItemStagePlan> findItemStagePlanByMainInfoId(
			Long conMainInfoSid) {
		List<ContractItemStagePlan> list = commonDao
				.list(
						"from ContractItemStagePlan where contractMainSid=? order by makeInvoiceDate , id ",
						conMainInfoSid);
		return list;
	}

	public void saveContractStage(List<ContractItemStage> contractItemStageList) {
		for (int i = 0; i < contractItemStageList.size(); i++) {
			commonDao.saveOrUpdate(contractItemStageList.get(i));
		}

	}

	public void deleteContractStagePlan(
			ContractItemStagePlan contractItemStagePlan) {
		ContractItemStagePlan toDeletePlan = (ContractItemStagePlan) commonDao
				.load(ContractItemStagePlan.class, contractItemStagePlan
						.getId());
		commonDao.delete(toDeletePlan);
		// 要刷新缓存，不然没有执行delete语句，下面的查询还会查询出来
		commonDao.flushSession();
		Number planCount = (Number) commonDao
				.uniqueResult(
						"select count(*) from ContractItemStagePlan where contractItemStage.conIStageSid = ?",
						toDeletePlan.getContractItemStage().getConIStageSid());
		if (planCount.intValue() == 0) {
			commonDao.delete(toDeletePlan.getContractItemStage());
		}
		commonDao.flushSession();
		// 把删除的语句sql执行了
		DelPlanByStageId(toDeletePlan.getContractMainSid(), toDeletePlan
				.getContractItemStage().getConIStageSid(), toDeletePlan
				.getContractMaininfoPart().getMoneytype());
	}

	public boolean isStageOfStagePlanDuplicate(
			ContractItemStagePlan contractItemStagePlan) {
		Number planCount = (Number) commonDao
				.uniqueResult(
						"select count(*) from ContractItemStagePlan where contractItemStage.itemStageName = ? and contractMaininfoPart.id = ?",
						contractItemStagePlan.getContractItemStage()
								.getItemStageName(), contractItemStagePlan
								.getContractMaininfoPart().getId());
		return planCount.intValue() > 0;
	}

	public void saveContractStagePlan(
			ContractItemStagePlan contractItemStagePlan) {
		
		//设定计划收款日期
//		ContractMaininfoPart partX = (ContractMaininfoPart) commonDao.load(ContractMaininfoPart.class, contractItemStagePlan.getContractMaininfoPart().getId());
//		contractItemStagePlan.setGatheringDate(DateUtils.addMonths(contractItemStagePlan.getMakeInvoiceDate(), 
//				typeManageService.getHarvestMonth(partX.getMoneytype())));
		//done....
		
		ContractItemStage stage = (ContractItemStage) commonDao.uniqueResult("from ContractItemStage where itemStageName = ? and contractMainSid = ? order by conIStageSid ", contractItemStagePlan.getContractItemStage().getItemStageName(),contractItemStagePlan.getContractMainSid());
		if(stage == null || isStageOfStagePlanDuplicate(contractItemStagePlan)){
			stage = new ContractItemStage();
			stage.setContractMainSid(contractItemStagePlan.getContractMainSid());
			stage.setItemStageName(contractItemStagePlan.getContractItemStage().getItemStageName());
			commonDao.save(stage);
		}
		contractItemStagePlan.setContractItemStage(stage);
		commonDao.save(contractItemStagePlan);
		// 把保存的sql执行了
		commonDao.flushSession();
		ContractMaininfoPart part = (ContractMaininfoPart) commonDao.load(ContractMaininfoPart.class, contractItemStagePlan.getContractMaininfoPart().getId());
		GeneratePlanByStageId(contractItemStagePlan.getContractMainSid(), stage.getConIStageSid(), part.getMoneytype(), contractItemStagePlan.getStageAmout());
	}

	public void updateContractStagePlan(
			ContractItemStagePlan contractItemStagePlan) {
		
		//设定计划收款日期
//		ContractMaininfoPart partX = (ContractMaininfoPart) commonDao.load(ContractMaininfoPart.class, contractItemStagePlan.getContractMaininfoPart().getId());
//		contractItemStagePlan.setGatheringDate(DateUtils.addMonths(contractItemStagePlan.getMakeInvoiceDate(), 
//				typeManageService.getHarvestMonth(partX.getMoneytype())));
		//done....
		
		ContractItemStagePlan toUpdatePlan = (ContractItemStagePlan) commonDao.load(ContractItemStagePlan.class, contractItemStagePlan.getId());
		//不是导入的阶段才重新计算计划金额
		if(!Boolean.TRUE.equals(toUpdatePlan.getContractItemStage().getImportFromFile())){
			ContractItemStagePlan newPlan = new ContractItemStagePlan();
			try {
				BeanUtils.copyProperties(newPlan, toUpdatePlan);
				newPlan.setId(null);
				newPlan.setStageRemark(contractItemStagePlan.getStageRemark());
				newPlan.setStageAmout(contractItemStagePlan.getStageAmout());
				newPlan.setReveAmount(contractItemStagePlan.getReveAmount());
				newPlan.setTicketType(contractItemStagePlan.getTicketType());
				newPlan.setMakeInvoiceDate(contractItemStagePlan.getMakeInvoiceDate());
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(),e);
			}
			//删除阶段，清除计划
			deleteContractStagePlan(contractItemStagePlan);
			//添加阶段，重新创建计划
			saveContractStagePlan(newPlan);
		}else{
			String newTicketType = contractItemStagePlan.getTicketType();
			String oldTicketType = toUpdatePlan.getTicketType();
			///////////
			toUpdatePlan.setStageRemark(contractItemStagePlan.getStageRemark());
			toUpdatePlan.setStageAmout(contractItemStagePlan.getStageAmout());
			toUpdatePlan.setReveAmount(contractItemStagePlan.getReveAmount());
			toUpdatePlan.setTicketType(contractItemStagePlan.getTicketType());
			toUpdatePlan.setMakeInvoiceDate(contractItemStagePlan.getMakeInvoiceDate());
			//////////
			commonDao.update(toUpdatePlan);
			//更新计划
			List<InitContractBillpro> datePlan = commonDao.list("from InitContractBillpro p where p.conItemStage = ? and p.billNature = ? ", toUpdatePlan.getContractItemStage().getConIStageSid(),toUpdatePlan.getContractMaininfoPart().getMoneytype());
			for (InitContractBillpro initContractBillpro : datePlan) {
				// 更新计划开票日期
				initContractBillpro.setInitBillDate(toUpdatePlan.getMakeInvoiceDate());
				// 更新计划收款日期
				initContractBillpro.setInitReceDate(DateUtils.addMonths(toUpdatePlan.getMakeInvoiceDate(), typeManageService.getHarvestMonth(toUpdatePlan.getContractMaininfoPart().getMoneytype())));
				// 开票类型
				initContractBillpro.setBillType(toUpdatePlan.getTicketType());
				//算含税金额
				ContractMainInfo maininfo = (ContractMainInfo) commonDao.load(ContractMainInfo.class, initContractBillpro.getConMainInfoSid());
				if(initContractBillpro.getInitBillAmount() != null &&
						!StringUtils.equals(newTicketType, oldTicketType)){
					// 不含税的，票据类型变了，要重新计算含税金额
					if(maininfo.getStandard().equals("2")){
						initContractBillpro.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(TaxChange.NoTaxToTaxDouble(initContractBillpro.getInitBillAmount().doubleValue(),initContractBillpro.getBillType())));
					}
				}
				commonDao.update(initContractBillpro);
			}
		}
	}

	public void saveContractEvent(List<ContractItemMaininfo> iteminfolist) {
		// 需加入方法
		// 将变更值存入根据ID号查找出的对象 并更新
		for (int i = 0; i < iteminfolist.size(); i++) {
			// 查找出数据库中对象
			ContractItemMaininfo maininfo = (ContractItemMaininfo) commonDao
					.load(ContractItemMaininfo.class, iteminfolist.get(i)
							.getConItemMinfoSid());
			maininfo.setConItemId(iteminfolist.get(i).getConItemId());
			maininfo.setWar(iteminfolist.get(i).getWar());
			maininfo.setItemResDept(iteminfolist.get(i).getItemResDept());
			maininfo.setItemResDeptP(iteminfolist.get(i).getItemResDeptP());
			commonDao.update(maininfo);
		}
	}

	public void saveEventInfo(Long contractitemmaininfo, String itemcontrent,
			BigDecimal money) {
		ContractItemInfo iteminfoa = new ContractItemInfo();
		iteminfoa.setContractItemMaininfo(contractitemmaininfo);
		iteminfoa.setItemcontrent(itemcontrent);
		iteminfoa.setConItemAmountWithTax(money);
		iteminfoa.setConModInfo(false);
		commonDao.save(iteminfoa);
		SetAccount(contractitemmaininfo);
		
	}

	public void removeEventInfo(Long id) {
		ContractItemInfo iteminfo = (ContractItemInfo) commonDao.load(
				ContractItemInfo.class, id);
		Long mainid = iteminfo.getContractItemMaininfo();
		commonDao.delete(iteminfo);
		SetAccount(mainid);

	}

	private void setMainInfoState(ContractMainInfo c) {
		// 设置合同主初始状态信息
		// 是否有项目信息
		List list1 = commonDao.list(
				"from ContractItemMaininfo where contractMainInfo=?", c
						.getConMainInfoSid());
		if (list1 == null) {
			c.setExistIteminfo(false);
		} else {
			c.setExistIteminfo(true);
		}
		System.out.println("项目执行成功");
		// 是否有其他信息
		List list2 = commonDao.list(
				"from ContractOtherInfo where contractMainSid=?", c
						.getConMainInfoSid());
		if (list2 == null) {
			c.setExistFinAccout(false);
		} else {
			c.setExistFinAccout(true);
		}
		System.out.println("其他信息执行成功");
		// 是否有自有产品
		List list3 = commonDao.list("from ContractOwnProduce where conMinfo=?",
				c.getConMainInfoSid());
		if (list3 == null) {
			c.setExistOwnProd(false);
		} else {
			c.setExistOwnProd(true);
		}
		System.out.println("自有产品执行成功");
		// 是否有外协信息
		if (c.getConId() != null) {
			List list4 = commonDao.list(
					"from AssistanceContract where maininfoid=?", c.getConId());
			if (list4 == null) {
				c.setExistSidehelp(false);
			} else {
				c.setExistSidehelp(true);
			}
		} else {
			c.setExistSidehelp(false);
		}
		System.out.println("外协执行成功");
		// 是否有申购采购
		if (c.getConId() != null) {
			List list5 = commonDao.list("from ApplyMessage where mainId=?", c
					.getConId());
			if (list5 == null) {
				c.setExistPurchase(false);
			} else {
				c.setExistPurchase(true);
			}
		} else {
			c.setExistPurchase(false);
		}
		// 是否有存在变更
		List list6 = commonDao.list(
				"from ContractChangeInfo where contractMainSid=?", c
						.getConMainInfoSid());
		if (list6 == null) {
			c.setExistC(false);
		} else {
			c.setExistC(true);
		}
		System.out.println("变更执行成功");
		commonDao.update(c);
	}

	private ContractItemMaininfo setEventInfoState(ContractItemMaininfo iteminfo) {
		iteminfo.setConItemCostSure(0L);
		iteminfo.setConModInfo(false);
		System.out.println("***执行成功***");
		return iteminfo;
	}

	private void SetAccount(Long itemMainId) {
		ContractItemMaininfo item = (ContractItemMaininfo) commonDao.load(
				ContractItemMaininfo.class, itemMainId);
		List<BigDecimal> iteminfo = commonDao
				.list(
						"select sum(conItemAmountWithTax) from ContractItemInfo where contractItemMaininfo=?",
						item.getConItemMinfoSid());
		if (iteminfo.size() != 0) {
			item.setAccountmoney(iteminfo.get(0));
		} else {
			item.setAccountmoney(BigDecimal.ZERO);
		}
		commonDao.update(item);
	}

	public int accountStageNum(Long mainid) {
		List list = commonDao.list(
				"from ContractItemStage where contractMainSid=?", mainid);
		return list.size();
	}

	public int accountDepartNum(Long mainid) {
		List list = commonDao.list(
				"from ContractItemMaininfo where contractMainInfo=?", mainid);
		return list.size();
	}

	public List<InitContractBillpro> findPlanlist(Long mainid) {
		List<InitContractBillpro> list = commonDao
				.list(
						"from InitContractBillpro where conMainInfoSid=? order by initReceDate,conItemStage,conItemInfo",
						mainid);
		return list;
	}
	
	
	public List<RealContractBillandRecePlan> findPlanlistR(Long mainid){
		List<RealContractBillandRecePlan> list = commonDao.list(
				"from RealContractBillandRecePlan where conMainInfoSid=? order by realPredReceDate,conItemStage,contractItemMaininfo",
				mainid);
		return list;
	}

	public void splitPlan(Long splititemNum, Long splitstageNum, Long mainid) {
		InitContractBillpro intcontractbillpro = new InitContractBillpro();
		intcontractbillpro.setConMainInfoSid(mainid);
		intcontractbillpro.setConItemStage(splititemNum);
		intcontractbillpro.setConItemInfo(splitstageNum);
		commonDao.save(intcontractbillpro);
	}

	public String findDepName(Long itemid) {
		ContractItemMaininfo itemMaininfo = (ContractItemMaininfo) commonDao
				.load(ContractItemMaininfo.class, itemid);
		YXTypeManage typemanage = typeManageService.getYXTypeManage(1018L,
				itemMaininfo.getItemResDept());
		return typemanage.getTypeName();
	}

	public String findStageName(Long stageid) {
		ContractItemStage itemstage = (ContractItemStage) commonDao.load(
				ContractItemStage.class, stageid);
		YXTypeManage typemanage = typeManageService.getYXTypeManage(1023L,
				itemstage.getItemStageName());
		return typemanage.getTypeName();
	}

	public ContractOtherInfo findOtherInfo(Long mainid) {
		ContractOtherInfo otherinfo = (ContractOtherInfo) commonDao
				.uniqueResult(
						"select otherInfo from  ContractOtherInfo otherInfo,ContractMainInfo minfo " +
	    		"where otherInfo.contractMainSid = minfo.conMainInfoSid and minfo.conMainInfoSid =?  ",
						mainid);
		return otherinfo;
	}

	public void saveOtherInfo(ContractOtherInfo otherinfo) {
		commonDao.saveOrUpdate(otherinfo);
	}

	public void savePlanInfo(List<InitContractBillpro> planlist) {
		// 根据阶段查询出计划开票和收款日期
		for (int i = 0; i < planlist.size(); i++) {
			InitContractBillpro ib = (InitContractBillpro) commonDao.load(
					InitContractBillpro.class, planlist.get(i)
							.getInitConBillproSid());
			ContractMainInfo maininfo= (ContractMainInfo) commonDao.load(ContractMainInfo.class,planlist.get(i).getConMainInfoSid());
			if(maininfo.getStandard().equals("1")){
				ib.setInitBillAmount(planlist.get(i).getInitBillAmount());
				ib.setInitTaxBillAmount(planlist.get(i).getInitBillAmount());
			}else{
				ib.setInitBillAmount(planlist.get(i).getInitBillAmount());
				ib.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(TaxChange.NoTaxToTaxDouble(planlist.get(i).getInitBillAmount().doubleValue(),planlist.get(i).getBillType())));
			}
			ib.setInitReceAmount(planlist.get(i).getInitReceAmount());
			ib.setInitTaxReceAmount(planlist.get(i).getInitReceAmount());
			ib.setBillType(planlist.get(i).getBillType());
			ib.setInitReceDate(planlist.get(i).getInitReceDate());
			commonDao.update(ib);
		}
	}

	public List<ContractOwnProduce> findselfProductByMainid(Long mainid) {
		List<ContractOwnProduce> list = commonDao
				.list(
						"from ContractOwnProduce where conMinfo=? order by conOwnProdSid",
						mainid);
		return list;
	}

	public void conProduct(Long mainid, Long selfproduceid) {
		ContractOwnProduce op = new ContractOwnProduce();
		op.setConMinfo(mainid);
		op.setOwnProduceId(selfproduceid);
		commonDao.save(op);
		System.out.println("****保存完毕***");
	}

	public void delConSelfProduct(Long delselfproduct) {
		ContractOwnProduce cp = (ContractOwnProduce) commonDao.load(
				ContractOwnProduce.class, delselfproduct);
		commonDao.delete(cp);
	}

	public void saveConSelfProductInfo(
			List<ContractOwnProduce> contractOwnProduce) {
		for (int i = 0; i < contractOwnProduce.size(); i++) {
			commonDao.update(contractOwnProduce.get(i));
		}
	}

	// 根据自有产品ID查找自有产品名称
	public String findSelfProductNameById(Long selfproductid) {
		SelfProduction sp = (SelfProduction) commonDao.load(
				SelfProduction.class, selfproductid);
		return sp.getRegisterName();
	}

	public List<ApplyBill> findInviceByMainid(Long mainid) {
		List<ApplyBill> list = commonDao.list(
				" from ApplyBill where contractMainInfo=?", mainid);
		return list;
	}

	public List<ApplyMessage> findPurchaseByMainid(Long mainid) {
		List<ApplyMessage> list = commonDao.list(
				" from ApplyMessage where mainId=?", mainid);
		return list;
	}

	public void removeConInvoice(Long delInvoice) {
		ApplyBill ab = (ApplyBill) commonDao.load(ApplyBill.class, delInvoice);
		ab.setContractMainInfo(null);
		ab.setIsNoContract(true);
		commonDao.update(ab);
	}

	public void removeConPurchase(Long delInvoice) {
		ApplyMessage am = (ApplyMessage) commonDao.load(ApplyMessage.class,
				delInvoice);
		am.setMainId(null);
		am.setApplyState("0");
		commonDao.update(am);
	}

	public void conPur(Long selectid, Long mainid) {
		ApplyMessage am = (ApplyMessage) commonDao.load(ApplyMessage.class,
				selectid);
		am.setMainId(mainid);
		am.setApplyState("1");
		commonDao.update(am);

	}

	public void conInvoice(Long mainid, Long selectid) {
		ApplyBill ab = (ApplyBill) commonDao.load(ApplyBill.class, selectid);
		ab.setContractMainInfo(mainid);
		commonDao.update(ab);

	}

	@SuppressWarnings("unchecked")
	public void updateContractMainState(Long[] ids, Long conState) {
		List<ContractMainInfo> contracts = null;

		StringBuffer sp = new StringBuffer();
		sp.append("(");
		if (ids != null && ids.length > 0) {
			for (int i = 0; i < ids.length - 1; i++) {
				sp.append(ids[i] + ",");
			}
			sp.append(ids[ids.length - 1] + ")");
		}

		String hql = "from ContractMainInfo c where c.conMainInfoSid in"
				+ sp.toString();
		contracts = (List<ContractMainInfo>) commonDao.list(hql);
		if (contracts != null && contracts.size() > 0) {
			for (ContractMainInfo c : contracts) {
				c.setConState(conState);
				commonDao.saveOrUpdate(c);
			}
		}

	}

	@SuppressWarnings("unchecked")
	public void updateContractAndConItemId(List<MyBean> list) {
		for (MyBean bean : list) {
			ContractItemMaininfo cm = getContractItemMaininfo(bean.getKey());
			cm.setConItemId(bean.getValue());
			commonDao.saveOrUpdate(cm);
		}

	}

	public ContractItemMaininfo getContractItemMaininfo(Long conMainItemSid) {
		ContractItemMaininfo itemInfo = (ContractItemMaininfo) commonDao.load(
				ContractItemMaininfo.class, conMainItemSid);
		return itemInfo;
	}

	public void updateContractMainItem(ContractItemMaininfo item) {
		commonDao.update(item);

	}

	public String getYXClientCode(Long mainid) {
		YXClientCode yxcc = (YXClientCode) commonDao.load(YXClientCode.class,
				mainid);
		return yxcc.getName();
	}

	public List<YXClientCode> findClient(Long id) {
		List<YXOEmployeeClient> lista = commonDao.list(
				"from YXOEmployeeClient where exp.id=?", id);
		List<YXClientCode> yxcc = new ArrayList();
		if (lista.size() != 0) {
			for (int i = 0; i < lista.size(); i++) {
				YXClientCode cc = (YXClientCode) commonDao
						.uniqueResult(
								"from YXClientCode where  is_active='1' and isEventUnit='0' and id=?",
								lista.get(i).getCli().getId());
				if (cc != null) {
					yxcc.add(cc);
				}
			}
		}
		System.out.println("客户查询成功");
		return yxcc;
	}

	public void sureSubmit(Long mainid) {
		ContractMainInfo cmain = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, mainid);
		cmain.setConState(1L);
		commonDao.update(cmain);
	}

	public void delContract(Long mainid) {
		// 查询主合同信息
		ContractMainInfo cmain = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, mainid);
		// 查询项目信息
		List<ContractItemMaininfo> listitem = commonDao.list(
				"from ContractItemMaininfo where contractMainInfo=?", mainid);
		
		//阶段组成
		List<ContractItemStagePlan> stageplanlist = commonDao.list("from ContractItemStagePlan where contractMainSid=?", mainid);
		
		
		//删除阶段组成
		if(stageplanlist!=null){
	         for (ContractItemStagePlan itemStageplan : stageplanlist) {
				commonDao.delete(itemStageplan);
			}
		}
		
		
		
		// 删除项目组成表
		for (ContractItemMaininfo cm : listitem) {
			List<ContractItemInfo> iteminfolist = commonDao.list(
					"from  ContractItemInfo where contractItemMaininfo=?", cm
							.getConItemMinfoSid());
			if (iteminfolist.size() != 0) {
				for (ContractItemInfo cii : iteminfolist) {
					commonDao.delete(cii);
				}
			}
		}
		// 查询合同阶段信息
		List<ContractItemStage> itemstagelist = commonDao.list(
				"from ContractItemStage where contractMainSid=?", mainid);
	
		// 查询合同计划信息
		List<InitContractBillpro> billprolist = commonDao.list(
				"from InitContractBillpro where conMainInfoSid=?", mainid);
		// 查询合同关联的开票
		List<ApplyBill> applybilllist = commonDao.list(
				"from ApplyBill where contractMainInfo=?", mainid);
		for (ApplyBill ab : applybilllist) {
			ab.setIsNoContract(true);
			commonDao.update(ab);
		}
		// 查询合同关联的申购
		List<ApplyMessage> applymessagelist = commonDao.list(
				"from ApplyMessage where mainId = ?", mainid);
		for (ApplyMessage applymessage : applymessagelist) {
			applymessage.setApplyState("0");
		}
		// 查询合同的自有产品
		List<ContractOwnProduce> ownproducelist = commonDao.list(
				"from ContractOwnProduce where conMinfo=?", mainid);
		// 查询合同的其他信息
		ContractOtherInfo otherinfo = (ContractOtherInfo) commonDao
				.uniqueResult("from ContractOtherInfo where contractMainSid=?",
						mainid);

		// 删除其他信息
		if (otherinfo != null) {
			commonDao.delete(otherinfo);
		}
		// 删除合同自有产品
		if (ownproducelist.size() != 0) {
			for (ContractOwnProduce cop : ownproducelist) {
				commonDao.delete(cop);
			}
		}
		// 删除合同开票计划
		if (billprolist.size() != 0) {
			for (InitContractBillpro ib : billprolist) {
				commonDao.delete(ib);
			}
		}

		// 删除合同阶段
		if (itemstagelist.size() != 0) {
			for (ContractItemStage cis : itemstagelist) {
				commonDao.delete(cis);
			}
		}
		// 删除合同项目
		if (listitem.size() != 0) {
			for (ContractItemMaininfo cim : listitem) {
				commonDao.delete(cim);
			}
		}
		// 删除合同主体信息
		commonDao.delete(cmain);

	}

	public void changeToFormalContract(ContractMainInfo cmaininfo) {
			if (cmaininfo.getConState() == 3L)
				cmaininfo.setActiveDate(new Date());
			cmaininfo.setConState(4L);
			List<ApplyBill> ablist = commonDao.list(
					"from ApplyBill where contractMainInfo=?", cmaininfo.getConMainInfoSid());
			for (ApplyBill ab : ablist) {
				ab.setIsNoContract(false);
				commonDao.update(ab);
			}
			String str1 = "您的草稿合同已经确认通过为正式合同，合同号为:" + cmaininfo.getConId() + "";
			noticeservice.addNotice(str1, cmaininfo.getSaleMan());
			cmaininfo.setOpPeople(UserUtils.getUser().getId());
			cmaininfo.setOpTime(new Date());
			cmaininfo.setActiveDate(new Date());
			commonDao.update(cmaininfo);
			testService.updateOneContractIsActiveDate(cmaininfo);
	}

	// 生成合同号
	public String producedConId(ContractMainInfo c) {
		return codeGenerateService.generateContractCode(c.getConCustomer());
	}

	@SuppressWarnings("unchecked")
	public List<ContractMainInfo> findContractByState(Long[] conMainId) {
		List<ContractMainInfo> contracts = null;

		StringBuffer sp = new StringBuffer();
		sp.append("(");
		if (conMainId != null && conMainId.length > 0) {
			for (int i = 0; i < conMainId.length - 1; i++) {
				sp.append(conMainId[i] + ",");
			}
			sp.append(conMainId[conMainId.length - 1] + ")");
		}

		String hql = "from ContractMainInfo c where c.conMainInfoSid in"
				+ sp.toString();
		contracts = (List<ContractMainInfo>) commonDao.list(hql);

		return contracts;
	}

	public void changeState(List<ContractMainInfo> contracts, Long state,String contractId) {
		for (ContractMainInfo c : contracts) {
			c.setConState(state);
			c.setConId(contractId);
			commonDao.saveOrUpdate(c);
		}
	}
	public void changeState(List<ContractMainInfo> contracts, Long state) {
		for (ContractMainInfo c : contracts) {
			c.setConState(state);
			commonDao.saveOrUpdate(c);
		}
	}

	public void saveContrac(ContractMainInfo info) {
		commonDao.saveOrUpdate(info);

	}

	public void conCustom(Long id, Long conCustomer) {
		List<Object> cliList = service
				.list(" from YXOEmployeeClient empCli where empCli.cli = "
						+ conCustomer + "and empCli.exp = " + id);
		if (cliList.size() == 0) {
			Employee e = (Employee) commonDao.load(Employee.class, id);
			YXClientCode yxcc = (YXClientCode) commonDao.load(
					YXClientCode.class, conCustomer);
			YXOEmployeeClient ec = new YXOEmployeeClient();
			ec.setCli(yxcc);
			ec.setExp(e);
			ec.setIs_active("1");
			ec.setById(id);
			System.out.println("保存关联~~~~~~~~~~~~");
			commonDao.save(ec);
		}
	}

	// 根据主合同号查询合同开票收款计划 然后将原始开票收款计划表转存至实际开票收款计划表
	public void copysked(Long mainid) {
		//
		List<InitContractBillpro> initbillprolist = commonDao.list(
				"from InitContractBillpro where conMainInfoSid = ? ", mainid);
		for (InitContractBillpro icbp : initbillprolist) {
			RealContractBillandRecePlan rcbarp = new RealContractBillandRecePlan();
			rcbarp.setInitContractBillpro(icbp.getInitConBillproSid());
			rcbarp.setBillSureSign(icbp.getBillRecvSign());
			rcbarp.setBillContent(icbp.getBillInfo());
			rcbarp.setBase(icbp.getBase());
			rcbarp.setBillNature(icbp.getBillNature());
			rcbarp.setBillType(icbp.getBillType());
			//基准开票和收款金额
			rcbarp.setRealBillAmount(icbp.getInitBillAmount());
			rcbarp.setRealReceAmount(icbp.getInitReceAmount());
			rcbarp.setUniteBill(icbp.getUnifyBill());
			rcbarp.setRealPredBillDate(icbp.getInitBillDate());
			rcbarp.setRealPredReceDate(icbp.getInitReceDate());
			//含税开票和收款金额
			rcbarp.setRealTaxBillAmount(icbp.getInitTaxBillAmount());
			rcbarp.setRealTaxReceAmount(icbp.getInitReceAmount());
			rcbarp.setExistC(false);
			rcbarp.setConModInfo(false);
			rcbarp.setConMainInfoSid(mainid);
			rcbarp.setContractItemMaininfo(icbp.getConItemInfo());
			rcbarp.setConItemStage(icbp.getConItemStage());
			commonDao.save(rcbarp);
		}

	}

	public void delrealBillpro(Long mainid) {
		List<RealContractBillandRecePlan> realbillrecelist = commonDao.list(
				"from RealContractBillandRecePlan where conMainInfoSid=?",
				mainid);
		for (RealContractBillandRecePlan rcbrp : realbillrecelist) {
			//删除变更历史
			int count = commonDao.executeUpdate("delete from BillReceChangeHistory his where his.realContractBillandRecePlan.realConBillproSid = ? ", rcbrp.getRealConBillproSid());
			//删除计划
			commonDao.delete(rcbrp);
		}
	}

	public void changeZSOneComain(ContractMainInfo cm) {
			changeToFormalContract(cm);
		    copysked(cm.getConMainInfoSid());
		    cleanRealPlan(cm.getConMainInfoSid());
	}

	public void chageZSTwoComain(Long mian, String resNumber,
			ContractMainInfo comain) {
		if (comain.getConState() == 3L)
			comain.setActiveDate(new Date());
		comain.setOpPeople(UserUtils.getUser().getId());
		comain.setOpTime(new Date());
		comain.setConId(resNumber);
		comain.setConState(new Long(4));
		String str = "您的草稿合同已经确认通过为正式合同，合同号为:" + resNumber + "";
		noticeservice.addNotice(str, comain.getSaleMan());
		comain.setActiveDate(new Date());
		saveContrac(comain);
		List<ApplyBill> ablist = commonDao.list(
				"from ApplyBill where contractMainInfo=?", comain
						.getConMainInfoSid());
		if (ablist != null) {
			for (ApplyBill ab : ablist) {
				ab.setIsNoContract(false);
				commonDao.update(ab);
			}
		}
		copysked(mian);
		cleanRealPlan(mian);
	}

	@SuppressWarnings("unchecked")
	public void concelCirform(List view) {

		changeState(view, new Long(1));
		List<ContractMainInfo> list = (List<ContractMainInfo>) view;
		for (ContractMainInfo lis : list) {
			if(lis.getImportFromFile() == null||!lis.getImportFromFile()){
				lis.setConId(null);
			}
			String str1 = "合同号为:" + lis.getConId() + "的合同已被取消确认";
			noticeservice.addNotice(str1, lis.getSaleMan());
			delrealBillpro(lis.getConMainInfoSid());
		}
	}

	public List<ContractMaininfoPart> findMainMoneyList(Long mainid) {
		List<ContractMaininfoPart> list = commonDao.list(
				"from ContractMaininfoPart where contractmainid=?  ", mainid);
		return list;
	}
	
	public List<ChangingContractMaininfoPart> findChangeMainMoneyList(Long mainid) {
		List<ChangingContractMaininfoPart> list = commonDao.list(
				"from ChangingContractMaininfoPart where contractmainid = ?  ", mainid);
		return list;
	}

	public List<Object[]> findMainMoneyWithPlanAmountList(Long mainid) {
		List<Object[]> list = commonDao
				.list(
						"select p ," +
						"(select sum(s.stageAmout) from ContractItemStagePlan s where s.ticketType <> '4' and s.contractMaininfoPart = p )," +
						"(select sum(s.stageAmout) from ContractItemStagePlan s where s.ticketType = '4'  and s.contractMaininfoPart = p ) " +
						"from ContractMaininfoPart p where p.contractmainid=? ",
						mainid);
		return list;
	}

	public Double getStagePlanAmountOfContractPart(Long partId, boolean isReceipt) {
		String hql = "select sum(s.stageAmout) from ContractItemStagePlan s where ";
		if(isReceipt){
			//收据
			hql+=" s.ticketType = '4' ";
		}else{
			//发票
			hql+=" s.ticketType <> '4' ";
		}
		hql+=" and s.contractMaininfoPart.id = ? ";
		Number amount = (Number) commonDao.uniqueResult(hql,partId);
		if (amount != null) {
			return amount.doubleValue();
		} else {
			return 0.0;
		}
	}

	public void saveMainMoneyPart(String mainmoneytype, Double mainmoney,
			Long mainid, String ticketType) {
		ContractMaininfoPart cmp = new ContractMaininfoPart();
		cmp.setContractmainid(mainid);
		cmp.setMoney(mainmoney);
		cmp.setMoneytype(mainmoneytype);
		cmp.setTicketType(ticketType);
		commonDao.save(cmp);
	}

	public void delContractMainPart(Long delmainpartid) {
		ContractMaininfoPart cmp = (ContractMaininfoPart) commonDao.load(
				ContractMaininfoPart.class, delmainpartid);
		List<ContractItemStagePlan> itemstageplanlist = commonDao
				.list(
						"from ContractItemStagePlan where contractMainSid=? and contractMaininfoPart.moneytype=?",
						cmp.getContractmainid(), cmp.getMoneytype());
		for (ContractItemStagePlan contractItemStagePlan : itemstageplanlist) {
			deleteContractStagePlan(contractItemStagePlan);
		}
		List<ContractItemInfo> iteminfolist = commonDao
				.list(
						"select ii from ContractItemInfo ii ,ContractItemMaininfo m where ii.contractItemMaininfo = m.conItemMinfoSid and m.contractMainInfo=? and ii.mainInfoPartId=?",
						cmp.getContractmainid(), delmainpartid);
		for (ContractItemInfo contractItemInfo : iteminfolist) {
			this.deleteEventInfo(contractItemInfo.getConItemInfoSid());
		}
		commonDao.delete(cmp);
	}

	public String findContractType(Long mainid) {
		ContractMainInfo cmi = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, mainid);
		return cmi.getContractType();
	}

	public void GeneratePlanByEventId(Long mainid, Long itemid,
			String moneytype, Double money) {
		// 查询出此费用的阶段信息
		List<ContractItemStagePlan> itemstageplanlist = commonDao
				.list(
						"from ContractItemStagePlan where contractMainSid=? and contractMaininfoPart.moneytype=?",
						mainid, moneytype);

		ContractMainInfo maininfo = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, mainid);

		// 工程类合同
		if ("1".equals(maininfo.getContractType())) {
			// 如果此合同有此阶段信息的话执行操作
			if (itemstageplanlist != null) {
				// 查询出项目的主体信息
				ContractItemMaininfo itemmaininfo = (ContractItemMaininfo) commonDao
						.load(ContractItemMaininfo.class, itemid);

				for (ContractItemStagePlan cisp : itemstageplanlist) {
					InitContractBillpro contractbillpro = new InitContractBillpro();
					// 添加项目号
					contractbillpro.setConItemInfo(itemid);
					// 添加阶段号
					contractbillpro.setConItemStage(cisp.getContractItemStage()
							.getConIStageSid());
					// 添加计划开票日期
					contractbillpro.setInitBillDate(cisp.getMakeInvoiceDate());
					// 添加计划收款日期
					contractbillpro.setInitReceDate(DateUtils.addMonths(cisp
							.getMakeInvoiceDate(), typeManageService
							.getHarvestMonth(moneytype)));
					// 添加开票性质
					contractbillpro.setBillNature(moneytype);
					// 添加票据类型
					contractbillpro.setBillType(cisp.getTicketType());
					
					// 添加主合同号
					contractbillpro.setConMainInfoSid(mainid);
					

					// 添加开票内容
					contractbillpro.setBillInfo(generateBillContent(mainid, moneytype).toString());

					// 添加计划开票金额
					// 此费用的项目金额
					Double a = money;
					// 阶段金额
					Double b = cisp.getStageAmout();
					// 此费用总金额
					Double c = cisp.getContractMaininfoPart().getMoney();
					// 计算出的开票金额&计划收款金额
					Double d = a * b / c;
					
					if(maininfo.getStandard().equals("1")){
						contractbillpro.setInitBillAmount(BigDecimalUtils.toBigDecial(d));
						contractbillpro.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(d));						
					}else{
						contractbillpro.setInitBillAmount(BigDecimalUtils.toBigDecial(d));
						contractbillpro.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(TaxChange.NoTaxToTaxDouble(d, cisp.getContractMaininfoPart().getTicketType())));
					}
					
					//添加到款金额
					Double b2=cisp.getReveAmount();
					Double reveamoune = a * b2 / c;
					contractbillpro.setInitTaxReceAmount(BigDecimalUtils.toBigDecial(reveamoune));
					contractbillpro.setInitReceAmount(BigDecimalUtils.toBigDecial(reveamoune));
					
					
					
					commonDao.save(contractbillpro);
				}
			}

		}
	}

	public void GeneratePlanByStageId(Long mainid, Long stageid,
			String moneytype, Double money) {
		commonDao.flushSession();
		// 查找合同下有此费用的项目
		ContractMaininfoPart cmaininfopart = (ContractMaininfoPart) commonDao
				.uniqueResult(
						"from ContractMaininfoPart where contractmainid=? and moneytype=?",
						mainid, moneytype);
		List<ContractItemInfo> iteminfolist = commonDao
				.list(
						"select ii from ContractItemInfo ii ,ContractItemMaininfo m where ii.contractItemMaininfo = m.conItemMinfoSid and m.contractMainInfo=? and ii.mainInfoPartId=?",
						mainid, cmaininfopart.getId());

		ContractMainInfo maininfo = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, mainid);

		// 工程类合同
		if ("1".equals(maininfo.getContractType())) {
			if (iteminfolist != null) {
				for (ContractItemInfo iteminfo : iteminfolist) {
					InitContractBillpro contractbillpro = new InitContractBillpro();
					ContractItemMaininfo itemmaininfo = (ContractItemMaininfo) commonDao
							.load(ContractItemMaininfo.class, iteminfo
									.getContractItemMaininfo());
					// 添加项目号
					contractbillpro.setConItemInfo(itemmaininfo
							.getConItemMinfoSid());
					// 添加阶段号
					contractbillpro.setConItemStage(stageid);
					// 添加计划开票日期
					ContractItemStagePlan cisp = (ContractItemStagePlan) commonDao
							.uniqueResult(
									"from ContractItemStagePlan where contractMainSid=? and contractItemStage.conIStageSid = ? and contractMaininfoPart.moneytype = ?",
									mainid, stageid, moneytype);
					contractbillpro.setInitBillDate(cisp.getMakeInvoiceDate());
					// 添加计划收款日期
					contractbillpro.setInitReceDate(DateUtils.addMonths(cisp
							.getMakeInvoiceDate(), typeManageService
							.getHarvestMonth(moneytype)));
					// 添加开票性质
					contractbillpro.setBillNature(moneytype);
					// 添加票据类型
					contractbillpro.setBillType(cisp.getTicketType());
					// 添加主合同号
					contractbillpro.setConMainInfoSid(mainid);

					// 添加开票内容
					contractbillpro.setBillInfo(generateBillContent(mainid, moneytype).toString());
					// 添加计划开票金额
					// 此费用的项目金额
					Double a = iteminfo.getConItemAmountWithTax().doubleValue();
					// 阶段金额
					Double b = money;
					// 此费用总金额
					Double c = cmaininfopart.getMoney();
					// 计算出的开票金额
					Double d = a * b / c;
					if(maininfo.getStandard().equals("1")){
						contractbillpro.setInitBillAmount(BigDecimalUtils.toBigDecial(d));
						contractbillpro.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(d));
					}else{
						contractbillpro.setInitBillAmount(BigDecimalUtils.toBigDecial(d));
						ContractMaininfoPart cmp =(ContractMaininfoPart) commonDao.load(ContractMaininfoPart.class,cisp.getContractMaininfoPart().getId()); 
						contractbillpro.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(TaxChange.NoTaxToTaxDouble(d,cmp.getTicketType())));
					}
					//添加到款金额
					Double b2=cisp.getReveAmount();
					Double reveamoune = a * b2 / c;
					contractbillpro.setInitTaxReceAmount(BigDecimalUtils.toBigDecial(reveamoune));
					contractbillpro.setInitReceAmount(BigDecimalUtils.toBigDecial(reveamoune));

					commonDao.save(contractbillpro);
				}
			}
		}
		if ("2".equals(maininfo.getContractType())) {
			InitContractBillpro contractbillpro = new InitContractBillpro();
			// 添加阶段号
			contractbillpro.setConItemStage(stageid);
			// 添加计划开票日期
			ContractItemStagePlan cisp = (ContractItemStagePlan) commonDao
					.uniqueResult(
							"from ContractItemStagePlan where contractMainSid=? and contractItemStage.conIStageSid = ? and contractMaininfoPart.moneytype = ?",
							mainid, stageid, moneytype);
			ContractMaininfoPart cmp =(ContractMaininfoPart) commonDao.load(ContractMaininfoPart.class,cisp.getContractMaininfoPart().getId()); 
			contractbillpro.setInitBillDate(cisp.getMakeInvoiceDate());
			// 添加开票性质
			contractbillpro.setBillNature(moneytype);
			// 添加票据类型
			contractbillpro.setBillType(cisp.getTicketType());
			// 添加主合同号
			contractbillpro.setConMainInfoSid(mainid);
			// 添加开票内容
			contractbillpro.setBillInfo(generateBillContent(mainid, moneytype).toString());
			
        	//添加收款日起
			contractbillpro.setInitReceDate(DateUtils.addMonths(cisp
					.getMakeInvoiceDate(), typeManageService
					.getHarvestMonth(moneytype)));
			// 集成类 阶段金额既开票金额
			if(maininfo.getStandard().equals("1")){
				contractbillpro.setInitBillAmount(BigDecimalUtils.toBigDecial(money));
				contractbillpro.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(money));
			}else{
				contractbillpro.setInitBillAmount(BigDecimalUtils.toBigDecial(money));
				contractbillpro.setInitTaxBillAmount(BigDecimalUtils.toBigDecial(TaxChange.NoTaxToTaxDouble(money,cmp.getTicketType())));
			}
			
			//集成类 阶段收款金额机预计收款金额
			contractbillpro.setInitReceAmount(BigDecimalUtils.toBigDecial(cisp.getReveAmount()));
		    contractbillpro.setInitTaxReceAmount(BigDecimalUtils.toBigDecial(cisp.getReveAmount()));
			
			
			commonDao.save(contractbillpro);
		}

	}

	private StringBuffer generateBillContent(Long mainid, String moneytype){
		StringBuffer str = new StringBuffer();
		ContractMainInfo cmaininfo = (ContractMainInfo) commonDao
				.load(ContractMainInfo.class, mainid);
		if (cmaininfo.getConName() != null) {
			str.append(cmaininfo.getConName().toString());
			str.append("/");
		}
		if (cmaininfo.getPartyAConId() != null) {
			str.append(cmaininfo.getPartyAConId().toString());
			str.append("/");
		}
		if (cmaininfo.getPartyAProId() != null) {
			str.append(cmaininfo.getPartyAProId().toString());
		}
		str.append(typeManageService.getYXTypeManage(1012L,
				moneytype).getTypeName());
		return str;
	}

	public void DelPlanByEventId(Long mainid, Long itemid, String moneytype) {
		List<InitContractBillpro> planlist = commonDao
				.list(
						"from InitContractBillpro where conMainInfoSid =? and conItemInfo = ? and billNature = ?",
						mainid, itemid, moneytype);
		for (InitContractBillpro icbp : planlist) {
			commonDao.delete(icbp);
		}
	}

	public void DelPlanByStageId(Long mainid, Long stageid, String moneytype) {
		List<InitContractBillpro> planlist = commonDao
				.list(
						"from InitContractBillpro where conMainInfoSid=? and conItemStage = ? and billNature = ? ",
						mainid, stageid, moneytype);
		for (InitContractBillpro icbp : planlist) {
			commonDao.delete(icbp);
		}
	}

	public void deleteEventInfo(Long conItemInfoSid) {
		// No1.通过费用id查询费用信息，并获取里面的项目id
		ContractItemInfo itemInfo = (ContractItemInfo) commonDao.load(
				ContractItemInfo.class, conItemInfoSid);
		Long itemMaininfoId = itemInfo.getContractItemMaininfo();
		// No2.删除项目费用信息
		commonDao.delete(ContractItemInfo.class, conItemInfoSid);
		// No3.通过费用中的项目id判断该id在费用中的其他记录中是否还有外键！如果没有的话把该项目也删掉
		List<ContractItemInfo> itemInfoList = commonDao
				.list(
						"from ContractItemInfo itemInfo where itemInfo.contractItemMaininfo=?",
						itemMaininfoId);
		if (itemInfoList == null || itemInfoList.size() == 0) {
			commonDao.delete(ContractItemMaininfo.class, itemMaininfoId);
		}
		// 通过项目费用查找合同费用
		ContractMaininfoPart part = (ContractMaininfoPart) commonDao.load(
				ContractMaininfoPart.class, itemInfo.getMainInfoPartId());
		// 通过合同费用查找合同id
		ContractMainInfo maininfo = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, part.getContractmainid());
		DelPlanByEventId(maininfo.getConMainInfoSid(), itemMaininfoId, part
				.getMoneytype());
	}

	public List<List> accountEventMoney(Long mainid) {
		List<List> accountMoneylist=new ArrayList();
		List<ContractMaininfoPart> maininfopart=commonDao.list("from ContractMaininfoPart where contractmainid = ?",mainid);
		List<ContractItemMaininfo> itemmainlist = commonDao.list("from ContractItemMaininfo where contractMainInfo = ?", mainid);	
		if(itemmainlist!=null){
			for (ContractMaininfoPart contractMaininfoPart : maininfopart) {			
			    for(ContractItemMaininfo contractItemMaininfo:itemmainlist){
			    	List<ContractItemInfo> citemlist = commonDao.list("from ContractItemInfo where contractItemMaininfo = ?", contractItemMaininfo.getConItemMinfoSid());
			    	BigDecimal allmoney=BigDecimal.ZERO;
			    	for (ContractItemInfo contractItemInfo : citemlist) {
			        	if(contractItemInfo.getMainInfoPartId().equals(contractMaininfoPart.getId())){
			        		allmoney=allmoney.add(contractItemInfo.getConItemAmountWithTax());
			        	}
			        }
			    	if(!BigDecimal.ZERO.equals(allmoney)){
				    	List a=new ArrayList();
				    	//项目号
				    	a.add(0,contractItemMaininfo.getConItemMinfoSid());
				    	//费用代码
				    	a.add(1,contractMaininfoPart.getMoneytype());
				    	//费用下项目总金额
				        a.add(2,allmoney);
				        //项目名称
				        YXTypeManage typemanage=typeManageService.getYXTypeManage(1018L, contractItemMaininfo.getItemResDept());
				    	a.add(3,typemanage.getTypeName());
				    	accountMoneylist.add(a);
			    	}
			    }
			}
		}
		return accountMoneylist;
	}


	public Double accountnoTaxMoney(Long mainid) {
		List<ContractMaininfoPart> maininfopartlist = commonDao.list(
				"from ContractMaininfoPart where contractmainid = ? ", mainid);
		Double notaxmoney = new Double(0);
		if (maininfopartlist != null) {
			for (ContractMaininfoPart contractMaininfoPart : maininfopartlist) {
				notaxmoney = notaxmoney
						+ TaxChange.TaxToNoTaxDouble(contractMaininfoPart
								.getMoney(), contractMaininfoPart
								.getTicketType());
			}
		}
		return notaxmoney;
	}

	public List<ContractMaininfoPart> findEquipmentMoneyByMainid(Long mainid) {
		List<ContractMaininfoPart> lista = new ArrayList();
		List<ContractMaininfoPart> listb = commonDao.list(
				"from ContractMaininfoPart where contractmainid=?", mainid);
		for (ContractMaininfoPart contractMaininfoPart : listb) {
			System.out.println(contractMaininfoPart.getMoneytype().substring(0,
					1));
			if (contractMaininfoPart.getMoneytype().substring(0, 1).equals("1")) {
				lista.add(contractMaininfoPart);
			}
		}
		return lista;
	}

	public Double accountTaxMoney(Long mainid) {
		List<ContractMaininfoPart> maininfopartlist = commonDao.list(
				"from ContractMaininfoPart where contractmainid = ? ", mainid);
		Double notaxmoney = new Double(0);
		for (ContractMaininfoPart contractMaininfoPart : maininfopartlist) {
			notaxmoney = notaxmoney + TaxChange.NoTaxToTaxBigDecimal(BigDecimalUtils.toBigDecial(contractMaininfoPart.getMoney()),contractMaininfoPart.getTicketType()).doubleValue();
		}
		return notaxmoney;
	}

	public List<List> accountStageMoney(Long mainid) {
		List<List> accountStagelist = new ArrayList();
		List<ContractItemStage> itemstagelist = commonDao.list(
				" from ContractItemStage where contractMainSid=?", mainid);
		List<ContractMaininfoPart> maininfopart=commonDao.list("from ContractMaininfoPart where contractmainid = ?",mainid);
		if (itemstagelist != null) {
			for (ContractMaininfoPart contractMaininfoPart : maininfopart) {			
				for (ContractItemStage contractItemStage : itemstagelist) {
					List<ContractItemStagePlan> itemstageplanlist = commonDao.list("from ContractItemStagePlan where  contractItemStage.conIStageSid = ?  ",contractItemStage.getConIStageSid());
					Double stagemoney = Double.valueOf(0L);
					for (ContractItemStagePlan contractItemStagePlan : itemstageplanlist) {
						if(contractMaininfoPart.getId().equals(contractItemStagePlan.getContractMaininfoPart().getId())){
						   if(!contractItemStagePlan.getTicketType().equals("4")){
							stagemoney = stagemoney
								+ contractItemStagePlan.getStageAmout();	
						   }
						}
					}
					if(!BigDecimal.ZERO.equals(stagemoney)){
						List a = new ArrayList();
						//阶段号
						a.add(0, contractItemStage.getConIStageSid());
						//费用组成
						a.add(1,contractMaininfoPart.getMoneytype());
						//总金额
						a.add(2, stagemoney);
						//阶段名称
						YXTypeManage typemanage = typeManageService.getYXTypeManage(
								1023L, contractItemStage.getItemStageName());
						a.add(3, typemanage.getTypeName());
						accountStagelist.add(a);
					}
				}
			}
		}
		return accountStagelist;
	}

	public String findDepNameByMainid(Long mainid) {
		ContractMainInfo maininfo = (ContractMainInfo) commonDao.load(
				ContractMainInfo.class, mainid);
		YXTypeManage typemanage = typeManageService.getYXTypeManage(1018L,
				maininfo.getMainItemDept());
		return typemanage.getTypeName();
	}

	private String idIn(Long[] conMainId) {
		StringBuffer sp = new StringBuffer();
		sp.append("(");
		if (conMainId != null && conMainId.length > 0) {
			for (int i = 0; i < conMainId.length - 1; i++) {
				sp.append(conMainId[i] + ",");
			}
			sp.append(conMainId[conMainId.length - 1] + ")");
		}
		return sp.toString();
	}
	@SuppressWarnings("unchecked")
	public void cirformOkChangeMain(Long[] ids) {
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new BooleanConverter(null), Boolean.class);
		if(ids!=null && ids.length>0){
			for(int k = 0; k< ids.length; k ++){
				bakContract(ids[k]);
				changeContract(ids[k]);
				deleteChangeContract(ids[k]);
				deletePrimaryTable(ids[k]);
			}
			
		}
	}
	public void deleteMonthlyBillbyPlanId(RealContractBillandRecePlan realPlan){
		String hql = "from MonthlyBillpro where realContractBillandRecePlan = ?";
		List<MonthlyBillpro> list = commonDao.list(hql, realPlan);
		for (MonthlyBillpro monthlyBillpro : list) {
			commonDao.delete(monthlyBillpro);
		}
	}
	public void deleteMonthlyReceByPlanId(RealContractBillandRecePlan realPlan){
		String hql = "from MonthlyRecepro where realContractBillandRecePlan = ?";
		List<MonthlyRecepro> list = commonDao.list(hql, realPlan);
		for (MonthlyRecepro monthlyRecepro : list) {
			commonDao.delete(monthlyRecepro);
		}
	}
//////删除计划，同时删除月度收款和开票计划里面的信息
	public void deleteRealPlanByRealPlan(RealContractBillandRecePlan realPlan){
		if(realPlan.getRealBillAmount() == null || realPlan.getRealBillAmount().doubleValue() == 0){
			deleteMonthlyBillbyPlanId(realPlan);
		}
		if(realPlan.getRealReceAmount() == null || realPlan.getRealReceAmount().doubleValue() == 0){
			deleteMonthlyReceByPlanId(realPlan);
		}
		if((realPlan.getRealBillAmount() == null || realPlan.getRealBillAmount().doubleValue() == 0)&&
				(realPlan.getRealReceAmount() == null || realPlan.getRealReceAmount().doubleValue() == 0)){
			deleteMonthlyBillbyPlanId(realPlan);
			deleteMonthlyReceByPlanId(realPlan);
			commonDao.delete(realPlan);
		}
		
	}
	
	///////通过itemInfo删除itemInfo的对象并判断如果itemInfo不存在的话相应的itemMainInfo也删掉
	public void deleteItemInfoByItemInfo(ContractItemInfo itemInfo){
		ContractItemMaininfo itemMainInfo = (ContractItemMaininfo)commonDao.load(ContractItemMaininfo.class,itemInfo.getContractItemMaininfo());
		ContractMaininfoPart mainInfoPart = (ContractMaininfoPart)commonDao.load(ContractMaininfoPart.class, itemInfo.getMainInfoPartId());
		commonDao.delete(itemInfo);/////删除项目组成信息，如果某一个项目下的项目组成没有了。那么相应的项目信息也被删除
		String hql = "select count(*) from ContractItemInfo where contractItemMaininfo = ?";
		Number count = (Number)commonDao.uniqueResult(hql, itemMainInfo.getConItemMinfoSid());
		if(count.longValue()==0){
			deletePlanByItemId(itemMainInfo.getConItemMinfoSid(), mainInfoPart.getMoneytype());
			commonDao.delete(itemMainInfo);
		}
	}
	public void deletePlanByItemId(Long itemId,String moneyType){
		String hql = "from RealContractBillandRecePlan plan where plan.contractItemMaininfo = ? and plan.billNature = ?";
		List<RealContractBillandRecePlan> planList = commonDao.list(hql, itemId,moneyType);
		for (RealContractBillandRecePlan realContractBillandRecePlan : planList) {			
			deleteRealPlanByRealPlan(realContractBillandRecePlan);
		}
	}
	
	//////////////通过itemStagePlan删除itemStagePlan的对象。判断如果itemStagePlan不存在的话相应的itemStage也删掉
	public void deleteItemStagePlanByItemStagePlan(ContractItemStagePlan stagePlan){
		ContractItemStage itemStage = stagePlan.getContractItemStage();
		ContractMaininfoPart mainInfoPart = stagePlan.getContractMaininfoPart();
		commonDao.delete(stagePlan);/////删除阶段计划，如果有一个阶段下面的阶段计划都没有了，那么相应的阶段信息也被删除
		String hql = "select count(*) from ContractItemStagePlan where contractItemStage = ?";
		Number count = (Number) commonDao.uniqueResult(hql, itemStage);
		if(count.longValue()==0){
			///////删除阶段的同时把阶段下面的计划也删掉
			delPlanByStageId(itemStage.getConIStageSid(), mainInfoPart.getMoneytype());
			commonDao.delete(itemStage);
		}
	}
	@SuppressWarnings("unchecked")
	public void delPlanByStageId(Long stageId,String moneyType){
		String hql = "from RealContractBillandRecePlan plan where plan.conItemStage = ? and plan.billNature = ?";
		List<RealContractBillandRecePlan> planList = commonDao.list(hql, stageId,moneyType);
		for (RealContractBillandRecePlan realContractBillandRecePlan : planList) {			
			deleteRealPlanByRealPlan(realContractBillandRecePlan);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deletePrimaryTable(Long contractId){
		
		//////通过合同号查询开票收款计划信息
		List<RealContractBillandRecePlan> realPlanList = commonDao.list("from RealContractBillandRecePlan where conMainInfoSid = ?", contractId);
		for (RealContractBillandRecePlan realPlan : realPlanList) {
			if(realPlan.getRealBillAmount() == null || realPlan.getRealBillAmount().doubleValue() == 0){
				deleteMonthlyBillbyPlanId(realPlan);
			}
			if(realPlan.getRealReceAmount() == null || realPlan.getRealReceAmount().doubleValue() == 0){
				deleteMonthlyReceByPlanId(realPlan);
			}
			if((realPlan.getRealBillAmount() == null || realPlan.getRealBillAmount().doubleValue() == 0)&&
					(realPlan.getRealReceAmount() == null || realPlan.getRealReceAmount().doubleValue() == 0)){
				deleteRealPlanByRealPlan(realPlan);
			}
		}

		/////通过合同号查询项目组成信息
		List<ContractItemInfo> itemInfoList = commonDao.
					list("select iteminfo from ContractItemInfo iteminfo," +
							"ContractItemMaininfo item where " +
							"iteminfo.contractItemMaininfo = item.conItemMinfoSid " +
							"and item.contractMainInfo = ?", contractId);
		for (ContractItemInfo itemInfo : itemInfoList) {
			if(itemInfo.getConItemAmountWithTax() == null || itemInfo.getConItemAmountWithTax().doubleValue() == 0){
				deleteItemInfoByItemInfo(itemInfo);
			}
		}
		
		
		/////通过合同号查询阶段计划信息
		List<ContractItemStagePlan> stagePlanList = commonDao.list("from ContractItemStagePlan plan where plan.contractMainSid = ?", contractId);
		for (ContractItemStagePlan stagePlan : stagePlanList) {
			if((stagePlan.getReveAmount()==null || stagePlan.getReveAmount().doubleValue() == 0) && 
					(stagePlan.getStageAmout() == null || stagePlan.getStageAmout().doubleValue() == 0)){
				deleteItemStagePlanByItemStagePlan(stagePlan);
			}
		}
		
		///通过合同号查询费用组成信息
		List<ContractMaininfoPart> mainInfoPartList = commonDao.list("from ContractMaininfoPart where contractmainid = ?", contractId);
		for (ContractMaininfoPart mainInfoPart : mainInfoPartList) {
			if(mainInfoPart.getMoney() == null || mainInfoPart.getMoney().doubleValue() == 0){
				commonDao.delete(mainInfoPart);
			}
		}
		
	
	}
	
	/**
	 * 把原始表中的数据拷贝到备份表中
	 * @param conId
	 */
	private void bakContract(Long conId){
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new BooleanConverter(null), Boolean.class);
		Long changeBatch = sequenceService.getNextValue(SequenceKey.contractChangeBatch);
		ContractMainInfo mainInfo = (ContractMainInfo) commonDao.load(ContractMainInfo.class, conId);
		//修改实际开票收款计划,并删除change表中的信息
		List<RealContractBillandRecePlan> cplan=commonDao.list("from RealContractBillandRecePlan c where c.conMainInfoSid = "+conId);
		for (RealContractBillandRecePlan newplan : cplan) {
			/**备份计划（新增）**/
			BakRealContractBillandRecePlan bakplan2 = new BakRealContractBillandRecePlan();
			try {
				BeanUtils.copyProperties(bakplan2, newplan);
				bakplan2.setConfirmDate(new Date());
				bakplan2.setConfirmPeople(UserUtils.getUser().getId());
				bakplan2.setChangeBatch(changeBatch);
				commonDao.save(bakplan2);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		List<ContractItemMaininfo> itemMainInfoList=new ArrayList<ContractItemMaininfo>();
		itemMainInfoList = commonDao
					.list("from ContractItemMaininfo item where item.contractMainInfo = ?",mainInfo.getConMainInfoSid());
		for (ContractItemMaininfo itemMainInfo : itemMainInfoList) {
			
			List<ContractItemInfo> itemInfoList = new ArrayList<ContractItemInfo>();
			itemInfoList = commonDao
						.list("from ContractItemInfo item where item.contractItemMaininfo = ? ", itemMainInfo.getConItemMinfoSid());
			for(ContractItemInfo itemInfo : itemInfoList){
				/**备份项目组成**/
				BakContractItemInfo bakitem =new BakContractItemInfo();
				try {
					BeanUtils.copyProperties(bakitem, itemInfo);
					bakitem.setConfirmDate(new Date());
					bakitem.setConfirmPeople(UserUtils.getUser().getId());
					bakitem.setChangeBatch(changeBatch);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				commonDao.save(bakitem);
			}
			/**备份项目主体**/
			BakContractItemMaininfo bakItemMain = new BakContractItemMaininfo();
			try {
				BeanUtils.copyProperties(bakItemMain, itemMainInfo);
				bakItemMain.setConfirmDate(new Date());
				bakItemMain.setConfirmPeople(UserUtils.getUser().getId());
				bakItemMain.setChangeBatch(changeBatch);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			commonDao.save(bakItemMain);
		}
		//修改阶段和阶段计划并删除阶段和阶段计划
		List<ContractItemStage> itemStageList=new ArrayList<ContractItemStage>();
		itemStageList = commonDao
					.list("from ContractItemStage stage where stage.contractMainSid = ?", mainInfo.getConMainInfoSid());
		for (ContractItemStage itemStage : itemStageList) {
			List<ContractItemStagePlan> itemStagePlanList = new ArrayList<ContractItemStagePlan>();
			itemStagePlanList = commonDao
					.list("from ContractItemStagePlan sp where sp.contractItemStage = ? ", itemStage);
			for (ContractItemStagePlan itemStagePlan : itemStagePlanList) {
					/**备份阶段计划**/
				BakContractItemStagePlan bakitemStagePlan = new BakContractItemStagePlan();
				try {
					BeanUtils.copyProperties(bakitemStagePlan, itemStagePlan);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				bakitemStagePlan.setConfirmDate(new Date());
				bakitemStagePlan.setConfirmPeople(UserUtils.getUser().getId());
				bakitemStagePlan.setContractItemStageId(itemStagePlan.getContractItemStage().getConIStageSid());
				bakitemStagePlan.setContractMaininfoPartId(itemStagePlan.getContractMaininfoPart().getId());
				bakitemStagePlan.setChangeBatch(changeBatch);
				commonDao.save(bakitemStagePlan);
				////////////
			}
			/**备份阶段**/
			BakContractItemStage bakitemStage = new BakContractItemStage();
			try {
				BeanUtils.copyProperties(bakitemStage, itemStage);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			bakitemStage.setConfirmDate(new Date());
			bakitemStage.setConfirmPeople(UserUtils.getUser().getId());
			bakitemStage.setChangeBatch(changeBatch);
			commonDao.save(bakitemStage);
			///////////
		}
		//修改合同费用组成并删除change表中的信息
		List<ContractMaininfoPart>cpart=commonDao.list("from ContractMaininfoPart c where c.contractmainid = "+mainInfo.getConMainInfoSid());
		for (ContractMaininfoPart newpartn : cpart) {
			/**备份合同费用组成**/
			BakContractMaininfoPart bakpartn = new BakContractMaininfoPart();
			try{
				BeanUtils.copyProperties(bakpartn, newpartn);
			}catch(Exception e){
				throw new RuntimeException(e.getMessage(),e);
			}
			bakpartn.setConfirmDate(new Date());
			bakpartn.setConfirmPeople(UserUtils.getUser().getId());
			bakpartn.setChangeBatch(changeBatch);
			commonDao.save(bakpartn);
			//////////////////////
		}
		/*****备份合同主信息****/
		BakContractMainInfo bakContract = new BakContractMainInfo();
		try {
			BeanUtils.copyProperties(bakContract, mainInfo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		long changeCount = 1l;
		if(mainInfo.getChangeCount()!=null){
			changeCount += mainInfo.getChangeCount();
		}
		bakContract.setChangeCount(changeCount);
		bakContract.setChangeBatch(changeBatch);
		commonDao.save(bakContract);
	}	
	
	/**
	 * 把变更表中的有关数据拷贝到实际表中
	 * @param changContractId
	 */
	@SuppressWarnings("unused")
	private void changeContract(Long changContractId){
		ChangingContractMainInfo chageMain = (ChangingContractMainInfo)commonDao.load(ChangingContractMainInfo.class, changContractId);
		ContractMainInfo mainInfo = (ContractMainInfo) commonDao.load(ContractMainInfo.class, chageMain.getConMainInfoSid());
		//修改实际开票收款计划,并删除change表中的信息
		List<ChangeRealContractBillandRecePlan> cplan=commonDao.list("from ChangeRealContractBillandRecePlan c where c.conMainInfoSid = "+chageMain.getConMainInfoSid());
		for (ChangeRealContractBillandRecePlan plan : cplan) {
			String planHql = "from RealContractBillandRecePlan plan where plan.realConBillproSid = ?";
			RealContractBillandRecePlan newplan= (RealContractBillandRecePlan) commonDao.uniqueResult(planHql, plan.getRealConBillproSid());
			if(newplan!=null){
				newplan.setRealBillAmount(plan.getRealBillAmount());//修改预计开票金额（基准）
				newplan.setRealReceAmount(plan.getRealReceAmount());//修改预计收款金额（基准）
				newplan.setRealTaxBillAmount(plan.getRealTaxBillAmount());//修改预计开票金额（含税）
				newplan.setRealTaxReceAmount(plan.getRealTaxReceAmount());//修改预计收款金额（含税）
				commonDao.update(newplan);
			}else{
				RealContractBillandRecePlan newPlan2= new RealContractBillandRecePlan();
				try {
					BeanUtils.copyProperties(newPlan2, plan);
					commonDao.save(newPlan2);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		//修改项目费用组成并删除change表中的信息
		List<ChangingContractItemMaininfo> itemMainInfoList=new ArrayList<ChangingContractItemMaininfo>();
		itemMainInfoList = commonDao
					.list("from ChangingContractItemMaininfo item where item.contractMainInfo = ?",mainInfo.getConMainInfoSid());
		for (ChangingContractItemMaininfo citemMain : itemMainInfoList) {
			ContractItemMaininfo itemMainInfo=(ContractItemMaininfo)commonDao.load(ContractItemMaininfo.class,citemMain.getConItemMinfoSid());
			List<ChangingContractItemInfo> itemInfoList = new ArrayList<ChangingContractItemInfo>();
			itemInfoList = commonDao
						.list("from ChangingContractItemInfo item where item.contractItemMaininfo = ? ", itemMainInfo.getConItemMinfoSid());
			for(ChangingContractItemInfo changeItemInfo : itemInfoList){
				ContractItemInfo itemInfo = (ContractItemInfo)commonDao.load(ContractItemInfo.class, changeItemInfo.getConItemInfoSid());
				itemInfo.setConItemAmountWithTax(changeItemInfo.getConItemAmountWithTax());
				itemInfo.setConItemAmountWithNoTax(changeItemInfo.getConItemAmountWithNoTax());
				commonDao.update(itemInfo);
			}
		}
		//修改阶段和阶段计划并删除阶段和阶段计划
		List<ChangingContractItemStage> itemStageList=new ArrayList<ChangingContractItemStage>();
		itemStageList = commonDao
					.list("from ChangingContractItemStage stage where stage.contractMainSid = ?", mainInfo.getConMainInfoSid());
		for (ChangingContractItemStage citemStage : itemStageList) {
			ContractItemStage itemStage = (ContractItemStage)commonDao.load(ContractItemStage.class, citemStage.getConIStageSid());
	
			List<ChangingContractItemStagePlan> itemStagePlanList = new ArrayList<ChangingContractItemStagePlan>();
			itemStagePlanList = commonDao
					.list("from ChangingContractItemStagePlan sp where sp.ccontractItemStage = ? ", citemStage);
			for (ChangingContractItemStagePlan cstagePlan : itemStagePlanList) {
				ContractItemStagePlan itemStagePlan = (ContractItemStagePlan)commonDao.load(ContractItemStagePlan.class, cstagePlan.getId());
				itemStagePlan.setStageAmout(cstagePlan.getStageAmout());
				itemStagePlan.setReveAmount(cstagePlan.getReveAmount());
				commonDao.update(itemStagePlan);
			}
		}
		//修改合同费用组成并删除change表中的信息
		List<ChangingContractMaininfoPart>cpart=commonDao.list("from ChangingContractMaininfoPart c where c.contractmainid = "+chageMain.getConMainInfoSid());
		for (ChangingContractMaininfoPart part : cpart) {
			ContractMaininfoPart newpartn=(ContractMaininfoPart) commonDao.load(ContractMaininfoPart.class, part.getId());
			newpartn.setMoney(part.getMoney());//修改合同费用组成金额
			commonDao.update(newpartn);
		}
		long changeCount = 1l;
		if(mainInfo.getChangeCount()!=null){
			changeCount += mainInfo.getChangeCount();
		}
		mainInfo.setChangeCount(changeCount);
		//修改合同信息
		mainInfo.setConNoTaxTamount(chageMain.getConNoTaxTamount());
		mainInfo.setConTaxTamount(chageMain.getConTaxTamount());
		mainInfo.setFinalAccount(chageMain.getFinalAccount());
		mainInfo.setChangeCount(changeCount);
		commonDao.update(mainInfo);
	}
	
	/**
	 *删除变更表中的数据
	 * @param changeContractId
	 */
	private void deleteChangeContract(Long changeContractId){
		ChangingContractMainInfo chageMain = (ChangingContractMainInfo)commonDao.load(ChangingContractMainInfo.class, changeContractId);
		/**删除合同费用组成**/
		List<ChangingContractMaininfoPart>cpart=commonDao.list("from ChangingContractMaininfoPart c where c.contractmainid = "+chageMain.getConMainInfoSid());
		for (ChangingContractMaininfoPart part : cpart) {
			commonDao.delete(part);
		}
		/**删除项目和项目组成**/
		List<ChangingContractItemMaininfo> itemMainInfoList=new ArrayList<ChangingContractItemMaininfo>();
		itemMainInfoList = commonDao
					.list("from ChangingContractItemMaininfo item where item.contractMainInfo = ?",chageMain.getConMainInfoSid());
		for (ChangingContractItemMaininfo citemMain : itemMainInfoList) {
			List<ChangingContractItemInfo> itemInfoList = new ArrayList<ChangingContractItemInfo>();
			itemInfoList = commonDao
						.list("from ChangingContractItemInfo item where item.contractItemMaininfo = ? ", citemMain.getConItemMinfoSid());
			for(ChangingContractItemInfo changeItemInfo : itemInfoList){
				commonDao.delete(changeItemInfo);
			}
			commonDao.delete(citemMain);
		}
		/** 删除阶段和阶段计划**/
		List<ChangingContractItemStage> itemStageList=new ArrayList<ChangingContractItemStage>();
		itemStageList = commonDao
					.list("from ChangingContractItemStage stage where stage.contractMainSid = ?", chageMain.getConMainInfoSid());
		for (ChangingContractItemStage citemStage : itemStageList) {
			List<ChangingContractItemStagePlan> itemStagePlanList = new ArrayList<ChangingContractItemStagePlan>();
			itemStagePlanList = commonDao
					.list("from ChangingContractItemStagePlan sp where sp.ccontractItemStage = ? ", citemStage);
			for (ChangingContractItemStagePlan cstagePlan : itemStagePlanList) {
				commonDao.delete(cstagePlan);
			}
			commonDao.delete(citemStage);
		}
		/**删除实际开票收款计划**/
		List<ChangeRealContractBillandRecePlan> cplan=commonDao.list("from ChangeRealContractBillandRecePlan c where c.conMainInfoSid = "+chageMain.getConMainInfoSid());
		for (ChangeRealContractBillandRecePlan plan : cplan) {
			commonDao.delete(plan);
		}
		/**删除合同主体信息**/
		commonDao.delete(chageMain);
		
	}


	@SuppressWarnings("unchecked")
	public void deleteChangeConMains(Long[] ids) {
		List<ChangingContractMainInfo> changeMains = null;
		changeMains = commonDao
		.list("from ChangingContractMainInfo c where c.conMainInfoSid in "
				+ idIn(ids));
		for(ChangingContractMainInfo chang:changeMains){
			this.updateChangeContractState(chang,"2");
	    }
	}
	private void updateChangeContractState(ChangingContractMainInfo mainInfo,String state){
		mainInfo.setChangeContractState(state);
		commonDao.update(mainInfo);
	}
	
//	private void deleteChangingData(ChangingContractMainInfo chang) {
//		commonDao.executeUpdate("delete from ChangeRealContractBillandRecePlan b where b.conMainInfoSid = ?", chang.getConMainInfoSid());
//		commonDao.executeUpdate("delete from ChangingContractMaininfoPart p where p.contractmainid = ?", chang.getConMainInfoSid());
//		commonDao.delete(chang);
//	}

	@SuppressWarnings("unchecked")
	public ContractItemMaininfo findContractItemMaininfoByItemNo(String temNo) {
		List<ContractItemMaininfo> item=(List<ContractItemMaininfo>) commonDao.list("select c from ContractItemMaininfo c where c.conItemId='"+temNo+"'");
		Iterator<ContractItemMaininfo>it=item.iterator();
		if(it.hasNext()){
			//System.out.println("========================"+it.next().getConItemId());
		  return it.next();
		  }
		return null;
	}

	public Map<Long,ChangingContractMaininfoPart> getMainInfoPartById(Long id) {
		// TODO Auto-generated method stub
		String hql="from ChangingContractMaininfoPart part where part.contractmainid=?";
		List<ChangingContractMaininfoPart> list=commonDao.list(hql, id);
		Map<Long, ChangingContractMaininfoPart> map=new HashMap<Long, ChangingContractMaininfoPart>();
		if(list!=null&&list.size()>0){
			for (ChangingContractMaininfoPart changingContractMaininfoPart : list) {
				map.put(changingContractMaininfoPart.getId(), changingContractMaininfoPart);
			}
		}
		return map;
	}

	public Map<Long, ChangeRealContractBillandRecePlan> getPlanById(Long id) {
		// TODO Auto-generated method stub
		String hql="from ChangeRealContractBillandRecePlan plan where plan.conMainInfoSid=?";
		List<ChangeRealContractBillandRecePlan> list=commonDao.list(hql, id);
		Map<Long, ChangeRealContractBillandRecePlan> map=new HashMap<Long, ChangeRealContractBillandRecePlan>();
		if(list!= null&&list.size()>0){
			for (ChangeRealContractBillandRecePlan changeRealContractBillandRecePlan : list) {
				map.put(changeRealContractBillandRecePlan.getRealConBillproSid(), changeRealContractBillandRecePlan);
			}
		}
		return map;
	}

	public void contractIsCloseByContractId(Long mainid) {
		commonDao.flushSession();
		ContractMainInfo cmi = (ContractMainInfo)commonDao.load(ContractMainInfo.class, mainid);
		List<ContractItemMaininfo> itemmaininfolist = commonDao.list(" from ContractItemMaininfo where  conItemState = '0' or conItemState is null and contractMainInfo = "+mainid+" ");
		 if(itemmaininfolist.size()==0){
	        String sql = "select sum(rcbrp.realArriveAmount),sum(rcbrp.realTaxReceAmount) " +
	        		"from RealContractBillandRecePlan rcbrp where conMainInfoSid = "+mainid+" ";
	        List<Object[]> moneyList = commonDao.list(sql);
	       if(moneyList.get(0)[0]==null){
	        	cmi.setConState(4L);
	        	commonDao.update(cmi);
	       }else{
		       if((Double.valueOf(moneyList.get(0)[1].toString())-Double.valueOf(moneyList.get(0)[0].toString()))==0){
			        cmi.setConState(8L);
		        	commonDao.update(cmi);
		       }
	       }
        }else if(cmi.getConState()==8L){
        	cmi.setConState(4L);
        	commonDao.update(cmi);
        }
        
	}

	public void itemIsCloseByApplyId(Long applyid) {
		commonDao.flushSession();
		ApplyBill ab = (ApplyBill)commonDao.load(ApplyBill.class, applyid);
 		if(ab.getContractMainInfo() != null){
			ContractMainInfo contractMainInfo = (ContractMainInfo)commonDao.load(ContractMainInfo.class,ab.getContractMainInfo());
			if(contractMainInfo.getContractType().equals("1")){
				String sql1 = "select r.contractItemMaininfo from RealContractBillandRecePlan r," +
						     "BillandProRelaion b where b.applyBill = "+applyid+"" +
						     " and b.realContractBillandRecePlan = r.realConBillproSid ";
				List<Long> itemidlist =commonDao.list(sql1);
				for (Long itemid : itemidlist) {
					itemIsCloseByItem(itemid);
				}	
		    }else{
		    	String sql2 = "select sum(i.invoiceAmount) from ApplyBill ab,InvoiceInfo i where i.applyInvoiceId = ab.billApplyId and ab.contractMainInfo = "+contractMainInfo.getConMainInfoSid()+" ";
		        Double allTicketMoney = (Double)commonDao.uniqueResult(sql2);
		        if(allTicketMoney == null){
		        	allTicketMoney = 0.0;
		        }
		        if((contractMainInfo.getConNoTaxTamount()-allTicketMoney)==0){
		        	contractMainInfo.setConState(8L);
		        	commonDao.update(contractMainInfo);
		        }
		    }
		}
	}

	public void itemIsCloseByItem(String itemCode) {
		commonDao.flushSession();
		ContractItemMaininfo cmaininfo = (ContractItemMaininfo)commonDao.uniqueResult(" from ContractItemMaininfo where conItemId = ? ", itemCode);
		itemIsCloseByItem(cmaininfo.getConItemMinfoSid());
	}

	public void itemIsCloseByItem(Long itemid) {
		commonDao.flushSession();
		ContractItemMaininfo contractItemMaininfo = (ContractItemMaininfo)commonDao.load(ContractItemMaininfo.class, itemid);
		//项目外协总是否结束
		List<AssistanceContract> assistanceContract = commonDao.list(" from AssistanceContract where assistanceContractType <> '5' and conItemMainInfoSid = ? ",contractItemMaininfo.getConItemMinfoSid());
		//项目是否已经成本确认
	//	if(contractItemMaininfo.getConItemCostSure()==3L){
			if(isItemFullBill(contractItemMaininfo)&&isItemFullRece(contractItemMaininfo)&&assistanceContract.size()!=0){	    
			    contractItemMaininfo.setConItemState(1L);
			    commonDao.update(contractItemMaininfo);
			}
			if(isItemFullBill(contractItemMaininfo)&&isItemFullRece(contractItemMaininfo)&&assistanceContract.size()==0){
			    contractItemMaininfo.setConItemState(1L);
			    commonDao.update(contractItemMaininfo);
			    contractIsCloseByContractId(contractItemMaininfo.getContractMainInfo());
			}
	//	}
	}
	
	private boolean isItemFullRece(ContractItemMaininfo itemMainInfo){
		List<RealContractBillandRecePlan> rbrp = commonDao.list(" from RealContractBillandRecePlan where contractItemMaininfo = ? ", itemMainInfo.getConItemMinfoSid());
		for (RealContractBillandRecePlan realContractBillandRecePlan : rbrp) {
			if(realContractBillandRecePlan.getRealArriveAmount() != null){
				if(realContractBillandRecePlan.getRealTaxReceAmount().doubleValue()-realContractBillandRecePlan.getRealArriveAmount().doubleValue()!=0){
					return false;
				}
			}else{
				return false;
			}
		}
		return true;
	}
	
	private boolean isItemFullBill(ContractItemMaininfo itemMainInfo){
		List<RealContractBillandRecePlan> rbrp = commonDao.list(" from RealContractBillandRecePlan where billType <> '4' and contractItemMaininfo = ? ", itemMainInfo.getConItemMinfoSid());
		for (RealContractBillandRecePlan realContractBillandRecePlan : rbrp) {
			if(realContractBillandRecePlan.getBillInvoiceAmount() != null){
				if(realContractBillandRecePlan.getRealTaxBillAmount().doubleValue()-realContractBillandRecePlan.getBillInvoiceAmount().doubleValue()!=0){
					return false;
				}
			}else{
				return false;
			}
		}
		return true;
	}
	/**
	 * 根据项目号计算剩余外协
	 * @param mainProjectId
	 * @return
	 */
	public double sumRemainAssCon(String mainProjectId) {
		ContractItemMaininfo cm = (ContractItemMaininfo)commonDao.uniqueResult("from ContractItemMaininfo cm where cm.conItemId = ?", mainProjectId);
		Number contractMoney = (Number) commonDao.uniqueResult("select sum(a.contractMoney) from AssistanceContract a where a.mainProjectId = ?", mainProjectId);
		Number ticketMoney = (Number) commonDao.uniqueResult("select sum(a.ticketMoney) from AssistanceContract a where a.mainProjectId = ?", mainProjectId);
		double sum = 0;
		if(contractMoney != null){
			if(ticketMoney != null){
				sum = contractMoney.doubleValue() - ticketMoney.doubleValue() ;
			}else{
				sum = contractMoney.doubleValue();
			}
		}
		cm.setSysRemainAssistance(sum);
		commonDao.update(cm);
		return sum;
	}
	/**
	 * 根据项目id计算剩余发票
	 * @param conItemMinfoSid
	 * @return
	 */
	public double sumRemainBIll(Long conItemMinfoSid) {
		ContractItemMaininfo cm = (ContractItemMaininfo) commonDao.load(ContractItemMaininfo.class, conItemMinfoSid);
		Number itemMoney = (Number) commonDao.uniqueResult("select sum(r.conItemAmountWithTax) from ContractItemInfo r where r.contractItemMaininfo = ? ", conItemMinfoSid);
		double sum = 0;
		if(itemMoney != null){
			Number brMoney = (Number) commonDao.uniqueResult(" select sum(br.relateAmount) from BillandProRelaion br,RealContractBillandRecePlan r where br.realContractBillandRecePlan = r.realConBillproSid and r.billType != 4 and r.contractItemMaininfo = ? ", conItemMinfoSid);
			if(brMoney != null){
				sum = itemMoney.doubleValue() - brMoney.doubleValue();
			}else{
				sum = itemMoney.longValue();
			}
		}
		cm.setSysRemainBill(sum);
		commonDao.update(cm);
		return sum;
	}

	public String[] getContractMaterialSetByMainId(Long mainid) {
		ContractMainInfo mainInfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, mainid);
		Long clientId=mainInfo.getConCustomer();
		String clientType=mainInfo.getCustomereventtype();
		String hql="from ContractMaterialSet material where material.customerName=? and material.customerProjectType=?";
		ContractMaterialSet materialSet=(ContractMaterialSet)commonDao.uniqueResult(hql, clientId,clientType);
		if(materialSet!=null){
			String customerMaterial=materialSet.getCustomerMaterial();
			String[] materialArr=StringUtils.split(customerMaterial,",");
			return materialArr;
		}
		return null;
	}
	public List<MaterialManager> getMaterialManagerByMainId(Long mainid){
		ContractMainInfo mainInfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, mainid);
		Long clientId=mainInfo.getConCustomer();
		String clientType=mainInfo.getCustomereventtype();
		List<MaterialManager> list=new ArrayList<MaterialManager>();
		String hql="from ContractMaterialSet material where material.customerName=? and material.customerProjectType=?";
		ContractMaterialSet materialSet=(ContractMaterialSet)commonDao.uniqueResult(hql, clientId,clientType);
		if(materialSet!=null){
			String customerMaterial=materialSet.getCustomerMaterial();
			String[] materialArr=StringUtils.split(customerMaterial,",");
			if(materialArr!=null&&materialArr.length>0){
				for(int k=0;k<materialArr.length;k++){
					MaterialManager material=(MaterialManager)commonDao.uniqueResult("from MaterialManager manager where manager.materialCode=?", materialArr[k]);
					if(material!=null){
						list.add(material);
					}
				}
			}
		}
		return list;
	}

	public List<MaterialManager> getMaterialManagerByOtherId(Long otherId) {
		//No1.通过id查询对象
		ContractOtherInfo otherInfo=(ContractOtherInfo)commonDao.load(ContractOtherInfo.class, otherId);
		String lize=otherInfo.getFinallyLize();
		String[] lizeArr=StringUtils.split(lize,",");
		List<MaterialManager> list=new ArrayList<MaterialManager>();
		if(lizeArr!=null&&lizeArr.length>0){
			for(int k=0;k<lizeArr.length;k++){
				MaterialManager material=(MaterialManager)commonDao.uniqueResult("from MaterialManager manager where manager.materialCode=?", lizeArr[k]);
				if(material!=null){
					list.add(material);
				}
			}
		}
		return list;
	}

	public List<MaterialManager> getAllMaterialManager() {
		String hql="from MaterialManager manager order by manager.sortOrder";
		List<MaterialManager> list=commonDao.list(hql);
		return list;
	}



	public List<RealContractBillandRecePlan> findFormalPlanlist(Long mainid) {
		List<RealContractBillandRecePlan> list = commonDao
		.list("from RealContractBillandRecePlan where conMainInfoSid=? order by realPredBillDate,conItemStage,contractItemMaininfo",
				mainid);
        return list;
	}

	public void saveFormalPlanInfo(List<RealContractBillandRecePlan> planlist) {
		// 根据阶段查询出计划开票和收款日期
		for (int i = 0; i < planlist.size(); i++) {
			RealContractBillandRecePlan ib = (RealContractBillandRecePlan) commonDao.load(
					RealContractBillandRecePlan.class, planlist.get(i)
							.getRealConBillproSid());
			ContractMainInfo maininfo= (ContractMainInfo) commonDao.load(ContractMainInfo.class,planlist.get(i).getConMainInfoSid());
			if(maininfo.getStandard().equals("1")){
				ib.setRealBillAmount(planlist.get(i).getRealBillAmount());
				ib.setRealTaxBillAmount(planlist.get(i).getRealBillAmount());
			}else{
				ib.setRealBillAmount(planlist.get(i).getRealBillAmount());
				ib.setRealTaxBillAmount(BigDecimalUtils.toBigDecial(TaxChange.NoTaxToTaxDouble((Double)(Number)planlist.get(i).getRealBillAmount(),planlist.get(i).getBillType())));
			}
			
			ib.setRealReceAmount(planlist.get(i).getRealReceAmount());
			ib.setRealTaxReceAmount(planlist.get(i).getRealReceAmount());
			ib.setBillType(planlist.get(i).getBillType());
			
			commonDao.update(ib);
		}
		
	}

	public String isPlanModify(Long planid) {
		List<BillandProRelaion> bp = commonDao.list(" from BillandProRelaion where realContractBillandRecePlan = ? ", planid);
		if(bp.size()==0){
			return "true";
		}else{
			return "false";
		}

	}

	public int isSureSubmit(Long mainid) {
		//合同总金额
		ContractMainInfo cmi =  (ContractMainInfo)commonDao.load(ContractMainInfo.class, mainid);
		Double contractMoney = Double.valueOf(0);
		if(cmi.getStandard().equals("1")){
			contractMoney = cmi.getConTaxTamount();
		}else{
			contractMoney = cmi.getConNoTaxTamount();
		}
		
		//比对费用组成
		String sqlOfMainInfoPart = " select sum(c.money) " +
				" from ContractMaininfoPart c " +
				" where c.contractmainid = "+mainid+" " ;
		Double allMainInfoPartMoney = (Double)commonDao.uniqueResult(sqlOfMainInfoPart);
		if(allMainInfoPartMoney==null||(contractMoney-allMainInfoPartMoney)!=0){
			return 1;//表示费用组成不相等
		} 

	    //比对阶段金额和合同总金额
	    String sqlOfStage = "select sum(cp.stageAmout) from ContractItemStagePlan cp where ticketType!='4'  and  contractMainSid = "+mainid+" ";
	    Double allStageMoney =  (Double)commonDao.uniqueResult(sqlOfStage);
	    if(allStageMoney==null||(contractMoney-allStageMoney)!=0){
	    	return 2;//表示阶段总金额和合同金额不等
	    }
	    
	    //对比计划中费用组成的收款金额和费用含税金额相等
	    String sqlPlanReveSql = " select count(*) "+
	                            " from (select p.fk_con_main_sid, "+
	                            " p.fk_bill_nature, "+
	                            " (select sum(p2.init_tax_bill_amount) "+
	                            " from yx_init_con_billpro p2 "+
	                            " where p2.fk_bill_type <> '4' "+
	                            " and p2.fk_con_main_sid = p.fk_con_main_sid "+
	                            " and p2.fk_bill_nature = p.fk_bill_nature) as tax_bill_amount, "+
	                            " sum(p.int_rece_amount) as tax_rece_amount "+
	                            " from yx_init_con_billpro p "+
	                            " group by p.fk_con_main_sid, p.fk_bill_nature  ) rb "+
	                            " where rb.tax_rece_amount <> rb.tax_bill_amount "+
	                            " and rb.fk_con_main_sid = '"+mainid+"' ";
	   
	    Number countId = (Number) commonDao.uniqueSQLResult(sqlPlanReveSql);
	    if(countId.longValue()!=0){
	    	return 9;  //费用下收款金额和费用含税金额不等
	    }	    
	        
	    if("1".equals(cmi.getContractType())){
		    //比对项目金额和合同总金额
		    String sqlOfItem = " select sum(ci.conItemAmountWithTax) " +
		    		" from ContractItemInfo ci,ContractItemMaininfo cim " +
		    		" where ci.contractItemMaininfo = cim.conItemMinfoSid" +
		    		" and cim.contractMainInfo = "+mainid+" ";
		    BigDecimal allItemMoney = (BigDecimal)commonDao.uniqueResult(sqlOfItem);
			if(allItemMoney==null||(contractMoney-allItemMoney.doubleValue())!=0){
				return 3;//项目总金额和合同总金额不等
			}
	    }
	    
	    //比对合同项目部门（主合同部门必须出现在项目中）
	    if("1".equals(cmi.getContractType())){
		    String hasItemDepSql = " select count(*) from ContractMainInfo c where exists "+
	                    " (select 1 from ContractItemMaininfo item "+
	                    " where item.contractMainInfo = c.conMainInfoSid "+
	                    " and item.itemResDept = c.mainItemDept) and c.conMainInfoSid = " +mainid+ "";
		    Long hasItemDep = (Long) commonDao.uniqueResult(hasItemDepSql);
		    if(hasItemDep==0){
		    	return 4;//主负责部门必须在项目中出现 
		    }
	    }
	    
		//比对计划金额和合同总金额
		List<InitContractBillpro> planlist = findPlanlist(mainid);
		BigDecimal allreveMoney=BigDecimal.ZERO;
		//计划中无收款日期的不能通过
		if(planlist.size()!=0){
			for (InitContractBillpro initContractBillpro : planlist) {
				if(initContractBillpro.getInitBillDate()==null||initContractBillpro.getInitReceDate()==null||initContractBillpro.getConItemStage()==null){
					return 5;  //计划开票日期或者计划收款日期为空
				}
					allreveMoney=allreveMoney.add(initContractBillpro.getInitReceAmount());	
			}	
				if(BigDecimalUtils.toBigDecial(cmi.getConTaxTamount()).compareTo(allreveMoney)!=0){
			    	return 6;//计划中收款金额不等于合同含税总金额
			    }
		}
	
		
		BigDecimal allPlanMoney = BigDecimal.ZERO ;
		if(planlist.size()!=0){
		    for(InitContractBillpro icb:planlist){
		    	if(!icb.getBillType().equals("4")){
		    		allPlanMoney=allPlanMoney.add(icb.getInitBillAmount());
		    	}
		    }
		    if(BigDecimalUtils.toBigDecial(contractMoney).compareTo(allPlanMoney)!=0){
		    	return 7;//计划中开票金额和合同金额不等
		    }
		}else{
			return 8;//计划为生成
		}
		
		
		//判断合同客户ERP号是否为数字
		YXClientCode client = (YXClientCode)commonDao.load(YXClientCode.class, cmi.getConCustomer());
	    if(!NumberUtils.isDigits(client.getUserCode())){
	         return 10;		
	    }
	    
	    
	    ///// 判断合同的其他信息时候存在，如果不存在，提示没有其他信息，如果存在，但是质保期要求字段为空，提示质保期要求必须填写
	    String otherInfoHql = "select otherInfo from  ContractOtherInfo otherInfo,ContractMainInfo minfo " +
	    		"where otherInfo.contractMainSid = minfo.conMainInfoSid and minfo.conMainInfoSid = ?";
	    ContractOtherInfo otherInfo = (ContractOtherInfo)commonDao.uniqueResult(otherInfoHql, cmi.getConMainInfoSid());
	    if(otherInfo == null ){
	    	return 11;
	    }else{
	    	if(StringUtils.isBlank(otherInfo.getGuaranteeInfo())){
	    		return 11;
	    	}
	    }
	    
	    return 0;
	}

	public Double accountReveMoney(Long mainid) {
		String accountReveMoneySql=" select sum(icbp.initReceAmount) from InitContractBillpro icbp where conMainInfoSid = "+mainid+" ";
		BigDecimal ReveMoney =(BigDecimal)commonDao.uniqueResult(accountReveMoneySql);
		if(ReveMoney==null){
			ReveMoney=BigDecimal.ZERO;
		}
		return ReveMoney.doubleValue();
	}

	public double getRemainContractReceAmount(Long contractId) {
		ContractMainInfo maininfo = (ContractMainInfo) commonDao.load(ContractMainInfo.class, contractId);
		Number planCount = (Number) commonDao.uniqueResult("select sum(reveAmount) from ContractItemStagePlan where contractMainSid = ?",contractId);
		if(planCount == null){
			return maininfo.getConTaxTamount();
		}else{
			return maininfo.getConTaxTamount() - planCount.doubleValue();
		}
	}
	
	public void cleanRealPlan(Long mainid) {
		commonDao.flushSession();
		   List<RealContractBillandRecePlan> rcbrpList = commonDao.list(" from RealContractBillandRecePlan where conMainInfoSid = ? ", mainid);
		   for (RealContractBillandRecePlan realContractBillandRecePlan : rcbrpList) {
			   if(realContractBillandRecePlan.getRealBillAmount().equals(BigDecimal.ZERO)&&realContractBillandRecePlan.getRealTaxReceAmount().equals(BigDecimal.ZERO)){
				   commonDao.delete(realContractBillandRecePlan);
				   System.out.print("删除开票金额和收款金额为0的计划");
			   }
		   }
		}

	public Long getLinkManIdByName(String linkManName,Long customId) {
		YXLinkMan linkman = (YXLinkMan) commonDao.uniqueResult("from YXLinkMan where clientId = ? and name = ?", customId,linkManName);
		if(linkman==null){
			YXLinkMan newlinkman = new YXLinkMan();
			newlinkman.setName(linkManName);
			newlinkman.setClientId(customId);
			newlinkman.setUpdateBy(new Date());
			newlinkman.setById(UserUtils.getUser().getId());
			newlinkman.setIs_active("1");
			commonDao.save(newlinkman);
			return newlinkman.getId();
		}else{
			return linkman.getId();
		}
		
	}

	public boolean ContractIdIsRepeat(Long mainid,String contractId) {
		ContractMainInfo cmain = (ContractMainInfo)commonDao.uniqueResult("from ContractMainInfo where conId = ? and conMainInfoSid != ? ", contractId,mainid);
		if(cmain==null){
			return false;
		}
		return true;
	}
	public List<YXTypeManage> getTicketTypeByPartId(String typeSmall){
		YXTypeManage tm1 = (YXTypeManage)commonDao.uniqueResult("from YXTypeManage tm where tm.typeBig =1004 and tm.typeSmall = ? and rownum <= 1", typeSmall);
		YXTypeManage tm2 =(YXTypeManage)commonDao.uniqueResult("from YXTypeManage tm where tm.typeBig =1004 and tm.typeSmall = '4' and rownum <= 1");
		List<YXTypeManage> list = new ArrayList<YXTypeManage>();
		list.add(tm1);
		list.add(tm2);
		return list;
	}
	
	public void updateItemNoTaxMoney() {
		List<ContractItemInfo> iteminfolist = commonDao.list("from ContractItemInfo where conItemAmountWithNoTax is null ");
		for (ContractItemInfo contractItemInfo : iteminfolist) {
		   ContractMaininfoPart mainpart = (ContractMaininfoPart) commonDao.load(ContractMaininfoPart.class, contractItemInfo.getMainInfoPartId());
		   ContractMainInfo cmaininfo = (ContractMainInfo) commonDao.load(ContractMainInfo.class, mainpart.getContractmainid());
		   //基准为含税
		   if(cmaininfo.getStandard().equals("1")){
			  contractItemInfo.setConItemAmountWithNoTax(TaxChange.TaxToNoTaxBigDecimal(contractItemInfo.getConItemAmountWithTax(), mainpart.getTicketType()));
			  commonDao.update(contractItemInfo);  
		   }
		   //基准为不含税
		   if(cmaininfo.getStandard().equals("2")){
			  contractItemInfo.setConItemAmountWithNoTax(contractItemInfo.getConItemAmountWithTax());
			  commonDao.update(contractItemInfo);
		   }
		   
		}
		
	}

	

}
