<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>选择关联发票</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
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
         		if(!validate()){
         			 if(checkA()){
         			 	if(confirm("确定要关联吗?")){
					         document.showSelectRelation.action="<s:url includeParams="none" action="relationAmount"><s:param name="method">relationSuccess</s:param><s:param name="ids">checkStr.substring(1)</s:param></s:url>";
							 document.showSelectRelation.submit();
						}
					 }
				 }
         }
		   if(j==0){
		      alert("您还没有选择修改的对象！");
		   }
		   if(j>1){
    
    		 alert("不能选择多个修改对象！");
   			}
 	}

	//验证表单
	function validate(){
		var ev2=new Validator();
		var tempOp=textValue();
		if(tempOp!=null&&tempOp!="")
		{
			var txtNode=document.getElementById("amount"+tempOp);
			var txtValue=txtNode.value;
			with(showSelectRelation){
			    ev2.test("+float+0","项目预计金额为空!",txtValue); 
			  if (ev2.size() > 0) {
		 	    ev2.writeErrors(errorsFrame, "errorsFrame");
		 	    return true;
			 }
			}
		}
		 return false;
	
	}
	function textValue()
	{
		var radNode=document.getElementsByName("ids");
  		 var returnValue="";
         for(var i=0;i<radNode.length;i++){
            if(radNode[i].checked){
                 returnValue=radNode[i].value;
                 break;
             }
         }
		return returnValue;
	}
	function checkA()
	{
		var amount=parseFloatNumber(getNumberChar(document.getElementById('lastSum').value));
		var tempOp=textValue();
		var txtNode=document.getElementById("amount"+tempOp);
		var remainAmountNode=document.getElementById("remainAmount"+tempOp);
		
		var remainAmountValue=parseFloatNumber(getNumberChar(remainAmountNode.innerHTML.replace(/(^\s+)|\s+$/g,"")));
		var txtValue=parseFloatNumber(getNumberChar(txtNode.value));
		if(txtValue > amount)
		{
			alert("你输入的金额"+txtValue+"大与剩余可关联金额! \n"+"您还可以关联金额为:"+amount);
			return false;
		}
		if( txtValue > remainAmountValue)
		{
			alert("你输入的金额"+txtValue+"大与此发票剩余金额!\n"+"您还可以使用此发票的:"+remainAmountValue);
			return false;
		}
		return true;
	}
	function changeInputDisable(robj)
	{	
		if(robj.checked){
			document.getElementById("amount"+robj.value).disabled=false;
		}
		var radNode=document.forms(0).ids;
		if(radNode!=null&&radNode.length>0){
			for(var k=0;k<radNode.length;k++){
				if(radNode[k].checked){
					document.getElementById("amount"+radNode[k].value).disabled=false;
				}else{
					document.getElementById("amount"+radNode[k].value).disabled=true;
					document.getElementById("amount"+radNode[k].value).value="";
				}
			}
		}
	}
</script>
<body leftmargin="0">
<s:form action="showSelectRelation" theme="simple">
<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
	<s:hidden name="lastSum" id="lastSum" value="${lastSum}"></s:hidden>	
		
<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" id="receiptConvert" bordercolor="#808080" style=" border-collapse: collapse;">
  <tr align="center">
    <td width="3%" class="bg_table01"></td>
    <td width="8%" class="bg_table01">发票号</td>
    <td width="13%" class="bg_table01">发票类型</td>
    <td width="13%" class="bg_table01">发票金额</td>
    <td width="11%" class="bg_table01">开票时间</td>
    <td width="11%" class="bg_table01">开票余额</td>
    <td width="13%" class="bg_table01">关联金额</td>
  </tr>
 <s:iterator value="info.result" id="invoice" status="status">
  <tr align="center">
    <td align="left">
	     <input type="radio"  name="ids" value="<s:property value="#invoice[0].id"/>" onclick="changeInputDisable(this)" />
	</td>
    <td align="left"><s:property value="#invoice[0].invoiceNo"/></td>
    <td align="left"><s:property value="typeManageService.getYXTypeManage(1004,#invoice[0].type).typeName"/></td>
    <td align="right"><s:property value="#invoice[0].invoiceAmount"/></td>
    <td align="center"><s:property value="#invoice[0].invoiceDate"/></td>
    <td align="right"><div id="remainAmount<s:property value="#invoice[0].id"/>"><s:property value="#invoice[0].invoiceAmount - #invoice[1]" /></div>
    </td>
    <td align="right">
    	<input type="text" name="relateAmount" onkeydown="valNum(event);" id="amount<s:property value="#invoice[0].id"/>" disabled="true" onblur="formatInputNumber(this)"/>
    </td>
  </tr> 
  </s:iterator>
  <s:iterator value="invoiceList" >
			<input type="hidden" name="receiptId" id="receiptId"  value="<s:property value="id"/>"/>		
   			<input type="hidden" name="idss" value="<s:property value="id"/>"/>
	  </s:iterator>
</table>
<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
		<tr valign="top">
			<td class="bg_table04"><baozi:pages value="info"
				beanName="info" formName="forms(0)" /></td>
		</tr>
	</TABLE>
 <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="receiptConvert">
		<tr valign="top" >
			<td colspan="7" class="bg_table01" align="center">
				<input type="button" name="SearchBtn" value="　确认关联　" class="button01" onclick="relation()" />
				<input type="button" value=" 关  闭 " name="reset" class="button01" onclick="window.close()"/>
			</td>
		</tr>
	</TABLE>
</s:form>
</body>
</html>