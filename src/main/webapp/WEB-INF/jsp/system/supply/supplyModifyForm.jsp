<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>ModifyClient</title>
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
<s:form action="supply" theme="simple">
<s:hidden name="method" value="updateExp" />
<s:hidden name="cc.supplierid"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr> 
		<td valign="top" > 
		<table width="94%" border="0" cellspacing="1" cellpadding="1">
			<tr>
			  <td height="3" align="left" >当前页面:基础管理->供应商修改</td>
		  </tr>
			<tr><td class="bg_table01" height="1"><img src="../../images/temp.gif" width="1" height="1"></td>
			</tr>
		</table>
		<table width="94%" border="0" cellspacing="1" cellpadding="1">
			<tr align="center">
			  <td class="bg_table02" align="right"><font color="red">* </font>供应商名称：</td>
			  <td class="bg_table02" align="left"><s:textfield name="cc.supplierName"></s:textfield></td>
			  <td class="bg_table02" align="right"><font color="red">* </font>供应商代码：</td>
			  <td class="bg_table02" align="left"><s:textfield name="cc.supplierCode"></s:textfield></td>
              
		  </tr>
			<tr align="center">
			  <td class="bg_table02" align="right">供应商开票名称：</td>
			  <td class="bg_table02" align="left"><s:textfield name="cc.nameOfInovice"></s:textfield></td>
			  <td class="bg_table02" align="right">开户银行：</td>
			  <td class="bg_table02" align="left"><s:textfield name="cc.billBank"></s:textfield></td>
		  </tr>

			<tr align="center">
              <td class="bg_table02" align="right"><font color="red">* </font>开户帐号：</td>
			  <td class="bg_table02" align="left"><s:textfield name="cc.billAccount"></s:textfield></td>
			  <td class="bg_table02" align="right">税号：</td>
			  <td class="bg_table02" align="left"><s:textfield name="cc.dutyParagraph"></s:textfield></td>

			<tr align="center">
    			<td class="bg_table02" width="15%" align="right">客户开票地址：</td>
				<td class="bg_table02" width="35%" align="left"><s:textfield name="cc.addressOfInovice"></s:textfield></td>
    			<td class="bg_table02" align="right"><font color="red">* </font>开票电话</td>
    			<td class="bg_table02" align="left"><s:textfield name="cc.phoneOfInovice"></s:textfield></td>   
			</tr>
			<tr align="center">
             
			  <td class="bg_table02" align="right"><LABEL for="client_cc_areaID">供货商地域 ：</td>
			  <td class="bg_table02" align="left"><s:select name="cc.eareCode" list="supplyArea" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
				 </s:select>              </td>
				 <td class="bg_table02" align="right"><LABEL for="client_cc_areaID">供应商类别 ：</td>
			  <td class="bg_table02" align="left"><s:select name="cc.supplierType" list="supplierTypeList" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
				 </s:select>              </td>
		  </tr>
			<tr align="center">
			 
			  
			  
			  <td class="bg_table02" align="left">&nbsp;</td>
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
        with(supply){
		    ev.test("notblank", "供应商不能为空", $('cc.supplierName').value);
		    ev.test("notblank", "供应商代码不能为空", $('cc.supplierCode').value);
		    ev.test("+integer", "开票电话只能为数字", $('cc.phoneOfInovice').value);
			ev.test("+integer","开户帐号只能为数字",$('cc.billAccount').value);
		}
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
	}
	
</script>
</body>
</html>
