<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<html>
<head>
<title>重点工程项目管理搜索</title>
</head>
<script type="text/javascript">
	
</script>
<body leftmargin="0">
<s:form action="importantProject" target="rightFrame" theme="simple">
<s:hidden name="resetCondition" value="true"/>
<s:hidden name="method" value="goQueryInfo"/>
<table width="100%"  class="bg_table02" >
    <tr>
       <td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
    </tr>
       	 
     <tr class="bg_table02">
		 <td class="bg_table02" align="right">客户：</td>
		 <td class="bg_table02"  align="left" >
		<input type="text" id="clientName" style="width:132px;height:16px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
		<s:select name="clientSelectId" list="yxClientCodeList" emptyOption="true"  id="clientSelect"
			cssStyle="margin-left:-142px;width:138px;" listKey="cli.id" listValue="cli.name" onchange="clientChange(this)"></s:select>
		</span>
		<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=650');">
		<s:hidden name="customerId" id="clientId"></s:hidden>
		</td>
	  </tr>
	 <tr class="bg_table02">
		   <td align="right" nowrap class="bg_table02"><div align="right">工程编号：</div></td>
		 	<td   nowrap><div align="left">
		 		<s:textfield name="projectNum"></s:textfield>
	       </div></td>
      </tr>
	 <tr class="bg_table02">
	       <td align="right" nowrap class="bg_table02"><div align="right">工程名称：</div></td>
	       <td  nowrap><div align="left">
				<s:textfield name="projectName"></s:textfield>
	        </div></td>
     </tr>   	 
    <tr class="bg_table02">
		<td colspan="2" nowrap class="bg_table04"><div align="center">
			<input type="submit" value=" 查  询 "  class="button02"/>
		</div></td>
  </tr>
</table>
</s:form>		
<script type="text/javascript">
function selectedClient(clientObj){
	var selectValue = clientObj.clientId;
	var selectTest = clientObj.clientName;
	document.getElementById("clientName").value = selectTest;
	document.getElementById("clientId").value = selectValue;
	addOptionX("clientSelect", selectValue , selectTest , 1);
}
function clientChange(selObj){
	document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
	document.getElementById("clientId").value = selObj.value;
}
</script>
</body>
</html>