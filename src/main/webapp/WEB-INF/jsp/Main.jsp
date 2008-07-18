<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>宝信营销综合管理系统</title>
<style>
/* for all */
a:link,a:visited {
	color: #ffffff;
	text-decoration: none;
}

a:hover,a:active {
	color: #fff;
}

* {
	margin: 0;
	padding: 0;
}

li {
	list-style: none;
	vertical-align: middle;
}

ol li {
	list-style: inside decimal;
	padding-left: 6px;
}

img {
	border: 0;
}

.FL {
	float: left;
}

.FR {
	float: right;
}

.Hide {
	border-top: 1px solid transparent !important;
	margin-top: -1px !important;
	border-top: 0;
	margin-top: 0;
	clear: both;
	visibility: hidden;
}

/*结构布局*/
body {
	font: 12px/ 21px "宋体";
	color: #000;
	text-align: center;
}

.Wp {
	text-align: left;
	margin: 0 auto;
	width: 880px;
}

#Head {
	height: auto;
	width: 100%;
}

/*公用*/
#Head .Wp {
	height: auto;
	width: 100%;
}

#Head h1 {
	float: left;
}

#nav {
	float: right
}

#nav li {
	float: left;
	background:
		url('<s:url value="/commons/images/main_menu_blue_bk.gif"/>') repeat-x;
		height: auto;
}

#nav li a {
	width: 78px;
	height: auto;
	display: block;
	font: bold 14px/28px "宋体";
	text-align: center;
}

#nav a:link,#nav a:visited {
	color: #104E8B;
	text-decoration: none
}

#nav a:hover,#nav a:active,#hover {
	color: #fff;
	font: bold 14px/28px "宋体";
	background: url(/commons/images/main_menu_blue_bk.gif) repeat-x;
	border-width: 100%;
}

#subNav li a {
	text-align: left;
}

/*一级菜单*/
#down1 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down2 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down3 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down4 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down5 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down6 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down7 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down8 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down9 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down10 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down11 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down12 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down13 {
	position: absolute;
	middle: 33px;
	margin: 0;
	padding: 0;
}

#down1 li,#down2 li,#down3 li,#down4 li,#down5 li,#down6 li,#down7 li,#down8 li,#down9 li,#down10 li,#down11 li,#down12 li,#down13 li
	{
	float: none
}

#down1 li a,#down2 li a,#down3 li a,#down4 li a,#down5 li a,#down6 li a,#down7 li a,#down8 li a,#down9 li a,#down10 li a,#down11 li a,#down12 li a,#down13 li a
	{
	width: 120px;
	height: 12px;
	border-bottom: 1px dotted #818488;
	display: block;
	font: 12px/ 24px "微软雅黑", "宋体";
	color: #FFF;
}

#down1 a:link,#down1 a:visited,#down2 a:link,#down2 a:visited,#down3 a:link,#down3 a:visited,#down4 a:link,#down4 a:visited,#down5 a:link,#down5 a:visited,#down6 a:link,#down6 a:visited,#down7 a:link,#down7 a:visited,#down8 a:link,#down8 a:visited,#down9 a:link,#down9 a:visited,#down10 a:link,#down10 a:visited,#down11 a:link,#down11 a:visited,#down12 a:link,#down12 a:visited,#down13 a:link,#down13 a:visited
	{
	text-decoration: none;
	background: #3f4249
}

#down1 a:hover,#down1 a:active,#down2 a:hover,#down2 a:active,#down3 a:hover,#down3 a:active,#down4 a:hover,#down4 a:active,#down5 a:hover,#down5 a:active,#down6 a:hover,#down6 a:active,#down7 a:hover,#down7 a:active,#down8 a:hover,#down8 a:active,#down9 a:hover,#down9 a:active,#down10 a:active,#down10 a:hover,#down11 a:active,#down11 a:hover,#down12 a:active,#down12 a:hover,#down13 a:active,#down13 a:hover
	{
	width: 120px;
	height: 12px;
	font: bold 12px/ 24px "微软雅黑", "宋体";
	background: url(commons/images/navbg2.jpg) repeat-x;
	color: #FF3030
}

.STYLE1 {
	color: #FFFFFF
}
</style>
<script src="<s:url value="/commons/scripts/main_menu.js"/>" type="text/javascript"></script>
<script language="javascript">
// JavaScript Document
var flag=true;
function $(s) {
  return document.getElementById(s)?document.getElementById(s):s;
}
function findPosX(obj)
{
  var curleft = 0;
  if (obj.offsetParent)
  {
    while (obj.offsetParent)
    {
      curleft += obj.offsetLeft
      obj = obj.offsetParent;
    }
  }
  else if (obj.x)
    curleft += obj.x;
  return curleft;
}
function findPosY(obj)
{
  var curtop = 0;
  if (obj.offsetParent)
  {
    while (obj.offsetParent)
    {
      curtop += obj.offsetTop
      obj = obj.offsetParent;
    }
  }
  else if (obj.y)
    curtop += obj.y;
  return curtop;
}

var currPcnNav=null;
var flag = null;

function pcnNav(o){
	var offLeft = findPosX(o);
	var offTop = findPosY(o);
	var arrNav=document.getElementById("subNav").getElementsByTagName("ul");
	if (currPcnNav!=null) {
	   hidPcnNav(currPcnNav);
	}
	
	if (o.getAttribute("urn")!="null") {
		shwPcnNav($(o.getAttribute("urn")),offLeft);
		currPcnNav=$(o.getAttribute("urn"));
		document.body.onmouseup=function(){
			hidPcnNav(currPcnNav);
		}
	} else {
	     currPcnNav?hidPcnNav(currPcnNav):"";
	}
		
}

function flagToHide(){
	if(flag){
		hidPcnNav(currPcnNav);
	}
}

function shwPcnNav(o,n){
	o.style.display="";
	o.style.left=n+"px";
	var coverFrame = document.getElementById(o.id+"Frame");
	if(coverFrame!=null){
		coverFrame.style.height= o.clientHeight||o.offsetHeight;
	    coverFrame.style.width= o.clientWidth||o.offsetWidth;
	}
}
function hidPcnNav(o){
	o.style.display="none";
	document.body.onmouseup=null;
}
function pcnNavInit(){
	var arrNav=document.getElementById("nav").getElementsByTagName("a");
	for (var i=0;i<arrNav.length;i++ ) {
		arrNav[i].onmouseover=function(){
		pcnNav(this);
		}
		
	}	
}


window.onload=pcnNavInit;
</script>
</head>
<BODY style="BACKGROUND-IMAGE: url('<s:url value="/commons/images/main_bk.jpg"/>')" onfocus=top.main_view.focus() leftMargin=0 topMargin=0 scroll=no>
<BGSOUND id=auIEContainer>
<INPUT id=help_file_name type=hidden name=help_file_name>
<INPUT id=help_code type=hidden name=help_code>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TBODY>
		<TR>
			<TD id=main_menu_top style="DISPLAY: block; BACKGROUND-IMAGE: url('<s:url value="/commons/images/main_bg.gif"/>'); HEIGHT: 106px"
				vAlign=bottom name="main_menu_top">
			<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
				<TBODY>
					<TR>
						<TD vAlign=bottom align=right>
						<TABLE width="100%" border=0>
							<TBODY>
								<TR>
									<TD width="654"></TD>
									<TD><span class="STYLE1"><font class=whitefont style1></font> <FONT class=whitefont><s:property value="username"/></FONT> </span></TD>
									<TD width="129" align=right vAlign=center id=logon_time name="logon_time"><span class="STYLE1"><FONT class=whitefont></FONT> <SCRIPT
										language=JavaScript>
                                        //window.setInterval("get_sys_time()",1000);
                                	</SCRIPT> </span></TD>
								</TR>
							</TBODY>
						</TABLE>
						</TD>
					</TR>
					<TR>
						<TD align=right vAlign=bottom nowrap>
						<div id="Head">
						<div class="Wp">
						<ul id="nav">
							<li><a urn="down0" href="/yx/mainView.action" target=main_view>首页</a></li>
							<s:if test="userService.hasAuthority('menu_sellbefore')"><li><a urn="down1" href="#">售前合同</a></li></s:if>
							<s:if test="userService.hasAuthority('menu_project_econ')"><li><a urn="down2" href="#">工程经济</a></li></s:if>
							<s:if test="userService.hasAuthority('menu_contractor')"><li><a urn="down3" href="#">合同管理</a></li></s:if>
							<s:if test="userService.hasAuthority('menu_bill')"><li><a urn="down4" href="#">开票管理</a></li></s:if>
							<s:if test="userService.hasAuthority('menu_harvest')"><li><a urn="down5" href="#">收款管理</a></li></s:if>
							<s:if test="userService.hasAuthority('menu_buy')"><li><a urn="down6" href="#">申购采购</a></li></s:if>
							<s:if test="userService.hasAuthority('menu_outsourcing')"><li><a urn="down7" href="#">外协管理</a></li></s:if>
							<s:if test="userService.hasAuthority('menu_stat')"><li style="display:none"><a urn="down8" href="#">统计查询</a></li></s:if>
							<s:if test="userService.hasAuthority('menu_file_manage')"><li><a urn="down9" href="#">文件管理</a></li></s:if>
							<s:if test="userService.hasAuthority('menu_base_manage')"><li><a urn="down10" href="#">基础管理</a></li></s:if>
							<s:if test="userService.hasAuthority('menu_authority')"><li><a urn="down11" href="#">权限管理</a></li></s:if>
							<li><a urn="down12" href="#">个人信息</a></li>
							<li><a urn="down13" href="logout.action">退出</a></li>
						</ul>
						</div>
						</div>
						<div id="subNav">
						<ul id='down0' style="display: none;">
						</ul>
						<ul id='down1' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">
							<iframe id="down1Frame" style="position:absolute;z-index:-1;width:100%;height:30%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<s:if test="userService.hasAuthority('contractBeforeSellQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="sellbefore/contractBeforeSellQuery.action" target=main_view>&nbsp;&nbsp;售前合同新建</a></li></s:if>
							<s:if test="userService.hasAuthority('sellBeforeMainQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="sellbefore/sellBeforeMainQuery.action" target=main_view>&nbsp;&nbsp;售前合同管理</a></li></s:if>
						</ul>
						<ul id='down2' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">
							<iframe id="down2Frame" style="position:absolute;z-index:-1;width:100%;height:10%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<s:if test="userService.hasAuthority('economyVerifyL.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="programEconomy/economyVerifyL.action" target=main_view>&nbsp;&nbsp;工程经济确认</a></li></s:if>
							<s:if test="userService.hasAuthority('economyManageM.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="programEconomy/economyManageM.action" target=main_view>&nbsp;&nbsp;工程经济管理</a></li></s:if>
							<s:if test="userService.hasAuthority('economyLoginM.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="programEconomy/economyLoginM.action" target=main_view>&nbsp;&nbsp;录入管理</a></li></s:if>
						</ul>
						<ul id='down3' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">
							<iframe id="down3Frame" style="position:absolute;z-index:-1;width:100%;height:83.33%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<s:if test="userService.hasAuthority('contract.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="contract/contract.action" target=main_view>&nbsp;&nbsp;合同新建</a></li></s:if>
							<s:if test="userService.hasAuthority('contractHome.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="contract/contractHome.action" target=main_view>&nbsp;&nbsp;草稿合同管理</a></li></s:if>
							<s:if test="userService.hasAuthority('contractOK.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="contract/contractOK.action" target=main_view>&nbsp;&nbsp;合同确认</a></li></s:if>
							<s:if test="userService.hasAuthority('contractItemWriteHome.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="contract/contractItemWriteHome.action" target=main_view>&nbsp;&nbsp;录入项目号</a></li></s:if>
							<s:if test="userService.hasAuthority('formalContractManageFrame.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="contract/formalContractManage/formalContractManageFrame.action" target=main_view>&nbsp;&nbsp;正式合同管理</a></li></s:if>
							<s:if test="userService.hasAuthority('contractmanager.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="contract/contractmanager.action" target=main_view>&nbsp;&nbsp;合同变更确认</a></li></s:if>
							<s:if test="userService.hasAuthority('iframeQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="contract/iframeQuery.action " target=main_view>&nbsp;&nbsp;合同工程经济</a></li></s:if>
							<s:if test="userService.hasAuthority('contractProjectInput.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="contract/contractProject/contractProjectInput.action?resetCondition=true&confirmOrAll=0" target=main_view>&nbsp;&nbsp;合同成本录入</a></li></s:if>
							<s:if test="userService.hasAuthority('contractProject.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="contract/contractProject/contractProject.action?resetCondition=true&confirmOrAll=0" target=main_view>&nbsp;&nbsp;合同成本确认</a></li></s:if>
						</ul>
						<ul id='down4' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">

							<iframe id="down4Frame" style="position:absolute;z-index:-1;width:100%;height:100%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<s:if test="userService.hasAuthority('createInvoice.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="invoice/createInvoice.action" target=main_view>&nbsp;&nbsp;新建未签开票申请</a></li></s:if>
							<s:if test="userService.hasAuthority('queryInvoice.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="invoice/queryInvoice.action?method=showMainInvoice" target=main_view>&nbsp;&nbsp;开票申请管理</a></li></s:if>
							<s:if test="userService.hasAuthority('billApplyVerifyQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="billtoReceipt/billApplyVerifyQuery.action" target=main_view>&nbsp;&nbsp;开票申请确认</a></li></s:if>
							<s:if test="userService.hasAuthority('billApplyVerifyQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="billtoReceipt/relationContractQuery.action" target=main_view>&nbsp;&nbsp;未签开票关联合同</a></li></s:if>
							<s:if test="userService.hasAuthority('billReceiptQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="billtoReceipt/billReceiptQuery.action" target=main_view>&nbsp;&nbsp;开票/收据管理</a></li></s:if>
							<s:if test="userService.hasAuthority('receiptToBiellMainQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="billtoReceipt/receiptToBiellMainQuery.action" target=main_view>&nbsp;&nbsp;收据转发票</a></li></s:if>
							<s:if test="userService.hasAuthority('signMain.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="billtoReceipt/signMain.action" target=main_view>&nbsp;&nbsp;签收管理</a></li></s:if>
							<s:if test="userService.hasAuthority('signMain.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="billtoReceipt/applyBillQuery.action" target=main_view>&nbsp;&nbsp;已签开票申请</a></li></s:if>
							
							<s:if test="userService.hasAuthority('showMoonBillQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="billtoReceipt/showMoonBillQuery.action" target=main_view>&nbsp;&nbsp;月度开票计划</a></li></s:if>
						</ul>
						<ul id='down5' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">
							<iframe id="down5Frame" style="position:absolute;z-index:-1;width:100%;height:10%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<s:if test="userService.hasAuthority('harvestMeansMain.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="harvestMeansManager/harvestMeansMain.action" target=main_view>&nbsp;&nbsp;收款管理</a></li></s:if>
							<s:if test="userService.hasAuthority('moonHarvestProgram.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="harvestMeansManager/moonHarvestProgram.action" target=main_view>&nbsp;&nbsp;月度收款计划</a></li></s:if>
						</ul>

						<ul id='down6' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">
							<iframe id="down6Frame" style="position:absolute;z-index:-1;width:100%;height:10%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<s:if test="userService.hasAuthority('purchase.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="purchase/purchase.action" target=main_view>&nbsp;&nbsp;申购新建</a></li></s:if>
							<s:if test="userService.hasAuthority('purManageQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="purchase/purManageQuery.action?method=manage" target=main_view>&nbsp;&nbsp;申购采购管理</a></li></s:if>
							<s:if test="userService.hasAuthority('purManageQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="purchase/purManageQuery.action?method=confirm" target=main_view>&nbsp;&nbsp;申购确认</a></li></s:if>
						</ul>
						<ul id='down7' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">
						<iframe id="down7Frame" style="position:absolute;z-index:-1;width:100%;height:83.33%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<s:if test="userService.hasAuthority('assistance.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="assistance/assistance.action" target=main_view>&nbsp;&nbsp;外协合同新建</a></li></s:if>
							<s:if test="userService.hasAuthority('loginMain.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="assistance/loginMain.action" target=main_view>&nbsp;&nbsp;外协合同管理</a></li></s:if>
							<s:if test="userService.hasAuthority('affirmMain.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="assistance/affirmMain.action" target=main_view>&nbsp;&nbsp;外协合同确认</a></li></s:if>
							<s:if test="userService.hasAuthority('ticketMain.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="assistance/ticketMain.action" target=main_view>&nbsp;&nbsp;外协发票管理</a></li></s:if>
							<s:if test="userService.hasAuthority('applyMain.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="assistance/applyMain.action" target=main_view>&nbsp;&nbsp;外协付款申请管理</a></li></s:if>
							<s:if test="userService.hasAuthority('passApplyMain.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="assistance/passApplyMain.action" target=main_view>&nbsp;&nbsp;外协付款确认</a></li></s:if>
						</ul>
						<ul id='down8' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">

							<iframe id="down8Frame" style="position:absolute;z-index:-1;width:100%;height:83.33%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<s:if test="userService.hasAuthority('0')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="javascript:void(0)" target=main_view>&nbsp;&nbsp;售前合同统计</a></li></s:if>
							<s:if test="userService.hasAuthority('0')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="javascript:void(0)" target=main_view>&nbsp;&nbsp;开票精度统计</a></li></s:if>
							<s:if test="userService.hasAuthority('0')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="javascript:void(0)" target=main_view>&nbsp;&nbsp;收款精度统计</a></li></s:if>
							<s:if test="userService.hasAuthority('0')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="javascript:void(0)" target=main_view>&nbsp;&nbsp;开票统计</a></li></s:if>
							<s:if test="userService.hasAuthority('0')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="javascript:void(0)" target=main_view>&nbsp;&nbsp;收款统计</a></li></s:if>
							<s:if test="userService.hasAuthority('0')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="javascript:void(0)" target=main_view>&nbsp;&nbsp;开票计划变更统计</a></li></s:if>
							<s:if test="userService.hasAuthority('0')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="javascript:void(0)" target=main_view>&nbsp;&nbsp;收款计划变更统计</a></li></s:if>
							<s:if test="userService.hasAuthority('0')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="javascript:void(0)" target=main_view>&nbsp;&nbsp;正式合同统计</a></li></s:if>
							<s:if test="userService.hasAuthority('0')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="javascript:void(0)" target=main_view>&nbsp;&nbsp;自有产品签订情况</a></li></s:if>


						</ul>
						<ul id='down9' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">
							<iframe id="down9Frame" style="position:absolute;z-index:-1;width:100%;height:10%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<s:if test="userService.hasAuthority('fileUpLoadQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="fileManager/fileUpLoadQuery.action" target=main_view>&nbsp;&nbsp;文件上传</a></li></s:if>
							<s:if test="userService.hasAuthority('fileDownMain.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="fileManager/fileDownMain.action" target=main_view>&nbsp;&nbsp;文件下载</a></li></s:if>
						</ul>
						<ul id='down10' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">
							<iframe id="down10Frame" style="position:absolute;z-index:-1;width:100%;height:10%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<s:if test="userService.hasAuthority('clientMain.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="system/clientMain.action" target=main_view>&nbsp;&nbsp;客户管理</a></li></s:if>
							<s:if test="userService.hasAuthority('supplyQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="system/supplyMain.action" target=main_view>&nbsp;&nbsp;供应商管理</a></li></s:if>
							<s:if test="userService.hasAuthority('clientLinkManQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="system/clientLinkManQuery.action?resetCondition=true" target=main_view>&nbsp;&nbsp;联系人管理</a></li></s:if>
							<s:if test="userService.hasAuthority('chargemanQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="system/chargemanQuery.action?resetCondition=true" target=main_view>&nbsp;&nbsp;部门负责人管理</a></li></s:if>
							<s:if test="userService.hasAuthority('typeManageMainQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="system/typeManageMainQuery.action" target=main_view>&nbsp;&nbsp;类型管理</a></li></s:if>
							<s:if test="userService.hasAuthority('selfProductMainQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="system/selfProductMainQuery.action" target=main_view>&nbsp;&nbsp;自有产品管理</a></li></s:if>
							<s:if test="userService.hasAuthority('gongGaoQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="system/gongGaoQuery.action" target=main_view>&nbsp;&nbsp;公告管理</a></li></s:if>

						</ul>
						<ul id='down11' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">
							<iframe id="down11Frame" style="position:absolute;z-index:-1;width:100%;height:10%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<s:if test="userService.hasAuthority('showPersonnelMain.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="personnelManager/showPersonnelMain.action" target=main_view>&nbsp;&nbsp;员工管理</a></li></s:if>
							<s:if test="userService.hasAuthority('roleQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="system/manage/roleQuery.action" target=main_view>&nbsp;&nbsp;角色管理</a></li></s:if>
							<s:if test="userService.hasAuthority('organizationTreeQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="system/manage/organizationTreeQuery.action" target=main_view>&nbsp;&nbsp;用户组织树管理</a></li></s:if>
							<s:if test="userService.hasAuthority('userAuthorityQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="system/manage/userAuthorityQuery.action" target=main_view>&nbsp;&nbsp;用户权限管理</a></li></s:if>
							<s:if test="userService.hasAuthority('authorityQuery.action')"><li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="system/manage/authorityQuery.action" target=main_view>&nbsp;&nbsp;操作权限管理</a></li></s:if>

						</ul>
						<ul id='down12' style="display: none;" onMouseOut="if (currPcnNav!=null) {setTimeout('flagToHide();',100);}">
							<iframe id="down12Frame" style="position:absolute;z-index:-1;width:100%;height:10%;top:0;left:0;scrolling:no;" frameborder="0" src="about:blank"></iframe>
							<li onMouseOver="flag=false;" onMouseOut="flag=true;"><a href="personnelManager/personnelManager.action?method=alterSelfPwd" target=main_view>&nbsp;&nbsp;修改密码</a></li>
						</ul>
						<ul id='down13' style="display: none;">
						</ul>
						</div>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
		<TR>
			<TD style="BACKGROUND-IMAGE: url('<s:url value="/commons/images/pixel_bk.jpg"/>'); HEIGHT: 16px" align=right>
			<TABLE style="WIDTH: 100%" cellSpacing=0 cellPadding=0 border=0>
				<TBODY>
					<TR>
						<TD style="WIDTH: 100%" vAlign=bottom align=right>
						<TABLE cellSpacing=0 cellPadding=0 border=0>
							<TBODY>
								<TR>
									<TD><IMG id=main_menu_expand style="DISPLAY: none; CURSOR: hand" onclick="do_expand();return false;"
										src="<s:url value="/commons/images/main_menu_expand.gif"/>" border=0 name=main_menu_expand></TD>
									<TD><IMG id=main_menu_shrink style="DISPLAY: block; CURSOR: hand" onclick="do_shrink();return false;"
										src="<s:url value="/commons/images/temp.gif"/>" border=0 name=main_menu_shrink></TD>
								</TR>
							</TBODY>
						</TABLE>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>

<iframe id=main_text name=main_view src="<s:url action='stat.action'/>" width="100%" height="80%" marginwidth="0" marginheight="0" frameborder="0" />
</body>
</HTML>