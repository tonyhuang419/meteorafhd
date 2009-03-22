<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>addNewClient</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script language="javascript" src="<s:url value="/commons/scripts/checkLength.js"/>"></script>

  <script src="<s:url value="/commons/scripts/mootools-release-1[1].11.js"/>" type="text/javascript"></script>
  <script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
</script>

</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<s:form action="clientLinkMan" theme="simple">
<s:hidden name="method" value="updateExp" />
<s:hidden name="cc.id"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr> 
		<td valign="top" > 
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
			  <td height="3" align="left" >当前页面:基础管理->联系人修改</td>
		  </tr>
			<tr><td class="bg_table01" height="1"><img src="../../images/temp.gif" width="1" height="3"></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr align="center">
			  <td class="bg_table02" align="right"><font color="red">* </font>联系人姓名：</td>
			  <td class="bg_table02" align="left"><s:textfield name="cc.name"></s:textfield></td>
			  <td class="bg_table02" align="right">手机：</td>
			  <td class="bg_table02" align="left"><s:textfield name="cc.callPhone"></s:textfield></td>
            </tr>
            
			<tr align="center">
			  <td class="bg_table02" align="right">固定电话：</td>
			  <td class="bg_table02" align="left"><s:textfield name="cc.phone"></s:textfield></td>
			  <td class="bg_table02" align="right">email：</td>
			  <td class="bg_table02" align="left"><s:textfield name="cc.email"></s:textfield></td>
		    </tr>

			<tr align="center">
              <td class="bg_table02" align="right">其他联系方式：</td>
			  <td class="bg_table02" align="left"><s:textfield name="cc.otherPhone"></s:textfield></td>
			  <td class="bg_table02" align="right">联系地址：</td>
			  <td class="bg_table02"  align="left" ><s:textfield name="cc.address"></s:textfield></td>       
		    </tr>
		  	      	           
	        <tr align="center">
              <td class="bg_table02" align="right">联系人性质：</td>
			  <td class="bg_table02" align="left"><s:select name="cc.natureId" list="linkmanNature" listKey="typeSmall"
	              listValue="typeName" required="true"  emptyOption="true" headerValue=""></s:select> </td>              
	              
			  <td class="bg_table02" align="right">所属客户：</td>
			 <td class="bg_table02"  align="left" ><s:textfield name="namex" id="clientName" readonly="true"></s:textfield><s:hidden name="cc.clientId"  id="clientId">
			  </s:hidden><input type="button" value="…"	onclick="javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
					</td>                    
		  </tr>	      	  
	
			<tr class="bg_table03" align="center" style="height:42px">
				<td colspan="4">
				<Table style="width:0%;100%">
					<tfoot class="bg_table03" style="height:42px">
						<tr>
							<td align="right" colspan="2">
                                <div align="center">
                                	<input class="button01" type="button" name="Input" value="保    存"  
                                	onclick="check();" />
								</td>                             
                         
					    <td width="43" colspan="2">		<input class="button01" type="submit" value="返    回" onclick="javascript:document.forms(0).action='yx/system/clientLinkManQuery.action?resetCondition=true'"></td></tr>	      
					</tfoot>
				</Table>				</td>
			</tr>
		</table>
	  </td>
	</tr>
</table>
</s:form>
<script language="javascript" src="<s:url value="/commons/scripts/checkLength.js"/>"></script>
<script>
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
	
function check(){
	
	if(!validate()){
	document.forms(0).submit();
	}
}
function validate()
	{
		var ev = new Validator();
        with(clientLinkMan){
		    ev.test("notblank", "联系人姓名不能为空", $('cc.name').value);
		  //  ev.test("notblank", "联系地址不能为空", $('cc.address').value);
		  //  ev.test("notblank","固定电话不能为空",$('cc.phone').value);
		  //  ev.test("email","email格式不正确",$('cc.email').value);
		  //  ev.test("notblank", "手机不能为空", $('cc.callPhone').value);
		  //  ev.test("notblank", "所属客户不能为空", $('namex').value);
		}
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
	}
	
	</script>
</body>
</html>