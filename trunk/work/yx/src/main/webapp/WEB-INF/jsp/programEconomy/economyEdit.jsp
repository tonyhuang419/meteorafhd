<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<%@ include file="/commons/jsp/meta.jsp"%>

<script language="javascript"
	src="<s:url value="/commons/scripts/checkLength.js"/>">

</script>
<script language="javascript"
	src="<s:url value="/commons/scripts/select.js"/>">

</script>
<title>Stock Management</title>
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

	<s:hidden name="proe"></s:hidden>
	<input type="hidden" name="equip.projectEconomyId"
		value="<s:property value="yxosi.economy.id"/>" />
	<input type="hidden" name="projectEconomyId"
		value="<s:property value="yxosi.economy.id"/>" />
	<input type="hidden" name="bid.projectEconomyId"
		value="<s:property value="yxosi.economy.id"/>" />
	<input type="hidden" name="economyId"
		value="<s:property value="yxosi.economy.id"/>" />
	<!--   选中阶段的id		-->
	<s:hidden name="choseSectionId" id="choseSectionId" />
	
	<s:hidden name="sectionInfoId" id="myid" />
	<s:hidden name="choseSectionIdSet" id="choseid" />
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
							onclick="switchTag('tag1','content1');" class="selectli1"><span
							class="selectspan1">主体信息</span></a></li>
						<li id="tag2"><a href="#" onclick="showRecord();"><span>阶段信息</span></a></li>
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
							<s:if test="proEconomy.bidVerify==0||proEconomy.bidVerify==null">
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
							<td class="bg_table02" align="left"><s:property
								value="proEconomy.equipTotalMoney" /></td>
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
						<tr nowrap>
							<td class="bg_table02">
							<div align="center">选择</div>
							</td>
							<td class="bg_table02">
							<div align="center" nowrap>阶段名称</div>
							</td>
							<td class="bg_table02">
							<div align="center" nowrap>设计委托进度</div>
							</td>
							<td class="bg_table02">
							<div align="center">投资估计概算</div>
							</td>
							<td class="bg_table02">
							<div align="left" nowrap>BPMS录入标志</div>
							</td>
							<td class="bg_table02" nowrap>
							<div align="center">录入时间</div>
							</td>
							<td align="center" class="bg_table02" nowrap>操作</td>
						</tr>
						<!--循环输出该工程经济的阶段信息-->
						<s:iterator value="sectionInfoList">
							<tr>
								<td align="right" class="bg_table02">
								<div align="center"><input type="radio" name="sectionId"
									value="<s:property value="id"/>" id="sectionId"></div>
								</td>
								<td align="left" class="bg_table02" nowrap><!--								<s:property  value="sectionName" />-->
								<s:property
									value="typeManageService.getYXTypeManage(1011,sectionName).typeName" />

								</td>
								<td align="left" class="bg_table02" nowrap>
								<div align="center"><s:date name="designSpeed"
									format="yyyy-MM-dd" /></div>
								</td>
								<td align="right" class="bg_table02" nowrap>
								<div align="center"><s:property value="investmentCount" /></div>
								</td>
								<td align="right" class="bg_table02" nowrap><input
									type="hidden" value="<s:property value="bpmsFlag"/>"
									name="bpms" /> <s:if test="bpmsFlag==0">
									<div align="center">未录入</div>
								</s:if> <s:elseif test="bpmsFlag==1">
									<div align="center">已录入</div>
								</s:elseif> <s:else>
									<div align="center"></div>
								</s:else></td>
								<td align="right" class="bg_table02" nowrap>
								<div align="center"><s:date name="bpmsEnterTime"
									format="yyyy-MM-dd" /></div>
								</td>
								<td align="center" class="bg_table02" nowrap><a
									href='<s:url action="programEconomy" ><s:param name="method">deleteSection</s:param><s:param name="yxosi.id" value="id" />
                                 </s:url>'
									onclick="javascript:if(confirm('确实要删除吗？')){return true} else{return false}">删除</a></td>
							</tr>
						</s:iterator>
						<tr nowrap>
							<td width="72" align="right" class="bg_table02">
							<div align="center"></div>
							</td>
							<td width="73" align="right" class="bg_table02">
							<div align="center"><s:select name="ssave.sectionName"
								id="sectionListId"
								onchange="checkSection(this.options[this.selectedIndex].text)"
								list="sectionTypeManageList" listKey="typeSmall"
								listValue="typeName" emptyOption="true" required="true"
								headerValue="请选择">
							</s:select></div>
							</td>
							<td width="181" align="left" class="bg_table02" nowrap>
							<div align="center"><input type="text"
								id="ssavedesignSpeed" name="ssave.designSpeed"
								readonly="readonly" 
								size="15" /> <img src="/yx/commons/images/calendar.gif"
								onClick="javascript:ShowCalendar('ssavedesignSpeed')"
								align=absMiddle alt="" /></div>
							</td>
							<td width="95" align="right" class="bg_table02">
							<div align="center">
							<div align="center"><input type="text" id="investmentCount"
								name="ssave.investmentCount" size="10" onblur="formatInputNumber(this)"></div>
							</div>
							</td>
							<td align="right" class="bg_table02">
							<div align="center"></div>
							</td>
							<td align="right" class="bg_table02">
							<div align="center"></div>
							</td>
							<td width="88" align="left" class="bg_table02">
							<div align="center"><input type="button" value="新增"
								onclick="checkAddSection();" /></div>
							</td>
						</tr>
					</table>
					</div>
					</div>
					<div id="content2" class="hidecontent">

					<div align="center">
					<table width="100%">
						<tr>
							<td width="21%" align="right" class="bg_table02">阶段名称：</td>
							<td width="28%" align="left" class="bg_table02"><s:property
								value="typeManageService.getYXTypeManage(1011,sr.sectionName).typeName" />
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
								<div align="center"></div>
							</s:else></td>
							<td align="right" class="bg_table02">BPMS录入时间：</td>
							<td align="left" class="bg_table02"><s:date
								name="sr.bpmsEnterTime" format="yyyy-MM-dd" /></td>
						</tr>
					</table>
					<hr>

					<table width="100%">
						<tr>
							<td>
							<div align="center">
							<table width="100%">
								<tr>
									<td width="5%" class="bg_table02">
									<div align="center">&nbsp;</div>
									</td>
									<td width="13%">
									<div align="center" class="bg_table02">稿数</div>
									</td>

									<td width="18%" class="bg_table02">
									<div align="center">录入日期</div>
									</td>
									<td width="23%" class="bg_table02">
									<div align="center">费用组成</div>
									</td>
									<td width="12%" class="bg_table02">
									<div align="center">金额</div>
									</td>
									<td width="9%" class="bg_table02">
									<div align="center">操作</div>
									</td>
								</tr>









								<s:set name="maxRcord" value="0" />
								<s:iterator value="infoList" status="stat">
									<tr>
										<s:if test="infoList.size() == #stat.index + 1">
											<s:set name="maxRcord" value="id" />
										</s:if>
										<td class="bg_table02" align="center">&nbsp;</td>
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
														value="typeManageService.getYXTypeManage(1012,typeCodee).typeName" />
													</td>
													<td class="bg_table02" align="center"><s:property
														value="moneyy" /></td>
													<!--													bpms已经录入则不允许删除，添加，加稿操作-->
													<s:if test="sr.bpmsFlag==0">
													   <input type="hidden" name="4d" value="<s:property value="sr.id"/>"/>
														<td class="bg_table02" align="center"><a
															href='<s:url action="programEconomy" ><s:param name="method">deleteRecordInfo</s:param>
															<s:param name="srid" value="sr.id" />
															<s:param name="id" value="id" />
                                 </s:url>'
															onclick="javascript:if(confirm('确实要删除吗？')){return true} else{return false}">删除<s:property
															value="#maxRecord" /></a></td>
													</s:if>
													<s:else>
														<td class="bg_table02">&nbsp;</td>
													</s:else>
												</s:if>
												<s:else>
													<tr class="bg_table02">
														<td class="bg_table02">&nbsp;</td>
														<td class="bg_table02">&nbsp;</td>
														<td class="bg_table02">&nbsp;</td>
														<td class="bg_table02">
														<div align="center"><s:property
															value="typeManageService.getYXTypeManage(1012,typeCodee).typeName" /></div>
														</td>
														<td class="bg_table02">
														<div align="center"><s:property value="moneyy" /></div>
														</td>
														<s:if test="sr.bpmsFlag==0">
															<td class="bg_table02" align="center"><a
																href='<s:url action="programEconomy" ><s:param name="method">deleteRecordInfo</s:param><s:param name="id" value="id" />
                                 </s:url>'
																onclick="javascript:if(confirm('确实要删除吗？')){return true} else{return false}">删除<s:property
																value="#maxRecord" /></a></td>
														</s:if>
														<s:else>
															<td class="bg_table02">&nbsp;</td>
														</s:else>
													</tr>
												</s:else>
											</s:iterator>
										</s:if>
										<s:else>
											<s:iterator value="recordInfo" status="status">
												<s:if test="#status.index==0">
													<td class="bg_table02">
													<div align="center"><s:property
														value="typeManageService.getYXTypeManage(1012,typeCodee).typeName" /></div>
													</td>
													<td class="bg_table02">
													<div align="center"><s:property value="moneyy" /></div>
													</td>
													<td class="bg_table02">&nbsp;</td>
												</s:if>
												<s:else>
													<tr class="bg_table02">
														<td class="bg_table02">&nbsp;</td>
														<td class="bg_table02">&nbsp;</td>
														<td class="bg_table02">&nbsp;</td>
														<td class="bg_table02">
														<div align="center"><s:property
															value="typeManageService.getYXTypeManage(1012,typeCodee).typeName" /></div>
														</td>
														<td class="bg_table02">
														<div align="center"><s:property value="moneyy" /></div>
														</td>
														<td class="bg_table02">&nbsp;</td>
													</tr>
												</s:else>
											</s:iterator>
										</s:else>
									</tr>
								</s:iterator>
								<!--在搞数不存在情况下，先把为某搞增加的费用组成和金额给影藏掉d-->

								<tr>
									<s:if test="infoList.size()>0">
										<td colspan="3" bordercolor="#FFFFFF" class="bg_table02">&nbsp;</td>
										<td bordercolor="#FFFFFF" class="bg_table02">
										<div align="center"><s:select name="yxosrinfo.typeCodee"
											id="typeCodeListId" list="marryTypeManageList"
											listKey="typeSmall" listValue="typeName" emptyOption="true"
											required="true" headerValue="请选择">
										</s:select>
										</td>
										<td bordercolor="#FFFFFF" class="bg_table02">
										<div align="center"><input type="text" name="money" 
											width="50" size="20" id="m" onblur="formatInputNumber(this)"></div>
										</td>
									</s:if>

									<input type="hidden" name="sectionRecordId"
										value="<s:property value="#maxRcord"/>" />
									<s:if test="sr.bpmsFlag==0">
										<s:if test="#maxRcord!=0">
											<td bordercolor="#FFFFFF" class="bg_table02">
											<div align="center"><input type="button" onclick="checkAddMoney()"
												value="添加"></div>
											</td>
										</s:if>
										<s:else>
											<td bordercolor="#FFFFFF" class="bg_table02">&nbsp;</td>
										</s:else>
									</s:if>
									<s:else>
										<td class="bg_table02">&nbsp;</td>
									</s:else>

								</tr>

							</table>
							<hr>

							<s:if test="sr.bpmsFlag==0">
								<input type="submit" value="加稿"
									onclick="javascript:document.forms(0).action='<s:url action="programEconomy"><s:param name="method">addPapers</s:param></s:url>'" />
							</s:if> <s:else>
								<td class="bg_table02">&nbsp;</td>
							</s:else></div>
							</td>
						</tr>
					</table>

					<hr>

					</div>
					</div>
					<div id="content3" class="hidecontent">
					<table width="100%">
						<tr>
							<td align="left" class="bg_table02">
							<div align="center">
							<div align="center">
							<table width="100%">
								<tr>
									<td width="6%" align="right" class="bg_table02">
									<div align="center">序号</div>
									</td>
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
									<td width="12%" align="right" class="bg_table02">
									<div align="center">录入人</div>
									</td>
									<!--									<td width="11%" align="right" class="bg_table02">&nbsp;</td>-->
								</tr>
								<s:iterator id="equipl" value="equipList">
									<tr>
										<td align="right" class="bg_table02">
										<div align="center"><s:property value="#equipl[0].id" /></div>
										</td>
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
										<td align="right" class="bg_table02">
										<div align="center"><s:property value="#equipl[2]" /></div>
										</td>

									</tr>

								</s:iterator>

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
							<td width="6%" align="right" class="bg_table02">
							<div align="center">序号</div>
							</td>
							<td width="19%" align="right" class="bg_table02">
							<div align="center">招标文件名称</div>
							</td>
							<td width="38%" align="left" class="bg_table02">
							<div align="center">描述</div>
							<div align="center"></div>
							</td>
							<td width="21%" align="left" nowrap class="bg_table02">
							<div align="center">录入时间</div>
							</td>

							<td width="17%" align="right" class="bg_table02">
							<div align="center">录入人</div>
							</td>
							<!--							<td width="11%" align="right" class="bg_table02">&nbsp;</td>-->
						</tr>

						<s:iterator id="bidl" value="bidList">
							<tr>

								<td align="right" class="bg_table02">
								<div align="center"><s:property value="#bidl[0].id" /></div>
								</td>
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

							</tr>
						</s:iterator>

					</table>
					</div>






					</div>


					</div>

					</div>



					<div align="center"><!--					<input type="" class="button01"-->
					<!--						value=" 保    存 " /> --> <input type="button"
						name="return" value="　返   回  " class="button01"
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
function back(){
	window.location="/yx/programEconomy/programEconomyManageQuery.action";
}
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
         location.href="../programEconomy/programEconomy.action?method=queryEdit&tag=2&sectionId2="+checkStr.substring(1);
       
      }
   if(j==0){
        alert("您还没有选择阶段！");
        return;
   }
  switchTag('tag2','content2');
} 

function addmg(){
    var checkArr=document.getElementsByName("ids");
    var checkStr="";
     var j=0;
    var inid=document.getElementById("choseid").value;
    var g=document.getElementById("g").value;
    var m=document.getElementById("m").value;
  for(var i=0;i<checkArr.length;i++){
     
        if(checkArr[i].checked){
           j++; 
         checkStr=checkStr+","+checkArr[i].value;
        }
    }
  
    if(j==1)
      {  //rid阶段履历id;g费用组成;m金额
         location.href="../programEconomy/programEconomy.action?method=addMoney&tag=2&choseSectionIdGet="+inid+"&group="+g+"&money="+m+"&rid="+checkStr.substring(1);
       
      }
   if(j==0){
        alert("您还没有选择需要添加的对象！");
   }
  
} 
 //将已经选过的阶段放进js数组
 /////////////////////////
  	var existSection = new Array();
	<s:iterator value="sectionInfoList">
	existSection.push("<s:property value="typeManageService.getYXTypeManage(1011,sectionName).typeName"/>");
	</s:iterator>
/////////////////////////
	var sectionOrder=[["可研报告","可研报告（二阶段设计）"],"初步设计","基本设计"];
//////////////////////// 
	function checkFirstOrder(sectionName){
		if(!isInArray(sectionOrder[0],sectionName)){
			alert("第一阶段必须是"+sectionOrder[0][0]+" or " +sectionOrder[0][1]);
			return false;
		}
		return true;
	}
	function checkSecondToEnd(preSectionName,sectionName){
		if(preSectionName == sectionOrder[0][0] 
			&& (sectionName != sectionOrder[0][1] && sectionName != sectionOrder[1]) ){
			alert("下一阶段是"+sectionOrder[0][1]+" or " +sectionOrder[1]);
			return false;
		}
		if(preSectionName == sectionOrder[0][1] 
			&& sectionName != sectionOrder[1] ){
			alert("下一阶段是"+sectionOrder[1]);
			return false;
		}
		if(preSectionName == sectionOrder[1] 
			&& sectionName != sectionOrder[2]){
			alert("下一阶段是"+sectionOrder[2]);
			return false;
		}
		if(preSectionName == sectionOrder[2]){
			alert("已经到最后一个阶段，不能添加");
			return false;
		}
		return true;
	}
 //
 function checkSection(sectionName){
 	if(existSection.length == 0){
 		if(!checkFirstOrder(sectionName)){
 			document.getElementById("sectionListId").value="";
 		}
 	}else{
 		if(!checkSecondToEnd(existSection[existSection.length-1],sectionName)){
 			document.getElementById("sectionListId").value="";
 		}
 	}
 }
 //过滤掉已经有的阶段
 function filerSectionList(){
 	removeSelectOption(document.getElementById("sectionListId"),existSection);
 }
 //调用过滤方法
 filerSectionList();
 
///////////////////////////////////////////////验证
//新增阶段时对其验证367
function checkAddSection(){
	
	if(!validate1()){
    document.forms(0).action='<s:url value="/programEconomy/programEconomy.action?method=saveSection"/>';
	document.forms(0).submit();
	}
}
function validate1()
	{
		var ev = new Validator();
        with(programEconomy){
            ev.test("notblank", "设计委托进度不能为空", document.getElementById('ssavedesignSpeed').value);
            ev.test("notblank", "阶段名称不能为空", document.getElementById('sectionListId').value);
		    ev.test("float", "投资估概算应为数字型", document.getElementById('investmentCount').value  );
		   }
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
  }

//新增费用组成和金额时对其验证540
function checkAddMoney(){
	
	if(!validate2()){
    document.forms(0).action='<s:url value="/programEconomy/programEconomy.action?method=addMoney"/>';
	document.forms(0).submit();
	}
}
function validate2()
	{
		var ev = new Validator();
        with(programEconomy){
            ev.test("notblank", "费用组成不能为空", document.getElementById('typeCodeListId').value);
            ev.test("float", "金额应为数字型", document.getElementById('m').value  );
		   }
		if (ev.size() > 0) {
			ev.writeErrors(errorsFrame, "errorsFrame");
			return true;
		}
  }
</script>

