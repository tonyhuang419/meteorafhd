<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协合同阶段拆分</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script type="text/javascript" src="/yx/commons/scripts/public.js"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script language="javascript">
	function splitSectionAmount(sectionVal)
	{ 
		window.open("splitSection.action?method=showSplitSction&sectionId="+sectionVal,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=300,width=400');
	}
	function reflushPage(){
		 location.href="../assistance/splitSection.action?ids=<s:property value="#parameters.ids" />";
    }
    function mergeSection(){
       var flag=0;
	   var chck=document.getElementsByName("sectionId");
	   var checkStr="";
	   for(var i=0;i<chck.length;i++){
	       if(chck[i].checked==true){
                checkStr=checkStr+","+chck[i].value;
	           flag++;
	       }
	   }
	   if(flag>0){
			if(flag > 1){
				window.open("splitSection.action?method=mergeSection&"+$(splitSection).toQueryString(),'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=900');
			}
			if(flag == 1){
				alert("请至少选择两项!")
			}
       }
       else{
       		alert("您没有选择任何一项!");
       }
	}
	function updateSection(sectionVal){
		window.open("splitSection.action?method=showSplitSction&updateSelect=1&sectionId="+sectionVal,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=300,width=400');
	}
</script>
<style type="text/css">
<!--
.STYLE1 {font-size: 14px}
-->
</style>
</head>
<body>
	<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
		
<s:form action="splitSection" theme="simple">
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center"><table width="96%" border="0" cellspacing="1" cellpadding="1">
    <iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
      <tr>
        <td height="3" align="left">当前页面:外协管理->外协合同阶段拆分</td>
      </tr>
      <tr>
            	<td colspan="4" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
    </table>
    <s:iterator value="accistanceList" id="ac">
        <table width="96%" border="0" cellspacing="1" cellpadding="1">
        <td colspan="4" align="right" class="bg_table04">&nbsp;</td>
          <tr align="center">
            <td class="bg_table02" width="17%" align="right">销售人员：</td>
            <td class="bg_table02" align="left" colspan="3"><s:property value="userName"/></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">合同名称：</td>
            <td width="33%" align="left" class="bg_table02">
            	<s:property value="#ac[2]"/>
            <td width="13%" align="right" class="bg_table02">合同号：</td>
            <td width="37%" align="left" class="bg_table02">
            	<s:property value="#ac[3]"/>
            </td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">项目号：</td>
            <td class="bg_table02" align="left">
            	<s:property value="#ac[0].mainProjectId"/>
            <td class="bg_table02" align="right">外协供应商：</td>
            <td align="left" class="bg_table02"><s:property value="#ac[1]"/></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">外协合同名称：</td>
            <td align="left" class="bg_table02">
             	 <s:property value="#ac[0].assistanceName"/>
            </td>
            <td class="bg_table02" nowrap="nowrap" align="right">外协合同金额：</td>
            <td class="bg_table02" align="left">	
            <s:property value="#ac[0].contractMoney"/>	
            </td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">合同签订日期：</td>
            <td class="bg_table02" align="left">
			  	<s:property value="#ac[0].contractDate"/>
			 </td>
            <td class="bg_table02" align="right">预计结束日期：</td>
            <td class="bg_table02" align="left">
				<s:property value="#ac[0].endDate"/>			  	
			</td>
          </tr>
          <tr align="center">
            <td height="79" align="right" class="bg_table02">分包合同内容描述：</td>
            <td class="bg_table02" align="left" colspan="3" >
            	<s:textarea  name="#ac[0].contractContent" id="remark" cols="20" rows="3" readonly="true">
            	</s:textarea>
			</td>
          </tr>
           <tr class="bg_table02">
		      <td align="right">供应商名称：</td>
		      <td align="left"><s:property value="supInfo.supplierName"/>
		        </td>
		      <td align="right">代码：</td>
		      <td align="left"><s:property value="supInfo.supplierCode"/></td>
		    </tr>
			<tr>
			  <td class="bg_table02" align="right">开户银行：</td>
			  <td class="bg_table02" align="left"><s:property value="supInfo.billBank"/></td>
			  <td class="bg_table02" align="right">银行帐号：</td>
			  <td class="bg_table02" align="left"><s:property value="supInfo.billAccount"/></td>
		  </tr>
			<tr>
			  <td colspan="4" align="right" class="bg_table02"><hr></td>
		  </tr>
          <tr>
			  <td colspan="4" align="right" class="bg_table02"><div align="left" class="STYLE1">阶段信息：</div></td>
		    </tr>
          <tr>
			  <td colspan="4" align="right" class="bg_table02">
			  	<table width="100%" border="0" cellspacing="1" cellpadding="1">
			  	<tr align="center">
					<td  class="bg_table01">选择</td>
					<td  class="bg_table01">阶段名称</td>
					<td  class="bg_table01">阶段金额</td>
					<td class="bg_table01">阶段日期</td>
					<td class="bg_table01"></td>
				</tr>
				<s:iterator value="sectionList" >
					<tr align="center">
						<td class="bg_table02">
						<s:if test = "!assistanceService.checkSectionIsRelation(id)">
						<input type="checkbox" name="sectionId" value="<s:property value="id"/>" />
						</s:if>
						<s:else>
							<input type="checkbox" name="sectionId" value="<s:property value="id"/>" disabled="true"/>
						</s:else>
						</td>
						<td class="bg_table02"><s:property value="contractService.findStageName(assistanceStageSId)" /></td>
						<td class="bg_table02"><s:property value="sectionAmount" /></td>
						<td class="bg_table02"><s:property value="sectionBillTime" /></td>
						<td class="bg_table02">
						<s:if test = "!assistanceService.checkSectionIsRelation(id)">
						<a href="#" onclick="splitSectionAmount(<s:property value="id"/>)" >拆分</a>
						<a href="#" onclick="updateSection(<s:property value="id"/>)" >修改</a>
						</s:if>
						<s:else>
						&nbsp;
						</s:else>
						</td>
					</tr>
				</s:iterator>
				</table>
			  </td>
		    </tr>
					
          <tr class="bg_table03" align="center" style="height:42px">
            <td colspan="4">
            <table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                  <tr>
                    <td align="right" colspan="5">
                    	<input type="button" value=" 合  并 " onclick="mergeSection()" class="button02">
                    	<input type="button" value=" 关  闭 " onclick="window.close()" class="button02">
                    </td>
                  </tr> 
                </tfoot>
             </table>
           </td>
           </tr>
</table>
    </s:iterator>
</s:form>
<p>&nbsp;</p>
<p>&nbsp; </p>
</body>
</html>
