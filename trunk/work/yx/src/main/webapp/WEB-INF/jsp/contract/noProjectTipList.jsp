<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>导入项目号</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">

</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.STYLE2 {color: #000000}
-->
</style>
</head>
<body>
<s:form action="contractItemManager" theme="simple">
<input name="method" type="hidden" value="queryNoProjectCode"/>
<div align="left">
   <div  style="color:#000000"><p >当前页面：首页提醒 -> 无项目号的合同</p></div>
</div>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td><table width="100%" border="1"   id="recordItemNo" bordercolor="#808080" style=" border-collapse: collapse;">
        <tr>
          <td width="100%" colspan="7" class="bg_table02">
          	总计合同数：<s:property value="noProjectCodeConCount"/>&nbsp;&nbsp;&nbsp;&nbsp;总计项目数：<s:property value="info.totalCount"/>
          </td>
        </tr>
        <tr>
          <td width="90" nowrap   class="bg_table01">
              <div align="center">合同号</div>
          </td>
          <td width="140" nowrap  class="bg_table01"><div align="center">项目号</div></td>
          <td width="150" nowrap  class="bg_table01">
              <div align="center">合同名称</div>
          </td>
          <td width="100" nowrap class="bg_table01">
              <div align="center">客户名称</div>
            </div></td>
          <td width="100" nowrap class="bg_table01">
              <div align="center">项目金额</div>
            </div></td>
          <td width="100" nowrap class="bg_table01">
              <div align="center">工程部门</div>
            </div></td>
          <td width="100" nowrap  class="bg_table01">
              <div align="center">销售员</div>
            </div></td>
        </tr>
        
        <s:iterator value="info.result" id="conclitemp" status="status">
        <tr onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
          <td><div align="left"><s:property value="#conclitemp[0].conId"/></div></td>
          <td><div align="left">
              <s:property value="#conclitemp[3].conItemId"/>
            </div></td>
          <td><div align="left"><s:property value="#conclitemp[0].conName"/></div></td>
          <td><div align="left"><s:property value="#conclitemp[2].name"/></div></td>
          <td><div align="left"><s:property value="#conclitemp[4]"/></div></td>
          <td><div align="left">
          	<s:property value="typeManageService.getYXTypeManage(1018,#conclitemp[3].itemResDept).typeName"/>
          </div></td>
          <td><div align="left">
			<s:property value="#conclitemp[1].name"/>
          </div></td>
        </tr>
        </s:iterator>
      </table></td>
  </tr>
  <TR>
    <TD class="bg_table02"><DIV align=center>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr>
					<td><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
      </DIV></TD>
  </TR>
</TABLE>
</s:form>
</body>
</html>