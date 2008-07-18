<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>申购采购管理</title>
<SCRIPT language=JavaScript src="./js/public.js"></SCRIPT>
<script language="javascript">
	
	function checkDel(){
	  var checkArr=document.getElementsByName("amids");
	    var j=0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	             j++;    
	    }
	      }
	   if(j==0){
	        alert("您还没有选择需要操作的对象！");
	        return false;
	   }
		var states=document.getElementsByName("state");
    	var amids=document.getElementsByName("amids");
    	var state;
    	var id;
    	var isSuc1;
    	var isSuc2;
    	var isSuc3;
    	var amidsStr="";
    	for(var i=0;i<states.length;i++){
        	if(amids[i].checked){
             	id=amids[i].value;
             	state=states[i].value;
             	isSuc1=checkState(state,2,"确认通过的不能被删除！请重新选择！");
             	isSuc2=checkOut(i,"已经出库的不能被删除！请重新选择！");
             	isSuc3=checkState(state,1,"待确认的不能被删除！请重新选择！");
            	if(!isSuc1 || !isSuc2  || !isSuc3){
            	 	break;
            	}
            	amidsStr=amidsStr+","+id;
       	}
    }
    	if(isSuc1 && isSuc2&&isSuc3&&window.confirm("确定要删除？")){
    		
			document.forms(0).action="<s:url includeParams="none"  action="purchase"><s:param name="method">delPru</s:param></s:url>";
	    	document.forms(0).submit();
		} 
	}
	
	function checkLink(){
	  var checkArr=document.getElementsByName("amids");
	    var j=0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	             j++;    
	    }
	      }
	   if(j==0){
	        alert("您还没有选择合同！");
	        return false;
	   }
		var states=document.getElementsByName("state");
    	var amids=document.getElementsByName("amids");
    	var state;
		var isSuc;
		var isSuc3;
		var id;
		var amidsStr="";
		for(var i=0;i<states.length;i++){
			if(amids[i].checked){
				id=amids[i].value;
				state=states[i].value;
				isSuc = checkHasContrack(i,"该申购已存在合同关联！");
				isSuc3=checkState(state,1,"待确认的不能关联合同！请重新选择！");
            	if(!isSuc ||!isSuc3){
            	 	break;
            	}
            	amidsStr=amidsStr+","+id;
			}
			
		}
		if(isSuc && isSuc3){
	    	window.open("../purchase/contractQuery.action?amids="+amidsStr+"&action=link","newwindow","menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800");
		}
	}
	
	function checkConfirm()
	{
	  var checkArr=document.getElementsByName("amids");
	
	    var j=0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	             j++;    
	    }
	      }
	   if(j==0){
	        alert("您还没有选择需要操作的对象！");
	        return false;
	   }
		var states=document.getElementsByName("state");
    	var amids=document.getElementsByName("amids");
    	var state;
    	var id;
    	var isSuc1;
    	var isSuc2;
    	var amidsStr="";
    	for(var i=0;i<states.length;i++){
        	if(amids[i].checked){
             	id=amids[i].value;
             	state=states[i].value;
             	isSuc1=checkState(state,2,"确认通过的不能被重新提交！请重新选择！");
             	isSuc3=checkState(state,1,"待确认的不能被重新提交！请重新选择！");
             	isSuc2=checkOut(i,"已经出库的不能被提交！请重新选择！");
            	if(!isSuc1 || !isSuc2 || !isSuc3){
            	 	break;
            	}
            }
       	}
       	if(isSuc1 && isSuc2 && isSuc3){
    
			document.forms(0).action="<s:url includeParams="none"  action="purchase"><s:param name="method">submitPru</s:param></s:url>";
	    	document.forms(0).submit();
	    	 alert("11");
		}
	}
	
	function checkRenew(){
          var checkArr=document.getElementsByName("amids");
	    var j=0;
	    for(var i=0;i<checkArr.length;i++){
	        if(checkArr[i].checked){
	             j++;    
	    }
	      }
	   if(j==0){
	        alert("您还没有选择需要操作的对象！");
	        return false;
	   }
		var states=document.getElementsByName("state");
    	var amids=document.getElementsByName("amids");
    	var state;
    	var id;
    	var isSuc1;
    	var isSuc2;
    	var isSuc3;
    	var isSuc4;
    	var amidsStr="";
    	var num=0;
    	for(var i=0;i<states.length;i++){
        	if(amids[i].checked){
             	id=amids[i].value;
             	num=num+1;
             	state=states[i].value;
             	if(num<1){alert("请选择复选框！");}
             	if(num>1){
             		alert("修改时只能选择一个申购单！请重新选择！");
             		isSuc3=false;
             		break;
             	}
             	isSuc3=true;
             	isSuc1=checkState(state,2,"确认通过的不能被修改！请重新选择！");
             	isSuc4=checkState(state,1,"待确认的不能被修改！请重新选择！");
             	isSuc2=checkOut(i,"已经出库的不能被修改！请重新选择！");
            	if(!isSuc1 || !isSuc2 || ! isSuc4){
            	 	break;
            	}
            }
       	}
       	if(isSuc1 && isSuc2 && isSuc3 && isSuc4){
       		window.open("../purchase/purchase.action?method=enterUpdate&action=update&amids="+id);
       	}
	}
	
	//判断申购是否存在合同
	function checkHasContrack(i,message){	 
		var outs=document.getElementsByName("conid");
		var out;
		var isSuc
		out = outs[i].value;
		if(out.length > 0 ){
			alert(message);
			return false;
		}
		else{
			return true;
		}
	}
		
		
	function checkOut(i,message){
	 
		var outs=document.getElementsByName("out");
		var out;
		var isSuc
		out = outs[i].value;
		isSuc=checkState(out,1,message);
		return isSuc;
	}
	
	function checkState(str1,str2,message){
		if(str1==str2){
			alert(message);
			return false;
		}else{
			return true;
		}
	}
	function viewPurchase(id){
		window.open("../purchase/purchase.action?method=viewPurchase&action=view&amids="+id,"newwindow","menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=500,width=860");
	}
	
</script>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<s:form action="purManageQuery" theme="simple">
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		align="center">
		<tr>
			<td height="3" align="left">当前页面:申购采购->申购采购管理</td>
		</tr>
		<tr>
			<td class="bg_table01" height="1"><img
				src="../../images/temp.gif" alt="temp" width="1" height="1"></td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table class="bg_table03" style="width: 100%;" algin="center">
				<tr>
					<td width="99%" align="center" class="bg_table03"><input
						type="button" value="修    改" class="button02"
						onClick="checkRenew();"> <input
						type="button" value="确认提交" class="button02" onclick=checkConfirm();> <input
						type="button" value="关联合同" class="button02"
						onclick=checkLink();> <input type="button"
						value="删    除" class="button02"
						onclick=checkDel();>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</td>
	</tr>

	<tr>
		<td align="center">
		<table align="center" border=0 cellpadding="1" cellspacing=1
			width="100%">
			<tr align="center">
				<td width="4%" class="bg_table01">选择</td>
				<td width="10%" class="bg_table01">申购单号</td>
				<td width="7%" class="bg_table01">申购人</td>
				<td width="17%" class="bg_table01">主体合同号</td>
				<td width="7%" class="bg_table01">项目号</td>
				<td width="6%" class="bg_table01">任务号</td>
				<td width="14%" class="bg_table01">客户名称</td>
				<td width="13%" class="bg_table01">申购内容简述</td>
				<td width="8%" class="bg_table01">申购日期</td>
				<td width="7%" nowrap class="bg_table01">出库状态</td>
				<td width="10%" nowrap class="bg_table01">申购单状态</td>
			</tr>

			<s:iterator value="info.result">
				<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
					<td><input name="amids" value="<s:property value="id" />" type="checkbox"></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:property value="applyId" /></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:property value="pruService.getEmployee(sellmanId).name" /></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:hidden name="conid" value="%{mainId}"/><s:if test="mainId==null">无关联合同</s:if><s:else><s:property value="pruService.getContractMainInfo(mainId).conId"/></s:else></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:if test="eventId==null">无</s:if><s:else><s:property value="eventId" /></s:else></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:if test="assignmentId==null">无</s:if><s:else><s:property value="assignmentId"/></s:else></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:property value="pruService.getYXClientCode(customerId).name"/></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:property value="applyContent" /></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:date name="applyDate" format="yyyy-MM-dd"/></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:hidden name="out" value="%{outState}"/><s:if test="1 == outState">是</s:if><s:elseif test="0 == outState">否</s:elseif></td>
					<td onClick="viewPurchase(<s:property value="id" />);"><s:hidden name="state" value="%{affirmState}"/><s:if test="0 == affirmState">草稿</s:if><s:elseif test="1 == affirmState">待确认</s:elseif><s:elseif test="2 == affirmState">确认通过</s:elseif><s:elseif test="3 == affirmState">确认退回</s:elseif></td>
				</tr>
			</s:iterator>
		</table>
		</td>
	</tr>
	<tr>
		<td algin="center">
		<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0
			algin="center">
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
</html>

