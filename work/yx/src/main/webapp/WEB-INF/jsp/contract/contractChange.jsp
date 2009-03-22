<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>合同确认</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script language="javascript">
	 function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
	
	//用于合同预结算转决算明细
function openConPriToFA(obj){
	var form = document.forms[0]; 
	var baseURL="/yx/contract/contractPriororFAtoFinalAccount.action?cmisysid=";
	var con_sid = obj.parentNode.all.operation2.value;
	var con_url = baseURL + con_sid;
	form.action = con_url+"&opInterface=3";
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
	
	function aaa(){
	    var checkArr=document.getElementsByName("operation2");
	    var j=0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	             j++;    
	    }
	      }
	   if(j==0){
	        alert("您还没有选择需要操作的对象！");
	        return ;
	   }
	   if(j>1){
			alert("合同变更确认只能进行单个确认！");
			return ;
		}
	  if(j>0)
	   {
         if(confirm("是否要通过?")){
	         document.form1.method.value = "relationTicket";
			 document.form1.submit();
		 }
		 return;
	   }
 } 
	      
	      
	      function bbb(){
	    var checkArr=document.getElementsByName("operation2");
	    var checkStr="";
	    var j=0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	             j++;    
	             checkStr=checkStr+","+checkArr[i].value;
	        }
	    }
	   if(j==0){
	        alert("您还没有选择需要操作的对象！");
	   }
   else
   {
        if(confirm("是否要退回?")){
         document.form1.method.value = "relationTicketBack";
		 document.form1.submit();
		 }
		  return;
	   }
	      } 
	function relationTicket(){
	var j=0;
	var check = document.getElementsByName("operation2");
	for(var i=0;i<check.length;i++)
	{
	   if(check[i].checked ==true)
	   {
	      j=1;
	   }
	  
	}
	if(j==0)
		alert("请选择复选框!");
	else
	{
		location.href="../contract/contractmanager.action?method=relationTicket";
	}
}
	function ccc()
	{
	  document.form1.contractSales.value=document.form1.contractSales.value
	  location.href="../contract/contractmanager.action?method=salesTicket&contractSales="+form1.contractSales.value;
	}
</script>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 16px
}

-->
</style>
</head>
<body style="background-color: #FFFFFF;">
<s:form action="contractmanager" name="form1" theme="simple">
<s:hidden name="method" value=""></s:hidden>

<div  align="left" style="color:#000000">当前页面：合同管理->合同变更确认 </div>

	<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
        <tr>
          <td  height="0.5" colspan="2" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="3"></td>
        </tr>
        <tr class="bg_table02">
          <td class="bg_table03"><div align="center">销售员
             
                 <s:select  name="contractSales" list="listExp" listKey="id" listValue="name" required="true"
							headerValue="" emptyOption="true" onchange="ccc()">
              </s:select>
          </div></td>
          <td width="80%" align="right" class="bg_table03">
            <div align="left">
              &nbsp;&nbsp;<input type="button" name="save3232" value="通  过" onClick="aaa();" class="button01">
              <input type="button" name="save3232" value="退  回" onClick="bbb()" class="button01">
            </div></td>
        </tr>
      </table>
      <table id="sketchConList" width="100%" border="1" align="center" id="conChangeSure" bordercolor="#808080" style=" border-collapse: collapse;">
        <tr>
          <td width="5%"    align="center" class="bg_table01">选择</td>
          <td width="9%"    align="center" class="bg_table01">合同号</td>
          <td width="18%"    align="center" class="bg_table01">合同名称</td>
          <td width="11%"   align="center" class="bg_table01">客户名称</td>
          <td width="10%"   align="center" class="bg_table01">合同金额</td>
          <td width="9%"  align="center" class="bg_table01">签订日期</td>
      
          <td width="7%"  align="center" class="bg_table01">销售员</td>
          <td width="13%"  align="center" class="bg_table01">工程责任部门</td>
          <td width="9%"   align="center" class="bg_table01">合同性质</td>
        </tr>
        <s:iterator value="info.result" id="contractClient">
        <tr onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
          <td align="center"><input type="checkbox" name="operation2" value="<s:property value="#contractClient[0].conMainInfoSid"/>">          </td>
          <td align="left"  onClick=""><s:property value="#contractClient[0].conId"/></td>
          <td onClick="" align="left"><s:property value="#contractClient[0].conName"/></td>
          <td onClick="" align="left"><s:property value="#contractClient[1].name"/></td>
          <td onClick="" align="right"><s:property value="#contractClient[0].conTaxTamount"/></td>
          <td onClick="" align="center" ><s:property value="#contractClient[0].conSignDate"/></td>

          <td onClick="" align="left"><s:property value="#contractClient[2].name"/></td>
          <td onClick="" align="left"><s:property value="typeManageService.getYXTypeManage(1018,#contractClient[0].mainItemDept).typeName"/></td>
          <td onClick="" align="left"><s:property value="typeManageService.getYXTypeManage(1019,#contractClient[0].conState).typeName"/></td>
        </tr>
        </s:iterator>
      </table>
       

			<table cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</table>

</s:form>
</body>
<script type="text/javascript">
addOnclickFunction();
<s:if test="#isSure == 0" >
alert("合同变更确认成功！");
</s:if>

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
<s:if test="#isSure == 10" >
alert("部门金额不正确！");
</s:if>
<s:if test="#isSure == 11" >
alert("计划金额不正确！");
</s:if>
<s:if test="#isSure == 12" >
alert("阶段金额不正确！");
</s:if>

</script>
</html>
