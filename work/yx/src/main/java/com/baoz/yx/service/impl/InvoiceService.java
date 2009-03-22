package com.baoz.yx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.bill.BillandProRelaion;
import com.baoz.yx.entity.bill.HongChongApplyBill;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.ProcessResult;
import com.baoz.yx.vo.UserDetail;


@Service("invoiceService")
@Transactional
public class InvoiceService implements IInvoiceService {

	@Autowired
	@Qualifier("commonDao") 
	private ICommonDao	commonDao;

	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;

	protected Log logger = LogFactory.getLog(this.getClass());

	public void text() {
		//System.out.println("我测试成功了");
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}



	/**
	 * 保存申请、计划关联。。。。正式合同。。。开收据用
	 */	
	public void saveBillandProRelaion(Map<Long, Double> m, ApplyBill applyBill){
		BillandProRelaion br = null;
		Double amount = new Double(0d);
		Double sum = applyBill.getBillAmountTax();
		int i =0;
		for(Object mm: m.keySet()){
			br = new BillandProRelaion();
			amount = m.get(mm);
			br.setApplyBill(applyBill.getBillApplyId());
			br.setRealContractBillandRecePlan(Long.parseLong(mm.toString()));
			if(sum>0.0){
				//最后一个存所有剩余金额，sum会大于所有计划金额的总额
				if(i == m.keySet().size() -1){
					br.setRelateAmount(sum);
					sum=0.0;
				}else{
					if(sum>amount){
						br.setRelateAmount(amount);
						sum-=amount;
					}else{
						br.setRelateAmount(amount);
						sum=0.0;
					}
				}
			}else{
				br.setRelateAmount(0.0);
			}
			commonDao.save(br);
			i++;
		}	
	}


	/**
	 * 修改申请、计划关联 。。。  计划系统号，开票申请系统号，开票金额
	 */	
	@SuppressWarnings("unchecked")
	public void updateBillandProRelaion(Map<Long, Double> m, ApplyBill applyBill){
		List<BillandProRelaion> brList = commonDao.list("from BillandProRelaion br where br.applyBill = ? ", applyBill.getBillApplyId());
		Double amount = new Double(0d);
		Double sum = applyBill.getBillAmountTax();
		int i =0;
		for(BillandProRelaion br : brList){
			BigDecimal tempb = (BigDecimal)commonDao.uniqueResult("select r.realTaxBillAmount from RealContractBillandRecePlan r " +
					" where r.realConBillproSid = ? ",  br.getRealContractBillandRecePlan()) ;
			amount = new Double(tempb.toString());
			if(sum>0.0){
				//最后一个存所有剩余金额，sum会大于所有计划金额的总额
				if(i == brList.size() -1){
					br.setRelateAmount(sum);
					sum=0.0;
				}else{
					if(sum>amount){
						br.setRelateAmount(amount);
						sum-=amount;
					}else{
						br.setRelateAmount(amount);
						sum=0.0;
					}
				}
			}else{
				br.setRelateAmount(0.0);
			}
			commonDao.update(br);
			i++;
		}	
	}

	/**
	 * 保存一条申请
	 * @param applyBill
	 */	
	public void saveApplicationsX(ApplyBill applybill, List<ApplyMessage> list ,Map<Long, Double> m){

		commonDao.save(applybill);
		//		applybill.setBillApplyNum("KPSQ"+applybill.getBillApplyId().toString());
		commonDao.update(applybill);

		if(list!=null){
			for(int i=0;i<list.size();i++){
				ApplyMessage am = list.get(i);
				am.setTicketApplyId(applybill.getBillApplyId());
				commonDao.update(am);
			}
		}	
		if(m!=null){
			this.saveBillandProRelaion(m , applybill); 
		}
	}

	//开票申请实例，开票收款计划，金额字符串（"," 分割）
	public void saveApplications(ApplyBill applyBill,List<ApplyMessage> list,String rcList,Double applyAmount) {
		BillandProRelaion br = null;
		commonDao.save(applyBill);
		//		applyBill.setBillApplyNum("KPSQ"+applyBill.getBillApplyId().toString());
		commonDao.update(applyBill);
		if(list!=null){
			for(int i=0;i<list.size();i++){
				ApplyMessage am = list.get(i);
				am.setTicketApplyId(applyBill.getBillApplyId());
				commonDao.update(am);
			}
		}
		//如果是已签开票申请，关联表写入
		//		if(applyBill.getIsNoContract()==false){
		//		String rcArray[] = null;
		//		if(StringUtils.isNotEmpty(rcList)){	
		//		rcArray = rcList.split(",");
		//		}
		//		String amountArray[] = null;
		//		if(StringUtils.isNotEmpty(amountString)){	
		//		amountArray = amountString.split(",");
		//		}

		//	for(int i=0;i<rcArray.length;i++){
		//Long planid = Long.parseLong(rcList);
		//		br = new BillandProRelaion();
		//		br.setApplyBill(applyBill.getBillApplyId());
		//		br.setRealContractBillandRecePlan(-1L);           //这里用-1表明没有计划
		//		br.setRelateAmount(applyAmount);

		//		SeeBeanFields.travBean(br);
		//		commonDao.save(br);
		//		}
		//		}
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
				ab.setApplyId(new Date());  //修改提交日期为当前日期
				commonDao.update(ab);
				updatePlanApplyBillState(2L,ab.getBillApplyId());
			}
		}
	}
	/**
	 *  根据申请单修改计划中申请单状态
	 */
	public void updatePlanApplyBillState(Long state,Long applyBillId){
		StringBuffer sql = new StringBuffer();
		sql.append("update RealContractBillandRecePlan plan set plan.applyBillState = ").append(state);
		sql.append(" where exists ");
		sql.append("(select 1 from BillandProRelaion br where br.realContractBillandRecePlan = plan.realConBillproSid and br.applyBill = ? )");
		commonDao.executeUpdate(sql.toString(), applyBillId);
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
	public void updateInvoice(ApplyBill ab, List<ApplyMessage> list,String id) {//更新开票申请
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
		aBill.setApplyBillState(ab.getApplyBillState());

		aBill.setBillCustomer(ab.getBillCustomer());
		aBill.setCustomerId(ab.getCustomerId());

		aBill.setOneOut(ab.getOneOut());
		aBill.setStockOrg(ab.getStockOrg());
		aBill.setBillSpot(ab.getBillSpot());
		aBill.setFirstReceiveMan(ab.getFirstReceiveMan());

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
	public void updateInvoiceHasContract(ApplyBill applybill, List<ApplyMessage> list , Map<Long, Double> rrcPlanList ){
		//		this.disconnectIncoice(applybill.getBillApplyId().toString());

		commonDao.update(applybill);

		if(rrcPlanList!=null){
			this.updateBillandProRelaion(rrcPlanList, applybill);
		}

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
				//如果是已签申请，则要删除开票计划关联表里面的内容
				if(aBill.getInitIsNoContract()!=null&&aBill.getInitIsNoContract()==0L)
				{
					this.updatePlanApplyBillState(null, aBill.getBillApplyId());
					List<BillandProRelaion> relationList=commonDao.list("select b from BillandProRelaion b,ApplyBill a where b.applyBill=a.billApplyId and a.billApplyId=?", aBill.getBillApplyId());
					if(relationList!=null&&relationList.size()>0){
						for (BillandProRelaion billandProRelaion : relationList) {
							commonDao.delete(billandProRelaion);

						}
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
		if(rcsid!=null){ //预预决算开票申请 会出现这种情况——属于已签申请，但是没有计划
			unite = (Boolean)commonDao.uniqueResult("select r.uniteBill " +
					" from RealContractBillandRecePlan r where r.realConBillproSid = ?", rcsid);
		}
		return unite;
	}


	/**
	 *  按开票申请系统号，通过计划-申请关联表，返回实际开票和收款计划(计划系统号、金额)
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

		List list =  commonDao.list("select  rc.realConBillproSid ,  " +
				" rr.relateAmount from RealContractBillandRecePlan rc , BillandProRelaion rr ,  ApplyBill ab" +
				" where rc.realConBillproSid = rr.realContractBillandRecePlan" +
				" and  ab.billApplyId  =  rr.applyBill and ab.billApplyId  = ? " , applysid);
		Iterator it=list.iterator();
		while(it.hasNext()){
			Object[] obj = (Object[])it.next();
			Long l = (Long)obj[0];
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
	 * 跟具开票申请系统号,返回申请List
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
	public void makePurOut(Long applyBillSid){  //以下代码，申购关联合同未作测试。。。
		List<ApplyMessage> lam = null;
		lam = this.loadAM(applyBillSid);
		Long conMainSid = null;   //合同系统号
		for(ApplyMessage am:lam){
			logger.info("申购系统号："+ am.getId());
			//logger.info("合同号："+ this.findOneInvocie(applyBillSid.toString()).getContractMainInfo());

			conMainSid = this.findOneInvocie(applyBillSid.toString()).getContractMainInfo();
			if(null != conMainSid){
				am.setMainId(conMainSid);
			}			
			am.setOutState("1");
			commonDao.update(am);
		}
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

	public String getItemMainInfoByApplyBillSid(Long itemSid) {
		ContractItemMaininfo mainInfo=(ContractItemMaininfo)commonDao.load(ContractItemMaininfo.class, itemSid);
		if(mainInfo!=null){
			return mainInfo.getConItemId();
		}
		return null;
	}



	/**
	 * 按票据类型，返回税率
	 */
	public String getTaxRate(String billType){
		return  (String)commonDao.uniqueResult("select y.info from YXTypeManage y where " +
				" y.typeBig = 1004 and  y.is_active = '1'  and y.typeSmall = ?", billType);		
	}



	/**
	 * 根据开票系统申请号获取计划日期
	 * 2008年7月15日14:35:04
	 */
	public Date getPlanDate(Long applyBillSid){
		Date planDate = null;		
		planDate = (Date)commonDao.uniqueResult("select min(rcbrp.realPredBillDate)  from  ApplyBill a ,BillandProRelaion r, " +
				" RealContractBillandRecePlan rcbrp where a.billApplyId = r.applyBill  and" +
				" rcbrp.realConBillproSid =   r.realContractBillandRecePlan and a.billApplyId = ?", applyBillSid);
		logger.info(planDate.toGMTString());
		return planDate;
	}


	/**
	 * 按开票申请获取计划时间
	 * select a.apply_bill_sid , min(rcbrp.real_pred_bill_date)  from  yx_apply_bill a , yx_billandpro_relaion r, 
	 *	yx_real_con_bc_plan rcbrp where a.apply_bill_sid = r.fk_apply_bill_sid  and
	 *  rcbrp.real_con_billpro_sid =   r.fk_real_con_bcplan_sid and a.apply_bill_sid in  ( 733,732,731,730 )
	 * group by a.apply_bill_sid 
	 */
	@SuppressWarnings("unchecked")
	public Map<Long,Date> getProDateFromApplyBill(List<Object[]> oArray){
		Map<Long,Date> proDateMap = new HashMap<Long,Date>();
		StringBuffer b = new StringBuffer("");
		for (Object[] o : oArray) {
			ApplyBill a = (ApplyBill)o[0];
			b.append(a.getBillApplyId()+",");		
		}
		b.replace(b.length()-1, b.length(), "");
		logger.info(b);

		List<Object[]> oo = commonDao.list("  select a.billApplyId , min(rcbrp.realPredBillDate)  from  ApplyBill a ,BillandProRelaion r, " +
				" RealContractBillandRecePlan rcbrp where a.billApplyId = r.applyBill  and " +
				" rcbrp.realConBillproSid =   r.realContractBillandRecePlan and a.billApplyId in ( " + b.toString() +" )" +
		" group by a.billApplyId ");

		//将List拼装成MAP，以方便前台获取
		for(Object[] o:oo){
			proDateMap.put((Long)o[0], (Date)o[1]);
		}	
		return proDateMap;
	}




	/**
	 * 按开票申请获取项目号
	 */
	@SuppressWarnings("unchecked")
	public Map<Long,List<String>> getItemNumFromApplyBilll(List<Object[]> oArray){
		Map<Long,List<String>> itemNoList = null;

		if( oArray!=null && oArray.size()>0 ){
			itemNoList = new HashMap<Long,List<String>>();
			for (Object[] o : oArray) {
				ApplyBill a = (ApplyBill)o[0];
				List<String> list =  commonDao.list("select  distinct(i.conItemId)   " +
						" from RealContractBillandRecePlan rc , BillandProRelaion rr ,  ApplyBill ab , ContractItemMaininfo i " +
						" where rc.realConBillproSid = rr.realContractBillandRecePlan" +
						" and i.conItemMinfoSid = rc.contractItemMaininfo " +
						" and ab.billApplyId  =  rr.applyBill " +
						" and ab.billApplyId  = ?" , a.getBillApplyId());
				if(list.size() > 0 && list.get(0) != null ){
					itemNoList.put(a.getBillApplyId() , list  );
				}
			}
		}
		return itemNoList;
	}

	/**
	 * 按红冲开票申请获取项目号
	 */
	@SuppressWarnings("unchecked")
	public Map<Long,List<String>> getItemNumFromHongChongApplyBilll(List<Object[]> oArray){
		Map<Long,List<String>> itemNoList = null;

		if( oArray!=null && oArray.size()>0 ){
			itemNoList = new HashMap<Long,List<String>>();
			for (Object[] o : oArray) {
				HongChongApplyBill a = (HongChongApplyBill)o[0];
				List<String> list =  commonDao.list("select  distinct(im.conItemId)   " +
						" from RealContractBillandRecePlan plan , ContractItemMaininfo im ,HongChongRelationPlan hr " +
						" where plan.realConBillproSid = hr.realContractBillandRecePlan " +
						"and im.conItemMinfoSid = plan.contractItemMaininfo " +
						"and hr.hongChongApplyBill  = ?" , a.getId());
				if(list.size() > 0 && list.get(0) != null ){
					itemNoList.put(a.getApplyBillId() , list  );
				}
			}
		}
		return itemNoList;
	}



	/**
	 * 增加 销售员 客户 关联
	 */
	public void saveSaleAndCusRealtion(Long saleId , Long cusId){

		List<YXOEmployeeClient>  yec = commonDao.list(" from YXOEmployeeClient t where exp.id = ? and  cli.id = ? ", saleId, cusId );
		if(	yec!=null && yec.size()==0 ){
			YXOEmployeeClient yxEmployeeClient = new YXOEmployeeClient();

			YXClientCode cli = (YXClientCode)commonDao.load(YXClientCode.class, cusId );
			yxEmployeeClient.setCli(cli); 		
			Employee exp = (Employee)commonDao.load(Employee.class, saleId );
			yxEmployeeClient.setExp(exp);

			commonDao.save(yxEmployeeClient);
		}

	}

	/**
	 * 按实际计划，获取开票性质
	 */
	public String getBillTypeFromRCPlan(long rcplansid){
		return (String)commonDao.uniqueResult("select r.billNature from RealContractBillandRecePlan r where  r.realConBillproSid=?",rcplansid );
	}


	/**
	 * 按实际计划，获取阶段信息
	 */
	public String getStageByRCPlan(long rcplansid){
		Long stageSid = (Long)commonDao.uniqueResult(" select r.conItemStage from  RealContractBillandRecePlan r where r.id = ? ", rcplansid);
		ContractItemStage cis = (ContractItemStage)commonDao.load ( ContractItemStage.class, stageSid);
		String cisp =  (String)commonDao.uniqueResult(" select c.stageRemark   from ContractItemStagePlan c where c.contractItemStage.conIStageSid=? " , cis.getConIStageSid());

		String stageName = typeManageService.getYXTypeManage(1023L,cis.getItemStageName()).getTypeName();

		if ( StringUtils.isNotBlank(cisp) ){
			return stageName + "（" + cisp + "）";
		}
		return stageName;
	}

	/**
	 *  查找退回申请
	 */
	public PageInfo queryApplyBillRejectInfo(PageInfo info,String groupId ,Long expId){
		StringBuffer sb = new StringBuffer( " select a ,emp.name , " +
				" ( select cm.conId from ContractMainInfo cm where cm.conMainInfoSid = a.contractMainInfo ) " +
				" from ApplyBill a ,Employee emp ,OrganizationTree orgTree" +
		" where a.employeeId = emp.id and a.applyBillState = 4 ");
		String s = this.addAuthority(sb, " a.employeeId ", groupId, expId);
		// logger.info(s);
		s = s + " order by a.applyId ";
		info = queryService.listQueryResult(SqlUtils.getCountSql(s), s , info);
		return info;
	}


	// 处理hql 增加权限 注：Employee emp , OrganizationTree orgTree
	synchronized private String addAuthority(StringBuffer sb,
			String saleManString, String groupId, Long saleId) {
		UserDetail user = UserUtils.getUserDetail();

		sb.append(" and emp.position = orgTree.id    ");

		if (!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())) {
			saleId = user.getUser().getId();
		} else if(StringUtils.isBlank(groupId)) {
			groupId = user.getPosition().getOrganizationCode();
		}

		if (groupId != null && !"".equals(groupId)) {
			String temp = " and orgTree.organizationCode like '" + groupId + "%'";
			sb.append(temp);
		}

		if (saleId != null && !saleId.equals("")) {
			String temp = " and " + saleManString + " = " + saleId;
			sb.append(temp);
		}
		return sb.toString();
	}



	/**
	 * 修改发票
	 */
	public void modifyInvoice( InvoiceInfo  invoiceInfo){
		commonDao.update(invoiceInfo);
		//获取最大日期用来更新实际开票收款计划中的统计字段：		realNowBillDate;   //最后一次开票时间
		Date maxDate = this.doGetMaxInvoiceDate(invoiceInfo);
		//更新计划中最后开票日期
		List<RealContractBillandRecePlan> realPlanIdList = this.doGetRcbrpByInvoiceId(invoiceInfo);
		for( RealContractBillandRecePlan r  : realPlanIdList){
			r.setRealNowBillDate(maxDate);
			commonDao.update(r);
		}
	}

	/**
	 *  获取一个计划下所有开票的最大开票日期
	 */
	public Date doGetMaxInvoiceDate( InvoiceInfo  invoiceInfo){
		ApplyBill bill = (ApplyBill) commonDao.load(ApplyBill.class, invoiceInfo.getApplyInvoiceId());
//		List<InvoiceInfo> infoList = this.getInvoiceInfoFromApplySid(bill.getBillApplyId());
//		Date maxDate = new Date(0); 
//		for( InvoiceInfo info: infoList ){
//			if(info.getInvoiceDate().after(maxDate)){
//				maxDate = info.getInvoiceDate();
//			}
//		}
		return (Date)commonDao.uniqueResult(" select max(i.invoiceDate) from InvoiceInfo i where i.applyInvoiceId = ? ", bill.getBillApplyId());
	}

	/**
	 * 按票据系统号返回实际计划
	 */
	@SuppressWarnings("unchecked")
	public List<RealContractBillandRecePlan> doGetRcbrpByInvoiceId( InvoiceInfo  invoiceInfo){
		ApplyBill bill = (ApplyBill) commonDao.load(ApplyBill.class, invoiceInfo.getApplyInvoiceId());
		List<Long> realPlanIdList =  commonDao.list(" select br.realContractBillandRecePlan from BillandProRelaion br where br.applyBill = "+bill.getBillApplyId());
		List<RealContractBillandRecePlan> realPlanList = new ArrayList<RealContractBillandRecePlan>();
		for(Long id:realPlanIdList){
			RealContractBillandRecePlan r = (RealContractBillandRecePlan)commonDao.load(RealContractBillandRecePlan.class, id);
			realPlanList.add(r);
		}
		return realPlanList;
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
	
	public ProcessResult checkApplyBillCancelConfirm(Long applyBillId){
		ProcessResult result = new ProcessResult();
		result.setSuccess(Boolean.TRUE);
		ApplyBill apply = (ApplyBill) commonDao.load(ApplyBill.class, applyBillId);
		String hql = "select invoice from InvoiceInfo invoice,ApplyBill bill " +
				"where invoice.applyInvoiceId = bill.billApplyId and bill.billApplyId = ?";
		List<InvoiceInfo> invoiceList = commonDao.list(hql, applyBillId);
		if(invoiceList == null || invoiceList.size() == 0){
			return result;
		}else{
			int hasInput = 0;
			int noInput = 0;
			for(InvoiceInfo invoice : invoiceList){
				String state = invoice.getInputState();
				if(StringUtils.equals(state, "0")){
					noInput ++;
				}else if(StringUtils.equals(state, "1")){
					hasInput ++ ;
				}
			}
			if(hasInput > 0){
				result.setSuccess(Boolean.FALSE);
				result.setErrorCode(0);///0表示存在发票信息
				result.addErrorMessage("付款申请"+apply.getBillApplyNum()+"存在已确认的发票信息，请联系管理员");
			}
			if(noInput > 0){
				result.setSuccess(Boolean.FALSE);
				result.setErrorCode(0);///0表示存在发票信息
				result.addErrorMessage("付款申请"+apply.getBillApplyNum()+"存在未确认的发票信息，请删除发票信息");
			}
			return result;
		}
//		return result;
	}
	
} 
