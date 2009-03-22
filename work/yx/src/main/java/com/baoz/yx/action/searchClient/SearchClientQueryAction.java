package com.baoz.yx.action.searchClient;

import java.util.List;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.components.json.annotations.JSON;
import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.append.helper.StringAppender;
import com.baoz.yx.utils.UserUtils;

/**
 * 联系人性质查询操作
 */
@Results( {
		@Result(name = "setClient", value = "/WEB-INF/jsp/sellbefore/sellBeforeList.jsp"),
		@Result(name = "showClient", value = "/WEB-INF/jsp/system/searchClient/showClient.jsp"),
		@Result(name = "searchClient", value = "/WEB-INF/jsp/system/searchClient/showClient.jsp"), 
		@Result(name = "showClientY", value = "/WEB-INF/jsp/system/searchClient/showClientY.jsp"),
		@Result(name = "searchClientSelect", value = "/WEB-INF/jsp/system/searchClient/showSelectClient.jsp")
})
public class SearchClientQueryAction extends DispatchAction {
	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;
	@Autowired
	@Qualifier("queryService")
	private IQueryService 		queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typemanageservice;
	private List 			    clientConditionList;	
	private List				projectNameList;
	private PageInfo 			info;	
	private YXClientCode 		ycc;
	private String 				cname;
	private List<Object> 		list;
	private List<String> 		showList;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("获取客户");		
		Long uid = UserUtils.getUser().getId();
		clientConditionList = commonService.list("select d.id,d.name,count(*) from " +
				"YXClientCode d,ContractBeforeSell c " +
				"where c.is_active!=0  " +
				"and d.is_active != '0' " +
				"and d.id=c.customerId " +
				" and not exists (  select 1 from ContractMainInfo cmi where cmi.preConSysid = c.id   ) " +
				"and c.byId = " + uid +
				"group by d.id,d.name"); 
		projectNameList = commonService.list("select c.id,c.projectName,c.customerId from ContractBeforeSell c where c.is_active!='0'   " +
				" and not exists (  select 1 from ContractMainInfo cmi where cmi.preConSysid = c.id   )  and  c.byId =" + uid);	
//		logger.info("projectNameList的长度有"+projectNameList.size());
//		logger.info("info的总长度为"+clientConditionList.size());
		return "setClient";
	}
	/**
	 * 显示查询客户页面
	 * @return
	 * @throws Exception
	 */
	public String showClient() throws Exception {
		return "showClient";
	}
	/**
	 * 获取模糊查询客户
	 * @return
	 * @throws Exception
	 */
	public String getClientList() throws Exception {
		StringAppender hql = new StringAppender();
		hql.append("from YXClientCode d where d.id not in(0) and d.is_active!=0 ");
		hql.appendIfNotEmpty(" and d.fullName like '%"+cname+"%' ", cname);
//		hql.append(" order by nlssort(d.name,'NLS_SORT=SCHINESE_PINYIN_M')  ");
		if(info==null){
			info=new PageInfo();
		}
		info.setPageSize(10);
		info=queryService.listQueryResult(hql.toString(),info);
		return "searchClient";
	}
	/**
	 * 筛选客户
	 * @return
	 * @throws Exception
	 */
	public String getSelectClientlist() throws Exception
	{
		StringAppender hql = new StringAppender();
		hql.append("from YXClientCode d where d.id not in(0) and d.is_active!=0 ");
		hql.appendIfNotEmpty(" and d.fullName like '%"+cname+"%' ", cname);
		hql.append(" and ( d.isEventUnit = '0' or d.isEventUnit is null ) ");
//		hql.append(" order by nlssort(d.name,'NLS_SORT=SCHINESE_PINYIN_M')  ");
		if(info==null){
			info=new PageInfo();
		}
		info.setPageSize(10);
		info=queryService.listQueryResult(hql.toString(),info);
		return "searchClient";
	}
	public String getClientListY() throws Exception {
		StringAppender hql = new StringAppender();
		hql.append("from YXClientCode d where d.id not in(0) and d.is_active!=0 ");
		hql.appendIfNotEmpty(" and d.name like '%"+cname+"%' ", cname);
		if(info==null){
			info=new PageInfo();
		}
		info.setPageSize(10);
		info=queryService.listQueryResult(hql.toString(),info);
		return "showClientY";
	}
	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
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

	public YXClientCode getYcc() {
		return ycc;
	}

	public void setYcc(YXClientCode ycc) {
		this.ycc = ycc;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}
	@JSON(name = "arrList")
	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}

	public List<String> getShowList() {
		return showList;
	}

	public void setShowList(List<String> showList) {
		this.showList = showList;
	}
	public List getClientConditionList() {
		return clientConditionList;
	}
	public void setClientConditionList(List clientConditionList) {
		this.clientConditionList = clientConditionList;
	}
	public List getProjectNameList() {
		return projectNameList;
	}
	public void setProjectNameList(List projectNameList) {
		this.projectNameList = projectNameList;
	}
	public IYXTypeManageService getTypemanageservice() {
		return typemanageservice;
	}
	public void setTypemanageservice(IYXTypeManageService typemanageservice) {
		this.typemanageservice = typemanageservice;
	}

}
