<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>addNewDepartment</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all"
	href="./css/calendar-win2k-cold-1.css" title="win2k-cold-1" />
	<script src="/yx/commons/scripts/public.js" type="text/javascript"></script>

<script src="<s:url value="/commons/scripts/CalendarSelector.js"/>"
	type="text/javascript"></script>

<script language="javascript">

function show(){
var infotype=document.getElementsByName("am.applyState");
var infotype2=document.getElementsByName("am.applyType");
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

if(a==1&&b==1){
       //未签工程类
       document.all["yq"].style.display="none";
	   document.all["yqgc"].style.display="none";
	   document.all["yqjc"].style.display="none";
	   document.all["wq"].style.display="block";
	   document.all["gc"].style.display="block";
	   document.all["jc"].style.display="none";
}
if(a==1&&b==2){
      //未签集成类
       document.all["yq"].style.display="none";
	   document.all["yqgc"].style.display="none";
	   document.all["yqjc"].style.display="none";
	   document.all["wq"].style.display="block";
	   document.all["gc"].style.display="none";
	   document.all["jc"].style.display="block";
}
if(a==2&&b==1){
      //已签工程类
       document.all["yq"].style.display="block";
	   document.all["yqgc"].style.display="block";
	   document.all["yqjc"].style.display="none";
	   document.all["wq"].style.display="none";
	   document.all["gc"].style.display="block";
	   document.all["jc"].style.display="none";
}
<!--if(a==2&&b==1){-->
<!--      //已经签-->
<!--       document.all["yq"].style.display="block";-->
<!--	   document.all["yqgc"].style.display="block";-->
<!--	   document.all["yqjc"].style.display="none";-->
<!--	   document.all["wq"].style.display="none";-->
<!--	   document.all["gc"].style.display="block";-->
<!--	   document.all["jc"].style.display="none";-->
<!--}-->
if(a==2&&b==2){
       //已签集成
       document.all["yq"].style.display="block";
	   document.all["yqgc"].style.display="none";
	   document.all["yqjc"].style.display="block";
	   document.all["wq"].style.display="none";
	   document.all["gc"].style.display="none";
	   document.all["jc"].style.display="block";
}

}

</script>

</head>
<body onLoad="javascript:show();">
<s:form action="purchase" theme="simple">

	<s:hidden name="method" value="updateAm" />
	<s:hidden name="am.id"></s:hidden>
	<table width="95%" border="0" align="center" cellpadding="1"
		cellspacing="1">
		<tr>
			<td valign="top" align="center">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td height="3" align="left">当前页面：申购采购->申购采购新建</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1"><img
						src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
				</tr>
			</table>
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" align="right" class="bg_table02">申购人：</td>
					<td width="23%" align="left" class="bg_table02">徐同心</td>
					<td width="17%" align="right" class="bg_table02">申购部门：</td>
					<td width="35%" align="left" class="bg_table02">制造营销</td>
				</tr>

				<tr align="center">
					<td class="bg_table02" align="right">是否未签合同申购</td>
					<td align="left" class="bg_table02"> 
						<s:if test="am.applyState==1">
						       <input
						name="am.applyState" type="radio" checked="true" value="1"
						onClick="javascript:show();">未签
						
						<input
						name="am.applyState" type="radio"   value="2"
						onClick="javascript:show();">已签
						</s:if>
						<s:if test="am.applyState==2">
						   <input
						name="am.applyState" type="radio"  checked="true" value="2"
						onClick="javascript:show();">已签
						</s:if>
						
						</td>
					<td class="bg_table02" align="right">申购类型：</td>
					<td align="left" class="bg_table02"> 
						
						
						<s:if test="am.applyType==1">
						      <input type="radio"
						checked="true" name="am.applyType" value="1" onClick="javascript:show();" />
					工程类
					<input type="radio" name="am.applyType"  value="2"
						onClick="javascript:show();" /> 集成类 
						</s:if>
						<s:if test="am.applyType==2">
						   <input type="radio" name="am.applyType" checked="true" value="2"
						onClick="javascript:show();" /> 集成类 
						</s:if>
						</td>
				</tr>
			</table>

			<div id="yq">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" align="right" class="bg_table02">选择合同：</td>
					<td width="23%" align="left" class="bg_table02"><label>
					<s:textfield name="" size="15" /> <input type="button" value="…"
						onclick="javascript:openUrl('合同选择.html');"></label></td>
					<td class="bg_table02" width="17%" align="right">选择项目：</td>
					<td class="bg_table02" width="35%" align="left"><s:textfield
						name="am.projectName" size="15" /> <input type="button" value="…"
						onclick="javascript:openUrl('合同选择.html');"></td>
				</tr>
			</table>
			</div>
			<!--
				<tr align="center">
					<td colspan="4" align="right" class="bg_table02">
					<hr>
					</td>
				</tr>
        --> <!-- end 未签 --> <!-- s 已签 工程类-->
			<div id="yqgc">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" align="right" class="bg_table02">项目号：<s:property
						value="am.mainId" /></td>
					<td width="23%" align="left" class="bg_table02">&nbsp;</td>
					<td width="17%" align="right" class="bg_table02">&nbsp;</td>
					<td width="35%" align="left" class="bg_table02">&nbsp;</td>
				</tr>
			</table>
			</div>

			<div id="yqjc">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" align="right" class="bg_table02">销售合同号：<s:property
						value="am.mainId" /></td>
					<td width="23%" align="left" class="bg_table02">&nbsp;</td>
					<td width="17%" class="bg_table02" align="right">&nbsp;</td>
					<td width="35%" class="bg_table02" align="left">&nbsp;</td>
				</tr>
			</table>
			</div>


			<!-- 为签-->
			<div id="wq">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center" id="yq1">
					<td width="25%" align="right" class="bg_table02">预计合同签订日期：</td>
					<td width="23%" align="left" class="bg_table02"><s:textfield
						name="am.estimateDate" size="15" value="2008-01-02" /></td>
					<td width="17%" class="bg_table02" align="right">&nbsp;</td>
					<td width="35%" class="bg_table02" align="left">&nbsp;</td>
				</tr>
			</table>
			</div>

			<div id="gc">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" class="bg_table02" align="right">客户：</td>
					<td width="23%" align="left" class="bg_table02"><s:hidden
						name="am.customerId" /> <s:textfield name="cc.name" /> <input
						type="button" value="…"
						onclick="javascript:window.open('../purchase/purchase.action?method=showPurList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');">
					</td>
					<td width="17%" class="bg_table02" align="right">&nbsp;</td>
					<td width="35%" class="bg_table02" align="left">&nbsp;</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">项目名称：</td>
					<td class="bg_table02" align="left"><!--					<s:textfield name="pName" size="15" ></s:textfield>-->
					<input type="text" name="pName" size="15"
						value="<s:property value="am.projectName"/>" />
					<td class="bg_table02" align="right">任务号：</td>
					<td align="left" class="bg_table02"><s:textfield
						name="am.assignmentId" size="15" /></td>



				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">设备预算：</td>
					<td class="bg_table02" align="left"><s:textfield
						name="am.bugget" size="15" /></td>
					<td class="bg_table02" align="right">&nbsp;</td>
					<td align="left" class="bg_table02">&nbsp;</td>
				</tr>
			</table>
			</div>

			<div id="jc">
			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" height="23" align="right" class="bg_table02">客户：</td>
					<td width="23%" align="left" class="bg_table02"><s:hidden
						name="cusId" /> <input type="text" name="cname"
						value="<s:property value="cc.name"/>" /> <!--						<s:textfield name="cname"></s:textfield>-->
					<input type="button" value="…"
						onclick="javascript:window.open('../purchase/purchase.action?method=showPurList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');">
					</td>

					<td width="17%" class="bg_table02" align="right">&nbsp;</td>
					<td width="35%" class="bg_table02" align="left">&nbsp;</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">项目名称：</td>
					<td class="bg_table02" align="left"><input type="text"
						name="pNam" size="15" value="<s:property value="am.projectName"/>" />
					<!--						<s:textfield name="pNam" size="15"></s:textfield>--></td>
					<td class="bg_table02" align="right"></td>
					<td align="left" class="bg_table02"></td>
				</tr>
			</table>
			</div>

			<table width="94%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="25%" height="24" align="right" class="bg_table02">联系人：</td>
					<td width="23%" class="bg_table02" align="left"><s:hidden
						name="am.linkmanId" /> <s:textfield name="lm.name" id="linkName"
						size="15" /> <input type="button" value="…"
						onclick="javascript:window.open('../purchase/purchase.action?method=showLinkMList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');">
					</td>
					<td colspan="2" align="left" class="bg_table02"><s:if
						test="am.informState==0">
					   		到齐通知 <input name="idstate1" value="0" type="checkbox"
							checked="true" />
					</s:if> <s:else>
					       到齐通知 <input name="idstate1" value="0" type="checkbox" />
					</s:else> <s:if test="am.informState==1">
			              分批通知 <input type="checkbox" name="idstate2" value="0"
							checked="true" />
					</s:if> <s:else>
			             分批通知 <input type="checkbox" name="idstate2" value="0">
					</s:else> <s:if test="am.informState==2">
                             到齐通知 <input name="idstate1" value="0"
							type="checkbox" checked="true">
							 分批通知 <input type="checkbox" name="idstate2" value="0"
							checked="true">
					</s:if> <s:elseif test="am.informState==null">
						 到齐通知 <input name="idstate1" value="0" type="checkbox" />
						  分批通知 <input type="checkbox" name="idstate2" value="0">
					</s:elseif> <s:else>

					</s:else></td>
				</tr>


				<tr align="center">
					<td height="29" align="right" class="bg_table02">申购内容：</td>
					<td class="bg_table02" align="left" colspan="3"><s:textarea
						name="am.applyContent" cols="60" rows="5"></s:textarea></td>
				</tr>

				<tr align="center">
					<td height="29" align="right" class="bg_table02">备注：</td>
					<td class="bg_table02" align="left" colspan="3"><s:textarea
						name="am.remark" cols="40" rows="5"></s:textarea></td>
				</tr>

				<tr align="center">
					<td class="bg_table02" width="25%" align="right">&nbsp;</td>
					<td class="bg_table02" width="23%" align="left">&nbsp;</td>
					<td width="8%" align="right" class="bg_table02">&nbsp;</td>
					<td width="44%" align="left" class="bg_table02">申购日期：2007-5-6</td>
				</tr>


				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
					<table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
								<td align="right" colspan="2"><input class="button01"
									type="submit" name="gonext" value="保    存" /></td>

								<td align="right" colspan="2"><input class="button01"
									type="button" name="gonext" value="关   闭"
									onClick="javascript:window.close()"></td>
							</tr>
						</tfoot>
					</table>
					</td>
				</tr>
			</table>
			</s:form>
</body>

</html>
