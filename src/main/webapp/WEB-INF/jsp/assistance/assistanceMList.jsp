<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style>
table#checkInfo tr:hover {background: lightblue; color: blue;}
</style>
<title>外协合同管理</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');
}
function displayXX(i , assistanceId){
	var info = document.getElementById('dispalyInfo'+i);
	getApplyHistory(info,assistanceId);
	if(info.style.visibility == "hidden"){
		info.style.visibility = "visible";
		info.style.position = "static";
	}
	else{
		info.style.visibility = "hidden";
		info.style.position = "absolute";
	}
}
function getApplyHistory(contentDiv,assistanceId){
	var myHTMLRequest = new Request.HTML({url:'../assistance/assistance.action?method=showApplyHistory&assistanceContractId='+assistanceId,update:contentDiv}).get({'assistanceId': assistanceId,randnum:Math.random()});
}
function aaa() 
{ 
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
        }
    }
   if(j==0){
        alert("您还没有选择提交确认的对象！");
   }else{
   		location.href="../assistance/assistance.action?method=verifyState&stateId="+checkStr.substring(1); 
   }
}
function bbb(){
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(j==1)
      {  
         var url="../assistance/assistance.action?method=enterUpdate&ids="+checkStr.substring(1);
         window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=yes,scrollbars=1,resizable=1,height=600,width=800');
      }
   if(j==0){
        alert("您还没有选择修改的对象！");
   }
   if(j>1){
    
     alert("不能选择多个修改对象！");
   }
      }
      
      function ccc(){
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
   
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
        }
    }
    if(j==1)
      {  
       var url="../assistance/assistance.action?method=enterApply&ids="+checkStr.substring(1);
       window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
               
      }
   if(j==0){
        alert("您还没有选择付款申请的对象！");
   }
   if(j>1){
    
     alert("不能选择多个付款申请对象！");
   }
      }
function delChose(){
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
    var j=0;
    for(var i=0;i<checkArr.length;i++){
        if(checkArr[i].checked){
             j++;    
             checkStr=checkStr+","+checkArr[i].value;
        }
    }
   if(j==0){
        alert("您还没有选择删除的对象！");
   }else{
   		location.href="../assistance/assistance.action?method=del&ids="+checkStr.substring(1); 
   }
}   
</script>
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
</head>
<body>
<s:form theme="simple" action="">
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td align="center"><table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
      <tr>
        <td align="left" >当前页面:外协管理->外协合同管理</td>
      </tr>
      <tr>
        <td align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
      </tr>
      <tr>
        <td align="left" class="bg_table03"><div align="center">
          <input type="button" class="button02" value="修    改" onClick="javascript:bbb()">
          <input type="button" class="button02" value="确认提交" onClick="javaScript:aaa()">
          <input type="button" class="button02" value="付款申请" onClick="javaScript:ccc()">
          <input type="button" class="button02" value="删    除" onClick="javascript:delChose()">
        </div></td>
        </tr>
    </table>
<table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="checkInfo">
          <tr align="center">
            <td width="6%" class="bg_table01">选择</td>
            <td width="9%" class="bg_table01">外协合同号</td>
            <td width="17%" class="bg_table01">外协合同名称</td>
            <td width="20%" class="bg_table01">外协供应商</td>
            <td width="11%" class="bg_table01">签订日期</td>
            <td width="11%" nowrap class="bg_table01">预计结束日期</td>
            <td width="10%" class="bg_table01">合同金额</td>
            <td width="9%" nowrap class="bg_table01">已支付金额</td>
            <td width="7%" class="bg_table01">余额</td>
          </tr>
          <s:iterator id="ac" value="info.result" status="status">
	    <tr align="center"  onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='#FFFFFF';">
						<td><input type="checkbox" name="ids" value="<s:property value="#ac[0].id"/>" /></td>
						<td onClick="displayXX(<s:property value="#status.index" />,<s:property value="#ac[0].id" />);"><s:property value="#ac[0].id"/></td>
						<td onClick="displayXX(<s:property value="#status.index" />,<s:property value="#ac[0].id" />);"><s:property value="#ac[0].assistanceName" /></td>
						<td onClick="displayXX(<s:property value="#status.index" />,<s:property value="#ac[0].id" />);"><s:property value="#ac[1]"/></td>
						<td onClick="displayXX(<s:property value="#status.index" />,<s:property value="#ac[0].id" />);"><s:date name="#ac[0].contractDate" format="yyyy-MM-dd" /></td>
						<td onClick="displayXX(<s:property value="#status.index" />,<s:property value="#ac[0].id" />);"><s:date name="#ac[0].endDate" format="yyyy-MM-dd" /></td>
						<td onClick="displayXX(<s:property value="#status.index" />,<s:property value="#ac[0].id" />);"><s:property value="#ac[0].contractMoney" /></td>
						<td onClick="displayXX(<s:property value="#status.index" />,<s:property value="#ac[0].id" />);"><s:property value="#ac[0].ticketMoney" /></td>
						<td onClick="displayXX(<s:property value="#status.index" />,<s:property value="#ac[0].id" />);"><s:property value="#ac[0].contractMoney - #ac[0].ticketMoney" /></td>
					</tr>
					 <tr>
            <td colspan="11"><div id ="dispalyInfo<s:property value="#status.index" />" align="left"  style="visibility:hidden; position:absolute">

            </div></td>
          </tr>
        </s:iterator>
        </table>
<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
			</td>
		</tr>  
</table>
<p>&nbsp;</p>
</s:form>
</body>
</html>
