<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>重点工程项目管理搜索</title>
</head>
<script type="text/javascript">
	
</script>
<body leftmargin="0">
<s:form action="importantProject" theme="simple">
<s:hidden name="method" value="goQueryInfoForCBS"/>
<table width="100%"  class="bg_table02" >
    <tr>
       <td colspan="4" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
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
		<td></td>
		<td></td>
	  </tr>
	 <tr class="bg_table02">
		   <td align="right" nowrap class="bg_table02"><div align="right">工程编号：</div></td>
		 	<td   nowrap><div align="left">
		 		<s:textfield name="projectNum"></s:textfield>
	       </div></td>

	       <td align="right" nowrap class="bg_table02"><div align="right">工程名称：</div></td>
	       <td  nowrap><div align="left">
				<s:textfield name="projectName"></s:textfield>
	        </div></td>
     </tr>   	 
    <tr class="bg_table02">
		<td colspan="4" nowrap class="bg_table04"><div align="center">
			<input type="submit" value=" 查  询 "  class="button02"/>
		</div></td>
  </tr>
</table>
<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
	<tr align="center" >
		<td class="bg_table01" nowrap>选择</td>
		<td class="bg_table01">客户名称</td>
		<td class="bg_table01">工程编号</td>
		<td class="bg_table01">工程名称</td>
		<td class="bg_table01">修改人</td>
		<td class="bg_table01">修改时间</td>
	</tr>
	<s:iterator value="info.result" id="imp">
	<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
		<td><input type="radio" name="impId" value="<s:property value="#imp[0].id"/>"/></td>
		<td align="left"><s:property value="#imp[1]" /></td>
		<td align="left"><s:property value="#imp[0].projectNum" /></td>
		<td align="left"><s:property value="#imp[0].projectName" />
		<input type="hidden" name="pn" value="<s:property value="#imp[0].projectName" />"></td>
		<td align="left"><s:property value="#imp[2]" /></td>
		<td><s:property value="#imp[0].updateBy" /></td>
	</tr>
	</s:iterator>
</table>
<table cellSpacing=1 cellPadding=2 width="100%" border=0>
	<tr valign="top">
			<td class="bg_table04"><baozi:pages value="info" beanName="info" formName="forms(0)" /></td>
	</tr>
</table>
<table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">	
	<tr class="bg_table03" height="42px">
		<td align="center">
		<input class="button01" type="button" value="添  加" onclick="link();">
		<input class="button01" type="button" value="关  闭" onclick="window.close()">
		</td>
	</tr>
</table>

</s:form>		
<script type="text/javascript">
function link(){
	var impIds=document.getElementsByName("impId");
	var impNames=document.getElementsByName("pn");

	var radios = $$("input[name=impId][checked]");
	if(radios.length == 0)
	{
		alert("请选择客户");
		return;
	}
	else{
		 for(var i=0;i<impIds.length;i++){
			 if(impIds[i].checked){
				 var pIdStr = impIds[i].value;
				 var pNameStr =  impNames[i].value;
				window.opener.fullImpName(pIdStr,pNameStr);
				window.close();
			 }
		}
	}
}
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
$('clientName').value = $('clientSelect').options[$('clientSelect').selectedIndex].text;
</script>
</body>
</html>