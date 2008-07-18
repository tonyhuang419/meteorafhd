package com.baoz.yx.utils;

import java.util.HashMap;
import java.util.Map;

public class YxConstants {
	/**
	 * 值集大类名值对
	 */
	public final static Map<Long,String> typeBigMap = new HashMap<Long,String>();
	public static String	adminRoleCode	= "01";	// 职级
	/**
	 * 工程类合同
	 */
	public final static String	ENGINEERING_CONTRACT	= "1";
	/**
	 * 集成类合同
	 */
	public final static String	INTEGRATION_CONTRACT	= "2";
	/**
	 * 初始化
	 */
	static{
		// 初始化值集大类
		typeBigMap.put(1001L,"客户性质");
		typeBigMap.put(1002L,"行业类型");
		typeBigMap.put(1003L,"联系人性质");
		typeBigMap.put(1004L,"发票类型");
		typeBigMap.put(1005L,"地域代码");
		typeBigMap.put(1006L,"项目状况");
		typeBigMap.put(1007L,"客户项目类型");
		typeBigMap.put(1008L,"售前合同类型");
		typeBigMap.put(1009L,"售前跟踪状态");
		typeBigMap.put(1010L,"工程经济组成内容");
		typeBigMap.put(1011L,"工程经济阶段代码");
		typeBigMap.put(1012L,"项目组成内容");
		typeBigMap.put(1013L,"供应商类别");
		typeBigMap.put(1014L,"上传文件类型");
		typeBigMap.put(1015L,"货币代码");
		typeBigMap.put(1016L,"销售组织代码表");
		typeBigMap.put(1017L,"到款方式");
		typeBigMap.put(1018L,"工程责任部门");
		typeBigMap.put(1019L,"合同性质");
		typeBigMap.put(1020L,"合同类型");
		typeBigMap.put(1021L,"库存组织");
		typeBigMap.put(1022L,"开票性质");
		typeBigMap.put(1023L,"项目阶段");
		//////////////////////////////////////////////////////////////
	}
	
}
