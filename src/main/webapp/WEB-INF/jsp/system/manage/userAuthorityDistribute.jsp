<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css" />

<title>操作权限授权管理</title>

</head>

<body>



<table align="center" id="table01" width="96%">
	<tr>
		<td width="">当前页面：操作权限授权管理</td>

		<td width="7">&nbsp;</td>
	</tr>
</table>

<s:form action="userAuthority" method="post" theme="simple">
	<s:hidden name="method" value="saveAuthority" />
	<s:hidden name="userId" />
	
	<table align="center" width="96%" class="bg_table">	
		<tr align="center" class="bg_table01">
			<td width="25%" align="center">权限名称</td>
			<td width="15%" align="center">权限描述</td>
			<td width="10%" align="center">权限代码</td>
			<td width="10%" align="center">类型</td>
		</tr>
	
		<s:iterator id="result" value="authorityList">
			<tr class="bg_table02">
				<td align="left">
					<s:property escape="false" value="printBlank(#result[0].code)" />
					<input type='checkbox' name="checkedauthority" code="<s:property value="#result[0].code" />"	value='<s:property value="#result[0].id" />' 
						<s:if test="#result[1]!=0">checked="true"</s:if> onclick="doClick(this)" />
					<s:property value="#result[0].authorityName" />
				</td>
				<td align="center">
					<s:property value="#result[0].authorityDesc" />
				</td>
				<td align="center">
					<s:property value="#result[0].code" />
				</td>
				<td align="center">
					<s:property value="#result[0].type" />
				</td>
			</tr>
		</s:iterator>
		
		<tr class="bg_table03" align="center">
			<td colspan="4">
				<input type="button" class="button01" value='确定' onclick="doSubmit();" />
			</td>
		</tr>
		
	</table>
</s:form>

</body>

</html>



<script language="JavaScript">

var checkboxList = document.getElementsByName("checkedauthority");

function doSubmit()
{
	document.forms[0].submit();
	window.opener.location.reload();
	window.close();
}

function doClick(obj)
{
	// 选中和去掉所有子的checkbox
	for(var i=0; i<checkboxList.length; i++)
	{
		if(checkboxList[i].code.indexOf(obj.code) == 0)
		{
			checkboxList[i].checked = obj.checked;
		}
	}
	// 递归选中自己父的checkbox
	checkFather(obj.code);
	
	//如果是取消的话，判断是否所有的字权限全部取消
	
	var fCode =  obj.code.substring(0, obj.code.length-2);
	
	if(!obj.checked)
	{
		var checkedNum = 0;
		
		for(var i=0; i<checkboxList.length; i++)
		{
			if(checkboxList[i].code.indexOf(fCode) == 0 && checkboxList[i].code.length == obj.code.length && checkboxList[i].checked)
			{
				checkedNum++;
			}
		}
		if(checkedNum == 0)
		{
			//取消父
			for(var i=0; i<checkboxList.length; i++)
			{
				if(checkboxList[i].code == fCode)
				{
					checkboxList[i].checked = false;
					break;
				}
			}
		}
	}
	
	
	/*for(var i=0; i<checkboxList.length; i++)
	{
		if(obj.code.indexOf(checkboxList[i].code) == 0 && obj.code != checkboxList[i].code)
		{
			checkboxList[i].checked = true;
		}
	}
	*/
	/* 如果没有子选中去掉自己
	for(var i=0; i<checkboxList.length; i++)
	{
		for(var j=0; j<checkboxList.length; j++)
		{
			if(checkboxList[j].code != checkboxList[i].code 
				&& checkboxList[j].code.indexOf(checkboxList[i].code) == 0
				&& checkboxList[j].checked)
			{
				checkboxList[i].checked = true;
				break;
			}
		}
	}*/
}

function checkFather(code)
{
	//找到传过来的code的父checkbox，然后选中
	for(var i=0; i<checkboxList.length; i++)
	{
		if(code.indexOf(checkboxList[i].code)==0 && checkboxList[i].code.length+2==code.length)
		{
			checkboxList[i].checked = true;
			//选中父
			checkFather(checkboxList[i].code);
			break;
		}
	}
}


</script>





