<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/thousands.js" type="text/javascript"></script>
<html>

<head>
<title>收款信息录入</title>
</head>


<body style="margin: 0px;">
<s:form action="w_HarvestMeansSearch.action" theme="simple" id="w_HarvestMeansSearch">
<s:hidden name="method" id="method" value=""/>  
<s:hidden name="maxAddAmount" id="maxAddAmount"/>	  <%--不带千分位，用于判断--%>
<s:hidden name="maxAddAmount" id="maxAddAmountOrg"/>  <%--带千分位，用于显示--%>
<s:hidden name="itemId" />  
<s:hidden name="conId"/> 
<s:hidden name="reveInfoSid" />   <%--删除方法用，记录--%>
<s:hidden name="defaultDate" id="defaultDate"></s:hidden>

<input type="hidden" id="reveAmountOrg" value=""/> 
 <div align="left" style="color: #000000;" >
 <br>
		<p>当前页面：收款管理 -&gt; 录入&修改</p>
<br>
</div>

<s:if test="maxAddAmount<=0">
	<div align="left" style="color:red">提示：票据余额已为 <s:property value="maxAddAmount"/></div>
</s:if>

<div id="noticeX" align="left" style="color:red; display: none">提示：本次到款将大于计划余额</div>

	<iframe name="errorsFrame" frameborder="0"
							framespacing="0" height="0" width="100%" scrolling="no"></iframe>
 <div align="left" style="color: #000000">
合同号：<s:property value="conIdStr"/>
</div>
			<table align="center" border=1 cellpadding="1" cellspacing=1 
				width="100%" id="billApplyList"  bordercolor="#808080" style="border-collapse: collapse;">
	 		<tr class="bg_table01">
	 			<td align="center">编号</td>
	 			<td align="center">到款金额</td>
	 			<td align="center">到款方式</td>
	 			<td align="center">到款日期</td>
	 			<td align="center">操作</td>
	 		</tr>
	 	<s:iterator value="r" status="status" id="rl">
	 		<tr class="bg_table02">
	 			<td align="center"><s:property value="#status.index + 1"/></td>
	 			<td align="right"><s:property value="#rl.amount"/></td>
	 			<td align="left"><s:property value="typeManageService.getYXTypeManage(1017L,#rl.receType).typeName"/></td>
	 			<td align="center"><s:property value="#rl.amountTime"/></td>
	 			<td align="center">
	 			<s:if test="#rl.hasSure==0">
	 				<a href="#" onclick="openReveInfoModify(<s:property value="#rl.id"/>)">修改</a>
	 				/
	 				<a href="#" onclick="delReveInfo(<s:property value="#rl.id"/>)">删除</a>
	 			</s:if>
	 			</td>
	 		</tr>
	 	</s:iterator>
			<tr class="bg_table02" >
				<td>&nbsp;
				</td>
				<td  align="center">
	        		<FONT color="red"> *</FONT><s:textfield name="ri.amount" id="reveAmount" size="10" onblur="changeNum(this);"/>
	        	</td>
	        	<td  align="center">
	        		<FONT color="red"> *</FONT><s:select name="ri.receType" list="receType" listKey="typeSmall"
				       listValue="typeName" required="true" id="reveType"> </s:select>
	        	</td>        	
	        	<td  align="center">			  
				  	<FONT color="red"> *</FONT><s:textfield id="riDate" name="ri.amountTime" id="reveDate"  size="7" />
				  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('reveDate')" align="absMiddle" alt=""  />		  	
	        	</td>
	        	<td  align="center">
	        		<a href="#" onclick="saveReveInfo()" >添加</a> 
	        	</td>       
	       	</tr>
	       </table>
		</s:form>
	      
	      <br>
	       <div align="left" style="color: #000000">
	     可分配金额：
	     <s:if test="distributeMoneyX==null">
	     	0.00
	     </s:if>
	     <s:else>
	     	<s:property value="distributeMoneyX" />
	     	</s:else>  
	     </div>

	    <s:form action="w_HarvestMeansSearch.action" theme="simple" id="w_HarvestMeansSearchX">  
	    <s:hidden name="method" id="methodX" value=""/>  
	    <s:hidden name="distributeMoneyX" id="distributeMoneyX"/>  <%--不带千分位，用于判断--%>
	    <s:hidden name="distributeMoneyX" id="distributeMoneyXOrg"/>  <%--带千分位，用于显示--%>
	    <s:hidden name="itemId" />  
		<s:hidden name="conId"/> 
	       	<table id="rpPlan" align="center" border=1 cellpadding="1" cellspacing=1 
				width="100%" id="billApplyList"  bordercolor="#808080" style="border-collapse: collapse;" >	
	    	
	    	 <tr class="bg_table01" align="center">
					<td nowrap>合同阶段</td>
					<td nowrap>负责部门</td>
					<td nowrap>开票性质</td>
					<td nowrap>开票类型</td>
					<td nowrap>计划开票日期</td>
					<td nowrap>已开票金额</td>
					<td nowrap>计划收款日期</td>
					<td nowrap>余款</td>
					<td nowrap>本次到款</td>
    		 </tr>
    		  <s:iterator value="rp" status="status">
    		 
    		  <tr class="bg_table02">
    		  		<td  align="left"><s:property value="contractService.findStageName(conItemStage)" /></td>
    		  		<s:if test="itemId!=null">
    		  			<td  align="left"><s:property value="contractService.findDepName(contractItemMaininfo)" /></td>
    		  		</s:if>
    		  		<s:else>
    		  			<td  align="left"><s:property value="typeManageService.getYXTypeManage(1018,deptNum).typeName" /></td>
    		  		</s:else>
    		  		<td  align="left"><s:property value="typeManageService.getYXTypeManage(1012,billNature).typeName"/></td>
    		  		<td  align="left"><s:property value="typeManageService.getYXTypeManage(1004,billType).typeName"/></td>
    		  		<td  align="center"><s:property value="realPredBillDate"/></td>
    		  		
    		  		<td  align="right">
    		  		<s:if test="billInvoiceAmount==null">
    		  			0.00
    		  		</s:if>
    		  		<s:else>
    		  			<s:property value="billInvoiceAmount"/>
    		  		</s:else>
    		  		</td>
    		  		
    		  		<td  align="center"><s:property value="realPredReceDate"/></td>
					<td  align="right">
					<s:if test="realArriveAmount==null">
						<s:set name="realArriveAmountX" value="0.0"></s:set>
					</s:if>
					<s:else>
						<s:set name="realArriveAmountX" value="realArriveAmount"></s:set>
					</s:else>
					<s:property value="realTaxReceAmount - #realArriveAmountX" />
					</td>
									
					<s:hidden name="%{'rp['+#status.index+'].realConBillproSid'}"></s:hidden>
					<td  align="left">
					  <s:if test="currentArriveAmount!=0 && currentArriveAmount!=null">
							<s:textfield name="%{'rp['+#status.index+'].currentArriveAmount'}" size="10"  
							onblur="changeNum2(this);checkNum(this);"/>
					  </s:if>
					 <s:else>
						<s:textfield name="%{'rp['+#status.index+'].currentArriveAmount'}" size="10" value="0.00"
							onblur="changeNum2(this);checkNum(this);"/>
					 </s:else>
					</td>
					
				</tr>
			
    		 </s:iterator>
    		 </table>
	       	<table width="100%" >	
	       	<tr class="bg_table03">
	       		<td colspan="5" align="center">
	       			<input type="button" class="button01" value="保  存" onclick="distributePlan()"/>
	       			<input type="button" class="button01" value="确认收款" onclick="daokuanqueren();"> 
	       			<input type="button" class="button01" value="关  闭" onclick="closeWin()"/>
	       		</td>
	       	</tr>
	</table>
</s:form>
</body>

<script type="text/javascript">
function daokuanqueren(){
	var url="w_HarvestMeansSearch.action";
	var fm=document.getElementById("w_HarvestMeansSearchX");
	fm.action=url;
	fm.method.value="confirmOneReveInfo";
	fm.submit();
}

function checkNum(obj){
	var noticeX = document.getElementById("noticeX");
	var inputNum = parseFloatNumber(obj.value);
	var maxNum = parseFloatNumber(obj.parentNode.parentNode.cells[3].innerHTML);
	if(inputNum > maxNum){
		noticeX.style.display = "block";
	}
	else{
		noticeX.style.display = "none";
	}
}

function distributePlan(){
	if(!validate2()){
		document.forms(1).method.value = "distributePlan";
		document.forms(1).submit();
	 }
}

function saveReveInfo(){
	if(!validate()){
 		 //document.forms(0).sign.value = "1";
		 document.forms(0).method.value = "saveReve";
		 document.forms(0).submit();
	 }
}

function delReveInfo(obj){
	if(confirm('确定要删除吗？')){
		document.forms(0).method.value = "delReve";
		document.forms(0).reveInfoSid.value = obj;
		document.forms(0).submit();
	}
}

function openReveInfoModify(obj){
 	openUrl('w_HarvestMeansSearch.action?method=openReveInfoModify&reveInfoSid='+obj);
 	//window.open('w_HarvestMeansSearch.action?method=openReveInfoModify&reveInfoSid='+obj);
}

function openUrl(url){
      window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=300,width=500');
}  
            
function refreshSuper()	{
	document.forms(0).action="/yx/harvestMeansManager/w_HarvestMeansSearch.action";
	document.forms(0).method.value="showReveInfo";
	document.forms(0).submit();
}

function validate2(){
	var ev2=new Validator();
	with(w_HarvestMeansSearch){
		     ev2.test("equals","分配总金额不等于可分配金额，请重新分配", $('distributeMoneyX').value ,calc());
	} 
	ev2.writeErrors(errorsFrame, "errorsFrame");	 
	if (ev2.size() > 0) {
		  return true;
	}
	return false;
}


function validate(){
	var ev2=new Validator();
	with(w_HarvestMeansSearch){
		     ev2.test("+float","到款金额大于0的数字!",$('reveAmount').value);
		     ev2.test("notblank","到款方为空,请选择",$('reveType').value);
		     if( ev2.test("dateYX","到款日期格式不正确",$("reveDate").value)){
		       	   var arrJHRQ=$("reveDate").value.split('-'); //转成成数组，分别为年，月，日，下同
			       var arrJHWCSJ=$("defaultDate").value.split('-');
			       var dateJHRQ=new Date(parseInt(arrJHRQ[0]),parseInt(arrJHRQ[1])-1,parseInt(arrJHRQ[2]),0,0,0); //新建日期对象
			       var dateJHWCSJ=new Date(parseInt(arrJHWCSJ[0]),parseInt(arrJHWCSJ[1])-1,parseInt(arrJHWCSJ[2]),0,0,0);
			       if (dateJHRQ.getTime()>dateJHWCSJ.getTime()) {
			           ev2.addError("到款日期大于当天日期");
			       }    

		     }
		     ev2.test("notblank","到款日期格为空",$("reveDate").value);
		     
		    //<s:if test="noRemain != 1">
		    //	 ev2.test("greater","到款金额不允许超过 "+$('maxAddAmountOrg').value , $('maxAddAmount').value , $('reveAmountOrg').value   );
		    // </s:if>
		}  
	ev2.writeErrors(errorsFrame, "errorsFrame");
	if (ev2.size() > 0) {
		  return true;
	}
	 return false;
}

 <%--输入框显示千分位，隐藏于去千分位，用于判断--%>
function changeNum(obj){
	obj.value = formatNumber(parseFloatNumber(obj.value),'#,###.00');
	document.forms(0).reveAmountOrg.value = parseFloatNumber(obj.value);
}

function changeNum2(obj){
	obj.value = formatNumber(parseFloatNumber(obj.value),'#,###.00');
}


function closeWin(){
	opener.refresh();
	window.close();
}

function calc(){
	var table = document.getElementById("rpPlan");
	var inputs=table.getElementsByTagName("input");
	var amount = 0;

	for(var i=1;i<inputs.length;i=i+2){
		if(inputs[i].value!=""){
			amount = amount +  parseFloatNumber(inputs[i].value);
		}
	}
	return amount;
}

</script>



<script type="text/javascript">
	var f = document.getElementById("maxAddAmount");
	f.value = parseFloatNumber(f.value);
	
	var t = document.getElementById("distributeMoneyX");
	t.value = parseFloatNumber(t.value);
	
<s:if test = "#hasErrorMsg == false">
	alert("还没有生成该月的月度收款计划，不能收款！");
</s:if>

<s:if test="sign == 1">
	
		opener.refresh();
		alert("收款信息修改成功");

</s:if>

<s:if test="sign == 2 || sign == 3 ||sign==4">

		<%-- 删除、添加 成功 --%>
		opener.refresh();
	
</s:if>

</script>
</html>