<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>选择关联合同</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<script language="javascript">
	
	function onSubmit(){
	var checkArr=document.getElementsByName("cmId");
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
         	var billId =document.getElementById("ids").value;
			location.href="../billtoReceipt/relationContractQuery.action?method=relationCon&ids="+billId+"&cmId="+checkStr.substring(1);
		 }
	}	
</script>
<body>
<s:form action="relationContractQuery" theme="simple">
	<s:hidden name="method" value="searchContract"></s:hidden>
	<s:hidden name="ids" id="ids"  value="%{#parameters.ids}"></s:hidden>
	<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
				<tr class="bg_table03">
					<td colspan="3" class="bg_table03">合同名称：<s:textfield name="cName"></s:textfield></td>
					<td colspan="3" class="bg_table03">合同号：<s:textfield name="cId"></s:textfield></td>
				</tr>
				<tr class="bg_table03">
					<td colspan="6" class="bg_table03">
					<div align="center">
				<input value="查询" type="submit" class="button01"/>	
				</tr>
			</table>
			<table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td class="bg_table01">合同号</td>
					<td class="bg_table01">合同名称</td>
				</tr>
				<s:iterator value="info.result" id="result" status="status">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td>
							<input type="radio" name="cmId" value="<s:property value="conMainInfoSid"/>" />
						</td>
						<td>
							<s:property value="conId" />
						</td>
						<td>
							<s:property value="conName" />
						</td>
					</tr>
				</s:iterator>
				<tr class="bg_table03">
					<td colspan="8" align="center" class="bg_table03"><input type="button" name="SearchBtn" value="　确认关联　" class="button01" onClick="onSubmit()">
					<input type="button" name="close" value="　关 闭　" class="button01" onClick="javascript:window.close();"></td>
				</tr>
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