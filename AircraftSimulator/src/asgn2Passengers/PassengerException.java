/**
 * This file forms part of the AircraftSimulator Project
 * Assignment Two - CAB302 2016
 */
package asgn2Passengers;

/**
 * This class represents exceptions associated with the misuse of the Passenger hierarchy. 
 * @author hogan 
 */
@SuppressWarnings("serial") // We're not interested in binary i/o here
public class PassengerException extends Exception {

	/**
	 * Creates a new instance of PassengerException.
	 * 
	 * @param message String holding an informative message about the problem encountered
	 */
	public PassengerException(String message) {
		super("Passenger Exception: " + message);
	}
}
