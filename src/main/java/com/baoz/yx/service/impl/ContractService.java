package com.baoz.yx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.service.ICommonService;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.SelfProduction;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractItemStagePlan;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IEventInfoService;
import com.baoz.yx.service.INoticeService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.MyBean;



@Service("contractService")
@Transactional
public class ContractService implements IContractService {

	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao  commonDao;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService	service;
	
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	@Autowired
	@Qualifier("noticeService")
	private INoticeService noticeservice;
	
	
	public void SaveContract(List list) {
		

	}
	


	//按系统号获取合同主体信息
	public ContractMainInfo loadContractMainInfo(long conID) {
		Object obj = commonDao.load(ContractMainInfo.class, conID);
		ContractMainInfo ctemp = null;
		if(obj!=null){
			try{
				ctemp = (ContractMainInfo)obj;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return ctemp;
	}

	//保存合同主题信息，并创建合同项目和合同阶段对象
	public ContractMainInfo saveContractMainInfo(ContractMainInfo c) {
	    c.setSaleMan(UserUtils.getUser().getId());
	    c.setOpPeople(UserUtils.getUser().getId());
	    c.setOpTime(new Date());
        c.setConModInfo(false);
        c.setConState(0L);
        c.setConReceState(0L);
        c.setConBillState(0L);
		commonDao.saveOrUpdate(c);
		setMainInfoState(c);
		System.out.println("*****完毕******");	
		return c;
	}
	
	//查询项目客户
	
	public List<YXClientCode> findEventClient(Long id){
		List<YXOEmployeeClient> lista=commonDao.list("from YXOEmployeeClient where exp.id=?", id);
		List<YXClientCode> yxcc=new ArrayList();
		if(lista.size()!=0){
		  for(int i=0;i<lista.size();i++){
			  YXClientCode cc=(YXClientCode) commonDao.uniqueResult("from YXClientCode where isEventUnit='1' and is_active='1' and id=?",lista.get(i).getCli().getId());
			  if(cc!=null){
			   yxcc.add(cc);
			  }
		  }
		}
		System.out.println("项目客户查询成功");
		return yxcc;
				
	}


   //查询开票客户
	public List<YXClientCode> findAllClient(Long id) {
		List<YXOEmployeeClient> lista=commonDao.list("from YXOEmployeeClient where exp.id=?", id);
		List<YXClientCode> yxcc=new ArrayList();
		if(lista.size()!=0){
		  for(int i=0;i<lista.size();i++){
			  YXClientCode cc=(YXClientCode) commonDao.uniqueResult("from YXClientCode where  is_active='1' and id=?",lista.get(i).getCli().getId());
			  if(cc!=null){
			   yxcc.add(cc);
			  }
		  }
		}
		System.out.println("开票客户查询成功");
		return yxcc;
	}
	
	
    public List<YXLinkMan> findlinkMan(Long id){
        List<YXLinkMan> list=commonDao.list("from YXLinkMan where is_active=1 and clientId=? ",id);
        if(list==null){
        	return new ArrayList();
        }
    	return list;
    }


	public List<ContractItemMaininfo> findEventByMainInfoId(Long conMainInfoSid) {
		List<ContractItemMaininfo> list=commonDao.list("from ContractItemMaininfo where contractMainInfo=?",conMainInfoSid );
		// 初始化延迟加载
		// 可以把ContractItemMaininfo对象循环初始化
		for (ContractItemMaininfo contractItemMaininfo : list) {
			contractItemMaininfo.getContractItemInfolist().size();
		}
		return list;
	}
	
	public List<ContractItemStage> findItemStageByMainInfoId(Long conMainInfoSid){
		List list=commonDao.list("from ContractItemStage where contractMainSid=?",conMainInfoSid );
		return list;
	}


	public List<ContractItemStagePlan> findItemStagePlanByMainInfoId(
			Long conMainInfoSid) {
		List list=commonDao.list("from ContractItemStagePlan where contractMainSid=? order by id ",conMainInfoSid );
		return list;
	}



	public void saveContractStage(List<ContractItemStage> contractItemStageList) {
        for(int i=0;i<contractItemStageList.size();i++){
        	commonDao.saveOrUpdate(contractItemStageList.get(i));
        }
        
		
	}

	public void deleteContractStagePlan(
			ContractItemStagePlan contractItemStagePlan) {
		ContractItemStagePlan toDeletePlan = (ContractItemStagePlan) commonDao.load(ContractItemStagePlan.class, contractItemStagePlan.getId());
		commonDao.delete(toDeletePlan);
		// 要刷新缓存，不然没有执行delete语句，下面的查询还会查询出来
		commonDao.flushSession();
		Number planCount = (Number) commonDao.uniqueResult("select count(*) from ContractItemStagePlan where contractItemStage.conIStageSid = ?", toDeletePlan.getContractItemStage().getConIStageSid());
		if(planCount.intValue() == 0){
			commonDao.delete(toDeletePlan.getContractItemStage());
		}
		commonDao.flushSession();
		// 把删除的语句sql执行了
		DelPlanByStageId(toDeletePlan.getContractMainSid(), toDeletePlan.getContractItemStage().getConIStageSid(), toDeletePlan.getContractMaininfoPart().getMoneytype());
	}

	public boolean isStageOfStagePlanDuplicate(
			ContractItemStagePlan contractItemStagePlan) {
		Number planCount = (Number) commonDao.uniqueResult("select count(*) from ContractItemStagePlan where contractItemStage.itemStageName = ? and contractMaininfoPart.id = ?", contractItemStagePlan.getContractItemStage().getItemStageName(),contractItemStagePlan.getContractMaininfoPart().getId());
		return planCount.intValue() > 0;
	}

	public void saveContractStagePlan(
			ContractItemStagePlan contractItemStagePlan) {
		ContractItemStage stage = (ContractItemStage) commonDao.uniqueResult("from ContractItemStage where itemStageName = ? and contractMainSid = ? ", contractItemStagePlan.getContractItemStage().getItemStageName(),contractItemStagePlan.getContractMainSid());
		if(stage == null){
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
		ContractItemStagePlan toUpdatePlan = (ContractItemStagePlan) commonDao.load(ContractItemStagePlan.class, contractItemStagePlan.getId());
		toUpdatePlan.setStageAmout(contractItemStagePlan.getStageAmout());
		toUpdatePlan.setMakeInvoiceDate(contractItemStagePlan.getMakeInvoiceDate());
		commonDao.update(toUpdatePlan);
	}



	public void saveContractEvent(List<ContractItemMaininfo> iteminfolist) {
		//需加入方法
		//将变更值存入根据ID号查找出的对象 并更新
		for(int i=0;i<iteminfolist.size();i++){
			//查找出数据库中对象
			ContractItemMaininfo maininfo=(ContractItemMaininfo)commonDao.load(ContractItemMaininfo.class, iteminfolist.get(i).getConItemMinfoSid());
			maininfo.setConItemId(iteminfolist.get(i).getConItemId());
			maininfo.setWar(iteminfolist.get(i).getWar());
			maininfo.setItemResDept(iteminfolist.get(i).getItemResDept());
			maininfo.setItemResDeptP(iteminfolist.get(i).getItemResDeptP());
			commonDao.update(maininfo);
		}
	}



	public void saveEventInfo(Long contractitemmaininfo, String itemcontrent, BigDecimal money) {
		ContractItemInfo iteminfoa=new ContractItemInfo();		
		iteminfoa.setContractItemMaininfo(contractitemmaininfo);
		iteminfoa.setItemcontrent(itemcontrent);
		iteminfoa.setConItemAmountWithTax(money);
		iteminfoa.setConModInfo(false);
		commonDao.save(iteminfoa);
		SetAccount(contractitemmaininfo);
		
	}



	public void removeEventInfo(Long id) {
		ContractItemInfo iteminfo= (ContractItemInfo)commonDao.load(ContractItemInfo.class,id);
		Long mainid=iteminfo.getContractItemMaininfo();
		commonDao.delete(iteminfo);
		SetAccount(mainid);
		
	}
	
	
	private void setMainInfoState(ContractMainInfo c){
		//设置合同主初始状态信息
		//是否有项目信息
        List list1=commonDao.list("from ContractItemMaininfo where contractMainInfo=?", c.getConMainInfoSid());
        if(list1==null){
        	c.setExistIteminfo(false);
        }else{
        	c.setExistIteminfo(true);
        }
        System.out.println("项目执行成功");
        //是否有其他信息
        List list2=commonDao.list("from ContractOtherInfo where contractMainSid=?", c.getConMainInfoSid());
        if(list2==null){
        	c.setExistFinAccout(false);
        }else{
        	c.setExistFinAccout(true);
        }
        System.out.println("其他信息执行成功");
        //是否有自有产品
        List list3=commonDao.list("from ContractOwnProduce where conMinfo=?", c.getConMainInfoSid());
		if(list3==null){
			c.setExistOwnProd(false);
		}else{
			c.setExistOwnProd(true);
		}
		System.out.println("自有产品执行成功");
		//是否有外协信息
		if(c.getConId()!=null){
			List list4=commonDao.list("from AssistanceContract where maininfoid=?", c.getConId());
			if(list4==null){
				c.setExistSidehelp(false);
			}else{
				c.setExistSidehelp(true);
			}
		}else{
			c.setExistSidehelp(false);
		}
		System.out.println("外协执行成功");
		//是否有申购采购
		if(c.getConId()!=null){
		    List list5=commonDao.list("from ApplyMessage where mainId=?", c.getConId());
		    if(list5==null){
		    	c.setExistPurchase(false);
		    }else{
		    	c.setExistPurchase(true);
		    }
		}else{
			 c.setExistPurchase(false);
		}
		//是否有存在变更
		List list6=commonDao.list("from ContractChangeInfo where contractMainSid=?",c.getConMainInfoSid());
        if(list6==null){
        	c.setExistC(false);
        }else{
        	c.setExistC(true);
        }		
        System.out.println("变更执行成功");
        commonDao.update(c);
	}
	
	private ContractItemMaininfo setEventInfoState(ContractItemMaininfo iteminfo){
		iteminfo.setConItemCostSure(0L);
		iteminfo.setConModInfo(false);
		System.out.println("***执行成功***");
		return iteminfo;
	}

    private void SetAccount(Long itemMainId){
    	ContractItemMaininfo item= (ContractItemMaininfo) commonDao.load(ContractItemMaininfo.class, itemMainId);   	
		List<BigDecimal> iteminfo=commonDao.list("select sum(conItemAmountWithTax) from ContractItemInfo where contractItemMaininfo=?", item.getConItemMinfoSid());
		if(iteminfo.size()!= 0){
			item.setAccountmoney(iteminfo.get(0));
	     }else{
	    	 item.setAccountmoney(BigDecimal.ZERO);
	     }
		commonDao.update(item);
    }



	public int accountStageNum(Long mainid) {
		List list=commonDao.list("from ContractItemStage where contractMainSid=?",mainid);		
		return list.size();
	}



	public int accountDepartNum(Long mainid) {
		List list=commonDao.list("from ContractItemMaininfo where contractMainInfo=?", mainid);
		return list.size();
	}



	public List<InitContractBillpro> findPlanlist(Long mainid) {
		List<InitContractBillpro> list=commonDao.list("from InitContractBillpro where conMainInfoSid=? order by conItemStage,conItemInfo",mainid);
		return list;
	}


	public void splitPlan(Long splititemNum, Long splitstageNum, Long mainid) {
		 InitContractBillpro intcontractbillpro=new InitContractBillpro();
		 intcontractbillpro.setConMainInfoSid(mainid);
		 intcontractbillpro.setConItemStage(splititemNum);
		 intcontractbillpro.setConItemInfo(splitstageNum);
		 commonDao.save(intcontractbillpro);
	}



	public String findDepName(Long itemid) {
		 ContractItemMaininfo itemMaininfo=(ContractItemMaininfo) commonDao.load(ContractItemMaininfo.class, itemid);
		 YXTypeManage typemanage=typeManageService.getYXTypeManage(1018L, itemMaininfo.getItemResDept());
		 return typemanage.getTypeName();
	}



	public String findStageName(Long stageid) {
		 ContractItemStage itemstage=(ContractItemStage) commonDao.load(ContractItemStage.class, stageid);
		 YXTypeManage typemanage=typeManageService.getYXTypeManage(1023L, itemstage.getItemStageName());
		 return typemanage.getTypeName();
	}
	
	public ContractOtherInfo findOtherInfo(Long mainid){
		 ContractOtherInfo otherinfo=(ContractOtherInfo) commonDao.uniqueResult("from ContractOtherInfo where contractMainSid=?  ", mainid);
		 return otherinfo;
	}



	public void saveOtherInfo(ContractOtherInfo otherinfo) {
		commonDao.saveOrUpdate(otherinfo);
	}


	public void savePlanInfo(List<InitContractBillpro> planlist) {
	 //根据阶段查询出计划开票和收款日期
	    for(int i=0;i<planlist.size();i++){
	    	InitContractBillpro ib=(InitContractBillpro) commonDao.load(InitContractBillpro.class, planlist.get(i).getInitConBillproSid());
            ib.setInitBillAmount(planlist.get(i).getInitBillAmount());
	    	commonDao.update(ib);
	    }
	}



	public List<ContractOwnProduce> findselfProductByMainid(Long mainid) {
		List<ContractOwnProduce> list=commonDao.list("from ContractOwnProduce where conMinfo=? order by conOwnProdSid",mainid );
		return list;
	}



	public void conProduct(Long mainid, Long selfproduceid) {
			ContractOwnProduce op=new ContractOwnProduce();
			op.setConMinfo(mainid);
			op.setOwnProduceId(selfproduceid);
		    commonDao.save(op);
		    System.out.println("****保存完毕***");
		}



	public void delConSelfProduct(Long delselfproduct) {
	       ContractOwnProduce cp=(ContractOwnProduce) commonDao.load(ContractOwnProduce.class, delselfproduct);
	       commonDao.delete(cp);
	}



	public void saveConSelfProductInfo(List<ContractOwnProduce> contractOwnProduce) {
		for(int i=0;i<contractOwnProduce.size();i++){
			commonDao.update(contractOwnProduce.get(i));
		}  	
	}


    //根据自有产品ID查找自有产品名称
	public String findSelfProductNameById(Long selfproductid) {
        SelfProduction sp=(SelfProduction)commonDao.load(SelfProduction.class, selfproductid);
		return sp.getRegisterName();
	}



	public List<ApplyBill> findInviceByMainid(Long mainid) {
		List<ApplyBill> list=commonDao.list(" from ApplyBill where contractMainInfo=?", mainid);
		return list;
	}



	public List<ApplyMessage> findPurchaseByMainid(Long mainid) {
		List<ApplyMessage> list=commonDao.list(" from ApplyMessage where mainId=?", mainid);
		return list;
	}



	public void removeConInvoice(Long delInvoice) {
		ApplyBill ab=(ApplyBill)commonDao.load(ApplyBill.class,delInvoice);
		ab.setContractMainInfo(null);
		ab.setIsNoContract(true);
		commonDao.update(ab);
	}



	public void removeConPurchase(Long delInvoice) {
		ApplyMessage am=(ApplyMessage)commonDao.load(ApplyMessage.class, delInvoice);
		am.setMainId(null);
		am.setApplyState("0");
		commonDao.update(am);
	}



	public void conPur(Long selectid, Long mainid) {
			ApplyMessage am=(ApplyMessage) commonDao.load(ApplyMessage.class, selectid);
			am.setMainId(mainid);
			am.setApplyState("1");
			commonDao.update(am);
		
	}



	public void conInvoice(Long mainid, Long selectid) {
			ApplyBill ab=(ApplyBill)commonDao.load(ApplyBill.class, selectid);
			ab.setContractMainInfo(mainid);
			commonDao.update(ab);
		
	}
	@SuppressWarnings("unchecked")
	public void updateContractMainState(Long[] ids, Long conState) {
		List<ContractMainInfo> contracts=null;
		
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
		contracts=(List<ContractMainInfo>)commonDao.list(hql);
		if(contracts!=null&&contracts.size()>0){
			for(ContractMainInfo c:contracts){
				c.setConState(conState);
				commonDao.saveOrUpdate(c);
			}
		}

	}




	@SuppressWarnings("unchecked")
	public void updateContractAndConItemId(List<MyBean> list) {
		for(MyBean bean:list){
			ContractItemMaininfo cm=getContractItemMaininfo(bean.getKey());
			cm.setConItemId(bean.getValue());
			commonDao.saveOrUpdate(cm);
		}
		
		
	}



	public ContractItemMaininfo getContractItemMaininfo(Long conMainItemSid) {
		ContractItemMaininfo itemInfo=(ContractItemMaininfo) commonDao.load(ContractItemMaininfo.class, conMainItemSid);
		return itemInfo;
	}



	public void updateContractMainItem(ContractItemMaininfo item) {
		commonDao.update(item);
		
	}




	public String getYXClientCode(Long mainid) {
		YXClientCode yxcc=(YXClientCode)commonDao.load(YXClientCode.class, mainid);
		return yxcc.getName();
	}



	public List<YXClientCode> findClient(Long id) {
		List<YXOEmployeeClient> lista=commonDao.list("from YXOEmployeeClient where exp.id=?", id);
		List<YXClientCode> yxcc=new ArrayList();
		if(lista.size()!=0){
		  for(int i=0;i<lista.size();i++){
			  YXClientCode cc=(YXClientCode) commonDao.uniqueResult("from YXClientCode where  is_active='1' and isEventUnit='0' and id=?",lista.get(i).getCli().getId());
			  if(cc!=null){
			   yxcc.add(cc);
			  }
		  }
		}
		System.out.println("客户查询成功");
		return yxcc;
	}



	public void sureSubmit(Long mainid) {
		ContractMainInfo cmain= (ContractMainInfo)commonDao.load(ContractMainInfo.class, mainid);
		cmain.setConState(1L);		
		commonDao.update(cmain);
	}



	public void delContract(Long mainid) {
		//查询主合同信息
		ContractMainInfo cmain=(ContractMainInfo)commonDao.load(ContractMainInfo.class, mainid);
		//查询项目信息
		List<ContractItemMaininfo> listitem=commonDao.list("from ContractItemMaininfo where contractMainInfo=?", mainid);
		//删除项目组成表
		for(ContractItemMaininfo cm:listitem){
			List<ContractItemInfo> iteminfolist=commonDao.list("from  ContractItemInfo where contractItemMaininfo=?", cm.getConItemMinfoSid());
		    if(iteminfolist.size()!=0){
		    	for(ContractItemInfo cii:iteminfolist){
		    		commonDao.delete(cii);
		    	}
		    }
		}
		//查询合同阶段信息
		List<ContractItemStage> itemstagelist=commonDao.list("from ContractItemStage where contractMainSid=?", mainid);
		//查询合同计划信息
		List<InitContractBillpro> billprolist=commonDao.list("from InitContractBillpro where conMainInfoSid=?", mainid);
	    //查询合同关联的开票
		List<ApplyBill> applybilllist=commonDao.list("from ApplyBill where contractMainInfo=?", mainid);
		for(ApplyBill ab:applybilllist){
		    ab.setIsNoContract(true);
		    commonDao.update(ab);
		}
		//查询合同关联的申购
		List<ApplyMessage> applymessagelist=commonDao.list("from ApplyMessage where mainId = ?", mainid);
		for(ApplyMessage applymessage:applymessagelist){
			applymessage.setApplyState("0");
		}
		//查询合同的自有产品
		List<ContractOwnProduce> ownproducelist=commonDao.list("from ContractOwnProduce where conMinfo=?", mainid);
		//查询合同的其他信息
		ContractOtherInfo otherinfo=(ContractOtherInfo) commonDao.uniqueResult("from ContractOtherInfo where contractMainSid=?", mainid);
	    
		//删除其他信息
		if(otherinfo!=null){
			commonDao.delete(otherinfo);
		}
		//删除合同自有产品
		if(ownproducelist.size()!=0){
			for(ContractOwnProduce cop:ownproducelist){
				commonDao.delete(cop);
			}
		}
		//删除合同开票计划
		if(billprolist.size()!=0){
			for(InitContractBillpro ib:billprolist){
				commonDao.delete(ib);
			}
		}
		//删除合同阶段
		if(itemstagelist.size()!=0){
			for(ContractItemStage cis:itemstagelist){
		    	commonDao.delete(cis);
			}
		}
		//删除合同项目
		if(listitem.size()!=0){
			for(ContractItemMaininfo cim:listitem){
				commonDao.delete(cim);
			}
		}
		//删除合同主体信息
		commonDao.delete(cmain);
		
	}



	public void changeToFormalContract(Long[] conMainId) {
		for(int i=0;i<conMainId.length;i++){
			ContractMainInfo cmaininfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class,conMainId[i]);
			if(cmaininfo.getConState()==3L)
				cmaininfo.setActiveDate(new Date());
			String str=producedConId(cmaininfo);
			cmaininfo.setConState(4L);
			cmaininfo.setConId(str);
			List<ApplyBill> ablist=commonDao.list("from ApplyBill where contractMainInfo=?",conMainId[i]);
			for(ApplyBill ab:ablist){
					 ab.setIsNoContract(false);
					 commonDao.update(ab);
			}
			String str1="您的草稿合同已经确认通过为正式合同，合同号为:"+str+"";
			noticeservice.addNotice(str1,cmaininfo.getSaleMan());
			commonDao.update(cmaininfo);
		}
		
	}
	
	//生成合同号
	private String producedConId(ContractMainInfo c){
		YXClientCode client=(YXClientCode)commonDao.load(YXClientCode.class, c.getConCustomer());
		StringBuffer conid = new StringBuffer();
		conid.append("PBSG");
		//获取行业市场
		conid.append(client.getBusinessAreaID());
		//获取但前年份
		Calendar   cal   =   Calendar.getInstance();   
		String   year   =   String.valueOf(cal.get(Calendar.YEAR));
		year=year.substring(year.length()-2);
		conid.append(year);
		//获取四位流水号
		String number=String.valueOf(commonDao.uniqueSQLResult("select tableid from sysid where id = 'contractnumber'"));
		if(number.length()==1){
			conid.append("000");
			conid.append(Integer.valueOf(number)+1);
		}
		if(number.length()==2){
			conid.append("00");
			conid.append(Integer.valueOf(number)+1);
		}
		if(number.length()==3){
			conid.append("0");
			conid.append(Integer.valueOf(number)+1);
		}
		if(number.length()==4){
			conid.append(Integer.valueOf(number)+1);
		}
		if(Integer.valueOf(number)+1==9999){
			commonDao.executeSQLUpdate("update sysid set tableid = 0 where id = 'contractnumber'");
		}else{
			int a=Integer.valueOf(number)+1;
		    commonDao.executeSQLUpdate("update sysid set tableid="+a+" where id = 'contractnumber'");	    
		}
		System.out.println(conid.toString());
	    return conid.toString();	
	}



	@SuppressWarnings("unchecked")
	public List<ContractMainInfo> findContractByState(Long[] conMainId) {
        List<ContractMainInfo> contracts=null;
		
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
		contracts=(List<ContractMainInfo>)commonDao.list(hql);
		
		return contracts;
	}



	public void changeState(List<ContractMainInfo> contracts, Long state) {
		for(ContractMainInfo c:contracts){
			c.setConState(state);
			commonDao.saveOrUpdate(c);
		}
		
	}




	public void saveContrac(ContractMainInfo info) {
		commonDao.saveOrUpdate(info);
		
	}

	public void conCustom(Long id, Long conCustomer) {
		List<Object> cliList=service.list(" from YXOEmployeeClient empCli where empCli.cli = " + conCustomer +
				"and empCli.exp = " + id);						
			   if(cliList.size()==0){
					Employee e=(Employee) commonDao.load(Employee.class, id);
					YXClientCode yxcc=(YXClientCode) commonDao.load(YXClientCode.class, conCustomer);
					YXOEmployeeClient ec=new YXOEmployeeClient();
					ec.setCli(yxcc);
					ec.setExp(e);
					ec.setIs_active("1");
					ec.setById(id);
					System.out.println("保存关联~~~~~~~~~~~~");
					commonDao.save(ec);
			   }
	    	}



	//根据主合同号查询合同开票收款计划 然后将原始开票收款计划表转存至实际开票收款计划表
	public void copysked(Long mainid) {
		//
		List<InitContractBillpro> initbillprolist=commonDao.list("from InitContractBillpro where conMainInfoSid = ? ",mainid);
		for(InitContractBillpro icbp:initbillprolist){
			RealContractBillandRecePlan rcbarp=new RealContractBillandRecePlan();
			rcbarp.setInitContractBillpro(icbp.getInitConBillproSid());
			rcbarp.setBillSureSign(icbp.getBillRecvSign());
			rcbarp.setBillContent(icbp.getBillInfo());
			rcbarp.setBase(icbp.getBase());
			rcbarp.setBillNature(icbp.getBillNature());
			rcbarp.setBillType(icbp.getBillType());
			rcbarp.setRealBillAmount(icbp.getInitBillAmount());
			rcbarp.setRealReceAmount(icbp.getInitBillAmount());
			rcbarp.setUniteBill(icbp.getUnifyBill());
			rcbarp.setRealPredBillDate(icbp.getInitBillDate());
			rcbarp.setRealPredReceDate(icbp.getInitReceDate());
			rcbarp.setExistC(false);
			rcbarp.setConModInfo(false);
			rcbarp.setConMainInfoSid(mainid);
			rcbarp.setContractItemMaininfo(icbp.getConItemInfo());
			rcbarp.setConItemStage(icbp.getConItemStage());
			commonDao.save(rcbarp);			
		}
		
	}



	public void delrealBillpro(Long mainid) {
		List<RealContractBillandRecePlan> realbillrecelist = commonDao.list("from RealContractBillandRecePlan where conMainInfoSid=?", mainid);
		for(RealContractBillandRecePlan rcbrp:realbillrecelist){
			commonDao.delete(rcbrp);
		}
	}



	public void changeZSOneComain(Long mainId) {	
		changeToFormalContract(new Long[]{mainId});
		copysked(mainId);
	}



	public void chageZSTwoComain(Long mian,String resNumber,ContractMainInfo comain) {
		if(comain.getConState()==3L)
			comain.setActiveDate(new Date());
		comain.setConId(resNumber);
		comain.setConState(new Long(4));
		String str="您的草稿合同已经确认通过为正式合同，合同号为:"+resNumber+"";
		noticeservice.addNotice(str,comain.getSaleMan());
		saveContrac(comain);
		List<ApplyBill> ablist=commonDao.list("from ApplyBill where contractMainInfo=?",comain.getConMainInfoSid());
		if(ablist!=null){
	    	for(ApplyBill ab:ablist){
				 ab.setIsNoContract(false);
		 		 commonDao.update(ab);
	    	}
	    }
		copysked(mian);
	}



	@SuppressWarnings("unchecked")
	public void concelCirform(List view) {
		
		changeState(view, new Long(1));
		List<ContractMainInfo> list=(List<ContractMainInfo>) view;
		for(ContractMainInfo lis:list){
			String str1 = "合同号为:"+lis.getConId()+"的合同已被取消确认";
			noticeservice.addNotice(str1,lis.getSaleMan());
			delrealBillpro(lis.getConMainInfoSid());
		}
	}



	public List<ContractMaininfoPart> findMainMoneyList(Long mainid) {
		List<ContractMaininfoPart> list=commonDao.list("from ContractMaininfoPart where contractmainid=?  ", mainid);
		return list;
	}


	public List<Object[]> findMainMoneyWithPlanAmountList(Long mainid) {
		List<Object[]> list=commonDao.list("select p ,(select sum(s.stageAmout) from ContractItemStagePlan s where s.contractMaininfoPart = p) from ContractMaininfoPart p where p.contractmainid=? ", mainid);
		return list;
	}


	public Double getStagePlanAmountOfContractPart(Long partId) {
		Number amount = (Number) commonDao.uniqueResult("select sum(s.stageAmout) from ContractItemStagePlan s where s.contractMaininfoPart.id = ?", partId);
		if(amount != null){
			return amount.doubleValue();
		}else{
			return null;
		}
	}



	public void saveMainMoneyPart(String mainmoneytype, Double mainmoney,
			Long mainid,String ticketType) {
		ContractMaininfoPart cmp=new ContractMaininfoPart();
		cmp.setContractmainid(mainid);
		cmp.setMoney(mainmoney);
		cmp.setMoneytype(mainmoneytype);
		cmp.setTicketType(ticketType);
		commonDao.save(cmp);	
	}



	public void delContractMainPart(Long delmainpartid) {
			ContractMaininfoPart cmp=(ContractMaininfoPart) commonDao.load(ContractMaininfoPart.class, delmainpartid);
			List<ContractItemStagePlan> itemstageplanlist = commonDao.list("from ContractItemStagePlan where contractMainSid=? and contractMaininfoPart.moneytype=?", cmp.getContractmainid(),cmp.getMoneytype());
			for (ContractItemStagePlan contractItemStagePlan : itemstageplanlist) {
				deleteContractStagePlan(contractItemStagePlan);
			}
			List<ContractItemInfo> iteminfolist=commonDao.list("select ii from ContractItemInfo ii ,ContractItemMaininfo m where ii.contractItemMaininfo = m.conItemMinfoSid and m.contractMainInfo=? and ii.mainInfoPartId=?", cmp.getContractmainid(),delmainpartid);
			for (ContractItemInfo contractItemInfo : iteminfolist) {
				this.deleteEventInfo(contractItemInfo.getConItemInfoSid());
			}
			commonDao.delete(cmp);
	}



	public String findContractType(Long mainid) {
		ContractMainInfo cmi=(ContractMainInfo) commonDao.load(ContractMainInfo.class, mainid);
		return cmi.getContractType();
	}





	public void GeneratePlanByEventId(Long mainid, Long itemid,
			String moneytype, Double money) {
		 //查询出此费用的阶段信息
		 List<ContractItemStagePlan> itemstageplanlist = commonDao.list("from ContractItemStagePlan where contractMainSid=? and contractMaininfoPart.moneytype=?", mainid,moneytype);
	
		 ContractMainInfo maininfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, mainid);
		 
		 //工程类合同
		 if("1".equals(maininfo.getContractType())){
			 //如果此合同有此阶段信息的话执行操作
			 if(itemstageplanlist!=null){
				 //查询出项目的主体信息
				 ContractItemMaininfo itemmaininfo=(ContractItemMaininfo)commonDao.load(ContractItemMaininfo.class, itemid);
				 
				 for(ContractItemStagePlan cisp:itemstageplanlist){
					 InitContractBillpro contractbillpro=new InitContractBillpro();
					 //添加项目号
					 contractbillpro.setConItemInfo(itemid);
					 //添加阶段号
					 contractbillpro.setConItemStage(cisp.getContractItemStage().getConIStageSid()); 
		             //添加计划开票日期
					 contractbillpro.setInitBillDate(cisp.getMakeInvoiceDate());
					 //添加计划收款日期
					 contractbillpro.setInitReceDate(DateUtils.addMonths(cisp.getMakeInvoiceDate(), typeManageService.getHarvestMonth(moneytype)));
					 //添加开票性质
		             contractbillpro.setBillNature(moneytype);
		             //添加票据类型
		             contractbillpro.setBillType(cisp.getContractMaininfoPart().getTicketType());
		             //添加主合同号
		             contractbillpro.setConMainInfoSid(mainid);
		             
		             //添加开票内容
		             StringBuffer str=new StringBuffer();
		             ContractMainInfo cmaininfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, mainid);
		             	 if(cmaininfo.getConName()!=null){
			               str.append(cmaininfo.getConName().toString());
		                 }else{
		                	 str.append(" ");
		                 }
		                 str.append("/");
		                 if(cmaininfo.getPartyAConId()!=null){
		                	 str.append(cmaininfo.getPartyAConId().toString());
		                 }else{
		                	 str.append(" ");
		                 }
		                 str.append("/");
		                 if(cmaininfo.getPartyAProId()!=null){
		                	 str.append(cmaininfo.getPartyAProId().toString());
		                 }else{
		                	 str.append(" ");
		                 }
                     str.append(typeManageService.getYXTypeManage(1012L, moneytype).getTypeName());
		             contractbillpro.setBillInfo(str.toString());
		             
		             //添加计划开票金额
		             //此费用的项目金额
		             Double a=money;
		             //阶段金额
		             Double b=cisp.getStageAmout();
		             //此费用总金额
		             Double c=cisp.getContractMaininfoPart().getMoney();
		             //计算出的开票金额
		             Double d=a*b/c;            
		             contractbillpro.setInitBillAmount(BigDecimal.valueOf(d));	             
		             commonDao.save(contractbillpro);
				 }
			 }
	
		 } 
	}



	public void GeneratePlanByStageId(Long mainid, Long stageid,
			String moneytype, Double money) {
		//查找合同下有此费用的项目
		ContractMaininfoPart cmaininfopart=(ContractMaininfoPart)commonDao.uniqueResult("from ContractMaininfoPart where contractmainid=? and moneytype=?",mainid,moneytype);
		List<ContractItemInfo> iteminfolist=commonDao.list("select ii from ContractItemInfo ii ,ContractItemMaininfo m where ii.contractItemMaininfo = m.conItemMinfoSid and m.contractMainInfo=? and ii.mainInfoPartId=?", mainid,cmaininfopart.getId());

		ContractMainInfo maininfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, mainid);
		 
		 //工程类合同
		if("1".equals(maininfo.getContractType())){ 
			if(iteminfolist!=null){
				for(ContractItemInfo iteminfo:iteminfolist){
				InitContractBillpro contractbillpro=new InitContractBillpro();
				ContractItemMaininfo itemmaininfo=(ContractItemMaininfo)commonDao.load(ContractItemMaininfo.class,iteminfo.getContractItemMaininfo());
				//添加项目号
				contractbillpro.setConItemInfo(itemmaininfo.getConItemMinfoSid());
				 //添加阶段号
				 contractbillpro.setConItemStage(stageid);
				//添加计划开票日期
				 ContractItemStagePlan cisp=(ContractItemStagePlan)commonDao.uniqueResult("from ContractItemStagePlan where contractMainSid=? and contractItemStage.conIStageSid = ? and contractMaininfoPart.moneytype = ?",mainid,stageid,moneytype);
				 contractbillpro.setInitBillDate(cisp.getMakeInvoiceDate());				 
				 //添加计划收款日期
				 contractbillpro.setInitReceDate(DateUtils.addMonths(cisp.getMakeInvoiceDate(), typeManageService.getHarvestMonth(moneytype)));
				 //添加开票性质
	             contractbillpro.setBillNature(moneytype);
	             //添加票据类型
	             contractbillpro.setBillType(cmaininfopart.getTicketType());
	             //添加主合同号
	             contractbillpro.setConMainInfoSid(mainid);
	             
	           //添加开票内容
	             StringBuffer str=new StringBuffer();
	             ContractMainInfo cmaininfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, mainid);
                 if(cmaininfo.getConName()!=null){
	               str.append(cmaininfo.getConName().toString());
                 }else{
                	 str.append(" ");
                 }
                 str.append("/");
                 if(cmaininfo.getPartyAConId()!=null){
                	 str.append(cmaininfo.getPartyAConId().toString());
                 }else{
                	 str.append(" ");
                 }
                 str.append("/");
                 if(cmaininfo.getPartyAProId()!=null){
                	 str.append(cmaininfo.getPartyAProId().toString());
                 }else{
                	 str.append(" ");
                 }
                 str.append("/");
                 str.append(typeManageService.getYXTypeManage(1012L, moneytype).getTypeName());
	             contractbillpro.setBillInfo(str.toString());
	             System.out.println(str.toString());
	             //添加计划开票金额
	             //此费用的项目金额
	             Double a=iteminfo.getConItemAmountWithTax().doubleValue();
	             //阶段金额
	             Double b=money;
	             //此费用总金额
	             Double c=cmaininfopart.getMoney();
	             //计算出的开票金额
	             Double d=a*b/c;
	             contractbillpro.setInitBillAmount(BigDecimal.valueOf(d));
				 
	             commonDao.save(contractbillpro);
				}
			}	
		}	
		if("2".equals(maininfo.getContractType())){
			InitContractBillpro contractbillpro=new InitContractBillpro();
			//添加阶段号
			contractbillpro.setConItemStage(stageid);
			//添加计划开票日期
			ContractItemStagePlan cisp=(ContractItemStagePlan)commonDao.uniqueResult("from ContractItemStagePlan where contractMainSid=? and contractItemStage.conIStageSid = ? and contractMaininfoPart.moneytype = ?",mainid,stageid,moneytype);
			contractbillpro.setInitBillDate(cisp.getMakeInvoiceDate());
			 //添加开票性质
            contractbillpro.setBillNature(moneytype);
            //添加票据类型
            contractbillpro.setBillType(cmaininfopart.getTicketType());
            //添加主合同号
            contractbillpro.setConMainInfoSid(mainid);
            //集成类 阶段金额既开票金额
            contractbillpro.setInitBillAmount(BigDecimal.valueOf(money));
            commonDao.save(contractbillpro);
		}
		
	}
	
	public void DelPlanByEventId(Long mainid, Long itemid, String moneytype) {
		List<InitContractBillpro> planlist = commonDao.list("from InitContractBillpro where conMainInfoSid =? and conItemInfo = ? and billNature = ?", mainid,itemid,moneytype);
		for(InitContractBillpro icbp:planlist){
			commonDao.delete(icbp);
		}
	}


	public void DelPlanByStageId(Long mainid, Long stageid, String moneytype) {
		List<InitContractBillpro> planlist = commonDao.list("from InitContractBillpro where conMainInfoSid=? and conItemStage = ? and billNature = ? ", mainid,stageid,moneytype); 
		for(InitContractBillpro icbp:planlist){
			commonDao.delete(icbp);
		}
	}
	

	public void deleteEventInfo(Long conItemInfoSid) {
		//No1.通过费用id查询费用信息，并获取里面的项目id
		ContractItemInfo itemInfo=(ContractItemInfo)commonDao.load(ContractItemInfo.class, conItemInfoSid);
		Long itemMaininfoId=itemInfo.getContractItemMaininfo();
		//No2.删除项目费用信息
		commonDao.delete(ContractItemInfo.class, conItemInfoSid);
		//No3.通过费用中的项目id判断该id在费用中的其他记录中是否还有外键！如果没有的话把该项目也删掉
		List<ContractItemInfo> itemInfoList=commonDao.list("from ContractItemInfo itemInfo where itemInfo.contractItemMaininfo=?", itemMaininfoId);
		if(itemInfoList==null||itemInfoList.size()==0)
		{
			commonDao.delete(ContractItemMaininfo.class,itemMaininfoId);
		}
		//通过项目费用查找合同费用
		ContractMaininfoPart part=(ContractMaininfoPart)commonDao.load(ContractMaininfoPart.class, itemInfo.getMainInfoPartId());
		//通过合同费用查找合同id
		ContractMainInfo maininfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, part.getContractmainid());
		DelPlanByEventId(maininfo.getConMainInfoSid(), itemMaininfoId, part.getMoneytype());
	}

		
}



	

