/**
 * 
 */
package com.baoz.yx.action.contract.contractProject;

import java.io.OutputStream;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.DateUtil;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.DownloadUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

/**
 * @author wq
 *
 */
public class DownLoadNoCodeProjectAction extends DispatchAction{
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	
	 public String downLoad() throws Exception{
		 //无项目号合同
		 List<Object[]> noProjectCodeList = getNoCodeProjectList();
		 //获得output
		 OutputStream os = DownloadUtils.getResponseOutput("无项目号的合同项目.xls");
		 //导成excel
		 WritableWorkbook workbook = Workbook.createWorkbook(os);
		 //插入表格
		 WritableSheet noCodeSheet = workbook.createSheet("无项目号的项目", 0);
		 //插入单元格
		 noCodeSheet.addCell(new Label(0,0,"项目号"));
		 noCodeSheet.addCell(new Label(1,0,"项目名称"));
		 noCodeSheet.addCell(new Label(2,0,"项目状态"));
		 noCodeSheet.addCell(new Label(3,0,"项目经理"));
		 noCodeSheet.addCell(new Label(4,0,"客户名称"));
		 noCodeSheet.addCell(new Label(5,0,"销售人员"));
		 noCodeSheet.addCell(new Label(6,0,"项目金额"));
		 noCodeSheet.addCell(new Label(7,0,"协议编号"));
		 noCodeSheet.addCell(new Label(8,0,"项目组织"));
		 noCodeSheet.addCell(new Label(9,0,"合同金额"));
		 noCodeSheet.addCell(new Label(10,0,"协议起始日期"));
		 noCodeSheet.addCell(new Label(11,0,"销售组织"));
		 noCodeSheet.addCell(new Label(12,0,"风险管理"));
		 /////////////////
		 for(int i=0;i<noProjectCodeList.size();i++){
			Object[] conMain=(Object[])noProjectCodeList.get(i);
			ContractMainInfo con =((ContractMainInfo)conMain[0]);
			Employee emp =((Employee)conMain[1]);
			YXClientCode yc=((YXClientCode)conMain[2]);
			ContractItemMaininfo cm = ((ContractItemMaininfo)conMain[3]);
			 int rowNum = i + 1;
			 noCodeSheet.addCell(new Label(0,rowNum,""));
			 noCodeSheet.addCell(new Label(1,rowNum,""));       //项目名称   添加的是合同名称
			 noCodeSheet.addCell(new Label(2,rowNum,""));
			 noCodeSheet.addCell(new Label(3,rowNum,""));
			 noCodeSheet.addCell(new Label(4,rowNum,yc.getFullName()));        // 客户名称
			 noCodeSheet.addCell(new Label(5,rowNum,emp.getName()));		//销售员
			 java.lang.Number itemAmount = (java.lang.Number) commonService.uniqueResult("select sum(ii.conItemAmountWithTax) from ContractItemInfo ii where ii.contractItemMaininfo = ? ",cm.getConItemMinfoSid());
			 noCodeSheet.addCell(new Number(6,rowNum,itemAmount.doubleValue()));
			 noCodeSheet.addCell(new Label(7,rowNum,con.getConId()));
			 noCodeSheet.addCell(new Label(8,rowNum,cm.getItemResDept()+"_"+typeManageService.getYXTypeManage(1018L, cm.getItemResDept()).getTypeName()));
			 //////////////////////
//			 NumberFormat numberFormat = new NumberFormat("#,##0.00");
//			 WritableCellFormat cellFormat = new WritableCellFormat(numberFormat);
			 Number conAmount = null;
			 if(con.getStandard().equals("1")){
				 conAmount = new Number(9,rowNum,con.getConTaxTamount());
			 }else{
				 conAmount = new Number(9,rowNum,con.getConNoTaxTamount());
			 }
			 noCodeSheet.addCell(conAmount);	       //合同金额
			 //////////////////////
			 noCodeSheet.addCell(new Label(10,rowNum,DateUtil.format(con.getConStartDate(), "yyyy-MM-dd")));      //协议起始日期
			 noCodeSheet.addCell(new Label(11,rowNum,""));
			 noCodeSheet.addCell(new Label(12,rowNum,""));
		}
		 noCodeSheet.setColumnView(7, 14);
		 noCodeSheet.setColumnView(8, 18);
		 noCodeSheet.setColumnView(9, 12);
		 noCodeSheet.setColumnView(10, 12);
		 workbook.write();
		 workbook.close();
		 /////////关闭输入流
		 DownloadUtils.closeResponseOutput();
		 ////////
	     return null;
	 }

	 /**
	  * 获得无项目号的项目,查询同ContractItemManagerAction.doDefault()
	  * @return
	  */
	private List<Object[]> getNoCodeProjectList() {
		StringBuffer sp = new StringBuffer();
		sp.append("select c,e,y,cm from ContractMainInfo c,Employee e,YXClientCode y,ContractItemMaininfo cm,OrganizationTree orgTree where c.saleMan=e.id and c.conCustomer=y.id and c.conMainInfoSid=cm.contractMainInfo and e.position=orgTree.id and c.conId is not null");
		UserDetail user = UserUtils.getUserDetail();
		sp.append(" and c.ContractType=1");
		String expId = null;
		String groupId = null;
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId()+"";
		}else if(StringUtils.isBlank(groupId)){
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		if (groupId != null && !"".equals(groupId)) {
			sp.append(" and orgTree.organizationCode like '").append(groupId+"%'");
		}
		if (expId != null && !expId.equals("")) {
			sp.append(" and e.id='" + expId + "'");
		}
		//无项目号
		sp.append(" and cm.conItemId IS NULL");
		//正式未关闭的合同
		sp.append(" and c.conState>=4 and c.conState<=9");
		return commonService.list(sp.toString());
	}
}
