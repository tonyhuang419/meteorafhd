package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.bill.BillandProRelaion;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IInvoiceService;


@Service("invoiceService")
@Transactional
public class InvoiceService implements IInvoiceService {

	@Autowired
	@Qualifier("commonDao") 
	private ICommonDao	commonDao;

	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	protected Log logger = LogFactory.getLog(this.getClass());

	public void text() {
		System.out.println("我测试成功了");

	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	//开票申请实例，开票收款计划，金额字符串（"," 分割）
	public void saveApplications(ApplyBill applyBill,List<ApplyMessage> list,String rcList,Double applyAmount) {
		BillandProRelaion br = null;
		commonDao.save(applyBill);
		applyBill.setBillApplyNum("KPSQ"+applyBill.getBillApplyId().toString());
		commonDao.update(applyBill);
		if(list!=null){
			for(int i=0;i<list.size();i++){
				ApplyMessage am = list.get(i);
				am.setTicketApplyId(applyBill.getBillApplyId());
				commonDao.update(am);
			}
		}
		//如果是已签开票申请，关联表写入
		if(applyBill.getIsNoContract()==false){
//			String rcArray[] = null;
//			if(StringUtils.isNotEmpty(rcList)){	
//			rcArray = rcList.split(",");
//			}
//			String amountArray[] = null;
//			if(StringUtils.isNotEmpty(amountString)){	
//			amountArray = amountString.split(",");
//			}

			//	for(int i=0;i<rcArray.length;i++){
			//Long planid = Long.parseLong(rcList);
			br = new BillandProRelaion();
			br.setApplyBill(applyBill.getBillApplyId());
			br.setRealContractBillandRecePlan(Long.parseLong(rcList));
			br.setRelateAmount(applyAmount);

//			SeeBeanFields.travBean(br);
			commonDao.save(br);
//			}
		}
	}


//	public void saveApplications2(ApplyBill applyBill,List<ApplyMessage> list) {
//	commonDao.save(applyBill);
//	applyBill.setBillApplyNum("KPSQ"+applyBill.getBillApplyId().toString());
//	commonDao.update(applyBill);
//	if(list!=null){
//	for(int i=0;i<list.size();i++){
//	ApplyMessage am=list.get(i);
//	am.setTicketApplyId(applyBill.getBillApplyId());
//	commonDao.update(am);
//	}
//	}
//	}


	public List<ApplyBill> findInvoiceApplications() {
		//commonDao.uniqueResult("from ApplyBill l where l.employeeId=?",Long.valueOf(admin));
		List<ApplyBill> list=commonDao.list("from ApplyBill");
		return list;
	}


	public void updateApplyBillState(String[] ids) {
		if(ids!=null&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				List list=commonDao.list("from ApplyBill t where t.billApplyId=?",Long.valueOf(ids[i]));
				ApplyBill ab=(ApplyBill)list.get(0);
				ab.setApplyBillState(2L);
				commonDao.update(ab);
			}
		}
	}


	public List applicationsAssociation(String[] ids) {//进行关联查找
		List<ApplyMessage> llll=new ArrayList<ApplyMessage>();
		if(ids!=null&&ids.length>0){
			for(int i=0;i<ids.length;i++){
				List list=commonDao.list("from ApplyMessage t where t.id=?",Long.valueOf(ids[i]));
				ApplyMessage ab=(ApplyMessage)list.get(0);
				llll.add(ab);
			}
		}
		return llll;
	}


	public ApplyBill findOneInvocie(String id) {
		List list=commonDao.list("from ApplyBill t where t.billApplyId=?",Long.valueOf(id));
		ApplyBill ab=(ApplyBill)list.get(0);
		return ab;
	}


	@SuppressWarnings("unchecked")
	public List<ApplyMessage> findMessage(String id) {
		List<ApplyMessage> list= commonDao.list("from ApplyMessage t where t.ticketApplyId=?",Long.valueOf(id));
		return list;
	}

	public void disconnectIncoice(String id){
		List <ApplyMessage>list=commonDao.list("from ApplyMessage t where t.ticketApplyId=?",Long.valueOf(id));
		if(list!=null&&list.size()>0){
			for(ApplyMessage am:list){
//				ApplyMessage am=list.get(i);
				am.setTicketApplyId(null);
				commonDao.update(am);
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void updateInvoice(ApplyBill ab, List<ApplyMessage> list,String id) {//更新开票单
		ApplyBill aBill=null;
		//System.out.println("我的id是（传到service的）+++++++++++++++++++++++++++"+id);
		List<ApplyBill> llll=commonDao.list("from ApplyBill t where t.billApplyId=?",Long.valueOf(id));
		if(llll!=null&&llll.size()>0)
			aBill=llll.get(0);
		aBill.setBillAmountTax(ab.getBillAmountTax());
		aBill.setBillAmountNotax(ab.getBillAmountNotax());
		//ab.setCustomerId(ab.getCustomerId());
		aBill.setRemarks(ab.getRemarks());
		aBill.setBillContent(ab.getBillContent());
		aBill.setBillType(ab.getBillType());
		aBill.setBillNature(ab.getBillNature());
		aBill.setContactName(ab.getContactName());
		aBill.setApplyId(new Date());


		aBill.setBillCustomer(ab.getBillCustomer());
		aBill.setCustomerId(ab.getCustomerId());

		aBill.setOneOut(ab.getOneOut());
		aBill.setStockOrg(ab.getStockOrg());
		aBill.setBillSpot(ab.getBillSpot());

		commonDao.update(aBill);

		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				ApplyMessage am=list.get(i);
				am.setTicketApplyId(aBill.getBillApplyId());
				commonDao.update(am);
			}
		}
		//System.out.println("");
	}

	/**
	 * 修改一条申请
	 * @param applyBill
	 */	
	public void updateInvoiceHasContract(ApplyBill applybill, List<ApplyMessage> list ,BillandProRelaion r){
		this.disconnectIncoice(applybill.getBillApplyId().toString());

		commonDao.update(applybill);
		commonDao.update(r);
		if(list!=null&&list.size()>0){
			for(ApplyMessage am:list){
//				ApplyMessage am=list.get(i);
				am.setTicketApplyId(applybill.getBillApplyId());
				commonDao.update(am);
			}
		}

	}


	public void deleteApplyBill(String [] id) {
		ApplyBill aBill = null;
		if(id!=null&&id.length>0){
			for(int i=0;i<id.length;i++){
				aBill = (ApplyBill)commonDao.uniqueResult("from ApplyBill a" +
						"  where a.billApplyId = ?", Long.parseLong(id[i]) );

				//删除申购关联
				List <ApplyMessage> list = commonDao.list("from ApplyMessage t" +
						"  where t.ticketApplyId=?",aBill.getBillApplyId());

				if(list!=null&&list.size()>0){
					for(ApplyMessage am:list){
//						ApplyMessage am=list.get(i);
						am.setTicketApplyId(null);
						commonDao.update(am);
					}
				}
//				aBill.setBillApplyId(Long.valueOf(id[i]));
				commonDao.delete(aBill);
			}
		}
	} 




	/**
	 * 按开票申请编号，获取是否统一开票(前提条件：已签开票)
	 * 
	 * 说明：先从关联表获取 实际开票和收款系统号，然后获取“是否统一开票”
	 * 2008年7月6日14:50:07
	 */
	public boolean getIsUniteBill(Long applysid){	
		Long rcsid = null; 		//实际计划系统号
		boolean unite = false;  //是否统一

		rcsid = (Long)commonDao.uniqueResult("select b.realContractBillandRecePlan " +
				" from BillandProRelaion b where b.applyBill = ?", applysid);
		unite = (Boolean)commonDao.uniqueResult("select r.uniteBill " +
				" from RealContractBillandRecePlan r where r.realConBillproSid = ?", rcsid);
		return unite;
	}


	/**
	 *  按开票申请系统号，通过计划-申请关联表，返回实际开票和收款计划(项目号、金额)
	 * 2008年7月8日18:26:36
	 * select p.fk_con_item_minfo_sid,rr.fk_relate_amount  ,ab.apply_bill_sid
		from yx_real_con_bc_plan p,      yx_billandpro_relaion rr,      yx_apply_bill ab
 		where p.real_con_billpro_sid = rr.fk_real_con_bcplan_sid   
  		and   ab.apply_bill_sid =  rr.fk_apply_bill_sid  and ab.apply_bill_sid = 158
	 */
	@SuppressWarnings("unchecked")
	public Map<Long,Double> getRCplanFromApply(Long applysid){
		Map<Long,Double> m = null;


		m = new HashMap<Long,Double>();

		List list =  commonDao.list("select  rc.contractItemMaininfo ,  " +
				" rr.relateAmount from RealContractBillandRecePlan rc , BillandProRelaion rr ,  ApplyBill ab" +
				" where rc.realConBillproSid = rr.realContractBillandRecePlan" +
				" and  ab.billApplyId  =  rr.applyBill and ab.billApplyId  = ?" , applysid);
		Iterator it=list.iterator();
		while(it.hasNext()){
			Object[] obj = (Object[])it.next();
			Long l = Long.parseLong(obj[0].toString());
			Double b = Double.parseDouble(obj[1].toString());  
			m.put(l, b);
		}	 
		return m;
	}



	/**
	 * 按实际开票收款计划系统号，获取合同主体
	 * 2008年7月10日17:32:32
	 */
	public ContractMainInfo getCmiFromRCPlan(Long rcSysId){
		RealContractBillandRecePlan rc = this.getRCPlanFromRCPlan(rcSysId);
		ContractMainInfo cmi = null;
		cmi = (ContractMainInfo)commonDao.uniqueResult("from ContractMainInfo cmi " +
				" where cmi.conMainInfoSid = ?", rc.getConMainInfoSid());
		return cmi;
	}

	/**
	 * 按合同主体信息，获取合同客户信息
	 * 2008年7月10日17:32:32
	 */
	public YXClientCode getClientInfoFromCmi(ContractMainInfo cmi){
		YXClientCode cd = null;
		cd = (YXClientCode)commonDao.uniqueResult("from YXClientCode cd where " +
				" cd.id = ?", cmi.getConCustomer());
		return cd;
	}


	/**
	 * 按合同主体信息，获取合同开票客户信息
	 * 2008年7月10日17:32:32
	 */
	public YXClientCode getBillClientInfoFromCmi(ContractMainInfo cmi){
		YXClientCode cd = null;
		cd = (YXClientCode)commonDao.uniqueResult("from YXClientCode cd where " +
				" cd.id = ?", cmi.getBillCustomer());
		return cd;
	}

	/**
	 * 按实际开票收款计划系统号，获取实际开票收款计划实例
	 * 2008年7月10日17:32:32
	 */
	public RealContractBillandRecePlan getRCPlanFromRCPlan(Long rcSysId){
		RealContractBillandRecePlan rc  = null;
		rc = (RealContractBillandRecePlan)commonDao.uniqueResult("from RealContractBillandRecePlan rc " +
				" where rc.realConBillproSid = ? ", rcSysId);
		return rc;	
	}

	/**
	 * 按实际开票收款计划系统号，返回项目主体系统号
	 * 2008年7月10日17:32:32
	 */
	public Long getItemNo(Long rcSysId){
		RealContractBillandRecePlan r = null;
		Long itemNo = 0L;	
		r = this.getRCPlanFromRCPlan(rcSysId);
		itemNo = r.getContractItemMaininfo();
		logger.info("项目主体系统号："+itemNo);
		return itemNo;
	}


	/**
	 * 按实际开票收款计划系统号，获取项目名称
	 * 2008年7月10日17:32:32
	 */
	public String getCimiName(Long rcSysId){
		String itemName = "";
		Long itemNo = 0L;
		itemNo = this.getItemNo(rcSysId);
		itemName = (String)foramlContractService.getItemNo(itemNo);	
		logger.info("项目主体名："+itemName);
		return itemName;
	}



	/**
	 * 按开票申请系统号，·获取关联实体
	 * 2008年7月10日17:32:32
	 */
	public BillandProRelaion getBPR(Long applyBillSid){
		BillandProRelaion r =null;
		r = (BillandProRelaion)commonDao.uniqueResult("from BillandProRelaion r where " +
				" r.applyBill = ? ", applyBillSid);
		return r;
	}


	/**
	 * 更具开票申请系统号,返回申请List
	 * 2008年7月14日14:34:57
	 */
	@SuppressWarnings("unchecked")
	public List<ApplyMessage> loadAM(Long applyBillSid){
		List<ApplyMessage> lam = null;
		lam = (List<ApplyMessage>)commonDao.list("from  ApplyMessage a where a.ticketApplyId   = ?", applyBillSid);
		logger.info("申购数量："+lam.size());
		return lam;
	}

	/**
	 * 更具开票系统申请号设出库out
	 * 2008年7月14日14:34:53
	 */
	public void makePurOut(Long applyBillSid){
		List<ApplyMessage> lam = null;
		lam = this.loadAM(applyBillSid);
		for(ApplyMessage am:lam){
			logger.info("申购系统号："+ am.getId());
			am.setOutState("1");
			commonDao.update(am);
		}
	}



	/**
	 * 根据开票系统申请号获取计划日期
	 * 2008年7月15日14:35:04
	 */
	public Date getPlanDate(Long applyBillSid){
		Date planDate = null;		
		if(foramlContractService.applyHasNoCon(applyBillSid)){	
			
		}
		else{	
			planDate = (Date)commonDao.uniqueResult("select rcbrp.realPredBillDate  from  ApplyBill a ,BillandProRelaion r, " +
					" RealContractBillandRecePlan rcbrp where a.billApplyId = r.applyBill  and" +
					" rcbrp.realConBillproSid =   r.realContractBillandRecePlan and a.billApplyId = ?", applyBillSid);
		}	
		return planDate;
	}
	
	
	/**
	 * 根据开票申请系统号，获取其名下的所有关联发票
	 * 2008年7月17日10:59:33
	 */
	@SuppressWarnings("unchecked")
	public List<InvoiceInfo> getInvoiceInfoFromApplySid (Long applyBillSid){
		List<InvoiceInfo> li = null;
		li = commonDao.list("from InvoiceInfo i where i.applyInvoiceId = ?", applyBillSid);	
		logger.info("invoice size:" + li.size());
		return li;
	}
	

	/**
	 * 根据开票申请系统号，将其名下的所有发票 关联上合同
	 * 2008年7月17日10:59:33
	 */
	public void relateContract(Long applyBillSid , Long conSid){
		List<InvoiceInfo> li = this.getInvoiceInfoFromApplySid(applyBillSid);
		for(InvoiceInfo i:li){
			i.setContractMainSid(conSid);
			logger.info("has conn - "  +  i.getId()+"  :  " + i.getContractMainSid());
			commonDao.update(i);
		}
	}

	
	/**
	 * 根据开票申请系统号，卸掉其名下的所有发票关联合同
	 * 2008年7月17日10:59:33
	 */
	public void unRelateContract(Long applyBillSid){
		List<InvoiceInfo> li = this.getInvoiceInfoFromApplySid(applyBillSid);
		for(InvoiceInfo i:li){
			i.setContractMainSid(null);
			logger.info("has conn - "  +  i.getId()+"  :  " + i.getContractMainSid());
			commonDao.update(i);
		}	
	}
	
	
	
	
	
	
	
	
	
	

//	/**
//	* 按开票申请系统号获取开票内容
//	*/
//	public String getInvoiceContent(long applyInvoiceId){
//	String invoiceContent = null;
//	invoiceContent = (String)commonDao.uniqueResult("select a.billContent from ApplyBill a " +
//	" where a.billApplyId = ?", applyInvoiceId);		
//	return invoiceContent;
//	}
	
	
	

	/**
	 * 按实际开票收款计划系统号，获取开票申请实例
	 * 
	 * 通过计划系统号，从关联表找到开票申请
	 *  
	 * 2008年7月10日17:32:32
	 */
//	public ApplyBill getApplyBillFromRCPlan(Long rcSysId){
//	ApplyBill ab = null;

//	ab = commonDao.uniqueResult(arg0, arg1)

//	return ab;
//	}
} 
