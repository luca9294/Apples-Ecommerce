<%@page import="net.webservicex.www.GeoIPServiceSoapProxy"%>
<%@page import="interfaces.LoginServiceIntProxy"%>
<%@page import="interfaces.CustomerObject"%>
<%@page import="interfaces.CustomerIntProxy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

   <%  
   String email = request.getParameter("uname");
   String pwd = request.getParameter("psw");
   LoginServiceIntProxy lsi = new LoginServiceIntProxy();
   int result =  lsi.login(email, pwd);
   if (result <= 0){
       session.setAttribute("logged", false);
	   response.sendRedirect(String.format("%s%s", request.getContextPath(), "/index.jsp?message=Your login credentials are incorrect"));
   }
	  
   else{
	   String token = lsi.getCookieToken();
	   Cookie cookie = new Cookie("token", token);
	   cookie.setMaxAge(60 * 60 * 30);
	   cookie.setPath("/");
	   response.addCookie(cookie);
	   lsi.insertNewToken(result, token);
       session.setAttribute("customer_id", result);
       session.setAttribute("logged", true);
	   response.sendRedirect(String.format("%s%s", request.getContextPath(), "/index.jsp?message=You logged in succesfully"));
   }
     
  %>
 
  
