package com.baoz.yx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.YXTypeManage;
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
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.service.IFinalToCloseService;
import com.baoz.yx.service.ISequenceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.TaxChange;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.utils.SequenceKey;
import com.baoz.yx.vo.MyBean;

@Service("finalToCloseService")
@Transactional
public class FinalToCloseService implements IFinalToCloseService {

//	@Autowired
//	@Qualifier("commonDao")
//	private ICommonDao commonDao;
	
	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao commonDao;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	@Autowired
	@Qualifier("sequenceService")
	private ISequenceService sequenceService;
	


	public List<ChangingContractMaininfoPart> getMainInfoPartByConId(Long conId) {
		List<ChangingContractMaininfoPart> list = new ArrayList<ChangingContractMaininfoPart>();
		List<ChangingContractMaininfoPart> tempList = new ArrayList<ChangingContractMaininfoPart>();
		List<ChangingContractItemInfo> tempItemInfoList = new ArrayList<ChangingContractItemInfo>();

		tempList = commonDao.list(
				"from ChangingContractMaininfoPart part where part.contractmainid=?",
				conId);
		if (tempList != null && tempList.size() > 0) {
			for (ChangingContractMaininfoPart contractMaininfoPart : tempList) {
				List<ChangingContractItemInfo> itemInfoList = new ArrayList<ChangingContractItemInfo>();
				tempItemInfoList = commonDao
						.list(
								"from ChangingContractItemInfo info where info.mainInfoPartId=?",
								contractMaininfoPart.getId());
				for (ChangingContractItemInfo contractItemInfo : tempItemInfoList) {
					ChangingContractItemMaininfo itemMainInfo = (ChangingContractItemMaininfo) commonDao
							.load(ChangingContractItemMaininfo.class, contractItemInfo
									.getContractItemMaininfo());
					contractItemInfo.setItemMainInfo(itemMainInfo);
					itemInfoList.add(contractItemInfo);
				}
				contractMaininfoPart.setItemInfoList(itemInfoList);
				list.add(contractMaininfoPart);
			}
		}
		return list;
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
	
	public List<Object[]> findMainMoneyWithPlanAmountList(Long mainid) {
		List<Object[]> list = commonDao
				.list(
						"select p ," +
						"(select sum(s.stageAmout) from ChangingContractItemStagePlan s where s.ticketType <> '4' and s.ccontractMaininfoPart = p )," +
						"(select sum(s.stageAmout) from ChangingContractItemStagePlan s where s.ticketType = '4'  and s.ccontractMaininfoPart = p ) " +
						"from ChangingContractMaininfoPart p where p.contractmainid=? ",
						mainid);
		return list;
	}
	
	public List<ChangingContractItemStagePlan> findItemStagePlanByMainInfoId(
			Long conMainInfoSid) {
		List<ChangingContractItemStagePlan> list = commonDao
				.list(
						"from ChangingContractItemStagePlan where contractMainSid=? order by makeInvoiceDate , id ",
						conMainInfoSid);
		return list;
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
	
	public Map<Long, ChangingContractItemInfo> getItemInfoMapByConId(Long mainid){
		String hql="select itemInfo from ChangingContractItemInfo itemInfo,ChangingContractMaininfoPart part " +
				"where itemInfo.mainInfoPartId = part.id and part.contractmainid = ?";
		List<ChangingContractItemInfo> list=commonDao.list(hql, mainid);
		Map<Long, ChangingContractItemInfo> map=new HashMap<Long, ChangingContractItemInfo>();
		for (ChangingContractItemInfo changingContractItemInfo : list) {
			map.put(changingContractItemInfo.getConItemInfoSid(), changingContractItemInfo);
		}
		return map;
	}
	
	public Map<Long, ChangingContractItemStagePlan> getItemStagePlanMapByConId(Long mainid){
		String hql="select stagePlan from ChangingContractItemStagePlan stagePlan where stagePlan.contractMainSid = ?";
		List<ChangingContractItemStagePlan> list=commonDao.list(hql, mainid);
		Map<Long, ChangingContractItemStagePlan> map=new HashMap<Long, ChangingContractItemStagePlan>();
		for (ChangingContractItemStagePlan changingContractItemStagePlan : list) {
			map.put(changingContractItemStagePlan.getId(), changingContractItemStagePlan);
		}
		return map;
	}
	public List<ChangeRealContractBillandRecePlan> getChangePlanNotExistsReal(Long mainid){
		String hql="from ChangeRealContractBillandRecePlan crp " +
				"where crp.realConBillproSid not in(select rp.realConBillproSid from RealContractBillandRecePlan rp " +
				"where rp.conMainInfoSid = crp.conMainInfoSid) and crp.conMainInfoSid = ?";
		List<ChangeRealContractBillandRecePlan> list = commonDao.list(hql, mainid);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<ChangingContractItemInfo> getchangeItemInfoNotExistsReal(Long mainid){
		
		String hql = "select item,info from ChangingContractItemInfo item,ChangingContractItemMaininfo info where" +
				" info.conItemMinfoSid = item.contractItemMaininfo  and info.contractMainInfo = ? and " +
				" item.conItemInfoSid not in(select it.conItemInfoSid from ContractItemInfo it,ContractItemMaininfo cin " +
				" where cin.conItemMinfoSid = it.contractItemMaininfo and cin.contractMainInfo = info.contractMainInfo ) ";
		List<Object[]> resultList = commonDao.list(hql, mainid);
		List<ChangingContractItemInfo> result =new ArrayList<ChangingContractItemInfo>();
		for (Object[] objects : resultList) {
			ChangingContractItemInfo itemInfo = (ChangingContractItemInfo)objects[0];
			ChangingContractItemMaininfo item = (ChangingContractItemMaininfo)objects[1];
			itemInfo.setItemMainInfo(item);
			result.add(itemInfo);
		}
		return result;
	}
	public List<ChangingContractMaininfoPart> findMainMoneyNotExistsReal(Long mainid){
		List<ChangingContractMaininfoPart> list = commonDao.list(
				"from ChangingContractMaininfoPart c where c.id not in (select m.id from ContractMaininfoPart m where m.contractmainid = c.contractmainid) and c.contractmainid=?  ", mainid);
		return list;
		
	}
	
	public List<ChangingContractMaininfoPart> getMainInfoPartNotExistsReal(Long mainid){
		List<ChangingContractMaininfoPart> list = new ArrayList<ChangingContractMaininfoPart>();
		List<ChangingContractMaininfoPart> tempList = new ArrayList<ChangingContractMaininfoPart>();
		List<ChangingContractItemInfo> tempItemInfoList = new ArrayList<ChangingContractItemInfo>();

		tempList = commonDao.list(
				"from ChangingContractMaininfoPart c where c.id not in (select m.id from ContractMaininfoPart m where m.contractmainid = c.contractmainid) and c.contractmainid=? ",
				mainid);
		if (tempList != null && tempList.size() > 0) {
			for (ChangingContractMaininfoPart contractMaininfoPart : tempList) {
				List<ChangingContractItemInfo> itemInfoList = new ArrayList<ChangingContractItemInfo>();
				tempItemInfoList = commonDao
						.list(
								"from ChangingContractItemInfo info where info.mainInfoPartId=?",
								contractMaininfoPart.getId());
				for (ChangingContractItemInfo contractItemInfo : tempItemInfoList) {
					ChangingContractItemMaininfo itemMainInfo = (ChangingContractItemMaininfo) commonDao
							.load(ChangingContractItemMaininfo.class, contractItemInfo
									.getContractItemMaininfo());
					contractItemInfo.setItemMainInfo(itemMainInfo);
					itemInfoList.add(contractItemInfo);
				}
				contractMaininfoPart.setItemInfoList(itemInfoList);
				list.add(contractMaininfoPart);
			}
		}
		return list;
		
	}
	
	public List<ChangingContractItemStagePlan> getChangeStagePlanNotExistsReal(Long mainid){
		String hql = "from ChangingContractItemStagePlan cplan where cplan.id not in " +
				"(select plan.id from ContractItemStagePlan plan where plan.contractMainSid = cplan.contractMainSid) and cplan.contractMainSid = ?";
		List<ChangingContractItemStagePlan> list = commonDao.list(hql, mainid);
		return list;
	}
	
	public int isSureSubmit(Long mainid) {
		//合同总金额
		ChangingContractMainInfo cmi =  (ChangingContractMainInfo)commonDao.load(ChangingContractMainInfo.class, mainid);
		Double contractMoney = Double.valueOf(0);
		if(cmi.getStandard().equals("1")){
			contractMoney = cmi.getConTaxTamount();
		}else{
			contractMoney = cmi.getConNoTaxTamount();
		}
		
		//比对费用组成
		String sqlOfMainInfoPart = " select sum(c.money) " +
				" from ChangingContractMaininfoPart c " +
				" where c.contractmainid = ?" ;
		Double allMainInfoPartMoney = (Double)commonDao.uniqueResult(sqlOfMainInfoPart,mainid);
		if(allMainInfoPartMoney==null||(contractMoney-allMainInfoPartMoney)!=0){
			return 1;//表示费用组成不相等
		} 

	    //比对阶段金额和合同总金额
	    String sqlOfStage = "select sum(cp.stageAmout) from ChangingContractItemStagePlan cp where ticketType!='4'  and  contractMainSid = ?";
	    Double allStageMoney =  (Double)commonDao.uniqueResult(sqlOfStage,mainid);
	    if(allStageMoney==null||(contractMoney-allStageMoney)!=0){
	    	return 2;//表示阶段总金额和合同金额不等
	    }
	    //比对合同项目部门（主合同部门必须出现在项目中）
	    if("1".equals(cmi.getContractType())){
		    String hasItemDepSql = " select count(*) from ChangingContractMainInfo c where exists "+
	                    " (select 1 from ChangingContractItemMaininfo item,ChangingContractItemInfo iteminfo "+
	                    " where item.contractMainInfo = c.conMainInfoSid and item.conItemMinfoSid = iteminfo.contractItemMaininfo " +
	                    " and iteminfo.conItemAmountWithTax >0 "+
	                    " and item.itemResDept = c.mainItemDept) and c.conMainInfoSid = " +mainid+ "";
		    Long hasItemDep = (Long) commonDao.uniqueResult(hasItemDepSql);
		    if(hasItemDep==0){
		    	return 4;//主负责部门必须在项目中出现 
		    }
	    }
	    
	    //对比计划中费用组成的收款金额和费用含税金额相等
	    String sqlPlanReveSql = "select count(*) "+
	    						"from (select p.fk_con_main_sid,"+
	    						"p.fk_bill_nature, "+
	    						"(select sum(p2.real_tax_bill_amount) "+
	    						"from yx_changing_real_con_bc_plan p2 "+
	    						"where p2.fk_bill_type <> '4' "+
	    						"and p2.fk_con_main_sid = p.fk_con_main_sid "+
	    						"and p2.fk_bill_nature = p.fk_bill_nature) as tax_bill_amount,"+
	    						"sum(p.real_rece_amount) as tax_rece_amount "+
	    						"from yx_changing_real_con_bc_plan p "+
	    						"group by p.fk_con_main_sid, p.fk_bill_nature ) rb "+
	    						"where rb.tax_rece_amount <> rb.tax_bill_amount "+
	    						"and rb.fk_con_main_sid = ?";
	   
	    Number countId = (Number) commonDao.uniqueSQLResult(sqlPlanReveSql,mainid);
	    if(countId.longValue()!=0){
	    	return 9;  //费用下收款金额和费用含税金额不等
	    }	    
	        
	    if("1".equals(cmi.getContractType())){
		    //比对项目金额和合同总金额
		    String sqlOfItem = " select sum(ci.conItemAmountWithTax) " +
		    		" from ChangingContractItemInfo ci,ChangingContractItemMaininfo cim " +
		    		" where ci.contractItemMaininfo = cim.conItemMinfoSid" +
		    		" and cim.contractMainInfo = "+mainid+" ";
		    BigDecimal allItemMoney = (BigDecimal)commonDao.uniqueResult(sqlOfItem);
			if(allItemMoney==null||(contractMoney-allItemMoney.doubleValue())!=0){
				return 3;//项目总金额和合同总金额不等
			}
	    }
		//比对计划金额和合同总金额
		List<ChangeRealContractBillandRecePlan> planlist = commonDao
		.list("from ChangeRealContractBillandRecePlan where conMainInfoSid=? order by realPredReceDate,conItemStage,contractItemMaininfo",
				mainid);
		BigDecimal allreveMoney=BigDecimal.ZERO;
		//计划中无收款日期的不能通过
		if(planlist.size()!=0){
			for (ChangeRealContractBillandRecePlan initContractBillpro : planlist) {
					allreveMoney=allreveMoney.add(initContractBillpro.getRealReceAmount());	
			}	
				if(BigDecimalUtils.toBigDecial(cmi.getConTaxTamount()).compareTo(allreveMoney)!=0){
			    	return 6;//计划中收款金额不等于合同含税总金额
			    }
		}
		
		BigDecimal allPlanMoney = BigDecimal.ZERO ;
		if(planlist.size()!=0){
		    for(ChangeRealContractBillandRecePlan icb:planlist){
		    	if(!icb.getBillType().equals("4")){
		    		allPlanMoney=allPlanMoney.add(icb.getRealBillAmount());
		    	}
		    }
		    if(BigDecimalUtils.toBigDecial(contractMoney).compareTo(allPlanMoney)!=0){
		    	return 7;//计划中开票金额和合同金额不等
		    }
		}else{
			return 8;//计划未生成
		}
		/// 判断合同下面的项目金额等于项目下计划的开票金额
		 if("1".equals(cmi.getContractType())){
			 /// 项目类判断项目金额等于项目下计划开票金额
			String itemAndRealPlanHql = "select count(*) from (select ii.con_item_amount_with_tax as itemAmount,"+
					"(select sum(p.real_bill_amount) "+
					"from yx_changing_real_con_bc_plan p "+
					"where p.fk_con_main_sid = im.fk_con_main_info_sid "+
					"and p.fk_con_item_minfo_sid = im.con_item_minfo_sid "+
					"and p.fk_bill_nature = pt.money_type "+
					"and p.fk_bill_type <> '4') as itemAmountInPlan, "+
					"im.item_res_dept, "+
					"pt.money_type,im.fk_con_main_info_sid "+
					"from yx_changing_con_item_info ii, yx_changing_con_item_minfo im, yx_changing_con_maininfo_part pt "+ 
					"where ii.fk_con_item_minfo_sid = im.con_item_minfo_sid "+
					"and ii.item_maininfopart_id = pt.id "+
					"and ii.con_item_amount_with_tax <> "+
					"(select sum(p.real_bill_amount) "+
					"from yx_changing_real_con_bc_plan p "+
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
			 "from yx_changing_real_con_bc_plan p "+
			 "where p.fk_con_main_sid = info.con_main_info_sid "+
			 "and p.fk_bill_nature = pt.money_type "+
			 "and p.fk_bill_type <> '4') as conAmountInPlan, "+
			 "info.main_item_department, "+
			 "pt.money_type,info.con_main_info_sid "+
			 "from yx_changing_con_main_info info, yx_changing_con_maininfo_part pt "+
			 "where info.con_main_info_sid = pt.contract_main_id "+
			 "and info.con_tax_tamount <> "+
			 "(select sum(p.real_tax_bill_amount) "+
			 "from yx_changing_real_con_bc_plan p "+
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
         		    "from yx_changing_real_con_bc_plan p " +
         		    "where p.fk_con_main_sid = cisp.con_main_info_sid " +
         		    "and p.fk_con_i_stage_sid = cis.con_item_stage_sid " +
         		    "and p.fk_bill_nature = pt.money_type) as stageAmountInPlan, " +
         		    "cis.item_stage_name, " +
         		    "pt.money_type,pt.id  " +
         		    "from yx_changing_con_item_stage      cis, " +
         		    "yx_changing_con_stage_plan cisp, "+
         		    "yx_changing_con_maininfo_part   pt " +
         		    "where cisp.contract_item_stage_id = cis.con_item_stage_sid " +
         		    "and cisp.contract_maininfo_part_id = pt.id  " +
         		    "and cisp.stage_amout <> " +
         		    "(select sum(p.real_bill_amount) " +
         		    "from yx_changing_real_con_bc_plan p " +
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
	
	@SuppressWarnings("unchecked")
	public List<MyBean> getItemByConAndPart(Long mainId,Long partId){
		ChangingContractMainInfo mainInfo = (ChangingContractMainInfo)commonDao.load(ChangingContractMainInfo.class, mainId);
		List<MyBean> myList = new ArrayList<MyBean>();
		if(mainInfo.getContractType().equals("2")){
			YXTypeManage  tm = typeManageService.getYXTypeManage(1018L, mainInfo.getMainItemDept());
			MyBean myBean = new MyBean();
			myBean.setKey(tm.getId());
			myBean.setValue(tm.getTypeName());
			myList.add(myBean);
			return myList;
		}
		String sql = "select tm.type_name,info.con_item_info_sid from yx_changing_con_item_info info,"+
            "yx_changing_con_item_minfo item,"+
            "yx_changing_con_maininfo_part part,"+
            "yx_changing_con_main_info minfo,"+
            "yx_type_manage tm "+
        	"where part.contract_main_id = minfo.con_main_info_sid "+
            "and info.item_maininfopart_id = part.id "+
            "and info.fk_con_item_minfo_sid = item.con_item_minfo_sid "+
            "and item.item_res_dept = tm.type_small and tm.type_big = 1018 "+
            "and minfo.con_main_info_sid = ? "+
            "and part.id = ?";
		List<Object[]> list = commonDao.listSQL(sql, mainId,partId);
	
		for(int i = 0;i<list.size(); i++){
			MyBean myBean = new MyBean();
			Object[] obj =list.get(i);
			String value = (String)obj[0];
			Long key =((BigDecimal)obj[1]).longValue();
			myBean.setKey(key);
			myBean.setValue(value);
			myList.add(myBean);
		}
		return myList;
	}
	
	@SuppressWarnings("unchecked")
	public List<MyBean> getItemStageByConAndPart(Long mainId,Long partId)
	{
		String  sql = "select tm.type_name,sp.stage_remark,sp.id from yx_changing_con_item_stage    stage," +
            "yx_changing_con_stage_plan    sp," +
            "yx_changing_con_main_info     minfo," +
            "yx_changing_con_maininfo_part part," +
            "yx_type_manage                tm " +
            "where part.contract_main_id = minfo.con_main_info_sid " +
            "and sp.contract_maininfo_part_id =part.id " +
            "and sp.contract_item_stage_id = stage.con_item_stage_sid " +
            "and stage.item_stage_name = tm.type_small and tm.type_big = 1023 " +
            "and minfo.con_main_info_sid=? " +
            "and part.id = ?";
		List<Object[]> list = commonDao.listSQL(sql, mainId,partId);
		List<MyBean> myList = new ArrayList<MyBean>();
		for(int i = 0;i< list.size(); i++){
			MyBean myBean = new MyBean();
			Object[] obj = list.get(i);
			String typeName = (String)obj[0];
			String remark = (String)obj[1];
			String value = typeName;
			Long key = ((BigDecimal)obj[2]).longValue();
			if(!StringUtils.isBlank(remark)){
				value += "("+remark+")";
			}
			myBean.setKey(key);
			myBean.setValue(value);
			myList.add(myBean);
		}
		
		return myList;
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
	
	public String findChangeDepName(Long itemid) {
		ChangingContractItemMaininfo itemMaininfo = (ChangingContractItemMaininfo) commonDao
				.load(ChangingContractItemMaininfo.class, itemid);
		YXTypeManage typemanage = typeManageService.getYXTypeManage(1018L,
				itemMaininfo.getItemResDept());
		return typemanage.getTypeName();
	}

	public String findChangeStageName(Long stageid) {
		ChangingContractItemStage itemstage = (ChangingContractItemStage) commonDao.load(
				ChangingContractItemStage.class, stageid);
		YXTypeManage typemanage = typeManageService.getYXTypeManage(1023L,
				itemstage.getItemStageName());
		return typemanage.getTypeName();
	}

	public String findChangeDepNameByMainid(Long mainid) {
		ChangingContractMainInfo maininfo = (ChangingContractMainInfo) commonDao.load(
				ChangingContractMainInfo.class, mainid);
		YXTypeManage typemanage = typeManageService.getYXTypeManage(1018L,
				maininfo.getMainItemDept());
		return typemanage.getTypeName();
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
	
	public IYXCommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(IYXCommonDao commonDao) {
		this.commonDao = commonDao;
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

}
