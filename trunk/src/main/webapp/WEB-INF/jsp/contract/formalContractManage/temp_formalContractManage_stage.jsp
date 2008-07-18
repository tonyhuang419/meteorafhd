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
            <div id="content3" >
                      <div align="center" class="scrolDiv1lTitle">
                        <!--开票和收款阶段开始-->
                        <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
                          <tr>
                            <td width="20%" class="bg_table01"><div align="center">预计完工日期</div></td>
                            <td width="20%" class="bg_table01"><div align="center">收款和开票阶段</div></td>
                            <td width="20%" class="bg_table01"><div align="center">预计开票日期</div></td>
                            <td width="20%" class="bg_table01" ><div align="center">预计收款日期</div></td>
                            <td width="12%"  class="bg_table01"><div align="center">阶段金额</div></td>
                            <td width="8%" class="bg_table01"><div align="center">尾款标志</div></td>
                          </tr>
                        </table>
                      </div>
                      <div class="scrollDiv1" id="stage">
                        <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%">
                         <s:iterator  id="cis" value="cisList" status="cisistat" >
                    <s:if test="#cisistat.index!=0" >
                       <tr>
                            <td colspan="7"><hr></td>
                          </tr>
                    </s:if>
                          <tr>
                            <td width="20%"  class="bg_table02"><div align="center"><s:date name="#cis.ItemStagePredFdate" format="yyyy-MM-dd"/></div></td>
                            <td width="20%"  class="bg_table02" ><div align="center"><s:property value="#cis.ItemStageName" /></div></td>
                            <td width="20%"  class="bg_table02"><div align="center"><s:date name="#cis.initBillPdate" format="yyyy-MM-dd" /></div></td>
                            <td width="20%" class="bg_table02"><div align="center"><s:date name="#cis.initRecePdate" format="yyyy-MM-dd"/></div></td>
                            <td width="12%" class="bg_table02"><div align="center"><s:property value="#cis.stageAmout" /></div></td>
                           <td width="8%" class="bg_table02 STYLE3"><div align="center">
                          <s:if test="#cis.lastAmount == 0" > &times; </s:if>
                          <s:elseif test="#cis.lastAmount == 1"> &radic; </s:elseif>
                          <s:else> 状态错误 </s:else>
                        </div></td>
                          </tr>
                         
                          </s:iterator>
                        </table>
                      </div>
                      <!--开票和收款阶段结束-->
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
