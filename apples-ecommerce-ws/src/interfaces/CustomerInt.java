package interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

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
	public CustomerObject find(int id);

	
}
