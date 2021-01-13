/**
 * Solution for "BookReport.java".    
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 3rd 
 */

package a01045801.io;

import java.io.IOException;
import java.util.Collection;
import java.util.Formatter;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01045801.data.Book;

public class BookReport {

	protected static final String TARGET_FILENAME = "book_report.txt";
	private static final Logger LOG = LogManager.getLogger();

	public static void write(Map<Long, Book> books) throws IOException {

		final int MAX_AUTHOR_FIELD = 40;
		final int MAX_TITLE_FIELD = 40;
		
		// output.
		Formatter output = new Formatter(TARGET_FILENAME);

		try {
			LOG.info("Beginning to write customer report to the destination file: [" + TARGET_FILENAME + "].");
			output.format("Book Report%n");
			output.format("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
			output.format("ID       ISBN         Authors                                  Title                                    Year Rating Ratings Count Image URL                                                   %n");
			output.format("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------%n");
			
			//Quickly cast out Map to a iteratable collection.
			Collection<Book> books_iteratable = books.values();

			for (Book book_data : books_iteratable) {
				
				String authors = ""; 
				String title = 	"";
				
				
				if(book_data.getAuthors().length() > MAX_AUTHOR_FIELD) {
					LOG.debug("Authors field is too long for formatter; now editting it.");
					authors += book_data.getAuthors().substring(0, 37) + "...";
						if(book_data.getOriginal_Title().length() > MAX_TITLE_FIELD) {
							LOG.debug("Title and authors fields are too long for formatter; now editting them.");
							title += book_data.getOriginal_Title().substring(0, 37) + "...";
							//If authors and title fields are too long output this.
							output.format("%08d %-12s %-40s %-40s %4d %6.3f %13d %-60s%n", book_data.getId(),book_data.getBook_ISBN(), authors, title, book_data.getOriginal_Publication_Year(), book_data.getRating(), book_data.getRatings_Count(),book_data.getImage_URL());
							LOG.debug("Writing book: " + book_data.toString() + " to destination file. Edited: authors, and title.");
						}
						//If the only the authors is to long output this
						output.format("%08d %-12s %-40s %-40s %4d %6.3f %13d %-60s%n", book_data.getId(),book_data.getBook_ISBN(), authors, book_data.getOriginal_Title(), book_data.getOriginal_Publication_Year(), book_data.getRating(), book_data.getRatings_Count(),book_data.getImage_URL());
						LOG.debug("Writing book: " + book_data.toString() + " to destination file. Edited: authors.");
				}
				else if(book_data.getOriginal_Title().length() > MAX_TITLE_FIELD) {
					LOG.debug("Title field is too long for formatter; now editting it.");
					title += book_data.getOriginal_Title().substring(0, 37) + "...";
					//If the title field is too long output this.
					output.format("%08d %-12s %-40s %-40s %4d %6.3f %13d %-60s%n", book_data.getId(),book_data.getBook_ISBN(), book_data.getAuthors(), title, book_data.getOriginal_Publication_Year(), book_data.getRating(), book_data.getRatings_Count(),book_data.getImage_URL());
					LOG.debug("Writing book: " + book_data.toString() + " to destination file. Edited: title.");
				}
				else {
					//If everything is ok output this.
					output.format("%08d %-12s %-40s %-40s %4d %6.3f %13d %-60s%n", book_data.getId(),book_data.getBook_ISBN(), book_data.getAuthors(), book_data.getOriginal_Title(), book_data.getOriginal_Publication_Year(), book_data.getRating(), book_data.getRatings_Count(),book_data.getImage_URL());
					LOG.debug("Writing book: " + book_data.toString() + " to destination file.");
				}
			}
			output.format("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		
		} finally {
			LOG.info("Finished writing book report in destination file: [ " + TARGET_FILENAME + " ].");
			output.close();
			LOG.info("Closed file: [ " + TARGET_FILENAME + " ].");
		}
	}

}
