<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>合同新建</title>
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
	function selectedClient(clientObj){
		document.getElementById("clientName").value = clientObj.clientName;
		document.getElementById("clientId").value = clientObj.clientId;
	}
	function selectedClient(client){
	var i= client.clientId;
	var j= client.clientName;
	 if(i!=""&&j!=""){
	    var showhidden=document.getElementById("showhidden");
	    var select=document.getElementById(showhidden.value);
	    for(a=0;a<select.length;a++){
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
	         setShowId("cmb");selectedClient({clientId:select.options[i].value,clientName:select.options[i].text});
             setShowId("cmi");selectedClient({clientId:select.options[i].value,clientName:select.options[i].text});
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
	ajaxSetSelectOptions("<s:url action="jsonData"></s:url>?method=doGetChargeManOfDepartment&departmentCode="+departmentCode,$('chargeManList'),"id","name");
    }
<s:if test="#isRelationSuccess == 'true' ">
   alert("保存成功");
</s:if>
</script>

<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
</head>
<body  onload="refresh()"><div align="left">
<s:if test="isModify==0" >
  <div  style="color:#000000">当前页面：合同管理 -&gt; 合同新建</div>
</s:if>
<s:if test="isModify==1">
 <div  style="color:#000000">当前页面：合同管理 -&gt; 草稿合同修改</div>
</s:if>
</div>
 <s:form action="contract" theme="simple">
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
        <%@ include file="/WEB-INF/jsp/contract/ContractTopTab.jsp"%> 
      </div>
        <div id="content" class="content1" >
         <input type="hidden"  name="delmainpartid" id="delmainpartid"/>
          <div  id="content"  >
            <div align="center">
              <table  width="100%" height="307">
                <tr class="bg_table02">
                  <td align="right" width="98"><div align="right"><font color="red">* </font>客户名称：</div></td>
                  <td align="left" width="251"><div align="left">
					  <s:select name="maininfo.conCustomer" list="clientlist" headerKey="" headerValue="--请选择--"  cssStyle="width:168px;" listKey="id" listValue="name" onchange="setAllClient(this);getLinkMainOfClient(this.value);"  ></s:select>
					  <input type="button" value="..."  id="cmc"	onclick="setShowId(this.id);javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">                 
                       </td>
                  <td align="right"><div align="right"><font color="red">* </font>开票客户：</div></td>
                  <td align="left">
                      <s:select name="maininfo.billCustomer" headerKey="" headerValue="--请选择--" cssStyle="width:168px;"  list="allclientlist" listKey="id" listValue="name"  >
                      </s:select>  
                      <input type="button"  id="cmb" value="..." onclick="setShowId(this.id);javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
                  </td>
                </tr>
                <tr>
                  <td width="98" align="right" class="bg_table02"><font color="red">* </font>项目单位：</td>
                  <td width="251" align="left" class="bg_table02"> 
                  <s:select cssStyle="width:168px;"  name="maininfo.itemCustomer"  headerKey="" headerValue="--请选择--" list="eventclientlist" listKey="id" listValue="name"  >
                      </s:select>    
                    <input type="button"  id="cmi" value="..." onclick="setShowId(this.id);javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');"></td>
                  <td width="136" align="right" class="bg_table02">&nbsp;</td>
                  <td width="243" align="left" class="bg_table02">&nbsp;</td>
                </tr>
                <tr>
                  <td align="right" class="bg_table02"><div align="right">客户联系人：</div></td>
                  <td align="left" class="bg_table02">
                  <s:select list="linkmanlist" name="maininfo.linkManId" required="true" listKey="id" listValue="name" headerKey="" headerValue="--请选择--" ></s:select>
                  </td>
                  <td align="right" class="bg_table02"><div align="right">甲方的合同号：</div></td>
                  <td align="left" class="bg_table02"><div align="left">
                          <s:textfield name="maininfo.partyAConId" />     
                  </div></td>
                </tr>
                <tr>
                  <td align="right" class="bg_table02"><div align="right">客户项目类型： </div></td>
                  <td align="left" class="bg_table02"><div align="left">
                    <s:select headerKey="" cssStyle="width:84px;" headerValue="--请选择--"  name="maininfo.customereventtype" list="customeventypelist" listKey="typeSmall" listValue="typeName"  >
                      </s:select>    
                    </div></td>
                  <td class="bg_table02" align="right"><div align="right">甲方的项目工程编号：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                      <s:textfield name="maininfo.partyAProId" />
                    </div></td>
                </tr>
                <tr>
                  <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right"><div align="right"><font color="red">* </font>合同名称：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                      <s:textfield name="maininfo.conName" />
                    </div></td>
                  <td class="bg_table02" align="right"><div align="right"><font color="red">* </font>主项目部门：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
           <s:select  name="maininfo.mainItemDept" headerKey="" onchange="getChargeManOfDepartment(this.value)" headerValue="--请选择--" list="dutydepartmentlist" listKey="typeSmall" listValue="typeName"  >
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
							name="maininfo.mainItemPeople"
							style="width: 90px; height: 21px; font-size: 10pt;" /><span
							style="width: 18px; border: 0px solid red;"><select
							style="margin-left: -90px; width: 108px;" id="chargeManList"
							onchange="departPChange(this)"></select> </span>
                  
                  </div></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right"><div align="right"><font color="red">* </font>合同类型：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
<s:select list="contracttype"  name="maininfo.ContractType" listKey="typeSmall" listValue="typeName" ></s:select> 
                    </div></td>
                  <td align="right" class="bg_table02"><div align="right"><font color="red">* </font>合同性质：</div></td>
                  <td align="left" class="bg_table02"><div align="left">
<s:select list="contractnature" headerKey="" headerValue="--请选择--" name="maininfo.conType" listKey="typeSmall" listValue="typeName" ></s:select>
                    </div></td>
                </tr>
                 <tr>
                  <td class="bg_table02" nowrap align="right"><div align="right"><font color="red">* </font>合同含税总金额：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
               <s:textfield name="maininfo.conTaxTamount" maxlength="15" />
                    </div></td>
                  <td align="right" class="bg_table02"><div align="right">货币单位：</div></td>
                  <td align="left" class="bg_table02"><div align="left"><s:select list="copecklist" name="maininfo.copeck" listKey="typeSmall" listValue="typeName" ></s:select>
                    </div></td>
                </tr>
                <tr>
                <td class="bg_table02" align="right">基准汇率：</td>
                <td class="bg_table02" align="left">
                  <s:if test="maininfo.baserate!=null">
                   <s:textfield name="maininfo.baserate" ></s:textfield>
                  </s:if>
                  <s:else>
                  <s:textfield name="maininfo.baserate" value="1" ></s:textfield>
                  </s:else>
				 </td>
                <td align="right" class="bg_table02"></td>
                <td align="left" class="bg_table02">
               
                </td>
                </tr>
                <tr>
                  <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
                </tr>
                <tr>
                  <td class="bg_table02" nowrap align="right"><div align="right">合同签订日期：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                  <s:textfield name="maininfo.conSignDate" id="conSignDate"   size="12" onclick="javascript:ShowCalendar(this.id);" />
                  <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('conSignDate')" align="absMiddle" alt=""  />                 
                   </div></td>
                  <td class="bg_table02" align="right"><div align="right">退税： </div></td>
                  <td class="bg_table02" align="left"><div align="left">
                      <s:checkbox name="maininfo.conDrawback"></s:checkbox>
                    </div></td>
                </tr>
                <tr>
                  <td align="right" nowrap class="bg_table02"><div align="right">合同起始日期： </div></td>
                  <td align="left" class="bg_table02"><div align="left">
                  <s:textfield name="maininfo.conStartDate" id="conStartDate"   size="12" onclick="javascript:ShowCalendar(this.id);"   />
                  <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('conStartDate')" align=absMiddle alt=""  />  
                     <td class="bg_table02" align="right"><div align="right">中标合同：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                     <s:checkbox name="maininfo.conBid"></s:checkbox>
                    </div></td>
                </tr>
                <tr>
                  <td height="30" nowrap align="right" class="bg_table02"><div align="right">合同结束日期：</div></td>
                  <td align="left" class="bg_table02"><div align="left">
                  <s:textfield name="maininfo.conEndDate" id="conEndDate"   size="12"   onclick="javascript:ShowCalendar(this.id);"   />
                  <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('conEndDate')" align=absMiddle alt=""  />  
                     <td class="bg_table02" align="right"><div align="right">纳入年度运维合同：</div></td>
                  <td align="left" class="bg_table02"><div align="left">
                      <s:checkbox name="maininfo.IntoYearCon"></s:checkbox>
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
                <tr>
                  <td align="center" class="bg_table01">编号</td>
                  <td align="center" class="bg_table01">费用名称</td>
                  <td align="center" class="bg_table01">金额</td>
                  <td align="center" class="bg_table01">开票类型</td>
                  <td align="center" class="bg_table01">操作</td>
                  </tr>
                <s:iterator value="mainMoneyList" status="mainMoneyList" >
                  <tr>
                  <td align="center"><s:property value="#mainMoneyList.index+1"/></td>
                  <td align="center"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/></td>
                  <td align="center"><s:property value="money"/></td>
                  <td align="center"><s:property value="typeManageService.getYXTypeManage(1004,ticketType).typeName" /></td>
                  <td align="center"><input type="button" value="删除" onclick="javascript:cleanmoney();if(!validate2()){setNum(1);delmainmoney(<s:property value="id"/>);document.contract.submit();}"/></td>
                 
                  </tr>
                </s:iterator>
                <tr>
                <td ></td>
                <td align="center"><s:select headerKey="" headerValue="--请选择--" name="mainmoneytype" list="itemdesigntypelist" listKey="typeSmall" listValue="typeName" >
						</s:select></td>
                <td align="center"><s:textfield name="mainmoney"/></td>
                <td align="center"><s:select list="tickettype" name="ticketType" listKey="typeSmall" listValue="typeName" ></s:select>  </td>
                <td align="center"><input type="button" value="添加" onclick="javascript:if(!validate1()){setNum(1);document.contract.submit();}"></td>
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
        <s:if test="isModify==0" >
            <input  type="button" value="保    存" onclick="javascript:if(!validate2()){setNum(1);document.contract.submit();}" class="button02"/>
            <input  type="button" value="保存并关闭" onClick="javascript:if(!validate()){{if(confirm('确认保存并关闭草稿合同')){setNum(0);document.contract.submit();}return false;}}"  class="button02"/>
        </s:if>
        <s:if test="isModify==1">
           <input  type="button" value="保存"  onClick="javascript:if(!validate2()){setNum(1);document.contract.submit();}" class="button01"/>
          <input  type="submit" value="确认提交"  onClick="setMethod('sureSubmit');{if(confirm('确认提交草稿合同')){return true;}return false;}" class="button01"/>
          <input  type="submit" value="删除"  onClick="setMethod('delContract');{if(confirm('确认删除草稿合同')){return true;}return false;}" class="button01"/>
           <input  type="submit" value="返回"  onClick="setMethod('goback');" class="button01"/>
        </s:if>      
        
        </div>
<!--else-->
</s:form>
<script type="text/javascript">
setShowId("cmc");selectedClient({clientId:"<s:property value="maininfo.conCustomer"/>",clientName:"<s:property value="contractservice.getYXClientCode(maininfo.conCustomer)"/>"});
setShowId("cmb");selectedClient({clientId:"<s:property value="maininfo.billCustomer"/>",clientName:"<s:property value="contractservice.getYXClientCode(maininfo.billCustomer)"/>"});
setShowId("cmi");selectedClient({clientId:"<s:property value="maininfo.itemCustomer"/>",clientName:"<s:property value="contractservice.getYXClientCode(maininfo.itemCustomer)"/>"});
function validate(){
       var ev2=new Validator();
       with(contract){  
       ev2.test("notblank","客户不能为空",$('maininfo.conCustomer').value);   
       ev2.test("notblank","开票客户不能为空",$('maininfo.billCustomer').value); 
       ev2.test("notblank","项目单位不能为空",$('maininfo.itemCustomer').value); 
       ev2.test("notblank","合同决算信息不能为空",$('maininfo.FinalAccount').value); 
       ev2.test("notblank","合同类型不能为空",$('maininfo.ContractType').value); 
       ev2.test("notblank","合同性质不能为空",$('maininfo.conType').value); 
       ev2.test("notblank","合同名称不能为空",$('maininfo.conName').value);
       ev2.test("float","合同金额必须为数字",$('maininfo.conTaxTamount').value);  
       ev2.test("+float","未添加费用组成","<s:property value="mainMoneyList.size()"/>");     
       
       var enddate=document.getElementById("conEndDate").value;
   	   var startdate=document.getElementById("conStartDate").value;
   
   	   var arrJHRQ=enddate.split('-'); //转成成数组，分别为年，月，日，下同
       var arrJHWCSJ=startdate.split('-');
       var dateJHRQ=new Date(parseInt(arrJHRQ[0]),parseInt(arrJHRQ[1])-1,parseInt(arrJHRQ[2]),0,0,0); //新建日期对象
       var dateJHWCSJ=new Date(parseInt(arrJHWCSJ[0]),parseInt(arrJHWCSJ[1])-1,parseInt(arrJHWCSJ[2]),0,0,0);
   
       if (dateJHRQ.getTime()<dateJHWCSJ.getTime()) {
           ev2.addError("结束日期小于起始日期");
       }    
        
       var a=0;
        <s:iterator value="mainMoneyList" status="mainMoneyList" >
         a=a+parseFloatNumber("<s:property value="money"/>");
         </s:iterator>
       var b=document.getElementById("contract_mainmoney").value;
       var c=document.getElementById("contract_maininfo_conTaxTamount").value;
       var d=0;
       if(b!=""){
        d=parseFloatNumber(a)+parseFloatNumber(b);
       }
       if(b==""){
         d=a;
       }
       c=parseFloatNumber(c);
       if(d!=c){
         ev2.addError("费用组成和总金额不相等");
       }  
        
       
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}
function validate1(){
       var ev2=new Validator();
       with(contract){  
       ev2.test("notblank","客户不能为空",$('maininfo.conCustomer').value);   
       ev2.test("notblank","开票客户不能为空",$('maininfo.billCustomer').value); 
       ev2.test("notblank","项目单位不能为空",$('maininfo.itemCustomer').value); 
       ev2.test("notblank","合同决算信息不能为空",$('maininfo.FinalAccount').value); 
       ev2.test("notblank","合同类型不能为空",$('maininfo.ContractType').value); 
       ev2.test("notblank","合同性质不能为空",$('maininfo.conType').value); 
       ev2.test("notblank","合同名称不能为空",$('maininfo.conName').value);
       ev2.test("float","合同金额必须为数字",$('maininfo.conTaxTamount').value);  
        var a=0;
         <s:iterator value="mainMoneyList" status="mainMoneyList" >
         a=a+parseFloatNumber("<s:property value="money"/>");
         </s:iterator>
       var b=document.getElementById("contract_mainmoney").value;
       var c=document.getElementById("contract_maininfo_conTaxTamount").value;
       var d=parseFloatNumber(a)+parseFloatNumber(b);
       c=parseFloatNumber(c);
       if(d>c){
         ev2.addError("费用组成超过合同总金额");
       }  
       
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}
function validate2(){
       var ev2=new Validator();
       with(contract){  
       ev2.test("notblank","客户不能为空",$('maininfo.conCustomer').value);   
       ev2.test("notblank","开票客户不能为空",$('maininfo.billCustomer').value); 
       ev2.test("notblank","项目单位不能为空",$('maininfo.itemCustomer').value); 
       ev2.test("notblank","合同决算信息不能为空",$('maininfo.FinalAccount').value); 
       ev2.test("notblank","合同类型不能为空",$('maininfo.ContractType').value); 
       ev2.test("notblank","合同性质不能为空",$('maininfo.conType').value); 
       ev2.test("notblank","合同名称不能为空",$('maininfo.conName').value);
       ev2.test("float","合同金额必须为数字",$('maininfo.conTaxTamount').value);  
     
       
       var enddate=document.getElementById("conEndDate").value;
   	   var startdate=document.getElementById("conStartDate").value;
   
   	   var arrJHRQ=enddate.split('-'); //转成成数组，分别为年，月，日，下同
       var arrJHWCSJ=startdate.split('-');
       var dateJHRQ=new Date(parseInt(arrJHRQ[0]),parseInt(arrJHRQ[1])-1,parseInt(arrJHRQ[2]),0,0,0); //新建日期对象
       var dateJHWCSJ=new Date(parseInt(arrJHWCSJ[0]),parseInt(arrJHWCSJ[1])-1,parseInt(arrJHWCSJ[2]),0,0,0);
   
       if (dateJHRQ.getTime()<dateJHWCSJ.getTime()) {
           ev2.addError("结束日期小于起始日期");
       }    
        
       var a=0;
        <s:iterator value="mainMoneyList" status="mainMoneyList" >
         a=a+parseFloatNumber("<s:property value="money"/>");
         </s:iterator>
       var b=document.getElementById("contract_mainmoney").value;
       var c=document.getElementById("contract_maininfo_conTaxTamount").value;
       var d=parseFloatNumber(a)+parseFloatNumber(b);
       c=parseFloatNumber(c);
       if(d>c){
         ev2.addError("费用组成超过合同总金额");
       }  
        
       
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
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
<s:if test="maininfo.mainItemPeople!=null">
showdepart('<s:property value="maininfo.mainItemPeople"/>');
getChargeManOfDepartment(<s:property value="maininfo.mainItemDept"/>);
</s:if>
</script>
</body>
</html>


