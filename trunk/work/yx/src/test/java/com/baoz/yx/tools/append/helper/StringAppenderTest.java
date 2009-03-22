package com.baoz.yx.tools.append.helper;

import com.baoz.yx.tools.append.dynamic.DynamicAppender;

import junit.framework.TestCase;

public class StringAppenderTest extends TestCase {
	public void testAppendHql(){
		String condition1 = "dd";
		String condition2 = "";
		StringAppender sql = new StringAppender();
		sql.append(" select e,g from Employee e,OrganizationTree g where e.is_active=1 and e.position = g.id ");
		sql.appendIfNotEmpty(" and e.username like '%adsad%'", condition1);
		sql.appendIfEmpty("and e.name like 'ddddd'", condition2).append(" order by e.id");
		System.out.println(sql.getString());
	}
	public void testDynamicAppendHql(){
		String condition1 = "dd";
		String condition2 = "";
		StringAppender sql = new StringAppender();
		sql.append(" select e,g from Employee e,OrganizationTree g ");
		//动态where
		DynamicAppender where = new DynamicAppender(" where ");
		// 条件
		StringAppender condition = new StringAppender();
		condition.appendIfNotEmpty(" and "," e.username like '%sss%'", condition1);
		condition.appendIfEmpty(" and "," e.name = 'sfdf'", condition2);
		// 条件加入where
		where.append(condition);
		// where加在条件后面
		sql.append(where).append(" order by e.id");
		// 自动去掉第一个条件的and
		System.out.println(sql.getString());
		assertTrue(sql.getString().indexOf("and")>0);
		///////////////////////////////////////////////////////////////
		String condition3 = null;
		String condition4 = "xx";
		StringAppender sql1 = new StringAppender();
		sql1.append(" select e,g from Employee e,OrganizationTree g ");
		//动态where
		DynamicAppender where1 = new DynamicAppender(" where ");
		StringAppender wcondition1 = new StringAppender();
		// 条件加入where
		where1.append(wcondition1);
		wcondition1.appendIfNotEmpty(" and "," e.username like '%"+condition3+"%'", condition3);
		wcondition1.appendIfEmpty(" and "," e.name like '%"+condition4+"%'", condition4);
		// where加在条件后面
		sql1.append(where1).append(" order by e.id");
		// 自动去掉 where
		System.out.println(sql1.getString());
		assertTrue(sql1.getString().indexOf("where")==-1);
	}
	
	public void testAppenderWithParam(){
		String condition1 = "dd";
		String condition2 = "";
		String param1 = "dd";
		String param2 = "";
		StringAppender sql = new StringAppender();
		sql.append(" select e,g from Employee e,OrganizationTree g ");
		//动态where
		DynamicAppender where = new DynamicAppender(" where ");
		// 条件
		StringAppender condition = new StringAppender();
		condition.appendIfNotEmpty(" and "," e.username like ? ", condition1 ,param1);
		condition.appendIfEmpty(" and "," e.name like ? ", condition2 ,param2);
		// 条件加入where
		where.append(condition);
		// where加在条件后面
		sql.append(where).append(" order by e.id");
		// 自动去掉第一个条件的and
		System.out.println(sql.getString());
		// 对应问号的参数
		assertTrue(sql.getParameters().size() == 2);
	}
}
