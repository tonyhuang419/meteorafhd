<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协合同新建</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
function avaliableText(){
var selectX = document.getElementById("selectX");
var info;
for(var i=0; i<selectX.options.length; i++){
		if(selectX.options[i].selected){
			info = selectX.options[i].text;
		}
	}
	if( info == '是'){
		document.getElementById("ava1").disabled = false;
		document.getElementById("ava2").disabled = false;
		document.getElementById("ava3").disabled = false;
		document.getElementById("cc").onclick = function(){
			setday(this,CalendarSelector2,2000,2010,'yyyy-MM-dd');
		}
	}
	else{
		document.getElementById("ava1").disabled = true;
		document.getElementById("ava2").disabled = true;
		document.getElementById("ava3").disabled = true;
		document.getElementById("cc").onclick = null;
	}
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center"><table width="96%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td height="3" align="left">当前页面：外协管理->外协合同明细</td>
      </tr>
      <tr>
        <td class="bg_table01" height="1"><img src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
      </tr>
    </table>
        <table width="96%" border="0" cellspacing="1" cellpadding="1">
        <tr align="center">
            <td class="bg_table02" width="17%" align="right">销售人员：</td>
            <td class="bg_table02" align="left" colspan="3"><s:property value="userName"/></td>
          </tr>
          <tr align="center">
            <td align="right" class="bg_table02">外协供应商：</td>
            <td align="left" class="bg_table02"><s:property value="supplierName"/></td>
            <td align="right" class="bg_table02">&nbsp;</td>
            <td align="left" class="bg_table02">&nbsp;</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">项目名称：</td>
            <td width="33%" align="left" class="bg_table02"><s:property value="ac.mainProjectName" /></td>
            <td width="13%" align="right" class="bg_table02">项目号：</td>
            <td width="37%" align="left" class="bg_table02"><s:property value="ac.mainProjectId"/></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">外协合同名称：</td>
            <td align="left" class="bg_table02"><label>
              <s:property value="ac.assistanceName"/>
            </label></td>
            <td class="bg_table02" align="right"></td>
            <td class="bg_table02" align="left"></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">外协合同金额：</td>
            <td class="bg_table02" align="left"><s:property value="ac.contractMoney" /></td>
            <td class="bg_table02" align="right">&nbsp;</td>
            <td class="bg_table02" align="left">&nbsp;</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">合同签订日期：</td>
            <td class="bg_table02" align="left"><s:property value="ac.contractDate" />
                </td>
            <td class="bg_table02" align="right">预计结束日期：</td>
            <td class="bg_table02" align="left"><s:property value="ac.endDate" />
                </td>
          </tr>
          <tr align="center">
            <td height="79" align="right" class="bg_table02">分包合同内容描述：</td>
            <td class="bg_table02" align="left" colspan="3" ><s:property value="ac.contractContent" /></td>
          </tr>

          <tr class="bg_table03" align="center" style="height:42px">
            <td colspan="4"><table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                  <tr>
                    <td align="right" colspan="2"><input class="button01" type="button" name="gonext" value="关 闭" onClick="javascript:window.close()"></td>
                  </tr> 
                </tfoot>
            </table></td>
          </tr>
    </table></td>
  </tr>
</table>
</body>

</html>
