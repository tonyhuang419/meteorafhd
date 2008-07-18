<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>合同确认</title>
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
<input name="expId" type="hidden" value="<s:property value="expId"/>"/>
<input name="customerId" type="hidden" value="<s:property value="customerId"/>"/>
<input name="conType" type="hidden" value="<s:property value="conType"/>"/>
<input name="projectDept" type="hidden" value="<s:property value="projectDept"/>"/>
<input name="projectNoState" type="hidden" value="<s:property value="projectNoState"/>"/>
<div align="left">
   <div  style="color:#000000"><p >当前页面：合同管理 -> 录入项目号</p></div>
</div>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td  height="0.5" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr class="bg_table03">
    <td class="bg_table03"><div>
        <div align="center">
          <input type="button" name="save3232" value="录入保存" onclick="dosubmit();" class="button01">
          &nbsp;&nbsp;&nbsp;<input type="button" name="save3232" value="导  入" onClick="inputClick()" class="button01">
        </div>
      </div></td>
  </tr>
  <tr>
    <td><table width="100%" border="0"   id="recordItemNo">
        <tr>
          <td width="91" nowrap   class="bg_table01"><div  >
              <div align="center">合同号</div>
            </div></td>
          <td width="180" nowrap  class="bg_table01"><div  >
              <div align="center">合同名称</div>
            </div></td>
          <td width="127"  nowrap  class="bg_table01"><div  >
              <div align="center">客户名称</div>
            </div></td>
          <td width="105" nowrap   class="bg_table01"><div  >
              <div align="center">销售员</div>
            </div></td>
          <td width="87"  nowrap  class="bg_table01"><div  >
              <div align="center">合同性质</div>
            </div></td>
          <td width="128" nowrap   class="bg_table01"><div  >
              <div align="center">工程部门</div>
            </div></td>
          <td width="147" nowrap   class="bg_table01"><div align="center">项目号</div></td>
          <td width="55"  nowrap  class="bg_table01"><div align="center">操作</div></td>
        </tr>
        
        <s:iterator value="info.result" id="conclitemp" status="status">
        <tr>
          <td><div align="center"><s:property value="#conclitemp[0].conId"/></div></td>
          <td><div align="center"><s:property value="#conclitemp[0].conName"/></div></td>
          <td><div align="center"><s:property value="#conclitemp[2].name"/></div></td>
          <td><div align="center"><s:property value="#conclitemp[1].name"/></div></td>
          <td><div align="center">
          <s:iterator value="contractTypeList" id="typeManager">
            <s:if test="#typeManager.typeSmall==#conclitemp[0].conType">
             
              <s:property value="#typeManager.typeName"/>
           </s:if>
        </s:iterator>
          
          </div></td>
          <td><div align="center">
                <s:iterator value="projectDeptTypeList" id="project">
                <s:if test="#project.typeSmall==#conclitemp[3].itemResDept">
                
                   <s:property value="#project.typeName"/>
                </s:if>
               
          
                </s:iterator>
          </div></td>
           <s:if test="#conclitemp[3].conItemId!=null">
          <td onClick="showlist('info2')"  ><div align="center">
            
              <s:property value="#conclitemp[3].conItemId"/>
<%--              <input name="itemsId" type="hidden" value="<s:property value="#conclitemp[3].conItemMinfoSid"/>"/>--%>
            </div></td>
          <td width="55"><div align="center"><a href="#" onclick="openUrl('<s:property value="#conclitemp[3].conItemMinfoSid"/>');" >修改</a></div></td>
          </s:if>
          <s:else>
             <td onClick="showlist('info2')"  ><div align="center">
                 <input name="itemsId" type="hidden" value="<s:property value="#conclitemp[3].conItemMinfoSid"/>"/>
                  <input id="itemsNo" name="itemsNo" type="text"  size="15" maxlength="20"/>
            </div></td>
          <td width="55"><div align="center">&nbsp;</div></td>
          </s:else>
        </tr>
        </s:iterator>
      </table></td>
  </tr>
  <TR>
    <TD   height=20 class="bg_table02"><DIV align=center>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /><br></td>
				</tr>
			</TABLE>
      </DIV><br></TD>
  </TR>
</TABLE>
</s:form>
</body>
</html>
