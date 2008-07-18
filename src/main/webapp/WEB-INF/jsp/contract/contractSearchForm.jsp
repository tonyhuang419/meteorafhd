<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>

<title>草稿合同搜索</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/yx/commons/scripts/time.js" ></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>

<script type="text/javascript">

	function doSubmit(){
	
	//<!--document.form1.action="contract/searchContractList.action";-->
	    document.form1.submit();
	  
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
</script>
<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
</head>
<body leftmargin="0">

<s:form target="content" name="form1" action="searchContractList" theme="simple">
<table width="100%" class="bg_table02">
  <tr>
    <td colspan="2" align="right" class="bg_table01"  height="3"></td>
  </tr>
  <tr>
    <td width="30%"><div align="right">客户名称：</div></td>
    <td width="70%">
    <div align="left">
        <input name="textname" type="text" value="" id="textname" style="width:130px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
	  		<s:select name="maininfo.conCustomer" emptyOption="true" list="yXClientCodes" cssStyle="margin-left:-120px;width:138px;" listKey="id" listValue="name" onchange="clientChange(this)"></s:select>
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
        <br>
      </div></td>
  </tr>
  <tr>
    <td><div align="right">签订日期：</div></td>
    <td><div align="left">从：
        <input id="minConSignDate" style="WIDTH: 90px" maxlength=10 name="minConSignDate" readonly="readonly" value="" onclick="javascript:ShowCalendar(this.id)">
        <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('minConSignDate')" align="absMiddle" alt=""  /> <br>
        至：
        <input id=maxConSignDate style="WIDTH: 90px" maxlength=10 name="maxConSignDate" readonly="readonly" value="" onclick="javascript:ShowCalendar(this.id)">
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
