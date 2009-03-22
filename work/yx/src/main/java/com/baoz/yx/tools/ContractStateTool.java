package com.baoz.yx.tools;


final public class ContractStateTool {
	//判定合同状态
	static public String getContractStateSnToName(int i){
		switch(i){
		case 0:
			return "草稿状态";
		case 1:
			return "合同待确认";
		case 2:
			return "确认退回";
		case 3:
			return "预合同";
		case 4:
			return "正式合同";
		case 5:
			return "变更保存";
		case 6:
			return "变更待确认";	
		case 7:
			return "变更退回";			
		case 8:
			return "建议关闭";
		case 9:
			return "项目成本确认";
		case 10:
			return "合同已关闭";
		default:
			return "合同状态值异常";
		}
	}


	static public int getContractStateNameToSn(String state){
		if(state.equals("草稿状态")){
			return 0;
		}
		else if(state.equals("合同待确认")){
			return 1;
		}
		else if(state.equals("确认退回")){
			return 2;
		}
		else if(state.equals("预合同")){
			return 3;
		}
		else if(state.equals("正式合同")){
			return 4;
		}
		else if(state.equals("变更保存")){
			return 5;
		}
		else if(state.equals("变更待确认")){
			return 6;
		}
		else if(state.equals("变更退回")){
			return 7;
		}
		else if(state.equals("建议关闭")){
			return 8;
		}
		else if(state.equals("项目成本确认")){
			return 9;
		}
		else if(state.equals("已关闭")){
			return 10;
		}
		else{
			return -1;  //全部
		}
	}

	//判定合同开票状态
	static public String getContractBillState(int i){
		switch(i){
		case 0:
			return "未开票";
		case 1:
			return "存在收据";
		case 2:
			return "部分未开票";
		case 3:
			return "尾款剩余";
		case 4:
			return "全额开票";
		default:
			return "合同开票状态值异常";
		}
	}

	//判定合同收款状态
	static public String getContractReceState(int i){
		switch(i){
		case 0:
			return "未到款";
		case 1:
			return "部分到款";
		case 2:
			return "尾款剩余";
		case 3:
			return "全额到款";
		default:
			return "合同收款状态值异常";
		}
	}
}

