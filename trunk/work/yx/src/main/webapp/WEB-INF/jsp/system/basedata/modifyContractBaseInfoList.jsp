<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>合同基础信息修改</title>

</head>
<body>
<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>

<s:form action="baseData" theme="simple">
	<s:hidden name="method" value="modifyContractBaseInfo"></s:hidden>
	<s:hidden name="resetCondition" value="false" id="resetCondition"/>
	<table align="center" width="100%">
	<tr>
	<td>
		<table align=left>
			<tr>
				<td>合同号：<s:textfield name="conId" size="11" />&nbsp;&nbsp;
				项目号：<s:textfield name="itemId" size="11" />&nbsp;&nbsp;
				<input type="button" onclick="search();" value="搜索" class="button01" /></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="modifyConInfo();" value="修改合同" class="button01" />
				<input type="button" onclick="toFormal();" value="开放合同" class="button01" />
				<input type="button" onclick="toClose();" value="关闭合同" class="button01" />
				</td>
			</tr>
		</table>
		</td>
		</tr>
		<tr>
			<td>
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill" bordercolor="#808080"
				style="border-collapse: collapse;">
				<s:if test="info != null">
					<s:set name="resultList" value="info.result"></s:set>
					<tr align="center"  class="bg_table01">
						<td nowrap>选择</td>
						<td nowrap>合同号</td>
						<td nowrap>合同名称</td>
						<td nowrap>销售员</td>
						<td nowrap>客户名称</td>
						<td nowrap>开票客户</td>
						<td nowrap>合同性质</td>
						<td nowrap>合同金额</td>
						<td nowrap>签订日期</td>
					</tr>
					<s:iterator value="resultList" id="info" status="status">
						<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
							onMouseOut="this.bgColor='#FFFFFF';">
							<td><input type="radio" name="conSid" value="<s:property value="#info[0].conMainInfoSid"/>" /></td>
							<td align="left"><s:property value="#info[0].conId" /></td>		
							<td align="left"><s:property value="#info[0].conName" /></td>
							<td align="left"><s:property value="#info[1]" /></td>	
							<td align="left"><s:property value="#info[2]" /></td>	
							<td align="left"><s:property value="#info[3]" /></td>	
							<td align="left"><s:property value="typeManageService.getYXTypeManage(1019,#info[0].conType).typeName" /></td>	
							<td align="center"><s:property value="#info[0].conTaxTamount" /></td>					
							<td align="center"><s:property value="#info[0].conSignDate" /></td>					
						</tr>
					</s:iterator>
					<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr valign="top">
							<td class="bg_table04">
							<baozi:pages value="info" beanName="info" formName="forms(0)" />
							</td>
						</tr>
					</TABLE>
				</s:if>
			</table>
			</td>
		</tr>
	</table>
</s:form>
<script type="text/javascript">
function search(){
	$("resetCondition").value="true";
	document.forms(0).submit();
}

function checkChoose(){
	var checkedNode=$$("input[name=conSid][checked]");
	if(checkedNode.length==null||checkedNode.length<=0){
		alert("请选择合同");
		return;
	}
	else{
		return checkedNode[0].value;
	}
}

function modifyConInfo()
{
	var conSid = checkChoose();
	if(conSid!=null){
		var  url="/yx/contract/modifyContractBaseInfo.action?conSid="+conSid;
		window.open(url);
	}
}

function toFormal()
{
	var conSid = checkChoose();
	if( conSid!=null && confirm("确定要转为正式合同吗？")){
		location.href("baseData.action?method=toFormal&conSid="+conSid);
	}
}

function toClose()
{
	var conSid = checkChoose();
	if(conSid!=null && confirm("确定要关闭合同吗？")){
		location.href("baseData.action?method=toClose&conSid="+conSid);
	}
}


function refreshSuper()	{
	document.forms(0).action="/yx/system/basedata/baseData.action";
	document.forms(0).submit();
	alert("合同修改成功");
}

<s:if test="alertInfo!=null">
	alert("<s:property value="alertInfo"/>");
</s:if>

</script>
</body>
</html>