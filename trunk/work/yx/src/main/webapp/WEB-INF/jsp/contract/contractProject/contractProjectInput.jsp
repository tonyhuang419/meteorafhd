<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<title>合同项目成本录入</title>
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
<s:form name="contractProjectInput" action="contractProjectInput" theme="simple">
<s:hidden name="resetCondition" value="true"></s:hidden>
	<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td>当前页面:合同管理->合同项目成本录入</td>
		</tr>
		<tr>
    <td align="center"><table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%" class="bg_table03">
        <tr>
          <td  height="0.5" colspan="4" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="1"></td>
        </tr>
        <tr class="bg_table03">
          <td  class="bg_table03">
            <div align="center">
               状态：<s:select name="confirmOrAll" list="#@java.util.TreeMap@{'0':'全部','1':'已录入','2':'待确认','3':'确认通过','4':'确认退回','5':'确认签收','6':'保留'}" onchange="changeQuery()"></s:select>
              </div></td>
          <td width="20%" class="bg_table03">
            <div align="center"> 销售员:<s:select name="saleMan" list="employeeList" headerKey="" headerValue="" listKey="id" listValue="name" onchange="changeQuery()"></s:select></div></td>
          <td width="30%" class="bg_table03"><input type="button" name="save2" value="提交确认" onClick="confirmSubmit()" class="button01">
          <input type="button" name="save2" value="签收确认" onClick="qianshou();" class="button01">
          <input type="button" name="save2" value=" 保  留 " onClick="baoliu();" class="button01">
          </td>
          <td width="30%" class="bg_table03"><div align="center">
            <input type="button" name="save3232" value="导  入" onClick="inputClick()" class="button01">
          </div></td>
        </tr>
    </table></td>
  </tr>
		<tr>
			<td>
			<table align="center" border="1`" cellpadding="1" cellspacing="1" width="100%" bordercolor="#808080" style=" border-collapse: collapse;">
				<tr>
					<td class="bg_table01">&nbsp;</td>
					<td  class="bg_table01" nowrap="nowrap">
					<div align="center">合同号</div>
					</td>
					<td  class="bg_table01" nowrap="nowrap">
					<div>
					<div align="center">合同名称</div>
					</div>
					</td>
					<td  class="bg_table01" nowrap="nowrap">
					<div>
					<div align="center">销售员</div>
					</div>
					</td>
					<td  class="bg_table01" nowrap="nowrap">
					<div align="center">项目号</div>
					</td>
					<td class="bg_table01" nowrap="nowrap">
					<div align="center">项目部门</div>
					</td>
					<td  class="bg_table01" nowrap="nowrap">
					<div align="center" >项目负责人</div>
					</td>
					<td  class="bg_table01" nowrap="nowrap">
					<div align="center">导入剩余外协</div>
					</td>
					<td  class="bg_table01" nowrap="nowrap">
					<div align="center">导入剩余发票</div>
					</td>
					<td  class="bg_table01" nowrap="nowrap">
					<div align="center">剩余外协</div>
					</td>
					<td  class="bg_table01" nowrap="nowrap">
					<div align="center">剩余发票</div>
					</td>
					<td  class="bg_table01" nowrap="nowrap">
					<div align="center">状态</div>
					</td>
					<td  class="bg_table01" nowrap="nowrap" align="center">
					<div align="center">操作</div>
					</td>
				</tr>
				<s:set name="name" value="0" ></s:set>
				<s:iterator value="info.result" id="projectlist" status="status">
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="itemId" value="<s:property value="#projectlist[0].conItemMinfoSid"/>"/></td>
						<s:if test="#status.index==0">
						<td align="left" ><s:property value="#projectlist[1].conId" /></td>
						<td align="left" ><s:property value="#projectlist[1].conName" /></td>
						<td align="left" ><s:property value="#projectlist[2].name" /></td>
						</s:if>
				        <s:if test="#name!=#projectlist[1].conId&&#status.index!=0">
						<td align="left" ><s:property value="#projectlist[1].conId" /></td>
						<td align="left" ><s:property value="#projectlist[1].conName" /></td>
						<td align="left" ><s:property value="#projectlist[2].name" /></td>
				        </s:if>		
				        <s:elseif test="#status.index!=0">
						<td align="left" ></td>
						<td align="left" ></td>
						<td align="left" ></td>
				        </s:elseif>
						<td align="left" ><s:property value="#projectlist[0].conItemId" /></td>
						<td align="left" ><s:property value="#projectlist[3]" /></td>
						<td align="left" ><s:property value="#projectlist[0].itemResDeptP" /></td>
						<td align="right" ><s:property value="#projectlist[0].remainAssistance" /></td>
						<td align="right" ><s:property value="#projectlist[0].remainBill" /></td>
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
						<td nowrap="nowrap" align="right" >
						<s:if test="#projectlist[0].conItemCostSure == 2">待确认</s:if>
						<s:elseif test="#projectlist[0].conItemCostSure == 3">确认通过</s:elseif>
						<s:elseif test="#projectlist[0].conItemCostSure == 4">确认退回</s:elseif>
						<s:elseif test="#projectlist[0].conItemCostSure == 1">已录入</s:elseif>
						<s:elseif test="#projectlist[0].conItemCostSure == 5">签收确认</s:elseif>
						<s:elseif test="#projectlist[0].conItemCostSure == 6">保留</s:elseif>
						</td>
						<td align="left" ><s:if test="#projectlist[0].conItemCostSure == 1"><a href="javascript:void(window.open('contract/contractProject/contractProjectInput.action?method=modify&id=<s:property value="#projectlist[0].conItemMinfoSid"/>','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=230,width=600'));">修改</a></s:if>
						<s:if test="#projectlist[0].conItemCostSure == 4"><a href="javascript:void(window.open('contract/contractProject/contractProjectInput.action?method=reinput&id=<s:property value="#projectlist[0].conItemMinfoSid"/>','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=230,width=600'));">再录入</a></s:if>
						<input type="hidden" name="ids" id="idstate<s:property value="#projectlist[0].conItemMinfoSid"/>" value="<s:property value="#projectlist[0].conItemCostSure"/>" /></td>
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
var statusArr=new Array();
<s:iterator value="info.result" id="projectlist" status="status">
 statusArr[statusArr.length]=new Array("<s:property value="#projectlist[0].conItemMinfoSid"/>","<s:property value="#projectlist[0].conItemCostSure"/>");
</s:iterator>
function changeQuery(){
       document.forms(0).action='<s:url includeParams="none" action="contractProjectInput"></s:url>';
       document.forms(0).submit();
} 
function inputClick(){
	   openUrl("<s:url includeParams="none" action="contractProjectInput"><s:param name='method'>inputClick</s:param></s:url>");
	} 
function confirmSubmit(){
	//已经录入，退回，保留
 	
    var flag=checkOperator('1');
    if(flag){
    	if(window.confirm("确定要提交确认吗?"))
    	{
			document.forms(0).action="contractProjectInput.action?method=confirmSub";
      	 	document.forms(0).submit();
		} 
    }   
}     
	function openUrl(url){
		window.open(url,'cpc','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=150,width=500');
	}
	
function qianshou()
{
	var flag=checkOperator('2');
    if(flag){
    	if(window.confirm("确定要签收确认吗?"))
    	{
			document.forms(0).action="contractProjectInput.action?method=qianshouQueRen";
      	 	document.forms(0).submit();
		} 
    } 
	//确认通过的
}
function baoliu(status)
{

    var flag=checkOperator('3');
    if(flag){
    	if(window.confirm("确定要保留吗?"))
   	 	{
			document.forms(0).action="contractProjectInput.action?method=baoLiu";
      	 	document.forms(0).submit();
		} 
    } 
	
}
function checkOperator(op){
//op:代表要做的操作，1，表示提交确认，2，表示签收确认，3表示保留
  //0未录入 1已录入 2待确认 3确认通过 4确认退回,5签收确认，6保留
	var checkNode=$$("input[name='itemId']");//document.forms(0).itemId;
	var checkedNode=$$("input[name='itemId'][checked]");
	if(checkedNode.length<=0){
		alert("您还没有选择项目！");
		return false;
	}
	for(var k=0;k<checkNode.length;k++){
		if(checkNode[k].checked){
	  	    if(op=='1'){
			    if(!(statusArr[k][1]=='1'||statusArr[k][1]=='4'||statusArr[k][1]=='6')){
			    	alert("您选择的项目在当前状态下不能进行提交确认的操作！");
			    	return false;
			    }
	 		 }else if(op=='2'){
				if(!(statusArr[k][1]=='3')){
					alert("您选择的项目在当前状态下不能进行签收确认的操作！");
			    	return false;
				}
			 }else if(op=='3'){
			   if(!(statusArr[k][1]=='1'||statusArr[k][1]=='4')){
			   	alert("您选择的项目在当前状态下不能进行保留的操作！");
			    	return false;
			   }
		     }
		}	    
	}
	return true;
}


</script>
</s:form>
</body>
</html>

