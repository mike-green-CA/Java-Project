/**
 * Solution for "CompareByJoinDate.java".    
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 3rd 
 */

package a01045801.util;

import a01045801.data.Customer; 

import java.util.ArrayList;
import java.util.Collections;

public class CompareByJoinDate {
	
	private ArrayList<Customer> customer;
	
	public CompareByJoinDate(ArrayList<Customer> customer) {
		this.customer = customer;		
	}
	
	/**
	 * @return will be a sorted, in ascending, array of customer's according to their JoinDates.
	 */
	public ArrayList<Customer> sortByJoinDate(){
		
		Collections.sort(customer);
		
		return customer;
	}	
}
	

