<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>queryClient</title>
<%@ include file="/commons/jsp/meta.jsp"%>

<script language="javascript">

  </SCRIPT>
</head>
<style>

</style>
<body leftmargin="0">
<s:form action="clientQuery" theme="simple" target="rightFrame">
<s:hidden name="resetCondition" value="true"/>

<table  width="100%" class="bg_table02">
<tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
	            <tr class="bg_table02">
                  <td align="right" nowrap class="bg_table02"><div align="right">客户全称：</div></td>
	              <td nowrap> <s:textfield name="name" size="15"></s:textfield>
 		 </tr>
   		  <tr class="bg_table02">
                  <td align="right" nowrap class="bg_table02"><div align="right">客户简称：</div></td>
	              <td nowrap> <s:textfield name="fullName" size="15"></s:textfield>
 		 </tr>
	            <tr class="bg_table02">
                  <td align="right" nowrap class="bg_table02"><div align="right">客户性质：</div></td>
	              <td nowrap><s:select name="clientNID" list="clientNature" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
                  
                </s:select>              </td>
  </tr>
	            <tr class="bg_table02">
	              <td align="right" nowrap class="bg_table02"><div align="right">行业类型：</div>	                
	                
	               <td nowrap><s:select name="businessID" list="businessType" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
                  
                </s:select>              </td>
	            </tr>
	            <tr class="bg_table02">
	              <td align="right" nowrap class="bg_table02"><div align="right">客户地域：</div>	                
	                
	               <td nowrap><s:select name="areaID" list="clientArea" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
                  
                </s:select>              </td>
	            </tr>
	            
	            <tr class="bg_table02">
	              <td align="right" nowrap class="bg_table02"><div align="right">是否项目单位：</div>	                
	                <div align="right"></div></td>
	              <td nowrap><select name="isEventUnit">
	              <option Value=''> </option> 
                      <option Value="1">是</option> 
                      <option Value="0">否</option>
                  </select></td>
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
