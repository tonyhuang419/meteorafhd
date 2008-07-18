package com.baoz.yx.tools.compare;

import junit.framework.TestCase;

import com.baoz.yx.entity.contract.ContractMainInfo;
import com.baoz.yx.tools.compare.beans.ClassInfo;
import com.baoz.yx.tools.compare.beans.CompareObject;
import com.baoz.yx.tools.compare.beans.CompareResult;
import com.baoz.yx.tools.compare.beans.PropertyCompareResult;

/**
 * 类BeanCompareUtilsTest.java的实现描述：TODO 类实现描述
 * 
 * @author xurong Jun 13, 2008 7:41:42 PM
 */
public class BeanCompareUtilsTest extends TestCase {
	public void testCompareObject() {
		ContractMainInfo src = new ContractMainInfo();
		src.setConMainInfoSid(1L);
		ContractMainInfo target = new ContractMainInfo();
		target.setConMainInfoSid(2L);
		CompareResult result = BeanCompareUtils
				.compareObject(new CompareObject(ContractMainInfo.class, src),
						new CompareObject(ClassInfoRegister
								.autoCreateAndRegister(ContractMainInfo.class),
								target));
		PropertyCompareResult[] propResults = result
				.getPropertyCompareResults();
		printResult(propResults);
	}

	public void testCompareObjectWithDesc() {
		ContractMainInfo src = new ContractMainInfo();
		src.setConMainInfoSid(1L);
		ContractMainInfo target = new ContractMainInfo();
		target.setConMainInfoSid(2L);
		ClassInfo classInfo = new ClassInfo(ContractMainInfo.class);
		classInfo.addPropertyInfo("conMainInfoSid", Long.class, "合同表主键");
		classInfo.addPropertyInfo("conId", String.class, "正式合同号");
		classInfo.addPropertyInfo("conName", String.class, "合同名称");
		// 显示描述
		CompareResult result = BeanCompareUtils.compareObject(
				new CompareObject(classInfo, src), new CompareObject(
						ClassInfoRegister
								.autoCreateAndRegister(ContractMainInfo.class),
						target));
		PropertyCompareResult[] propResults = result
				.getPropertyCompareResults();
		printResult(propResults);
		// 包含属性
		assertTrue(1 == BeanCompareUtils.compareObjectIncludeProperties(
				new CompareObject(classInfo, src),
				new CompareObject(ContractMainInfo.class, target),
				new String[] { "conMainInfoSid" }).getPropertyCompareResults().length);
		// 排除属性
		assertTrue(2 == BeanCompareUtils.compareObjectExcludeProperties(
				new CompareObject(classInfo, src),
				new CompareObject(ContractMainInfo.class, target),
				new String[] { "conMainInfoSid" }).getPropertyCompareResults().length);
	}
	/**
	 * 打印结果
	 * @param propResults
	 */
	private void printResult(PropertyCompareResult[] propResults) {
		System.out.println("======================================================");
		for (PropertyCompareResult propertyCompareResult : propResults) {
			System.out.println("property:"
					+ propertyCompareResult.getSourcePropertyObject()
							.getPropertyInfo().getPropertyName()
					+ " ,desc:"
					+ propertyCompareResult.getSourcePropertyObject()
							.getPropertyInfo().getShortDesc()
					+ " ,result:"
					+ propertyCompareResult.getResult()
					+ " ,isChanged:"
					+ propertyCompareResult.isChanged()
					+ ",src:"
					+ propertyCompareResult.getSourcePropertyObject()
							.getPropertyValue()
					+ ",dest:"
					+ propertyCompareResult.getTargetPropertyObject()
							.getPropertyValue());
			if (propertyCompareResult.getSourcePropertyObject()
					.getPropertyInfo().getPropertyName().equals(
							"conMainInfoSid")) {
				assertTrue(propertyCompareResult.isChanged());
			}
		}
		System.out.println("======================================================");
	}
}
