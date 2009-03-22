<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>导入项目号</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">

 function openUrl(pram){
    
     
	window.open('/yx/contract/contractItemManager.action?method=updateConItemId&resNo='+pram,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=170,width=300');
}

function dosubmit(){
if(typeof(document.all.itemsNo)!="undefined"){
   document.forms(0).action="/yx/contract/contractItemManager.action?method=saveItemNo"
   document.forms(0).submit();
   }
   
}

	function inputClick(){
	window.open('/yx/contract/contractProject/contractProjectInput.action?method=inputRightClick','','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=150,width=500');
		} 
	function downLoad(){
	    var oldAction = document.forms(0).action;
		document.forms(0).action="/yx/contract/contractProject/downLoadNoCodeProject.action?method=downLoad"
   		document.forms(0).submit();
   		document.forms(0).action = oldAction;
	}
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
<s:form action="contractItemManager" theme="simple" target="content">
<s:hidden name="itemNo"></s:hidden>
<input name="expId" type="hidden" value="<s:property value="expId"/>"/>
<input name="customerId" type="hidden" value="<s:property value="customerId"/>"/>
<input name="conType" type="hidden" value="<s:property value="conType"/>"/>
<input name="projectDept" type="hidden" value="<s:property value="projectDept"/>"/>
<input name="projectNoState" type="hidden" value="<s:property value="projectNoState"/>"/>
<div align="left">
   <div  style="color:#000000"><p >当前页面：合同管理 -> 导入项目号</p></div>
</div>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td  height="0.5" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="3"></td>
  </tr>
  <tr class="bg_table03">
    <td class="bg_table03"><div>
        <div align="center">         
          <input type="button" name="save3232" value="导  入" onClick="inputClick()" class="button01">
          <input type="button" name="save3232" value="导出无项目号的项目" onClick="downLoad()" class="button01" style="width: 120">
        </div>
      </div></td>
  </tr>
  <tr>
    <td><table width="100%" border="1"   id="recordItemNo" bordercolor="#808080" style=" border-collapse: collapse;">
        <tr>
          <td nowrap   class="bg_table01">
              <div align="center">合同号</div>
          </td>
          <td  nowrap   class="bg_table01"><div align="center">项目号</div></td>
          <td   nowrap  class="bg_table01">
              <div align="center">合同名称</div>
          </td>
          <td   nowrap  class="bg_table01">
              <div align="center">客户名称</div>
            </div></td>
          <td  nowrap   class="bg_table01">
              <div align="center">销售员</div>
            </div></td>
          <td   nowrap  class="bg_table01">
              <div align="center">合同性质</div>
            </div></td>
          <td  nowrap   class="bg_table01">
              <div align="center">工程部门</div>
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
          <td><div align="left"><s:property value="#conclitemp[1].name"/></div></td>
          <td><div align="left">
          <s:iterator value="contractTypeList" id="typeManager">
            <s:if test="#typeManager.typeSmall==#conclitemp[0].conType">
             
              <s:property value="#typeManager.typeName"/>
           </s:if>
        </s:iterator>
          
          </div></td>
          <td><div align="left">
                <s:iterator value="projectDeptTypeList" id="project">
                <s:if test="#project.typeSmall==#conclitemp[3].itemResDept">            
                   <s:property value="#project.typeName"/>
                </s:if>        
                </s:iterator>
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
