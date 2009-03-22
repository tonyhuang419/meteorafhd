package com.baoz.yx.vo;

import com.baoz.yx.entity.AssistanceTicket;

public class AssistanceTicketAndPrepRelation {

	private AssistanceTicket assistanceTicket;
	
	private Double SelfPayAmount;
	
	private Double PrepPayAmount;

	public AssistanceTicket getAssistanceTicket() {
		return assistanceTicket;
	}

	public void setAssistanceTicket(AssistanceTicket assistanceTicket) {
		this.assistanceTicket = assistanceTicket;
	}

	public Double getSelfPayAmount() {
		return SelfPayAmount;
	}

	public void setSelfPayAmount(Double selfPayAmount) {
		SelfPayAmount = selfPayAmount;
	}

	public Double getPrepPayAmount() {
		return PrepPayAmount;
	}

	public void setPrepPayAmount(Double prepPayAmount) {
		PrepPayAmount = prepPayAmount;
	}
	
	
}
