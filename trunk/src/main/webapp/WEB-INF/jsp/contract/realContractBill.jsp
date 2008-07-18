<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>

<title>开票收款计划变更</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/yx/commons/scripts/CalendarSelector.js"/>"></script>
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script src="/yx/commons/scripts/my_f.js"></script>
<script type="text/javascript">
var whitespace=" \t\n\r";
function isEmpty(s){
var i;
if((s==null)||(s.length==0))
   return true;
for(i=0;i<s.length;i++){
    var c=s.charAt(i);
    if(whitespace.indexOf(c)==-1)
      return false;
 }
 return true;
}

function displayX(obj,s){
	if(obj.checked){	
 		document.getElementById(s).style.display="block";	
	}
	else{
		document.getElementById(s).style.display="none";
	}
}
function changeBgen(thjs){
  var bfdate="pdate"+thjs;
  var rcdate="rdate"+thjs;
  var afbfdate="dateOne"+thjs;
  var afrcdate="dateTwo"+thjs;
  var area="area"+thjs;
  var chresf="trusie"+thjs;
  if(isEmpty(document.getElementById(afbfdate).value)){
          alert("变更开票日期不能为空!");
          document.forms(0).elements(afbfdate).focus();
          return false;
    }
  if(isEmpty(document.getElementById(afrcdate).value)){
          alert("变更收款日期不能为空!");
          document.forms(0).elements(afrcdate).focus();
          return false;
    }
  document.myform.beforBillDate.value=document.getElementById(bfdate).value;
  document.myform.beforReceDate.value=document.getElementById(rcdate).value;
  document.myform.afterChangeDate.value=document.getElementById(afbfdate).value;
  document.myform.afterReceDate.value=document.getElementById(afrcdate).value;
  document.myform.realConBillproSid.value=document.getElementById(chresf).value;
  document.myform.changeExp.value=document.getElementById(area).value;
  
  document.myform.action="/yx/contract/realContractBillandRecePlan.action?method=saveRealHistory";
  document.myform.submit();
}
</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.STYLE1 {
	font-size: 16px;
	font-weight: bold;
}
-->
</style>
</head>
<body>


<form name="myform" method="post">
<input type="hidden" name="beforBillDate"/>
<input type="hidden" name="beforReceDate"/>
<input type="hidden" name="afterChangeDate"/>
<input type="hidden" name="afterReceDate"/>
<input type="hidden" name="changeExp">
<input type="hidden" name="realConBillproSid"/>
<input type="hidden" name="mainId" value="<s:property value="mainId"/>"/>
<s:iterator value="monthlyBillproSids" id="billSid">
<input type="hidden" value="<s:property />" name="monthlyBillproSids"/>
</s:iterator>
<div>
  <div align="left" class="STYLE1">
    <p align="center">开票收款计划变更</p>
<br>
  </div>
</div>
<table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
  <tr align="center">
    <td width="3%" class="bg_table01"><div align="center"></div></td>
    <td width="10%" class="bg_table01"><div align="center">负责部门</div></td>
    <td width="16%" class="bg_table01" ><div align="center">收款和开票阶段</div></td>
    <td width="11%" class="bg_table01" ><div align="center">开票性质</div></td>
    <td width="13%" class="bg_table01" ><div align="center">发票类型</div></td>
    <td width="13%" class="bg_table01" >
      <div align="center">开票金额</div></td>
    <td width="13%" class="bg_table01" ><div align="center">计划开票时间</div></td>
    <td width="11%" class="bg_table01" ><div align="center">计划收款时间</div></td>
 <td width="10%" class="bg_table01" ><div align="center">操作</div></td>
  </tr>
  <s:iterator value="retlBillList" id="bill" status="status">
  <input type="hidden" id="pdate<s:property value="#status.index"/>" name="pdate<s:property value="#status.index"/>" value="<s:property value="#bill[0].realPredBillDate"/>"/>
  <input type="hidden" id="rdate<s:property value="#status.index"/>" name="rdate<s:property value="#status.index"/>" value="<s:property value="#bill[0].realPredReceDate"/>"/>
  <input type="hidden" id="trusie<s:property value="#status.index"/>" name="trusie<s:property value="#status.index"/>" value="<s:property value="#bill[0].realConBillproSid"/>"/>
  <tr>
    <td width="3%" class="bg_table02">
      
      <div align="center">
        <input type="checkbox" name="checkbox" id="checkbox" onClick="displayX(this,'field<s:property value="#status.index"/>')">
        </div></td>
    <td width="10%" class="bg_table02"><div align="center">
    <s:iterator value="projectDeptTypeList" id="pdept">
      <s:if test="#pdept.typeSmall==#bill[3].itemResDept">
          <s:property value="#pdept.typeName"/>
      </s:if>
    </s:iterator>
    
     </div></td>
    <td width="16%" class="bg_table02"><div align="center">
      <s:property value="typeManageService.getYXTypeManage(1023,#bill[1].itemStageName).typeName" />
    
    </div></td>
    <td width="11%"  class="bg_table02"><div align="center">
    
       <s:iterator value="openBillType" id="billsOp">
               <s:if test="#billsOp.typeSmall==#bill[0].billNature">
                       <s:property value="#billsOp.typeName"/>
               </s:if>
       </s:iterator>
    </div></td>
    <td width="13%" class="bg_table02"><div align="center"> 
       <s:iterator value="fapBillType" id="billsp">
               <s:if test="#billsp.typeSmall==#bill[0].billType">
                       <s:property value="#billsp.typeName"/>
               </s:if>
       </s:iterator>
    
    </div></td>
    <td width="13%" class="bg_table02"><div align="center"><s:property value="#bill[0].realBillAmount"/></div></td>
    <td width="13%" class="bg_table02">
      <div align="center"><s:date name="#bill[0].realPredBillDate" format="yyyy-M-d"/></div></td>
    <td  class="bg_table02">
      <div align="center"><s:date name="#bill[0].realPredReceDate" format="yyyy-M-d"/></div></td>
 <td width="10%" class="bg_table02"><div align="center"><a href="/yx/contract/seeRealContractHistory.action?mainId=<s:property value="#bill[0].conMainInfoSid"/>&dept=<s:property value="#bill[3].itemResDept"/>&htxz=<s:property value="#bill[0].billNature"/>&receId=<s:property value="#bill[0].realConBillproSid"/>" target="_blank">变更历史</a></div></td>
  </tr>
 <tr >
    <td  class="bg_table02"><div align="center"></div></td>
    <td colspan="9" class="bg_table02"><div id="field" align="left"  >
        
        <div align="left">
          <table width="380" class="con_stage_tableborder">
            <tr>
              <td width="339"><strong>开票内容：</strong><s:property value="#bill[0].billContent"/></td>
            </tr>
          </table>
        </div>
    </div></td>
 </tr>
 
<tr> 
    <td  class="bg_table02"> <div align="center"></div></td>
    <td colspan="8" class="bg_table02">    
     <div align="left" id="field<s:property value="#status.index"/>"  style="display:none"  >
            <table width="91%"  bgcolor="#003366" class="bg_table02">
              <tr>
                <td width="22%" class="bg_table02"><div align="center">变更开票日期</div></td>
                <td width="21%" class="bg_table02"><div align="center">变更收款日期</div></td>
                <td width="42%" class="bg_table02"><div align="center">变更说明：</div></td>
                </tr>
              <tr>
                <td height="29" class="bg_table02"><div align="center">
                  <input id="dateOne<s:property value="#status.index"/>" style="WIDTH: 90px" maxlength=10 name="dateOne<s:property value="#status.index"/>" value="" onclick="javascript:ShowCalendar(this.id)">
                  <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('dateOne<s:property value="#status.index"/>')" align="absMiddle" alt=""  /></div></td>
                <td class="bg_table02"><div align="center">
                  <input id="dateTwo<s:property value="#status.index"/>" style="WIDTH: 90px" maxlength=10 name="dateTwo<s:property value="#status.index"/>" value="" onclick="javascript:ShowCalendar(this.id)">
                   <img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('dateTwo<s:property value="#status.index"/>')" align="absMiddle" alt=""  /></div></td>
                <td class="bg_table02"><div align="center">
                  <textarea id="area<s:property value="#status.index"/>" name="area<s:property value="#status.index"/>" cols="40"></textarea>
                </div></td>
                   <td width="15%" class="bg_table02"> 
                   <div align="center">
                     <input type="button" name="button" id="button" value="确认保存" class="button01" onclick="changeBgen('<s:property value="#status.index"/>');">
                                  </div>
          </td>
              </tr>
        </table>
     </div>      </td>

  </tr>
  </s:iterator>
  
 </table>
</form>
<script type="text/javascript">
if('<s:property value="mesaage"/>'=='success'){
  alert("变更已保存");
}
</script>
</body>
</html>
