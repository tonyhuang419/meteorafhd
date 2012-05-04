package com.fhdone.demo2012.dao.demo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fhdone.demo2012.entity.UserLog;

public interface UserLogDao {

	String COUNT_USER_LOG = "select count(*) from  nasa2.Z_USER_LOG";
	String GET_USERLOG_BY_ID = "select * from nasa2.Z_USER_LOG where id=#{id}";
	String GET_USER_LOG_LT_ID = "select * from nasa2.Z_USER_LOG where id<#{id}";
	String GET_USER_LOGS_BY_TWOID = "select * from nasa2.Z_USER_LOG where id between  #{id1} and #{id2} ";
	String GET_USER_LOG_BY_MAXID = "select max(id) from nasa2.Z_USER_LOG ";
	String UPDATE_COMPANY_CD = "update t set t.COMPANY_CD = #{ccUpdate}" +
			" from  nasa2.Z_USER_LOG t where t.COMPANY_CD = #{ccSelect}";
	
	@Select(COUNT_USER_LOG)  
	Long countUserLog();  


	@Select(GET_USERLOG_BY_ID)
//	@Results(value = {
//			@Result(property="id"),
//			@Result(property="companyCd",column="COMPANY_CD")
//	})
	@ResultMap(value = "com.fhdone.demo2012.dao.demo.UserLogDao.userLogResult")
	UserLog getUserLogById(@Param("id") Long logId); 


	@Select(GET_USER_LOG_LT_ID) 
	@ResultMap(value = "com.fhdone.demo2012.dao.demo.UserLogDao.userLogResult")
	List<UserLog> getUserLogltId(@Param("id") Long logId); 

	@Select(GET_USER_LOGS_BY_TWOID) 
	@ResultMap(value = "com.fhdone.demo2012.dao.demo.UserLogDao.userLogResult")
	List<UserLog> getUserLogsByTwoId(Map<String,Long> ids); 
	
	@Select(GET_USER_LOG_BY_MAXID) 
	Long getUserLogByMaxId(); 
	
	@Update(UPDATE_COMPANY_CD)
	int updateCompanyCD(Map<String,Long> ids);
	
	/**
	 * cache test
	 */
	List<UserLog> selectUserLogByIdFromCache(Map<String,Object> paras); 
	
	
}
