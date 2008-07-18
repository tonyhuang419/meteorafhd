package com.baoz.yx.action.programEconomy;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.programEconomy.YXOProgramEconomy;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

/**
 * 工程经济管理查询操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/programEconomy/economyManageList.jsp")
public class ProgramEconomyManageQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	private PageInfo info;

	private String money1;
	private String money2;

	private String str3;
	private String str4;

	private Long id;
	private Long sid;

	private String str7;
	private String str8;
	private String str9;// 申请状态 工程主体信息中
	private String str10;
	private HttpServletRequest request = null;
	private Long clientId;
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
			"ProgramEconomyManageParameters", this, new String[] { "money1",
					"money2", "str3", "str4", "clientId", "sid", "str7",
					"str8", "str9", "str10" });

	@Override
	public String doDefault() throws Exception {
		ParameterUtils.prepareParameters(holdTool);

		UserDetail user = UserUtils.getUserDetail();
		// true组长
		if (!DepartmentUtils.isTeamLeader(user.getPosition()
				.getOrganizationCode())) {
			sid = user.getUser().getId();
		}else if(StringUtils.isBlank(str10)){
			//是组长，只查本组的
			str10 = user.getPosition().getOrganizationCode();
		}

		String hqlms;
		if (str9 == null || "".equals(str9))
			hqlms = "select pe,s,e.name,orgTree from YXOProgramEconomy pe left join pe.sectionInfo as s,Employee e,OrganizationTree orgTree where (s.id ="
					+ "(select max(s1.id) from YXOSectionInfo s1 where s1.economy = pe) or s.id is null) "
					+ "and e.id = pe.exployeeId and pe.is_active=1 and (pe.state=3 or pe.state=4) and e.position=orgTree.id";
		else
			hqlms = "select pe,s,e.name,orgTree from YXOProgramEconomy pe left join pe.sectionInfo as s,Employee e,OrganizationTree orgTree where (s.id ="
					+ "(select max(s1.id) from YXOSectionInfo s1 where s1.economy = pe) or s.id is null) "
					+ "and e.id = pe.exployeeId and e.is_active=1 and pe.is_active=1 and pe.state="
					+ str9 + " and e.position=orgTree.id  ";
		String economyManageHql = systemService.getSqlB(hqlms, money1, money2,
				str3, str4, clientId, sid, str7, str8, str9, str10);

		info = queryService.listQueryResult(economyManageHql, info);
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

	public String getMoney1() {
		return money1;
	}

	public void setMoney1(String money1) {
		this.money1 = money1;
	}

	public String getMoney2() {
		return money2;
	}

	public void setMoney2(String money2) {
		this.money2 = money2;
	}

	public String getStr3() {
		return str3;
	}

	public void setStr3(String str3) {
		this.str3 = str3;
	}

	public String getStr4() {
		return str4;
	}

	public void setStr4(String str4) {
		this.str4 = str4;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getStr7() {
		return str7;
	}

	public void setStr7(String str7) {
		this.str7 = str7;
	}

	public String getStr8() {
		return str8;
	}

	public void setStr8(String str8) {
		this.str8 = str8;
	}

	public String getStr9() {
		return str9;
	}

	public void setStr9(String str9) {
		this.str9 = str9;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getStr10() {
		return str10;
	}

	public void setStr10(String str10) {
		this.str10 = str10;
	}

}
