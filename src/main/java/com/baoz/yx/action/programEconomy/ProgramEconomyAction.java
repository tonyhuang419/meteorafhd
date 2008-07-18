package com.baoz.yx.action.programEconomy;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.baoz.core.service.ICommonService;
import com.baoz.core.utils.PageInfo;
import com.baoz.core.web.struts2.DispatchAction;
import com.baoz.yx.entity.Employee;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.programEconomy.YXOBidInfo;
import com.baoz.yx.entity.programEconomy.YXOEquipList;
import com.baoz.yx.entity.programEconomy.YXOProgramEconomy;
import com.baoz.yx.entity.programEconomy.YXOSectionInfo;
import com.baoz.yx.entity.programEconomy.YXOSectionRecord;
import com.baoz.yx.entity.programEconomy.YXOSectionRecordInfo;
import com.baoz.yx.service.ISystemService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;

/**
 * 工程经济相关操作
 * 
 * @author yang yuan (yangyuan@baoz.com.cn)
 */
@Results( {
		@Result(name = "succ", type = ServletRedirectResult.class, value = "/programEconomy/programEconomyVerifyQuery.action"),
		@Result(name = "success", type = ServletRedirectResult.class, value = "/programEconomy/programEconomyManageQuery.action"),
		@Result(name = "pinfo", value = "/WEB-INF/jsp/programEconomy/projectInfo.jsp"),
		@Result(name = "showpurs", value = "/WEB-INF/jsp/purchase/showpurs.jsp"),
		@Result(name = "showlinkms", value = "/WEB-INF/jsp/purchase/showlinkms.jsp"),
		@Result(name = "economyInfo", value = "/WEB-INF/jsp/programEconomy/projectEconomyInfo.jsp"),
		@Result(name = "economyView", value = "/WEB-INF/jsp/programEconomy/economyView.jsp"),
		@Result(name = "showClientsList", value = "/WEB-INF/jsp/programEconomy/showClients.jsp"),
		@Result(name = "edit", value = "/WEB-INF/jsp/programEconomy/economyEdit.jsp"),
		@Result(name = "enterUpdate", value = "/WEB-INF/jsp/purchase/modifyPurchase.jsp") })
public class ProgramEconomyAction extends DispatchAction {

	@Autowired
	@Qualifier("commonService")
	private ICommonService service;
	@Autowired
	@Qualifier("systemService")
	private ISystemService systemService;
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;

	private List<YXTypeManage> sectionTypeManageList;
	private List<YXTypeManage> marryTypeManageList; // 添加每稿的费用和金额的list
	private String action; // 工程经济确认,用于知道是确认通过的状态，还是未通过的状态
	private String stateId; // 工程经济确认,用于知道对哪个进行确认通过和不通过
	private String state;
	private String ids;
	private String pid; // 用于获得工程经济明细YXOSectionInfo id值
	private String tag; // tag4 招标 tag3 设备清单 tag2 BPMS tag1 设备总表
	private YXOSectionInfo yxosi; // 用于获得工程经济明细
	private YXOProgramEconomy proEconomy; // use
	private String sectionId; // 阶段信息表id
	private String sectionId2;
	private Long choseSectionId; // 保存阶段id use
	private Long s2ida; // 取阶段id
	private Long sectionInfoId;
	private Long infoId;
	private List bidList; // 招标信息列表
	private List equipList; // 设备清单信息列表
	private List sectionList;// 阶段信息列表
	private List sectionRecord;// 阶段履历列表
	private YXOEquipList equip; // use
	private YXOEquipList eq;
	private YXOSectionInfo sr; // use
	private YXOSectionInfo srr; // use
	private YXOSectionInfo ssave;
	private YXOBidInfo bid;
	private Long pubSectionId;
	private PageInfo info;
	private HttpServletRequest request = null;
	private Long economyId;
	private String moneyState; // 如果moneyState的值为no，就执行更新，否则就不执行更新
	private YXOSectionRecord record;
	private Date d;
	private Long seid;
	private List infoList;
	private Employee exp;
	private Long back;
	private int a;
	private String editId;
	private Long projectEconomyId;
	private YXOProgramEconomy proe;
	private YXOSectionRecordInfo yxosrinfo;
	private Double money;
	private String group;
	private String rid;
	private Long add; // 当add里有值，显示加稿页面，没有则不显示
	private Double moneyy;
	private String groupp;
	private Long id;
	private Long choseSectionIdSet;
	private Long choseSectionIdGet;
	private Long recordId;
	private Long sectionRecordId;
	private Long countSect; // 某个工程经济的阶段总数
	private String typeCodee;

	// 最新修改,显示某个工程经济主体信息的所有阶段信息
	private List<YXOSectionInfo> sectionInfoList; // use
	private Long lastId;
	private Long bpmsF;
	private Long lastChoseSectionId; // 记录某个工程经济主题信息的最后一阶段的阶段信息
	private Long sectionID;
	private Long srid;
	private List<YXOBidInfo>		dutyList1; 
	private List<YXOEquipList>		dutyList2; 


	public List<YXOBidInfo> getDutyList1() {
		return dutyList1;
	}

	public void setDutyList1(List<YXOBidInfo> dutyList1) {
		this.dutyList1 = dutyList1;
	}

	public String doDefault() throws Exception {
		return "";
	}

	/**
	 * 工程经济管理,修改功能
	 * 
	 * @return
	 */
	String ehql = "from Employee obj where obj.is_active=1 and obj.id=?";
	String economyHql = "from YXOProgramEconomy obj where obj.is_active=1 and obj.id=?";
	String sectionHql = "from YXOSectionInfo obj where obj.is_active=1 and  obj.economy.id=?";

	public String economyEdit() {

		String sectionid[] = editId.split(",");// 得到工程经济主体信息id
		Long eid = Long.parseLong(sectionid[0]);
		logger.info("=============eid=========" + eid);
		request = ServletActionContext.getRequest();
		request.getSession().setAttribute("SectionInfoId",
				Long.parseLong(sectionid[0]));// 保存你选择的主题信息id到session中
		this.commonLists(eid);
		return "edit";
	}

	/**
	 * 工程经济管理,修改功能--阶段信息的添加与删除
	 * 
	 * @return
	 */
	// 新增阶段
	public String saveSection() {
		request = ServletActionContext.getRequest();
		Long mid = (Long) request.getSession().getAttribute("SectionInfoId");// 保存你选择的主题信息id到session中
		ssave.setIs_active("1");
		ssave.setBpmsFlag("0");// 初始化bpms为未录入
		ssave.setEconomy((YXOProgramEconomy) service.load(
				YXOProgramEconomy.class, mid));
		service.save(ssave);
		if (mid != null)
			this.commonLists(mid);
		this.setTag("1");
		return "edit";
	}

	// 删除某个阶段
	public String deleteSection() {

		request = ServletActionContext.getRequest();
		Long sinfoId = (Long) request.getSession()
				.getAttribute("SectionInfoId"); // 得到主体信息id
		String delEquip = "update YXOSectionInfo obj set obj.is_active=0 where obj.id=?";
		if (yxosi.getId() != null && !"".equals(yxosi.getId()))
			systemService.deleteById(yxosi.getId(), delEquip);
		if (sinfoId != null)
			this.commonLists(sinfoId);
		this.setTag("1");
		return "edit";
	}

	// 显示某个阶段的所有阶段履历信息
	public String queryEdit() {

		request = ServletActionContext.getRequest();
		String aaab[] = null;
		if (sectionId2 != null && !"".equals(sectionId2)) {
			aaab = sectionId2.split(",");
		}
		Long sinfoId = (Long) request.getSession()
				.getAttribute("SectionInfoId");
		// 保存用户选择的阶段id,主要用于加稿操作
		if (sectionId2 != null && !"".equals(sectionId2)) {
			if (aaab[0] != null && !"".equals(aaab[0])) {
				String orderHql = "from YXOSectionRecord obj where  obj.sectionId=?";
				this.setTag("2");
				this.setChoseSectionId(Long.parseLong(aaab[0]));// 把选种的阶段id保存到ChoseSectionId

				sectionRecord = service.list(orderHql, Long.parseLong(aaab[0]));
				String infohql = "from YXOSectionRecord obj where  obj.sectionId='"
						+ Long.parseLong(aaab[0]) + "' order by obj.draftCount";

				infoList = systemService.recordList(infohql);

				String hqls = "from YXOSectionInfo obj where obj.is_active=1 and obj.id=?";
				sr = (YXOSectionInfo) systemService.uniqueQuery(hqls, Long
						.parseLong(aaab[0]));

			}
		} else {
			Long sid = (Long) request.getAttribute("sectionIDD");
			if (sid != null) {
				String orderHql = "from YXOSectionRecord obj where  obj.sectionId=?";
				this.setTag("2");
				// this.setChoseSectionId(Long.parseLong(aaab[0]));//
				// 把选种的阶段id保存到ChoseSectionId

				sectionRecord = service.list(orderHql, sid);
				String infohql = "from YXOSectionRecord obj where  obj.sectionId='"
						+ sid + "' order by obj.draftCount";
				infoList = systemService.recordList(infohql);
				String hqls = "from YXOSectionInfo obj where obj.is_active=1 and obj.id=?";
				sr = (YXOSectionInfo) systemService.uniqueQuery(hqls, sid);
			}
		}

		if (sinfoId != null && !"".equals(sinfoId))
			this.commonLists(sinfoId); // 这里空了
		return "edit";
	}

	// 添加某个阶段的履历信息--主要添加费用组成和金额
	public String addMoney() {
		request = ServletActionContext.getRequest();
		Long sinfoId = (Long) request.getSession()
				.getAttribute("SectionInfoId");
		// 做好在使用前做判空处理，如果record为空，添加费用和金额是没有意义的！！！！！
		// 只有加了稿的才能添加金额和费用组成。在页面里完成这个判断，没有搞的直接影藏掉添加
		// 费用组成和金额的添加按纽
		Long recordId = this.getSectionRecordId();
		Long cid = this.getChoseSectionId();
		if (recordId != null) {
			this.record = (YXOSectionRecord) service.uniqueResult(
					"from YXOSectionRecord obj where obj.id=? ", recordId);
			this.setChoseSectionIdSet(cid);
			YXOSectionRecordInfo srinfo = new YXOSectionRecordInfo();
			yxosrinfo.setIs_active("1");
			yxosrinfo.setMoneyy(money);
			yxosrinfo.setRecord(record);
			yxosrinfo.setTypeCodee(yxosrinfo.getTypeCodee());

			service.save(yxosrinfo);
			String infohql = "from YXOSectionRecord obj where  obj.sectionId='"
					+ cid + "' order by obj.draftCount";
			infoList = systemService.recordList(infohql);
		}
		String hqls = "from YXOSectionInfo obj where obj.is_active=1 and obj.id=?";
		sr = (YXOSectionInfo) systemService.uniqueQuery(hqls, this
				.getChoseSectionId());
		this.setTag("2");
		this.commonLists(sinfoId);
		return "edit";

	}

	// 加稿addPaper
	/**
	 * 点击加稿计算出当前的最大稿数然后存入数据库，记录当前的系统时间为加搞时间，然后在加入 费用组成和金额，这样加稿就完成了
	 */
	public String addPapers() {
		YXOSectionRecord record = new YXOSectionRecord();

		request = ServletActionContext.getRequest();
		Long id = (Long) request.getSession().getAttribute("SectionInfoId");
		Long choseSid = this.getChoseSectionId();// 被选中的阶段id
		Long max = (Long) service
				.uniqueResult(
						"select max(obj.draftCount) from YXOSectionRecord obj where obj.sectionId=?",
						choseSid);// 得到最大的搞数
		record.setEnterTime(new Date());
		if (max != null)
			record.setDraftCount(max + 1);
		else
			record.setDraftCount(1l);
		record.setSectionId(this.getChoseSectionId());
		service.save(record); // 保存加稿
		// // 把加入的数据在查询出来
		String hql = "from YXOSectionRecord obj where obj.sectionId=? and obj.draftCount=?";
		if (max != null)
			this.record = (YXOSectionRecord) service.uniqueResult(hql,
					choseSid, max + 1);
		else
			this.record = (YXOSectionRecord) service.uniqueResult(hql,
					choseSid, 1l);
		this.commonLists(id);
		String hqls = "from YXOSectionInfo obj where obj.is_active=1 and obj.id=?";
		sr = (YXOSectionInfo) systemService.uniqueQuery(hqls, choseSid);

		String infohql = "from YXOSectionRecord obj where  obj.sectionId='"
				+ choseSid + "' order by obj.draftCount";
		infoList = systemService.recordList(infohql);
		this.setTag("2");
		this.setAdd(2l);
		return "edit";

	}

	/**
	 * savePapers用于保存新增搞
	 * 
	 * @return
	 */
	public String savePapers() {
		 
		YXOSectionRecordInfo yxosrinfo = new YXOSectionRecordInfo();
		request = ServletActionContext.getRequest();
		Long id = (Long) request.getSession().getAttribute("SectionInfoId");
		yxosrinfo.setIs_active("1");
		yxosrinfo.setMoneyy(moneyy);
		yxosrinfo.setTypeCodee(groupp);
		yxosrinfo.setRecord(this.record);
		service.save(yxosrinfo);
        //--------------------
		yxosrinfo=null;
		this.setAdd(0l);// 置0
		this.commonLists(id);
		this.setTag("2");
		return this.queryEdit();

	}

	/**
	 * 删除某个阶段的某个履历的费用组成和金额deleteRecordInfo
	 */
	public String deleteRecordInfo() {
		Long id = this.getId();// 得到费用组成和金额的id
		String hql = "update YXOSectionRecordInfo obj set obj.is_active=0 where obj.id=? ";
		service.executeUpdate(hql, id);
		this.setAdd(0l);// 置0
		this.setTag("2");
		String hqls = "from YXOSectionRecordInfo obj where obj.id=? ";
		String hqlss = "from YXOSectionRecord obj where obj.id=? ";
		if (id != null) {
			yxosrinfo = (YXOSectionRecordInfo) service.uniqueResult(hqls, id);
			if (yxosrinfo != null) {
				Long recordID = yxosrinfo.getRecord().getId();
				record = (YXOSectionRecord) service.uniqueResult(hqlss,
						recordID);
			}
		}
		request = ServletActionContext.getRequest();
		request.setAttribute("sectionIDD", record.getSectionId()); // new

		return this.queryEdit();
	}

	/**
	 * 工程经济管理录入管理，查看信息
	 * 
	 * @return
	 */
	public String economyView() {

		request = ServletActionContext.getRequest();
		request.getSession().setAttribute("SectionInfoId_economyView", seid);

		bidList = systemService.getNameById(service.list(bidHql, seid));
		equipList = systemService.getNameById2(service.list(equipHql, seid));
		marryTypeManageList = typeManageService.getYXTypeManage(1012l);
		sectionTypeManageList = typeManageService.getYXTypeManage(1011l);

		YXOProgramEconomy oe = (YXOProgramEconomy) systemService.uniqueQuery(
				economyHql, seid);
		exp = (Employee) service.uniqueResult(ehql, oe.getExployeeId());
		this.proEconomy = oe;

		List<YXOSectionInfo> os = systemService.sectionInfoList(sectionHql,
				seid);
		this.sectionInfoList = os;
		return "economyView";
	}

	public String queryView() {
		String aaab[] = sectionId2.split(",");
		request = ServletActionContext.getRequest();
		Long eid = (Long) request.getSession().getAttribute(
				"SectionInfoId_economyView");
		if (aaab[0] != null && !"".equals(aaab[0])) {
			String orderHql = "from YXOSectionRecord obj where  obj.sectionId=?";
			this.setTag("2");

			this.setChoseSectionId(Long.parseLong(aaab[0]));// 把选种的阶段id保存到ChoseSectionId
			// 所选阶段的id不能保存到session注意!
			sectionRecord = service.list(orderHql, Long.parseLong(aaab[0]));
			String infohql = "from YXOSectionRecord obj where  obj.sectionId='"
					+ Long.parseLong(aaab[0]) + "' order by obj.draftCount";

			infoList = systemService.recordList(infohql);
			String hqls = "from YXOSectionInfo obj where obj.id=?";
			sr = (YXOSectionInfo) systemService.uniqueQuery(hqls, Long
					.parseLong(aaab[0]));

		}
		if (eid != null || !"".equals(eid))
			this.commonLists(eid); // 这里空了
		return "economyView";
	}

	/**
	 * 查询出所有客户, 申购采购确认模块
	 * 
	 * @return
	 */
	public String showClientsList() {
		info = systemService
				.purList("from YXClientCode cc where cc.id not in(0) and cc.is_active!=0");
		return "showClientsList";
	}

	/**
	 * 工程经济状态 0-结束；1-售前合同；2-正式合同3-确认通过4-确认退回5-草稿-6待确认
	 * 
	 * @return
	 */
	public String verifyState() {

		String aaa[] = stateId.split(",");
		if (aaa[0] != null && !"".equals(aaa[0])) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < aaa.length; i++) {
				sb.append(aaa[i]).append(",");
			}
			sb.append('0');
			int a = systemService.updateProEconomyState(action, sb);
			this.setA(a);
			if (a > 0)
				return "succ";
			else {
				logger.info("更新状态操作不成功！");
				return "succ";
			}
		} else {
			return "succ";
		}

	}

	public String verifyState2() {

		StringBuffer sb = new StringBuffer();
		if (stateId != null && !"".equals(stateId)) {
			sb.append(stateId).append(",");
			sb.append('0');
			int a = systemService.updateProEconomyState(action, sb);
			if (a > 0)
				return "succ";
			else {
				logger.info("更新状态操作不成功！");
				return "succ";
			}
		} else {
			return "succ";
		}
	}

	public String verifyState3() {

		String aaa[] = stateId.split(",");
		StringBuffer sb = new StringBuffer();
		if (aaa[0] != null && !"".equals(aaa[0])) {
			for (int i = 0; i < aaa.length; i++) {
				sb.append(aaa[i]).append(",");
			}
			sb.append('0');
			int a = systemService.updateProEconomyState(action, sb);
			if (a > 0)
				return "success";
			else {
				logger.info("更新状态操作不成功！");
				return "success";
			}
		} else {
			return "success";
		}

	}

	/**
	 * 显示工程经济明细
	 * 
	 * @return
	 */
	public String projectInfo() {
		/**
		 * 这里只能显示一个阶段,如果有多条，说明在工程经济增加的时候有问题
		 */
		Long id = Long.valueOf(pid);
		String hql = "from YXOProgramEconomy obj where obj.is_active=1 and obj.state=6 and obj.id=?";
		String shql = "from YXOSectionInfo obj where obj.is_active=1 and obj.economy.id=?";
		YXOProgramEconomy o = (YXOProgramEconomy) systemService.uniqueQuery(
				hql, id);
		this.yxosi = (YXOSectionInfo) systemService
				.uniqueQuery(shql, o.getId());
		this.proe = o;
		return "pinfo";
	}

	// 删除:修改好
	public String delEconomy() {

		String hql = " update YXOProgramEconomy obj set obj.is_active=0 ";
		int a = systemService.deleteChose(ids, hql);
		if (a > 0) {
			return "success";
		} else {
			logger.info("删除操作不成功！");
			return "success";
		}
	}

	// 以下是工程经济录入管理相关操作
	// 得到阶段信息表id,返回阶段信息表，已经工程经济信息表对象
	String bidHql = "from YXOBidInfo obj where obj.is_active=1 and obj.projectEconomyId=?";// 查询招标文件信息
	String equipHql = "from YXOEquipList obj where obj.is_active=1 and obj.projectEconomyId=?";// 设备清单信息列表

	public String enterManageOperate() {
		String aaa[] = sectionId.split(","); // sectionId第一次进入enterManageList.jsp选的阶段id
		Long eid = Long.parseLong(aaa[0]);
		request = ServletActionContext.getRequest();
		request.getSession().setAttribute("SectionInfoId", eid); // 把第一次进入enterManageList.jsp选的阶段id保存到session中
		this.commonLists(eid);
		return "economyInfo";
	}

	// 新增设备清单信息
	public String saveEquip() {
		// 录入人应该是当前登入操作用户，暂设定一个固定值.
		logger.info("====ssssssssssssssssssssssssssssbbbbbbbbbbbbbbbbbbbbbbbbbbbb!===" );
		Long uid = UserUtils.getUser().getId();
		request = ServletActionContext.getRequest();
		Long eid = (Long) request.getSession().getAttribute("SectionInfoId");
		equip.setIs_active("1");
		if (uid != null && !"".equals(uid))
			equip.setExployeeId(uid);
		String sql="select yx.listName from YXOEquipList yx where yx.is_active=1 and yx.listName='"+equip.getListName()+"'";
		dutyList2=service.list(sql);
		if(dutyList2.size()<1)
		{
		  service.save(equip);
		}
		// 从主体信息进入的
		if (eid != null || !"".equals(eid)) {
			this.commonLists(eid);
			this.setTag("3");
		} else {
			// 直接进入的，
			String aaa[] = sectionId.split(",");
			request.getSession().setAttribute("SectionInfoId",
					Long.parseLong(aaa[0]));
			this.commonLists(Long.parseLong(aaa[0]));
			this.setTag("3");
		}
		this.equip = null; // 看看
		return "economyInfo";
	}

	// 删除设备清单，
	public String deletes() {
		Long sID = 0l;
		if (sectionId != null && !"".equals(sectionId)) {
			String aaa[] = sectionId.split(",");
			sID = Long.parseLong(aaa[0]);
		}

		request = ServletActionContext.getRequest();
		Long eid = (Long) request.getSession().getAttribute("SectionInfoId");
		String delEquip = "update YXOEquipList obj set obj.is_active=0 where obj.id=?";
		int a = systemService.deleteById(equip.getId(), delEquip);
		if (a > 0) {
			this.setTag("3");
			// 从主体信息进入
			if (eid != null && !"".equals(eid)) {
				this.commonLists(eid);
				return "economyInfo";
			} else {
				// 直接进入
				this.commonLists(sID);
				return "economyInfo";
			}

		} else {
			logger.info("删除失败！");
			this.setTag("3");
			// 从主体信息进入
			if (eid != null && !"".equals(eid)) {
				this.commonLists(eid);
				return "economyInfo";
			} else {
				// 直接进入
				this.commonLists(sID);
				return "economyInfo";
			}
		}
	}

	/**
	 * 显示某个阶段的履历信息
	 * 
	 * @return
	 */
	public String queryOrder() {
		request = ServletActionContext.getRequest();

		String aaab[] = sectionId2.split(",");
		this.setBack(Long.parseLong(aaab[0]));

		Long eid = (Long) request.getSession().getAttribute("SectionInfoId");
		if (aaab[0] != null && !"".equals(aaab[0])) {
			Long sid = Long.parseLong(aaab[0]);
			this.setChoseSectionId(sid);
			request.getSession().setAttribute("section2", sid); // 保存新的所选的阶段id
			String orderHql = "from YXOSectionRecord obj where  obj.sectionId=?";
			this.setTag("2");
			request.getSession().setAttribute("section2", sid);// 在session中保存sectionId
			sectionRecord = service.list(orderHql, sid);
			String infohql = "from YXOSectionRecord obj where  obj.sectionId='"
					+ sid + "' order by obj.draftCount";
			infoList = systemService.recordList(infohql);
			String hqls = "from YXOSectionInfo obj where obj.id=?";
			sr = (YXOSectionInfo) systemService.uniqueQuery(hqls, sid);
		}
		return this.commonList(eid);// 近来时的主题信息id

	}

	/**
	 * bpms确认，如果是直接点bpms进入的，而不是先选阶段进入的 先得到选种的阶段号，在得到工程经济的id号，在选中该工程的最后的一个阶段
	 */
	public String enterBpms() {
		request = ServletActionContext.getRequest();
		String aaa[] = sectionId.split(",");
		Long eid = Long.parseLong(aaa[0]);// 主题信息id
		request.getSession().setAttribute("SectionInfoId", eid);// 将主题信息id保存到session中
		this.setLastChoseSectionId(lastId); // 将阶段id保存到LastChoseSectionId中
		this.commonLists(eid);
		String shql = "from YXOSectionInfo obj where obj.is_active=1 and obj.id=?";
		this.srr = (YXOSectionInfo) service.uniqueResult(shql, lastId); // 得到阶段信息
		String infohql = "from YXOSectionRecord obj where  obj.sectionId='"
				+ srr.getId() + "' order by obj.draftCount";
		infoList = systemService.recordList(infohql);
		if (infoList.size() > 0)
			this.setBpmsF(2l);
		else
			this.setBpmsF(3l);
		return "economyInfo";
	}

	public String bpmsVerifyLast() {
		request = ServletActionContext.getRequest();
		Long eid = (Long) request.getSession().getAttribute("SectionInfoId");

		Long csid = this.getLastChoseSectionId();
		String hql = "from YXOSectionInfo obj where obj.is_active=1 and obj.id=?";
		srr = (YXOSectionInfo) service.uniqueResult(hql, csid);
		Number max = (Number) service
				.uniqueResult(
						"select max(obj.draftCount) from YXOSectionRecord obj where obj.sectionId=?",
						csid);// 得到最大的搞数
		// 将取消的确认
		if (max != null) {

			if (srr.getBpmsFlag().equals("0")) {
				String updateHql = "update  YXOSectionInfo obj set obj.bpmsEnterTime=?,obj.bpmsFlag=? where obj.id=?";

				logger.info("====可能没有搞，没有搞就没能操作，否则要出错max===" + max);

				String mhql = "from YXOSectionRecord obj where obj.sectionId="
						+ csid + " and obj.draftCount=?";
				YXOSectionRecord sinfo = (YXOSectionRecord) service
						.uniqueResult(mhql, max);
				if (sinfo != null) {
					d = sinfo.getEnterTime();
				}
				if (d != null) {
					service.executeUpdate(updateHql, sinfo.getEnterTime(), "1",
							csid);
				} else {
					logger.info("时间为空");
				}

			} else {
				// 将确认的取消
				if (srr.getBpmsFlag().equals("1")) {
					String updateHql = "update  YXOSectionInfo obj set obj.bpmsEnterTime = null,obj.bpmsFlag=? where obj.id=?";

					String mhql = "from YXOSectionRecord obj where obj.sectionId="
							+ csid + " and obj.draftCount=?";
					YXOSectionRecord sinfo = (YXOSectionRecord) service
							.uniqueResult(mhql, max);
					if (sinfo != null) {
						d = sinfo.getEnterTime();
					}
					if (d != null) {
						service.executeUpdate(updateHql, "0", csid);
					} else {
						logger.info("时间为空");
					}
				}

			}
		} else {
			logger.info("====nullmax===" + max);
		}
		this.setTag("2");
		bidList = systemService.getNameById(service.list(bidHql, eid));
		equipList = systemService.getNameById2(service.list(equipHql, eid));
		marryTypeManageList = typeManageService.getYXTypeManage(1012l);
		sectionTypeManageList = typeManageService.getYXTypeManage(1011l);

		YXOProgramEconomy oe = (YXOProgramEconomy) systemService.uniqueQuery(
				economyHql, eid);
		exp = (Employee) service.uniqueResult(ehql, oe.getExployeeId());
		this.proEconomy = oe;

		List<YXOSectionInfo> os = systemService
				.sectionInfoList(sectionHql, eid);
		this.sectionInfoList = os;

		String infohql = "from YXOSectionRecord obj where  obj.sectionId='"
				+ srr.getId() + "' order by obj.draftCount";
		infoList = systemService.recordList(infohql);
		logger.info("========size=====" + infoList.size());
		if (infoList.size() > 0)
			this.setBpmsF(2l);
		else
			this.setBpmsF(3l);
		logger.info("====================sr=======" + sr);
		sr = null; // 注意这里？
		String hqls = "from YXOSectionInfo obj where obj.is_active=1 and obj.id=?";
		srr = (YXOSectionInfo) systemService.uniqueQuery(hqls, csid);
		return "economyInfo";

	}

	/**
	 * bpms确认
	 * 
	 * @return
	 */
	public String bpmsVerify() {
		request = ServletActionContext.getRequest();
		// 得到所选的阶段id
		Long s2id = this.getChoseSectionId();
		Long eid = (Long) request.getSession().getAttribute("SectionInfoId");// 主题信息id
		// 从主题信息进入
		if (sr.getBpmsFlag().equals("0")) {
			String updateHql = "update  YXOSectionInfo obj set obj.bpmsEnterTime=?,obj.bpmsFlag=? where obj.id=?";
			Number max = (Number) service
					.uniqueResult(
							"select max(obj.draftCount) from YXOSectionRecord obj where obj.sectionId=?",
							s2id);// 得到最大的搞数
			String mhql = "from YXOSectionRecord obj where  obj.sectionId="
					+ s2id + " and obj.draftCount=?";
			YXOSectionRecord sinfo = (YXOSectionRecord) service.uniqueResult(
					mhql, max);
			if (sinfo != null) {
				d = sinfo.getEnterTime();
			}
			if (d != null) {
				service.executeUpdate(updateHql, sinfo.getEnterTime(), "1",
						s2id);
			} else {
				logger.info("时间为空");
			}

		} else {
			if (sr.getBpmsFlag().equals("1")) {
				String updateHql = "update  YXOSectionInfo obj set obj.bpmsEnterTime = null,obj.bpmsFlag=? where obj.id=?";
				Number max = (Number) service
						.uniqueResult(
								"select max(obj.draftCount) from YXOSectionRecord obj where obj.sectionId=?",
								s2id);// 得到最大的搞数
				String mhql = "from YXOSectionRecord obj where obj.sectionId="
						+ s2id + " and  obj.draftCount=?";
				YXOSectionRecord sinfo = (YXOSectionRecord) service
						.uniqueResult(mhql, max);
				if (sinfo != null) {
					d = sinfo.getEnterTime();
				}
				if (d != null) {
					service.executeUpdate(updateHql, "0", s2id);
				} else {
					logger.info("时间为空");
				}
			}
		}
		this.setTag("2");
		return this.queryOrder();
	}

	/**
	 * 招标文件处理模块
	 * 
	 * @return
	 */
	// 新增招标信息
	public String saveBid() {
		request = ServletActionContext.getRequest();
		Long eid = (Long) request.getSession().getAttribute("SectionInfoId");
		bid.setIs_active("1");
		Long uid = UserUtils.getUser().getId();
		if (uid != null){
			bid.setExployeeId(uid);
		}
		String sql="select yx.bidName from YXOBidInfo yx where yx.is_active=1 and yx.bidName='"+bid.getBidName()+"'";
		dutyList1=service.list(sql);
		if(dutyList1.size()<1)
		{
		 service.save(bid);
		}
		if (eid == null || !"".equals(eid)) {
			this.commonLists(eid);
			this.setTag("4");
			request.getSession().setAttribute("tagState", "4");
		} else {
			String aaa[] = sectionId.split(",");
			request.getSession().setAttribute("section2",
					Long.parseLong(aaa[0]));
			this.commonLists(Long.parseLong(aaa[0]));
			this.setTag("4");
			request.getSession().setAttribute("tagState", "4");
		}
		return "economyInfo";

	}

	public String deletess() {
		request = ServletActionContext.getRequest();
		Long eid = (Long) request.getSession().getAttribute("SectionInfoId");
		Long sID = 0l;
		if (sectionId != null && !"".equals(sectionId)) {
			String aaa[] = sectionId.split(",");
			sID = Long.parseLong(aaa[0]);
		}
		String delBid = "update YXOBidInfo obj set obj.is_active=0 where obj.id=?";
		int a = systemService.deleteById(bid.getId(), delBid);
		if (a > 0) {
			this.setTag("4");
			request.getSession().setAttribute("tagState", "4");
			// 从主体信息进入
			if (eid != null && !"".equals(eid)) {
				this.commonLists(eid);
				return "economyInfo";
			} else {
				// 直接进入
				this.commonLists(sID);
				return "economyInfo";
			}

		} else {
			logger.info("删除失败！");
			this.setTag("4");
			request.getSession().setAttribute("tagState", "4");
			// 从主体信息进入
			if (eid != null && !"".equals(eid)) {
				this.commonLists(eid);
				return "economyInfo";
			} else {
				// 直接进入
				this.commonLists(sID);
				return "economyInfo";
			}
		}
	}

	/**
	 * 设备总表金额
	 * 
	 * @return
	 */
	public String bpmsAndEquip() {

		request = ServletActionContext.getRequest();
		Long eid = (Long) request.getSession().getAttribute("SectionInfoId");
		String updateHql = "update  YXOProgramEconomy obj set obj.equipEnterTime=?,obj.equipTotalMoney=?,obj.enterFlag=? where obj.id=?";
		Double etm = proEconomy.getEquipTotalMoney();
		proEconomy.getEquipEnterTime();
		service.executeUpdate(updateHql, new Date(), etm, "1", eid);
		this.setTag("1");
		return this.commonList(eid);
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

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public YXOSectionInfo getYxosi() {
		return yxosi;
	}

	public void setYxosi(YXOSectionInfo yxosi) {
		this.yxosi = yxosi;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public YXOProgramEconomy getProEconomy() {
		return proEconomy;
	}

	public void setProEconomy(YXOProgramEconomy proEconomy) {
		this.proEconomy = proEconomy;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public List getBidList() {
		return bidList;
	}

	public void setBidList(List bidList) {
		this.bidList = bidList;
	}

	public List getEquipList() {
		return equipList;
	}

	public void setEquipList(List equipList) {
		this.equipList = equipList;
	}

	public YXOEquipList getEquip() {
		return equip;
	}

	public void setEquip(YXOEquipList equip) {
		this.equip = equip;
	}

	public YXOEquipList getEq() {
		return eq;
	}

	public void setEq(YXOEquipList eq) {
		this.eq = eq;
	}

	public List getSectionList() {
		return sectionList;
	}

	public void setSectionList(List sectionList) {
		this.sectionList = sectionList;
	}

	public List getSectionRecord() {
		return sectionRecord;
	}

	public void setSectionRecord(List sectionRecord) {
		this.sectionRecord = sectionRecord;
	}

	public String getSectionId2() {
		return sectionId2;
	}

	public void setSectionId2(String sectionId2) {
		this.sectionId2 = sectionId2;
	}

	public Long getSectionInfoId() {
		return sectionInfoId;
	}

	public void setSectionInfoId(Long sectionInfoId) {
		this.sectionInfoId = sectionInfoId;
	}

	public Long getInfoId() {
		return infoId;
	}

	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	public YXOSectionInfo getSr() {
		return sr;
	}

	public void setSr(YXOSectionInfo sr) {
		this.sr = sr;
	}

	public Long getPubSectionId() {
		return pubSectionId;
	}

	public void setPubSectionId(Long pubSectionId) {
		this.pubSectionId = pubSectionId;
	}

	public YXOBidInfo getBid() {
		return bid;
	}

	public void setBid(YXOBidInfo bid) {
		this.bid = bid;
	}

	public PageInfo getInfo() {
		return info;
	}

	public void setInfo(PageInfo info) {
		this.info = info;
	}

	public String getMoneyState() {
		return moneyState;
	}

	public void setMoneyState(String moneyState) {
		this.moneyState = moneyState;
	}

	public Long getEconomyId() {
		return economyId;
	}

	public void setEconomyId(Long economyId) {
		this.economyId = economyId;
	}

	public String commonList(Long eid) {

		bidList = systemService.getNameById(service.list(bidHql, eid));
		equipList = systemService.getNameById2(service.list(equipHql, eid));
		marryTypeManageList = typeManageService.getYXTypeManage(1012l);
		sectionTypeManageList = typeManageService.getYXTypeManage(1011l);

		YXOProgramEconomy oe = (YXOProgramEconomy) systemService.uniqueQuery(
				economyHql, eid);
		exp = (Employee) service.uniqueResult(ehql, oe.getExployeeId());
		this.proEconomy = oe;

		List<YXOSectionInfo> os = systemService
				.sectionInfoList(sectionHql, eid);
		this.sectionInfoList = os;

		return "economyInfo";
	}

	public void commonLists(Long eid) {

		bidList = systemService.getNameById(service.list(bidHql, eid));
		equipList = systemService.getNameById2(service.list(equipHql, eid));
		marryTypeManageList = typeManageService.getYXTypeManage(1012l);
		sectionTypeManageList = typeManageService.getYXTypeManage(1011l);

		YXOProgramEconomy oe = (YXOProgramEconomy) systemService.uniqueQuery(
				economyHql, eid);
		exp = (Employee) service.uniqueResult(ehql, oe.getExployeeId());
		this.proEconomy = oe;

		List<YXOSectionInfo> os = systemService
				.sectionInfoList(sectionHql, eid);
		this.sectionInfoList = os;
	}

	public YXOSectionRecord getRecord() {
		return record;
	}

	public void setRecord(YXOSectionRecord record) {
		this.record = record;
	}

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	}

	public Long getChoseSectionId() {
		return choseSectionId;
	}

	public void setChoseSectionId(Long choseSectionId) {
		this.choseSectionId = choseSectionId;
	}

	public Long getS2ida() {
		return s2ida;
	}

	public void setS2ida(Long s2ida) {
		this.s2ida = s2ida;
	}

	public Long getSeid() {
		return seid;
	}

	public void setSeid(Long seid) {
		this.seid = seid;
	}

	public List getInfoList() {
		return infoList;
	}

	public void setInfoList(List infoList) {
		this.infoList = infoList;
	}

	public Employee getExp() {
		return exp;
	}

	public void setExp(Employee exp) {
		this.exp = exp;
	}

	public Long getBack() {
		return back;
	}

	public void setBack(Long back) {
		this.back = back;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public String getEditId() {
		return editId;
	}

	public void setEditId(String editId) {
		this.editId = editId;
	}

	public YXOSectionInfo getSsave() {
		return ssave;
	}

	public void setSsave(YXOSectionInfo ssave) {
		this.ssave = ssave;
	}

	public Long getProjectEconomyId() {
		return projectEconomyId;
	}

	public void setProjectEconomyId(Long projectEconomyId) {
		this.projectEconomyId = projectEconomyId;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public YXOProgramEconomy getProe() {
		return proe;
	}

	public void setProe(YXOProgramEconomy proe) {
		this.proe = proe;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public YXOSectionRecordInfo getYxosrinfo() {
		return yxosrinfo;
	}

	public void setYxosrinfo(YXOSectionRecordInfo yxosrinfo) {
		this.yxosrinfo = yxosrinfo;
	}

	public Long getAdd() {
		return add;
	}

	public void setAdd(Long add) {
		this.add = add;
	}

	public Double getMoneyy() {
		return moneyy;
	}

	public void setMoneyy(Double moneyy) {
		this.moneyy = moneyy;
	}

	public String getGroupp() {
		return groupp;
	}

	public void setGroupp(String groupp) {
		this.groupp = groupp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getChoseSectionIdSet() {
		return choseSectionIdSet;
	}

	public void setChoseSectionIdSet(Long choseSectionIdSet) {
		this.choseSectionIdSet = choseSectionIdSet;
	}

	public Long getChoseSectionIdGet() {
		return choseSectionIdGet;
	}

	public void setChoseSectionIdGet(Long choseSectionIdGet) {
		this.choseSectionIdGet = choseSectionIdGet;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public List<YXTypeManage> getSectionTypeManageList() {
		return sectionTypeManageList;
	}

	public void setSectionTypeManageList(
			List<YXTypeManage> sectionTypeManageList) {
		this.sectionTypeManageList = sectionTypeManageList;
	}

	public Long getSectionRecordId() {
		return sectionRecordId;
	}

	public void setSectionRecordId(Long sectionRecordId) {
		this.sectionRecordId = sectionRecordId;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getCountSect() {
		return countSect;
	}

	public void setCountSect(Long countSect) {
		this.countSect = countSect;
	}

	public List<YXTypeManage> getMarryTypeManageList() {
		return marryTypeManageList;
	}

	public void setMarryTypeManageList(List<YXTypeManage> marryTypeManageList) {
		this.marryTypeManageList = marryTypeManageList;
	}

	public String getTypeCodee() {
		return typeCodee;
	}

	public void setTypeCodee(String typeCodee) {
		this.typeCodee = typeCodee;
	}

	public List<YXOSectionInfo> getSectionInfoList() {
		return sectionInfoList;
	}

	public void setSectionInfoList(List<YXOSectionInfo> sectionInfoList) {
		this.sectionInfoList = sectionInfoList;
	}

	public Long getLastId() {
		return lastId;
	}

	public void setLastId(Long lastId) {
		this.lastId = lastId;
	}

	public Long getBpmsF() {
		return bpmsF;
	}

	public void setBpmsF(Long bpmsF) {
		this.bpmsF = bpmsF;
	}

	public YXOSectionInfo getSrr() {
		return srr;
	}

	public void setSrr(YXOSectionInfo srr) {
		this.srr = srr;
	}

	public Long getLastChoseSectionId() {
		return lastChoseSectionId;
	}

	public void setLastChoseSectionId(Long lastChoseSectionId) {
		this.lastChoseSectionId = lastChoseSectionId;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Long getSrid() {
		return srid;
	}

	public void setSrid(Long srid) {
		this.srid = srid;
	}

	public Long getSectionID() {
		return sectionID;
	}

	public void setSectionID(Long sectionID) {
		this.sectionID = sectionID;
	}

	public List<YXOEquipList> getDutyList2() {
		return dutyList2;
	}

	public void setDutyList2(List<YXOEquipList> dutyList2) {
		this.dutyList2 = dutyList2;
	}
}
