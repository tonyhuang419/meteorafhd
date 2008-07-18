<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>正式合同管理</title>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>

<script type="text/javascript">
function trav_rcplanckbox(){
	//从table遍历tr，首先判断cells0的checkbox，然后对比cells3、cells4、cells5
	cks = document.getElementsByName("kpskjh");
	
	var billtype = "";
	var base = "";
	var billnatrue = "";
	
	var sign = false;
	var len = cks.length;
	
	var getString = "";

	
	for(var i=0;i<len;i++){        
		if(cks[i].checked){
			getString = getString + cks[i].parentNode.parentNode.parentNode.all.rcplan.value +",";
			if(sign==false){
				billtype = cks[i].parentNode.parentNode.parentNode.cells[3].innerHTML;
				base = cks[i].parentNode.parentNode.parentNode.cells[4].innerHTML;
				billnatrue = cks[i].parentNode.parentNode.parentNode.cells[5].innerHTML;
				sign = true;
			}
			else{
				if(cks[i].parentNode.parentNode.parentNode.cells[3].innerHTML != billtype){
					alert("选择存在错误");
					return;
				}
				if(cks[i].parentNode.parentNode.parentNode.cells[4].innerHTML != base){
					alert("选择存在错误");
					return;
				}
				if(cks[i].parentNode.parentNode.parentNode.cells[5].innerHTML != billnatrue){
					alert("选择存在错误");
					return;
				}
			}
		}
	}
	if(getString!=""){
		var contractmainsid = document.getElementById("contractmainsid").value;
		var billcustomersid = document.getElementById("billcustomersid").value;
		var contractid = document.getElementById("contractid").value;
		
		var url = "/yx/contract/formalContractManage/formalContractInvoiceApply.action?method=doApplyBill&rclist="+getString
			+"&contractmainsid="+contractmainsid+"&billcustomersid="+billcustomersid+"&contractid="+contractid;
		openUrlCustomer(url,600,1000);
	}
	else{
		alert("请先选择实际开票和收款计划");
	}
}

function openUrlBillApply(url){
		window.open(url,null,'menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=1000');
}
</script>



<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
-->
</style>
</head>
<body>
<div align="left"> &nbsp;当前页面：合同管理 -> 正式合同管理 </div>
<table width="100%" height="100%" border="0" align="center"
			cellpadding="1" cellspacing="1" class="bg_table02">
  <tr class="bg_table02">
  
  <td colspan="4" align="center" class="bg_table02">
  
  <div id="container" class="bg_table02">
    <div align="center">
    
    <table width="100%" height="100%" border="0" align="center"
								cellpadding="1" cellspacing="1" class="bg_table02">
      <tr>
        <td align="center"><table align="center" border=0 cellpadding=1 cellspacing=1
											width="100%">
          </table></td>
      </tr>
      <tr>
        <td colspan="4" align="center" height="0.5"><img src="./../images/temp.gif" width="1" height="1"> </td>
      </tr>
      <tr class="bg_table02">
        <td colspan="4" align="center" class="bg_table02"><div id="container2" class="bg_table02">
            <div id="title" class="bg_table02">
              <div align="center" class="bg_table02">
                <ul class="bg_table02">
                  <li id="tag1"> <a href="#"
																onClick="switchTag('tag1','content1');this.blur();"><span>主体信息</span> </a> </li>
                  <li id="tag2"> <a href="#"
																onClick="switchTag('tag2','content2');this.blur();"><span>项目信息</span> </a> </li>
                  <li id="tag3"> <a href="#"
																onClick="switchTag('tag3','content3');this.blur();"><span>开票收款阶段</span> </a> </li>
                  <li id="tag4"> <a href="#"
																onClick="switchTag('tag4','content4');this.blur();"><span>实际开票收款计划</span> </a> </li>
                  <li id="tag5"> <a href="#"
																onClick="switchTag('tag5','content5');this.blur();"><span>开票信息</span> </a> </li>
                  <li id="tag6"> <a href="#"
																onClick="switchTag('tag6','content6');this.blur();"><span>收款信息</span> </a> </li>
                  <li id="tag7"> <a href="#"
																onClick="switchTag('tag7','content7');this.blur();"><span>申购信息</span> </a> </li>
                  <li id="tag8"> <a href="#"
																onClick="switchTag('tag8','content8');this.blur();"><span>外协信息</span> </a> </li>
                  <li id="tag9"> <a href="#"
																onClick="switchTag('tag9','content9');this.blur();"><span>其它</span> </a> </li>
                </ul>
              </div>
            </div>
            <div id="content4">
              <div align="center" class="scrolDiv1lTitle">
                <!--开票和收款计划开始-->
               <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
                  <tr align="center">
                    <td width="5%" class="bg_table01"><div align="center"></div></td>
                    <td width="17%" class="bg_table01" ><div align="center">收款和开票阶段</div></td>
                    <td width="9%" class="bg_table01"><div align="center">负责部门</div></td> 
                    <td width="15%" class="bg_table01" ><div align="center">开票性质</div></td>
                    <td width="12%" class="bg_table01" ><div align="center">发票类型</div></td>
                    <td width="6%" class="bg_table01" ><div align="center">基准</div></td>
                    <td width="11%" class="bg_table01" ><div align="center">计划开票时间</div></td>
                    <td width="10%" class="bg_table01" ><div align="center">开票金额</div></td>
                    <td width="9%" class="bg_table01" ><div align="center">开票确定<br>
                        收入标志</div></td>
                    <td width="6%" class="bg_table01" ><div align="left">&nbsp;统一<br>
                        &nbsp;开票</div></td>
                  </tr>
                </table>
              </div>
              <div class="scrollDiv1" id="pro">
                <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" id="rcbrptable">
                  <s:iterator  id="rcbrp" value="rcbrpList" status="rcbrpstat" >
                     <s:if test="#rcbrpstat.index!=0" >
                      <tr>
                        <td colspan="10" class="bg_table02"><hr></td>
                      </tr>
                      </s:if>
                    <tr>
                      <td width="5%" class="bg_table02"><div align="center">
                          <input type="checkbox" name="kpskjh" id="checkbox">
                        </div></td>
                  <td width="17%" class="bg_table02"><div align="center"> 
                    <s:property value="foramlContractService.getItemStageName(#rcbrp.conItemStage) "/>
                        </div></td>
                      <td width="9%" class="bg_table02"><div align="center">
                       <s:property value="foramlContractService.getResDeptName(#rcbrp.contractItemMaininfo) "/>
                     </div></td>
                      <td width="15%"  class="bg_table02"><div align="center">              
                          <s:property value="typeManageService.getYXTypeManage(1012,#rcbrp.billNature).typeName" />           
                        </div></td>
                      <td width="11%" class="bg_table02"><div align="center">
                          <s:property value="typeManageService.getYXTypeManage(1004,#rcbrp.billType).typeName"/>
                        </div></td>
                      <td width="7%" class="bg_table02"><div align="center">
                          <s:if test="#rcbrp.base ==0 ">不含税</s:if>
                          <s:elseif test="#rcbrp.base ==1"> 含税 </s:elseif>
                          <s:else> 状态错误 </s:else>
                        </div></td>
                      <td width="11%" class="bg_table02"><div align="center">
                          <s:date name="#rcbrp.realPredBillDate" format="yyyy-MM-dd" />
                        </div></td>
                      <td width="10%" class="bg_table02"><div align="center">
                          <s:property value="#rcbrp.realBillAmount" />
                        </div>
                        <div align="center"></div></td>
                      <td width="9%" class="bg_table02"><div align="center">
                          <s:if test="#rcbrp.billSureSign ==0 ">&times;</s:if>
                          <s:elseif test="#rcbrp.billSureSign ==1"> &radic; </s:elseif>
                          <s:else> 状态错误 </s:else>
                        </div></td>
                      <td width="6%" colspan="2" class="bg_table02"><div align="center">
                          <s:if test="#rcbrp.uniteBill ==0 ">&times;</s:if>
                          <s:elseif test="#rcbrp.uniteBill ==1"> &radic; </s:elseif>
                          <s:else> 状态错误 </s:else>
                        </div></td>
                      <s:hidden name="rcplan" value="${rcbrp.realConBillproSid}" />
                    </tr>
                    <s:if test = "#rcbrp.billContent != null">
                    <s:if test = "#rcbrp.billContent != ''">
                    <tr >
                      <td></td>
                      <td colspan="9" class="bg_table02"><div id="field1" align="left" >
                          <div align="left">
                            <table class="con_stage_tableborder">
                              <tr>
                                <td><strong>开票内容：</strong>
                                  <s:property value="#rcbrp.billContent" /></td>
                              </tr>
                            </table>
                          </div>
                        </div></td>
                    </tr>
                    </s:if>
                    </s:if>
                  </s:iterator>
                </table>
              </div>
              <!--开票和收款计划结束-->
            </div>
          </div>
          <div align="center">
            <input type="submit" class="button01" value="返回"
							onClick="javascript:window.history.go(-1)" />
            <input type="submit" class="button02" value="开票申请"
							onClick="trav_rcplanckbox();" />
            <input type="submit" class="button02" value="申购申请"
							onClick="openUrl('合同申购新建.htm');" />
            <input type="submit" class="button02" value="外协申请"
							onClick="openUrl('合同外协新建.html');" />
            <input type="submit" class="button02" value="开票收款计划变更"
							onClick="openUrl('开票收款计划变更.html');" />
            <input type="submit" class="button02" value="合同变更"
							onClick="openUrl('合同变更.html');" />
            <input type="submit" class="button01" value="关闭合同"
							onClick="alert('合同关闭');" />
          </div>
      </div>
      
      </td>
      
      </tr>
      
    </table>
  </div>
  </div>
  
  </td>
  
  </tr>
  
</table>
</body>
<s:debug/>
</html>
