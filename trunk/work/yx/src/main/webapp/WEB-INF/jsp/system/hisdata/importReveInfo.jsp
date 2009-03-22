<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="./css/style.css" rel="stylesheet" type="text/css">
<head>
<title>合同历史数据导入</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
<script type="text/javascript">
function shangchuan(){
	var flag=checkHasNoConfirm();
	if(flag){
		var flag=window.confirm("存在未确认的到款清单，点击确定将清这些空未确认的到款清单！！！");
		if(flag){
			document.importReveInfo.submit();
		}
	}else{
		document.importReveInfo.submit();
	}
}
function checkHasNoConfirm()
{
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
	myRequest.send("method=checkHasNoConfirm&randomNum="+Math.random());
	return flag;
}
</script>
</head>
<body>
<s:form  action ="importReveInfo" method ="POST" theme="simple" enctype ="multipart/form-data" > 
<s:hidden name="method" value="execute"></s:hidden>
<table width="98%" border="0" cellspacing="1" cellpadding="1"
	class="bg_white" align="center">
	<tr>
		<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				<tr>
					<td colspan="2" align="left">当前页面：收款管理->收款管理->导入收款信息</td>
				</tr>
				<tr class="bg_table02">
					<td width="30%" align="right" class="bg_table02">
						<div align="right">请选择文件：</div>
					</td>
					<td align="right" class="bg_table02">
						<div align="left"><s:file name="excelFile"/></div>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2" class="bg_table03"><input type="button" value="上传" class="button01" onclick="shangchuan();"/></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</s:form>
<p>&nbsp;</p>
<p>&nbsp;</p>
</body>
</html>