<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>

<title>添加合同号</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script type="text/javascript">
var whitespace=" \t\n\r";
function isEmpty(s){
var i;
if((s==null)||(s.length==0))
   return true;
for(i=0;i<s.length;i++){
    var c=s.charAt(i);
    if(whitespace.indexOf(c)==-1)
      return false;
 }
 return true;
}

function doSubmit(){
  	if(isEmpty(document.forms(0).lresNubmer.value)){
          	alert("请输入合同号!");
         	 document.forms(0).lresNubmer.focus();
          	return false;
    }
   
    if(document.forms(0).issave.value!="1"){
    	document.itemForm.action="/yx/contract/searchContractOkJCList.action?method=doUpdateConNo";
   
    	document.itemForm.submit();
  
    	window.close();
    }
  
   }
   
   function isExist(url, method, name,mainid){
	   var ev=new Validator();   
	       var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
			  if(responseText =='1'){
			  	 $('issave').value="1";
			     ev.addError("合同号重复!");
			  }else{
			  	 $('issave').value="0";
			  }
		    });
		   myRequest.send("method="+method+"&resNubmer="+name+"&conMainId="+mainid+"&randomNum="+Math.random());
	    ev.writeErrors(errorsFrame, "errorsFrame");   
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



<form method="post" name="itemForm" target="content">

<input type="hidden" name="groupId" value="<s:property value="groupId"/>"/>
<input type="hidden" name="expId" value="<s:property value="expId"/>"/>
<input type="hidden" name="customerId" value="<s:property value="customerId"/>"/>
<input type="hidden" name="conType" value="<s:property value="conType"/>"/>
<input type="hidden" name="minMoney" value="<s:property value="minMoney"/>"/>
<input type="hidden" name="maxMoney" value="<s:property value="maxMoney"/>"/>
<input type="hidden" name="minConSignDate" value="<s:property value="minConSignDate"/>"/>
<input type="hidden" name="maxConSignDate" value="<s:property value="maxConSignDate"/>"/>
<input type="hidden" name="conState" value="<s:property value="conState"/>"/>

<input type="hidden" value="<s:property value="conMainId[0]"/>" name="conMainId">
<p>&nbsp;</p>
 <iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" scrolling="no"></iframe>
<table width="100%" border=0   cellpadding="1" cellspacing=1 >
    <tr>
    <td  colspan="2"align="right" class="bg_table01"  height="0.5"><input type="hidden" id="issave" name="issave"/><img src="../../images/temp.gif" width="1" height="1"></td>
  </tr>

  <tr>
    <td  class="bg_table02"><div align="right">合同号：    </div></td>
    <td class="bg_table02"><input name="resNubmer" value="<s:property value="resNubmer"/>" type="text" id="lresNubmer"  size="15" maxlength="20" onblur="isExist('/yx/contract/searchContractOkJCList.action','isExist',$('resNubmer').value,$('conMainId').value);"   ></td>
  </tr>
    <tr>
    <td colspan="5"  class="bg_table04">
      <div align="center">
        <input type="button" name="button" id="button" value="保  存" class="button01" onclick="doSubmit();">      
        <input type="button" name="button2" id="button2" value="关  闭" class="button01"  onclick="window.close();">
      </div></td>
  </tr>
</table>
</form>
</body>
</html>
