package com.baoz.yx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.service.IQueryService;
import com.baoz.core.utils.PageInfo;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.Assistance;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.YXClientCode;
import com.baoz.yx.entity.YXTypeManage;
import com.baoz.yx.entity.contract.ContractItemStage;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistancePayService;
import com.baoz.yx.service.IAssistanceService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.MyBean;
import common.Logger;
@Service("assistanceService")
@Transactional
public class AssistanceService implements IAssistanceService {

	private static Logger logger =  Logger.getLogger(AssistanceService.class);
	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao commonDao;
	@Autowired
	@Qualifier("queryService")
	private IQueryService queryService;
	@Autowired
	@Qualifier("assistancePayService")
	private IAssistancePayService assistancePayService;

	private AssistanceContract ac; 

	private PageInfo info;

	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;

	public List listA(String hql) {
		List l = null;
		if (hql != null) {
			l = commonDao.list(hql);
		}
		return l;
	}
	public Object[] isSureSubmit(String[] ids){
		commonDao.flushSession();
		Object[] obj = new Object[]{Boolean.FALSE,-1L,"-1"};
		for (String asId : ids) {
			AssistanceContract contract = (AssistanceContract)commonDao.load(AssistanceContract.class, Long.valueOf(asId));
			String supplyHql = "select sp from AssistanceContract ac,SupplierInfo sp where ac.supplyId = sp.supplierid and ac.id = ?";
			SupplierInfo supply = (SupplierInfo) commonDao.uniqueResult(supplyHql, contract.getId());
			if(!NumberUtils.isDigits(supply.getSupplierCode())){
				obj[0] = Boolean.TRUE;
				obj[1] = Long.valueOf(asId);
				obj[2] = "1";/////1表示供应商erp编号不正确
				return obj;
			}

			String sectionHql = "select section from AssistanceSection section,AssistanceContract contract " +
			"where section.assistanceId = contract.id and contract.id = ?";
			List<AssistanceSection> sectionList = commonDao.list(sectionHql, contract.getId());
			////////验证是否添加阶段
			if(sectionList == null || sectionList.size() == 0){
				obj[0] = Boolean.TRUE;
				obj[1] = Long.valueOf(asId);
				obj[2] = "2";//表示没有添加阶段
				return obj;
			}

			////验证阶段在计划中是否存在
			/**
			 * No1,该阶段在该合同中，查询itemStage表；
			 * No2,该阶段和项目所在的计划是否存在，查询plan表；
			 */
			for (AssistanceSection section : sectionList) {
				String stageHql = "select count(*) from ContractItemStage stage,ContractMainInfo minfo " +
				"where minfo.conMainInfoSid = stage.contractMainSid and stage.conIStageSid = ? and minfo.conMainInfoSid = ?";
				Number stageCount = (Number)commonDao.uniqueResult(stageHql, section.getAssistanceStageSId(),section.getAssistanceConSId());
				if(stageCount ==null || stageCount.longValue() == 0){
					obj[0] = Boolean.TRUE;
					obj[1] = Long.valueOf(asId);
					obj[2] = "3";//表示合同阶段名称不正确
					return obj;
				}
				String planHql = "select count(*) from RealContractBillandRecePlan plan,ContractItemStage stage,ContractItemMaininfo item " +
				"where plan.conItemStage = stage.conIStageSid and plan.contractItemMaininfo = item.conItemMinfoSid and plan.conMainInfoSid = ?" +
				"and stage.conIStageSid = ? and item.conItemMinfoSid = ?";
				Number planCount = (Number)commonDao.uniqueResult(planHql,contract.getContractId(), section.getAssistanceStageSId(),contract.getConItemMainInfoSid());
				if(planCount == null || planCount.longValue() == 0){
					obj[0] = Boolean.TRUE;
					obj[1] = Long.valueOf(asId);
					obj[2] = "3";//表示合同阶段名称不正确
					return obj;
				}
			}
			////////验证阶段金额等于外协合同金额
			String sumSectionHql = "select sum(section.sectionAmount) from AssistanceSection section,AssistanceContract contract " +
			"where section.assistanceId = contract.id and contract.id = ?";
			Number sumSection = (Number)commonDao.uniqueResult(sumSectionHql, contract.getId());
			if(sumSection == null || sumSection.doubleValue() != contract.getContractMoney()){
				obj[0] = Boolean.TRUE;
				obj[1] = Long.valueOf(asId);
				obj[2] = "4";//表示阶段金额不等于合同金额
				return obj;
			}

			///////验证外协合同金额不大于项目金额
			BigDecimal itemAmount = (BigDecimal) commonDao
			.uniqueResult(
					" select sum(ii.conItemAmountWithTax) from ContractItemInfo ii,ContractItemMaininfo cim where ii.contractItemMaininfo = cim.conItemMinfoSid and cim.conItemMinfoSid  = ?",
					contract.getConItemMainInfoSid());
			Double tAmount = (Double) commonDao.uniqueResult(
					" select sum(ac.contractMoney) from AssistanceContract ac where ac.conItemMainInfoSid = ? ",
					contract.getConItemMainInfoSid());
			if(itemAmount.doubleValue() < tAmount ){
				obj[0] = Boolean.TRUE;
				obj[1] = Long.valueOf(asId);
				obj[2] = "5";//表示外协合同的金额总额大于项目金额
				return obj;
			}
		}
		return obj;
	}

	public Object[] isSureSubmitApply(Long payInfoId){
		/***
		 * 预付的时候判断关联收据金额是否大于等于本次付款金额
		 * 
		 */
		commonDao.flushSession();
		Object[] obj=new Object[]{Boolean.FALSE,-1L,"-1"};

		AssistancePayInfo payInfo = (AssistancePayInfo) commonDao.load(AssistancePayInfo.class, payInfoId);
		AssistanceContract contract = getContactByPayInfoId(payInfoId);
		//不是预付申请单时候判断
		if(!payInfo.getApplyPay()){
			//判断任务号，接收号是否未空
			if(StringUtils.isBlank(payInfo.getReceivNum())){
				//接收号为空不得提交
				return getReturnInfo(Boolean.TRUE,payInfo.getId(),"1");
			}
			if(StringUtils.isBlank(payInfo.getAssignmentId())){
				//任务号为空不得提交
				return getReturnInfo(Boolean.TRUE,payInfo.getId(),"2");
			}	
		}
		//关联阶段总额等于付款申请金额（不论是否预付都要判断）
		String getSectionAllMoneyHql = "select sum(ass.sectionAmount) from SectionAndPayInfoRelation spr,AssistanceSection ass where ass.id = spr.sectionId and spr.payInfoId = ?";
		Double allSectionMoney = (Double)commonDao.uniqueResult(getSectionAllMoneyHql, payInfo.getId());
		if(allSectionMoney.doubleValue() != payInfo.getPayNum()){
			//阶段总金额不等于付款申请金额
			return getReturnInfo(Boolean.TRUE,payInfo.getId(),"3");
		}
		/**
		 * 添加两个判断：
		 * 1.如果是预付，判断本次支付金额加上已支付金额的是否大于等于合同金额，如果等于，提示最后一笔付款不能是预付款
		 * 2.如果是正常付款，判断本次支付金额加上已支付金额的和是否大于等于合同金额，如果等于，进一步查询该合同下面的其他预付款是否都关联付款了，如果还有没有关联的提出必须关联！
		 */
		///step 1:
		String payInfoSum = "select sum(payInfo.payNum) from AssistancePayInfo payInfo,AssistanceContract contract " +
				"where payInfo.assistanceId = contract.id and contract.id = ?";
		Number sumNumber = (Number) commonDao.uniqueResult(payInfoSum,contract.getId());
		double sumAmount = sumNumber == null ? 0.00 :sumNumber.doubleValue();
		double hasAmount = contract.getContractMoney().doubleValue();
		if(sumAmount >= hasAmount){
			if(payInfo.getApplyPay()){/// 最后一笔付款不能是预付款
				return getReturnInfo(Boolean.TRUE,payInfo.getId(),"4");
			}else{
				String relationHql = "select count(*) from AssistancePayInfo payInfo,AssistanceContract contract " +
						"where payInfo.assistanceId = contract.id and payInfo.applyPay = 1 and  payInfo.relationPrePayId is null " +
						"and contract.id = ?";
				Number relationNumber = (Number)commonDao.uniqueResult(relationHql, contract.getId());
				if(relationNumber != null && relationNumber.longValue() > 0){
					///最后一笔付款申请必须关联预付款
					return getReturnInfo(Boolean.TRUE,payInfo.getId(),"5");
				}
			}
		}
		return obj;
	}

	/**
	 * 获取返回信息
	 * @return
	 */
	private Object[] getReturnInfo(Boolean info,Long id,String errorType){
		Object[] obj=new Object[]{info,id,errorType};
		return obj;
	}

	public AssistanceContract getContactByPayInfoId(Long payInfoId){

		String hql =" select contract from AssistanceContract contract,AssistancePayInfo payInfo where payInfo.assistanceId = contract.id and payInfo.id =?";
		AssistanceContract contract = (AssistanceContract)commonDao.uniqueResult(hql, payInfoId);
		return contract;
	}

	public void confirmPayInfoOperator(Long payInfoId){
		AssistancePayInfo payInfo = (AssistancePayInfo)commonDao.load(AssistancePayInfo.class, payInfoId);
		String aHql = "select ac from AssistanceContract ac,AssistancePayInfo payInfo where ac.id = payInfo.assistanceId and payInfo.id = ?";
		AssistanceContract ac = (AssistanceContract)commonDao.uniqueResult(aHql,payInfoId);
		double hasPayAmount = getHasPayAmount(ac);
		// 计算合同付款总额
		ac.setHasPayAmount(hasPayAmount+payInfo.getPayNum());
		if(ac.getHasPayAmount().doubleValue()== 0.00){
			ac.setAssistanceType("0");
			if(ac.getAssistanceContractType().equals("4")){
				ac.setAssistanceContractType("2");
			}
		}else if(ac.getContractMoney() == ac.getHasPayAmount().doubleValue()){
			ac.setAssistanceType("2");//原来是把合同的付款状态置为全额付款，现在不在维护成2了直接维护外协合同状态为已关闭（4）
			ac.setAssistanceContractType("4");
		}else {
			ac.setAssistanceType("1");
			if(ac.getAssistanceContractType().equals("4")){
				ac.setAssistanceContractType("2");
			}
		}
		commonDao.update(ac);
		contractService.itemIsCloseByItem(ac.getMainProjectId());
		// 更新剩余外协
		contractService.sumRemainAssCon(ac.getMainProjectId());
		List<Assistance> assistanceList = getAssistanceListByPayInfo(payInfo);
		for (Assistance assistance : assistanceList) {
			AssistanceTicket ticket = assistance.getTicket();
			double ticketHasPay = getHasRelationAssistanceAmount(ticket);
			ticket.setHasPayAmount(ticketHasPay + assistance.getRelationPrepPayAmount());
			if(ticket.getIsTurnOver()==null || !ticket.getIsTurnOver()){
				ticket.setIsTurnOver(Boolean.TRUE);
			}
			if(ticket.getTurnOverDate() == null ){
				ticket.setTurnOverDate(new Date());
			}
			commonDao.update(ticket);
		}
		
		
		payInfo.setPayState("4");
		payInfo.setRealPayTime(new Date());
		commonDao.update(payInfo);
	}
	/**
	 * 获取同一个外协合同下面已经支付的外协付款申请金额的和
	 * @param contract
	 * @return
	 */
	private Double getHasPayAmount(AssistanceContract contract){
		String amountHql = "select sum(payInfo.payNum) from AssistancePayInfo payInfo,AssistanceContract contract " +
		"where payInfo.assistanceId = contract.id and payInfo.payState = 4 and contract.id = ?";
		Number sumNumber = (Number) commonDao.uniqueResult(amountHql, contract.getId());
		return sumNumber == null ? 0.00 : sumNumber.doubleValue();
	}
	
	private Double getHasRelationAssistanceAmount(AssistanceTicket ticket){
			double sumAmount = 0.00;
			String sumHql = "select sum(assistance.relationPrepPayAmount) from Assistance assistance, " +
					" AssistancePayInfo payInfo,AssistanceTicket ticket where assistance.assistancePayId = payInfo.id " +
					"and assistance.ticket = ticket and payInfo.payState = 4  and ticket.id = ? ";
			Number sumNumber = (Number)commonDao.uniqueResult(sumHql, ticket.getId());
			if(sumNumber != null ){
				sumAmount = sumNumber.doubleValue();
			}
			return sumAmount;
	}
	
	private List<Assistance> getAssistanceListByPayInfo(AssistancePayInfo payInfo){
		String hql = "select assistance from Assistance assistance,AssistancePayInfo payInfo where assistance.assistancePayId = payInfo.id " +
				"and payInfo.id = ? ";
		List<Assistance> list = commonDao.list(hql, payInfo.getId());
		return list;
		
		
	}
	
	
	public void confirmPayInfoOperator(String[] ids){
		if(ids !=null &&ids.length>0){
			for( int i = 0 ; i < ids.length ; i ++ ){
				confirmPayInfoOperator(Long.valueOf(ids[i].trim()));
			}

		}
	}

	public void confirmPayInfoPass(Long payInfoId){
		AssistancePayInfo payInfo = (AssistancePayInfo)commonDao.load(AssistancePayInfo.class, payInfoId);
		payInfo.setPayState("2");
		commonDao.update(payInfo);
	}
	public SupplierInfo getSupplierByAssistanceContractId(Long contractId){
		String hql = "select supply from SupplierInfo supply,AssistanceContract contract where contract.supplyId = supply.supplierid and contract.id = ?";
		SupplierInfo supply = (SupplierInfo) commonDao.uniqueResult(hql, contractId);
		return supply;
	}

	public void confirmPayInfoPass(String[] ids){
		if(ids !=null &&ids.length>0){
			for( int i = 0 ; i < ids.length ; i ++ ){
				confirmPayInfoPass(Long.valueOf(ids[i]));
			}

		}
	}

	public List<MyBean> getStageByItemSid(Long itemSid){

		String hql = "select stage from RealContractBillandRecePlan plan," +
		"ContractItemStage stage,ContractItemMaininfo item " +
		"where plan.conItemStage = stage.conIStageSid " +
		"and plan.contractItemMaininfo = item.conItemMinfoSid " +
		"and item.conItemMinfoSid = ? ";
		List<ContractItemStage> list = commonDao.list(hql, itemSid);
		List<MyBean> returnList = new ArrayList<MyBean>();
		for (ContractItemStage contractItemStage : list) {
			MyBean myBean = new MyBean();
			String tmHql = "select tm from YXTypeManage tm where tm.typeSmall = ? and tm.typeBig = 1023 ";
			YXTypeManage tm  = (YXTypeManage)commonDao.uniqueResult(tmHql, contractItemStage.getItemStageName());
			myBean.setKey(contractItemStage.getConIStageSid());
			myBean.setValue(tm.getTypeName());
			returnList.add(myBean);
		}
		return returnList;
	}

	public List queryAssistanceContract(List list) {
		List<AssistanceContract> dlist = list;
		List alist = new ArrayList();
		if (dlist != null) {
			for (AssistanceContract ac : dlist) {
				Object[] ob = new Object[3];
				ob[0] = ac;
				// 得到供应商id,通过供应商id查询出供应商名称
				if (ac.getSupplyId() != null) {
					String chql = "from SupplierInfo d where d.supplierid ='" + ac.getSupplyId() +"'";
					SupplierInfo s = (SupplierInfo) commonDao
					.uniqueResult(chql);
					ob[1] = s.getSupplierName();					
				} else
					ob[1] = null;
				Long cId = ac.getContractId();
				if(cId != null){
					String conHql = "from ContractMainInfo cmi where cmi.conMainInfoSid = "+cId;
					ContractMainInfo cmi = (ContractMainInfo)commonDao.uniqueResult(conHql);
					ob[2] = cmi;
				}
				alist.add(ob);				
			}
		}
		return alist;
	}

	public List queryAssisContract(List list) {
		List<SupplierInfo> dlist = list;
		List alist = new ArrayList();
		if (dlist != null) {
			for (SupplierInfo s : dlist) {
				Object[] ob = new Object[2];
				ob[0] = s.getSupplierName();
				Long sid = s.getSupplierid(); // 得到供应商id,通过供应商id查询出供应商名称				
				if (sid != null && !"".equals(sid)) {
					String chql = "from AssistanceContract d where d.supplyId =" + sid;
					AssistanceContract ac = (AssistanceContract) commonDao
					.uniqueResult(chql);
					if (ac != null)
						ob[1] = ac;
					else
						ob[1] = null;
				} else
					ob[1] = null;		
				alist.add(ob);
			}
		}		
		return alist;
	}

	public List queryId(String supplier) {
		List<Long> idList = new ArrayList();
		String hql = "from SupplierInfo s where s.supplierName like '%"+supplier+"%'";
		List<SupplierInfo> si = commonDao.list(hql);
		for(SupplierInfo o:si){
			Long id = o.getSupplierid();
			idList.add(id);
		}
		return idList;
	}

	public List getSupply(List list) {
		List<AssistanceTicket> aList = list;
		List l = new ArrayList();
		if(aList!=null){
			for(AssistanceTicket at : aList){
				Object[] ob = new Object[2];
				ob[0] = at;
				Long sid = at.getCustomerId();
				String hql = "from SupplierInfo s where s.supplierid = "+sid;
				SupplierInfo s = (SupplierInfo)commonDao.uniqueResult(hql);
				ob[1] = s.getSupplierName();
				l.add(ob);
			}
		}
		return l;
	}

	public int updateState(String[] id) {
		int a = 0;
		for(String s : id){
			String hql = "update AssistanceContract ac set ac.assistanceContractType = '1' where ac.id = "+Long.parseLong(s);
			int i = commonDao.executeUpdate(hql);
			a += i;
		}
		return a;
	}

	public int updateState2(String[] id) {
		int a = 0;
		for(String s : id){
			String hql = "update AssistanceContract ac set ac.assistanceContractType = '2' where ac.id = "+Long.parseLong(s);
			int i = commonDao.executeUpdate(hql);
			a += i;
		}
		return a;
	}

	public int back(String[] id) {
		int a = 0;
		for(String s : id){
			String hql = "update AssistanceContract ac set ac.assistanceContractType = '0' where ac.id = "+Long.parseLong(s);
			int i = commonDao.executeUpdate(hql);
			a += i;
		}
		return a;
	}

	public List queryTicket(List list){
		List<AssistanceTicket> tList = list;
		List l = new ArrayList();
		for(AssistanceTicket at : tList){
			Object[] ob = new Object[2];
			ob[0] = at;
			Long supplyId = at.getCustomerId();
			String hql = "from SupplierInfo si where si.supplierid = "+supplyId;
			SupplierInfo si = (SupplierInfo)commonDao.uniqueResult(hql);
			ob[1] = si.getSupplierName();
			l.add(ob);
		}
		return l;
	}

	/**
	 * 查询正式合同的所有信息
	 * @return 
	 */
	public List<Object> contractList(List list) {
		List<ContractMainInfo> conList = list;
		List contractMainInfoList = new ArrayList();
		if (conList != null) {
			for (ContractMainInfo cmi : conList) {
				Object[] temp = new Object[3];
				temp[0] = cmi;
				String oql = "from YXClientCode yxcc where id="
					+ cmi.getConCustomer();
				YXClientCode yxcc = (YXClientCode) commonDao.uniqueResult(oql
						.toString());
				String oql1 = "from YXTypeManage yxtm where yxtm.typeSmall='"
					+ cmi.getMainItemDept() + "'";
				YXTypeManage yxtm = (YXTypeManage) commonDao.uniqueResult(oql1
						.toString());
				if (yxcc != null)
					temp[1] = yxcc.getName();
				if (yxtm != null)
					temp[2] = yxtm.getTypeName();
				contractMainInfoList.add(temp);
				temp = null;
			}
		}
		return contractMainInfoList;
	}

	/**
	 * 查询条件拼接合同查询的SQL语句
	 * @return 
	 */
	public String oql(String conName, String conId, String mainItemDept,
			String partyAProId ,String contype) {
		/*StringBuffer str = new StringBuffer();
		str.append("from ContractMainInfo cmi where cmi.conState=4");
		if (StringUtils.isNotEmpty(conName))
			str.append(" and conName like '%").append(conName).append("%'");
		if (StringUtils.isNotEmpty(conId))
			str.append(" and conId like '%").append(conId).append("%'");
		if (StringUtils.isNotEmpty(mainItemDept) && !"-1".equals(mainItemDept))
			str.append(" and mainItemDept = '").append(mainItemDept)
			.append("'");
		if (StringUtils.isNotEmpty(partyAProId))
			str.append(" and partyAProId = '").append(partyAProId).append("'");
		if(StringUtils.isNotBlank(contype))
			str.append(" and ContractType='").append(contype).append("'");
		return str.toString();*/
		StringBuffer sb = new StringBuffer();
		sb.append("select p , c from ContractItemMaininfo p, ContractMainInfo c where p.contractMainInfo=c.conMainInfoSid and p.conItemId is not null and c.conState!='10' and c.saleMan="+UserUtils.getUser().getId());
		if (StringUtils.isNotEmpty(conName))
			sb.append(" and c.conName like '%").append(conName).append("%'");
		if (StringUtils.isNotEmpty(conId))
			sb.append(" and c.conId like '%").append(conId).append("%'");
		if (StringUtils.isNotEmpty(mainItemDept) && !"-1".equals(mainItemDept))
			sb.append(" and p.itemResDept = '").append(mainItemDept)
			.append("'");
		if (StringUtils.isNotEmpty(partyAProId))
			sb.append(" and p.conItemId = '").append(partyAProId).append("'");
		if(StringUtils.isNotBlank(contype))
			sb.append(" and ContractType='").append(contype).append("'");
		return sb.toString();
	}

	/**
	 * 查询条件拼接客户查询的SQL语句
	 * @return 
	 */
	public String oql1(String clientName) {
		StringBuffer str = new StringBuffer();
		str.append("from YXClientCode cc where cc.id not in(0) and cc.is_active!=0");
		if (StringUtils.isNotEmpty(clientName))
			str.append(" and name like'%").append(clientName).append("%'");

		return str.toString();
	}

	/**
	 * 获取外协合同 所属主合同 销售员名称
	 * 2008年8月18日13:52:00
	 */
	public String getAContractName(Long assistanceId){
		String name = (String)commonDao.uniqueResult("select e.name  from ContractMainInfo c , AssistanceContract a ,Employee e  where " +
				" c.conMainInfoSid = a.contractId and c.saleMan = e.id and  a.id = ? ", assistanceId );
		return name;
	}
	/**
	 * 拆分阶段金额
	 */
	public void splitSectionAmount(Long sectionId, Double splitAmount) {
		AssistanceSection oldSection = (AssistanceSection) commonDao.load(AssistanceSection.class, sectionId);
		try {
			AssistanceSection newSection =  (AssistanceSection) BeanUtils.cloneBean(oldSection);
			newSection.setId(null);
			newSection.setSectionAmount(oldSection.getSectionAmount() - splitAmount);
			commonDao.save(newSection);
			oldSection.setSectionAmount(splitAmount);
			commonDao.update(oldSection);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void mergeRealPlan(Long[] sectionId) {
		String sql = " from AssistanceSection ass " +
		"where ass.id in ("+StringUtils.join(sectionId,",")+") order by ass.id ";
		List<AssistanceSection> realList = commonDao.list(sql);
		AssistanceSection oldSection = realList.get(0);
		for (int i = 1; i < realList.size(); i++) {
			AssistanceSection plan1 = realList.get(i);
			oldSection.setSectionAmount(oldSection.getSectionAmount() + plan1.getSectionAmount());
			commonDao.delete(plan1);
		}
		commonDao.update(oldSection);
	}
	public Boolean checkSectionIsRelation(Long sectionId){

		String hql = "select count(*) from AssistanceSection section,SectionAndPayInfoRelation relation " +
		"where relation.sectionId = section.id and section.id = ?";
		Number count = (Number) commonDao.uniqueResult(hql, sectionId);
		if(count!=null&&count.longValue()>0){//表示已经被关联过了
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;//表示没有被关联过
		}
	}

	public Boolean checkSectionNotRelation(Long sectionId,Long payInfoId){
		String hql = "select count(*) from AssistanceSection section,SectionAndPayInfoRelation relation " +
		"where relation.sectionId = section.id and section.id = ? and relation.payInfoId != ?";
		Number count = (Number) commonDao.uniqueResult(hql, sectionId,payInfoId);
		if(count!=null&&count.longValue()>0){//表示已经被关联过了
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;//表示没有被关联过
		}
	}
	public Boolean getCheckedSectionRelation(Long sectionId,Long payInfoId){
		String hql = "select count(*) from AssistanceSection section,SectionAndPayInfoRelation relation " +
		"where relation.sectionId = section.id and section.id = ? and relation.payInfoId  = ?";
		Number count = (Number) commonDao.uniqueResult(hql, sectionId,payInfoId);
		if(count!=null&&count.longValue()>0){//表示已经被关联过了
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;//表示没有被关联过
		}
	}
	public List<AssistanceSection> getAssistanceSEctionByPayInfoId(Long payInfoId){
		String hql = "select section from AssistanceSection section,SectionAndPayInfoRelation relation " +
		"where relation.sectionId = section.id and relation.payInfoId  = ?";
		List<AssistanceSection> list =commonDao.list(hql, payInfoId);
		return list;
	}

	public Long[] getcheckedSectionByPayInfoId(Long payInfoId){
		String hql = "select section from AssistanceSection section,SectionAndPayInfoRelation relation " +
		"where relation.sectionId = section.id and relation.payInfoId  = ?";
		List<AssistanceSection> list =commonDao.list(hql, payInfoId);
		if(list!=null&&list.size()>0){
			Long[] payInfoIds = new Long[list.size()];
			for(int k = 0; k < list.size();k++){
				payInfoIds[k] = list.get(k).getId();
			}
			return payInfoIds;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<AssistanceSection> getAssistanceSectionByContractId(Long assistanceContractId){
		String hql = "from AssistanceSection section where section.assistanceId = ?";
		List<AssistanceSection> list = commonDao.list(hql, assistanceContractId);
		return list;
	}

	public Boolean checkAssignmentIdExists(String assignmentId){

		String hql = "select count(*) from AssistancePayInfo p where p.assignmentId = ?";
		Number count = (Number)commonDao.uniqueResult(hql, assignmentId);
		if(count!=null&&count.longValue()>0){//如果在付款表中存在的话返回true
			return Boolean.TRUE;
		}else{///如果不存在的话返回false
			return Boolean.FALSE;
		}
	}

	public Boolean checkAssignmentIdExists(String assignmentId,Long payInfo){
		String hql = "select count(*) from AssistancePayInfo p where p.assignmentId = ? and p.id != ?";
		Number count = (Number)commonDao.uniqueResult(hql, assignmentId,payInfo);
		if(count!=null&&count.longValue()>0){//如果在付款表中存在的话返回true
			return Boolean.TRUE;
		}else{///如果不存在的话返回false
			return Boolean.FALSE;
		}
	}

	public Boolean checkreceivNumExists(String reveNum){
		String hql = "select count(*) from AssistancePayInfo p where p.receivNum = ?";
		Number count = (Number)commonDao.uniqueResult(hql, reveNum);
		if(count!=null&&count.longValue()>0){//如果在付款表中存在的话返回true
			return Boolean.TRUE;
		}else{///如果不存在的话返回false
			return Boolean.FALSE;
		}
	}

	public Boolean checkreceivNumExists(String reveNum,Long payInfoId){
		String hql = "select count(*) from AssistancePayInfo p where p.receivNum = ? and p.id != ?";
		Number count = (Number)commonDao.uniqueResult(hql, reveNum,payInfoId);
		if(count!=null&&count.longValue()>0){//如果在付款表中存在的话返回true
			return Boolean.TRUE;
		}else{///如果不存在的话返回false
			return Boolean.FALSE;
		}
	}

	public Boolean checkAssistanceNoExists(String assistanceNo,Long assistanceId){
		String hql = "select count(*) from AssistanceContract contract where contract.assistanceId = ? and contract.id != ?";
		Number count = (Number)commonDao.uniqueResult(hql, assistanceNo,assistanceId);
		if(count != null&&count.longValue()>0){///表示存在，如果存在返回true
			return Boolean.TRUE;
		}else{////不存在返回false
			return Boolean.FALSE;
		}
	}
	@SuppressWarnings("unchecked")
	public List<AssistancePayInfo> getAssistancepayInfoBySupplierId(AssistanceContract assistanceContract){
		String hql = "select payInfo from AssistancePayInfo payInfo,AssistanceContract contract where " +
		"payInfo.assistanceId = contract.id  and payInfo.applyPay = 1 " +
		//"and payInfo.relationPrePayId is null " +
		"and payInfo.payState = '4' and payInfo.relationPrePayId is null " +
		"and contract.id = ?";
		List<AssistancePayInfo> list = commonDao.list(hql,assistanceContract.getId());
		return list;
	}
	public SupplierInfo getSupplierBySupplyId(Long supplyId){
		SupplierInfo  supply = (SupplierInfo)commonDao.load(SupplierInfo.class, supplyId);
		return supply;
	}

	public List<AssistancePayInfo> getPayInfoRecordNotContained(Long payInfoId){
		List<AssistancePayInfo> list = new ArrayList<AssistancePayInfo>();
		AssistancePayInfo payInfo = (AssistancePayInfo)commonDao.load(AssistancePayInfo.class,payInfoId);
		String piHql = "select pi from AssistancePayInfo pi,AssistanceContract contract where " +
				"pi.assistanceId = contract.id and pi.id != ? and contract.id = ? " +
				"order by pi.applyDate ";
		list = commonDao.list(piHql,payInfoId,payInfo.getAssistanceId());
		return list;
	}

	public List<AssistancePayInfo> getRelationPayInfoByPayInfo(Long payInfoId){
		List<AssistancePayInfo> list = new  ArrayList<AssistancePayInfo>();
		String hql = "select payInfo from AssistancePayInfo payInfo where payInfo.applyPay = 1 and payInfo.relationPrePayId = ?";
		list = commonDao.list(hql,payInfoId);
		return list;
	}



	public Object[]  cancelSure(Long[] outsourceContractId){
		List<String> sArray = new ArrayList<String>();
		String message="";
		int mark = 1;
		List<AssistanceContract> tempA = new ArrayList<AssistanceContract>();
		for(Long cid : outsourceContractId){
			AssistanceContract ac = (AssistanceContract)commonDao.load(AssistanceContract.class, cid);
			if( ! ac.getAssistanceContractType().equals("2") ){
				message = "只有正式外协合同才能取消确认";
				mark = 0;
				break;
			}
			if (  this.existAssistanceTicket(cid)   ){
				sArray.add( ac.getAssistanceName()+"存在外协发票不能取消确认");
				mark = 0;
				continue;
			}
			if (  this.existPayInfo(cid)   ){
				sArray.add( ac.getAssistanceName()+"存在付款申请不能取消确认");
				mark = 0;
				continue;
			}
			tempA.add(ac);
		}
		if(mark ==1 ){
			for( AssistanceContract a : tempA  ){
				a.setAssistanceContractType("1");
				a.setAssistanceId(null);
				commonDao.save(a);
			}
			message = "取消确认成功";
		}
		Object[]  o = new Object[2];
		if(message.equals("")){
			o[0] = sArray;
		}
		o[1] = message;
		return o;
	}

	/**
	 * 检查外协存在付款申请
	 */
	private boolean existPayInfo( Long id){
		String countPayApply = " select count(*) from  AssistancePayInfo ap where ap.assistanceId = ? and ap.is_active = '1' ";
		Long payCount = (Long)commonDao.uniqueResult( countPayApply ,  id);
		if(payCount>0){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * 检查外协存在发票
	 */
	private boolean existAssistanceTicket( Long id){
		String countAssistanceTicket = " select count(*) from  AssistanceTicket at where at.contractId = ? and at.is_active = '1' ";
		Long assistanceTicket = (Long)commonDao.uniqueResult( countAssistanceTicket ,  id);
		if(assistanceTicket>0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void cancelPayInfoOperator(String[] ids){
		for (int i = 0; i < ids.length; i++) {
			cancelPayInfoOperator(Long.valueOf(ids[i].trim()));
		}
	}
	

	public void cancelPayInfoOperator(Long payInfoId){
		AssistancePayInfo payInfo = (AssistancePayInfo)commonDao.load(AssistancePayInfo.class, payInfoId);
		String aHql = "select ac from AssistanceContract ac,AssistancePayInfo payInfo where ac.id = payInfo.assistanceId and payInfo.id = ?";
		AssistanceContract ac = (AssistanceContract)commonDao.uniqueResult(aHql,payInfoId);
		double hasPayAmount = getHasPayAmount(ac);
		// 计算合同付款总额
		ac.setHasPayAmount(hasPayAmount-payInfo.getPayNum());
		if(ac.getHasPayAmount().doubleValue()==0.00){
			ac.setAssistanceType("0");
			if(ac.getAssistanceContractType().equals("4")){
				ac.setAssistanceContractType("2");
			}
		}else if(ac.getContractMoney() == ac.getHasPayAmount().doubleValue()){
			ac.setAssistanceType("2");//原来是把合同的付款状态置为全额付款，现在不在维护成2了直接维护外协合同状态为已关闭（4）
			ac.setAssistanceContractType("4");
		}else {
			ac.setAssistanceType("1");
			if(ac.getAssistanceContractType().equals("4")){
				ac.setAssistanceContractType("2");
			}
		}
		commonDao.update(ac);
		contractService.itemIsCloseByItem(ac.getMainProjectId());
		// 更新剩余外协
		contractService.sumRemainAssCon(ac.getMainProjectId());
		List<Assistance> assistanceList = getAssistanceListByPayInfo(payInfo);
		for (Assistance assistance : assistanceList) {
			AssistanceTicket ticket = assistance.getTicket();
			double ticketHasPay = getHasRelationAssistanceAmount(ticket);
			ticket.setHasPayAmount(ticketHasPay - assistance.getRelationPrepPayAmount());
			if(ticket.getIsTurnOver()!=null && ticket.getIsTurnOver()){
				ticket.setIsTurnOver(null);
			}
			if(ticket.getTurnOverDate() != null ){
				ticket.setTurnOverDate(null);
			}
			commonDao.update(ticket);
		}
		payInfo.setPayState("2");
		payInfo.setRealPayTime(null);
		commonDao.update(payInfo);
	}


	public IYXCommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(IYXCommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public AssistanceContract getAc() {
		return ac;
	}

	public void setAc(AssistanceContract ac) {
		this.ac = ac;
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

	public IContractService getContractService() {
		return contractService;
	}
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}




}
