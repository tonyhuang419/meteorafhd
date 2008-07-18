<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<title>自有产品搜索</title>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script language="javascript" src="<s:url value="/commons/scripts/checkLength.js"/>"></script>
</head>
<body>
<s:form action="selectSelfProduct" target="rightFrame" theme="simple">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<s:hidden name="resetCondition" value="true"></s:hidden>
<table height="40%"  border=0  cellpadding=1 cellspacing=0>
          <tr>
            	<td colspan="3" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
		  <tr class="bg_table02"><!--
		          <td align="center" nowrap class="bg_table02"><div align="right">产品代码：</div></td>
		          <td  colspan="2"  nowrap><div align="left">
		 				 <s:textfield name="projectCode"></s:textfield>
	              </div></td>-->
          </tr>	       
          <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">登记名称：</div></td>
		          <td  colspan="2"  nowrap><div align="left">
		 				 <s:textfield name="registerNames"></s:textfield>
	              </div></td>
          </tr>
           <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">证书标号：</div></td>
		          <td  colspan="2"  nowrap><div align="left">
		 				 <s:textfield name="certificateNumber"></s:textfield>
	              </div></td>
          </tr>
	            <tr class="bg_table02">
	              <td align="center" nowrap class="bg_table02"><div align="right">是否失效：</div></td>
	              <td colspan="2" nowrap><div align="left">
	              <select name="isValidProduction">
	                <option value="0">全部</option>
	              	<option value="1">否</option>
	              	<option value="2">是</option>           	
	              </select>
	              </div></td>
          </tr>
          
          <tr class="bg_table02">
				  <td rowspan="2" align="center" nowrap class="bg_table02"><div align="right">登记日期：</div></td>
				  <td nowrap><div align="left">从<input type="text" id="bidDate1"
						name="registerDate1" readonly="readonly"
						onClick="javascript:ShowCalendar(this.id)" size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate1')" align=absMiddle
						alt="" /></div></td>
  </tr>
				<tr class="bg_table02">
                  <td nowrap><div align="left">至<input type="text" id="bidDate2"
						name="registerDate2" readonly="readonly"
						onClick="javascript:ShowCalendar(this.id)" size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate2')" align=absMiddle
						alt="" /></div></td></tr>
                
            <tr class="bg_table02">
			  <td colspan="3" nowrap class="bg_table04"><div align="center">
			    <input type="submit" value=" 查  询 " onclick="return check()" class="button02">
		      </div></td>
  </tr>
		</table>
<script language="javascript"> 
function check(){	
	if(!validate()){
		document.forms(0).submit();
	}
	return false;
}

function validate(){
       var ev2=new Validator();
       var str1=$('registerDate1').value;
       var str2=$('registerDate2').value;
       with(selectSelfProduct){  
             
       } 
        if (ev2.size() > 0) {
		     ev2.writeErrors(errorsFrame, "errorsFrame");
		     return true;
		 }
		 if(str1!=""&&str2!=""){
		    if(!checkTime(str1,str2)){
		    alert("开始时间不能大于结束时间");
		    return true;
		    }
		 }	 
		 return false;
}
</script>
--></s:form>		
</body>
</html>