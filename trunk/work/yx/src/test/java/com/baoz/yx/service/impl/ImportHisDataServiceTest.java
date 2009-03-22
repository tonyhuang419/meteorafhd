package com.baoz.yx.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.DateUtil;
import com.baoz.yx.YingXiaoBaseTest;
import com.baoz.yx.action.system.hisdata.CompareRealPlanAction;
import com.baoz.yx.action.system.hisdata.UpdateBillAndReceDateAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.service.IHarvestService;
import com.baoz.yx.service.IImportHisDataService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.IUserService;
import com.baoz.yx.tools.TaxChange;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

public class ImportHisDataServiceTest extends YingXiaoBaseTest {

	private IImportHisDataService importHisDataService;
	private IHarvestService harvestService;
	private ICommonService commonService;
	private IInvoiceService invoiceService;
	private IUserService userService;
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public IHarvestService getHarvestService() {
		return harvestService;
	}

	public void setHarvestService(IHarvestService harvestService) {
		this.harvestService = harvestService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public IImportHisDataService getImportHisDataService() {
		return importHisDataService;
	}

	public void setImportHisDataService(IImportHisDataService importHisDataService) {
		this.importHisDataService = importHisDataService;
	}

	@Override
	protected void prepareTestInstance() throws Exception {
		super.setAutowireMode(AUTOWIRE_BY_NAME);
		super.setDefaultRollback(false);
		super.prepareTestInstance();
	}
//	public void testCreate ()throws Exception
//	{
//		TaxChange.setInvoiceService(invoiceService);
//		CompareRealPlanAction a = new CompareRealPlanAction();
//		/////////////文件路径是自己定义的。在运行的时候确保该文件存在
//		a.setExcelFile(new File("D:\\import\\复件 戴燕.xls"));
//		a.setImportHisDataService(importHisDataService);
//		a.setService(commonService);
//		a.execute();
//	}
	/*public void testNihao()throws Exception
	{
		UpdateBillAndReceDateAction a = new UpdateBillAndReceDateAction();
		/////////////文件路径是自己定义的。在运行的时候确保该文件存在
		a.setExcelFile(new File("D:\\import\\复件 戴燕.xls"));
		a.setImportHisDataService(importHisDataService);
		a.setService(commonService);
		a.execute();
	}*/
	
//	public void testCancelPlanBill(){
//		importHisDataService.cancelPlanRece(1431, "PBSGA080490", "孙达");
//	}
	
//	public void testImportReveInfo()throws Exception{
//		TaxChange.setInvoiceService(invoiceService);
//		ImportReveInfoAction reveInfoAction=new ImportReveInfoAction();
//		reveInfoAction.setExcelFile(new File("D:\\import\\reveInfo\\副本08年9月收款明细表.xls"));
//		reveInfoAction.setImportHisDataService(importHisDataService);
//		reveInfoAction.execute();
//	}
//	public void testUpdateAndCal(){
//		String str="1736";
//		String[] op = StringUtils.split(str, ",");
//		for(int k=0;k<op.length;k++){
//			importHisDataService.updateAndCal(new Long(op[k]));
//		}
//	}
	/**
	 * 更新月度收款计划内外
	 */
//	public void testUpdateRecePlan(){
//		//
//		Date today = new Date();
//		//获得本月第一天
//		Date nowMonth = DateUtils.truncate(today, Calendar.MONTH);
//		
//		List<RealContractBillandRecePlan> pList = commonService.list("from RealContractBillandRecePlan p where p.realNowReceDate >= ?", nowMonth);
//		for (RealContractBillandRecePlan plan : pList) {
//			harvestService.updateMonthlyRecePlan(plan, plan.getRealNowReceDate());
//		}
//	}
	
//	public void testUpdateDepartmentCode(){
//		List<CodePair> codePairList = new ArrayList<CodePair>();
//		codePairList.add(new CodePair("N0","K0F0"));
//		codePairList.add(new CodePair("Z0","Z0C0"));
//		//codePairList.add(new CodePair("","Z0D0"));
//		codePairList.add(new CodePair("U0","Z0E0"));
//		codePairList.add(new CodePair("Q0","Q0C0"));
//		codePairList.add(new CodePair("Y0","Q0D0"));
//		codePairList.add(new CodePair("R0","R0C0"));
//		//codePairList.add(new CodePair("","R0D0"));
//		codePairList.add(new CodePair("20","R0E0"));
//		//codePairList.add(new CodePair("","R0F0"));
//		codePairList.add(new CodePair("K0","K0C0"));
//		codePairList.add(new CodePair("L0","K0D0"));
//		codePairList.add(new CodePair("M0","K0E0"));
//		//codePairList.add(new CodePair("","R0G0"));
//		codePairList.add(new CodePair("T0","T0C0"));
//		codePairList.add(new CodePair("P0","P0C0"));
//		codePairList.add(new CodePair("X0","P0D0"));
//		for (CodePair codePair : codePairList) {
//			commonService.executeUpdate("update YXChargemanDepartment cd set cd.departmentid = ? where cd.departmentid = ? ", codePair.getNewCode(), codePair.getOldCode());
//			commonService.executeUpdate("update ContractMainInfo c set c.mainItemDept = ? where c.mainItemDept = ? ", codePair.getNewCode(), codePair.getOldCode());
//			commonService.executeUpdate("update ContractItemMaininfo cim set cim.itemResDept = ? where cim.itemResDept = ? ", codePair.getNewCode(), codePair.getOldCode());
//			commonService.executeUpdate("update YXTypeManage tm set tm.typeSmall = ? where tm.typeBig = 1018 and tm.typeSmall = ? ", codePair.getNewCode(), codePair.getOldCode());
//		}
//      typeManage有缓存，需要重启服务器
//	}
//	private class CodePair{
//		private String oldCode;
//		private String newCode;
//		
//		public CodePair(String oldCode, String newCode) {
//			super();
//			this.oldCode = oldCode;
//			this.newCode = newCode;
//		}
//		public String getOldCode() {
//			return oldCode;
//		}
//		public void setOldCode(String oldCode) {
//			this.oldCode = oldCode;
//		}
//		public String getNewCode() {
//			return newCode;
//		}
//		public void setNewCode(String newCode) {
//			this.newCode = newCode;
//		}
//	}
}
