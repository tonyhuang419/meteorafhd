<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>正式合同管理</title>
<%@ include file="/commons/jsp/formalContractMeta.jsp"%>
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
            <div id="content10"   >
                      <div align="center" class="dispalyInfo" >
                        <table width="100%" >
                          <tr>
                            <td width="20%" ><div align="right">开工报告：</div></td>
                            <td width="80%" ><div align="left">
                            <s:if test="coi.needPerativeReport==1">
                           	 		<s:if test="coi.perativeReport != null "><s:date name="coi.perativeReport" format="yyyy-MM-dd" /></s:if>
          			 			 <s:elseif test="coi.perativeReport == null "> 未录入 </s:elseif>
            						<s:else> 状态错误 </s:else>
                            </s:if>	
                            <s:else>不需要</s:else>		
</div></td>
                          </tr>
                          <tr>
                            <td ><div align="right">竣工验收证书：</div></td>
                            <td ><div align="left">
                           <s:if test="coi.needFinallyReport==1">
                            	<s:if test="coi.finallyReport != null "><s:date name="coi.finallyReport" format="yyyy-MM-dd" /></s:if>
          			 			 <s:elseif test="coi.finallyReport == null "> 未录入 </s:elseif>
            						<s:else> 状态错误 </s:else>
                            </s:if>
                             <s:else>不需要</s:else>
                            			
</div></td>
                          </tr>
                          <tr>
                            <td><div align="right">实物交接：</div></td>
                            <td><div align="left">
                             <s:if test="coi.recivedThing==1">
                             	<s:if test="coi.recivedThing != null "><s:date name="coi.recivedThing" format="yyyy-MM-dd" /></s:if>
          						  <s:elseif test="coi.recivedThing == null "> 未录入 </s:elseif>
            						<s:else> 状态出现错误 </s:else>
                             </s:if>
                              <s:else>不需要</s:else>
                             
					
</div></td>
                          </tr>
                          <tr>
                            <td align="right"><div align="right">备注：</div></td>
                          <td align="right"><div align="left"><s:property value="coi.otherRemarks" /> </div>                          </tr>
                        </table>
                      </div>
                    </div>
          </div>
          <div align="center">
            <input type="submit" class="button01" value="返回"
							onClick="javascript:window.history.go(-1)" />
            <input type="submit" class="button02" value="开票申请"
							onClick="openUrl('合同开票申请明细表.html');" />
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
