<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {background: lightblue; color: blue;}
</style>
<title>外协付款申请管理</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script language="javascript">
var typeArr=new Array();
<s:iterator value="info.result" id="appResult">
typeArr[typeArr.length]=new Array("<s:property value="#appResult[0].id"/>","<s:property value="#appResult[0].payState"/>","<s:property value="#appResult[0].employeeId"/>");
</s:iterator>
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
	function edit(){
	    var checkArr=document.getElementsByName("ids");
	    var checkStr="";
	    var j=0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	             j++;    
	             checkStr=checkStr+","+checkArr[i].value;
	        }
	    }
	    if(j==1)
	      {
	      	var current = document.forms(0).currentLoginId.value;
	      	var emp = getEmployeeId(checkStr.substring(1));
	      	if(current!=emp){
	      		alert("销售员只能操作自己的付款申请！");
	      		return;
	      	}
	        if(checkOperator("edit")){
	         var url="../assistance/apply.action?method=enterUpdate&ids="+checkStr.substring(1);
	         window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');
	      	}
	      }
	   if(j==0){
	        alert("您还没有选择修改的对象！");
	   }
	   if(j>1){
	     alert("不能选择多个修改对象！");
	   }
	}
function delChose(){	 	
	var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
        }
    }
   if(j==0){
       	alert("您还没有选择修改的对象！");
        
   }else if(j == 1){
   		var current = document.forms(0).currentLoginId.value;
		      	var emp = getEmployeeId(checkStr.substring(1));
		      	if(current!=emp){
		      		alert("销售员只能操作自己的付款申请！");
		      		return;
		      	}
   		if(checkOperator("delChose")){
	 	  	var name = confirm("确定删除?");
	   		if(name==true){
	   			location.href="../assistance/apply.action?method=delete&ids="+checkStr.substring(1);
	   			
	   		}
   		}	
   }else{
   		alert("不能选择多个删除对象！");
   }
}
//根据传入的操作类型判断提示信息
function checkOperator(opType){
    var checkArr=document.getElementsByName("ids");
    var checkLen=checkArr.length;
    var typeLen=typeArr.length;
    var flag=false;
    //修改,删除,提交和退回都是新建和待确认状态的！
    for(var k=0;k<typeLen;k++)
    {
    	for(var j=0;j<checkLen;j++)
    	{
    		if(checkArr[j].checked)
    		{
    			if(checkArr[j].value==typeArr[k][0])
    			{
    				if(typeArr[k][1]!=0 && typeArr[k][1]!=3)
    				{
    					flag=true;
    					break;
    				}
    			}
    		}
    	}
    }
    var msg="您选择的对象在当前状态下不允许";
    if(opType=="edit")
    
    {
    	msg+="修改!";
    }else if(opType=="delChose")
    {
    	msg+="删除！";
    }else if(opType=="aaa")
    {
    	msg+="提交确认！";
    }
    if(flag)
    {
    	alert(msg);
   		return false;
    }
    return true;
}
function aaa() 
{ 
  var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          checkStr=checkStr+","+checkArr[i].value;
          j++;
        }
    }
    if(j==1)
    {
    	var current = document.forms(0).currentLoginId.value;
	      	var emp = getEmployeeId(checkStr.substring(1));
	      	if(current!=emp){
	      		alert("销售员只能操作自己的付款申请！");
	      		return;
	      	}
    	if(confirm("确认要提交吗？")&&checkOperator("aaa"))
    	{
    		location.href="../assistance/apply.action?method=pass&ids="+checkStr.substring(1); 
    	}
    }else{
    alert("请选择一个您要提交的对象！");
    }
}
function printList()
{
	 var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var flag=false;
    if(checkArr!=null&&checkArr.length>0){
    for(var i=0;i<checkArr.length;i++){
    	if(checkArr[i].checked)
    	{
    		checkStr=checkArr[i].value;
    		flag=true;
    		break;
    	}
    }
    }
    if(!flag)
    {
    	alert("请选择要打印的申请单");
    	return;
    }
    
    	var sum=0;
				if(checkArr!=null&&checkArr.length>0)
				{
					for(var k=0;k<checkArr.length;k++){
						if(checkArr[k].checked)
						{
							sum++;
						}
						
					}
				}
				if(sum>1)
				{
					alert("只能选择一个打印！");
					return;
				}
    	window.open("assistancePayForPDF.action?method=assistancePayFor&paramId="+checkStr,"","menubar=yes,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800");
    
}
	function reflushPage(){
		document.apply.submit();
	}
	
	function getEmployeeId(contractId){
		if(typeArr!=null && typeArr.length > 0 ){
			for(var k = 0; k < typeArr.length ; k ++){
				if(typeArr[k][0]==contractId){
					return typeArr[k][2];
				}
			}
		}
		return null;
	}
	
</script>
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
</head>
<body leftmargin="0">

<s:form action="apply">
	<s:hidden name="method" value="query"></s:hidden>
	<input type="hidden" name="currentLoginId" value="<s:property value="%{#session.baox_yx_user.id}"/>"/>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
<tr>
	<td align="center"> 
<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td align="left" >当前页面：外协管理->外协付款申请管理</td>
			</tr>
			<tr>
            	<td align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
          	<tr>
          		<td>
          			<s:if test = "result!=null && result.isSuccess()!=null">
						<s:if test="result.isSuccess()">
							<font color="red"><strong>
							<s:iterator value="result.successMessages" id="successMessage">
								<s:property value="#successMessage"/>
							</s:iterator>	
							</strong>
							</font>
				  		</s:if>
						<s:else>
							<font color="red"><strong>
								<s:iterator value="result.errorMessages" id="errorMessage">
									<s:property value="#errorMessage"/>
								</s:iterator>	
								</strong>
							</font>
						</s:else>
					</s:if>
          		</td>
          	</tr>
			    <tr class="bg_table02">
			      <td align="right"><div align="center">
			        <input type="button" name="SearchBtn3" value="修  改" class="button01" onClick="javascript:edit();" >
		            <input type="button" name="SearchBtn2" value="提交确认" class="button01" onClick="javascript:aaa();">
		            <input type="button" name="SearchBtn2" value="申请单打印" class="button01" onClick="printList();">
		          <input type="button" value="删    除" class="button02" onclick="javascript:delChose()">			     
		          </div></td>
        </tr>
		</table>
	  <table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" id="checkInfo" bordercolor="#808080" style=" border-collapse: collapse;">
<tr align="center">
	      <td nowrap class="bg_table01">选择</td>
	      <td nowrap class="bg_table01">销售员</td>
	      <td nowrap class="bg_table01">外协合同号</td>
          <td nowrap class="bg_table01">申请序号</td>
          <td  nowrap class="bg_table01">外协合同名称</td>
          <td nowrap class="bg_table01">外协供应商</td>
          <td nowrap class="bg_table01">申请日期</td>
          <td nowrap class="bg_table01">申请金额</td>
          <td nowrap class="bg_table01">是否预付</td>
          <td  nowrap class="bg_table01">申请单状态</td>
</tr>
	<s:iterator value="info.result" id="apply">
	    <tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
          <td><input type="checkbox" name="ids"  value="<s:property value="#apply[0].id"/>"></td>
          <td onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')"><s:property value="#apply[4]"/></td>
          <td align="left" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')">
          	<s:property value="#apply[1]"/></td>
          <td align="left" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')">
          	<s:property value="#apply[0].applyInfoCode"/></td>
          <td align="left" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')">
          	<s:if test="#apply[2] != null">
          		<s:property value="#apply[2]"/>
          	</s:if>
          	<s:else>
          		<s:property value="#apply[0].assistanceName"/>
          	</s:else>
          </td>
          <td align="left" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')"><s:property value="#apply[3]"/></td>
          <td onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')"><s:property value="#apply[0].applyDate"/></td>
          <td align="right" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')"><s:property value="#apply[0].payNum"/></td>
          <td align="left" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')">
          <s:if test="#apply[0].applyPay">
           	预付款
           </s:if>
           <s:else>
          	 正常付款
           </s:else>
           </td>
          
          <td align="left" onclick="javavscript:openUrl('apply.action?method=detail&applyInfoId=<s:property value="#apply[0].id"/>')">
          <s:if test="#apply[0].payState==0">
          	草稿
          </s:if>
          <s:elseif test="#apply[0].payState==1">
          	待确认
          </s:elseif>
          <s:elseif test="#apply[0].payState==2">
          	确认通过
          </s:elseif>
          <s:elseif test="#apply[0].payState==3">
          	确认退回
          </s:elseif>
          <s:elseif test="#apply[0].payState==4">
          	确认处理
          </s:elseif>
          </td>
	    </tr>
	</s:iterator>  
	  </table>
<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE> 
    </td>
  </tr>

</table>
</s:form>
</body>
<script type="text/javascript">
<s:if test = "#notSure == true">
		var alertMsg = "<s:property value="#payInfoCode"/>:";
		<s:if test = "#returnCode == 1">
		alertMsg += "接收号为空！";
		</s:if>
		<s:if test = "#returnCode == 2">
		alertMsg += "任务号为空！";
		</s:if>
		<s:if test = "#returnCode == 3">
		alertMsg += "阶段总金额不等于付款申请金额！";
		</s:if>
		<s:if test = "#returnCode == 4">
		alertMsg += "最后一笔付款不能是预付款！";
		</s:if>
		<s:if test = "#returnCode == 5">
		alertMsg += "最后一笔付款申请必须关联预付款！";
		</s:if>
	alert(alertMsg);
	</s:if>	
</script>
</html>
