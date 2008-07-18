<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>通知</title>
<%@ include file="/commons/jsp/meta.jsp" %>
</head>
<style>
html { overflow-x:hidden;overflow-y:hidden;  }
</style>
<body background="/yx/commons/images/bj.gif">
<table height="40%" border=0 cellpadding=1 cellspacing=0>
	<tr align="center">
		<td width="30%" height="51" align="center">
		<div align="center"><img src="/yx/commons/images/tz.gif"></div>
		</td>
	</tr>
	<tr>
		<td height="2">
	<tr>
		<td align="center" nowrap>
		<div align="right">
		<div align="right">
		<table>
			<tr>
				<marquee scrollamount='1' scrolldelay='30' direction='UP'
					width='240' id='helpor_net' height='380'
					onmouseover='helpor_net.stop()' onmouseout='helpor_net.start()'
					Author:redriver; For more,visit:www.helpor.net> <s:iterator
					value="noticeList">
					<p><a href="#"><s:property value="content" /></a></p>
				</s:iterator> </marquee>
			</tr>
		</table>
		</div>
		</div>
		</td>
	</tr>
	<tr class="bg_table02">
		<td nowrap class="bg_table04">
		<div align="center"></div>
		</td>
	</tr>
	<tr class="bg_table04">
		<td nowrap class="bg_table04">
		<div align="center"></div>
		</td>
	</tr>
</table>
</body>
</html>