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
public class PremiumTests {
	
	/**
	 * Test method for {@link asgn2Passengers.Premium#Premium(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConstructorGetID() throws PassengerException {
		String pattern = "P:[0-9]+";
		int bookingTime = 1;
		int departureTime = 3;
		
		Premium passenger = new Premium(bookingTime,departureTime);
		
		String id = passenger.getPassID();
		
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(id);
		
		assertTrue(m.matches());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Premium#Premium(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConstructorGetTimes() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		
		Premium passenger = new Premium(bookingTime,departureTime);
		
		assertEquals(bookingTime,passenger.getBookingTime());
		assertEquals(departureTime,passenger.getDepartureTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Premium#Premium(int, int)}.
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

	/**
	 * Test method for {@link asgn2Passengers.Premium#noSeatsMsg()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testNoSeatsMsg() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 2;
		
		Premium passenger = new Premium(bookingTime,departureTime);
		
		assertEquals("No seats available in Premium", passenger.noSeatsMsg());
	}

	/**
	 * Test method for {@link asgn2Passengers.Premium#upgrade()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testUpgrade() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 2;
		
		Premium passenger = new Premium(bookingTime,departureTime);
		
		if( passenger.upgrade() instanceof Business) {
		    assertTrue(true);
		} else {
			fail("Upgraded Premium passenger is of unexpected type");
		}
	}

	/**
	 * Test method for {@link asgn2Passengers.Premium#upgrade()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testUpgradeGetID() throws PassengerException {
		String pattern = "J:[0-9]+";
		int bookingTime = 1;
		int departureTime = 3;
		
		Premium passenger = new Premium(bookingTime,departureTime);
		Passenger upgraded = passenger.upgrade();
		
		String id = upgraded.getPassID();
		
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(id);
		
		assertTrue(m.matches());
	}

}
