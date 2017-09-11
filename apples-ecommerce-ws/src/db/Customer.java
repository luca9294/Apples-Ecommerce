package db;

import java.sql.*;

import Serializables.CustomerObject;

import javax.jws.WebResult;
import javax.jws.WebService;

import connection.ConnectionManager;
import helper.CustomerUtilities;
import interfaces.CustomerInt;


@WebService(endpointInterface = "interfaces.CustomerInt")  
public class Customer implements CustomerInt {
	 
	private CustomerObject currentCustomer;
	
	public Customer(){};

    @Override
    
	public CustomerObject modify(String salutation, String name,
			String surname, String country, String province,
			String city, String street,	String streetNo, String zip, String email, String pwd){

    	return null;
	}	
	
    @Override
	public boolean delete(){
		boolean success = false;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE customer_id = "+this.currentCustomer.getId()+";");		
			if(preparedStatement.executeUpdate() > 0){
				success = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}		
		return success;
	}


    @Override 
	public int findByEmail(String email) {
    	int result = 0;;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM customer where email = ?");
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			result =  resultSet.getInt("customer_id");
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		} finally {
			ConnectionManager.close(connection);
		}
    	
    	return result;
	}
    
    @WebResult(name="CustomerObject")
    @Override 
	public CustomerObject findByCookie(int id, String cookieId) {
    	/*
    	CustomerObject customer = null;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM customer LEFT JOIN cookie ON customer.customer_id = cookie.customer_id WHERE cookie.cookie_id = ?");
			preparedStatement.setString(1, cookieId);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next() && (resultSet.getInt("customer_id") == id)) {
				customer = new CustomerObject(id, resultSet.getString("salutation"),
						resultSet.getString("firstname"), resultSet.getString("lastname"),
						resultSet.getString("country"), resultSet.getString("province"),
						resultSet.getString("city"), resultSet.getString("street"),
						resultSet.getString("street"), resultSet.getString("zip"),
						resultSet.getString("email"), resultSet.getString("pwd"),resultSet.getString("key"));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
		return customer;
		
		*/
    	
    	return null;
   	}
    
    private CustomerObject findById(int id) {
    	
    	/*
    	CustomerObject customer = null;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM customer WHERE customer_id = ?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				customer = new CustomerObject(id, resultSet.getString("salutation"),
						resultSet.getString("firstname"), resultSet.getString("lastname"),
						resultSet.getString("country"), resultSet.getString("province"),
						resultSet.getString("city"), resultSet.getString("street"),
						resultSet.getString("street"), resultSet.getString("zip"),
						resultSet.getString("email"), resultSet.getString("pwd"),resultSet.getString("key"));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
		return customer;
		
		*/
    	
    	return null;
    }
}