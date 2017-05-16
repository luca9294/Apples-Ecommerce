<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="category" class="beans.NavigationBean" scope="session"></jsp:useBean>
<jsp:useBean id="auction" class="db.Auction" scope="session"></jsp:useBean>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" rev="stylesheet" type="text/css" href="css/style.css" media="screen" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<title>AST - My auctions</title>
</head>
<body>
<div id="page">

 <c:import url="include/header.inc.jsp"/>

<c:import url="include/navigation.inc.jsp"/>
 
 <div id="content">

	<c:choose>
 	<c:when test="${param.delete == false}">	
  
  	<p class="error">Deletion failed!</p>
 	
 	</c:when>
	</c:choose>
    
    
     <c:choose>
   <c:when test="${sessionScope.loggedIn == true}">	
	
    <%
	
	db.Client client = (db.Client) 	session.getAttribute("clientObject");
	pageContext.setAttribute("auctions",client.getAuctions());
	
	%>   
 	
    <c:forEach items="${auctions}" var="auction" varStatus="vs"> 
		<div class="result">
		<a href="detail.jsp?auction_id=${auction.id}">${auction.title}</a>
        <a href="deleteAuction.jsp?auction_id=${auction.id}"> [delete]</a>
		</div>
	</c:forEach>
    
   </c:when>
   <c:otherwise>
 
 	<p class="error">Please login ! </p>
 	
   </c:otherwise>
  </c:choose>		

 </div>
 
<c:import url="include/footer.inc.jsp"/>

</div>
</body>
</html>