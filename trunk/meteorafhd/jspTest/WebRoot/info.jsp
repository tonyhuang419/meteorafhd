
<%@ page language="java" import="java.util.*,chatServlet.*"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%!String name;
	boolean hasRead = false;%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>ChatRecord</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">

		<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
	</head>

	<body>
		<%
			AboutRecord record = new AboutRecord();
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			name = (String) session.getAttribute("username");
			out.write("<H1>Welcome~~~" + name + "</H1>");

			String tempinfo = request.getParameter("n_info");

			if (tempinfo == null || tempinfo.equals("")) {

			} else {
				java.util.Date currentTime = new java.util.Date();
				String time = currentTime.toString();

				record.insertXml(name, tempinfo, time);
				CollectionX.addList(name, tempinfo, time);
			}
			Iterator<Info> it;
			if (hasRead == false) {
				it = record.readXml();
				hasRead = true;
			} else
				it = CollectionX.readList();
		%>
		<table width="100%">
			<tr>
				<strong>
				<td width="20%">
					Name
				</td>
				<td width="55%">
					Info
				</td>
				<td width="25%">
					Time
				</td>
				</strong>
			</tr>
			<%
				if (it != null) {
					while (it.hasNext()) {
						Info temp = it.next();
						out.println("<tr>");
						out.println("<td width='20%'>");
						out.println("<B>" + temp.getName() + ":</B>");
						out.println("</td><td width='55%'>");
						out.println(temp.getInfo() + "<br>");
						out.println("</td><td width='25%'>");
						out.println(temp.getTime() + "<br>");
						out.println("</tr>");
					}
				}
			%>
		</table>
	</body>
</html>
