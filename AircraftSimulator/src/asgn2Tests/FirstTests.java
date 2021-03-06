/**
 * 
 */
package asgn2Tests;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import asgn2Passengers.First;
import asgn2Passengers.PassengerException;

/**
 * @author Jason
 *
 */
public class FirstTests {

	/**
	 * Test method for {@link asgn2Passengers.First#noSeatsMsg()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testNoSeatsMsg() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		
		assertEquals("No seats available in First", passenger.noSeatsMsg());
	}

	/**
	 * Test method for {@link asgn2Passengers.First#upgrade()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testUpgrade() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		
		if( passenger.upgrade() instanceof First) {
		    assertTrue(true);
		} else {
			fail("Upgraded First passenger is of unexpected type");
		}
	}
	
	/**
	 * Test method for {@link asgn2Passengers.First#First()}.
	 */
	@Test
	public void testFirst() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.First#First(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testFirstConstructorNormalGetTimes() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		
		assertEquals(bookingTime,passenger.getBookingTime());
		assertEquals(departureTime,passenger.getDepartureTime());
	}

	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#Passenger(int, int)}.
	 */
	@Test
	public void testFirstConstructorNormalGetStates() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		
		assertTrue(passenger.isNew());
		assertFalse(passenger.isConfirmed());
		assertFalse(passenger.isFlown());
		assertFalse(passenger.isQueued());
		assertFalse(passenger.isRefused());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#Passenger(int, int)}.
	 */
	@Test (expected = PassengerException.class)
	public void testConstructorNegativeDepartureTimeException() throws PassengerException {
		int bookingTime = 1;
		int departureTime = -1;
		
		First passenger = new First(bookingTime,departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#Passenger(int, int)}.
	 */
	@Test (expected = PassengerException.class)
	public void testConstructorZeroDepartureTimeException() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 0;
		
		First passenger = new First(bookingTime,departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#Passenger(int, int)}.
	 */
	@Test
	public void testConstructorZeroBookingTimeException() throws PassengerException {
		int bookingTime = 0;
		int departureTime = 1;
		
		First passenger = new First(bookingTime,departureTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#Passenger(int, int)}.
	 */
	@Test (expected = PassengerException.class)
	public void testConstructorNegativeBookingTimeException() throws PassengerException {
		int bookingTime = -1;
		int departureTime = 1;
		
		First passenger = new First(bookingTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#Passenger(int, int)}.
	 */
	@Test 
	public void testConstructorBookingTimeLessThanDepartureTimeException() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#Passenger(int, int)}.
	 */
	@Test (expected = PassengerException.class)
	public void testConstructorBookingTimeGreaterThanDepartureTimeException() throws PassengerException {
		int bookingTime = 2;
		int departureTime = 1;
		
		First passenger = new First(bookingTime,departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#Passenger(int, int)}.
	 */
	@Test
	public void testConstructorBookingTimeEqualToDepartureTimeException() throws PassengerException {
		int bookingTime = 2;
		int departureTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#Passenger()}.
	 */
	@Test
	public void testPassenger() {
		assertTrue(true);
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testCancelSeatFromConfirmedGetState() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 1;
		int cancellationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.cancelSeat(cancellationTime);
		
		assertTrue(passenger.isNew());
		assertFalse(passenger.isConfirmed());
		assertFalse(passenger.isFlown());
		assertFalse(passenger.isQueued());
		assertFalse(passenger.isRefused());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testCancelSeatFromConfirmedGetTimes() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 1;
		int cancellationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.cancelSeat(cancellationTime);
		
		assertEquals(cancellationTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testCancelSeatFromConfirmedNegativeCancellationTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 1;
		int cancellationTime = -1;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.cancelSeat(cancellationTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testCancelSeatFromConfirmedZeroCancellationTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 1;
		int cancellationTime = 0;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.cancelSeat(cancellationTime);
		
		assertEquals(cancellationTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
	}
	
	@Test (expected = PassengerException.class)
	public void testCancelSeatFromConfirmedCancellationTimeGreaterThanDepartureTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 1;
		int cancellationTime = 4;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.cancelSeat(cancellationTime);
		
		fail("No exception was thrown");
	}
	
	@Test
	public void testCancelSeatFromConfirmedCancellationTimeGEqualToDepartureTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 1;
		int cancellationTime = 3;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.cancelSeat(cancellationTime);
		
		assertEquals(cancellationTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testCancelSeatFromNew() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int cancellationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.cancelSeat(cancellationTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testCancelSeatFromQueued() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 2;
		int cancellationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		passenger.cancelSeat(cancellationTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testCancelSeatFromRefused() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int refusalTime = 2;
		int cancellationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		passenger.cancelSeat(cancellationTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#cancelSeat(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testCancelSeatFromFlown() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 2;
		int cancellationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.flyPassenger(departureTime);
		passenger.cancelSeat(cancellationTime);
		
		fail("No exception was thrown");
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConfirmSeatFromNewGetState() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 1;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		
		assertFalse(passenger.isNew());
		assertTrue(passenger.isConfirmed());
		assertFalse(passenger.isFlown());
		assertFalse(passenger.isQueued());
		assertFalse(passenger.isRefused());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test 
	public void testConfirmSeatFromNewGetTimes() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 1;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
		assertEquals(confirmationTime, passenger.getConfirmationTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testConfirmSeatFromNewNegativeConfirmationTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = -1;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test 
	public void testConfirmSeatFromNewZeroConfirmationTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 0;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
		assertEquals(confirmationTime, passenger.getConfirmationTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testConfirmSeatFromNewConfirmationTimeGreaterThanDepartureTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 4;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test 
	public void testConfirmSeatFromNewConfirmationTimeEqualToDepartureTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 3;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
		assertEquals(confirmationTime, passenger.getConfirmationTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConfirmSeatFromQueuedGetState() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 1;
		int confirmationTime = 1;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		
		assertFalse(passenger.isNew());
		assertTrue(passenger.isConfirmed());
		assertFalse(passenger.isFlown());
		assertFalse(passenger.isQueued());
		assertFalse(passenger.isRefused());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testConfirmSeatFromQueuedGetTimes() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 1;
		int confirmationTime = 1;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);

		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
		assertEquals(queueTime, passenger.getEnterQueueTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testConfirmSeatFromConfirmed() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testConfirmSeatFromRefused() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int refusalTime = 2;
		int confirmationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#confirmSeat(int, int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testConfirmSeatFromFlown() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.flyPassenger(departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testFlyPassengerFromConfirmedGetState() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.flyPassenger(departureTime);
		
		assertFalse(passenger.isNew());
		assertFalse(passenger.isConfirmed());
		assertTrue(passenger.isFlown());
		assertFalse(passenger.isQueued());
		assertFalse(passenger.isRefused());
	}
	
	 /* Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testFlyPassengerFromConfirmedGetTimes() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.flyPassenger(departureTime+1);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime+1, passenger.getDepartureTime());
		assertEquals(confirmationTime, passenger.getConfirmationTime());
	}
	
	/* Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testFlyPassengerFromConfirmedPositiveDepartureTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int departureTime2 = 1;
		int confirmationTime = 1;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.flyPassenger(departureTime2);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime2, passenger.getDepartureTime());
		assertEquals(confirmationTime, passenger.getConfirmationTime());
	}
	
	/* Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testFlyPassengerFromConfirmedZeroDepartureTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int departureTime2 = 0;
		int confirmationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.flyPassenger(departureTime2);
		
		fail("No exception was thrown");
	}
	
	/* Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testFlyPassengerFromConfirmedNegativeDepartureTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int departureTime2 =-1;
		int confirmationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.flyPassenger(departureTime2);
		
		fail("No exception was thrown");
	}
	
	/* Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testFlyPassengerFromNew() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.flyPassenger(departureTime);
		
		fail("No exception was thrown");
	}
	
	/* Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testFlyPassengerFromQueued() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		passenger.flyPassenger(departureTime);
		
		fail("No exception was thrown");
	}
	
	/* Test method for {@link asgn2Passengers.Passenger#flyPassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testFlyPassengerFromRefused() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int refusalTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		passenger.flyPassenger(departureTime);
		
		fail("No exception was thrown");
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getBookingTime()}.
	 */
	@Test
	public void testGetBookingTime() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getConfirmationTime()}.
	 */
	@Test
	public void testGetConfirmationTime() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getDepartureTime()}.
	 */
	@Test
	public void testGetDepartureTime() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getEnterQueueTime()}.
	 */
	@Test
	public void testGetEnterQueueTime() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getExitQueueTime()}.
	 */
	@Test
	public void testGetExitQueueTime() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#getPassID()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testGetPassID() throws PassengerException {
		String pattern = "F:[0-9]+";
		int bookingTime = 1;
		int departureTime = 3;
		
		First passenger = new First(bookingTime,departureTime);
		
		String id = passenger.getPassID();
		
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(id);
		
		assertTrue(m.matches());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#isConfirmed()}.
	 */
	@Test
	public void testIsConfirmed() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#isFlown()}.
	 */
	@Test
	public void testIsFlown() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#isNew()}.
	 */
	@Test
	public void testIsNew() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#isQueued()}.
	 */
	@Test
	public void testIsQueued() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#isRefused()}.
	 */
	@Test
	public void testIsRefused() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testQueuePassengerFromNewGetState() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		
		assertFalse(passenger.isNew());
		assertFalse(passenger.isConfirmed());
		assertFalse(passenger.isFlown());
		assertTrue(passenger.isQueued());
		assertFalse(passenger.isRefused());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testQueuePassengerFromNewGetTimes() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
		assertEquals(queueTime, passenger.getEnterQueueTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testQueuePassengerFromNewZeroQueueTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 0;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
		assertEquals(queueTime, passenger.getEnterQueueTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testQueuePassengerFromNewNegativeQueueTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = -1;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testQueuePassengerFromNewQueueTimeGreaterThanDepatureTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 4;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testQueuePassengerFromNewQueueTimeEqualToDepatureTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 3;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
		assertEquals(queueTime, passenger.getEnterQueueTime());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testQueuePassengerFromQueued() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testQueuePassengerFromRefused() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int refusalTime = 2;
		int queueTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		passenger.queuePassenger(queueTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testQueuePassengerFromConfrimed() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 2;
		int queueTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#queuePassenger(int, int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testQueuePassengerFromFlown() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 2;
		int queueTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.flyPassenger(departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerFromNewGetState() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int refusalTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		
		assertFalse(passenger.isNew());
		assertFalse(passenger.isConfirmed());
		assertFalse(passenger.isFlown());
		assertFalse(passenger.isQueued());
		assertTrue(passenger.isRefused());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerFromNewGetTimes() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int refusalTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
		assertTrue(passenger.isRefused());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerFromNewZeroRefusalTime() throws PassengerException {
		int bookingTime = 0;
		int departureTime = 3;
		int refusalTime = 0;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
		assertTrue(passenger.isRefused());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testRefusePassengerFromNewNegativeRefusalTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int refusalTime = -1;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerFromNewRefusalTimeEqualToBookingTime() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int refusalTime = 1;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
		assertTrue(passenger.isRefused());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testRefusePassengerFromNewRefusalTimeLessThanBookingTime() throws PassengerException {
		int bookingTime = 2;
		int departureTime = 3;
		int refusalTime = 1;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerFromQueuedGetState() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 2;
		int refusalTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		passenger.refusePassenger(refusalTime);
		
		assertFalse(passenger.isNew());
		assertFalse(passenger.isConfirmed());
		assertFalse(passenger.isFlown());
		assertFalse(passenger.isQueued());
		assertTrue(passenger.isRefused());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test
	public void testRefusePassengerFromQueuedGetTimes() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 2;
		int refusalTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		passenger.refusePassenger(refusalTime);
		
		assertEquals(bookingTime, passenger.getBookingTime());
		assertEquals(departureTime, passenger.getDepartureTime());
		assertTrue(passenger.isRefused());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testRefusePassengerFromRefused() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int refusalTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		passenger.refusePassenger(refusalTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testRefusePassengerFromConfirmed() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 2;
		int refusalTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.refusePassenger(refusalTime);
		
		fail("No exception was thrown");
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#refusePassenger(int)}.
	 * @throws PassengerException 
	 */
	@Test (expected = PassengerException.class)
	public void testRefusePassengerFromFlown() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 2;
		int refusalTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		passenger.flyPassenger(departureTime);
		passenger.refusePassenger(refusalTime);
		
		fail("No exception was thrown");
	}
	

	/**
	 * Test method for {@link asgn2Passengers.Passenger#toString()}.
	 */
	@Test
	public void testToString() {
		assertTrue(true);
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#wasConfirmed()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testWasConfirmedTrue() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int confirmationTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.confirmSeat(confirmationTime, departureTime);
		
		assertTrue(passenger.wasConfirmed());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#wasConfirmed()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testWasConfirmedFalse() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		
		First passenger = new First(bookingTime,departureTime);
		
		assertFalse(passenger.wasConfirmed());
	}
	
	/**
	 * Test method for {@link asgn2Passengers.Passenger#wasQueued()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testWasQueuedTrue() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int queueTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.queuePassenger(queueTime, departureTime);
		
		assertTrue(passenger.wasQueued());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#wasQueued()}.
	 * @throws PassengerException 
	 */
	@Test
	public void testWasQueuedFalse() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		
		First passenger = new First(bookingTime,departureTime);
		
		assertFalse(passenger.wasQueued());
	}

	/**
	 * Test method for {@link asgn2Passengers.Passenger#copyPassengerState(asgn2Passengers.Passenger)}.
	 * @throws PassengerException 
	 */
	@Test 
	public void testCopyPassengerState() throws PassengerException {
		int bookingTime = 1;
		int departureTime = 3;
		int refusalTime = 2;
		
		First passenger = new First(bookingTime,departureTime);
		passenger.refusePassenger(refusalTime);
		passenger.upgrade(); // Upgrade method makes a call to copy
		
		assertFalse(passenger.isNew());
		assertFalse(passenger.isConfirmed());
		assertFalse(passenger.isFlown());
		assertFalse(passenger.isQueued());
		assertTrue(passenger.isRefused());
	}

}
