/**
 * 
 */
package com.baoz.yx.action.contract;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.MaterialManager;
import com.baoz.yx.entity.SelfProduction;
import com.baoz.yx.entity.YXChargeMan;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXLinkMan;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.entity.contract.ContractMaininfoPart;
import com.baoz.yx.entity.contract.ContractOtherInfo;
import com.baoz.yx.entity.contract.ContractOwnProduce;
import com.baoz.yx.entity.contract.InitContractBillpro;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IYXTypeManageService;

/**
 * @author 合同概况说明导出
 *
 */
@Results( {
	@Result(name = "success", value = "/WEB-INF/jsp/contract/ContractAllInfoPage.jsp")
})
public class DownLoadContractInfoAction extends DispatchAction{

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("contractService")
	private IContractService contractservice;

	
	private Long mainid;
	
	private ContractMainInfo cmaininfo;
	
	private YXClientCode customer;
	
	private YXLinkMan linkMan;
	
	private Date today;
	
	private Employee selsmaninfo;
	
	private List<ContractMaininfoPart> mainPart;
	
	private YXChargeMan mainDepPerson;
	private ContractOtherInfo otherinfo;
	private List<MaterialManager> checkedMaterialList;
	private List<YXTypeManage> itemdesigntypelist;
	private List<Object[]> iteminfolist;
	private List<ContractOwnProduce> ownproductlist;
	private List<InitContractBillpro> planlist;
	private List<RealContractBillandRecePlan> planlistR;
	private List<String[]>  itemlist;//经过处理后的项目信息
	private ArrayList  ticketlist;//经过处理后的发票信息
	private ArrayList productNameList;
	private ArrayList productPrice;
	private ArrayList productNumberList;
	
	private Map<String,ContractMaininfoPart> showAmountPartList;//

	public Map<String, ContractMaininfoPart> getShowAmountPartList() {
		return showAmountPartList;
	}




	public void setShowAmountPartList(
			Map<String, ContractMaininfoPart> showAmountPartList) {
		this.showAmountPartList = showAmountPartList;
	}




	public String downLoad() throws Exception {
		 cmaininfo = (ContractMainInfo)commonService.load(ContractMainInfo.class, mainid);
		 customer = (YXClientCode)commonService.load(YXClientCode.class, cmaininfo.getConCustomer());
		 if(cmaininfo.getLinkManId()!=null){
			 linkMan = (YXLinkMan) commonService.load(YXLinkMan.class, cmaininfo.getLinkManId());
		 }
		 mainPart = commonService.list(" from ContractMaininfoPart where contractmainid = ? ", mainid);
		 showAmountPartList = new HashMap<String, ContractMaininfoPart>();
		 for (ContractMaininfoPart mainInfoPart : mainPart) {
			 showAmountPartList.put(mainInfoPart.getMoneytype(), mainInfoPart);
		}
		 today	= new Date();
		 selsmaninfo = (Employee)commonService.load(Employee.class, cmaininfo.getSaleMan());
		 mainDepPerson = (YXChargeMan) commonService.uniqueResult(" from YXChargeMan where name = ? ", cmaininfo.getMainItemPeople());
		 otherinfo = contractservice.findOtherInfo(mainid);
		 if(otherinfo!=null){
			checkedMaterialList=contractservice.getMaterialManagerByOtherId(otherinfo.getOtherInfoId());
		 }
		 itemdesigntypelist = typeManageService.getYXTypeManage(1012L); 
		 String itemSql = " select  m.conItemMinfoSid,m.itemResDept,sum(i.conItemAmountWithTax),mp.moneytype" +
		 		" from ContractItemMaininfo m," +
		 		" ContractItemInfo i,ContractMaininfoPart mp where i.contractItemMaininfo = m.conItemMinfoSid " +
		 		" and i.mainInfoPartId = mp.id" +
		 		" and m.contractMainInfo =  "+mainid+" group by mp.moneytype,m.itemResDept,m.conItemMinfoSid  ";
		 iteminfolist = commonService.list(itemSql);
		 
		 
		 setItemlist(new ArrayList());
		 List<ContractItemMaininfo> itemMaininfoList = commonService.list(" from ContractItemMaininfo  where contractMainInfo = ? ",mainid);
	     for (ContractItemMaininfo contractItemMaininfo : itemMaininfoList) {
	    	//不管费用名称将每列一个设为工程部门代码
	    	 String[] a = new String[itemdesigntypelist.size()+1];
	    	 //负责部门
	    	 a[0]=contractItemMaininfo.getItemResDept();
	    	 for(int j = 0;j<itemdesigntypelist.size();j++){
	    		 for(int i = 0 ;i<iteminfolist.size();i++){ 
				     if(iteminfolist.get(i)[0].equals(contractItemMaininfo.getConItemMinfoSid())&&iteminfolist.get(i)[3].equals(itemdesigntypelist.get(j).getTypeSmall())){
				    	 a[j+1]=iteminfolist.get(i)[2].toString();   	
				     }
	    		 }
	    	 }
	    	 itemlist.add(a);
		 }
	     
	     ticketlist = new ArrayList();

	     //开票类型 获取合同费用组成页面的票据类型
	     for(int i=0;i<itemdesigntypelist.size();i++){
	    	 for(int j=0;j<mainPart.size();j++){
	    		 if(itemdesigntypelist.get(i).getTypeSmall().equals(mainPart.get(j).getMoneytype())){	
	    			 YXTypeManage tp = (YXTypeManage)typeManageService.getYXTypeManage(1004L, mainPart.get(j).getTicketType());
	    			 ticketlist.add(i,tp.getTypeName());
	    		 }
	    	 }
	    	 if(ticketlist.size()!=(i+1)){
	    		 ticketlist.add(i,"");
	    	 }
	     }
 

	     
	     
	     productNameList = new ArrayList();
		 productNumberList = new ArrayList();
		 productPrice =  new ArrayList();

	     
		 ownproductlist = commonService.list(" from ContractOwnProduce where conMinfo = ? ", mainid);
		 for(int i=0;i<ownproductlist.size();i++){
			 SelfProduction pro = (SelfProduction) commonService.load(SelfProduction.class,ownproductlist.get(i).getOwnProduceId());
			 productNameList.add(i,pro.getRegisterName());
			 productNumberList.add(i,ownproductlist.get(i).getConOwnProdAmount());
			 productPrice.add(i,ownproductlist.get(i).getConOwnProdPrice());
		}
		 
		 for(int i=productNameList.size();i<9;i++){
			 productNameList.add("");
			 productNumberList.add("");
			 productPrice.add("");
		 }
		 

		 // 如果是合同状态未正式合同，查找正式表
		 if(cmaininfo.getConState() < 4){
			 planlist = contractservice.findPlanlist(mainid);
			 for (ListIterator<InitContractBillpro> iterator = planlist.listIterator(); iterator.hasNext();) {
				 InitContractBillpro init = iterator.next();
				 if(init.getInitBillAmount()==BigDecimal.ZERO&&init.getInitReceAmount()==BigDecimal.ZERO){
					 iterator.remove();
				 }
			 }
		 }
		 else{
			 planlistR = contractservice.findPlanlistR(mainid);
			 for (ListIterator<RealContractBillandRecePlan> iterator = planlistR.listIterator(); iterator.hasNext();) {
				 RealContractBillandRecePlan init = iterator.next();
				 if(init.getRealReceAmount()==BigDecimal.ZERO&&init.getRealReceAmount()==BigDecimal.ZERO){
					 iterator.remove();
				 }
			 }
		 }
	
		 
		 return "success";		
	}
	
	
	
	
	//原先导入方法 现在改名暂时不进行使用
	/*	public String downLoad1() throws Exception{
			 //获得output
			 OutputStream os = getResponseOutPut1(new String("合同信息.xls".getBytes("GBK"),"iso-8859-1"));
			 //导成excel
			 WritableWorkbook workbook = Workbook.createWorkbook(os);
			 //插入表格
			 WritableSheet noCodeSheet = workbook.createSheet("合同信息", 0);
			 //插入单元格
			 
			 ContractMainInfo cmaininfo = (ContractMainInfo)commonService.load(ContractMainInfo.class, mainid);
			 YXClientCode customer = (YXClientCode)commonService.load(YXClientCode.class, cmaininfo.getConCustomer());
			 YXClientCode itemCustomer = (YXClientCode)commonService.load(YXClientCode.class, cmaininfo.getItemCustomer());
			 YXClientCode billCustomer = (YXClientCode)commonService.load(YXClientCode.class, cmaininfo.getBillCustomer());
			 YXLinkMan linkMan = (YXLinkMan) commonService.load(YXLinkMan.class, cmaininfo.getLinkManId());
			 
	         int allrows=0;  //记录总行数
	 		 //////////////客户信息/////////////////////
			 noCodeSheet.addCell(new Label(0,0,"合同主信息："));
			 noCodeSheet.addCell(new Label(0,1,"客户名称："));
			 noCodeSheet.addCell(new Label(2,1,customer.getFullName()));
			 noCodeSheet.addCell(new Label(6,1,"项目单位名称："));
			 noCodeSheet.addCell(new Label(8,1,itemCustomer.getFullName()));
			 noCodeSheet.addCell(new Label(0,2,"开票客户名称："));
			 noCodeSheet.addCell(new Label(2,2,billCustomer.getFullName()));
			 noCodeSheet.addCell(new Label(6,2,"客户联系人："));
			 noCodeSheet.addCell(new Label(8,2,linkMan.getName()));		 
			 noCodeSheet.addCell(new Label(0,3,"客户项目类型："));
			 if(cmaininfo.getCustomereventtype()!=null){
				 noCodeSheet.addCell(new Label(2,3,typeManageService.getYXTypeManage(1007L, cmaininfo.getCustomereventtype()).getTypeName()));
			 }
		     noCodeSheet.addCell(new Label(0,4,"甲方的合同号："));
			 noCodeSheet.addCell(new Label(2,4,cmaininfo.getPartyAConId()));
			 noCodeSheet.addCell(new Label(6,4,"甲方的项目工程编号："));
			 noCodeSheet.addCell(new Label(8,4,cmaininfo.getPartyAProId()));
			 ///////////////合同信息////////////////////////
			 noCodeSheet.addCell(new Label(0,5,"合同名称："));
			 noCodeSheet.addCell(new Label(2,5,cmaininfo.getConName()));
			 noCodeSheet.addCell(new Label(6,5,"主项目部门："));
			 noCodeSheet.addCell(new Label(8,5,typeManageService.getYXTypeManage(1018L, cmaininfo.getMainItemDept()).getTypeName()));
			 noCodeSheet.addCell(new Label(0,6,"预决算信息："));
			 if(("0").equals(cmaininfo.getFinalAccount())){
				 noCodeSheet.addCell(new Label(2,6,"非预决算"));
			 }else{
				 noCodeSheet.addCell(new Label(2,6,"预决算"));
			 }
			 noCodeSheet.addCell(new Label(6,6,"主项目负责人："));
			 noCodeSheet.addCell(new Label(8,6,cmaininfo.getMainItemPeople()));
			 noCodeSheet.addCell(new Label(0,7,"合同类型："));
			 noCodeSheet.addCell(new Label(2,7,typeManageService.getYXTypeManage(1020L,cmaininfo.getContractType()).getTypeName()));
			 noCodeSheet.addCell(new Label(6,7,"合同性质："));
			 noCodeSheet.addCell(new Label(8,7,typeManageService.getYXTypeManage(1019L,cmaininfo.getConType()).getTypeName()));
			 noCodeSheet.addCell(new Label(0,8,"基准："));
			 noCodeSheet.addCell(new Label(6,8,"合同金额："));
			 if("1".equals(cmaininfo.getStandard())){
				 noCodeSheet.addCell(new Label(2,8,"含税"));
				 noCodeSheet.addCell(new Number(8,8,cmaininfo.getConTaxTamount()));
			 }else{
			     noCodeSheet.addCell(new Label(2,8,"不含税"));
			     noCodeSheet.addCell(new Number(8,8,cmaininfo.getConNoTaxTamount()));
			 }
			 noCodeSheet.addCell(new Label(0,9,"货币单位："));
			 noCodeSheet.addCell(new Label(2,9,typeManageService.getYXTypeManage(1015L,cmaininfo.getCopeck()).getTypeName()));
			 noCodeSheet.addCell(new Label(6,9,"基准汇率："));
			 noCodeSheet.addCell(new Number(8,9,cmaininfo.getBaserate()));
			 noCodeSheet.addCell(new Label(0,10,"合同签订日期："));
			 if(cmaininfo.getConSignDate()!=null){
				 noCodeSheet.addCell(new Label(2,10,cmaininfo.getConSignDate().toString()));
			 }
			 noCodeSheet.addCell(new Label(6,10,"合同起始日期："));
			 if(cmaininfo.getConStartDate()!=null){
				 noCodeSheet.addCell(new Label(8,10,cmaininfo.getConStartDate().toString()));
			 }
			 noCodeSheet.addCell(new Label(0,11,"合同结束日期："));
			 if(cmaininfo.getConEndDate()!=null){
				 noCodeSheet.addCell(new Label(2,11,cmaininfo.getConEndDate().toString()));
			 }
			 noCodeSheet.addCell(new Label(6,11,"退税："));
			 if(cmaininfo.getConDrawback()){
				 noCodeSheet.addCell(new Label(8,11,"是"));
			 }else{
				 noCodeSheet.addCell(new Label(8,11,"否"));
			 }
			 noCodeSheet.addCell(new Label(0,12,"中标合同："));
			 if(cmaininfo.getConBid()){
				 noCodeSheet.addCell(new Label(2,12,"是"));
			 }else{
				 noCodeSheet.addCell(new Label(2,12,"否"));
			 }
			 noCodeSheet.addCell(new Label(6,12,"纳入年度运维合同："));
			 if(cmaininfo.getIntoYearCon()){
				 noCodeSheet.addCell(new Label(8,12,"是"));
			 }else{
				 noCodeSheet.addCell(new Label(8,12,"否"));
			 }
			 /////////////////////合同费用组成///////////////////////
	         noCodeSheet.addCell(new Label(0,13,"合同费用组成："));
			 noCodeSheet.addCell(new Label(0,14,"费用名称"));
			 noCodeSheet.addCell(new Label(3,14,"费用总金额"));
			 noCodeSheet.addCell(new Label(6,14,"开票类型"));
	         List<ContractMaininfoPart> mainPart = commonService.list(" from ContractMaininfoPart where contractmainid = ? ", mainid);
			 for(int i=1;i<=mainPart.size();i++){
				 noCodeSheet.addCell(new Label(0,14+i,typeManageService.getYXTypeManage(1012L,mainPart.get(i-1).getMoneytype()).getTypeName()));
			     noCodeSheet.addCell(new Number(3,14+i,Double.valueOf(mainPart.get(i-1).getMoney().toString())));
			     noCodeSheet.addCell(new Label(6,14+i,typeManageService.getYXTypeManage(1004L,mainPart.get(i-1).getTicketType()).getTypeName()));		     
			 }
			 allrows=14+mainPart.size();
			 /////////////////////合同项目/////////////////////////////
			 if("1".equals(cmaininfo.getContractType())){
				 noCodeSheet.addCell(new Label(0,15+mainPart.size(),"项目信息："));
				 String iteminfosql = " select cim.itemResDept,cim.itemResDeptP,cmp.moneytype,cii.conItemAmountWithTax from" +
				 		" ContractItemMaininfo cim,ContractItemInfo cii,ContractMaininfoPart cmp " +
				 		" where cii.contractItemMaininfo = cim.conItemMinfoSid " +
				 		" and cii.mainInfoPartId = cmp.id " +
				 		" and cim.contractMainInfo = "+mainid+" ";
				 noCodeSheet.addCell(new Label(0,16+mainPart.size(),"负责部门"));
				 noCodeSheet.addCell(new Label(3,16+mainPart.size(),"负责人"));
				 noCodeSheet.addCell(new Label(6,16+mainPart.size(),"费用名称"));
				 noCodeSheet.addCell(new Label(9,16+mainPart.size(),"金额"));
				 List<Object[]> itemInfo = commonService.list(iteminfosql);
				 for(int i=1;i<=itemInfo.size();i++){			 
					noCodeSheet.addCell(new Label(0,16+mainPart.size()+i,typeManageService.getYXTypeManage(1018L,itemInfo.get(i-1)[0].toString()).getTypeName()));
				    noCodeSheet.addCell(new Label(3,16+mainPart.size()+i,itemInfo.get(i-1)[1].toString()));
				    noCodeSheet.addCell(new Label(6,16+mainPart.size()+i,typeManageService.getYXTypeManage(1012L,itemInfo.get(i-1)[2].toString()).getTypeName()));
				    noCodeSheet.addCell(new Number(9,(16+mainPart.size()+i),Double.valueOf(itemInfo.get(i-1)[3].toString())));
				 }
		     allrows=16+mainPart.size()+itemInfo.size();
			 }
			 ///////////////////合同阶段//////////////////////////////////
			 noCodeSheet.addCell(new Label(0,allrows+1,"阶段信息："));
			 String stageinfosql=" select cis.itemStageName,cmp.moneytype,cisp.stageAmout,cisp.makeInvoiceDate" +
			 		             " from ContractItemStage cis,ContractItemStagePlan cisp,ContractMaininfoPart cmp " +
			 		             " where cisp.contractItemStage.conIStageSid = cis.conIStageSid " +
			 		             " and cisp.contractMaininfoPart.id = cmp.id" +
			 		             " and cis.contractMainSid = "+mainid+" ";
			 noCodeSheet.addCell(new Label(0,allrows+2,"阶段名称"));
			 noCodeSheet.addCell(new Label(3,allrows+2,"费用名称"));
			 noCodeSheet.addCell(new Label(6,allrows+2,"金额"));
			 noCodeSheet.addCell(new Label(9,allrows+2,"计划开票日期"));
			 List<Object[]> stageInfo = commonService.list(stageinfosql);
			 for(int i=1;i<=stageInfo.size();i++){			 
					noCodeSheet.addCell(new Label(0,allrows+2+i,typeManageService.getYXTypeManage(1023L,stageInfo.get(i-1)[0].toString()).getTypeName()));
					noCodeSheet.addCell(new Label(3,allrows+2+i,typeManageService.getYXTypeManage(1012L,stageInfo.get(i-1)[1].toString()).getTypeName()));
				    noCodeSheet.addCell(new Number(6,(allrows+2+i),Double.valueOf(stageInfo.get(i-1)[2].toString())));
					noCodeSheet.addCell(new Label(9,allrows+2+i,DateUtil.format((Date)stageInfo.get(i-1)[3],"yyyy-MM-dd")));
			  }
			 allrows = allrows+2+stageInfo.size();
			 ////////////////////////开票收款计划/////////////////////////////////////
			 List<InitContractBillpro> initBillList = commonService.list(" from InitContractBillpro where conMainInfoSid = ? ", mainid); 
			 noCodeSheet.addCell(new Label(0,allrows+1,"开票收款计划：")); 
			 noCodeSheet.addCell(new Label(0,allrows+2,"合同阶段"));
			 noCodeSheet.addCell(new Label(3,allrows+2,"负责部门"));
			 noCodeSheet.addCell(new Label(6,allrows+2,"费用名称"));
			 noCodeSheet.addCell(new Label(9,allrows+2,"计划开票日期"));
			 noCodeSheet.addCell(new Label(12,allrows+2,"计划收款日期 "));
			 noCodeSheet.addCell(new Label(15,allrows+2,"开票类型"));
			 noCodeSheet.addCell(new Label(18,allrows+2,"开票金额"));
			 StringBuffer planListSql=new StringBuffer();
			 planListSql.append("select cis.contractItemStage.itemStageName, ");
			 if("1".equals(cmaininfo.getContractType())){
				planListSql.append(" cim.itemResDept,"); 
			 }else{
				planListSql.append(" cmain.mainItemDept,");
			 }
			 planListSql.append(" cis.contractMaininfoPart.moneytype,icb.initBillDate,icb.initReceDate,icb.billType,icb.initBillAmount ");
			 planListSql.append(" from InitContractBillpro icb,ContractItemStagePlan cis , ");
			 if("1".equals(cmaininfo.getContractType())){
				planListSql.append(" ContractItemMaininfo cim "); 
			 }else{
				planListSql.append(" ContractMainInfo cmain ");
			 }
			 planListSql.append(" where icb.conItemStage = cis.contractItemStage.conIStageSid  ");
			 if("1".equals(cmaininfo.getContractType())){
				planListSql.append(" and icb.conItemInfo = cim.conItemMinfoSid "); 
			 }else{
				planListSql.append(" and icb.conMainInfoSid = cmain.conMainInfoSid ");
			 }
			 planListSql.append(" and icb.conMainInfoSid =" + mainid );
		     List<Object[]> planList=commonService.list(planListSql.toString());
		     for(int i=1;i<=planList.size();i++){
				 noCodeSheet.addCell(new Label(0,allrows+2+i,typeManageService.getYXTypeManage(1023L,planList.get(i-1)[0].toString()).getTypeName()));
				 noCodeSheet.addCell(new Label(3,allrows+2+i,typeManageService.getYXTypeManage(1018L,planList.get(i-1)[1].toString()).getTypeName()));
				 noCodeSheet.addCell(new Label(6,allrows+2+i,typeManageService.getYXTypeManage(1012L,planList.get(i-1)[2].toString()).getTypeName()));
				 noCodeSheet.addCell(new Label(9,allrows+2+i,DateUtil.format((Date)planList.get(i-1)[3],"yyyy-MM-dd")));
				 noCodeSheet.addCell(new Label(12,allrows+2+i,DateUtil.format((Date)planList.get(i-1)[4],"yyyy-MM-dd")));
				 noCodeSheet.addCell(new Label(15,allrows+2+i,typeManageService.getYXTypeManage(1004L,planList.get(i-1)[5].toString()).getTypeName()));
				 noCodeSheet.addCell(new Number(18,allrows+2+i,Double.valueOf(planList.get(i-1)[6].toString())));
		     }
		     allrows = allrows+2+planList.size();		 
			 ////////////////////自有产品信息//////////////////////////////////
		     List<ContractOwnProduce> selfProduceList = commonService.list(" from ContractOwnProduce where conMinfo = ? ", mainid);
			     if(selfProduceList.size()==0){
			     noCodeSheet.addCell(new Label(0,allrows+1,"自有产品信息："));
			     noCodeSheet.addCell(new Label(0,allrows+2,"产品名称"));
			     noCodeSheet.addCell(new Label(3,allrows+2,"数量"));
			     noCodeSheet.addCell(new Label(6,allrows+2,"单价"));
			     noCodeSheet.addCell(new Label(9,allrows+2,"总计金额")); 
				 for(int i=1;i<=selfProduceList.size();i++){
					 SelfProduction selfproduct = (SelfProduction)commonService.load(SelfProduction.class, selfProduceList.get(i-1).getOwnProduceId());
					 noCodeSheet.addCell(new Label(0,allrows+2+i,selfproduct.getRegisterName()));
					 noCodeSheet.addCell(new Number(3,allrows+2+i,selfProduceList.get(i-1).getConOwnProdAmount()));
					 noCodeSheet.addCell(new Number(6,allrows+2+i,selfProduceList.get(i-1).getConOwnProdPrice()));
					 Double allMoney=selfProduceList.get(i-1).getConOwnProdAmount()*selfProduceList.get(i-1).getConOwnProdPrice();
					 noCodeSheet.addCell(new Number(9,allrows+2+i,allMoney));			 
				 }	 
				 allrows = allrows+2+selfProduceList.size();
		     }
			 /////////////////////资料要求//////////////////////////////////////////
			 ContractOtherInfo otherinfo  = (ContractOtherInfo)commonService.uniqueResult(" from ContractOtherInfo where contractMainSid = ?", mainid);
		     noCodeSheet.addCell(new Label(0,allrows+1,"资料要求："));
		     noCodeSheet.addCell(new Label(0,allrows+2,"开工报告"));
		     noCodeSheet.addCell(new Label(3,allrows+2,"开工报告日期"));
		     noCodeSheet.addCell(new Label(6,allrows+2,"实物交接"));
		     noCodeSheet.addCell(new Label(9,allrows+2,"实物交接日期"));
		     noCodeSheet.addCell(new Label(12,allrows+2,"竣工验收"));
		     noCodeSheet.addCell(new Label(15,allrows+2,"竣工验收日期"));
		     if(otherinfo.getNeedPerativeReport()){
		    	 noCodeSheet.addCell(new Label(0,allrows+3,"需要"));
		    	 if(otherinfo.getPerativeReport()!=null){
		    		 noCodeSheet.addCell(new Label(3,allrows+3,otherinfo.getPerativeReport().toString()));
		    	 }
		     }else{
		    	 noCodeSheet.addCell(new Label(0,allrows+3,"不需要"));
		     }
		     if(otherinfo.getNeedRecivedThing()){
		    	 noCodeSheet.addCell(new Label(6,allrows+3,"需要"));
		    	 if(otherinfo.getRecivedThing()!=null){
		    		 noCodeSheet.addCell(new Label(9,allrows+3,otherinfo.getRecivedThing().toString()));	 
		    	 }
		     }else{
		    	 noCodeSheet.addCell(new Label(6,allrows+3,"不需要"));
		     }
		     if(otherinfo.getNeedFinallyReport()){
		    	 noCodeSheet.addCell(new Label(12,allrows+3,"需要"));
		    	 if(otherinfo.getFinallyReport()!=null){
		    		 noCodeSheet.addCell(new Label(15,allrows+3,otherinfo.getFinallyReport().toString()));
		    	 }
		     }else{
		    	 noCodeSheet.addCell(new Label(12,allrows+3,"不需要"));
		     }
			 noCodeSheet.addCell(new Label(0,allrows+4,"完工应交材料:"));
			 if(otherinfo.getFinallyLize()!=null){
			     String[] finallyLize = otherinfo.getFinallyLize().split(",");
			     for(int i=0;i<finallyLize.length;i++){
			    	 MaterialManager materialManager = (MaterialManager)commonService.uniqueResult(" from" +
			    	 		" MaterialManager where materialCode = ?",finallyLize[i]);
			    	 noCodeSheet.addCell(new Label(i+2,allrows+4,materialManager.getMaterialName()));
			     }
			 }else{
				 noCodeSheet.addCell(new Label(3,allrows+4,"无")); 
			 }		 
			 noCodeSheet.addCell(new Label(0,allrows+5,"备注"));
			 if(otherinfo.getOtherRemarks()!=null){
				 noCodeSheet.addCell(new Label(2,allrows+5,otherinfo.getOtherRemarks()));  
			 }else{
				 noCodeSheet.addCell(new Label(2,allrows+5,"无")); 
			 }
			 
			 
			 
			 workbook.write();
			 workbook.close();
			 /////////关闭输入流
			 os.flush();
			 os.close();
			 ////////
		     return null;
		 }*/
	
/*
	*//**
	 * 获得response输入流，用于下载
	 * @param fileName 下载框显示的文件名
	 * @return
	 * @throws IOException
	 *//*
	private OutputStream getResponseOutPut1(String fileName) throws IOException{
		 HttpServletResponse oResponse = ServletActionContext.getResponse();
		 // Set the content type
         oResponse.setContentType("application/x-msdownload");
         //Set the content-disposition
         oResponse.addHeader("Content-disposition", "attachment;filename="+fileName);
         //// Get the outputstream
         return oResponse.getOutputStream();
	}*/

	public Long getMainid() {
		return mainid;
	}

	public void setMainid(Long mainid) {
		this.mainid = mainid;
	}


	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}


	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}


	public ICommonService getCommonService() {
		return commonService;
	}


	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}




	public ContractMainInfo getCmaininfo() {
		return cmaininfo;
	}




	public void setCmaininfo(ContractMainInfo cmaininfo) {
		this.cmaininfo = cmaininfo;
	}




	public YXClientCode getCustomer() {
		return customer;
	}




	public void setCustomer(YXClientCode customer) {
		this.customer = customer;
	}




	public YXLinkMan getLinkMan() {
		return linkMan;
	}




	public void setLinkMan(YXLinkMan linkMan) {
		this.linkMan = linkMan;
	}




	public Date getToday() {
		return today;
	}




	public void setToday(Date today) {
		this.today = today;
	}




	public List<ContractMaininfoPart> getMainPart() {
		return mainPart;
	}




	public void setMainPart(List<ContractMaininfoPart> mainPart) {
		this.mainPart = mainPart;
	}




	public IContractService getContractservice() {
		return contractservice;
	}




	public void setContractservice(IContractService contractservice) {
		this.contractservice = contractservice;
	}




	public Employee getSelsmaninfo() {
		return selsmaninfo;
	}




	public void setSelsmaninfo(Employee selsmaninfo) {
		this.selsmaninfo = selsmaninfo;
	}




	public YXChargeMan getMainDepPerson() {
		return mainDepPerson;
	}




	public void setMainDepPerson(YXChargeMan mainDepPerson) {
		this.mainDepPerson = mainDepPerson;
	}




	public ContractOtherInfo getOtherinfo() {
		return otherinfo;
	}




	public void setOtherinfo(ContractOtherInfo otherinfo) {
		this.otherinfo = otherinfo;
	}




	public List<MaterialManager> getCheckedMaterialList() {
		return checkedMaterialList;
	}




	public void setCheckedMaterialList(List<MaterialManager> checkedMaterialList) {
		this.checkedMaterialList = checkedMaterialList;
	}




	public List<YXTypeManage> getItemdesigntypelist() {
		return itemdesigntypelist;
	}




	public void setItemdesigntypelist(List<YXTypeManage> itemdesigntypelist) {
		this.itemdesigntypelist = itemdesigntypelist;
	}




	public List getIteminfolist() {
		return iteminfolist;
	}




	public List<ContractOwnProduce> getOwnproductlist() {
		return ownproductlist;
	}




	public void setOwnproductlist(List<ContractOwnProduce> ownproductlist) {
		this.ownproductlist = ownproductlist;
	}




	public List<InitContractBillpro> getPlanlist() {
		return planlist;
	}




	public void setPlanlist(List<InitContractBillpro> planlist) {
		this.planlist = planlist;
	}








	public List<String[]> getItemlist() {
		return itemlist;
	}




	public void setItemlist(List<String[]> itemlist) {
		this.itemlist = itemlist;
	}




	public void setIteminfolist(List<Object[]> iteminfolist) {
		this.iteminfolist = iteminfolist;
	}




	public ArrayList getProductNameList() {
		return productNameList;
	}




	public void setProductNameList(ArrayList productNameList) {
		this.productNameList = productNameList;
	}




	public ArrayList getProductPrice() {
		return productPrice;
	}




	public void setProductPrice(ArrayList productPrice) {
		this.productPrice = productPrice;
	}




	public ArrayList getProductNumberList() {
		return productNumberList;
	}




	public void setProductNumberList(ArrayList productNumberList) {
		this.productNumberList = productNumberList;
	}




	public ArrayList getTicketlist() {
		return ticketlist;
	}




	public void setTicketlist(ArrayList ticketlist) {
		this.ticketlist = ticketlist;
	}




	public List<RealContractBillandRecePlan> getPlanlistR() {
		return planlistR;
	}




	public void setPlanlistR(List<RealContractBillandRecePlan> planlistR) {
		this.planlistR = planlistR;
	}




}
