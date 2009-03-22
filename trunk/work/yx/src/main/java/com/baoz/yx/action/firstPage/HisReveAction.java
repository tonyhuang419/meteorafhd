package com.baoz.yx.action.firstPage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.service.IFirstPageService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.GetMonthTool;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.Department;


@Results( {
	@Result(name = "hisReveFrame", value = "/WEB-INF/jsp/firstPage/hisReveInfo/hisReveFrame.jsp"),
	@Result(name = "hisReveSearch", value = "/WEB-INF/jsp/firstPage/hisReveInfo/hisReveSearch.jsp"),
	@Result(name = "hisReveRight", value = "/WEB-INF/jsp/firstPage/hisReveInfo/hisReveInfo.jsp")
})
public class HisReveAction extends DispatchAction {

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;

	@Autowired
	@Qualifier("firstPageService")
	private IFirstPageService firstPageService;


	private List<Object>        	yxClientCodeList;
	private List<Object> 			listExp; 			// 查询显示所有的销售员
	private List<Department>		groupList;	

	private PageInfo info;
	private String 	groupId;      //组别
	private Long 	expId;      //销售员

	private Date  reveStartDate;
	private Date  reveEndDate;

	private String  signHisReve  ;            //1 昨日到款  2 今日到款 3本月到款
	
	private Double reveSum;    //到款总计
	
	static final long MILLIS_IN_A_DAY = 1000*60*60*24; 

	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"searchHisReveInfo",this,new String[]{"groupId","expId","reveStartDate","reveEndDate"});


	@SuppressWarnings("unchecked")
	public String frame() throws Exception {
		processDate();
//		ParameterUtils.prepareParameters(holdTool);
		return "hisReveFrame";
	}


	@Override
	public String doDefault() throws Exception {
		yxClientCodeList = new ArrayList<Object>();
		groupList = UserUtils.getUserDetail().getDepartments();
		listExp = new ArrayList<Object>();
		return "hisReveSearch";
	}


	public String right() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		info = ParameterUtils.preparePageInfo(info, "fpHisReve");
		Object[] o = firstPageService.queryHisReveInfo(info, reveStartDate , reveEndDate , groupId, expId);
		info = (PageInfo)o[0];
		reveSum = (Double)o[1];
//		logger.info(reveSum);
		return "hisReveRight";
	}


	private void processDate(){
		if( signHisReve.equals("2")){
			reveStartDate = new Date();
			reveEndDate = new Date();		
		}
		else if ( signHisReve.equals("1")){
			reveStartDate = new Date(new Date().getTime() - MILLIS_IN_A_DAY);
			reveEndDate = new Date(new Date().getTime() - MILLIS_IN_A_DAY) ;
		}
		else if ( signHisReve.equals("3")){
			reveStartDate = GetMonthTool.getFirstDayOfMonth();
			reveEndDate = GetMonthTool.getLastDayOfMonth();
		}
		else if ( signHisReve.equals("4")){
			reveStartDate = new Date(new Date().getTime() - MILLIS_IN_A_DAY*2);
			reveEndDate = new Date(new Date().getTime() - MILLIS_IN_A_DAY*2) ;
		}
	}


	public List<Object> getYxClientCodeList() {
		return yxClientCodeList;
	}

	public void setYxClientCodeList(List<Object> yxClientCodeList) {
		this.yxClientCodeList = yxClientCodeList;
	}

	public List<Object> getListExp() {
		return listExp;
	}

	public void setListExp(List<Object> listExp) {
		this.listExp = listExp;
	}

	public List<Department> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Department> groupList) {
		this.groupList = groupList;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
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

	public String getSignHisReve() {
		return signHisReve;
	}

	public void setSignHisReve(String signHisReve) {
		this.signHisReve = signHisReve;
	}

	public ObjectPropertySessionHoldTool getHoldTool() {
		return holdTool;
	}

	public void setHoldTool(ObjectPropertySessionHoldTool holdTool) {
		this.holdTool = holdTool;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public IFirstPageService getFirstPageService() {
		return firstPageService;
	}

	public void setFirstPageService(IFirstPageService firstPageService) {
		this.firstPageService = firstPageService;
	}

	public Date getReveStartDate() {
		return reveStartDate;
	}

	public void setReveStartDate(Date reveStartDate) {
		this.reveStartDate = reveStartDate;
	}

	public Date getReveEndDate() {
		return reveEndDate;
	}

	public void setReveEndDate(Date reveEndDate) {
		this.reveEndDate = reveEndDate;
	}


	public Double getReveSum() {
		return reveSum;
	}


	public void setReveSum(Double reveSum) {
		this.reveSum = reveSum;
	}

}
