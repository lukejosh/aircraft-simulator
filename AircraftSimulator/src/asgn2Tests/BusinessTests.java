/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import asgn2Passengers.Business;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * @author Jason
 *
 */
public class BusinessTests {

	/**
	 * Test method for {@link asgn2Passengers.Business#noSeatsMsg()}.
	 */
	@Test
	public void testNoSeatsMsg() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 2;
		
		Business passenger = new Business(bookingTime,departureTime);
		
		assertEquals("No seats available in Business", passenger.noSeatsMsg());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Business#upgrade()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testUpgrade() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 2;
		
		Business passenger = new Business(bookingTime,departureTime);
		
		if( passenger.upgrade() instanceof First) {
		    assertTrue(true);
		} else {
			fail("Upgraded Business passenger is of unexpected type");
		}
	}

	/**
	 * Test method for {@link asgn2Passengers.Business#upgrade()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testUpgradeGetID() throws PassengerException {
		String pattern = "F:[0-9]+";
		int bookingTime = 1;
		int departureTime = 3;
		
		Business passenger = new Business(bookingTime,departureTime);
		Passenger upgraded = passenger.upgrade();
		
		String id = upgraded.getPassID();
		
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(id);
		
		assertTrue(m.matches());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Business#Business(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConstructorGetID() throws PassengerException {
		String pattern = "J:[0-9]+";
		int bookingTime = 1;
		int departureTime = 3;
		
		Business passenger = new Business(bookingTime,departureTime);
		
		String id = passenger.getPassID();
		
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(id);
		
		assertTrue(m.matches());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Business#Business(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConstructorGetTimes() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		
		Business passenger = new Business(bookingTime,departureTime);
		
		assertEquals(bookingTime,passenger.getBookingTime());
		assertEquals(departureTime,passenger.getDepartureTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Business#Business(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConstructorGetState() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		
		Premium passenger = new Premium(bookingTime,departureTime);
		
		assertFalse(passenger.isConfirmed());
		assertFalse(passenger.isFlown());
		assertFalse(passenger.isQueued());
		assertFalse(passenger.isRefused());
	}

}
