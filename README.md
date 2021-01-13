# Java-Project

This is a project I made in my Java class COMP 2613.

This program will read in Customer information, book information, and purchase information.

It will match up with purchase ID matches the Customer ID and the corresponding book. The program will list off all the books purchased by all the customers in many different ways.



##
## Full specs below.
##

COMP 2613 Assignment 1
The assignment will build on the labs and reinforce the concepts and features of the java framework we’ve learned in weeks one through five. You’ll be building the core of a bookstore application named ‘Books’. Because we haven’t covered graphical user interfaces, Books will be a commandline-based program – which means we’ll be focusing on data modelling, good design, and java programming techniques. 

At the core of this application are your labs; the assignment is a consolidation and extension of the requirements for the labs.
Requirements

The design of Books must follow good object-oriented principles and practices. 

Your code must compile and run; compile-time warnings are considered errors and must be eliminated from your code by using appropriate annotation tags.
 
The jar file containing your runnable code must be named Books.jar.

All activity must be logged to a text file named books.log using the Apache Log4j 2 library. Typical logged activities would be program startup and shutdown, and program flow; these activities will be logged as INFO messages. Any exception would be logged as ERROR messages. More detailed information, such as data input would likely be logged as DEBUG messages.

Exceptions must be handled such that no stack traces are displayed in the console, but as mentioned above, a message will be logged explaining the cause of the exception (if there is one). Stack traces will be logged to the log file so that programmers maintaining your application can fix the errors by looking at the log information.

Instead of the data being passed as commandline arguments, it will be read from a data files in plaintext format. For the assignment, there will be three data files:

1.	customers.dat (text file similar to what was used in the labs)
2.	books500.csv (comma-separated values text file)
3.	purchases.csv (comma-separated values text file)

The format of the data in these files are:

1.	ID|FIRST_NAME|LAST_NAME|STREET|CITY|POSTAL_CODE|PHONE|EMAIL| JOIN_DATE
2.	book_id,isbn,authors,original_publication_year,original_title,average_rating,ratings_count,image_url 
3.	id,customer_id,book_id,price

Note: the first line of the file describes the contents and is to be ignored when the file is read. The CustomerReader that was used in the labs may be reused to read customers.dat, but the other two CSV files should be read using the Apache Commons CSV library. 

For each of these data files, you will need to have classes to represent the data:

1.	Book
2.	Customer
3.	Purchase

Book and Customer are independent of one another, but customer ties them together with the book ID and customer ID

Books will accept commandline options, full or short, and will be processed using the Apache Commons CLI library. The BookOptions class is provided to assist you in processing the commandline options.

 The options will have the following effect (each option is always preceded by a dash ‘-‘):
Full	Short	Result
help	?	Display help
customers	c	Print the customer report
books	b	Print the books report
purchases	p	Print the purchases report
total	t	When combined with the 'purchases' option, also print the total value of the purchases
by_author	A	Sorts the books report by author in ascending order. This option is ignored if ‘books’ isn’t also specified
by_lastname	L	Sorts the purchases report by customer last name in ascending order. This option is ignored if ‘purchases’ isn’t also specified
by_title	T	Sorts the purchases report by book title in ascending order. This option is ignored if ‘purchases’ isn’t also specified
by_join_date	J	Sorts the customers report by join date in ascending order. This option is ignored if ‘customers’ isn’t also specified
customer_id	C	Filters the purchases report, showing only customers that match the customer id
desc	d	Any sorted report is sorted in descending order. Must be combined with 'by_lastname', 'by_title', or 'by_join_date'

The following tests must pass (note multiple arguments can be specified in ANY order):
1.	If no commandline parameters are specified, all three reports are printed.
2.	If -customers (-c) is specified, the customer report is printed.
3.	If -books (-b) is specified, the books report is printed.
4.	If -purchases (-p) is specified, the customers report is printed.
5.	If -customers -books -purchases (-c -b -p) are specified, all three reports are printed.
6.	If -customers -by_join_date (-c -J) are specified, the customers report is printed and the customers sorted by join date.
7.	If -customers -by_join_date -desc (-c -J -d) are specified, the customers report is printed and the customers sorted by join date in descending order.
8.	If -books -by_author (-b -A) are specified, the books report is printed and the books are sorted by author.
9.	If -purchases -by_lastname -desc (-p  -L -d) are specified, the purchases report and the purchases are sorted by last name in descending order.
10.	If -purchases -by_title (-p -T) are specified, the purchases report is printed and the purchases are sorted by title.
11.	If -purchases -by_title -total (-p -T -t) the purchases report is printed and the purchases are sorted by book title in ascending order (A-Z), and includes the value of the customer's purchases.
12.	 If -purchases -customer_id=XXXX –total -by_title - desc (-p –C=XXXX -t -T -d) are specified, display the purchases report sorted by book title in descending order (Z-A), include the value of the customer's purchases, ex. java -jar Books.jar -p -C=8479 -t -T -d
