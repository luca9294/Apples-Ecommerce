package Serializables;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import connection.ConnectionManager;

@XmlRootElement(name = "CustomerObject")
public class CustomerObject  {

	private static final long serialVersionUID = -5577579081118070434L;

	@XmlElement(name = "customer_id")
	private int customer_id;
	@XmlElement(name = "firstname")
	private String firstname;
	@XmlElement(name = "lastname")
	private String lastname;
	@XmlElement(name = "city")
	private String city;
	@XmlElement(name = "address")
	private String address;
	@XmlElement(name = "zip")
	private String zip;
	@XmlElement(name = "email")
	private String email;
	@XmlElement(name = "organization")
	private String organization;
	
	

	public CustomerObject(){}
	
	
	public CustomerObject(String firstname,
			String lastname, String email, int phoneNumber, String organization,
			String city, String address, String zip) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.city = city;
		this.address = address;
		this.zip = zip;
		this.email = email;
		this.organization = organization;
	}

	public int getId() {
		return customer_id;
	}




	public String getFirstname() {
		return firstname;
	}





	public String getLastname() {
		return lastname;
	}




	public String getCity() {
		return city;
	}




	public String getAddress() {
		return address;
	}





	public String getZip() {
		return zip;
	}




	public String getEmail() {
		return email;
	}



	public String getOrganization() {
		return organization;
	}


}
