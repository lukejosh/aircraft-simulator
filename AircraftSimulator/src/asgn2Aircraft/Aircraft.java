/**
 * 
 * This file is part of the AircraftSimulator Project, written as 
 * part of the assessment for CAB302, semester 1, 2016. 
 * 
 */
package asgn2Aircraft;


import java.util.ArrayList;
import java.util.List;

import asgn2Passengers.Business;
import asgn2Passengers.Economy;
import asgn2Passengers.First;
import asgn2Passengers.Passenger;
import asgn2Passengers.PassengerException;
import asgn2Passengers.Premium;
import asgn2Simulators.Log;

/**
 * The <code>Aircraft</code> class provides facilities for modelling a commercial jet 
 * aircraft with multiple travel classes. New aircraft types are created by explicitly 
 * extending this class and providing the necessary configuration information. 
 * 
 * In particular, <code>Aircraft</code> maintains a collection of currently booked passengers, 
 * those with a Confirmed seat on the flight. Queueing and Refused bookings are handled by the 
 * main {@link asgn2Simulators.Simulator} class. 
 *   
 * The class maintains a variety of constraints on passengers, bookings and movement 
 * between travel classes, and relies heavily on the asgn2Passengers hierarchy. Reports are 
 * also provided for logging and graphical display. 
 * 
 * @author hogan
 *
 */
public abstract class Aircraft {

	protected int firstCapacity;
	protected int businessCapacity;
	protected int premiumCapacity;
	protected int economyCapacity;
	protected int capacity;
		
	protected int numFirst;
	protected int numBusiness;
	protected int numPremium;
	protected int numEconomy; 

	protected String flightCode;
	protected String type; 
	protected int departureTime; 
	protected String status;
	protected List<Passenger> seats;

	/**
	 * Constructor sets flight info and the basic size parameters. 
	 * 
	 * @param flightCode <code>String</code> containing flight ID 
	 * @param departureTime <code>int</code> scheduled departure time
	 * @param first <code>int</code> capacity of First Class 
	 * @param business <code>int</code> capacity of Business Class 
	 * @param premium <code>int</code> capacity of Premium Economy Class 
	 * @param economy <code>int</code> capacity of Economy Class 
	 * @throws AircraftException if isNull(flightCode) OR (departureTime <=0) OR ({first,business,premium,economy} <0)
	 */
	public Aircraft(String flightCode,int departureTime, int first, int business, int premium, int economy) throws AircraftException {
		//Check input validity
		if(flightCode == null){
			throw new AircraftException("flightCode cannot be null");
		}
		
		if(departureTime <= 0){
			throw new AircraftException("Departure time cannot be negative");
		}
		
		if(first < 0 || business < 0 || premium < 0 || economy < 0){
			throw new AircraftException("Capacity of passengers cannot be negative");
		}
		
		this.flightCode = flightCode;
		this.departureTime = departureTime;
		
		//Set capacities
		this.firstCapacity = first;
		this.businessCapacity = business;
		this.premiumCapacity = premium;
		this.economyCapacity = economy;
		this.capacity = first + business + premium + economy;

		//Leave status for child class to implement
		this.status = "";
		
		//Set confirmed passengers to 0
		this.numFirst = 0;
		this.numBusiness = 0;
		this.numPremium = 0;
		this.numEconomy = 0;
		
		this.seats = new ArrayList<Passenger>();
	}
	
	/**
	 * Method to remove passenger from the aircraft - passenger must have a confirmed 
	 * seat prior to entry to this method.   
	 *
	 * @param p <code>Passenger</code> to be removed from the aircraft 
	 * @param cancellationTime <code>int</code> time operation performed 
	 * @throws PassengerException if <code>Passenger</code> is not Confirmed OR cancellationTime 
	 * is invalid. See {@link asgn2Passengers.Passenger#cancelSeat(int)}
	 * @throws AircraftException if <code>Passenger</code> is not recorded in aircraft seating 
	 */
	public void cancelBooking(Passenger p,int cancellationTime) throws PassengerException, AircraftException {
		if (!this.hasPassenger(p)){ //Ensure passenger is booked on the flight
			throw new AircraftException("Passenger is not currently booked on the flight");
		}
		
		p.cancelSeat(cancellationTime); //Set the passenger to be cancelled
		this.status += Log.setPassengerMsg(p,"C","N");
		this.seats.remove(p);
		
		this.decrementPassengerCount(this.getPassengerFareType(p));
		
	}

	/**
	 * Method to add a Passenger to the aircraft seating. 
	 * Precondition is a test that a seat is available in the required fare class
	 * 
	 * @param p <code>Passenger</code> to be added to the aircraft 
	 * @param confirmationTime <code>int</code> time operation performed 
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * OR confirmationTime OR departureTime is invalid. See {@link asgn2Passengers.Passenger#confirmSeat(int, int)}
	 * @throws AircraftException if no seats available in <code>Passenger</code> fare class. 
	 */
	public void confirmBooking(Passenger p, int confirmationTime) throws AircraftException, PassengerException {
		if(!this.seatsAvailable(p)){ //Ensure there is a free seat
			throw new AircraftException(this.noSeatsAvailableMsg(p));
		}
		
		p.confirmSeat(confirmationTime, this.departureTime); //Set the passenger to confirmed
		this.status += Log.setPassengerMsg(p,"N/Q","C");
		this.seats.add(p);
		this.incrementPassengerCount(this.getPassengerFareType(p));

	}
	
	/**
	 * State dump intended for use in logging the final state of the aircraft. (Supplied) 
	 * 
	 * @return <code>String</code> containing dump of final aircraft state 
	 */
	public String finalState() {
		String str = aircraftIDString() + " Pass: " + this.seats.size() + "\n";
		for (Passenger p : this.seats) {
			str += p.toString() + "\n";
		}
		return str + "\n";
	}
	
	/**
	 * Simple status showing whether aircraft is empty
	 * 
	 * @return <code>boolean</code> true if aircraft empty; false otherwise 
	 */
	public boolean flightEmpty() {
		if (this.getNumPassengers() == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Simple status showing whether aircraft is full
	 * 
	 * @return <code>boolean</code> true if aircraft full; false otherwise 
	 */
	public boolean flightFull() {
		if (this.capacity == this.getNumPassengers()){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Method to finalise the aircraft seating on departure. 
	 * Effect is to change the state of each passenger to Flown. 
	 * departureTime parameter allows for rescheduling 
	 * 
	 * @param departureTime <code>int</code> actual departureTime from simulation  
	 * @throws PassengerException if <code>Passenger</code> is in incorrect state 
	 * See {@link asgn2Passengers.Passenger#flyPassenger(int)}. 
	 */
	public void flyPassengers(int departureTime) throws PassengerException { 
		for(Passenger p: this.seats){
			p.flyPassenger(departureTime);
		}
	}
	
	/**
	 * Method to return an {@link asgn2Aircraft.Bookings} object containing the Confirmed 
	 * booking status for this aircraft. 
	 * 
	 * @return <code>Bookings</code> object containing the status.  
	 */
	public Bookings getBookings() {
		int freeCapacity = this.capacity - this.getNumPassengers(); //Get number of empty seats on the plane
		return new Bookings(this.numFirst, this.numBusiness, this.numPremium, this.numEconomy, this.getNumPassengers(), freeCapacity);
	}
	
	/**
	 * Simple getter for number of confirmed Business Class passengers
	 * 
	 * @return <code>int</code> number of Business Class passengers 
	 */
	public int getNumBusiness() {
		return this.numBusiness;
	}
	
	
	/**
	 * Simple getter for number of confirmed Economy passengers
	 * 
	 * @return <code>int</code> number of Economy Class passengers 
	 */
	public int getNumEconomy() {
		return this.numEconomy;
	}

	/**
	 * Simple getter for number of confirmed First Class passengers
	 * 
	 * @return <code>int</code> number of First Class passengers 
	 */
	public int getNumFirst() {
		return this.numFirst;
	}

	/**
	 * Simple getter for the total number of confirmed passengers 
	 * 
	 * @return <code>int</code> number of Confirmed passengers 
	 */
	public int getNumPassengers() {
		int totalPassengers = this.numEconomy + this.numPremium + this.numBusiness + this.numFirst;
		return totalPassengers;
	}
	
	/**
	 * Simple getter for number of confirmed Premium Economy passengers
	 * 
	 * @return <code>int</code> number of Premium Economy Class passengers
	 */
	public int getNumPremium() {
		return this.numPremium;
	}
	
	/**
	 * Method to return an {@link java.util.List} object containing a copy of 
	 * the list of passengers on this aircraft. 
	 * 
	 * @return <code>List<Passenger></code> object containing the passengers.  
	 */
	public List<Passenger> getPassengers() {
		return new ArrayList<Passenger>(this.seats);
	}
	
	/**
	 * Method used to provide the current status of the aircraft for logging. (Supplied) 
	 * Uses private status <code>String</code>, set whenever a transition occurs. 
	 *  
	 * @return <code>String</code> containing current aircraft state 
	 */
	public String getStatus(int time) {
		String str = time +"::"
		+ this.seats.size() + "::"
		+ "F:" + this.numFirst + "::J:" + this.numBusiness 
		+ "::P:" + this.numPremium + "::Y:" + this.numEconomy; 
		str += this.status;
		this.status="";
		return str+"\n";
	}
	
	/**
	 * Simple boolean to check whether a passenger is included on the aircraft 
	 * 
	 * @param p <code>Passenger</code> whose presence we are checking
	 * @return <code>boolean</code> true if isConfirmed(p); false otherwise 
	 */
	public boolean hasPassenger(Passenger p) {
		return this.seats.contains(p);
	}
	

	/**
	 * State dump intended for logging the aircraft parameters (Supplied) 
	 * 
	 * @return <code>String</code> containing dump of initial aircraft parameters 
	 */ 
	public String initialState() {
		return aircraftIDString() + " Capacity: " + this.capacity 
				+ " [F: " 
				+ this.firstCapacity + " J: " + this.businessCapacity 
				+ " P: " + this.premiumCapacity + " Y: " + this.economyCapacity
				+ "]";
	}
	
	/**
	 * Given a Passenger, method determines whether there are seats available in that 
	 * fare class. 
	 *   
	 * @param p <code>Passenger</code> to be Confirmed
	 * @return <code>boolean</code> true if seats in Class(p); false otherwise
	 */
	public boolean seatsAvailable(Passenger p) {		
		int numSeats = 0;
		
		switch(this.getPassengerFareType(p)){
		case "F":
			numSeats = this.firstCapacity - this.numFirst;
			break;
		case "J":
			numSeats = this.businessCapacity - this.numBusiness;
			break;
		case "P":
			numSeats = this.premiumCapacity - this.numPremium;
			break;
		case "Y":
			numSeats = this.economyCapacity - this.numEconomy;
			break;
		}
		
		if(numSeats > 0){
			return true;
		}
		else{
			return false;
		}
	}

	/* 
	 * (non-Javadoc) (Supplied) 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return aircraftIDString() + " Count: " + this.seats.size() 
				+ " [F: " + numFirst
				+ " J: " + numBusiness 
				+ " P: " + numPremium 
				+ " Y: " + numEconomy 
			    + "]";
	}


	/**
	 * Method to upgrade Passengers to try to fill the aircraft seating. 
	 * Called at departureTime. Works through the aircraft fare classes in 
	 * descending order of status. No upgrades are possible from First, so 
	 * we consider Business passengers (upgrading if there is space in First), 
	 * then Premium, upgrading to fill spaces already available and those created 
	 * by upgrades to First), and then finally, we do the same for Economy, upgrading 
	 * where possible to Premium. 
	 */
	
	public void upgradeBookings(){
		
		ArrayList<Passenger> businessPassengers = new ArrayList<Passenger>();
		ArrayList<Passenger> premiumPassengers = new ArrayList<Passenger>();
		ArrayList<Passenger> economyPassengers = new ArrayList<Passenger>();
		
		for(Passenger p: this.seats){
			switch(this.getPassengerFareType(p)){
			case "J":
				businessPassengers.add(p);
				break;
			case "P":
				premiumPassengers.add(p);
				break;
			case "Y":
				economyPassengers.add(p);
				break;
			}
		}
		
		if(!this.typeIsFull("F")){ //First class cabin is not fill
			for(Passenger p: businessPassengers){
				try {
					this.upgradePassenger(p, this.departureTime);
				} catch (PassengerException | AircraftException e) {
					e.printStackTrace();
				}
				if(this.typeIsFull("F")){ //Stop searching when first class seats are filled
					break;
					
				}
			}
		}

		if (!this.typeIsFull("J")){ //Business class cabin is not filled
			for(Passenger p: premiumPassengers){
				try {
					this.upgradePassenger(p, this.departureTime);
				} catch (PassengerException | AircraftException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(this.typeIsFull("J")){
					break;
				}
			}
		}
		
		if (!this.typeIsFull("P")){ //Premium cabin is not filled
			for(Passenger p: economyPassengers){
				try {
					this.upgradePassenger(p, this.departureTime);
				} catch (PassengerException | AircraftException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(this.typeIsFull("P")){
					break;
				}
			}
		}
	}

	/**
	 * Simple String method for the Aircraft ID 
	 * 
	 * @return <code>String</code> containing the Aircraft ID 
	 */
	private String aircraftIDString() {
		return this.type + ":" + this.flightCode + ":" + this.departureTime;
	}


	//Various private helper methods to check arguments and throw exceptions, to increment 
	//or decrement counts based on the class of the Passenger, and to get the number of seats 
	//available in a particular class


	//Used in the exception thrown when we can't confirm a passenger 
	/** 
	 * Helper method with error messages for failed bookings
	 * @param p Passenger seeking a confirmed seat
	 * @return msg string failure reason 
	 */
	private String noSeatsAvailableMsg(Passenger p) {
		String msg = "";
		return msg + p.noSeatsMsg(); 
	}
	
	/** 
	 * Helper method to get a passengers type
	 * @param p Passenger
	 * @return type string type of passenger 
	 */
	private String getPassengerFareType(Passenger p){
		String type = null;
		if(p instanceof Economy){
			type = "Y";
		}
		else if(p instanceof Business){
			type = "J";
		}
		else if(p instanceof Premium){
			type = "P";
		}
		else if(p instanceof First){
			type = "F";
		}
		return type;
	}
	
	/** 
	 * Helper method to increment confirmed passenger count
	 * @param type string the type of passenger to increment count for
	 */
	private void incrementPassengerCount(String type){
		switch(type){
		case "F":
			this.numFirst++;
			break;
		case "J":
			this.numBusiness++;
			break;
		case "P":
			this.numPremium++;
			break;
		case "Y":
			this.numEconomy++;
			break;
		}
	}
	
	/** 
	 * Helper method to decrement confirmed passenger count
	 * @param type string the type of passenger to decrement count for
	 */
	private void decrementPassengerCount(String type){
		switch(type){
		case "F":
			this.numFirst--;
			break;
		case "J":
			this.numBusiness--;
			break;
		case "P":
			this.numPremium--;
			break;
		case "Y":
			this.numEconomy--;
			break;
		}
	}
	
	/** 
	 * Helper method to upgrade a passenger to the next highest tier
	 * @param p Passenger the passenger to be upgraded
	 * @throws AircraftException 
	 * @throws PassengerException 
	 */
	private void upgradePassenger(Passenger p, int upgradeTime) throws PassengerException, AircraftException{
		this.cancelBooking(p, upgradeTime);
		Passenger new_passenger = p.upgrade();
		this.confirmBooking(new_passenger, upgradeTime);
		
	}
	
	/** 
	 * Helper method to determine if a fare type is full
	 * @param type string fare type to check
	 * @return boolean result true if fare type is full, false otherwise
	 */
	private boolean typeIsFull(String type){
		boolean result = true;
		
		switch(type){
			case "F": return this.firstCapacity == this.numFirst;
			case "J": return this.businessCapacity == this.numBusiness;
			case "P": return this.premiumCapacity == this.numPremium;
			case "Y": return this.economyCapacity == this.numEconomy;
		}
		return result;
	}	
}
