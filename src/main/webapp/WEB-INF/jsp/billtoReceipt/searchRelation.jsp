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
				         document.showSelectRelation.action="<s:url includeParams="none" action="relationAmount"><s:param name="method">relationSuccess</s:param><s:param name="ids">checkStr.substring(1)</s:param></s:url>";
						 document.showSelectRelation.submit();
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
	
function valNum(ev)   
  
{   
  
   if(window.event.shiftKey)   
  
        {   
  
           ev.returnValue = "";   
  
        }   
  
        else   
  
        {   
  
    var e = ev.keyCode;   
  
    //允许的有大、小键盘的数字，左右键，backspace, delete, Control + C, Control + V   
  
    if(e != 48 && e != 188 && e != 49 && e != 50 && e != 51 && e != 52 && e != 53 && e != 54 && e != 55 && e != 56 && e != 57 && e != 96 && e != 97 && e != 98 && e != 99 && e != 100 && e != 101 && e != 102 && e != 103 && e != 104 && e != 105 && e != 37 && e != 39 && e != 13 && e != 8 && e != 46 && e != 9)   
  
    {   
  
        if(ev.ctrlKey == false)   
  
        {   
  
            //不允许的就清空!   
  
            ev.returnValue = "";   
  
        }   
  
        else   
  
        {   
  
            //验证剪贴板里的内容是否为数字!   
  
            valClip(ev);   
  
        }   
  
    }   
  
    }   
  
}  
	function checkA()
	{
		var amount=getNumberChar(document.getElementById('lastSum').value);
		var tempOp=textValue();
		var txtNode=document.getElementById("amount"+tempOp);
		var txtValue=getNumberChar(txtNode.value);
		if(txtValue > amount)
		{
			alert("你输入的金额"+txtValue+"大与剩余可关联金额! \n"+"你还可以关联金额为:"+amount);
			return false;
		}
		return true;
	}
</script>
<body>
<s:form action="showSelectRelation" theme="simple">
<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
	<s:hidden name="lastSum" id="lastSum" value="${lastSum}"></s:hidden>			
<table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="receiptConvert">
  <tr align="center">
    <td width="3%" class="bg_table01"></td>
    <td width="8%" class="bg_table01">发票号</td>
    <td width="13%" class="bg_table01">发票类型</td>
    <td width="13%" class="bg_table01">发票金额</td>
    <td width="11%" class="bg_table01">开票时间</td>
    <td width="11%" class="bg_table01">开票余额</td>
    <td width="13%" class="bg_table01">关联金额</td>
  </tr>
 <s:iterator value="info.result" status="status">
  <tr align="center">
    <td class="bg_table02">
	     <input type="radio" name="ids" value="<s:property value="id"/>" onclick="document.getElementById('amount<s:property value="id"/>').disabled=false;" />
	</td>
    <td class="bg_table02"><s:property value="invoiceNo"/></td>
    <td class="bg_table02"><s:property value="typeManageService.getYXTypeManage(1004,type).typeName"/></td>
    <td class="bg_table02"><s:property value="invoiceAmount"/></td>
    <td class="bg_table02"><s:property value="invoiceDate"/></td>
    <td class="bg_table02"><s:property value=""  default="开票余额"/></td>
    <td class="bg_table02">

    	<input type="text" name="relateAmount" onkeydown="valNum(event);" id="amount<s:property value="id"/>" disabled="true" />

    </td>
  </tr> 
	  
  </s:iterator>
  <s:iterator value="invoiceList" >
			<input type="hidden" name="receiptId" id=""receiptId""  value="<s:property value="id"/>"/>		
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