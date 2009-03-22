<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>确认退回理由</title>
</head>
<script type="text/javascript">
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(purchase){
	      	   ev2.test("notblank","请填写理由!",$("returnReason").value);
	      	   ev2.test("length","退回理由请少于250字符",$('returnReason').value,250);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	function save(){
		if(!validate()){
			document.purchase.submit();
		}
	}
</script>
<body>
<div align="left" style="color:#000 ">
  当前页面：申购采购->申购采购确认->退回申请理由
</div>
<iframe name="errorsFrame" frameborder="0"
							framespacing="0" height="0" width="100%" scrolling="no"></iframe>
	<s:form action="purchase" theme="simple">
	<s:hidden name="method" value="checkBback"></s:hidden>
	<s:hidden name="amids"></s:hidden>
		<table width="100%"  class="bg_table02" >
		<tr>
          <td align="right" class="bg_table01" colspan="2" height="3"><img src="./../images/temp.gif" width="1" height="1"></td>
        </tr>
			<tr class="bg_table02">
			      <td align="right">理由:</td>
		          <td class="bg_table02" align="left" >
		          	<s:textarea cols="25" rows="4" name="returnReason" id="returnReason"></s:textarea>
		         </td>
	     	</tr>
			<tr class="bg_table01">
		          <td class="bg_table01" align="center" colspan="2">
					<input type="button" name="querentuihui" class="button01" value="退  回" onclick="save()" />
					<input type="button" name="reset" value="取消" class="button01" onclick="window.close();" />
		         </td>
	     	</tr>
		</table>
	</s:form>
</body>
</html>