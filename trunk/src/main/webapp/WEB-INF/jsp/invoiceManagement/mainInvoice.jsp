<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>开票管理主页面</title>

<style type="text/css">
<!--
.navPoint {
	COLOR: blank;
	CURSOR: hand;
	FONT-FAMILY: Webdings;
	FONT-SIZE: 9pt
}
-->
</style>
<script language="javascript">
function showlistiframe(obj){

	if(document.getElementById(obj).style.display  == "block"){
		document.getElementById(obj).style.display = "none";
		document.getElementById("sign").innerHTML = "4";
	}
	else{
		document.getElementById(obj).style.display = "block";
		document.getElementById("sign").innerHTML="3";
	}	
}
</script>
</head>
<style>
	html { overflow-x:hidden;overflow-y:hidden;  }
</style>
<body style="margin:0px;" scroll=no>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="250" id="td1" style="display:block;"><iframe src="/yx/invoice/queryInvoice.action" scrolling="no" width="100%" id="leftFrame" name="leftFrame" frameborder="0" height="100%" style="display:block"></iframe></td>
    <td width="10"  onClick="showlistiframe('td1');" ><table height="100%" border="0" cellpadding="0" cellspacing="0';"
onMouseOver="this.bgColor='#BBBBFF'; "  onMouseOut="this.bgColor='lightblue';" bgcolor="lightblue"> 
        <tr>
          <td id="sign" width="10" class="navPoint"> 3 </td>
        </tr>
      </table></td>
    <td width="*"><iframe scrolling="yes" 
    src="/yx/invoice/createInvoice.action?method=findInvoiceApplications" width="100%" id="rightFrame" scrolling="yes" frameborder="0" height="100%" name="rightFrame"></iframe></td>
  </tr>
</table>
</body>
</html>
