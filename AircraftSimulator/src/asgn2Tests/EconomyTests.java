/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

/**
 * @author Jason
 *
 */
public class EconomyTests {

	/**
	 * Test method for {@link asgn2Passengers.Economy#noSeatsMsg()}.
	 */
	@Test
	public void testNoSeatsMsg() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 2;
		
		Economy passenger = new Economy(bookingTime,departureTime);
		
		assertEquals("No seats available in Economy", passenger.noSeatsMsg());
	}

	/**
	 * Test method for {@link asgn2Passengers.Economy#upgrade()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testUpgrade() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 2;
		
		Economy passenger = new Economy(bookingTime,departureTime);
		
		if( passenger.upgrade() instanceof Premium) {
		    assertTrue(true);
		} else {
			fail("Upgraded Economy passenger is of unexpected type");
		}
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Economy#upgrade()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testUpgradeGetID() throws PassengerException {
		String pattern = "P:[0-9]+";
		int bookingTime = 1;
		int departureTime = 2;
		
		Economy passenger = new Economy(bookingTime,departureTime);
		Passenger upgraded = passenger.upgrade();
		
		String id = upgraded.getPassID();
		
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(id);
		
		assertTrue(m.matches());
	}

	/**
	 * Test method for {@link asgn2Passengers.Economy#Economy(int, int)}.
	 */
	@Test
	public void testConstructorGetID() throws PassengerException {
		String pattern = "Y:[0-9]+";
		int bookingTime = 1;
		int departureTime = 3;
		
		Economy passenger = new Economy(bookingTime,departureTime);
		
		String id = passenger.getPassID();
		
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(id);
		
		assertTrue(m.matches());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Economy#Economy(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConstructorGetTimes() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		
		Economy passenger = new Economy(bookingTime,departureTime);
		
		assertEquals(bookingTime,passenger.getBookingTime());
		assertEquals(departureTime,passenger.getDepartureTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Economy#Economy(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConstructorGetState() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		
		Economy passenger = new Economy(bookingTime,departureTime);
		
		assertFalse(passenger.isConfirmed());
		assertFalse(passenger.isFlown());
		assertFalse(passenger.isQueued());
		assertFalse(passenger.isRefused());
	}

}
