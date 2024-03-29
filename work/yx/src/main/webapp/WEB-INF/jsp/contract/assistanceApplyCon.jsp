<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<head>
<title>外协申请</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/thousands.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
	//验证通过表单提交....0保存 1提交
	function doSave(obj){
		if(!validate()){
			document.forms(0).action="/yx/contract/assistanceApplyQuery.action?sign="+obj;
			document.forms(0).submit();
		}
	}
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(assistanceApplyQuery){
		       ev2.test("notblank","供应商名称为空,请选择供应商合同名称!",$('a.supplyId').value);
		       ev2.test("notblank","外协合同名称为空,请输入合同名称!",$('a.assistanceName').value);
		       ev2.test("notblank","外协金额为空,请输入金额数字!",$('a.contractMoney').value);
		       ev2.test("+float+0","合同金额不是数字!",$('a.contractMoney').value);
		       ev2.test("length","分包合同内容描述内容过长!",$('describeInfo').value , 200);
		       ev2.test("dateYX","合同签订日期格式不正确",$("startDate").value); 
		       ev2.test("dateYX","预计结束日期格式不正确",$("endDate").value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	
	//验证要添加的合同接不能超过
	function checkAmount(url, method, name , num){
	   //去掉逗号,通过url传带逗号的数字，变成0了
		 var ev=new Validator();  
	       if(num!=""){
	   	     ev.test("+float","合同金额不是数字!",num);
	   	  }
	   	  num = getNumberChar(num);
	      var myRequest = new Request({url:url,async:false,method:'get'});
		   myRequest.addEvent("onSuccess",function(responseText, responseXML){
		      var result = JSON.decode(responseText);
			  if(result.isOverAmount){
			  	 $('contractMoney').value="";
			     ev.addError("你输入的金额不能超过:"+result.amount);
			  } 
		    });
		   myRequest.send("method="+method+"&mainProjectId="+name+"&amount="+num+"&randomNum="+Math.random());
	       ev.writeErrors(errorsFrame, "errorsFrame");   
	}
	
	
	function cmoney(obj){
		var num = parseFloatNumber(obj.value);
		obj.value = formatNumber(num,'#,###.00');
        checkAmount('/yx/assistance/assistance.action','checkAmount',$('contractProjectId').value,obj.value);
	}


</script>
<body leftmargin="0">
<s:form action="assistanceApplyQuery" theme="simple">
<s:hidden name="method" value="saveAContract"></s:hidden>
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center"><table width="100%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td height="3" align="left">当前页面:合同管理 -> 正式合同管理 -> 外协合同新建
        </td>
      </tr>
      	<tr>
			<td class="bg_table01" height="1"><img
					src="../../images/temp.gif" alt="temp" width="1" height="3"></td>
		</tr>
    </table>
        <table width="100%" border="0" cellspacing="1" cellpadding="1"> 
        <s:iterator value="mainContract" id="contract"> 
         <input type="hidden" name="contractMId" value="<s:property value="#contract[3]"/>" />
         <input type="hidden" name="contractCustomerID" value="<s:property value="#contract[4]"/>" />
          <tr align="center">
            <td class="bg_table02" width="17%" align="right">销售人员：</td>
            <td class="bg_table02" align="left" colspan="3"><s:property value="#contract[2]" id="empName" /></td>
          </tr>
          <tr align="center">
            <td width="15%" class="bg_table02" align="right">合同号：</td>
            <td width="25%" align="left" class="bg_table02"><s:property value="#contract[0]" /><input type="hidden" name="contractId" value="<s:property value="#contract[0]" />"/></td>
            <td width="15%" align="right" class="bg_table02"><font color=red>*</font>外协供应商：</td>
            <td width="25%" align="left" class="bg_table02">
            	<s:textfield id="supplyId" name="a.supplyId" size="15" readonly="true" ></s:textfield><s:hidden id="supId" name="supplyName"/>
            	<input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseSup.action');">
            </td>
          </tr>
          <tr align="center">
           <%--  <td class="bg_table02" align="right">合同名称：</td>
            <td class="bg_table02" align="left"><s:property value="#contract[1]"/> <input type="hidden" name="contractName" value="<s:property value="#contract[1]"/>"/> </td>
          --%>  
            <td class="bg_table02" align="right">项目号：</td>
            <s:iterator value="mainProject" >
           	 <td align="left" class="bg_table02"><s:property  value="mainProject[0]" /> <input type="hidden" name="contractProjectId" id="contractProjectId" value="<s:property value="mainProject[0]" />" /> </td>
            </s:iterator>
            <td class="bg_table02" align="right">&nbsp;</td>
            <td class="bg_table02" align="left">&nbsp;</td>
          </tr>
         </s:iterator>
          <tr align="center">
			 <td class="bg_table02" align="right"><font color=red>*</font>外协合同名称：</td>
            <td class="bg_table02" align="left"><s:textfield name="a.assistanceName"/></td>
            <td class="bg_table02" align="right"><font color=red>*</font>外协合同金额：</td>
            <td class="bg_table02" align="left"><s:textfield name="a.contractMoney" id="contractMoney"  onblur="cmoney(this);" /></td>
          </tr>
          <tr align="center">
 			<td class="bg_table02" align="right"><font color=red>*</font>合同签订日期：</td>
                        <td class="bg_table02" align="left">
				<div align="left">
				  	<input type="text" id="startDate" name="a.contractDate"   size="12" />
				  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('startDate')" align=absMiddle alt=""  />
			  	</div>
			</td>
            <td class="bg_table02" align="right"><font color=red>*</font>预计结束日期：</td>
                        <td class="bg_table02" align="left">
				<div align="left">
				  	<input type="text" id="endDate" name="a.endDate"   size="12" />
				  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('endDate')" align=absMiddle alt=""  />
			  	</div>
			</td>
          </tr>
        
          <tr align="center">
            <td height="79" align="right" class="bg_table02">分包合同内容描述：</td>
            <td class="bg_table02" align="left" colspan="3" >
           		 <s:textarea name="a.contractContent" cols="20" rows="3" cssClass="txtarea" id="describeInfo"></s:textarea>
            </td>
          </tr>


          <tr class="bg_table03" align="center" style="height:42px">
            <td colspan="4"><table style="width:0%;100%">
                <tfoot class="bg_table03" style="height:42px">
                  <tr>
                    <td align="right" colspan="2"><input class="button01" type="button" name="gonext" onclick="doSave(0)" value="保    存"></td>
                    <td><input class="button01" type="button" name="gonext" value="确认提交" onClick="doSave(1)"></td>
                    <td align="right" colspan="2"><input type="button" name="button4" id="button4" value="关   闭" class="button01"  onClick="window.close();"></td>
                  </tr> 
                </tfoot>
            </table></td>
          </tr>
    </table></td>
  </tr>
</table>
</s:form>
</body>

</html>
