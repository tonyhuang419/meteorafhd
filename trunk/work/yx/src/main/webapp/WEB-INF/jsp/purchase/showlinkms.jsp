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
    for(var i=0;i<names.length;i++){
        if(ids[i].checked){
             id=ids[i].value;
             name=names[i].value;
             	window.opener.document.getElementById("linkName").value= name;
             	window.opener.document.getElementById("purchase_am_linkmanId").value=id;
       }
    }
  
   this.window.close();
   
}
</script>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="">
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=1 width="100%">
				<tr class="bg_table02">
					<td width="10%" align="right">项目名称：</td>
					<td width="20%" align="left"><input name="proName" type="text"
						size="10"/ >&nbsp;&nbsp;&nbsp;&nbsp; <input value="查询"
						type="submit"
						onclick="javascript:document.forms(0).action='<s:url action="projectReport"><s:param name="method">selectById</s:param></s:url>'" />
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
					<td class="bg_table01">联系人ID</td>
					<td class="bg_table01">联系人</td>
					
				</tr>

				<s:iterator value="info.result">
					<tr align="center">
						<td><input type="radio" name="ids"
							value="<s:property value="id"/>" onclick=""></td>
						<td><s:property value="id" /></td>
<!--						<td><s:property value="name" /></td>-->
				<td><input type="text" name="name"
							value="<s:property value="name"/>" style="border:0px"></td>
					
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
		<td align="center" colspan="8" ><input class="button01" type="button" value="关  闭" onclick="window.close()"/>
		<input class="button01" type="button" value="添  加" onclick="checkString()"/></td>
	</tr>
	</table>
</s:form>
</body>
</html>

