<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>发票/收据管理</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	function relation(){
   		 var checkArr=document.getElementsByName("ids");
  		 var checkStr="";
    	 var j=0;
         for(var i=0;i<checkArr.length;i++){
            if(checkArr[i].checked){
                 j++;    
                 checkStr=checkStr+","+checkArr[i].value;
             }
         }
         if(j==1)
         {  
              window.open('showSelectRelation.action?&idss='+checkStr.substring(1),'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
         }
		   if(j==0){
		      alert("您还没有选择修改的对象！");
		   }
		   if(j>1){
    
    		 alert("不能选择多个修改对象！");
   			}
 	}
		
	//删除关联信息
	function deletRelation(cliendId){
		if(window.confirm("确定要删除此关联金额吗?"))	
		{
			document.forms(0).action="<s:url action="relationAmount"></s:url>";
			document.forms(0).method.value = "delRelation";
			document.forms(0).relationAId.value = cliendId;
			document.forms(0).submit();
		}
	}	
	
	//子页面刷新父页面	
	function refreshClient()
    {
    	location.href="../billtoReceipt/receiptToBillQuery.action";
    }
	

</script>

<body leftmargin="0">
<s:form action="receiptToBillQuery" theme="simple">
<s:hidden name="method" value=""></s:hidden>
<s:hidden name="relationAId"></s:hidden>
		<div align="left" style="color: #000000">
			当前页面：开票管理 -> 收据转发票
		</div>
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
  		 <tr>
 			   <td align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="3"></td>
 		</tr>
  <tr>
    <td align="center" class="bg_table03" >
        <input type="button" name="SearchBtn2" value="关联发票" onClick="relation()" class="button01" >
      </td>
  </tr>
</table>
<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" id="relationBill" bordercolor="#808080" style=" border-collapse: collapse;">
  <tr align="center">
    <td width="3%" class="bg_table01"></td>
    <td width="8%" class="bg_table01">收据号</td>
    <td width="13%" class="bg_table01">合同号</td>
    <td width="13%" class="bg_table01">客户名称</td>
    <td width="11%" class="bg_table01">收据金额</td>
    <td width="11%" class="bg_table01">余额</td>
    <td width="13%" class="bg_table01">关联金额</td>
    <td width="18%" class="bg_table01">发票号</td>
    <td width="10%" class="bg_table01">操作</td>
  </tr>
 <s:iterator value="info.result" id="billRE" status="status"> 
  <tr align="center">
  
    <td class="bg_table02">
        <input type="checkbox" name="ids"  id="ids" value="<s:property value="#billRE[2].id"/>" 
            <s:iterator value="feeMoneyList" id="feeList">
		 		 <s:if test="#feeList[1]==#billRE[2].id">
		 		 	<s:if test="#billRE[2].invoiceAmount - #feeList[0] == 0">
		 		 		disabled="disabled"
		 		 	</s:if>
		    	</s:if>
    		</s:iterator>
        />
         <s:hidden name="invoiceId" value="%{#billRE[2].id}"></s:hidden>
    </td>
    <td align="left" class="bg_table02"><s:property value="#billRE[2].invoiceNo"/></td>
    <td align="left" class="bg_table02">
    		<s:property value="#billRE[3]"/>
    </td>
    <td class="bg_table02" align="left"><s:property value="#billRE[1].name"/></td>
    <td class="bg_table02" align="right"><s:property value="#billRE[2].invoiceAmount"/></td>
    <td class="bg_table02" align="right" >
    <s:iterator value="feeMoneyList" id="feeList">
 		 <s:if test="#feeList[1]==#billRE[2].id">
 		 	<s:if test="#billRE[2].invoiceAmount - #feeList[0] == 0">
 		 		无
 		 	</s:if>
 		 	<s:else>
 		 		<s:property value="#billRE[2].invoiceAmount - #feeList[0]" id="feeAmount"/>	
 		 	</s:else>
    	</s:if>
    </s:iterator>
    </td>
    <!--   利用set值循环取invoiceList的第一条数据放在第一个tr里面,其他换行循环在下面    	-->
        	<s:set name="billIndex"  value="0"></s:set>
        	<s:iterator value="relationAmount.get(#status.index)" id="relation" >
        		<s:if test="#billIndex == 0">
						    <td class="bg_table02" align="right"><s:property value="#relation[0].relateAmount"/></td>
						    <td class="bg_table02" align="left"><s:property value="#relation[1].invoiceNo" /></td>
						    <td class="bg_table02" align="left">
						      <input type="button" name="button" id="button" value="取消关联" onclick="deletRelation(<s:property value="#relation[0].relationAmountId"/>)" >
							</td>
			       			<s:set name="billIndex" value="1"></s:set>	 
	      	 	</s:if>
	      	 	<s:else>
					<tr align="center">
						<td class="bg_table02" align="left" colspan="6"></td>
					    <td class="bg_table02" align="right"><s:property value="#relation[0].relateAmount"/></td>
					    <td class="bg_table02" align="left"><s:property value="#relation[1].invoiceNo" /></td>
					    <td class="bg_table02" align="left">
					      <input type="button" name="button" id="button" value="取消关联" onclick="deletRelation(<s:property value="#relation[0].relationAmountId"/>)" >
						</td>
					</tr>
	      	 	</s:else>
       		</s:iterator>    		
       	<s:if test="#billIndex==0">
       		<td colspan="3" align="left"  class="bg_table02"></td>
       	</s:if>
       		
    	
  </tr>
  <tr align="center">
    <td colspan="11" align="left" class="bg_table02"><hr align="center"></td>
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
