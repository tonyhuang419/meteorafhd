<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>

<title>开票收款计划变更</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css"
	rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet"
	type="text/css">
	<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
	<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
var whitespace=" \t\n\r";
function isEmpty(s){
var i;
if((s==null)||(s.length==0))
   return true;
for(i=0;i<s.length;i++){
    var c=s.charAt(i);
    if(whitespace.indexOf(c)==-1)
      return false;
 }
 return true;
}

function displayX(obj,s){
	if(obj.checked){	
 		document.getElementById(s).style.display="block";	
	}
	else{
		document.getElementById(s).style.display="none";
	}
}
	function changeBgen(thjs){
	 
		if(!validate()){
	 	 	document.myform.submit();
	 	 }
	}
	function validate()
	{
		var ev2=new Validator();
		
	 	ev2.test("notblank","开始日期为空！",$('afterChangeDate').value);
	 	ev2.test("notblank","收款日期为空！",$('afterReceDate').value);
	 	ev2.test("dateYX","开始日期输入格式不正确！",$('afterChangeDate').value);
	 	ev2.test("dateYX","收款日期输入格式不正确！",$('afterReceDate').value);
	 	
	 	var receFlag = compareTwoDateWithStard($('afterChangeDate').value,$('afterReceDate').value,90);
	 	if(!receFlag){
	 		ev2.addError("收款和开票的跨度必须在90天以内！");
	 	}
	 	ev2.test("notblank","请选择变更类型!",$('billChangeType').value);
	 	ev2.test("notblank","变更说明为空!",$('changeExp').value);
	 	ev2.writeErrors(errorsFrame, "errorsFrame");
	 	if(ev2.size()>0){
	 			return true;
	 	}
	 	return false;
	}
</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}

.STYLE1 {
	font-size: 16px;
	font-weight: bold;
}
-->
</style>
</head>
<body>
<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:form name="myform" method="post" action="realContractBillandRecePlan" theme="simple" >
<s:hidden name="realConBillproSid"></s:hidden>
<s:hidden name="method" value="saveRealHistory"></s:hidden>
<table border=1 cellpadding="0" cellspacing=0 width="100%"
	bordercolor="#808080" style="border-collapse: collapse;">
	<tr class="bg_table01">
	<td colspan="2" align="center"><strong>开票收款计划变更</strong></td>
	</tr>
	<tr height="29" class="bg_table02">
		<td align="right">
		原开票日期：
		</td>
		<td align="left" nowrap="nowrap">
			<s:property value="beforBillDate" />
			<input type="hidden" name="beforeBill" value="<s:property value="beforBillDate" />"/>
		</td>
	</tr>
	<tr height="29" class="bg_table02">
		<td align="right">
		原收款日期：
		</td>
		<td nowrap="nowrap" align="left">	
			<s:property value="beforReceDate" />
			<input type="hidden" name="beforeRece" value="<s:property value="beforReceDate" />"/>
		</td>
	</tr>
	<tr class="bg_table02">
		<td>
		<div align="right"><font color="red">*</font> 变更开票日期：</div>
		</td>
		<td height="29" nowrap="nowrap">
		<div align="left"><s:textfield
			id="dateOne"  
			maxlength="10" name="afterChangeDate" value=""/> <img
			src="/yx/commons/images/calendar.gif"
			onClick="javascript:ShowCalendar('dateOne')"
			align="absMiddle" alt="" /></div>
		</td>
	</tr>
	<tr class="bg_table02">
		<td>
		<div align="right"><font color="red">*</font> 变更收款日期：</div>
		</td>
		<td nowrap="nowrap">
		<div align="left"><s:textfield
			id="dateTwo" 
			maxlength="10" name="afterReceDate" value=""/> <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('dateTwo')" align="absMiddle" alt="" /></div>
		</td>
	</tr>
	<tr class="bg_table02">
		<td align="right"><font color="red">*</font>变更类型：</td>
		<td align="left"><select
			name="billChangeType">
			<option value="">--请选择--</option>
			<s:iterator value="changeTypeList" id="types">
				<option value="<s:property value="typeSmall"/>"><s:property
					value="typeName" /></option>
			</s:iterator>
		</select></td>
	</tr>
	<tr class="bg_table02">
		<td>
		<div align="right"><font color="red">*</font> 变更说明：</div>
		</td>
		<td>
		<div align="left"><s:textarea
			id="area"
			name="changeExp" cols="40" value=""></s:textarea>
		</div>
		</td>
		</tr>
	<tr class="bg_table02">
		<td colspan="2" align="center">
		<input type="button" name="button" id="button"
			value="确认保存" class="button01"
			onclick="changeBgen();">
			<input type="button" value="关闭" onclick="window.close();" class="button01"/>
		</td>
	</tr>
</table>
<table align="center" border=1 cellpadding="1" cellspacing=1
	width="100%" id="family" bordercolor="#808080"
	style="border-collapse: collapse;">
	<tr class="bg_table02">
		<td nowrap="nowrap" align="center" colspan="8">变更履历</td>
	</tr>
	</tr>
	<tr class="bg_table01">
		<td nowrap="nowrap"  align="right">编号</td>
		<td  align="center">变更前开票时间</td>
		<td  align="center">变更后开票时间</td>
		<td  align="center">变更类型</td>
		<td  align="center">变更前收款时间</td>
		<td  align="center">变更后收款时间</td>
		<td  align="center">变更操作时间</td>
		<td  align="left">变更理由</td>
	</tr>
	<s:iterator value="bilhistoryList" id="history" status="hisIndex">
		<tr class="bg_table02">
			<td nowrap="nowrap"><s:property value="#hisIndex.index+1" /></td>
			<td nowrap="nowrap" align="center"><s:property value="beforBillDate" /></td>
			<td nowrap="nowrap"><s:property value="afterChangeDate" /></td>
			<td ><s:property
				value="typeManageService.getYXTypeManage(1024L,billChangeType).typeName" />
			</td>
			<td nowrap="nowrap" align="center"><s:property value="beforReceDate" /></td>
			<td nowrap="nowrap" align="center"><s:property value="afterReceDate" /></td>
			<td nowrap="nowrap"><s:property value="changeTime" /></td>
			<td title="<s:property value="changeExp" />" ><s:if test="changeExp!=null&&changeExp.length()>20">
				<s:property value="changeExp.substring(0,10)" />……
			</s:if> <s:else>
				<s:property value="changeExp" />
			</s:else></td>
		</tr>
	</s:iterator>
	<tr class="bg_table02">
		<td colspan="8" align="center"><input type="button" value="关闭"
			class="button01" onclick="window.close();" /></td>
	</tr>
</table>
</s:form>

<s:if test="sign==1">
<script type="text/javascript">
	opener.refreshClient();
</script>
</s:if>

</body>
</html>
