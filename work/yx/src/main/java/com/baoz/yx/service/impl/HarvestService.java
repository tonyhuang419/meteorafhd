package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.baoz.core.utils.PageInfo;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.NoContractRecevieAmount;
import com.baoz.yx.entity.PlanReceInfo;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.MonthlyRecepro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IHarvestService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.tools.DateTools;
import com.baoz.yx.tools.NumberToTime;
import com.baoz.yx.utils.BigDecimalUtils;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;


@Service("harvestService")
@Transactional
public class HarvestService implements IHarvestService {


	@Autowired
	@Qualifier("commonDao")
	private ICommonDao	commonDao;

	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao	yxCommonDao;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("yxQueryService")
	private IYXQueryService yxQueryService;

	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;

	protected Log logger = LogFactory.getLog(this.getClass());

	public void delReveInfoByID(Long id) {
		ReveInfo r = (ReveInfo)commonDao.load(ReveInfo.class, id);
		commonDao.delete(r);
		modifyInfo(r.getConMainInfoSid(),r.getBillSid());
	}



	// 处理hql 增加权限 注：Employee e , OrganizationTree orgTree
	synchronized private StringBuffer addAuthority(StringBuffer sb,
			String saleManString, String groupId, Long saleId) {
		UserDetail user = UserUtils.getUserDetail();

		sb.append(" and e.position = orgTree.id    ");

		if (!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())) {
			saleId = user.getUser().getId();
		} else if(StringUtils.isBlank(groupId)) {
			groupId = user.getPosition().getOrganizationCode();
		}

		if (groupId != null && !"".equals(groupId)) {
			String temp = " and orgTree.organization_code like '" + groupId + "%'";
			sb.append(temp);
		}

		if (saleId != null && !saleId.equals("")) {
			String temp = " and " + saleManString + " = " + saleId;
			sb.append(temp);
		}
		return sb;
	}

	public PageInfo getReveinfo(PageInfo info ,String groupId, Long expId ,
			String conItemId ,String contractId,String noRemain , String  reveStartDate, String  reveEndDate){

		StringBuffer sql1 = new StringBuffer(" select cmi.con_id,   cim.con_item_id  ,cmi.con_name, c.name as c_name, e.name as e_name , "+
				"(select sum(cii.con_item_amount_with_tax) from yx_con_item_info cii where cii.fk_con_item_minfo_sid = cim.con_item_minfo_sid)  "+
				" , cim.BILL_INVOICE_AMOUNT , cim.BILL_RECEIPT_AMOUNT , cim.real_arrive_amount  , cim.SHOULD_RECE_AMOUNT  " +
				" , cmi.con_main_info_sid , cim.con_item_minfo_sid "+
				" from yx_con_main_info cmi, yx_con_item_minfo cim , yx_exployee e, yx_client c ,yx_organization_tree orgTree"+
				" where cmi.con_main_info_sid = cim.fk_con_main_info_sid  "+
				"  and cmi.sale_man = e.id  "+
				"   and cmi.con_customer = c.id " +
				" and cmi.CONTRACT_TYPE = '1'   "+
		"   and cmi.con_state in (4, 5, 6, 7, 8, 9) " ); 

		StringBuffer sql2 = new StringBuffer(" union all  "+     
				"  select cmi.con_id,  null  ,cmi.con_name,  c.name as c_name ,  e.name as e_name, cmi.con_tax_tamount, "+
				"  cmi.BILL_INVOICE_AMOUNT ,cmi.BILL_RECEIPT_AMOUNT  , cmi.real_arrive_amount  , cmi.SHOULD_RECE_AMOUNT  "+
				" , cmi.con_main_info_sid , null  "+
				" 	 from yx_con_main_info cmi,  yx_exployee e, yx_client c ,yx_organization_tree orgTree"+
				" 	where  cmi.sale_man = e.id  "+
				"    and cmi.con_customer = c.id "+
				"    and cmi.con_state in (4, 5, 6, 7, 8, 9)   "+
		"   and cmi.CONTRACT_TYPE = '2'      "  );

		if( contractId!=null && !contractId.equals("") ){
			contractId = contractId.trim().toUpperCase();
			sql1.append(" and upper(cmi.con_id) like '%" + contractId + "%'");
			sql2.append(" and upper(cmi.con_id) like '%" + contractId + "%'");
		}

		if( conItemId!=null && !conItemId.equals("") ){
			conItemId = conItemId.trim().toUpperCase();
			sql1.append(" and upper(cim.con_item_id) like '%" + conItemId + "%'");
		}

		if( reveStartDate!=null && !reveStartDate.equals("") ){
			sql1.append(" and exists ( select 1 from " +
					" yx_reve_info r where cim.con_item_minfo_sid = r.bill_sid " +
					" and  r.amount_time >= to_date('"+reveStartDate+"','yyyy-MM-dd')  )");
			sql2.append(" and exists ( select 1 from " +
					" yx_reve_info r where cmi.con_main_info_sid = r.fk_con_main_info_sid " +
					" and  r.amount_time >= to_date('"+reveStartDate+"','yyyy-MM-dd')  )");
		}

		if( reveEndDate!=null && !reveEndDate.equals("") ){
			sql1.append(" and exists ( select 1 from " +
					" yx_reve_info r where cim.con_item_minfo_sid = r.bill_sid " +
					" and  r.amount_time <= to_date('"+reveEndDate+"','yyyy-MM-dd')  )");
			sql2.append(" and exists ( select 1 from " +
					" yx_reve_info r where cmi.con_main_info_sid = r.fk_con_main_info_sid " +
					" and  r.amount_time <= to_date('"+reveEndDate+"','yyyy-MM-dd')  )");
		}

		//存在余额
		if(noRemain==null || noRemain.equals("0")){
			sql1.append(" and  ( NVL(cim.SHOULD_RECE_AMOUNT,0 )  - NVL(cim.real_arrive_amount,0 ))  > 0 " );
			sql2.append(" and  ( NVL(cmi.SHOULD_RECE_AMOUNT,0 )  - NVL(cmi.real_arrive_amount,0 ))  > 0 " );
		}
		//余额为0
		else if( noRemain.equals("1")){
			sql1.append("  and   ( NVL(cim.SHOULD_RECE_AMOUNT,0 )  - NVL(cim.real_arrive_amount,0 )) <= 0 " );
			sql2.append("  and   ( NVL(cmi.SHOULD_RECE_AMOUNT,0 )  - NVL(cmi.real_arrive_amount,0 )) <= 0" );
		}

		sql1 = this.addAuthority(sql1, "cmi.sale_man", groupId, expId);
		sql2 = this.addAuthority(sql2, "cmi.sale_man", groupId, expId);

		StringBuffer sqlAll = new StringBuffer("");	
		if( conItemId==null || conItemId.equals("") ){
			sqlAll = new StringBuffer(sql1.append(sql2)); 
		}else{
			sqlAll = sql1;
		}

		sqlAll.append(" order by 11 desc,2 asc ");

		String sqlCount = "select count(*) from ( "+ sqlAll +" ) ";

		logger.info(sqlAll.toString());
		info = yxQueryService.listQueryResultBySql(sqlCount ,sqlAll.toString(),info);

		return info;
	}


	/**
	 *  查询收款信息,首页用
	 */
	public PageInfo getReveinfoForSaler(PageInfo info,Long uid){
		Employee user=UserUtils.getUser();
//		String sql = "select a.* " +
//		"  from (select cmi.con_name,  " +
//		"    cmi.con_id,    " +
//		"    cim.con_item_id,   " + 
//		"    (select sum(rcplanx.BILL_INVOICE_AMOUNT) "+ 
//		"     from yx_con_item_minfo cimx, yx_real_con_bc_plan rcplanx "+ 
//		"     where cimx.con_item_minfo_sid = "+ 
//		"          rcplanx.fk_con_item_minfo_sid "+ 
//		"     and cimx.con_item_minfo_sid = cim.con_item_minfo_sid "+ 
//		"     and rcplanx.fk_bill_type <> '4') as invoice_amount, " +

//		"   (select sum(rv.amount) " +
//		"       from yx_reve_info rv " +
//		"     where rv.bill_sid = cim.con_item_minfo_sid) as rece_amount, " +
//		"    cim.con_item_minfo_sid, " +
//		"    cmi.con_main_info_sid," +

//		" (select sum(rcplanx.BILL_INVOICE_AMOUNT) "+ 
//		"   from yx_con_item_minfo cimx, yx_real_con_bc_plan rcplanx "+ 
//		"   where cimx.con_item_minfo_sid = "+ 
//		"         rcplanx.fk_con_item_minfo_sid "+ 
//		"     and cimx.con_item_minfo_sid = cim.con_item_minfo_sid "+ 
//		"     and rcplanx.fk_bill_type = '4') as receipt_amount , " +

//		"  (select sum(rcplanx.SHOULD_RECE_AMOUNT) "+ 
//		"  from yx_con_item_minfo cimx, yx_real_con_bc_plan rcplanx "+ 
//		"  where cimx.con_item_minfo_sid = "+ 
//		"      rcplanx.fk_con_item_minfo_sid "+ 
//		"  and cimx.con_item_minfo_sid = cim.con_item_minfo_sid) as shouldreceamount "+ //shouldreceAmount 

//		"     from yx_con_main_info cmi, yx_con_item_minfo cim " +
//		"     where cmi.con_main_info_sid = cim.fk_con_main_info_sid " +
//		"        and cmi.con_state in (4, 5, 6, 7, 8, 9)" +
//		"     and cmi.sale_man =  " + user.getId()    + 
//		"  ) a " +
//		"   where ( a.shouldReceAmount > a.rece_amount )" +

//		" union all " +

//		" select con_name,con_id,null,invoice_amount,rece_amount,null,con_main_info_sid,receipt_amount,shouldReceAmount " +
//		"  from (select cmi.con_name, " +
//		"              cmi.con_id, " +
//		"              null, " +
//		"             (select sum(iix.invoice_amount)  "+
//		"      from yx_apply_bill abx, yx_invoice_info iix  "+
//		"    where abx.apply_bill_sid = iix.apply_invoice_id  "+
//		"     and abx.fk_bill_type <> '4'  "+
//		"     and iix.fk_con_main_info_sid = cmi.con_main_info_sid) as invoice_amount, " +
//		"           (select sum(rv.amount) " +
//		"             from yx_reve_info rv " +
//		"        where rv.FK_CON_MAIN_INFO_SID = cmi.con_main_info_sid) as rece_amount, " +
//		"     null, " +
//		"    cmi.con_main_info_sid," +	
//		"  (select sum(iix.invoice_amount)  "+
//		"     from yx_apply_bill abx, yx_invoice_info iix  "+
//		"  where abx.apply_bill_sid = iix.apply_invoice_id  "+
//		"    and abx.fk_con_main_info_sid = cmi.con_main_info_sid  "+
//		"    and abx.fk_bill_type = '4') as receipt_amount," +

//		"  (select sum(p.SHOULD_RECE_AMOUNT)  "+
//		"    from yx_real_con_bc_plan p  "+
//		"   where p.fk_con_main_sid = cmi.con_main_info_sid) as shouldReceAmount   " +    //shouldreceAmount  

//		"   from yx_con_main_info cmi " +
//		"      where cmi.con_state in (4, 5, 6, 7 , 8, 9) " +
//		"     and cmi.sale_man =  " + user.getId()    + 
//		"          and cmi.CONTRACT_TYPE = '2') a " +
//		"   where (shouldReceAmount  > rece_amount )" +
//		" order by 7 desc,3 asc " ;


//		String sqlCount = "select count(*) from ( "+ sql +" ) ";

//		info = yxQueryService.listQueryResultBySql(sqlCount ,sql ,info);
		return info;
	}


	/**
	 * 获取项目开票总额By项目系统号
	 */
	public Double getItemBillAmoutByItemID(Long itemID){
		Double sum = null;
		sum = (Double)commonDao.uniqueResult("select sum(iix.invoiceAmount) " +
				" from ContractItemMaininfo cimx , " +
				"      RealContractBillandRecePlan   rcplanx," +
				"      BillandProRelaion brx, " +
				"      ApplyBill         abx," +
				"      InvoiceInfo       iix  " +
				"   where cimx.conItemMinfoSid = rcplanx.contractItemMaininfo  " +
				"   and rcplanx.realConBillproSid = brx.realContractBillandRecePlan" +
				"   and brx.applyBill = abx.billApplyId " +
				"   and iix.applyInvoiceId = abx.billApplyId " +
				"   and cimx.conItemMinfoSid = ? " , itemID);
		return sum;
	}


	/**
	 * 获取合同开票总额By项目List
	 */
	@SuppressWarnings("unchecked")
	public Map<Long,Double> getItemBillAmoutByItemList(List<ContractItemMaininfo> iteminfolist){
		StringBuffer baseHQL = new StringBuffer("select cimx.conItemMinfoSid , sum(iix.invoiceAmount) " +
				" from ContractItemMaininfo cimx , " +
				"      RealContractBillandRecePlan   rcplanx," +
				"      BillandProRelaion brx, " +
				"      ApplyBill         abx," +
				"      InvoiceInfo       iix  " +
				"   where cimx.conItemMinfoSid = rcplanx.contractItemMaininfo  " +
				"   and rcplanx.realConBillproSid = brx.realContractBillandRecePlan" +
				"   and brx.applyBill = abx.billApplyId " +
				"   and iix.applyInvoiceId = abx.billApplyId " +
		"   and cimx.conItemMinfoSid in ( " );

		StringBuffer itemNum= new StringBuffer("");
		Map m = new HashMap<Long,Double>();
		for(ContractItemMaininfo i:iteminfolist){
			itemNum.append(i.getConItemMinfoSid()+",");
		}
		String hql = baseHQL.append(itemNum.substring(0, itemNum.length()-1)).append(" ) group by cimx.conItemMinfoSid ").toString();
		List<Object[]> l = commonDao.list(hql);

		logger.info(l.size());

		return m;
	}



	/**
	 * 获取项目可收款余额		...注：统计字段在确认后才被修改，故此处不能用
	 */
	public Double getBalanceByItemID(Long itemID){
//		Double sum = (Double)commonDao.uniqueResult(" select i.shouldReceAmount "+
//		"   from ContractItemMaininfo i "+
//		"   where i.conItemMinfoSid = ? ",itemID);
		Double sum = (Double)commonDao.uniqueResult(" select sum(rcplanx.shouldReceAmount) "+
				"   from ContractItemMaininfo cimx, RealContractBillandRecePlan rcplanx "+
				"   where cimx.conItemMinfoSid = "+
				"      rcplanx.contractItemMaininfo  "+
				"   and cimx.conItemMinfoSid = ? ",itemID);
		Double reveSum = this.getSumByItemId(itemID);
		if(sum == null)
			sum = 0d;
		if(reveSum == null)
			reveSum = 0d;
		return   sum - reveSum;
	}


	/**
	 * 获取合同可收款余额		...注：统计字段在确认后才被修改，故此处不能用
	 */
	public Double getBalanceByConID(Long conID){

//		Double sum = (Double)commonDao.uniqueResult(" select cmi.shouldReceAmount) "+
//		"  from ContractMainInfo cmi "+
//		"  where cmi.conMainInfoSid = ?  ",conID); 
		Double sum = (Double)commonDao.uniqueResult(" select sum(p.shouldReceAmount) "+
				"  from RealContractBillandRecePlan p "+
				"  where p.conMainInfoSid = ?  ",conID); 
		Double reveSum = this.getSumByConId(conID);		
		if(sum==null)
			sum = 0d;
		if(reveSum==null)
			reveSum = 0d;
		return sum - reveSum ;
	}


	/**
	 * 获取合同开票总额By合同系统号
	 */
	public Double getItemBillAmoutByConID(Long conID){
		Double sumBill = (Double)commonDao.uniqueResult(" select sum(iix.invoiceAmount) "+
				"  from ContractMainInfo cmi, ApplyBill abx,InvoiceInfo iix  where "+
				"  cmi.conMainInfoSid = abx.contractMainInfo " +
				"  and abx.billApplyId = iix.applyInvoiceId  " +
				"  and cmi.conMainInfoSid =?", conID);
		return sumBill;
	}


	/**
	 * 获取项目开票收款计划By项目系统号
	 */
	@SuppressWarnings("unchecked")
	public List<RealContractBillandRecePlan> getRealContractBillandRecePlanByItemID(Long itemID){
		List<RealContractBillandRecePlan> r = commonDao.list("from  RealContractBillandRecePlan r where " +
				" r.contractItemMaininfo = ? order by r.realPredBillDate ", itemID);
		return r;
	}


	/**
	 * 获取合同开票收款计划By合同系统号
	 */
	@SuppressWarnings("unchecked")
	public List<RealContractBillandRecePlan> getRealContractBillandRecePlanByConID(Long conID){
		List<RealContractBillandRecePlan> r = commonDao.list("from  RealContractBillandRecePlan r where " +
				" r.conMainInfoSid = ? order by r.realPredBillDate ", conID);
		return r;
	}



	/**
	 * 查询收款信息by项目系统号 	
	 */
	@SuppressWarnings("unchecked")
	public List<ReveInfo> getReveInfoByItemId(Long itemID){
		List<ReveInfo> r = null;
		r = commonDao.list("from ReveInfo r where r.billSid = ? order by r.id asc", itemID);	
		return r;
	}

	/**
	 * 查询收款信息by合同系统号 	
	 */
	@SuppressWarnings("unchecked")
	public List<ReveInfo> getReveInfoByConId(Long conID){
		List<ReveInfo> r = null;
		r = commonDao.list("from ReveInfo r where r.conMainInfoSid = ? order by r.id asc", conID);	
		return r;
	}


	/**
	 * 获取未确认的收款
	 */
	@SuppressWarnings("unchecked")
	public List<ReveInfo> getReveInfoNotSure(){
		List<ReveInfo> r = null;
		r = commonDao.list("from ReveInfo r where r.hasSure == '0'  " +
		" order by r.id desc" );
		return r;
	}


	/**
	 * 获得合同已收总额 by合同系统号 ...注：统计字段在确认后才被修改，故此处不能用
	 */
	public Double getSumByConId(Long conID){
		Double sum = null;
//		sum = (Double)commonDao.uniqueResult("select cmi.realArriveAmount from ContractMainInfo cmi where cmi.conMainInfoSid = ?", conID);
		sum = (Double)commonDao.uniqueResult("select sum(r.amount) from ReveInfo r where r.conMainInfoSid = ?", conID);
		return sum;	
	}


	/**
	 * 获得项目已收总额 by项目系统号 ...注：统计字段在确认后才被修改，故此处不能用
	 */
	public Double getSumByItemId(Long itemID){
		Double sum = null;
//		sum = (Double)commonDao.uniqueResult("select i.realArriveAmount from ContractItemMaininfo i where i.conItemMinfoSid = ?", itemID);
		sum = (Double)commonDao.uniqueResult("select sum(r.amount) from ReveInfo r where r.billSid = ?", itemID);
		return sum;
	}


	/**
	 * 增加、修改 收款信息
	 */
	public void saveOrUpdateReveInfo(ReveInfo r){
		commonDao.saveOrUpdate(r);
		modifyInfo(r.getConMainInfoSid(),r.getBillSid());
	}


	/**
	 * 填充到款总额,
	 */
	private void fillArriveAmount(ReveInfo r){
		ContractMainInfo contractMainInfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, r.getConMainInfoSid());

		if(contractMainInfo.getContractType().equals("1")){  //项目类
			this.fillItemArriveAmount(r);
			this.fillContractArriveAmount(r);
		}else{
			this.fillContractArriveAmount(r);
		}
	}

	/**
	 * 填充项目到款总额
	 */
	private void fillItemArriveAmount(ReveInfo r ){
		ContractItemMaininfo i  = (ContractItemMaininfo)commonDao.load(ContractItemMaininfo.class , r.getBillSid());
		Double newSum = 0.0;
		if( i.getRealArriveAmount() == null){
			newSum = r.getAmount();
		}
		else{
			newSum = i.getRealArriveAmount() + r.getAmount();
		}
		i.setRealArriveAmount(newSum);
		commonDao.saveOrUpdate(i);
	}

	/**
	 * 合同项目到款总额
	 */
	private void fillContractArriveAmount(ReveInfo r){
		ContractMainInfo cmi  = (ContractMainInfo)commonDao.load( ContractMainInfo.class , r.getConMainInfoSid());
		Double newSum = 0.0;
		if( cmi.getRealArriveAmount() == null){
			newSum = r.getAmount();
		}
		else{
			newSum = cmi.getRealArriveAmount() + r.getAmount();
		}
		cmi.setRealArriveAmount(newSum);
		commonDao.saveOrUpdate(cmi);
	}

	/**
	 * 删除收款信息
	 */
//	public void delReveInfo(ReveInfo r){
//	commonDao.delete(r);
//	}

	/**
	 * 查询ReveInfoByID	
	 */
	public ReveInfo loadReveInfoById(Long reveID){
		return (ReveInfo)commonDao.load(ReveInfo.class, reveID);
	}

	public boolean confirmReveInfo(List<ReveInfo> list) {
		boolean flag=true;
		for (ReveInfo reve : list) {
			if("1".equals(reve.getHasSure())){
				continue;
			}
			Long conId=reve.getConMainInfoSid();
			Long itemId=reve.getBillSid();
			ContractMainInfo contract=(ContractMainInfo)commonDao.load(ContractMainInfo.class, conId);
			double reveSum=0.0;
			double planSum=0.0;
			/**没有项目号的为集成类合同**/
			if(contract.getContractType().equals("2")){
				String hql="select sum(reve.amount) from ReveInfo reve where reve.conMainInfoSid=? " +
				"and reve.hasSure='0'";
				String planHql="select sum(plan.currentArriveAmount) from RealContractBillandRecePlan plan where plan.conMainInfoSid=?";
				Double tempPlan=(Double)commonDao.uniqueResult(planHql, conId);
				if(tempPlan!=null)
					planSum=tempPlan;
				Double tempReve=(Double)commonDao.uniqueResult(hql, conId);
				if(tempReve!=null)
					reveSum=tempReve;

			}else{
				//项目类合同
				String hql="select sum(reve.amount) from ReveInfo reve where reve.billSid=? and reve.hasSure='0'";
				String planHql="select sum(plan.currentArriveAmount) from RealContractBillandRecePlan plan where plan.contractItemMaininfo=?";
				Double tempPlan=(Double)commonDao.uniqueResult(planHql, itemId);
				if(tempPlan!=null)
					planSum=tempPlan;
				Double tempReve=(Double)commonDao.uniqueResult(hql, itemId);
				reveSum=tempReve;
			}
			if(planSum==reveSum){
				modifyRealArriveAmount(reve.getConMainInfoSid(),reve.getBillSid(), reve.getAmountTime(), reve.getId());
				for (ReveInfo reveInfo : list) {
					if(reveInfo.getConMainInfoSid().equals(reve.getConMainInfoSid())){
						if(reve.getBillSid()!= null && reve.getBillSid().equals(reveInfo.getBillSid())
								|| reve.getBillSid() == reveInfo.getBillSid()){
							reveInfo.setHasSure("1");
							commonDao.update(reveInfo);

							//确认通过后，修改合同、项目到款额
							this.fillArriveAmount(reveInfo);
						}
					}
				}

				/**如果是工程类合同，通过项目建议关闭合同，如果是集成类合同通过合同id建议关闭祥和*/
				if(contract.getContractType().equals("1")){
					contractService.itemIsCloseByItem(itemId);
				}else{
					contractService.contractIsCloseByContractId(conId);
				}

			}else{
				flag=false;
			}
		}
		return flag;
	}

	public void confirmOneReveInfo(Long reveId)
	{
		ReveInfo reve=(ReveInfo)commonDao.load(ReveInfo.class, reveId);
		if(StringUtils.equals(reve.getHasSure(), "0")){
			return ;
		}
		Long conId=reve.getConMainInfoSid();
		Long itemId=reve.getBillSid();
		ContractMainInfo contract=(ContractMainInfo)commonDao.load(ContractMainInfo.class, conId);
		double reveSum=0.0;
		double planSum=0.0;
		/**没有项目号的为集成类合同**/
		if(contract.getContractType().equals("2")){
			String hql="select sum(reve.amount) from ReveInfo reve where reve.conMainInfoSid=? " +
			"and reve.hasSure='0'";
			String planHql="select sum(plan.currentArriveAmount) from RealContractBillandRecePlan plan where plan.conMainInfoSid=?";
			Double tempPlan=(Double)commonDao.uniqueResult(planHql, conId);
			if(tempPlan!=null)
				planSum=tempPlan;
			Double tempReve=(Double)commonDao.uniqueResult(hql, conId);
			if(tempReve!=null)
				reveSum=tempReve;
		}else{
			//项目类合同
			String hql="select sum(reve.amount) from ReveInfo reve where reve.billSid=? and reve.hasSure='0'";
			String planHql="select sum(plan.currentArriveAmount) from RealContractBillandRecePlan plan where plan.contractItemMaininfo=?";
			Double tempPlan=(Double)commonDao.uniqueResult(planHql, itemId);
			if(tempPlan!=null)
				planSum=tempPlan;
			Double tempReve=(Double)commonDao.uniqueResult(hql, itemId);
			if(tempReve!=null){
				reveSum=tempReve;
			}
		}
		if(planSum==reveSum){
			modifyRealArriveAmount(reve.getConMainInfoSid(),reve.getBillSid(), reve.getAmountTime(), reve.getId());
			String reveHql="from ReveInfo reve where reve.hasSure = 0 and reve.conMainInfoSid= "+conId+" ";
			if(itemId!=null){
				reveHql+=" and reve.billSid="+itemId;
			}
			List<ReveInfo> list=commonDao.list(reveHql);
			for (ReveInfo reveInfo : list) {
				if(reveInfo.getConMainInfoSid().equals(reve.getConMainInfoSid())){
					reveInfo.setHasSure("1");
					commonDao.update(reveInfo);
					//确认通过后，修改合同、项目到款额
					this.fillArriveAmount(reveInfo);
				}
			}

			/**如果是工程类合同，通过项目建议关闭合同，如果是集成类合同通过合同id建议关闭合同*/
			if(contract.getContractType().equals("1")){
				contractService.itemIsCloseByItem(itemId);
			}else{
				contractService.contractIsCloseByContractId(conId);
			}

		}else{

		}
	}




	/**
	 * 获取分配金额通过合同号
	 */
	public Double getDistributeMoneyXByConId(Long conId){
		return (Double)commonDao.uniqueResult("select sum(reve.amount) from ReveInfo reve where reve.conMainInfoSid=? and reve.hasSure='0'",conId);
	}


	/**
	 * 获取分配金额通过项目号
	 */
	public Double getDistributeMoneyXByItemId(Long itemId){
		return (Double)commonDao.uniqueResult("select sum(reve.amount) from ReveInfo reve where reve.billSid=? and reve.hasSure='0'",itemId);
	}



	public void modifyInfo(Long conId,Long itemId){
//		this.modifyRealArriveAmount(conId,itemId);
		this.modifyInvoiceAmount(conId);	
		this.modifyCurrentArriveAmount(conId,itemId);
	}

	public List<RealContractBillandRecePlan> processRP(List<RealContractBillandRecePlan> rp){
		RealContractBillandRecePlan rr = null;
		Double amount = null;
		for(RealContractBillandRecePlan r:rp ){
			amount = r.getCurrentArriveAmount();
			rr = (RealContractBillandRecePlan) commonDao.load(RealContractBillandRecePlan.class, r.getRealConBillproSid());
			rr.setCurrentArriveAmount(amount);
			commonDao.update(rr);			
		}
		return rp;
	}


	public void modifyRealArriveAmount(Long conId,Long itemId, Date amountTime, Long reveInfoId){
		yxCommonDao.flushSession();
		ContractMainInfo contractMainInfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, conId);
		List<RealContractBillandRecePlan> list=new ArrayList<RealContractBillandRecePlan>();
		///判断合同类型是项目类还是集成类，如果是项目类的话可以通过项目id去查询实际收款计划表表
		String palnHql=" from RealContractBillandRecePlan real where real.currentArriveAmount > 0";
		if(contractMainInfo.getContractType().equals("1")){
			palnHql+=" and real.contractItemMaininfo="+itemId;
			////如果是集成类项目只能通过合同id去查询实际收款计划表了。
		}else{
			palnHql+=" and real.conMainInfoSid="+conId;
		}
		list=commonDao.list(palnHql);
		for (RealContractBillandRecePlan plan : list) {
			double firstAmount=0.0;
			if(plan.getRealArriveAmount()!=null){
				firstAmount=plan.getRealArriveAmount().doubleValue();
			}
			double currentArriveAmount = plan.getCurrentArriveAmount();
			plan.setRealArriveAmount(BigDecimalUtils.toBigDecial(firstAmount+currentArriveAmount));
			plan.setCurrentArriveAmount(0.0);
			//传Null的数字参数会报错
			Date lastReceDate = (Date) commonDao.uniqueResult("select max(ri.amountTime) from ReveInfo ri where ri.conMainInfoSid = ? and ( ri.billSid is null or ri.billSid = ? )", plan.getConMainInfoSid(),plan.getContractItemMaininfo()==null?0:plan.getContractItemMaininfo());
			plan.setRealNowReceDate(lastReceDate);
			commonDao.update(plan);
			if(currentArriveAmount > 0){
				//创建计划收款日志
				PlanReceInfo planReceInfo = new PlanReceInfo();
				planReceInfo.setAmount(currentArriveAmount);
				planReceInfo.setAmountTime(amountTime);
				planReceInfo.setPlanId(plan.getRealConBillproSid());
				planReceInfo.setReveInfoId(reveInfoId);
				commonDao.save(planReceInfo);
				//更新月度计划中的字段
				updateMonthlyRecePlan(plan, amountTime);
			}
		}
	}



	public void updateMonthlyRecePlan(RealContractBillandRecePlan plan,
			Date amountTime) {
		yxCommonDao.flushSession();
		Date today = new Date();
		//获得本月
		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
		//获得下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(today, 1), Calendar.MONTH);
		MonthlyRecepro currentMonth = (MonthlyRecepro) commonDao.uniqueResult(" from MonthlyRecepro m where m.billproMonth = ? and m.realContractBillandRecePlan = " + plan.getRealConBillproSid() , nowMonth);
		MonthlyRecepro receMonth = (MonthlyRecepro) commonDao.uniqueResult(" from MonthlyRecepro m where m.billproMonth = ? and m.realContractBillandRecePlan = " + plan.getRealConBillproSid() , amountTime);
		//如果收款就在本月
		if(DateUtils.truncate(new Date(), Calendar.MONTH).equals(DateUtils.truncate(amountTime, Calendar.MONTH))){
			updateReceDateMonth(plan, amountTime, currentMonth);
		}else{
			if(currentMonth != null){
				// 本月之前到款
				Double beforeArrivedAmount = getArrivedAmountBeforeMonth(plan.getRealConBillproSid(),nowMonth);
				// 本月预计收款
				currentMonth.setReceTaxAmount(plan.getRealTaxReceAmount().doubleValue() - beforeArrivedAmount);
				//
				commonDao.update(currentMonth);
				// 如果月度计划当月的收款金额为0，删除计划外的月度计划
				if(currentMonth.getIsInsidePlan() == 1 && currentMonth.getAlreadyArrivedAmount() == 0.0){
					commonDao.delete(currentMonth);
				}
				// 如果没有预计收款，删除计划
				if(currentMonth.getReceTaxAmount() <= 0.0){
					commonDao.delete(currentMonth);
				}
			}
			updateReceDateMonth(plan, amountTime, receMonth);
		}
	}
	/**
	 * 更新收款日期所在月的月度计划
	 * @param plan
	 * @param receDate
	 * @param receMonth 和receDate在同一个月
	 */
	private void updateReceDateMonth(RealContractBillandRecePlan plan,Date receDate, MonthlyRecepro receMonth) {
		//获得收款日期所在本月
		Date nowMonth = DateUtils.truncate(receDate, Calendar.MONTH);
		//获得收款日期所在下月
		Date nextMonth = DateUtils.truncate(DateUtils.addMonths(receDate, 1), Calendar.MONTH);
		//收款日期所在月到款
		Double currentArrivedAmount = getArrivedAmountCurrentMonth(plan.getRealConBillproSid(),nowMonth,nextMonth);
		//收款日期所在月之前到款
		Double beforeArrivedAmount = getArrivedAmountBeforeMonth(plan.getRealConBillproSid(),nowMonth);
		if(receMonth != null){
			receMonth.setActualArrivedDate(receDate);
			receMonth.setAlreadyArrivedAmount(currentArrivedAmount);
			commonDao.update(receMonth);
			// 如果月度计划当月的收款金额为0，删除计划外的月度计划
			if(receMonth.getIsInsidePlan() == 1 && receMonth.getAlreadyArrivedAmount() == 0.0){
				commonDao.delete(receMonth);
			}
		}else{
			// 计划外收款
			receMonth = new MonthlyRecepro();
			receMonth.setProCreateDate(new Date());
			receMonth.setProCreatePeo(UserUtils.getUser().getId());
			receMonth.setBillproMonth(nowMonth);
			receMonth.setRealContractBillandRecePlan(plan);
			receMonth.setReceTaxAmount(plan.getRealTaxReceAmount().doubleValue() - beforeArrivedAmount);
			receMonth.setRecePlanDate(plan.getRealPredReceDate());
			receMonth.setActualArrivedDate(receDate);
			receMonth.setAlreadyArrivedAmount(currentArrivedAmount);
			receMonth.setIsInsidePlan(1);//计划外收款
			commonDao.save(receMonth);
		}
	}
	
	private Double getArrivedAmountCurrentMonth(Long planId,Date nowMonth , Date nextMonth){
		Number n = (Number)commonDao.uniqueResult("select sum(pr.amount) from PlanReceInfo pr where pr.amountTime >= ? and pr.amountTime < ? and pr.planId = ? ", nowMonth, nextMonth ,planId);
		if(n==null){
			return 0.0;
		}else{
			return n.doubleValue();
		}
	}

	private Double getArrivedAmountBeforeMonth(Long planId,Date nowMonth){
		Number n = (Number)commonDao.uniqueResult("select sum(pr.amount) from PlanReceInfo pr where pr.amountTime < ? and pr.planId = ? ", nowMonth , planId);
		if(n==null){
			return 0.0;
		}else{
			return n.doubleValue();
		}
	}

	public void modifyInvoiceAmount(Long conId){
		yxCommonDao.flushSession();
		String allHql="select invoice from InvoiceInfo invoice,ApplyBill bill,ContractMainInfo mainInfo " +
		"where invoice.applyInvoiceId=bill.billApplyId " +
		"and bill.contractMainInfo=mainInfo.conMainInfoSid " +
		"and mainInfo.conMainInfoSid="+conId+" order by invoice.invoiceDate";
		String reveHql="select sum(reve.amount) from ReveInfo reve,ContractMainInfo mainInfo " +
		"where reve.conMainInfoSid=mainInfo.conMainInfoSid " +
		"and mainInfo.conMainInfoSid="+conId;
		List<InvoiceInfo> list=commonDao.list(allHql);
		Double sumAmount=(Double)commonDao.uniqueResult(reveHql);
		if(sumAmount==null){
			sumAmount=0.0;
		}
		for (InvoiceInfo invoiceInfo : list) {
			invoiceInfo.setReceAmount(new Double("0"));
		}
		for (InvoiceInfo invoiceInfo : list) {
			double amount=invoiceInfo.getInvoiceAmount();
			if(sumAmount>0){
				if(sumAmount-amount>0){
					invoiceInfo.setReceAmount(amount);
					sumAmount-=amount;
				}else{
					invoiceInfo.setReceAmount(sumAmount);
					sumAmount=0.0;
				}
			}
			commonDao.update(invoiceInfo);
		}
	}

	public IYXCommonDao getYxCommonDao() {
		return yxCommonDao;
	}


	public void setYxCommonDao(IYXCommonDao yxCommonDao) {
		this.yxCommonDao = yxCommonDao;
	}

	public ICommonDao getCommonDao() {
		return commonDao;
	}


	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}


	public IYXQueryService getYxQueryService() {
		return yxQueryService;
	}

	
	
	public PageInfo getNoContractReceiveAmount(String groupId, Long saleMan,
			String startRecevieDate, String endRecevieDate, String state,
			Long id, PageInfo info) {
		UserDetail user = UserUtils.getUserDetail();
			if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
				saleMan = user.getUser().getId();
			}else if(StringUtils.isBlank(groupId)){
				//是组长，只查本组的
				groupId = user.getPosition().getOrganizationCode();
			}
		
		String FindReceiveAmountSql =" select n.id,c.fullName,n.recevieAmount,n.recevieDate,n.state,e.name,n.remark,n.isPerArrive " +
				"from NoContractRecevieAmount n,YXClientCode c,Employee e  " +
		" where n.customerid = c.id " +
		" and e.id = n.saleMan ";
		if (saleMan != null ) {
			FindReceiveAmountSql+=" and n.saleMan = "+saleMan+" ";
		}
		if (groupId != null && !"".equals(groupId)) {
			FindReceiveAmountSql+=" and exists (select 1 from Employee e1 ,OrganizationTree ot where e1.position=ot.id and ot.organizationCode like '"+groupId+"%' and e1.id = n.saleMan)   ";
		}
		if(StringUtils.isNotBlank(startRecevieDate)){
			FindReceiveAmountSql+=" and n.recevieDate >= to_date('" + startRecevieDate
					+ "','yyyy-mm-dd')";
		}
		if(StringUtils.isNotBlank(endRecevieDate)){
			FindReceiveAmountSql+=" and n.recevieDate <= to_date('" + endRecevieDate
			+ "','yyyy-mm-dd')";
		}
		if(StringUtils.isNotBlank(state)){
			FindReceiveAmountSql+=" and n.state = " + state;
		}
		FindReceiveAmountSql+=" order by n.state , n.recevieDate desc ";
		info = (PageInfo) queryService.listQueryResult(FindReceiveAmountSql,info);
		return info;
	}

	
	
	public Double getSumReceiveAmount(String groupId, Long saleMan,
			String startRecevieDate, String endRecevieDate, String state,
			Long id) {
		UserDetail user = UserUtils.getUserDetail();
		
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			saleMan = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		
		String FindReceiveAmountSql =" select sum(n.recevieAmount) " +
				"from NoContractRecevieAmount n,YXClientCode c,Employee e  " +
		" where n.customerid = c.id " +
		" and e.id = n.saleMan ";
		if (saleMan != null ) {
			FindReceiveAmountSql+=" and n.saleMan = "+saleMan+" ";
		}
		if (groupId != null && !"".equals(groupId)) {
			FindReceiveAmountSql+=" and exists (select 1 from Employee e1 ,OrganizationTree ot where e1.position=ot.id and ot.organizationCode like '"+groupId+"%' and e1.id = n.saleMan ) ";
		}
		if(StringUtils.isNotBlank(startRecevieDate)){
			FindReceiveAmountSql+=" and n.recevieDate >= to_date('" + startRecevieDate
					+ "','yyyy-mm-dd')";
		}
		if(StringUtils.isNotBlank(endRecevieDate)){
			FindReceiveAmountSql+=" and n.recevieDate <= to_date('" + endRecevieDate
			+ "','yyyy-mm-dd')";
		}
		if(StringUtils.isNotBlank(state)){
			FindReceiveAmountSql+=" and n.state = " + state;
		}
		FindReceiveAmountSql+=" order by n.state , n.recevieDate desc ";
		Double receiveAmount = (Double) commonDao.uniqueResult(FindReceiveAmountSql);
		return receiveAmount;
	}



	public void setYxQueryService(IYXQueryService yxQueryService) {
		this.yxQueryService = yxQueryService;
	}

	public NoContractRecevieAmount loadNoContractRecevieAmount(
			Long recevieAmountId) {
		NoContractRecevieAmount noContractRecevieAmount = (NoContractRecevieAmount) commonDao.load(NoContractRecevieAmount.class,recevieAmountId);
		return noContractRecevieAmount;
	}

	public String getCustomerNameById(Long customer) {
		YXClientCode client  = (YXClientCode)commonDao.load(YXClientCode.class, customer);
		return client.getFullName();
	}


	public Log getLogger() {
		return logger;
	}


	public void setLogger(Log logger) {
		this.logger = logger;
	}

	@SuppressWarnings("unchecked")
	public void modifyCurrentArriveAmount(Long conId,Long itemId) {
		yxCommonDao.flushSession();
		ContractMainInfo contractMainInfo=(ContractMainInfo)commonDao.load(ContractMainInfo.class, conId);
		List<RealContractBillandRecePlan> list=new ArrayList<RealContractBillandRecePlan>();
		///判断合同类型是项目类还是集成类，如果是项目类的话可以通过项目id去查询实际收款计划表表
		String planHql=" from RealContractBillandRecePlan real where 1=1";
		String reveHql="select sum(reve.amount) from ReveInfo reve where reve.hasSure='0'";
		if(contractMainInfo.getContractType().equals("1")){
			planHql+=" and real.contractItemMaininfo="+itemId;
			reveHql+=" and reve.billSid="+itemId;
			////如果是集成类项目只能通过合同id去查询实际收款计划表了。
		}else{
			planHql+=" and real.conMainInfoSid="+conId;
			reveHql+=" and reve.conMainInfoSid="+conId;
		}
		planHql += " order by real.realNowBillDate  " ;
		list = commonDao.list(planHql);
		Double sum=(Double)commonDao.uniqueResult(reveHql);
		if(sum==null){
			sum=0.0;
		}
		if(list!=null&&list.size()>0){
			for(int k=0;k<list.size();k++)
			{
				RealContractBillandRecePlan plan=list.get(k);
				plan.setCurrentArriveAmount(0.0);
				if(sum>0.0){
					double total=0.0;   //1个计划的预计收款总额
					double arrive=0.0;  //1个计划的到款总额
					if(plan.getRealTaxReceAmount()!=null){
						total=plan.getRealTaxReceAmount().doubleValue();
					}
					if(plan.getRealArriveAmount()!=null){
						arrive=plan.getRealArriveAmount().doubleValue();
					}
					Double fee=total-arrive;
					if(fee>0.0){
						if(sum>fee){
							plan.setCurrentArriveAmount(fee);
							sum-=fee;
						}else{
							plan.setCurrentArriveAmount(sum);
							sum=0.0;
						}	
					}
				}
				if( (k==list.size()-1) && sum>0 ){
					plan.setCurrentArriveAmount(sum + plan.getCurrentArriveAmount());
				}
				commonDao.update(plan);

			}
		}

	}



	public void saveNoContractReceiveAmount(NoContractRecevieAmount receAmount) {
		commonDao.saveOrUpdate(receAmount);
	}

	public void delReceAmount(Long deleteid) {
		NoContractRecevieAmount receAmount  = (NoContractRecevieAmount)commonDao.load(NoContractRecevieAmount.class, deleteid);
		commonDao.delete(receAmount);
	}

	public void changeState(Long long1,String str,String remark) {
		NoContractRecevieAmount receAmount  =  (NoContractRecevieAmount)commonDao.load(NoContractRecevieAmount.class, long1);
		receAmount.setState(str);
		receAmount.setRemark(remark);
		commonDao.update(receAmount);
	}

	public IQueryService getQueryService() {
		return queryService;
	}

	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}


	public List<Object[]> test() {
		StringBuffer sql1 = new StringBuffer(" select cmi.con_id,   cim.con_item_id  ,cmi.con_name  "+
				" from yx_con_main_info cmi, yx_con_item_minfo cim  "+
		" where cmi.con_main_info_sid = cim.fk_con_main_info_sid  " ); 
		return   yxQueryService.listQueryNoPage(sql1.toString());
	}



	public Boolean checkDateInMonthlyRece(Date arriveDate) {
		//获取时间
		String hql = "select max(billproMonth )from MonthlyRecepro";
		Date maxMonth = (Date)commonDao.uniqueResult(hql);
		if(maxMonth == null){
			return Boolean.FALSE;
		}
		String strMaxMonth = NumberToTime.getDateFormat(maxMonth, "yyyy-MM-dd");
		String[] days = strMaxMonth.split("-");
		int year = Integer.parseInt(days[0]);
		int month = Integer.parseInt(days[1]);
		int lastDay = DateTools.getLastDayOfMonth(strMaxMonth);
		Date opDate = NumberToTime.getStringConvertDate(year+"-"+month+"-"+lastDay);
		if(opDate.getTime()-arriveDate.getTime()>=0){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
