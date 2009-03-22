
package com.baoz.yx.action.system;


import org.apache.struts2.config.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;

/**
 *	联系人性质查询操作
 * 
 * 
 */
@Result(name = "fileTypeList", value = "/WEB-INF/jsp/system/ye/fileType.jsp")

public class FileTypeQueryAction extends DispatchAction {

	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;

	@Override
	public String doDefault() throws Exception {
		this.logger.info("供应商类别查询");
		info = queryService.listQueryResult(
				" from FileType ft where ft.id not in(0) and ft.is_active!=0", info);
		logger.info("=================info="+info);
		return "fileTypeList";
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

}

