<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<%@ include file="/commons/jsp/meta.jsp" %>
<%@ include file="/commons/jsp/scriptx.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
<s:if test="cmaininfo.conId!=null">
	<s:property value="cmaininfo.conId" />
</s:if>
<s:else>
	<s:property value="cmaininfo.conName" />
</s:else>
</title>
<head>
<title>合同概况说明</title>
</head>
<style media=print>       
  .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->       
</style> 
<body>
<s:form action="downLoadContractInfo" theme="simple">
<div align="center" style="color:#000000" >合同概况说明</div>
	<table width="100%" border="1" cellspacing="1" cellpadding="1"
		 bordercolor="#808080" style=" border-collapse: collapse;" align="center">
		<tr>
		  <td width="10%" >填表日期</td>
		  <td width="18%"  colspan="2"><s:date name="today" format="yyyy-M-d" /></td>
		  <td width="9%" >是否为中标合同:</td>
		  <td width="18%"  colspan="2">
		  <s:if test="cmaininfo.conBid">
		   是
		  </s:if>
		  <s:else>
		   否
		  </s:else>
		  </td>
		  <td width="40%"  colspan="4">注：
		  <s:if test="otherinfo == null ">
		   无
		  </s:if>
		  <s:elseif test="otherinfo.guaranteeInfo==null">
		  	无
		  </s:elseif>
		  <s:else>
		  	<s:property value="otherinfo.guaranteeInfo"/>
		  </s:else>
		  </td>
		</tr>
		<tr>
		  <td width="10%" >是否退税</td>
		  <td width="10%">
		  <s:if test="cmaininfo.conDrawback">
		  是
		  </s:if>
		  <s:else>
		  否
		  </s:else>
		  </td>
		  <td width="10%">合同号</td>
		  <td width="10%"><s:property value="cmaininfo.conId" /></td>
		  <td width="20%" colspan="2">合同名称</td>
		  <td width="40%" colspan="4"><s:property value="cmaininfo.conName" /></td>
		</tr>
		<tr>
		  <td width="10%" >销售员、电话</td>
		  <td width="30%" colspan="3"><s:property value="selsmaninfo.name"/>,<s:property value="selsmaninfo.callPhone" /></td>
		  <td width="20%" colspan="2" >合同签定、结束日期</td>
		  <td width="40%" colspan="4" ><s:date format="yyyy-M-d" name="cmaininfo.conSignDate" />,<s:date format="yyyy-M-d" name="cmaininfo.conEndDate" /></td>
		</tr>
		<tr>
		  <td width="10%" >项目部门和项目负责人、电话</td>
		  <td width="30%" colspan="3"><s:property value="typeManageService.getYXTypeManage(1018L, cmaininfo.mainItemDept).typeName" />,<s:property value="mainDepPerson.name" />,<s:property value="mainDepPerson.mobilephone" />,<s:property value="mainDepPerson.phone" /></td>
		  <td width="20%" colspan="2">
		  <s:if test="cmaininfo.standard==1" >
		  合同含税总金额（元）
		  </s:if>
		  <s:else>
		  合同不含税总金额（元）
		  </s:else>
		  </td>
		  <td width="40%" colspan="4">
		  <s:if test="cmaininfo.standard==1" >
		    <s:property value="cmaininfo.conTaxTamount"/> 
		  </s:if>
		  <s:else>
		    <s:property value="cmaininfo.conNoTaxTamount"/> 
		  </s:else>
		  </td>
		</tr>
		<tr>
		  <td width="10%" >甲方的项目工程编号</td>
		  <td width="10%" ><s:property value="cmaininfo.partyAProId" /></td>
		  <td width="10%">甲方的合同号</td>
		  <td width="10%"><s:property value="cmaininfo.partyAConId" />  </td>
		  <td width="10%">行业类型</td>
		  <td width="10%"><s:property  value="typeManageService.getYXTypeManage(1002L,customer.businessID).typeName" /></td>
		  <td width="10%">客户项目类型</td>
		  <td width="30%" colspan="3"><s:property value="typeManageService.getYXTypeManage(1007L, cmaininfo.customereventtype).typeName"/></td>
		</tr>
	    <tr>
		  <td width="10%">客户名称/编号</td>
		  <td width="30%" colspan="3"><s:property value="customer.fullName" />/<s:property value="customer.userCode"/>/</td>
		  <td width="10%">客户联系人、电话</td>
		  <td width="10%"><s:property value="linkMan.name"/>,<s:property value="linkMan.callPhone" />,<s:property value="linkMan.phone" /></td>
		  <td width="10%">客户性质</td>
		  <td width="30%" colspan="3"><s:property value="typeManageService.getYXTypeManage(1001L,customer.clientNID).typeName" /></td>
		</tr>
		<tr>
		  <td width="10%">完工应交材料</td>
		  <td width="90%" colspan="9">
		  <s:iterator value="checkedMaterialList" id="material" status="materialIndex">
                     <s:property value="#material.materialName"/>
          </s:iterator>
		  </td>
		</tr>
		<tr>
		   <table  width="100%" border="1" bordercolor="#808080" style=" border-collapse: collapse;"  cellpadding="1">
	       <TR><TH width="10%" rowspan="3"><div  style="color:#000000">责任部门</div></TH>
	       <s:if test="cmaininfo.standard==1" >
		       <TH colspan="9"> <div  style="color:#000000">含税金额</div></TH>
		      </s:if>
		      <s:else>
		       <TH colspan="9"> <div  style="color:#000000">不含税金额</div></TH>
		      </s:else>   
	      </TR>
         <TR>
                <s:iterator value="itemdesigntypelist"  status="listIndex" >
                        <td width="9%"  align="center" ><s:property value="typeName"/></td>
                </s:iterator>
         </TR>
         <TR>
                <s:iterator value="ticketlist" id="ticketlist"  status="listIndex" >
                        <td width="9%"  align="center" ><s:property value = "#ticketlist"/></td>
                </s:iterator>
         </TR>
         
		      </tr>
		   </table>
		</tr>	
		</table>
		<!-- 此处循环项目信息  -->
		<table width="100%" border="1" bordercolor="#808080" style=" border-collapse: collapse;"  cellpadding="1" >
		<s:if test="cmaininfo.ContractType ==1">
		<s:iterator value="itemlist" id="itemlist" status="itemIndex" >
		<tr>
		  <td width="10%" ><s:property value="typeManageService.getYXTypeManage(1018,#itemlist[0]).typeName" /></td>
 		  <td width="9%" ><s:property value="#itemlist[1]" /></td>
          <td width="9%"><s:property value="#itemlist[2]" /></td>
          <td width="9%"><s:property value="#itemlist[3]" /></td>
          <td width="9%"><s:property value="#itemlist[4]" /></td>
          <td width="9%"><s:property value="#itemlist[5]" /></td>
          <td width="9%"><s:property value="#itemlist[6]" /></td>
          <td width="9%"><s:property value="#itemlist[7]" /></td>
          <td width="9%"><s:property value="#itemlist[8]" /></td>
          <td width="9%"><s:property value="#itemlist[9]" /></td>
		</tr>
		</s:iterator>
		</s:if>
		<s:elseif test="cmaininfo.ContractType ==2">
			<tr>
		  <td width="10%" ><s:property value="typeManageService.getYXTypeManage(1018,cmaininfo.mainItemDept).typeName" /></td>
          <s:iterator value="itemdesigntypelist"  status="listIndex" >
              <td width="9%"  align="center" >
              	<s:if test="showAmountPartList.containsKey(typeSmall)">
              		<s:property value="showAmountPartList.get(typeSmall).money"/>
              	</s:if>
              	<s:else>
              		&nbsp;
              	</s:else>
              </td>
             </s:iterator>
		</tr>
		</s:elseif>
		<!-- 此处循环结束 -->
	    </table>
	 <table  width="100%" border="1" bordercolor="#808080" style=" border-collapse: collapse;"  cellpadding="1">
		<tr>
		<td width="100%" colspan="10" align="center" >项目阶段性完成计划、收款和开票计划</td>
		</tr>
		<tr>
		 <td width="10%" >项目责任部门</td>
		 <td width="10%" >项目阶段性预计完成日期</td>
		 <td width="10%" >开票确定收入标志</td>
		 <td width="10%" >收款和开票阶段</td>
		 <td width="10%" >预计收款日期</td>
		 <td width="10%" >预计开票日期</td>
		 <td width="10%" >开票金额</td>
		 <td width="10%" >收据金额</td>
		 <td width="10%" >预决算金额或尾款标志</td>
		 <td width="10%" >收款金额</td>
		</tr>
        <!-- 循环计划 -->
        <s:if test="planlist!=null">
        <s:iterator value="planlist" >
		<tr>
		 <td width="10%" >
	        <s:if test="cmaininfo.ContractType==2">
	           <s:property value="contractservice.findDepNameByMainid(mainid)"/>
	        </s:if>
	        <s:else>
	           <s:property value="contractservice.findDepName(conItemInfo)" />
	        </s:else>
         </td>
		 <td width="10%" ></td>
		 <td width="10%" ></td>
		 <td width="10%" ><s:property value="contractservice.findStageName(conItemStage)" /></td>
		 <td width="10%" ><s:property value="initReceDate" /></td>
		 <td width="10%" ><s:property value="initBillDate"/></td>
		 <s:if test="billType == 4">
			 <td width="10%" ></td>
			 <td width="10%" ><s:property value="initBillAmount" /></td>
		 </s:if>
		 <s:else>
		     <td width="10%" ><s:property value="initBillAmount" /></td>
			 <td width="10%" ></td>
		 </s:else>
		 <td width="10%" ></td>
		 <td width="10%" ><s:property value="initReceAmount" /></td>
		</tr>
		</s:iterator>
		<!-- 循环结束 -->
		</s:if>
		<s:else>
		<s:iterator value="planlistR" >
		<tr>
		 <td width="10%" >
	        <s:if test="cmaininfo.ContractType==2">
	           <s:property value="contractservice.findDepNameByMainid(mainid)"/>
	        </s:if>
	        <s:else>
	           <s:property value="contractservice.findDepName(contractItemMaininfo)" />
	        </s:else>
         </td>
		 <td width="10%" ></td>
		 <td width="10%" ></td>
		 <td width="10%" ><s:property value="contractservice.findStageName(conItemStage)" /></td>
		 <td width="10%" ><s:property value="realPredReceDate" /></td>
		 <td width="10%" ><s:property value="realPredBillDate"/></td>
		 <s:if test="billType == 4">
			 <td width="10%" ></td>
			 <td width="10%" ><s:property value="realBillAmount" /></td>
		 </s:if>
		 <s:else>
		     <td width="10%" ><s:property value="realBillAmount" /></td>
			 <td width="10%" ></td>
		 </s:else>
		 <td width="10%" ></td>
		 <td width="10%" ><s:property value="realReceAmount" /></td>
		</tr>
		</s:iterator>
		</s:else>
    </table>
    <s:set name="hasownproduct" value="0"/>
     <s:iterator value="productNameList" id = "namelist" >	
       <s:if test="#namelist.trim().length()!=0" >
           <s:set name="hasownproduct" value="1"/>
       </s:if>
    </s:iterator>
    <s:if test="#hasownproduct==0">
     <font color="black">*此合同无自有产品信息</font>
    </s:if>
    <s:else>
    <br>
    <table width="100%" border="1" bordercolor="#808080" style=" border-collapse: collapse;"  cellpadding="1" >
		<tr>
		  <td width="10%">自有产品名称</td>
		  <s:iterator value="productNameList" id = "namelist" >	
		  	<td width="10%"><s:property value = "#namelist" /></td>
          </s:iterator>
		   <!-- 此处循环TD写入自有产品名称 -->
		</tr>
		<tr>
		   <td>数量（套）</td>
		  <s:iterator value="productNumberList" id = "numberlist" >	
		  	<td ><s:property value = "#numberlist" /></td>
          </s:iterator>
		   <!-- 循环自有产品数量 -->
		   <!-- 循环8次已便填充格式 -->
		</tr>
		<tr>
		<td width="10%">金额（含税，元）</td>
		  <s:iterator value="productPrice" id = "pricelist" >	
		  	<td ><s:property value = "#pricelist" /></td>
          </s:iterator>
		</tr> 
		</table>
    </s:else>
    
	<Table width="100%">
		<tr align="center" >
			<td align="center"><input type="button" class="Noprint" onclick="Print({horizontal:true})" value="  打  印  "/>  <input type="button"  onclick="window.close()"  class="Noprint"  value="  关  闭  " /></td>
		</tr>
    </Table>
    </s:form>
</body>
</html>