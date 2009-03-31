package com.waterking.action.demo;

import com.waterking.action.BaseAction;


//@ParentPackage(value = "jasperreports-default")
//@Results( {
//	@Result(name = "success", type=JasperReportsResult.class , value = "", 
//			params={"location","/WEB-INF/jasperReport/HelloWorld.jasper","dataSource","dataSource"})
//})
public class JasperReportAction extends BaseAction{
	private static final long serialVersionUID = 3629590860713480238L;

	String dataSource="";
	
	public String jr(){
		return "success";
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	
	
}
