<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>文件下载搜索</title>

<script language="javascript">

	function clean()
	{
		document.getElementById("clientName").value="";
	}
	
	function validate()
	{
		var ev = new Validator();
        with(fileDownSelect){ 
 		 ev.test("dateYX","上传起始日期格式不正确",$("bidDate1").value); 
 		 ev.test("dateYX","上传结束日期格式不正确",$("bidDate2").value); 
		}
	    ev.writeErrors(errorsFrame, "errorsFrame");
		if (ev.size() > 0) {			
			return true;
		}
	}
	function search(){
		if(!validate()){
			document.forms(0).submit();
		}
	}
	
</script>
</head>

<body leftmargin="0">
	<div align="left" style="color: #FF0000">
	<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" scrolling="no"></iframe>
	</div>	
<s:form action="fileDownSelect" target="rightFrame" theme="simple">
<s:hidden name="resetCondition" value="true"></s:hidden>
  <table width="100%" class="bg_table02">
  <tr>
    <td colspan="2" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
		  <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">文件类型：</div></td>
		          <td   nowrap><div align="left">
		 				 <s:select name="filetype" id="clientName"
					list="fileTypeList" listKey="typeSmall" listValue="typeName"
					required="true" headerValue="" emptyOption="true">
				</s:select>
	              </div></td>
          </tr>
		        <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">客户全称：</div></td>
		          <td nowrap><div align="left">
		          <input type="text" id="cName" style="width:100px;height:21px;font-size:10pt;" readonly/><span style="width:18px;border:0px solid red;">
					<s:select name="fm.clientcode" list="clientList" cssStyle="margin-left:-100px;width:118px;" listKey="id" listValue="name" onchange="clientChange(this)" emptyOption="true"></s:select>
					</span>
					<input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
					<s:hidden name="clientcode" id="clientId"></s:hidden>		 				
	              </div></td>
          </tr>
          <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">上传人：</div></td>
		          <td nowrap><div align="left">
		 				 <s:select name="personcode" id="clientName"
					list="employeeList" listKey="id" listValue="name"
					required="true" headerValue="" emptyOption="true">
				</s:select>
	              </div></td>
          </tr>
          <tr class="bg_table02">
				  <td rowspan="2" align="center" nowrap class="bg_table02"><div align="right">上传时间：</div></td>
				  <td nowrap><div align="left">从<input type="text" id="bidDate1"
						name="uploadDate1" 
						 size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate1')" align=absMiddle
						alt="" /></div></td>
 			 </tr>
			<tr class="bg_table02">
                  <td nowrap><div align="left">至<input type="text" id="bidDate2"
						name="uploadDate2" 
						 size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate2')" align=absMiddle
						alt="" /></div></td></tr>
						<tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">文件名称：</div></td>
		          <td   nowrap><div align="left">
		 				 <s:textfield name="filename"></s:textfield>
	              </div></td>
          </tr>
          <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">文件说明：</div></td>
		          <td   nowrap><div align="left">
		 				 <s:textfield name="filecontent"></s:textfield>
	              </div></td>
          </tr>
                
            <tr class="bg_table02">
			  <td colspan="2" nowrap class="bg_table04"><div align="center">
			    <input type="button" value=" 查  询 "  class="button02" onclick="search();">
		      </div></td>
 		 </tr>
		</table>
</s:form>		
</body>
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
</html>