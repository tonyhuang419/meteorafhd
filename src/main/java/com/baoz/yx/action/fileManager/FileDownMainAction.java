package com.baoz.yx.action.fileManager;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import com.baoz.core.web.struts2.DispatchAction;

/**
 * 显示文件下载主页面
 */
@Results( {
		@Result(name = "showSellBeforeMain", value = "/WEB-INF/jsp/filemanage/fileDownMain.jsp")})
public class FileDownMainAction extends DispatchAction {

	@Override
	public String doDefault() throws Exception {
		this.logger.info("显示主页面");
		return "showSellBeforeMain";
	}
}

