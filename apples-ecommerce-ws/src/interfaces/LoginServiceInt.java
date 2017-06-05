package interfaces;

import java.sql.SQLException;

import javax.jws.WebMethod;
import javax.jws.WebService;

import Serializables.CustomerObject;

@WebService
public interface LoginServiceInt {
	@WebMethod
	public boolean createNewUser(String salutation, String name,
			String surename, String country, String province,
			String city, String street,	String streetNo, String zip, int customer_id,
			String email, String pwd
			);
	@WebMethod
	public String getError();
	@WebMethod
	public int login(String email, String pwd);
	@WebMethod
	public int loginCookie(String cookieId);
	@WebMethod
	public String updateCookieToken(int customerId, String cookieId) throws SQLException;
	@WebMethod
	public int getCustomerIdFromToken(String cookieId) throws SQLException;
	@WebMethod
	public String getCookieToken() throws SQLException;
	@WebMethod 
	public boolean insertNewToken(int customerId, String token);
	@WebMethod 
	public boolean updateToken(int customerId, String token);
	@WebMethod
	public String getPublicKey();
	@WebMethod
	public String getPublicKeyFromEmail(String email);
	@WebMethod
	public boolean logout(String cookie_id);
	
	


}
