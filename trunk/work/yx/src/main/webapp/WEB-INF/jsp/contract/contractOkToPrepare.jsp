<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title></title>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
	function doSubmit(){
		if(confirm("确定要转为预合同吗?")){
			document.searchContractOkList.submit();
		}
	}
	
	function isExist(url, method, name){
	   var oldcode="<s:property value="contractId"/>";
	   if(oldcode != $('contractId').value){
		   var ev=new Validator();   
		       var myRequest = new Request({url:url,async:false,method:'get'});
			   myRequest.addEvent("onSuccess",function(responseText, responseXML){
				  if(responseText =='1'){
				  	 $('contractId').value="";
				     ev.addError("合同号已经存在，请重新输入一个编号！");
				  } 
			    });
			   myRequest.send("method="+method+"&resNubmer="+name+"&randomNum="+Math.random());
		    ev.writeErrors(errorsFrame, "errorsFrame");
	    }
	}
	
	function exit(){
		document.searchContractOkList.method.value="showExit";
		document.searchContractOkList.submit();
	}
</script>
</head>
<body leftmargin="0">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" scrolling="no"></iframe>
<s:form action="searchContractOkList" theme="simple">
	<s:hidden name="method" value="changeOrderState"></s:hidden>
	<s:hidden name="conMainId" value="%{conMainId[0]}"/>
	<s:hidden name="XMJC" value="%{#parameters.XMJC}"/>
	<div align="left">
 	  <div  style="color:#000000"><p>当前页面：合同管理 -> 转预合同</p></div>
	</div>
	<table width="100%"  border=0  cellpadding=1 cellspacing=0>
          <tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5">
            	<img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
       	<tr class="bg_table02">
			<td align="center">合同号：<s:textfield name="contractId" onblur="isExist('/yx/contract/searchContractOkList.action','isExist',$('contractId').value);"></s:textfield></td>
		</tr>  
	       <tr class="bg_table04">
			      <td align="center" nowrap> 
			      	<input class="button01" type="button" value="转预合同" onclick="doSubmit();"/> 
			      	<input class="button01" type="button" value=" 取  消 " onclick="exit();"/> 
			      </td>
	      </tr>
	 </table>
	</s:form>
</body>
</html>