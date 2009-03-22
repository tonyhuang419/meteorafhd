<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>queryClient</title>
<%@ include file="/commons/jsp/meta.jsp"%>


<body leftmargin="0">
<s:form action="supplyQuery" theme="simple" target="rightFrame">
<s:hidden name="resetCondition" value="true"/>

<table width="100%" class="bg_table02">

<tr>
          <td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
 </tr>
	            <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">供应商名称：</div></td>
	              <td nowrap> <s:textfield name="supplierName" size="15"></s:textfield>
  </tr>
	            <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">供应商类别：</div></td>
	              <td nowrap><s:select name="supplierType" list="supplierTypeList" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
                  
                </s:select>              </td>
  </tr>
	            
	            <tr class="bg_table02">
	              <td align="center" nowrap class="bg_table02"><div align="right">供应商地域：</div>	                
	                
	               <td nowrap><s:select name="eareCode" list="supplyArea" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
                  
                </s:select>              </td>
	            </tr>
	            
	           

            <tr class="bg_table02">
			  <td colspan="2" nowrap class="bg_table04"><div align="center">
			    	<input class="button01" type="submit" name="Input" value="查  询"  />
		      </div></td>
  </tr>
		</table>
		</s:form>		
</body>
</html>
