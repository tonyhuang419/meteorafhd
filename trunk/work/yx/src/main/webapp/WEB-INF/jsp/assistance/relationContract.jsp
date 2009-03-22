<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {background: lightblue; color: blue;}
</style>
<title>关联合同</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var pageArr = new Array();
	<s:iterator value="info.result" id="ac">
		pageArr[pageArr.length] = new Array(
		'<s:property value="#ac[0].id"/>',
		'<s:property value="#ac[0].assistanceName"/>',
		'<s:property value="#ac[0].supplyId"/>',
		'<s:property value="#ac[1]"/>',
		'<s:property value="#ac[0].assistanceId"/>',
		'<s:property value="#ac[0].mainProjectId"/>');
	</s:iterator>
	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
	
	function getCheckedObj(acId){
		if(pageArr == null || pageArr.length == 0 ){
			return null;
		}
		var acName = "";
		var supId = "";
		var supName = "";
		var acContractId = "";
		var acProjectId = "";
		for(var i = 0 ; i <pageArr.length ; i ++ ){
			if(pageArr[i][0] == acId){
				acName = pageArr[i][1];
				supId = pageArr[i][2];
				supName = pageArr[i][3];
				acContractId = pageArr[i][4];
				acProjectId = pageArr[i][4];
			}
		}
		return {acId:acId,acName:acName,supId:supId,supName:supName,acContractId:acContractId,acProjectId:acProjectId};
	
	}
	function relationContract(){
    	var checkArr=document.getElementsByName("ids");
    	var checkStr="";
    	var j=0;
    	for(var i=0;i<checkArr.length;i++){
        	if(checkArr[i].checked){ 
            	 checkStr=checkArr[i].value;
             	j++;
             	break;
        	}
    	}
    	if(j==1)
    	{  
       		var checkObj = getCheckedObj(checkStr);
       		if(checkObj!=null){
       			window.opener.setParentValueByChild(checkObj);
       		}
       		window.close();
       		
    	}
   		if(j==0){
        	alert("您还没有选择关联的对象！");
   		}
	}
</script>
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>
<body>
<s:form theme="simple" action="assistanceTicket">
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
<tr>
	<td align="center"> 
	  <table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="5" align="left" >当前页面：外协管理->关联外协合同</td>
		</tr>
			    <tr class="bg_table04">
                  <td width="15%" align="right" >外协合同名称：</td>
                  <td width="24%" align="left" ><s:textfield name="assistanceContractName"/></td>
                  <td width="12%" align="right" >外协合同号：</td>
                  <td width="24%" align="left" ><s:textfield name="assistanceContractNo"/></td>
	    		 <td> <input type="submit" value="查    询" class="button02" ></td>
	    </tr>
		</table>
		</td>
		</tr>
		<tr>
		<td align="center">
	  <table align="center" border=1 cellpadding="0" cellspacing=0 width="100%" bordercolor="#808080" style=" border-collapse: collapse;" id="checkInfo">	  
		<tr align="center">
   		  <td class="bg_table01">&nbsp;</td>
		  <td class="bg_table01">外协合同号</td>
          <td class="bg_table01">外协合同名称</td>
          <td class="bg_table01">外协供应商</td>
          <td class="bg_table01">签订日期</td>
          <td class="bg_table01">预计结束日期</td>
          <td class="bg_table01">合同金额</td>
          <td class="bg_table01">已开票金额</td>
          <td class="bg_table01">已开收据金额</td> 
       	 </tr>
       	 <s:iterator value="info.result" id="ac">
			<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
			  <td>
			    <input type="radio" name="ids"  value="<s:property value="#ac[0].id"/>">
			  </td>
			  <td nowrap><DIV align="left"><s:property value="#ac[0].assistanceId"/></DIV></td>
			  <td align="left"><s:property value="#ac[0].assistanceName"/></td>
			  <td align="left"><s:property value="#ac[1]"/></td>
			  <td align="center"><s:property value="#ac[0].contractDate"/></td>
			  <td align="center"><div align="center"><s:property value="#ac[0].endDate"/></div></td>
			  <td align="right"><s:property value="#ac[0].contractMoney"/></td>
			  <td align="right"><s:property value="#ac[0].ticketMoney"/></td>
			  <td align="right"><s:property value="#ac[0].receiptAmount"/></td>
	    </tr>
	    </s:iterator>
	  </table>
	  </td></tr>
	  <tr>
	  <td align="center">
		<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
	  </td>
  </tr>
<tr><td align="center">
<table width="100%" border="0" align='center' class="bg_table04">
<tr> 
  <td><div align="center">
    <input type="button" class="button02" value="确    认" onClick="javascript:relationContract()">
    <input type="button" class="button02" value="关    闭" onClick="window.close()">
  </div></td>
</tr>
</table>
</td>
</tr>
</table>
</s:form>
</body>
</html>
