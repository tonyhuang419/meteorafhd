<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>外协预付款操作</title>
<link href="/yx/commons/styles/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../../js/CalendarSelector.js"></script>
<script language="javascript" src="<s:url value="/commons/scripts/checkLength.js"/>"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script language="javascript">
	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
	function getSupplyInfo(jsonObjGetUrl)
	{
		var jsonRequest = new Request.JSON({async:false,url:jsonObjGetUrl, onComplete: function(jsonObj){
		if(jsonObj!=null && jsonObj.jsonData !=null ){
				if(jsonObj.jsonData.supplierCode != null){
					document.getElementById('code').innerHTML=jsonObj.jsonData.supplierCode;
				}
				else{
					alert(jsonObj.jsonData.supplierCode)
					document.getElementById('code').innerHTML="";
				}
				if(jsonObj.jsonData.billBank!=null){
					document.getElementById('bankName').innerHTML=jsonObj.jsonData.billBank;
				}else{
					document.getElementById('bankName').innerHTML="";
				}
				if(jsonObj.jsonData.billAccount!=null){
					document.getElementById('bankNum').innerHTML=jsonObj.jsonData.billAccount;}
				else{
					document.getElementById('bankNum').innerHTML="";
				}
		}
		}}).get({randerNum:Math.random()});	 	
	}	
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(assBeforehandPay){
		       ev2.test("notblank","供应商名称为空,请选择供应商!",$('sup.supplyId').value);
		       ev2.test("notblank","外协合同名称为空!",$('sup.assistanceName').value);
		       ev2.test("notblank","外协合同金额为空!",$('sup.payNum').value);
		   }  
		  ev2.writeErrors(errorsFrame, "errorsFrame");
		  if (ev2.size() > 0) {
		     return true;
		 }
		 return false;
	}
	
	function dosave(val){
		if(!validate()){
			if(val == 1){
				document.assBeforehandPay.selectVal.value = "save";
			}
			else if(val == 2){
				document.assBeforehandPay.selectVal.value = "confirm";
			}
			else{
				document.assBeforehandPay.selectVal.value = "close";
			}
			document.assBeforehandPay.submit();
		}
	}
</script>
</head>
<body leftmargin="0">
<s:form action="assBeforehandPay" theme="simple">
<s:hidden name="method" value="savePayInfo"></s:hidden>
<s:hidden name="sup.id"></s:hidden>
<s:hidden name="selectVal"></s:hidden>
<s:hidden name="updateSel"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center">
    <table width="100%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td height="3" align="left">
        	<s:if test="updateSel == 1 ">
        		当前页面:外协管理->外协预付款申请修改
        	</s:if>
        	<s:else>
        		当前页面:外协管理->外协预付款申请新建
        	</s:else>
       	</td>
      </tr>
      <tr>
      <td>    <iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe></td>
      </tr>
      <tr>
        <td class="bg_table01" height="1"><img src="../../images/temp.gif" alt="temp" width="1" height="3">
        	 <s:iterator id="erMessage" value="processResult.errorMessages">
				<font color="red"><strong><s:property value="#erMessage"/></strong></font><br/>
			</s:iterator>
			<s:iterator id="suMessage" value="processResult.successMessages">
				<font color="green"><strong><s:property value="#suMessage"/></strong></font><br/>
			</s:iterator>
        </td>
      </tr>
    </table>

        <table width="100%" border="0" cellspacing="1" cellpadding="1">
          <tr align="center">
            <td class="bg_table02" width="17%" align="right">销售人员：</td>
            <td class="bg_table02" align="left" colspan="3"><s:property value="userName"/></td>
          </tr>
          <tr align="center" class="bg_table02">
            <td width="13%" align="right" class="bg_table02"><font color="red"> *</font>外协供应商：</td>
            <td width="37%" align="left" class="bg_table02">
            	<s:textarea name="supplierName" id="supplyId" readonly="true" rows="2" cols="20"></s:textarea>
         	   	<s:hidden id="supplierid" name="sup.supplyId"  />
                <input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseSup.action');">
            </td>
            <td width="13%" align="right">代码：</td>
            <td width="37%" align="left" id="code">
            </td>
          </tr>
          <tr align="center" class="bg_table02">
            <td align="right">开户银行：</td>
            <td align="left" id="bankName">
            </td>
            <td align="right">银行账号：</td>
            <td align="left" id="bankNum">
			</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color="red"> *</font>外协合同名称：</td>
            <td align="left" class="bg_table02">
            	<s:textarea name="sup.assistanceName" rows="2" cols="20"></s:textarea>
            </td>
            <td class="bg_table02" align="right"><font color="red"> *</font>外协合同金额：</td>
            <td class="bg_table02" align="left">
            	<s:textfield id="money" name="sup.payNum" onblur="formatInputNumber(this);" size="15" />
            </td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">付款事项：</td>
            <td class="bg_table02" align="left">
            	<s:textarea name="sup.info" rows="2" cols="20"></s:textarea>
            </td>
            <td class="bg_table02" align="right">备注：</td>
            <td class="bg_table02" align="left">
            	<s:textarea name="sup.remark" rows="2" cols="20"></s:textarea>
			</td>
          </tr>
          <tr class="bg_table03" align="center" style="height:42px">
            <td colspan="4">
            	<table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                  <tr>
                    <td align="right" colspan="2">
                    <s:if test="updateSel == 1 ">
                    	<input class="button01" type="button" name="gonext1" value="保存修改" onClick="javascript:dosave(1)" >
                 	    <input class="button01" type="button" name="gonext" value="确认提交" onClick="javascript:dosave(2)">
                  		<input type="button" value=" 关 闭 " class="button01" onclick="window.close()">
                    </s:if>
                    <s:else>
                    	<input class="button01" type="button" name="gonext1" value="保    存" onClick="javascript:dosave(1)" >
                 	    <input class="button01" type="button" name="gonext" value="确认提交" onClick="javascript:dosave(2)">
                  		<input type="button" value="保存并关闭" class="button01" onclick="dosave(3);">
                    </s:else>
                  	</td>
                  </tr> 
                </tfoot>
            </table></td>
          </tr>
    </table></td>
  </tr>
</table>
</s:form>
</body>
<script type="text/javascript">
	getSupplyInfo("/yx/jsonData.action?method=doGetSupplyInfoOfSupplyId&supplyId=" + $('supplierid').value);
</script>
</html>
