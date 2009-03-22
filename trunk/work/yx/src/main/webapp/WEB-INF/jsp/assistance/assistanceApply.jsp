<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协合同付款申请</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script type="text/javascript" src="/yx/commons/scripts/public.js"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script language="javascript">
var asectionArray = new Array();
	<s:iterator value="asectionList" id="assistanceSection">
		asectionArray[asectionArray.length] =  new Array('<s:property value = "id"/>','<s:property value = "sectionAmount"/>','<s:property value="contractService.findStageName(assistanceStageSId)" />');
	</s:iterator>
var relationTicketArr = new Array();
		  <s:iterator id="result" value="tList">
		  relationTicketArr[relationTicketArr.length]=new Array("<s:property value="ticket.id"/>","<s:property value="ticket.type"/>",<s:if test = "ticket.doneMoney == null">"<s:property value="ticket.money" />"</s:if>
						<s:else>"<s:property value="ticket.doneMoney" />"</s:else>);
	     </s:iterator>
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');
}
	function relationTicket(id){
	    assistance.action = "/yx/assistance/assistance.action?ids="+id;
	    assistance.method.value="relationTicket";
	    assistance.submit();
	}
	function openRelationWindow(contractId){
		//验证是否有发票信息   有的话弹出发票信息  没有的话就提示需要添加发票
		var flag = checkIsNull('/yx/assistance/assistanceApplyPay.action','checkIsNull',contractId);
		if(flag){
			var isPrePayNode = document.assistance.elements("pi.applyPay");
			var isPrePay="0";
			if(isPrePayNode.checked){
				isPrePay="1";
			}
			openUrl('../assistance/assistance.action?method=enterTicket&id='+contractId+"&isPrePay="+isPrePay);
		}else{
			alert("你还没有发票信息,请先添加发票信息.");	
		}
	}
	//验证是否有发票信息
	function checkIsNull(url, method, name){
		var flag;
        var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
			  if(responseText =='1'){
			  	 flag = true;
			  }
			  else{
			  	 flag = false;
			  }
		    });
		   myRequest.send("method="+method+"&assistanceId="+name+"&randomNum="+Math.random());
	   return flag;
    }
	
	
	function unchainRelation(ticketId){
	    assistance.action = "/yx/assistance/assistance.action?ticketId="+ticketId;
	    assistance.method.value="unchainRelation";
	    assistance.submit();
	}
	
<!--	提交的时候的验证-->
	function validate()
	{	
		var ev2=new Validator();
		var isPrePayNode = document.assistance.elements("pi.applyPay");
		var isPrePay="0";
		if(isPrePayNode.checked){
			isPrePay="1";
		}
		var amount = getRelationAmount();
		if(amount == 0.00){
			ev2.addError("没选阶段金额！");
		}
		if(isPrePay=="0"){///表示非预付需要验证发票和阶段
			if(relationTicketArr == null || relationTicketArr.length ==0){
				ev2.addError("非预付款请关联发票！");
			}
			if(!checkTicketType("0")){
				ev2.addError("非预付款不能关联收据！");
			}
			/////非预付款要验证任务号和接受号。
			var renwu=document.assistance.elements("pi.assignmentId").value;
			var jieshou=document.assistance.elements("pi.receivNum").value;
			ev2.test("notblank","请填写任务号",renwu);
			ev2.test("notblank","请填写接受号",jieshou);
			////非预付要验证阶段金额+预付金额的和小于等于发票余额的和
			var  hasRelation = calHasRelationAmount();
			var prepSum = parseFloatNumber(document.assistance.prepPaySum.value);
			if(amount+prepSum > hasRelation){
				ev2.addError("非预付款本次支付金额加上预付款金额大于关联的发票余额！");
			}
			
		}else if(isPrePay=="1"){/////表示预付，预付的话只要验证一下阶段金额就行了
			if(!checkTicketType("1")){
				ev2.addError("预付款只能关联收据！");
			}
		}
		ev2.writeErrors(errorsFrame,"errorsFrame");
		if(ev2.size()>0){
			return true;
		}
		return false;
	}
<!--保存的时候的验证-->
	function validate1()
	{	
		var ev2=new Validator();
		var isPrePayNode = document.assistance.elements("pi.applyPay");
		var isPrePay="0";
		if(isPrePayNode.checked){
			isPrePay="1";
		}
		var amount = getRelationAmount();
		if(amount == 0.00){
			ev2.addError("没选阶段金额！");
		}
		if(isPrePay=="0"){///表示非预付需要验证发票和阶段
			if(relationTicketArr == null || relationTicketArr.length ==0){
				ev2.addError("非预付款请关联发票！");
			}
			if(!checkTicketType("0")){
				ev2.addError("非预付款不能关联收据！");
			}
			/////非预付款要验证任务号和接受号。
<!--			var renwu=document.assistance.elements("pi.assignmentId").value;-->
<!--			var jieshou=document.assistance.elements("pi.receivNum").value;-->
<!--			ev2.test("notblank","请填写任务号",renwu);-->
<!--			ev2.test("notblank","请填写接受号",jieshou);-->
			////非预付要验证阶段金额+预付金额的和小于等于发票余额的和
			var  hasRelation = calHasRelationAmount();
			var prepSum = parseFloatNumber(document.assistance.prepPaySum.value);
			if(amount+prepSum > hasRelation){
				ev2.addError("非预付款本次支付金额加上预付款金额小于等于关联的发票余额！");
			}
			
		}else if(isPrePay=="1"){/////表示预付，预付的话只要验证一下阶段金额就行了
			if(!checkTicketType("1")){
				ev2.addError("预付款只能关联收据！");
			}
		}
		ev2.writeErrors(errorsFrame,"errorsFrame");
		if(ev2.size()>0){
			return true;
		}
		return false;
	}
	///////验证票据类型，如果是预付，只能出现收据，如果是非预付不能出现收据
	function checkTicketType(prePay){
		if(relationTicketArr!=null&&relationTicketArr.length>0){
			for(var i = 0; i <relationTicketArr.length; i ++){
				if(prePay=="1"){///1表示预付款，预付款，关联的只能是收据
					if(relationTicketArr[i][1] != "4"){
						return false;
					}
				}
				if(prePay=="0"){
					if(relationTicketArr[i][1] == "4"){
						return false;
					}
				}
			}
			return true;
		}else{
			return true;
		}
	}
	function calHasRelationAmount(){
		var sum = 0.00;
		if(relationTicketArr!=null&&relationTicketArr.length>0){
			for(var i = 0; i <relationTicketArr.length; i ++){
					sum += parseFloatNumber(relationTicketArr[i][2]);
			}
		}
		return sum;
	}
	
<!--计算点击关联了的阶段的金额	-->
	function getRelationAmount()
	{
		var amount = 0.0;
		var sectionNode = document.getElementsByName("assistanceSectionId");
		if(sectionNode != null && sectionNode.length > 0){
			for(var k = 0;k < sectionNode.length ; k++){
				if(sectionNode[k].checked){
					for(var j = 0;j < asectionArray.length; j++){
						if(sectionNode[k].value == asectionArray[j][0]){
							amount += parseFloatNumber(asectionArray[j][1]);
						}
					}
				}
			}
		}
		return amount;
	}
	function doSave()
	{
		var isPrePayNode = document.assistance.elements("pi.applyPay");
		var returnFlag = true;
		if(!isPrePayNode.checked){
			returnFlag = checkreceivNumExists($('pi.receivNum'));
		 }
		 
		var validateFlag = validate1();
		if(returnFlag && !validateFlag){
			document.assistance.submit();
		}
	}
	
	function tijiao()
	{
		
		var validateFlag = validate();
		if(validateFlag){
			return;
		}
		var returnFlag = checkreceivNumExists($('pi.receivNum'));
		if(!returnFlag){
			return ;
		}
		if(returnFlag && !validateFlag){
			document.assistance.method.value='passAssistanceApply';
			document.assistance.submit();
		}
	}
	
	function checkAssignmentIdExists(obj){
		var assignmentId = obj.value;
		var url = "/yx/assistance/assistance.action";
		var method = "checkAssignmentIdExists";
		 var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
		      if(responseText == "true"){
			 	alert("任务号："+assignmentId+" 已经存在！请重新输入");	
			 	obj.value = "";
			 	obj.focus();
			 }
		    });
		   myRequest.send("method="+method+"&assignmentId="+assignmentId+"&randomNum="+Math.random());
	}
	function checkreceivNumExists(obj){
	
		var returnFlag = false;
		var receiveNum = obj.value;
		var url = "/yx/assistance/assistance.action";
		var method = "checkreceivNumExists";
		 var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
		      if(responseText == "true"){
			    var flag = window.confirm("接受号：["+receiveNum+"] 重复,是否继续提交?");	
			 	if(!flag){
<!--				 	obj.value = "";-->
				 	obj.focus();
				 	returnFlag = false;
			 	}else{
			 		returnFlag =true;
			 	}
			 	
			 }else{
			 	returnFlag =true;
			 }
		    });
		   myRequest.send("method="+method+"&receiveNum="+receiveNum+"&randomNum="+Math.random());
		return returnFlag;
	}
	
	function splitSection(obj,i,compareValue){
		formatInputNumber(obj);
		var id = document.assistance.assistanceContractId.value;
		if( obj.value == null || obj.value == "" ||obj.value == " "){
			alert("请填写金额!");
			obj.value=compareValue;
			formatInputNumber(obj);
			obj.focus();
			return;
		}
		
		if(!validateSelfFloat(obj.value)){
			alert("金额必须为数字");
			obj.value=compareValue;
			formatInputNumber(obj);
			obj.focus();
			return;
		}
		
		var opValue =parseFloatNumber(obj.value);
		if(opValue>parseFloatNumber(compareValue)){
			alert("金额大与阶段金额！");
			obj.value=compareValue;
			formatInputNumber(obj);
			obj.focus();
			return;
		}
	
		if(opValue != parseFloatNumber(compareValue)){
			var sectionNode = document.getElementsByName("assistanceSectionId");
			if(sectionNode[i] == null || sectionNode[i].checked==false){
				alert("请选中该阶段！");
				obj.value=compareValue;
				formatInputNumber(obj);
				return;
			}
			var flag = window.confirm("金额不等于阶段金额，是否拆分？");
			if(flag){
				var sectionId = sectionNode[i].value;
				document.assistance.action="/yx/assistance/assistance.action?ids="+id;
				document.assistance.method.value="splitSection";
				document.assistance.sectionId.value=sectionId;
				document.assistance.splitSectionAmount.value=opValue;
				document.assistance.submit();
			}else{
				obj.value = compareValue;
				formatInputNumber(obj);
			}
		}
	}
	
	function addTicket(){
		var checkedValue = document.assistance.assistanceContractId.value;
		var flag = checkIsNullBill('/yx/assistance/assistanceApplyPay.action','checkIsNullBill',checkedValue);
		if(flag){
			if(confirm("该外协合同已全额录票,是否需要添加收据信息?")){
				var url = "/yx/assistance/assistanceTicket.action?fromContractManager=1&checkHasBill=1&assistanceConId="+checkedValue;
				window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=400,width=650');
			}
		}else{
			var url = "/yx/assistance/assistanceTicket.action?fromContractManager=1&assistanceConId="+checkedValue;
			window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=400,width=650');
		}	
	}
	
	//验证是否添加完发票信息
	function checkIsNullBill(url, method, name){
		var flag;
        var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
			  if(responseText =='1'){
			  	 flag = true;
			  }
			  else{
			  	 flag = false;
			  }
		    });
		   myRequest.send("method="+method+"&assistanceId="+name+"&randomNum="+Math.random());
	   return flag;
    }
    ////检查。当正常付款关联发票以后再次选择预付款直接提示不能选择
    function checkPrepPay(obj){
    	if(obj.checked){
    		var flag  = checkTicketType("1");
    		if(!flag){
    			alert("正常付款已经关联发票，不能选中预付款！");
    			obj.checked = false;
    		}
    	}
    }
    
    function calAmountAndInfo(obj)
    {
    	var checkBoxNode = document.assistance.assistanceSectionId;
    	var info = document.assistance.elements("pi.info").value;
    	calThisPayAmount();
    	if(obj.checked){
    		var name = getSectionName(obj.value);
    		if(name != null)
    		{
    			info += " " + name;
    			document.assistance.elements("pi.info").value =info;
    		}
    		
    	}else{
    		var name = getSectionName(obj.value);
    		if(name != null)
    		{
    			if(info.indexOf(" "+name)>-1){
    				info = info.replace(" "+name,"");
    				document.assistance.elements("pi.info").value =info;
    			}
    		}
    	}
    }
    function getSectionName(sectionId){
    	if(asectionArray!=null&&asectionArray.length>0){
    		for(var k = 0 ; k <asectionArray.length ; k ++ ){
    			if(asectionArray[k][0]==sectionId){
    				return asectionArray[k][2];
    			}
    		}
    	}
			return null;    	
    }
    
</script>
<style type="text/css">
<!--
.STYLE1 {font-size: 14px}
-->
</style>
</head>
<body>
	<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<s:form theme="simple" action="assistance">
<s:hidden name="method" value="saveAssistanceApply"></s:hidden>
<s:hidden name="assistanceContractId"></s:hidden>
<input type="hidden" name="sectionId"/>
<input type="hidden" name="splitSectionAmount"/>
<s:hidden name="prepPaySum"></s:hidden>
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr align="left">
		<td> 
		<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="4" align="left">当前页面：外协管理->外协合同付款申请</td>
			</tr>
			<tr>
            	<td colspan="4" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
          	</tr>
            	<td colspan="4" align="right" class="bg_table04">&nbsp;</td>
			    
			    <tr align="center">
                  <td class="bg_table02" align="right">主体合同号：</td>
			      <td align="left" class="bg_table02"><s:property value="cm.conId"/></td>
			      <td class="bg_table02" align="right">合同名称：</td>
			      <td class="bg_table02" align="left"><s:property value="cm.conName"/></td>
	      </tr>
			    <tr align="center">
			      <td class="bg_table02" align="right">项目号：</td>
			      <td class="bg_table02" align="left"><s:property value="ac.mainProjectId"/></td>
			      <td align="right" class="bg_table02">成本中心：</td>
			      <td align="left" class="bg_table02"><s:property value="typemanageservice.getYXTypeManage(1018,c.itemResDept).typeName"/></td>
	      </tr>
			    <tr align="center">
			      <td class="bg_table02" align="right">外协合同号：</td>
			      <td class="bg_table02" align="left"><s:property value="ac.assistanceId"/></td>
			      <td class="bg_table02" align="right">外协合同名：</td>
			      <td class="bg_table02" align="left"><s:property value="ac.assistanceName"/></td>
	      </tr>
			    <tr align="center">
			      <td class="bg_table02" align="right">合同金额：</td>
			      <td class="bg_table02" align="left"><s:property value="ac.contractMoney"/></td>
			      <td class="bg_table02" align="right">申请人：</td>
			      <td class="bg_table02" align="left"><s:property value="userName"/></td>
	      </tr>
			    <tr align="center">
			      <td align="right" class="bg_table02">申请部门：</td>
			      <td align="left" class="bg_table02"><s:property value="groupName"/></td>
			      <td class="bg_table02" align="right">申请日期：</td>
                  <td class="bg_table02" align="left">
                  <s:if test="pi == null || pi.applyDate == null">
                	  <s:property value="date"/>
                  </s:if>
                  <s:else>
                 	 <s:property value="pi.applyDate"/>
                  </s:else>
                  </td>
         </tr>
	           	<tr class="bg_table02">
		          <td colspan="4" align="right"><hr></td>
          </tr>
	          <tr class="bg_table02">
			      <td align="right">供应商名称：</td>
			      <td align="left"><s:property value="s.supplierName"/>
			        </td>
			      <td align="right">代码：</td>
			      <td align="left"><s:property value="s.supplierCode"/></td>
		    </tr>
			<tr>
			  <td class="bg_table02" align="right">开户银行：</td>
			  <td class="bg_table02" align="left"><s:property value="s.billBank"/></td>
			  <td class="bg_table02" align="right">银行帐号：</td>
			  <td class="bg_table02" align="left"><s:property value="s.billAccount"/></td>
		  </tr>
			<tr>
			  <td colspan="4" align="right" class="bg_table02"><hr></td>
		  </tr>
          </table>
      	 </td>
      </tr>
       <tr>
          <td>
          <table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
			<tr>
			  <td colspan="7" align="right" class="bg_table02"><div align="left" class="STYLE1">支付履历：</div></td>
		    </tr>
			<tr>
		  <td width="11%" class="bg_table01"><div align="center">申请序号</div></td>
          <td width="12%" class="bg_table01"><div align="center">申请日期</div></td>
          <td width="14%" class="bg_table01"><div align="center">申请金额</div></td>
          <td width="14%" class="bg_table01"><div align="center">任务号</div></td>
          <td width="14%" class="bg_table01"><div align="center">接受号</div></td>
          <td width="14%" class="bg_table01"><div align="center">是否预付</div></td>
          <td width="11%" nowrap class="bg_table01"><div align="center">申请单状态</div></td>
		  </tr>
		  <s:iterator id="result" value="pList" status="resultIndex">
			<tr>
          		<td class="bg_table02"><div align="center">
          			<s:property value="#resultIndex.index+1"/>
<!--          		<s:property value="applyInfoCode"/>-->
          		</div></td>
          		<td class="bg_table02"><div align="center"><s:property value="applyDate"/></div></td>
          		<td class="bg_table02"><div align="center"><s:property value="payNum"/></div></td>
          		<td class="bg_table02"><div align="center"><s:property value="assignmentId"/></div></td>
          		<td class="bg_table02"><div align="center"><s:property value="receivNum"/></div></td>
          		<td class="bg_table02"><div align="center">
          			<s:if test="applyPay">
          				预付款
          			</s:if>
          			<s:else>
          				正常付款
          			</s:else>
          		</div></td>
         		<td class="bg_table02"><div align="center">
         				<s:if test="payState==0">
         					草稿
         				</s:if>
         				<s:elseif test="payState==1">
         					待确认
         				</s:elseif>
         				<s:elseif test="payState==2">
         					确认通过
         				</s:elseif>
         				<s:elseif test="payState==3">
         					确认退回
         				</s:elseif>
         				<s:elseif test="payState==4">
         					确认处理
         				</s:elseif>
         		</div></td>
			<tr>
		  </s:iterator>	
         </table>
         </td></tr>
         <tr>
         <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
			<tr class="bg_table02 STYLE1">
			  <td colspan="6">已关联发票：
			  <a href = "javascript:addTicket();">新增发票</a>&nbsp;&nbsp;&nbsp;&nbsp;
			  <a href = "javascript:openRelationWindow('<s:property value="ac.id"/>');">关联发票</a>
			  </td>
	    </tr>
			<tr class="bg_table01">
             <td width="15%"   class="bg_table01"><div align="center">发票号</div></td>
         	 <td width="16%"   class="bg_table01"><div align="center">发票类型</div></td>
          	 <td width="14%"   class="bg_table01"><div align="center">发票金额</div></td>
             <td width="11%"   class="bg_table01"><div align="center">发票余额</div></td>
             <td width="12%" class="bg_table01"><div align="center">开票时间</div></td>
             <td width="10%" class="bg_table01" ><div align="center">
          </div></td>
			</tr>
		  <s:iterator id="result" value="tList">
	   	 	<tr>
	   	 		<td class="bg_table02"><div align="center"><s:property value="ticket.num"/></div></td>
	      		<td class="bg_table02"><div align="center"><s:property value="typemanageservice.getYXTypeManage(1004,ticket.type).typeName"/></div></td>
	      		<td class="bg_table02"><div align="center"><s:property value="ticket.money"/></div></td>
	      		<td class="bg_table02"><div align="center"><s:property value="ticket.doneMoney"/></div></td>
	      		<td class="bg_table02"><div align="center"><s:property value="ticket.ticket_Time"/></div></td>
          		<td class="bg_table02"><div align="center">
          		<a href="javascript:unchainRelation(<s:property value="ticket.id"/>)">解除关联</a>
          		</div></td>
	    	</tr>
	     </s:iterator>	 
		 <tr>
			  <td align="right"  colspan="6" class="bg_table02"><hr></td>
		 </tr>
         </table>
         </td>
         </tr>
         <s:if test="%{#session.prepApplyPayLists != null && #session.prepApplyPayLists.size() >0}">
         <tr><td>
         <table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
        	<tr>
        		<td colspan="5" class="bg_table02 STYLE1">已关联预付款：</td>
        	</tr>
        	<tr class="bg_table02" align="center">
				<td>外协合同名称</td>
				<td>供应商名称</td>
				<td>申请日期</td>
				<td>申请金额</td>
				<td>实际收款日期</td>
		    </tr>
		<s:iterator value="%{#session.prepApplyPayLists}">
			<tr class="bg_table02">
				<td><s:property value="assistanceService.getContactByPayInfoId(id).assistanceName"/></td>
				<td><s:property value="assistanceService.getSupplierBySupplyId(supplyId).supplierName"/></td>
				<td><s:property value="applyDate"/></td>
				<td><s:property value="payNum"/></td>
				<td><s:property value="realPayTime"/></td>
			</tr>
		</s:iterator>
		<tr>
			  <td align="right"  colspan="5" class="bg_table02"><hr></td>
		 </tr>
        </table>
         </td></tr>
         </s:if>
         
        <tr>
        <td>
        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
        	<tr>
        		<td colspan="6" class="bg_table02 STYLE1">关联阶段：</td>
        	</tr>
        	<tr>
        		<td class="bg_table01" align="center">
        		选择
        		</td>
        		<td class="bg_table01" align="center">
        			阶段名称
        		</td>
        		<td class="bg_table01" align="center">
        			辅助阶段
        		</td>
        		<td class="bg_table01" align="center">
        			阶段金额
        		</td>
        		<td class="bg_table01" align="center">
        			阶段日期
        		</td>
        		<td class="bg_table01" align="center">
        			金额
        		</td>
        	</tr>
        	
        	<s:iterator value="asectionList" id="assistanceSection" status="sectionIndex">
        		<tr>
        			<td align="right" class="bg_table02">
        				<s:if test="!assistanceService.checkSectionIsRelation(id)">
        					<input type="checkbox" onclick="calAmountAndInfo(this);" name= "assistanceSectionId" value="<s:property value = "#assistanceSection.id"/>" />
        				</s:if>
        				<s:else>
        				<input type="checkbox" name= "assistanceSectionId" value="<s:property value = "#assistanceSection.id"/>" disabled="true" />
        				</s:else>
        			</td>
        			<td class="bg_table02" align="left">
        				<s:property value="contractService.findStageName(#assistanceSection.assistanceStageSId)" />
        			</td>
        			<td class="bg_table02" align="left">
						   <s:property value="sectionRemarks"/>     				
        			</td>
        			<td class="bg_table02" align="center">
        			<s:property value="sectionAmount" />
        			</td>
        			<td class="bg_table02" align="center">
        			<s:property value="sectionBillTime" />
        			</td>
        			<td class="bg_table02" align="left">
        			<s:if test="!assistanceService.checkSectionIsRelation(id)">
        					<input type="text"  value="<s:property value = "sectionAmount"/>" 
        					id="section<s:property value="#sectionIndex.index"/>" 
        					name="section<s:property value="#sectionIndex.index"/>" 
        					onblur="splitSection(this,'<s:property value="#sectionIndex.index"/>','<s:property value = "sectionAmount"/>')"/>
        				</s:if>
        				<s:else>
        				<input type="text" value="<s:property value = "sectionAmount"/>" id="section<s:property value="#sectionIndex.index"/>" name="section<s:property value="#sectionIndex.index"/>"  disabled="true"/>
        				</s:else>
        				
        			</td>
        		</tr>
        	</s:iterator>
        </table>
        </td>
       </tr> 
        <tr>
        <td>
        <table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
			<tr>
			  <td class="bg_table02" align="right">本次申请支付金额：</td>
			  <td class="bg_table02" align="left" id="testTicketSum">
				0.00
			  </td>
			 
			  <td class="bg_table02" align="right">是否预付款：</td>
			  <td class="bg_table02" align="left">
			   <s:if test="%{#session.prepApplyPayLists != null && #session.prepApplyPayLists.size() >0}">
			  	<s:checkbox name="pi.applyPay" disabled="true"  ></s:checkbox>
			  </s:if>
			  <s:else>
			  <s:checkbox name="pi.applyPay" onclick="checkPrepPay(this);"></s:checkbox>
			  </s:else>
			  </td>
		   </tr>
			<tr>
			  <td class="bg_table02" align="right">任务号：</td>
			  <td class="bg_table02" align="left"><s:textfield name="pi.assignmentId"/></td>
			  <td class="bg_table02" align="right"> 接收号：</td>
			  <td class="bg_table02" align="left"><s:textfield name="pi.receivNum"/></td>
		  </tr>
			<tr>
			  <td align="right" class="bg_table02">付款事项说明：</td>
			  <td align="left" class="bg_table02">
                  <label>
                  <s:textarea cols="20" rows="5" name="pi.info"></s:textarea>
                  </label>
              </td>
			  <td align="right" class="bg_table02">备注：</td>
			  <td align="left" class="bg_table02"><s:textarea cols="20" rows="5" name="pi.remark"></s:textarea></td>
		  </tr>
		</table>
		</td></tr>
		<tr><td>
		<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>	
		<tr valign="top">
			  <td class="bg_table04"><div align="center"><span class="bg_blue07">
              <input name="save3" type="button" class="button02" value="保   存" onclick="doSave();"/>
              &nbsp;<input name="save3" type="button" class="button02" onClick="tijiao();" value="确认提交"> 
              &nbsp;</span>
		        <span class="bg_blue07">&nbsp;
			      <input name="save" type="button" class="button02" onClick="javascript:window.close();" value="关   闭">
		        </span></div></td>
		 </tr>
		<tr valign="top">
			  <td class="bg_table04"><div align="center"></div></td>
		 </tr>
		</TABLE>
	  </td>
  </tr>
</table>
</s:form>
<script type="text/javascript">
	var arr =new Array();
	<s:if test = "assistanceSectionId != null">
		<s:iterator value ="assistanceSectionId" id = "assistance">
		arr[arr.length] = new Array("<s:property value = "#assistance"/>");
		</s:iterator>
		 checkedCheckbox();
		 calThisPayAmount();
	</s:if>
	function checkedCheckbox(){
		var checkNode = document.getElementsByName("assistanceSectionId");
		if(checkNode!= null && checkNode.length>0&&arr!=null&&arr.length>0){
			for(var i = 0;i<checkNode.length;i++){
				for(var j = 0; j <arr.length;j++){
					if(checkNode[i].value == arr[j]){
						checkNode[i].checked = true;
					}
				}
			}
		}
	}
	function calThisPayAmount(){
		var sum = 0.00;
		var checkNode = document.getElementsByName("assistanceSectionId");
		if(checkNode!= null && checkNode.length>0&&asectionArray!=null&&asectionArray.length>0){
			for(var i = 0;i<checkNode.length;i++){
				if(checkNode[i].checked){
					for(var j = 0; j <asectionArray.length;j++){
						if(checkNode[i].value == asectionArray[j][0]){
							sum+=parseFloatNumber(asectionArray[j][1]);
						}
					}
				}
			}
		}
		document.getElementById("testTicketSum").innerHTML = sum;
	}
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
	alert(alertMsg);
	</s:if>	
</script>

</body>
</html>
