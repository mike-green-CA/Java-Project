/**
 * Solution for "CustomerReader.java".  
 * 
 * Author: Michael Green
 * Created: 2020, June 1st 
 */

package a01045801.io;

import java.io.File;  
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01045801.book.ApplicationException;
import a01045801.data.Customer;
import a01045801.util.Validator;


public class CustomerReader {

	public static final String RECORD_DELIMITER = "\\r?\\n";
	public static final String FIELD_DELIMITER = "\\|";
	private static final Logger LOG = LogManager.getLogger();

	
	/**
	 * private constructor to prevent instantiation
	 */
	private CustomerReader() {
	}

	/**
	 * Read the customer input data.
	 * 
	 * @param data
	 *            The input data.
	 * @return An array of customers.
	 * @throws ApplicationException
	 *             throws an exception if the input contains invalid data
	 * @throws FileNotFoundException 
	 */
	public static ArrayList<Customer> read(String file_Name) throws ApplicationException, FileNotFoundException {
		
		ArrayList<Customer> customers = new ArrayList<Customer>();
		File source_File = new File(file_Name);
		Scanner input = new Scanner(source_File);
		LOG.info("File: [ " + file_Name + " ] opened.");
		
		try {			
			//This second local scanner is used only to calculate how many lines are in the file. Then destroyed.
			Scanner line_counter = new Scanner(source_File);
			
			//Lets Quickly count how many lines our input has.
			int lines_of_input = 0;
			LOG.info("Counting total lines in the File: [ " + file_Name + " ].");
			while(line_counter.hasNextLine() == true) {
				lines_of_input++;
				line_counter.nextLine();
			}
			LOG.info("File: [ " + file_Name + " ] has: " + lines_of_input +" lines of text.");
			
			//goodbye line_counter. Thanks for helping.
			line_counter.close();
			line_counter = null;
			
			/** Now lets get each line stored into a string collection for Customer object creation.
			 * 
			 * Also remember customer_data will be initialized to lines_of_input - 1 because we don't want...
			 * To include that first ID|FIRST_NAME| etc line in the scanner.
			 * We will also skip that line before filling up customer_data.
			 */
			String[] customer_data = new String[lines_of_input - 1];
			input.nextLine(); //skipping the first line in scanner ID|First_Name|etc...
			
			//Now lets fill up our customer_data collection
			for(int i = 0; i < lines_of_input - 1; i++) {
				customer_data[i] = input.nextLine();
			}
			
			int customer_counter = 1;
			//Now we will make Customer objects and add them to a Customer object collection -> "customers".
			for(int i = 0; i < customer_data.length; i++) {
				LOG.debug("Processing Customer #"+ customer_counter +". See blow this line for that data.");
				Customer customer = readCustomerString(customer_data[i]);
				customers.add(customer);
				LOG.debug("Created customer object above has been added to customer collection.");
				++customer_counter;
			}
		}
		catch (FileNotFoundException e ) {
			System.err.println("The file could not be found/opened!");
			LOG.error("Failed to find/open/read the file.");
			System.exit(-1);
		}
		finally {		
			LOG.info("Closed file: [ " + file_Name + " ].");
			input.close();	
		}
		
		return customers;	
	}

	/**
	 * Parse a Customer data string into a CUstomer object;
	 * 
	 * @param row
	 *            a customer record string
	 * @throws ApplicationException
	 *             throws an exception if the input contains invalid data
	 */
	private static Customer readCustomerString(String row) throws ApplicationException {
		String[] elements = row.split(FIELD_DELIMITER);
		if (elements.length != Customer.ATTRIBUTE_COUNT) {
			LOG.error("Invalid amount of data values within the line of Customer data!");
			throw new ApplicationException(String.format("Expected %d but Got %d: %s", Customer.ATTRIBUTE_COUNT, elements.length, Arrays.toString(elements)));
		}

		LOG.debug("-----===== Beginning to obtain customer data values from line of text. =====-----");
		int index = 0;
		long id = Integer.parseInt(elements[index++]);
		LOG.debug("Got a long 'id' data value for a customer object. Value = [ " + id + " ].");
		
		String firstName = elements[index++];
		LOG.debug("Got a String 'firstName' data value for a customer object. Value = [ " + firstName + " ].");
		
		String lastName = elements[index++];
		LOG.debug("Got a String 'lastName' data value for a customer object. Value = [ " + lastName + " ].");
		
		String street = elements[index++];
		LOG.debug("Got a String 'street' data value for a customer object. Value = [ " + street + " ].");
		
		String city = elements[index++];
		LOG.debug("Got a String 'city' data value for a customer object. Value = [ " + city + " ].");
		
		String postalCode = elements[index++];
		LOG.debug("Got a String 'postalCode' data value for a customer object. Value = [ " + postalCode + " ].");
		
		String phone = elements[index++];
		LOG.debug("Got a String 'phone' data value for a customer object. Value = [ " + phone + " ].");
		
		String emailAddress = Validator.getValidEmail(elements[index++]);
		LOG.debug("Got a String 'emailAddress' data value for a customer object. Value = [ " + emailAddress + " ].");
		
		LocalDate joinDate = Validator.getValidJoinedDate(elements[index]);
		LOG.debug("Got a LocalDate 'joinDate' data value for a customer object. Value = [ " + joinDate + " ].");
		
		Customer customer = null;
		LOG.debug("Created the new Customer object 'customer' for all the created data values.");

		LOG.debug("Calling Customer.Builder");
		customer = new Customer.Builder(id, phone) //
				.setFirstName(firstName) //
				.setLastName(lastName) //
				.setStreet(street) //
				.setCity(city) //
				.setPostalCode(postalCode) //
				.setEmailAddress(emailAddress) //
				.setJoinedDate(joinDate).build();

		LOG.debug("Customer object created. Value = " + customer.toString() + ".");
		return customer;
	}

}