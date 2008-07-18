<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>已签开票申请计划</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function check()
	{
		if(!validate()){
			if($(applyBill).toQueryString() != ""){
				location.href="../billtoReceipt/applyBill.action?&"+$(applyBill).toQueryString();
			}
		}
	}
	
	function checkAmount()
	{
		alert(document.getElementById("realBillAmount").value);
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
  
    if(e != 188 && e != 110 && e != 48 && e != 49 && e != 50 && e != 51 && e != 52 && e != 53 && e != 54 && e != 55 && e != 56 && e != 57 && e != 96 && e != 97 && e != 98 && e != 99 && e != 100 && e != 101 && e != 102 && e != 103 && e != 104 && e != 105 && e != 37 && e != 39 && e != 13 && e != 8 && e != 46 && e != 9)   
  
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
	//验证表单
	function validate(){
		var ev2=new Validator();
		
		with(applyBill){
			<s:iterator value="info.result" id="month" status="status">
		       ev2.test("notblank","<s:property value="#month[2].conId"  />开票内容为空!",$('content<s:property value="#status.index"/>').value);
		       ev2.test("notblank","<s:property value="#month[2].conId"  />开票金额为空!",$('amount<s:property value="#status.index"/>').value);
		    </s:iterator>
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
</script>
<body >
<s:form action="applyBill" theme="simple">
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<div align="left"> 
	<p>当前页面：开票管理 -> 开票申请</p>
	<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe>
	</div>
</td>
		</tr>
		<tr>
			<td>
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill">
				<tr align="center">
					<td class="bg_table01">合同号</td>
					<td class="bg_table01">合同名称</td>
					<td  class="bg_table01">客户名称</td>
					<td  class="bg_table01">负责部门</td>
					<td  class="bg_table01">阶段</td>
					<td  class="bg_table01">计划开票日期</td>
					<td  class="bg_table01">开票性质</td>
					<td  class="bg_table01">发票类型</td>
					<td  class="bg_table01">基准</td>
					<td  class="bg_table01">开票金额</td>
				</tr>
				<s:iterator value="info.result" id="month" status="status">
				<input type="hidden" name="realPanid" value="<s:property value="#month[0].realConBillproSid"/>"/>
					<tr align="center">
						<td class="bg_table02"><s:property value="#month[2].conId"  /></td>
						<td class="bg_table02"><s:property value="#month[2].conName" /></td>
						<td class="bg_table02"><s:property value="#month[1].name" id="cusName"/></td>
						<td class="bg_table02"><s:property
							value="#month[0].contractItemMaininfo" /></td>
						<td class="bg_table02"><s:property
							value="#month[0].conItemStage"  /></td>
						<td class="bg_table02"><s:property
							value="#month[0].realPredBillDate" /></td>
						<td class="bg_table02"><s:property
							value="typeManageService.getYXTypeManage(1004,#month[0].billNature).typeName" /></td>
						<td class="bg_table02"><s:property
							value="typeManageService.getYXTypeManage(1004,#month[0].billType).typeName"  /></td>
						<td class="bg_table02">
							<s:select name="base" list="#@java.util.TreeMap@{1:'含税',0:'不含税'}"></s:select>
						</td>
						<td class="bg_table02"><font color="red">* </font>
						<s:textfield readonly="true" name="realBillAmount" id="amount%{#status.index}" value="%{#month[0].realBillAmount}"  onkeydown=" valNum(event);"/></td>
						
					</tr>
					<tr>
						<td  class="bg_table02"  colspan="9" align="right"><font color="red">* </font>开票内容</td>
						<td class="bg_table02">  <s:textarea id="content%{#status.index}" name="billContent" value="%{#month[0].billContent}" /></td>
						
					</tr>
					<tr>
						<td colspan="11" class="bg_table02" ><hr></td>
					</tr>
					
				</s:iterator>
				<tr align="center">
						<td colspan="11" class="bg_table01" >
							
							<input type="button" class="button01" name="submit" value=" 保  存 " onclick="check()"/>
							<input type="button" name="reset" class="button01" value=" 关  闭 " onclick="window.close()" />
						</td>
					</tr>
			</table>
			</td>
		</tr>
	</table>
</s:form>
</body>
</html>
