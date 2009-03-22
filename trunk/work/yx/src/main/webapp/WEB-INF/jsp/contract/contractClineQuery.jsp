<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<script src="/yx/commons/scripts/validator.js?method=getClientList" type="text/javascript"></script>
<script src="/yx/commons/scripts/calender.js?method=getClientList" type="text/javascript"></script>
<script src="/yx/commons/scripts/time.js?method=getClientList" type="text/javascript"></script>
<script src="/yx/commons/scripts/public.js?method=getClientList" type="text/javascript"></script>
<link href="/yx/commons/styles/default.css?method=getClientList" type="text/css" rel="stylesheet">
<link href="/yx/commons/styles/style.css?method=getClientList" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>选择客户</title>
<SCRIPT language=JavaScript src="./js/public.js"></SCRIPT>

<script language="javascript">
	function checkString(){
		 var names=document.getElementsByName("name");
		 var id=document.getElementsByName("id");
	     var ids=document.getElementsByName("ids");
	     var name;
	     for(var i=0;i<names.length;i++){
	        if(ids[i].checked){
	             id=ids[i].value;
	             name=names[i].value;
	            
	           
	            
	             if(opener.selectedClient != null){
	             	opener.selectedClient({clientName:name,clientId:id});
	             }
	             break;
	       }
	     }  
	    this.window.close();   
    }
    function aa()
    {
           alert(document.forms(0).clientname.value);
	          location.href="../contract/searchClientOneQuery.action?method=relationTicket&clientnameone="+document.forms(0).clientname.value;

    }
</script>
<link href="./css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
			
<form id="searchClientQuery" name="searchClientOneQuery" action="/yx/searchClient/searchClientOneQuery.action" method="post">
<table class="wwFormTable">
<input type="hidden" name="method" value="getClientList" id="searchClientQuery_method"/>

	<table width="98%" border="0" cellspacing="1" cellpadding="1" class="bg_white" align="center">
		<tr>
			<td align="center">
			<table align="center" border=0 width="100%">
				<tr class="bg_table02">
					<td width="10%" align="right">请输入客户：</td>
					<td width="20%" align="left"><input name="clientname" type="text"
						size="10"/ >&nbsp;&nbsp;&nbsp;&nbsp; <input value="查询"
						type="button" class="button01"	onclick="aa()"/>
					</td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td align="center">
			<table align="center" border=0 cellpadding="1" cellspacing=1
				width="100%">
				<tr align="center">
					
					 <td width="18"  align="center" class="bg_table01"><div align="center"></div></td>
					<td width="94"  align="center" class="bg_table01"><div align="center">合同号</div></td>
          <td width="154"  align="center" class="bg_table01"><div align="center">合同名称</div></td>
          <td width="168"  align="center" class="bg_table01" ><div align="center">客户名称</div></td>
          <td width="73"  align="center" class="bg_table01" ><div align="center">销售员</div></td>

				</tr>
				      <s:iterator value="info.result" id="infoId">
<tr>  
          <td align="center"><div align="center"><input type="radio" name="operation2"  value="<s:property value="#infoId[3].contractMainSid"/>"></div></td>
          <td align="center" onClick=""><div align="center"><s:property value="#infoId[0].conId"/></div></td>
          <td align="center" onClick=""><div align="center"><s:property value="#infoId[0].conName"/></div></td>
          <td onClick=""  width="168"  align="center"  ><div align="center"><s:property value="#infoId[1].name"/></div></td>
          <td  onClick=""width="73"  align="center"><s:property value="#infoId[2].name"/></td>
          </tr>
				</s:iterator>
			</table>
			</td>
		</tr>
		  <TR>
    <TD   height=20 class="bg_table02"><DIV align=center>
       <TABLE cellSpacing="1" cellPadding="2" width="100%" border="0">
				<tr valign="top">
					<td class="bg_table04"><baozi:pages value="info"
						beanName="info" formName="forms(0)" /></td>
				</tr>
			</TABLE>
      </DIV></TD>
  </TR>
		<tr class="bg_table01">
			<td align="center" colspan="8"> <input
				class="button01" type="button" value="添  加" onclick="checkString()" /><input class="button01"
				type="button" value="关  闭" onclick="window.close()" /></td>
		</tr>
	</table>
</table></form>




</body>
</html>
