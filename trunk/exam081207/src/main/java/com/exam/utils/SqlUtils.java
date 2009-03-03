package com.exam.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class SqlUtils {
	/**
	 * 匹配select和from的正则表达式
	 */
	private static Pattern selectFromPattern = Pattern.compile("select|from");
	/**
	 * 根据select语句返回获得总数的语句，支持带子查询的
	 * select a,b,c from d where f = g order by h,i desc 返回 select count(*) from  d where f = g
	 * @param selectSql select 语句
	 * @return count语句
	 */
	public static String getCountSql(String selectSql){
		return "select count(*) "+ getFromSql(selectSql);
	}

	/**
	 * 根据select语句返回获from语句，支持带子查询的
	 * select a,b,c from d where f = g order by h,i desc 返回 from  d where f = g
	 * @param selectSql select 语句
	 * @return from 语句
	 */
	public static String getFromSql(String selectSql){
		String lowerSelectSql = selectSql.toLowerCase();
		// from的起始位置
		int fromIndex = 0;
		// 最后一个order by的起始位置
		int lastOrderIndex = -1;
		// select的个数
		int selectCount = 0;
		// 获得主语句from的位置
		Matcher m = selectFromPattern.matcher(lowerSelectSql);
		while (m.find()) {
			// 遇到select,select个数加一
			if("select".equals(m.group())){
				selectCount++;
			}
			// 遇到一个from,select个数减一
			if("from".equals(m.group())){
				selectCount--;
			}
			// 遇到主语句的from时，刚好select的个数是0
			if(selectCount == 0){
				fromIndex = m.start();
				break;
			}
		}
		// 最后一个order by 的起始位置
		lastOrderIndex = lowerSelectSql.lastIndexOf(" order ");
		// 最后一个)号的位置
		int lastRightBracketIndex = lowerSelectSql.lastIndexOf(")");
		// 如果没有order或者更order在子查询里，就取整条sql
		if(lastOrderIndex == -1 || lastOrderIndex < lastRightBracketIndex){
			lastOrderIndex = lowerSelectSql.length();
		}
		return selectSql.substring(fromIndex,lastOrderIndex);
	}

	/**
	 * 拼接querySql 和 orderSQL
	 */
	public static String combineSQL(String querySql , String orderSql){
		if(StringUtils.isBlank(orderSql)){
			return querySql;
		}
		Pattern p = Pattern.compile("order\\sby",Pattern.CASE_INSENSITIVE);
		Matcher mQuerySql = p.matcher(querySql);
		Matcher mOrderSql = p.matcher(orderSql);
		boolean queryHasOrderBy = mQuerySql.find();
		boolean orderHasOrderBy = mOrderSql.find();
		
		//orderSql为空 || orderSql不存在order by
		if( !orderHasOrderBy ){
			return querySql;
		}
		else{
			//query 不存在order by
			if( !queryHasOrderBy){
				return querySql + orderSql;
			}
			else{
				//去除order语句的order by
				mOrderSql = p.matcher(orderSql);
				orderSql = mOrderSql.replaceAll("");
				return querySql + "," + orderSql;
			}
		}
	}

	public static void main(String[] args){
		System.out.println(SqlUtils.combineSQL("select * from a ", "   "));
		System.out.println(SqlUtils.combineSQL("select * from a ", " aa"));
		System.out.println(SqlUtils.combineSQL("select * from a order by aa ", ""));
		System.out.println(SqlUtils.combineSQL("select * from a ", "order by aa"));
		System.out.println(SqlUtils.combineSQL("select * from a order by aaa", "order by aa"));
	}
}
