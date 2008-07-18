<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="<s:url value="/commons/scripts/mootools-release-1[1].11.js"/>" type="text/javascript"></script>
<title><s:if test="typeManageList[0].id!=null">修改类型管理</s:if><s:else>新增类型管理</s:else></title>
</head>
<body>
<s:form action="typeManage.action" theme="simple">
	<s:hidden name="method" value="saveorUpdate" />
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td>当前页面:基础管理-><s:if test="typeManageList[0].id!=null">修改类型管理</s:if><s:else>新增类型管理</s:else></td>
		</tr>
		<tr>
			<td class="bg_table01" height="1"><img src="../images/temp.gif" width="1" height="1"> <iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe></td>
		</tr>
		<tr>
			<td align="center">
			<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
				<tr>
					<td align="right" class="bg_table01" height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr>
					<td align="right" class="bg_table02"><s:iterator value="typeManageList" status="status">
						<input type="hidden" name="orgTypeBig" value="<s:property value='typeManageList[#status.index].typeBig'/>"/>
						<input type="hidden" name="orgTypeSmall" value="<s:property value='typeManageList[#status.index].typeSmall'/>"/>
						<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
							<tr align="center">
								<td class="bg_table02" align="right">类型种类<input type="hidden" name="ids" value="<s:property value='typeManageList[#status.index].id'/>" /></td>
								<td class="bg_table02" align="left"><select name="typeBig" value="<s:property value='typeManageList[#status.index].typeBig'/>"
									onchange="isExist('/yx/system/typeManage.action','isExist','<s:property value="typeManageList[#status.index].typeBig"/>','<s:property value="typeManageList[#status.index].typeSmall"/>',$('typeBig').value,$('typeSmall').value);">
									<s:iterator value="type">
										<s:if test="key!=typeManageList[#status.index].typeBig">
											<option value="<s:property value='key'/>"><s:property value="value" /></option>
										</s:if>
										<s:else>
											<option value="<s:property value='key'/>" selected><s:property value="value" /></option>
										</s:else>
									</s:iterator>
								</select></td>
							</tr>
							<tr align="center">
								<td class="bg_table02" align="right"><font color="red">* </font>编号</td>
								<td class="bg_table02" align="left"><input type="text" name="typeSmall" 
									value="<s:property value='typeManageList[#status.index].typeSmall'/>"
									onchange="isExist('/yx/system/typeManage.action','isExist','<s:property value="typeManageList[#status.index].typeBig"/>','<s:property value="typeManageList[#status.index].typeSmall"/>',$('typeBig').value,$('typeSmall').value);" /></td>
							</tr>
							<tr align="center">
								<td class="bg_table02" align="right"><font color="red">* </font>类型名称</td>
								<td class="bg_table02" align="left"><input type="text" name="typeName"
									value="<s:property value='typeManageList[#status.index].typeName'/>" /></td>
							</tr>
							<tr align="center">
								<td class="bg_table02" align="right">其他信息</td>
								<td class="bg_table02" align="left"><input type="text" name="info" value="<s:property value='typeManageList[#status.index].info'/>" /></td>
							</tr>
							<tr align="center">
								<td class="bg_table02" align="right">排列顺序</td>
								<td class="bg_table02" align="left"><input size="5" type="text" name="orderNum" value="<s:property value='typeManageList[#status.index].orderNum'/>" /></td>
							</tr>
						</table>
						<br>
						<br>
						<br>
						<br>
					</s:iterator></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2" class="bg_table03"><s:if test="typeManageList[0].id!=null">
				<input type="button" class="button02" value="修    改" onClick="doSave();" />
			</s:if><s:else>
				<input type="button" class="button02" value="保    存" onClick="doSave();" />
			</s:else><input type="button" value="返    回" class="button02" onclick="javascript:history.go(-1)"/></td>
		</tr>
	</table>
</s:form>
<script type="text/javascript">
function isExist(url, method,typeBig,typeSmall,big,small){
   var ev=new Validator();   
   //编号用字符型
   //ev.test("+integer","编号只能为数字",$('typeSmall').value);
   if (ev.size()<1) {
      if(big!=typeBig||small!=typeSmall){ 
      var myAjax = new Ajax(url, {
      	async: false,
		method : 'get',
		data : Object.toQueryString({
			method : method,
			big : big,
			small : small,
			randomNum :Math.random()
		}),
		onComplete : function(obj) {
		  if(obj=='1'){
		  	 document.forms(0).typeSmall.value="";
		     ev.addError("该类型对应的编号已经存在，请重新输入一个编号！！！");
		  } 
		  ev.writeErrors(errorsFrame, "errorsFrame");            
		}
	}).request();
      }          
   }     
    if(ev.size() > 0){
    	return true;
    }else{
    	return false;
    }

}
function doSave(){
	if(!validate()){
		document.forms(0).submit();
	}
}
function validate(){
var ev2=new Validator();
with(typeManage){
       ev2.test("notblank","编号不能为空",$('typeSmall').value);
       ev2.test("notblank","类型名称不能为空",$('typeName').value);
   }  
  if (ev2.size() > 0) {
     ev2.writeErrors(errorsFrame, "errorsFrame");
     return true;
 }
 
 return false;
}
</script>
</body>
</html>

