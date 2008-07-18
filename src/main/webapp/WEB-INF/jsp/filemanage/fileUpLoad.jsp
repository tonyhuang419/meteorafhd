<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<head>
<title>文件上传</title>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<body>
<s:form name="fileupload" action ="fileUpLoad" method ="POST" theme="simple" enctype ="multipart/form-data" > 
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<table width="98%" border="0" cellspacing="1" cellpadding="1"
	class="bg_white" align="center">
	<tr>
		<td align="center">
		<table align="center" border=0 cellpadding=1 cellspacing=1
			width="100%">
			<tr>
				<td colspan="3" align="left">当前页面：文件管理->文件上传</td>
			</tr>
			<tr>
				<td colspan="3" align="right" class="bg_table01" height="0.5"><img
					src="./../images/temp.gif" width="1" height="1"></td>
			</tr>
			<td colspan="3" align="right" class="bg_table03">&nbsp;</td>
			<tr class="bg_table02">
				<td width="17%" align="right" class="bg_table02">
				<div align="right"><font color="red">* </font>请选择文件：</div>
				</td>
				<td align="right" class="bg_table02">
				<div align="left"><s:file name="myFile" onchange="fillFileName(this.value)"/></div>
				</td>
			</tr>
			<tr class="bg_table02">
				<td align="right">文件类型：</td>
				<td align="left"><s:select name="fm.filetype" 
					list="fileTypeList" listKey="typeSmall" listValue="typeName"
					required="true" headerValue="">
				</s:select></td>
			</tr>
			<tr class="bg_table02">
				<td align="right">所属客户：</td>
				<td class="bg_table02"  align="left" >
					<input type="text" id="clientName" style="width:150px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select name="fm.clientcode" list="clientList" cssStyle="margin-left:-150px;width:168px;" listKey="id" listValue="name" onchange="clientChange(this)"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
					<s:hidden name="cbs.customerId" id="clientId"></s:hidden>
					</td>
			</tr>
			<tr class="bg_table02">
				<td align="right"><font color="red">* </font>文件名：</td>
				<td align="left"><s:textfield size="40" maxlength="50" name="fm.filename"/>
				</td>
			</tr>
			<tr class="bg_table02">
				<td align="right" class="bg_table02">文件说明：</td>
				<td align="right" class="bg_table02">
				<div align="left"><textarea name="fm.remarks" cols="40" rows="5"></textarea></div>
				</td>
			</tr>
			<tr align="center">
				<td colspan="3" class="bg_table02">&nbsp;</td>
			</tr>
			<tr align="center">
				<td colspan="3" class="bg_table03"><s:submit value="上传" onclick="return check()"/></td>
			</tr>
		</table>
</table>
<script language="javascript">
if('<s:property value="#parameters.isSuccess"/>' == 'true'){
	alert("文件已上传成功！");
}
	
function check(){	
	if(!validate()){
		document.forms(0).submit();
	}
	return false;
}

function validate(){
       var ev2=new Validator();
       with(fileUpLoad){  
       ev2.test("notblank","上传文件不能为空",$('myFile').value);       
       ev2.test("notblank","文件名不能为空",$('fm.filename').value);
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 if($('fm.remarks').value.length > 200){
		 	alert("文件说明最多只能输入200个字");
		 	return true;
		 }
		 return false;
}
function clientChange(selObj){
		document.getElementById('clientName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
		getLinkMainOfClient(selObj.value);
	}
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
	
function fillFileName(filePath){
	if(filePath != null){
		var lastNameIndex = filePath.lastIndexOf('\\');
		if(lastNameIndex == -1){
			lastNameIndex = filePath.lastIndexOf('/');
		}
		if(lastNameIndex != -1){
			$('fm.filename').value = filePath.substring(lastNameIndex+1);
		}
	}
}
</script>
</s:form>
<p>&nbsp;</p>
<p>&nbsp;</p>
</body>
</html>