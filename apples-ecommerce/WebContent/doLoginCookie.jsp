<%@page import="net.webservicex.www.GeoIPServiceSoapProxy"%>
<%@page import="interfaces.LoginServiceIntProxy"%>
<%@page import="interfaces.CustomerObject"%>
<%@page import="interfaces.CustomerIntProxy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

   <%  
   String token = request.getParameter("token");
   LoginServiceIntProxy lsi = new LoginServiceIntProxy();
   int result =  lsi.loginCookie(token);
   Cookie[] cookies = request.getCookies();
   Cookie myCookie = null;
   if (cookies != null) {
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("token")) {
         myCookie = cookie;
       }
     }
   }
   if (result <= 0){
	   token = lsi.getCookieToken();
	   myCookie.setMaxAge(60 * 60 * 30);
	   myCookie.setPath("/");
	   myCookie.setValue(token);
	   lsi.updateToken(result, token);
       session.setAttribute("customer_id", result);
       session.setAttribute("logged", true);
	   response.sendRedirect(String.format("%s%s", request.getContextPath(), "/index.jsp?message=Your login credentials are incorrect"));
   }
   else{
	   myCookie.setMaxAge(0);
	   response.sendRedirect(String.format("%s%s", request.getContextPath(), "/index.jsp"));
   }
     
  %>
 
  
