package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.jws.WebService;
import Serializables.ProductObject;
import connection.ConnectionManager;
import interfaces.ProductInt;

@WebService(endpointInterface = "interfaces.ProductInt")  
public class Product implements ProductInt{

	@Override
	public ProductObject findProduct(int product_id) {
		ProductObject po = null;
		ResultSet resultSet;
		PreparedStatement preparedStatement;
		Connection connection = ConnectionManager.connect();
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(
					"SELECT * FROM products WHERE product_id = ? ");
			preparedStatement.setInt(1, product_id);

			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {				
				po = new ProductObject(product_id, resultSet.getInt("category_id"), resultSet.getString("title"),
						resultSet.getString("summary"), resultSet.getString("description"), resultSet.getInt("price"),
						resultSet.getInt("price_type"), resultSet.getString("image_link"),  resultSet.getInt("quantity"));
			}
			preparedStatement.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(connection);
		}
		return po;
	}

}
