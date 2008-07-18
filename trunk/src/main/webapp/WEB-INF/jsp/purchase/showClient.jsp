<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<SCRIPT language=JavaScript src="./js/public.js"></SCRIPT>

<script language="javascript">
	
	
	function checkString(){
	var names=document.getElementsByName("name");
    var ids=document.getElementsByName("ids");
    var name;
    var id;
    for(var i=0;i<names.length;i++){
        if(ids[i].checked){
             id=ids[i].value;
             name=names[i].value;
             	window.opener.document.getElementById("purchase_cName1").value=name;
             	window.opener.document.getElementById("purchase_cNameId1").value=id;
       }
    }
  
   this.window.close();
   
}
</script>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="">
<table width="98%" border="0" cellspacing="1" cellpadding="1" align="center">
				<tr>
					<td height="3" align="left">当前页面：选择客户</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1"><img
						src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
				</tr>
			</table>
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 width="100%">
				<tr class="bg_table02">
					<td width="5%" align="right">请输入客户名：</td>
					<td width="20%" align="left"><input name="clientName" type="text"
						size="10"/ >&nbsp;<input class="button01" value="查  询" type="submit" onclick='javascript:document.forms(0).action='<s:url action="contractQuery"><s:param name="method">showClient</s:param></s:url>''>&nbsp;&nbsp;&nbsp;&nbsp; 
					</td>
				</tr>
			</table>
			</td>
		</tr>

		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td class="bg_table01" width="20%">客户名称</td>
					<td class="bg_table01">客户性质</td>
					<td class="bg_table01">行业类型</td>
					<td class="bg_table01">税号</td>
					<td class="bg_table01">帐号</td>
					
				</tr>

				<s:iterator value="info.result">
					<tr align="center">
						<td><input type="radio" name="ids"
							value="<s:property value="id"/>" onclick=""></td>
						
						<td><input type="text" name="name"
							value="<s:property value="name"/>" style="border:0px"></td>
					<td><s:property value="typemanageservice.getYXTypeManage(1001,clientNID).typeName"/></td>
					<td><s:property value="typemanageservice.getYXTypeManage(1002,businessID).typeName"/></td>
					<td><s:property value="taxNumber"/></td>
					<td><s:property value="account"/></td>
					</tr>
				</s:iterator>
			</table>
			</td>
		</tr>
		<tr>
		<td>
		   <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			</td>
		</tr>
		<tr class="bg_table01">
		<td align="center" colspan="8" >
		<input class="button01" type="button" value="添  加" onclick="checkString()"/>&nbsp;&nbsp;<input class="button01" type="button" value="关  闭" onclick="window.close()"></td>
	</tr>
	</table>
</s:form>
</body>
</html>

