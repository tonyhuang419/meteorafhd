<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>

<html>
<head>
<title>收款管理列表</title>
<script type="text/javascript">
function daoru()
{
	window.open("/yx/system/hisdata/importReveInfo.action");
	//openWin("/yx/system/hisdata/importReveInfo.action",400,300);
}
function showImportList(){
	window.open("/yx/system/hisdata/confirmReveInfo.action?method=showReveInfoList");
}
</script>

</head>
<body leftmargin="0">
  <div align="left" style="color:#000000">当前页面:收款管理->收款管理</div>
<s:form action="w_HarvestMeansSearch" theme="simple"> 

<s:if test="noRemain == 1"><br>
	<div align="left" style="color:red">提示：查询结果票据余额已为0</div>
</s:if>

	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
		 <td  height="3" colspan="7" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="1"></td>
	</tr>
     <tr class="bg_table03">
       <td align="center" colspan="7"> <input type="button" name="" value="确认收款" onclick="shoukuanqueren();" class="button01"> 
   		 <input type="button" name="" value="导入收款" onclick="daoru();" class="button01"> 
   		<input type="button" value="未确认的到款清单" onclick="showImportList();" class="button02">
   		</td> 
    </tr>
	</table>	
	<table align="center" border=1  width="100%"   bordercolor="#808080" style=" border-collapse: collapse;">
		<tr align="center">
			<td nowrap   class="bg_table01">合同号</td>
			<td nowrap   class="bg_table01">项目号</td>
			<td nowrap   class="bg_table01">合同名称</td>
			<td nowrap   class="bg_table01">客户名称</td>
			<td nowrap   class="bg_table01">销售员</td>
			<td nowrap   class="bg_table01">项目金额</td>
			<td nowrap   class="bg_table01">累计票额</td>
			<td nowrap   class="bg_table01">累计收据</td>
			<td nowrap   class="bg_table01">到款额</td>
			<td nowrap   class="bg_table01">应收余额</td>
			<td nowrap   class="bg_table01">操作</td>
		</tr>
	<s:iterator value="info.result" id="invoice"> 
		<tr align="center"  onMouseOver="this.bgColor='#BBBBFF';" onMouseOut="this.bgColor='#FFFFFF';">
			<td><div align="left"><s:property value="#invoice[0]"/></div></td>
			<td><div align="left"><s:property value="#invoice[1]"/></div></td>
			<td><div align="left"><a href="#" onclick="openCon('<s:property value="#invoice[10].longValue()"/>')" ><s:property value="#invoice[2]"/></a></div></td>
			<td><div align="left"><s:property value="#invoice[3]"/></div></td>
			<td><div align="left"><s:property value="#invoice[4]"/></div></td>
			
			<td><div align="right">		
			<s:if test="#invoice[5]==null">
				0.00
			</s:if>
			<s:else>
				<s:property value="#invoice[5]"/>
			</s:else>
			</div></td>
				
			<td><div align="right">		
			<s:if test="#invoice[6]==null">
				0.00
			</s:if>
			<s:else>
				<s:property value="#invoice[6]"/>
			</s:else>
			</div></td>
			
			<td><div align="right">		
			<s:if test="#invoice[7]==null">
				0.00
			</s:if>
			<s:else>
				<s:property value="#invoice[7]"/>
			</s:else>
			</div></td>
			
			<td><div align="right">		
			<s:if test="#invoice[8]==null">
			<s:set name="hasReve" value="0.00"/>
				0.00
			</s:if>
			<s:else>
				<s:property value="#invoice[8]"/>
				<s:set name="hasReve" value="#invoice[8]"/>
			</s:else>
			</div></td>
	
			<td><div align="right">	
				<s:if test="#invoice[9]==null">
					<s:set name="shouldReve" value="0.00"/>
				</s:if>
				<s:else>
					<s:set name="shouldReve" value="#invoice[9]"/>
				</s:else>	
				<s:property value="#shouldReve - #hasReve"/>
			</div></td>

			<td><div align="center"><a href="#" onclick="inputReve('<s:property value="#invoice[11]"/>','<s:property value="#invoice[10]"/>')">录入&修改</a></div></td>
		
		</tr>
	   </s:iterator>
    
    <table width="100%" border="0">
        <tr valign="top">
          <td class="bg_table04">
         	 <baozi:pages value="info" beanName="info" formName="forms(0)" />
          </td>
        </tr>
     </table>
</s:form>

<script type="text/javascript">


function cleanReg(str){
	str = str.replace(/.\d\d$/,'');
	str = str.replace(/,/g,'');
	return str;
}

function inputReve(itemId,conId){
	itemId = cleanReg(itemId);
	conId = cleanReg(conId);
	openWin('w_HarvestMeansSearch.action?method=showReveInfo&itemId='+itemId+'&conId='+conId,700,400);
	//window.open('w_HarvestMeansSearch.action?method=showReveInfo&itemId='+itemId+'&conId='+conId);
}
function shoukuanqueren()
{
	openWin("w_ReveInfoMakeSure.action",600,400);
	//window.open("w_ReveInfoMakeSure.action");
}

function refresh()
{
	document.forms(0).submit();
}
</script>
	
</body>
</html>