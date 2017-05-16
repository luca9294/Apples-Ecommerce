<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" rev="stylesheet" type="text/css" href="css/style.css" media="screen" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<title>AST - Delete Address</title>
</head>
<body>
<div id="page">

 <c:import url="include/header.inc.jsp"/>

<c:import url="include/navigation.inc.jsp"/>
 
 <div id="content">

	<c:set var="addressId" value="${param.address_id}"/>
     <c:choose>
   <c:when test="${sessionScope.loggedIn == true}">	
	 <c:choose>
     <c:when test="${!empty addressId}">	
    	
        <%
			int addressId = Integer.parseInt(""+pageContext.getAttribute("addressId"));
			//db.Address address = db.Address.find(addressId);
			boolean result = address.delete();
			pageContext.setAttribute("result",result);
		%>
         <jsp:forward page="usercenter.jsp"> 
 		  <jsp:param name="delete" value="${result}"/>
		 </jsp:forward>
 
     </c:when>
      <c:otherwise>
 
 	   <p class="error">No address to delete</p>
 	
     </c:otherwise>
      </c:choose>	
    
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