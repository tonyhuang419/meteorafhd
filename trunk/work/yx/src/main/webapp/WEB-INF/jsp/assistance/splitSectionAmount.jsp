<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>外协合同变更</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function checkAmount()
	{
		var totalAmount = parseFloatNumber(document.getElementById('sectionAmount').value);
		var splitAmount = parseFloatNumber($('splitAmount').value);
		
		var msg=" 确定要拆分出吗?";
		if(splitAmount != ""){
				if(window.confirm(msg))
				{	
					if(splitAmount == 0){
						alert("拆分开票金额不能为0!");
						return;
					}
					if(splitAmount > totalAmount)
					{
						alert("拆分开票金额不能大于原始阶段金额");
						return;
					}
					document.forms(0).submit();
				}
		}else{
			if(splitAmount == ""){
				alert("请输入需要拆分的阶段金额数字!");	
			}
			return;			
		}
	}
	
	function updateDate(){
		if(!validate()){
			document.splitSection.method.value = "updateSectionDate";
			document.splitSection.submit();
		}
	}
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(splitSection){
		       ev2.test("notblank","请选择日期!",$('sectionBillTime').value);
	      	   ev2.test("dateYX","项目计划投运日期格式不正确",$("sectionBillTime").value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
</script>
<body class="bg_table02" leftmargin="0">
<s:form action="splitSection" theme="simple">
<s:hidden name="method" value="splitSection"/>
<input type="hidden" name="sectionId" value="<s:property value="assSection.id"/>" />
<table >
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
		<s:if test="#parameters.updateSelect != null">
	        <td align="left" >当前页面:外协管理->外协合同阶段修改</td>
		</s:if>
		<s:else>
	        <td align="left" >当前页面:外协管理->外协合同阶段拆分</td>
		</s:else>
      </tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
        <tr align="center">
		    <td colspan="2" class="bg_table01" height="1"><img src="../images/temp.gif" width="1" height="1"> <iframe name="errorsFrame" frameborder="0"
						framespacing="0" height="0" width="100%" scrolling="no"></iframe></td>
		</tr>
	<s:if test="#parameters.updateSelect != null">
			<tr align="center">
				<td class="bg_table02" align="right">阶段日期:</td>
				<td class="bg_table02" align="left">
					<s:property value="assSection.sectionBillTime"  />  
					<input type="hidden" name="sectionAmount" id="sectionAmount" value="<s:property value="assSection.sectionBillTime"  /> " />
				</td>
		</tr>
		<tr align="center">
			<td class="bg_table02" align="right"><font color="red"> *</font>修改日期</td>
			<td class="bg_table02" align="left">
				<div align="left">
                    <input type="text" id=sectionBillTime name="sectionBillTime"  size="12" />
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('sectionBillTime')" align=absMiddle alt=""  />
			  	</div>			
			</td>
		</tr>
	</s:if>
	<s:else>
		<tr align="center">
				<td class="bg_table02" align="right">阶段金额:</td>
				<td class="bg_table02" align="left">
					<s:property value="assSection.sectionAmount"  />  
					<input type="hidden" name="sectionAmount" id="sectionAmount" value="<s:property value="assSection.sectionAmount"  /> " />
				</td>
		</tr>
		<tr align="center">
			<td class="bg_table02" align="right"><font color="red"> *</font>拆分阶段金额:</td>
			<td class="bg_table02" align="left">
				<s:textfield name="splitAmount" maxLength="15" size="16" onkeyup="quanjiao(this)" onblur="formatInputNumber(this)"></s:textfield>				
			</td>
		</tr>
		
	</s:else>
			<td class="bg_table03" colspan="2" align="center">
			<s:if test="#parameters.updateSelect != null">
				<input type="button" name="button21" value="修  改" class="button01" onclick="updateDate()"/>	
			</s:if>
			<s:else>
				<input type="button" name="button2" value="确  认" class="button01" onclick="checkAmount()"/>	
			</s:else>
			<input type="button" name="close" value="关  闭" class="button01" onClick="window.close()" /></td>
		</tr>
	</table>
</s:form>
</body>
</html>