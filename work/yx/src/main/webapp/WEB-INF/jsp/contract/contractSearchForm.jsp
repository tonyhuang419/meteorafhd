<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>草稿合同搜索</title>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>

<script type="text/javascript">

	function doSubmit(){
	if(!validate()){
	//<!--document.form1.action="contract/searchContractList.action";-->
	    document.form1.submit();
	 } 
	}
	function clientChange(selObj){
		document.getElementById('textname').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("name").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("textname").value = clientObj.clientName;
		document.getElementById("name").value = clientObj.clientId;
	}
	function cleanrCus(){
	   document.forms(0).textname.value="";
	   document.forms(0).name.value="";
	}
	
	function validate()
	{
		var ev = new Validator();
        with(searchContractList){ 
 		 ev.test("dateYX","开票起始日期格式不正确",$("minConSignDate").value); 
 		 ev.test("dateYX","开票结束日期格式不正确",$("maxConSignDate").value); 
		   }
		   ev.writeErrors(errorsFrame, "errorsFrame");
		if (ev.size() > 0) {			
			return true;
		}
	}
</script>
<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
</head>
<body leftmargin="0">
	<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:form target="content" name="form1" action="searchContractList" theme="simple">
<table width="100%" class="bg_table02">
<tr>
      <td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
</tr>
  <tr>
    <td width="30%"><div align="right">客户名称：</div></td>
    <td width="70%">
    <div align="left">
        <input name="textname" type="text" value="" id="textname" style="width:130px;height:16px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
	  		<s:select name="maininfo.conCustomer" emptyOption="true" list="yXClientCodes" cssStyle="margin-left:-142px;width:138px;" listKey="id" listValue="name" onchange="clientChange(this)"></s:select>
	    </span>
	 
	    <%--<s:hidden name="name" id="name" value=""></s:hidden>
	    --%><input type="hidden" name="name">
	    <input type="button" value="…"	onclick="javascript:window.open('/yx/searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
					  
			<%--     
        <select name="name">
         
            <s:iterator value="yXClientCodes" id="yXClientCode">
            <option value="<s:property value="#yXClientCode.id"/>"><s:property value="#yXClientCode.name"/></option>
            </s:iterator>
           
        </select>
        --%>
      </div>
      
      </td>
  </tr>
  <tr>
    <td><div align="right">合同性质：</div></td>
    <td><div align="left">
        <select name="conType">
          <option value="">全部</option>       
          <s:iterator value="contractTypeList" id="typeManager">
           <option value="<s:property value="#typeManager.typeSmall"/>"> <s:property value="#typeManager.typeName"/> </option>
          </s:iterator>
        </select>
      </div></td>
  </tr>
  <tr>
    <td><div align="right">签订日期：</div></td>
    <td><div align="left">从：
        <input id="minConSignDate" style="WIDTH: 90px" maxlength=10 name="minConSignDate"  id="minConSignDate" value="" >
        <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('minConSignDate')" align="absMiddle" alt=""  /> <br>
        至：
        <input id=maxConSignDate style="WIDTH: 90px" maxlength=10 name="maxConSignDate"  id="maxConSignDate" value="" >
         <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('maxConSignDate')" align="absMiddle" alt=""  /></div></td>
  </tr>
  <tr>
    <td><div align="right">合同状态：</div></td>
    <td><div align="left">
        <select name="conState">
          <option value="">全部</option>
          <option value="0">草稿状态</option>
          <option value="1">待确认</option>
          <option value="2">确认退回</option>
          <option value="3">预合同</option>
        </select>
      </div></td>
  </tr>
  <tr class="bg_table04">
    <td colspan="2">
      <div align="center">
        <input type="button" name="button2" id="button2" value="查询" class="button01"  onClick="doSubmit();"/>
        </div></td>
  </tr>
</table>
</s:form>
</body>
</html>
