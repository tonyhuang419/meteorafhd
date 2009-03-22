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
function opentomanage(id){
 location.href="/yx/contract/contract.action?method=Modify&isModify=1&mainid="+id;
}

//function openCon(obj){
//	var baseURL="/yx/contract/formalContractManage/formalContractManage.action?cmisysid=";
//	var con_url = baseURL + obj;
//	location.href = con_url;				
//}


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
  <div  style="color:#000000"> <p>当前页面：合同管理 -> 草稿合同管理</p></div>
</div>
<s:form action="searchContractList" target="content" theme="simple">
<input type="hidden" name="conType" value="<s:property value="conType"/>"/>
<input type="hidden" name="name" value="<s:property value="name"/>"/>
<input type="hidden" name="minConSignDate" value="<s:property value="minConSignDate"/>"/>
<input type="hidden" name="maxConSignDate" value="<s:property value="maxConSignDate"/>"/>
<input type="hidden" name="conState" value="<s:property value="conState"/>"/>
<table width="100%" border="1" align="center" id="sketchConList"  bordercolor="#808080" style=" border-collapse: collapse;">
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
   <tr onclick="opentomanage(<s:property value="#contract[0].conMainInfoSid"/>)"   onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
    <td width="231" align="center"  ><div align="left"><s:property value="#contract[0].conName"/></div></td>
    <td width="183"  align="center"  ><div align="left">
      <s:iterator value="yXClientCodeList" id="clietCode">
             <s:if test="#clietCode.id==#contract[0].conCustomer">
                  <s:property value="#clietCode.name"/>
             </s:if>
      </s:iterator>
    </div></td>
    <td width="120"  align="center" ><div align="left"><s:property value="#contract[1].name"/></div></td>
    <td width="110"  align="center" ><div align="left">
     <s:iterator value="contractTypeList" id="typeManager">
       <s:if test="#typeManager.typeSmall==#contract[0].conType">
             
              <s:property value="#typeManager.typeName"/>
       </s:if>
    </s:iterator>
    </div></td>
    <td width="120"  align="center" ><div align="right">
    <s:if test="#contract[0].standard == 1" >
    <s:property value="#contract[0].conTaxTamount"/>
    </s:if>   
    <s:else>
      <s:property value="#contract[0].conNoTaxTamount"/>
    </s:else>
    </div></td>
    <td width="100" align="center"><div align="center"><s:property value="#contract[0].conSignDate"/></div></td>
    <td width="180"  align="center" ><div align="left">
      <s:iterator value="projectDeptTypeList" id="pdept">
        <s:if test="#pdept.typeSmall==#contract[0].mainItemDept">
            <s:property value="#pdept.typeName"/>
        </s:if>
      </s:iterator>
    
    </div>
    </td>
   
    <td width="120"  align="center" ><div align="left"><s:property value="@com.baoz.yx.tools.ContractStateTool@getContractStateSnToName(#contract[0].conState)" /></div></td>
    
    
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
