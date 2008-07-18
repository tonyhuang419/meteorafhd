<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>开票申请确认</title>

<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}

.STYLE3 {color: #000000}
-->
</style>
</head>
<body>
 <s:form action="billApplyVerifyQuery" theme="simple">
<div align="left">
  <p class="STYLE3">&nbsp;当前页面：开票管理 -> 开票申请确认</p>

</div>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td align="center"><table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
        <tr>
          <td align="right" class="bg_table01"  height="3"><img src="./../images/temp.gif" width="1" height="1"></td>
        </tr>
        <tr class="bg_table02">
          <td align="right" class="bg_table03"  ><div align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;销售员：
              
              <s:select name="expId" id="selectId" onchange="" list="listExp" listKey="id" listValue="name" required="true" 
              headerKey="" headerValue="全部"
              onchange="javascript:queryByExp();"
              >
              </s:select>
&nbsp;&nbsp;
<input type="button" name="SearchBtn" value="申请确认通过" onClick="javascript:aaa()" class="button01">
              <input type="button" name="SearchBtn2" value="申请确认退回" onClick="javascript:ccc()" class="button01">
          </div></td>
        </tr>
      </table>
    </td>
      </tr>
      <tr>
      <td>
      <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="billApplySure">
        <tr align="center">
          <td width="6%" class="bg_table01"><div align="center">选择</div></td>
          <td width="9%" class="bg_table01"><div align="center">开票申请编号</div></td>
          <td width="11%" class="bg_table01"><div align="center">申请日期</div></td>
          <td width="10%" class="bg_table01"><div align="center">申请金额</div></td>
          <td width="11%" class="bg_table01"><div align="center">申请类型</div></td>
          <td width="10%" class="bg_table01"><div align="center">开票性质</div></td>
          <td width="13%" class="bg_table01"><div align="center">票据类型</div></td>
          <td width="22%" class="bg_table01"><div align="center">开票内容</div></td>
          <td width="8%" class="bg_table01"><div align="center">申请人</div></td>
        </tr>
        
        <s:iterator id="result" value="info.result">
					
        <tr align="center" onMouseOver="this.bgColor='#BBBBFF'; " onMouseOut="this.bgColor='#FFFFFF';">
          <td ><div align="center">
           <input type="checkbox" name="ids" value="<s:property value="#result[0].billApplyId"/>" />
          </div></td>
          <td ><div align="center"><s:property value="#result[0].billApplyNum"/></div></td>
          <td ><div align="center"><s:property value="#result[0].applyId"/></div></td>
          <td ><div align="center"><s:property value="#result[0].billAmountTax"/></div></td>
<!--          <td ><div align="center"><s:property value="#result[0].isNoContract"/></div></td>-->
          <s:if test="#result[0].isNoContract">
            <td>未签</td>
          </s:if>
          <s:else>
            <td>已签</td>
          </s:else>
          
          
          <td ><div align="center">
         <s:property value="typeManageService.getYXTypeManage(1012,#result[0].billNature).typeName" />
          </div></td>
          <td ><div align="center">
          <s:property value="typeManageService.getYXTypeManage(1004,#result[0].billType).typeName" />
          </div></td>
          <td ><div align="center"><s:property value="#result[0].billContent"/></div></td>
          <td ><div align="center"><s:property value="#result[1]" /></div></td>
        </tr>
       </s:iterator> 
     </table>
			<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>

<script language="javascript">
//确认通过
function aaa() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var act="pass";
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
location.href="../billtoReceipt/billApplyVerify.action?method=verifyState&action="+act+"&bId="+checkStr.substring(1); 
} 
//确认退回
function ccc() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          checkStr=checkStr+","+checkArr[i].value;
        }
    }
location.href="../billtoReceipt/billApplyVerify.action?method=verifyState&bId="+checkStr.substring(1); 
} 

function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
 
 function queryByExp(){
    var expId=document.getElementById("selectId").value;
   // alert(expId);
	location.href="../billtoReceipt/billApplyVerify.action?method=queryByExployee&expId="+expId; 
}
</script>

