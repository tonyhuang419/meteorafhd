package com.baoz.yx.action.system.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Authority;

/**
 * 
 * @author shaoyx
 * 
 * Jun 12, 2008, 6:24:34 PM
 * 
 * todo: 权限列表
 */
@Result(name = "queryList", value = "/WEB-INF/jsp/system/manage/authorityList.jsp")
@ParentPackage("yx")
public class AuthorityQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	private PageInfo info;

	private Map<String, Authority> authorityMap = null;

	@Override
	public String doDefault() throws Exception {

		List<Authority> l = commonService.list("from Authority a");
		authorityMap = new HashMap<String, Authority>();
		// //////////
		for (int i = 0; i < l.size(); i++) {
			authorityMap.put(l.get(i).getCode(), l.get(i));
		}
		// /////////
		info = queryService.listQueryResult("from Authority a order by code", info);
		return QUERY_LIST;
	}

	public String getCodeDestPath(String code) {
		String destPath = "";
		if(code == null){
			return "";
		}
		for (int j = 0; j < (code.length() / 2); j++) {
			String fCode = code.substring(0, j * 2 + 2);
			Authority a = (Authority) authorityMap.get(fCode);
			destPath = destPath + a.getAuthorityDesc() + "->";
		}
		return destPath.substring(0, destPath.length() - 2);
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

	public Map getAuthorityMap() {
		return authorityMap;
	}

	public void setAuthorityMap(Map authorityMap) {
		this.authorityMap = authorityMap;
	}

	public ICommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

}
