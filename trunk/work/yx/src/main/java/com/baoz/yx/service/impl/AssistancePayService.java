package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.security.krb5.internal.Ticket;

import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.Assistance;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.entity.SectionAndPayInfoRelation;
import com.baoz.yx.entity.SupplierInfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistancePayService;
import com.baoz.yx.service.INoticeService;
import com.baoz.yx.service.IYXTypeManageService;
import com.baoz.yx.utils.UserUtils;
import com.baoz.yx.vo.ProcessResult;

import common.Logger;

@Service("assistancePayService")
@Transactional
public class AssistancePayService implements IAssistancePayService {

	private static Logger logger=Logger.getLogger(ContractService.class);

	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao commonDao;
	
	@Autowired
	@Qualifier("yxTypeManageService")
	private IYXTypeManageService typeManageService;
	
	@Autowired
	@Qualifier("noticeService")
	private INoticeService noticeservice;

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		AssistancePayService.logger = logger;
	}

	public IYXCommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(IYXCommonDao commonDao) {
		this.commonDao = commonDao;
	}

	public IYXTypeManageService getTypeManageService() {
		return typeManageService;
	}

	public void setTypeManageService(IYXTypeManageService typeManageService) {
		this.typeManageService = typeManageService;
	}

	public INoticeService getNoticeservice() {
		return noticeservice;
	}

	public void setNoticeservice(INoticeService noticeservice) {
		this.noticeservice = noticeservice;
	}

	public void createRelationBetweenPayInfoandTicket(
			List<AssistancePayInfo> payInfoList,
			List<AssistanceTicket> ticketList,AssistancePayInfo payInfo) {
		//payInfo为本次支付的申请单:
		//1.如果payInfo为预付申请单，由于预付无法关联预付，故此情况下payInfoList无作用
		//2.如果payInfo为非预付申请单，payInfoList为关联的预付申请单列表
		
		//发票的ID序列号
		Long[] ticketid = new Long[ticketList.size()];
		//预付和本次支付的总和金额
		Double allPayMoney = 0.00;
		///计算关联的预付款的总金额
		if(!payInfo.getApplyPay()){
			 for(AssistancePayInfo prepPay:payInfoList){
		        	allPayMoney+=prepPay.getPayNum();
		        }
		}
		allPayMoney += payInfo.getPayNum(); 
        for(int i=0;i<ticketList.size();i++){
        	ticketid[i]=ticketList.get(i).getId();	
        }
        
        if(ticketid.length!=0){
	        //发票金额的余额需要自动填充所以重新根据日期排序获取发票列表
	        String findTicketListHql = " from AssistanceTicket where id in ( "+ StringUtils.join(ticketid, ',')+" ) order by ticket_Time ";
	        //把查找出的LIST替代原来的TICKETLIST
	        ticketList =  commonDao.list(findTicketListHql);
	        for(AssistanceTicket ticket:ticketList){
	        	if(allPayMoney!=0D){
		        	if(allPayMoney<ticket.getDoneMoney()){
		        		//获取这张票和这个申请单的关联对象
		        		Assistance assistance =  new Assistance();
		        		assistance.setAssistancePayId(payInfo.getId());
		        		assistance.setIs_active("1");
		        		assistance.setRelationPrepPayAmount(allPayMoney);
		        		ticket.setDoneMoney(ticket.getDoneMoney()-allPayMoney);
		        		if(!StringUtils.equals(ticket.getIs_related(), "1")){
		            		ticket.setIs_related("1");
		            	}
		        		commonDao.update(ticket);
		        		assistance.setTicket(ticket);
		        		commonDao.save(assistance);
		        		allPayMoney = 0D;
		        	}else{
		        		Assistance assistance =  new Assistance();
		        		assistance.setAssistancePayId(payInfo.getId());
		        		assistance.setIs_active("1");
		        		assistance.setRelationPrepPayAmount(ticket.getDoneMoney());
		        		ticket.setDoneMoney(0D);
		        		if(!StringUtils.equals(ticket.getIs_related(), "1")){
		            		ticket.setIs_related("1");
		            	}
	                    commonDao.update(ticket);
	                    assistance.setTicket(ticket);
	                    commonDao.save(assistance);
	    	        	allPayMoney -= ticket.getDoneMoney();
		        	}
	        	}
	        } 
        }   
	}
	public void createRelationBetweenSectionandPayInfo(
			List<AssistanceSection> sectionList,
			AssistancePayInfo payInfo) {
		//把外协阶段和payInfo进行关联 
		for(AssistanceSection assistanceSection:sectionList){
			SectionAndPayInfoRelation sectionAndPayInfoRelation = new SectionAndPayInfoRelation();
			sectionAndPayInfoRelation.setSectionId(assistanceSection.getId());
			sectionAndPayInfoRelation.setPayInfoId(payInfo.getId());
			sectionAndPayInfoRelation.setAssistanceContractId(payInfo.getAssistanceId());
			sectionAndPayInfoRelation.setImportFromFile(false);
			commonDao.save(sectionAndPayInfoRelation);	
		}
	}

	public void createRelationBetweenPrePayInfoandPayInfo(
			List<AssistancePayInfo> payInfoList,AssistancePayInfo payInfo) {
				/**
				 * 说明：如果payInfo为非预付款，可能会进行非预付款关联预付款的操作
				 * 如果payInfo为预付款则跳过该操作！！！！
				 */
        	  if(!payInfo.getApplyPay()){
        	      //给payInfoList中是预付款的申请单的关联字段赋值 并回填任务号接收号
                  for(AssistancePayInfo prepPayInfo:payInfoList){
                	  if(prepPayInfo.getApplyPay()){
                		  prepPayInfo.setRelationPrePayId(payInfo.getId());
                		  prepPayInfo.setReceivNum(payInfo.getReceivNum());
                		  prepPayInfo.setAssignmentId(payInfo.getAssignmentId());
                		  commonDao.update(prepPayInfo);
                	  }
                  }
        	  }
	}

	public Double getAllRelationPayInfoMoney(AssistancePayInfo payInfo) {
		String allRelationPayInfoMoneyHql =" select sum(payNum) from AssistancePayInfo where relationPrePayId = "+payInfo.getId();
		Double allRelationPayInfoMoney  = (Double)commonDao.uniqueResult(allRelationPayInfoMoneyHql);
		return (allRelationPayInfoMoney+payInfo.getPayNum());
	}
	
	public AssistancePayInfo saveAssistancePayInfo(AssistancePayInfo payInfo){
		
		AssistanceContract contract = (AssistanceContract)commonDao.load(AssistanceContract.class, payInfo.getAssistanceId());
		String contractHql = "select contract from  ContractMainInfo contract,AssistanceContract assistance " +
				"where assistance.contractId = contract.conMainInfoSid and assistance.id = ?";
		ContractMainInfo contractMain = (ContractMainInfo)commonDao.uniqueResult(contractHql,payInfo.getAssistanceId());
		payInfo.setEmployeeId(contractMain.getSaleMan());//////维护销售员
		SupplierInfo supply =(SupplierInfo)commonDao.load(SupplierInfo.class, contract.getSupplyId());
		payInfo.setSupplyId(supply.getSupplierid());////维护外协供应商
		payInfo.setUpdateBy(new Date());
		payInfo.setById(UserUtils.getUser().getId());
		payInfo.setIs_active("1");
		////////////////////
		if (contract.getTicketMoney() == null)
			contract.setTicketMoney(0.0);
		Date date = new Date();
		payInfo.setApplyDate(date);
		payInfo.setUpdateBy(new Date());
		payInfo.setById(UserUtils.getUser().getId());
		payInfo.setIs_active("1");
		commonDao.saveOrUpdate(payInfo);
		return payInfo;
	}
	
	public void deleteHasRelationSectionByPayInfoId(Long payInfoId){
		String sectionHql = "select relation from SectionAndPayInfoRelation relation,AssistancePayInfo py where relation.payInfoId = py.id and py.id = ?";
		List<SectionAndPayInfoRelation> sectionAllList = commonDao.list(sectionHql, payInfoId);
		for (SectionAndPayInfoRelation relation : sectionAllList) {
			commonDao.delete(relation);
		}
	}
	
	public Double getRelationSectionAmountBySectionId(Long[] assistanceSectionIds){
		String ids = StringUtils.join(assistanceSectionIds, ',');
		String hql = "select sum(section.sectionAmount) from AssistanceSection section where section.id in("+ids+")";
		Number sum = (Number)commonDao.uniqueResult(hql);
		if(sum!=null){
			return sum.doubleValue();
		}
		else{
			return 0.0;
		}
	}

	public List<AssistanceSection> getSectionBySectionIds(Long[] assistanceSectionIds){
		List<AssistanceSection> list = new ArrayList<AssistanceSection>();
		if(assistanceSectionIds != null && assistanceSectionIds.length > 0){
			for(int i = 0 ; i < assistanceSectionIds.length; i ++ ){
				AssistanceSection  section = (AssistanceSection)commonDao.load(AssistanceSection.class, assistanceSectionIds[i]);
				list.add(section);
			}
		}
			return list;
	}
	public List<AssistanceTicket> changeTicketToAssistance(List<Assistance> assistanceList){
		List<AssistanceTicket> ticketList = new ArrayList<AssistanceTicket>();
		Map<Long, AssistanceTicket> ticketMap = new HashMap<Long, AssistanceTicket>();
		for (Assistance assistance : assistanceList) {
			AssistanceTicket ticket = (AssistanceTicket)commonDao.load(AssistanceTicket.class, assistance.getTicket().getId());
			ticketMap.put(ticket.getId(), ticket);
		}
		ticketList.addAll(ticketMap.values());
		return ticketList;
		
	}
	
	public AssistancePayInfo updateAssistancePayInfo(AssistancePayInfo payInfo){
		/**
		 * 需要修改的东西有：
		 * 1，任务号，2接受号，3，是否预付，4，付款事项说明5，备注，6，本次申请金额
		 */
		AssistancePayInfo assistancePayInfo = (AssistancePayInfo)commonDao.load(AssistancePayInfo.class, payInfo.getId());
		assistancePayInfo.setAssignmentId(payInfo.getAssignmentId());
		assistancePayInfo.setReceivNum(payInfo.getReceivNum());
		assistancePayInfo.setApplyPay(payInfo.getApplyPay());
		assistancePayInfo.setInfo(payInfo.getInfo());
		assistancePayInfo.setRemark(payInfo.getRemark());
		assistancePayInfo.setPayNum(payInfo.getPayNum());
		assistancePayInfo.setUpdateBy(new Date());
		assistancePayInfo.setById(UserUtils.getUser().getId());
		commonDao.update(assistancePayInfo);
		return assistancePayInfo;
	}
	
	public AssistancePayInfo saveAssistancePayInfo(AssistancePayInfo payInfo,List<AssistancePayInfo> payInfoList,List<AssistanceTicket> ticketList,List<AssistanceSection> assistanceSectionList){
		AssistancePayInfo newAssistance = saveAssistancePayInfo(payInfo);
		deleteHasRelationSectionByPayInfoId(newAssistance.getId());
		createRelationBetweenPayInfoandTicket(payInfoList, ticketList,newAssistance);
		createRelationBetweenSectionandPayInfo(assistanceSectionList, newAssistance);
		createRelationBetweenPrePayInfoandPayInfo(payInfoList,newAssistance);
		return newAssistance;
	}
	
	public void unchainRelation(Assistance assistance){
		AssistanceTicket ticket=assistance.getTicket();
		Double opMoney=assistance.getRelationPrepPayAmount()== null ? 0.00 : assistance.getRelationPrepPayAmount();
		ticket.setDoneMoney(ticket.getDoneMoney() + opMoney);
	//////判断与该关联相关的发票是否还存在关联，如果存在关联不操作，如果不存在关联了则把发票表中的is_related字段设置成0
		if(!checkTicketInAssistance(ticket, assistance.getId())){
			ticket.setIs_related("0");
		}
		commonDao.update(ticket);
		commonDao.delete(assistance);
		
	}
	
	/**
	 * 如果存在返回true。此时不做任何操作。如果不存在返回false。此时把发票表中的is_related字段设置成0
	 * @param ticket
	 * @param assistanceId
	 * @return
	 */
	public Boolean checkTicketInAssistance(AssistanceTicket ticket,Long assistanceId){
		String hql = "select count(*) from Assistance assistance , AssistanceTicket ticket " +
				"where assistance.ticket = ticket and ticket.id = ? and assistance.id != ?";
		Number count = (Number)commonDao.uniqueResult(hql, ticket.getId(),assistanceId);
		if(count!=null && count.longValue()>0){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}
	
	public Assistance doTicketByUpdateApply(Assistance assistance,AssistancePayInfo payInfo,List<AssistancePayInfo> prepPayInfoList){
		AssistanceTicket ticket = assistance.getTicket();
		Double sum = 0.0;
		if(prepPayInfoList != null &&prepPayInfoList.size() > 0){
			for (AssistancePayInfo prepPay : prepPayInfoList) {
				sum += prepPay.getPayNum();
			}
		}
		/**
		 * 本次支付的金额+关联的预付款的金额- 本次支付已经关联的金额的差
		 */
		String assistanceHql = "select sum(assistance.relationPrepPayAmount)  from AssistancePayInfo payInfo, Assistance assistance where assistance.assistancePayId = payInfo.id and payInfo.id = ?";
		Number assistanceNumber = (Number) commonDao.uniqueResult(assistanceHql, payInfo.getId());
		Double assistanceSum =0.0;
		if(assistanceNumber != null ){
			assistanceSum = assistanceNumber.doubleValue();
		}
		Double opFee = payInfo.getPayNum()+sum-assistanceSum;
		if(ticket.getDoneMoney() == 0.00){
			return null;
		}
		if(ticket.getDoneMoney() > opFee){///// 如果发票的余额大于本次申请的金额+所关联的预付款的金额的和则操作。
			assistance.setRelationPrepPayAmount(opFee);
			ticket.setDoneMoney(ticket.getDoneMoney() - opFee);
		}else{
			assistance.setRelationPrepPayAmount(ticket.getDoneMoney());
			ticket.setDoneMoney(0.00);
		}
		ticket.setIs_related("1");
		commonDao.save(assistance);
		commonDao.update(ticket);
		return assistance;
	}

	
	public ProcessResult deleteAssistancePayInfo(Long payInfoId){
		ProcessResult result = new ProcessResult();
		AssistancePayInfo payInfo = (AssistancePayInfo)commonDao.load(AssistancePayInfo.class, payInfoId);
		if(payInfo.getApplyPay()){
			if(payInfo.getRelationPrePayId()!= null){
				result.setSuccess(Boolean.FALSE);
				result.addErrorMessage("预付款申请已经关联过正常付款，不能删除");
				return result;
			}
		}
		
		//No1，删除assistance关联
		//No2,assistanceTicket钱还回去
		//No3,删除sectionAndPayInfoRelation关联
		//No4,如果是非预付款还要看看是否关联了预付款，如果关联了把关联字段清除
		//No5,删除assistancePayInfo
		String assistanceHql = "select assistance from Assistance assistance," +
				"AssistancePayInfo payInfo where assistance.assistancePayId = payInfo.id " +
				"and payInfo.id = ?";
		//////No1,No2
		List<Assistance> assistanceList = commonDao.list(assistanceHql, payInfo.getId());
		for (Assistance assistance : assistanceList) {
			AssistanceTicket ticket = assistance.getTicket();
			Double opMoney = ticket.getDoneMoney() == null ? 0.00 : ticket.getDoneMoney();
			ticket.setDoneMoney(opMoney + assistance.getRelationPrepPayAmount());
			commonDao.update(ticket);
			commonDao.delete(assistance);
		}
		////No3
		String relationHql = "select relation from SectionAndPayInfoRelation relation,AssistancePayInfo payInfo " +
				"where relation.payInfoId = payInfo.id and payInfo.id = ?";
		List<SectionAndPayInfoRelation> relationList = commonDao.list(relationHql, payInfo.getId());
		for (SectionAndPayInfoRelation sectionAndPayInfoRelation : relationList) {
			commonDao.delete(sectionAndPayInfoRelation);
		}
		
		
		
		//////No4
		if(!payInfo.getApplyPay()){
			String relationPrepHql = "select payInfo from AssistancePayInfo payInfo where payInfo.relationPrePayId = ?";
			List<AssistancePayInfo> prepPayList = commonDao.list(relationPrepHql, payInfo.getId());
			if(prepPayList !=null &&prepPayList.size() > 0 ){
				for (AssistancePayInfo prepPay : prepPayList) {
					prepPay.setRelationPrePayId(null);
					commonDao.update(prepPay);
				}
			}
		}
		
		///////No5
		commonDao.delete(payInfo);
		result.setSuccess(Boolean.TRUE);
		result.addSuccessMessage("付款申请删除成功！");
		return result;
	}
	
	public void deleteAssistanceTicket(Long[] ticketIds){
		for (int i = 0; i < ticketIds.length; i++) {
			deleteAssistanceTicket(ticketIds[i]);
		}
	}
	public void deleteAssistanceTicket(Long ticketId){
		
		AssistanceTicket ticket = (AssistanceTicket)commonDao.load(AssistanceTicket.class, ticketId);
		if(ticket.getContractId()!= null)
		{
			AssistanceContract contract = (AssistanceContract)commonDao.load(AssistanceContract.class, ticket.getContractId());
			if("4".equals(ticket.getType())){
				Double receiptAmount = contract.getReceiptAmount() == null ? 0.00 : contract.getReceiptAmount();
				contract.setReceiptAmount(receiptAmount-ticket.getMoney());
			}else{
				Double ticketMoney = contract.getTicketMoney() == null ? 0.00 : contract.getTicketMoney();
				contract.setTicketMoney(ticketMoney - ticket.getMoney());
			}
			commonDao.update(contract);
		}
		commonDao.delete(ticket);
	}
	
	public List<AssistanceTicket> getTicketByAssistancePayInfo(AssistancePayInfo payInfo){
		String assistanceHql = "select assistance from Assistance assistance,AssistancePayInfo payInfo where assistance.assistancePayId = payInfo.id and payInfo.id = ?";
		List<Assistance> assistanceList = commonDao.list(assistanceHql,payInfo.getId());
		return changeTicketToAssistance(assistanceList);
	}
	
	public List<AssistancePayInfo> getPrepPayInfoListByRelationNotPrep(AssistancePayInfo notPrep){
		String hql = "from AssistancePayInfo payInfo where payInfo.applyPay = 1 and payInfo.relationPrePayId = ?";
		List<AssistancePayInfo> prepPayList = commonDao.list(hql,notPrep.getId());
		return prepPayList;
	}
	
	public List<AssistanceTicket> getTicketByConId(Long assConId) {
		String hql = " from AssistanceTicket at where at.contractId = ? ";
		List<AssistanceTicket> ticketList = commonDao.list(hql, assConId);
		return ticketList;
	}
	
	
}
