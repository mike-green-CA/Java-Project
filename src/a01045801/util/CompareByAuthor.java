/**
 * Solution for "CompareByAuthor.java".   
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 3rd
 */

package a01045801.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import a01045801.data.Book;

public class CompareByAuthor {
	
	private Map<Long,Book> books;
	
	public CompareByAuthor(Map<Long,Book> books) {
		this.books = books;		
	}
	
	/**
	 * @param Map of books to sort <Long,Book>
	 * 
	 * This method is simple.
	 * 1 - Lets take our param of books and extract all the books into a collection.
	 * 2 - Then we'll use the Collections.sort(List<Book>) to sort it using Book's compartor method.
	 *     We'll need to cast it though.
	 * 
	 * 3 - Then we'll go through that sorted collection; and for each book object, populate a new Map.
	 * 4 - The newly populated Map is then returned.
	 * 
	 * @return will be a sorted, in ascending, Map<Long,Book> of book's according to their author(s).
	 */
	public Map<Long,Book> sortByAuthor(boolean descending){
		
		Collection<Book> books_to_get = books.values();
		books = null; //Delete useless memory.
		List<Book> books_to_sort = new LinkedList<Book>();
		
		for(Book book : books_to_get) {
			books_to_sort.add(book);
		}
		
		Collections.sort(books_to_sort);
		
		//Only switch the List if it is to be sorted descending.
		if(descending == true){
			Collections.reverse(books_to_sort);
		}
		
		Map<Long,Book> return_map = new LinkedHashMap<Long,Book>();
		
		for(Book book_data : books_to_sort) {
			return_map.put(book_data.getId(), book_data);
		}
				
		return return_map;
	}
}
//		
//		
//		Object[][] converted_map = new Object[books.size()][books.size()];
//		Set entries = books.entrySet();
//		Iterator entriesIterator = entries.iterator();
//
//		int i = 0;
//		while(entriesIterator.hasNext()){
//
//			Iterating through or HashMap; or in this case our set we created from the HasMap.
//		    Map.Entry mapping = (Map.Entry) entriesIterator.next();
//
//		    converted_map[i][0] = mapping.getKey();
//	       converted_map[i][1] = mapping.getValue();
//
//		    i++;
//		}
//		
//		Our 2d array is now filled; lets sort it.
//		
//		
//		
//		
//		return books;
//	}
//
//	 @SuppressWarnings("unchecked")
//	private static Map sortByValues(Map map ) { 
//	       List list = new LinkedList(map.entrySet());
//	       // Defined Custom Comparator here
//	       Collections.sort(list, new Comparator() { 
//	    	   public int compare(Object book1, Object book2) {
//	    		   return ((Comparable) ((Map.Entry) (book1)).getValue()).compareTo(((Map.Entry) (book2)).getValue());
//	           }
//	       });
//	       return map;
//	 }
//}
//	
//	public Object[][] sorter_for_2d_array(Object[][] array_2d){
//		java.util.Arrays.sort(array_2d, new java.util.Comparator<Object[]>() {
//		    public int compare(Object[] a, Object[] b) {
//		        return Object.compare(a[0], b[0]);
//		    }
//		});
//		
//		return array_2d;
//	}
		


