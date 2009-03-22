<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>公告发布</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<body>
<s:form action="gonggao" theme="simple">
<div align="left">
<p  style="color:#000000">
<s:if test="comeForm.equals('new')">
当前页面：基础管理->公告发布</p>
<s:hidden name="method" value="savePro" />
</s:if>
<s:elseif test="comeForm.equals('mod')">
当前页面：基础管理->公告修改</p>
<s:hidden name="method" value="updatePro" />
<s:hidden name="gonggao.id" />
<s:hidden name="gonggao.updateBy" />
</s:elseif>
</div>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<table align="center" border="1" cellpadding="1" cellspacing="1" width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
	<tr class="bg_table02">
		<td width="30%"  align="right" ><font color="red">* </font>公告内容：</td>
		<td width="70%"  align="right" ><div align="left">
		<s:textarea name="gonggao.content" cols="40" rows="7"></s:textarea> </div></td>
	</tr>
	<tr class="bg_table02">
		 <td align="right" ><font color="red">* </font>公告天数：</td>
		 <td align="right" ><div align="left">
		 <s:textfield size="5" onkeypress="with(window.event)return keyCode<58&&keyCode>47" name="gonggao.gdays"></s:textfield> </div></td>
	</tr>     
	<tr align="center" class="bg_table02">
		 <td align="right">置顶：</td>
		 <td align="left"><s:checkbox name="gonggao.top"></s:checkbox></td>
	</tr>
	<tr align="center" class="bg_table02">
		 <td align="right" >管理员删除：</td>
		 <td align="left" >
		 <s:if test="gonggao.delLevel == true">
			 <s:checkbox name="gonggao.delLevel" disabled="true"></s:checkbox>
		 </s:if>
		 <s:else>
		 	 <s:checkbox name="gonggao.delLevel"></s:checkbox>
		 </s:else>
		 </td>
	</tr>
	<tr align="center" class="bg_table03">
          <td colspan="2" >
          <input type="button" name="SaveBtn" value="发  布" onclick="return check()"  class="button01">
          </td>
	</tr>
</table>
</s:form>
</body>
</html>

<script type="text/javascript">
function check(){	
	if(!validate()){
		document.forms(0).submit();
	}
	return false;
}

function validate(){
       var ev2=new Validator();
       with(gonggao){  
     	  ev2.test("notblank","公告内容不能为空",$('gonggao.content').value);       
      	 ev2.test("notblank","公告天数不能为空",$('gonggao.gdays').value);
      	 ev2.test("length","公告内容不能超过999个字符",$('gonggao.content').value ,999); 
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}
</script>
