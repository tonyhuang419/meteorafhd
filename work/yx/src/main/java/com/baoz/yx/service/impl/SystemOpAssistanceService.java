package com.baoz.yx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoz.core.dao.ICommonDao;
import com.baoz.yx.dao.IYXCommonDao;
import com.baoz.yx.entity.Assistance;
import com.baoz.yx.entity.AssistanceContract;
import com.baoz.yx.entity.AssistancePayInfo;
import com.baoz.yx.entity.AssistanceSection;
import com.baoz.yx.entity.AssistanceTicket;
import com.baoz.yx.entity.SectionAndPayInfoRelation;
import com.baoz.yx.entity.contract.ContractItemMaininfo;
import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.service.IAssistancePayService;
import com.baoz.yx.service.ICodeGenerateService;
import com.baoz.yx.service.IContractService;
import com.baoz.yx.service.ISystemOpAssistanceService;
import com.baoz.yx.vo.ProcessResult;

@Service("systemOpAssistanceService")
@Transactional
public class SystemOpAssistanceService implements ISystemOpAssistanceService {

	
	
	
	@Autowired
	@Qualifier("yxCommonDao")
	private IYXCommonDao commonDao;
	
	@Autowired
	@Qualifier("codeGenerateService")
	private ICodeGenerateService codeGenerateService;
	
	@Autowired
	@Qualifier("assistancePayService")
	private IAssistancePayService assistancePayService;
	
	@Autowired
	@Qualifier("contractService")
	private IContractService contractService;
	

	public void createAssistanceApplyPayInfo
	(AssistanceContract contract,
			List<AssistanceSection> sectionList,
			AssistancePayInfo payInfo,List<AssistancePayInfo> payInfoList){
		double ticketMoney = 0.00;
		for (AssistanceSection assistanceSection : sectionList) {
			ticketMoney += assistanceSection.getSectionAmount();
		}
		payInfo.setPayNum(ticketMoney);
		AssistancePayInfo pay = createAssistancePayInfo(contract, payInfo);
		if(!payInfo.getApplyPay()){
			for(AssistancePayInfo prepPay : payInfoList){
				ticketMoney += prepPay.getPayNum();
			}
			AssistanceTicket ticket = createAssistanceTicket(contract, ticketMoney, payInfo.getApplyDate(), payInfo.getEmployeeId());
			/**
			 * 建立付款申请和ticket之间的关联
			 */
			createAssistance(ticket, pay, ticketMoney);
			/**
			 * 建立预付和正常付款申请之间的关联
			 */
			for(AssistancePayInfo prepPay : payInfoList){
				prepPay.setRelationPrePayId(pay.getId());
				commonDao.update(prepPay);
			}
		}
		/**
		 * 建立付款申请和阶段之间的关联
		 */
		createSectionAndPayInfoRelation(sectionList, pay);
		/**
		 * 修改外协合同的相关信息
		 */
		updateContractInfo(pay,"add");
		
	}
	
	///修改外协信息
	public void updateContractInfo(AssistancePayInfo payInfo,String op){
		commonDao.flushSession();
		AssistanceContract contract = (AssistanceContract)commonDao.load(AssistanceContract.class, payInfo.getAssistanceId());
		Double hasPayAmount = getHasPayAmount(contract);
		contract.setHasPayAmount(hasPayAmount);
		if(StringUtils.equals(op, "del")){
			contract.setHasPayAmount(hasPayAmount-payInfo.getPayNum());
		}
		if(contract.getHasPayAmount().doubleValue()==0.00){
			contract.setAssistanceType("0");
			if(contract.getAssistanceContractType().equals("4")){
				contract.setAssistanceContractType("2");
			}
		}else if(contract.getContractMoney() == contract.getHasPayAmount().doubleValue()){
			contract.setAssistanceType("2");//原来是把合同的付款状态置为全额付款，现在不在维护成(2)了直接维护外协合同状态为已关闭（4）
			contract.setAssistanceContractType("4");
		}else {
			contract.setAssistanceType("1");
			if(contract.getAssistanceContractType().equals("4")){
				contract.setAssistanceContractType("2");
			}
		}
		commonDao.update(contract);
		ContractItemMaininfo itemMain = (ContractItemMaininfo) commonDao.load(ContractItemMaininfo.class,contract.getConItemMainInfoSid());
		contractService.itemIsCloseByItem(itemMain.getConItemId());
		// 更新剩余外协
		contractService.sumRemainAssCon(itemMain.getConItemId());
		
		List<Assistance> assistanceList = getAssistanceListByPayInfo(payInfo);
		for (Assistance assistance : assistanceList) {
			AssistanceTicket ticket = assistance.getTicket();
			double ticketHasPay = getHasRelationAssistanceAmount(ticket);
			ticket.setHasPayAmount(ticketHasPay);
			if(ticket.getIsTurnOver()!=null && ticket.getIsTurnOver()){
				ticket.setIsTurnOver(null);
			}else{
				ticket.setIsTurnOver(Boolean.TRUE);
			}
			if(ticket.getTurnOverDate() != null ){
				ticket.setTurnOverDate(null);
			}else{
				ticket.setTurnOverDate(new Date());
			}
			commonDao.update(ticket);
		}
	}
	
	/**
	 * 获取同一个外协合同下面已经支付的外协付款申请金额的和
	 * @param contract
	 * @return
	 */
	private Double getHasPayAmount(AssistanceContract contract){
		commonDao.flushSession();
		String amountHql = "select sum(payInfo.payNum) from AssistancePayInfo payInfo,AssistanceContract contract " +
		"where payInfo.assistanceId = contract.id and payInfo.payState = 4 and contract.id = ?";
		Number sumNumber = (Number) commonDao.uniqueResult(amountHql, contract.getId());
		return sumNumber == null ? 0.00 : sumNumber.doubleValue();
	}
	private List<Assistance> getAssistanceListByPayInfo(AssistancePayInfo payInfo){
		String hql = "select assistance from Assistance assistance,AssistancePayInfo payInfo where assistance.assistancePayId = payInfo.id " +
				"and payInfo.id = ? ";
		List<Assistance> list = commonDao.list(hql, payInfo.getId());
		return list;
		
		
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
	public void createSectionAndPayInfoRelation(List<AssistanceSection> sectionList,AssistancePayInfo pay){
		for (AssistanceSection assistanceSection : sectionList) {
			SectionAndPayInfoRelation relation = new SectionAndPayInfoRelation();
			relation.setAssistanceContractId(pay.getAssistanceId());
			relation.setImportFromFile(Boolean.TRUE);
			relation.setPayInfoId(pay.getId());
			relation.setSectionId(assistanceSection.getId());
			commonDao.save(relation);
		}
	}
	
	public void createAssistance(AssistanceTicket ticket,AssistancePayInfo pay,Double ticketMoney){
		Assistance assistance = new Assistance();
		assistance.setIs_active("1");
		assistance.setTicket(ticket);
		assistance.setAssistancePayId(pay.getId());
		assistance.setRelationPrepPayAmount(ticketMoney);
		assistance.setIsFromSys(Boolean.TRUE);
		commonDao.save(assistance);
	}
	
	public AssistanceTicket createAssistanceTicket(AssistanceContract contract ,Double ticketMoney,Date ticketTime,Long person){
		/*String hql = "select sum(ticket.money) from AssistanceTicket ticket,AssistanceContract contract " +
				"where ticket.contractId and ticket.type != '4' and contract.id = ?";
		Number hasTicketNumber = (Number)commonDao.uniqueResult(hql, contract);
		double hasTicketAmount =0.00;
		if(hasTicketNumber!=null){
			hasTicketAmount = hasTicketNumber.doubleValue();
		}
		if(hasTicketAmount == contract.getContractMoney()){
			return ;
		}*/
		AssistanceTicket ticket = new AssistanceTicket();
		ticket.setContractId(contract.getId());
		ticket.setCustomerId(contract.getSupplyId());
		ticket.setType("1");////目前默认的是服务业普通发票
		ticket.setNum(codeGenerateService.generateAssistanceTicketNo());
		ticket.setMoney(ticketMoney);
		ticket.setTicket_Time(ticketTime);
		ticket.setDoneMoney(0.00);
		ticket.setDoneTime(new Date());
		ticket.setPerson(person);
		ticket.setIs_related("0");
		ticket.setIs_active("1");
		ticket.setIsFromSys(Boolean.TRUE);
		commonDao.save(ticket);
		if(contract.getTicketMoney()==null){
			contract.setTicketMoney(ticketMoney);
		}else{
			contract.setTicketMoney(contract.getTicketMoney() + ticketMoney);
		}
		commonDao.update(contract);
		return ticket;
	}
	
	public AssistancePayInfo createAssistancePayInfo(AssistanceContract contract,AssistancePayInfo pay){
		pay.setAssistanceId(contract.getId());
		pay.setSupplyId(contract.getSupplyId());
		pay.setAssistanceName(contract.getAssistanceName());
		pay.setApplyInfoCode(codeGenerateService.assApplyInfoCode(contract.getId()));
		if(!pay.getApplyPay()){///非预付的生成任务号和接受号，预付的不管
			pay.setAssignmentId(codeGenerateService.generateAssistancePayInfoAssignmentId());
			pay.setReceivNum(codeGenerateService.generateAssistancePayInfoReceivNum());
		}
		pay.setPayState("4");///确认处理
		pay.setEmployeeId(contract.getEmployeeId());
		pay.setRealPayTime(pay.getApplyDate());
		pay.setIs_active("1");
		pay.setIsFromSys(Boolean.TRUE);
		commonDao.save(pay);
		return pay;
		
	}
	
	public void cancelAssistanceApplyPayInfo(Long[] payInfoIds){
		for(int i = 0 ; i < payInfoIds.length ; i ++ ){
			cancelAssistanceApplyPayInfo(payInfoIds[i]);
		}
	}
	public ProcessResult cancelAssistanceApplyPayInfo(Long payInfoId){
		AssistancePayInfo payInfo = (AssistancePayInfo)commonDao.load(AssistancePayInfo.class, payInfoId);
		ProcessResult result = cancelAssistanceAndUpdateTicket(payInfo);
		if(result.isSuccess()){
			if(!payInfo.getApplyPay()){
				List<AssistancePayInfo> prepList = assistancePayService.getPrepPayInfoListByRelationNotPrep(payInfo);
				cancelRelationBetweenPrepandNot(prepList);
			}
			cancelSectionAndPayRelation(payInfo);
			updateContractInfo(payInfo,"del");
			commonDao.delete(payInfo);
		}
		return result;
		
	}
	public void cancelSectionAndPayRelation(AssistancePayInfo payInfo){
		String sectionHql ="select relation from AssistanceSection section,SectionAndPayInfoRelation relation ," +
				" AssistancePayInfo payInfo  where relation.sectionId = section.id and relation.payInfoId = payInfo.id " +
				" and payInfo.id = ?";
		List<SectionAndPayInfoRelation> sectionList = commonDao.list(sectionHql, payInfo.getId());
		for (SectionAndPayInfoRelation relation : sectionList) {
			commonDao.delete(relation);
		}
	}
	public void deleteAssistanceAndUpdateTicket(AssistancePayInfo payInfo){
		commonDao.flushSession();
		AssistanceContract contract = (AssistanceContract)commonDao.load(AssistanceContract.class, payInfo.getAssistanceId());
		String assistanceHql = "select assistance from Assistance assistance,AssistancePayInfo payInfo where assistance.assistancePayId = payInfo.id and payInfo.id = ?";
		List<Assistance> assistanceList = commonDao.list(assistanceHql,payInfo.getId());
		for (Assistance assistance : assistanceList) {
			AssistanceTicket ticket = assistance.getTicket();
			if(ticket.getIsFromSys()){
				if(ticket.getType()!=null && ticket.getType()=="4"){
					double receiptAmount = 0.00;
					if(contract.getReceiptAmount()!=null){
						receiptAmount = contract.getReceiptAmount();
					}
					contract.setReceiptAmount(receiptAmount-ticket.getMoney());
				}else{
					double ticketMoney = 0.00;
					if(contract.getTicketMoney()!=null){
						ticketMoney = contract.getTicketMoney();
					}
					contract.setTicketMoney(ticketMoney-ticket.getMoney());
				}
				commonDao.delete(assistance);
				commonDao.delete(ticket);
			}else{
				double doneMoney = ticket.getDoneMoney() == null ? 0.00 : ticket.getDoneMoney().doubleValue();
				ticket.setDoneMoney(doneMoney+assistance.getRelationPrepPayAmount());
				double ticketHasPay = getHasRelationAssistanceAmount(ticket);
				ticket.setHasPayAmount(ticketHasPay - assistance.getRelationPrepPayAmount());
				if(ticket.getIsTurnOver()!=null && ticket.getIsTurnOver()){
					ticket.setIsTurnOver(null);
				}else{
					ticket.setIsTurnOver(Boolean.TRUE);
				}
				if(ticket.getTurnOverDate() != null ){
					ticket.setTurnOverDate(null);
				}else{
					ticket.setTurnOverDate(new Date());
				}
				commonDao.update(ticket);
				
				updateTicketRelation(assistance);
				commonDao.delete(assistance);
			}
			
		}
		commonDao.update(contract);
	}
	public void updateTicketRelation(Assistance assistance){
		commonDao.flushSession();
		String hql = "select ticket from Assistance assistance,AssistanceTicket ticket where assistance.ticket.id = ticket.id and assistance.id != ? and ticket.id = ?";
		List<AssistanceTicket> ticketList = commonDao.list(hql, assistance.getId(),assistance.getTicket().getId());
		if(ticketList == null || ticketList.size() == 0){
			AssistanceTicket ticket = assistance.getTicket();
			ticket.setIs_related("0");////标记未关联
			commonDao.update(ticket);
		}
	}
	/**
	 *  取消关联
	 * @param prepList
	 */
	public void cancelRelationBetweenPrepandNot(List<AssistancePayInfo> prepList){
		for (AssistancePayInfo assistancePayInfo : prepList) {
			assistancePayInfo.setRelationPrePayId(null);
			commonDao.update(assistancePayInfo);
		}
	}
	public ProcessResult cancelAssistanceAndUpdateTicket(AssistancePayInfo payInfo){
		ProcessResult result =new ProcessResult();
		if(payInfo.getRelationPrePayId()!=null){
			result.setSuccess(false);
			result.addErrorMessage("预付关联过非预付，不能取消付款申请，请先取消关联！");
			return result;
		}else{
			deleteAssistanceAndUpdateTicket(payInfo);
		}
		result.setSuccess(true);
		return result;
	}
	
	public ProcessResult cancelPrep(Long payInfoId){
		ProcessResult result =new ProcessResult();
		AssistancePayInfo payInfo = (AssistancePayInfo) commonDao.load(AssistancePayInfo.class, payInfoId); 
		String contractHql = "select contract from AssistanceContract contract,AssistancePayInfo payInfo " +
				"where payInfo.assistanceId = contract and payInfo.id = ?";
		AssistanceContract contract = (AssistanceContract) commonDao.uniqueResult(contractHql, payInfoId);
		
		/**
		 *step.1查询出该正常付款下面的预付款申请单的list，把该list里面的付款款的关联字段置为null；
		 *step.2查询出该正常付款下面的关联list，删除关联；
		 *step.3重新进行关联发票
		 */
		List<AssistancePayInfo> prepList = assistancePayService.getPrepPayInfoListByRelationNotPrep(payInfo);
		if(prepList!=null && prepList.size()>0){
			cancelRelationBetweenPrepandNot(prepList);
			String assistanceHql = "select assistance from Assistance assistance,AssistancePayInfo payInfo where assistance.assistancePayId = payInfo.id and payInfo.id = ?";
			List<Assistance> assistanceList = commonDao.list(assistanceHql,payInfo.getId());
			List<AssistanceTicket> ticketList = assistancePayService.changeTicketToAssistance(assistanceList);
			deleteAssistanceAndUpdateTicket(payInfo);
			List<AssistancePayInfo> nullPrepList = new ArrayList<AssistancePayInfo>();
			if(payInfo.getIsFromSys()!=null &&payInfo.getIsFromSys()){
				ticketList.clear();
				AssistanceTicket ticket = createAssistanceTicket(contract,payInfo.getPayNum(), payInfo.getApplyDate(), payInfo.getEmployeeId());
				ticketList.add(ticket);
			}
			assistancePayService.createRelationBetweenPayInfoandTicket(nullPrepList,ticketList,payInfo);
		}
		result.setSuccess(Boolean.TRUE);
		result.addSuccessMessage("外协付款申请取消关联成功！");
		return result;
	}
	public IYXCommonDao getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(IYXCommonDao commonDao) {
		this.commonDao = commonDao;
	}
	public ICodeGenerateService getCodeGenerateService() {
		return codeGenerateService;
	}

	public void setCodeGenerateService(ICodeGenerateService codeGenerateService) {
		this.codeGenerateService = codeGenerateService;
	}

	public IAssistancePayService getAssistancePayService() {
		return assistancePayService;
	}

	public void setAssistancePayService(IAssistancePayService assistancePayService) {
		this.assistancePayService = assistancePayService;
	}

	public IContractService getContractService() {
		return contractService;
	}

	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}

}
