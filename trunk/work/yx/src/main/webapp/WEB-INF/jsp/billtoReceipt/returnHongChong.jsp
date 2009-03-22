<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>确认退回理由</title>
</head>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script type="text/javascript">
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(hongChongQuery){
	      	   ev2.test("notblank","请填写理由!",$("returnReason").value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	function save(){
		if(!validate()){
			document.hongChongQuery.submit();
			window.close();
		}
	}
</script>
<body>
<div align="left" style="color:#000 ">
  当前页面：开票管理 -> 红冲发票确认->退回申请理由
</div>
<iframe name="errorsFrame" frameborder="0"
							framespacing="0" height="0" width="100%" scrolling="no"></iframe>
	<s:form action="hongChongQuery" theme="simple" target="rightFrame">
	<s:hidden name="method" value="cancelSubmit"></s:hidden>
	<s:hidden name="invoiceIds" value="%{#parameters.invoiceId[0]}" ></s:hidden>
	<s:hidden name="confirmHC" value="1"/>
		<table width="100%"  class="bg_table02" >
		<tr>
          <td align="right" class="bg_table01" colspan="2" height="3"><img src="./../images/temp.gif" width="1" height="1"></td>
        </tr>
			<tr class="bg_table02">
			      <td align="right">理由:</td>
		          <td class="bg_table02" align="left" >
		          	<s:textarea cols="25" rows="4" name="returnReason"></s:textarea>
		         </td>
	     	</tr>
			<tr class="bg_table01">
		          <td class="bg_table01" align="center" colspan="2">
					<input type="button" name="querentuihui" class="button01" value="退  回" onclick="save()" />
					<input type="button" name="reset" value="取  消" class="button01" onclick="window.close();" />
		         </td>
	     	</tr>
		</table>
	</s:form>
</body>
</html>