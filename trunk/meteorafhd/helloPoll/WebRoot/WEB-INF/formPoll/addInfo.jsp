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
			<font size="5" face="΢���ź�" color="#ff0080">�𾴵��οͣ���Ҳһ������MJ�ɣ�����&hellip;</font><font
				size="5" face="΢���ź�" color="#ff0080">����&hellip;</font><font size="5"
				face="΢���ź�" color="#ff0080">���Ը����Ҳ�����ó���ɹɹ<br><br></font>
		</div>
		</blockquote>
		<table id="addInfo" border="0" width="70%" align="CENTER">
			<tr>
				<td>
					MJ����
					<input type="text" maxlength="20" size="15" name="mjName"
						id="addName">
				</td>
				<td>
					�۸�
					<input type="text" maxlength="6" size="10" name="mjprice"
						id="addPrice">
				</td>
				<td>
					������
					<select name="mjdeal" id="addDeal">
						<option value="0">
							����
						</option>
						<option value="1">
							����
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td align="center">
					<a href="/helloPoll/RandImage"><image src="/helloPoll/RandImage" /> </a>
				</td>
				<td align="left">		
					��������֤��:<input type="text" maxlength="4" size="10" id="checkNo">
				</td>
				<td>
				</td>
			</tr>
			<tr>
				<td></td>
				<td align="center">
					<input type="button" value="   ��  ��   " onclick="addMJ()" />
				</td>
				<td></td>
			</tr>
		</table>
	</body>
</html>