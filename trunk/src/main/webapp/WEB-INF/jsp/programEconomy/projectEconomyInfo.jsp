<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>

<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">

</script>
<title>Stock Management</title>
<link href="./css/style.css" rel="stylesheet" type="text/css">
<link href="<s:url value="/commons/styles/ka.css"/>" rel="stylesheet"
	type="text/css">
<script language="javascript">
function give(){
	 var names=document.getElementsByName("setionName");
	 var speeds=document.getElementsByName("designSpeed");
	 var investments=document.getElementsByName("investmentCount");
	 var bpmss=document.getElementsByName("bpms");
	 var enterTime=document.getElementsByName("bpmsEnterTime");
     var sid=document.getElementsByName("sectionId");
     var name;
     var name2;
     var name3;
     var name4;
     var name5;
     var id;
    for(var i=0;i<names.length;i++){
      if(sid[i].checked){
        name=names[i].value;
        document.getElementById("myid").value=name;
     }
  }
    for(var i=0;i<speeds.length;i++){
       if(sid[i].checked){
       name2=speeds[i].value;
       document.getElementById("speedid").value=name2;
      }
   }
   
   for(var i=0;i<investments.length;i++){
      if(sid[i].checked){
      name3=investments[i].value;
      document.getElementById("investmeid").value=name3;
      }
   }
   
   for(var i=0;i<bpmss.length;i++){
      if(sid[i].checked){
      name4=bpmss[i].value;
      if(name4==0){
         document.getElementById("bpm").value="未录入";
      }
      else{
          if(name4==1){
         document.getElementById("bpm").value="已录入";   
          }
      }
     
      }
   }
   
   for(var i=0;i<enterTime.length;i++){
      if(sid[i].checked){
      name5=enterTime[i].value;
      document.getElementById("bpmEnter").value=name5;
      }
   }
}
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}  
 
function switchTag(tag,content)
{

	for(i=1; i <5; i++)
	{
		if ("tag"+i==tag)
		{
			document.getElementById(tag).getElementsByTagName("a")[0].className="selectli"+i;
			document.getElementById(tag).getElementsByTagName("a")[0].getElementsByTagName("span")[0].className="selectspan"+i;
		}else{
			document.getElementById("tag"+i).getElementsByTagName("a")[0].className="";
			document.getElementById("tag"+i).getElementsByTagName("a")[0].getElementsByTagName("span")[0].className="";
		}
		if ("content"+i==content)
		{
			document.getElementById(content).className="";
		}else{
			document.getElementById("content"+i).className="hidecontent";
		}
		document.getElementById("content").className=content;
	}
}  
  </SCRIPT>


</head>
<s:if test="tag==1">

	<body onLoad="switchTag('tag1','content1');">
</s:if>
<s:if test="tag==2">

	<body onLoad="switchTag('tag2','content2');">
</s:if>
<s:if test="tag==3">

	<body onLoad="switchTag('tag3','content3');">
</s:if>
<s:if test="tag==4">

	<body onLoad="switchTag('tag4','content4');">
</s:if>

<iframe name="errorsFrame" frameborder="0" framespacing="0" height="0"
	width="100%" scrolling="no"></iframe>
<s:form action="programEconomy" theme="simple">

	<input type="hidden" name="equip.projectEconomyId"
		value="<s:property value="proEconomy.id"/>" />
	<input type="hidden" name="bid.projectEconomyId"
		value="<s:property value="proEconomy.id"/>" />

	<input type="hidden" name="economyId"
		value="<s:property value="yxosi.economy.id"/>" />
	<!--   选中阶段的id		-->
	<s:hidden name="choseSectionId" id="choseSectionId" />
    <s:hidden name="lastChoseSectionId" id="lastChoseSectionId"></s:hidden>  
	<s:hidden name="sectionInfoId" id="myid" />
	<s:hidden name="pubSectionId" />
	<table width="100%" border="0" cellspacing="1" cellpadding="1"
		class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border="0" cellpadding="1" cellspacing="1"
				width="100%">

				<tr>
					<td colspan="4" align="center" class="bg_table01" height="0.5"><img
						src="./../images/temp.gif" width="1" height="1"></td>
				</tr>
				<td colspan="4" align="center" class="bg_table03">&nbsp;</td>
				<tr class="bg_table02">
					<td colspan="4" align="center" class="bg_table02">
					<body>
					<div id="container">
					<div id="title">

					<div align="center">
					<ul>
						<li id="tag1"><a href="#"
							onclick="switchTag('tag1','content1');"
							class="selectli1"><span class="selectspan1">主体信息</span></a></li>
						<li id="tag2"><a href="#"
							onclick="showRecord();"><span>阶段信息</span></a></li>

						<li id="tag3"><a href="#"
							onclick="switchTag('tag3','content3');"><span>设备信息</span></a></li>
						<li id="tag4"><a href="#"
							onclick="switchTag('tag4','content4');"><span>招标文件信息</span></a></li>
					</ul>
					</div>
					</div>

					<!--主体信息-->
					<div id="content" class="content1">
					<div id="content1">

					<div align="center">
					<table>
						<tr class="bg_table02">
							<td width="300" class="bg_table02" align="right">工程编号：</td>
							<td width="300" class="bg_table02" align="left"><s:property
								value="proEconomy.projectNumber" /></td>
							<td colspan="2" align="right" class="bg_table02">&nbsp;</td>
						</tr>
						<tr>
							<td align="right" class="bg_table02">项目名称：</td>
							<td align="left" class="bg_table02"><s:property
								value="proEconomy.projectName" /></td>
							<td align="left" class="bg_table02">
							<div align="right">项目联系人（电话）：</div>
							</td>
							<td align="left" class="bg_table02"><s:property
								value="proEconomy.projectLMP" /></td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">投资匡算：</td>
							<td class="bg_table02" align="left"><s:property
								value="proEconomy.investment" /></td>
							<td width="300" align="right" class="bg_table02">跟踪销售人员：</td>
							<td width="300" align="left" class="bg_table02"><s:property
								value="exp.name" /></td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">项目编号（甲方）：</td>
							<td class="bg_table02" align="left"><s:property
								value="proEconomy.projectNumberJ" /></td>
							<td align="right" class="bg_table02">&nbsp;</td>
							<td align="left" class="bg_table02">&nbsp;</td>
						</tr>
						<tr>
							<td align="right" nowrap class="bg_table02">客户项目单位：</td>
							<td class="bg_table02" align="left"><s:property
								value="proEconomy.clientFactory" /></td>
							<td align="right" nowrap class="bg_table02">客户项目负责人（电话）：</td>
							<td class="bg_table02" align="left"><s:property
								value="proEconomy.clientOrderP" /></td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">客户管理部门：</td>
							<td class="bg_table02" align="left"><s:property
								value="proEconomy.clientManageDep" /></td>
							<td align="right" nowrap class="bg_table02">管理部门联系人（电话）：</td>
							<td align="left" class="bg_table02"><s:property
								value="proEconomy.manageDepLMP" /></td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">招标文件录入标志：</td>
							<s:if test="proEconomy.bidVerify==0">
								<td class="bg_table02" align="left">未录入</td>
							</s:if>
							<s:elseif test="proEconomy.bidVerify==1">
								<td class="bg_table02" align="left">已录入</td>
							</s:elseif>
							<s:else>
								<td class="bg_table02" align="left"></td>
							</s:else>
							<td align="right" class="bg_table02">招标文件录入时间：</td>
							<td align="left" class="bg_table02"><s:date
								name="proEconomy.enterTime" format="yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<td class="bg_table02" align="right">设备总表录入标志：</td>

							<s:if test="proEconomy.enterFlag==0">

								<td class="bg_table02" align="left">未录入</td>
							</s:if>
							<s:elseif test="proEconomy.enterFlag==1">
								<td class="bg_table02" align="left">已录入</td>
							</s:elseif>
							<s:else>
								<td class="bg_table02" align="left"></td>
							</s:else>

							<td align="right" class="bg_table02">设备总表录入时间：</td>
							<td align="left" class="bg_table02"><s:date
								name="proEconomy.equipEnterTime" format="yyyy-MM-dd" /></td>
						</tr>

						<tr>
							<td class="bg_table02" align="right">设备总表金额：</td>
							<td class="bg_table02" align="left"><input type="text" id="equipTotalMoney"
								name="proEconomy.equipTotalMoney"
								value="<s:property value="proEconomy.equipTotalMoney"/>"
								size="15"></td>
							<s:if test="proEconomy.equipTotalMoney==null">
								<s:hidden name="moneyState" value="no" />
							</s:if>
							<td align="right" class="bg_table02">&nbsp;</td>
							<td align="left" class="bg_table02">&nbsp;</td>
						</tr>
					</table>
					<hr>
					<div align="left">阶段信息：</div>
					<table width="100%">
						<tr>
							<td class="bg_table02">
							<div align="center">选择</div>
							</td>
							<td class="bg_table02">
							<div align="center">阶段名称</div>
							</td>
							<td class="bg_table02">
							<div align="center">设计委托进度</div>
							</td>
							<td class="bg_table02">
							<div align="center">投资估计概算</div>
							</td>
							<td class="bg_table02">
							<div align="center">BPMS录入标志</div>
							</td>
							<td class="bg_table02">
							<div align="center">录入时间</div>
							</td>
						</tr>
						<!--循环输出该工程经济的阶段信息-->
						<s:iterator value="sectionInfoList">
							<tr>
								<td align="right" class="bg_table02">
								<div align="center"><input type="radio" name=""
									value="<s:property value="id"/>" id="sectionId"></div>
								</td>
								<td align="left" class="bg_table02">
<!--								<s:property value="sectionName" />-->
								<s:property value="typeManageService.getYXTypeManage(1011,sectionName).typeName" />
								</td>
								<td align="left" class="bg_table02">
								<div align="center"><s:date name="designSpeed"
									format="yyyy-MM-dd" /></div>
								</td>
								<td align="right" class="bg_table02">
								<div align="center"><s:property value="investmentCount" /></div>
								</td>
								<td align="right" class="bg_table02"><input type="hidden"
									value="<s:property value="bpmsFlag"/>" name="bpms" /> <s:if
									test="bpmsFlag==0">
									<div align="center">未录入</div>
								</s:if> <s:elseif test="bpmsFlag==1">
									<div align="center">已录入</div>
								</s:elseif> <s:else>
									<div align="center"></div>
								</s:else></td>
								<td align="right" class="bg_table02">
								<div align="center"><s:date name="bpmsEnterTime"
									format="yyyy-MM-dd" /></div>
								</td>
							</tr>
						</s:iterator>
					</table>
					</div>
					</div>
					<div id="content2" class="hidecontent">

					<div align="center">
					<table width="100%">
						<s:if test="sr!=null">
							<tr>
								<td width="21%" align="right" class="bg_table02">阶段名称：</td>
								<td width="28%" align="left" class="bg_table02">
<!--								<s:property value="sr.sectionName" />-->
									<s:property value="typeManageService.getYXTypeManage(1011,sr.sectionName).typeName" />
									</td>
								<td width="26%" align="right" class="bg_table02">&nbsp;</td>
								<td width="25%" align="left" class="bg_table02">&nbsp;</td>
							</tr>
							<tr>
								<td align="right" class="bg_table02">设计委托进度：</td>
								<td align="left" class="bg_table02"><s:date
									name="sr.designSpeed" format="yyyy-MM-dd" /></td>
								<td align="right" class="bg_table02">投资估概算：</td>
								<td align="left" class="bg_table02"><s:property
									value="sr.investmentCount" /></td>
							</tr>
							<tr>
								<td align="right" class="bg_table02">BPMS录入标志：</td>
								<td align="left" class="bg_table02"><s:if
									test="sr.bpmsFlag==0">
									<div align="left">未录入</div>
								</s:if> <s:elseif test="sr.bpmsFlag==1">
									<div align="left">已录入</div>
								</s:elseif> <s:else>
									<div align="left"></div>
								</s:else></td>
								<td align="right" class="bg_table02">BPMS录入时间：</td>
								<td align="left" class="bg_table02"><s:date
									name="sr.bpmsEnterTime" format="yyyy-MM-dd" /></td>
							</tr>
						</s:if>
						<s:if test="srr!=null">
							<tr>
								<td width="21%" align="right" class="bg_table02">阶段名称：</td>
								<td width="28%" align="left" class="bg_table02">
								
<!--								<s:property value="srr.sectionName" />-->
								<s:property value="typeManageService.getYXTypeManage(1011,srr.sectionName).typeName" />
								</td>
								<td width="26%" align="right" class="bg_table02">&nbsp;</td>
								<td width="25%" align="left" class="bg_table02">&nbsp;</td>
							</tr>
							<tr>
								<td align="right" class="bg_table02">设计委托进度：</td>
								<td align="left" class="bg_table02"><s:date
									name="srr.designSpeed" format="yyyy-MM-dd" /></td>
								<td align="right" class="bg_table02">投资估概算：</td>
								<td align="left" class="bg_table02"><s:property
									value="srr.investmentCount" /></td>
							</tr>
							<tr>
								<td align="right" class="bg_table02">BPMS录入标志：</td>
								<td align="left" class="bg_table02"><s:if
									test="srr.bpmsFlag==0">
									<div align="left">未录入</div>
								</s:if> <s:elseif test="srr.bpmsFlag==1">
									<div align="left">已录入</div>
								</s:elseif> <s:else>
									<div align="left"></div>
								</s:else></td>
								<td align="right" class="bg_table02">BPMS录入时间：</td>
								<td align="left" class="bg_table02"><s:date
									name="srr.bpmsEnterTime" format="yyyy-MM-dd" /></td>
							</tr>
						</s:if>

					</table>
					<hr>

					<table width="100%">
						<tr>
							<td>
							<div align="center">
							<table width="100%">
								<tr>

									<td width="13%">
									<div align="center" class="bg_table02">稿数</div>
									</td>

									<td width="25%" class="bg_table02">
									<div align="center">录入日期</div>
									</td>
									<td width="24%" class="bg_table02">
									<div align="center">费用组成</div>
									</td>
									<td width="15%" class="bg_table02">
									<div align="center">金额</div>
									</td>
								</tr>



								<s:set name="maxRcord" value="0" />
								<s:iterator value="infoList" status="stat">
									<tr>
										<s:if test="infoList.size() == #stat.index + 1">
											<s:set name="maxRcord" value="id" />
										</s:if>

										<td class="bg_table02">
										<div align="center">第<s:property value="draftCount" />稿</div>
										</td>
										<td class="bg_table02">
										<div align="center"><s:date name="enterTime"
											format="yyyy-MM-dd" /></div>
										</td>

										<s:if test="infoList.size() == #stat.index + 1">
											<s:iterator value="recordInfo" status="status">
												<s:if test="#status.index==0">
													<td class="bg_table02" align="center"><s:property
														value="typeCodee" /></td>
													<td class="bg_table02" align="center"><s:property
														value="moneyy" /></td>

												</s:if>
												<s:else>
													<tr class="bg_table02">

														<td class="bg_table02">&nbsp;</td>
														<td class="bg_table02">&nbsp;</td>
														<td class="bg_table02">
														<div align="center"><s:property value="typeCodee" /></div>
														</td>
														<td class="bg_table02">
														<div align="center"><s:property value="moneyy" /></div>
														</td>

													</tr>
												</s:else>
											</s:iterator>
										</s:if>
										<s:else>
											<s:iterator value="recordInfo" status="status">
												<s:if test="#status.index==0">
													<td class="bg_table02">
													<div align="center"><s:property value="typeCodee" /></div>
													</td>
													<td class="bg_table02">
													<div align="center"><s:property value="moneyy" /></div>
													</td>

												</s:if>
												<s:else>
													<tr class="bg_table02">

														<td class="bg_table02">&nbsp;</td>
														<td class="bg_table02">&nbsp;</td>
														<td class="bg_table02">
														<div align="center"><s:property value="typeCodee" /></div>
														</td>
														<td class="bg_table02">
														<div align="center"><s:property value="moneyy" /></div>
														</td>

													</tr>
												</s:else>
											</s:iterator>
										</s:else>
									</tr>
								</s:iterator>


							</table>
							</div>
							</td>
						</tr>
					</table>

					<hr>
					<s:hidden name="sr.bpmsFlag"></s:hidden> <s:if test="bpmsF==2">
						<s:if test="srr.bpmsFlag==0">
						      <s:if test="infoList.size()>0">
							   <input type="submit" value="BPMS录入确认ssr"
								onclick="javascript:document.forms(0).action='<s:url action="programEconomy"><s:param name="method">bpmsVerifyLast</s:param></s:url>'" />
						       </s:if>
						</s:if>
						<s:if test="srr.bpmsFlag==1">
						        <s:if test="infoList.size()>0">
							       <input type="submit" value="BPMS录入取消ssr"
								     onclick="javascript:document.forms(0).action='<s:url action="programEconomy"><s:param name="method">bpmsVerifyLast</s:param><s:param name="bpmsDelete">bpmsDelete</s:param></s:url>'" />
						         </s:if>
						</s:if>
					</s:if> <s:elseif test="bpmsF!=2 && bpmsF!=3">
						<s:if test="sr.bpmsFlag==0">
						    <s:if test="infoList.size()>0"> 
							<input type="submit" value="BPMS录入确认sr"
								onclick="javascript:document.forms(0).action='<s:url action="programEconomy"><s:param name="method">bpmsVerify</s:param></s:url>'" />
						    </s:if>      
						</s:if>
						<s:elseif test="sr.bpmsFlag==1 && infoList.size()>0">
						   
							<input type="submit" value="BPMS录入取消sr"
								onclick="javascript:document.forms(0).action='<s:url action="programEconomy"><s:param name="method">bpmsVerify</s:param><s:param name="bpmsDelete">bpmsDelete</s:param></s:url>'" />
						     
						</s:elseif>
					</s:elseif> <s:else>
					</s:else></div>
					</div>
					<div id="content3" class="hidecontent">
					<table width="100%">
						<tr>
							<td align="left" class="bg_table02">
							<div align="center">
							<div align="center">
							<table width="100%">
								<tr>

									<td width="16%" align="right" class="bg_table02">
									<div align="center">清单名称</div>
									</td>
									<td width="14%" align="left" class="bg_table02">
									<div align="center">录入时间</div>
									</td>
									<td width="14%" align="left" nowrap class="bg_table02">
									<div align="center">起始设备编号</div>
									</td>
									<td width="17%" align="left" nowrap class="bg_table02">
									<div align="center">结束设备编号</div>
									</td>
									<td width="10%" align="left" nowrap class="bg_table02">
									<div align="center">合计次数</div>
									</td>
									<td  nowrap width="12%" align="right" class="bg_table02">
									<div align="center">录入人</div>
									</td>
									<td width="11%" align="right" class="bg_table02">操作</td>
								</tr>

								<s:iterator id="equipl" value="equipList" status="status">
									<tr>
										<td align="right" class="bg_table02">
										<div align="center"><s:property
											value="#equipl[0].listName" /></div>
										</td>
										<td align="left" class="bg_table02">
										<div align="center"><s:date name="#equipl[0].enterTime"
											format="yyyy-MM-dd" /></div>
										</td>
										<td align="left" class="bg_table02">
										<div align="center"><s:property
											value="#equipl[0].sequipNumber" /></div>
										</td>
										<td align="left" class="bg_table02">
										<div align="center"><s:property
											value="#equipl[0].eequipNumber" /></div>
										</td>
										<td align="left" class="bg_table02">
										<div align="center"><s:property value="#equipl[0].total" /></div>
										</td>
										<td align="right" class="bg_table02" nowrap>
										<div align="center"><s:property value="#equipl[2]" /></div>
										</td>
										<s:if test="equipList.size() == #status.index + 1">
											<td align="center" class="bg_table02">
											<div align="center"><a
												href='<s:url action="programEconomy" ><s:param name="method">deletes</s:param><s:param name="equip.id" value="#equipl[0].id" />
                                 </s:url>'
												onclick="javascript:if(confirm('确实要删除吗？')){return true} else{return false}">删除</a>

											</div>
											</td>
										</s:if>

										<s:set name="equipid" value="id" />
									</tr>

								</s:iterator>
								<tr>


									<td align="right" class="bg_table02">
									<div align="center"><input type="text" id="listName"
										name="equip.listName" size="15"></div>
									</td>

									<td align="left" nowrap class="bg_table02">
									<div align="center"><input type="text"
										id="equipenterTime" name="equip.enterTime" readonly="readonly"
										onClick="javascript:ShowCalendar(this.id)" size="12" /> <img
										src="/yx/commons/images/calendar.gif"
										onClick="javascript:ShowCalendar('equipenterTime')"
										align=absMiddle alt="" />
									</td>
									<td align="left" class="bg_table02">
									<div align="center">
									<div align="center"><input type="text" id="sequipNumber"
										name="equip.sequipNumber" size="15"></div>
									</div>
									</td>
									<td align="left" class="bg_table02">
									<div align="center">
									<div align="center"><input type="text" id="eequipNumber"
										name="equip.eequipNumber" size="15"></div>
									</div>
									</td>
									<td align="left" class="bg_table02">
									<div align="center">
									<div align="center"><input type="text" name="equip.total" id="total"
										size="7"></div>
									</div>
									</td>
									<td align="right" class="bg_table02">&nbsp;</td>
									<td align="right" class="bg_table02">
									<div align="center"><input type="button" value="新增"
										onclick="checkSaveEquip()" />
									</div>
									</td>
								</tr>
							</table>
							</div>
							</div>
							</td>
						</tr>
					</table>
					</div>

					<!--                 招标文件tag4-->
					<div id="content4" class="hidecontent">
					<table width="100%" id="tab1">
						<tr>
							<!--							<td width="6%" align="right" class="bg_table02">-->
							<!--							<div align="center">序号</div>-->
							<!--							</td>-->
							<td width="19%" align="right" class="bg_table02">
							<div align="center">招标文件名称</div>
							</td>
							<td width="30%" align="left" class="bg_table02">
							<div align="center">描述</div>
							<div align="center"></div>
							</td>
							<td width="18%" align="left" nowrap class="bg_table02">
							<div align="center">录入时间</div>
							</td>

							<td width="14%" align="right" class="bg_table02">
							<div align="center">录入人</div>
							</td>
							<td width="11%" align="center" class="bg_table02">操作</td>
						</tr>

						<s:iterator id="bidl" value="bidList">
							<tr>

								<td align="right" class="bg_table02">
								<div align="center"><s:property value="#bidl[0].bidName" /></div>
								</td>
								<td align="left" class="bg_table02">
								<div align="center"><s:property value="#bidl[0].desc" /></div>
								<div align="center"></div>
								</td>
								<td align="left" class="bg_table02">
								<div align="center"><s:date name="#bidl[0].enterTime"
									format="yyyy-MM-dd" /></div>
								</td>

								<td align="right" class="bg_table02">
								<div align="center"><s:property value="#bidl[2]" /></div>
								</td>
								<td align="right" class="bg_table02">
								<div align="center"><a
									href='<s:url action="programEconomy" ><s:param name="method">deletess</s:param><s:param name="bid.id" value="#bidl[0].id" />
                                
                                 </s:url>'
									onclick="javascript:if(confirm('确实要删除吗？')){return true} else{return false}">删除</a>
								</div>
								</td>
							</tr>
						</s:iterator>

						<tr>

							<td align="right" class="bg_table02">
							<div align="center"><input type="text" size="15" id="bidName"
								name="bid.bidName" /></div>
							</td>

							<td align="left" nowrap class="bg_table02">
							<div align="center">
							<div align="center"><input type="text" name="bid.desc" id="bidDesc"
								size="25"></div>
							</div>
							</td>
							<td align="left" nowrap class="bg_table02"><input
								type="text" id="bidenterTime" name="bid.enterTime"
								readonly="readonly" onClick="javascript:ShowCalendar(this.id)"
								size="12" /> <img src="/yx/commons/images/calendar.gif"
								onClick="javascript:ShowCalendar('bidenterTime')"
								align=absMiddle alt="" /></td>
							<td align="right" class="bg_table02">
							<div align="center"></div>
							</td>
							<td width="8%" align="right" class="bg_table02">
							<div align="center"><input type="button" value="新增"
								onclick="checkSaveBid()" />
							</div>
							</td>
						</tr>


					</table>
					</div>






					</div>


					</div>

					</div>



					<div align="center"><input type="button" class="button01"
						onclick="checkAddBpmsAndEquip()"
						value=" 保    存 " /> <input type="button" name="return"
						value="　返   回  " class="button01"
						onclick="back()"></div>
					</div>
					</td>
				</tr>

				<tr align="center">
					<td colspan="4" class="bg_table03">&nbsp;</td>
				</tr>
			</table>

			<table width="100%" border="0" align="center">
			</table>
			</div>
			</DIV>
			<DIV class=child id=KB2Child>
			<DIV class=parent id=KB3Parent>
			<div align="right"></div>
			</DIV>
			</DIV>
			</TD>
		</TR>
		</TBODY>
	</TABLE>
	</TD>
	</TR>
	</TABLE>
	</TD>
	</TR>
	</TBODY>
	</TABLE>

	<TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
		<tr valign="top">
			<td class="bg_table04">
			<div align="center"></div>
			</td>
		</tr>
	</TABLE>
	</td>
	</tr>
	</table>
	<p>&nbsp;</p>
	<p>&nbsp;</p>

</s:form>
</body>
</html>
<Script Language="Javascript">

function showRecord(){
    var checkArr=document.getElementsByName("sectionId");
    var checkStr="";
     var j=0;
   
  for(var i=0;i<checkArr.length;i++){
     
        if(checkArr[i].checked){
           j++; 
         checkStr=checkStr+","+checkArr[i].value;
        }
    }
  
    if(j==1)
      {  
         location.href="../programEconomy/programEconomy.action?method=queryOrder&tag=2&sectionId2="+checkStr.substring(1);
       
      }
   if(j==0){
        alert("您还没有选择阶段！");
   return;
   }
  switchTag('tag2','content2');
  
} 

//新增设备总表金额时对其验证721
function checkAddBpmsAndEquip(){
	
	if(!validate()){
    document.forms(0).action='<s:url value="/programEconomy/programEconomy.action?method=bpmsAndEquip"/>';
	document.forms(0).submit();
	}
}
function back(){
	window.location="/yx/programEconomy/enterManageQuery.action";
}
function validate()
	{
		var ev = new Validator();
        with(programEconomy){
            ev.test("float", "设备总表金额应为数字型", document.getElementById('equipTotalMoney').value  );
		   }
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
  }
  //新增设备信息时验证603
  function checkSaveEquip(){
	
	if(!validate2()){
    document.forms(0).action='<s:url value="/programEconomy/programEconomy.action?method=saveEquip"/>';
	document.forms(0).submit();
	}
}
function validate2()
	{
		var ev = new Validator();

        with(programEconomy){
            ev.test("integer", "合计次数应为整数", document.getElementById('total').value  );
            ev.test("notblank", "清单名称不能为空", document.getElementById('listName').value);
            ev.test("notblank", "录入时间不能为空", document.getElementById('equipenterTime').value);
            ev.test("notblank", "起始设备编号不能为空", document.getElementById('sequipNumber').value);
            ev.test("notblank", "结束设备编号不能为空", document.getElementById('eequipNumber').value);
		   }
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
  }
  
  //新增招标文件时验证728
  function checkSaveBid(){
	
	if(!validate3()){
    document.forms(0).action='<s:url value="/programEconomy/programEconomy.action?method=saveBid"/>';
	document.forms(0).submit();
	}
}
function validate3()
	{
		var ev = new Validator();
        with(programEconomy){
            ev.test("notblank", "招标文件不能为空", document.getElementById('bidName').value);
            ev.test("notblank", "描述不能为空", document.getElementById('bidDesc').value);
            ev.test("notblank", "录入时间不能为空", document.getElementById('bidenterTime').value);
           
		   }
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
  }
</script>

