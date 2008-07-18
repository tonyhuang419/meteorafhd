<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>Stock Management</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
</head>
<style>
html { overflow-x:hidden;overflow-y:hidden;  }
</style>
<body leftmargin="0">
<s:form theme="simple" action="ticketLeft" target="rightFrame">
	<s:hidden name="method" value="query"/>
<table height="40%" width="100%"  border=0  cellpadding=1 cellspacing=0>

<tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
	            <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">供应商：</div></td>
	              <td nowrap><s:textfield name="supply" size="20"/>
            </td>
  </tr>
	            <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">发票类型：</div></td>
	              <td nowrap><select name="type">
	              	  <option></option>
                      <option>增票</option>
                      <option>普票</option>
                  </select></td>
  </tr>
                <tr class="bg_table02">
                  <td rowspan="2" align="center" nowrap class="bg_table02"><div align="right">开票时间：</div></td>
                  <td nowrap><div align="left">
                    从
                    <input type="text" id="sDate" name="sDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
                    <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sDate')" align=absMiddle alt=""  /></div></td>
                </tr>
                <tr class="bg_table02">
                  <td nowrap>至
                  <input type="text" id="eDate" name="eDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
                  <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('eDate')" align=absMiddle alt=""  /></td>
                </tr>
            <tr class="bg_table02">
			  <td colspan="2" nowrap class="bg_table04"><div align="center">
			    <input type="submit" value="查    询" class="button02">
		      </div></td>
  </tr>
		</table>
		</s:form>		
</body>
</html>
