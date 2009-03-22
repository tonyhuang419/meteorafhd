<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<title>合同项目成本确认</title>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 16px
}
body {
	background-color: #FFFFFF;
}
.AutoNewline {
	word-break: break-all;/*必须*/
}
-->
</style>
</head>
<body>
<s:form name="contractProjectConfirm" action="contractProject" theme="simple">
<s:hidden name="resetCondition" value="true"></s:hidden>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td>当前页面:合同管理->合同项目成本确认</td>
		</tr>
		<tr>
    <td align="center"><table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%" class="bg_table03">
        <tr>
          <td  height="0.5" colspan="4" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="1"></td>
        </tr>
        <tr class="bg_table03">
          <td width="16%" class="bg_table03">
            <div align="center">
                <s:select name="confirmOrAll" list="#@java.util.TreeMap@{'0':'全部','1':'待确认'}" onchange="changeQuery()"></s:select>
              </div></td>

          <td width="49%" class="bg_table03"><div align="center">
            <input type="button" name="save" value="通  过" onClick="confirmProject()" class="button01">
            <input type="button" name="save3232" value="退  回" onClick="rollback()" class="button01">
          </div></td>
          <td width="20%" class="bg_table03"><input type="button" name="save2" value="取消确认" onClick="recall()" class="button01"></td>
        </tr>
    </table></td>
  </tr>
		<tr>
			<td>
			<table align="center" border="1" cellpadding="1" cellspacing="1" width="100%"  bordercolor="#808080" style=" border-collapse: collapse;">
				<tr>
					<td width="26" class="bg_table01">
					<div align="center">选择</div>
					</td>
					<td width="104" class="bg_table01">
					<div align="center">合同号</div>
					</td>
					<td width="167" class="bg_table01">
					<div>
					<div align="center">合同名称</div>
					</div>
					</td>
					<td width="78" class="bg_table01">
					<div>
					<div align="center">销售员</div>
					</div>
					</td>
					<td width="85" class="bg_table01">
					<div align="center">项目号</div>
					</td>
					<td width="95" nowrap="nowrap" class="bg_table01">
					<div>
					<div align="center">项目部门</div>
					</div>
					</td>
					<td width="89" class="bg_table01">
					<div align="center">项目负责人</div>
					</td>
					<td width="106" class="bg_table01">
					<div align="center">导入剩余外协</div>
					</td>
					<td width="78" class="bg_table01">
					<div align="center">导入剩余发票</div>
					</td>
					<td width="106" class="bg_table01">
					<div align="center">剩余外协</div>
					</td>
					<td width="78" class="bg_table01">
					<div align="center">剩余发票</div>
					</td>
					<td width="86" class="bg_table01">
					<div align="center">状态</div>
					</td>
				</tr>
				<s:set name="name" value="0" ></s:set>
				<s:iterator value="info.result" id="projectlist" status="status">
					<tr align="center"  onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="ids" value="<s:property value="#projectlist[0].conItemMinfoSid"/>" /><input type="hidden" id="idstate<s:property value="#projectlist[0].conItemMinfoSid"/>" value="<s:property value="#projectlist[0].conItemCostSure"/>" /></td>
						<s:if test="#status.index==0">
						<td  align="left" ><s:property value="#projectlist[1].conId" /></td>
						<td  align="left" ><s:property value="#projectlist[1].conName" /></td>
						<td  align="left" ><s:property value="#projectlist[2].name" /></td>
						</s:if>
				        <s:if test="#name!=#projectlist[1].conId&&#status.index!=0">
						<td  align="left" ><s:property value="#projectlist[1].conId" /></td>
						<td  align="left" ><s:property value="#projectlist[1].conName" /></td>
						<td  align="left" ><s:property value="#projectlist[2].name" /></td>
				        </s:if>		
				        <s:elseif test="#status.index!=0">
						<td  align="left" ></td>
						<td  align="left" ></td>
						<td  align="left" ></td>
				        </s:elseif>
						<td onclick="clickdown(<s:property value="#projectlist[0].conItemMinfoSid"/>)" align="center" ><a href="#"><s:property value="#projectlist[0].conItemId" /></a></td>
						<td  align="left" ><s:property value="#projectlist[3]" /></td>
						<td  align="left" ><s:property value="#projectlist[0].itemResDeptP" /></td>
						<td  align="right" ><s:property value="#projectlist[0].remainAssistance" /></td>
						<td  align="right" ><s:property value="#projectlist[0].remainBill" /></td>
						<td  align="right" >
							<s:if test="#projectlist[0].sysRemainAssistance != null">
								<s:property value="#projectlist[0].sysRemainAssistance" />
							</s:if>
							<s:else>
								0.00
							</s:else>
						</td>
						<td  align="right" >
							<s:if test="#projectlist[0].sysRemainBill != null">
								<s:property value="#projectlist[0].sysRemainBill" />
							</s:if>
							<s:else>
								0.00
							</s:else>
						</td>
						<td  align="left" ><s:if test="#projectlist[0].conItemCostSure == 2">待确认</s:if><s:if test="#projectlist[0].conItemCostSure == 3">确认通过</s:if><s:if test="#projectlist[0].conItemCostSure == 4">确认退回</s:if></td>
					</tr>				
                      <s:set name="name" value="#projectlist[1].conId"/>
				</s:iterator>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info" beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			</td>
		</tr>
	</table>
<script language="javascript"> 
function changeQuery(){
       document.forms(0).action='<s:url includeParams="none" action="contractProject"></s:url>';
       document.forms(0).submit();
}

    function clickdown(id){
	   openUrl("<s:url includeParams="none" action="contractProject"><s:param name="method">enterView</s:param></s:url>&id="+id);
	} 

	function checkedCount(){
	   var idArray=window.document.contractProjectConfirm.ids;
	   var count = 0;
	   if(idArray !=null && idArray.length == null){
	   		if(idArray.checked){
				count++;
		   	}
	   }else if(idArray !=null){
		   for(var i=0;i<idArray.length;i++){
		   	if(idArray[i].checked){
				count++;
		   	}
		   }
	   }
	   return count;     
	}

	function rollback(){
	   var checkedNum = checkedCount();
	   if(checkedNum == 0){
	   	alert("请选择要确认退回的合同项目");
	   	return;
	   }
	   if(checkedNum >1){
	   	alert("只能确认退回一个合同项目");
	   	return;
	   }   	
   
	   var idArray=window.document.contractProjectConfirm.ids;
	   var checkedId = "";
	   if(idArray !=null && idArray.length == null){
	   		if(idArray.checked){
				checkedId = idArray.value;
		   		if(document.getElementById("idstate"+checkedId).value!=2){
			    alert("只能确认退回状态为待确认的合同项目");
			    return;
			   }
		   	}
	   }else if(idArray !=null){
		   for(var i=0;i<idArray.length;i++){
		   	if(idArray[i].checked){
		   		checkedId = idArray[i].value;
		   		if(document.getElementById("idstate"+checkedId).value!=2){
			    alert("只能确认退回状态为待确认的合同项目");
			    return;
			   }
		   		break;
		   	}
		   }
	   }   
	   openUrl("<s:url includeParams="none" action="contractProject"><s:param name="method">rollback</s:param></s:url>&id="+checkedId);
   } 
   
	function confirmProject(){
	   var checkedNum = checkedCount();
	   if(checkedNum == 0){
	   	alert("请选择要确认通过的合同项目");
	   	return;
	   }
	   var idArray=window.document.contractProjectConfirm.ids;
	   var checkedId = "";
	   if(idArray !=null && idArray.length == null){
	   		if(idArray.checked){
				checkedId = idArray.value;
		   		if(document.getElementById("idstate"+checkedId).value!=2){
			    alert("只能确认通过状态为待确认的合同项目");
			    return;
			   }
		   	}
	   }else if(idArray !=null){
		   for(var i=0;i<idArray.length;i++){
		   	if(idArray[i].checked){
		   		checkedId = idArray[i].value;
		   		if(document.getElementById("idstate"+checkedId).value!=2){
			    alert("只能确认通过状态为待确认的合同项目");
			    return;
			   }
		   	}
		   }
	   }   
	    if(window.confirm("确定要通过吗?"))
       {
       document.forms(0).action='<s:url includeParams="none" action="contractProject"><s:param name='method'>confirm</s:param></s:url>';
	   document.forms(0).submit();
       return true;
                              
       }
       else
       {
       return false;
       } 
	}
	
	function recall(){
	   var checkedNum = checkedCount();
	   if(checkedNum == 0){
	   	alert("请选择要取消确认的合同项目");
	   	return;
	   }
	   var idArray=window.document.contractProjectConfirm.ids;
	   var checkedId = "";
	   if(idArray !=null && idArray.length == null){
	   		if(idArray.checked){
				checkedId = idArray.value;
		   		if(document.getElementById("idstate"+checkedId).value!=3){
			    alert("只能取消确认状态为确认通过的合同项目");
			    return;
			   }
		   	}
	   }else if(idArray !=null){
		   for(var i=0;i<idArray.length;i++){
		   	if(idArray[i].checked){
		   		checkedId = idArray[i].value;
		   		if(document.getElementById("idstate"+checkedId).value!=3){
			    alert("只能取消确认状态为确认通过的合同项目");
			    return;
			   }
		   	}
		   }
	   }   
	    if(window.confirm("确定要取消吗?"))
       {
       document.forms(0).action='<s:url includeParams="none" action="contractProject"><s:param name='method'>recall</s:param></s:url>';
	   document.forms(0).submit();
       return true;
                              
       }
       else
       {
       return false;
       } 
	}
	    
	function openUrl(url){
		window.open(url,'cpc','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=250,width=550');
	}

</script>
</s:form>
</body>
</html>

