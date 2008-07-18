<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>addNewClient</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">
	</script>
<link href="../../shaoyx/css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="css/calendar-win2k-cold-1.css" title="win2k-cold-1" />
  <script type="text/javascript" src="js/calendar.js"></script>
  <script type="text/javascript" src="js/calendar-zh.js"></script>
  <script type="text/javascript" src="js/calendar-setup.js"></script>
  <script src="<s:url value="/commons/scripts/mootools-release-1[1].11.js"/>" type="text/javascript"></script>
  <script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
</script>
<SCRIPT src="js/CalendarSelector.js"></SCRIPT>
</head>
<body>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<s:form action="client" theme="simple">
<s:hidden name="method" value="saveExployee" />
<s:hidden name="cc.id"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr> 
		<td valign="top" > 
		<table width="94%" border="0" cellspacing="1" cellpadding="1">
			<tr>
			  <td height="3" align="left" >当前页面:基础管理->客户新增</td>
		  </tr>
			<tr><td class="bg_table01" height="1"><img src="../../images/temp.gif" width="1" height="1"></td>
			</tr>
		</table>
		<table width="94%" border="0" cellspacing="1" cellpadding="1">
			<tr align="center">
			  <td class="bg_table02" align="right"><font color="red">* </font>姓名：</td>
			  <td class="bg_table02" align="left"><input type="text" name="cc.name" size="20" /></td>
			  <td class="bg_table02" align="right">行业类别：</td>
			  <td class="bg_table02" align="left"><s:select name="cc.businessID" list="businessType" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
                  
                </s:select>              </td>
              
		  </tr>
			<tr align="center">
			  <td class="bg_table02" align="right"><font color="red">* </font>性质：</td>
			  <td class="bg_table02" align="left"><s:select name="cc.clientNID" list="clientNature" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue=""> </s:select></td>
			  <td class="bg_table02" align="right"><font color="red">* </font>ERP编号：</td>
			  <td class="bg_table02" align="left"><input type="text" name="cc.userCode" size="20" /></td>
		  </tr>

			<tr align="center">
              <td class="bg_table02" align="right">开户银行：</td>
			  <td class="bg_table02" align="left"><input type="text" name="cc.billBank" size="20" /></td>
			  <td class="bg_table02" align="right"><font color="red">* </font>开户帐号：</td>
			  <td class="bg_table02" align="left"><input type="text" name="cc.account" size="20" /></td>           
		  </tr>

			<tr align="center">
    			<td class="bg_table02" width="15%" align="right">税号：</td>
				<td class="bg_table02" width="35%" align="left"><input type="text" name="cc.taxNumber" size="20" /></td>   
    			<td class="bg_table02" align="right">开票地址</td>
    			<td class="bg_table02" align="left"><input type="text" name="cc.billAdd" size="20" /></td>   
			</tr>
			<tr align="center">
              <td class="bg_table02" align="right"><font color="red">* </font>开票电话：</td>
			  <td class="bg_table02" align="left"><input type="text" name="cc.billPhone" size="20" /></td>
			  <td class="bg_table02" align="right"><LABEL for="client_cc_areaID"><font color="red">* </font>地域</LABEL>
			    ：</td>
			  <td class="bg_table02" align="left"><s:select name="cc.areaID" list="clientArea" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
				 </s:select>              </td>
		  </tr>
			<tr align="center">
			<td class="bg_table02" align="right">开票名称：</td>
			  <td class="bg_table02" align="left"><input type="text" name="cc.billName" size="20" /></td>
			  <td class="bg_table02" align="right"><font color="red">* </font>行业市场：</td>
			  <td class="bg_table02" align="left"><s:select name="cc.businessAreaID" list="businessArea" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
                  
                </s:select>              </td>
                
                <tr align="center">
                 <td class="bg_table02" align="right" width="183">是否为项目单位：</td>
			  <td class="bg_table02" align="left">否<input type="radio" checked name="cc.isEventUnit"  value="0">是<input name="cc.isEventUnit" type="radio"  value="1"></td>
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
                         
					    <td width="43" colspan="2">		<input class="button01" type="button" value="返    回" onClick="javascript:history.go(-1)"></td>		      </tr>
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
function check(){
	
	if(!validate()){
	document.forms(0).submit();
	}
}
function validate()
	{
		var ev = new Validator();
        with(client){
		    ev.test("notblank", "客户不能为空", $('cc.name').value);
		    ev.test("notblank", "客户性质不能为空", $('cc.clientNID').value);
		    ev.test("notblank", "ERP编号不能为空", $('cc.userCode').value);
		    ev.test("notblank", "客户开票电话不能为空", $('cc.billPhone').value);
			ev.test("notblank", "客户地域不能为空", $('cc.areaID').value);
			ev.test("notblank","开户帐号不能为空",$('cc.account').value);
			ev.test("notblank", "行业市场不能为空", $('cc.businessAreaID').value);
		}
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
	}
	
</script>
</body>
</html>
