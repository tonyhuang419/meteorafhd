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
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>

<s:form action="gonggao" theme="simple">
	<s:hidden name="method" value="updatePro" />
	<s:hidden name="gonggao.id" />
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>
		<td align="center"> 
		<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="3" align="left" >当前页面：基础管理->公告修改</td>
			</tr>
			<tr>
            	<td colspan="3" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>			    
			    <tr class="bg_table02">
			      <td width="33%"  align="right" class="bg_table02"><font color="red">* </font>公告内容：</td>
			      <td width="67%"  align="right" class="bg_table02"><div align="left"><s:textarea name="gonggao.content" cols="40" rows="7"></s:textarea> </div></td>
		          <tr class="bg_table02">
			      <td width="33%"  align="right" class="bg_table02"><font color="red">* </font>公告天数：</td>
			      <td width="67%"  align="right" class="bg_table02"><div align="left"><s:textfield size="5" onkeypress="with(window.event)return keyCode<58&&keyCode>47" name="gonggao.gdays"></s:textfield> </div></td>
	      </tr>     
			    <tr align="center">
			      <td  colspan="3" class="bg_table02">&nbsp;</td>
	      </tr>
			    <tr align="center">
                  <td colspan="3" class="bg_table03">
                  <input type="button" name="SaveBtn" value="　发   布　" onclick="return check()" class="button02"></td>
	      </tr>
		</table>

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
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 if($('gonggao.content').value.length > 999){
		 	alert("公告内容不能超过999个字");
		 	return true;
		 }
		 return false;
}
</script>
