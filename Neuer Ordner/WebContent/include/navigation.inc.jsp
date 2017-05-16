<%@page import="interfaces.CategoryIntProxy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <div id="nav">
 <% 
 CategoryIntProxy cip = new CategoryIntProxy();
 String[] categories = cip.getCategories();%>
  	<ul>
 	  <li>
 	 <a class="active" style="color:black;  font-weight: bold; font-size: 160%;"><strong>CATEGORIES</strong></a>
 	  </li>  
    </ul>
 <%
 for (String category : categories){
 	%>
 	<ul>
 	  <li>
 	  <a class="active"><%out.print(category); %></a>
 	  </li>  
    </ul>
 	<% 
}
 %>

 </div>  


 