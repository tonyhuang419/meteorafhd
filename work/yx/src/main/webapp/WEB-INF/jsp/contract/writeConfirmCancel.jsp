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
		with(searchContractOkList){
	      	   ev2.test("notblank","请填写理由!",$("returnReason").value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	function saveXM(){
		if(!validate()){
			document.searchContractOkList.submit();
			window.close();
		}
	}
	function saveJC(){
		if(!validate()){
			document.forms(0).action = "/yx/contract/searchContractOkJCList.action"
			document.forms(0).method.value="confirmReturn";
			document.forms(0).submit();
			window.close();
		}
	}
</script>
<body>
	<iframe name="errorsFrame" frameborder="0"
							framespacing="0" height="0" width="100%" scrolling="no"></iframe>
	<s:form action="searchContractOkList" theme="simple" target="content">
<div align="left" style="color:#000 ">
<s:if test="#parameters.XMJC[0] == 1">
	 当前页面：合同管理 -> 合同确认(项目类)->退回申请理由
</s:if>
<s:if test="#parameters.XMJC[0] == 2">
	 当前页面：合同管理 -> 合同确认(集成类)->退回申请理由
</s:if>
</div>
	<s:hidden name="method" value="confirmReturn"></s:hidden>
	<s:hidden name="conMainId" value="%{conMainId[0]}"/>
	<s:hidden name="XMJC" value="%{#parameters.XMJC}"/>
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
		          <s:if test="#parameters.XMJC[0] == 1">
					<input type="button" name="querentuihui" class="button01" value="退  回" onclick="saveXM()" />
				  </s:if>
		          <s:if test="#parameters.XMJC[0] == 2">
					<input type="button" name="querentuihui" class="button01" value="退  回" onclick="saveJC()" />
				  </s:if>
					<input type="button" name="reset" value="取消" class="button01" onclick="window.close();" />
		         </td>
	     	</tr>
		</table>
	</s:form>
</body>
</html>