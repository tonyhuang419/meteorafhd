package com.baoz.yx.utils;

public class DepartmentUtils {
	/**
	 * 组长的负责人后缀
	 */
	private final static int LEADER_POSITION_MAX_LENGTH = 4;
	/**
	 * 每个职位在code中占的长度
	 */
	private final static int PER_POSITION_LENGTH = 2;
	/**
	 * 部门经理的代码长度
	 */
	private final static int DEPARTMENT_POSITION_LENGTH = 2;
	/**
	 * 获得部门代码
	 * @param positionCode
	 * @return
	 * @deprecated 使用getDepartmentId(String positionCode),因为有商务组和部门代码相同
	 */
	public static String getDepartmentCode(String positionCode) {
		if(positionCode.length() > PER_POSITION_LENGTH){
			return positionCode.substring(0,positionCode.length()-PER_POSITION_LENGTH);
		}else{
			return positionCode;
		}
	}
	/**
	 * 获得部门的id
	 * @param positionCode
	 * @return
	 */
	public static Long getDepartmentId(String positionCode){
		//制造业营销部
		return -1L;
	}
	/**
	 * 判断是不是组长或经理
	 * @param positionCode
	 * @return
	 */
	public static boolean isTeamLeader(String positionCode){
		return positionCode.length() <= LEADER_POSITION_MAX_LENGTH;
	}
	
	/**
	 * 判断是不是部门经理
	 * @param positionCode
	 * @return
	 */
	public static boolean isDepartmentLeader(String positionCode){
		return positionCode.length() == DEPARTMENT_POSITION_LENGTH;
	}	
}
