package interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;

import Serializables.CardObject;
import Serializables.CartEntryObject;

@WebService
public interface CardInt {

	@WebMethod
	/**
	 * Add a new card
	 * @param co
	 * @return
	 */
	public boolean AddCard (CardObject co);
	
	@WebMethod
	/**
	 * Get cards of the customer
	 * @param co
	 * @return
	 */
	public CardObject[] getCards(int customer_id);
	
	@WebMethod
	/**
	 * Get new public key
	 * @param co
	 * @return
	 */
	public String getPublicKey();
}
