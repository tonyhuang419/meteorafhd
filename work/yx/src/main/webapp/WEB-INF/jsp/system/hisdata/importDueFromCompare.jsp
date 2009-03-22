<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>应收比较导入</title>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 16px
}

body {
	background-color: #FFFFFF;
}

.AutoNewline {
	word-break: break-all; /*必须*/
}
-->
</style>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
	<script type="text/javascript">
		function doSubmit(){
			var flag=checkExists();
			if(flag){
				var f=window.confirm("存在已经导入的应收比较信息，点击确定将清这些空记录！！！");
				if(f){
					document.importDueFromCompare.submit();
				}
			}else{
				document.importDueFromCompare.submit();
			}
			
		}
		function checkExists(){
			var flag=true;
			var url="yx/system/hisdata/checkHasNoConfirm.action";
		  	var myRequest = new Request({url:url,async:false,method:'get'});
		  	myRequest.addEvent("onSuccess",function(responseText, responseXML){
				if(responseText =='false'){//false表示无记录
					flag = false;
				}else{//true表示有记录
					flag = true;
				} 
		   	});
			myRequest.send("method=checkHasSelfDueFrom&randomNum="+Math.random());
			return flag;
		}
	</script>
	
</head>
<body leftmargin="0">

<p>&nbsp;</p>
<s:form action="importDueFromCompare" method="POST" theme="simple"
	enctype="multipart/form-data">
	<div style="color: red"><s:fielderror /></div>
	<s:hidden name="method" value="execute"></s:hidden>
	<table width="98%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				<tr>
					<td colspan="2" align="left">当前页面:统计查询—>应收款统计->导入应收</td>
				</tr>
				<tr class="bg_table02">
					<td width="17%" align="right" class="bg_table02">
					<div align="right">请选择文件：</div>
					</td>
					<td align="right" class="bg_table02">
					<div align="left"><s:file name="excelFile" /></div>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="bg_table03">
						<input type="button" value="上传" class="button01" onclick="doSubmit();">
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>

