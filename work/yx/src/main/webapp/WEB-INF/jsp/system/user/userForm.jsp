<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>宝资HR信息系统</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="<s:url value="/commons/scripts/mootools-release-1[1].11.js"/>" type="text/javascript"></script>
</head>
<body style="position: relative;">
<s:form name="userForm" action="user.action" theme="simple">
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td valign="top">
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td height="3" align="left">当前页面:权限管理->员工管理</td>
				</tr>
				<tr>
					<td class="bg_table01" height="1"><img src="../images/temp.gif" width="1" height="1"> <iframe name="errorsFrame" frameborder="0"
						framespacing="0" height="0" width="100%" scrolling="no"></iframe></td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr align="center">
					<td width="15%" align="right" nowrap class="bg_table02">用户名：</td>
					<td width="35%" align="left" nowrap class="bg_table02"><input id="user_user_username" type="text" name="user.username" value="<s:property value='user.username'/>"
						onchange="isExistUsername('<s:property value="user.username"/>');" /></td>
					<td align="right" nowrap class="bg_table02">密码：</td>
					<td align="left" nowrap class="bg_table02"><s:password name="password" /></td>
				</tr>
				<tr align="center">
					<td width="15%" align="right" nowrap class="bg_table02">工号：</td>
					<td width="35%" align="left" nowrap class="bg_table02"><input id="user_user_userCode" type="text" name="user.userCode" value="<s:property value='user.userCode'/>"
						onchange="isExistUserCode('<s:property value="user.userCode"/>');" /></td>
					<td align="right" nowrap class="bg_table02">姓名：</td>
					<td align="left" nowrap class="bg_table02"><s:textfield name="user.name" /></td>
				</tr>
				<tr align="center">
					<td align="right" nowrap class="bg_table02">手机：</td>
					<td align="left" nowrap class="bg_table02"><s:textfield name="user.callPhone" /></td>
					<td align="right" nowrap class="bg_table02">email：</td>
					<td align="left" nowrap class="bg_table02"><s:textfield name="user.email" /></td>
				</tr>

				<tr align="center">
					<td align="right" nowrap class="bg_table02">固定电话：</td>
					<td align="left" nowrap class="bg_table02"><s:textfield name="user.phone" /></td>
					<td align="right" nowrap class="bg_table02">职责：</td>
					<td align="left" nowrap class="bg_table02"><s:select name="user.position" list="typeManageList" listKey="id" listValue="typeName"
						required="true" key="<s:property value='user.position'/>" /></td>
				</tr>
				<tr align="center">
					<td align="right" nowrap class="bg_table02">其他联系方式：</td>
					<td align="left" nowrap class="bg_table02"><s:textfield name="user.otherPhone" /></td>
				</tr>
				<tr align="center">
					<td colspan="6" align="left" nowrap class="bg_table02">
					<hr>
					</td>
				</tr>
			</table>
			<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
				<tr>
					<td colspan="2" align="right" nowrap class="bg_table02" clspan="7">
					<div align="center" class="STYLE1">
					<div align="left">已关联客户</div>
					</div>
					</td>
					<td colspan="3" align="left" nowrap class="bg_table02"></td>
				</tr>

				<tr align="center">
					<td width="13%" class="bg_table01" align="left">编号</td>
					<td width="28%" class="bg_table01" align="left">客户名称</td>
					<td width="17%" class="bg_table01" align="left">客户性质</td>
					<td width="18%" class="bg_table01" align="left">行业类型</td>
					<td width="24%" class="bg_table01" align="left">操作</td>
				</tr>
				<s:iterator value="userclientList" status="status">
					<tr>
						<td class="bg_table02">
						<div align="left"><s:property value="cli.id" /></div>
						</td>
						<td class="bg_table02">
						<div align="left"><s:property value="cli.name" /></div>
						</td>
						<td class="bg_table02">
						<div align="left"><s:property value="clientNameList[#status.index]" /></div>
						</td>
						<td class="bg_table02">
						<div align="left"><s:property value="typeNameList[#status.index]" /></div>
						</td>
						<td class="bg_table02">
						<div align="left"><input type="submit" value="删除"
							onClick="javascript:document.forms(0).action='<s:url action="user"><s:param name="deleteExployeeClient.id"><s:property value="id"/></s:param><s:param name="method">deleteExployeeClient</s:param><s:param name="user.id"><s:property value="user.id"/></s:param></s:url>';"
							style="height: 21px;" /></div>
						</td>
					</tr>
				</s:iterator>
			</table>
			<table id="newClient" width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
				<tr>
					<td colspan="2" align="right" nowrap class="bg_table02" clspan="7">
					<div align="center" class="STYLE1">
					<div align="left">新增关联客户</div>
					</div>
					</td>
					<td colspan="3" align="left" nowrap class="bg_table02"></td>
				</tr>
				<tr align="center">
					<td width="13%" class="bg_table01" align="left">编号</td>
					<td width="28%" class="bg_table01" align="left">客户名称</td>
					<td width="17%" class="bg_table01" align="left">客户性质</td>
					<td width="18%" class="bg_table01" align="left">行业类型</td>
					<td width="24%" class="bg_table01" align="left">操作</td>
				</tr>
			</table>
			<table width="100%">
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4" nowrap>
					<Table style="width: 0%;">
						<tfoot class="bg_table03" style="height: 42px">
							<tr>
								<td align="right" colspan="2"><input type='button' class='button01' value='关联客户'
									onClick="openUrl('<s:url action="user.action"><s:param name="method">doSelect</s:param><s:param name="user.id"><s:property value="user.id"/></s:param></s:url>');"></td>
								<td align="right" colspan="2"><input class="button01" type="button" name="" value="保  存"
									onClick="doSave('<s:url action="user.action"><s:param name="method">saveorUpdate</s:param><s:param name="user.id"><s:property value="user.id"/></s:param></s:url>');"></td>

								<td colspan="2"><input class="button01" type="button" name="Input" value="关  闭"
									onClick="javascript:openUrl('../chufeng/合同管理/合同修改.html');">
							</tr>
						</tfoot>
					</Table>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
var newClient=document.getElementById("newClient");  
function wirte(id,name,typeBigName,typeSmallName){     
     mtr=newClient.insertRow();
     mtr.insertCell().innerHTML="<input type='hidden' name='newid' value='"+id.value+"'/>"+id.value;
     mtr.insertCell().innerText=name.value;
     mtr.insertCell().innerText=typeBigName.value;
     mtr.insertCell().innerText=typeSmallName.value;
     mtr.insertCell().innerHTML="<input type='button' value='删除' onclick='delTableRow(this);'/>";
}
function delTableRow(td){
      newClient.deleteRow(td.parentNode.parentNode.rowIndex);  
      var newid=window.document.userForm.newid;
 }
function getClientCode(id,name,typeBigName,typeSmallName){
  for(j=0;j<id.length;j++){
    wirte(id[j],name[j],typeBigName[j],typeSmallName[j]);
  }	
}

function doSave(url){
	if(!validate()){
	document.forms(0).action=url;
	document.forms(0).submit();
	}
}
function isExist(url, method,username,name,message){   
   var ev=new Validator();   
     if(name!=username){   
      var myAjax = new Ajax(url, {
		method : 'get',
		data : Object.toQueryString({
			method : method,
			param : username
		}),
		onComplete : function(obj) {
		  if(obj=='1'){		    
		     ev.addError(message);		     
		  } 	
		  ev.writeErrors(errorsFrame, "errorsFrame");	      
		}
	}).request();
	}else{
	ev.writeErrors(errorsFrame, "errorsFrame");
	}	
}
function isExistUsername(name){
var username=window.document.userForm.user_user_username.value;
isExist('/yx/system/user.action','isExistUsername',username,name,'该用户名已经存在，请重新输入一个新的用户名！！！');
}
function isExistUserCode(code){
var usercode=window.document.userForm.user_user_userCode.value;
isExist('/yx/system/user.action','isExistUserCode',usercode,code,'该工号已经存在，请重新输入一个新的工号！！！');
}
function validate(){
var ev2=new Validator();
with(userForm){
       ev2.test("notblank","用户名不能为空",window.document.userForm.user_user_username.value);
       ev2.test("notblank","工号不能为空",window.document.userForm.user_user_userCode.value);
       ev2.test("notblank","姓名不能为空",window.document.userForm.user_user_name.value);
   }  
  if (ev2.size() > 0) {
     ev2.writeErrors(errorsFrame, "errorsFrame");
     return true;
 }
 return false;
}


</script>
</s:form>
</body>
</html>