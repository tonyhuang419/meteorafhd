package com.baoz.yx.action.system.material;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.MaterialManager;
import com.baoz.yx.service.IMaterialService;
import com.opensymphony.xwork2.ActionContext;

/**
 * 应交材料管理操作
 *
 */
@Results( {	
	@Result(name = "success", value = "/WEB-INF/jsp/system/material/materialForm.jsp"),
	@Result(name = "showSuccess", value = "/WEB-INF/jsp/system/material/success.jsp"),
	@Result(name = "saveEnter", type = ServletRedirectResult.class,value = "/system/material/materialQuery.action")
	})
public class MaterialAction extends DispatchAction{
	@Autowired
	@Qualifier("commonService")
	private ICommonService 		service;
	@Autowired
	@Qualifier("materialService")
	private IMaterialService 		materialService;
	private Long[] 				mid;          // 材料ID
	
	private Long 				mids;
	
	private MaterialManager     mm;
	
	private String 				stage;
	
	@Override
	public String doDefault() throws Exception {
		
		return SUCCESS;
	}
	/**
	 * 显示修改信息
	 * @return
	 */
	public String updateMeterial(){
		mm = (MaterialManager) service.load(MaterialManager.class,mids);
		return SUCCESS;
	}
	/**
	 * 保存应交材料
	 * @return
	 */
	public String saveMater(){
		service.save(mm);
		stage="1";
		ActionContext.getContext().getSession().put("stage", stage);
		return "showSuccess";
	}
	/**
	 * 修改应交材料
	 * @return
	 */
	public String updateMater(){
		MaterialManager mm1 = (MaterialManager) service.load(MaterialManager.class,mm.getId());
		mm1.setMaterialCode(mm.getMaterialCode());
		mm1.setMaterialName(mm.getMaterialName());
		mm1.setSortOrder(mm.getSortOrder());
		service.update(mm1);
		stage="2";
		ActionContext.getContext().getSession().put("stage", stage);
		return "showSuccess";
	}
	/**
	 * 删除材料
	 * @return
	 */
	public String deleteMater(){
		materialService.deleteMaterial(mid);
		stage="3";
		ActionContext.getContext().getSession().put("stage", stage);
		return "saveEnter";
	}
	/**
	 * 验证代码不能重复
	 * @return
	 */
	public String checkCode(){
		logger.info("checkCode");
		MaterialManager mater=(MaterialManager) service.uniqueResult(" from MaterialManager mm where mm.materialCode = ?", mm.getMaterialCode());
		logger.info(mater);
		if(mater!=null) this.renderText("1");
		else this.renderText("0");		
		return null;
	}
	/**
	 * 验证名称不能重复
	 * @return
	 */
	public String checkName(){
		logger.info("checkName");
		MaterialManager mater=(MaterialManager) service.uniqueResult(" from MaterialManager mm where mm.materialName = ?", mm.getMaterialName());
		logger.info(mater);
		if(mater!=null) this.renderText("1");
		else this.renderText("0");		
		return null;
	}
	public ICommonService getService() {
		return service;
	}
	public void setService(ICommonService service) {
		this.service = service;
	}
	public MaterialManager getMm() {
		return mm;
	}
	public void setMm(MaterialManager mm) {
		this.mm = mm;
	}
	public Long[] getMid() {
		return mid;
	}
	public void setMid(Long[] mid) {
		this.mid = mid;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public Long getMids() {
		return mids;
	}
	public void setMids(Long mids) {
		this.mids = mids;
	}
	
}
