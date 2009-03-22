<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>通知</title>
<%@ include file="/commons/jsp/meta.jsp" %>
</head>
<style>
html { overflow-x:hidden;overflow-y:hidden;  }

.bg{
	background-image:url(/yx/commons/images/bj.gif);
	background-repeat:repeat-x;
}

</style>
<body class="bg" >
<table  border=0 cellpadding=1 cellspacing=0>
	<tr align="center">
		<td width="30%" height="50" align="center">
			<div align="center"><img src="/yx/commons/images/tz.gif"></div>
		</td>
	</tr>
	<tr>
		<td  class="bg_table01" ><img src="./../images/temp.gif" width="1" height="1" ></td>
	</tr>
	<tr>
		<td height="30"></td>
	</tr>
	
	<tr>
		<td align="center" nowrap>
		<div align="right">
		<table>
			<tr>
				<marquee scrollamount='1' scrolldelay='30' direction='UP'
					width='220' id='helpor_net' height='380'
					onmouseover='helpor_net.stop()' onmouseout='helpor_net.start()'> 
					<s:iterator value="noticeList">
					<p>&nbsp;&nbsp;&nbsp;&nbsp;<a href="#"><s:property value="content" /></a></p><br>
				</s:iterator> </marquee>
			</tr>
		</table>
		</div>
		</td>
	</tr>

</table>
</body>
</html>