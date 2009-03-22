<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css" />
<html>
<head>
<title>用户权限管理</title>
</head>

<body leftmargin="0">
<div  align="left" style="color:#000000">
	当前页面：权限管理->用户权限管理
</div>		
			<s:form action="userAuthorityQuery" theme="simple">
			<s:hidden name="method" value="quryList"/>
			<table align="center" width="100%">
				<tr>
					<td align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="3"></td>
				</tr>	
				<tr align="center" class="bg_table02">
					<td class="bg_table03">
						<input type="button" class="button01" value='操作权限' onClick="distributeAuthority();" />
						<input type="button" class="button01" value='操作角色' onClick="distributeRole();">
					</td>
				</tr>
				</table>
			
				<table align="center" width="100%" border="1"   bordercolor="#808080" style=" border-collapse: collapse;">
					<tr align="center" class="bg_table01">
						<td>选择</td>
						<td>用户名</td>
						<td>员工名称</td>
						<td>工号</td>
						<td>职位名称</td>
					</tr>
				
					<s:iterator id="result" value="info.result">
						<tr onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
							<td align="center" nowrap>
								<input type="radio" name="checkeduser" value="<s:property value="#result[0].id" />">
							</td>
							<td align="left" nowrap>
								<s:property value="#result[0].username" />
							</td>								
							<td align="left" nowrap>
								<s:property value="#result[0].name" />
							</td>							
							<td align="left" nowrap>
								<s:property value="#result[0].workId" />
							</td>
							<td align="left" nowrap>
								<s:property value="#result[1]" />
							</td>
						</tr>
					</s:iterator>
				</table>
				

				
			<table cellSpacing="1" cellPadding="2" width="100%" border="0">
				<tr valign="top">
					<td class="bg_table04">
						<s:if test="info.result!=null">
								<baozi:pages value="info" beanName="info" formName="forms(0)" />
						</s:if>
					</td>
				</tr>
			</table>
				
	</s:form>
</body>
</html>

<script language="javascript">

function openUrl(url)
{
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}

function distributeAuthority()
{
	// 取得选中的checkbox的value
	var id = 0;
	
	var l = document.getElementsByName("checkeduser");
	
	for(var i=0; i<l.length; i++)
	{
		if(l[i].checked == true)
		{
			id = l[i].value;
			break;
		}
	}
	
	if(id == 0)
	{
		alert("请选择一个角色以编辑！");
	}
	else
	{
		openUrl('/yx/system/manage/userAuthority.action?method=editAuthority&userId='+id);
	}
	
}

function distributeRole()
{
	
	// 取得选中的checkbox的value
	var id = 0;
	
	var l = document.getElementsByName("checkeduser");
	
	for(var i=0; i<l.length; i++)
	{
		if(l[i].checked == true)
		{
			id = l[i].value;
			break;
		}
	}
	
	if(id == 0)
	{
		alert("请选择一个角色以编辑！");
	}
	else
	{
		openUrl('/yx/system/manage/userAuthority.action?method=editRole&userId='+id);
	}
	
	
	
}


</script>




