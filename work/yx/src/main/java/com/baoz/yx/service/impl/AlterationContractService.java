package com.baoz.yx.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.YXClientCode;
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
import com.baoz.yx.service.IAlterationContractService;
import com.baoz.yx.service.IContractCommonService;
import com.baoz.yx.service.ISequenceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.TaxChange;
import com.baoz.yx.tools.exception.DefineException;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.utils.SequenceKey;
import com.baoz.yx.utils.UserUtils;
import common.Logger;

/**
 * 合同变更的所有业务操作实现类，实现IAlterationContractService接口
 * @author xusheng
 *
 */
@Service("alterationContractService")
@Transactional
public class AlterationContractService implements IAlterationContractService {

	private static Logger log = Logger.getLogger(AlterationContractService.class);
	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao	commonDao;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService 			service;
	
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	@Autowired
	@Qualifier("sequenceService")
	private ISequenceService sequenceService;
	
	@Autowired
	@Qualifier("contractCommonService")
	private IContractCommonService contractCommonService;
	
	
	public IContractCommonService getContractCommonService() {
		return contractCommonService;
	}

	public void setContractCommonService(
			IContractCommonService contractCommonService) {
		this.contractCommonService = contractCommonService;
	}

	@SuppressWarnings("unchecked")
	public ChangingContractMainInfo loadandcopyContractMainInfotoclose(
			Long mainid) {
		
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new BooleanConverter(null), Boolean.class);
		ContractMainInfo maininfoa=(ContractMainInfo) commonDao.load(ContractMainInfo.class, mainid);
        //备份合同主信息
		ChangingContractMainInfo cmaininfo=new ChangingContractMainInfo();
		try {
			BeanUtils.copyProperties(cmaininfo, maininfoa);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		} 
		maininfoa.setExistC(true);
		commonDao.update(maininfoa);
		cmaininfo.setOpTime(new Date());
		cmaininfo.setConModInfo(true);
		cmaininfo.setChangeType("1");
		cmaininfo.setChangeContractState("0");
		commonDao.save(cmaininfo);
		List<ContractMaininfoPart> mainlist=commonDao.list("from ContractMaininfoPart where contractmainid=?  ", mainid);
        
		//备份合同组成内容

		for(ContractMaininfoPart cmp:mainlist){
			ChangingContractMaininfoPart ccmp=new ChangingContractMaininfoPart();
             try {
        	
				BeanUtils.copyProperties(ccmp, cmp);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(),e);
			} 
			commonDao.save(ccmp);
        }
		//备份阶段
		List<ContractItemStage> itemStageList = commonDao.list("from ContractItemStage stage where stage.contractMainSid = ?", mainid);
		for (ContractItemStage contractItemStage : itemStageList) {
			ChangingContractItemStage ccis = new ChangingContractItemStage();
			try{
		
				BeanUtils.copyProperties(ccis, contractItemStage);
			}catch(Exception e){
				throw new RuntimeException(e.getMessage(),e);
			}
			commonDao.save(ccis);
		}
		//备份阶段计划
		List<ContractItemStagePlan> itemStagePlanList=commonDao.list("from ContractItemStagePlan tab where tab.contractMainSid = ?", mainid);
		for (ContractItemStagePlan contractItemStagePlan : itemStagePlanList) {
			ChangingContractItemStagePlan  ccisp=new ChangingContractItemStagePlan();
			try{
			
				BeanUtils.copyProperties(ccisp, contractItemStagePlan);
				ChangingContractItemStage stage = (ChangingContractItemStage)commonDao.load(ChangingContractItemStage.class, contractItemStagePlan.getContractItemStage().getConIStageSid());
				ChangingContractMaininfoPart part = (ChangingContractMaininfoPart)commonDao.load(ChangingContractMaininfoPart.class, contractItemStagePlan.getContractMaininfoPart().getId());
				ccisp.setCcontractMaininfoPart(part);
				ccisp.setCcontractItemStage(stage);
			}catch (Exception e) {
				throw new RuntimeException(e.getMessage(),e);
			}
			commonDao.save(ccisp);
		}
		//备份项目主体信息和项目组成
		List<ContractItemMaininfo> itemMainInfoList=commonDao.list("from ContractItemMaininfo itemMain where itemMain.contractMainInfo = ?", mainid);
		for (ContractItemMaininfo contractItemMaininfo : itemMainInfoList) {
			ChangingContractItemMaininfo ccimi =  new ChangingContractItemMaininfo();
			try {
			
				BeanUtils.copyProperties(ccimi, contractItemMaininfo);
			} catch (IllegalAccessException e) {
				
				log.info(e);
			} catch (InvocationTargetException e) {
				log.info(e);
			}
			commonDao.save(ccimi);
			
			//备份项目组成
			List<ContractItemInfo> itemInfoList = contractItemMaininfo.getContractItemInfolist();
			for (ContractItemInfo contractItemInfo : itemInfoList) {
				ChangingContractItemInfo ccii=new ChangingContractItemInfo();
				try {
			
					BeanUtils.copyProperties(ccii, contractItemInfo);
				} catch (IllegalAccessException e) {
					log.info(e);
				} catch (InvocationTargetException e) {
					log.info(e);
				}
				commonDao.save(ccii);
			}
		}
		
		
		
		//备份合同开票收款计划
		List<RealContractBillandRecePlan> realbillplanlist=commonDao.list("from RealContractBillandRecePlan where conMainInfoSid=?", mainid);
		for (RealContractBillandRecePlan realContractBillandRecePlan : realbillplanlist) {
			ChangeRealContractBillandRecePlan crcbrp=new ChangeRealContractBillandRecePlan();
			try {
			
				BeanUtils.copyProperties(crcbrp, realContractBillandRecePlan);
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(),e);
			}
			commonDao.save(crcbrp);
		}		
        return cmaininfo;
	}
	
	public ChangingContractMainInfo saveContractInfo(ChangingContractMainInfo maininfo) {
		ChangingContractMainInfo ccmi=(ChangingContractMainInfo) commonDao.load(ChangingContractMainInfo.class, maininfo.getConMainInfoSid());
        ccmi.setConNoTaxTamount(maininfo.getConNoTaxTamount());
        ccmi.setConTaxTamount(maininfo.getConTaxTamount());
        ccmi.setChangeExplain(maininfo.getChangeExplain());
        commonDao.update(ccmi);
		return ccmi;
	}
	
	public void saveContractMaininfoPart(List<ChangingContractMaininfoPart> mainInfoPartList,Long mainid) {
		ChangingContractMainInfo dbMainInfo = (ChangingContractMainInfo) commonDao.load(ChangingContractMainInfo.class, mainid);
		Double conMoney = 0.0;
		for (ChangingContractMaininfoPart contractMaininfoPart : mainInfoPartList) {
			 ChangingContractMaininfoPart cmp=(ChangingContractMaininfoPart) commonDao.load(ChangingContractMaininfoPart.class, contractMaininfoPart.getId());
		     cmp.setMoney(contractMaininfoPart.getMoney());
		     commonDao.update(cmp);
		     //累计合同金额
		     if(dbMainInfo.getStandard().equals("1")){
		    	 //输入的是含税，算不含税
		    	 conMoney += TaxChange.TaxToNoTaxDouble(cmp.getMoney(), cmp.getTicketType());
		     }else{
		    	 //输入的是不含税，算含税
		    	 conMoney += TaxChange.NoTaxToTaxDouble(cmp.getMoney(), cmp.getTicketType());
		     }
		}
	     if(dbMainInfo.getStandard().equals("1")){
	    	 //输入的是含税，算不含税
	    	 dbMainInfo.setConNoTaxTamount(conMoney);
	     }else{
	    	 //输入的是不含税，算含税
	    	 dbMainInfo.setConTaxTamount(conMoney);
	     }
	     commonDao.update(dbMainInfo);
	}

	/***
	 * 修改合同变更信息，1，为待确认，2为退回
	 * @param mainId
	 * @param state
	 */
	public ChangingContractMainInfo applySubmit(Long mainId,String state){
		ChangingContractMainInfo mainInfo=(ChangingContractMainInfo)commonDao.load(ChangingContractMainInfo.class, mainId);
		mainInfo.setChangeContractState(state);
		commonDao.update(mainInfo);
		return mainInfo;
		
	}
	
	public void savePlanInfo(List<ChangeRealContractBillandRecePlan> planlist,Long mainid){
		ChangingContractMainInfo mainInfo = (ChangingContractMainInfo)commonDao.load(ChangingContractMainInfo.class, mainid);
		 //根据阶段查询出计划开票和收款日期
			for (int i = 0; i < planlist.size(); i++) {
				ChangeRealContractBillandRecePlan ib = (ChangeRealContractBillandRecePlan) commonDao.load(
						ChangeRealContractBillandRecePlan.class, planlist.get(i).getRealConBillproSid());
				ib.setRealBillAmount(planlist.get(i).getRealBillAmount());
				if(mainInfo.getStandard().equals("1")){
					ib.setRealTaxBillAmount(ib.getRealBillAmount());
				}
				else{
					ib.setRealTaxBillAmount(TaxChange.NoTaxToTaxBigDecimal(ib.getRealBillAmount(), ib.getBillType()));
				}
				ib.setRealReceAmount(planlist.get(i).getRealReceAmount());
				ib.setRealTaxReceAmount(ib.getRealReceAmount());
				commonDao.update(ib);
			}
	}
	
	public void saveStagePlan(List<ChangingContractItemStagePlan> itemStagePlanList){
		if(itemStagePlanList!=null&&itemStagePlanList.size()>0){
			for (ChangingContractItemStagePlan itemStagePlan : itemStagePlanList) {
				ChangingContractItemStagePlan changingItemStagePlan = (ChangingContractItemStagePlan)commonDao.load(ChangingContractItemStagePlan.class, itemStagePlan.getId());
				changingItemStagePlan.setStageAmout(itemStagePlan.getStageAmout());
				changingItemStagePlan.setReveAmount(itemStagePlan.getReveAmount());
				commonDao.update(changingItemStagePlan);
			}
		}
		
	}
	
	public void saveItemMainInfo(List<ChangingContractItemInfo> itemInfoList){
		if(itemInfoList!=null&&itemInfoList.size()>0){
			for (ChangingContractItemInfo itemInfo : itemInfoList) {
				Long itemInfoId = itemInfo.getConItemInfoSid();
				ChangingContractItemInfo changingItemInfo = (ChangingContractItemInfo)commonDao.load(ChangingContractItemInfo.class, itemInfoId);
				///拿到费用组成的id，通过费用组成拿到项目主体信息
				ChangingContractMaininfoPart part = (ChangingContractMaininfoPart)commonDao.load(ChangingContractMaininfoPart.class, changingItemInfo.getMainInfoPartId());
				ChangingContractMainInfo mainInfo = (ChangingContractMainInfo)commonDao.load(ChangingContractMainInfo.class, part.getContractmainid());
				changingItemInfo.setConItemAmountWithTax(itemInfo.getConItemAmountWithTax());
				if("2".equals(mainInfo.getStandard())){///如果不含税。项目组成的不含税金额等于基准金额
					changingItemInfo.setConItemAmountWithNoTax(itemInfo.getConItemAmountWithTax());
				}else if("1".equals(mainInfo.getStandard())){////如果含税，项目组成的不含税金额等于基准金额*票据类型
					changingItemInfo.setConItemAmountWithNoTax(TaxChange.TaxToNoTaxBigDecimal(itemInfo.getConItemAmountWithTax(), part.getTicketType()));
				}
				commonDao.update(changingItemInfo);
			}
		}
	}
	
	public void addChangeRealPlan(Long mainId,ChangeRealContractBillandRecePlan changeRealPlan,Long partId){
		ChangingContractMainInfo contract = (ChangingContractMainInfo)commonDao.load(ChangingContractMainInfo.class, mainId);
		ChangingContractItemMaininfo itemMainInfo = null;
		ChangingContractItemStage itemStage = null;
		String itemStageHql = "select sp.ccontractItemStage from " +
				"ChangingContractItemStagePlan sp " +
				"where sp.id = ?";
		
		itemStage = (ChangingContractItemStage)commonDao.uniqueResult(itemStageHql, changeRealPlan.getConItemStage());
		
		ChangingContractMaininfoPart contractMainPart = (ChangingContractMaininfoPart)commonDao.load(ChangingContractMaininfoPart.class, partId);
		ChangeRealContractBillandRecePlan contractRealPlan = new ChangeRealContractBillandRecePlan();
		
		contractRealPlan.setRealConBillproSid(sequenceService.getNextValue(SequenceKey.changeRealConPlan));
		contractRealPlan.setConMainInfoSid(contract.getConMainInfoSid());//添加合同号
		
		contractRealPlan.setConItemStage(itemStage.getConIStageSid());//添加阶段id
		
		if(contract.getContractType().equals("1")){
			String itemMainHql = "select item from ChangingContractItemMaininfo item ," +
			"ChangingContractItemInfo info " +
			"where info.contractItemMaininfo = item.conItemMinfoSid " +
			"and info.conItemInfoSid = ?";
			itemMainInfo =(ChangingContractItemMaininfo)commonDao.uniqueResult(itemMainHql, changeRealPlan.getContractItemMaininfo());
			contractRealPlan.setContractItemMaininfo(itemMainInfo.getConItemMinfoSid());//添加项目id
		}
		contractRealPlan.setRealPredBillDate(changeRealPlan.getRealPredBillDate());
		contractRealPlan.setRealPredReceDate(changeRealPlan.getRealPredReceDate());
		contractRealPlan.setRealBillAmount(changeRealPlan.getRealBillAmount());
		contractRealPlan.setRealReceAmount(changeRealPlan.getRealReceAmount());
		contractRealPlan.setRealTaxReceAmount(contractRealPlan.getRealReceAmount());
		contractRealPlan.setBillNature(contractMainPart.getMoneytype());
		contractRealPlan.setBillType(contractMainPart.getTicketType());
		contractRealPlan.setBillContent(generateBillContent(mainId,contractRealPlan.getBillNature()).toString());
		if(contract.getStandard().equals("1")){
			contractRealPlan.setRealTaxBillAmount(changeRealPlan.getRealBillAmount());						
		}else{
			contractRealPlan.setRealTaxBillAmount(TaxChange.NoTaxToTaxBigDecimal(changeRealPlan.getRealBillAmount(), contractMainPart.getTicketType()));
		}
		commonDao.save(contractRealPlan);
		
	}
	
	public void addStageInfo(ChangingContractItemStagePlan citemStagePlan){
		
		ChangingContractItemStage stage = (ChangingContractItemStage) commonDao.uniqueResult("from ChangingContractItemStage where itemStageName = ? and contractMainSid = ? order by conIStageSid ", citemStagePlan.getCcontractItemStage().getItemStageName(),citemStagePlan.getContractMainSid());
		if(stage == null || isStageOfStagePlanDuplicate(citemStagePlan)){
			stage = new ChangingContractItemStage();
			////通过sequence获取系统id
			Long stageId = sequenceService.getNextValue(SequenceKey.changeItemStageInfo);
			stage.setConIStageSid(stageId);
			stage.setContractMainSid(citemStagePlan.getContractMainSid());
			stage.setItemStageName(citemStagePlan.getCcontractItemStage().getItemStageName());
			commonDao.save(stage);
		}
		////通过sequence获取系统id
		Long stagePlanId = sequenceService.getNextValue(SequenceKey.changeItemStagePlan);
		citemStagePlan.setId(stagePlanId);
		citemStagePlan.setCcontractItemStage(stage);
		commonDao.save(citemStagePlan);
	}
	
	public void addMainInfoPart(ChangingContractMaininfoPart mainPart){
		Long id = sequenceService.getNextValue(SequenceKey.changeMainInfoPart);
		mainPart.setId(id);
		commonDao.save(mainPart);
	}
	
	public Double accountnoTaxMoney(Long mainid){
		
		List<ChangingContractMaininfoPart> maininfopartlist = commonDao.list(
				"from ChangingContractMaininfoPart where contractmainid = ? ", mainid);
		Double notaxmoney = 0.0;
		if (maininfopartlist != null) {
			for (ChangingContractMaininfoPart contractMaininfoPart : maininfopartlist) {
				notaxmoney = notaxmoney
						+ TaxChange.TaxToNoTaxDouble(contractMaininfoPart
								.getMoney(), contractMaininfoPart
								.getTicketType());
			}
		}
		return notaxmoney;
	}
	
	public Double accountTaxMoney(Long mainid){
		List<ChangingContractMaininfoPart> maininfopartlist = commonDao.list(
				"from ChangingContractMaininfoPart where contractmainid = ? ", mainid);
		Double notaxmoney = 0.0;
		for (ChangingContractMaininfoPart contractMaininfoPart : maininfopartlist) {
			notaxmoney = notaxmoney + TaxChange.NoTaxToTaxDouble(contractMaininfoPart.getMoney(), contractMaininfoPart.getTicketType());
		}
		return notaxmoney;
	}
	
	private boolean isStageOfStagePlanDuplicate(
			ChangingContractItemStagePlan contractItemStagePlan) {
		Number planCount = (Number) commonDao
				.uniqueResult(
						"select count(*) from ChangingContractItemStagePlan where ccontractItemStage.itemStageName = ? and ccontractMaininfoPart.id = ?",
						contractItemStagePlan.getCcontractItemStage()
								.getItemStageName(), contractItemStagePlan
								.getCcontractMaininfoPart().getId());
		return planCount.intValue() > 0;
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
	
	public Integer confirmOkChangeMain(Long ids){
		ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new BooleanConverter(null), Boolean.class);
		bakContract(ids);
		changeContract(ids);
		deleteChangeContract(ids);
		deletePrimaryTable(ids);
		return returnIsSureSubmit(ids);
	}
	public int returnIsSureSubmit(Long mainid){
		int result = isSureSubmit(mainid);
		if(result != 0){
			throw new DefineException();
		}
		return result;
	}
	
	/**
	 * 正式合同变更以后，需要删除一些为0的数据。这个时候需要验证一下数据是否合理，因此做了这样一个验证。
	 * @param mainid
	 * @return
	 */
	public int isSureSubmit(Long mainid) {
		commonDao.flushSession();
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
	    String sqlPlanReveSql = "select count(*) "+
	    						"from (select p.fk_con_main_sid,"+
				                "p.fk_bill_nature,"+
				               	"(select sum(p2.real_tax_bill_amount) "+
				                "from yx_real_con_bc_plan p2 "+
				                "where p2.fk_bill_type <> '4' "+
				                "and p2.fk_con_main_sid = p.fk_con_main_sid "+
				                "and p2.fk_bill_nature = p.fk_bill_nature) as tax_bill_amount,"+
				               	"sum(p.real_rece_amount) as tax_rece_amount "+
				          		"from yx_real_con_bc_plan p "+
				         		"group by p.fk_con_main_sid, p.fk_bill_nature) rb "+
				 				"where rb.tax_rece_amount <> rb.tax_bill_amount "+
				   				"and rb.fk_con_main_sid = ?";
	   
	    Number countId = (Number) commonDao.uniqueSQLResult(sqlPlanReveSql,mainid);
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
	    List<RealContractBillandRecePlan> planlist = commonDao
		.list("from RealContractBillandRecePlan where conMainInfoSid=? order by realPredReceDate,conItemStage,contractItemMaininfo",
				mainid);
		BigDecimal allreveMoney=BigDecimal.ZERO;
		//计划中无收款日期的不能通过
		if(planlist.size()!=0){
			for (RealContractBillandRecePlan realPlan : planlist) {
				if(realPlan.getRealPredBillDate()==null||realPlan.getRealPredReceDate()==null||realPlan.getConItemStage()==null){
					return 5;  //计划开票日期或者计划收款日期为空
				}
					allreveMoney=allreveMoney.add(realPlan.getRealReceAmount());	
			}	
				if(BigDecimalUtils.toBigDecial(cmi.getConTaxTamount()).compareTo(allreveMoney)!=0){
			    	return 6;//计划中收款金额不等于合同含税总金额
			    }
		}
	
		
		BigDecimal allPlanMoney = BigDecimal.ZERO ;
		if(planlist.size()!=0){
		    for(RealContractBillandRecePlan icb:planlist){
		    	if(!icb.getBillType().equals("4")){
		    		allPlanMoney=allPlanMoney.add(icb.getRealBillAmount());
		    	}
		    }
		    if(BigDecimalUtils.toBigDecial(contractMoney).compareTo(allPlanMoney)!=0){
		    	return 7;//计划中开票金额和合同金额不等
		    }
		}else{
			return 8;//计划为生成
		}
		
		
	  /// 判断合同下面的项目金额等于项目下计划的开票金额
		 if("1".equals(cmi.getContractType())){
			 /// 项目类判断项目金额等于项目下计划开票金额
			String itemAndRealPlanHql = "select count(*) from (select ii.con_item_amount_with_tax as itemAmount,"+
					"(select sum(p.real_bill_amount) "+
					"from yx_real_con_bc_plan p "+
					"where p.fk_con_main_sid = im.fk_con_main_info_sid "+
					"and p.fk_con_item_minfo_sid = im.con_item_minfo_sid "+
					"and p.fk_bill_nature = pt.money_type "+
					"and p.fk_bill_type <> '4') as itemAmountInPlan, "+
					"im.item_res_dept, "+
					"pt.money_type,im.fk_con_main_info_sid "+
					"from yx_con_item_info ii, yx_con_item_minfo im, yx_con_maininfo_part pt "+ 
					"where ii.fk_con_item_minfo_sid = im.con_item_minfo_sid "+
					"and ii.item_maininfopart_id = pt.id "+
					"and ii.con_item_amount_with_tax <> "+
					"(select sum(p.real_bill_amount) "+
					"from yx_real_con_bc_plan p "+
					"where p.fk_con_main_sid = im.fk_con_main_info_sid "+
					"and p.fk_con_item_minfo_sid = im.con_item_minfo_sid "+
					"and p.fk_bill_nature = pt.money_type "+
					"and p.fk_bill_type <> '4') " +
					"and im.fk_con_main_info_sid = ? )";
			 Number itemCount = (Number) commonDao.uniqueSQLResult(itemAndRealPlanHql, mainid);
			 if(itemCount.longValue()!=0){
				 return 10;
			 }
		 }else if("2".equals(cmi.getContractType())){
			 String itemAndRealPlanHql = "select count(*) from (select info.con_tax_tamount as conAmount,"+
			 "(select sum(p.real_tax_bill_amount) "+
			 "from yx_real_con_bc_plan p "+
			 "where p.fk_con_main_sid = info.con_main_info_sid "+
			 "and p.fk_bill_nature = pt.money_type "+
			 "and p.fk_bill_type <> '4') as conAmountInPlan, "+
			 "info.main_item_department, "+
			 "pt.money_type,info.con_main_info_sid "+
			 "from yx_con_main_info info, yx_con_maininfo_part pt "+
			 "where info.con_main_info_sid = pt.contract_main_id "+
			 "and info.con_tax_tamount <> "+
			 "(select sum(p.real_tax_bill_amount) "+
			 "from yx_real_con_bc_plan p "+
			 "where p.fk_con_main_sid = info.con_main_info_sid "+
			 "and p.fk_bill_nature = pt.money_type "+
			 "and p.fk_bill_type <> '4') " +
			 "and info.con_main_info_sid = ? )";
			 Number itemCount = (Number) commonDao.uniqueSQLResult(itemAndRealPlanHql, mainid);
			 if(itemCount.longValue()!=0){
				 return 11;
			 }
		 }
		 
		 /////判断阶段金额等于阶段下计划的开票金额
		 String stageAndRealPlanHql = "select count(*) from (select cisp.stage_amout as stageAmount, " +
        		    "(select sum(p.real_bill_amount) " +
        		    "from yx_real_con_bc_plan p " +
        		    "where p.fk_con_main_sid = cisp.con_main_info_sid " +
        		    "and p.fk_con_i_stage_sid = cis.con_item_stage_sid " +
        		    "and p.fk_bill_nature = pt.money_type) as stageAmountInPlan, " +
        		    "cis.item_stage_name, " +
        		    "pt.money_type,pt.id  " +
        		    "from yx_con_item_stage      cis, " +
        		    "yx_con_item_stage_plan cisp, "+
        		    "yx_con_maininfo_part   pt " +
        		    "where cisp.contract_item_stage_id = cis.con_item_stage_sid " +
        		    "and cisp.contract_maininfo_part_id = pt.id  " +
        		    "and cisp.stage_amout <> " +
        		    "(select sum(p.real_bill_amount) " +
        		    "from yx_real_con_bc_plan p " +
        		    "where p.fk_con_main_sid = cisp.con_main_info_sid " +
        		    "and p.fk_con_i_stage_sid = cis.con_item_stage_sid " +
        		    "and p.fk_bill_nature = pt.money_type " +
        		    "and p.fk_bill_type <> '4') " +
        		    "and cisp.con_main_info_sid = ? )";
		 Number StageCount = (Number) commonDao.uniqueSQLResult(stageAndRealPlanHql, mainid);
		 if(StageCount.longValue()!=0){
			 return 12;
		 }
		
		return 0;
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
				log.info(e);
			} catch (InvocationTargetException e) {
				log.info(e);
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
					log.info(e);
				} catch (InvocationTargetException e) {
					log.info(e);
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
				log.info(e);
			} catch (InvocationTargetException e) {
				log.info(e);
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
					log.info(e);
				} catch (InvocationTargetException e) {
					log.info(e);
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
				log.info(e);
			} catch (InvocationTargetException e) {
				log.info(e);
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
			log.info(e);
		} catch (InvocationTargetException e) {
			log.info(e);
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
	/**
	 * 把变更表中的有关数据拷贝到实际表中
	 * @param changContractId
	 */
	@SuppressWarnings("unused")
	private void changeContract(Long changContractId){
		ChangingContractMainInfo chageMain = (ChangingContractMainInfo)commonDao.load(ChangingContractMainInfo.class, changContractId);
		ContractMainInfo mainInfo = (ContractMainInfo) commonDao.load(ContractMainInfo.class, chageMain.getConMainInfoSid());

		////合同费用组成的map。里面保存着变更表中的id和实际表中的id
		Map<Long, Long> partMap = new HashMap<Long, Long>();
		//修改合同费用组成并删除change表中的信息
		List<ChangingContractMaininfoPart>cpart=commonDao.list("from ChangingContractMaininfoPart c where c.contractmainid = "+chageMain.getConMainInfoSid());
		for (ChangingContractMaininfoPart part : cpart) {
			String partHql = "from ContractMaininfoPart where id = ?";
			ContractMaininfoPart newpartn=(ContractMaininfoPart) commonDao.uniqueResult(partHql, part.getId());
			if(newpartn == null){
				newpartn = new ContractMaininfoPart();
				try {
					BeanUtils.copyProperties(newpartn, part);
				} catch (IllegalAccessException e) {
					log.info(e);
				} catch (InvocationTargetException e) {
					log.info(e);
				}
				commonDao.save(newpartn);
			}else{
				newpartn.setMoney(part.getMoney());//修改合同费用组成金额
				commonDao.update(newpartn);
			}
			partMap.put(part.getId(), newpartn.getId());
		}
		Map<Long, Long> itemMainInfoMap = new HashMap<Long, Long>();
		Map<Long,Long> itemInfoMap = new HashMap<Long, Long>();
		//修改项目费用组成并删除change表中的信息
		List<ChangingContractItemMaininfo> itemMainInfoList=new ArrayList<ChangingContractItemMaininfo>();
		itemMainInfoList = commonDao
					.list("from ChangingContractItemMaininfo item where item.contractMainInfo = ?",chageMain.getConMainInfoSid());
		for (ChangingContractItemMaininfo citemMain : itemMainInfoList) {
			String mainHql = "from ContractItemMaininfo item where item.conItemMinfoSid = ?";
			ContractItemMaininfo itemMainInfo=(ContractItemMaininfo)commonDao.uniqueResult(mainHql, citemMain.getConItemMinfoSid());
			List<ChangingContractItemInfo> itemInfoList = new ArrayList<ChangingContractItemInfo>();
			if(itemMainInfo==null){
				try {
					itemMainInfo = new ContractItemMaininfo();
					BeanUtils.copyProperties(itemMainInfo, citemMain);
				} catch (IllegalAccessException e) {
					log.info(e);
				} catch (InvocationTargetException e) {
					log.info(e);
				}
				commonDao.save(itemMainInfo);
				
			}
			itemMainInfoMap.put(citemMain.getConItemMinfoSid(), itemMainInfo.getConItemMinfoSid());
			itemInfoList = commonDao
						.list("from ChangingContractItemInfo item where item.contractItemMaininfo = ? ", citemMain.getConItemMinfoSid());
			for(ChangingContractItemInfo changeItemInfo : itemInfoList){
				ContractItemInfo itemInfo = (ContractItemInfo)commonDao.uniqueResult("from ContractItemInfo where  conItemInfoSid = ?", changeItemInfo.getConItemInfoSid());
				if(itemInfo ==null){
					try {
						itemInfo = new ContractItemInfo();
						BeanUtils.copyProperties(itemInfo, changeItemInfo);
						itemInfo.setContractItemMaininfo(itemMainInfoMap.get(changeItemInfo.getContractItemMaininfo()));
						itemInfo.setMainInfoPartId(partMap.get(changeItemInfo.getMainInfoPartId()));
					} catch (IllegalAccessException e) {
						log.info(e);
					} catch (InvocationTargetException e) {
						log.info(e);
					}
					commonDao.save(itemInfo);
				}else{
					itemInfo.setConItemAmountWithTax(changeItemInfo.getConItemAmountWithTax());
					itemInfo.setConItemAmountWithNoTax(changeItemInfo.getConItemAmountWithNoTax());
					commonDao.update(itemInfo);
				}
				itemInfoMap.put(changeItemInfo.getConItemInfoSid(), itemInfo.getConItemInfoSid());
			}
		}
		Map<Long, Long> itemStageMap = new HashMap<Long, Long>();
		Map<Long,Long> itemStagePlanMap = new HashMap<Long, Long>();
		//修改阶段和阶段计划并删除阶段和阶段计划
		List<ChangingContractItemStage> itemStageList=new ArrayList<ChangingContractItemStage>();
		itemStageList = commonDao
					.list("from ChangingContractItemStage stage where stage.contractMainSid = ?", chageMain.getConMainInfoSid());
		for (ChangingContractItemStage citemStage : itemStageList) {
			String stageHql = "from ContractItemStage where conIStageSid = ?";
			ContractItemStage itemStage = (ContractItemStage)commonDao.uniqueResult(stageHql, citemStage.getConIStageSid());
			
			if(itemStage == null){
				itemStage = new ContractItemStage();
				try {
					BeanUtils.copyProperties(itemStage, citemStage);
				} catch (IllegalAccessException e) {
					log.error(e);
				} catch (InvocationTargetException e) {
					log.error(e);
				}
				commonDao.save(itemStage);
			}
			itemStageMap.put(citemStage.getConIStageSid(), itemStage.getConIStageSid());	
			List<ChangingContractItemStagePlan> itemStagePlanList = new ArrayList<ChangingContractItemStagePlan>();
			itemStagePlanList = commonDao
					.list("from ChangingContractItemStagePlan sp where sp.ccontractItemStage = ? ", citemStage);
			for (ChangingContractItemStagePlan cstagePlan : itemStagePlanList) {
				String stagePlanHql = "from ContractItemStagePlan where id = ?";
				ContractItemStagePlan itemStagePlan = (ContractItemStagePlan)commonDao.uniqueResult(stagePlanHql, cstagePlan.getId());
				if(itemStagePlan == null){
					itemStagePlan = new ContractItemStagePlan();
					try {
						BeanUtils.copyProperties(itemStagePlan, cstagePlan);
					} catch (IllegalAccessException e) {
						log.error(e);
					} catch (InvocationTargetException e) {
						log.error(e);
					}
					itemStagePlan.setContractItemStage(itemStage);
					itemStagePlan.setContractMaininfoPart((ContractMaininfoPart)commonDao.load(ContractMaininfoPart.class, partMap.get(cstagePlan.getCcontractMaininfoPart().getId())));
					commonDao.save(itemStagePlan);
				}else{
					itemStagePlan.setStageAmout(cstagePlan.getStageAmout());
					itemStagePlan.setReveAmount(cstagePlan.getReveAmount());
					itemStagePlan.setContractItemStage(itemStage);
					itemStagePlan.setContractMaininfoPart((ContractMaininfoPart)commonDao.load(ContractMaininfoPart.class, partMap.get(cstagePlan.getCcontractMaininfoPart().getId())));
					commonDao.update(itemStagePlan);
				}
				itemStagePlanMap.put(cstagePlan.getId(), itemStagePlan.getId());
				
			}
		}
		//修改实际开票收款计划,并删除change表中的信息
		List<ChangeRealContractBillandRecePlan> cplan=commonDao.list("from ChangeRealContractBillandRecePlan c where c.conMainInfoSid = "+chageMain.getConMainInfoSid());
		for (ChangeRealContractBillandRecePlan plan : cplan) {
			String planHql = "from RealContractBillandRecePlan plan where plan.realConBillproSid = ?";
			RealContractBillandRecePlan newplan= (RealContractBillandRecePlan) commonDao.uniqueResult(planHql, plan.getRealConBillproSid());
			if(newplan!=null){
				newplan.setContractItemMaininfo(itemMainInfoMap.get(newplan.getContractItemMaininfo()));
				newplan.setConItemStage(itemStageMap.get(newplan.getConItemStage()));
				newplan.setRealBillAmount(plan.getRealBillAmount());//修改预计开票金额（基准）
				newplan.setRealReceAmount(plan.getRealReceAmount());//修改预计收款金额（基准）
				newplan.setRealTaxBillAmount(plan.getRealTaxBillAmount());//修改预计开票金额（含税）
				newplan.setRealTaxReceAmount(plan.getRealTaxReceAmount());//修改预计收款金额（含税）
				commonDao.update(newplan);
			}else{
				RealContractBillandRecePlan newPlan2= new RealContractBillandRecePlan();
				try {
					BeanUtils.copyProperties(newPlan2, plan);
					newPlan2.setContractItemMaininfo(itemMainInfoMap.get(newPlan2.getContractItemMaininfo()));
					newPlan2.setConItemStage(itemStageMap.get(newPlan2.getConItemStage()));
					commonDao.save(newPlan2);
				} catch (IllegalAccessException e) {
					log.info(e);
				} catch (InvocationTargetException e) {
					log.info(e);
				}
			}
		}
		
		long changeCount = 1L;
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
	
	public void delPlanByStageId(Long stageId,String moneyType){
		String hql = "from RealContractBillandRecePlan plan where plan.conItemStage = ? and plan.billNature = ?";
		List<RealContractBillandRecePlan> planList = commonDao.list(hql, stageId,moneyType);
		for (RealContractBillandRecePlan realContractBillandRecePlan : planList) {			
			deleteRealPlanByRealPlan(realContractBillandRecePlan);
		}
	}

	public void deletePlanByItemId(Long itemId,String moneyType){
		String hql = "from RealContractBillandRecePlan plan where plan.contractItemMaininfo = ? and plan.billNature = ?";
		List<RealContractBillandRecePlan> planList = commonDao.list(hql, itemId,moneyType);
		for (RealContractBillandRecePlan realContractBillandRecePlan : planList) {			
			deleteRealPlanByRealPlan(realContractBillandRecePlan);
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
	
	
	
	
	
	public void confirmNoChangeMain(Long[] ids){
		List<ChangingContractMainInfo> changeMains = null;
		changeMains = commonDao
		.list("from ChangingContractMainInfo c where c.conMainInfoSid in "
				+ idIn(ids));
		for(ChangingContractMainInfo chang:changeMains){
			chang.setChangeContractState("2");
			commonDao.update(chang);
	    }
		
	}
	

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

	public ISequenceService getSequenceService() {
		return sequenceService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
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
	public void AddEventInfo(ChangingContractItemMaininfo contractItemMaininfo,ChangingContractItemInfo contractItemInfo){
		// TODO Auto-generated method stub
		
		ChangingContractMaininfoPart inforPart = (ChangingContractMaininfoPart) commonDao.load(
				ChangingContractMaininfoPart.class, contractItemInfo.getMainInfoPartId());
		ChangingContractMainInfo contract = (ChangingContractMainInfo)commonDao.load(ChangingContractMainInfo.class, inforPart.getContractmainid());
		Long mainId = contract.getConMainInfoSid();
		if("2".equals(contract.getStandard())){///如果不含税。项目组成的不含税金额等于基准金额
			contractItemInfo.setConItemAmountWithNoTax(contractItemInfo.getConItemAmountWithTax());
		}else if("1".equals(contract.getStandard())){////如果含税，项目组成的不含税金额等于基准金额*票据类型
			contractItemInfo.setConItemAmountWithNoTax(TaxChange.TaxToNoTaxBigDecimal(contractItemInfo.getConItemAmountWithTax(), inforPart.getTicketType()));
		}
		
		// 在添加的时候判断如果项目选择的负责部门在同一个合同号下面存在的话就不用重新添加项目了。只添加项目费用信息。此时需要把项目id从数据库中查出来
		List list = commonDao
				.list(
						"from ChangingContractItemMaininfo mainInfo where mainInfo.contractMainInfo=? and mainInfo.itemResDept=?",
						contractItemMaininfo.getContractMainInfo(), contractItemMaininfo
								.getItemResDept());
		if (list == null || list.size() == 0) {
			Long itemMainId = sequenceService.getNextValue(SequenceKey.changeItemMainInfo);
			contractItemMaininfo.setConItemMinfoSid(itemMainId);
			commonDao.save(contractItemMaininfo);
			contractItemInfo.setContractItemMaininfo(contractItemMaininfo.getConItemMinfoSid());
		} else {
			ChangingContractItemMaininfo mainInfo = (ChangingContractItemMaininfo) list.get(0);
			mainInfo.setItemResDeptP(contractItemMaininfo.getItemResDeptP());
			commonDao.update(mainInfo);
			////
			contractItemInfo.setContractItemMaininfo(mainInfo.getConItemMinfoSid());
		}
		Long itemInfoId = sequenceService.getNextValue(SequenceKey.changeItemInfo);
		contractItemInfo.setConItemInfoSid(itemInfoId);
		contractItemInfo.setItemcontrent(inforPart.getMoneytype());
		commonDao.save(contractItemInfo);
		/**
		 *添加项目的时候自动生成计划暂时没有做，等到了后面再重新考虑！
		 */
		
		
		contractCommonService.addChargeManAndDepartment(contractItemMaininfo.getItemResDept(), contractItemMaininfo.getItemResDeptP());
		
		
		
	}
	
	

}
