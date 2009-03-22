package com.baoz.yx.action.personnelManager;


import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.baoz.yx.utils.UserUtils;

/**
 *	员工管理信息
 *  
 */

@Results( { 
		@Result( name = "personnelManager", value = "/WEB-INF/jsp/personnelManager/personnelInfoManager.jsp"),
		@Result(name = "showSelClient", value = "/WEB-INF/jsp/personnelManager/SearchClient.jsp"),
		@Result(name = "select", value = "/WEB-INF/jsp/personnelManager/SearchClient.jsp"),
		@Result(name = "clientSelected", value = "/WEB-INF/jsp/personnelManager/clientSelected.jsp"),
		@Result(name = "showCha", value = "/WEB-INF/jsp/personnelManager/searchPersonnel.jsp")})
public class SelectPerQueryAction extends DispatchAction implements ServletRequestAware{

	@Autowired
	@Qualifier("queryService")
	private IQueryService 			queryService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 	typeManageService;
	@Autowired
	@Qualifier("commonService")
	private ICommonService 			service;
	private List<YXTypeManage>		clientNIDList;
	private List<YXOEmployeeClient>	empClients;
	private Employee				emp;
	private ServletRequest			request;
	private YXClientCode			yc;
	private PageInfo 				info;
	private String					clientName;     //用户名称
	private String 					userName;		//用户登陆名称
	private	String 					cName;			//客户名
	private Long 					tName;			//客户性质	
	private Long 					perId;			//获取用户id
    private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"SelectPerQueryParameters",this,new String[]{"userName","clientName"});

	@Override
	public String doDefault(){
		this.logger.info("员工管理信息");
		StringBuffer str = new StringBuffer();
		String hqlMain="select e, (select ot from OrganizationTree ot where ot.id=e.position ) from Employee e where e.is_active='1'";
		String hql="select count(*) from Employee e where e.is_active='1'";
		ParameterUtils.prepareParameters(holdTool);
		if (StringUtils.isNotBlank(userName)) {
			str.append(" and e.username like '%").append(userName+"%'");
		}
		if (StringUtils.isNotBlank(clientName)) {
			str.append(" and e.name like '%").append(clientName+"%'");
		}
		str.append(" order by e.id desc");		
		info = ParameterUtils.preparePageInfo(info, "selectPerQueryPage");
		info = queryService.listQueryResult(hql+str,hqlMain+str, info);
		return "personnelManager";
	}
	/**
	 *	显示客户查询页面
	 *  
	 */
	public String showCha()
	{
		logger.info("查询用户页面");
		return "showCha";
	}
	
	/**
	 *	显示查询客户页面
	 *  
	 *  
	 */
	public String showSelClient()
	{
		logger.info("显示客户查询");		
		return selectClient();
	}
	
	/**
	 *	查询客户
	 *  
	 */
	public String selectClient()
	{
		logger.info("根据模糊查询显示结果");		
		StringBuffer str = new StringBuffer();
		str.append(" from YXClientCode yc where yc.is_active=1 ");
		if (StringUtils.isNotBlank(cName)) {
			str.append(" and yc.name like '%").append(cName+"%'");
		}
		if (tName!=null&&!"".equals(tName)) {
			str.append(" and yc.clientNID =").append(tName);
		}
		str.append(" order by yc.id");				
		clientNIDList=typeManageService.getYXTypeManage(1001L);
		info = queryService.listQueryResult(str.toString(), info);
		return "select";
	}

	/**
	 * 显示关联客户信息
	 *  
	 *  @exception
	 *  
	 */
	public String associateClient()
	{
		logger.info("关联客户");	
			if(empClients!=null){
				for (YXOEmployeeClient empClient : empClients) {
					if(empClient.getCli() != null){
						System.out.println(empClient.getCli().getId());
						System.out.println(empClient.getExp().getId());
						//判断关联是否存在
						List<Object> cliList=service.list(" from YXOEmployeeClient empCli where empCli.cli = " + empClient.getCli().getId() +
								"and empCli.exp = " + empClient.getExp().getId());						
						if(cliList.size() == 0){
							Long uid = UserUtils.getUser().getId();
							empClient.setIs_active("1");
							empClient.setById(uid);
							empClient.setUpdateBy(new Date());
							service.save(empClient);
							logger.info("+++++++保存成功+++++++");
							
						}
					}else{
						System.out.println(empClient);
					}
				}
			}
		return "clientSelected";
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

	public String getClientName() {
		return clientName;
	}


	public void setClientName(String clientName) {
		this.clientName = clientName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCName() {
		return cName;
	}

	public void setCName(String name) {
		cName = name;
	}



	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getClientNIDList() {
		return clientNIDList;
	}

	public void setClientNIDList(List<YXTypeManage> clientNIDList) {
		this.clientNIDList = clientNIDList;
	}

	public YXClientCode getYc() {
		return yc;
	}

	public void setYc(YXClientCode yc) {
		this.yc = yc;
	}

	public Long getTName() {
		return tName;
	}

	public void setTName(Long name) {
		tName = name;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public List<YXOEmployeeClient> getEmpClients() {
		return empClients;
	}

	public void setEmpClients(List<YXOEmployeeClient> empClients) {
		this.empClients = empClients;
	}

	public Long getPerId() {
		return perId;
	}

	public void setPerId(Long perId) {
		this.perId = perId;
	}

}

