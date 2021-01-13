/**
 * Solution for "Book.java".  
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 1st 
 */

package a01045801.data;


public class Book implements Comparable<Book> {

	public static final int ATTRIBUTE_COUNT = 7;

	private long id;
	private String book_isbn;
	private String authors;
	private int original_publication_year;
	private String original_Title;
	private float rating;
	private int ratings_count;
	private String image_url;

	public static class Builder {
		// Required parameters
		private final long id;
		
		// Non required parameters 
		private String book_isbn;
		private String authors;
		private int original_publication_year;
		private String original_Title;
		private float rating;
		private int ratings_count;
		private String image_url;
		
		public Builder(long id) {
			this.id = id;		
		}
		
		/**
		 * @param book_isbn the book_isbn to set
		 */
		public Builder setBook_isbn(String book_isbn) {
			this.book_isbn = book_isbn;
			return this;
		}

		/**
		 * @param authors the authors to set
		 */
		public Builder setAuthors(String authors) {
			this.authors = authors;
			return this;
		}

		/**
		 * @param original_publication_year the original_publication_year to set
		 */
		public Builder setOriginal_publication_year(int original_publication_year) {
			this.original_publication_year = original_publication_year;
			return this;
		}

		/**
		 * @param original_Title the original_Title to set
		 */
		public Builder setOriginal_Title(String original_Title) {
			this.original_Title = original_Title;
			return this;
		}

		/**
		 * @param rating the rating to set
		 */
		public Builder setRating(float rating) {
			this.rating = rating;
			return this;
		}

		/**
		 * @param ratings_count the ratings_count to set
		 */
		public Builder setRatings_count(int ratings_count) {
			this.ratings_count = ratings_count;
			return this;
		}

		/**
		 * @param image_url the image_url to set
		 */
		public Builder setImage_url(String image_url) {
			this.image_url = image_url;
			return this;
		}

		/**
		 * Build the Book object.
		 * 
		 * @return the Book object.
		 */
		public Book build() {
			return new Book(this);
		}
	}

	/**
	 * Default Constructor
	 */
	private Book(Builder builder) {
		id = builder.id;
		book_isbn = builder.book_isbn;
		authors = builder.authors;
		original_publication_year = builder.original_publication_year;
		original_Title = builder.original_Title;
		rating = builder.rating;
		ratings_count = builder.ratings_count;
		image_url = builder.image_url;
	}

	public Book() {
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the isbn
	 */
	public String getBook_ISBN() {
		return book_isbn;
	}

	/**
	 * @return the authors
	 */
	public String getAuthors() {
		return authors;
	}

	/**
	 * @return the original_publication_year
	 */
	public int getOriginal_Publication_Year() {
		return original_publication_year;
	}

	/**
	 * @return the title
	 */
	public String getOriginal_Title() {
		return original_Title;
	}

	/**
	 * @return the rating
	 */
	public float getRating() {
		return rating;
	}

	/**
	 * @return the ratings_count
	 */
	public int getRatings_Count() {
		return ratings_count;
	}
	
	/**
	 * @return the image_url
	 */
	public String getImage_URL() {
		return image_url;
	}
	

	/**
	 * @param a Book Object for comparing.
	 * 
	 * @return an int of -1,0,1 
	 * 1: indicates that the param Book's author is before alphabetically then this Book's. 
	 * 0: indicates they are the author.
	 * -1: indicates that param Book's author is after alphabetically then this Book's.
	 */
	@Override
	public int compareTo(Book book) {
		
		return this.getAuthors().compareTo(book.getAuthors());	
	}
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", book_isbn=" + book_isbn + ", authors=" + authors + ", original_publication_year="
				+ original_publication_year + ", original_title=" + original_Title + ", rating=" + rating
				+ ", ratings_count=" + ratings_count + "]";
	}

	
}
