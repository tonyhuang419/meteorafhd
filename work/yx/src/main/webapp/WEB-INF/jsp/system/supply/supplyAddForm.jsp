<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<html>
<head>
<title>新增供应商</title>
  <script language="javascript">
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}
</script>
</head>
<body leftmargin="0">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<s:form action="supply" theme="simple">
<s:hidden name="method" value="saveExployee" />
<s:hidden name="cc.id"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr> 
		<td valign="top" > 
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
			  <td height="3" align="left" >当前页面:基础管理->供应商新增</td>
		  </tr>
			<tr><td class="bg_table01" height="1"><img src="../../images/temp.gif" width="1" height="3"></td>
			</tr>
		</table>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr align="center">
			  <td class="bg_table02" align="right"><font color="red">* </font>供应商名称：</td>
			  <td class="bg_table02" align="left"><input type="text" name="cc.supplierName" size="20" /></td>
			  <td class="bg_table02" align="right"><font color="red">* </font>供应商代码：</td>
			  <td class="bg_table02" align="left"><input type="text" id="orgNum" name="cc.supplierCode" size="20" onchange="checkNum(this);"/></td>
		  </tr>
			<tr align="center">
			  <td class="bg_table02" align="right">供应商开票名称：</td>
			  <td class="bg_table02" align="left"><input type="text" name="cc.nameOfInovice" size="20" /></td>
			  <td class="bg_table02" align="right">开户银行：</td>
			  <td class="bg_table02" align="left"><input type="text" name="cc.billBank" size="20" /></td>
		  </tr>

			<tr align="center">
              <td class="bg_table02" align="right"><font color="red">* </font>开户帐号：</td>
			  <td class="bg_table02" align="left"><input type="text" name="cc.billAccount" size="20" /></td>
			  <td class="bg_table02" align="right">税号：</td>
			  <td class="bg_table02" align="left"><input type="text" name="cc.dutyParagraph" size="20" /></td>           
		  </tr>

			<tr align="center">
    			<td class="bg_table02" width="15%" align="right">开票地址：</td>
				<td class="bg_table02" width="35%" align="left"><input type="text" name="cc.addressOfInovice" size="20" /></td>   
    			<td class="bg_table02" align="right"><font color="red">* </font>开票电话：</td>
    			<td class="bg_table02" align="left"><input type="text" name="cc.phoneOfInovice" size="20" /></td>   
			</tr>
			<tr align="center">
             
			  <td class="bg_table02" align="right"><LABEL for="client_cc_areaID">供货商地域 ：</td>
			  <td class="bg_table02" align="left"><s:select name="cc.eareCode" list="supplyArea" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
				 </s:select>              </td>
				  <td class="bg_table02" align="right"><LABEL for="client_cc_areaID">供应商类别 ：</td>
			  <td class="bg_table02" align="left"><s:select name="cc.supplierType" list="supplierTypeList" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
				 </s:select>              </td>
		  </tr>
			<tr class="bg_table03" align="center" style="height:42px">
				<td colspan="4">
				<Table style="width:0%;100%">
					<tfoot class="bg_table03" >
						<tr>
							<td align="right" colspan="2">
                                <div align="center">
                                	<input class="button01" type="button" name="Input" value="保    存"  
                                	onclick="check();" />
								</td>                             
                         
					    <td width="43" colspan="2">		<input class="button01" type="button" value="返    回" onClick="javascript:location.href='yx/system/supplyQuery.action?resetCondition=true'"></td>		      </tr>
					</tfoot>
				</Table>				</td>
			</tr>
		</table>
	  </td>
	</tr>
</table>
</s:form>
<script language="javascript" src="<s:url value="/commons/scripts/checkLength.js"/>"></script>
<script>
function check(){
	
	if(!validate()){
	document.forms(0).submit();
	}
}
function validate()
{
	var ev = new Validator();
    with(supply){
		ev.test("notblank", "供应商不能为空", $('cc.supplierName').value);
		ev.test("notblank", "供应商代码不能为空", $('cc.supplierCode').value);
		ev.test("notblank", "开票电话不能为空", $('cc.phoneOfInovice').value);
		ev.test("notblank","开户帐号不能为空",$('cc.billAccount').value);
	}
	if (ev.size() > 0) {
		ev.writeErrors(errorsFrame, "errorsFrame");
		return true;
	}
}

function checkNum(obj){
	var jsonRequest = new Request.JSON({async:false,url:'/yx/jsonData.action?method=uniqueSupplyNum&orgNum='+obj.value, onComplete: function(jsonObj){
		if(jsonObj!=null && jsonObj.jsonData !=null ){
			if( !parseInt(jsonObj.jsonData)){
				alert("该供应商代码已存在");	
				$('cc.supplierCode').value = "";
			}
		}
	}}).get({randerNum:Math.random()});	
}
	
</script>
</body>
</html>
