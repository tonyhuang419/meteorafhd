
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>合同变更</title>
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
	    }    
	}
	function setShowId(buttonid){	
      var hidden=document.getElementById("showhidden");
      if(buttonid=="fmc"){
      	hidden.setAttribute("value","formalContractModify_maininfo_conCustomer");
      }  
      if(buttonid=="fmb"){
        hidden.setAttribute("value","formalContractModify_maininfo_billCustomer")
      }
      if(buttonid=="fmi"){
        hidden.setAttribute("value","formalContractModify_maininfo_itemCustomer");
      }
	}
	function setAllClient(select){
	   for(i=0;i<select.length;i++){
	       if(select.options[i].selected){
	         setShowId("fmb");selectedClient({clientId:select.options[i].value,clientName:select.options[i].text});
             setShowId("fmi");selectedClient({clientId:select.options[i].value,clientName:select.options[i].text});
	       }
	   }
	}
	function getLinkMainOfClient(clientIdStr){
	    ajaxSetSelectOptions("/yx/jsonData.action?method=doGetLinkMainOfClient&clientId="+clientIdStr,document.getElementById("maininfo.linkManId"),"id","name");
	}
</script>

<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
</head>
<body ><div align="left">
 <div  style="color:#000000">当前页面：合同管理 -&gt; 正式合同变更</div>
</div>
 <s:form action="formalContractModify" disabled="false" theme="simple">
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
        <div id="title" >
        <%@ include file="/WEB-INF/jsp/contract/modifyformalContract/ContractTopTab.jsp"%> 
      </div>
        <div id="content" class="content1" >
          <div  id="content"  >
            <div align="center">
              <table  width="100%" height="307">
                <tr class="bg_table02">
                  <td align="right" width="98"><div align="right">客户名称：</div></td>
                  <td align="left" width="251"><div align="left">
					  <s:select name="maininfo.conCustomer" list="clientlist" headerKey="" headerValue="--请选择--"  cssStyle="width:168px;" listKey="id" listValue="name" onchange="setAllClient(this);getLinkMainOfClient(this.value);"  ></s:select>
					  <input type="button" value="..." id="fmc"	onclick="setShowId(this.id);javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">                 
                       </td>
                  <td align="right"><div align="right">开票客户：</div></td>
                  <td align="left">
                      <s:select name="maininfo.billCustomer" headerKey="" headerValue="--请选择--" cssStyle="width:168px;"  list="allclientlist" listKey="id" listValue="name"  >
                      </s:select>  
                      <input type="button" id="fmb" value="..." onclick="setShowId(this.id);javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');">
                  </td>
                </tr>
                <tr>
                  <td width="98" align="right" class="bg_table02">项目单位：</td>
                  <td width="251" align="left" class="bg_table02"> 
                  <s:select cssStyle="width:168px;"  name="maininfo.itemCustomer"  headerKey="" headerValue="--请选择--" list="eventclientlist" listKey="id" listValue="name"  >
                      </s:select>    
                    <input type="button" id="fmi" value="..." onclick="setShowId(this.id);javascript:window.open('../searchClient/searchClientQuery.action?method=getClientList','newwindow','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=500,width=600');"></td>
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
                  <td class="bg_table02" align="right"><div align="right">合同名称：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                      <s:textfield name="maininfo.conName" />
                    </div></td>
                  <td class="bg_table02" align="right"><div align="right">主项目部门：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
           <s:select  name="maininfo.mainItemDept" headerKey="" headerValue="--请选择--" list="dutydepartmentlist" listKey="typeSmall" listValue="typeName"  >
                      </s:select>   
                    </div></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right"><div align="right">预决算信息：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                 <s:select list="#@java.util.TreeMap@{0:'非预决算',1:'预决算'}"  headerKey="" headerValue="--请选择--" name="maininfo.FinalAccount"  ></s:select> </div></td>
                  <td class="bg_table02" align="right"><div align="right">主项目负责人：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                    <s:textfield name="maininfo.mainItemPeople" />
                  </div></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right"><div align="right">合同类型：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
<s:select list="contracttype" headerKey="" headerValue="--请选择--" name="maininfo.ContractType" listKey="typeSmall" listValue="typeName" ></s:select> 
                    </div></td>
                  <td align="right" class="bg_table02"><div align="right">主项目负责人电话：</div></td>
                  <td align="left" class="bg_table02"><div align="left">
                      <s:textfield name="maininfo.mainItemPhone" />
                    </div></td>
                </tr>
                 <tr>
                  <td class="bg_table02" align="right"><div align="right">合同性质：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
               <s:select list="contractnature" headerKey="" headerValue="--请选择--" name="maininfo.conType" listKey="typeSmall" listValue="typeName" ></s:select>
                    </div></td>
                  <td align="right" class="bg_table02"><div align="right">合同含税总金额：</div></td>
                  <td align="left" class="bg_table02"><div align="left"><s:textfield name="maininfo.conTaxTamount"  />
                    </div></td>
                </tr>
                <tr>
                  <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right"><div align="right">合同签订日期：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                  <s:textfield name="maininfo.conSignDate" id="conSignDate" readonly="true"  size="12" onclick="javascript:ShowCalendar(this.id);" />
                  <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('conSignDate')" align="absMiddle" alt=""  />                 
                   </div></td>
                  <td class="bg_table02" align="right"><div align="right">是否退税： </div></td>
                  <td class="bg_table02" align="left"><div align="left">
                      <s:checkbox name="maininfo.conDrawback"></s:checkbox>
                    </div></td>
                </tr>
                <tr>
                  <td align="right" class="bg_table02"><div align="right">合同起始日期： </div></td>
                  <td align="left" class="bg_table02"><div align="left">
                  <s:textfield name="maininfo.conStartDate" id="conStartDate" readonly="true"  size="12" onclick="javascript:ShowCalendar(this.id);"   />
                  <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('conStartDate')" align=absMiddle alt=""  />  
                     <td class="bg_table02" align="right"><div align="right">是否中标合同：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                     <s:checkbox name="maininfo.conBid"></s:checkbox>
                    </div></td>
                </tr>
                <tr>
                  <td height="30" align="right" class="bg_table02"><div align="right">合同结束日期：</div></td>
                  <td align="left" class="bg_table02"><div align="left">
                  <s:textfield name="maininfo.conEndDate" id="conEndDate" readonly="true"  size="12" onclick="javascript:ShowCalendar(this.id);"   />
                  <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('conEndDate')" align=absMiddle alt=""  />  
                     <td class="bg_table02" align="right"><div align="right">是否纳入年度运维合同：</div></td>
                  <td align="left" class="bg_table02"><div align="left">
                      <s:checkbox name="maininfo.IntoYearCon"></s:checkbox>
                    </div></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right"><div align="right">完工应交材料： </div></td>
                  <td class="bg_table02" align="left"><div align="left">
                      <s:textfield name="maininfo.finProStuff" />
                    </div></td>
                  <td class="bg_table02" align="right"><div align="right" style=" width : 81px;">负责部门数：</div></td>
                  <td class="bg_table02" align="left"><div align="left">
                     <s:select list="#@java.util.TreeMap@{1:'1',2:'2',3:'3',4:'4',5:'5',6:'6',7:'7',8:'8',9:'9',10:'10'}" name="departNum"></s:select>
                    </div></td>
                </tr>
                <tr>
                  <td class="bg_table02" align="right">&nbsp;</td>
                  <td class="bg_table02" align="left">&nbsp;</td>
                  <td align="right" class="bg_table02"><div align="right">计划阶段数： </div></td>
                  <td align="left" class="bg_table02"><div align="left">
                     <s:select  list="#@java.util.TreeMap@{1:'1',2:'2',3:'3',4:'4',5:'5',6:'6',7:'7',8:'8',9:'9',10:'10',11:'11',12:'12',13:'13',14:'14',15:'15',16:'16',17:'17',18:'18',19:'19',20:'20'}" name="stageNum"></s:select>
                     
                    </div></td>
                </tr>
                        <tr>
             <td colspan="4" align="right" class="bg_table02"><hr align="right"></td>
                </tr>
                <tr>
                  <td align="center" class="bg_table01">编号</td>
                  <td align="center" class="bg_table01">费用名称</td>
                  <td align="center" class="bg_table01">金额</td>
                  <td align="center" class="bg_table01">操作</td>
                  </tr>
                <s:iterator value="mainMoneyList" status="mainMoneyList" >
                  <tr>
                  <td align="center"><s:property value="#mainMoneyList.index+1"/></td>
                  <td align="center"><s:property value="typeManageService.getYXTypeManage(1012,moneytype).typeName"/></td>
                  <td align="center"><s:property value="money"/></td>
                  <td align="center"><input type="button" value="删除" onclick="javascript:if(!validate()){setNum(1);delmainmoney(<s:property value="id"/>);document.contract.submit();}"/></td>
                 
                  </tr>
                </s:iterator>
                       <tr>
                <td ></td>
                <td align="center"><s:select headerKey="" headerValue="--请选择--" name="mainmoneytype" list="itemdesigntypelist" listKey="typeSmall" listValue="typeName" >
						</s:select></td>
                <td align="center"><s:textfield name="mainmoney"/></td>
                <td align="center"><input type="button" value="添加" onclick="javascript:if(!validate()){setNum(1);document.contract.submit();}"></td>
                </tr>
                <tr>
                 <td colspan="4" align="right" class="bg_table02"><hr></td>
                </tr>
                <tr>
                <td class="bg_table02" align="right">变更说明：</td>
                  <td colspan="3" align="left" class="bg_table02"><s:textarea name="changeExplain" ></s:textarea> </td>
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
   <input  type="submit" value="变更提交"  onClick="setNum(0);" class="button02" >
        </div>
<!--else-->
</s:form>
<script type="text/javascript">
setShowId("fmc");selectedClient({clientId:"<s:property value="maininfo.conCustomer"/>",clientName:"<s:property value="contractservice.getYXClientCode(maininfo.conCustomer)"/>"});
setShowId("fmb");selectedClient({clientId:"<s:property value="maininfo.billCustomer"/>",clientName:"<s:property value="contractservice.getYXClientCode(maininfo.billCustomer)"/>"});
setShowId("fmi");selectedClient({clientId:"<s:property value="maininfo.itemCustomer"/>",clientName:"<s:property value="contractservice.getYXClientCode(maininfo.itemCustomer)"/>"});
function validate(){
       var ev2=new Validator();
       with(formalContractModify){  
       ev2.test("notblank","客户不能为空",$('maininfo.conCustomer').value);   
       ev2.test("notblank","开票客户不能为空",$('maininfo.billCustomer').value); 
       ev2.test("notblank","项目单位不能为空",$('maininfo.itemCustomer').value); 
       ev2.test("notblank","合同决算信息不能为空",$('maininfo.FinalAccount').value); 
       ev2.test("notblank","合同类型不能为空",$('maininfo.ContractType').value); 
       ev2.test("notblank","合同性质不能为空",$('maininfo.conType').value); 
       ev2.test("notblank","合同名称不能为空",$('maininfo.conName').value);
       ev2.test("float","合同金额必须为数字",$('maininfo.conTaxTamount').value);       
       
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
}
</script>
</body>
</html>

