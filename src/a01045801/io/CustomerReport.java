/**
 * Solution for "CustomerReport.java".    
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 3rd
 */

package a01045801.io;

import java.io.IOException; 
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01045801.data.Customer;

public class CustomerReport {
	
	protected static final String TARGET_FILENAME = "customers_report.txt";
	private static final Logger LOG = LogManager.getLogger();
	

	public static void write(List<Customer> customers) throws IOException {
		//output.
		Formatter output = new Formatter(TARGET_FILENAME);
		LocalDateTime current_Date = LocalDateTime.now();
		 
		try {
			LOG.info("Beginning to write customer report to the destination file: [" + TARGET_FILENAME + "].");
			output.format("Customer Report%n");
			output.format("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
			output.format("   #. ID     First name   Last name    Street                                   City                      Postal Code  Phone           Email                                    Join Date     Length%n");
			output.format("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
	
			int position = 1;

				for(Customer customer_data : customers) {
					
					//Creates a formated date string for customer_data. IE: May 05 1999 || Jun. 06 2001.
					String formatted_date = fancy_date(customer_data);	
					LOG.debug("Date has been transformed. From: " + customer_data.getJoinedDate().toString() + " To: " + formatted_date +".");
					
					output.format("%4d. %06d %-12s %-12s %-40s %-25s %-12s %-15s %-40s %-12s %7d%n", position, customer_data.getId(), customer_data.getFirstName(), customer_data.getLastName(), customer_data.getStreet(), customer_data.getCity(), customer_data.getPostalCode(), customer_data.getPhone(), customer_data.getEmailAddress(),formatted_date, (current_Date.getYear() - customer_data.getJoinedDate().getYear()));
					++position;
					LOG.info("Writing customer: " + customer_data.toString() + " to destination file.");
				}

				output.format("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		}
		finally {
			LOG.info("Finished writing customer report in destination file: [ " + TARGET_FILENAME + " ].");
			output.close();
			LOG.info("Closed file: [ " + TARGET_FILENAME + " ].");
		}
	}
	
	/**
	 * @param the object with a date.
	 *
	 * May 5 1997 -> May 05 1997
	 * June 15 1997 -> Jun 15 1997
	 * etc.
	 * 
	 * @return a string made out of the Object date's data values.
	 */
	private static String fancy_date(Customer customer) {
		
		String date_device = "";
		
			date_device += customer.getJoinedDate().getMonth().toString().substring(0,1).toUpperCase(); //First letter of month Capitalized
			date_device += customer.getJoinedDate().getMonth().toString().substring(1,3).toLowerCase(); //2 more letters of month month lower case.
			date_device += " ";	//Add a space.
			
			//if day is less then 10 we must throw a zero infront.
			if(customer.getJoinedDate().getDayOfMonth() < 10) {
				date_device += "0" + customer.getJoinedDate().getDayOfMonth();
			}
			else {
				date_device += customer.getJoinedDate().getDayOfMonth();
			}
			
			date_device += " " + customer.getJoinedDate().getYear();
		
		return date_device;
	}

}
