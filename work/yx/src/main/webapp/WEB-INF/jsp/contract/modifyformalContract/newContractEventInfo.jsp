<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同变更</title>
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
		var deptList=document.formalContractModify.itemResDept;
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
			document.formalContractModify.method.value="addEventInfo";
			document.formalContractModify.submit();
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
		window.open("contract.action?method=tempUpdateEventInfo&mainid="+mid+"&itemInfoId="+iid+"&clietId="+cid,'newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=300,width=400');
		//window.open("contract.action?method=tempUpdateEventInfo&mainid="+mid+"&itemInfoId="+iid+"&clietId="+cid);
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
 <div align="left" style="color:#000000">当前页面：合同管理 -&gt; 正式合同管理 -&gt;合同变更</div>
</div>
 <s:form action="formalContractModify" theme="simple">
	<s:hidden name="method" value="saveEventInfo" />
	
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
                  <%@ include file="/WEB-INF/jsp/contract/modifyformalContract/ContractTopTab.jsp"%>  
        </div>
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
				<s:set name="itemIndex" value="0"></s:set>
				<s:iterator id="infoPart" value="mainInfoPartList"
					status="partIndex">
					<tr>
						<td colspan="4" class="bg_table01" align="left"><input
							type="hidden" name="partInfoList"
							value="<s:property value="id"/>" /><s:property
							value="typeManageService.getYXTypeManage(1012,#infoPart.moneytype).typeName" />总金额:<LABEL id="totalMoney<s:property value="#partIndex.index"/>"><s:property value="#infoPart.money" /></LABEL>
						</td>
					</tr>
					<tr class="bg_table02">
						
						<td  align="center">工程部门</td>
						<td  align="center">负责人</td>
						<td  align="center">含税金额</td>
						<td></td>
				
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
							<td  align="right">
							<s:hidden name="itemInfoList[%{#itemIndex}].conItemInfoSid" value="%{#itemInfo.conItemInfoSid}"></s:hidden>
							<s:textfield name="itemInfoList[%{#itemIndex}].conItemAmountWithTax" value="%{#itemInfo.conItemAmountWithTax}" onblur="formatInputNumber(this)"></s:textfield>
							</td>
							<td></td>
						</tr>
						<s:set name="itemIndex" value="#itemIndex+1"></s:set>
					</s:iterator>
					<input type="hidden" name="itemInfoIndex" value="<s:property value="#itemIndex"/>"/>
					<tr>
						<td  align="center"><s:select
							name="itemResDept" headerKey="" headerValue="--请选择--"
							list="typeManageService.getYXTypeManage(1018)" listKey="typeSmall"
							onchange="getChargeManOfDepartment(this,'%{#partIndex.index}')" listValue="typeName">
						</s:select></td>
						<td  align="center"><input type="text"
							name="itemResDeptP" onkeyup="showtips('<s:property value="#partIndex.index"/>');" onkeydown="enterTips('<s:property value="#partIndex.index"/>');"
							style="width: 90px; height: 21px; font-size: 10pt;" /><span
							style="width: 18px; border: 0px solid red;"><select
							style="margin-left: -90px; width: 108px;" id="chargeManList"
							onchange="departPChange(this)"></select></span></td>
						<td  align="right">
						<font color="red">* </font>
						<input  type="text" name="money" onblur="formatInputNumber(this)"/></td>
						<td  align="center">
							<a href="javascript:setNum(3);setSaveIndex('<s:property value="#partIndex.index"/>');setAddNum('<s:property value="conItemMinfoSid"/>');saveEventInfo('<s:property value="#partIndex.index"/>','<s:property value="#infoPart.itemInfoList.size()"/>');">添加</a>
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
          <input  type="button" value="保存"  onClick="if(!validate()){setNum(3);document.formalContractModify.submit();}" class="button01" >   
          <input type="button" value="申请提交" onclick="if(!validate()){setNum(0);tijiao();}" class="button01"/> 
           <input type="button" value="合同拆分导出" onclick="javascript:window.open('/yx/contract/contractSplitTable.action?method=doDefaultChange&contractMainInfoSid=<s:property value="maininfo.conMainInfoSid"/>');" class="button01">  
          <input type="button" value="关闭" onclick="window.close();" class="button01"> 
        </div>
</s:form>
<script type="text/javascript">

function tijiao()
{
	document.formalContractModify.method.value="applySubmitByItemInfo";
	document.formalContractModify.submit();
}

	function validate1(){
		var deptList=document.formalContractModify.itemResDept;
		var moneyList=document.formalContractModify.money;
		var hrNode=document.getElementsByTagName("hr");
		var deptPList=document.formalContractModify.itemResDeptP;
		var i=document.getElementById("saveIndex").getAttribute("value");
		var moneys;
		if(hrNode.length==1)
		{
			depts=deptList.value;
			moneys=moneyList.value;
			deptPs=deptPList.value;
			
			
		}else{
			depts=deptList[i].value;
			moneys=moneyList[i].value;
			deptPs=deptPList[i].value;
			
		}
	   var ev2=new Validator();
	
       with(formalContractModify){ 
           ev2.test("notblank","请选择工程部门",depts);
           ev2.test("notblank","请填写负责人",deptPs);
           ev2.test("+float","含税金额必须是大于0的数字",moneys);
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
        return false;	
	}
function getChargeManOfDepartment(obj){
	var tdNode=obj.parentNode;
	var ntdNode=tdNode.nextSibling;
	var selectNode=ntdNode.lastChild.firstChild;
	var txtNode=ntdNode.firstChild;
	txtNode.value="";
	ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetChargeManOfDepartment&departmentCode="+obj.value,selectNode,"id","name",{defaultAsync:false,value:"",text:"      "});
	if(selectNode.options.length >1){
		txtNode.value=selectNode.options[1].text;
	}
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
