<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">

      <s:if  test="recevieAmountId == null" >
        <title>新增收款</title>
      </s:if>
      <s:else>
        <title>修改收款</title>
      </s:else>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
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
function saveEdit(){
	if( !validate() ){
		document.forms(0).submit();
	}
}
	function getEmployeeOfDepartment(departmentCode){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=doGetEmployeeOfDepartmentSimple&departmentCode="+departmentCode,document.getElementById("saleman"),"id","name",{value:"",text:"    "});
	}


	//验证表单
	function validate(){
		var ev2=new Validator();
		with(noContractHarvestManage){
		       ev2.test("notblank","客户不能为空!",$('receAmount.customerid').value);
		       ev2.test("notblank","收款日期不能为空!",$('receAmount.recevieDate').value);
		       ev2.test("notblank","收款金额不能为空!",$('receAmount.recevieAmount').value);
		       ev2.test("dateYX","收款日期格式不正确!",$('receAmount.recevieDate').value);
		       ev2.test("+float","收款金额为0或不是数字！",$('receAmount.recevieAmount').value);
		       ev2.test("notblank","销售员不能为空！",$('receAmount.saleMan').value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	
	function selectedClient(client){
	   var i= client.clientId;
	   var j= client.clientName;
	   if(i!=""&&j!=""){
	     var customernametext = document.getElementById("customername");
	     customernametext.value = j;
	     var customeridhidden = document.getElementById("customerid");
	     customeridhidden.value = i; 
	   }
	}
	<s:if test="#isSaveSuccess == 'true'">
	opener.reflushPage();
	window.close();
    </s:if>
</script>
</head>
<body>
<s:form theme="simple" action="noContractHarvestManage">
<s:hidden name="method" value="saveHarvest"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center"><table width="96%" border="0" cellspacing="1" cellpadding="1">
      <iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
      <tr>
      <s:if  test="recevieAmountId == null" >
        <td height="3" align="left">当前页面:收款管理->新增收款</td>
      </s:if>
      <s:else>
        <td height="3" align="left">当前页面:收款管理->修改收款</td>
      </s:else>
      </tr>
      <tr>
        <td class="bg_table01" height="1"><img src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
      </tr>
    </table>
        <table width="96%" border="0" cellspacing="1" cellpadding="1">
        <tr align="center">
			      <td align="right" class="bg_table02" nowrap>组别：</td>
		          <td class="bg_table02" align="left">
		        	  <s:select name="groupId"  onchange="getEmployeeOfDepartment(this.value)" list="groupList" listKey="departmentCode" listValue="departmentName"></s:select>
		         </td>
		         <td align="right" class="bg_table02" nowrap><font color="red"> *</font>销售员：</td>
		          <td class="bg_table02" align="left" >
		             <s:select name="receAmount.saleMan" id="saleman" headerKey="" headerValue="--请选择--"  list="empList" listKey="id" listValue="name" required="true"  ></s:select>
	              </td>
          </tr>
         <tr align="center">         
         <s:hidden name="receAmount.id" ></s:hidden>
            <td  align="right" class="bg_table02"><font color="red"> *</font>客户名称：</td>
            <td  align="left" class="bg_table02"><s:textarea name="customername" readonly="true" id="customername"></s:textarea>
            <s:hidden name="receAmount.customerid" id="customerid"></s:hidden>
            <input type="button" value="…" onclick="javascript:openUrl('/yx/searchClient/searchClientQuery.action?method=getSelectClientlist');"></td>
            <td  align="right" class="bg_table02">&nbsp;</td>
            <td  align="left" class="bg_table02">&nbsp;</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>收款金额：</td>
            <td class="bg_table02" align="left"><s:textfield onblur="formatInputNumber(this)" name="receAmount.recevieAmount" size="15" /></td>
            <td class="bg_table02" align="left"><div align="right"><font color="red"> *</font>收款日期：</div></td>
            <td class="bg_table02" align="left">
            <s:textfield name="receAmount.recevieDate" id = "recevieDate" size="12" ></s:textfield>
            <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('recevieDate')" align=absMiddle alt=""  />
			</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>收款状态：</td>
            <td align="left" class="bg_table02"><s:select name = "receAmount.state" list="#@java.util.TreeMap@{0:'未核销',1:'已核销',2:'历史核销'}" ></s:select></td>
            <td class="bg_table02" align="right">是否预收款：</td>
            <td class="bg_table02" align="left"><s:select name="receAmount.isPerArrive" list="#@java.util.TreeMap@{0:'否',1:'是'}" /></td>
          </tr>
         <tr align="center">         
            <td  align="right" class="bg_table02">备注：</td>
            <td  align="left" class="bg_table02"><s:textarea name="receAmount.remark" ></s:textarea></td>
            <td  align="left" class="bg_table02">&nbsp;</td>
            <td  align="left" class="bg_table02">&nbsp;</td>
          </tr>
          
          <tr class="bg_table03" align="center">
            <td colspan="4"><table style="width:0%;100%">
              
                  <tr>
                    <td align="right" colspan="2"><input class="button01" type="button"  value="保    存" onclick="javascript:if(!validate()){document.noContractHarvestManage.submit();}" ></td>
                    <td align="right" colspan="2"><input class="button01" type="button"  value="关    闭" onClick="javascript:window.close()"></td>
                  </tr> 
              
            </table></td>
          </tr>
    </table></td>
  </tr>
</table>
</s:form>
</body>
</html>
