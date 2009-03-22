<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>合同新建</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function setNum(i){
var hidden=document.getElementById("tag");
hidden.setAttribute("value",i);
}

function time(img){
	alert("aa");
  var par=img.parentNode;
  var text=par.firstChild;
  ShowCalendar(text.id);
}
function displayX(obj,s,i){
	if(obj.checked){	
 		document.getElementById(s).style.display="block";	
	}
	else{
		document.getElementById(s).style.display="none";
		document.getElementById(i).value="";
		obj.checked=false;
	}
	
}
function onloadClick(){
   displayX(document.getElementById("checkbox6"),"report_date",'CalendarSelector4');
   displayX(document.getElementById("checkbox7"),"fin_date",'CalendarSelector5');
   displayX(document.getElementById("checkbox8"),"reve_date",'CalendarSelector6');
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
<div align="left" style="color: #FF0000"><iframe
		name="errorsFrame" frameborder="0" framespacing="0" height="0"
		scrolling="no"></iframe></div>
 <s:form action="contract" theme="simple">
 <s:hidden name="method" value="saveOtherInfo" />
 <s:hidden name="otherinfo.finallyLize"></s:hidden>
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
   
          <div id="content2" class="hidecontent">
            <!--合同项目开始-->
        
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
          <s:hidden name="otherinfo.otherInfoId"></s:hidden>
              <!--else-->
              <table width="100%"  >
                <tr>
                  <td width="132" class="bg_table02"><div align="right">完工应交材料：</div></td>
                  <td width="200" class="bg_table02" colspan="2"><label>
                    <div align="left">
                    <a href="#" onclick="selectMaterial('<s:property  value="otherinfo.otherInfoId"/>','<s:property  value="mainid"/>')" >选择应交材料</a>
                      </div>
                  </label></td>
                </tr>
                <s:iterator value="checkedMaterialList" id="material" status="materialIndex">
                    <tr>
                  <td width="132" class="bg_table02"><div align="right"></div></td>
                  <td class="bg_table02" colspan="2">
                       <s:property value="#material.materialName"/>
                  </td>
                </tr>
                </s:iterator>
                <tr>
                  <td width="132" class="bg_table02"><div align="right">开工报告：</div></td>
                  <td width="27" class="bg_table02"><label>
                    <div align="center">
                      <s:checkbox name="otherinfo.needPerativeReport" id="checkbox6"  onclick="displayX(this,'report_date','CalendarSelector4');" />
                      </div>
                  </label></td>
                  <td width="573" class="bg_table02"><div id="report_date" style="display:none"><s:textfield id="CalendarSelector4" name="otherinfo.wantOpenReportDate" size="12" />
                     <img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('CalendarSelector4')" align="absMiddle" alt=""  /></div></td>
                </tr>
                
               <tr>
                  <td  class="bg_table02"><div align="right">实物交接：</div></td>
                  <td class="bg_table02"><div align="center">
                    <s:checkbox name="otherinfo.needRecivedThing" id="checkbox8" onclick="displayX(this,'reve_date','CalendarSelector6');" />
                  </div></td>
                  <td class="bg_table02"><div id="reve_date" style="display:none"><s:textfield id="CalendarSelector6" name="otherinfo.wantReciveThing" size="12"  />
                      <img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('CalendarSelector6')" align="absMiddle" alt=""  /></div></td>
                </tr>
                <tr>
                  <td   class="bg_table02"><div align="right"><div id="report_date4">
                    <div align="right">竣工验收证书：</div>
                  </div></td>
                  <td   class="bg_table02"><div align="center">
                    <s:checkbox name="otherinfo.needFinallyReport" id="checkbox7" onclick="displayX(this,'fin_date','CalendarSelector5');" />
                  </div></td>
                  <td   class="bg_table02"><div id="fin_date" style="display:none"><s:textfield id="CalendarSelector5" name="otherinfo.wantFinallyReptDate" size="12"  />
                  <img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('CalendarSelector5')" align="absMiddle" alt=""  /></div></td>
                </tr>
                <tr>
                  <td  class="bg_table02"><div align="right">
                   合同交货日期：</td>
                   <td></td>
                  <td   class="bg_table02"><s:textfield id="conDeliveryDate" name="otherinfo.conDeliveryDate" size="12"  />
                  <img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('conDeliveryDate')" align="absMiddle" alt=""/></td>
                 </tr>

                 <tr class="bg_table02">
                  <td class="bg_table02" align="right"><div align="right">质保期要求：</div></td>
                  <td colspan="2" align="right" class="bg_table02"><div align="left">
                      <s:textarea name="otherinfo.guaranteeInfo" cols="50" rows="5" id="textfield"></s:textarea>
                 </div>
                </tr>
                <tr class="bg_table02">
                  <td class="bg_table02" align="right"><div align="right">备注：</div></td>
                  <td colspan="2" align="right" class="bg_table02"><div align="left">
                      <s:textarea name="otherinfo.otherRemarks" cols="50" rows="5" id="textfield"></s:textarea>
                </div>
                </tr>
              </table>
            
            <!-- ELSE END -->
          </div>
  </div><!--总体结束DIV-->
        <div align="center">
         <s:if test="isModify==0" >
            <input  type="button" value="保    存" onclick="javascript:if(!validate()){setNum(6);document.contract.submit();}" class="button02"/>
            <input  type="button" value="保存并关闭" onClick="javascript:if(!validate()){{if(confirm('确认保存并关闭草稿合同')){setNum(0);document.contract.submit();}return false;}}"  class="button02"/>
       </s:if>
        <s:if test="isModify==1">
           <input  type="button" value="保存"  onclick="javascript:if(!validate()){setNum(6);document.contract.submit();}" class="button01"/>
        </s:if>
        <s:if test="mainid==null">
          </s:if>
          <s:else>
             <input  type="button" value="确认提交"  onclick="if(!validate()){{if(confirm('确认提交草稿合同')){setMethod('sureSubmit');document.contract.submit();}}}" class="button01"/>
      <input type="button" value="合同拆分导出" class="button01" onClick="javascript:window.open('/yx/contract/contractSplitTable.action?contractMainInfoSid=<s:property value="mainid"/>')"/>
        </s:else>
        <s:if test="isModify==1">
          <input  type="submit" value="删除"  onclick="{if(confirm('确认删除草稿合同')){setMethod('delContract');return true;}return false;}" class="button01"/>
           <input  type="submit" value="返回"  onClick="setMethod('goback');"  class="button01"/>
        </s:if>   
        </div>
</s:form>
<script type="text/javascript">
function validate(){
	
	 var ev2=new Validator();
	 ev2.test("dateYX","开工报告日期时间格式不正确！",document.contract.elements('otherinfo.wantOpenReportDate').value);
	 ev2.test("dateYX","实物交接日期时间格式不正确！",document.contract.elements('otherinfo.wantReciveThing').value);
	 ev2.test("dateYX","竣工验收日期时间格式不正确！",document.contract.elements('otherinfo.wantFinallyReptDate').value);
	 ev2.test("dateYX","合同交货日期时间格式不正确！",document.contract.elements('otherinfo.conDeliveryDate').value);
	 ev2.writeErrors(errorsFrame, "errorsFrame");
	 if(ev2.size()>0)
	 {
	 	return true;
	 }
	 return false;
}
onloadClick();
function selectMaterial(otherId,mainId){
	openWin("contract.action?method=tempSaveOtherInfo&otherInfoId="+otherId+"&mainid="+mainId,500,400);
	//window.open("contract.action?method=tempSaveOtherInfo&otherInfoId="+otherId+"&mainid="+mainId);
}
function baocunMaterial(lize)
{
	setNum(6);
	document.contract.elements("otherinfo.finallyLize").value=lize;
	document.contract.submit();
}
</script>
</body>
</html>
