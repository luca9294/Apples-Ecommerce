package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Vector;

import javax.jws.WebService;

import Serializables.ProductObject;
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

	@Override
	public ProductObject[] getProducts(int cat_id) {
		LinkedList<ProductObject> list = new LinkedList<ProductObject>();
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM products WHERE  category_id= ? ");
			preparedStatement.setInt(1, cat_id);

			// Retrieve the result of RETURNING statement to get the current id.
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {				
				ProductObject po = new ProductObject(resultSet.getInt("product_id"), cat_id, 
						resultSet.getString("title"), resultSet.getString("summary"), 
						resultSet.getString("description"), resultSet.getInt("price"), resultSet.getInt("price_type"), resultSet.getString("image_link"));
				list.add(po);
			
			}
			preparedStatement.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
		ProductObject[] array = new ProductObject[list.size()];
		for (int i = 0; i < list.size(); i++)
			array[i] = list.get(i);
		
		return array;
	}
}