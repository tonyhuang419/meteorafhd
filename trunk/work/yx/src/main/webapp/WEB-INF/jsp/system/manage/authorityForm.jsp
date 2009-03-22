<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src='..//js/function.js'></script>
<script type="text/javascript" src='..//js/page.js'></script>
<script type="text/javascript" src='..//js/jquery.js'></script>
<script type="text/javascript" src='..//js/jquery.tablesorter.js'></script>
<script type="text/javascript" src='..//js/sorttable.js'></script>
<script type="text/javascript" src='..//js/YS_checkboxHelper.js'></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<title>新增权限信息</title>

</head>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" class="bg_img01">
<s:form action="authority" method="POST" theme="simple">
	<s:hidden name="method" value="saveAuthority" />
	<s:hidden name="authority.id" />
	<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
	<TABLE class=bg_white cellSpacing=1 cellPadding=0 width=780 align=center border=0>
		<TR>
			<TD align=center>
			
				<table align="center" id="table01" width="96%">
					<tr>
						<td width="7"><img src="images/title_conner01.gif" width="7" height="28" /></td>
						<td><s:if test="authority.id == null">新增权限信息</s:if><s:else>修改权限信息</s:else></td>
						<td width="7"><img src="images/title_conner02.gif" width="7" height="28" /></td>
					</tr>
				</table>
				
				<TABLE cellSpacing="1" borderColorDark="#ffffff" cellPadding="2" width="98%" align="center" borderColorLight="#cccccc" border="0">
					<tr>
						<td width="35%" align="right" class="bg_table02">
							权限名称<font color=red>*</font>:
						</td>
						<td width="65%" class="txtinput">
							<s:textfield name="authority.authorityName" id="a"/>
						</td>
					</tr>
					<tr style="display:none">
						<td align="right" class="bg_table02">
							父权限:
						</td>
						<td class="txtinput">
							<s:select name="authority.fid" list="authorityList" emptyOption="true"
								listKey="id" listValue="authorityName +'-'+ code" required="true" />
						</td>
					</tr>
					
					<tr>
						<td align="right" class="bg_table02">
							权限代码<font color=red>*</font>:
						</td>
						<td class="txtinput">
							<s:textfield name="authority.code" id="b"/>
						</td>
					</tr>
					<tr>
						<td align="right" class="bg_table02">
							ACTION:
						</td>
						<td class="txtinput">
							<s:textfield name="authority.action" size="40" />
						</td>
					</tr>
					<tr>
						<td align="right" class="bg_table02">
							METHOD:
						</td>
						<td class="txtinput">
							<s:textfield name="authority.method" />
						</td>
					</tr>
					
					<tr>
						<td align="right" class="bg_table02">
							TYPE<font color=red>*</font>:
						</td>
						<td class="txtinput">
							<s:select id="c" name="authority.type" list="#@java.util.HashMap@{'1':'模块', '2':'页面', '3':'按钮'}" required="true" />
						</td>
					</tr>
					
					<tr>
						<td align="right" class="bg_table02">
							权限描述:
						</td>
						<td class="txtinput">
							<s:textarea name="authority.authorityDesc" />
						</td>
					</tr>
		
					<TR align="center">
						<TD class=bg_table03 colSpan=4 height=40>
						<input type="button" name="SaveBtn" value="　确  认 " onclick="return check()" class="button02">
						<input type="button" name="backBtn" value="　返 回 " onclick="window.history.back()" class="button02">
						</TD>
					</TR>
				</TABLE>
			</TD>
		</TR>
	</TABLE>

</s:form>

</BODY>
</html>

<script languang="javascript">
<!--function doCheck()-->
<!--{-->
<!--	if(document.PrivilegeMgrForm.privilegeNo.value == ""){-->
<!--		alert("权限代码必须输入!");-->
<!--		document.PrivilegeMgrForm.privilegeNo.focus();-->
<!--		return false;-->
<!--	}-->
<!--	if(document.PrivilegeMgrForm.privilegeDesc.value == ""){-->
<!--		alert("权限描述必须输入!");-->
<!--		document.PrivilegeMgrForm.privilegeDesc.focus();-->
<!--		return false;-->
<!--	}	-->
<!--	if(document.PrivilegeMgrForm.menuName.value == ""){-->
<!--		alert("菜单必须选择!");-->
<!--		document.PrivilegeMgrForm.menuName.focus();-->
<!--		return false;-->
<!--	}-->
<!--}-->
<!---->
<!--function popInfo(respInfo){-->
<!--	if(respInfo=="新增成功!"){-->
<!--		alert(respInfo);-->
<!--		opener.location = opener.location;-->
<!--		window.close();-->
<!--	}else{-->
<!--		alert(respInfo);-->
<!--	}-->
<!--}-->
<!---->
<!--function setMenuInfo(menuId, menuName){-->
<!--	document.PrivilegeMgrForm.menuName.value = menuName;-->
<!--	document.PrivilegeMgrForm.menuId.value = menuId;-->
<!--}-->
function check(){	
	if(!validate()){
		document.forms(0).submit();
	}
	return false;
	}

function validate(){
       var ev2=new Validator();
       with(authority){  
       ev2.test("notblank","权限名称不能为空",$('authority.authorityName').value);       
       ev2.test("notblank","权限代码不能为空",$('authority.code').value);
       ev2.test("notblank","type不能为空",$('authority.type').value);
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 if($('authority.authorityDesc').value.length > 100){
		 	alert("权限描述不能超过100个字");
		 	return true;
		 }
		 return false;
}
</script>
