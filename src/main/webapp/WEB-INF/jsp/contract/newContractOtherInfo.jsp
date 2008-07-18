<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>合同新建</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<s:url value="/commons/scripts/time.js"/>" ></script>
<script type="text/javascript">
function setNum(i){
var hidden=document.getElementById("tag");
hidden.setAttribute("value",i);
}

function time(img){
  var par=img.parentNode;
  var text=par.firstChild;
  ShowCalendar(text.id);
}
function displayX(obj,s){
	if(obj.checked){	
 		document.getElementById(s).style.display="block";	
	}
	else{
		document.getElementById(s).style.display="none";
		obj.checked=false;
	}
	
}
function onloadClick(){
   displayX(document.getElementById("checkbox6"),"report_date");
   displayX(document.getElementById("checkbox7"),"fin_date");
   displayX(document.getElementById("checkbox8"),"reve_date");
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
 <s:hidden name="method" value="saveOtherInfo" />
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
                  <td width="27" class="bg_table02" colspan="2"><label>
                    <div align="left">
                      <s:textfield name="otherinfo.finallyLize" />
                      </div>
                  </label></td>
                  
                </tr>
                <tr>
                  <td width="132" class="bg_table02"><div align="right">开工报告：</div></td>
                  <td width="27" class="bg_table02"><label>
                    <div align="center">
                      <s:checkbox name="otherinfo.needPerativeReport" id="checkbox6"  onclick="displayX(this,'report_date');" />
                      </div>
                  </label></td>
                  <td width="573" class="bg_table02"><div id="report_date" style="display:none"><s:textfield id="CalendarSelector4" name="otherinfo.wantOpenReportDate" readonly="true" onclick="javascript:ShowCalendar(this.id)"/>
                     <img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('CalendarSelector4')" align="absMiddle" alt=""  /></div></td>
                </tr>
                
               <tr>
                  <td  class="bg_table02"><div align="right">实物交接：</div></td>
                  <td class="bg_table02"><div align="center">
                    <s:checkbox name="otherinfo.needRecivedThing" id="checkbox8" onclick="displayX(this,'reve_date');" />
                  </div></td>
                  <td class="bg_table02"><div id="reve_date" style="display:none"><s:textfield id="CalendarSelector6" name="otherinfo.wantReciveThing" readonly="true" onclick="javascript:ShowCalendar(this.id)"/>
                      <img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('CalendarSelector6')" align="absMiddle" alt=""  /></div></td>
                </tr>
                <tr>
                  <td   class="bg_table02"><div align="right"><div id="report_date4">
                    <div align="right">竣工验收证书：</div>
                  </div></td>
                  <td   class="bg_table02"><div align="center">
                    <s:checkbox name="otherinfo.needFinallyReport" id="checkbox7" onclick="displayX(this,'fin_date');" />
                  </div></td>
                  <td   class="bg_table02"><div id="fin_date" style="display:none"><s:textfield id="CalendarSelector5" name="otherinfo.wantFinallyReptDate" readonly="true" onclick="javascript:ShowCalendar(this.id)"/>
                  <img src="/yx/commons/images/calendar.gif" onclick="javascript:ShowCalendar('CalendarSelector5')" align="absMiddle" alt=""  /></div></td>
                </tr>
                

                <tr class="bg_table02">
                  <td class="bg_table02" align="right"><div align="right">备注：</div></td>
                  <td colspan="2" align="right" class="bg_table02"><div align="left">
                      <s:textarea name="otherinfo.otherRemarks" cols="50" rows="5" id="textfield"></s:textarea>
                </div>                </tr>
                <tr class="bg_table02">
                  <td  height="*" class="bg_table02" align="right">&nbsp;</td>
                <td colspan="2" align="right" class="bg_table02">                </tr>
              </table>
            
            <!-- ELSE END -->
          </div>
  </div><!--总体结束DIV-->
        <div align="center">
         <s:if test="isModify==0" >
            <input  type="button" value="保    存" onclick="javascript:if(!validate()){document.contract.submit();}" class="button02"/>
            <input  type="button" value="保存并关闭" onClick="javascript:if(!validate()){{if(confirm('确认保存并关闭草稿合同')){setNum(0);document.contract.submit();}return false;}}"  class="button02"/>
       </s:if>
        <s:if test="isModify==1">
           <input  type="submit" value="保存"  onclick="setNum(1);" class="button01"/>
             <input  type="submit" value="确认提交"  onclick="setMethod('sureSubmit');{if(confirm('确认提交草稿合同')){refresh();return true;}return false;}" class="button01"/>
          <input  type="submit" value="删除"  onclick="setMethod('delContract');{if(confirm('确认删除草稿合同')){return true;}return false;}" class="button01"/>
           <input  type="submit" value="返回"  onClick="setMethod('goback');"  class="button01"/>
        </s:if>   
        </div>
</s:form>
<script type="text/javascript">
function validate(){
		 return false;
}
onloadClick();
</script>
</body>
</html>
