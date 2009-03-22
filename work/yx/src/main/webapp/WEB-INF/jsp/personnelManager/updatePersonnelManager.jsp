<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>员工信息管理</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}

	
	function doSave(url){
		if(!validate()){
		document.forms(0).submit();
		}
	}
	function refreshClient(){
		document.forms(0).method.value = "refreshPage";
		document.forms(0).submit();
	}
	function aaa(){
	  
	   if(document.all["a"].value=="")
	   {
	      alert("用户名不能为空");
	      return false;
	   }
	   else if(document.all["b"].value=="")
	   {
	      alert("工号不能为空");
	      return false;
	   }
	   else if(document.all["c"].value=="")
	   {
	      alert("姓名不能为空");
	      return false;
	   }
	   else
	   {
	         if(confirm("是否确定修改？"))
	         {
				document.forms(0).method.value = "updatePer";
				document.forms(0).submit();
			}
			else
			{
			   return false;
			}
		}
	}
	function deletRelation(cliendId){
		if(confirm('确定要删除此关联吗?')){
			document.forms(0).cliId.value = cliendId;
			document.forms(0).method.value = "delClient";
			document.forms(0).submit();
		}
		
	}
function back()
{
  		location.href="../personnelManager/selectPerQuery.action?method=doDefault";
}
</script>
<body>
<s:form action="personnelManager" theme="simple">
<s:hidden name="method" value="updatePer"></s:hidden>
<input type="hidden" name="perId" value="<s:property value="per.id"/>" />
<input type="hidden" name="cliId" />
<s:hidden name="per.id"/>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center" >
	<tr> 
		<td valign="top" > 
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
			  <td height="3" align="left" >当前页面：基础管理->客户管理</td>
		  </tr>
			<tr><td class="bg_table01" height="1"><img src="../../images/temp.gif" width="1" height="1"></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
<tr align="center">
			  <td width="15%" align="right" nowrap class="bg_table02"><font color="red">* </font>用户名：</td>
			  <td width="35%" align="left" nowrap class="bg_table02"><s:textfield cssClass="textinput" name="per.username" id="a" size="20"/></td>
			  <td align="right" nowrap class="bg_table02">&nbsp;</td>
					<td align="left" nowrap class="bg_table02">&nbsp;</td>
			 	 
		  </tr>
			<tr align="center">			 
			<td width="15%" align="right" nowrap class="bg_table02"><font color="red">* </font>工号：</td>
			  <td width="35%" align="left" nowrap class="bg_table02">
			  		<s:textfield cssClass="textinput" name="per.workId" size="20" id="b"/></td>
			  <td align="right" nowrap class="bg_table02"><font color="red">* </font>姓名：</td>
			  <td align="left" nowrap class="bg_table02">
			  		<s:textfield cssClass="textinput" name="per.name" size="20" id="c"/>             </td>
			  			
		  </tr>
			<tr align="center">
	  <td align="right" nowrap class="bg_table02">手机：</td>
			  <td align="left" nowrap class="bg_table02">
			  		<s:textfield cssClass="textinput" name="per.callPhone" size="20"/>              </td>	
              <td align="right" nowrap class="bg_table02">email：</td>
              <td align="left" nowrap class="bg_table02">
              		<s:textfield cssClass="textinput" name="per.email" size="20"/></td>
             
		  </tr>
		  
			<tr align="center">
					  <td align="right" nowrap class="bg_table02">固定电话：</td>
			  <td align="left" nowrap class="bg_table02"><s:textfield cssClass="textinput" name="per.phone" size="20"/></td>
			  <td align="right" nowrap class="bg_table02">职责：</td>
			  <td align="left" nowrap class="bg_table02">
			  <s:select name="per.position" list="dutyList" listKey="id" listValue="organizationName" required="true">
				</s:select>
			  </td>
		  </tr>
		  
		<tr align="center">
		
			  	  <td align="right" nowrap class="bg_table02">其他联系方式：</td>
			  <td align="left" nowrap class="bg_table02"><s:textfield cssClass="textinput" name="per.otherPhone" size="20"/></td>
			   <td align="right" nowrap class="bg_table02">&nbsp;</td>
			   <td align="right" nowrap class="bg_table02">&nbsp;</td>
		  </tr>
		  
			<tr align="center">
 			<td colspan="6" align="left" nowrap class="bg_table02">	<hr></td>
        </tr>
</table>

	<table id="newClient" width="100%"  border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
             <tr>
			  <td colspan="2" align="right" nowrap class="bg_table02" clspan="7"><div align="center" class="STYLE1">
			    <div align="left">关联客户</div>
			  </div></td>
			  <td colspan="3" align="left" nowrap class="bg_table02"></td>
             </tr>			
					<tr align="center">
						<td width="13%" class="bg_table01">编号</td>
						<td width="28%" class="bg_table01">客户名称</td>
						<td width="17%" class="bg_table01">客户性质</td>
						<td width="18%" class="bg_table01">行业类型</td>
						<td width="24%" class="bg_table01">操作</td>
					</tr>
					<s:iterator value="empCliInfo.result" status="status">
					<tr align="center">						
						<td width="13%" class="bg_table02"><s:property value="#status.index + 1" /></td>
						<td width="28%" class="bg_table02"><s:property value="cli.name" /></td>
						<td width="17%" class="bg_table02"><s:property value="typeManageService.getYXTypeManage(1001,cli.clientNID).typeName" /></td>
						<td width="18%" class="bg_table02"><s:property value="typeManageService.getYXTypeManage(1002,cli.businessID).typeName"/></td>						
						<td width="24%" class="bg_table02">								
								<input type="button" value="删除" onclick="deletRelation(<s:property value="id" />)" />														
						</td>				
						
					</tr>
					</s:iterator>
				</table>
		<table width="100%">
			<tr class="bg_table03" align="center" style="height:42px">
				<td colspan="4" nowrap>
				<Table style="width:0%;100%">
					<tfoot class="bg_table03" style="height:42px">
						<tr>
							<td align="right" colspan="2">			
							
							<input type="button" class="button01" value="关联客户" onClick="openUrl('selectPerQuery.action?method=showSelClient&perId=<s:property value="per.id"/>')">
							</td>
							<td align="right" colspan="2">
							<input class="button01" type="button" name="savePer" value="保  存" onclick="aaa()"></td>						
						<td colspan="2">
						<input class="button01" type="button" name="Input" value="返  回" onClick="back();">						  </tr>
					</tfoot>
				</Table>
				</td>
			</tr>
		</table>
	  </td>
	</tr>
</table>
</s:form>
</body>
</html>