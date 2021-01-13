/**
 * Solution for "PurchaseReport.java".   
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 3rd 
 */

package a01045801.io;

import java.io.IOException;
import java.util.Collection;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01045801.data.Book;
import a01045801.data.Customer;
import a01045801.data.Purchase;

public class PurchaseReport {

	protected static final String TARGET_FILENAME = "purchases_report.txt";
	private static final Logger LOG = LogManager.getLogger();

	public static void write(List<Purchase> purchases, Map<Long,Book> books, List<Customer> customers, boolean add_total) throws IOException {
		//output.
		Formatter output = new Formatter(TARGET_FILENAME);
		
		try {
			LOG.info("Beginning to write purchases report to the destination file: [" + TARGET_FILENAME + "].");
			output.format("Purchases Report%n");
			output.format("-------------------------------------------------------------------------------------------------------------------%n");
			output.format("Name                     Title                                                                            Price   %n");
			output.format("-------------------------------------------------------------------------------------------------------------------%n");

				for(Purchase purchase_data : purchases) {
					
					//Use for formatter arguments.
					Book the_book = new Book();
					Customer the_customer = new Customer();
					String full_name = "";
					
					//Convert Map books into list for iteration.
					List<Book> book_List = MapToList(books);
					
					//Find the book.
					for(Book book_info : book_List) {
						if(book_info.getId() == purchase_data.getBook_id()) {
							the_book = book_info;
						}
					}
					
					//Find the customer.
					for(Customer customer_info : customers) {
						if(customer_info.getId() == purchase_data.getCustomer_id()) {
							the_customer = customer_info;
						}
					}
					
					//Make a full name of customer.
					full_name += the_customer.getFirstName() + " " + the_customer.getLastName();
					
					
					//name title price
					output.format("%-24s %-80s $%.2f%n", full_name, the_book.getOriginal_Title(), purchase_data.getCost());
					LOG.info("Writing purchase: " + purchase_data.toString() + " to destination file.");
				}
				
				//If we want a total value do this.
				if(add_total == true) {
					double total_value = 0;
					
					for(Purchase purchase: purchases) {
						total_value += purchase.getCost();
					}
					
					output.format("%n%s %.2f%n", "Value of purchases: $", total_value);
				}

				output.format("-------------------------------------------------------------------------------------------------------------------");
		}
		finally {
			LOG.info("Finished writing purchases report in destination file: [ " + TARGET_FILENAME + " ].");
			output.close();
			LOG.info("Closed file: [ " + TARGET_FILENAME + " ].");
		}
	}
	
	
	/**
	 * 
	 * @param books a map to be converted to an array
	 * @return a List of Book objects made from map's values.
	 */
	private static List<Book> MapToList(Map<Long,Book> books){
		Collection<Book> books_values = books.values();
		List<Book> books_List = new LinkedList<Book>();
		
		//extrapolate book objects from the map
		for(Book book_obj : books_values) {
			books_List.add(book_obj);
		}
		
		return books_List;
		
	}
	
}
