<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>取消关联</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
	<script type="text/javascript">
		function searchApply(){
			var conNo = document.systemOpAssistance.assistanceConNo.value;
			if(conNo!=null&&conNo.length>0){
				document.systemOpAssistance.submit();
			}
		}
		
		
		function checkboxChecked(){
			var checkNode = document.getElementsByName("payInfoId");
			var j =0 ;
			if(checkNode != null &&checkNode.length > 0){
				for(var i = 0 ; i < checkNode.length ; i ++ ){
					if(checkNode[i].checked){
						j++;
						break;
					}
				}
			}
			if(j == 0){
				return false;
			}else{
				return true;
			}
		}
		
		function cancelPrep(){
			var flag = window.confirm("您确定要取消该申请单下面所有的关联吗？");
			if(flag){
				if(checkboxChecked()){
					document.systemOpAssistance.method.value="cancelPrep";
					document.systemOpAssistance.submit();
				}else{
					alert("请选择您要操作的付款申请单！");
				}
			}
		}
	</script>
</head>
<body>
<s:if test="result.isSuccess()">
	<s:iterator value="result.successMessages" id="sucMsg">
		<font color="red"><strong><s:property value="#sucMsg"/></strong></font><br/>
	</s:iterator>
</s:if>
<s:form action="systemOpAssistance" theme="simple">
	<s:hidden name="method" value="searchCancelPayInfo"></s:hidden>
	<s:hidden name="assistanceContract.id"></s:hidden>
	<table align="center" width="100%">
	<tr>
		<td>
			<table>
				<tr>
					<td>
					外协合同号：
					</td>
					<td>
						<s:textfield name="assistanceConNo"></s:textfield>
					</td>
					<td><input type="button" value="搜索" class="button01" onclick="searchApply();"/></td>
					<td>
					<s:if test="cancelPaySign != null && cancelPaySign == 1">
					<input type="button" value="取消关联" class="button01" onclick="cancelPrep();"/>
					</s:if>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	<s:if test="cancelPaySign != null && cancelPaySign == 1">
	<tr>
		<td align="center">
			  <table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" id="checkInfo" bordercolor="#808080" style=" border-collapse: collapse;">
			 <tr align="center">
			      <td nowrap class="bg_table01">选择</td>
			      <td nowrap class="bg_table01">销售员</td>
			      <td nowrap class="bg_table01">外协合同号</td>
		          <td nowrap class="bg_table01">申请序号</td>
		          <td  nowrap class="bg_table01">外协合同名称</td>
		          <td nowrap class="bg_table01">外协供应商</td>
		          <td nowrap class="bg_table01">申请日期</td>
		          <td nowrap class="bg_table01">申请金额</td>
		          <td nowrap class="bg_table01">是否预付</td>
		          <td  nowrap class="bg_table01">申请单状态</td>
		   </tr>
			<s:iterator value="info.result" id="apply">
			    <tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
		          <td>
		          <input type="radio" name="payInfoId" <s:if test="#apply[0].payState != 4"> disabled="true" </s:if>  value="<s:property value="#apply[0].id"/>"/>
		          </td>
		          <td ><s:property value="#apply[4]"/></td>
		          <td align="left" >
		          	<s:property value="#apply[1]"/></td>
		          <td align="left" >
		          	<s:property value="#apply[0].applyInfoCode"/></td>
		          <td align="left" >
		          	<s:if test="#apply[2] != null">
		          		<s:property value="#apply[2]"/>
		          	</s:if>
		          	<s:else>
		          		<s:property value="#apply[0].assistanceName"/>
		          	</s:else>
		          </td>
		          <td align="left" ><s:property value="#apply[3]"/></td>
		          <td ><s:property value="#apply[0].applyDate"/></td>
		          <td align="right" ><s:property value="#apply[0].payNum"/></td>
		          <td align="left" >
		          <s:if test="#apply[0].applyPay">
		           	预付
		           </s:if>
		           <s:else>
		          	 非预付
		           </s:else>
		           </td>
		          
		          <td align="left" >
		          <s:if test="#apply[0].payState==0">
		          	草稿
		          </s:if>
		          <s:elseif test="#apply[0].payState==1">
		          	待确认
		          </s:elseif>
		          <s:elseif test="#apply[0].payState==2">
		          	确认通过
		          </s:elseif>
		          <s:elseif test="#apply[0].payState==3">
		          	确认退回
		          </s:elseif>
		          <s:elseif test="#apply[0].payState==4">
		          	确认处理
		          </s:elseif>
		          </td>
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
	</s:if>
	
	</table>
	</s:form>
</body>
</html>