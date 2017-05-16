<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="my_message" value="${requestScope.recurse_message}"/>
<c:set var="categoryId" value="${requestScope.catId}"/>
<ul class="subnav">
<c:forEach var="message" items="${my_message.children}">
  <li>
  	
  	<c:choose>
   	   <c:when test="${categoryId == message.id}">	
    	 <a class="active" href="overview.jsp?cat_id=${message.id}">${message.name}</a>
   	   </c:when>
   	   <c:otherwise>
   	     <a href="overview.jsp?cat_id=${message.id}">${message.name}</a>
   	   </c:otherwise>
      </c:choose> 
  
     
     <c:choose>
   	  <c:when test="${!empty message.children}">
   	   <c:set var="catId" value="${categoryId}" scope="request"/>
       <c:set var="recurse_message" value="${message}" scope="request"/>
       <c:import url="include/recurse.jsp"/>
      </c:when>
     </c:choose>
  </li>
</c:forEach>
</ul>
<c:set var="recurse_message" value="${my_message}" scope="request" />