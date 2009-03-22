<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>

<title>结算转决算列表</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/yx/commons/scripts/mootools-1.2-core-jm.js"></script>
<script language="javascript">
var typeArr=new Array();
  <s:iterator value="info.result" id="contract" status="status">
  typeArr[typeArr.length]=new Array("<s:property value="#contract[0].conMainInfoSid"/>","<s:property value="#contract[2]"/>");
  </s:iterator>
var paramValue=-1;
function setMainidValue(frhg){
   paramValue=frhg.value;
}
function getTypeById(bid){
	var returnValue=-1;
	for(var k=0;k<typeArr.length;k++){
		if(typeArr[k][0]==bid){
		returnValue=typeArr[k][1];
		break;
		}
	}
	return returnValue;
}
 function doSubmit(){
     
    var radNodes=document.forms(0).connid;
    var paramValue="";
    var flag=false;
    if(radNodes.length!=null){
	 for(var k=0;k<radNodes.length;k++)
	 {
	 	if(radNodes[k].checked)
	 	{
	 		paramValue=radNodes[k].value;
	 		flag=true;
	 		break;
	 	}
	 }
	 }else{
	 	if(radNodes.checked){
	 	paramValue=radNodes.value;
	 	flag=true;
	 	}
	 }

	 if(flag){
	 		//没有转和保存，退回的都可以进行结算转决算
	 	if(getTypeById(paramValue)!=-1&&(getTypeById(paramValue)==0||getTypeById(paramValue)==null||getTypeById(paramValue)==""||getTypeById(paramValue)==2)){
    	 document.forms(0).action="/yx/contract/finalToclose.action?mainid="+paramValue;
     	 document.forms(0).submit();
     	 }else{
     	 	alert("您选择的合同不能进行结算转决算！");
     	 }
    }else{
    	alert("您还没有选择，请选择！");
    }
     
 }
 function applySubmit()
 {
 	var radNodes=document.forms(0).connid;
    var paramValue="";
    var flag=false;
    if(radNodes.length!=null){
	 for(var k=0;k<radNodes.length;k++)
	 {
	 	if(radNodes[k].checked)
	 	{
	 		paramValue=radNodes[k].value;
	 		flag=true;
	 		break;
	 	}
	 }
	 }else{
	 	if(radNodes.checked){
	 	paramValue=radNodes.value;
	 	flag=true;
	 	}
	 }

	 if(flag){
	 	//退回和保存的都可以进行提交
	 	if(getTypeById(paramValue)!=-1 &&getTypeById(paramValue)!=null&&getTypeById(paramValue)!="") {
	    	 if(confirm("确定要提交申请吗？")){
		    	 document.forms(0).action="/yx/contract/searchReservationReturn.action?method=applySubmit&mainid="+paramValue;
		     	 document.forms(0).submit();
	     	 }
     	 }else{
     	 	alert("您选择的合同不能进行申请提交！");
     	 }
    }else{
    	alert("您还没有选择，请选择！");
    }
 }

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
function openConPriToFA(obj){
	var form = document.forms[0]; 
	var baseURL="/yx/contract/contractPriororFAtoFinalAccount.action?cmisysid=";
	var con_sid = obj.parentNode.all.conid.value;
	//alert(con_sid);
	var con_url = baseURL + con_sid;
	form.action = con_url+"&opInterface=1";
	form.submit();				
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
<body>
<div align="left">
  <div  style="color:#000000"> <p>当前页面：合同管理 -> 结算转决算</p></div>
</div>
<s:form action="searchReservationReturn.action" target="content" theme="simple">
	<table align="center" border=0 cellpadding=1 cellspacing=1 width="100%">
		<tr>
			<td align="right" class="bg_table01" height="3"><img
				src="./../images/temp.gif" width="1" height="1"></td>
		</tr>
		<tr class="bg_table02">
			<td align="right" class="bg_table03">
			<div align="center">
			<input type="button" value="结算转决算" class="button01" onclick="doSubmit();">
			<input type="button" value="申请提交" class="button01" onclick="applySubmit();">
			</div>
			</td>
		</tr>
	</table>
<table width="100%" border="1" align="center" id="sketchConList" bordercolor="#808080" style=" border-collapse: collapse;">
  <tr>
    <td width="*" align="center" class="bg_table01" ><div align="center">&nbsp;</div></td>
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
    <td width="15"<div align="center"><input type="radio" name="connid" id="conid" value="<s:property value="#contract[0].conMainInfoSid"/>" onclick="setMainidValue(this)" <s:if test="#contract[2]==1">disabled="disabled"</s:if>/></div></td>
    <td width="231"><div align="left"><s:property value="#contract[0].conName"/></div></td>
    <td width="107"><div align="left"><s:property value="#contract[0].conId"/></div></td>
    <td width="183"><div align="left">
      <s:iterator value="yXClientCodeList" id="clietCode">
             <s:if test="#clietCode.id==#contract[0].conCustomer">
                  <s:property value="#clietCode.name"/>
             </s:if>
      </s:iterator>
    </div></td>
    <td width="122"><div align="left"><s:property value="#contract[1].name"/></div></td>
    <td width="119"><div align="left">
     <s:iterator value="contractTypeList" id="typeManager">
       <s:if test="#typeManager.typeSmall==#contract[0].conType">
             
              <s:property value="#typeManager.typeName"/>
       </s:if>
    </s:iterator>
    </div></td>
    <td width="121" ><div align="right"><s:property value="#contract[0].conTaxTamount"/></div></td>
    <td width="107"><div align="center"><s:property value="#contract[0].conSignDate"/></div></td>
    <td width="188" ><div align="left">
      <s:iterator value="projectDeptTypeList" id="pdept">
        <s:if test="#pdept.typeSmall==#contract[0].mainItemDept">
            <s:property value="#pdept.typeName"/>
        </s:if>
      </s:iterator>
    
    </div>
    </td>
    <td width="126"  align="center" ><div align="center">
    <s:if test="#contract[2]==null">
    未申请
    </s:if>
    <s:else>
  		  <s:if test="#contract[2]==0">
   		 申请保存
   		  </s:if>
    	  <s:elseif test="#contract[2]==1">
   	    待确认
  	     </s:elseif>
   	     <s:elseif test="#contract[2]==2">
 	     退回
  	    </s:elseif>
     </s:else>
    </div></td>
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
<script type="text/javascript">
<s:if test="#isSure == 1" >
alert("合同费用组成不等于合同金额！");
</s:if>
<s:if test="#isSure == 2" >
alert("合同阶段总金额不等于合同金额！");
</s:if>
<s:if test="#isSure == 3" >
alert("合同项目总金额不等于合同金额！");
</s:if>
<s:if test="#isSure == 4" >
alert("主负责部门在项目负责部门中未出现！");
</s:if>
<s:if test="#isSure == 5" >
alert("计划开票日期或者计划收款日期为空！");
</s:if>
<s:if test="#isSure == 6" >
alert("开票收款计划中收款总金额不等于合同含税总金额！");
</s:if>
<s:if test="#isSure == 7" >
alert("开票收款计划中开票总金额和合同金额不等！");
</s:if>
<s:if test="#isSure == 8" >
alert("开票收款计划未生成,请填写全合同信息！");
</s:if>
<s:if test="#isSure == 9" >
alert("开票收款计划中收款金额和费用含税金额不匹配！");
</s:if>
addOnclickFunction();
</script>
</html>
