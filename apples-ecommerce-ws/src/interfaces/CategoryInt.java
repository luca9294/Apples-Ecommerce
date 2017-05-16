package interfaces;

import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface CategoryInt {
	@WebMethod
	public String[] getCategories();
}
