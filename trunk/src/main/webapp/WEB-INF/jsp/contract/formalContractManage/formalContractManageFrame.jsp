<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<title>正式合同管理框架</title>
		
<style type="text/css">
<!--
.navPoint {
	COLOR: blank;
	CURSOR: hand;
	FONT-FAMILY: Webdings;
	FONT-SIZE: 9pt
}
-->
</style>
		<script language="javascript">
function showlistiframe(obj){

	if(document.getElementById(obj).style.display  == "block"){
		document.getElementById(obj).style.display = "none";
		document.getElementById("sign").innerHTML = "4";
	}
	else{
		document.getElementById(obj).style.display = "block";
		document.getElementById("sign").innerHTML="3";
	}	
}
</script>
	</head>
	<body  bgcolor="lightblue" scroll="no" style="MARGIN: 0px">
		<table width="100%" height="100%" border="0" cellpadding="0"
			cellspacing="0">
			<tr>
				<td width="300" id="td1" style="display: block">
					<iframe scrolling="no"
						src="/yx/contract/formalContractManage/formalContractManageLeftFrame.action"
						width="100%" id="leftframe" frameborder="0" height="100%"
						style="display: block"></iframe>
				</td>
				<td width="10" onClick="showlistiframe('td1');">
					<table height="100%" border="0" cellpadding="0" cellspacing="0"
						onMouseOver="this.bgColor='#BBBBFF'; "
						onMouseOut="this.bgColor='lightblue';" bgcolor="lightblue">
						<tr>
							<td id="sign" width="15" class="navPoint">
								3
							</td>
						</tr>
					</table>
				</td>
				<td width="*">
					<iframe scrolling="yes"
						src="/yx/contract/formalContractManage/formalContractManageQuery.action"
						width="100%" id="rightiframe" frameborder="0" height="100%"
						name="content"></iframe>
				</td>
			</tr>
		</table>
	</body>
</html>
