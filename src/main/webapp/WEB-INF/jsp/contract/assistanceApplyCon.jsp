<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<head>
<title>外协申请</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/select.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
	function openUrl(url){
		window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
	}
	//验证通过表单提交....0保存 1提交
	function doSave(obj){
		if(!validate(2)){
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
		       ev2.test("+float+0","外协金额金额不是数字!",$('a.contractMoney').value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
</script>
<body>
<s:form action="assistanceApplyQuery" theme="simple">
<s:hidden name="method" value="saveAContract"></s:hidden>
<table width="100%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
  <tr>
    <td valign="top" align="center"><table width="96%" border="0" cellspacing="1" cellpadding="1">
      <tr>
        <td height="3" align="left">当前页面:合同管理 -> 正式合同管理 &raquo; 外协合同新建
        </td>
      </tr>
      <tr>
        <td class="bg_table01" height="1"><img src="../../images/temp.gif" alt="temp" width="1" height="1">
        
        <iframe name="errorsFrame" frameborder="0"
				framespacing="0" height="0" width="100%" scrolling="no"></iframe></td>
      </tr>
    </table>
        <table width="96%" border="0" cellspacing="1" cellpadding="1">
        <s:iterator value="mainContract" id="contract"> 
         <input type="hidden" name="contractMId" value="<s:property value="#contract[3]"/>" />
          <tr align="center">
            <td class="bg_table02" width="17%" align="right">销售人员：</td>
            <td class="bg_table02" align="left" colspan="3"><s:property value="#contract[2]" id="empName" /></td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">合同号：</td>
            <td width="33%" align="left" class="bg_table02"><s:property value="#contract[0]" /><input type="hidden" name="contractId" value="<s:property value="#contract[0]" />"/></td>
            <td width="13%" align="right" class="bg_table02"><font color=red>*</font>外协供应商：</td>
            <td width="37%" align="left" class="bg_table02">
            	<s:textfield id="supplyId" name="a.supplyId" size="15" readonly="true" ></s:textfield><s:hidden id="supId" name="supplyName"/> 
                <input type="button" value="…" onclick="javascript:openUrl('../assistance/chooseSup.action');">
            </td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">合同名称：</td>
            <td class="bg_table02" align="left"><s:property value="#contract[1]"/> <input type="hidden" name="contractName" value="<s:property value="#contract[1]"/>"/> </td>
            <td class="bg_table02" align="right"><font color=red>*</font>外协合同名称：</td>
            <td class="bg_table02" align="left"><s:textfield name="a.assistanceName"/></td>
          </tr>
         </s:iterator>
          <tr align="center">
            <td class="bg_table02" align="right">项目号：</td>
            <s:iterator value="mainProject" >
           	 <td align="left" class="bg_table02"><s:property  value="mainProject[0]" /> <input type="hidden" name="contractProjectId" value="<s:property value="mainProject[0]" />" /> </td>
            </s:iterator>
            <td class="bg_table02" align="right">&nbsp;</td>
            <td class="bg_table02" align="left">&nbsp;</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right"><font color=red>*</font>外协合同金额：</td>
            <td class="bg_table02" align="left"><s:textfield name="a.contractMoney" /></td>
            <td class="bg_table02" align="right">&nbsp;</td>
            <td class="bg_table02" align="left">&nbsp;</td>
          </tr>
          <tr align="center">
            <td class="bg_table02" align="right">合同签订日期：</td>
            <td class="bg_table02" align="left">
				<div align="left">
				  	<input type="text" id="startDate" name="a.contractDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
				  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('startDate')" align=absMiddle alt=""  />
			  	</div>
			</td>
            <td class="bg_table02" align="right">预计结束日期：</td>
            <td class="bg_table02" align="left">
				<div align="left">
				  	<input type="text" id="endDate" name="a.endDate" readonly="readonly" onClick="javascript:ShowCalendar(this.id)" size="12" />
				  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('endDate')" align=absMiddle alt=""  />
			  	</div>
			</td>
          </tr>
          <tr align="center">
            <td height="79" align="right" class="bg_table02">分包合同内容描述：</td>
            <td class="bg_table02" align="left" colspan="3" >
           		 <s:textarea name="a.contractContent" cols="60" rows="5" cssClass="txtarea"></s:textarea>
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
