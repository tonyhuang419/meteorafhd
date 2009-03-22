<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {background: lightblue; color: blue;}
</style>
<title>外协合同管理</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/public.js" type="text/javascript"></script>
<script language="javascript">
var typeArr=new Array();
<s:iterator value="info.result" id="appResult">
typeArr[typeArr.length]=new Array("<s:property value="#appResult[0].id"/>","<s:property value="#appResult[0].assistanceContractType"/>","<s:property value="#appResult[0].employeeId"/>");
</s:iterator>
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');
}
function displayXX(i , assistanceId){
	var info = document.getElementById('dispalyInfo'+i);
	var tr = document.getElementById('trDispalyInfo'+i);
	if(tr.style.display == "none"){
		getApplyHistory(info,assistanceId);
		tr.style.display="";
	}
	else{
		tr.style.display="none";
	}
}

function getApplyHistory(contentDiv,assistanceId){
	var myHTMLRequest = new Request.HTML({url:'../assistance/assistance.action?method=showApplyHistory&assistanceContractId='+assistanceId,update:contentDiv}).get({'assistanceId': assistanceId,randnum:Math.random()});
}
function aaa() 
{  
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var flag=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
          checkStr=checkStr+","+checkArr[i].value;
          flag++;
        }
    }
    if(flag==1)
    {
   		var current = document.forms(0).currentLoginId.value;
   	  	var emp = getEmployeeId(checkStr.substring(1));
		if(current!=emp){
   			alert("销售员只操作自己的合同！");
   			return;
   		}
    	if(checkOperator("aaa")&&confirm("确认要提交吗?"))
    	{	
    		location.href="../assistance/assistance.action?method=verifyState&stateId="+checkStr.substring(1); 
    	}
    }else if(flag == 0){
    alert("请选择您要提交的对象！");
    }else{
    	alert("不能选择多个确认提交对象！");
    }
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
      		alert("销售员只能操作自己的合同！");
      		return;
      	}     
      if( checkOperator("edit")){
      	
         var url="../assistance/assistance.action?method=enterUpdate&ids="+checkStr.substring(1);
         window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=1000');
      	}
      }
   if(j==0){
        alert("您还没有选择修改的对象！");
   }
   if(j>1){
   	  	alert("不能选择多个修改对象！");
   }
}
      
function ccc(){
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
      {  	if( checkOperatorCcc()){
     		 var url="../assistance/assistance.action?method=enterApply&ids="+checkStr.substring(1);
     		 var current = document.forms(0).currentLoginId.value;
      		 var emp = getEmployeeId(checkStr.substring(1));
      		 if(current!=emp){
      			alert("销售员只能操作自己的合同！");
      			return;
      		 }
      		/////检查是否有预付款信息
      		var flag = checkExistsPrepPay(checkStr.substring(1));
      		if(flag){
      			var hasPrep = window.confirm("该外协供应商下面有预付款信息，是否关联？");
      			if(hasPrep){
      				url="../assistance/assistance.action?method=enterPrepApplyPay&ids="+checkStr.substring(1);
      				openWin(url,800,600);
      			}else{
      				openWin(url,800,600);
      			}
      		}else{
      			openWin(url,800,600);
      		}
     		//window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');     		
      	}
      }
   if(j==0){
        alert("您还没有选择付款申请的对象！");
   }
   if(j>1){
    
     alert("不能选择多个付款申请对象！");
   }
}

function checkExistsPrepPay(assistanceId){
		var url = "/yx/assistance/assistanceApplyPay.action";
		var method = "checkExistsPrepPay";
		var flag = false;
		 var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
		      if(responseText == "true"){
			 	flag = true;
			 }else{
			 	flag = false;
			 }
		    });
		   myRequest.send("method="+method+"&assistanceId="+assistanceId+"&randomNum="+Math.random());
		   return flag;
}

function splitSection(){   //拆分阶段
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
      {  	if( checkOperatorCcc()){
       		var url="../assistance/splitSection.action?ids="+checkStr.substring(1);
     		window.open(url);
     		//window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');     		
      	}
      }
   if(j==0){
        alert("您还没有选择付款申请的对象！");
   }
   if(j>1){
    
     alert("不能选择多个付款申请对象！");
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
      		alert("销售员只能操作自己的合同！");
      		return;
      	}
      	if( checkOperator("delChose")){
 	  		var name = confirm("确定删除?");
   			if(name==true){
   				location.href="../assistance/assistance.action?method=del&ids="+checkStr.substring(1);
   			}
   		}else{
        	 return;
       	}
   }else{
   	alert("请选择一个删除！");
   }
}   


//根据传入的操作类型判断提示信息
function checkOperatorCcc(){
    var checkArr=document.getElementsByName("ids");
    var checkLen=checkArr.length;
    var typeLen=typeArr.length;
    var flag=false;
    //修改删除和提交都是新建和待确认状态的！
    for(var k=0;k<typeLen;k++)
    {
    	for(var j=0;j<checkLen;j++)
    	{
    		if(checkArr[j].checked)
    		{
    			if(checkArr[j].value==typeArr[k][0])
    			{
    				if(typeArr[k][1]!="2")
    				{
    					flag=true;
    					break;
    				}
    			}
    		}
    	}
    }
    var msg="您选择的对象在当前状态下不允许此操作！";
 
    if(flag)
    {
    	alert(msg);
   		return false;
    }
    return true;
}

//根据传入的操作类型判断提示信息
function checkOperator(opType){
    var checkArr=document.getElementsByName("ids");
    var checkLen=checkArr.length;
    var typeLen=typeArr.length;
    var flag=false;
    var msg="不允许";
    var a;
    //修改删除和提交都是新建和待确认状态的！
    for(var k=0;k<typeLen;k++)
    {
    	for(var j=0;j<checkLen;j++)
    	{
    		if(checkArr[j].checked)
    		{
    			if(checkArr[j].value==typeArr[k][0])
    			{
    				if(typeArr[k][1]!="0" && typeArr[k][1]!="3")
    				{
    				  if(typeArr[k][1]=="1"){
    				     msg="待确认中"+msg;
    				  }
                      if(typeArr[k][1]=="2"){
    				     msg="确认通过"+msg;
    				  }
    				   if(typeArr[k][1]=="4"){
    				     msg="已关闭"+msg;
    				  }
    					flag=true;
    					break;
    				}
                 
    			}
    		}
    	}
    }
 
    
    if(opType=="edit")
    {
    	msg+="修改!";
    }else if(opType=="delChose")
    {
    	msg+="删除！";
    }else if(opType=="aaa")
    {
    	msg+="确认提交！";
    }else if(opType=="ccc")
    {
    	msg+="付款申请！";
    }
    if(flag)
    {
    	alert(msg);
   		return false;
    }
    return true;
}
	function addTicket(){
		var idsCheckBox = $('assistanceMLeftQuery').getElements("input[name='ids']");
		var checkCount = 0;
		var checkedValue = "";
		//计算选中的个数
		for(var i=0;i<idsCheckBox.length;i++){
			if(idsCheckBox[i].checked){
				checkCount++;
				checkedValue = idsCheckBox[i].value;
			}
		}
		//不能不选
		if(checkCount==0){
			alert("您还没有选择修改的对象！");
			return;
		}
		//不能选中多个
		if(checkCount>1){
			alert("不能选择多个对象！");
			return;
		}
		//只选中了一个
		var typeState = getContractState(checkedValue);
		if(typeState!=null&&typeState=='2'){
			var url = "../assistance/assistanceTicket.action?fromContractManager=1&assistanceConId="+checkedValue;
			openWin(url,650,400);
		}else{
			alert("没有确认通过不能进行开票");
		}
		
	}
	function getContractState(contractId){
		if(typeArr !=null &&typeArr.length > 0){
			for(var k = 0; k< typeArr.length ; k++){
				if(typeArr[k][0] == contractId){
					return typeArr[k][1];
				}
			}
		}
		return null;
	}
	
	function showDetail(realIdVal)
	{ 
		window.open('assistanceMLeftQuery.action?method=showDetail&assistanceId='+realIdVal,null,'menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=700');
	}
	
	function reflushParent(){
		document.assistanceMLeftQuery.submit();
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
</head>
<body style="margin: 0px;">
<s:form theme="simple" action="assistanceMLeftQuery">
	<s:hidden name="method" value="query"></s:hidden>
	<input type="hidden" name="currentLoginId" value="<s:property value="%{#session.baox_yx_user.id}"/>"/>
	<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
      <tr>
        <td align="left" >当前页面:外协管理->外协合同管理</td>
      </tr>
      <tr>
        <td align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="3"></td>
      </tr>
      <tr>
        <td align="left" class="bg_table03"><div align="center">
          <input type="button" class="button02" value="修    改" onClick="javascript:edit()">
          <input type="button" class="button02" value="确认提交" onClick="javaScript:aaa()">
<!--          <input type="button" class="button02" value="新增发票" onClick="javaScript:addTicket()">-->
          <input type="button" class="button02" value="付款申请" onClick="javaScript:ccc()">
          <input type="button" class="button02" value="删    除" onClick="javascript:delChose()">
<!--          <input type="button" class="button02" value="阶段变更" onClick="javascript:splitSection()">-->
        </div></td>
        </tr>
    </table>
<table align="center" border=1 cellpadding="1" cellspacing=1 width="100%" id="checkInfo" bordercolor="#808080" style=" border-collapse: collapse;">
          <tr align="center">
            <td width="3%" class="bg_table01">选择</td>
            <td width="6%" nowrap class="bg_table01">销售员</td>
            <td width="9%" nowrap class="bg_table01">外协合同号</td>
            <td width="13%" nowrap class="bg_table01">外协合同名称</td>
            <td width="18%" nowrap class="bg_table01">外协供应商</td>
            <td width="9%" nowrap class="bg_table01">签订日期</td>
            <td width="9%" nowrap class="bg_table01">预计结束日期</td>
            <td width="9%" nowrap class="bg_table01">合同金额</td>
            <td width="9%" nowrap class="bg_table01">已支付金额</td>
            <td width="9%" nowrap class="bg_table01">余额</td>
            <td width="8%" nowrap class="bg_table01">外协状态</td>
          </tr>
          <s:set name="contractMoney" value="0.00"></s:set>
          <s:set name="hasPayAmount" value="0.00"></s:set>
          <s:set name="feeAmount" value="0.00"></s:set>
          <s:iterator id="ac" value="info.result" status="status">
	    <tr align="center"  onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="ids" value="<s:property value="#ac[0].id"/>" /></td>
						<td align="left" ><s:property value="#ac[3]"/></td>
						<td align="left" ><s:property value="#ac[0].assistanceId"/></td>
						<td align="left" ><a href="#" onclick="showDetail(<s:property value="#ac[0].id"/>)"><s:property value="#ac[0].assistanceName" /></a></td>
						<td align="left" ><s:property value="#ac[1]"/></td>
						<td ><s:date name="#ac[0].contractDate" format="yyyy-MM-dd" /></td>
						<td ><s:date name="#ac[0].endDate" format="yyyy-MM-dd" /></td>
						<td align="right" ><s:property value="#ac[0].contractMoney" />
						 <s:set name="contractMoney" value="#contractMoney+#ac[0].contractMoney"></s:set>
						</td>
						<td align="right">
						<s:if test="#ac[0].hasPayAmount != null">
						<s:property value="#ac[0].hasPayAmount" />
						<s:set name="hasPayAmount" value="#hasPayAmount+#ac[0].hasPayAmount"></s:set>
						</s:if>
						<s:else>
						0.00
						</s:else>
						</td>
						<td align="right">
						<s:if test="#ac[0].hasPayAmount != null">
						<s:property value="#ac[0].contractMoney - #ac[0].hasPayAmount" />
						<s:set name="feeAmount" value="#feeAmount+#ac[0].contractMoney - #ac[0].hasPayAmount"></s:set>
						</s:if>
						<s:else>
						<s:property value="#ac[0].contractMoney" />
						<s:set name="feeAmount" value="#feeAmount+#ac[0].contractMoney"></s:set>
						</s:else>
						</td>
						<td align="left">
							<s:if test = "#ac[0].assistanceContractType==0">
								草稿	
							</s:if>
							<s:elseif test = "#ac[0].assistanceContractType==1">
								待确认
							</s:elseif>
							<s:elseif test = "#ac[0].assistanceContractType==2">
								确认通过
							</s:elseif>
							<s:elseif test = "#ac[0].assistanceContractType==3">
								确认退回
							</s:elseif>
							<s:elseif test = "#ac[0].assistanceContractType==4">
								已关闭
							</s:elseif>						
						</td>
					</tr>
        </s:iterator>
        <tr>
            <td colspan="7" align="right" >小计：</td>
            <td nowrap  align="right"><s:property value="#contractMoney"/></td>
            <td nowrap  align="right"><s:property value="#hasPayAmount"/></td>
            <td nowrap  align="right"><s:property value="#feeAmount"/></td>
            <td nowrap >&nbsp;</td>
           </tr>
         <tr>
            <td colspan="7" align="right" >总计：</td>
            <td nowrap  align="right">
            <s:if test="sumConMoney==null">
            0.00
            </s:if>
            <s:else>
            <s:property value="sumConMoney"/>
            </s:else>
            </td>
            <td nowrap  align="right">
            <s:if test="sumHasPayAmount==null">
            0.00
            </s:if>
            <s:else>
            <s:property value="sumHasPayAmount"/>
            </s:else>
            </td>
            <td nowrap  align="right">
            <s:if test="sumFeeAmount==null">
            0.00
            </s:if>
 			<s:else>
 			<s:property value="sumFeeAmount"/>
 			</s:else>           
            </td>
            <td nowrap >&nbsp;</td>
           </tr>
        </table>
<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>

</s:form>
</body>
<script type="text/javascript">
<s:property value = "#tijiaoSucc"/>
<s:if test = "#tijiaoSucc == 1">
	alert("提交成功！");
</s:if>
</script>
</html>
