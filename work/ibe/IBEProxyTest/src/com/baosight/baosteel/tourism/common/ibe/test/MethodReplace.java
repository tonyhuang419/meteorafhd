package com.baosight.baosteel.tourism.common.ibe.test;

import java.lang.reflect.Method;

import org.springframework.beans.factory.support.MethodReplacer;

public class MethodReplace implements MethodReplacer {
	public Object reimplement(Object obj, Method method, Object[] args)
	throws Throwable {
		System.out.println(obj.toString());
		System.out.println(method.getName());
		if(args.length>0)
			System.out.println(args[0].getClass().getName());
		System.out.println("方法已经被替换!");
		return "方法已经被替换str";
	}

}