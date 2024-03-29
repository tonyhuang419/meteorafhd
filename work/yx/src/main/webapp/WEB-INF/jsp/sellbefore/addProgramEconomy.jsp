<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<head>
<title>新增工程经济</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<script type="text/javascript">
	//验证通过表单提交
	function doSave(type){
	   if(!validate()){
		   	if(type == 0){
				document.forms(0).submit();
			}
			if(type == 1){
				document.forms(0).method.value = "saveProgramEconomySubmit";
				document.forms(0).submit();		
			}
		}		
	 }
	//验证表单
	function validate(){
		var ev2=new Validator();
		with(addProgramEconomyQuery){
		       ev2.test("notblank","工程编号为空,请输入编号!!",$('yp.projectNumber').value);
		       ev2.test("notblank","请正确输入电话号码!",$('yp.projectLMP').value);
		       ev2.test("notblank","项目编号为空,请输入项目名称!",$('yp.projectNumberJ').value);
		       ev2.test("+float+0","投资匡算应该为数字!",$('yp.investment').value);
		       ev2.test("+float+0","阶段投资估概算应该为数字!",$('ys.investmentCount').value);
		   }  
		  if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 return false;
	}
	//验证电话号码
	function isTel(str){
       var reg=/^([0-9]|[\-])+$/g ;
       if(str.length<7 || str.length>18){
          return false;
       }
       else{
          return reg.exec(str);
       }
	}
	function fanhui()
	{
		location.href="sellbefore/selectSellBefore.action";
	}
	function isExist(url, method, name){
	   var oldcode="<s:property value="yp.projectNumber"/>";
	   if(oldcode != $('yp.projectNumber').value){
		   var ev=new Validator();   
		       var myRequest = new Request({url:url,async:false,method:'get'});
			   myRequest.addEvent("onSuccess",function(responseText, responseXML){
				  if(responseText =='1'){
				  	 $('yp.projectNumber').value="";
				     ev.addError("工程编号已经存在，请重新输入一个工程编号！");
				  } 
			    });
			   myRequest.send("method="+method+"&yp.projectNumber="+name+"&randomNum="+Math.random());
		    ev.writeErrors(errorsFrame, "errorsFrame");
	    }
	}
</script>
<body>
<s:form action="addProgramEconomyQuery" theme="simple">
<s:hidden name="method" value="saveProgramEconomy"></s:hidden>
<s:hidden name="yp.id" ></s:hidden>
<s:hidden name="ys.id" ></s:hidden>
<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
	<tr>	
		<td align="center"> 
		<table align="center"  border=0  cellpadding=1 cellspacing=1 width="100%">
			<tr> 
				<td colspan="4" align="left" >当前页面:工程经济信息录入
				<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
				</td>
		  </tr>
			<tr>
            	<td colspan="4" align="right" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1">
            	</td>
          	</tr>
			    <tr align="center">
			      <td colspan="4" align="right" class="bg_table02"><div align="center">主体信息</div></td>
	      </tr>
		    <tr align="center">
			 <td class="bg_table02" width="16%" align="right">售前合同号：</td>
			 <s:iterator value="info.result">
			  <td class="bg_table02" width="34%" align="left">
				<s:property value="sellBeforeNum" /> <input type="hidden" value="<s:property value="ID" />" name="ID" />
				</td>
   			  <td width="26%" align="right" class="bg_table02">项目名称：</td>
   			  <td width="24%" align="left" class="bg_table02"><s:property value="projectName" id="projectName"  /><s:hidden name="projectName"></s:hidden></td>
   			  </s:iterator>
			</tr>
			<tr align="center">
			  <td class="bg_table02" align="right" ><font color="red"> *</font>工程编号：</td>
			  <td class="bg_table02" align="left"><s:textfield cssClass="txtinput" name="yp.projectNumber" size="20" onblur="isExist('/yx/sellbefore/addProgramEconomyQuery.action','isExist',$('yp.projectNumber').value);" /></td>
			  <td class="bg_table02" align="right" ><font color="red"> *</font>项目联系人（电话）：</td>
			  <td class="bg_table02" align="left"><s:textfield cssClass="txtinput" name="yp.projectLMP" size="20" /></td>
		  </tr>
			<tr align="center">
			  <td class="bg_table02" align="right" ><font color="red"> *</font>项目编号（甲方）：</td>
			  <td class="bg_table02" align="left"><s:textfield cssClass="txtinput" name="yp.projectNumberJ" size="20" /></td>
			  <td class="bg_table02" align="right" ><font color="red"> *</font>投资匡算：</td>
			  <td class="bg_table02" align="left"><s:textfield cssClass="txtinput" name="yp.investment" size="20" onblur="formatInputNumber(this)" /></td>
		  </tr>
			<tr align="center">
			  <td class="bg_table02" align="right" >客户项目单位：</td>
			  <td class="bg_table02" align="left"><s:textfield cssClass="txtinput" name="yp.clientFactory" size="20" /></td>
              <td class="bg_table02" align="right">客户项目负责人（电话）：</td>
              <td class="bg_table02" align="left"><s:textfield cssClass="txtinput" name="yp.clientOrderP" size="20" /></td>
		  </tr>
			<tr align="center">
			  <td class="bg_table02" align="right" >客户管理部门：</td>
			  <td class="bg_table02" align="left"><s:textfield cssClass="txtinput" name="yp.clientManageDep" size="20" /></td>
			  <td class="bg_table02" align="right">管理部门联系人（电话）：</td>
			  <td class="bg_table02" align="left"><s:textfield cssClass="txtinput" name="yp.manageDepLMP" size="20" /></td>
		  </tr>
			<tr>
			  <td colspan="4" align="right" class="bg_table02"><hr></td>
		  </tr>
			<tr>
			  <td colspan="4" align="right" class="bg_table02"><div align="center">阶段信息</div></td>
		  </tr>
			<tr>
              <td class="bg_table02" align="right">阶段名称：</td>
			  <td class="bg_table02" align="left">
			  <s:select name="ys.sectionName" list="designSpeedList" listKey="typeSmall" listValue="typeName" required="true"
							headerValue="">
						</s:select></td>
			  <td class="bg_table02" align="right"><font color="red"> *</font>设计委托进度：</td>
			  <td class="bg_table02" align="left">
			  	<div align="left">
			  	<input type="text" id="programDate" name="ys.designSpeed" value="<s:property value="ys.designSpeed" />" readonly="readonly"  size="12">
			  	<img src="/yx/commons/images/calendar.gif" onClick="javascript:ShowCalendar('programDate')" align=absMiddle alt=""  />
			  	</div></td>		
			  </tr>
			<tr>
			  <td class="bg_table02" align="right">阶段投资估概算：</td>
			  <td class="bg_table02" align="left"><s:textfield cssClass="txtinput" name="ys.investmentCount" size="20" onblur="formatInputNumber(this)"/></td>
			  <td class="bg_table02" align="right">&nbsp;</td>
			  <td class="bg_table02" align="left">&nbsp;</td>
		  </tr>
			<tr align="center">
    		  <td colspan="4" class="bg_table03"><div align="center"><span class="bg_blue07">
    		  
			    <input name="save2" type="button" class="button02" onclick="doSave(1)" value=" 保  存 ">
			    <input name="save2" type="button" class="button02" onclick="doSave(0)" value="保存提交">
			    <input name="save" type="button" class="button02" onclick="fanhui()" value="返    回">
		      </span></div></td>
			</tr>
		</table>
		<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
			<tr valign="top">
			  <td class="bg_table04"></td>
		    </tr>
		</TABLE>
		</td>
	</tr>
</table>
</s:form>
</body>
</html>