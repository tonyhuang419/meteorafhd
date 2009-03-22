<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>修改项目号</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">

 function openUrl(pram){
    
     
	window.open('/yx/contract/contractItemManager.action?method=updateConItemId&resNo='+pram,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=170,width=300');
}

function dosubmit(){
if(typeof(document.all.itemsNo)!="undefined"){
   document.forms(0).action="/yx/contract/contractItemUpdateManager.action?method=saveItemNo"
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

<s:form action="contractItemUpdateManager" theme="simple" target="content">

<input name="expId" type="hidden" value="<s:property value="expId"/>"/>
<input name="customerId" type="hidden" value="<s:property value="customerId"/>"/>
<input name="conType" type="hidden" value="<s:property value="conType"/>"/>
<input name="projectDept" type="hidden" value="<s:property value="projectDept"/>"/>
<input name="projectNoState" type="hidden" value="<s:property value="projectNoState"/>"/>
<div align="left">
   <div  style="color:#000000"><p >当前页面：合同管理 -> 修改项目号</p></div>
</div>
          <font color="red"><s:property value="message"/></font>
<table width="100%" border="1"   id="recordItemNo" bordercolor="#808080" style=" border-collapse: collapse;">
        <tr>
          <td  nowrap   class="bg_table01"><div  >
              <div align="center">合同号</div>
            </div></td>
           <td  nowrap   class="bg_table01"><div align="center">项目号</div></td>
          <td   nowrap  class="bg_table01"><div  >
              <div align="center">合同名称</div>
            </div></td>
          <td  nowrap  class="bg_table01"><div  >
              <div align="center">客户名称</div>
            </div></td>
          <td   nowrap   class="bg_table01"><div  >
              <div align="center">销售员</div>
            </div></td>
          <td   nowrap  class="bg_table01"><div  >
              <div align="center">合同性质</div>
            </div></td>
          <td  nowrap   class="bg_table01"><div  >
              <div align="center">工程部门</div>
            </div></td>
          
          <td width="*"  nowrap  class="bg_table01"><div align="center">操作</div></td>
        </tr>
        
        <s:iterator value="info.result" id="conclitemp" status="status">
        <tr  onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
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
           
          <td width="55"><div align="center"><a href="#" onclick="openUrl('<s:property value="#conclitemp[3].conItemMinfoSid"/>');" >修改</a></div></td>   
                 <input name="itemsId" type="hidden" value="<s:property value="#conclitemp[3].conItemMinfoSid"/>"/>
        </tr>
        </s:iterator>
      </table>

  <table cellSpacing=1 cellPadding=2 width="100%" border=0>
	<tr>
		<td class="bg_table04"><baozi:pages value="info"  beanName="info" formName="forms(0)" /></td>
	</tr>
</table>

</s:form>

</body>
</html>
