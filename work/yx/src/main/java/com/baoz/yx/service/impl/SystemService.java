package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.core.exception.ServiceException;
import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.yx.entity.ApplyMessage;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.SupplierInfoHistory;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXOEmployeeClient;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.bill.ApplyBill;
import com.baoz.yx.entity.contract.RealContractBillandRecePlan;
import com.baoz.yx.entity.programEconomy.YXOBidInfo;
import com.baoz.yx.entity.programEconomy.YXOEquipList;
import com.baoz.yx.entity.programEconomy.YXOProgramEconomy;
import com.baoz.yx.entity.programEconomy.YXOSectionInfo;
import com.baoz.yx.entity.programEconomy.YXOSectionRecord;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.IInvoiceService;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.tools.compare.CompareBeanTools;
import com.baoz.yx.tools.compare.beans.CompareContext;
import com.baoz.yx.utils.DepartmentUtils;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.UserDetail;

@Service("systemService")
@Transactional
public class SystemService implements ISystemService {
	@Autowired
	@Qualifier("commonDao")
	private ICommonDao commonDao;
	
	@Autowired
	@Qualifier("queryService")
	private IQueryService service;
	
	@Autowired
	@Qualifier("invoiceService")
	private IInvoiceService invoiceService;
	@Autowired
	@Qualifier("codeGenerateService")
	private ICodeGenerateService 	codeGenerateService;
	
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService 		typeManageService;
	
	private ApplyMessage am;
	private HttpServletRequest request = null;

	/**
	 * 删除选择的对象
	 * 
	 * @param idss
	 * @param hql
	 * @return
	 */
	public int deleteChose(String idss, String hql) {
		int a = 0;
		if (idss != null && hql != null) {
			String aaa[] = idss.split(",");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < aaa.length; i++) {
				sb.append(aaa[i]).append(",");
			}
			sb.append('0');
			a = commonDao.executeUpdate(hql + "  where obj.id in(" + sb + "0)");
		}
		return a;
	}

	public PageInfo purList(String hql) {
		PageInfo info = null;
		if (hql != null) {
			info = service.listQueryResult(hql, info);
		}
		return info;
	}

	/**
	 * 查询所有待确认的审购采购信息
	 * 
	 * @param list
	 * @return
	 */

	public List queryVerityPurchase(List list) {

		List<ApplyMessage> dlist = list;
		List alist = new ArrayList();
		if (dlist != null) {
			for (ApplyMessage am : dlist) {
				Object[] ob = new Object[3];
				ob[0] = am;
				Long cid = am.getCustomerId(); // 得到客户id,通过客户id查询出客户名称
				Long sid = am.getSellmanId(); // 得到销售人员id,通过销售人员id得到销售人员的名称

				if (cid != null && !"".equals(cid)) {
					String chql = "from YXClientCode d where d.id ='" + cid
							+ "' and d.is_active!=0";
					YXClientCode cc = (YXClientCode) commonDao
							.uniqueResult(chql);
					if (cc != null)
						ob[1] = cc.getName();
					else
						ob[1] = null;
				} else
					ob[1] = null;

				if (sid != null && !"".equals(sid)) {
					String ehql = "from Employee d where d.id ='" + sid
							+ "' and d.is_active!=0";
					Employee exp = (Employee) commonDao.uniqueResult(ehql);
					if (exp != null)
						ob[2] = exp.getName();
					else
						ob[2] = null;
					ob[1] = "a";
					ob[2] = "b";
				} else
					ob[2] = "";
				alist.add(ob);
			}
		}
		return alist;
	}

	public List listA(String hql) {
		List l = null;
		if (hql != null) {
			l = commonDao.list(hql);
		}
		return l;
	}

	public List listB(String hql) {
		List l = null;
		if (hql != null) {
			l = commonDao.list(hql);
		}
		return l;
	}

	/**
	 * 查询一个类，返回条件为id的对象
	 * 
	 * @param hql
	 * @param id
	 * @return
	 */
	public Object uniqueQuery(String hql, Long id) {
		Object oo = null;
		if (id != null && !"".equals(id)) {
			oo = commonDao.uniqueResult(hql, id);
		}
		return oo;
	}

	/**
	 * 
	 */
	public int updateState(String action, StringBuffer sb) {
		int a = 0;
		if (action != null && "pass".equals(action)) {
			a = commonDao
					.executeUpdate("update ApplyMessage obj set obj.affirmState=1 where obj.id in("
							+ sb + "0)");
		} else {
			a = commonDao
					.executeUpdate("update ApplyMessage obj set obj.affirmState=2 where obj.id in("
							+ sb + "0)");
		}
		return a;
	}

	/**
	 * 申购采购新增修改设置参数
	 * 
	 * @param idstate1
	 * @param idstate2
	 * @param act
	 * @param pNam
	 * @param pName
	 * @param cusId
	 */
	public ApplyMessage saveSet(String idstate1, String idstate2, String act,
			String pNam, String pName, Long cusId) {

		if (idstate1 != null && !"".equals(idstate1) && idstate2 != null
				&& !"".equals(idstate2)) {
			am.setInformState("2");
		}
		if (idstate1 != null && !"".equals(idstate1) && idstate2 == null
				|| "".equals(idstate2)) {
			am.setInformState("0");
		}
		if (idstate2 != null && !"".equals(idstate2) && idstate1 == null
				|| "".equals(idstate1)) {
			am.setInformState("1");
		}
		if (act != null && "verify".equals(act)) {
			am.setAffirmState("4");
		} else {
			am.setAffirmState("0");
		}
		if (pNam != null && !"".equals(pNam))
			am.setProjectName(pNam);
		if (pName != null && !"".equals(pName))
			am.setProjectName(pName);
		if (cusId != null && !"".equals(cusId))
			am.setCustomerId(cusId);
		am.setApplyId("12345667"); // 先这样固定，由算法生成的
		return am;
	}

	// ->->->->->->->->->->->->->->->->以下是工程经济模块中所使用的方法<-<-<-<-<-<-<-<-<-<-<-<-<-<-<-//
	/**
	 * 返回一个查询字符串
	 * 
	 * @param money1
	 * @param money2
	 * @param str3
	 * @param str4
	 * @param str5
	 * @param str6
	 * @param str7
	 * @param str8
	 * @param hql
	 * @return
	 * @throws ServiceException
	 */
	public String getSqlA(String hql, String money1, String money2,
			String str3, String str4, Long id, Long sid, String str7,
			String str8, String str9, String groupId) throws ServiceException {
		StringBuffer str = new StringBuffer();
		str.append(hql);
		// 申请时间
		if (str3 != null && !str3.equals("")) {
			str.append(" and pe.applyTime >= to_date('" + str3
					+ "','yyyy-mm-dd')");
		}
		if (str4 != null && !str4.equals("")) {

			str.append(" and pe.applyTime <= to_date('" + str4
					+ "','yyyy-mm-dd')");
		}
		// 投资匡算判断
		if (money1 != null && !money1.equals("")) {
			str.append(" and pe.investment>='").append(money1).append("'");
		}
		if (money2 != null && !money2.equals("")) {
			str.append(" and pe.investment<='").append(money2).append("'");
		}
		// 销售员
		if (sid != null && !sid.equals("")) {
			str.append(" and pe.exployeeId=").append(sid);
		}
		// 客户

		if (id != null && !id.equals("")) {
			str.append(" and pe.clientId = ").append(id);
		}
		// 工程编号
		if (str7 != null && !str7.equals("")) {
			str.append(" and lower(pe.projectNumber) like '%").append(StringUtils.lowerCase(StringUtils.trim(str7))).append("%'");
		}
		// 设计委托进度
		if (str8 != null && !str8.equals("")) {

			str.append(" and s.designSpeed >= to_date('" + str8
					+ "','yyyy-mm-dd')");
		}
		if (str9 != null && !str9.equals("")) {
			str.append(" and s.designSpeed<= to_date('" + str9
					+ "','yyyy-mm-dd')");
		}
		if (groupId != null && !"".equals(groupId)) {
			str.append(" and orgTree.organizationCode like '").append(
					groupId + "%'");
		}
		str.append(" order by pe.id desc");
		return str.toString();

	}

	public String getSqlB(String hql, String money1, String money2,
			String str3, String str4, Long id, Long sid, String str7,
			String str8, String str9, String groupId) throws ServiceException {

		StringBuffer str = new StringBuffer();
		str.append(hql);
		// 设计委托进度
		if (str3 != null && !"".equals(str3)) {

			str.append("  and s.designSpeed >= to_date('" + str3
					+ "','yyyy-mm-dd')");
		}
		if (str4 != null && !"".equals(str4)) {

			str.append(" and s.designSpeed<= to_date('" + str4
					+ "','yyyy-mm-dd')");
		}
		// 投资估概算
		if (money1 != null && !money1.equals("")) {
			str.append(" and s.investmentCount>='").append(money1).append("'");
		}
		if (money2 != null && !money2.equals("")) {
			str.append(" and s.investmentCount<='").append(money2).append("'");
		}
		// 销售员
		if (sid != null && !sid.equals("")) {
			str.append(" and pe.exployeeId=").append(sid);
		}
		// 客户
		if (id != null && !id.equals("")) {
			str.append(" and pe.clientId=").append(id);
		}
		// 工程编号
		if (str7 != null && !str7.equals("")) {
			str.append(" and lower(pe.projectNumber) like '%").append(StringUtils.lowerCase(StringUtils.trim(str7))).append("%'");
		}
		// 执行阶段
		if (str8 != null && !str8.equals("")) {
			str.append(" and s.sectionName='").append(str8).append("'");

		}
		if (groupId != null && !"".equals(groupId)) {
			str.append(" and orgTree.organizationCode like '").append(
					groupId + "%'");
		}
		str.append(" order by pe.id desc");
		return str.toString();

	}

	/**
	 * 返回一个查询字符串
	 * 
	 * @param hql
	 * @param str3
	 * @param str4
	 * @param id
	 * @param sid
	 * @param str7
	 * @param str8
	 * @param str9
	 * @return
	 * @throws ServiceException
	 */
	public String getSqlC(String hql, String str3, String str4, Long id,
			Long sid, String str7, String str8, String str9, String groupId)
			throws ServiceException {

		StringBuffer str = new StringBuffer();
		str.append(hql);
		// 执行阶段
		if (StringUtils.isNotBlank(str3)) {
			str.append(" and s.sectionName='").append(str3).append("'");
		}
		// 设备总表
		if (StringUtils.isNotBlank(str4)) {
			str.append(" and pe.enterFlag='").append(str4).append("'");
		}

		// 销售员
		if (sid != null && !sid.equals("")) {
			str.append(" and pe.exployeeId='").append(sid).append("'");
		}
		// 客户
		if (id != null && !id.equals("")) {
			str.append(" and pe.clientId='").append(id).append("'");
		}
		// 工程编号
		if (StringUtils.isNotBlank(str7)) {
			str.append(" and lower(pe.projectNumber) like '%").append(StringUtils.lowerCase(StringUtils.trim(str7))).append("%'");
		}
		// 设计委托进度
		if (StringUtils.isNotBlank(str8)) {
			str.append("  and s.designSpeed >= to_date('" + str8
					+ "','yyyy-mm-dd')");
		}
		if (StringUtils.isNotBlank(str9)) {
			str.append("  and s.designSpeed <= to_date('" + str9
					+ "','yyyy-mm-dd')");
		}
		if (groupId != null && !"".equals(groupId)) {
			str.append(" and orgTree.organizationCode like '").append(
					groupId + "%'");
		}
		str.append(" order by pe.id desc");
		return str.toString();

	}

	public List queryByDwr(Long id) {
		String hqlc = "from YXClientCode cc where cc.id=" + id;
		List listlm = this.listA(hqlc);
		return listlm;
	}

	/**
	 * 工程经济确认查询
	 * 
	 * @param list
	 * @return queryEconomyManage
	 */
	public List queryVerityEconomy(List list) {
		List<YXOProgramEconomy> dlist = list;
		List alist = new ArrayList();
		if (dlist != null) {
			for (YXOProgramEconomy am : dlist) {
				Object[] ob = new Object[3];
				ob[0] = am;
				Long sid = am.getExployeeId(); // 得到销售人员id,通过销售人员id得到销售人员的名称

				if (sid != null && !"".equals(sid)) {
					String ehql = "from Employee d where d.id ='" + sid
							+ "' and d.is_active!=0";
					Employee exp = (Employee) commonDao.uniqueResult(ehql);

					if (exp != null) {
						ob[2] = exp.getName();

					} else
						ob[2] = null;
				} else
					ob[2] = "";
				alist.add(ob);
			}
		}
		return alist;
	}

	public List queryEconomyManage(List list) {
		for (int i = 0; i < list.size(); i++) {
			YXOProgramEconomy pe = (YXOProgramEconomy) list.get(i);
		}
		List<YXOProgramEconomy> dlist = list;
		List alist = new ArrayList();
		if (dlist != null) {
			for (YXOProgramEconomy am : dlist) {
				Object[] ob = new Object[3];
				ob[0] = am;
				Long sid = am.getExployeeId(); // 得到销售人员id,通过销售人员id得到销售人员的名称

				if (sid != null && !"".equals(sid)) {
					String ehql = "from Employee d where d.id ='" + sid
							+ "' and d.is_active!=0";
					Employee exp = (Employee) commonDao.uniqueResult(ehql);

					if (exp != null) {
						ob[2] = exp.getName();

					} else
						ob[2] = null;
				} else
					ob[2] = "";
				alist.add(ob);
			}
		}
		return alist;
	}

	/**
	 * 用于更新工程经济状态updateProEconomyState
	 * 
	 * @param action
	 * @param sb
	 * @param updateStr1
	 * @param updateStr2
	 * @return
	 */
	public int updateProEconomyState(String action, StringBuffer sb,
			String updateStr1, String updateStr2) {
		int a = 0;
		if (action != null && "pass".equals(action)) {
			a = commonDao.executeUpdate(updateStr1 + sb + "0)");
			
			//判断是不是申购的确认通过，给个出库，并录入合同号(通过方法“makePurOut”)
			if(updateStr1.contains("ApplyBill")){
				String s[] = sb.toString().split(",");
				if(s!=null && s.length>0){
					// 生成申请单号
					for(int i=0;i<s.length;i++){
						if("0".equals(s[i])){
							continue;
						}
						ApplyBill ab = (ApplyBill) commonDao.load(ApplyBill.class, Long.parseLong(s[i]));
						if(ab.getInitIsNoContract() == 1){
							ab.setBillApplyNum(codeGenerateService.generateWBillCode());
							commonDao.update(ab);
							
						}
						if(ab.getInitIsNoContract() == 0){
							ab.setBillApplyNum(codeGenerateService.generateSBillCode());
							commonDao.update(ab);
						}
						//更新剩余发票
						List<RealContractBillandRecePlan> rbList = commonDao.list("select p from RealContractBillandRecePlan p ,BillandProRelaion br where br.realContractBillandRecePlan = p.realConBillproSid and br.applyBill = ?", ab.getBillApplyId());
						for (RealContractBillandRecePlan realContractBillandRecePlan : rbList) {
							if(realContractBillandRecePlan.getContractItemMaininfo() != null){
								contractService.sumRemainBIll(realContractBillandRecePlan.getContractItemMaininfo());
							}
							//更新计划中已确认字段
							realContractBillandRecePlan.setApplyBillState(1L);
							commonDao.update(realContractBillandRecePlan);
						}
					}
					// 出库
					for(int i=0;i<s.length;i++){
						invoiceService.makePurOut(Long.parseLong(s[i]));
					}
				}
			}
		} else {
			a = commonDao.executeUpdate(updateStr2 + sb + "0)");
		}
		return a;
	}

	public int updateProEconomyState(String action, StringBuffer sb) {
		int a = 0;
		int b = 0;
		if (action != null && !"".equals(action) && sb != null
				&& !"".equals(sb)) {
			if (action != null && "pass".equals(action)) {

				a = commonDao
						.executeUpdate("update YXOProgramEconomy obj set obj.state=3 where obj.id in("
								+ sb + "0)");
				b = 3;
			}
			if (action != null && "submit".equals(action)) {

				a = commonDao
						.executeUpdate("update YXOProgramEconomy obj set obj.state=6 where obj.id in("
								+ sb + "0)");
				b = 6;
			}
		} else {
			a = commonDao
					.executeUpdate("update YXOProgramEconomy obj set obj.state=4 where obj.id in("
							+ sb + "0)");
			b = 4;
		}
		// return a;
		return b;
	}

	public List queryClients(Long exployeeId) {
		List li = new ArrayList();
		if (exployeeId != null && !"".equals(exployeeId)) {
			String hql = "from YXOEmployeeClient obj where obj.exp.id='"
					+ exployeeId + "'";
			List expClient = commonDao.list(hql);
			String hqlc = "from YXClientCode obj where obj.id=?";
			if (expClient.size() > 0) {
				for (int i = 0; i < expClient.size(); i++) {
					YXOEmployeeClient exp = (YXOEmployeeClient) expClient
							.get(i);
					if (exp != null) {
						YXClientCode cc = (YXClientCode) commonDao
								.uniqueResult(hqlc, exp.getCli().getId());
						li.add(cc);
					}
				}
			}
		}
		return li;
	}

	/**
	 * 通过id删除某个对象
	 * 
	 * @param id
	 * @param hql
	 * @return
	 */
	public int deleteById(Long id, String hql) {
		int a = 0;
		if (id != null && !"".equals(id)) {
			a = commonDao.executeUpdate(hql, id);
		} else {
			a = -1;
		}
		return a;
	}

	public List getNameById(List list) {
		List<YXOBidInfo> dlist = list;
		List alist = new ArrayList();
		if (dlist != null) {
			for (YXOBidInfo am : dlist) {
				Object[] ob = new Object[3];
				ob[0] = am;
				Long sid = am.getExployeeId(); // 得到销售人员id,通过销售人员id得到销售人员的名称
				if (sid != null && !"".equals(sid)) {
					String ehql = "from Employee d where d.id ='" + sid
							+ "' and d.is_active!=0";
					Employee exp = (Employee) commonDao.uniqueResult(ehql);
					if (exp != null)
						ob[2] = exp.getName();
					else
						ob[2] = null;
				} else
					ob[2] = "";
				alist.add(ob);
			}
		}
		return alist;
	}

	public List getNameById2(List list) {
		List<YXOEquipList> dlist = list;
		List alist = new ArrayList();
		if (dlist != null) {
			for (YXOEquipList am : dlist) {
				Object[] ob = new Object[3];
				ob[0] = am;
				Long sid = am.getExployeeId(); // 得到销售人员id,通过销售人员id得到销售人员的名称
				if (sid != null && !"".equals(sid)) {
					String ehql = "from Employee d where d.id ='" + sid
							+ "' and d.is_active!=0";
					Employee exp = (Employee) commonDao.uniqueResult(ehql);
					if (exp != null)
						ob[2] = exp.getName();
					else
						ob[2] = null;
				} else
					ob[2] = "";
				alist.add(ob);
			}
		}
		return alist;
	}

	/**
	 * 招标文件信息列表
	 * 
	 * @param list
	 * @return
	 */
	public List BidList(List list) {
		List<YXOBidInfo> dlist = list;
		List alist = new ArrayList();
		if (dlist != null) {
			for (YXOBidInfo bm : dlist) {
				Object[] ob = new Object[3];
				ob[0] = bm;
				Long sid = bm.getExployeeId(); // 得到销售人员id,通过销售人员id得到销售人员的名称
				if (sid != null && !"".equals(sid)) {
					String ehql = "from Employee d where d.id ='" + sid
							+ "' and d.is_active!=0";
					Employee exp = (Employee) commonDao.uniqueResult(ehql);
					if (exp != null)
						ob[2] = exp.getName();
					else
						ob[2] = null;
				} else
					ob[2] = "";
				alist.add(ob);
			}
		}
		return alist;
	}

	/**
	 * 设备清单列表
	 * 
	 * @param list
	 * @return
	 */
	public List equipList(List list) {
		List<YXOEquipList> dlist = list;
		List alist = new ArrayList();
		if (dlist != null) {
			for (YXOEquipList bm : dlist) {
				Object[] ob = new Object[3];
				ob[0] = bm;
				Long sid = bm.getExployeeId(); // 得到销售人员id,通过销售人员id得到销售人员的名称
				if (sid != null && !"".equals(sid)) {
					String ehql = "from Employee d where d.id ='" + sid
							+ "' and d.is_active!=0";
					Employee exp = (Employee) commonDao.uniqueResult(ehql);
					if (exp != null)
						ob[2] = exp.getName();
					else
						ob[2] = null;
				} else
					ob[2] = "";
				alist.add(ob);
			}
		}
		return alist;
	}

	/**
	 * 
	 * @param hql
	 * @return
	 */
	public List<YXOSectionRecord> recordList(String hql) {

		List<YXOSectionRecord> list = commonDao.list(hql);
		// 初始化延迟加载
		// 可以把ContractItemMaininfo对象循环初始化
		for (YXOSectionRecord recordInfo : list) {
			recordInfo.getRecordInfo().size();
		}
		return list;
	}

	// 最新修改
	public List<YXOSectionInfo> sectionInfoList(String hql, Long id) {
		return commonDao.list(hql, id);

	}

	// --------------------------开票签收管理------------------------------//
	/**
	 * 返回开票签收管理左查询的参数
	 */
	public String getSignSql(Long clientId, Long expId, String billNumber,
			Long signState) {
		String hqlms = null;
		if (signState != null && !"".equals(signState)) {
			if (signState.equals(0L)) {
				if (billNumber != null && !"".equals(billNumber)) {
					hqlms = ("select ab,yc.name from ApplyBill ab ,YXClientCode yc where "
							+ " (yc.id = ab.customerId ) and ab.applyBillState in (5,6) and ab.sign=0 "
							+ " and exists (select 1 from InvoiceInfo invoice where lower(invoice.invoiceNo) like '%"
							+ StringUtils.lowerCase(StringUtils.trim(billNumber)) + "%' and invoice.applyInvoiceId = ab.billApplyId )");
				} else {
					hqlms = ("select ab,yc.name  from ApplyBill ab ,YXClientCode yc where ab.applyBillState  in (5,6) and (yc.id = ab.customerId )  and ab.sign=0" + "");
				}

			} 
			else if(signState.equals(1L)){

				if (billNumber != null && !"".equals(billNumber)) {
					hqlms = ("select ab,yc.name from ApplyBill ab ,YXClientCode yc where"
							+ " (yc.id = ab.customerId ) and ab.applyBillState  in (5,6) and ab.sign=1 "
							+ " and exists (select 1 from InvoiceInfo invoice where lower(invoice.invoiceNo) like '%"
							+ StringUtils.lowerCase(StringUtils.trim(billNumber)) + "%' and invoice.applyInvoiceId = ab.billApplyId )");
				} else {
					hqlms = ("select ab,yc.name from ApplyBill ab ,YXClientCode yc where ab.applyBillState  in (5,6) and (yc.id = ab.customerId ) and ab.sign = 1" + "");
				}
			}else{
				if (billNumber != null && !"".equals(billNumber)) {
					hqlms = ("select ab,yc.name from ApplyBill ab ,YXClientCode yc where"
							+ " (yc.id = ab.customerId ) and ab.applyBillState  in (5,6) and ab.sign in(0,1) "
							+ " and exists (select 1 from InvoiceInfo invoice where lower(invoice.invoiceNo) like '%"
							+ StringUtils.lowerCase(StringUtils.trim(billNumber)) + "%' and invoice.applyInvoiceId = ab.billApplyId )");
				} else {
					hqlms = ("select ab,yc.name from ApplyBill ab ,YXClientCode yc where and ab.applyBillState  in (5,6)  and (yc.id = ab.customerId ) and ab.sign in(0,1)" + "");
				}
			}
		} else {
			if (billNumber != null && !"".equals(billNumber)) {
				hqlms = ("select ab,yc.name from ApplyBill ab ,YXClientCode yc where (yc.id = ab.customerId ) and ab.sign=0 and ab.applyBillState  in (5,6) "
						+ " and exists (select 1 from InvoiceInfo invoice where lower(invoice.invoiceNo) like '%"
						+ StringUtils.lowerCase(StringUtils.trim(billNumber)) + "%' and invoice.applyInvoiceId = ab.billApplyId )");
			} else {
				hqlms = ("select ab,yc.name  from ApplyBill ab ,YXClientCode yc where "
						+ "  (yc.id = ab.customerId) and ab.applyBillState  in (5,6) ");
			}
		}
		
		StringBuffer str = new StringBuffer();
		str.append(hqlms);
		Long uid = new UserUtils().getUser().getId();
		// 客户
		if (clientId != null && !clientId.equals("")) {
			str.append(" and ab.customerId='").append(clientId).append("'");
		}
		///////////////////////////////////////////////////////
		// 不是组长,只能查自己
		UserDetail user = UserUtils.getUserDetail();
		String groupId = null;
		if(!DepartmentUtils.isTeamLeader(user.getPosition().getOrganizationCode())){
			expId = user.getUser().getId();
		}else{
			//是组长，只查本组的
			groupId = user.getPosition().getOrganizationCode();
		}
		// 销售员
		if (expId != null && !expId.equals("")) {
			str.append(" and ab.employeeId='").append(expId).append("'");

		}
		if (groupId != null && !"".equals(groupId)) {
			str.append(" and exists (select 1 from Employee e ,OrganizationTree ot where e.position=ot.id and ot.organizationCode like '").append(groupId+"%' and e.id = ab.employeeId ) ");
		}
		///////////////////////////////////////////////////////
		str.append("order by ab.billApplyId DESC");
		return str.toString();
	}

	// /////////////////////////////////////add by others
	/**
	 * 如在此service中增加方法请写上svn帐户名和增加时间还有该方法的用途 新增加的方法请写在最下面！！！！！！！！！！！！！！！！！！！！ 如
	 * add by yangyuan 6.20 查询销售员返回一个列表 public List<Employee> listEmployee()
	 * 
	 *
	 */
	// add by xiaoping
	public List<YXClientCode> queryAllClients() {
		List<YXClientCode> clients = (List<YXClientCode>) commonDao
				.list("from YXClientCode y");
		if (clients != null)
			return clients;
		return null;
	}
	/**
	 * 保存选择客户时候把客户添加到当前用户所关联的客户
	 */
	public void addRelation(Long clientId) {
		Long uid = UserUtils.getUser().getId();
		List<Object> cliList=this.commonDao.list(" from YXOEmployeeClient empCli where empCli.cli = " + clientId +
				"and empCli.exp = " + uid);	
		YXClientCode cliExpList = (YXClientCode) commonDao.load(YXClientCode.class, clientId);
		Employee expCliList = (Employee) commonDao.load(Employee.class, uid);
		if(cliList.size() == 0){
			YXOEmployeeClient yxoEC = new YXOEmployeeClient();
			yxoEC.setIs_active("1");
			yxoEC.setById(uid);
			yxoEC.setUpdateBy(new Date());
			yxoEC.setCli(cliExpList);
			yxoEC.setExp(expCliList);
			commonDao.save(yxoEC);
		}
	}

	public void deleteAmount(Object obj,Long amountId) {
		commonDao.executeUpdate("delete from " +obj+
				" ii where ii.id="+amountId);		
	}
	
	public void deleteObj(Class objClass,Long id) {
		String hql="delete from "+objClass.getName()+" d where d.id="+id;
		commonDao.executeUpdate(hql);	
	}
	
	public void updatePwd(String pwd, Long id) {
		commonDao.executeUpdate("update Employee e set e.password = '" + pwd + "' where e.id = " + id);
	}
	
	
	/**
	 * 保存供应商修改历史
	 */
	synchronized public void recordSupplyInfoChange (  SupplierInfo si ){
		
		CompareBeanTools cbt = new CompareBeanTools();
		SupplierInfo supplyIfoOrg;
		supplyIfoOrg = (SupplierInfo)commonDao.load(SupplierInfo.class, si.getSupplierid());
		List<CompareContext> ccList = cbt.compare(supplyIfoOrg, si , new String[]{"serialVersionUID","supplierid"});
		Long groupId = -1L;
		this.processFieldAndPropertyForSup(ccList);
		if ( ccList!=null && ccList.size() > 0){
			for( CompareContext c : ccList  ){
				SupplierInfoHistory sih = new SupplierInfoHistory();
				sih.setSupId(si.getSupplierid());
				sih.setIs_active("1");
				sih.setUpdateBy(new Date());
				sih.setById(UserUtils.getUser().getId());		
				sih.setType(c.getFieldName());
				sih.setOriginal(c.getSrcContext());
				sih.setPresent(c.getTargetContext());
				commonDao.save(sih);
				if( groupId.equals(-1L) ){
					groupId = sih.getId();
				}
				sih.setGroupId(groupId);
				commonDao.update(sih);
			}	
		}
	}

	
	private List<CompareContext>  processFieldAndPropertyForSup(List<CompareContext> ccList  ){
		Map<String,String> cBSNVMap  = this.getSupNVMap();
		String fieldName;
		String srcContext;
		String tarContext;
		for( CompareContext cc : ccList  ){
			fieldName = cc.getFieldName();
			srcContext = cc.getSrcContext();
			tarContext = cc.getTargetContext();

			if ( null == fieldName ){  fieldName = ""; }
			else if(  "supplierType".equals(fieldName)){
				srcContext = this.getTypeName(1013L,srcContext);
				tarContext = this.getTypeName(1013L,tarContext);
			}
			else if(  "eareCode".equals(fieldName)){
				srcContext = this.getTypeName(1005L,srcContext);
				tarContext = this.getTypeName(1005L,tarContext);
			}

			fieldName = cBSNVMap.get(fieldName);
			cc.setFieldName(fieldName);
			cc.setSrcContext(srcContext);
			cc.setTargetContext(tarContext);
		}
		return ccList;
	}
	
	
	private String getTypeName(Long typeBig, String typeSmall){
		if( StringUtils.isBlank( typeSmall )){
			return "";
		}
		else{
			YXTypeManage yxtm =(YXTypeManage) typeManageService.getYXTypeManage(typeBig, typeSmall );
			if( yxtm!=null ){
				return yxtm .getTypeName();
			}
			else{
				return "";
			}
		}
	}
	
	private Map<String,String> getSupNVMap(){	
		Map<String,String> cbsNV = new HashMap<String,String>();
		cbsNV.put("supplierName","供应商名称");
		cbsNV.put("supplierCode","供应商代码");
		cbsNV.put("billBank","开户银行");
		cbsNV.put("nameOfInovice","供应商开票名称");
		cbsNV.put("billAccount","开户帐号");
		cbsNV.put("dutyParagraph","税号");
		cbsNV.put("addressOfInovice","客户开票地址");
		cbsNV.put("phoneOfInovice","开票电话");
		cbsNV.put("eareCode","地域代码");
		cbsNV.put("supplierType","供应商类别");
		return cbsNV;
	}
	
	
	/**
	 * 验证供应商代码
	 */
	public boolean uniqueSupNum(String supNum){
		String hql = "select count(*)  from SupplierInfo s where s.supplierCode = ? ";
		Long c = (Long)commonDao.uniqueResult(hql , supNum);
		if(c>0){
			return false;
		}
		else{
			return true;
		}
	}
	
	public ICommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public IQueryService getService() {
		return service;
	}

	public void setService(IQueryService service) {
		this.service = service;
	}

	public ApplyMessage getAm() {
		return am;
	}

	public void setAm(ApplyMessage am) {
		this.am = am;
	}

	public IInvoiceService getInvoiceService() {
		return invoiceService;
	}

	public void setInvoiceService(IInvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public ICodeGenerateService getCodeGenerateService() {
		return codeGenerateService;
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
}
