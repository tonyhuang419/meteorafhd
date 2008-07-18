<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<input type="hidden" name="currentPage" value="<s:property value="pager.currentPage"/>"/>
<s:hidden name="pagerAction"/>
共<s:property value="pager.totalRows"/>行&nbsp;
    		第<s:property value="pager.currentPage"/>页&nbsp;
    		共<s:property value="pager.totalPages"/>页&nbsp;
    		<a href="javascript:goFirst()">首页</a>
    		<a href="javascript:goPrevious()">上一页</a>
    		<a href="javascript:goNext()">下一页</a>
    		<a href="javascript:goLast()">尾页</a>
 <script language="javascript">
 function goFirst(){
	document.all.pagerAction.value='first';
	document.forms[0].submit();
 }
  function goPrevious(){
	document.all.pagerAction.value='previous';
	document.forms[0].submit();
 }
  function goNext(){
	document.all.pagerAction.value='next';
	document.forms[0].submit();
 }
  function goLast(){
	document.all.pagerAction.value='last';
	document.forms[0].submit();
 }
  function query(){
	goFirst();
 }
 </script>   		
   