package asgn2Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import asgn2Aircraft.A380;
import asgn2Aircraft.AircraftException;
import asgn2Aircraft.Bookings;
import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;

public class A380Tests {
	private A380 oneEconA380;
	private A380 regularA380;
	private A380 smallA380;
	
	private Economy testPassenger;
	
	@Before
	public void setup() throws AircraftException, PassengerException{
		smallA380 = new A380("FT01", 10, 1, 3, 6, 4);
		oneEconA380 = new A380("FT01", 10, 0, 0, 0, 1);
		regularA380 = new A380("FT01", 10);
		testPassenger = new Economy(0, 10);
		
		smallA380.confirmBooking(new First(1, 10), 2);
		smallA380.confirmBooking(new Business(1,10), 2);
		for(int i = 0; i < 3; i++){
			smallA380.confirmBooking(new Premium(1, 10), 10);
		}
		
		for(int i = 0; i < 4; i++){
			smallA380.confirmBooking(new Economy(1, 10), 10);
		}
		
		assert(true);
	}
	
	@Test(expected=AircraftException.class)
	public void testA380ShortConstructorThrowsExceptionWithNullFlightCode() throws AircraftException {
		A380 test = new A380(null, 2);
	}
	
	@Test
	public void willfail(){
		assert(false);
	}
	
	@Test(expected=AircraftException.class)
	public void testA380ShortConstructorThrowsExceptionWithNegativeDepartureTime() throws AircraftException {
		A380 test = new A380("FL01", -1);
	}
	
	public void testA380ShortConstructorPassesRegularly() throws AircraftException {
		A380 test = new A380("FL01", 1);
	}

	@Test(expected=AircraftException.class)
	public void testA380LongConstructorThrowsExceptionWithNullFlightCode() throws AircraftException {
		A380 test = new A380(null, 10, 1, 1, 1, 1);
	}
	
	@Test(expected=AircraftException.class)
	public void testA380LongConstructorThrowsExceptionWithNegativeDepartureTime() throws AircraftException {
		A380 test = new A380(null, 10, -1, 1, 1, 1);
	}
	
	@Test(expected=AircraftException.class)
	public void testA380LongConstructorThrowsExceptionWithNegativeFirstCapacity() throws AircraftException {
		A380 test = new A380(null, 10, 1, -1, 1, 1);
	}
	
	@Test(expected=AircraftException.class)
	public void testA380LongConstructorThrowsExceptionWithNegativeBusinessCapacity() throws AircraftException {
		A380 test = new A380(null, 10, 1, 1, -1, 1);
	}
	
	@Test(expected=AircraftException.class)
	public void testA380LongConstructorThrowsExceptionWithNegativePremiumCapacity() throws AircraftException {
		A380 test = new A380(null, 10, 1, 1, -1, 1);
	}
	
	@Test(expected=AircraftException.class)
	public void testA380LongConstructorThrowsExceptionWithNegativeEconomyCapacity() throws AircraftException {
		A380 test = new A380(null, 10, 1, 1, 1, -1);
	}

	@Test(expected=AircraftException.class)
	public void testCancelBookingThrowsExceptionWhenPassengerNotBooked() throws PassengerException, AircraftException {
		oneEconA380.cancelBooking(testPassenger, 0);
	}
	
	@Test
	public void testCancelBookingRemovesPassenger() throws AircraftException, PassengerException {
		oneEconA380.confirmBooking(testPassenger, 10);
		oneEconA380.cancelBooking(testPassenger, 1);
		
		assertEquals(oneEconA380.getNumEconomy(), 0);
	}
	
	@Test(expected=PassengerException.class)
	public void testCancelBookingThrowsPassengerExceptionWhenPassengerIsNotConfirmed() throws AircraftException, PassengerException {
		Passenger testPassenger = new Economy(1, 10);
		oneEconA380.confirmBooking(testPassenger, 2);
		testPassenger.cancelSeat(3);
		
		oneEconA380.cancelBooking(testPassenger, 4);
	}

	public void testCancelBookingSetsPassengerState() throws PassengerException, AircraftException {
		oneEconA380.confirmBooking(testPassenger, 11);
		oneEconA380.cancelBooking(testPassenger, 12);
		
		assert(testPassenger.isNew());
	}

	
	@Test
	public void testConfirmBookingAddsPassenger() throws PassengerException, AircraftException {
		oneEconA380.confirmBooking(testPassenger, 0);
		System.out.println(oneEconA380.getNumEconomy());
		assertEquals(oneEconA380.getNumEconomy(), 1);
	}
	
	@Test(expected=AircraftException.class)
	public void testConfirmBookingThrowsExceptionWhenClassIsFull() throws PassengerException, AircraftException {
		Passenger testPassenger1 = new Economy(0, 10);
		
		oneEconA380.confirmBooking(testPassenger, 0);
		oneEconA380.confirmBooking(testPassenger1, 0);

	}
	
	@Test(expected=PassengerException.class)
	public void testConfirmBookingThrowsExceptionWhenPassengerIsConfirmed() throws PassengerException, AircraftException {
		Passenger testPassenger1 = new Economy(0, 10);
		testPassenger1.confirmSeat(1, 2);
		oneEconA380.confirmBooking(testPassenger1, 0);

	}
	
	@Test()
	public void testConfirmBookingSetsPassengerState() throws PassengerException, AircraftException {
		oneEconA380.confirmBooking(testPassenger, 0);
		assertTrue(testPassenger.isConfirmed());

	}

	@Test
	public void testFlightEmptyIsTrueWhenEmpty() {
		assertTrue(oneEconA380.flightEmpty());
	}
	
	@Test
	public void testFlightEmptyIsFalseWhenFull() throws AircraftException, PassengerException {
		oneEconA380.confirmBooking(testPassenger, 1);
		assertFalse(oneEconA380.flightEmpty());
	}
	
	@Test
	public void testFlightEmptyIsTrueWhenNotEmpty() throws AircraftException, PassengerException {
		regularA380.confirmBooking(testPassenger, 1);
		assertTrue(oneEconA380.flightEmpty());
	}

	@Test
	public void testFlightFullTrueWhenFull() throws AircraftException, PassengerException {
		oneEconA380.confirmBooking(testPassenger, 10);
		assertTrue(oneEconA380.flightFull());
	}
	
	@Test
	public void testFlightFullFalseWhenNotFull() throws AircraftException, PassengerException {
		regularA380.confirmBooking(testPassenger, 10);
		assertFalse(oneEconA380.flightFull());
	}
	
	@Test
	public void testFlightFullFalseWhenEmpty() throws AircraftException, PassengerException {
		assertFalse(oneEconA380.flightFull());
	}

	@Test
	public void testFlyPassengersSetsStates() throws AircraftException, PassengerException {
		oneEconA380.confirmBooking(testPassenger, 1);
		oneEconA380.flyPassengers(2);
		
		assert(testPassenger.isFlown());
	}

	@Test
	public void testGetBookingsReturnsCopy() throws AircraftException, PassengerException {
		Bookings testBooking = new Bookings(0, 0, 0, 0, 1, 0);
		oneEconA380.confirmBooking(testPassenger, 2);
		assertNotEquals(testBooking, oneEconA380.getBookings());
	}

	@Test
	public void testGetNumPassengersGetsCorrectSum() {
		assertEquals(smallA380.getNumPassengers(), 9);
	}

	@Test
	public void testGetPassengersReturnsCopy() throws AircraftException, PassengerException {
		oneEconA380.confirmBooking(testPassenger, 2);
		List<Passenger> testCopy = oneEconA380.getPassengers();
		
		testCopy.add(new Economy(1, 1));
		
		assertNotEquals(testCopy, oneEconA380.getPassengers());
	}
	
	@Test
	public void testGetPassengersReturnsCorrectPassengers() throws AircraftException, PassengerException {
		oneEconA380.confirmBooking(testPassenger, 2);
		assertEquals(testPassenger, oneEconA380.getPassengers().get(0));
	}

	@Test
	public void testHasPassengerFindsPassenger() throws PassengerException, AircraftException {
		oneEconA380.confirmBooking(testPassenger, 1);
		assertTrue(oneEconA380.hasPassenger(testPassenger));
	}
	
	@Test
	public void testHasPassengerFailsToFindPassenger() throws AircraftException, PassengerException {
		oneEconA380.confirmBooking(testPassenger, 1);
		assertFalse(oneEconA380.hasPassenger(new Economy(1, 10)));
	}

	@Test
	public void testSeatsAvailableFirst() throws PassengerException {
		assertFalse(smallA380.seatsAvailable(new First(1, 10)));
	}
	
	@Test
	public void testSeatsAvailableBusiness() throws PassengerException {
		assertTrue(smallA380.seatsAvailable(new Business(1, 10)));
	}
	
	@Test
	public void testSeatsAvailablePremium() throws PassengerException {
		assertTrue(smallA380.seatsAvailable(new Premium(1, 10)));
	}
	
	@Test
	public void testSeatsAvailableEconomy() throws PassengerException {
		assertFalse(smallA380.seatsAvailable(new Economy(1, 10)));
	}

	@Test
	public void testUpgradeBookingsBusinessToFirst() throws AircraftException, PassengerException {
		A380 testA380 = new A380("FL01", 10, 1, 1, 0, 0);
		testA380.confirmBooking(new Business(1, 10), 1);
		testA380.upgradeBookings();
		
		assert(!testA380.seatsAvailable(new First(1, 10)) && testA380.seatsAvailable(new Business(1, 10)));
	}
	
	@Test
	public void testUpgradeBookingsPremiumToBusiness() throws AircraftException, PassengerException {
		A380 testA380 = new A380("FL01", 10, 0, 1, 1, 0);
		testA380.confirmBooking(new Premium(1, 10), 1);
		testA380.upgradeBookings();
		
		assert(!testA380.seatsAvailable(new Business(1, 10)) && testA380.seatsAvailable(new Premium(1, 10)));
	}
	
	@Test
	public void testUpgradeBookingsEconomyToPremium() throws AircraftException, PassengerException {
		A380 testA380 = new A380("FL01", 10, 0, 0, 1, 1);
		testA380.confirmBooking(new Economy(1, 10), 1);
		testA380.upgradeBookings();
		
		assert(!testA380.seatsAvailable(new Premium(1, 10)) && testA380.seatsAvailable(new Economy(1, 10)));
	}
	
	@Test
	public void testUpgradeBookingsAllUpgraded() throws AircraftException, PassengerException {
		A380 testA380 = new A380("FL01", 10, 1, 1, 1, 1);
		testA380.confirmBooking(new Business(1, 10), 1);
		testA380.confirmBooking(new Premium(1, 10), 1);
		testA380.confirmBooking(new Economy(1, 10), 1);
		testA380.upgradeBookings();
		
		assert(testA380.seatsAvailable(new First(1, 10)) &&
			   !testA380.seatsAvailable(new Business(1,10)) &&
			   !testA380.seatsAvailable(new Premium(1,10)) &&
			   !testA380.seatsAvailable(new Economy(1,10)));
	}

}
