package com.baoz.yx.action.billtoReceipt;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXQueryService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

/**
 * 开票签收操作
 * 
 * 
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/billtoReceipt/signManageList.jsp")
public class SignManageQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;	
	@Autowired
	@Qualifier("yxQueryService")
	private IYXQueryService yxQueryService;
	private PageInfo info;

	private List<Object> listExp;
	private List<InvoiceInfo> invoiceList;

	private String returnSign;//返回操作是否成功
	// 定义传过来的参数
	private Long clientId;
	private Long expId;
	private String billNumber;
	private Long signState;
	private Long cId;
	private Long saleMan;
	private String contractNo;
	private String itemNo;
	private String groupId;
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"SignManageQueryParameters", this, new String[] {"clientId","groupId","billNumber","signState","saleMan","contractNo","itemNo" });
	
	//把一样的SQL定义成全局变量 免得写两遍 
	private String sql = "select ab.apply_bill_sid," +
		  "ab.bill_apply_num,"+
	      " cli.name,"+
	      " ab.apply_date,"+
	      " tm.type_name,"+
	      " ab.is_sign,"+
	      " inv.invoice_no,"+
	      " inv.invoice_amount,"+
	      " inv.invoice_date," +
	      " (select cm.con_id from yx_con_main_info cm where cm.con_main_info_sid = ab.fk_con_main_info_sid)," +
	      " null," +  
          "  (select e.name from yx_exployee e where e.id = ab.fk_employeeid)," +
          " (select cm.con_name from yx_con_main_info cm where cm.con_main_info_sid = ab.fk_con_main_info_sid)" +
	      " from yx_apply_bill   ab,"+
	      "      yx_client       cli,"+
	      "      yx_invoice_info inv,"+
	      "      yx_type_manage  tm"+
	      " where ab.fk_customerid = cli.id "+
	      " and ab.apply_bill_sid = inv.apply_invoice_id "+
	      " and tm.type_small = ab.fk_bill_type "+
	      " and tm.type_big = 1004 "+
	      " and inv.input_state = 1 ";
	
	
	
	@Override
	public String doDefault() throws Exception {
		//默认为签收过的
		signState = 0L;
		queryList(); 
	    return "queryList";
	}
	
	public String getItemNoByApplyBillId(Long applyBillId){
	  String getItemSql = "	select distinct(ii.con_item_id) "+
		   " from yx_apply_bill         ab,"+
		   "    yx_billandpro_relaion bar,"+
		   "   yx_real_con_bc_plan   r,"+
		   "    yx_con_item_minfo     ii"+
		   " where bar.fk_apply_bill_sid = ab.apply_bill_sid "+
		   " and bar.fk_real_con_bcplan_sid = r.real_con_billpro_sid"+
		   " and r.fk_con_item_minfo_sid = ii.con_item_minfo_sid" +
		   " and ab.apply_bill_sid ="+applyBillId+" ";
	   List itemlist = yxQueryService.listQueryNoPage(getItemSql);
	   String itemcode = "";
	   for(int i=0;i<itemlist.size();i++){
		   if(i!=0){
			   itemcode=itemcode+"</br>";
		   }
		   itemcode = itemcode + itemlist.get(i);
	   }
		
		
		return itemcode;
	}
	
	public String queryList() throws Exception{
		ParameterUtils.prepareParameters(holdTool);
		String querySql = sql;
		if(StringUtils.isNotBlank(contractNo)){
			querySql = querySql + " and exists (select 1 from yx_con_main_info cm" +
					" where cm.con_main_info_sid = ab.fk_con_main_info_sid " +
					" and lower(cm.con_id) like '%"+contractNo.toLowerCase()+"%' ) ";
		}
		if(StringUtils.isNotBlank(itemNo)){
			querySql = querySql + " and exists (select 1 "+
			          " from yx_real_con_bc_plan   r,"+
			          "     yx_con_item_minfo     im,"+
			          "     yx_billandpro_relaion br"+
			          " where ab.apply_bill_sid = br.fk_apply_bill_sid "+
			          "  and r.real_con_billpro_sid = br.fk_real_con_bcplan_sid and r.fk_con_item_minfo_sid = im.con_item_minfo_sid" +
			          " and lower(im.con_item_id) like '%"+itemNo.toLowerCase()+"%' ) ";
		}
		if(StringUtils.isNotBlank(billNumber)){
			querySql = querySql + " and exists (select 1 from yx_Invoice_info invoice where lower(invoice.invoice_no) like '%" +
					 StringUtils.lowerCase(StringUtils.trim(billNumber)) + "%' and invoice.apply_invoice_id = ab.apply_bill_sid )";
		}
		// 客户
		if (clientId != null) {
			querySql = querySql +" and ab.fk_customerid="+clientId;
		}
		//签收状态
		if(signState!=null){
			querySql = querySql + " and ab.is_sign = " +signState+" ";
		}
		
		Long uid = new UserUtils().getUser().getId();

		///////////////////////////////////////////////////////
		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			saleMan = user.getUser().getId();
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		// 销售员
		if (saleMan != null ) {
			querySql = querySql + " and ab.fk_employeeid="+saleMan+" ";
		}
		if (groupId != null ) {
			querySql = querySql + " and exists (select 1 from yx_exployee e ,yx_organization_tree t where t.id = e.position " +
					" and  t.organization_code like '"+groupId+"%' and e.id = ab.fk_employeeid ) ";
		}
		///////////////////////////////////////////////////////
		querySql = querySql + "order by ab.is_sign ,ab.bill_apply_num DESC,inv.invoice_date DESC";
		
		String countSql =  "select count(*) from ( "+querySql+") ";
    	info = yxQueryService.listQueryResultBySql(countSql, querySql, info); 
    	
		return "queryList";
	}

	public IQueryService getQueryService() {
		return queryService;
	}
	public void setQueryService(IQueryService queryService) {
		this.queryService = queryService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public List<InvoiceInfo> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<InvoiceInfo> invoiceList) {
		this.invoiceList = invoiceList;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getExpId() {
		return expId;
	}

	public void setExpId(Long expId) {
		this.expId = expId;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public Long getSignState() {
		return signState;
	}

	public void setSignState(Long signState) {
		this.signState = signState;
	}

	public Long getCId() {
		return cId;
	}

	public void setCId(Long id) {
		cId = id;
	}

	public String getReturnSign() {
		return returnSign;
	}

	public void setReturnSign(String returnSign) {
		this.returnSign = returnSign;
	}

	public IYXQueryService getYxQueryService() {
		return yxQueryService;
	}

	public void setYxQueryService(IYXQueryService yxQueryService) {
		this.yxQueryService = yxQueryService;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public Long getSaleMan() {
		return saleMan;
	}

	public void setSaleMan(Long saleMan) {
		this.saleMan = saleMan;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
