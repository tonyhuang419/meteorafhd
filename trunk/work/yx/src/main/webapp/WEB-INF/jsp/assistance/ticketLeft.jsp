<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协发票搜索</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
</head>
<style>
html { overflow-x:hidden;overflow-y:hidden;  }
</style>
<body leftmargin="0">
<iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe>
<s:form theme="simple" action="ticketLeft" target="rightFrame">
	<s:hidden name="method" value="query"/>
	<s:hidden name="resetCondition" value="true"></s:hidden>
<table width="100%" class="bg_table02" >

<tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
	            <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">供应商：</div></td>
	              <td nowrap><s:textfield size="15" name="supply" id="supplyId" readonly="true"/><input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseSup.action');">
	              	<s:hidden id="supplierid" name="supplierid"></s:hidden>
            </td>
  </tr>
  <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">外协合同号：</div></td>
	              <td nowrap>
	              	<s:textfield name="assistanceNo" size="15"></s:textfield>
	              </td>
  </tr>
	            <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">发票类型：</div></td>
	              <td nowrap> <s:select name="ticketType" list="invoiceList" listKey="typeSmall"  
				listValue="typeName"   headerKey="" headerValue="--全部--" id="billType"  ></s:select></td>
  </tr>
                <tr class="bg_table02">
                  <td rowspan="2" align="center" nowrap class="bg_table02"><div align="right">开票时间：</div></td>
                  <td nowrap><div align="left">
                    从
                    <input type="text" id="startDate" name="startDate" size="12" />
                    <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('startDate')" align=absMiddle alt=""  /></div></td>
                </tr>
                <tr class="bg_table02">
                  <td nowrap>至
                  <input type="text" id="endDate" name="endDate" size="12" />
                  <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('endDate')" align=absMiddle alt=""  /></td>
                </tr>
            <tr class="bg_table02">
			  <td colspan="2" nowrap class="bg_table04"><div align="center">
			    <input type="button" value="查    询" class="button02" onclick="if(!validate()){document.forms(0).submit();}">
		      </div></td>
  </tr>
		</table>
		</s:form>		
</body>

<script language="javascript">
	function validate()
	{
		var ev2=new Validator();
		ev2.test("dateYX","开票开始时间格式不正确",$('startDate').value);
		ev2.test("dateYX","开票结束时间格式不正确",$('endDate').value);
		ev2.writeErrors(errorsFrame, "errorsFrame");
		if(ev2.size()>0){
			return true;
		}else{
			return false;
		}
	}
	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
</script>


</html>
