<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>已签合同开票申请</title>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	
	// 点击月份触发onchange事件
	function monthChange(){
		var yearSel = document.getElementById("yearSel").value;
		var monthSel = document.getElementById("monthSel").value;
	 	location.href="../billtoReceipt/applyBillQuery.action?method=selectMonth&monthSel="+monthSel+"&yearSel="+yearSel;
	}
	function splitAmount(realIdVal)
	{ 
			window.open("splitBillAmountQuery.action?realPlanId="+realIdVal,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=300,width=400');
		//if(!checkIsRelation("splitBillAmountQuery.action","isRelation",realIdVal)){}
	}	
	function checkIsRelation(url,method,eid){
		var flag=false;
	  	var myRequest = new Request({url:url,async:false,method:'get'});
  		myRequest.addEvent("onSuccess",function(responseText, responseXML){
			if(responseText =='false'){
				alert("该订单不能打印！");
				flag = false;
			}else{
				flag = true;
			}
	   	});
		myRequest.send("method="+method+"&expId="+eid+"&randomNum="+Math.random());
		return flag;
	}	
	//确认开票
	function monthApply(typeVal){
	   var flag=0;
	   var chck=document.getElementsByName("monthlyBillproSids");
	   var checkStr="";
	   for(var i=0;i<chck.length;i++){
	       if(chck[i].checked==true){
                checkStr=checkStr+","+chck[i].value;
	       		var contType="contype"+chck[i].value;
	       		if($(contType).value == '1'){
	       			var item="item"+chck[i].value;
	       			if($(item).value == ""){
	       				alert("没有项目号,不能开票");
	       				return;
	       			}
	       		}
	           flag++;
	       }
	   }
	   if(flag>0){
	        if(typeVal == 1){
				window.open("applyBillQuery.action?method=showApply&"+$(applyBillQuery).toQueryString());
				//window.open("applyBillQuery.action?method=showApply&"+$(applyBillQuery).toQueryString(),'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=900');
			}
			if(typeVal == 2){
				if(flag > 1){
					window.open("applyBillQuery.action?method=togetherBill&"+$(applyBillQuery).toQueryString(),'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=900');
				}
				if(flag == 1){
					alert("请至少选择两项!")
				}
			}
			
       }
       else{
       		alert("您没有选择任何一项!");
       }
	}	
	// 刷新父页面
	function refreshClient(){
		location.href="../billtoReceipt/applyBillQuery.action";
	}
	function torealhistory(){
	   var flag=0;
	   var checkBoxs = ($$("input[name='monthlyBillproSids'][checked]"));
	   if(checkBoxs!=null){
	   	flag=checkBoxs.length;
	   }
	   if(flag==0){
	    alert("您还没有选择,请选择要查看的开票计划变更");
	    return;
	    }
	   var checkValue = checkBoxs[0].value;
	   if(flag ==1){
	   		var url="/yx/contract/realContractBillandRecePlan.action?realConBillproSid="+checkValue;
             //window.open(url);
             window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
        }else{
           alert("开票计划变更只能选择一项");
        }	
	}
	
	function applyReceipt(){
		var baseurl = "/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=doApplyBillFromRcbrps&comeFrom=2";
		var cks = document.getElementsByName("monthlyBillproSids");
		var len = cks.length;
		for(var i=0;i<len;i++){ 
			if(cks[i].checked){
				var contType="contype"+cks[i].value;
	       		if($(contType).value == '1'){
	       			var item="item"+cks[i].value;
	       			if($(item).value == ""){
	       				alert("没有项目号,不能开票");
	       				return;
	       			}
	       		}	
	       }
	    }
		for(var i=0;i<len;i++){ 
			if(cks[i].checked){
				var contType="contype"+cks[i].value;
	       		if($(contType).value == '1'){
	       			var item="item"+cks[i].value;
	       			if($(item).value == ""){
	       				alert("没有项目号,不能开票");
	       				return;
	       			}
	       		}		
				var rcform = document.getElementById("applyBillQuery");
				rcform.target = "_blank";
	        	rcform.action =  baseurl;
	        	rcform.submit();
	        	
	        	rcform.target = "_self";
	        	rcform.action = "/yx/billtoReceipt/applyBillQuery.action";
	        	
				return;
			}	
		}
		alert("请先选择计划");
	}
    function reflushPage(){
		 <s:if test="info != null">
		 	location.href="../billtoReceipt/applyBillQuery.action";
		 </s:if>
		 <s:else>
		 	location.href="../billtoReceipt/applyBillQuery.action?method=showCon&conId=<s:property value="#parameters.conId" />";
		 </s:else>
    }
    
    function showCon(){
   		 var checkArr=document.getElementsByName("monthlyBillproSids");
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
         	  var con="con"+checkStr.substring(1);
    		  openWin('applyBillQuery.action?method=showCon&conId='+$(con).value,900,700);
         }
		   if(j==0){
		      alert("您还没有选择修改的对象！");
		   }
		   if(j>1){
    
    		 alert("不能选择多个修改对象！");
   			}
    }
    
    function mergePlan(){
       var flag=0;
	   var chck=document.getElementsByName("monthlyBillproSids");
	   var checkStr="";
	   for(var i=0;i<chck.length;i++){
	       if(chck[i].checked==true){
                checkStr=checkStr+","+chck[i].value;
	       		var contType="contype"+chck[i].value;
	           flag++;
	       }
	   }
	   if(flag>0){
			if(flag > 1){
					window.open("applyBillQuery.action?method=mergePlan&"+$(applyBillQuery).toQueryString(),'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=900');
			}
			if(flag == 1){
				alert("请至少选择两项!")
			}
       }
       else{
       		alert("您没有选择任何一项!");
       }
	}	
</script>
<s:if test="succSave == 0">
	<script language="javascript"> 
		alert("开票申请已保存!");
	</script>
</s:if>
<s:if test="succSave == 1">
	<script language="javascript"> 
		alert("你选择的计划中合同号不相同!");
		window.close();
	</script>
</s:if>
<s:if test="succSave == 2">
	<script language="javascript"> 
		alert("合并发票申请已保存!");
	</script>
</s:if>
<s:if test="succSave == 3">
	<script language="javascript"> 
		alert("你选择的计划中不存在合同，请重新选择!");
		window.close();
	</script>
</s:if>
<s:if test="succSave == 4">
	<script language="javascript"> 
		alert("你选择的计划中开票性质不同!");
		window.close();
	</script>
</s:if>
<s:if test="succSave == 5">
	<script language="javascript"> 
		alert("你选择的计划中开票类型不同!");
		window.close();
	</script>
</s:if>
<s:if test="succSave == 6">
	<script language="javascript"> 
		alert("你选择的计划不符合合并条件!");
		window.close();
	</script>
</s:if>
<s:if test="succSave == 7">
	<script language="javascript"> 
		alert("合并成功!");
		window.close();
	</script>
</s:if>
<body leftmargin = "0">
<s:form action="applyBillQuery" theme="simple">

	<div align="left" style="color:#000000">
	<p>当前页面：开票管理 -> 已签合同开票申请</p>
	</div>
			<table align="center" border=0 cellpadding=1 cellspacing=1
				width="100%" class="bg_table03">
				<tr>
					<td colspan="3" align="right" class="bg_table01" height="3"><img
						src="./../images/temp.gif" width="1" height="1"></td>
				</tr>
				<tr class="bg_table03">
					<td width="27%" align="right">
					&nbsp;
					</td>
					<td width="36%" align="left">
						<input type="button" name="1" value="确认开票" class="button02" onclick="monthApply(1)" />&nbsp;
						<input type="button" name="2" value="开票计划变更" class="button02" onclick="torealhistory();" />
					</td>
					<td width="36%" align="left">
						<input type="button" name="3" value="合并开票" class="button02" onclick="monthApply(2)" />&nbsp;
						&nbsp;&nbsp;&nbsp;
						<!--  <input type="button" name="3" value="收据申请" class="button02" onclick="applyReceipt();" />&nbsp;
					-->
					<!-- 	<input type="button" name="21" value="合并计划" class="button02" onclick="mergePlan()" />&nbsp; -->
				    </td>
				</tr>
			</table>

			<table align="center" border=1 cellpadding="1" cellspacing=1
				width="100%" id="mouthlyBill"  bordercolor="#808080" style=" border-collapse: collapse;">
				<tr align="center">
					<td  class="bg_table01">选择</td>
					<td class="bg_table01">合同号</td>
					<td class="bg_table01">项目号</td>
					<td class="bg_table01">合同名称</td>
					<td  class="bg_table01">客户名称</td>
					<td  class="bg_table01">负责部门</td>
					<td  class="bg_table01">阶段</td>
					<td  class="bg_table01">计划开票日期</td>
					<td  class="bg_table01">开票性质</td>
					<td  class="bg_table01">发票类型</td>
					<td  class="bg_table01">开票金额</td>
					<td width="5%" class="bg_table01">操作</td>
					<td width="7%" class="bg_table01">确认状态</td>
					
				</tr>
				 <s:if test="info != null">
				 	<s:set name="resultList" value="info.result"></s:set>
				 </s:if>
				 <s:else>
				 	<s:set name="resultList" value="conStageList"></s:set>
				 </s:else>
				<s:iterator value="resultList" id="month" status="status">
				<input type="hidden" name="con<s:property value="#month[0].realConBillproSid"/>" value="<s:property value="#month[2].conId" />"/>
				<input type="hidden" name="item<s:property value="#month[0].realConBillproSid"/>" value="<s:property value="#month[3].conItemId" />"/>
				<input type="hidden" name="contype<s:property value="#month[0].realConBillproSid"/>" value="<s:property value="#month[2].contractType" />"/>
					<tr align="center" onMouseOver="this.bgColor='#BBBBFF'; "	onMouseOut="this.bgColor='#FFFFFF';">
						<td>
							 <s:property value="#bill"/>
								<input type="checkbox" name="monthlyBillproSids" 
								<s:if test="#month[5] > 0 ||(#month[0].billType == 4 && #month[6] > 0)"> disabled </s:if>
									value="<s:property value="#month[0].realConBillproSid"/>" />
							</td>
						<td align="left"><s:property value="#month[2].conId" /></td>
						<td align="left"><s:property value="#month[3].conItemId" /></td>
						<td align="left"><a href="#" onclick="openCon('<s:property value="#month[2].conMainInfoSid"/>')" ><s:property value="#month[2].conName" /></a></td>
						<td align="left"><s:property value="#month[1].name" /></td>
						<td align="left">
							<s:if test="#month[3] != null">
								<s:property	value="typeManageService.getYXTypeManage(1018,#month[3].itemResDept).typeName" />
							</s:if>
							<s:else>
								<s:property value="typeManageService.getYXTypeManage(1018,#month[2].mainItemDept).typeName"/>
							</s:else>
						</td>
						<td align="left">
							<s:property value="typeManageService.getYXTypeManage(1023,#month[4]).typeName" />
					    </td>
						<td align="center"><s:property
							value="#month[0].realPredBillDate" /></td>
						<td align="left"><s:property
							value="typeManageService.getYXTypeManage(1012,#month[0].billNature).typeName" /></td>
						<td align="left"><s:property
							value="typeManageService.getYXTypeManage(1004,#month[0].billType).typeName" /></td>
						<td align="right"><s:property
							value="#month[0].realBillAmount" /></td>
						<s:if test="#month[5] > 0 || (#month[0].billType == 4 && #month[6] > 0 ) ">
							<td align="center" >
									&nbsp;
								</td>	
						</s:if>
						<s:else>
							<td align="center" >
								<s:if test="#month[0].realArriveAmount > 0">
									&nbsp;
								</s:if>
								<s:else>
									<a href="#" onclick="splitAmount(<s:property value="#month[0].realConBillproSid"/>)" >拆分</a>
								</s:else>
								</td>
						</s:else>
						<td>
							<s:if test="#month[0].applyBillState == null ">未确认</s:if>
							<s:if test="#month[0].applyBillState == 1 || #month[0].applyBillState == 2">已确认</s:if>
							<s:if test="#month[0].applyBillState == 3 ">确认通过</s:if>
							<s:if test="#month[0].applyBillState == 4 ">确认退回</s:if>
							<s:if test="#month[0].applyBillState == 5 ">已开票</s:if>
							<s:if test="#month[0].applyBillState == 6 ">已签收</s:if>
							<s:if test="#month[0].applyBillState == 7 ">已处理</s:if>
						</td>
					</tr>
					
					</s:iterator>
				<s:if test="info != null">
					<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
						<tr valign="top">
							<td class="bg_table04"><baozi:pages value="info" 
								beanName="info" formName="forms(0)" /></td>
						</tr>
					</TABLE>
				</s:if>
				<s:else>
					<tr class="bg_table03">
						<td colspan="12" align="center">
							<input type="button" name="23" value="关  闭" onclick="window.close();" class="button01" />
						</td>
					</tr>					
				</s:else>
				</table>

</s:form>
</body>
</html>
