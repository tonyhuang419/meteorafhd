<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>文件下载搜索</title>
</head>

<body style="overflow-x:hidden;overflow-y:auto;">
<iframe frameborder="0" id="main" name="main"  src="/yx/fileManager/fileDownSearch.action" style="HEIGHT: 50%; VISIBILITY: inherit; WIDTH: 240; Z-INDEX: 2" scrolling="auto"></iframe>

<s:form action="fileDownSelect" target="rightFrame" theme="simple">
<s:hidden name="resetCondition" value="true"></s:hidden>
<table height="40%"  border=0  cellpadding=1 cellspacing=0>
          <tr>
            	<td colspan="3" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
		  <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">文件类型：</div></td>
		          <td  colspan="2"  nowrap><div align="left">
		 				 <s:select name="filetype" id="clientName"
					list="fileTypeList" listKey="typeSmall" listValue="typeName"
					required="true" headerValue="" emptyOption="true">
				</s:select>
	              </div></td>
          </tr>
		        <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">客户：</div></td>
		          <td  colspan="2"  nowrap><div align="left">
		          <input type="text" id="cName" style="width:100px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select name="fm.clientcode" list="clientList" cssStyle="margin-left:-100px;width:118px;" listKey="id" listValue="name" onchange="clientChange(this)" emptyOption="true"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
					<s:hidden name="clientcode" id="clientId"></s:hidden>
		 				
	              </div></td>
          </tr>
          <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">上传人：</div></td>
		          <td  colspan="2"  nowrap><div align="left">
		 				 <s:select name="personcode" id="clientName"
					list="employeeList" listKey="id" listValue="name"
					required="true" headerValue="" emptyOption="true">
				</s:select>
	              </div></td>
          </tr>
          <tr class="bg_table02">
				  <td rowspan="2" align="center" nowrap class="bg_table02"><div align="right">上传时间：</div></td>
				  <td nowrap><div align="left">从<input type="text" id="bidDate1"
						name="uploadDate1" readonly="readonly"
						onClick="javascript:ShowCalendar(this.id)" size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate1')" align=absMiddle
						alt="" /></div></td>
  </tr>
				<tr class="bg_table02">
                  <td nowrap><div align="left">至<input type="text" id="bidDate2"
						name="uploadDate2" readonly="readonly"
						onClick="javascript:ShowCalendar(this.id)" size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate2')" align=absMiddle
						alt="" /></div></td></tr>
						<tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">文件名称：</div></td>
		          <td  colspan="2"  nowrap><div align="left">
		 				 <s:textfield name="filename"></s:textfield>
	              </div></td>
          </tr>
          <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">文件说明：</div></td>
		          <td  colspan="2"  nowrap><div align="left">
		 				 <s:textfield name="filecontent"></s:textfield>
	              </div></td>
          </tr>
                
            <tr class="bg_table02">
			  <td colspan="3" nowrap class="bg_table04"><div align="center">
			    <input type="submit" value=" 查  询 "  class="button02">
		      </div></td>
  </tr>
		</table>
<script>
	function clientChange(selObj){
		document.getElementById('cName').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("clientId").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("cName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
</script>
</s:form>		
</body>
</html>