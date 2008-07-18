<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<table width="35%">
    <tr align="center">
    <td rowspan="1" nowrap><strong>外协付款申请<s:property value="sum"/>次</strong></td>
      <td class="bg_table02" width="15%" align="right" nowrap><div align="center"><strong>申请序号</strong></div></td>
      <td class="bg_table02" width="20%" align="left" nowrap><div align="center"><strong>申请金额</strong></div></td>
      <td width="16%" align="right" class="bg_table02" nowrap><div align="center"><strong>申请时间</strong></div></td>
      <td width="19%" align="left" class="bg_table02" nowrap><strong>申请状态</strong></td>
    </tr>
    <s:iterator id="result" value="pList">
    <tr>
      <td>&nbsp;</td>
      <td class="bg_table02" width="15%" align="right"><div align="center"><s:property value="id"/></div></td>
      <td class="bg_table02" width="20%" align="left"><div align="center"><s:property value="payNum"/></div></td>
      <td class="bg_table02" align="right"><div align="center"><s:property value="applyDate"/></div></td>
      <td align="left" class="bg_table02"><s:property value="payState"/></td>
    </tr>
    </s:iterator>
  </table>