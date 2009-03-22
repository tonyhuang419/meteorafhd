<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibsNoScript.jsp"%>
<table width="35%">
    <tr align="center">
      <td class="bg_table02" width="15%" align="right" nowrap><div align="center"><strong>申请序号</strong></div></td>
      <td class="bg_table02" width="20%" align="left" nowrap><div align="center"><strong>申请金额</strong></div></td>
      <td width="16%" align="right" class="bg_table02" nowrap><div align="center"><strong>申请时间</strong></div></td>
      <td width="19%" align="left" class="bg_table02" nowrap><strong>申请状态</strong></td>
    </tr>
    <s:iterator status="resultIndex"  value="pList">
    <tr>
      <td class="bg_table02">&nbsp;</td>
      <td class="bg_table02" width="15%" align="right"><div align="center"><s:property value="#resultIndex.index+1"/></div></td>
      <td class="bg_table02" width="20%" align="left"><div align="center"><s:property value="payNum"/></div></td>
      <td class="bg_table02" align="right"><div align="center"><s:property value="applyDate"/></div></td>
      <td align="left" class="bg_table02">
      		<s:if test="payState==0">
      			草稿
      		</s:if>
      		<s:elseif test="payState==1">
      			待确认
      		</s:elseif>
      		<s:elseif test="payState==2">
      			确认通过
      		</s:elseif>
      </td>
    </tr>
    </s:iterator>
  </table>