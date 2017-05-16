<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="category" class="beans.NavigationBean" scope="session"></jsp:useBean>
<jsp:useBean id="auction" class="db.Auction" scope="session"></jsp:useBean>	
	  
<c:set var="key" value="${param.key}"/>	

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" rev="stylesheet" type="text/css" href="css/style.css" media="screen" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<title>AST - Search ${param.key}"</title>
</head>
<body>
<div id="page">

 <c:import url="include/header.inc.jsp"/>

<c:import url="include/navigation.inc.jsp"/>
 
<div id="content">


<c:choose>
 <c:when test="${!empty param.key}">
     
   <%
   
   db.Auction search = new db.Auction();
   String key = (String) pageContext.getAttribute("key");
   
   db.Auction[] results = search.find(key);
   
   pageContext.setAttribute("result", results);
   
  %>
 
 	<c:choose>
 	<c:when test="${!empty result}">	
  	Results for: ${param.key} <br />
  	<c:forEach items="${result}" var="res" varStatus="vs"> 
		<div class="result">
		<a href="detail.jsp?auction_id=${res.id}"><b>${res.title}</b></a><br />
		${res.description}<br />
		</div>
	</c:forEach>
 	  
 	</c:when>
 	<c:otherwise>
 
 	<p class="error">No result found!</p>
 
 	</c:otherwise>
	</c:choose>
 	  
 </c:when>
 <c:otherwise>
 
 <p class="error">You entered no Keyword!</p>
 
 </c:otherwise>
</c:choose>

</div>

<c:import url="include/footer.inc.jsp"/>

</div>
</body>
</html>