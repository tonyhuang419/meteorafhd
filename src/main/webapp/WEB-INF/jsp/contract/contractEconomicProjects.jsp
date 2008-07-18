<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>合同确认</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script language="javascript">
 function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
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
    if(j==1)
      {  
 if(document.getElementById("perativeReport"+contractMainId) == null){
         window.open("contractSeconomicprojects.action?method=relationTicket&contract="+checkStr.substring(1),"newWindow","scrollbars=yes,location=no,toolbar=no,width=450,height=230,left=430,top=390");
       }
       else{
			 	alert("开工报告不需要确认！");
			 }
      }
   if(j==0){
        alert("您还没有选择需要操作的对象！");
   }
   if(j>1){
    
     alert("不能选择多个操作对象！");
   }
      } 
   function ccc(){

    var checkArr=document.getElementsByName("operation2");
    var checkStr="";
    var j=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(j==1)
      {  
         window.open("contractSeconomicprojects.action?method=relationTicketone&contract="+checkStr.substring(1),"newWindow","scrollbars=yes,location=no,toolbar=no,width=450,height=230,left=430,top=390");
       
      }
   if(j==0){
        alert("您还没有选择需要操作的对象！");
   }
   if(j>1){
    
     alert("不能选择多个操作对象！");
   }
      } 
      function ddd(){
    var checkArr=document.getElementsByName("operation2");
    var checkStr="";
    var j=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(j==1)
      {  
         window.open("contractSeconomicprojects.action?method=relationTickettwo&contract="+checkStr.substring(1),"newWindow","scrollbars=yes,location=no,toolbar=no,width=450,height=230,left=430,top=390");
       
      }
   if(j==0){
        alert("您还没有选择需要操作的对象！");
   }
   if(j>1){
    
     alert("不能选择多个操作对象！");
   }
      }    
    function refreshClient()
    {
    	document.forms(0).submit();
    }
    function setId(a,b,c){
     alert(a);
     alert(b);
     alert(c);
      var hidden=document.getElementByName('aa');
      hidden.setAttribute("value",a);
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
  	  for( var  i = 0;i<checkArr.length;i++){
        if(checkArr[i].checked){
           k = i;
       	 }
   	 }
   	 if(j==1)
      { 
   	  var tablett = document.getElementById("conPE");
   	 
   		 var tr = tablett.getElementsByTagName("tr");
         if(document.getElementById("recivedThing"+contractMainId) == null){
   		 if(tr[k+1].cells[5].innerHTML.length!=4){
   		 		 window.open("contractSeconomicprojects.action?method=relationTicketone&contract="+checkStr.substring(1),"newWindow","scrollbars=yes,location=no,toolbar=no,width=450,height=230,left=430,top=390");
			}
			else
			{
			  alert("开工日期为不需要状态或是未确定状态！");
			}
			}else{
			 	alert("实物交接不需要确认！");
			 }
			}
			else if(j==0){
        alert("您还没有选择需要操作的对象！");
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
	  	 for( var  i = 0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	           k = i;
	       	 }
	   	 }
	   	 if(j==1)
	     { 
	   	     var tablett = document.getElementById("conPE");
	   	 
	   		 var tr = tablett.getElementsByTagName("tr")

	   		 if(document.getElementById("finallyReport"+contractMainId) == null){
		   		 if(tr[k+1].cells[6].innerHTML.length!=3){
		   		 		  window.open("contractSeconomicprojects.action?method=relationTickettwo&contract="+checkStr.substring(1),"newWindow","scrollbars=yes,location=no,toolbar=no,width=450,height=230,left=430,top=390");
				 }
				 else
				 {
				   alert("实物交接为不需要状态或是未确定状态！");
				 }
			 }else{
			 	alert("竣工验收证书不需要确认！");
			 }
		 }else if(j==0){
	       	alert("您还没有选择需要操作的对象！");
	 	 }
	}
</script>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 16px
}

body {
	background-color: #FFFFFF;
}

-->
</style>
</head>
<body>
<s:form action="contractLeftPage" name="form1" theme="simple">
<div>

<s:hidden name="aa"></s:hidden>
</div>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
   
    <td align="center"><table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
     <p align="left">当前页面：基础管理->客户管理</p>
        <tr>
          <td align="right" class="bg_table01"  height="3"><img src="./../images/temp.gif" width="1" height="1"></td>
        </tr>
        <td align="right" class="bg_table03 STYLE1"><div align="center" >
              <input type="button" name="save3232" value="开工报告确认" onClick="bbb()" class="button01">
              <input type="button" name="save" value="实物交接确认" onClick="test();" class="button01">
              <input type="button" name="save2" value="竣工验收证书确认" onClick="test1();" class="button02">
        </div>      
      </table>
      <s:hidden  name="completion"></s:hidden>
       <s:hidden  name="start"></s:hidden>
        <s:hidden  name="physical"></s:hidden>
      <table width="100%" border="0" align="center" id="conPE">
        <tr>
          <td width="18"  align="center" class="bg_table01"><div align="center"></div></td>
          <td width="94"  align="center" class="bg_table01"><div align="center">合同号</div></td>
          <td width="154"  align="center" class="bg_table01"><div align="center">合同名称</div></td>
          <td width="168"  align="center" class="bg_table01" ><div align="center">客户名称</div></td>
          <td width="73"  align="center" class="bg_table01" ><div align="center">销售员</div></td>
          <td width="81"  align="center" class="bg_table01" >开工报告</td>
          <td width="70"  align="center" class="bg_table01" ><div align="center">实物交接</div></td>
          <td width="72"  align="center" class="bg_table01" ><div align="center">竣工验收</div></td>
          <td width="99"  align="center" class="bg_table01" ><div align="center">合同性质</div></td>
          <td width="109" align="center" class="bg_table01"  ><div align="center">合同金额</div></td>
        </tr>
      <s:iterator value="info.result" id="infoId">
        <tr>  
          <td align="center"><div align="center"><input type="radio" name="operation2"  value="<s:property value="#infoId[3].contractMainSid"/>"></div></td>
          <td align="center" onClick=""><div align="center"><s:property value="#infoId[0].conId"/></div></td>
          <td align="center" onClick=""><div align="center"><s:property value="#infoId[0].conName"/></div></td>
          <td onClick="" width="168"  align="center"  ><div align="center"><s:property value="#infoId[1].name"/></div></td>
          <td  onClick=""width="73"  align="center"><s:property value="#infoId[2].name"/></td>
          <td onClick="" width="81"  align="center">
          <s:if test="#infoId[3].needPerativeReport == true">
          	<s:if test="#infoId[3].perativeReport==null">未确认 </s:if><s:else><div align="center"><s:property value="#infoId[3].perativeReport"/></div></s:else>
          </s:if>
          <s:else>
          	不需要 <s:hidden id="perativeReport%{#infoId[3].contractMainSid}" value="1"></s:hidden>
       	  </s:else>
          </td>    
          <td onClick=""width="70"  align="center">
          <s:if test="#infoId[3].needRecivedThing == true">
          	<s:if test="#infoId[3].recivedThing==null">未确认</s:if><s:else><div align="center"><s:property value="#infoId[3].recivedThing"/></div></s:else>
          </s:if>
          <s:else>
          	不需要 <s:hidden id="recivedThing%{#infoId[3].contractMainSid}" value="2"></s:hidden>
       	  </s:else>
          </td>
          <td onClick=""width="72"  align="center">
          <s:if test="#infoId[3].needFinallyReport == true">
          	<s:if test="#infoId[3].finallyReport==null">未确认</s:if><s:else><div align="center"><s:property value="#infoId[3].finallyReport"/></div></s:else>
          </s:if>
          <s:else>
          	不需要  <s:hidden id="finallyReport%{#infoId[3].contractMainSid}" value="3"></s:hidden>
       	  </s:else>
          </td>
          <td onClick=""width="99"  align="center" ><div align="center"><s:property value="typeManageService.getYXTypeManage(1019,#infoId[0].conType).typeName"/></div></td>
          <td onClick="" width="109"  align="center" ><div align="center"><s:property value="#infoId[0].conTaxTamount"/></div></td>
        </tr>
      </s:iterator>
    </table></td>
  </tr>
  <TR>
    <TD   height=20 class="bg_table02"><DIV align=center>
       <TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
      </DIV></TD>
  </TR>
</TABLE>
</s:form>
</body>
</html>
