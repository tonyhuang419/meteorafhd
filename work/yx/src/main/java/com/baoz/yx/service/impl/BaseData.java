package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.MonthlyBillpro;
import com.baoz.yx.entity.contract.MonthlyRecepro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.tools.NumberToTime;
import com.baoz.yx.utils.SqlUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.ProcessResult;


@Service("baseDate")
@Transactional
public class BaseData implements com.baoz.yx.service.IBaseData {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;

	public PageInfo queryModifyContractBaseInfo(PageInfo info , String conId , String itemId ) {

		StringBuffer hql =  new StringBuffer(" select  cmi, emp.name, yc.name ,yc2.name " +
		"  from ContractMainInfo cmi ,  YXClientCode yc, Employee emp ,YXClientCode yc2 " +
		" where  cmi.saleMan = emp.id " +
		" and cmi.conCustomer = yc.id " +
		" and cmi.billCustomer = yc2.id " ); //and cmi.conState <> 10
		
		if( StringUtils.isNotBlank(conId) ){
			hql.append( " and upper(cmi.conId) like '%"+ conId.trim().toUpperCase() +"%' " );
		}
		if( StringUtils.isNotBlank(itemId)   ){
			hql.append( " and   exists  (  select 1 from  ContractItemMaininfo cimi where  upper(cimi.conItemId) like '%"+ itemId.trim().toUpperCase() +"%'   )  "  );
		}
		hql.append( " order by cmi.conMainInfoSid  desc");
		info = queryService.listQueryResult(hql.toString(), info);
		return info;
	}

	public PageInfo cancelReceByReceQuery(PageInfo info , String conId){
		conId = conId.trim().toUpperCase();
		String hql="select mainInfo," +
		"(select itemInfo from ContractItemMaininfo itemInfo where reve.billSid=itemInfo.conItemMinfoSid)," +
		" reve , e.name " +
		" from ContractMainInfo mainInfo, ReveInfo reve , Employee e  " +
		" where reve.conMainInfoSid=mainInfo.conMainInfoSid " +
		" and e.id = mainInfo.saleMan  " +
		" and upper(mainInfo.conId) like '%"+ conId +"%'  " +
		" and reve.hasSure='1' order by reve.amountTime desc" ;
		info = queryService.listQueryResult(SqlUtils.getCountSql(hql), hql, info);
		return info;
	}


	/**
	 * 取消月度开票查询
	 */
	public PageInfo cancelMonthBillQuery(PageInfo info , String conId){
		conId = conId.trim().toUpperCase();
		String hql= " select m,r,yc,cm,m.planBillAmount," +
		"(select im.conItemId from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo)," +
		"(select e.name from Employee e where e.id = cm.saleMan)," +
		"(select im.itemResDept from ContractItemMaininfo im where im.conItemMinfoSid = r.contractItemMaininfo) " +
		" from MonthlyBillpro m,RealContractBillandRecePlan r,YXClientCode yc,ContractMainInfo cm where " +
		" m.realContractBillandRecePlan = r.realConBillproSid " +
		" and cm.conCustomer = yc.id " +
		"and r.conMainInfoSid = cm.conMainInfoSid "+
		" and cm.conId like '%"+ conId +"%' order by   r.realPredBillDate desc	 "  ; 
		info = queryService.listQueryResult(SqlUtils.getCountSql(hql), hql, info);
		return info;
	}


	/**
	 * 取消月度收款查询
	 */
	public PageInfo cancelMonthReceQuery(PageInfo info , String conId){
		conId = conId.trim().toUpperCase();
		String hql = "select (select c from ContractMainInfo c where c.conMainInfoSid = mr.realContractBillandRecePlan.conMainInfoSid )," //合同号
			+"(select cc.name from ContractMainInfo c ,YXClientCode cc where c.itemCustomer = cc.id and c.conMainInfoSid = mr.realContractBillandRecePlan.conMainInfoSid),"//客户
			+" mr.realContractBillandRecePlan.realConBillproSid,"//负责部门
			+" mr.realContractBillandRecePlan.realPredReceDate,mr.receTaxAmount,"//计划收款日期，计划收款金额
			+" mr.alreadyArrivedAmount,"
			+" (select e.name from  Employee e  where c.saleMan = e.id )," 
			+" (select cim.conItemId from ContractItemMaininfo cim where mr.realContractBillandRecePlan.contractItemMaininfo = cim.conItemMinfoSid ), "
			+" (select cc.fullName from ContractMainInfo c ,YXClientCode cc where c.itemCustomer = cc.id and c.conMainInfoSid = mr.realContractBillandRecePlan.conMainInfoSid)," //客户全称
			+" mr , (select ot.organizationName from OrganizationTree ot,Employee e where ot.id=e.position and e.id = c.saleMan )," 
			+		"nvl(mr.realContractBillandRecePlan.billInvoiceAmount,0),mr.realContractBillandRecePlan.realNowBillDate "
			+  " from MonthlyRecepro mr,ContractMainInfo c where c.conMainInfoSid = mr.realContractBillandRecePlan.conMainInfoSid "
			+ "  and c.conId like '%"+ conId +"%'   order by    mr.realContractBillandRecePlan.realPredReceDate ";
		info = queryService.listQueryResult(SqlUtils.getCountSql(hql), hql, info);
		return info;
	}


	/**
	 * 取消月度开票查询
	 */
	public ProcessResult cancelMonthBill( Long planId){
		ProcessResult result = new ProcessResult();
		commonDao.delete(MonthlyBillpro.class, planId);
		result.setSuccess(true);
		return result;
	}


	/**
	 * 取消月度收款查询
	 */
	public ProcessResult cancelMonthRece( Long planId){
		ProcessResult result = new ProcessResult();
		commonDao.delete(MonthlyRecepro.class, planId);
		result.setSuccess(true);
		return result;
	}


	@SuppressWarnings("unchecked")
	public void saveChange(ContractMainInfo cmi){
		cmi.setOpPeople(UserUtils.getUser().getId());
		cmi.setOpTime(new Date());
		commonDao.update(cmi);	
		String hql = "";
		//获取所有开票申请，修改开票客户 (以及开票的客户)
		hql = " from ApplyBill ab where ab.contractMainInfo = ? ";
		List<ApplyBill> abList = commonDao.list( hql , cmi.getConMainInfoSid());
		for(ApplyBill ab:abList){
			ab.setBillCustomer(cmi.getBillCustomer());
			ab.setCustomerId(cmi.getConCustomer());
			ab.setEmployeeId(cmi.getSaleMan());
			commonDao.update(ab);
		}

		//获取所有申请申购，修改销售员...申购申请创建时是脱离合同的，所以修改合同时亦脱离
		//		hql  = " from ApplyMessage am where am.mainId = ? ";
		//		List<ApplyMessage> amList = commonDao.list( hql , cmi.getConMainInfoSid());
		//		for(ApplyMessage am:amList){
		//			am.setSellmanId(cmi.getBillCustomer());
		//			commonDao.update(am);
		//		}
	}
	
	public void updatePartName(List<ContractMaininfoPart> mainInfoPartList,Long mainId){
		for (ContractMaininfoPart contractMaininfoPart : mainInfoPartList) {
			///修改名称的同时修改计划里面的billNature
			ContractMaininfoPart orgPart = (ContractMaininfoPart)commonDao.load(ContractMaininfoPart.class,contractMaininfoPart.getId());
			/**
			 * 通过开票类型，开票性质和合同id能够获取相应的计划信息，把实际和原始计划都修改了
			 */
			String realHql = "from RealContractBillandRecePlan rplan where rplan.billNature = ? and rplan.conMainInfoSid = ?";
			List<RealContractBillandRecePlan> realPlanList = commonDao.list(realHql, orgPart.getMoneytype(),mainId);
			for (RealContractBillandRecePlan realContractBillandRecePlan : realPlanList) {
				realContractBillandRecePlan.setBillNature(contractMaininfoPart.getMoneytype());
				commonDao.update(realContractBillandRecePlan);
			}
			orgPart.setMoneytype(contractMaininfoPart.getMoneytype());
			commonDao.update(orgPart);
		}
	}
}
