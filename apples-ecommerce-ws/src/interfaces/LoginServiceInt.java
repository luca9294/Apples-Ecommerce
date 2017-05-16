package interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;

import Serializables.CustomerObject;

@WebService
public interface LoginServiceInt {
	@WebMethod
	public String getPublicKey();
	@WebMethod
	public boolean createNewUser(String salutation, String name,
			String surename, String country, String province,
			String city, String street,	String streetNo, String zip, int customer_id,
			String email, String pwd
			);
	@WebMethod
	public String getError();
	@WebMethod
	boolean login(String email, String pwd);
	
}
