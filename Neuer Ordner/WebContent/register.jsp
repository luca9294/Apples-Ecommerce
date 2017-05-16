<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" rev="stylesheet" type="text/css" href="css/style.css" media="screen" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<title>AST - Registration</title>
</head>
<body>
<div id="page">

<c:import url="include/header.inc.jsp"/>

<c:import url="include/navigation.inc.jsp"/>
 
<div id="content">

<c:choose>
 <c:when test="${!empty param.success && param.success == true}">	
  
  <p class="msg">Registration successful!</p>
 	  
 </c:when>
 <c:otherwise>  

<c:out value="${param.success}"></c:out>

<form action="doRegistration.jsp" method="POST">
<!-- <label for="name">Name: </label>
<input type="text" value="" id="name" name="name" /> -->
<label for="login">Login: </label>
<input type="text" value="${param.login}" id="login" name="login" /><br />
<label for="password">Password: </label>
<input type="password" value="${param.password}" id="password" name="password" /><br />
<label for="email">Email: </label>
<input type="text" value="${param.email}" id="email" name="email" /><br />
<label for="homepage">Website: </label>
<input type="text" value="${param.homepage}" id="homepage" name="homepage" />
<input type="submit" value="Register" name="register"/>
</form>


 </c:otherwise>
</c:choose>

</div>

<c:import url="include/footer.inc.jsp"/>

</div>
</body>
</html>