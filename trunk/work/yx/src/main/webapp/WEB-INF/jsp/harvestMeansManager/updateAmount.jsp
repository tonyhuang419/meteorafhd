<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>modifyreveinfo</title>
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
<body leftmargin="0">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<s:form action="harvestMeansManager" theme="simple">
		<s:hidden name="method" value="updateAmount" />
		<s:hidden name="ri.id"></s:hidden>
		<s:hidden name="ri.billSid"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr> 
		<td valign="top" > 
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
			  <td height="3" align="left" >当前页面：收款管理->收款明细修改</td>
		  </tr>
			<tr><td class="bg_table01" height="1"><img src="../../images/temp.gif" width="1" height="1"></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr align="center">
			  <td class="bg_table02" align="right"><font color="red">* </font>到款金额：</td>
			  <td class="bg_table02" align="left"><s:textfield name="ri.amount" onblur="formatInputNumber(this)"></s:textfield></td>
			 
		  </tr>
			<tr align="center">
			  <td class="bg_table02" align="right"><font color="red">* </font>到款方式：</td>
			  <td class="bg_table02" align="left"><s:select name="ri.receType" list="receTypetrans" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
                  
                </s:select>              </td>
			 
		  </tr>

			<tr align="center">
              <td class="bg_table02" align="right"><font color="red">* </font>到款时间：</td>
			 <td class="bg_table02" align="left"> <input type="text" id="amountTime" name="ri.amountTime" value="<s:date name="ri.amountTime" format="yyyy-MM-dd" />"  readonly="readonly"  onclick="ShowCalendar(this.id);">
			 <img src="/yx/commons/images/calendar.gif" onClick="time(this);"  alt=""/>
		
			    </td>  
		  </tr>

			
			<tr align="center">
             
			<tr class="bg_table03" align="center" style="height:42px">
				<td colspan="4">
				<Table style="width:0%;100%">
					<tfoot class="bg_table03" style="height:42px">
						<tr>
							<td align="right" colspan="2">
                                <div align="center">
                                	<input type="button"  name="addBtn" value=" 修  改 " onclick="check();"  class="button01" />
								</td>                             
                         
					    <td width="43" colspan="2">		<input class="button01" type="button" value="返    回" onclick="javascript:bbb()"></td></tr>
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

function time(img){
  var par=img.parentNode;
  var text=par.firstChild;
  ShowCalendar(text.id);
}

function check(){
	if(!validate()){
		var arriveAmount =parseFloatNumber('<s:property value="ri.amount"></s:property>)');
		var arriveInvoiceAmount = parseFloatNumber('<s:property value="ii.invoiceAmount"></s:property>');
		var arriveTotalAmount = parseFloatNumber('<s:property value="ii.receAmount"></s:property>');
		var inputAmout = parseFloatNumber($('ri.amount').value);
		
			
		if(arriveInvoiceAmount < (inputAmout - arriveAmount + arriveTotalAmount)){
			alert("到款总金额不能大于发票金额");
			return ;
		}
		document.forms(0).submit();
	}
}
function validate()
	{

		var ev = new Validator();
        with(harvestMeansManager){
            ev.test("+float", "到款金额只能为数字", $('ri.amount').value);
		    ev.test("notblank", "到款方式不能为空", $('ri.receType').value);
		    ev.test("notblank", "到款时间不能为空", $('ri.amountTime').value);
		   	
		}
			ev.writeErrors(errorsFrame, "errorsFrame");
		if (ev.size() > 0) {
		
			return true;
		}
	}
function bbb(){

         location.href="../harvestMeansManager/harvestMeansSearch.action?method=doDefault";
       
    
      }
      
</script>
</body>
</html>
