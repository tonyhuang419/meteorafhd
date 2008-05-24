<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<link href="/helloPoll/style.css" rel="stylesheet" type="text/css" />

<html>
	<head>
		<title>欢迎</title>
		<script type="text/javascript" src='/helloPoll/dwr/interface/ToolBidReocrd.js'></script>
		<script type="text/javascript" src='/helloPoll/dwr/engine.js'></script>
		<script type="text/javascript" src='/helloPoll/dwr/util.js'></script>

		<script language="javascript" src="/helloPoll/guestTool.js"></script>
	</head>

	<body>
		<h3>
			&nbsp; 欢迎光临MJ贩卖系统！！！
			<br>
		</h3>
		<br>
		<br>
		<br>
		<jsp:include flush="true" page="addInfo.jsp" />
		<br>
		<br>
		<br>
		用户名:
		<div id="username">
			${sessionScope.username}
		</div>
		<br>
		<%-- 密码：
		 <s:property value="pwd" /> --%>
		<br>
		<b><h3>
				<font color='#FF0000'><div id="message" align="CENTER"></div>
				</font>
			</h3> </b>
		<br>
		<table id="mjInfo" border="1" align="CENTER">
			<tr align="CENTER">
				<th width="50">
					MID
				</th>
				<th width="100">
					MJ Name
				</th>
				<th width="100">
					MJ MaxPrice（TSY）
				</th>
				<th width="100">
					Deal
				</th>
			</tr>
			<s:bean id="mjListC" name="fhd.tool.MJListCompator" />
			<s:sort source="#session.mjList" comparator="#mjListC">
				<s:iterator id="mjList" status="st">
					<tr align="CENTER"
						<s:if test="#st.odd">style="background-color:#cccccc"</s:if>>
						<td>
							<s:property value="id" />
						</td>
						<td>
							<s:property value="name" />
						</td>
						<td>
							<s:property value="price" />
						</td>
						<td>
							<s:property value="deal" />
						</td>
					</tr>
				</s:iterator>
			</s:sort>
		</table>
		<br>
		<br>
		<div id="bidMessage">
		</div>
	</body>
	<script language="javascript">
		modifyDeal();
		addSeeRecordBtn();
		addBuyBtn();
		addMJHref();
	</script>
</html>