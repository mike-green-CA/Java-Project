/**
 * Solution for "BookStore.java".   
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 1st 
 */

package a01045801.book;

import java.io.FileInputStream; 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import a01045801.data.Book;
import a01045801.data.Customer;
import a01045801.data.Purchase;
import a01045801.io.BookReader;
import a01045801.io.BookReport;
import a01045801.io.CustomerReader;
import a01045801.io.CustomerReport;
import a01045801.io.PurchaseReader;
import a01045801.io.PurchaseReport;
import a01045801.util.CompareByAuthor;
import a01045801.util.CompareByJoinDate;
import a01045801.util.CompareByLastName;
import a01045801.util.CompareByTitle;
import a01045801.util.SpecifiyCustomerPurchases;


public class BookStore {

	private static final String LOG4J_CONFIG_FILENAME = "log4j2.xml";
	private static final String BOOKS_DATA_FILE = "books500.csv";
	private static final String PURCHASES_DATA_FILE = "purchases.csv";
	private static final String CUSTOMER_DATA_FILE = "customers.dat";
	
	static {
		configureLogging();
	}
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * Bcmc Constructor. Processes the commandline arguments
	 * ex. -inventory -make=honda -by_count -desc -total -service
	 * 
	 * @throws ApplicationException
	 * @throws ParseException
	 */
	public BookStore(String[] args) throws ApplicationException, ParseException {
		LOG.info("Created Bcmc");

		BookOptions.process(args);
	}

	/**
	 * Entry point to GIS
	 * 
	 * @param args
	 * @throws ApplicationException 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws ApplicationException {
		Instant startTime = Instant.now();
		LOG.info(startTime);
		 
		// start the Book System
		try {
			BookStore book = new BookStore(args);
			if (BookOptions.isHelpOptionSet()) {
				BookOptions.Value[] values = BookOptions.Value.values();
				System.out.format("%-5s %-15s %-10s %s%n", "Option", "Long Option", "Has Value", "Description");
				for (BookOptions.Value value : values) {
					System.out.format("-%-5s %-15s %-10s %s%n", value.getOption(), ("-" + value.getLongOption()), value.isHasArg(),
							value.getDescription());
				}

				return;
			}

			book.run();
		} catch (Exception e) {
			e.printStackTrace();
			LOG.debug(e.getMessage());
		}

		Instant endTime = Instant.now();
		LOG.info(endTime);
		LOG.info(String.format("Duration: %d ms", Duration.between(startTime, endTime).toMillis()));
	}

	/**
	 * Configures log4j2 from the external configuration file specified in LOG4J_CONFIG_FILENAME.
	 * If the configuration file isn't found then log4j2's DefaultConfiguration is used.
	 */
	private static void configureLogging() {
		ConfigurationSource source;
		try {
			source = new ConfigurationSource(new FileInputStream(LOG4J_CONFIG_FILENAME));
			Configurator.initialize(null, source);
		} catch (IOException e) {
			System.out.println(String.format("WARNING! Can't find the log4j logging configuration file %s; using DefaultConfiguration for logging.",
					LOG4J_CONFIG_FILENAME));
			Configurator.initialize(new DefaultConfiguration());
		}
	}

	/**
	 * @throws ApplicationException
	 * @throws IOException 
	 * 
	 */
	private void run() throws ApplicationException, IOException {
		LOG.debug("run()");
		
		generateReports();
	}

	/**
	 * Generate the reports from the input data
	 * 
	 * @throws ApplicationException 
	 * @throws IOException 
	 */
	private void generateReports() throws ApplicationException, IOException {
		LOG.debug("generating the reports");
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Map<Long,Book> books = new HashMap<Long,Book>();
		List<Purchase> purchases = new ArrayList<Purchase>();
		
		customers = CustomerReader.read(CUSTOMER_DATA_FILE);
		LOG.info("Reading in file for customer data: " + CUSTOMER_DATA_FILE.toString() + "; and assigning it to 'customers'.");
		books = BookReader.read(BOOKS_DATA_FILE);
		LOG.info("Reading in file for book data: " + BOOKS_DATA_FILE.toString() + "; and assigning it to 'books'.");
		purchases = PurchaseReader.read(PURCHASES_DATA_FILE);
		LOG.info("Reading in file for purchase data: " + PURCHASES_DATA_FILE.toString() + "; and assigning it to 'purchases'.");

		CompareByJoinDate customer_comparer = new CompareByJoinDate(customers);
		CompareByAuthor book_comparer = new CompareByAuthor(books);
		CompareByTitle purchase_comparer_title = new CompareByTitle(purchases, books);
		CompareByLastName purchase_comparer_lastName = new CompareByLastName(purchases, customers);
		SpecifiyCustomerPurchases purchase_comparer_customerID = new SpecifiyCustomerPurchases(purchases);
		
		
		
		/**
		 * Customer Report!
		 * 4 if statements are as follows:
		 * 	1: True, false = write to file a: customer report sorted ascending order.
		 * 	2: True, true = write to file a: customer report sorted descending order.
		 * 	3: Should never print = LOG an error stating so.
		 * 	4: False, false = write to file a: customer report.	
		 */
		
		if (BookOptions.isCustomersOptionSet()) {
			LOG.debug("generating the customer report");
			// for program args: -c -J -d
			System.out.println("Cutomer Report: " + BookOptions.isCustomersOptionSet());
			System.out.println("Cutomer Join Date: " + BookOptions.isByJoinDateOptionSet());
			System.out.println("Cutomer Join Date DESC: " + BookOptions.isDescendingOptionSet());
						
			//Sorted by join date = true; sorted descending = false.
			if(BookOptions.isByJoinDateOptionSet() && BookOptions.isDescendingOptionSet() == false) {
				customers = customer_comparer.sortByJoinDate();
				LOG.info("Sorted ArrayList of customers by JoinDate Ascending.");	
				CustomerReport.write(customers);
			}
			//Sorted by join date = true; sorted descending = true.
			else if(BookOptions.isByJoinDateOptionSet() && BookOptions.isDescendingOptionSet()) {
				customers = customer_comparer.sortByJoinDate();
				LOG.info("Sorted ArrayList of customers by JoinDate Ascending.");	
				Collections.reverse(customers);
				LOG.info("Reveresed the ArrayList so that is now in order of Descending.");
				CustomerReport.write(customers);
			}
			else if(BookOptions.isByJoinDateOptionSet() == false && BookOptions.isDescendingOptionSet()){
				LOG.error("THIS MESSAGE SHOULD NEVER PRINT!");
				LOG.info("Invalid argument pattern! Cannot sort by Descending of nothing.");
			}
			else{
				CustomerReport.write(customers);
			}
			
			LOG.info("Finished writing customers report!");
		}
		
		/**
		 * Book Report!
		 * 
		 * 3 if statements as follows:
		 * 	1: True = write to file: a book report sorted by author ascending.
		 *  2: True = write to file: a book report sort by author descending.
		 * 	2: False = write to file: a book report.
		 * 	3: Should never print = LOG an error stating so.
		 */
	
		if (BookOptions.isBooksOptionSet()) {
			LOG.debug("generating the book report");
			System.out.println("Book Report: " + BookOptions.isBooksOptionSet());
			System.out.println("Book by Author: " + BookOptions.isByAuthorOptionSet());
			
			//sorted by author = true, descending = false.
			if(BookOptions.isByAuthorOptionSet() && BookOptions.isDescendingOptionSet() == false) {
				books = book_comparer.sortByAuthor(BookOptions.isDescendingOptionSet());
				LOG.debug("Sorted our books Map by Author ascending!");
				BookReport.write(books);	
			}
			
			//sorted by author = true, descending = true.
			if(BookOptions.isByAuthorOptionSet() && BookOptions.isDescendingOptionSet()) {
				books = book_comparer.sortByAuthor(BookOptions.isDescendingOptionSet());
				LOG.debug("Sorted our books Map by Author ascending!");
				BookReport.write(books);	
			}
			
			//sorted by author = false.
			else if(BookOptions.isByAuthorOptionSet() == false) {
				LOG.debug("Writing unsorted book report.");
				BookReport.write(books);
				
			}
			else if(BookOptions.isBooksOptionSet() == false && BookOptions.isByAuthorOptionSet()) {
				LOG.error("THIS MESSAGE SHOULD NEVER PRINT!");
				LOG.info("Invalid argument pattern! Cannot sort by Author of nothing.");
			}
			
			
			
		}
		
		/**
		 * Purchase Report!
		 * 
		 * If statesments as follows
		 * 
		 *  1 - Basic report; include total.
		 *  2 - Sort by title Ascending; include total.
		 *  3 - Sort by title Descending; include total.
		 *  4 - Sort by last name Ascending; include total.
		 *  5 - Sort by last name descending; include total.
		 *  6 - Sort by Customer id; include total.
		 *  7 - Sort by Customer id, and title ascending; include total.
		 *  8 - Sort by Customer id, and title descending; include total. 
		 *  
		 *  if statements 9-16 are the same order as 1-8 the only difference being the add total value is false.
		 * 
		 */
	
		if (BookOptions.isPurchasesOptionSet()) {
			LOG.debug("generating the inventory report");
			System.out.println("Purchase Report: " + BookOptions.isPurchasesOptionSet());
			System.out.println("Purchase add total: " + BookOptions.isTotalOptionSet());
			System.out.println("Purchase DESC order: " + BookOptions.isDescendingOptionSet());
			System.out.println("Purchase Title sort: " + BookOptions.isByTitleOptionSet());
			System.out.println("Purchase Customer ID sort: " + BookOptions.getCustomerId());
			System.out.println("Purchase sort by Last name: " + BookOptions.isByLastnameOptionSet());
			
			/**
			 * If we want a total
			 * First if statements are here.
			 */
			if(BookOptions.isTotalOptionSet()){
				
				//1
				//descending == false, sort title == false, customer id == null, sort last name == false
				if(BookOptions.isDescendingOptionSet() == false && BookOptions.isByTitleOptionSet() == false && BookOptions.getCustomerId() == null && BookOptions.isByLastnameOptionSet() == false) {
					PurchaseReport.write(purchases, books, customers, true);
				}
				
				//2
				//descending == false, sort_title == true, specific customer_id == false, sort last name == false.
				if(BookOptions.isDescendingOptionSet() == false && BookOptions.isByTitleOptionSet() && BookOptions.getCustomerId() == null && BookOptions.isByLastnameOptionSet() == false) {
				
					purchases = purchase_comparer_title.sortByTitle(purchases, false);
					PurchaseReport.write(purchases, books, customers, true);
				}
			
				//3
				//descending == true, sort_title == true, specific customer_id == null, sort last name == false.
				if(BookOptions.isDescendingOptionSet() && BookOptions.isByTitleOptionSet() && BookOptions.getCustomerId() == null && BookOptions.isByLastnameOptionSet() == false) {
				
					purchases = purchase_comparer_title.sortByTitle(purchases, true);
					PurchaseReport.write(purchases, books, customers, true);
				}
				
				//4
				//descending == false, sort_title == false, specific customer_id == false, sort last name == true.
				if(BookOptions.isDescendingOptionSet() == false && BookOptions.isByTitleOptionSet() == false && BookOptions.getCustomerId() == null && BookOptions.isByLastnameOptionSet()) {
				
					purchases = purchase_comparer_lastName.sortByLastName(false);
					PurchaseReport.write(purchases, books, customers, true);
				}
			
				//5
				//descending == true, sort_title == false, specific customer_id == null, sort last name == true.
				if(BookOptions.isDescendingOptionSet() && BookOptions.isByTitleOptionSet() == false && BookOptions.getCustomerId() == null && BookOptions.isByLastnameOptionSet()) {
				
					purchases = purchase_comparer_lastName.sortByLastName(true);
					PurchaseReport.write(purchases, books, customers, true);
				}
				
				//6
				//descending == false, sort_title == false, specific customer_id == something, sort last name == false.
				if(BookOptions.isDescendingOptionSet() == false && BookOptions.isByTitleOptionSet() == false && BookOptions.getCustomerId() != null && BookOptions.isByLastnameOptionSet() == false) {
				
					purchases = purchase_comparer_customerID.trimListByID(Long.parseLong(BookOptions.getCustomerId()));  
					PurchaseReport.write(purchases, books, customers, true);
				}
				
				//7
				//descending == false, sort_title == true, specific customer_id == something, sort last name == false.
				if(BookOptions.isDescendingOptionSet() == false && BookOptions.isByTitleOptionSet() && BookOptions.getCustomerId() != null && BookOptions.isByLastnameOptionSet() == false) {
				
					purchases = purchase_comparer_customerID.trimListByID(Long.parseLong(BookOptions.getCustomerId()));
					purchases = purchase_comparer_title.sortByTitle(purchases, false);
					PurchaseReport.write(purchases, books, customers, true);
				}
			
				//8
				//descending == true, sort_title == true, specific customer_id == something, sort last name == false.
				if(BookOptions.isDescendingOptionSet() && BookOptions.isByTitleOptionSet() == true && BookOptions.getCustomerId() != null && BookOptions.isByLastnameOptionSet() == false) {
				 
					purchases = purchase_comparer_customerID.trimListByID(Long.parseLong(BookOptions.getCustomerId()));
					purchases = purchase_comparer_title.sortByTitle(purchases, true);
					PurchaseReport.write(purchases, books, customers, true);
				}
			}
			else {
				
				
				//9
				//descending == false, sort title == false, customer id == null, sort last name == false
				if(BookOptions.isDescendingOptionSet() == false && BookOptions.isByTitleOptionSet() == false && BookOptions.getCustomerId() == null && BookOptions.isByLastnameOptionSet() == false) {
					PurchaseReport.write(purchases, books, customers, false);
				}
				
				//10
				//descending == false, sort_title == true, specific customer_id == false, sort last name == false.
				if(BookOptions.isDescendingOptionSet() == false && BookOptions.isByTitleOptionSet() && BookOptions.getCustomerId() == null && BookOptions.isByLastnameOptionSet() == false) {
				
					purchases = purchase_comparer_title.sortByTitle(purchases, false);
					PurchaseReport.write(purchases, books, customers, false);
				}
			
				//11
				//descending == true, sort_title == true, specific customer_id == null, sort last name == false.
				if(BookOptions.isDescendingOptionSet() && BookOptions.isByTitleOptionSet() && BookOptions.getCustomerId() == null && BookOptions.isByLastnameOptionSet() == false) {
				
					purchases = purchase_comparer_title.sortByTitle(purchases, true);
					PurchaseReport.write(purchases, books, customers, false);
				}
				
				//12
				//descending == false, sort_title == false, specific customer_id == false, sort last name == true.
				if(BookOptions.isDescendingOptionSet() == false && BookOptions.isByTitleOptionSet() == false && BookOptions.getCustomerId() == null && BookOptions.isByLastnameOptionSet()) {
				
					purchases = purchase_comparer_lastName.sortByLastName(false);
					PurchaseReport.write(purchases, books, customers, false);
				}
			
				//13
				//descending == true, sort_title == false, specific customer_id == null, sort last name == true.
				if(BookOptions.isDescendingOptionSet() && BookOptions.isByTitleOptionSet() == false && BookOptions.getCustomerId() == null && BookOptions.isByLastnameOptionSet()) {
				
					purchases = purchase_comparer_lastName.sortByLastName(true);
					PurchaseReport.write(purchases, books, customers, false);
				}
				
				//14
				//descending == false, sort_title == false, specific customer_id == something, sort last name == false.
				if(BookOptions.isDescendingOptionSet() == false && BookOptions.isByTitleOptionSet() == false && BookOptions.getCustomerId() != null && BookOptions.isByLastnameOptionSet() == false) {
				
					purchases = purchase_comparer_customerID.trimListByID(Long.parseLong(BookOptions.getCustomerId()));  
					PurchaseReport.write(purchases, books, customers, false);
				}
				
				//15
				//descending == false, sort_title == true, specific customer_id == something, sort last name == false.
				if(BookOptions.isDescendingOptionSet() == false && BookOptions.isByTitleOptionSet() == true && BookOptions.getCustomerId() != null && BookOptions.isByLastnameOptionSet() == false) {
				
					purchases = purchase_comparer_customerID.trimListByID(Long.parseLong(BookOptions.getCustomerId()));
					purchases = purchase_comparer_title.sortByTitle(purchases, false);
					PurchaseReport.write(purchases, books, customers, false);
				}
			
				//16
				//descending == true, sort_title == true, specific customer_id == something, sort last name == false.
				if(BookOptions.isDescendingOptionSet() && BookOptions.isByTitleOptionSet() == true && BookOptions.getCustomerId() != null && BookOptions.isByLastnameOptionSet() == false) {
				
					purchases = purchase_comparer_customerID.trimListByID(Long.parseLong(BookOptions.getCustomerId()));
					purchases = purchase_comparer_title.sortByTitle(purchases, true);
					PurchaseReport.write(purchases, books, customers, false);
				}
				
			}	
			
		}
	}
}
