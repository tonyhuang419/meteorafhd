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
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td align="center"><table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
        <tr>
          <td colspan="2" align="center"><div align="left">
              <p>当前页面：基础管理->客户管理</p>
            </div></td>
        </tr>
        <tr>
          <td  height="0.5" colspan="2" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="1"></td>
        </tr>
        <tr class="bg_table02">
          <td class="bg_table03"><div align="center">销售员
             
                 <s:select  name="contractSales" list="listExp" listKey="id" listValue="name" required="true"
							headerValue="" emptyOption="true" onchange="ccc()">
              </s:select>
          </div></td>
          <td width="80%" align="right" class="bg_table03">
            <div align="left">
              &nbsp;&nbsp;<input type="button" name="save3232" value="确认通过" onClick="aaa();" class="button01">
              <input type="button" name="save3232" value="确认退回" onClick="bbb()" class="button01">
            </div></td>
        </tr>
      </table>
      <table width="100%" border="0" align="center" id="conChangeSure">
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
        <tr>
          <td align="center"><input type="checkbox" name="operation2" value="<s:property value="#contractClient[0].conMainInfoSid"/>">          </td>
          <td align="center"  onClick=""><s:property value="#contractClient[0].conId"/></td>
          <td onClick="" align="center"><s:property value="#contractClient[0].conName"/></td>
          <td onClick="" align="center"><s:property value="#contractClient[1].name"/></td>
          <td onClick="" align="center"><s:property value="#contractClient[0].conTaxTamount"/></td>
          <td onClick="" align="center" ><s:property value="#contractClient[0].conSignDate"/></td>

          <td onClick="" align="center"><s:property value="#contractClient[2].name"/></td>
          <td onClick="" align="center"><s:property value="typeManageService.getYXTypeManage(1018,#contractClient[0].mainItemDept).typeName"/></td>
          <td onClick="" align="center"><s:property value="typeManageService.getYXTypeManage(1019,#contractClient[0].conState).typeName"/></td>
        </tr>
        </s:iterator>
      </table>
       

  </tr>
  <TR>
    <TD   height=20 class="bg_table02"><DIV align=center>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
</DIV></TD></TR>
</TABLE>
</s:form>
</body>
</html>
