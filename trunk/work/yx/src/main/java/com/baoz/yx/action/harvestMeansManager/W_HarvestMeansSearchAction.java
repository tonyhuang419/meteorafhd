package com.baoz.yx.action.harvestMeansManager;

import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IForamlContractService;
import com.baoz.yx.service.IHarvestService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.opensymphony.xwork2.ActionContext;


@Results( {
	@Result(name = "queryList", value = "/WEB-INF/jsp/harvestMeansManager/w_harvestMeansManagerList.jsp"),
	@Result(name = "showReveInfo",  value = "/WEB-INF/jsp/harvestMeansManager/w_reveInfoByItemId.jsp"),
	@Result(name = "reveModifyWin",  value = "/WEB-INF/jsp/harvestMeansManager/w_modifyReveInfo.jsp"),
	@Result(name = "modSucc",  value = "/WEB-INF/jsp/invoiceManagement/excessive.jsp"),
	@Result(name = "getReveInfoForSalers",  value = "/WEB-INF/jsp/harvestMeansManager/w_harvestMeansManagerListForSaler.jsp")
})
public class W_HarvestMeansSearchAction extends DispatchAction {


	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;

	@Autowired
	@Qualifier("harvestService")
	private IHarvestService 		harvestService;

	@Autowired
	@Qualifier("contractService")
	private IContractService 		contractService;

	@Autowired
	@Qualifier("ForamlContractService")
	private IForamlContractService foramlContractService;
	
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	private PageInfo info;


	//搜索用.start
	private String 	groupId;      //组别
	private Long 	expId;      //销售员
	
	private String projectNo ; // 项目号	
	private String contractId;  //合同号
	
	private String  reveStartDate;
	private String  reveEndDate;


	private Long itemId;  //项目号  ..显示收款明细用
	private Long conId ; // 合同号
	private List<ReveInfo> r;
	private ReveInfo ri;
	private List<YXTypeManage> receType; // 到款方式

	private String sign;   //1 收款信息修改成功  2 删除成功  3 添加成功 
	private Long reveInfoSid; //收款系统号

	private Double maxAddAmount;  //最大可新增到款金额，验证用

	private List<RealContractBillandRecePlan> rp = null;
	private String deptNum = null;
	private Double distributeMoneyX = null;
	private String conIdStr = null;     //合同号，显示用
	private String noRemain = null;     //'0':'存在余额','1':'余额为0','2':'全部'
	
	private Date defaultDate;//服务器的时间

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"searchHarvestInfo",this,new String[]{"groupId","expId","contractId","projectNo","noRemain","reveStartDate","reveEndDate"});	

	
	@SuppressWarnings("unchecked")
	@Override
	public String doDefault(){
		ActionContext.getContext().getSession().put("noRemain",noRemain);

		ParameterUtils.prepareParameters(holdTool);
		info = ParameterUtils.preparePageInfo(info, "w_HarvestMeansSearchQuery");
		info = harvestService.getReveinfo(info , groupId , expId ,projectNo , contractId , noRemain ,reveStartDate , reveEndDate);		
		return "queryList";
	}
	public String confirmOneReveInfo()throws Exception{
		this.harvestService.processRP(rp);
		String hql="select reve from " +
		"ReveInfo reve where" +
		" reve.hasSure='0' and reve.byId="+UserUtils.getUser().getId()+" and reve.conMainInfoSid="+conId+" ";
		if(itemId!=null)
		{
			hql+=" and reve.billSid = " + itemId + " ";
		}
		List<ReveInfo> list=commonService.list(hql);
		harvestService.confirmReveInfo(list);
		sign="4";
		return showReveInfo();
	}

	public String getReveInfoForSaler(){
		Employee user=UserUtils.getUser();
		info = ParameterUtils.preparePageInfo(info, "w_HarvestMeansSearchQueryForSaler");
		info = harvestService.getReveinfoForSaler(info , user.getId() );	
		return "getReveInfoForSalers";
	}


	public String showReveInfo(){
//		noRemainSession();
		
		if(ActionContext.getContext().getSession().get("sign")!=null){
			sign = (String)ActionContext.getContext().getSession().get("sign");
			ActionContext.getContext().getSession().remove("sign");
		}

		//通过项目系统号获取收款明细
		if(itemId!=null){
			r = harvestService.getReveInfoByItemId(itemId);
			maxAddAmount = harvestService.getBalanceByItemID(itemId);
			rp = harvestService.getRealContractBillandRecePlanByItemID(itemId);
			distributeMoneyX = harvestService.getDistributeMoneyXByItemId(itemId);
		}
		else{//通过合同系统号获取收款明细	
			r = harvestService.getReveInfoByConId(conId);
			maxAddAmount = harvestService.getBalanceByConID(conId);
			rp = harvestService.getRealContractBillandRecePlanByConID(conId);
			deptNum = foramlContractService.loadContractMainInfo(conId).getMainItemDept();
			distributeMoneyX = harvestService.getDistributeMoneyXByConId(conId);
		}

		//到款方式载入
		receType = typeManageService.getYXTypeManage(1017L); 
		//合同号
		conIdStr = (String)foramlContractService.getConSn(conId);
		
		ri = new ReveInfo();
		ri.setAmountTime(new Date());
		if(maxAddAmount>=0){
			ri.setAmount(maxAddAmount);
		}
		else{
			ri.setAmount(0d);
		}
		
		defaultDate = new Date();
		return "showReveInfo";
	}


	public String saveReve(){

		Boolean flag = harvestService.checkDateInMonthlyRece(ri.getAmountTime());
		if(!flag){
			ActionContext.getContext().put("hasErrorMsg",flag );
			return showReveInfo();
		}
		ri.setBillSid(itemId);
		ri.setConMainInfoSid(conId);
		ri.setIs_active("1");
		ri.setById(UserUtils.getUser().getId());
		ri.setUpdateBy(new Date());
		ri.setHasSure("0");
		harvestService.saveOrUpdateReveInfo(ri);
		contractService.contractIsCloseByContractId(conId);

		if(ri.getBillSid()!=null){
			maxAddAmount = harvestService.getBalanceByItemID(ri.getBillSid());
		}
		else{
			maxAddAmount = harvestService.getBalanceByConID(ri.getConMainInfoSid());
		}
		sign = "3";
		return showReveInfo();
	}


	public String distributePlan(){
		harvestService.processRP(rp);
		return showReveInfo();
	}


	public String openReveInfoModify(){
//		noRemainSession();
		ri = harvestService.loadReveInfoById(reveInfoSid);
		receType = typeManageService.getYXTypeManage(1017L);
		if(ri.getBillSid()!=null){
			maxAddAmount = harvestService.getBalanceByItemID(ri.getBillSid()) + ri.getAmount();
		}
		else{
			maxAddAmount = harvestService.getBalanceByConID(ri.getConMainInfoSid()) + ri.getAmount() ;
		}
		defaultDate = new Date();
		return "reveModifyWin";
	}


	public String delReve(){
		harvestService.delReveInfoByID(reveInfoSid);
		contractService.contractIsCloseByContractId(conId);
		sign = "2";
		return showReveInfo();
	}

	@SuppressWarnings("unchecked")
	public String modifySave(){
		
	
		ReveInfo riX = new ReveInfo();
		
		riX = ri;            
		ri = harvestService.loadReveInfoById(riX.getId());
		Boolean flag = harvestService.checkDateInMonthlyRece(riX.getAmountTime());
		if(!flag){
			ActionContext.getContext().put("hasErrorMsg",flag );
			reveInfoSid = ri.getId();
			return openReveInfoModify();
		}
		
		ri.setAmount(riX.getAmount());
		ri.setReceType(riX.getReceType());
		ri.setAmountTime(riX.getAmountTime());

		ri.setById(UserUtils.getUser().getId());
		ri.setUpdateBy(new Date());	
		harvestService.saveOrUpdateReveInfo(ri);

		ActionContext.getContext().getSession().put("sign", "1");	
		contractService.contractIsCloseByContractId(ri.getConMainInfoSid());
		return "modSucc";
	}
	
	private void noRemainSession(){	
		if(ActionContext.getContext().getSession().get("noRemain")!=null){
			noRemain = (String)ActionContext.getContext().getSession().get("noRemain");
		}
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public IHarvestService getHarvestService() {
		return harvestService;
	}

	public void setHarvestService(IHarvestService harvestService) {
		this.harvestService = harvestService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getConId() {
		return conId;
	}

	public void setConId(Long conId) {
		this.conId = conId;
	}

	public List<ReveInfo> getR() {
		return r;
	}

	public void setR(List<ReveInfo> r) {
		this.r = r;
	}

	public ReveInfo getRi() {
		return ri;
	}

	public void setRi(ReveInfo ri) {
		this.ri = ri;
	}

	public List<YXTypeManage> getReceType() {
		return receType;
	}

	public void setReceType(List<YXTypeManage> receType) {
		this.receType = receType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Long getReveInfoSid() {
		return reveInfoSid;
	}

	public void setReveInfoSid(Long reveInfoSid) {
		this.reveInfoSid = reveInfoSid;
	}

	public Double getMaxAddAmount() {
		return maxAddAmount;
	}

	public void setMaxAddAmount(Double maxAddAmount) {
		this.maxAddAmount = maxAddAmount;
	}

	public List<RealContractBillandRecePlan> getRp() {
		return rp;
	}

	public void setRp(List<RealContractBillandRecePlan> rp) {
		this.rp = rp;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public String getDeptNum() {
		return deptNum;
	}

	public void setDeptNum(String deptNum) {
		this.deptNum = deptNum;
	}

	public IForamlContractService getForamlContractService() {
		return foramlContractService;
	}

	public void setForamlContractService(
			IForamlContractService foramlContractService) {
		this.foramlContractService = foramlContractService;
	}

	public Double getDistributeMoneyX() {
		return distributeMoneyX;
	}

	public void setDistributeMoneyX(Double distributeMoneyX) {
		this.distributeMoneyX = distributeMoneyX;
	}

	public String getNoRemain() {
		return noRemain;
	}

	public void setNoRemain(String noRemain) {
		this.noRemain = noRemain;
	}

	public Date getDefaultDate() {
		return defaultDate;
	}

	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Long getExpId() {
		return expId;
	}

	public void setExpId(Long expId) {
		this.expId = expId;
	}

	public String getReveStartDate() {
		return reveStartDate;
	}

	public void setReveStartDate(String reveStartDate) {
		this.reveStartDate = reveStartDate;
	}

	public String getReveEndDate() {
		return reveEndDate;
	}

	public void setReveEndDate(String reveEndDate) {
		this.reveEndDate = reveEndDate;
	}
	public ICommonService getCommonService() {
		return commonService;
	}
	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}
	public String getConIdStr() {
		return conIdStr;
	}
	public void setConIdStr(String conIdStr) {
		this.conIdStr = conIdStr;
	}
	
	

}
