package com.baoz.yx.action.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.utils.YxConstants;

@Results( { @Result(name = "success", type = ServletRedirectResult.class, value = "/system/typeManageQuery.action"),
		@Result(name = "view", value = "/WEB-INF/jsp/manage/system/typeManage/typeManageForm.jsp") })
public class TypeManageAction extends DispatchAction {

	private List<YXTypeManage> typeManageList;

	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

	@Autowired
	@Qualifier("commonService")
	private ICommonService commonService;

	@SuppressWarnings("unchecked")
	private Map<Long,String> type = YxConstants.typeBigMap;
	
	private String big;
	
	private String small;
	
	private String orderNum;
	
	public String doDefault() {
		if (typeManageList != null && typeManageList.size() > 0) {
			logger.info("修改类型管理(size=" + typeManageList.size() + ")");
			for (int i = 0; i < typeManageList.size(); ++i) {
				if (typeManageList.get(i).getId() != null)
					typeManageList.set(i, (YXTypeManage) commonService.load(YXTypeManage.class, typeManageList.get(i)
							.getId()));
			}
		} else {
			logger.info("新增类型管理");
			typeManageList = new ArrayList<YXTypeManage>();
			YXTypeManage type_manage = new YXTypeManage();
			typeManageList.add(type_manage);
		}
		return "view";
	}

	public String saveorUpdate() {
		Employee user=UserUtils.getUser();
		if (typeManageList != null && typeManageList.size() > 0) {
			for (YXTypeManage type_manage : typeManageList) {
				type_manage.setIs_active("1");
				type_manage.setById(user.getId());
				type_manage.setUpdateBy(new Date());
				type_manage.setOrderNum(orderNum);
				commonService.saveOrUpdate(type_manage);
				logger.info("类型管理id(" + type_manage.getTypeName() + ")->操作成功");
			}
		}
		return SUCCESS;
	}


	 public String delete() {
	 logger.info("删除类型");
	 if (typeManageList != null && typeManageList.size() > 0){
	 typeManageService.deleteAll(typeManageList);
	 logger.info("类型删除->成功");
	 }
	 return SUCCESS;
	 }
	 
	 public String isExist(){
		 logger.info("isExist");
		 String oql="FROM YXTypeManage as tm WHERE tm.typeBig=? AND tm.typeSmall=?";
		 YXTypeManage typeManage=(YXTypeManage) commonService.uniqueResult(oql, Long.valueOf(big),small);
		 logger.info(Long.valueOf(big));
		 logger.info(small);
		 logger.info(typeManage);
		 if(typeManage!=null) this.renderText("1");
		 else this.renderText("0");		 
		 return null;
	 }

	public List<YXTypeManage> getTypeManageList() {
		return typeManageList;
	}

	public void setTypeManageList(List<YXTypeManage> typeManageList) {
		this.typeManageList = typeManageList;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public void setCommonService(ICommonService commonService) {
		this.commonService = commonService;
	}

	public void setIds(Long[] ids) {
		if (typeManageList == null)
			typeManageList = new ArrayList<YXTypeManage>();
		for (Long id : ids) {
			YXTypeManage type_manage = new YXTypeManage();
			type_manage.setId(id);
			typeManageList.add(type_manage);
		}
	}

	@SuppressWarnings("unchecked")
	public Map getType() {
		return type;
	}

	public void setTypeBig(Long[] typeBig) {
		if (typeManageList == null)
			typeManageList = new ArrayList<YXTypeManage>();
		for (int i = 0; i < typeManageList.size(); ++i) {
			typeManageList.get(i).setTypeBig(typeBig[i]);
		}
	}

	public void setTypeSmall(String[] typeSmall) {
		if (typeManageList == null)
			typeManageList = new ArrayList<YXTypeManage>();
		for (int i = 0; i < typeManageList.size(); ++i) {
			typeManageList.get(i).setTypeSmall(typeSmall[i]);
		}
	}

	public void setTypeName(String[] typeName) {
	if (typeManageList == null)
			typeManageList = new ArrayList<YXTypeManage>();
		for (int i = 0; i < typeManageList.size(); ++i) {
			typeManageList.get(i).setTypeName(typeName[i]);
		}
	}

	public void setInfo(String[] info) {
		if (typeManageList == null)
			typeManageList = new ArrayList<YXTypeManage>();
		for (int i = 0; i < typeManageList.size(); ++i) {
			typeManageList.get(i).setInfo(info[i]);
		}
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setBig(String big) {
		this.big = big;
	}

	public void setSmall(String small) {
		this.small = small;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
}
