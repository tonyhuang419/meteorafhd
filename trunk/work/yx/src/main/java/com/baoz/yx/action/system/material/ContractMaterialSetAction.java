package com.baoz.yx.action.system.material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.baoz.yx.entity.ContractMaterialSet;
import com.baoz.yx.entity.MaterialManager;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IMaterialService;
import com.baoz.yx.service.IYXClientCodeService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.ObjectPropertySessionHoldTool;
import com.baoz.yx.utils.ParameterUtils;
import com.opensymphony.xwork2.ActionContext;

@Results( {
		@Result(name = "contractMaterialList", value = "/WEB-INF/jsp/system/material/contractMaterialList.jsp"),
		@Result(name = "addEnter", value = "/WEB-INF/jsp/system/material/contractMaterial.jsp"),
		@Result(name = "updateEnter", value = "/WEB-INF/jsp/system/material/updateMaterialSet.jsp"),
		@Result(name = "showDetails", value = "/WEB-INF/jsp/system/material/materialSetDetails.jsp")
})
public class ContractMaterialSetAction extends DispatchAction implements ServletRequestAware{

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	@Autowired
	@Qualifier("yXClietCodeService")
	private IYXClientCodeService clientCodeService;
	
	@Autowired
	@Qualifier("materialService")
	private IMaterialService materialService;

	private PageInfo info;

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	private List<YXTypeManage> typeManageList;// 客户项目类型列表

	private ContractMaterialSet contractMaterial;

	private List<MaterialManager> materialManageList;// 应交材料列表

	private String[] customerMaterial;// 选择的应交材料的列表

	private Long oldCustomerName;// 上一次选择的客户

	private String cusName;// 客户名称

	private String oldCusProjectType;// 上一次选择的客户项目类型

	private Long mid;// 应交材料设置id
	
	private String queryCustomName;//查询条件，客户名称
	
	private ServletRequest request;
	
	private List<MaterialManager> materialList=new ArrayList<MaterialManager>();//显示已经选中的应交材料

	public List<MaterialManager> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<MaterialManager> materialList) {
		this.materialList = materialList;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public String getOldCusProjectType() {
		return oldCusProjectType;
	}

	public void setOldCusProjectType(String oldCusProjectType) {
		this.oldCusProjectType = oldCusProjectType;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public Long getOldCustomerName() {
		return oldCustomerName;
	}

	public void setOldCustomerName(Long oldCustomerName) {
		this.oldCustomerName = oldCustomerName;
	}

	public String[] getCustomerMaterial() {
		return customerMaterial;
	}

	public void setCustomerMaterial(String[] customerMaterial) {
		this.customerMaterial = customerMaterial;
	}

	public ContractMaterialSet getContractMaterial() {
		return contractMaterial;
	}

	public void setContractMaterial(ContractMaterialSet contractMaterial) {
		this.contractMaterial = contractMaterial;
	}

	public List<YXTypeManage> getTypeManageList() {
		return typeManageList;
	}

	public void setTypeManageList(List<YXTypeManage> typeManageList) {
		this.typeManageList = typeManageList;
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

	public String getQueryCustomName() {
		return queryCustomName;
	}

	public void setQueryCustomName(String queryCustomName) {
		this.queryCustomName = queryCustomName;
	}
	//查询条件
	private ObjectPropertySessionHoldTool holdTool = new ObjectPropertySessionHoldTool(
    		"MaterialSetQueryParams",this,new String[]{"queryCustomName"});
	/*
	 * 显示材料设置列表 (non-Javadoc)
	 * 
	 * @see com.baoz.core.web.struts2.DispatchAction#doDefault()
	 */
	@Override
	public String doDefault() throws Exception {
		ParameterUtils.prepareParameters(holdTool);
		StringBuffer hql = new StringBuffer("select material,clientCode.name from ContractMaterialSet material,YXClientCode clientCode where material.customerName=clientCode.id ");
		if(StringUtils.isNotBlank(queryCustomName)){
			hql.append(" and clientCode.name like '%"+queryCustomName+"%' ");
		}
		hql.append(" order by material.customerName ");
		//设置当前页，如果session中有值会用session中的当前页
		info = ParameterUtils.preparePageInfo(info, "applyBillQueryPage");
		info = queryService.listQueryResult(hql.toString(), info);
		if (this.getContractMaterialMap() != null) {
			this.getContractMaterialMap().clear();
		}
		return "contractMaterialList";
	}
	
	/**
	 *  添加应交材料设置跳转页面
	 * @return
	 * @throws Exception
	 */
	public String addEnter() throws Exception {
		this.getContractMaterialMap().clear();
		typeManageList = typeManageService.getYXTypeManage(1007L);
		materialManageList = commonService
				.list("from MaterialManager manage order by manage.sortOrder");
		return "addEnter";
	}

	/**
	 * 每一次选择一个项目类型时，都跳该页面，然后把上次记录保存在session里面去，同时去把第一次保存的值取出来
	 * @return
	 * @throws Exception
	 */
	public String tempAddEnter() throws Exception {
		typeManageList = typeManageService.getYXTypeManage(1007L);
		materialManageList = commonService
				.list("from MaterialManager manage order by manage.sortOrder");
		if (contractMaterial != null
				&& contractMaterial.getCustomerName() != null) {
			YXClientCode code = clientCodeService
					.getYXClientCode(contractMaterial.getCustomerName());
			if (code != null) {
				cusName = code.getName();
			}
		} else {
			cusName = "";
		}
		this.setSessionList();
		this.getSessionList();
		return "addEnter";
	}
	/**
	 * 修改保存。把session里面的值取出来，然后写入到数据库总，写入完毕后跳转到修改页面
	 * @return
	 * @throws Exception
	 */
	public String addContractMaterialSet() throws Exception {
		oldCusProjectType = contractMaterial.getCustomerProjectType();
		setSessionList();
		Map<String, ContractMaterialSet> materialMap = this
				.getContractMaterialMap();
		Iterator<String> iterator = materialMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			ContractMaterialSet m = materialMap.get(key);
			commonService.saveOrUpdate(m);
		}
		mid = this.getContractMaterialMap().get(contractMaterial.getCustomerName()+"/"+oldCusProjectType).getId();
		this.getContractMaterialMap().clear();
		return updateEnter();
	}
	/**
	 * 修改保存。把session里面的值取出来，然后写入到数据库总，写入完毕后跳转到列表页面
	 * @return
	 * @throws Exception
	 */
	public String addContractMaterialAndClose() throws Exception {
		oldCusProjectType = contractMaterial.getCustomerProjectType();
		setSessionList();
		Map<String, ContractMaterialSet> materialMap = this
				.getContractMaterialMap();
		Iterator<String> iterator = materialMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			ContractMaterialSet m = materialMap.get(key);
			commonService.saveOrUpdate(m);
		}
		this.getContractMaterialMap().clear();
		return this.doDefault();
	}

	/**
	 * 删除材料设置
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteContractMaterialSet() throws Exception {
	  	String ids=request.getParameter("idarr");
		String[] arr=StringUtils.split(ids,",");
		materialService.deleteMaterialSet(arr);
		return this.doDefault();
	}
	/**
	 * 修改材料设置页面跳转
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateEnter() throws Exception {
		Map<String, ContractMaterialSet> matMap = this
		.getContractMaterialMap();
		matMap.clear();
		typeManageList = typeManageService.getYXTypeManage(1007L);
		materialManageList = commonService
				.list("from MaterialManager manage order by manage.sortOrder");
		String hql = "from ContractMaterialSet materialSet where materialSet.id=?";
		contractMaterial = (ContractMaterialSet) commonService.uniqueResult(
				hql, mid);
		if (contractMaterial != null) {
			YXClientCode code = clientCodeService.getYXClientCode(contractMaterial.getCustomerName());
			//用户名称
			cusName = code.getName();
			String clientHql = "from ContractMaterialSet materialSet where materialSet.customerName=?";
			List<ContractMaterialSet> list = commonService.list(clientHql,
					contractMaterial.getCustomerName());
		
			//用户设置过的应交材料
			for (ContractMaterialSet contractMaterialSet : list) {
				String key = contractMaterialSet.getCustomerName() + "/"
						+ contractMaterialSet.getCustomerProjectType();
				matMap.put(key, contractMaterialSet);
			}
			//但前选中的应交材料
			customerMaterial = StringUtils.split(contractMaterial.getCustomerMaterial(), ",");
		}
		return "updateEnter";
	}
	/**
	 * 修改应交材料设置改变项目类型的时候把上次记录保存在session里面
	 * @return
	 * @throws Exception
	 */
	public String tempUpdateEnter() throws Exception {
		typeManageList = typeManageService.getYXTypeManage(1007L);
		materialManageList = commonService
				.list("from MaterialManager manage order by manage.sortOrder");
		this.setSessionList();
		this.getSessionList();
		print();
		return "updateEnter";
	}
	
	public String showDetails()throws Exception{
		String hql = "from ContractMaterialSet materialSet where materialSet.id=?";
		contractMaterial = (ContractMaterialSet) commonService.uniqueResult(
				hql, mid);
			YXClientCode code = clientCodeService.getYXClientCode(contractMaterial.getCustomerName());
			//用户名称
			cusName = code.getName();
			if(StringUtils.isNotBlank(contractMaterial.getCustomerMaterial())){
				String[] ar=StringUtils.split(contractMaterial.getCustomerMaterial(),",");
				String co="";
				for(int k=0;k<ar.length;k++){
					co+=",'"+ar[k]+"'";
				}
				materialList=commonService.list("from MaterialManager manage where manage.materialCode in ("+co.substring(1)+" ) order by manage.sortOrder ");
			}
		return "showDetails";
	}
	/**
	 * 把上次选择的客户项目类型的值保存在session中
	 * @throws Exception
	 */
	private void setSessionList() throws Exception {
		if (contractMaterial != null) {
			if (contractMaterial.getCustomerName() != null) {
				//用户没有改变
				if (contractMaterial.getCustomerName().equals(oldCustomerName)
						|| oldCustomerName == null) {
					String str_start = contractMaterial.getCustomerName()
							.toString();
					String oldtype = oldCusProjectType;
					//保存上次项目类型选中的值
					//有上次就保存上次的值
					if (oldCusProjectType != null
							&& !"".equals(oldCusProjectType)) {
						oldtype = oldCusProjectType;
					} else {
						//第一次没有上次，就保存自己
						oldtype = contractMaterial.getCustomerProjectType();
					}
					// 从session中取值
					Map<String, ContractMaterialSet> materialMap = this.getContractMaterialMap();
					ContractMaterialSet materialSet = materialMap.get(str_start + "/" + oldtype);
					// 不存在就创建
					if(materialSet == null){
						materialSet = new ContractMaterialSet();
						materialMap.put(str_start + "/" + oldtype, materialSet);
					}
					// 保存修改的值
					materialSet.setCustomerName(contractMaterial.getCustomerName());
					materialSet.setCustomerProjectType(oldtype);
					materialSet.setCustomerMaterial(StringUtils.join(customerMaterial,","));
				} else {
					if (this.getContractMaterialMap() != null) {
						this.getContractMaterialMap().clear();
					}
				}
			}
		}
	}
	/**
	 * 如果当前选中的项目类型名称在session里面有记录，就把改记录显示给客户
	 * @throws Exception
	 */
	private void getSessionList() throws Exception {
		if (contractMaterial != null) {
			if (contractMaterial.getCustomerName() != null) {
				String str_start = contractMaterial.getCustomerName()
						.toString();
				String str_end = "";
				str_end = contractMaterial.getCustomerProjectType();
				Map<String, ContractMaterialSet> materialMap = this
						.getContractMaterialMap();
				//取出设置
				ContractMaterialSet tempMaterial = materialMap.get(str_start
						+ "/" + str_end);
				if (tempMaterial != null) {
					String co = tempMaterial.getCustomerMaterial();
					customerMaterial = StringUtils.split(co, ",");
				} else {
					customerMaterial = null;
				}
			}
		}
	}
	/**
	 * 把Map里面的值保存在session里面去
	 * @return
	 */
	private Map<String, ContractMaterialSet> getContractMaterialMap() {
		Map<String, ContractMaterialSet> materialMap = (Map) ActionContext
				.getContext().getSession().get("allMaterialMap");
		if (materialMap == null) {
			materialMap = new HashMap<String, ContractMaterialSet>();
		}
		ActionContext.getContext().getSession().put("allMaterialMap",
				materialMap);
		return materialMap;
	}
	/**
	 * 在页面显示的时候判断是否被选中
	 * @param code
	 * @return
	 */
	public boolean isMaterialChecked(String code) {
		if (customerMaterial != null) {
			for (int i = 0; i < customerMaterial.length; i++) {
				String checkedCode = customerMaterial[i];
				if (StringUtils.equals(code, checkedCode)) {
					return true;
				}
			}
		}
		return false;
	}

	public String checkCustomerName() throws Exception {

		String hql = "from ContractMaterialSet material where material.customerName=?";
		List list = commonService.list(hql, contractMaterial.getCustomerName());
		if (list != null && list.size() > 0) {// 如果该客户名称在系统中已经存在，不能添加，返回false
			this.renderText("false");
		} else {// 如果该客户名称在系统中不存在，则可以继续添加。返回true
			this.renderText("true");
		}
		return null;
	}

	public List<MaterialManager> getMaterialManageList() {
		return materialManageList;
	}

	public void setMaterialManageList(List<MaterialManager> materialManageList) {
		this.materialManageList = materialManageList;
	}

	public IYXClientCodeService getClientCodeService() {
		return clientCodeService;
	}

	public void setClientCodeService(IYXClientCodeService clientCodeService) {
		this.clientCodeService = clientCodeService;
	}

	private void print() throws Exception {
		Map<String, ContractMaterialSet> materialMap = this
				.getContractMaterialMap();
		Iterator<String> iterator = materialMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			ContractMaterialSet m = materialMap.get(key);
			logger
					.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			logger.info("客户id：" + m.getCustomerName() + "项目类型："
					+ m.getCustomerProjectType() + "应交材料："
					+ m.getCustomerMaterial());
			}

	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		this.request=arg0;
		
	}

	public IMaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(IMaterialService materialService) {
		this.materialService = materialService;
	}


}
