<%@ page language="java" contentType="text/html; charset=GBK"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
	<head>
		<script type="text/javascript" src='/helloPoll/dwr/interface/CreatePoll.js'></script>
		<script type="text/javascript" src='/helloPoll/dwr/engine.js'></script>
		<script type="text/javascript" src='/helloPoll/dwr/util.js'></script>
		<script language="javascript" src='/helloPoll/addMJ.js'></script>
	</head>
	<body>
		<div align="center">
			<font size="5" face="微软雅黑" color="#ff0080">尊敬的游客，您也一起来卖MJ吧，创收&hellip;</font><font
				size="5" face="微软雅黑" color="#ff0080">创收&hellip;</font><font size="5"
				face="微软雅黑" color="#ff0080">嘛，不愿卖的也可以拿出来晒晒<br><br></font>
		</div>
		</blockquote>
		<table id="addInfo" border="0" width="70%" align="CENTER">
			<tr>
				<td>
					MJ名：
					<input type="text" maxlength="20" size="15" name="mjName"
						id="addName">
				</td>
				<td>
					价格：
					<input type="text" maxlength="6" size="10" name="mjprice"
						id="addPrice">
				</td>
				<td>
					卖不：
					<select name="mjdeal" id="addDeal">
						<option value="0">
							不卖
						</option>
						<option value="1">
							卖！
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="center">
					<a href="/helloPoll/RandImage"><image src="/helloPoll/RandImage" /> </a>
				</td>
				<td align="left">		
					请输入验证码:<input type="text" maxlength="4" size="10" id="checkNo">
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td></td>
				<td align="center">
					<input type="button" value="   添  加   " onclick="addMJ()" />
				</td>
				<td></td>
			</tr>
		</table>
	</body>
</html>