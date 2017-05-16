<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="auction" class="db.Auction" scope="session"></jsp:useBean>

 <c:set var="title" value="${param.title}"/>
 <c:set var="description" value="${param.description}"/>
 <c:set var="startPrice" value="${param.startPrice}"/>
 <c:set var="expressPrice" value="${param.expressPrice}"/>
 <c:set var="amount" value="${param.amount}"/>
 <c:set var="imagePath" value="${param.imagePath}"/>
 <c:set var="categories" value="${param.categories}"/>
 <c:set var="submit" value="${param.createAuction}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" rev="stylesheet" type="text/css" href="css/style.css" media="screen" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<title>AST - Create Auction</title>
</head>
<body>
<div id="page">

<c:import url="include/header.inc.jsp"/>

<c:import url="include/navigation.inc.jsp"/>
 
<div id="content">
	
    <c:choose>
    <c:when test="${!empty submit}">	
  
    <%

   String title = (String) pageContext.getAttribute("title");
   String description = (String) pageContext.getAttribute("description");
   int startPrice = Integer.parseInt(""+ pageContext.getAttribute("startPrice"));
   int expressPrice = Integer.parseInt((String) pageContext.getAttribute("expressPrice"));
   int amount = Integer.parseInt((String) pageContext.getAttribute("amount"));
   String imagePath = (String) pageContext.getAttribute("imagePath");
  
   String[] cats = request.getParameterValues("categories");
   int[] categories = new int[cats.length];
   
   for(int i=0;i<cats.length;i++)
	categories[i]=Integer.parseInt(cats[i]);
   
   int clientID = Integer.parseInt(""+ session.getAttribute("clientID"));
	
   java.util.Date todayDate = new java.util.Date();
   java.sql.Date today = new java.sql.Date(todayDate.getTime());

	pageContext.setAttribute("result", db.Auction.create(title,description,startPrice,expressPrice,amount,today,today,today,true,imagePath, 10, 1,clientID,categories));
	
	%>
	</c:when>
	</c:choose>
	
 	<c:choose>
     <c:when test="${empty result && !empty submit}">	
  
  	  <p class="error">Auction creation error!</p>
 	
 	  </c:when>
      <c:otherwise>
       <c:choose>
       <c:when test="${!empty result && !empty submit}">	
  
  	     <p class="msg">Auction created successfully!</p>
 	
 	    </c:when>
 	   </c:choose> 
 	  </c:otherwise>
    </c:choose> 
 	  
     <c:choose>
    
    <c:when test="${empty result}">
     
    <form action="" method="POST">
     <label for="title">Title:</label>
     <input type="text" name="title" id="title" value="${param.title}" /><br />

     <label for="description">Description:</label>
     <textarea name="description" id="description">${param.description}</textarea><br />
     
     <label for="startPrice">Start price:</label>
     <input type="text" name="startPrice" id="startPrice" value="${param.startPrice}" /><br />
     
     <label for="expressPrice">Express price:</label>
     <input type="text" name="expressPrice" id="expressPrice" value="${param.expressPrice}" /><br />
     
     <label for="amount">Amount:</label>
     <input type="text" name="amount" id="amount" value="${param.amount}" /><br />
     
     <label for="imagePath">Image:</label>
     <input type="text" name="imagePath" id="imagePath" value="${param.imagePath}" /><br />
     
     <label for="categories">Categories:</label>
     <select name="categories" id="categories" size="10" multiple>
      <c:forEach items="${category.allCategories}" var="item" varStatus="vs">
      <option value="${item.id}">${item.name}</option>
       <c:choose>
         <c:when test="${!empty item.children}">
          <c:set var="recurse_message" value="${item}" scope="request"/>
          <c:import url="include/multiselect.jsp"/>
          <c:remove var="recurse_message" scope="request"/>
         </c:when>
        </c:choose>
      </c:forEach>
     </select><br />
     
     <input type="submit" name="createAuction" value="create" />
    </form>
    
 
    </c:when>
    
 	</c:choose>
    
    
    <c:choose>
     <c:when test="${sessionScope.loggedIn == true}">	
     <!-- could be used to check, if user is logged in -->
 	 </c:when>
    </c:choose>  

</div>

<c:import url="include/footer.inc.jsp"/>

</div>
</body>
</html>