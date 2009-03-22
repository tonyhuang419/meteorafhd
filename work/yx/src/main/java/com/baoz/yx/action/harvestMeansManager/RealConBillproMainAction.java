package com.baoz.yx.action.harvestMeansManager;

import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.baoz.core.web.struts2.DispatchAction;
@Results({
	@Result(name="main",value="/WEB-INF/jsp/harvestMeansManager/realConBillproMain.jsp" ),
	@Result(name="leftQuery",value="/WEB-INF/jsp/harvestMeansManager/realConBillproLeft.jsp" )
})
public class RealConBillproMainAction extends DispatchAction{

	@Override
	public String doDefault() throws Exception {
		// TODO Auto-generated method stub
		return "main";
	}
	public String leftQuery()throws Exception{
		return "leftQuery";
	}
	
	
}
