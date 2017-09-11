package interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;

import Serializables.CustomerObject;

@WebService
public interface CustomerInt {
	@WebMethod
	public CustomerObject modify(String salutation, String name,
			String surname, String country, String province,
			String city, String street,	String streetNo, String zip, String email, String pwd);
	
	@WebMethod
	public boolean delete();
	
	@WebMethod
	public int findByEmail(String email);
	
	@WebMethod
	public CustomerObject findByCookie(int id, String cookieId);

	
}
