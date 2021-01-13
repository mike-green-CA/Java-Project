/**
 * Solution for "SpecifiyCustomerPurchases.java".    
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 3rd 
 */

package a01045801.util;

import java.util.LinkedList;
import java.util.List;

import a01045801.data.Purchase;

public class SpecifiyCustomerPurchases {

	private List<Purchase> purchases;
	
	public SpecifiyCustomerPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}
	 
	/**
	 * @return A list containing purchases by only the customer_id specified
	 */
	public List<Purchase> trimListByID(Long customer_id){
		List<Purchase> return_list = new LinkedList<Purchase>();
		
		for(Purchase purchase : purchases) {
			if(purchase.getCustomer_id() == customer_id) {
				return_list.add(purchase);
			}
		}
		
		return return_list;
		
	}

}
