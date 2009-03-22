<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title><s:if test="typeManageList[0].id!=null">修改自有产品管理</s:if><s:else>新增自有产品管理</s:else></title>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<body>
<s:form action="selfProduction" theme="simple">
	<s:hidden name="method" value="savePro" />
	<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
	<br>
	<div align="left" style="color:#000000">当前页面：自有产品管理-&gt;新增自有产品信息</div>

			<table width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
					<td align="right" colspan="4" class="bg_table01" height="0.5"><img src="../../images/temp.gif" width="1" height="3"></td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right"><font color="red">* </font>产品名称：</td>
					<td class="bg_table02" align="left"><s:textfield
						name="sp.registerName" onblur="isExist('/yx/system/selfProduction.action','isExist',$('sp.registerName').value);"></s:textfield></td>
					<td class="bg_table02" align="right"><font color="red">* </font>证书标号：</td>
					<td class="bg_table02" align="left"><s:textfield
						name="sp.certificateNum"></s:textfield></td>
				</tr>
				<tr align="center">	
					<td class="bg_table02" align="right"><font color="red">* </font>登记日期：</td>
					<td class="bg_table02" align="left">
					<div align="left"><input type="text" id="bidDate1"
						name="sp.registerDate" nly"
						 size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate1')" align=absMiddle
						alt="" /></div>
					</td>
					<td class="bg_table02" align="right"><font color="red">* </font>有效期：</td>
					<td class="bg_table02" align="left">
					<div align="left"><input type="text" id="bidDate2"
						name="sp.validateTime" 
						 size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate2')" align=absMiddle
						alt="" /></div>
					</td>
				</tr>
				<tr class="bg_table03" align="center" style="height: 42px">
					<td colspan="4">
						<input class="button01" type="button" name="" value="保  存" onclick="return check()"/>
						<input name="save" type="button" class="button01" onClick="window.close();" value="关   闭">
		</Table>

</s:form>

<script type="text/javascript">
function isExist(url, method, name){
   var ev=new Validator();   

       var myRequest = new Request({url:url,async:false,method:'get'});
	   myRequest.addEvent("onSuccess",function(responseText, responseXML){
		  if(responseText =='1'){
		  	 $('sp.registerName').value="";
		     ev.addError("该产品名称已经存在，请重新输入一个产品名称！！！");
		  } 
	    });
	   myRequest.send("method="+method+"&sp.registerName="+name+"&randomNum="+Math.random());
 
    ev.writeErrors(errorsFrame, "errorsFrame");   

}

function check(){	
	if(!validate()){
		document.forms(0).submit();
	}
	return false;
}

function validate(){
       var ev2=new Validator();
       with(selfProduction){  
       ev2.test("notblank","产品名称不能为空",$('sp.registerName').value);       
       ev2.test("notblank","证书标号不能为空",$('sp.certificateNum').value);
       ev2.test("notblank","登记日期不能为空",$('sp.registerDate').value);
       ev2.test("notblank","有效期不能为空",$('sp.validateTime').value);
       ev2.test("dateYX","登记日期格式不正确！",$('sp.registerDate').value);
       ev2.test("dateYX","有效期格式不正确！",$('sp.validateTime').value);
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

