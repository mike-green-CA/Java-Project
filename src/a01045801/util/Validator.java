/**
 * Solution for "Validator.java".   
 * 
 * Author: Michael Green, A01045801
 * Created: 2020, June 1st 
 */

package a01045801.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import a01045801.book.ApplicationException;

/**
 * Validate data.
 * 
 * @author Sam Cirka, A00123456
 *
 */
public class Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String YYYYMMDD_PATTERN = "(20\\d{2})(\\d{2})(\\d{2})"; // valid for years 2000-2099
	private static final Logger LOG = LogManager.getLogger();
	 
	private Validator() {
	}

	/**
	 * Validate an email string.
	 * 
	 * @param email
	 *            the email string.
	 * @return true if the email address is valid, false otherwise.
	 */
	public static String getValidEmail(String emailAddress) throws ApplicationException {
		if (!emailAddress.matches(EMAIL_PATTERN)) {
			LOG.error("An invalid email failed validation! Email = [ " + emailAddress + " ].");
			throw new ApplicationException(String.format("Invalid email: %s", emailAddress));
		}

		return emailAddress;
	}

	/**
	 * Validate a dateString in the format of yyyymmdd. Note this method only checks that the year is in the range from 2000 to 2099 and the month and
	 * day are digits. It does not do proper date validation.
	 * 
	 * @param yyyymmdd
	 *            the date string
	 * @return true if the date is somewhat valid, false otherwise.
	 */
	public static LocalDate getValidJoinedDate(String yyyymmdd) throws ApplicationException {
		LocalDate localDate = null;
		if (!yyyymmdd.matches(YYYYMMDD_PATTERN)) {
			LOG.error("An invalid joinDate failed validation! joinDate = [ " + yyyymmdd + " ].");
			throw new ApplicationException(String.format("Invalid joined date: %s", yyyymmdd));
		}
		
		try {
			localDate = LocalDate.parse(yyyymmdd, DateTimeFormatter.ofPattern("yyyyMMdd"));
		} catch (Exception e) {
			LOG.error("An invalid joinDate failed validation! joinDate = [ " + yyyymmdd + " ].");
			throw new ApplicationException(String.format("Invalid joined date: %s", yyyymmdd));
		}

		return localDate;
	}

}
