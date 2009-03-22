<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>决算</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script type="text/javascript">
    function setNum(i){
        var hidden=document.getElementById("tag");
        hidden.setAttribute("value",i);
    }
<!--	function selectedClient(clientObj){-->
<!--		document.getElementById("clientName").value = clientObj.clientName;-->
<!--		document.getElementById("clientId").value = clientObj.clientId;-->
<!--	}-->
	function selectedClient(client){
		var i= client.clientId;
		var j= client.clientName;
		 if(i!=""&&j!=""){
	    var showhidden=document.getElementById("showhidden");
	    var select=document.getElementById(showhidden.value);
	    for(var a=0;a<select.length;a++){
	         if(select.options[a].value==i){
	               select.value=i; 
	               return true;
	         }    
	    }	 
	    var option=document.createElement("option");
	    option.text=""+j+"";
	    option.value=""+i+"";
	    select.add(option);
	    select.value=i;
	       if(select.id=="contract_maininfo_conCustomer"){
	         setAllClient(select);
	       }
	      
	    }    
	}
	function setShowId(buttonid){	
      var hidden=document.getElementById("showhidden");
      if(buttonid=="cmc"){
      	hidden.setAttribute("value","contract_maininfo_conCustomer");
      }  
      if(buttonid=="cmb"){
        hidden.setAttribute("value","contract_maininfo_billCustomer")
      }
      if(buttonid=="cmi"){
        hidden.setAttribute("value","contract_maininfo_itemCustomer");
      }
	}
	function setAllClient(select){
	   for(i=0;i<select.length;i++){
	       if(select.options[i].selected){
	         setShowId("cmb");
             setShowId("cmi");
	       }
	   }
	}
	function getLinkMainOfClient(clientIdStr){
	    ajaxSetSelectOptions("/yx/jsonData.action?method=doGetLinkMainOfClient&clientId="+clientIdStr,document.getElementById("maininfo.linkManId"),"id","name");
	}
	function delmainmoney(delid){
	  var hidden=document.getElementById("delmainpartid");
	  hidden.setAttribute("value",delid);
	}
	function getChargeManOfDepartment(departmentCode){
		ajaxSetSelectOptions("/yx/jsonData.action?method=doGetChargeManOfDepartment&departmentCode="+departmentCode,$('chargeManList'),"id","name",{defaultAsync:false,value:"",text:""});
	 	if($('chargeManList').options.length >1){
			$('departP').value=$('chargeManList').options[1].text;
		}
    }
</script>

<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
</head>
<body  onload="refresh()"><div align="left">
 <div  style="color:#000000">当前页面：合同管理 -&gt; 结算转决算</div>
</div>
 <s:form action="finalToclose" theme="simple">
 <s:hidden name="method" value="saveMainInfo" />
 <input type="hidden" id="showhidden">
 <div  align="left" style="color: #FF0000" >
<iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" scrolling="no"></iframe></div>
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
        <%@ include file="/WEB-INF/jsp/contract/finalToclose/ContractTopTab.jsp"%> 
      </div>
        <div id="content" class="content1" >
         <input type="hidden"  name="delmainpartid" id="delmainpartid"/>
         <s:hidden name="noTaxMoney"></s:hidden>
         <s:hidden name="maininfo.conTaxTamount"></s:hidden>
          <div  id="content"  >
            <div align="center">
              <table  width="100%" height="307">
                <tr class="bg_table02">
                  <td align="right" width="98"><div align="right"><font color="red">* </font>客户名称:</div></td>
                  <td align="left" width="251"><div align="left">
                  <s:property value="clientCustomer" />
					  </td>
                  <td align="right"><div align="right"><font color="red">* </font>开票客户：</div></td>
                  <td align="left">
                      <s:property value="billCustomer" />
                      </td>
                </tr>
                <tr>
                  <td width="98" align="right" class="bg_table02"><font color="red">* </font>项目单位：</td>
                  <td width="251" align="left" class="bg_table02"> 
                  <s:textfield name="itemCustomer" disabled="true"></s:textfield>      
                    </td>
                  <td width="136" align="right" class="bg_table02">&nbsp;</td>
                  <td width="243" align="left" class="bg_table02">&nbsp;</td>
                </tr>
                <tr>
                  <td align="right" class="bg_table02"><div align="right">客户联系人：</div></td>
                  <td align="left" class="bg_table02">
                  <s:textfield name="linkMan" disabled="true"></s:textfield> 
                  </td>
                  <td align="right" class="bg_table02"><div align="right">甲方的合同号：</div></td>
                  <td align="left" class="bg_table02"><div align="left">
                          <s:textfield disabled="true" name="maininfo.partyAConId" />     
                  </div></td>
                </tr>
                <tr>
                  <td align="right" class="bg_table02"><div align="right">客户项目类型： </div></td>
                  <td align="left" class="bg_table02"><div align="left">
                   <s:textfield name="customereventtype" disabled="true"></s:textfield>       
                    </div></td>
                  <td class="bg_table02" align="right"><div align="right">甲方的项目工程编号：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                      <s:textfield disabled="true" name="maininfo.partyAProId" />
                    </div></td>
                </tr>
                <tr>
                  <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right"><div align="right"><font color="red">* </font>合同名称：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                      <s:textfield disabled="true" name="maininfo.conName" />
                    </div></td>
                  <td class="bg_table02" align="right"><div align="right"><font color="red">* </font>主项目部门：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
           <s:select  name="maininfo.mainItemDept" disabled="true" headerKey="" onchange="getChargeManOfDepartment(this.value)" headerValue="--请选择--" list="dutydepartmentlist" listKey="typeSmall" listValue="typeName"  >
                      </s:select>   
                    </div></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right"><div align="right"><font color="red">* </font>预决算信息：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                 <s:select list="#@java.util.TreeMap@{0:'非预决算',1:'预决算'}"   name="maininfo.FinalAccount"  ></s:select> </div></td>
                  <td class="bg_table02" align="right"><div align="right">主项目负责人：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                  <input type="text" id="departP" 
							name="maininfo.mainItemPeople" disabled="true"
							style="width: 90px; height: 21px; font-size: 10pt;" /><span
							style="width: 18px; border: 0px solid red;"><select
							style="margin-left: -90px; width: 108px;" disabled="true" id="chargeManList"
							onchange="departPChange(this)"></select> </span>
                  
                  </div></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right"><div align="right"><font color="red">* </font>合同类型：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
<s:select list="contracttype"  name="maininfo.ContractType" disabled="true" listKey="typeSmall" listValue="typeName" ></s:select> 
                    </div></td>
                  <td align="right" class="bg_table02"><div align="right"><font color="red">* </font>合同性质：</div></td>
                  <td align="left" class="bg_table02"><div align="left">
<s:select list="contractnature" headerKey="" disabled="true" headerValue="--请选择--" name="maininfo.conType" listKey="typeSmall" listValue="typeName" ></s:select>
                    </div></td>
                </tr>
                  <tr>
                  <td class="bg_table02" nowrap align="right">基准</td>
                  <td class="bg_table02" align="left"><s:select disabled="true" name="maininfo.standard"  list="#@java.util.TreeMap@{1:'含税',2:'不含税'}" ></s:select></td>
                  <td align="right" nowrap class="bg_table02"></td>
                  <td align="left" class="bg_table02">
                    </div></td>
                </tr>
                 <tr>
                  <td class="bg_table02" nowrap align="right"><div align="right"><font color="red">* </font>合同总金额：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
               		<s:textfield name="maininfoMoney" maxlength="15" onblur="formatInputNumber(this)"/>
                    </div></td>
                  <td align="right" nowrap class="bg_table02"><div align="right">合同不含税金额：</td>
                  <td align="left" class="bg_table02"><div align="left"><s:property value="maininfo.conNoTaxTamount"/>
                    </div></td>
                </tr>
                <tr>
                <td class="bg_table02" align="right">货币单位：</td>
                <td class="bg_table02" align="left">
         <s:select list="copecklist" disabled="true" name="maininfo.copeck" listKey="typeSmall" listValue="typeName" ></s:select>
				 </td>
                <td align="right" class="bg_table02">基准汇率：</td>
                <td align="left" class="bg_table02">
               <s:if test="maininfo.baserate!=null">
                   <s:textfield disabled="true" name="maininfo.baserate" ></s:textfield>
                  </s:if>
                  <s:else>
                  <s:textfield disabled="true" name="maininfo.baserate" value="1" ></s:textfield>
                  </s:else>
                </td>
                </tr>
                <tr>
                  <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
                </tr>
                <tr>
                  <td class="bg_table02" nowrap align="right"><div align="right">合同签订日期：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                  <s:textfield name="maininfo.conSignDate" id="conSignDate"  disabled="true" size="12" onclick="javascript:ShowCalendar(this.id);" />
                  <img src="/yx/commons/images/calendar.gif" align="absMiddle" alt=""  />                 
                   </div></td>
                  <td class="bg_table02" align="right"><div align="right">退税： </div></td>
                  <td class="bg_table02" align="left"><div align="left">
                      <s:checkbox disabled="true" name="maininfo.conDrawback"></s:checkbox>
                    </div></td>
                </tr>
                <tr>
                  <td align="right" nowrap class="bg_table02"><div align="right">合同起始日期： </div></td>
                  <td align="left" class="bg_table02"><div align="left">
                  <s:textfield name="maininfo.conStartDate" id="conStartDate" disabled="true"  size="12" onclick="javascript:ShowCalendar(this.id);"   />
                  <img src="/yx/commons/images/calendar.gif" align=absMiddle alt=""  />  
                     <td class="bg_table02" align="right"><div align="right">中标合同：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                     <s:checkbox disabled="true" name="maininfo.conBid"></s:checkbox>
                    </div></td>
                </tr>
                <tr>
                  <td nowrap align="right" class="bg_table02"><div align="right">合同结束日期：</div></td>
                  <td align="left" class="bg_table02"><div align="left">
                  <s:textfield name="maininfo.conEndDate" id="conEndDate" disabled="true"  size="12"   onclick="javascript:ShowCalendar(this.id);"   />
                  <img src="/yx/commons/images/calendar.gif" align=absMiddle alt=""  />  
                     <td class="bg_table02" align="right"><div align="right">纳入年度运维合同：</div></td>
                  <td align="left" class="bg_table02"><div align="left">
                      <s:checkbox disabled="true" name="maininfo.IntoYearCon"></s:checkbox>
                    </div></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right"><div align="right"></td>
                  <td class="bg_table02" align="left"></td>
                  <td class="bg_table02" align="right"></td>
                  <td class="bg_table02" align="left"></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right">&nbsp;</td>
                  <td class="bg_table02" align="left">&nbsp;</td>
                  <td align="right" class="bg_table02"></td>
                  <td align="left" class="bg_table02"></td>
                </tr>
                <tr>
             <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
                </tr>
                </table>
                <table  width="100%" border="1" bordercolor="#808080" style=" border-collapse: collapse;" >
                <tr>
                  <td align="center" class="bg_table01">编号</td>
                  <td align="center" class="bg_table01">费用名称</td>
                  <td align="center" class="bg_table01">金额</td>
                  <td align="center" class="bg_table01">开票类型</td>
                  </tr>
                  <s:set name="mainInfoPart" value="0"></s:set>
                <s:iterator value="mainMoneyList" status="mainMoneyList" >
                  <tr>
                  <td align="center"><s:property value="#mainMoneyList.index+1"/>
                  </td>
                  <td align="center"><s:hidden name="%{'mainMoneyList['+#mainMoneyList.index+'].id'}" ></s:hidden><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/></td>
                  <td align="center"><s:textfield name="%{'mainMoneyList['+#mainMoneyList.index+'].money'}" onblur="formatInputNumber(this)"></s:textfield></td>
                  <td align="center"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
                  </tr>
                  <s:set name="mainInfoPart" value="#mainInfoPart+1"></s:set>
                </s:iterator>
                <input type="hidden" name="mainInfoPartIndex" value="<s:property value="#mainInfoPart"/>"/>
                
             <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right">变更说明：</td>
                  <td colspan="4" align="left" class="bg_table02"><s:textarea cols="40" rows="5" name="maininfo.changeExplain" ></s:textarea> </td>
                </tr>
              </table>
            </div>
          </div>
          <div id="content2" class="hidecontent">
            <!--合同项目开始-->
        
              <!--合同项目结束-->
          </div>
          <div id="content3"    class="hidecontent">
           
            <!--开票和收款阶段结束-->
          </div>
          <div id="content4"   class="hidecontent"  >
                      
              <!--开票和收款计划开始-->
      
  
         
            <!--开票和收款计划结束-->
          </div>
          <div id="content5" class="hidecontent" >
            <!--未签开票关联开始-->
            <!--已关联清单开始-->
      
  
            <!--申购清单结束-->
            <!--未签申购清单开始-->
            <!--未签申购清单结束-->
            <!--未签申购关联结束-->
          </div>
          <div id="content7" class="hidecontent">
              <!--自有产品开始-->
           
              <!--自有产品结束-->
          </div>
          <div id="content8" class="hidecontent" >
              <!--else-->
            
          </div>
  </div><!--总体结束DIV-->
        <div align="center">
        <input type="button" value="保存" onclick="if(!validate1()){setNum(1);document.forms(0).submit();}" class="button01">
 		<input  type="submit" value="申请提交"  onClick="if(!validate1()){setNum(0);tijiao();}" class="button01" > 
        <input type="button" value="关闭" onclick="setNum(0),document.forms(0).submit();" class="button01">   
        
        </div>
<!--else-->
</s:form>
<script type="text/javascript"><!--
function validate1()
{
	 var ev2=new Validator();
	var k=parseInt(<s:property value="mainMoneyList.size"/>);
	var opSum=0;
	ev2.test("float","合同费用总金额必须为大于0的数字！",document.finalToclose.elements("maininfoMoney").value);
	ev2.writeErrors(errorsFrame, "errorsFrame");
	if(ev2.size()>0){
			return true;
	}
	var totalMoney=parseFloatNumber(document.finalToclose.elements("maininfoMoney").value);
	if(k!=null&&k>0){
		for(var i=0;i<k;i++){
		ev2.test("float","费用组成金额必须为大于0的数字",document.finalToclose.elements("mainMoneyList["+i+"].money").value);
		ev2.writeErrors(errorsFrame, "errorsFrame");
		if(ev2.size()>0){
			return true;
		}
		var num=parseFloatNumber(document.finalToclose.elements("mainMoneyList["+i+"].money").value);
		opSum+=num;
		}
	}
	if(opSum>totalMoney)
	{
		ev2.addError("费用组成总金额大于合同总金额！");
			ev2.writeErrors(errorsFrame, "errorsFrame");
		if(ev2.size()>0){
				return true;
		}
	}
	return false;
}
function tijiao()
{
	document.forms(0).method.value="applySubmitByMainInfo";
	document.forms(0).submit();
}
function validate(){
     var ev2=new Validator();
     //验证金额格式是否正确
     var totalMoney = document.finalToclose.maininfoMoney.value;
     ev2.test("float","合同总金额必须为大于0的数字！",totalMoney);
     var count= document.finalToclose.mainInfoPartIndex.value;
     for(var k=0;k<count;k++){
     	var partMoney = document.finalToclose.elements("mainMoneyList["+k+"].money").value;
     	ev2.test("float","合同费用总金额必须为大于0的数字！",partMoney);
     }
     ev2.writeErrors(errorsFrame, "errorsFrame");
     if(ev2.size()>0){
     return true;
     }
     return false;
}


function departPChange(obj)
{
	var tdNode=obj.parentNode.parentNode;
	var txtNode=tdNode.firstChild;
	txtNode.value=obj.options[obj.selectedIndex].text;
	
}
function showdepart(str){
    var text=document.getElementById("departP");
    text.value=str;
}
function cleanmoney(){
   var a=document.getElementById("contract_mainmoney");
   a.value="";
}
function doSubmit()
{
	var ev2=new Validator();
	 var mtypeArr=new Array();
	 <s:iterator value="mainMoneyList" status="mainMoneyList" >
		 mtypeArr[mtypeArr.length]=new Array("<s:property value="moneytype"/>");
	 </s:iterator>
	 var tempType=document.contract.mainmoneytype.value;
	 for(var k=0;k<mtypeArr.length;k++)
	 {
	 	if(mtypeArr[k]==tempType){
	 	ev2.addError("不能添加相同的费用名称！");
	 	break;
	 	}
	 }
	 ev2.writeErrors(errorsFrame, "errorsFrame");
	 if(ev2.size()>0)
	 {
	 	return;
	 }
	document.contract.submit();
}

<s:if test="maininfo.mainItemPeople!=null">
showdepart('<s:property value="maininfo.mainItemPeople"/>');
</s:if>
  var moneyDiv=document.getElementById("moneyname");
getChargeManOfDepartment("<s:property value="maininfo.mainItemDept"/>");
</script>
</body>
</html>


