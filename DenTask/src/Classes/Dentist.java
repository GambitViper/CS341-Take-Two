package Classes;

import java.util.HashMap;

/**
 * Object container for Dentist Usertype -Extends all data attributes from User class
 * 
 * @author Zach Baklund Last Updated: 12/10/2019
 */
public class Dentist extends User {
	
	//Assumes integer array is reference to daily hours (value) for each given day (key)
	// int[] times = { 1, 1, 1, 0, 1, 1, 1, 1, 1 }
	// >>> available (day) = 8-9, 9-10, 10-11, !(11-12), 12-1, 1-2, 2-3, 3-4, 4-5 
	private HashMap<String, int[]> availability = new HashMap<>();
	
	public HashMap<String, int[]> getAvailability() {
		return this.availability;
	}
	
	/**
	 * Method to query a specific day of the Dentist to check for availability
	 * @param day - String of day "Monday", "Tuesday" ...
	 * @param time - queried time
	 * @return - True/False of whether or not Dentist is available at given time parameters
	 */
	public boolean isAvailable(String day, int time) {
		HashMap<String, int[]> avail = getAvailability();
		if(avail.containsKey(day)) {
			return avail.get(day)[time - 8] == 1;
		}
		return false;
	}
	
	/**
	 * Method to change the availability of the dentist in object
	 * @param day - String of day "Monday", "Tuesday" ...
	 * @param times - bit array containing daily availability
	 * @return - The Dentist object for set builder pattern
	 */
	public Dentist setDayAvailability(String day, int[] times) {
		HashMap<String, int[]> newAvailability = this.getAvailability();
		if(newAvailability.containsKey(day)) {
			// Given day availability currently exists
			newAvailability.replace(day, times);
		}else {
			// Day availability not yet populated
			newAvailability.put(day, times);
		}
		
		this.availability = newAvailability;
		return this;
	}
	
	/**
	 * override for the toString method to display Dentist data
	 */
	public String toString() {
		StringBuilder printStr = new StringBuilder();
		printStr.append("User: " + this.getUsername() + "\n");
		printStr.append("Password: " + this.getPassword() + "\n");
		printStr.append("FirstName: " + this.getFirstName() + "\n");
		printStr.append("LastName: " + this.getLastName() + "\n");
		printStr.append("Email: " + this.getEmail() + "\n");
		printStr.append("PhoneNum: " + this.getPhoneNum() + "\n");
		printStr.append("Availability: {" + this.getAvailabilityString() + "\n}\n");
		printStr.append("CreateDate: " + this.getCreateDate() + "\n");
		printStr.append("Active: " + !this.getIsDeleted() + "\n");
		return printStr.toString();
	}

	/**
	 * Finds the availability of the Dentist
	 * @return String displaying the availability data
	 */
	public String getAvailabilityString() {
		StringBuilder printStr = new StringBuilder();
		HashMap<String, int[]> avail = getAvailability();
		for(String key : avail.keySet()) {
			printStr.append("\n\t" + key + convertTimes(avail.get(key)));
		}
		
		return printStr.toString();
	}
	
	/**
	 * Print method to convert internal availability times to readable format
	 * @param times - array of bits for availability information
	 * @return String containing readable format
	 */
	public String convertTimes(int[] times) {
		StringBuilder printStr = new StringBuilder();
		printStr.append(" { ");
		for(int i = 0; i < times.length; i++) {
			if(times[i] == 1) {
				printStr.append((i + 8) + ":00-" + (i + 8 + 1) + ":00, ");
			}
		}
		printStr.append(" }");

		return printStr.toString();
	}
}
