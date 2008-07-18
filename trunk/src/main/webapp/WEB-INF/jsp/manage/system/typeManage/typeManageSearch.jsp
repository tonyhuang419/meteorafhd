<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>类型管理搜索</title>
</head>

<body>
<s:form action="typeManageQuery" target="rightFrame" theme="simple">
<s:hidden name="resetCondition" value="true"/>
<table height="40%"  border=0  cellpadding=1 cellspacing=0>
          <tr>
            	<td colspan="3" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
		  <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">类型编号：</div></td>
		          <td  colspan="2"  nowrap><div align="left"><s:textfield name="typeSmall"></s:textfield></td>
          </tr>
		        <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">类型种类：</div></td>
		          <td  colspan="2"  nowrap><div align="left"><s:select  name="typeBig" list="yxBigTypeMap" emptyOption="true" ></s:select>
	              </div></td>
          </tr>
          <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">类型名称：</div></td>
		          <td  colspan="2"  nowrap><div align="left"><s:textfield name="typeName"></s:textfield>
	              </div></td>
          </tr>          
          <tr class="bg_table02">
				  <td rowspan="2" align="center" nowrap class="bg_table02"><div align="right">最后修改时间：</div></td>
				  <td nowrap><div align="left">从<input type="text" id="bidDate1"
						name="lastUpdatedDate1" readonly="readonly"
						onClick="javascript:ShowCalendar(this.id)" size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate1')" align=absMiddle
						alt="" /></div></td>
  </tr>
				<tr class="bg_table02">
                  <td nowrap><div align="left">至<input type="text" id="bidDate2"
						name="lastUpdatedDate2" readonly="readonly"
						onClick="javascript:ShowCalendar(this.id)" size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate2')" align=absMiddle
						alt="" /></div></td></tr>
                
            <tr class="bg_table02">
			  <td colspan="3" nowrap class="bg_table04"><div align="center">
			    <input type="submit" value=" 查  询 "  class="button02">
		      </div></td>
  </tr>
		</table>
<!--<script>
	jsLab_CreateSelectInput(document.getElementById("clientName"));
</script>
--></s:form>		
</body>
</html>