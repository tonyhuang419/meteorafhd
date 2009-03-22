<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/thousands.js" type="text/javascript"></script>
<html>
<head>
<title>收款信息修改</title>

</head>
<body leftmargin="0">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0" width="100%" scrolling="no"></iframe>
<s:form action="w_HarvestMeansSearch.action" theme="simple">
<s:hidden id="reveInfoSid" name="ri.id"/>
<s:hidden name="method" id="method" value=""/>  
<s:hidden name="maxAddAmount" id="maxAddAmount"/>      <%--不带千分位，用于判断--%>
<s:hidden name="maxAddAmount" id="maxAddAmountOrg"/>	<%--带千分位，用于显示--%>
<input type="hidden" id="reveAmountOrg" value=""/> 
<s:hidden name="defaultDate" id="defaultDate"></s:hidden>
<div align="left" style="color: #000000">
	<p>收款明细修改</p>
</div>

<s:if test="maxAddAmount<=0"><br>
	<div align="left" style="color:red">提示：票据余额已为 <s:property value="maxAddAmount - ri.amount"/></div>
</s:if>

<table width="100%" border="0" cellspacing="1" cellpadding="1">
	 <tr>
 		<td colspan="2" align="right" class="bg_table01"><img src="./../images/temp.gif" width="1" height="3"></td>
 	</tr>
	<tr align="center">
		<td class="bg_table02" align="right"><font color="red">* </font>到款金额：</td>
		 <td class="bg_table02" align="left"><s:textfield name="ri.amount" onblur="changeNum(this);" id="reveAmount"></s:textfield></td>
	 </tr>
	<tr align="center">
		 <td class="bg_table02" align="right"><font color="red">* </font>到款方式：</td>
		 <td class="bg_table02" align="left"><s:select name="ri.receType" list="receType" listKey="typeSmall"
				listValue="typeName" required="true" id="reveType">
               </s:select>
         </td>
	 </tr>
	<tr align="center">
          <td class="bg_table02" align="right"><font color="red">* </font>到款时间：</td>
		 <td class="bg_table02" align="left"> 
		<s:textfield id="riDate" name="ri.amountTime" id="reveDate"  size="7" />
		<img src="/yx/commons/images/calendar.gif"  onclick="ShowCalendar('reveDate');" align="absMiddle" alt=""/>
		 </td>  
	 </tr>

	<tr align="center"  class="bg_table04" height="42px">
			<td align="right" colspan="2">
             	<div align="center">
                      	<input class="button01" type="button"  value="保存" onclick="modifySave();"   />
						<input type="reset" class="button01" name="reset" value="重置" />
				</div>
			</td>
	</tr>
	</table>

</s:form>


<script language="javascript">

function modifySave(){
	if(!validate()){
		document.forms(0).method.value = "modifySave";
		document.forms(0).submit();
	}
}

function validate(){
	var ev2=new Validator();
	with(w_HarvestMeansSearch){
		     ev2.test("+float+0","到款金额不是数字!",$('reveAmount').value);
		     ev2.test("notblank","到款方为空,请选择",$('reveType').value);
		    if(ev2.test("dateYX","到款日期格式不正确",$("reveDate").value)){
		      var arrJHRQ=$("reveDate").value.split('-'); //转成成数组，分别为年，月，日，下同
			       var arrJHWCSJ=$("defaultDate").value.split('-');
			       var dateJHRQ=new Date(parseInt(arrJHRQ[0]),parseInt(arrJHRQ[1])-1,parseInt(arrJHRQ[2]),0,0,0); //新建日期对象
			       var dateJHWCSJ=new Date(parseInt(arrJHWCSJ[0]),parseInt(arrJHWCSJ[1])-1,parseInt(arrJHWCSJ[2]),0,0,0);
			       if (dateJHRQ.getTime()>dateJHWCSJ.getTime()) {
			           ev2.addError("到款日期大于当天日期");
			       }  
		     }
		   //  <s:if test="noRemain != 1">
		   //	 ev2.test("greater","到款金额不允许超过 "+$('maxAddAmountOrg').value , $('maxAddAmount').value , $('reveAmountOrg').value   );
		   //	</s:if>
		}  
	if (ev2.size() > 0) {
		    ev2.writeErrors(errorsFrame, "errorsFrame");
		  return true;
	}
	 return false;
}

 <%--输入框显示千分位，隐藏于去千分位，用于判断--%>
function changeNum(obj){
	obj.value = formatNumber(parseFloatNumber(obj.value),'#,###.00');
	document.forms(0).reveAmountOrg.value = parseFloatNumber(obj.value);
}

var f = document.getElementById("maxAddAmount");
f.value = parseFloatNumber(f.value);
<s:if test = "#hasErrorMsg == false">
	alert("还没有生成该月的月度收款计划，不能收款！");
</s:if>
</script>


</body>
</html>
