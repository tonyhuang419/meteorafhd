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
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script language="javascript">
	
	var sectionArr = new Array();
	<s:iterator value = "sectionList">
		sectionArr[sectionArr.length] = new Array("<s:property value = "assistanceStageSId"/>","<s:property value = "sectionAmount"/>");
	</s:iterator>

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
	function saveAssistanceAnClose(){
		document.forms(0).action = "<s:url action="assistance"><s:param name="method">saveAssistanceAnClose</s:param></s:url>";
		document.forms(0).submit();
	}
	//确认提交
	function doPass(){
		if(!validate()){
				text = document.getElementById("remark");
				if(text.value.length<200){
					document.forms(0).action = "<s:url action="assistance"><s:param name="method">saveConfirmSubmit</s:param></s:url>";
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
		       ev2.test("notblank","供应商名称为空,请选择供应商合同名称!",$('ac.supplyId').value);
		       ev2.test("notblank","合同金额为空,请输入金额数字!!",$('ac.contractMoney').value);
		       ev2.test("notblank","项目号为空,请输入项目号!!",$('ac.mainProjectId').value);
		       ev2.test("notblank","外协合同名称为空!",$('ac.assistanceName').value);
		       ev2.test("+float+0","合同金额不是数字!",$('ac.contractMoney').value);
		       ev2.test("notblank","合同签订日期为空",$('sDate').value);
		       ev2.test("dateYX","合同签订日期格式不正确",$('sDate').value);
		       ev2.test("notblank","预计结束日期为空",$('eDate').value);
		       ev2.test("dateYX","预计结束日期格式不正确",$('eDate').value);
		       ev2.test("notblank", "合同签订日期不能大于合同结束日期...", checkTime($('sDate').value,$('eDate').value));
		  	   ev2.test("notblank","分包合同内容描述为空",$('ac.contractContent').value);
		   }  
		  ev2.writeErrors(errorsFrame, "errorsFrame");
		  if (ev2.size() > 0) {
		     return true;
		 }
		 return false;
	}
	function validate1(){
		var ev2=new Validator();
		with(assistance){
		   ev2.test("notblank","合同金额为空,请输入金额数字!!",$('ac.contractMoney').value);
	  	   ev2.test("+float+0","合同金额不是数字!",$('ac.contractMoney').value);
	  	   ev2.test("notblank","合同签订日期为空",$('sDate').value);
	       ev2.test("dateYX","合同签订日期格式不正确",$('sDate').value);
	       ev2.test("notblank","预计结束日期为空",$('eDate').value);
	       ev2.test("dateYX","预计结束日期格式不正确",$('eDate').value);
	       ev2.test("notblank","阶段名称为空!",$('as.assistanceStageSId').value);
	       ev2.test("notblank","阶段金额为空!",$('as.sectionAmount').value);
	       ev2.test("+float","阶段金额不为大于0数字!",$('as.sectionAmount').value);
	       ev2.test("notblank","预计处理日期为空!",$('as.sectionBillTime').value);
	       ev2.test("dateYX","预计处理日期不正确",$('as.sectionBillTime').value);
	       ev2.test("notblank", "合同签订日期不能大于合同结束日期...", checkTime($('sDate').value,$('eDate').value));
	       ev2.test("notblank", "预计处理日期不能小于合同签订日期...", checkTime($('sDate').value,$('as.sectionBillTime').value));
	  	   ev2.test("notblank", "预计处理日期不能大于预计结束日期...", checkTime($('as.sectionBillTime').value,$('eDate').value));
	   	   var flag = checkSectionIsRepeat($('as.assistanceStageSId').value);
	   	   var amount = parseFloatNumber(getUnallocateAmount());
	   	   var assistanceAmount = parseFloatNumber($('as.sectionAmount').value);
	   	   if(assistanceAmount-amount> 0.00){
	   	  		ev2.addError("阶段金额和大于外协合同金额");
	   	   }
		   }  
		  ev2.writeErrors(errorsFrame, "errorsFrame");
		  if (ev2.size() > 0) {
		     return true;
		 }
		 return false;
	}
	function getUnallocateAmount(){
		
		var unall = parseFloatNumber($('ac.contractMoney').value);
		if(sectionArr.length > 0){
		 	for(var k = 0;k < sectionArr.length; k++){
		 		unall -= parseFloatNumber(sectionArr[k][1]);
		 	}
		}
		return number_format(unall);
	}
	function checkSectionIsRepeat(checkedSection){
		var flag = false;
		if(sectionArr.length > 0){
		 	for(var k = 0;k < sectionArr.length; k++){
		 		if(parseFloatNumber(checkedSection) == parseFloatNumber(sectionArr[k][0])){
		 		flag = true;
		 		break;
		 		}
		 	}
		}
		return flag;
	}
	
	function setItemValue(itemObj){
			document.getElementById("contractName").value = itemObj.name;
			document.getElementById("contractId").value = itemObj.id;
			document.getElementById("projectId").value = itemObj.projectId;
			document.getElementById("conItemMainInfoSid").value = itemObj.itemId;
			document.forms(0).elements("ac.assistanceName").value= itemObj.name;
			setItemId(itemObj.itemId);
			
	}
	function setItemId(obj){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=assistanceSectionByItemId&itemSid="+obj,document.assistance.elements("as.assistanceStageSId"),"key","value",{defaultAsync:false,value:"",text:"--请选择--"});
	}
	
	//验证要添加的合同接不能超过
	function checkAmount(url, method, name , num){
	   //去掉逗号,通过url传带逗号的数字，变成0了   
	       var ev=new Validator();  
	       if(name == null || name == ""){
	       		ev.addError("请先选择项目！");
	       }
	       if(num!=""){
	   	     ev.test("+float","合同金额不是数字!",num);
	   	   }
	       if(ev.size()>0){
	        ev.writeErrors(errorsFrame, "errorsFrame");   
	       	return;
	       }
	        num = getNumberChar(num);
	       var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
		      var result = JSON.decode(responseText);
			  if(result.isOverAmount){
			  	 $('ac.contractMoney').value="";
			     ev.addError("你输入的金额不能超过:"+result.amount);
			  } 
		    });
		   myRequest.send("method="+method+"&mainProjectId="+name+"&amount="+num+"&randomNum="+Math.random());
	       ev.writeErrors(errorsFrame, "errorsFrame");   
	}
	function setValuetoSection(obj){
		formatInputNumber(obj);
		if(obj.value != null &&obj.value !=""){
			var amount = getUnallocateAmount();
			$('as.sectionAmount').value =amount;
			formatInputNumber($('as.sectionAmount'));
		}
	}
	function saveSection(){
		if(checkSectionIsRepeat($('as.assistanceStageSId').value)){
			if(confirm("阶段名称相同,是否需要添加?")){
				if(!validate1()){
					document.forms(0).action = "<s:url includeParams="none" action="assistance"></s:url>?method=saveSection&randomNum="+Math.random();
					document.forms(0).submit();
				}
			}
		}
		else{
			if(!validate1()){
					document.forms(0).action = "<s:url includeParams="none" action="assistance"></s:url>?method=saveSection&randomNum="+Math.random();
					document.forms(0).submit();
			}
		}
	}
	function delSec(sId){
		if(confirm("确定要删除吗?")){
			document.forms(0).action = "<s:url includeParams="none" action="assistance"></s:url>?method=deleteSection&sectionId="+sId+"&randomNum="+Math.random();
			document.forms(0).submit();
		}
	}
</script>
</head>
<body leftmargin="0">
<s:form action="assistance" theme="simple">
	<s:hidden name="a.id"></s:hidden>
	<s:hidden name="ac.id"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td height="3" align="left">当前页面:外协管理->外协合同新建</td>
      </tr>
      <tr>
      <td>    <iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe></td>
      </tr>
      <tr>
        <td class="bg_table01" height="1"><img src="../../images/temp.gif" alt="temp" width="1" height="3"></td>
      </tr>
    </table>
        <table width="100%" border="0" cellspacing="1" cellpadding="1">
          <tr align="center">
            <td class="bg_table02" width="17%" align="right">销售人员：</td>
            <td class="bg_table02" align="left" colspan="3"><s:property value="userName"/></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>选择项目：</td>
            <td width="33%" align="left" class="bg_table02">
            <s:textarea name="contractName" id="contractName" rows="2" cols="20" readonly="true"></s:textarea>
            <s:hidden id="contractId" name="ac.contractId"/>
            <input type="button" value="…" onclick="javascript:openUrl('/yx/assistance/chooseContract.action');"></td>
            <td width="13%" align="right" class="bg_table02"><font color="red"> *</font>外协供应商：</td>
            <td width="37%" align="left" class="bg_table02">
            <s:textarea name="supplierName" id="supplyId" rows="2" cols="20" readonly="true"></s:textarea>
            <s:hidden id="supplierid" name="ac.supplyId"/>
                <input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseSup.action');"></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>项目号：</td>
            <td class="bg_table02" align="left">
            <s:textfield name="ac.mainProjectId" id="projectId" size="15" readonly="true"></s:textfield>
            <s:hidden name="ac.conItemMainInfoSid" id="conItemMainInfoSid"></s:hidden>
            <td class="bg_table02" align="right">&nbsp;</td>
            <td align="left" class="bg_table02">&nbsp;</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>外协合同名称：</td>
            <td align="left" class="bg_table02">
            <s:textarea name="ac.assistanceName" rows="2" cols="20"></s:textarea>
            </td>
            <td class="bg_table02" align="right"><font color="red"> *</font>外协合同金额：</td>
            <td class="bg_table02" align="left"><s:textfield id="money" name="ac.contractMoney" size="15" 
            	onblur="checkAmount('/yx/assistance/assistance.action','checkAmount',$('ac.mainProjectId').value,$('ac.contractMoney').value);setValuetoSection(this)" /></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>合同签订日期：</td>
            <td class="bg_table02" align="left">
            	<s:textfield name="ac.contractDate" id="sDate"  size="13"/>
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sDate')" align=absMiddle alt=""  /></td>
            <td class="bg_table02" align="right"><font color="red"> *</font>预计结束日期：</td>
            <td class="bg_table02" align="left"><s:textfield name="ac.endDate" id="eDate"  size="13"/>
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('eDate')" align=absMiddle alt=""  /></td>
          </tr>
          <tr align="center">
            <td height="79" align="right" class="bg_table02"><font color="red"> *</font>分包合同内容描述：</td>
            <td class="bg_table02" align="left" colspan="3" ><s:textarea  name="ac.contractContent" id="remark" cols="20" rows="3"></s:textarea>
</td>
          </tr>
		<tr>
			<td class="bg_table02" colspan="4">
				<hr>
			</td>
		</tr>
		<tr>
			<td align="center" class="bg_table02" colspan="4">
				&nbsp;
			</td>
		</tr>
	<tr class="bg_table02">
		<td class="bg_table02" colspan="4">
			<table width="100%" align="center" border="0" cellspacing="1" cellpadding="1">
				
				<s:iterator value="sectionList">
					<tr align="center">
						<td align="right">阶段名称：</td>
						<td ><s:property value="contractService.findStageName(assistanceStageSId)" /></td>
						<td align="right">辅助阶段：</td>
						<td ><s:property value="sectionRemarks" /></td>
						<td align="right">阶段金额：</td>
						<td ><s:property value="sectionAmount" /></td>
						<td align="right">预计处理日期：</td>
						<td ><s:property value="sectionBillTime" /></td>
						<td>
						<a href="#" onclick="delSec(<s:property value="id"/>)">删除</a></td>
					</tr>
				</s:iterator>
				<tr>
		            <td class="bg_table02" align="right"><font color="red"> *</font>阶段名称：</td>
		            <td align="left" class="bg_table02">
		            <select name="as.assistanceStageSId">
		            <option value="">----</option>
		            </select>
		            </td> 
		            <td align="right">辅助阶段：</td>
						<td >
						<input type="text"  name="as.sectionRemarks" value=""  size="12" />
						</td>
		            <td class="bg_table02" align="right"><font color="red"> *</font>阶段金额：</td>
		            <td class="bg_table02" align="left">
		            <input type="text" name="as.sectionAmount" value="<s:property value="unallocateAmount"/>" size="15" onblur="formatInputNumber(this);" />
		            <td align="right" class="bg_table02"><font color="red"> *</font>预计处理日期：</td>
		            <td align="left" class="bg_table02">
		           		 <input type="text" id="sectionDate" name="as.sectionBillTime" value=""  size="12" />
					  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sectionDate')" align=absMiddle alt=""  />
					</td>
		            <td class="bg_table02" align="center" colspan="2">
		            <a href="#" onclick="saveSection()">添加</a></td>
	          	</tr>
	        </table>
       </td>
     </tr>
          <tr class="bg_table03" align="center" style="height:42px">
            <td colspan="4"><table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                  <tr>
                    <td align="right" colspan="2"><input class="button01" type="button" name="gonext" value="保    存" onclick="javascript:doSave()" ></td>
                    <td><input class="button01" type="button" name="gonext" value="确认提交" onClick="javascript:doPass()"></td>
                  	<td>
                  		<input type="button" value="关闭" class="button01" onclick="saveAssistanceAnClose();">
                  	</td>
                  </tr> 
                </tfoot>
            </table></td>
          </tr>
    </table></td>
  </tr>
</table>
</s:form>
</body>
<script type="text/javascript">
	var isid = document.assistance.elements("ac.conItemMainInfoSid").value;
	if(isid!=null&&isid!=""){
		setItemId(isid);
	}
	<s:if test = "#notSure == true">
	var alertMsg = "外协合同：<s:property value="#assistanceContractName"/>";
	<s:if test = "#returnCode == 1">
	alertMsg += "供应商代码不正确！";
	</s:if>
	alert(alertMsg);
</s:if>
</script>
</html>
