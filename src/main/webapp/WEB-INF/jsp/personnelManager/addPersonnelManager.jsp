<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>员工信息管理</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	//验证通过表单提交
	function doSave(){
	if(!validate()){
		document.forms(0).submit();
	}
	}
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(personnelManager){
		       ev2.test("notblank","用户名为空,请输入用户名!",$('per.username').value);
		       ev2.test("notblank","密码为空,请输入密码!",$('password').value);
		       ev2.test("notblank","工号为空,请输入工号!",$('per.workId').value);
		        ev2.test("notblank","姓名为空,请输入姓名!",$('per.name').value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	
	function getUserNameOfName(userName){
		ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetUserName&userNameStr="+userName,document.getElementById("cbs.linkManId"),"id","name");
	}
	
</script>
<body style="position: relative;">
<s:form action="personnelManager" theme="simple">
<s:hidden name="method" value="savePer"></s:hidden>
<s:hidden name="username" value=""></s:hidden>
<s:hidden name="isRelationLinkMan" value="false"></s:hidden>

<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center" >
	<tr> 
		<td valign="top" > 
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
			  <td height="3" align="left" >当前页面：基础管理->客户管理</td>
		  </tr>
			<tr><td class="bg_table01" height="1"><img src="../../images/temp.gif" width="1" height="1">
			
			</td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr align="center">
			  <td width="15%" align="right" nowrap class="bg_table02"><font color="red">* </font>用户名：</td>
			  <td width="35%" align="left" nowrap class="bg_table02">
			  	<s:textfield cssClass="textinput" name="per.username" size="20" id="userName"  />
			  	<s:hidden name="userName"></s:hidden>
			  </td>
			  <td align="right" nowrap class="bg_table02"><font color="red">* </font>密码：</td>
			  <td align="left" nowrap class="bg_table02"><s:password  name="password" size="22" />             </td>
		  </tr>
			<tr align="center">
			  <td width="15%" align="right" nowrap class="bg_table02"><font color="red">* </font>工号：</td>
			  <td width="35%" align="left" nowrap class="bg_table02"><s:textfield cssClass="textinput" name="per.workId" size="20" /></td>
			  <td align="right" nowrap class="bg_table02"><font color="red">* </font>姓名：</td>
			  <td align="left" nowrap class="bg_table02"><s:textfield cssClass="textinput" name="per.name" size="20"/>             </td>
		  </tr>
			<tr align="center">
			  <td align="right" nowrap class="bg_table02">手机：</td>
			  <td align="left" nowrap class="bg_table02"><s:textfield cssClass="textinput" name="per.callPhone" size="20"/>              </td>
              <td align="right" nowrap class="bg_table02">email：</td>
              <td align="left" nowrap class="bg_table02"><s:textfield cssClass="textinput" name="per.email" size="20"/></td>
		  </tr>
		  
			<tr align="center">
			  <td align="right" nowrap class="bg_table02">固定电话：</td>
			  <td align="left" nowrap class="bg_table02"><s:textfield cssClass="textinput" name="per.phone" size="20"/></td>
			  <td align="right" nowrap class="bg_table02">职责：</td>
			  <td align="left" nowrap class="bg_table02">
			  <s:select name="per.position" list="dutyList" listKey="id" listValue="organizationName" required="true"
							headerValue="">
				</s:select>
			  </td>
		  </tr>
		  
			<tr align="center">
			  <td align="right" nowrap class="bg_table02">其他联系方式：</td>
			  <td align="left" nowrap class="bg_table02"><s:textfield cssClass="textinput" name="per.otherPhone" size="20"/></td>
			  <td align="right" nowrap class="bg_table02">&nbsp;</td>
		      <td align="left" nowrap class="bg_table02">&nbsp;</td>
		  </tr>
	
			<tr align="center">
 			<td colspan="6" align="left" nowrap class="bg_table02">	<hr></td>
        </tr>
</table>
<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
		<table width="100%">
			<tr class="bg_table03" align="center" style="height:42px">
				<td colspan="4" nowrap>
				<Table style="width:0%;100%">
					<tfoot class="bg_table03" style="height:42px">
						<tr>
							<td align="right" colspan="2">
							<input class="button01" type="button" name="savePer" onclick="doSave()" value="保存" >	
							<input class="button01" type="submit" onclick="document.forms(0).isRelationLinkMan.value = 'true'" name="savePer" value="保存并关联客户" >	
						   <input class="button01" type="button" name="Input" value="返  回" onClick="javascript:history.go(-1);">
						</td>
					</tr>
					</tfoot>
				</Table>				
			</td>
			</tr>
		</table>
	  </td>
	</tr>
</table>
</s:form>
</body>
</html>