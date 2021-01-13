/**
 * Solution for "CompareByLastName.java".    
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 3rd 
 */

package a01045801.util;

import a01045801.data.Customer;
import a01045801.data.Purchase;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class CompareByLastName implements Comparator<Customer> {
	
	private List<Purchase> purchases;
	private List<Customer> customers;
	
	public CompareByLastName(List<Purchase> purchases, List<Customer> customers) {
		this.purchases = purchases;
		this.customers = customers;
	}
	
	/**
	 * @param descending
	 * @return will be a sorted, in ascending, or descending, array of customer's according to their authors.
	 */
	public List<Purchase> sortByLastName(boolean descending){
		
		List<Purchase> return_List = new LinkedList<Purchase>();
		
		Collections.sort(customers, this);
		
		if(descending == true) {
			Collections.reverse(customers);
		}
		
		for(Customer customer : customers) {
			for(Purchase purchase : purchases) {
				if(customer.getId() == purchase.getCustomer_id()) {
					return_List.add(purchase);
				}
			}
		}
		
		return return_List;
	}

	@Override
	public int compare(Customer customer_1, Customer customer_2) {
		
		return customer_1.getLastName().compareTo(customer_2.getLastName());
		
//		if(result < 0) {
//			return -1;
//		}
//		else if(result > 0) {
//			return 1;
//		}
//		else {
//			return 0;
//		}
	}	
}
