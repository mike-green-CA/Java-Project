/**
 * Solution for "CompareByTitle.java".    
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 3rd 
 */

package a01045801.util;

import a01045801.data.Book;
import a01045801.data.Purchase;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CompareByTitle implements Comparator<Book> {
	
	private List<Purchase> purchases;
	private Map<Long,Book> books;
	 
	public CompareByTitle(List<Purchase> purchases, Map<Long,Book> books) {
		this.purchases = purchases;
		this.books = books;
	}
	
	/**
	 * @return will be a sorted, in ascending, array of customer's according to their JoinDates.
	 */
	public List<Purchase> sortByTitle(List<Purchase> purchases, boolean descending){
		
		Collection<Book> book_values = books.values();
		List<Book> book_List = new LinkedList<Book>();
		List<Purchase> return_list = new LinkedList<Purchase>();
		
		for(Book book_obj : book_values) {
			book_List.add(book_obj);
		}
		
		Collections.sort(book_List, this);
		
		//Only switch the List if it is to be sorted descending.
		if(descending == true){
			Collections.reverse(book_List);
		}
		
		for(Book book_data : book_List) {
			for(Purchase purchase_info : purchases) {
				if(book_data.getId() == purchase_info.getBook_id()) {
					return_list.add(purchase_info);
					break;
				}
			}
		}
		
		return return_list;
	}

	@Override
	public int compare(Book book_1, Book book_2) {
		
		int result = book_1.getOriginal_Title().compareTo(book_2.getOriginal_Title());
		
		if(result < 0) {
			return -1;
		}
		else if(result > 0) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
}