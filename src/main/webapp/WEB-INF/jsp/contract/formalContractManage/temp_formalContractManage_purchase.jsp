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
           <div id="content7"  >
                      <div align="center" class="scrolDiv1lTitle" >
                        <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
                          <tr align="center">
                            <td width="16%" class="bg_table01">申购单号</td>
                            <td width="16%" class="bg_table01">项目号</td>
                            <td width="16%" class="bg_table01">任务号</td>
                            <td width="16%" class="bg_table01">申购日期</td>
                            <td width="31%" class="bg_table01">申购内容</td>
                            <td width="5%" class="bg_table01">&nbsp;</td>
                          </tr>
                        </table>
                      </div>
                      <div class="scrollDiv1">
                        <table align="center" border=0 cellpadding="1" cellspacing=1 width="100%" >
                         <s:iterator  id="am" value="amList"  >
                          <tr align="center">
                            <td width="16%" class="bg_table02"><s:property value="#am.applyId" /></td>
                            <td width="16%" class="bg_table02">PBC1234</td>
                            <td width="16%" class="bg_table02"><s:property value="#am.assignmentId" /></td>
                            <td width="16%" class="bg_table02"> <s:date name="#am.applyDate" format="yyyy-MM-dd" /></td>
                            <td width="31%" class="bg_table02"><s:property value="#am.applyContent" /></td>
                            <td width="5%" class="bg_table02">&nbsp;</td>
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
