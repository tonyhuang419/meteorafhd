package com.baoz.yx.tools.compare;


/**
 * 类ValueCompareUtils.java的实现描述：值比较工具类
 * @author xurong Jun 13, 2008 7:14:53 PM
 */
public class ValueCompareUtils {
	/**
	 * 比较两个值,0相等，非0不等
	 * @param src
	 * @param target
	 * @return
	 */
	public static int compareValue(Object src ,Object target){
		if(src == target){
			return 0;
		}
		if(src != null){
			if(src instanceof Comparable && target != null){
				return ((Comparable)src).compareTo(target);
			}else{
				return src.equals(target)?0:1;
			}
		}else{
			return -1;
		}
	}
}
