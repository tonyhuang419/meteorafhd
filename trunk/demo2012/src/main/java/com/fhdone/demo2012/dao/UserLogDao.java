package com.fhdone.demo2012.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.fhdone.demo2012.entity.UserLog;

public interface  UserLogDao {

	String countUserLog = "select count(*) from  nasa2.Z_USER_LOG";
	String getUserLogById = "select * from nasa2.Z_USER_LOG where id=#{id}";
	String getUserLogltId = "select * from nasa2.Z_USER_LOG where id<#{id}";
	String getUserLogsByTwoId = "select * from nasa2.Z_USER_LOG where id between  #{id1} and #{id2} ";
	String getUserLogByMaxId = "select max(id) from nasa2.Z_USER_LOG ";

	@Select(countUserLog)  
	int countUserLog();  


	@Select(getUserLogById)
//	@Results(value = {
//			@Result(property="id"),
//			@Result(property="companyCd",column="COMPANY_CD")
//	})
	@ResultMap(value = "UserLogService.userLogResult")
	UserLog getUserLogById(@Param("id") Long logId); 


	@Select(getUserLogltId) 
	@ResultMap(value = "UserLogService.userLogResult")
	List<UserLog> getUserLogltId(@Param("id") Long logId); 

	@Select(getUserLogsByTwoId) 
	@ResultMap(value = "UserLogService.userLogResult")
	List<UserLog> getUserLogsByTwoId(Map<String,Long> ids); 
	
	@Select(getUserLogByMaxId) 
	Long getUserLogByMaxId(); 
	
}
