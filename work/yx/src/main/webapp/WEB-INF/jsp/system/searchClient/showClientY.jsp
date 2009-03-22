<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>选择开票单位</title>
<SCRIPT language=JavaScript src="./js/public.js"></SCRIPT>

<script language="javascript">
	function checkString(){
		 var names=document.getElementsByName("name");
		 var id=document.getElementsByName("id");
	     var ids=document.getElementsByName("ids");
	     var name;
	     for(var i=0;i<names.length;i++){
	        if(ids[i].checked){
	             id=ids[i].value;
	             name=names[i].value;
	             if(opener.selectedClientY != null){
	             	opener.selectedClientY({clientNameY:name,clientIdY:id});
	             }
	             break;
	       }
	     }  
	    this.window.close();   
    }
</script>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="searchClientQuery" theme="simple">
<s:hidden name="method" value="getClientListY"></s:hidden>
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 width="100%">
				<tr class="bg_table02">
					<td width="10%" align="right">请输入开票单位：</td>
					<td width="20%" align="left"><s:textfield name="cname" size="10"></s:textfield>&nbsp;&nbsp;&nbsp;&nbsp; <input value="查询"
						type="submit" class="button01"/>
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
						<td><s:property value="id"/></td>

						<td><s:property value="name"/><input type="hidden" name="name" value="<s:property value="name"/>" style="border: 0px"></td>

					</tr>
				</s:iterator>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><s:if test="cname!=null"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></s:if></td>
				</tr>
			</TABLE>
			</td>
		</tr>
		<tr class="bg_table01">

			<td align="center" colspan="8">
				<input class="button01" type="button" value="添  加" onclick="checkString()" />
				<input class="button01" type="button" value="关  闭" onclick="window.close()" /> 
				</td>
		</tr>
	</table>
</s:form>
</body>
</html>
