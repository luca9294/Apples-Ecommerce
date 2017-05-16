package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Vector;

import javax.jws.WebService;

import connection.ConnectionManager;
import interfaces.CategoryInt;

@WebService(endpointInterface = "interfaces.CategoryInt")  
public class Category implements CategoryInt {

	@Override
	public String[] getCategories(){
		int count =0;
		String[] categories = new String[100];
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
	
			try {
				preparedStatement = connection.prepareStatement(
						"SELECT * FROM category");
				
				resultSet = preparedStatement.executeQuery();
				while(resultSet.next()) {			
					categories[count] = (resultSet.getString(2));
					count++;
				}
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return categories;
		
}
}