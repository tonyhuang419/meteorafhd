<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协合同修改</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="css/calendar-win2k-cold-1.css" title="win2k-cold-1" />
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
function aaa(){
	document.forms(0).method.value = "editAssistance";
	document.forms(0).submit();
	window.close();
	
}
</script>
</head>
<body>

<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center"><table width="96%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td height="3" align="left">当前页面：外协管理->外协合同修改</td>
      </tr>
      <tr>
        <td class="bg_table01" height="1"><img src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
      </tr>
    </table>
 <s:form action="assistance" theme="simple">
 		<s:hidden name="method" value="editAssistance"></s:hidden>
        <table width="96%" border="0" cellspacing="1" cellpadding="1">
          <tr align="center">
            <td class="bg_table02" width="17%" align="right">销售人员：</td>
            <td class="bg_table02" align="left" colspan="3"><s:property value="userName"/></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">选择合同：</td>
            <td width="33%" align="left" class="bg_table02"><s:textfield name="MContractName" id="contractName" size="15" readonly="true"/>
            <s:hidden id="contractId" name="ac.contractId"/>
            <input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseContract.action');"></td>
            <td width="13%" align="right" class="bg_table02">外协供应商：</td>
            <td width="37%" align="left" class="bg_table02"><s:textfield name="supplierName"/><s:hidden name="ids" value="ac.id"/></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">项目号：</td>
            <td class="bg_table02" align="left"><s:textfield name="ac.mainProjectId"/></td>
            <td class="bg_table02" align="right">项目名称：</td>
            <td align="left" class="bg_table02"><s:textfield name="ac.mainProjectName"/></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">外协合同名称：</td>
            <td class="bg_table02" align="left"><s:textfield name="ac.assistanceName"/></td>
            <td class="bg_table02" align="right">外协合同金额：</td>
            <td class="bg_table02" align="left"><s:textfield name="ac.contractMoney"/></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">合同签订日期：</td>
            <td class="bg_table02" align="left"><s:textfield name="ac.contractDate"/></td>
            <td class="bg_table02" align="right">预计结束日期：</td>
            <td class="bg_table02" align="left"><s:textfield name="ac.endDate"/></td>
            <s:hidden name="ac.id"></s:hidden>
          </tr>
          <tr align="center">
            <td height="79" align="right" class="bg_table02">分包合同内容描述：</td>
            <td class="bg_table02" align="left" colspan="3" ><s:textarea name="address3" cols="60" rows="5" name="ac.contractContent"></s:textarea></td>
          </tr>
          <tr class="bg_table03" align="center" style="height:42px">
            <td colspan="4"><table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                  <tr>
                    <td><input class="button01" type="submit" value="保   存"/></td>
                    <td align="right" colspan="2"><input class="button01" type="button" value="关  闭" onClick="window.close()"/></td>
                  </tr> 
                </tfoot>
            </table>
         </td></tr></table></s:form>
        </td></table>
</body>
</html>
