<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同新建</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css"
	rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet"
	type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js"
	type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script language="javascript" for="document" event="onkeydown">

<!--
if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!=''){
	event.keyCode=9;
}
-->
</script>
<script type="text/javascript">
		function setNum(i){
			var hidden=document.getElementById("tag");
			hidden.setAttribute("value",i);
		}
		function setDelNum(objid){
    		 var hidden=document.getElementById("eventdelid");
     		 hidden.setAttribute("value",objid);
		}
		function setAddNum(button){
 			var hidden=document.getElementById("eventaddid");
  			hidden.setAttribute("value",button);  
		}
		function setSaveIndex(Index){
 			var hidden=document.getElementById("saveIndex");
			 hidden.setAttribute("value",Index);
		}
	function saveEventInfo(len1,len2)
	{
		var i=document.getElementById("saveIndex").getAttribute("value");
		var deptList=document.contract.itemResDept;
		var hrNode=document.getElementsByTagName("hr");
		var dep="";
		if(hrNode.length==1)
		{
			dep=deptList.value;
		}else{
			dep=deptList[i].value;
		}
		var ev2=new Validator();
		if(!validate1()){
			for(var k=0;k<len2;k++)
			{
				var str=len1+"/"+k;
				var tempOp=document.getElementById(str).innerHTML.replace(/(^\s+)|\s+$/g,"");
				if(tempOp==dep)
				{
					ev2.addError("一个费用下面只能有一个工程部门！");
					break;
				}
			}
			ev2.writeErrors(errorsFrame, "errorsFrame");
			if(ev2.size()>0)
			{
				return;
			}
			document.contract.method.value="addEventInfo";
			document.contract.submit();
		}
	}
	function deleteEventInfo()
	{
		var flag=window.confirm("您确定要删除改信息吗？");
		if(flag){
			document.contract.method.value="deleteEventInfo";
			document.contract.submit();
		}
	}
	function updateEventInfo(iid)
	{
<!--	传入三个参数，第一个是mainid,第二个是第二个是itemInfoid	,第三个是client id-->
		var mid=document.getElementById("contract_mainid").value;
		var cid=document.getElementById("contract_clietId").value;
		//window.open("contract.action?method=tempUpdateEventInfo&mainid="+mid+"&itemInfoId="+iid+"&clietId="+cid,'newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=300,width=400');
		window.open("contract.action?method=tempUpdateEventInfo&mainid="+mid+"&itemInfoId="+iid+"&clietId="+cid);
	}
	function reflushPage()
	{
		document.contract.submit();
		alert("修改成功!");
	}
	function calac(obj,cols)
	{
		var ev2=new Validator();
		var tdNode=obj.parentNode;
		var txtNode=tdNode.lastChild;
		var feeTempList=document.contract.feeMoney;
		var hrNode=document.getElementsByTagName("hr");
		var totalMo=document.getElementById("totalMoney"+cols)
		var opMoney=0;
		var percent=0;
		if(obj.value!=null&&obj.value.length>0){
			percent=parseInt(obj.value);
			if(percent>100)
			{
				ev2.addError("百分比不能大于100！");
			}
			if(ev2.size()>0)
			{
				ev2.writeErrors(errorsFrame,"errorsFrame");
				return;
			}
			if(hrNode.length==1)
			{
				opMoney=parseFloatNumber(feeTempList.value);
			}else{
				opMoney=parseFloatNumber(feeTempList[cols].value);
			}
			var total=parseFloatNumber(totalMo.innerHTML);
			var resultMoney=total*percent/100;
			if(resultMoney>opMoney)
			{
				txtNode.value=number_format(opMoney);
			}else{
				txtNode.value=number_format(resultMoney);
			}
		}
		
	}
	</script>

<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
</head>
<body>
<div align="left">
    <s:if test="isModify==0" >
  <div  style="color:#000000">当前页面：合同管理 -&gt; 合同新建</div>
</s:if>
<s:if test="isModify==1">
 <div  style="color:#000000">当前页面：合同管理 -&gt; 草稿合同修改</div>
</s:if>
</div>
 <s:form action="contract" theme="simple">
	<s:hidden name="method" value="toPage" />
	<s:hidden name="tag" id="tag"></s:hidden>
	<input type="hidden" name="eventaddid" id="eventaddid" />
	<input type="hidden" name="eventdelid" id="eventdelid" />
	<input type="hidden" name="saveIndex" id="saveIndex" />
	<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
<table width="100%" height="100%" border="0"  align="center" cellpadding="1" cellspacing="1" class="bg_table02">
  <tr>
    <td  colspan="4"  align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr>
    <td align="center"><table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
      </table></td>
  <tr>
    <td colspan="4" align="center" height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
  <tr class="bg_table02">
    <td colspan="4" align="center" class="bg_table02"><div id="container" class="bg_table02">
        <div id="title" class="bg_table02">
                  <%@ include file="/WEB-INF/jsp/contract/ContractTopTab.jsp"%>  
        </div>
      <s:hidden name="tag" id="tag"></s:hidden>

      <div id="content" class="content1" >
          <div  id="content1"  >
              <!--合同主信息 -->

          </div>
   
         	<s:if test="contractype==2">
					<div id="content2" style="display:none">
				</s:if> 
				<s:else>
					<div id="content2">
				</s:else>
            <!--合同项目开始-->
			<table align="center" border="1" bordercolor="#808080" style=" border-collapse: collapse;"
				width="100%">
				<s:iterator id="infoPart" value="mainInfoPartList"
					status="partIndex">
					<tr>
						<td colspan="4" class="bg_table01" align="left"><input
							type="hidden" name="partInfoList"
							value="<s:property value="id"/>" /><s:property
							value="typeManageService.getYXTypeManage(1012,#infoPart.moneytype).typeName" />总金额:<LABEL id="totalMoney<s:property value="#partIndex.index"/>"><s:property value="#infoPart.money" /></LABEL>&nbsp;&nbsp;可编辑余额:
							<s:iterator id="fee" value="feeMoney" status="feeIndex">
							<s:if test="#feeIndex.index==#partIndex.index">
								<s:property value="#fee" />
								<input type="hidden" name="feeMoney"
									value="<s:property value="#fee"/>" />
							</s:if>
						</s:iterator>
						</td>
					</tr>
					<tr class="bg_table02">
						
						<td  align="center">工程部门</td>
						<td  align="center">负责人</td>
						<td  align="center">项目金额</td>
						<td  align="center">操作</td>
					</tr>
					<!--显示合同费用信息-->
					<!--显示项目费用和项目信息-->
					<s:iterator id="itemInfo" value="itemInfoList"
						status="itemInfoIndex">
						<tr>
						
							<td  align="center">
							<div id="<s:property value="#partIndex.index"/>/<s:property value="#itemInfoIndex.index"/>" style="display:none;">
							<s:property value="#itemInfo.itemMainInfo.itemResDept"/>
							</div>
							<s:property value="typeManageService.getYXTypeManage(1018,#itemInfo.itemMainInfo.itemResDept).typeName" />
							</td>
							<td  align="center"><s:property
								value="#itemInfo.itemMainInfo.itemResDeptP" /></td>
							<td  align="right"><s:property
								value="#itemInfo.conItemAmountWithTax" /></td>
							<td  align="center">
								<a href="javascript:setNum(2);updateEventInfo('<s:property value="#itemInfo.conItemInfoSid"/>')">修改</a>
								<a href="javascript:setNum(2);setDelNum('<s:property value="#itemInfo.conItemInfoSid"/>');deleteEventInfo();">删除</a>
								</td>
						</tr>
					</s:iterator>
				
					<tr  <s:if test="feeMoney[#partIndex.index] <= 0">style="display:none"</s:if>>
						<td  align="center"><s:select
							name="itemResDept" headerKey="" headerValue="--请选择--"
							list="dutydepartmentlist" listKey="typeSmall"
							onchange="getChargeManOfDepartment(this,'%{#partIndex.index}')" listValue="typeName">
						</s:select></td>
						<td  align="center"><input type="text"
							name="itemResDeptP" onkeyup="showtips('<s:property value="#partIndex.index"/>');" onkeydown="enterTips('<s:property value="#partIndex.index"/>');"
							style="width: 90px; height: 21px; font-size: 10pt;" /><span
							style="width: 18px; border: 0px solid red;"><select
							style="margin-left: -90px; width: 108px;" id="chargeManList"
							onchange="departPChange(this)"></select></span></td>
						<td  align="center">
						<font color="red">* </font><input name="prencent" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" size="3" maxlength="3" onblur="calac(this,'<s:property value="#partIndex.index"/>')"/>%
						<input  type="text" name="money" maxlength="15" size="15" onblur="formatInputNumber(this)"/></td>
						<td  align="center">
							<a href="javascript:setNum(2);setSaveIndex('<s:property value="#partIndex.index"/>');setAddNum('<s:property value="conItemMinfoSid"/>');saveEventInfo('<s:property value="#partIndex.index"/>','<s:property value="#infoPart.itemInfoList.size()"/>');">添加</a>
						</td>
					</tr>
					<tr style="display:none">
						<td colspan="4">
						<hr />
						</td>
					</tr>
				</s:iterator>
			</table>
        
              <!--合同项目结束-->
          </div>
          <div id="content3"    class="hidecontent">
           
            <!--开票和收款阶段结束-->
          </div>
          <div id="content4"   >
                      
              <!--开票和收款计划开始-->
             
  
         
            <!--开票和收款计划结束-->
          </div>
          <div id="content5" class="hidecontent" >
            <!--未签开票关联开始-->
                
          
            <!--已关联清单开始-->
          </div>
          <div id="content6" class="hidecontent">
            <!--申购清单结束-->
            <!--未签申购清单开始-->
            <!--未签申购清单结束-->
            <!--未签申购关联结束-->
          </div>
          <div id="content7" class="hidecontent">
              <!--自有产品开始-->
           
              <!--自有产品结束-->
          </div>
          <div id="content8"  >
              <!--else-->
           
            
            <!-- ELSE END -->
          </div>
  </div><!--总体结束DIV-->
        <div align="center">
       <s:if test="isModify==0">
		<input type="submit" value="保    存" onclick="setNum(2);" class="button02"  />
		<input type="button" value="保存并关闭"
			onClick="javascript:if(!validate()){{if(confirm('确认保存并关闭草稿合同')){setNum(0);document.contract.submit();}return false;}}"
			class="button02" />
	</s:if>
	 <s:if test="isModify==1">
		<input type="submit" value="保存" onClick="setNum(2);" class="button01" />
	 </s:if>
	 <s:if test="mainid==null">
     </s:if>
     <s:else>
		<input type="button" value="确认提交"
			onClick="{if(confirm('确认提交草稿合同')&&!validate()){setMethod('sureSubmit');document.contract.submit()}}"
			class="button01" />
	 <input type="button" value="合同拆分导出" class="button01" onClick="javascript:window.open('/yx/contract/contractSplitTable.action?contractMainInfoSid=<s:property value="mainid"/>')"/>
	 </s:else>
	  <s:if test="isModify==1">
		<input type="submit" value="删除"
			onClick="{if(confirm('确认删除草稿合同')){setMethod('delContract');return true;}return false;}"
			class="button01" />
		<input type="submit" value="返回" onClick="setMethod('goback');" 
			class="button01" />
	    </s:if>
        </div>
</s:form>
<script type="text/javascript">
	var optionMatchSelect=new Array();
	<s:iterator id="infoPart" value="mainInfoPartList">
	 optionMatchSelect.push(new OptionMatchInput(null,null,null));
	</s:iterator>
	
	function showtips(indentity){
		optionMatchSelect[indentity].showtips();
	}
	function enterTips(indentity){
		optionMatchSelect[indentity].enterTips();
	}
	
	function validate1(){
		var deptList=document.contract.itemResDept;
		var moneyList=document.contract.money;
		var hrNode=document.getElementsByTagName("hr");
		var deptPList=document.contract.itemResDeptP;
		var feeTempList=document.contract.feeMoney;
		var i=document.getElementById("saveIndex").getAttribute("value");
		var moneys;
		if(hrNode.length==1)
		{
			depts=deptList.value;
			moneys=moneyList.value;
			deptPs=deptPList.value;
			sumTemp=parseFloatNumber(feeTempList.value);
		}else{
			depts=deptList[i].value;
			moneys=moneyList[i].value;
			deptPs=deptPList[i].value;
			sumTemp=parseFloatNumber(feeTempList[i].value);
		}
	   var ev2=new Validator();
	
       with(contract){ 
           ev2.test("notblank","请选择工程部门",depts);
           ev2.test("notblank","请填写负责人",deptPs);
           ev2.test("+float","含税金额必须是大于0的数字",moneys);
       } 
       if (ev2.size() == 0) {
      	  var opmo=parseFloatNumber(moneys);
       		if(opmo>sumTemp){
       			if(sumTemp<=0)
       			{
       			ev2.addError("没有可编辑余额");
       			}else{
       			ev2.addError("含税金额不能大于可编辑余额"+sumTemp);
       			}
       		}
	   }
		ev2.writeErrors(errorsFrame, "errorsFrame");
	  	if(ev2.size()>0)
	  	{
	  		return true;
	  	}
		 return false;
	}
	function validate()
	{
	<s:if test="contractype==1">
		var feeTempList=document.contract.feeMoney;
		var hrNode=document.getElementsByTagName("hr");
		var ev2=new Validator();
		if(hrNode.length==1)
		{
			moneys=parseFloatNumber(feeTempList.value);
			if(moneys>0)
			{
		    <s:if test="importFromFile" >
	        </s:if>
	        <s:else>
				ev2.addError("您的金额还没有分配完！请继续分配！");
				ev2.writeErrors(errorsFrame,"errorsFrame");
				return true;
			</s:else>
			}
		}else{
			var flag=false;
			for(var i=0;i<feeTempList.length;i++){
				moneys=parseFloatNumber(feeTempList[i].value);
				if(moneys>0)
				{
			    <s:if test="importFromFile" >
	   			</s:if>
	   			<s:else>
					ev2.addError("您的金额还没有分配完！请继续分配！");
					ev2.writeErrors(errorsFrame,"errorsFrame");
					flag=true;
				</s:else>
					break;

				}
			}
			if(flag)
			{
			return true;
			}
			
		}
		</s:if>
		<s:else>
			return false;
		</s:else>

}
function getChargeManOfDepartment(obj,indentity){
	var tdNode=obj.parentNode;
	var ntdNode=tdNode.nextSibling;
	var selectNode=ntdNode.lastChild.firstChild;
	var txtNode=ntdNode.firstChild;
	txtNode.value="";
	ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetChargeManOfDepartment&departmentCode="+obj.value,selectNode,"id","name",{defaultAsync:false,value:"",text:"      "});
	if(selectNode.options.length >1){
		txtNode.value=selectNode.options[1].text;
	}
	optionMatchSelect[indentity].inputObj = txtNode;
	optionMatchSelect[indentity].selectObj = selectNode;
	optionMatchSelect[indentity].getOptionFromSelect(selectNode,1);
}
function departPChange(obj)
{
	var tdNode=obj.parentNode.parentNode;
	var txtNode=tdNode.firstChild;
	txtNode.value=obj.options[obj.selectedIndex].text;
	
}
</script>
</body>
</html>
