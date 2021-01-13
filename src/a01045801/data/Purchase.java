/**
 * Solution for "Purchase.java".  
 * 
 * Author: Michael Green
 * Created: 2020, June 1st 
 */

package a01045801.data;

public class Purchase {

	private long id;
	private long customer_id;
	private long book_id;
	private double cost;

	
	public static class Builder {
		// Required parameters
		private final long customer_id;
		private final long book_id;
		
		
		// Optional parameters
		private double cost;
		private long id;
		
		public Builder(long customer_id, long book_id) {
			this.customer_id = customer_id;
			this.book_id = book_id;
		}
		
		/**
		 * 
		 * @param id to be set.
		 * @return returns the book.Builder object to allow for method chaining.
		 */
		public Builder setId(long id) {
			this.id = id;
			return this;
		}
		
		/**
		 * 
		 * @param id to be set.
		 * @return returns the book.Builder object to allow for method chaining.
		 */
		public Builder setCost(double cost) {
			this.cost = cost;
			return this;
		}
		
		public Builder(Builder builder) {
			this.id = builder.id;
			this.customer_id = builder.customer_id;
			this.book_id = builder.book_id;
			this.cost = builder.cost;
		}
		
		/**
		 * Build the Book object.
		 * 
		 * @return the Book object.
		 */
		public Purchase build() {
			return new Purchase(this);
		}
		
	}
	
	
	/**
	 * Default constructor.
	 */
	private Purchase(Builder builder) {
		id = builder.id;
		customer_id = builder.customer_id;
		book_id = builder.book_id;
		cost = builder.cost;
	}


	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}


	/**
	 * @return the customer_id
	 */
	public long getCustomer_id() {
		return customer_id;
	}


	/**
	 * @return the book_id
	 */
	public long getBook_id() {
		return book_id;
	}


	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}


	@Override
	public String toString() {
		return "Purchase [id=" + id + ", customer_id=" + customer_id + ", book_id=" + book_id + ", cost=" + cost + "]";
	}

}
