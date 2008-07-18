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
  <td colspan="4" align="center" class="bg_table02"><div id="container" class="bg_table02">
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
          <div id="content6" >
            <!--收款信息开始-->
            <div align="center" class="scrolDiv1lTitle" >
              <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
                <td width="11%" class="bg_table01"><div align="center">发票号</div></td>
                  <td width="17%" class="bg_table01"><div align="center">开票日期</div></td>
                  <td width="18%" class="bg_table01"><div align="center">开票金额</div></td>
                  <td width="19%" class="bg_table01"><div align="center">到款总金额</div></td>
                  <td width="18%" class="bg_table01"><div align="center">发票类型</div></td>
                  <td width="17%" class="bg_table01"><div align="center">到款状态</div></td>
              </table>
            </div>
            <div class="scrollDiv1" >
              <table id = "reveinfotable" width="100%">
                <s:iterator  id="ciil" value="conInvoiceInfoList" >
                  <tr>
                    <td colspan="6"><table  align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
                        <tr align="center" onClick="showlist('<s:property value="'dqmx'+ #ciil.invoiceNo" />');">
                          <td width="13%"><div align="center">
                                <s:property value="#ciil.invoiceNo" />
                            </div></td>
                          <td width="17%"><div align="center">
                              <s:date name="#ciil.invoiceDate" format="yyyy-MM-dd" />
                            </div></td>
                          <td width="18%"><div align="center">
                              <s:property value="#ciil.invoiceAmount" />
                            </div></td>
                          <td width="19%"><div align="center">
                              <s:property value="#ciil.receAmount" />
                            </div></td>
                          <td width="18%"><div align="center">
                              <s:property value="typeManageService.getYXTypeManage(1004,#ciil.type).typeName "/>
                            </div></td>
                          <td width="17%" ><div align="center">
                              <s:if test=" #ciil.receAmount == 0">无到款</s:if>
                              <s:elseif test="#ciil.invoiceAmount > #ciil.receAmount">部分到款</s:elseif>
                              <s:elseif test="#ciil.invoiceAmount == #ciil.receAmount">全部到款</s:elseif>
                              <s:else> 状态错误 </s:else>
                            </div></td>
                        </tr>
                      </table></td>
                  </tr>
                  <tr style="display:none" id="<s:property value="'dqmx'+#ciil.invoiceNo" />">
                    <td colspan="6"><table width="40%"  class="con_stage_tableborder">
                        <tr align="center">
                          <td width="35%" align="left" class="bg_table02"><div align="center"><strong>收款金额</strong></div></td>
                          <td width="35%" align="right" class="bg_table02"><div align="center"><strong>到款方式</strong></div></td>
                          <td width="30%" align="right" class="bg_table02"><div align="center"><strong>到款时间</strong></div></td>
                        </tr>
                        <s:iterator  id="crils" value="conReveInfoList" >
                          <s:iterator  id="cril" value="crils" >
                            <s:if test="#cril.billSid == #ciil.id">
                              <tr>
                                <td align="left"  class="bg_table02"><div align="center">
                                    <s:property value="#cril.amount" />
                                  </div></td>
                                <td align="right" class="bg_table02"><div align="center">
                                 <s:property value="typeManageService.getYXTypeManage(1017,#cril.receType).typeName "/>
								</div></td>
                                <td align="right" class="bg_table02"><div align="center">
                                    <s:date name="#cril.amountTime" format="yyyy-MM-dd" />
                                  </div></td>
                              </tr>
                            </s:if>
                          </s:iterator>
                        </s:iterator>
                      </table></td>
                  </tr>
                </s:iterator>
              </table>
            </div>
          </div></td>
      </tr>
    </table>
</body>
<script language="javascript">


</script>
<s:debug/>
</html>
