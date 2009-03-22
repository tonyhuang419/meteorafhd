<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协合同付款申请修改</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
	function confirmOk(){
		if(!validate()){
			document.assistance.method.value = "confirmPass";
			document.assistance.submit();
		}
	}
	
	function validate(){
		 var ev=new Validator(); 
		 ev.test("notblank","请填写外协合同号",$('ac.assistanceId').value);
		 var url ="/yx/assistance/assistance.action";
		 var method = "checkAssistanceNoExists";
		 var no = $('ac.assistanceId').value;
		 var asContractId = $('asContractId').value;
		 var flag = isExists(url,method,no,asContractId);
		 if(flag){
		 	ev.addError("合同号重复！");
		 }
		 ev.writeErrors(errorFrame,"errorFrame");
		 if(ev.size()>0){
		 	return true;
		 }
		 return false;
	}
	
	function confirmReturn(){
		document.assistance.method.value = "confirmReturn";
		document.assistance.submit();
	}
	 function isExists(url, method, no,asContractId){
	      var flag = false;
	      var myRequest = new Request({url:url,async:false,method:'get'});
		  myRequest.addEvent("onSuccess",function(responseText, responseXML){
			  if(responseText =='true'){
				  	flag = true;
			   }else{
				  	flag = false;
			   }
		  });
		myRequest.send("method="+method+"&assistanceNo="+no+"&asContractId="+asContractId+"&randomNum="+Math.random()); 
		return flag;
	}
</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.STYLE1 {
	font-size: 16px;
	color: #000000;
}
.STYLE2 {
	font-size: 14px
}
-->
</style>
</head>
<body leftmargin="0">
<div align="left" style="color: #FF0000"><iframe
		name="errorFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:form theme="simple" action="assistance">
	<s:hidden name="method" value=""></s:hidden>
	<s:hidden name="asContractId"></s:hidden>
	<table width="100%" border=0  align="center" cellpadding="1" cellspacing=1 >
    <tr>
    <td  colspan="2" align="right" class="bg_table01"  height="0.5"><img src="../../images/temp.gif" width="1" height="1"></td>
  </tr>

  <tr>
    <td colspan="2" class="bg_table02" align="center" >合同号：
   <s:textfield name="ac.assistanceId"></s:textfield></td>
  </tr>
    <tr>
    <td colspan="2" align="center" class="bg_table04">
      <input value="确认" type="button" onclick="confirmOk();" class="button01" />   
      <input value="取消" type="button" onclick="confirmReturn();" class="button01" /> 
    </td>
      
  </tr>
</table>
</s:form>
</body>
<script type="text/javascript">
	<s:if test = "#sign == 1 ">
		alert("合同号已经存在！");
	</s:if>
</script>
</html>
