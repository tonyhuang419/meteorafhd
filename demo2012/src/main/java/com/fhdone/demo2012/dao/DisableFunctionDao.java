package com.fhdone.demo2012.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.fhdone.demo2012.entity.DisableFunction;
import com.fhdone.demo2012.entity.UserLog;

public interface DisableFunctionDao {

	String COUNT_USER_LOG = "select count(*) from  nasa2.Z_DISABLE_FUNCTION";
	
	String GET_USER_LOG = "select  ul.ACTION_NAME , ul.USER_CD ,ul.OPERATION_TIME ,ul.PARAMETER_INFO" +
			" from nasa2.Z_USER_LOG ul " +
			" where exists ( select 1 from nasa2.Z_DISABLE_FUNCTION df " +
			" where ul.COMPANY_CD = df.COMPANY_CD and ul.ACTION_NAME = df.FUNCTION_NAME  )" +
			" order by ul.OPERATION_TIME desc";
	
	String GROUP_DISABLE_FUNCTION_NAME = "select df.FUNCTION_NAME , df.COMPANY_CD FROM  " +
			" nasa2.Z_DISABLE_FUNCTION df GROUP BY df.FUNCTION_NAME, df.COMPANY_CD ";
	
	String  CASCADE_QUERY = " select df.ID as DF_ID, df.COMPANY_CD , df.FUNCTION_NAME , ul.* from nasa2.Z_USER_LOG ul " +
			" left join nasa2.Z_DISABLE_FUNCTION df on ul.COMPANY_CD = df.COMPANY_CD and ul.ACTION_NAME = df.FUNCTION_NAME" +
			" where " +
			" exists ( select 1 from nasa2.Z_DISABLE_FUNCTION df " +
			" where ul.COMPANY_CD = df.COMPANY_CD and ul.ACTION_NAME = df.FUNCTION_NAME  )";
	
	
	@Select(COUNT_USER_LOG)  
	Long countUserLog();  
	
	
	@Select(GET_USER_LOG) 
	@ResultMap(value = "demo2012.userLogResult")
	List<UserLog> getUserLog();  
	
	@Select(GROUP_DISABLE_FUNCTION_NAME)
	@ResultMap(value = "demo2012.disableFunctionResult")
	List<DisableFunction> groupDisableFunctionName();  
	
	@Select(CASCADE_QUERY)
	@ResultMap(value = "demo2012.disableFunctionResult")
	List<DisableFunction> cascadeQuery();  
	
}
