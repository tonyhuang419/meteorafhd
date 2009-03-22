<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>

<title>草稿合同管理搜索列表</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
function checkMath(){
   var checkItem = document.getElementsByName("mainId");   
    for(var i=0;i<checkItem.length;i++){ 
       if(checkItem[i].checked==true) { 
             return true;
        }     
     }
     return false;   
}
 function doChangeToCon(){
     
    var flag=checkMath();
    if(flag==false){
        alert("请选择要改变的项!");
        return;
    }
    if(confirm("确定要确认通过吗？")){
    	document.forms(0).action="/yx/contract/defineSerachList.action?method=cirformOKToContractMain";
    	document.forms(0).submit();
    }
 }
 function doChangeReturn(){
     
    var flag=checkMath();
    if(flag==false){
        alert("请选择要改变的项!");
        return;
    }
    if(confirm("确定要确认退回吗？")){
	    document.forms(0).action="/yx/contract/defineSerachList.action?method=removeChangeConMains";
	    document.forms(0).submit();
    }
 }
function openUrlSelf(url){
	window.open(url,'_self','');
}

//用于合同预结算转决算明细
function openConPriToFA(obj){
	var form = document.forms[0]; 
	var baseURL="/yx/contract/contractPriororFAtoFinalAccount.action?cmisysid=";
	var con_sid = obj.parentNode.all.mainId.value;
	var con_url = baseURL + con_sid;
	form.action = con_url+"&opInterface=2";
	form.submit();				
}

//增加单击函数
function addOnclickFunction(){
    var tablename = document.getElementById("sketchConList");
    var tr=tablename.getElementsByTagName("tr");
    for(var i=1 ;i<tr.length;i++){
    	 var td=tr[i].getElementsByTagName("td");  	 	
    	 for(j=1;j<td.length;j++){
    	 	td[j].onclick=function(){
            openConPriToFA(this); 	 
    	 } 
       }
    }
}

function openUrlSelf(url){
	window.open(url,'_self','');
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
<body leftmargin="0">
<div align="left">
  <p  style="color:#000000">当前页面：合同管理->结算确认</p>
</div>

<s:form action="defineSerachList" target="content" theme="simple" >
<input type="hidden" name="contractName" value="<s:property value="contractName"/>"/>
<input type="hidden" name="contractNo" value="<s:property value="contractNo"/>"/>
<input type="hidden" name="ContractType" value="<s:property value="ContractType"/>"/>

<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
        <tr>
          <td  height="0.5" colspan="2" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="3"></td>
        </tr>
        <tr class="bg_table02">   
          <td align="right" class="bg_table03">
            <div align="center">
              &nbsp;&nbsp;<input type="button" name="save3232" value="通  过" onClick="doChangeToCon();" class="button01">
              <input type="button" name="save3232" value="退  回" onClick="doChangeReturn();" class="button01">
            </div></td>
        </tr>
<table width="100%" border="1" align="center" id="sketchConList"  bordercolor="#808080" style=" border-collapse: collapse;">
  
  <tr>
    <td width="10" align="center" class="bg_table01" ><div align="center">&nbsp;</div></td>
    <td width="231" align="center" class="bg_table01" ><div align="center">合同名称</div></td>
     <td width="107" align="center" class="bg_table01" ><div align="center">合同号</div></td>
    <td width="183" align="center" class="bg_table01" ><div align="center">客户名称</div></td>
    <td width="122" align="center" class="bg_table01" ><div align="center">销售员</div></td>
    <td width="119" align="center" class="bg_table01" ><div align="center">合同性质</div></td>
    <td width="121" align="center" class="bg_table01" ><div align="center">合同金额</div></td>
    <td width="107" align="center" class="bg_table01" ><div align="center">签订日期</div></td>
    <td width="188" align="center" class="bg_table01" ><div align="center">主项目负责部门</div></td>
    <td width="126"  align="center" class="bg_table01" ><div align="center">合同状态</div></td>
  </tr>
  <s:iterator value="info.result" id="contract" status="status">
   <tr onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
    <td width="10" align="left"><div align="center"><input type="checkbox" name="mainId" id="mainId" value="<s:property value="#contract[0].conMainInfoSid"/>" /></div></td>
    <td width="231" align="left"  ><div align="center"><s:property value="#contract[0].conName"/></div></td>
    <td width="107" align="left"><div align="center"><s:property value="#contract[0].conId"/></div></td>
    <td width="183"  align="left"  ><div align="center">
      <s:iterator value="yXClientCodeList" id="clietCode">
             <s:if test="#clietCode.id==#contract[0].conCustomer">
                  <s:property value="#clietCode.name"/>
             </s:if>
      </s:iterator>
    </div></td>
    <td width="122"  align="left" ><div align="left"><s:property value="#contract[1].name"/></div></td>
    <td width="119"  align="left" ><div align="left">
     <s:iterator value="contractTypeList" id="typeManager">
       <s:if test="#typeManager.typeSmall==#contract[0].conType">
             
              <s:property value="#typeManager.typeName"/>
       </s:if>
    </s:iterator>
    </div></td>
    <td width="121"  align="right" ><div align="right"><s:property value="#contract[0].conTaxTamount"/></div></td>
    <td width="107" align="center"><div align="center"><s:property value="#contract[0].conSignDate"/></div></td>
    <td width="188"  align="left" ><div align="left">
      <s:iterator value="projectDeptTypeList" id="pdept">
        <s:if test="#pdept.typeSmall==#contract[0].mainItemDept">
            <s:property value="#pdept.typeName"/>
        </s:if>
      </s:iterator>
    
    </div>
    </td>
   
    <td width="126"  align="left" ><div align="left"><s:property value="@com.baoz.yx.tools.ContractStateTool@getContractStateSnToName(#contract[0].conState)" /></div></td>
    
    
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
<script type="text/javascript">
if("<s:property value="msgAlert"/>"!=""){
   alert("<s:property value="msgAlert"/>");
}

addOnclickFunction();
</script>
</body>
</html>
