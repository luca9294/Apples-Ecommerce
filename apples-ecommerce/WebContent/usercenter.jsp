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

<title>AST - User Center</title>
</head>
<body>
<div id="page">

<c:import url="include/header.inc.jsp"/>

<c:import url="include/navigation.inc.jsp"/>
 
<div id="content">


 	<c:choose>
 	<c:when test="${param.login == false}">	
  
  	<p class="error">Login failed!</p>
 	
 	</c:when>
	</c:choose>
    
    
    
    <c:choose>
     <c:when test="${sessionScope.loggedIn == true}">	
     
     <h2>Your Profile:</h2> 
     
  	  <%
	  
	  int clientId = Integer.parseInt(""+session.getAttribute("clientID"));
	  db.Client client = db.Client.find(clientId);
	  pageContext.setAttribute("client", client);
	 // pageContext.setAttribute("addresses", db.Address.findByClient(clientId));
	  
	  %>
      
      Customer Number: ${client.id}<br />
      Username: ${client.login}<br />
      Email: ${client.email}<br />
      Homepage: ${client.url}<br />
      Auctions created: ${client.countOffers}<br />
      Bets placed: ${client.countOrders}<br />        
      
     
	 <h2>Your Addresses:</h2> 
	 <c:choose>
	 <c:when test="${param.delete == false}">	  
  	  <p class="error">Deletion Error!</p> 	
 	</c:when>
	</c:choose>
	
	<c:if test='${empty addresses}'>
   	  <p>Until now you have no Address!<br /> <a href="createAddress.jsp">Create one now!</a></p>
	</c:if>
	
    <c:forEach items="${addresses}" var="address" varStatus="vs"> 
		<div class="result">
		${address.title} ${address.firstname} ${address.lastname},  ${address.street} ${address.streetNo},  ${address.city} - ${address.zip}, ${address.country}  
        <a href="editAddress.jsp?address_id=${address.id}">[edit]</a><a href="deleteAddress.jsp?address_id=${address.id}">[delete]</a><br />
		</div>
	</c:forEach>
	
 	 </c:when>
    </c:choose>  

</div>


<c:import url="include/footer.inc.jsp"/>


</div>
</body>
</html>