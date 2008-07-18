<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>

<title>草稿合同管理搜索列表</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
 function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
function refresh()
    {
    	window.parent.window.leftframe.upFrame.location.reload();   
    }
</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.STYLE1 {
	font-size: 16px
}
-->
</style>
</head>
<body onload="refresh();">
<div align="left">
  <p>当前页面：合同管理 -> 草稿合同管理</p>
</div>
<s:form action="contractCustoms" target="content" theme="simple">
<input type="hidden" name="clietId" value="<s:property value="clietId"/>"/>

<table width="100%" border="0" align="center" id="sketchConList" >
  <tr class="bg_table01">
    <td align="right" colspan="8" height="3" class="bg_table01"></td>
  </tr>
  <tr>
    <td width="231" align="center" class="bg_table01" ><div align="center">合同名称</div></td>
    <td width="183" align="center" class="bg_table01" ><div align="center">客户名称</div></td>
    <td width="122" align="center" class="bg_table01" ><div align="center">销售员</div></td>
    <td width="119" align="center" class="bg_table01" ><div align="center">合同性质</div></td>
    <td width="121" align="center" class="bg_table01" ><div align="center">合同金额</div></td>
    <td width="107" align="center" class="bg_table01" ><div align="center">签订日期</div></td>
    <td width="188" align="center" class="bg_table01" ><div align="center">主项目负责部门</div></td>
    <td width="126"  align="center" class="bg_table01" ><div align="center">合同状态</div></td>
  </tr>
  <s:iterator value="info.result" id="contract">
   <tr onClick="openUrlSelf('草稿合同管理.html')">
  
    <td width="231" align="center"  ><div align="center"><s:property value="#contract.conName"/></div></td>
    <td width="183"  align="center"  ><div align="center">
      <s:property value="clientCode.name"/>
    </div></td>
    <td width="122"  align="center" ><div align="center"><s:property value="employee.name"/></div></td>
    <td width="119"  align="center" ><div align="center">
     <s:iterator value="contractTypeList" id="typeManager">
       <s:if test="#typeManager.typeSmall==#contract.conType">
             
              <s:property value="#typeManager.typeName"/>
       </s:if>
    </s:iterator>
    </div></td>
    <td width="121"  align="center" ><div align="center"><s:property value="#contract.conTaxTamount"/></div></td>
    <td width="107" align="center"><div align="center"><s:property value="#contract.conSignDate"/></div></td>
    <td width="188"  align="center" ><div align="center"><s:property value="#contract.mainItemDept"/></div></td>
   
    <td width="126"  align="center" ><div align="center"><s:property value="@com.baoz.yx.tools.ContractStateTool@getContractStateSnToName(#contract.conState)" /></div></td>
    
    
  </tr>
  </s:iterator>
</table>

<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
</s:form>
</body>
</html>
