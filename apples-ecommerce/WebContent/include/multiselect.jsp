<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="my_message" value="${requestScope.recurse_message}"/>
<c:forEach var="message" items="${my_message.children}">
  
    	 <option value="${message.id}">${message.name}</option>
   	
     
     <c:choose>
   	  <c:when test="${!empty message.children}">
       <c:set var="recurse_message" value="${message}" scope="request"/>
       <c:import url="include/multiselect.jsp"/>
      </c:when>
     </c:choose>
</c:forEach>
<c:set var="recurse_message" value="${my_message}" scope="request" />