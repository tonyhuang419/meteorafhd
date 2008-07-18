<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协合同新建</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<script language="javascript" src="<s:url value="/commons/scripts/checkLength.js"/>"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
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
function selectProject(pro){
	document.getElementById("contractId").value = pro.options[pro.selectedIndex].text;
}

function enterManage(){
	location.href("../assistance/loginMain.action");
}
//验证通过表单提交
	function doSave(){
		if(!validate()){
				text = document.getElementById("remark");
				if(text.value.length<200){
					document.forms(0).action = "<s:url action="assistance"><s:param name="method">saveAssistance</s:param></s:url>";
					document.forms(0).submit();
				}else{
					alert("分包合同内容描述内容过长!");
				}
		}
	}
	function doPass(){
		if(!validate()){
				text = document.getElementById("remark");
				if(text.value.length<200){
					document.forms(0).action = "<s:url action="assistance"><s:param name="method">saveAssistance2</s:param></s:url>";
					document.forms(0).submit();
				}else{
					alert("分包合同内容描述内容过长!");
				}
		}
	}
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(assistance){
		       ev2.test("notblank","项目名称为空,请输入项目名称!",$('contractName').value);
		       ev2.test("notblank","供应商为空,请输入项目内容!",$('ac.supplyId').value);
		       ev2.test("notblank","合同金额为空,请输入金额数字!!",$('ac.contractMoney').value);
		       ev2.test("notblank","项目名称为空,请输入金额数字!!",$('ac.assistanceName').value);
		       ev2.test("notblank","外协合同名称为空,请输入金额数字!!",$('ac.assistanceName').value);
		       ev2.test("+float+0","合同金额不是数字,请输入0~9数字!",$('ac.contractMoney').value);
		       ev2.test("notblank", "开票时间开始时间不能大于结束时间...", checkTime($('sDate').value,$('eDate').value));
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
</script>
</head>
<body>
<s:form action="assistance" theme="simple">
	<s:hidden name="a.id"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center"><table width="96%" border="0" cellspacing="1" cellpadding="1">
    <iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
      <tr>
        <td height="3" align="left">当前页面:外协管理->外协合同新建</td>
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
            <td class="bg_table02" align="right">选择合同：</td>
            <td width="33%" align="left" class="bg_table02"><s:textfield name="contractName" id="contractName" size="15" readonly="true"/>
            <s:hidden id="contractId" name="ac.contractId"/>
            <input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseContract.action');"></td>
            
            <td width="13%" align="right" class="bg_table02">外协供应商：</td>
            <td width="37%" align="left" class="bg_table02"><s:textfield id="supplyId" name="supplierName" size="15" readonly="true"></s:textfield>
            <s:hidden id="supplierid" name="ac.supplyId"/>
                <input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseSup.action');"></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">项目号：</td>
            <td class="bg_table02" align="left">
            <s:textfield name="ac.mainProjectId" id="projectId" size="15" readonly="true"></s:textfield>
            <td class="bg_table02" align="right">&nbsp;</td>
            <td align="left" class="bg_table02">&nbsp;</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">项目名称：</td>
            <td align="left" class="bg_table02">
             	<s:textfield name="pName" size="15" ></s:textfield>
            </td>
            <td class="bg_table02" align="right">外协合同名称：</td>
            <td class="bg_table02" align="left"><s:textfield name="ac.assistanceName" size="15" ></s:textfield></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">外协合同金额：</td>
            <td class="bg_table02" align="left"><s:textfield id="money" name="ac.contractMoney" size="15" ></s:textfield></td>
            <td class="bg_table02" align="right">&nbsp;</td>
            <td class="bg_table02" align="left">&nbsp;</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">合同签订日期：</td>
            <td class="bg_table02" align="left"><input type="text" id="sDate" name="sDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sDate')" align=absMiddle alt=""  /></td>
            <td class="bg_table02" align="right">预计结束日期：</td>
            <td class="bg_table02" align="left"><input type="text" id="eDate" name="eDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('eDate')" align=absMiddle alt=""  /></td>
          </tr>
          <tr align="center">
            <td height="79" align="right" class="bg_table02">分包合同内容描述：</td>
            <td class="bg_table02" align="left" colspan="3" ><s:textarea  name="ac.contractContent" id="remark" cols="20" rows="3"></s:textarea>
</td>
          </tr>


          <tr class="bg_table03" align="center" style="height:42px">
            <td colspan="4"><table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                  <tr>
                    <td align="right" colspan="2"><input class="button01" type="button" name="gonext" value="保    存" onclick="javascript:doSave()" ></td>
                    <td><input class="button01" type="button" name="gonext" value="确认提交" onClick="javascript:doPass()"></td>
                    <td align="right" colspan="2"><input class="button01" type="reset"  value="重    填" onClick="" ></td>
                  </tr> 
                </tfoot>
            </table></td>
          </tr>
    </table></td>
  </tr>
</table>
</s:form>
</body>

</html>
