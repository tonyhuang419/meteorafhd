<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.baoz.yx.tools.ContractStateTool" %>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>合同确认</title>
<link href="/yx/commons/styles/foramlContractStyles/ka_f.css" rel="stylesheet" type="text/css">
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/formal_con_manage.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script language="javascript">
<!--

var strid=0;
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
function openUrpl(param){
    
     
	window.open('/yx/contract/searchContractOkList.action?method=changeTrueAndJC&conMainId='+param,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=170,width=300');
}
function selAll(){   
     //checkItem為checkbox的集合   
    var checkItem = document.getElementsByName("conMainId");   
    for(var i=0;i<checkItem.length;i++){   
        checkItem[i].checked=true;      
     }   
 } 
function unselAll(){   
     //checkItem為checkbox的集合   
     var checkItem = document.getElementsByName("conMainId");   
     for(var i=0;i<checkItem.length;i++){   
         checkItem[i].checked=false;   
     }   
} 
function checkSelAll(){
     var checkselAll = document.getElementsByName("checksel");
     if(checkselAll[0].checked){
        selAll();
     }else{
         unselAll();  
     }
}
function checkCheckBox(){   
     //checkItem為checkbox的集合   
    var checkItem = document.getElementsByName("conMainId");   
    for(var i=0;i<checkItem.length;i++){ 
       if(checkItem[i].checked==true) { 
             return true;
        }     
     }
     return false;   
 } 
 
 function checkGC(){
     var flag=checkCheckBox();

if(flag==false){
      alert("请选择要改变状态的项");
      return;
}
     var strg="";
     var strid=0;
     var count=0;
     var imp="";
     var flag
     var checkItem = document.getElementsByName("conMainId");
     for(var i=0;i<checkItem.length;i++){ 
       if(checkItem[i].checked==true) {
             count++; 
             strg="thj"+i;
             strchj="thid"+i;
             impiu="imp"+i;
             strid=document.getElementById(strchj).value;
             flag=document.getElementById(strg).value;
             imp=document.getElementById(impiu).value;
            }     
     }
     if(count==1){
              var cthje=window.confirm("您确定要转正式合同吗？");
              if(cthje==true){
              if(flag==1){
                 
                  document.forms(0).action="/yx/contract/searchContractOkList.action?method=changeTrueState";
                  document.forms(0).submit();
              }
              if(flag==2){
                 if(imp){
                    document.forms(0).action="/yx/contract/searchContractOkList.action?method=changeTrueAndJC";
                    document.forms(0).submit();
                   
                 }else{
                        openUrpl(strid);
                 }
              }
              }
               
     }else{
          alert("转正式合同时只能单选");
     }
     return true;
 }
 
	function chageState(convar){
	
		var flag=checkCheckBox();
	
		if(flag==false){
		      alert("请选择要改变状态的项");
		      return;
		}
	
	
		if(convar.value=='转预合同'){
		       var chegth=window.confirm("您确定转预合同吗？");
		       if(chegth==true){
			      document.getElementById("states").value='预合同';
			      document.forms(0).action="/yx/contract/searchContractOkList.action?method=changeOrderState";
			      document.forms(0).submit();
			    }
		}
		else{
		     var chefrgth=window.confirm("你确定要取消确认该合同吗?");
		     if(chefrgth==true){
		     document.getElementById("states").value='草稿状态';
		     document.forms(0).action="/yx/contract/searchContractOkList.action?method=cancelConfirm";
		     document.forms(0).submit();
		     }
		}
	
	}
	//确认退回理由
	function confirmCancel(){
		var checkItem = document.getElementsByName("conMainId");   
	    var checkStr="";
	    var flag=0;
	    for(var i=0;i<checkItem.length;i++){ 
	        if(checkItem[i].checked){
	          checkStr=checkStr+","+checkItem[i].value;
	          flag++;
	        }
	    }
	    if(flag==1){
			var chegth=window.confirm("您确定退回吗？");
			if(chegth==true){
			      openWin("searchContractOkList.action?method=showConfirmCancel&"+$(searchContractOkList).toQueryString(),400,300);
			}
		}else{
			alert("请选择一个合同！");
		}
	}
	
	//转预合同
	function yuhetong()
	{ 
		var checkItem = document.getElementsByName("conMainId");   
	    var checkStr="";
	    var flag=0;
	    for(var i=0;i<checkItem.length;i++){ 
	        if(checkItem[i].checked){
	          checkStr=checkStr+","+checkItem[i].value;
	          flag++;
	        }
	    }
	    if(flag==1){
	    	if(!isExist("searchContractOkList.action","selectExist",checkStr.substring(1))){
			    var chegth=window.confirm("您确定转预合同吗？");
			    if(chegth==true){
				      document.getElementById("states").value='预合同';
				      document.forms(0).action="/yx/contract/searchContractOkList.action?method=toPrepare";
				      document.forms(0).submit();
				}
			}
			else{
				alert("该合同已经是预合同!")
			}
		}else{
			alert("请选择一个合同！");
		}
	} 
	
	function isExist(url, method, name){
		var flag;
    	var myRequest = new Request({url:url,async:false,method:'get'});
	   	myRequest.addEvent("onSuccess",function(responseText, responseXML){
			  if(responseText =='1'){
			    flag = true;
			  } 
			  else{
			  	flag = false;
			  }
	    });
	   myRequest.send("method="+method+"&conMainid="+name+"&randomNum="+Math.random());
	   return flag;
	   
	}

-->
</script>
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.STYLE1 {
	font-size: 16px
}

-->
</style>
</head>
<body leftmargin="0">

<s:form action="searchContractOkList" target="content" theme="simple">

<input type="hidden" name="groupId" value="<s:property value="groupId"/>"/>
<input type="hidden" name="expId" value="<s:property value="expId"/>"/>
<input type="hidden" name="customerId" value="<s:property value="customerId"/>"/>
<input type="hidden" name="conType" value="<s:property value="conType"/>"/>
<input type="hidden" name="minMoney" value="<s:property value="minMoney"/>"/>
<input type="hidden" name="maxMoney" value="<s:property value="maxMoney"/>"/>
<input type="hidden" name="minConSignDate" value="<s:property value="minConSignDate"/>"/>
<input type="hidden" name="maxConSignDate" value="<s:property value="maxConSignDate"/>"/>
<input type="hidden" name="conState" value="<s:property value="conState"/>"/>

<input type="hidden" name="states"/>
<div align="left">
  <p  style="color:#000000">当前页面：合同管理 -> 合同确认(项目类)</p>
</div>

<s:iterator value="messages" id="mes">
  <div align="left"><font color="red"><s:property value="#mes"/></font><br></div>
</s:iterator>
<s:hidden name="XMJC" value="1"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td  height="3" colspan="2" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="1"></td>
  </tr>
  
  <tr class="bg_table02">
    <td width="73%" align="right" class="bg_table03">
      
      <div align="center">
  		<input type="button" name="save2" value="退  回" onClick="confirmCancel()" class="button01">
  		<input type="button" name="save" value="转预合同" onClick="yuhetong()" class="button01">
        <input type="button" name="save" value="转正式合同" onclick="checkGC();" class="button02"> 
      </div>
      <div align="left"></div>      <div align="left"></div>      <div align="left"></div></td>
    <td width="27%" align="right" class="bg_table03"><div align="left">&nbsp;&nbsp;
      <input type="button" name="save3" value="取消确认" onClick="chageState(this);" class="button01">
    </div></td>
  </tr>
  <tr>
    <td colspan="2" align="center"><table width="100%" border="1" align="center" id="sureCon" bordercolor="#808080" style=" border-collapse: collapse;">
        <tr>
          <td width="10"  nowrap="nowrap" align="center" class="bg_table01"><div align="center"> <input type="checkbox" name="checksel" onclick="checkSelAll();"></div></td>
<%--          <td width="117"  align="center" class="bg_table01"><div align="center">合同号</div></td>--%>
          <td width="130" nowrap="nowrap" align="center" class="bg_table01" ><div align="center">合同名称</div></td>
          <td width="100" nowrap="nowrap" align="center" class="bg_table01" ><div align="center">客户名称</div></td>
          <td width="60"  nowrap="nowrap" align="center" class="bg_table01" ><div align="center">销售员</div></td>
          <td width="100" nowrap="nowrap" align="center" class="bg_table01" ><div align="center">合同类型</div></td>
          <td width="80"  nowrap="nowrap" align="center" class="bg_table01" ><div align="center">合同性质</div></td>
          <td width="80"  nowrap="nowrap" align="center" class="bg_table01"  ><div align="center">合同金额</div></td>
          <td width="70"  nowrap="nowrap" align="center" class="bg_table01"  ><div align="center">签订日期</div></td>       
          <td nowrap="nowrap" align="center" class="bg_table01" ><div align="center">合同状态</div></td>
          <td nowrap="nowrap" align="center" class="bg_table01" ><div align="center">预合同</div></td>
        </tr>
        <s:iterator value="info.result" id="conclitemp" status="status">
        <input type="hidden" name="imp<s:property value="#status.index"/>" value="<s:property value="#conclitemp[0].importFromFile"/>"/>
        <input type="hidden" name="conStates" value="<s:property value="#conclitemp[0].conState"/>"/>
        <input type="hidden" name="thj<s:property value="#status.index"/>" value="<s:property value="#conclitemp[0].ContractType"/>"/>
        <input type="hidden" name="thid<s:property value="#status.index"/>" value="<s:property value="#conclitemp[0].conMainInfoSid"/>"/>
        <tr  onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
          <td width="20"  align="center">            
            <div align="center">
              <input type="checkbox" name="conMainId" value="<s:property value="#conclitemp[0].conMainInfoSid"/>">          
            </div></td>
<%--          <td   width="117"  align="center"><div align="center"><s:property value="#conclitemp[0].conId"/></div></td>--%>
          <td onClick="openConSure(this);" ><div align="left"><s:property value="#conclitemp[0].conName"/></div></td>
          <td onClick="openConSure(this);" ><div align="left"><s:property value="#conclitemp[2].name"/></div></td>
          <td onClick="openConSure(this);" ><div align="left"><s:property value="#conclitemp[1].name"/></div></td>
          <td onClick="openConSure(this);" ><div align="left">
          	<s:property value="typeManageService.getYXTypeManage(1020,#conclitemp[0].ContractType).typeName" />
          </div></td>
          <td  onClick="openConSure(this);" ><div align="left">
               <s:iterator value="contractTypeList" id="typeManager">
                    <s:if test="#typeManager.typeSmall==#conclitemp[0].conType">            
                       <s:property value="#typeManager.typeName"/>
            </s:if>
           
           </s:iterator>
          </div></td>
          <td onClick="openConSure(this);" ><div align="right"><s:property value="#conclitemp[0].conTaxTamount"/></div></td>
          <td onClick="openConSure(this);" ><div align="center"><s:property value="#conclitemp[0].conSignDate"/></div></td>
          
          <td onClick="openConSure(this);" ><div align="left">
          <s:property value="@com.baoz.yx.tools.ContractStateTool@getContractStateSnToName(#conclitemp[0].conState)" />
          </div></td>
          <td onClick="openConSure(this);" >
          <s:if test="#conclitemp[0].beforeHandConState == false">
          	否
          </s:if>
          <s:if test="#conclitemp[0].beforeHandConState == true">
          	是
          </s:if>
          </td>
        </tr>
        </s:iterator>
        
    </table></TD>
  </TR>
  <TR>
    <TD height=20 colspan="2" class="bg_table02"><DIV align=center>
        <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
      </DIV></TD>
  </TR>

</TABLE>
</s:form>
<script type="text/javascript">
if('<s:property value="msgalert"/>'!=""){
   alert('<s:property value="msgalert"/>');
}
</script>
</body>
</html>
