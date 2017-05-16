<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="category" class="beans.NavigationBean" scope="session"></jsp:useBean>


<c:set var="categoryId" value="${param.cat_id}" />
<c:set var="auctionId" value="${param.auction_id}" />
<c:set var="success" value="${param.success}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" rev="stylesheet" type="text/css" href="css/style.css" media="screen" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<title>AST - Auction Detail</title>
</head>
<body>
<div id="page">

<c:import url="include/header.inc.jsp"/>

<c:import url="include/navigation.inc.jsp"/>

<div id="content">


<!--  auswertung der plazierung der bet -->

<c:choose>
  <c:when test="${!empty success}">
	<c:choose>
 	  <c:when test="${success == true}">
		
		<p class="msg">Placement successful</p>
		
	  </c:when>
 	  <c:otherwise>
 
 	    <p class="error">Placement NOT successful</p>
 
 	  </c:otherwise>
	</c:choose>
  </c:when>
</c:choose>

 <%
   
   int auctionId = Integer.parseInt((String) pageContext.getAttribute("auctionId"));
   db.Auction auction = db.Auction.find(auctionId);
   pageContext.setAttribute("auction", auction);
   
  %>
	<c:choose>
 	<c:when test="${!empty auction}">	
  
  	
		<div class="detail">
		${auction.title}<br />
		${auction.description}<br />
		
		<c:set var="bet" value="${auction.highestBet}" />
		
		<c:choose>
 		<c:when test="${!empty bet}">	
  
		 	  highest bet until now: ${bet.amount}<br /> 
		 	  Minimum Raise: ${auction.amount}
		 	 <c:choose>
 			  <c:when test="${sessionScope.loggedIn == true}">	
		 	    <form action="doBet.jsp" method="POST">
				 <input type="hidden" name="auction_id" value="${auctionId}" />
		 	 	 <label for="amount">Place your bet</label>
		 	 	 <input type="text" name="amount" value="" id="amount" />
		 	 	 <input type="submit" name="placebet" value="Place Bet" />
		 	 	</form>
		 	  </c:when>
		 	  <c:otherwise>
		 	  <p class="error">Please login to place a bet!</p>
		 	  </c:otherwise>
		 	 </c:choose>
		 	  
		 	  
		 </c:when>
		 <c:otherwise>
		
		Be the first to bet, it's only ${auction.startPrice}<br />
		
		<c:choose>
 			  <c:when test="${sessionScope.loggedIn == true}">	
		 	    <form action="doBet.jsp" method="POST">
				<input type="hidden" name="auction_id" value="${auctionId}" />
		 	  <label for="amount">Place your bet</label>
		 	  <input type="text" name="amount" value="" id="amount" />
		 	  <input type="submit" name="placebet" value="Place Bet" />
		 	  </form>
		 	  </c:when>
		 	  <c:otherwise>
		 	  <p class="error">Please login to place a bet!</p>
		 	  </c:otherwise>
		 	  </c:choose>
		 	  
		</c:otherwise>
		</c:choose>
		</div>
		
		
		
 	  
 	</c:when>
 	<c:otherwise>
 
 	<p class="error">No auction with this ID!</p>
 
 	</c:otherwise>
	</c:choose>

</div>

<c:import url="include/footer.inc.jsp"/>

</div>
</body>
</html>