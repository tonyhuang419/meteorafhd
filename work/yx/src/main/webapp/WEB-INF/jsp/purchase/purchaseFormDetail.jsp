<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>
	申购采购查看
</title>

<script language="javascript">

function show1(m,n,x,y){
var infotype=document.getElementsByName("am.applyState");
var infotype2=document.getElementsByName("am.applyType");
var idstate1=document.getElementsByName("idstate1");
var idstate2=document.getElementsByName("idstate2");
infotype[m].checked=true;
infotype2[n-1].checked=true;


if(x==1){
	idstate1[0].checked=true;
}else{
	idstate1[0].checked=false;
}

if(y==2){
	idstate2[0].checked=true;
}else{
	idstate2[0].checked=false;
}
for(var i=0;i<2;i++){
    if(infotype[i].checked){
	var a=infotype[i].value;
	}
}
for(var j=0;j<2;j++){
    if(infotype2[j].checked){
	var b=infotype2[j].value;
	
	}
}
if(a==0&&b==1){
       //未签工程类
       document.all["ht"].style.display="none";
	   document.all["xm"].style.display="none";
	   document.all["kh1"].style.display="none";
	   document.all["rq"].style.display="block";
	   document.all["kh2"].style.display="block";
	   document.all["xrs"].style.display="block";
	   document.all["x"].style.display="block";
}
if(a==0&&b==2){
      //未签集成类
       document.all["ht"].style.display="none";
	   document.all["xm"].style.display="none";
	   document.all["kh1"].style.display="none";
	   document.all["rq"].style.display="block";
	   document.all["kh2"].style.display="block";
	   document.all["xrs"].style.display="none";
	   document.all["x"].style.display="block";
}
if(a==1&&b==1){
      //已签工程类
       document.all["ht"].style.display="block";
	   document.all["xm"].style.display="block";
	   document.all["kh1"].style.display="block";
	   document.all["rq"].style.display="none";
	   document.all["kh2"].style.display="none";
	   document.all["xrs"].style.display="block";
	   document.all["x"].style.display="none";
}
if(a==1&&b==2){
       //已签集成
       document.all["ht"].style.display="block";
	   document.all["xm"].style.display="none";
	   document.all["kh1"].style.display="block";
	   document.all["rq"].style.display="none";
	   document.all["kh2"].style.display="none";
	   document.all["xrs"].style.display="none";
	   document.all["x"].style.display="none";
}
}



</script>

</head>


<body leftmargin="0"  <s:if test="am.id!=null">onLoad="javascript:show1(<s:property value="am.applyState" />,<s:property value="am.applyType" />,<s:property value="idstate1" />,<s:property value="idstate2" />);"</s:if><s:else>onLoad="javascript:show1(0,1,0,0);"</s:else>>

    <s:hidden name="am.id"></s:hidden>
    <s:hidden id="itemAmount" value="${maxAmount}"></s:hidden>
	<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
		<tr>
			<td valign="top" align="center">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td height="3" align="left">
					<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="30%" scrolling="no"></iframe>
					</td>
				</tr>
				<tr>
					<td align="left">当前页面：申购采购->申购采购查看</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1">
					<img src="../../images/temp.gif" alt="temp" width="1" height="3"></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%" align="right" class="bg_table02">申购人：</td>
					<td width="30%" align="left" class="bg_table02"><s:property value="applyMan" /></td>
					<td width="20%" align="right" class="bg_table02">申购部门：</td>
					<td width="30%" align="left" class="bg_table02"><s:property value="applyDepartment" /></td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">是否未签合同申购：</td>
					<td align="left" class="bg_table02">
					<input name="am.applyState" type="radio" disabled value="0" />未签
					<input name="am.applyState" type="radio" disabled value="1" />已签</td>
					<td class="bg_table02" align="right">申购类型：</td>
					<td align="left" class="bg_table02">
					<input type="radio" name="am.applyType" id="amj" value="1" disabled />项目类 
					<input type="radio" id="amj" name="am.applyType" value="2" disabled /> 集成类</td>
				</tr>
			</table>
			<!--合同相关信息-->
			<div id="ht">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%" align="right" class="bg_table02">合同名称：</td>
					<td width="30%" align="left" class="bg_table02" >
						<s:property value="contractName"/>
					</td>
					<td class="bg_table02" width="20%" align="right">合同号:</td>
					<td width="30%" align="left" class="bg_table02" >
						<s:property value="contractId"/>
					</td>
				</tr>
			</table>
			</div>
			<!--合同中的项目-->
			<div id="xm">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%" align="right" class="bg_table02">项目号：</td>
					<td width="30%" align="left" class="bg_table02">
						<s:property value="am.eventId"/>
					</td>
					<td colspan="2"  class="bg_table02">&nbsp;</td>
				</tr>
			</table>
			</div>
			<!--合同中的客户-->
			<div id="kh1">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%"  align="right" class="bg_table02">客户：</td>
					<td width="30%"  align="left" class="bg_table02">
						<s:property value="cName2"/>
					</td>				
					<td colspan="2"  class="bg_table02">&nbsp;</td>
				</tr>
			</table>
			</div>
			<!--预计合同签订日期-->
			<div id="rq">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center" id="yq1">
					<td width="20%" align="right" class="bg_table02">预计合同签订日期：</td>
					<td width="30%" align="left" class="bg_table02">
						<s:date name="am.estimateDate" format="yyyy-MM-dd"/>
					</td>
					<td colspan="2"  class="bg_table02">&nbsp;</td>
				</tr>
			</table>
			</div>
			<!--可选择客户-->
			<div id="kh2">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%" class="bg_table02" align="right">客户：</td>
					<td width="30%" align="left" class="bg_table02">
			       		<s:property value="customerName"/>
					</td>
					<td colspan="2"  class="bg_table02">&nbsp;</td>
				</tr>
			</table>
			</div>
			
			<!--项目名称-->
			<div id="x">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr align="center">
					<td width="20%" class="bg_table02" align="right">项目名称：</td>
					<td width="30%" class="bg_table02" align="left">
						<s:property value="am.projectName" />
					</td>
					<td colspan="2"  class="bg_table02">&nbsp;</td>
			</tr>
			</table>
			</div>
			
			<!--任务号,设备预算-->
			<div id="xrs">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
	         <tr align="center">
	         		<td width="20%" class="bg_table02" align="right">任务号：</td>			
					<td width="30%" align="left"  class="bg_table02">
						<s:property value="am.assignmentId"/>
					</td>	
					<td colspan="2"  class="bg_table02">&nbsp;</td>
				</tr>
		
				<tr align="center">
					<td class="bg_table02" align="right">设备预算：</td>
					<td class="bg_table02" align="left">
						<s:property value="am.bugget"/>
					</td>
					<td colspan="2"  class="bg_table02">&nbsp;</td>
				</tr>
			</table>
			</div>

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="20%" align="right" class="bg_table02">申购金额：</td>		
					<td width="30%" class="bg_table02" align="left">
					<s:property value="am.applymoney"/>
					 </td>
					<td colspan="2"  class="bg_table02">&nbsp;</td>
				</tr>
				<tr align="center">
					<td  class="bg_table02" align="right"  >联系人：</td>
					<td  class="bg_table02" align="left">
					<s:property value="am.linkman"/>
					<td colspan="2" align="left" class="bg_table02">到齐通知 
					<input name="idstate1" onclick="document.forms(0).idstate2.checked=false" value="1" type="checkbox"<s:if test="'view'.equals(action)">disabled</s:if>> 分批通知 
					<input type="checkbox" onclick="document.forms(0).idstate1.checked=false" name="idstate2" value="2"<s:if test="'view'.equals(action)">disabled</s:if>>
					</td>
				</tr>

				<tr align="center">
					<td align="right" class="bg_table02">申购内容：</td>
					<td class="bg_table02" align="left" colspan="3">
						<s:property value="am.applyContent"/>
					</td>
				</tr>

				<tr align="center">
					<td align="right" class="bg_table02">备注：</td>
					<td class="bg_table02" align="left" colspan="3">
						<s:property value="am.remark"/>
					</td>
				</tr>

				<tr align="center">
					<td width="20%"  class="bg_table02">&nbsp;</td>
					<td width="30%"  class="bg_table02">&nbsp;</td>
					<td width="20%"  align="right" class="bg_table02">申购日期：</td>
					<td width="30%" class="bg_table02" align="left">
						<s:date name="am.applyDate" format="yyyy-MM-dd" />
					</td>
				</tr>
				
				<s:if test="am.affirmState==3">
					<td align="right" class="bg_table02">退回理由：</td>
					<td class="bg_table02" align="left" >
						<s:property value="am.returnReason"/>
					</td>
					<td align="right" class="bg_table02">退回日期：</td>
					<td class="bg_table02" align="left">
						<s:date name="am.returnDate" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</s:if>

				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
								<td align="center" colspan="2"><input class="button01"
									type="button" name="gonext" value="关   闭"
									onclick="javascript:window.close();" />
								</td>
							</tr>
						</tfoot>
					</table>
					</td>
				</tr>
			</table>
</body>
</html>
