/**
 * Solution for "PurchaseReader.java".   
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 1st 
 */

package a01045801.io;

import java.io.File; 
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01045801.data.Purchase;

public class PurchaseReader {

	private static final Logger LOG = LogManager.getLogger();

	private PurchaseReader() {
	}

	/**
	 * 
	 * @param file_Name given from driver; should be a purchases list of CSV file
	 *                  type.
	 * @return a Map of Long, list<Long> Long being a customer ID and list<Long>
	 *         being all book_id(s) purchased by said customer.
	 * @throws IOException
	 */
	public static List<Purchase> read(String file_Name) throws IOException {

		File file = new File(file_Name);
		FileReader in;
		//FileReader in_2;
		Iterable<CSVRecord> records;
		//Iterable<CSVRecord> records_DEEP;
		in = new FileReader(file);
		// in_2 = new FileReader(file);
		records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		// records_DEEP = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in_2);

		LOG.debug("Reading" + file.getAbsolutePath());
		try {
			// Map<Long, List<Long>> purchases = new HashMap<>();
			// Customer the_customer;
			// ArrayList<Long> entered_ids = new ArrayList<Long>();
			// ArrayList<Long> book_ids_purchased = new ArrayList<Long>();
			// int purchases_counter = 1;

			List<Purchase> purchases = new ArrayList<Purchase>();

			for (CSVRecord record : records) {

				LOG.debug("Preparing to parse data for Purchase Objects.");

				Long id = Long.parseLong(record.get("id"));
				LOG.debug("Parsed data for Purchase Object's id field: [" + record.get("id") + "].");

				Long customer_id = Long.parseLong(record.get("customer_id"));
				LOG.debug("Parsed data for Purchase Object's customer_id field: [" + record.get("customer_id") + "].");

				Long book_id = Long.parseLong(record.get("book_id"));
				LOG.debug("Parsed data for Purchase Object's book_id field: [" + record.get("book_id") + "].");

				double cost = Double.parseDouble(record.get("price"));
				LOG.debug("Parsed data for Purchase Object's cost field: [" + record.get("price") + "].");

				Purchase new_purchase = new Purchase.Builder(customer_id, book_id).setId(id).setCost(cost).build();
				LOG.debug("Created a Purchase object to store in the array: [" + new_purchase.toString() + "].");

				purchases.add(new_purchase);
				LOG.debug("Added the purchase object to the array!");
			}

			return purchases;
		}
		finally {
			in.close();
		}
	}

}
