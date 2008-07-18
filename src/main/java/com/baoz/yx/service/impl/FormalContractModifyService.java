package com.baoz.yx.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
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
import com.baoz.yx.entity.contract.ChangingContractMainInfo;
import com.baoz.yx.entity.contract.ChangingContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractChangeExplain;
import com.baoz.yx.entity.contract.ContractItemInfo;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IFormalContractModifyService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.vo.MyBean;


@Service("formalContractModifyService")
@Transactional
public class FormalContractModifyService implements
		IFormalContractModifyService {

	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao	commonDao;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService 			service;
	
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	




	//保存合同主题信息，并创建合同项目和合同阶段对象
	public ChangingContractMainInfo saveContractMainInfo(ChangingContractMainInfo c,int departNum,int stageNum) {
	    ChangingContractMainInfo ccmi=(ChangingContractMainInfo)commonDao.load(ChangingContractMainInfo.class,c.getConMainInfoSid());
		ccmi.setConName(c.getConName());
		ccmi.setConType(c.getConType());
		ccmi.setConCustomer(c.getConCustomer());
		ccmi.setLinkManId(c.getLinkManId());
		ccmi.setCustomereventtype(c.getCustomereventtype());
		ccmi.setItemCustomer(c.getItemCustomer());
		ccmi.setBillCustomer(c.getBillCustomer());
		ccmi.setMainItemDept(c.getMainItemDept());
		ccmi.setConDrawback(c.getConDrawback());
		ccmi.setConBid(c.getConBid());
		ccmi.setConSignDate(c.getConSignDate());
		ccmi.setConStartDate(c.getConStartDate());
		ccmi.setConEndDate(c.getConEndDate());
		ccmi.setConTaxTamount(c.getConTaxTamount());
		ccmi.setPartyAProId(c.getPartyAProId());
		ccmi.setPartyAConId(c.getPartyAConId());
		ccmi.setFinProStuff(c.getFinProStuff());
		ccmi.setMainItemPeople(c.getMainItemPeople());
		ccmi.setMainItemPhone(c.getMainItemPhone());
		ccmi.setIntoYearCon(c.getIntoYearCon());
		ccmi.setContractType(c.getContractType());
		ccmi.setFinalAccount(c.getFinalAccount());
		commonDao.update(ccmi);
	    return ccmi;
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



	public void saveContractStage(List<ContractItemStage> contractItemStageList) {
        for(int i=0;i<contractItemStageList.size();i++){
        	commonDao.saveOrUpdate(contractItemStageList.get(i));
        }
        
		
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
/*		ContractItemInfo iteminfoa=new ContractItemInfo();		
		iteminfoa.setContractItemMaininfo(contractitemmaininfo);
		iteminfoa.setItemcontrent(itemcontrent);
		iteminfoa.setConItemAmountWithTax(money);
		iteminfoa.setConModInfo(false);
		commonDao.save(iteminfoa);
		SetAccount(contractitemmaininfo);*/
		
	}



	public void removeEventInfo(Long id) {
/*		ContractItemInfo iteminfo= (ContractItemInfo)commonDao.load(ContractItemInfo.class,id);
		Long mainid=iteminfo.getContractItemMaininfo();
		commonDao.delete(iteminfo);
		SetAccount(mainid);*/
		
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
 /*   	ContractItemMaininfo item= (ContractItemMaininfo) commonDao.load(ContractItemMaininfo.class, itemMainId);   	
		List<BigDecimal> iteminfo=commonDao.list("select sum(conItemAmountWithTax) from ContractItemInfo where contractItemMaininfo=?", item.getConItemMinfoSid());
		if(iteminfo.size()!= 0){
			item.setAccountmoney(iteminfo.get(0));
	     }else{
	    	 item.setAccountmoney(BigDecimal.ZERO);
	     }
		commonDao.update(item);*/
    }



	public int accountStageNum(Long mainid) {
		List list=commonDao.list("from ContractItemStage where contractMainSid=?",mainid);		
		return list.size();
	}



	public int accountDepartNum(Long mainid) {
		List list=commonDao.list("from ContractItemMaininfo where contractMainInfo=?", mainid);
		return list.size();
	}



	public List<RealContractBillandRecePlan> findPlanlist(Long mainid) {
		List<RealContractBillandRecePlan> list=commonDao.list("from RealContractBillandRecePlan where conMainInfoSid=? order by conItemStage,conItemInfo",mainid);
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
		 return itemstage.getItemStageName();
	}
	
	public ContractOtherInfo findOtherInfo(Long mainid){
		 ContractOtherInfo otherinfo=(ContractOtherInfo) commonDao.uniqueResult("from ContractOtherInfo where contractMainSid=?", mainid);
		 return otherinfo;
	}



	public void saveOtherInfo(ContractOtherInfo otherinfo) {
		commonDao.saveOrUpdate(otherinfo);
	}


	public void savePlanInfo(List<InitContractBillpro> planlist) {
	 //根据阶段查询出计划开票和收款日期
		System.out.println(planlist.get(0).getInitConBillproSid());
	    for(int i=0;i<planlist.size();i++){
	    	ContractItemStage itemstage=(ContractItemStage)commonDao.load(ContractItemStage.class, planlist.get(i).getConItemStage());
	    	//planlist.get(i).setInitBillDate(itemstage.getInitBillPdate());
	    	//planlist.get(i).setInitReceDate(itemstage.getInitRecePdate());
	    	commonDao.saveOrUpdate(planlist.get(i));
	    }
	}



	public List<ContractOwnProduce> findselfProductByMainid(Long mainid) {
		List<ContractOwnProduce> list=commonDao.list("from ContractOwnProduce where conMinfo=?",mainid );
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
			ab.setIsNoContract(false);
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
		System.out.println(id);
		List<YXOEmployeeClient> lista=commonDao.list("from YXOEmployeeClient where exp.id=?", id);
		System.out.println(lista);
		lista.get(0).getCli().getId();
		List<YXClientCode> yxcc=new ArrayList();
		if(lista.size()!=0){
		  for(int i=0;i<lista.size();i++){
			  YXClientCode cc=(YXClientCode) commonDao.uniqueResult("from YXClientCode where  is_active='1' and isEventUnit='0' and id=?",lista.get(i).getCli().getId());
			  if(cc!=null){
			   yxcc.add(cc);
			  }
		  }
		}
		System.out.println(yxcc.size());
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

	public ChangingContractMainInfo loadandcopyContractMainInfo(Long mainid) {
		ContractMainInfo maininfoa=(ContractMainInfo) commonDao.load(ContractMainInfo.class, mainid);
        //备份合同主信息
		ChangingContractMainInfo cmaininfo=new ChangingContractMainInfo();
		try {
			BeanUtils.copyProperties(cmaininfo, maininfoa);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		maininfoa.setExistC(true);
		maininfoa.setConState(6L);
		commonDao.update(maininfoa);
		cmaininfo.setOpTime(new Date());
		cmaininfo.setConModInfo(true);
		commonDao.save(cmaininfo);
		List<ContractMaininfoPart> mainlist=commonDao.list("from ContractMaininfoPart where contractmainid=?  ", mainid);
        
		//备份合同组成内容
		ChangingContractMaininfoPart ccmp=new ChangingContractMaininfoPart();
		for(ContractMaininfoPart cmp:mainlist){
             try {
				BeanUtils.copyProperties(ccmp, cmp);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			commonDao.save(ccmp);
        }
		
		
        return cmaininfo;
	}

	public ChangingContractMainInfo loadContractMainInfo(Long mainid) {
		ChangingContractMainInfo maininfo=(ChangingContractMainInfo) commonDao.uniqueResult("from ChangingContractMainInfo where conMainInfoSid=?", mainid);
		return maininfo;
	}

	public void saveContractChangeExplain(String changeExplain,Long mainid,Long personid) {
		 ContractChangeExplain cce=new ContractChangeExplain();
		 cce.setChangeexplain(changeExplain);
		 cce.setChangedate(new Date());
		 cce.setContractid(mainid);
		 cce.setChangepersonid(personid);
		 commonDao.save(cce);
		
	}

	public List<ContractMaininfoPart> findMainMoneyList(Long mainid) {
		List<ContractMaininfoPart> list=commonDao.list("from ChangingContractMaininfoPart where contractmainid=?  ", mainid);
		return list;
	}




}
