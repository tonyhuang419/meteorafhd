<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/yx/commons/styles/style.css" rel="stylesheet"
	type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
	<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
	<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
	<script src="/yx/commons/scripts/public.js" type="text/javascript"></script>
<title>修改项目信息</title>
<script language="javascript" for="document" event="onkeydown">

<!--
if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!=''){
	event.keyCode=9;
}
-->
</script>
<script type="text/javascript">
function tijiao()
{
	if(!validate()){
		document.contract.submit();
	}
}
function validate()
{
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
function setValues(obj)
{
	document.contract.departMentPerson.value=obj.options[obj.selectedIndex].text;
}
function cal(va)
{
	var ev2=new Validator();
	var totalMoney=parseFloatNumber(document.forms(0).totalPartMoney.value);
	var opMoney=parseFloatNumber(document.contract.elements("mainInfoPartFeeMoney").value);
	if(va!=null&&va.length>0){
		var pre=parseInt(va);
		if(pre>100){
			ev2.addError("百分比不能大于100！");
		}	
		if(ev2.size()>0)
		{
			ev2.writeErrors(errorsFrame,"errorsFrame");
			return;
		}
		var resultMoney=totalMoney*pre/100;
		if(resultMoney>opMoney){
		document.contract.elements("itemMoney").value=number_format(opMoney);
		}else{
		document.contract.elements("itemMoney").value=number_format(resultMoney);
		}
	}
}
</script>
</head>
<body class="bg_table02">
	<iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe>
<s:form action="contract" theme="simple">
	<s:hidden name="method" value="updateEventInfo"></s:hidden>
	<s:hidden name="mainid"></s:hidden>
	<s:hidden name="tag"></s:hidden>
	<s:hidden name="clietId"></s:hidden>
	<s:hidden name="itemInfoId"></s:hidden>
	<s:hidden name="totalPartMoney"></s:hidden>
	<table align="center"  width="100%" border="1" bordercolor="#808080" style=" border-collapse: collapse;">
		<tr class="bg_table02">
			<td width="100">工程部门:</td>
			<td><s:property value="departMentName"/>
		</td>
		</tr>
		<tr class="bg_table02">
		<td>
			负责人:
		</td>
		<td class="bg_table02"  align="left" >		
			<input type="text" name="departMentPerson" value="<s:property value="departMentPerson"/>" onkeyup="departMatchSelect.showtips();" onkeydown="departMatchSelect.enterTips();"
					style="width: 90px; height: 21px; font-size: 10pt;"/><span
					style="width: 18px; border: 0px solid red;"><s:select name="chargeManCode"
					cssStyle="margin-left: -90px; width: 108px;" headerKey="" headerValue="    " list="chargeManList"listKey="id" listValue="name" onchange="setValues(this);"></s:select></span>
			</td>
		</tr>
		<tr class="bg_table02">
		<td>
		百分比:
		</td>
		<td>
			<input name="prencent" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" size="3" maxlength="3" onblur="cal(this.value)"/>%
		</td>
		</tr>
		<tr class="bg_table02">
		<td>
		项目金额(含税):
		</td>
		<td>
			<s:hidden name="mainInfoPartFeeMoney"></s:hidden>
			<s:textfield name="itemMoney" maxlength="15" onblur="formatInputNumber(this)"/>
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
<script type="text/javascript">
 var departMatchSelect = new OptionMatchInput($('departMentPerson'),$('chargeManCode'),new Array());
 departMatchSelect.getOptionFromSelect($('chargeManCode'),1)
</script>
</html>