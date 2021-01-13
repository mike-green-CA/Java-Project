/**
 * Solution for "BookReader.java".  
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 1st 
 */

package a01045801.io;

import java.io.File; 
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01045801.book.ApplicationException;
import a01045801.data.Book;

public class BookReader {

	private static final Logger LOG = LogManager.getLogger();

	private BookReader() {
	}
	
	
	
	/**
	 * 
	 * @param file_Name is the file_name provided in main
	 * @return a Map<Long, Book> full of book objects; the Long key is the ID.
	 * 
	 * For more method information see LOG -> books.log
	 * 
	 * @throws ApplicationException
	 * @throws IOException
	 */
	public static Map<Long, Book> read(String file_Name) throws ApplicationException, IOException {
		File file = new File(file_Name);
		FileReader in;
		Iterable<CSVRecord> records;
		in = new FileReader(file);
		records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		
		try {
			Map<Long, Book> books = new HashMap<>();
			int book_counter = 1;

			LOG.debug("Reading" + file.getAbsolutePath());
			for (CSVRecord record : records) {
				
				LOG.info("Getting data for Book #"+ book_counter +".");
			
				String id_parsed = record.get("book_id");	// Needs to be converted
				
				String book_isbn = record.get("isbn");
				//if parsed data for book_isbn is null replace with: n/a.
				if(book_isbn.isBlank()) {
					book_isbn = "n/a";
					LOG.error("isbn for book #" + book_counter + " was null and replace with 'n/a'.");	
				}
				
				String authors = record.get("authors");
				String original_publication_year_parsed = record.get("original_publication_year");	// Needs to be converted
				String original_Title = record.get("original_title");
			
				String average_rating_parsed = record.get("average_rating");	// Needs to be converted
				String ratings_count_parsed = record.get("ratings_count");	// Needs to be converted
				String image_url = record.get("image_url");
			
				LOG.info("Successfully parsed in a line of data from CSV for Book data.");
			
				Book book_to_add = null;
				LOG.info("Created a Book object #"+ book_counter + " [book_to_add] to store the parsed data.");
			
				LOG.info("Converting all parsed String data into appropriate Book data values.");
				LOG.info("Fields to be converted are: id, original_publication_year, rating, ratingsCount.");
			
				long id = Long.parseLong(id_parsed);
				LOG.info("Successfully converted id_parsed(String) into id(Long)!");
			
				int original_publication_year = Integer.parseInt(original_publication_year_parsed);
				LOG.info("Successfully converted original_publication_year_parsed(String) into original_publication_year(int)!");
			
				float average_rating = Float.parseFloat(average_rating_parsed);
				LOG.info("Successfully converted average_rating_parsed(String) into average_rating(float)!");
			
				int ratings_count = Integer.parseInt(ratings_count_parsed);
				LOG.info("Successfully converted ratings_count_parsed(String) into ratings_count(int)!");
				LOG.info("Successfully converted all parsed String data into appropriate Book data values.");
			
				LOG.info("Now storing all parsed & converted data into the Book object #"+ book_counter + " [book_to_add]");
				book_to_add = new Book.Builder(id)
							.setBook_isbn(book_isbn)
							.setAuthors(authors)
							.setOriginal_publication_year(original_publication_year)
							.setOriginal_Title(original_Title)
							.setRating(average_rating)
							.setRatings_count(ratings_count)
							.setImage_url(image_url)
							.build();
				LOG.info("Successfully stored all necessary data into our Book object #"+ book_counter + " [book_to_add].");
		
				LOG.info("Added [book_to_add] object " + book_counter + " into our Map 'books'.");
				LOG.info("--------------------------");
				books.put(book_to_add.getId(), book_to_add);
				++book_counter;
			}//end of for loop
			
			LOG.info("Successfully obtained and stored "+ book_counter +" books into out map");
			
			return books;
		}
		finally {
			LOG.info("Closing the in file [" + file.toString() +"].");
			in.close();	
		}
	}
}
