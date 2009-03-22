<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<title>外协供应商选择</title>


<script language="javascript">
	function checkString(){
	var names=document.getElementsByName("name");
    var ids=document.getElementsByName("ids");
    var name;
    var id=0;
    for(var i=0;i<names.length;i++){
        if(ids[i].checked){
             id=ids[i].value;
             name=names[i].value;
             if(window.opener.document.getElementById("supId") != null){
             	window.opener.document.getElementById("supId").value=id;
             }
             if(window.opener.document.getElementById("supplierid") != null){
             	window.opener.document.getElementById("supplierid").value=id;
             }        
             window.opener.document.getElementById("supplyId").value=name;
 			 getSupplyInfo("/yx/jsonData.action?method=doGetSupplyInfoOfSupplyId&supplyId=" + id);
       }
    }
  		this.window.close();
 	}
 	
 	function getSupplyInfo(jsonObjGetUrl)
	{
		var jsonRequest = new Request.JSON({async:false,url:jsonObjGetUrl, onComplete: function(jsonObj){
		if(jsonObj!=null && jsonObj.jsonData !=null ){
				if(jsonObj.jsonData.supplierCode != null){
					window.opener.document.getElementById('code').innerHTML=jsonObj.jsonData.supplierCode;
				}
				else{
					window.opener.document.getElementById('code').innerHTML="";
				}
				if(jsonObj.jsonData.billBank!=null){
					window.opener.document.getElementById('bankName').innerHTML=jsonObj.jsonData.billBank;
				}else{
					window.opener.document.getElementById('bankName').innerHTML="";
				}
				if(jsonObj.jsonData.billAccount!=null){
					window.opener.document.getElementById('bankNum').innerHTML=jsonObj.jsonData.billAccount;}
				else{
					window.opener.document.getElementById('bankNum').innerHTML="";
				}
		}
		}}).get({randerNum:Math.random()});	 	
	}
</script>

</head>
<body style="margin: 0px;">
<br>
<s:form action="" theme="simple">
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center" bordercolor="#808080" style=" border-collapse: collapse;">

				<tr class="bg_table02">
					<td width="10%" align="right">供应商名称：</td>
					<td width="20%" align="left">
					<s:textfield name="supName" id="supName" />
					&nbsp;&nbsp;&nbsp;&nbsp; <input value="查询"
						type="submit" class="button01"
						onclick="javascript:document.forms(0).action='<s:url action="chooseSup"><s:param name="method">selectByName</s:param></s:url>'" />
					</td>
				</tr>
			</table>
			
			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td class="bg_table01">选择</td>
					<td class="bg_table01">供应商代码</td>
					<td class="bg_table01">供应商名称</td>
				</tr>
				<s:iterator value="info.result">
					<tr align="center">
						<td align="center" ><input type="radio" name="ids"
							value="<s:property  value="supplierid"/>" onclick=""></td>
						<td align="left" ><s:property  value="supplierCode" /></td>
						<td align="left" ><input type="hidden" name="name"
							value="<s:property value="supplierName"/>"><s:property value="supplierName"/></td>
					</tr>
				</s:iterator>
			</table>
			
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table02"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
	<table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
		<tr class="bg_table04" height="42px">
			<td align="center" colspan="8">
				<input class="button01" type="button" value="添  加" onclick="checkString()" />
				<input class="button01"	type="button" value="关  闭" onclick="window.close()" /> 
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>
