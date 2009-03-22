<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {background: lightblue; color: blue;}
</style>
<title>外协付款确认</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var allDocumentArr = new Array();
	<s:iterator value="info.result" id="apply">
	allDocumentArr[allDocumentArr.length] = new Array("<s:property value="#apply[0].id"/>","<s:property value="#apply[0].payState"/>");
	</s:iterator>
	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
	function checkPayInfoState(payState){
		var checkArr=document.getElementsByName("ids");
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	       	  for(var j = 0; j < allDocumentArr.length ; j++){
	       	  	if(checkArr[i].value == allDocumentArr[j][0]){
	       	  		if(allDocumentArr[j][1]!=payState){
	       	  			return false;
	       	  		}
	       	  	}
	       	  }
	        }
	    }
	    return true;
	}
	function aaa() 
	{ 
	  var checkArr=document.getElementsByName("ids");
	    var checkStr="";
	    var j = 0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	       	  j++
	          checkStr=checkStr+","+checkArr[i].value;
	        }
	    }
	    if(j == 0){
	    	alert("您还没有选择确认提交的申请单!");
	    }else{
	    	var flag = checkPayInfoState("1");
	    	if(flag){
		    	if(confirm("确定要通过选择的申请单吗？")){
		   	 		location.href="../assistance/passApply.action?method=pass&ids="+checkStr.substring(1); 
		   	 	}
	   	 	}else{
	   	 		alert("您选择的付款申请在该状态下不能确认通过!");
	   	 	}
	    }
	}
	//确认退回
	function confirmCancel() 
	{ 
	  var checkArr=document.getElementsByName("ids");
	    var checkStr="";
	    var j = 0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	          j++;
	          checkStr=checkStr+","+checkArr[i].value;
	        }
	    }
	    if(j == 0){
	    	alert("您还没有选择退回提交的申请单!");
	    }else if(j==1){
	    	var flag = checkPayInfoState("1");
	    	if(flag){
		    		openWin('passApply.action?method=showBack&ids='+checkStr.substring(1),400,300);
	    	}else{
	    		alert("您选择的付款申请在该状态下不能确认退回!");
	    	}
	    }else{
	    	alert("请单个确认退回申请单");
	    }
	}
	function cancelConfirm(){
			  var checkArr=document.getElementsByName("ids");
	    var checkStr="";
	    var j = 0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	          j++;
	          checkStr=checkStr+","+checkArr[i].value;
	        }
	    }
	    if(j == 0){
	    	alert("您还没有选择取消确认的申请单!");
	    }else if(j==1){
	    	var flag = checkPayInfoState("2");
	    	if(flag){
	    		var conFlag = window.confirm("是否取消确认？");
	    			if(conFlag){
	    				location.href = '../assistance/passApply.action?method=cancelConfirm&ids='+checkStr.substring(1);
	    			}
	    	}else{
	    		alert("您选择的付款申请在该状态下不能取消确认!");
	    	}
	    }else{
	    	alert("请单个取消确认申请单");
	    }
	}
	
</script>
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
</head>
<body leftmargin="0">
<s:form theme="simple" action="passApply">
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
<tr>
	<td align="center"> 
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td align="left" colspan="3">当前页面:外协管理->外协付款确认</td>
			</tr>
			<tr>
            	<td  colspan="3" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
			    <tr class="bg_table02">
			      <td  colspan="2"><div align="center">
			        <input type="button" name="SearchBtn4" value=" 通  过 " class="button01" onClick="javascript:aaa();">
			        <input type="button" name="SearchBtn2" value=" 退  回 " class="button01" onClick="javascript:confirmCancel();">
			      </div></td>
			      <td width="20%">
			      	<input type="button" value = "取消确认" class="button01" onclick = "cancelConfirm()"/> 
			      </td>
        </tr>
		</table>
	  <table align="center" border="1" cellpadding="1" cellspacing=1 width="100%" id="checkInfo" bordercolor="#808080" style=" border-collapse: collapse;">
		<tr align="center">
	      <td nowrap="nowrap" class="bg_table01">&nbsp;</td>
	      <td nowrap="nowrap" class="bg_table01">外协合同号</td>
          <td nowrap="nowrap" class="bg_table01">申请序号</td>
          <td nowrap="nowrap" class="bg_table01">外协合同名称</td>
	      <td nowrap="nowrap" class="bg_table01">外协供应商</td>
          <td nowrap="nowrap" class="bg_table01">申请日期</td>
          <td nowrap="nowrap" class="bg_table01">申请金额</td>
          <td nowrap="nowrap" class="bg_table01">销售员</td>
          <td nowrap="nowrap" class="bg_table01">是否预付</td>
          <td nowrap="nowrap" class="bg_table01">状态</td>
        </tr>   
     <s:iterator value="info.result" id="apply">
	    <tr align="center"  onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
          <td>
            <input type="checkbox" name="ids" value="<s:property value="#apply[0].id"/>">
          </td>
          	<td align="left" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')"><s:property value="#apply[1]"/></td>
	    	<td align="left" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')"><s:property value="#apply[0].applyInfoCode"/></td>
	      	<td align="left" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')"><s:property value="#apply[2]"/></td>
         	<td align="left" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')"><s:property value="#apply[3]"/></td>
         	<td onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')"><s:property value="#apply[0].applyDate"/></td>
         	<td align="right" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')"><s:property value="#apply[0].payNum"/></td>
       		<td align="left" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')"><s:property value="#apply[4].name"/></td>
       		<td align="left" nowrap="nowrap" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')">
       			<s:if test="#apply[0].applyPay">
       				预付款
       			</s:if>
       			<s:else>
       			正常付款
       			</s:else>
       		</td>
       		<td align="left" nowrap="nowrap" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')">
       			<s:if test="#apply[0].payState==1">
       				待确认
       			</s:if>
       			<s:elseif test = "#apply[0].payState==2">
       			确认通过
       			</s:elseif >
       			<s:elseif test="#apply[0].payState==3">
       				确认退回
       			</s:elseif>
       			<s:elseif test="#apply[0].payState==4">
       				确认处理
       			</s:elseif>
       		</td>
        </tr>
     </s:iterator>
	  </table>
	<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			</td>
		</tr>
	</table>
	</s:form>
</body>
</html>
