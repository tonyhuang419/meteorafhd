<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>选择客户</title>
<SCRIPT language=JavaScript src="./js/public.js"></SCRIPT>

<script language="javascript">
	function checkString(){
		 var names=document.getElementsByName("name");
		 var id=document.getElementsByName("id");
	     var ids=document.getElementsByName("ids");
	     var name;
	     var fullNames=document.getElementsByName("fullName");
	     var fullName;
	     var k = 0;
	     for(var i=0;i<names.length;i++){
	        if(ids[i].checked){
	             id=ids[i].value;
	             name=names[i].value;
	             fullName=fullNames[i].value;    
	            k++;
	             break;
	       }
	     }
	     if(k==1){
	      if(opener.selectedClient != null){
	             	opener.selectedClient({clientName:name,clientId:id,clientFullName:fullName});
	       }
	       else
	       {
	             	opener.selectedClient({clientId:id});
	       }
	       this.window.close();  
	     }else{
	     	alert("请选择客户！");
	     }
	     
    }
</script>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body style="margin: 0px;">
<s:form action="searchClientQuery" theme="simple">
<s:hidden name="method" value="getClientList"/>
	<br>
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 width="100%">
				<tr class="bg_table02">
					<td width="10%" align="right">请输入客户：</td>
					<td width="20%" align="left"><s:textfield name="cname" size="10"></s:textfield>&nbsp;&nbsp;&nbsp;&nbsp; <input value="查询"
						type="submit" class="button01"	/>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td align="center">
			<table align="center" border="1" cellpadding="1" bordercolor="#808080" style=" border-collapse: collapse;" cellspacing=1
				width="100%">
				<tr align="center">
					
					<td nowrap="nowrap" class="bg_table01">选择</td>
					<td nowrap="nowrap" class="bg_table01" width="20%">客户简称</td>
					<td nowrap="nowrap" class="bg_table01" width="20%">客户全称</td>
					<td nowrap="nowrap" class="bg_table01">客户性质</td>
					<td nowrap="nowrap" class="bg_table01">行业类型</td>
					<td nowrap="nowrap" class="bg_table01">税号</td>
					<td nowrap="nowrap" class="bg_table01">帐号</td>
				</tr>
				<s:iterator value="info.result">
					<tr class="bg_table02">
					<td align="center"><input type="radio" name="ids"	value="<s:property value="id"/>" onclick=""></td>
					<td align="left" ><input type="hidden" name="name" value="<s:property value="name"/>" style="border: 0px">
							<s:property value="name"/></td>
					<td align="left" >
					<input type="hidden" name="fullName" value="<s:property value="fullName"/>"/>
					<s:property value="fullName"/></td>
					<td align="left"><s:property value="typemanageservice.getYXTypeManage(1001,clientNID).typeName"/></td>
					<td align="left"><s:property value="typemanageservice.getYXTypeManage(1002,businessID).typeName"/></td>
					<td align="left"><s:property value="taxNumber"/></td>
					<td align="left"><s:property value="account"/></td>


					</tr>
				</s:iterator>
			</table>
			</td>
		</tr>
		<tr>
			<td  class="bg_table02">
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table02">
						<baozi:pages value="info" beanName="info" formName="forms(0)" />
					</td>
				</tr>
			</TABLE>
			</td>
		</tr>
		<tr class="bg_table04" height="42px">
			<td align="center" colspan="8">
			<input class="button01" type="button" value="添  加" onclick="checkString()" />
			<input class="button01"	type="button" value="关  闭" onclick="window.close()" /></td>
		</tr>
	</table>
</s:form>
</body>
</html>
