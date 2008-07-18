package com.baoz.yx.action.system;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;

import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.Gonggao;


/**
 * 公告
 * 
 * @author 
 */

@Results({
	@Result(name = "success", type =ServletRedirectResult.class, value = "/system/gongGaoQuery.action" ),
	@Result(name = "addOrUpdateSuccess", value = "/WEB-INF/jsp/manage/system/reportManage/addOrUpdateResult.jsp" ),
	@Result(name = "enterSave", value = "/WEB-INF/jsp/manage/system/reportManage/reportManageForm.jsp"),
	@Result(name = "enterUpdate", value = "/WEB-INF/jsp/manage/system/reportManage/reportManageUpdate.jsp")
	})

public class GonggaoAction extends DispatchAction implements ServletRequestAware{

	private List<Gonggao> gonggaoList;

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	
	private ServletRequest request;
	@Qualifier("queryService")
	private IQueryService queryService;
	private PageInfo info;	
	private Gonggao gg;
	private List<Gonggao>	gonggao;
	
	@Override
	public String doDefault() throws Exception {
		logger.info("公告新增");
		return "enterSave";	
	} 

	public String savePro() throws Exception{
	//	logger.info("================================看看是否");
		this.logger.info("新增公告");
		gg.setIs_active("1");
		Employee user=UserUtils.getUser(); 
		gg.setById(user.getId());
	    gg.setUpdateBy(new Date());
		service.save(gg);	
		return "addOrUpdateSuccess";
	}
	
	public String enterUpdate() throws Exception {
		String idss = request.getParameter("id");
		Long id = Long.valueOf(idss);
		Gonggao o = (Gonggao) service
				.uniqueResult("from Gonggao obj where obj.id='" + id + "'");
		this.gg = o;
		return "enterUpdate";

	}
	
	public String updatePro() throws Exception {
		Gonggao o = (Gonggao) service
		.uniqueResult("from Gonggao obj where obj.id='" + gg.getId() + "'");
        //Date d = new Date();
        o.setContent(gg.getContent());
        o.setGdays(gg.getGdays());
        Employee user=UserUtils.getUser(); 
		o.setById(user.getId());
	    o.setUpdateBy(new Date());
        
        service.update(o);
        return "addOrUpdateSuccess";
	}
	
	public String delete() {
		 logger.info("删除类型");	
		 if (gonggaoList != null && gonggaoList.size() > 0){
			Employee user=UserUtils.getUser(); 
			for (Gonggao delSelf:gonggaoList) 
			{
				if (delSelf.getId() != null)
				{
					Gonggao toDel = (Gonggao) service.load(Gonggao.class, delSelf.getId());
				    toDel.setIs_active("0");
				    toDel.setById(user.getId());
				    toDel.setUpdateBy(new Date());
				    service.saveOrUpdate(toDel);
				}
			}
		 logger.info("类型删除->成功");
		 }
		 return SUCCESS;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public void setIds(Long[] ids) {
		if (gonggaoList == null)
			gonggaoList = new ArrayList<Gonggao>();
		for (Long id : ids) {
			Gonggao type_manage = new Gonggao();
			type_manage.setId(id);
			gonggaoList.add(type_manage);
		}
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
	public Gonggao getGonggao() {
		return gg;
	}

	public void setGonggao(Gonggao gg) {
		this.gg = gg;
	}
    public List<Gonggao> getGonggaoList(){
    	return gonggaoList;
    }
    public void setGonggaoList(List<Gonggao> gonggaoList) {
		this.gonggaoList = gonggaoList;
	}


	public void setServletRequest(HttpServletRequest arg0) {
		this.request=arg0;
		// TODO Auto-generated method stub
	}

}
