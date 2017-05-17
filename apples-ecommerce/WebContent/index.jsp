<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" rev="stylesheet" type="text/css" href="css/style.css" media="screen" /> 
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<title>Auction South Tyrol</title>
</head>
<body>
<div id="page">

<c:import url="include/header.inc.jsp"/>
<c:import url="include/navigation.inc.jsp"/>



 <div id="content">
    <p class="start"><strong>
    	<big>Welcome to Apples Ecommerce - South Tyrol </big>!<br /></strong>
       <strong> <big><br />
        We proudly present the most advanced apples e-commerce platform in the WORLD !<br />
        <br /></big></strong>
    </p>
    
   <br>
   <p style="height: 55px; "></p>
   </br> 
    
  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/Suedtirolerapfel.svg/1200px-Suedtirolerapfel.svg.png" style="width: 579px;height: 429px;">

 </div>
 <p></p>
 <p></p>
 <br><p style="height: 249px; ">         </p></br>
 <br><p style="height: 249px; ">         </p></br
 <br><p style="height: 249px; ">         </p></br>
 

<div id="id05" class="modal">
   <form class="modal-content animate">
    <div class="imgcontainer">
      <span onclick="document.getElementById('id05').style.display='none'" class="close" title="Close Modal">&times;</span>
    </div>
    <div class="container">
      <label><b><%out.print(request.getParameter("message")); %></b></label>
    </div>
    <div class="container" style="background-color:#f1f1f1">
      <button type="button" onclick="document.getElementById('id05').style.display='none'" class="cancelbtn">Close</button>
    </div>
  </form>
</div>

 <c:if test="${not empty param.message}" >
	<script>
    document.getElementById('id05').style.display='block'
	</script>  
 </c:if>
 
 

<c:import url="include/footer.inc.jsp"/>

</body>
</html>