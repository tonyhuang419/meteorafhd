<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp"%>
<html>
<head>
<title>类型管理搜索</title>
<script src="/yx/commons/scripts/validator.js" type="text/javascript"></script>
<script src="/yx/commons/scripts/mootools-1.2-core-jm.js" type="text/javascript"></script>
<script language="javascript" src="<s:url value="/commons/scripts/checkLength.js"/>"></script>
</head>

<body>
<s:form action="typeManageQuery" target="rightFrame" theme="simple">
<div align="left" style="color: #FF0000">
<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
</div>
<s:hidden name="resetCondition" value="true"/>
<table width="100%" class="bg_table02">
          <tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
		  <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">类型编号：</div></td>
		          <td  nowrap><div align="left"><s:textfield name="typeSmall"></s:textfield></td>
          </tr>
		        <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">类型种类：</div></td>
		          <td  nowrap><div align="left"><s:select  name="typeBig" list="yxBigTypeMap" emptyOption="true" ></s:select>
	              </div></td>
          </tr>
          <tr class="bg_table02">
		          <td align="center" nowrap class="bg_table02"><div align="right">类型名称：</div></td>
		          <td  nowrap><div align="left"><s:textfield name="typeName"></s:textfield>
	              </div></td>
          </tr>          
          <tr class="bg_table02">
				  <td rowspan="2" align="center" nowrap class="bg_table02"><div align="right">最后修改时间：</div></td>
				  <td nowrap><div align="left">从<input type="text" id="bidDate1"
						name="lastUpdatedDate1" 
						 size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate1')" align=absMiddle
						alt="" /></div></td>
  </tr>
				<tr class="bg_table02">
                  <td nowrap><div align="left">至<input type="text" id="bidDate2"
						name="lastUpdatedDate2" 
						 size="12" /> <img
						src="/yx/commons/images/calendar.gif"
						onClick="javascript:ShowCalendar('bidDate2')" align=absMiddle
						alt="" /></div></td></tr>
						 <tr class="bg_table02">
	              <td align="center" nowrap class="bg_table02"><div align="right">是否有效：</div></td>
	              <td nowrap><div align="left">
	              <select name="isActiveorNot">
	                <option value="1">有效</option>
	              	<option value="0">无效</option>          	
	              </select>
	              </div></td>
          </tr>
                
            <tr class="bg_table02">
			  <td colspan="2" nowrap class="bg_table04"><div align="center">
			    <input type="button" value=" 查  询 "  class="button02" onclick="return check()">
		      </div></td>
  </tr>
		</table>
<script language="javascript">
<!--	jsLab_CreateSelectInput(document.getElementById("clientName"));-->
function check(){	
	if(!validate()){
		document.forms(0).submit();
	}
	return false;
}

function validate(){
       var ev2=new Validator();
       var str1=$('lastUpdatedDate1').value;
       var str2=$('lastUpdatedDate2').value;
        with(typeManageQuery){  
          	ev2.test("dateYX","最后修改起始日期格式不正确",$("bidDate1").value); 
 		    ev2.test("dateYX","最后修改结束日期格式不正确",$("bidDate2").value);   
       } 
        ev2.writeErrors(errorsFrame, "errorsFrame");
        if (ev2.size() > 0) {  
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
</s:form>		
</body>
</html>