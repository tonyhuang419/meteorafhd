<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>收款计划管理</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script type="text/javascript">
function save(){
	if(!validate()){
		document.realConBillpro.method.value="updateRealPredReceDate";
		document.realConBillpro.submit();
	}
}
function validate()
{
	var ev2=new Validator();
	
	ev2.test("dateYX","计划收款日期格式不正确！",$('realPredReceDate').value);
	if(!compareDateWithNowTime($('realPredReceDate').value)){
		ev2.addError("输入的计划收款日期小于当前日期！");
	}
	//////////////////判断日期不能超过90天
	//var flag = compareTwoDateWithStard($('realPredReceDate').value,$('billAndReceplan.realPredReceDate').value,90);
	//if(!flag){
		//ev2.addError("收款变更日期必须是90天以内！");
	//}
	ev2.test("notblank","请选择变更类型！",$('changeReason').value);
	ev2.test("notblank","请填写变更理由！",$('changeReason').value);
	ev2.writeErrors(errorsFrame,"errorsFrame");
	if(ev2.size()>0){
		return true;
	}
	return false;
}
</script>
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	scrolling="no"></iframe>
<s:form action="realConBillpro" theme="simple">
<s:hidden name="method"></s:hidden>
<s:hidden name="realConBillproSid"></s:hidden>
	<table align="center" border=1 cellpadding="1" cellspacing=1
		width="100%" id="family" bordercolor="#808080"
		style="border-collapse: collapse;">
		<tr class="bg_table02">
			<td nowrap="nowrap" align="right">原始计划收款日期：</td>
			<td>
			<s:property value="billAndReceplan.realPredReceDate" ></s:property>
			<!-- 变为hidden，不能去掉，后台用来记录原始收款日期 -->
			<s:hidden name="billAndReceplan.realPredReceDate"></s:hidden>
			
			</td>
		</tr>
	    <tr class="bg_table02">
			<td nowrap="nowrap" align="right"><FONT color="red"> *</FONT>修改计划收款日期：</td>
			<td><s:textfield name="realPredReceDate"></s:textfield><img
				src="/yx/commons/images/calendar.gif"
				onClick="javascript:ShowCalendar('realPredReceDate')" align=absMiddle alt="" /></td>
		</tr>
		<tr class="bg_table02">
			<td nowrap="nowrap" align="right"><FONT color="red"> *</FONT>变更类型：</td>
			<td>
			<s:select list="changeTypeList" name="receChangeType" listKey="typeSmall" listValue="typeName" headerKey="" headerValue="--请选择--"></s:select>
			</td>
		</tr>
		
		<tr class="bg_table02">
			<td nowrap="nowrap" align="right"><FONT color="red"> *</FONT>变更理由：</td>
			<td><s:textarea name="changeReason" cols="20" rows="5">
			</s:textarea></td>
		</tr>
		<tr class="bg_table03">
		<td colspan="2" align="center">
		<input type="button" value="保存" onclick="save();" class="button01"/>
		<input type="button" value="关闭" onclick="window.close();" class="button01"/>
		</td>
		</tr>
		<tr class="bg_table02">
			<td colspan="2"><hr width="100%"/></td>
		</tr>
		<tr class="bg_table02">
			<td align="center" colspan="2">变更履历
			</td>
		</tr>
		<tr class="bg_table02">
			<td nowrap="nowrap" width="100%" align="center" colspan="2">
				<table align="center" border=1 cellpadding="1" cellspacing=1
					width="100%" id="family" bordercolor="#808080"
					style="border-collapse: collapse;">
					<tr class="bg_table01" >
						<td align="right">
						编号
						</td>
						<td align="center">
						变更前收款时间
						</td>
						<td align="center">
						变更后收款时间
						</td>
						<td align="center">变更类型</td>
						
						<td align="center">变更时间</td>
						<td align="center">
						变更理由
						</td>
					</tr>
					<s:iterator value="changeHistoryList" id="history" status="hisIndex">
						<tr class="bg_table02">
							<td>
							<s:property value="#hisIndex.index+1"/>
							</td>
							<td>
							<s:property value="beforReceDate"/>
							</td>
							<td>
							<s:property value="afterReceDate"/>
							</td>
							<td>
							<s:property	value="yxTypeManageService.getYXTypeManage(1025L,receChangeType).typeName" />
							</td>
							<td><s:property value="changeTime" /></td>
							<s:if test="changeExp!=null&&changeExp.length()>10">
							<td title="<s:property value="changeExp"/>" nowrap="nowrap">
								<s:property value="changeExp.substring(0,10)"/>……
							</td>
							</s:if>
							<s:else><td nowrap="nowrap">
							<s:property value="changeExp"/></td>
							</s:else>
						</tr>
					</s:iterator>
				</table>
			</td>
		</tr>
	</table>
</s:form>
</body>
<script type="text/javascript">
	<s:if test="#updateSuccess=='true'">
		if(opener.refresh()!=null){
		refresh();
		}
	</s:if>
</script>
</html>