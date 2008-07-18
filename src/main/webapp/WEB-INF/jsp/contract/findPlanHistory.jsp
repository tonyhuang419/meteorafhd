<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>项目号修改</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/yx/commons/scripts/CalendarSelector.js"/>"></script>
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script src="/yx/commons/scripts/my_f.js"></script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.STYLE3 {
	font-size: 18px;
	font-weight: bold;
}
-->
</style>
</head>
<body>

<p>&nbsp;</p>
<div>
  <div align="center" class="STYLE3">
    <p>开票收款计划变更履历</p>
    <p>&nbsp;</p>
  </div>
</div>
<table width="100%" border=0   cellpadding="1" cellspacing=1 >
<tr>
<td width="33%" class="bg_table02"><div align="right">合同号：</div></td>
<td width="67%" class="bg_table02"><div align="left"><s:property value="htNo"/></div></td>
</tr>
<tr>
  <td class="bg_table02"><div align="right">负责部门：</div></td>
  <td width="67%" class="bg_table02"><div align="left">
    <s:iterator value="projectDeptTypeList" id="pdept">
      <s:if test="#pdept.typeSmall==#request.dept">
          <s:property value="#pdept.typeName"/>
      </s:if>
    </s:iterator>
  
  </div></td>
</tr>
<tr>
  <td class="bg_table02"><div align="right">开票性质：</div></td>
  <td width="67%" class="bg_table02"><div align="left">
  <s:iterator value="openBillType" id="billsOp">
               <s:if test="#billsOp.typeSmall==#request.htxz">
                       <s:property value="#billsOp.typeName"/>
               </s:if>
       </s:iterator>
  
  </div></td>
</tr>
<tr>
  <td colspan="2" class="bg_table02"><hr></td>
  </tr>
</table>

<table width="100%" border=0   cellpadding="1" cellspacing=1 >
  <tr>
    <td width="35%"  class="bg_table02"><div align="center"></div></td>
    <td width="11%" class="bg_table02"><div align="center">开票计划时间</div></td>
    <td width="15%" class="bg_table02"><div align="center">收款计划时间</div></td>
    <td width="39%" class="bg_table02"><div align="center">变更说明</div></td>
  </tr>
  <tr>
    <td  class="bg_table02"><div align="right">原始记录：</div></td>
    <td class="bg_table02"><div align="center"><s:date name="billpro.initBillDate" format="yyyy-M-d"/></div></td>
    <td class="bg_table02"><div align="center"><s:date name="billpro.initReceDate" format="yyyy-M-d"/></div></td>
    <td class="bg_table02"><div align="left"><s:property value="billpro.billInfo"/></div></td>
  </tr>
  <s:iterator value="bilhistoryList" id="hisbill" status="status"><s:property value="#status.index+1"/>
  <tr>
    <td  class="bg_table02"><div align="right">第<s:property value="#status.index+1"/>次变更：</div></td>
    <td class="bg_table02"><div align="center"><s:date name="#hisbill.afterChangeDate" format="yyyy-M-d"/></div></td>
    <td class="bg_table02"><div align="center"><s:date name="#hisbill.afterReceDate" format="yyyy-M-d"/></div></td>
    <td class="bg_table02"><div align="left"><s:property value="#hisbill.changeExp"/></div></td>
  </tr>
    </s:iterator>
    <tr>
    <td colspan="7"  class="bg_table02">
      <div align="center">
        <input type="submit" name="button2" id="button2" value="关闭" class="button01"  onclick="window.close();">
      </div></td>
  </tr>
</table>
</body>
</html>
