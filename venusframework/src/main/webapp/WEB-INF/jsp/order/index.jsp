<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/jsp/base.jsp"%>
<html>
<head>
<title>test</title>
</head>
<body>
     <a href="order">List</a>  
     <a href="order/new">Add User</a>  
     <table border="0">  
         <s:iterator value="list" id="u">  
             <tr>  
                 <td><s:property value="#u.orderNum"/></td>  
                 <td>  
                     <a href="order/${u.id}/edit">Edit</a>  
                     <span>|</span>  
                     <a href="order/${u.id}?_method=DELETE">Delete</a>  
                 </td>  
             </tr>  
         </s:iterator>  
     </table> 
</body>
</html>