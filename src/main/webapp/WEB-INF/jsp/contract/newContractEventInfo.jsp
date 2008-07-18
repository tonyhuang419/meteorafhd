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
<script src="/yx/commons/scripts/my_f.js" type="text/javascript"></script>
<script type="text/javascript">
		function setNum(i){
			var hidden=document.getElementById("tag");
			hidden.setAttribute("value",i);
		}
		function setDelNum(button){
    		 var hidden=document.getElementById("eventdelid");
     		 hidden.setAttribute("value",button.id);
		}
		function setAddNum(button){
 			var hidden=document.getElementById("eventaddid");
  			hidden.setAttribute("value",button.id);
  
		}
		function setSaveIndex(Index){
 			var hidden=document.getElementById("saveIndex");
			 hidden.setAttribute("value",Index);
		}
	function saveEventInfo()
	{
		if(!validate1()){
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
		window.open("contract.action?method=tempUpdateEventInfo&mainid="+mid+"&itemInfoId="+iid+"&clietId="+cid,'newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');
	}
	function reflushPage()
	{
		document.contract.submit();
		alert("修改成功!");
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
					<div id="content2" style="visibility: hidden">
				</s:if> 
				<s:else>
					<div id="content2">
				</s:else>
            <!--合同项目开始-->
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%">

				<s:iterator id="infoPart" value="mainInfoPartList"
					status="partIndex">
					<tr>
						<td colspan="5" class="bg_table01" align="left"><input
							type="hidden" name="partInfoList"
							value="<s:property value="id"/>" /> <s:property
							value="typeManageService.getYXTypeManage(1012,#infoPart.moneytype).typeName" />
						&nbsp;&nbsp;&nbsp;&nbsp;总金额：<s:property
							value="#infoPart.money" />&nbsp;&nbsp;
						可编辑余额： <s:iterator
							id="fee" value="feeMoney" status="feeIndex">
							<s:if test="#feeIndex.index==#partIndex.index">
								<s:property value="#fee" />
								<input type="hidden" name="feeMoney"
									value="<s:property value="#fee"/>" />
							</s:if>&nbsp;&nbsp;
						</s:iterator></td>
					</tr>
					<tr>
						<td class="bg_table02" align="center">合同费用</td>
						<td class="bg_table02" align="center">工程部门</td>
						<td class="bg_table02" align="center">负责人</td>
						<td class="bg_table02" align="center">含税金额</td>
						<td class="bg_table02" align="center">操作</td>
					</tr>
					<!--显示合同费用信息-->
					<!--显示项目费用和项目信息-->
					<s:iterator id="itemInfo" value="itemInfoList"
						status="itemInfoIndex">
						<tr>
							<td class="bg_table02" align="center">&nbsp;</td>
							<td class="bg_table02" align="center"><s:property
								value="typeManageService.getYXTypeManage(1018,#itemInfo.itemMainInfo.itemResDept).typeName" />
							</td>
							<td class="bg_table02" align="center"><s:property
								value="#itemInfo.itemMainInfo.itemResDeptP" /></td>
							<td class="bg_table02" align="center"><s:property
								value="#itemInfo.conItemAmountWithTax" /></td>
							<td class="bg_table02" align="center"><input type="button"
								value="修改" class="button02"
								onclick="setNum(2);updateEventInfo('<s:property value="#itemInfo.conItemInfoSid"/>')" />
							<input type="button" value="删除" class="button02"
								id="<s:property value="#itemInfo.conItemInfoSid"/>"
								onclick="setNum(2);;setDelNum(this);deleteEventInfo();" /></td>
						</tr>
					</s:iterator>

					<tr>
						<td class="bg_table02" align="center"></td>
						<td class="bg_table02" align="center"><s:select
							name="itemResDept" headerKey="" headerValue="--请选择--"
							list="dutydepartmentlist" listKey="typeSmall"
							onchange="getChargeManOfDepartment(this)" listValue="typeName">
						</s:select></td>
						<td class="bg_table02" align="center"><input type="text"
							name="itemResDeptP"
							style="width: 90px; height: 21px; font-size: 10pt;" /><span
							style="width: 18px; border: 0px solid red;"><select
							style="margin-left: -90px; width: 108px;" id="chargeManList"
							onchange="departPChange(this)"></select> </span></td>
						<td class="bg_table02" align="center"><!--											<s:property value="#itemInfo.conItemAmountWithTax"/>-->
						<input name="money" type="text" id="textfield" size="10"
							maxlength="10" /></td>
						<td class="bg_table02" align="center"><input type="button"
							id="<s:property value="conItemMinfoSid"/>" value="添加" class="button02"
							onclick="setNum(2);setSaveIndex('<s:property value="#partIndex.index"/>');setAddNum(this);saveEventInfo();" />
						</td>
					</tr>
					<tr>
						<td colspan="5">
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
		<input type="submit" value="保    存" class="button02"  />
		<input type="button" value="保存并关闭"
			onClick="javascript:if(!validate()){{if(confirm('确认保存并关闭草稿合同')){setNum(0);document.contract.submit();}return false;}}"
			class="button02" />
	</s:if> <s:if test="isModify==1">
		<input type="submit" value="保存" onClick="setNum(1);" class="button01" />
		<input type="submit" value="确认提交"
			onClick="setMethod('sureSubmit');{if(confirm('确认提交草稿合同')){refresh();return true;}return false;}"
			class="button01" />
		<input type="submit" value="删除"
			onClick="setMethod('delContract');{if(confirm('确认删除草稿合同')){return true;}return false;}"
			class="button01" />
		<input type="submit" value="返回" onClick="setMethod('goback');" 
			class="button01" />
	</s:if>
        </div>
</s:form>
<script type="text/javascript">
function validate1(){
		var deptList=document.contract.itemResDept;
		var moneyList=document.contract.money;
		var hrNode=document.getElementsByTagName("hr");
		var deptPList=document.contract.itemResDeptP;
		var feeTempList=document.contract.feeMoney;
		var i=document.getElementById("saveIndex").getAttribute("value");
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
				ev2.addError("您的金额还没有分配完！请继续分配！");
				ev2.writeErrors(errorsFrame,"errorsFrame");
				return true;
			}
		}else{
			var flag=false;
			for(var i=0;i<feeTempList.length;i++){
				moneys=parseFloatNumber(feeTempList[i].value);
				if(moneys>0)
				{
					ev2.addError("您的金额还没有分配玩！请继续分配！");
					ev2.writeErrors(errorsFrame,"errorsFrame");
					flag=true;
					break;
				}
			}
			if(flag)
			{
			return true;
			}
			
		}
		</s:if>
	return false;
}
function getChargeManOfDepartment(obj){
	var tdNode=obj.parentNode;
	var ntdNode=tdNode.nextSibling;
	var selectNode=ntdNode.lastChild.firstChild;
	var txtNode=ntdNode.firstChild;
		txtNode.value="";
	ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetChargeManOfDepartment&departmentCode="+obj.value,selectNode,"id","name",{value:"",text:"      "});
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
