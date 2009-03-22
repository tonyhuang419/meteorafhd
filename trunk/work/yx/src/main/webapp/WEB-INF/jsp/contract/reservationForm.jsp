<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<html>
<head>

<title>结算转决算搜索</title>

<script type="text/javascript">

	function doSubmit(){
	
	//<!--document.form1.action="contract/searchContractList.action";-->
	    document.form1.submit();
	  
	}
	function clientChange(selObj){
		document.getElementById('textname').value = selObj.options[selObj.selectedIndex].text;
		document.getElementById("name").value = selObj.value;
	}
	function selectedClient(clientObj){
		document.getElementById("textname").value = clientObj.clientName;
		document.getElementById("name").value = clientObj.clientId;
	}
	function cleanrCus(){
	   document.forms(0).textname.value="";
	   document.forms(0).name.value="";
	}
</script>
<style type="text/css">
body {
	background-color: #FFFFFF;
}
</style>
</head>
<body leftmargin="0">
<s:form target="content" name="form1" action="searchReservationReturn" theme="simple">
<s:hidden name="resetCondition" value="true"></s:hidden>
<table width="100%" class="bg_table02">
  <tr>
    <td colspan="2" align="right" class="bg_table01"  height="1"></td>
  </tr>
  <tr>
    <td width="30%"><div align="right">合同名称：</div></td>
    <td width="70%">
    <div align="left">
        <input name="contractName" type="text" value="" id="textname" style="width:130px;height:21px;font-size:10pt;"/>
      </div>
      
      </td>
  </tr>
  <tr>
    <td><div align="right">合同号：</div></td>
    <td><div align="left">
        <input name="contractNo" type="text" value="" id="textNo" style="width:130px;height:21px;font-size:10pt;"/>
      </div></td>
  </tr>
  
  <tr>
    <td><div align="right">合同类型：</div></td>
    <td><div align="left">
			<s:select name="contractType"  list="typeManageService.getYXTypeManage(1020)"   listKey="typeSmall" listValue="typeName"  
             headerKey="" headerValue="--全部--"   ></s:select> 
      </div></td>
  </tr>
  <tr class="bg_table04">
    <td colspan="2">
      <div align="center">
        <input type="button" name="button2" id="button2" value="查询" class="button01"  onClick="doSubmit();"/>
        </div></td>
  </tr>
</table>
</s:form>
</body>
</html>
