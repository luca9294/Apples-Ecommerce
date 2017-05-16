<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="login" value="${param.login}"/>
<c:set var="password" value="${param.password}"/>
 
 <%
   db.Client newClient = new db.Client();
   String login = (String) pageContext.getAttribute("login");
   String password = (String) pageContext.getAttribute("password");

   newClient = newClient.login(login,password);
   if(newClient != null)
   {
	   session.setAttribute("loggedIn", true);
	   session.setAttribute("clientID", newClient.getId());
	   session.setAttribute("clientObject", newClient);
	   pageContext.setAttribute("result", true);
   }
   else
   	  pageContext.setAttribute("result", false);
   
  %>
  
 <jsp:forward page="usercenter.jsp"> 
 <jsp:param name="login" value="${result}"/>
 </jsp:forward>