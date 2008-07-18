<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/yx/commons/styles/style.css" rel="stylesheet"
	type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
	<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
	<script src="/yx/commons/scripts/public.js" type="text/javascript"></script>
<title>修改项目信息</title>
<script type="text/javascript">
function tijiao()
{
	if(!validate()){
		document.contract.submit();
	}
}
function validate()
{
alert(parseFloatNumber(document.contract.elements("itemMoney").value));
	 var ev2=new Validator();
       with(contract){ 
           ev2.test("+float","含税金额必须是数字",document.contract.elements("mainInfoPartFeeMoney").value);
       } 
       if (ev2.size() == 0) {
       		var sumTemp=parseFloatNumber(document.contract.elements("mainInfoPartFeeMoney").value);
			var opmo=parseFloatNumber(document.contract.elements("itemMoney").value);
       		if(opmo>sumTemp){
       			ev2.addError("项目金额不能大于可编辑金额"+sumTemp);
       		}
	   }
	  	ev2.writeErrors(errorsFrame, "errorsFrame");
	  	if(ev2.size()>0)
	  	{
	  		return true;
	  	}
		 return false;
}
</script>
</head>
<body class="bg_table02">
	
<s:form action="contract" theme="simple">
	<s:hidden name="method" value="updateEventInfo"></s:hidden>
	<s:hidden name="mainid"></s:hidden>
	<s:hidden name="tag"></s:hidden>
	<s:hidden name="clietId"></s:hidden>
	<s:hidden name="itemInfoId"></s:hidden>
	<table align="center"  width="100%" border="1" cellpadding="5" cellspacing="0">
	<tr class="bg_table02">
	<td colspan="2">
	<iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe>
	</td>
	</tr>
		<tr class="bg_table02">
			<td width="100">工程部门：</td>
			<td><s:property value="departMentName"/>
		</td>
		</tr>
		<tr class="bg_table02">
		<td>
			负责人：
		</td>
		<td>
		<s:property value="departMentPerson"/>
		</td>
		</tr>
		<tr class="bg_table02">
		<td>
		项目金额(含税):
		</td>
		<td>
			<s:hidden name="mainInfoPartFeeMoney"></s:hidden>
			<s:textfield name="itemMoney" size="12"/>
		</td>
		</tr>
		<tr class="bg_table03">
			<td align="center" colspan="2">
			<input type="button" value="修改" onclick="tijiao();" class="button01">
				<input type="button" value="取消" class="button01" onclick="window.close();">
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>