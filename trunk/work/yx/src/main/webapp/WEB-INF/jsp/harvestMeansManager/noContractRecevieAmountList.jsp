<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>收款管理明细</title>
<script type="text/javascript">
    function reflushPage()
	{
		location.href="/yx/harvestMeansManager/noContractHarvestManage.action?method=doDefault";
	}
	function deleteInfo(delid){
	   var flag=window.confirm("您确定要删除该信息吗？");
		if(flag){
		location.href="/yx/harvestMeansManager/noContractHarvestManage.action?method=deleteInfo&deleteid="+delid+"";
		}
	}
	function sureCheck(){
	   var count = 0;
	   var nocontractAmountid = 0;
	   var selectid = document.getElementsByName("selectedid");
	   for(var i=0;i<selectid.length;i++){ 
        if(selectid[i].checked==true) {
             count++; 
             nocontractAmountid = selectid[i].value;
            }     
        }
	   
       if(checkCheckBox()){
         if(count==1){
             var flag=window.confirm("您确定要核销该信息吗？");
	         if(flag){
	           var str ="../harvestMeansManager/noContractHarvestManage.action?method=sureCheck&selectedid="+nocontractAmountid+"";  
	           window.open(str,'newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');  
	         }
         }else{
            alert("不能同时选择多个合同！");
         }
       }else{
          alert("请选择要确认核销的收款信息！");
       }
	}
	function sureHisCheck(){
	   var count = 0;
	   var nocontractAmountid = 0;
	   var selectid = document.getElementsByName("selectedid");
	   for(var i=0;i<selectid.length;i++){ 
        if(selectid[i].checked==true) {
             count++; 
             nocontractAmountid = selectid[i].value;
            }     
        }
       if(checkCheckBox()){
         if(count==1){       
           var flag=window.confirm("您确定要核销该信息吗？");
	         if(flag){
	           var str ="../harvestMeansManager/noContractHarvestManage.action?method=sureHisCheck&selectedid="+nocontractAmountid+"";  
	           window.open(str,'newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');  
	         }
	     }else{
	          alert("不能同时选择多个合同！");
	     }
       }else{
          alert("请选择要确认核销的收款信息！");
       }
	}
  function modify(modifyid){
    var str ="../harvestMeansManager/noContractHarvestManage.action?method=toNewAndModifyHarvestPage&recevieAmountId="+modifyid+"";  
    window.open(str,'newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');  
  }
	
  function checkCheckBox(){   
     //checkItem為checkbox的集合   
    var checkItem = document.getElementsByName("selectedid");   
    for(var i=0;i<checkItem.length;i++){ 
       if(checkItem[i].checked==true) { 
             return true;
        }     
     }
     return false;   
 } 
</script>
</head>

<body leftmargin="0">

<s:form action="noContractHarvestManage" theme="simple">	
	<s:hidden name="method" value="doDefault" />
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%">
				
				  <tr><td height="3" align="left" >当前页面:收款管理->未签合同收款</td></tr>
		</table>
		<table align="center" border="1" cellpadding="1" cellspacing="1"
				width="100%"  bordercolor="#808080" style="border-collapse: collapse;">
				<tr>
					<td colspan="9" align="right" class="bg_table01" height="3"><img
						src="./../images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr class="bg_table03">
					<td colspan="9" align="right">
					<div align="center">
					<input type="button" value="新    增"
					 onclick="javascript:window.open('../harvestMeansManager/noContractHarvestManage.action?method=toNewAndModifyHarvestPage','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');" class="button02" >
					 &nbsp;&nbsp;<input type="button" value="确认核销" onclick="sureCheck()" class="button02" >
					 &nbsp;&nbsp;<input type="button" value="历史核销" onclick="sureHisCheck()" class="button02" >	
					 </div>
					 </td>
			     </tr>
				<tr align="center">
				    <td width="5%" nowrap="nowrap"  class="bg_table01">选择</td>
				    <td width="10%"  class="bg_table01">销售员</td>
					<td width="20%" nowrap="nowrap" class="bg_table01">客户名称</td>
					<td width="10%"  class="bg_table01">收款金额</td>
					<td width="10%"   class="bg_table01">收款日期</td>
					<td width="5%" nowrap="nowrap"  class="bg_table01">收款状态</td>
					<td width="5%" nowrap="nowrap" class="bg_table01">是否预收款</td>
					<td width="25%"  class="bg_table01">备注</td>
					<td width="10%" class="bg_table01">操作</td>
				</tr>
			<s:set name="sumRecevieAmount" value="0.00"></s:set>
		<s:iterator value="info.result" id="recevieAmount" status="status"> 
	     	<tr>
			        <td nowrap="nowrap" align="center" >
			        <input  type="checkbox" <s:if test="#recevieAmount[4] == 1">disabled</s:if> <s:if test="#recevieAmount[4] == 2">disabled</s:if> name = "selectedid" id="selectedid" value = "<s:property value ="#recevieAmount[0]"/>"></td>
					<td nowrap="nowrap" align="left" ><s:property value="#recevieAmount[5]" ></s:property></td>
					<td align="left" ><s:property value="#recevieAmount[1]" ></s:property></td>
					<td nowrap="nowrap" align="right" ><s:property value="#recevieAmount[2]" /></td>
					<td nowrap="nowrap" align="center" ><s:property value="#recevieAmount[3]" /></td>
					<td nowrap="nowrap" align="left" ><s:if test="#recevieAmount[4] == 0">
                           未核销
                        </s:if>
                        <s:elseif test="#recevieAmount[4] == 1">
                           已核销
                        </s:elseif>
                        <s:elseif test="#recevieAmount[4] == 2">
                           历史核销
                        </s:elseif>
                        <s:else>
                           状态出错
                        </s:else>
                    </td>
                    <td nowrap="nowrap" align="center" >
                    <s:if test="#recevieAmount[7] == 0">
                           否
                    </s:if>
                    <s:elseif test="#recevieAmount[7] == 1">
                           是
                    </s:elseif>
                    <td  align="left" ><s:property  value="#recevieAmount[6]" /></td>
					<td nowrap="nowrap" align="center" ><s:if test="#recevieAmount[4] == 0"><a href="javascript:modify(<s:property value = "#recevieAmount[0]"/>)" >修改</a>/<a href="javascript:deleteInfo(<s:property value = "#recevieAmount[0]"/>);" >删除</a></s:if></td>
	        </tr>
	        <s:set name="sumRecevieAmount" value="#sumRecevieAmount+#recevieAmount[2]"></s:set>
	   </s:iterator>
	   	<tr>
	   		<td colspan="2"></td>
	   		<td align="right"><font style="font-weight:bold">小计:</font></td>
	   		<td align="right"><font style="font-weight:bold"><s:property value="#sumRecevieAmount"/></font></td>
	   		<td colspan="5"></td>
	   	</tr>
	   	<tr>
	   		<td colspan="2"></td>
	   		<td align="right"><font style="font-weight:bold">总计:</font></td>
	   		<td align="right"><font style="font-weight:bold"><s:property value="receiveAmount"/></font></td>
	   		<td colspan="5"></td>

	   	</tr>
	   	</table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04">
					<baozi:pages value="info"
						beanName="info" formName="noContractHarvestManage" /></td>				
				</tr>
			</TABLE>

</s:form>
</body>
</html>