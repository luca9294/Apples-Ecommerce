<%@page import="net.webservicex.www.GeoIPServiceSoapProxy"%>
<%@page import="interfaces.LoginServiceIntProxy"%>
<%@page import="interfaces.CustomerObject"%>
<%@page import="interfaces.CustomerIntProxy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

   <%
   //If a new user has registered, cookie of others must be deleted
    /* Cookie[] cookies = null;
    cookies = request.getCookies();
    for (Cookie c : cookies){
      c.setMaxAge(0);
      c.setPath("/");
      response.addCookie(c);
    }*/
   
   String name = request.getParameter("name");
   String salutation = request.getParameter("salutation");
   String sname = request.getParameter("sname");
   String street = request.getParameter("street");
   String province = request.getParameter("province");
   String zip = request.getParameter("zip");
   String uname = request.getParameter("uname");
   String country = request.getParameter("country");
   String psw = request.getParameter("country");
   String email = request.getParameter("uname");
   String city = request.getParameter("city");
   String pwd = request.getParameter("psw");
   
   LoginServiceIntProxy lsi = new LoginServiceIntProxy();
   boolean ct = lsi.createNewUser(salutation, name, sname, country, province, city, street, "", zip, 0, email, pwd);
   if (ct) {
	   
	   String ip = request.getHeader("X-Forwarded-For");  
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("Proxy-Client-IP");  
       }  
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("WL-Proxy-Client-IP");  
       }  
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("HTTP_CLIENT_IP");  
       }  
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
       }  
       if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
           ip = request.getRemoteAddr();  
       }
	   
	   CustomerIntProxy cip = new CustomerIntProxy();
	   Cookie cEmail = new Cookie("email", email);
	   Cookie cPwd   = new Cookie("pwd", pwd);
	   Cookie cCountry = new Cookie("country", new GeoIPServiceSoapProxy().getGeoIP(ip).getCountryCode());
	   Cookie cHost= new Cookie("host", request.getRemoteHost());
	   
	   cEmail.setMaxAge(24*60*60*15);
	   cPwd.setMaxAge(24*60*60*15);
	   cCountry.setMaxAge(24*60*60*15);
	   cHost.setMaxAge(24*60*60*15);
	   
       response.addCookie(cEmail);
       response.addCookie(cPwd);
       response.addCookie(cCountry);
       response.addCookie(cHost);
       
	   response.sendRedirect(String.format("%s%s", request.getContextPath(), "/index.jsp?message=Registration succesfully"));
   	   		
   }
   else
	    response.sendRedirect(String.format("%s%s", request.getContextPath(), "/index.jsp?message=" + lsi.getError()));
  %>
 
  
