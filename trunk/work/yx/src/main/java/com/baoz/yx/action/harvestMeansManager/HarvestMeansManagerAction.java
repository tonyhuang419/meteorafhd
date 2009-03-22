package com.baoz.yx.action.harvestMeansManager;

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
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.InvoiceInfo;
import com.baoz.yx.entity.ReveInfo;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.service.IHarvestService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

/**
 * 收款明细管理
 */
@Results( {
		@Result(name = "success", type = ServletRedirectResult.class, value = "/harvestMeansManager/harvestMeansManager.action"),
		@Result(name = "amount", type = ServletRedirectResult.class, value = "/harvestMeansManager/harvestMeansSearch.action"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/harvestMeansManager/updateAmount.jsp"),
		@Result(name = "enterSave", value = "/WEB-INF/jsp/harvestMeansManager/harvestMeansManagerList.jsp") })
public class HarvestMeansManagerAction extends DispatchAction implements
		ServletRequestAware {
	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("harvestService")
	private IHarvestService harvestservice;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	private ReveInfo ri;
	private InvoiceInfo ii;
	private ServletRequest request;
	private List<String> a;
	private String amount;

	private List<YXTypeManage> receTypetrans;

	public String doDefault() throws Exception {
		logger.info("收款管理");
		return ENTER_SAVE;
	}

	// 保存收款金额
	public String saveAmount() throws Exception {
		logger.info("===================我被执行了");
		ri.setIs_active("1");
		Long uid = UserUtils.getUser().getId();
		if (uid != null && !"".equals(uid))
			ri.setById(uid);

		ri.setUpdateBy(new Date());
		service.save(ri);
		Long ids = ri.getBillSid();
		Double amouts = (Double) service
				.uniqueResult("select sum(ri.amount) from ReveInfo ri where ri.billSid = '"
						+ ids + "' and ri.is_active =1");

		InvoiceInfo ii = (InvoiceInfo) service.load(InvoiceInfo.class, ids);

		if (amouts != null) {
			if (amouts.doubleValue() == ii.getInvoiceAmount()) {
				ii.setReceState("0");// 全到款
			} else if (amouts <= ii.getInvoiceAmount()) {
				ii.setReceState("1");// 部分到款
			} else if (amouts == 0.0) {
				ii.setReceState("2");// 从未到款
			}
			ii.setReceAmount(amouts.doubleValue());
		} else {
			ii.setReceAmount(0.0);
			ii.setReceState("2");// 从未到款
		}

		ii.setReceAmount(amouts.doubleValue());
		service.update(ii);
		return "amount";
	}

	// 修改收款信息
	public String enterUpdate() {
		logger.info("=================== 修改被执行了");
		receTypetrans = typeManageService.getYXTypeManage(1017L);// 到款方式

		String del = request.getParameter("delid");

		// harvestservice.delreve(Long.valueOf(del));
		Long id = Long.valueOf(del);
		ReveInfo o = (ReveInfo) service
				.uniqueResult("from ReveInfo obj where obj.id='" + id + "'");
		this.ri = o;
		ri.getAmount();
		ii = (InvoiceInfo) service.load(InvoiceInfo.class, ri.getBillSid());
		return ENTER_UPDATE;
	}

	/**
	 * 执行对收款明细的更新
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String updateAmount() throws Exception {
		logger.info("=================== 更新被执行了");
		if (ri.getId() != null && !"".equals(ri.getId())) {
			ReveInfo toUpdate = (ReveInfo) service.load(ReveInfo.class, ri
					.getId());
			Long uid = UserUtils.getUser().getId();
			if (uid != null && !"".equals(uid))
				toUpdate.setById(uid);

			toUpdate.setUpdateBy(new Date());
			toUpdate.setAmount(ri.getAmount());
			toUpdate.setAmountTime(ri.getAmountTime());
			toUpdate.setReceType(ri.getReceType());

			service.saveOrUpdate(toUpdate);
			Long ids = ri.getBillSid();
			Double amouts = (Double) service
					.uniqueResult("select sum(ri.amount) from ReveInfo ri where ri.billSid = '"
							+ ids + "' and ri.is_active =1");
			InvoiceInfo ii = (InvoiceInfo) service.load(InvoiceInfo.class, ids);
			if (amouts != null) {
				if (amouts.doubleValue() == ii.getInvoiceAmount()) {
					ii.setReceState("0");// 全到款
				} else if (amouts <= ii.getInvoiceAmount()) {
					ii.setReceState("1");// 部分到款
				} else if (amouts == 0.0) {
					ii.setReceState("2");// 从未到款
				}
				ii.setReceAmount(amouts.doubleValue());
			} else {
				ii.setReceAmount(0.0);
				ii.setReceState("2");// 从未到款
			}
			ii.setReceAmount(amouts.doubleValue());
			service.update(ii);
			return "amount";
		} else {
			service.saveOrUpdate(ri);
			return "amount";
		}

	}

	// 删除收款信息
	public String del() {
		logger.info("===================删除被执行了");
		String del = request.getParameter("delid");
		Long ids = Long.valueOf(del);
		ReveInfo ri = (ReveInfo) service.load(ReveInfo.class, ids);
		Long id = ri.getBillSid();
		harvestservice.delReveInfoByID(Long.valueOf(ids));
		Double amouts = (Double) service
				.uniqueResult("select sum(ri.amount) from ReveInfo ri where ri.billSid = '"
						+ id + "' and ri.is_active =1");
		InvoiceInfo ii = (InvoiceInfo) service.load(InvoiceInfo.class, id);

		if (amouts != null) {
			if (amouts.doubleValue() == ii.getInvoiceAmount()) {
				ii.setReceState("0");// 全到款
			} else if (amouts <= ii.getInvoiceAmount()) {
				ii.setReceState("1");// 部分到款
			} else if (amouts == 0.0) {
				ii.setReceState("2");// 从未到款
			}
			ii.setReceAmount(amouts.doubleValue());
		} else {
			ii.setReceAmount(0.0);
			ii.setReceState("2");// 从未到款
		}

		service.update(ii);
		return "amount";
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public ICommonService getService() {
		return service;
	}

	public void setService(ICommonService service) {
		this.service = service;
	}

	public ISystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	public ReveInfo getRi() {
		return ri;
	}

	public void setRi(ReveInfo ri) {
		this.ri = ri;
	}

	public ServletRequest getRequest() {
		return request;
	}

	public void setRequest(ServletRequest request) {
		this.request = request;
	}

	public List<String> getA() {
		return a;
	}

	public void setA(List<String> a) {
		this.a = a;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public IHarvestService getHarvestservice() {
		return harvestservice;
	}

	public void setHarvestservice(IHarvestService harvestservice) {
		this.harvestservice = harvestservice;
	}

	public List<YXTypeManage> getReceTypetrans() {
		return receTypetrans;
	}

	public void setReceTypetrans(List<YXTypeManage> receTypetrans) {
		this.receTypetrans = receTypetrans;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public InvoiceInfo getIi() {
		return ii;
	}

	public void setIi(InvoiceInfo ii) {
		this.ii = ii;
	}

}
