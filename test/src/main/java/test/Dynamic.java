package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class Dynamic {
	public void test(Object... args) {
		System.out.println("args length: " + args.length);
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i].toString());
		}	
	}

	public void test2(Object... args) {
		System.out.println("args length: " +args.length);
		System.out.println(args[0].toString());
		System.out.println(args[1].toString());

	}

	public int findCharacter(String str,String character) {
		int count = 0;
		int from = 0;

		for (int s = 0; s > -1;) {
			if ("".equals(str)) {
//				System.out.println("查询字符不能为空!");
				break;
			} else {
				s = str.indexOf(character , from);
				if (s == -1) {
//					System.out.println("查找结束");
					break;
				}
				count++;
//				System.out.println("这是第" + count + "个,它的位置在第" + (s + 1)
//						+ "个字符处");
				from = from + s + 1;
			}

		}
		return count;
	}
	
	
	public void testHQL(String hqlStr,List<Object> list ) {
		int argsNum = this.findCharacter(hqlStr, "?");
		
		

	}
	
	public void test3(String hqlstr,Object... args) {
		int argsNum = args.length;
		System.out.println(argsNum);
		System.out.println(hqlstr);
		switch(argsNum){
		case 0:		
			break;
		case 1:
			System.out.println(args[0].toString());
			break;
		case 2:
			System.out.println(args[1].toString());
			break;
		case 3:
			System.out.println(args[2].toString());
			break;
		case 4:
			System.out.println(args[3].toString());
			break;
		case 5:
			System.out.println(args[4].toString());
			break;
		}

	}
	
	
	


	public static void main(String[] args) {
		Dynamic dn = new Dynamic();
		
		dn.test3("select *","2","3");
//		int num = dn.findCharacter("select * from exp where id= ? and name = ?", "?");
//		System.out.println(num);
		
//		dn.test("1","2");
//
//		List<Object> lo = new ArrayList<Object>();
//		lo.add("1");
//		lo.add("2");
//		dn.test(lo);
//
//		dn.test2("1","2");
//
//		String i = null;
//		System.out.println(StringUtils.isNotBlank(i));
//		System.out.println(StringUtils.isNotEmpty(i));
//		i = "";
//		System.out.println(StringUtils.isNotBlank(i));
//		System.out.println(StringUtils.isNotEmpty(i));
//
//		Long l = 0L;
//		try{
//			System.out.println(StringUtils.isNotBlank(l.toString()));
//			System.out.println(StringUtils.isNotEmpty(l.toString()));
//		}catch(Exception e){
//			System.out.println("exception has been catched");
//			e.printStackTrace();
//
//		}finally{
//			System.out.println("exception over");
//		}
//
//		Date d = new Date();
//		try{
//			System.out.println(StringUtils.isNotBlank(d.toString()));
//			System.out.println(StringUtils.isNotEmpty(d.toString()));
//		}catch(Exception e){
//			System.out.println("exception has been catched");
//			e.printStackTrace();
//
//		}finally{
//			System.out.println("exception over");
//		}
	}
}
