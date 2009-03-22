<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>退票</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function cancelApplyHongChong(){
		var checkedBoxes = $$("input[name=invoiceId][checked]");
		if(checkedBoxes.length == 0){
			alert("请选择一个提交申请")
		}
		else if(checkedBoxes.length == 1){
			document.hongChongQuery.method.value="cancelHongChongApply";
			document.hongChongQuery.submit();
		}
		else{
			alert("只能选择一个发票提交申请");
		}
	}
	function applyHongChong(){
		var checkedBoxes = $$("input[name=invoiceId][checked]");
		if(checkedBoxes.length == 0){
			alert("请选择一个提交申请")
		}
		else if(checkedBoxes.length == 1){
			//document.hongChongQuery.method.value="submitApply";
			//document.hongChongQuery.submit();
			document.hongChongQuery.method.value="showConfirmHongChong";
			window.open("hongChongQuery.action?isConfirm=1&"+$(hongChongQuery).toQueryString());
			//openWin("hongChongQuery.action?isConfirm=1&"+$(hongChongQuery).toQueryString(),800,600);
		}
		else{
			alert("只能选择一个发票提交申请");
		}
	}
</script>
</head>
<body>
<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<div align="left" style="color:#000 ">
  当前页面：开票管理 -> 退票管理
</div>
<s:iterator id="erMessage" value="processResult.errorMessages">
	<font color="red"><strong><s:property value="#erMessage"/></strong></font><br/>
</s:iterator>
<s:iterator id="suMessage" value="processResult.successMessages">
	<font color="green"><strong><s:property value="#suMessage"/></strong></font><br/>
</s:iterator>
<s:form action="hongChongQuery" theme="simple">
	<s:hidden name="method" value="showHongChongManager"></s:hidden>
	<s:hidden name="confirmHC" value="2"></s:hidden>
	<table align="center" width="100%">
	<tr>
	<td>
		<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
  		 <tr>
 			   <td align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="3"></td>
 		</tr>
		  <tr>
		    <td align="center" class="bg_table03" >
		       <input type="button" onclick="applyHongChong()" value="提交申请" class="button01" />
					<input type="button" onclick="cancelApplyHongChong()" value="删除申请" class="button01" />
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
					<tr align="center">
						<td nowrap="nowrap" class="bg_table01">选择</td>
						<td nowrap="nowrap" class="bg_table01">销售员</td>
						<td nowrap="nowrap" class="bg_table01">退票申请单编号</td>
						<td nowrap="nowrap" class="bg_table01">合同号</td>
						<td nowrap="nowrap" class="bg_table01">项目号</td>
						<td class="bg_table01">合同名称</td>
						<td class="bg_table01">开票客户名称</td>
						<td nowrap="nowrap" class="bg_table01">发票号</td>
						<td nowrap="nowrap" class="bg_table01">发票类型</td>
						<td nowrap="nowrap" class="bg_table01">开票金额</td>
						<td nowrap="nowrap" class="bg_table01">开票日期</td>
						<td nowrap="nowrap" class="bg_table01">状态</td>
					</tr>
					<s:iterator value="info.result" id="invoice" status="status">
						<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "
							onMouseOut="this.bgColor='#FFFFFF';">
							<td><input type="checkbox" name="invoiceId" value="<s:property value="#invoice[4].id"/>" 
								<s:if test="#invoice[0].hongChongState == 2">
									disabled="disabled"
								</s:if>
								<s:if test="#invoice[0].hongChongState == 3">
									disabled="disabled"
								</s:if>
							 /></td>
							<td align="left"><s:property value="#invoice[3].name" /></td>
							<td align="left"><s:property value="#invoice[0].hongChongApplyBillNum" /></td>
							<td align="left"><s:property value="#invoice[1].conId" /></td>
							<td align="left">
								<s:iterator id="s" value="itemNoList.get(#invoice[0].applyBillId)"  status="stat2"  >    
            						<s:property value="s"/><s:if test="!#stat2.last"><br/></s:if>
           						</s:iterator>
							</td>
							<td align="left"><s:property value="#invoice[1].conName" /></td>
							<td align="left"><s:property value="#invoice[2].name" /></td>
							<td align="left"><s:property value="#invoice[4].invoiceNo" /></td>
							<td align="left"><s:property
								value="typeManageService.getYXTypeManage(1004,#invoice[0].billType).typeName" /></td>
							<td align="right"><s:property
								value="#invoice[4].invoiceAmount" /></td>
							<td align="center"><s:property
								value="#invoice[4].invoiceDate" /></td>
							<td align="center">
								<s:if test="#invoice[0].hongChongState == 1">
									待提交
								</s:if>
								<s:if test="#invoice[0].hongChongState == 2">
									已退票
								</s:if>
								<s:if test="#invoice[0].hongChongState == 3">
									待确认
								</s:if>
								<s:if test="#invoice[0].hongChongState == 4">
									确认退回
								</s:if>
							</td>
						</tr>
					</s:iterator>
					<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr valign="top">
							<td class="bg_table04"><baozi:pages value="info"
								beanName="info" formName="forms(0)" /></td>
						</tr>
					</TABLE>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>