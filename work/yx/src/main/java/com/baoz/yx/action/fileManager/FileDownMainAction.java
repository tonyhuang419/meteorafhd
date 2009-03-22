package com.baoz.yx.action.fileManager;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import com.baoz.core.web.struts2.DispatchAction;


@Results( {
		@Result(name = "showSellBeforeMain", value = "/WEB-INF/jsp/filemanage/fileDownMain.jsp"),
		@Result(name = "downLeftFrame", value = "/WEB-INF/jsp/filemanage/fileDownLeftFrame.jsp")
})
public class FileDownMainAction extends DispatchAction {

	/**
	 * 显示文件下载主页面
	 */
	@Override
	public String doDefault() throws Exception {
		this.logger.info("显示主页面");
		return "showSellBeforeMain";
	}
		
	/**
	 * 显示文件下载左框架
	 */
	public String downLeftFrame () throws Exception {
		return "downLeftFrame";
	}
	
}

