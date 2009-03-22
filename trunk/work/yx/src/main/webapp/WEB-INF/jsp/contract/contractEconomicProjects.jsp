<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<script type="text/javascript" src="<s:url value="/commons/scripts/formal_con_manage.js"/>" ></script>
<html>
<head>
<title>合同工程经济</title>
<script language="javascript">
window.onload=function(){
		showtable("conPE");
}
</script>
<style type="text/css">
<!--

-->
</style>
</head>
<body>
<s:form action="contractLeftPage" name="form1" theme="simple">
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
    <tr><td>当前页面：合同管理->合同工程经济</td></tr>
    <tr><td align="right" class="bg_table01"  height="3"><img src="./../images/temp.gif" width="1" height="1"></td></tr>
    <tr><td align="right" class="bg_table03"><div align="center" >
         <input type="button" name="save3232" value="开工报告确认" onClick="bbb()" class="button01">
         <input type="button" name="save" value="实物交接确认" onClick="test();" class="button01">
          <input type="button" name="save2" value="竣工验收证书确认" onClick="test1();" class="button02">
     </div></td></tr>    
</table>
      <s:hidden  name="completion"></s:hidden>
       <s:hidden  name="start"></s:hidden>
        <s:hidden  name="physical"></s:hidden>
<table width="100%" border="1" align="center" id="conPE"  bordercolor="#808080" style=" border-collapse: collapse;">
       <tr>
          <td  class="bg_table01"><div align="center">&nbsp;</div></td>
          <td nowrap class="bg_table01"><div align="center">合同号</div></td>
          <td width="150"  class="bg_table01"><div align="center">合同名称</div></td>
          <td width="100"  class="bg_table01"><div align="center">客户名称</div></td>
          <td nowrap class="bg_table01" ><div align="center">销售员</div></td>
          <td nowrap class="bg_table01" ><div align="center">开工报告</div></td>
          <td nowrap class="bg_table01" ><div align="center">实物交接</div></td>
          <td nowrap class="bg_table01" ><div align="center">竣工验收</div></td>
          <td width="100"  class="bg_table01" ><div align="center">合同性质</div></td>
          <td nowrap class="bg_table01"  ><div align="center">合同金额</div></td>
        </tr>
      <s:iterator value="info.result" id="infoId">
        <tr>  
          <td ><div align="center"><input type="radio" name="operation2"  value="<s:property value="#infoId[3].contractMainSid"/>"></div></td>
          <td onClick="openConSure3(this);" ><div align="left"><s:property value="#infoId[0].conId"/></div></td>
          <td onClick="openConSure3(this);" ><div align="left"><s:property value="#infoId[0].conName"/></div></td>
          <td onClick="openConSure3(this);" ><div align="left"><s:property value="#infoId[1].name"/></div></td>
          <td onClick="openConSure3(this);" ><div align="left"><s:property value="#infoId[2].name"/></div></td>
          <td onClick="openConSure3(this);" ><div align="center">
          <s:if test="#infoId[3].needPerativeReport == true">
          	<s:if test="#infoId[3].perativeReport==null">未确认 <s:hidden id="perativeReport%{#infoId[3].contractMainSid}" value="1"></s:hidden></s:if>
          	<s:else><s:property value="#infoId[3].perativeReport"/><s:hidden id="perativeReport%{#infoId[3].contractMainSid}" value="2"></s:hidden></s:else>
          </s:if>
          <s:else>
          	不需要 <s:hidden id="perativeReport%{#infoId[3].contractMainSid}" value="3"></s:hidden>
       	  </s:else>
          </div></td>    
          <td onClick="openConSure3(this);"  ><div align="center">
          <s:if test="#infoId[3].needRecivedThing == true">
          	<s:if test="#infoId[3].recivedThing==null">未确认<s:hidden id="recivedThing%{#infoId[3].contractMainSid}" value="1"></s:hidden></s:if>
          	<s:else><s:property value="#infoId[3].recivedThing"/><s:hidden id="recivedThing%{#infoId[3].contractMainSid}" value="2"></s:hidden></s:else>
          </s:if>
          <s:else>
          	不需要 <s:hidden id="recivedThing%{#infoId[3].contractMainSid}" value="3"></s:hidden>
       	  </s:else>
          </div></td>
          <td onClick="openConSure3(this);" ><div align="center">
          <s:if test="#infoId[3].needFinallyReport == true">
          	<s:if test="#infoId[3].finallyReport==null">未确认<s:hidden id="finallyReport%{#infoId[3].contractMainSid}" value="1"></s:hidden></s:if>
          	<s:else><s:property value="#infoId[3].finallyReport"/><s:hidden id="finallyReport%{#infoId[3].contractMainSid}" value="2"></s:hidden></s:else>
          </s:if>
          <s:else>
          	不需要  <s:hidden id="finallyReport%{#infoId[3].contractMainSid}" value="3"></s:hidden>
       	  </s:else>
       	  </div>
          </td>
          <td onClick="openConSure3(this);"  ><div align="left"><s:property value="typeManageService.getYXTypeManage(1019,#infoId[0].conType).typeName"/></div></td>
          <td onClick="openConSure3(this);" ><div align="right"><s:property value="#infoId[0].conTaxTamount"/></div></td>
        </tr>
      </s:iterator>
</table>
<table cellSpacing="1" cellPadding="2" width="100%" border="0">
	<tr valign="top">
		<td class="bg_table04">
		<baozi:pages value="info" beanName="info" formName="forms(0)" /></td>
	</tr>
</table>
</s:form>

<script language="javascript">
 function bbb(){
	   var checkArr=document.getElementsByName("operation2");
	   var checkStr="";
	   var j=0;
       var contractMainId = "";
	   for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	             j++;    
	             checkStr=checkStr+","+checkArr[i].value;
	              contractMainId = checkArr[i].value;
	        }
	   }
	   if(j==0){
	        alert("您还没有选择需要操作的对象！");
	        return;
	   }
	   if(j>1){
	     alert("不能选择多个操作对象！");
	     return;
	   }
      if(j==1)
      {  
	      var state = document.getElementById("perativeReport"+contractMainId).value;
	      if(state == "1"){
	         window.open("contractSeconomicprojects.action?method=relationTicket&contract="+checkStr.substring(1),"newWindow","scrollbars=yes,location=no,toolbar=no,width=450,height=230,left=430,top=390");
	      	return;
	      }else if(state == "2"){
	      	alert("开工报告已经确认！");
	      	return;
	      }else{
	      	alert("开工报告不需要确认！");
	      	return;
	      }
	  }
 } 
    
    function test()
    {
  	  var checkArr=document.getElementsByName("operation2");
  	  var checkStr="";
  	  var j=0;
  	  var k=0;
  	   var contractMainId = "";
      for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
              contractMainId = checkArr[i].value;
        }
      }
	  if(j==0){
	        alert("您还没有选择需要操作的对象！");
	        return;
	   }
	   if(j>1){
	     alert("不能选择多个操作对象！");
	     return;
	   }
	   
  	  for( var  i = 0;i<checkArr.length;i++){
        if(checkArr[i].checked){
           k = i;
       	 }
   	  }
   	  
   	 if(j==1)
      { 
   		   var reState = document.getElementById("recivedThing"+contractMainId).value;
   		   if(reState == "1"){
   		   		var peState = document.getElementById("perativeReport"+contractMainId).value;
   		   		if(peState == "2" || peState == "3"){
   		   		   	window.open("contractSeconomicprojects.action?method=relationTicketone&contract="+checkStr.substring(1),"newWindow","scrollbars=yes,location=no,toolbar=no,width=450,height=230,left=430,top=390");
   		   		}else{
   		   			alert("开工报告未确认！");
   		   		}
			}
			else if(reState == "2")
			{
			  alert("实物交接已经确认！");
			}else{
			  alert("实物交接不需要确认！");
			}
     }
 }
   function test1()
   {    
	  	 var checkArr=document.getElementsByName("operation2");
	  	 var checkStr="";
	  	 var j=0;
	  	 var k=0;
	  	 var contractMainId = "";
	     for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	             j++;    
	             checkStr=checkStr+","+checkArr[i].value;
	             contractMainId = checkArr[i].value;
	        }
	     }
		   if(j==0){
		        alert("您还没有选择需要操作的对象！");
		        return;
		   }
		   if(j>1){
		     alert("不能选择多个操作对象！");
		     return;
		   }
	  	 for( var  i = 0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	           k = i;
	       	 }
	   	 }
	   	 if(j==1)
	     { 
			var fiState = document.getElementById("finallyReport"+contractMainId).value;
	   		 if(fiState == "1"){
	   		 	var peState = document.getElementById("perativeReport"+contractMainId).value;
	   		 	var reState = document.getElementById("recivedThing"+contractMainId).value;
		   		 if(reState == "2" || reState == "3"){
		   		 	if(reState == "3"){
		   		 		if(peState == "2" || peState == "3"){
		   		 			window.open("contractSeconomicprojects.action?method=relationTickettwo&contract="+checkStr.substring(1),"newWindow","scrollbars=yes,location=no,toolbar=no,width=450,height=230,left=430,top=390");
		   		 		}else{
		   		 			alert("开工报告未确认！");
		   		 		}
		   		 	}else{
		   		 		window.open("contractSeconomicprojects.action?method=relationTickettwo&contract="+checkStr.substring(1),"newWindow","scrollbars=yes,location=no,toolbar=no,width=450,height=230,left=430,top=390");
				 	}
				 }
				 else
				 {
				   alert("实物交接未确认！");
				 }
			 }
			else if(fiState == "2")
			{
			  alert("竣工验收证书已经确认！");
			}else{
			  alert("竣工验收证书不需要确认！");
			}
	    }
}
    function refreshClient()
    {
    	document.forms(0).submit();
    }

</script>

</body>
</html>
