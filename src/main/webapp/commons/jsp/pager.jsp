<%@page pageEncoding="GBK" contentType="text/html; charset=GBK" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<input type="hidden" name="currentPage" value="<s:property value="pager.currentPage"/>"/>
<s:hidden name="pagerAction"/>
��<s:property value="pager.totalRows"/>��&nbsp;
    		��<s:property value="pager.currentPage"/>ҳ&nbsp;
    		��<s:property value="pager.totalPages"/>ҳ&nbsp;
    		<a href="javascript:goFirst()">��ҳ</a>
    		<a href="javascript:goPrevious()">��һҳ</a>
    		<a href="javascript:goNext()">��һҳ</a>
    		<a href="javascript:goLast()">βҳ</a>
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
   