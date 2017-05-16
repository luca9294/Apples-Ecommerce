package Publisher;

import javax.xml.ws.Endpoint;

import db.Category;
import db.Customer;
import db.LoginService;

public class Publisher {

		public static void main(String[] args) {
			Endpoint.publish("http://localhost:1234/category", new Category());
			Endpoint.publish("http://localhost:1235/customer", new Customer());
			Endpoint.publish("http://localhost:1215/login", new LoginService());
		}

	}
	

