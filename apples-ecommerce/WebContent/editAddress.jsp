<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <c:set var="salutation" value="${param.salutation}"/>
 <c:set var="title" value="${param.title}"/>
 <c:set var="firstname" value="${param.firstname}"/>
 <c:set var="lastname" value="${param.lastname}"/>
 <c:set var="company" value="${param.company}"/>
 <c:set var="country" value="${param.country}"/>
 <c:set var="province" value="${param.province}"/>
 <c:set var="city" value="${param.city}"/>
 <c:set var="street" value="${param.street}"/>
 <c:set var="streetNo" value="${param.streetNo}"/>
 <c:set var="zip" value="${param.zip}"/>
 <c:set var="modifyAddress" value="${param.modifyAddress}"/>
 <c:set var="addressId" value="${param.address_id}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" rev="stylesheet" type="text/css" href="css/style.css" media="screen" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<title>AST - Edit Address</title>
</head>
<body>
<div id="page">

<c:import url="include/header.inc.jsp"/>

<c:import url="include/navigation.inc.jsp"/>
 
<div id="content">

<c:choose>
 <c:when test="${sessionScope.loggedIn == true}">
    
    <%
    int addressId = Integer.parseInt((String) pageContext.getAttribute("addressId"));
	//db.Address address = db.Address.find(addressId);
    %>
    
 	<c:choose>
    <c:when test="${!empty modifyAddress}">	
    <%    
    String salutation = (String) pageContext.getAttribute("salutation");
    String title = (String) pageContext.getAttribute("title");
    String firstname = (String) pageContext.getAttribute("firstname");
    String lastname = (String) pageContext.getAttribute("lastname");
    String company = (String) pageContext.getAttribute("company");
    String country = (String) pageContext.getAttribute("country");
    String province = (String) pageContext.getAttribute("province");
    String city = (String) pageContext.getAttribute("city");
    String street = (String) pageContext.getAttribute("street");
    String streetNo = (String) pageContext.getAttribute("streetNo");
    String zip = (String) pageContext.getAttribute("zip");        
	pageContext.setAttribute("resultAddress", address.modify(salutation,title,firstname,lastname,company,country,province,city,street,streetNo,zip));	
	%>	
	</c:when>
	<c:otherwise>
	<%	
	pageContext.setAttribute("salutation", address.getSalutation());
	pageContext.setAttribute("title", address.getTitle());
	  pageContext.setAttribute("firstname", address.getFirstname());
	  pageContext.setAttribute("lastname", address.getLastname() );
	  pageContext.setAttribute("company", address.getCompany());	  
	  pageContext.setAttribute("country", address.getCountry());	  
	  pageContext.setAttribute("province", address.getProvince());
	  pageContext.setAttribute("city", address.getCity());
	  pageContext.setAttribute("street", address.getStreet());
	  pageContext.setAttribute("streetNo", address.getStreetNo());
	  pageContext.setAttribute("zip", address.getZip());
	%>	
	</c:otherwise>
	</c:choose>
	
	
 	<c:choose>
     <c:when test="${empty resultAddress && !empty modifyAddress}">
  	  <p class="error">Error! Address can't be modified</p>
 	
 	  </c:when>
      <c:otherwise>       
       <c:if test="${!empty resultAddress && !empty modifyAddress}">	
   	      <jsp:forward page="usercenter.jsp"> 
 		    <jsp:param name="success" value="true"/>
   		 </jsp:forward>
  	    </c:if> 	   
 	  </c:otherwise>
    </c:choose> 
 	  
    <c:choose>    
    <c:when test="${empty resultAddress}">
     
    <form action="" method="POST">       
    
     <label>Title:</label>
     <input type="radio" name="title" value="" <c:if test="${empty title}"><c:out value='checked="checked"' /></c:if> >None
     <input type="radio" name="title" value="Mag." <c:if test="${title == 'Mag.'}"><c:out value='checked="checked"' /></c:if> >Mag.
     <input type="radio" name="title" value="Dr." <c:if test="${title == 'Dr.'}"><c:out value='checked="checked"' /></c:if> >Dr.
     <input type="radio" name="title" value="Prof." <c:if test="${title == 'Prof.'}"><c:out value='checked="checked"' /></c:if> >Prof.
     <input type="radio" name="title" value="Prof. Dr." <c:if test="${title == 'Prof. Dr.'}"><c:out value='checked="checked"' /></c:if> >Prof. Dr.<br />
    
     <!-- TODO patrick soll di radio buttons mochn -->
     
     <label for="firstname">Firstname:</label>
     <input type="text" name="firstname" id="firstname" value="${firstname}" /><br />
    
     <label for="lastname">Lastname:</label>
     <input type="text" name="lastname" id="lastname" value="${lastname}" /><br />
     
     <label>Sex:</label>
     <input type="radio" name="salutation" value="Mr." <c:if test="${salutation == 'Mr.'}"><c:out value='checked="checked"' /></c:if> >Male
     <input type="radio" name="salutation" value="Mrs." <c:if test="${salutation == 'Mrs.'}"><c:out value='checked="checked"' /></c:if> >Female<br />
    
     <label for="company">Company:</label>
     <input type="text" name="company" id="company" value="${company}" /><br />
     
     <label for="country">Country:</label>
     <input type="text" name="country" id="country" value="${country}" /><br />
     
     <label for="province">Province:</label>
     <input type="text" name="province" id="province" value="${province}" /><br />
     
     <label for="city">City:</label>
     <input type="text" name="city" id="city" value="${city}" /><br />
     
     <label for="street">Street:</label>
     <input type="text" name="street" id="street" value="${street}" /><br />
     
     <label for="streetNo">Street No.:</label>
     <input type="text" name="streetNo" id="streetNo" value="${streetNo}" /><br />
     
     <label for="zip">ZIP:</label>
     <input type="text" name="zip" id="zip" value="${zip}" /><br />
     
     <input type="submit" name="modifyAddress" value="modify" />
    </form>
       
    </c:when>    
 	</c:choose>
          
 </c:when>
 <c:otherwise>
  <p class="error">Please login!</p>
 </c:otherwise>
</c:choose>
</div>

<c:import url="include/footer.inc.jsp"/>

</div>
</body>
</html>