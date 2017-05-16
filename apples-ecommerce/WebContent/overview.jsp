<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="category" class="beans.NavigationBean" scope="session"></jsp:useBean>

<c:set var="categoryId" value="${param.cat_id}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" rev="stylesheet" type="text/css" href="css/style.css" media="screen" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<title>AST - Category Browser</title>
</head>
<body>
<div id="page">

<c:import url="include/header.inc.jsp"/>

<c:import url="include/navigation.inc.jsp"/>
 
<div id="content">

 <%
 
   int categoryID = Integer.parseInt((String) pageContext.getAttribute("categoryId"));
   String password = (String) pageContext.getAttribute("password");
   
   db.Auction[] auctions = db.Auction.listByCategoryId(categoryID, 0, 1000);
   
   pageContext.setAttribute("auctions", auctions);
   
  %>
	<c:choose>
 	<c:when test="${!empty auctions}">	
  
  	<c:forEach items="${auctions}" var="auction" varStatus="vs"> 
		<div class="overview">
		<a href="detail.jsp?cat_id=${categoryId}&auction_id=${auction.id}">${auction.title}</a><br />
		${auction.description}<br />
		</div>
	</c:forEach>
 	  
 	</c:when>
 	<c:otherwise>
 
 	<p>This category contains no auctions!</p>
 
 	</c:otherwise>
	</c:choose>

</div>

<c:import url="include/footer.inc.jsp"/>

</div>
</body>
</html>