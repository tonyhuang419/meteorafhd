<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>

<title>
	<s:if test="asCreate == 1 ">
		外协合同新增
	</s:if>
	<s:else>
		外协合同修改
	</s:else>
</title>

<link rel="stylesheet" type="text/css" media="all" href="css/calendar-win2k-cold-1.css" title="win2k-cold-1" />
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
	function aaa(){
		document.forms(0).method.value = "editAssistance";
		document.forms(0).submit();
		window.close();
		
	}
	function doSave(){
		if(!validate()){
				text = document.getElementById("remark");
				if(text.value.length<200){
					document.forms(0).method.value = "editAssistance";
					document.forms(0).submit();
				}else{
					alert("分包合同内容描述内容过长!");
				}
		}
	}
	//验证通过表单提交   从新增页面过来的修改页面提交
	function doSaveAdd(){
		if(!validate()){
				text = document.getElementById("remark");
				if(text.value.length<200){
					document.forms(0).method.value = "saveAssistance";
					document.forms(0).submit();
				}else{
					alert("分包合同内容描述内容过长!");
				}
		}
	}
	function saveAssistanceAnClose(){
		document.forms(0).method.value = "saveAssistanceAnClose";
		document.forms(0).submit();
	}
	//从新增页面过来才有提交  这个时候会存在asCreate的值 就直接更新数据
	function doPass(){
		if(!validate()){
				text = document.getElementById("remark");
				if(text.value.length<200){
					document.forms(0).method.value = "saveConfirmSubmit";
					document.forms(0).submit();
				}else{
					alert("分包合同内容描述内容过长!");
				}
		}
	}
	
	function querentijiao()
	{
		if(!validate()){
				text = document.getElementById("remark");
				if(text.value.length<200){
					document.forms(0).method.value = "querentijiao";
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
		       ev2.test("notblank","供应商为空,请输入项目内容!",$('supplierName').value);
		       ev2.test("notblank","合同金额为空,请输入金额数字!!",$('ac.contractMoney').value);
		       ev2.test("notblank","外协合同名称为空!!",$('ac.assistanceName').value);
		       ev2.test("+float+0","合同金额不是数字,请输入0~9数字!",$('ac.contractMoney').value);
		       ev2.test("dateYX","合同签订日期时间格式不正确！",$('ac.contractDate').value);
		       ev2.test("dateYX","预计结束日期时间格式不正确!",$('ac.endDate').value);
		       ev2.test("notblank", "合同开始时间不能大于结束时间...", checkTime($('ac.contractDate').value,$('ac.endDate').value));
		  	   ev2.test("notblank","分包合同内容描述为空",$('ac.contractContent').value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
		//删除阶段信息
	function delSection(sId){
		if(window.confirm("确定要删除吗?"))	
		{
			document.forms(0).action="<s:url includeParams="none" action="assistance"></s:url>";
			document.forms(0).method.value = "delSection";
			document.forms(0).sectionId.value = sId;
			document.forms(0).submit();
		}
	}
	function saveSection(){
		if(checkSectionIsRepeat($('as.assistanceStageSId').value)){
			if(confirm("阶段名称相同,是否需要添加?")){
				if(!validate1()){
					document.forms(0).method.value = "saveSee";
					document.forms(0).submit();
				}
			}
		}
		else{
			if(!validate1()){
					document.forms(0).method.value = "saveSee";
					document.forms(0).submit();
			}
		}	
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
		   	  var amount = parseFloatNumber(getUnallocateAmount())
		   	  if(parseFloatNumber($('as.sectionAmount').value)-amount >0.00 ){
		   	  		ev2.addError("阶段金额和大于外协合同金额");
		   	  }
		   }  
		  ev2.writeErrors(errorsFrame, "errorsFrame");
		  if (ev2.size() > 0) {
		     return true;
		 }
		 return false;
	}
	function updateSection(sId){
		openWin('assistance.action?method=editSee&sectionId='+sId,400,300);
	}
	function refresh(){
		document.forms(0).action = "<s:url includeParams="none" action="assistance"></s:url>";
		document.forms(0).method.value = "enterUpdate";
		document.forms(0).submit();
	}
	function setItemId(obj){
		ajaxSetSelectOptions("<s:url includeParams="none" action="jsonData"></s:url>?method=assistanceSectionByItemId&itemSid="+obj,document.assistance.elements("as.assistanceStageSId"),"key","value",{defaultAsync:false,value:"",text:"--请选择--"});
	}
	
	function setItemValue(itemObj){
			document.getElementById("contractName").value = itemObj.name;
			document.getElementById("contractId").value = itemObj.id;
			document.getElementById("projectId").value = itemObj.projectId;
			document.getElementById("conItemMainInfoSid").value = itemObj.itemId;
			document.forms(0).elements("ac.assistanceName").value= itemObj.name;
			setItemId(itemObj.itemId);
			
	}
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
	       var asContractId = $('ac.id').value;
	        num = getNumberChar(num);
	       var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
		      var result = JSON.decode(responseText);
			  if(result.isOverAmount){
			  	 $('ac.contractMoney').value="";
			     ev.addError("你输入的金额不能超过:"+result.amount);
			  } 
		    });
		   myRequest.send("method="+method+"&mainProjectId="+name+"&asContractId="+asContractId+"&amount="+num+"&randomNum="+Math.random());
	       ev.writeErrors(errorsFrame, "errorsFrame");   
	}
	
	function getUnallocateAmount(){
		var unall = parseFloatNumber($('ac.contractMoney').value);
		if(sectionArr.length > 0){
		
		 	for(var k = 0;k < sectionArr.length; k++){
		 		unall-=parseFloatNumber(sectionArr[k][1]);
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
	function setValuetoSection(obj){
		formatInputNumber(obj);
		if(obj.value != null &&obj.value !=""){
			var amount = getUnallocateAmount();
			$('as.sectionAmount').value =amount;
			formatInputNumber($('as.sectionAmount'));
		}
	}
	
</script>
</head>
<body style="margin: 0px;">
<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
      <tr>
        <s:if test="asCreate == 1 ">
	        <td align="left">当前页面：外协管理->外协合同新增</td>
		</s:if>
		<s:else>
	        <td align="left">当前页面：外协管理->外协合同修改</td>
		</s:else>
      </tr>
    </table>
    
 <s:form action="assistance" theme="simple">
 		<s:hidden name="method" value="editAssistance"></s:hidden>
		<s:hidden name="sectionId"/>
		<s:hidden name="asCreate"></s:hidden>
		
		
        <table width="100%" border="0" cellspacing="1" cellpadding="1">
        <tr>
        <td colspan="4" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="3"></td>
      </tr>
      	<s:if test="ac.assistanceContractType==3">
      	<tr align="center">
      		<td class="bg_table02" width="17%." align="right">退回理由：</td>
            <td class="bg_table02" align="left" colspan="3"><font color="red">
				<s:property  value="ac.returnReason"/>
			</font></td>
		</tr>
		</s:if>
          <tr align="center">
            <td class="bg_table02" width="17%." align="right">销售人员：</td>
            <td class="bg_table02" align="left" colspan="3"><s:property value="userName"/></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>选择项目：</td>
            <td width="33%" align="left" class="bg_table02">
             <s:textarea name="MContractName" id="contractName" rows="2" cols="20" readonly="true"></s:textarea>
            <s:hidden id="contractId" name="ac.contractId"/>
            <input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseContract.action');"></td>
            <td align="right" class="bg_table02"><font color="red"> *</font>外协供应商：</td>
            <td width="33%" align="left" class="bg_table02">
            <s:textarea name="supplierName" id="supplyId" rows="2" cols="20" readonly="true"></s:textarea>
            <s:hidden name="ids"/>
            <s:hidden id="supplierid" name="ac.supplyId"/>
            <input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseSup.action');"></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>项目号：</td>
            <td class="bg_table02" align="left"><s:textfield name="ac.mainProjectId" id="projectId" readonly="true"/>
             <s:hidden name="ac.conItemMainInfoSid" id="conItemMainInfoSid"></s:hidden>
            </td>
            <td class="bg_table02" align="right"></td>
            <td align="left" class="bg_table02"></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>外协合同名称：</td>
            <td class="bg_table02" align="left">
            <s:textarea name="ac.assistanceName" rows="2" cols="20"></s:textarea>
            </td>
            <td class="bg_table02" align="right"><font color="red"> *</font>外协合同金额：</td>
            <td class="bg_table02" align="left"><s:textfield name="ac.contractMoney" 
            onblur="checkAmount('/yx/assistance/assistance.action','checkAmountNotContains',$('ac.mainProjectId').value,$('ac.contractMoney').value);setValuetoSection(this);"/></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">合同签订日期：</td>
            <td class="bg_table02" align="left"><s:textfield name="ac.contractDate" id="sDate"  size="13"/>
            <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sDate')" align=absMiddle alt=""  /></td>
            <td class="bg_table02" align="right">预计结束日期：</td>
            <td class="bg_table02" align="left"><s:textfield name="ac.endDate" id="eDate"  size="13"/>
            <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('eDate')" align=absMiddle alt=""  /></td>
            <s:hidden name="ac.id"></s:hidden>
          </tr>
          <tr align="center">
            <td height="79" align="right" class="bg_table02"><font color="red"> *</font>分包合同内容描述：</td>
            <td class="bg_table02" align="left" colspan="3" ><s:textarea name="address3" cols="20" rows="3" name="ac.contractContent" id="remark"></s:textarea></td>
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
				
				<s:iterator value="sectionList" id="sec">
					<tr align="center">
						<td class="bg_table02" align="right">阶段名称：</td>
						<td><s:property value="contractService.findStageName(assistanceStageSId)" /></td>
						<td class="bg_table02" align="right">辅助阶段：</td>
						<td><s:property value="sectionRemarks" /></td>
						<td class="bg_table02" align="right">阶段金额：</td>
						<td ><s:property value="sectionAmount" /></td>
						<td align="right" class="bg_table02">预计处理日期：</td>
						<td ><s:property value="sectionBillTime" /></td>
						<td>
						<a href="#" onclick="delSection(<s:property value="id"/>)">删除</a></td>
					</tr>
				</s:iterator>
				<tr>
		            <td class="bg_table02" align="right"><font color="red"> *</font>阶段名称：</td>
		            <td align="left" class="bg_table02">
		            <select name="as.assistanceStageSId">
		            <option value="">----</option>
		            </select>
		            </td>
	            	<td class="bg_table02" align="right">辅助阶段：</td>
					<td>
						<input type="text" name="as.sectionRemarks" value=""  size="12" />
					</td>            
		            <td class="bg_table02" align="right"><font color="red"> *</font>阶段金额：</td>
		            <td class="bg_table02" align="left">
		            <input type="text" name="as.sectionAmount" value="<s:property value="unallocateAmount"/>" size="15" onblur="formatInputNumber(this);" />
		            <td align="right" class="bg_table02"><font color="red"> *</font>预计处理日期：</td>
		            <td align="left" class="bg_table02">
		           		 <input type="text" id="sectionDate" name="as.sectionBillTime" value=""  size="12" />
					  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sectionDate')" align=absMiddle alt=""  />
					</td>
		            <td class="bg_table02" align="center" colspan="2"><a href="#" onclick="saveSection()">添加</a></td>
	          	</tr>
	        </table>
       </td>
     </tr>
     
          <tr class="bg_table03" align="center" style="height:42px">
            <td colspan="4"><table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                   <s:if test="asCreate == 1 ">
	                  <tr>
	                    <td align="right" colspan="2"><input class="button01" type="button" name="gonext" value="保    存" onclick="javascript:doSaveAdd()" ></td>
	                    <td><input class="button01" type="button" name="gonext" value="确认提交" onClick="javascript:doPass()"></td>
	                  	<td>
	                  		<input type="button" value="关闭" class="button01" onclick="saveAssistanceAnClose();">
	                  	</td>
	                  </tr> 
					</s:if>
					<s:else>
	                  <tr>
	                    <td><input class="button01" type="button" onclick="javascript:doSave()" value="保   存"/></td>
	                    <td><input class="button01" type="button" name="gonext" value="确认提交" onClick="javascript:querentijiao()"></td>
	                    <td align="right">
	                    	<input class="button01" type="button" value="关  闭" onClick="window.close()"/>
	                    </td>
	                  </tr> 
					</s:else>
                </tfoot>
            </table>
  </s:form>
        
</body>
<script type="text/javascript">
	var isid = document.assistance.elements("ac.conItemMainInfoSid").value;
	if(isid!=null&&isid!=""){
		setItemId(isid);
	}
	<s:if test = "#notSure == true">
		var alertMsg = "<s:property value="#assistanceContractName"/>:";
		<s:if test = "#returnCode == 1">
		alertMsg += "无供应商代码！";
		</s:if>
		<s:if test = "#returnCode == 2">
		alertMsg += "还没有添加阶段！";
		</s:if>
		<s:if test = "#returnCode == 3">
		alertMsg += "阶段名称和该项目下的阶段不匹配！";
		</s:if>
		<s:if test = "#returnCode == 4">
		alertMsg += "阶段金额不等于外协合同金额！";
		</s:if>
		<s:if test = "#returnCode == 5">
			alertMsg += "外协合同的金额总额大于项目金额！";
		</s:if>
		
	alert(alertMsg);
	</s:if>	
</script>
</html>
