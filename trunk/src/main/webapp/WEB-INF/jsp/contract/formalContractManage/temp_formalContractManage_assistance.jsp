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
    
    <td colspan="4" align="center" class="bg_table02">
    <div id="container2" class="bg_table02">
      <div id="title" class="bg_table02">
        <div align="center" class="bg_table02">
          <ul class="bg_table02">
            <li id="tag1"><a href="#" onClick="switchTag('tag1','content1');this.blur();"><span>主体信息</span></a></li>
            <li id="tag2"><a href="#" onClick="switchTag('tag2','content2');this.blur();"><span>项目信息</span></a></li>
            <li id="tag3"><a href="#" onClick="switchTag('tag3','content3');this.blur();"><span>开票收款阶段</span></a></li>
            <li id="tag4"><a href="#" onClick="switchTag('tag4','content4');this.blur();"><span>实际开票收款计划</span></a></li>
            <li id="tag5"><a href="#" onClick="switchTag('tag5','content5');this.blur();"><span>开票信息</span></a></li>
            <li id="tag6"><a href="#" onClick="switchTag('tag6','content6');this.blur();"><span>收款信息</span></a></li>
            <li id="tag7"><a href="#" onClick="switchTag('tag7','content7');this.blur();"><span>申购信息</span></a></li>
            <li id="tag8"><a href="#" onClick="switchTag('tag8','content8');this.blur();"><span>外协信息</span></a></li>
            <li id="tag9"><a href="#" onClick="switchTag('tag9','content9');this.blur();"><span>自有产品</span></a></li>
            <li id="tag10"><a href="#" onClick="switchTag('tag10','content10');this.blur();"><span>其它</span></a></li>
          </ul>
        </div>
      </div>
      <div id="content8"  >
        <div align="center" class="scrolDiv1lTitle" >
          <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
            <tr align="center">
              <td width="13%" class="bg_table01"><div align="center">外协合同号</div></td>
              <td width="17%" class="bg_table01"><div align="center">外协合同名称</div></td>
              <td width="22%" class="bg_table01"><div align="center">外协供应商</div></td>
              <td width="18%" class="bg_table01"><div align="center">签订日期</div>
              <td width="16%" class="bg_table01"><div align="center">合同金额</div></td>
              <td width="14%" class="bg_table01">已支付金额</td>
            </tr>
            <tr>
          </table>
        </div>
        <div class="scrollDiv1">
        
        <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
          <s:iterator  id="ac" value="acList" >
            <tr align="center" onClick="showlist('<s:property value="'fksq'+ #ac.id" />');">
            <td width="13%" class="bg_table02"><div align="center">
                  <s:property value="#ac.assistanceId" />
                </div></td>
              <td width="15%" class="bg_table02"><div align="center">
                  <s:property value="#ac.assistanceName" />
                </div></td>
              <td width="24%" class="bg_table02"><div align="center">
                  <s:property value="#ac.supplyId" />
                </div></td>
              <td width="18%" class="bg_table02"><div align="center">
                  <s:date name="#ac.contractDate" format="yyyy-MM-dd" />
                </div></td>
              <td width="16%" class="bg_table02"><div align="center">
                  <s:property value="#ac.contractMoney" />
                </div></td>
              <td width="14%" class="bg_table02"><div align="center">
                  <s:property value="----" />
                </div></td>
            </tr>
            <tr style="display:none" id="<s:property value="'fksq'+#ac.id" />">
            <td colspan="6"><div align="left" >
                  <table width="61%" class="con_stage_tableborder">
                    <tr align="center">
                      <td width="23%"   align="left" class="bg_table02"><div align="center"><strong>申请序号</strong></div></td>
                      <td width="31%" align="right" class="bg_table02"><div align="center"><strong>申请金额</strong></div></td>
                      <td width="25%" align="right" class="bg_table02"><div align="center"><strong>申请时间</strong></div></td>
                      <td width="21%" align="right" class="bg_table02"><div align="center"><strong>申请状态</strong></div></td>
                    </tr>
                    <s:iterator  id="apis" value="apiList" >
                      <s:iterator  id="api" value="apis" >
                        <s:if test="#ac.id == #api.assistanceId">
                          <tr>
                            <td class="bg_table02" width="23%" align="left"><div align="center">
                                <s:property value="#api.assistanceId" />
                              </div></td>
                            <td align="right" class="bg_table02"><div align="center">
                                <s:property value="#api.payNum" />
                              </div></td>
                            <td align="right" class="bg_table02"><div align="center">
                                <s:date name="#api.applyDate" format="yyyy-MM-dd" />
                              </div></td>
                            <td align="right" class="bg_table02"><div align="center">
                                <s:if test=" #api.payState == 0">新建</s:if>
                                <s:elseif test=" #api.payState == 1">待确认</s:elseif>
                                <s:elseif test=" #api.payState == 2">确认通过</s:elseif>
                                <s:elseif test=" #api.payState == 3">确认退回</s:elseif>
                                <s:elseif test=" #api.payState == 4">付款完成</s:elseif>
                                <s:else>状态错误</s:else>
                              </div></td>
                          </tr>
                        </s:if>
                      </s:iterator>
                    </s:iterator>
                  </table>
           
        
            </td>
            
            </tr>
            
          </s:iterator>
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
