<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>发票/收据管理</title>
<script language="javascript">
	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
	function showInsert(billIdVal){
		openWin('billReceipt.action?method=showInsertOrUpdate&billId='+billIdVal,600,400);
	}
	
	// 根据开票状态查询
	function billStateChange(selObj){
		document.forms(0).resetCondition.value="true";
	    document.forms(0).submit();
	}
	function reflushPage(){
		location.href="../billtoReceipt/billReceiptQuery.action?method=newDefault";
	}
	function refreshPage(){
		location.href="../billtoReceipt/billReceiptQuery.action?method=newDefault";
	}
	//确认录入
	function checkInputState(url,method){
		var myRequest = new Request({url:url,async:false,method:"get"});
		myRequest.addEvent("onSuccess",function(responseTest,responseXML){
			if(responseTest == '1'){
				alert("您今天还没有录入过发票信息!");
			}
			if(responseTest == '0'){
				openUrl("/yx/billtoReceipt/billReceipt.action?method=showAdmit");
			}
		});
		myRequest.send("method="+method+"&randomNum="+Math.random());
	}
</script>
<style type="text/css">
		<!--
		body {
			background-color: #FFFFFF;
		}
		-->
</style>
</head>
<body>
 <s:form action="billReceiptQuery" theme="simple">
    <s:hidden name="method" value="newDefault"></s:hidden>
    <s:hidden name="resetCondition" value=""/>
    <div align="left" style="color: #000000">
		<p>当前页面：开票管理 -&gt; 发票/收据管理</p>
	</div>
    <table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%" class="bg_table03">
		 <tr>
 			   <td align="right" class="bg_table01" colspan="3"><img src="./../images/temp.gif" width="1" height="3"></td>
 		</tr>
        <tr class="bg_table03">
        	<td>
        		&nbsp;
        	</td>
          <td width="50%" align="left" class="bg_table03">
           </td>
           <td colspan="1" width="50%" align="left" class="bg_table03">
          	 <input type="button" name="22" value="确认录入" class="button01" onclick="checkInputState('../billtoReceipt/billReceiptQuery.action','checkInputState');"/> 
          </td>
        </tr>
      </table>
      <table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" id="billList"
      bordercolor="#808080" style="border-collapse: collapse;">
        <tr align="center">
          <td nowrap width="8%" class="bg_table01">开票申请编号</td>
          <td nowrap width="6%" class="bg_table01">申请人</td>
           <td nowrap width="5%" class="bg_table01">合同号</td>
           <td nowrap width="5%" class="bg_table01">项目号</td>
          <td nowrap width="17%" class="bg_table01">合同名称</td>
          <td nowrap width="17%" class="bg_table01">开票客户</td>
          <td nowrap width="6%" class="bg_table01">申请日期</td>
          <td nowrap width="6%" class="bg_table01">票据类型</td>
          <td nowrap width="7%" class="bg_table01">申请金额</td>
          <td nowrap width="7%" class="bg_table01">余额</td>
          <td nowrap width="6%" class="bg_table01">操作</td>
        </tr>
        <s:iterator value="info.result" id="bill" status="mainStatus"> 
        <tr class="bg_table02" id="bill<s:property value="#bill[0].billApplyId"/>" >
			<td> 
        		<s:property value="#bill[0].billApplyNum" />
        	</td>
			<td> 
        	 	<s:property value="#bill[2].name" /> 
        	</td>
			<td> 
        		<s:property value="#bill[3]" /> 
        	</td>
			<td>
				<s:iterator id="s" value="itemNoList.get(#bill[0].billApplyId)"  status="stat2"  >    
            			<s:property value="s"/><s:if test="!#stat2.last"><br/></s:if>
           		 </s:iterator>
			</td>
			<td>
			<s:if test="#bill[5] != null">
        	 	<a href="#" onclick="openCon('<s:property value="#bill[0].contractMainInfo"/>')" >
        	 		<s:property value="#bill[5]" />
        	 	</a>
        	 </s:if>
        	 <s:else>
        	 		<s:property value="#bill[0].contactName" />
        	 </s:else>
        	</td>
			<td align="center"> 
        		 <s:property value="#bill[6]" /> 
        	</td>
			<td align="center" nowrap="nowrap"> 
        		 <s:date name="#bill[0].applyId" format="yyyy-MM-dd HH:mm" /> 
        	</td>
			
			<td> 
        		 <s:property value="typeManageService.getYXTypeManage(1004L ,#bill[0].billType ).typeName" id="billType" />
        	 </td>
  			<td align="right"> 
        		<s:property value="#bill[0].billAmountTax" />
			</td>
  			<td align="right"><s:property value="#bill[0].billAmountTax - #bill[4]"/></td>
  			<td align="center">
	       		<a href="javascript:showInsert(<s:property value="#bill[0].billApplyId" />);" >录入&修改</a>
			</td>	
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
