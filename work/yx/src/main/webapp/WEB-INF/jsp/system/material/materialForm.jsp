<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title><s:if test="mm!=null">修改应交材料管理</s:if><s:else>新增应交材料管理</s:else></title>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<body>
<s:form action="material" theme="simple">
	<s:hidden name="method" value="saveMater" />
	<s:hidden name="mm.id"/>
	
	
	<div align="left" style="color:#000000">
	当前页面：应交材料管理-&gt;<s:if test="mm!=null">修改应交材料管理</s:if><s:else>新增应交材料管理</s:else>
	</div>

	<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>


			<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
			<td colspan="2" align="right" class="bg_table01" height="3"></td>	
			</tr>
			
				<tr align="center">
					<td class="bg_table02" align="right"><font color="red">* </font>材料代码：</td>
					<td class="bg_table02" align="left">
						<s:textfield name="mm.materialCode" maxlength="4" onblur="checkCode('/yx/system/material/material.action','checkCode',$('mm.materialCode').value);" ></s:textfield>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right"><font color="red">* </font>材料名称：</td>
					<td class="bg_table02" align="left">
						<s:textfield name="mm.materialName" onblur="checkName('/yx/system/material/material.action','checkName',$('mm.materialName').value);"></s:textfield>
					</td>
				</tr>
				<tr align="center">
					<td class="bg_table02" align="right">材料排序：</td>
					<td class="bg_table02" align="left">
						<s:textfield name="mm.sortOrder" maxlength="4" ></s:textfield>
					</td>
				</tr>
				
				<tr class="bg_table03" align="center"><td colspan="2">
							<s:if test="mm == null">
								<input class="button01"	type="button" name="save" value=" 保  存 " onclick="return checkSave()"/>
								</s:if>
								<s:if test="mm != null">
									<input class="button01"	type="button" name="update" value=" 保  存 " onclick="return checkUpdate()"/>
							</s:if>
				
				<input name="save" type="button"
							class="button02" onClick="window.close();" value=" 关  闭 "></td>
				</tr>
				
		</table>


</s:form>

<script type="text/javascript">
	function checkSave(){	
		if(!validate()){
			document.forms(0).submit();
		}
		return false;
	}
	function checkUpdate(){	
		if(!validate()){
			document.forms(0).method.value="updateMater";
			document.forms(0).submit();
		}
		return false;
	}
	function validate(){
	       var ev2=new Validator();
	       with(material){  
		       ev2.test("notblank","材料代码不能为空",$('mm.materialCode').value);       
		       ev2.test("notblank","材料名称不能为空",$('mm.materialName').value);
		       if($('mm.sortOrder').value.length > 0){
		       	ev2.test("float","排序只能为数字",$('mm.sortOrder').value);
		       }
		       
	       } 
	        if (ev2.size() > 0) {
			     ev2.writeErrors(errorsFrame, "errorsFrame");
			     return true;
			 }
			 return false;
	}
	// 验证代码不能重复
	function checkCode(url, method, name){
	   var oldcode="<s:property value="mm.materialCode"/>";
	   var ev=new Validator();
	   if(oldcode != $('mm.materialCode').value){
	       var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
			  if(responseText =='1'){
			  	 $('mm.materialCode').value="";
			     ev.addError("材料代码已经存在，请重新输入！");
			  } 
		    });
		   myRequest.send("method="+method+"&mm.materialCode="+name+"&randomNum="+Math.random());
	    }
	    ev.writeErrors(errorsFrame, "errorsFrame");
	}
	// 验证名称不能重复
	function checkName(url, method, name){
	   var oldcode="<s:property value="mm.materialName"/>";
	   var ev=new Validator();   
	   if(oldcode != $('mm.materialName').value){
		       var myRequest = new Request({url:url,async:false,method:'get'});
			   myRequest.addEvent("onSuccess",function(responseText, responseXML){
				  if(responseText =='1'){
				  	 $('mm.materialName').value="";
				     ev.addError("材料名称已经存在，请重新输入！");
				  } 
			    });
			   myRequest.send("method="+method+"&mm.materialName="+name+"&randomNum="+Math.random());
	    }
	    ev.writeErrors(errorsFrame, "errorsFrame");
	}
</script>

</body>
</html>

