<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
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
          <td width="16%" class="bg_table03">
            <div align="center">
                <s:select name="confirmOrAll" list="#@java.util.TreeMap@{'0':'全部','1':'已录入','2':'待确认','3':'确认通过','4':'确认退回'}" onchange="changeQuery()"></s:select>
              </div></td>

          <td width="30%" class="bg_table03">
            <div align="center"></div></td>
          <td width="30%" class="bg_table03"><input type="button" name="save2" value="提交确认" onClick="confirmSubmit()" class="button01"></td>
          <td width="30%" class="bg_table03"><div align="center">
            <input type="button" name="save3232" value="导  入" onClick="inputClick()" class="button01">
          </div></td>
        </tr>
    </table></td>
  </tr>
		<tr>
			<td>
			<table align="center" border="0" cellpadding="1" cellspacing="1" width="100%">
				<tr>
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
					<td width="95" class="bg_table01">
					<div>
					<div align="center">项目部门</div>
					</div>
					</td>
					<td width="89" class="bg_table01">
					<div align="center">项目负责人</div>
					</td>
					<td width="106" class="bg_table01">
					<div align="center">剩余外协</div>
					</td>
					<td width="88" class="bg_table01">
					<div align="center">剩余发票</div>
					</td>
					<td width="50" class="bg_table01">
					<div align="center">状态</div>
					</td>
					<td width="60" class="bg_table01">
					<div align="center">操作</div>
					</td>
				</tr>
				<s:set name="name" value="0" ></s:set>
				<s:iterator value="info.result" id="projectlist" status="status">
					<s:if test="#name!=#projectlist[1].conId">
					   <s:if test="#status.index!=0">			   
					   <tr>
							<td colspan="10" class="bg_white">
							<hr align="center">
							</td>
						</tr>
					   </s:if>					
					</s:if>
					<tr align="center">
						<s:if test="#status.index==0">
						<td align="center" ><s:property value="#projectlist[1].conId" /></td>
						<td align="center" ><s:property value="#projectlist[1].conName" /></td>
						<td align="center" ><s:property value="#projectlist[2].name" /></td>
						</s:if>
				        <s:if test="#name!=#projectlist[1].conId&&#status.index!=0">
						<td align="center" ><s:property value="#projectlist[1].conId" /></td>
						<td align="center" ><s:property value="#projectlist[1].conName" /></td>
						<td align="center" ><s:property value="#projectlist[2].name" /></td>
				        </s:if>		
				        <s:elseif test="#status.index!=0">
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
				        </s:elseif>
						<td align="center" ><s:property value="#projectlist[0].conItemId" /></td>
						<td align="center" ><s:property value="#projectlist[3]" /></td>
						<td align="center" ><s:property value="#projectlist[0].itemResDeptP" /></td>
						<td align="center" ><s:property value="#projectlist[0].remainAssistance" /></td>
						<td align="center" ><s:property value="#projectlist[0].remainBill" /></td>
						<td align="center" ><s:if test="#projectlist[0].conItemCostSure == 2">待确认</s:if><s:if test="#projectlist[0].conItemCostSure == 3">确认通过</s:if><s:if test="#projectlist[0].conItemCostSure == 4">确认退回</s:if><s:if test="#projectlist[0].conItemCostSure == 1">已录入</s:if></td>
						<td align="center" ><s:if test="#projectlist[0].conItemCostSure == 1"><a href="javascript:void(window.open('contract/contractProject/contractProjectInput.action?method=modify&id=<s:property value="#projectlist[0].conItemMinfoSid"/>','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=230,width=600'));">修改</a></s:if>
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
function changeQuery(){
       document.forms(0).action='<s:url includeParams="none" action="contractProjectInput"></s:url>';
       document.forms(0).submit();
} 

function inputClick(){
	   openUrl("<s:url includeParams="none" action="contractProjectInput"><s:param name='method'>inputClick</s:param></s:url>");
	} 

function confirmSubmit(){
 if(window.confirm("确定要提交吗?"))
       {
       document.forms(0).action='<s:url includeParams="none" action="contractProjectInput"><s:param name='method'>confirmSub</s:param></s:url>';
       document.forms(0).submit();
       return true;                            
       }
       else
       {
       return false;
       }   
} 
	    
	function openUrl(url){
		window.open(url,'cpc','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=150,width=500');
	}

</script>
</s:form>
</body>
</html>

