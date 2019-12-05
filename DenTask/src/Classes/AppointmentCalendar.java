package Classes;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Class responsible for representing the calendar for the Dental Scheduler
 * along with associated methods used for creating appointments and checking availability
 * 
 * @author Zach Baklund Last Updated: 12/5/2019
 */
public class AppointmentCalendar {

	private static HashMap<String, LinkedList<Appointment>> appointments = new HashMap<>();
	
	private static LinkedList<Dentist> dentistsDB = new LinkedList<>();
	private static LinkedList<Hygienist> hygienistsDB = new LinkedList<>();
	
	public static HashMap<String, LinkedList<Appointment>> getAppointments() {
		return appointments;
	}
	
	public static LinkedList<Dentist> getDentists() {
		return dentistsDB;
	}
	
	public static LinkedList<Hygienist> getHygienists() {
		return hygienistsDB;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Creating a calendar
		Calendar calendar = Calendar.getInstance();

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int date = calendar.get(Calendar.DATE);

		// Getting value of all calendar date fields
		System.out.println("The given calendar's year is: " + year);
		System.out.println("The given calendar's month is: " + month);
		System.out.println("The given calendar's day is: " + date);

		int[][] calendarByMonth = fillArrayByMonth(year, month);
		for (int i = 0; i < calendarByMonth.length; i++) {
			System.out.println(Arrays.toString(calendarByMonth[i]));
		}

		fillDentists();
		fillHygienists();
		
		fillAppointments();
		printAppointments();
		
//		LinkedList<User> selectList = employeeSelectList("Friday", "2019-12-06", 11);
//		System.out.println(selectList);
//		
//		String result = makeAppointment("matt", "djohn", "checkup", "checkup database check", "2019-12-06", 11);
//		System.out.println(result);
	}

	/**
	 * Fills the internal representation for the Dentists in the Database
	 */
	public static void fillDentists() {
		Database dataConnector = new Database();
		dataConnector.connect();
		
		LinkedList<Dentist> dentists = dataConnector.getUser(1, false); //Dentist == 1
		
		dentistsDB = dentists;
		
		for(Dentist dentist: dentistsDB) {
			HashMap<String, int[]> avail = dataConnector.getAvailability(dentist.getUsername());
			System.out.println(avail.toString());
			for(String day : avail.keySet()) {
				dentist.setDayAvailability(day, avail.get(day));
			}
		}
		
		System.out.println(dentistsDB);
		
		dataConnector.disconnect();
	}
	
	/**
	 * Fills the internal representation for the Hygienists in the Database
	 */
	public static void fillHygienists() {
		Database dataConnector = new Database();
		dataConnector.connect();
		
		LinkedList<Hygienist> hygienists = dataConnector.getUser(2, false); //Hygienist == 2
		
		hygienistsDB = hygienists;
		
		for(Hygienist hygienist: hygienistsDB) {
			HashMap<String, int[]> avail = dataConnector.getAvailability(hygienist.getUsername());
			System.out.println(avail.toString());
			for(String day : avail.keySet()) {
				hygienist.setDayAvailability(day, avail.get(day));
			}
		}
		
		System.out.println(hygienistsDB);
		
		dataConnector.disconnect();		
	}

	/**
	 * Fills the internal representation of appointments for the AppointmentCalendar class
	 */
	public static void fillAppointments() {
		HashMap<String, LinkedList<Appointment>> newAppointments = getAppointments();
		
		Database dataConnector = new Database();
		dataConnector.connect();
		
		LinkedList<Appointment> appointmentsDB = dataConnector.getAllAppointments(true);
		for(Appointment appt : appointmentsDB) {
			if(newAppointments.containsKey(appt.getDate().toString())) {
				//Date in calendar already contains some existing appointments
				LinkedList<Appointment> currentAppts = newAppointments.get(appt.getDate().toString());
				currentAppts.add(appt);
				newAppointments.put(appt.getDate().toString(), currentAppts);
			}else {
				//Date in calendar does not already contain appointments
				LinkedList<Appointment> newAppts = new LinkedList<>();
				newAppts.add(appt);
				newAppointments.put(appt.getDate().toString(), newAppts);
			}
		}

		appointments = newAppointments;
		dataConnector.disconnect();
	}
	
	/**
	 * Method checks to see if a given employee is not scheduled
	 * 
	 * @param employee - Employee to check
	 * @param date - Date of possible appointment time
	 * @param time - Time of chosen appointment
	 * @return - Boolean whether or not the employee is scheduled
	 */
	public static boolean notScheduled(String employee, String date, int time) {
		HashMap<String, LinkedList<Appointment>> appointments = getAppointments();
		//Check for no appointments list
		if(appointments == null) return false;
		//Check if no appointments are yet scheduled on chosen date
		if(!appointments.containsKey(date)) return true;
		//Verify chosen employee does not have prior appointment
		LinkedList<Appointment> apptsOnDate = appointments.get(date);
		for(Appointment appt : apptsOnDate) {
			if(appt.getEmployee().equals(employee)) {
				if(appt.getTime() == time) {
					//Employee has a scheduled appointment at chosen time
					return false;
				}
			}
		}	
		return true;
	}
	
	/**
	 * Method to retrieve a list of users available at the desired time and date
	 * 
	 * @param dayOfWeek - Mo, Tu, We, Th, Fr
	 * @param date - Date of the appointment
	 * @param time - Time desired for the appointment
	 * @return - a LinkedList of Employees for which the desired patient appointment time would be available
	 */
	public static LinkedList<User> employeeSelectList(String dayOfWeek, String date, int time) {
		LinkedList<User> selectList = new LinkedList<>();
		LinkedList<Dentist> dentists = getDentists();
		LinkedList<Hygienist> hygienists = getHygienists();
		
		//Add dentists to selectList
		for(Dentist dentist : dentists) {
			//Dentist is available during the week at this time
			if(dentist.isAvailable(dayOfWeek, time)) {
				//Check if already scheduled appt at that time
				if(notScheduled(dentist.getUsername(), date, time)) {
					selectList.add(dentist);
				}
			}
		}
		
		//Add hygienists to selectList
		for(Hygienist hygienist : hygienists) {
			//Hygienist is available during the week at this time
			if(hygienist.isAvailable(dayOfWeek, time)) {
				//Check if already scheduled appt at that time
				if(notScheduled(hygienist.getUsername(), date, time)) {
					selectList.add(hygienist);
				}
			}
		}
		
		return selectList;
	}
	
	/**
	 * Method to create an appointment into the appointment table in the database
	 * 
	 * @param patient - patient username to assign to appointment
	 * @param employee - employee username to assign to appointment
	 * @param type - appointment type
	 * @param detail - appointment detail
	 * @param date - appointment date
	 * @param time - appointment time
	 * @return status message whether or not the appointment was created
	 */
	public static String makeAppointment(String patient, String employee, String type, String detail, String date, int time) {
		Database dataConnector = new Database();
		dataConnector.connect();
		Appointment newAppt = dataConnector.insertAppointment(patient, employee, type, detail, date, time);
		dataConnector.disconnect();
		
		if(newAppt == null) return "Error creating appointment";
		return "Successfully created";
	}
	
	/**
	 * Internal print method to see appointments properly
	 */
	private static void printAppointments() {
		StringBuilder printStr = new StringBuilder();
		HashMap<String, LinkedList<Appointment>> apps = getAppointments();
		printStr.append("Appointments: ");
		for(String key : apps.keySet()) {
			printStr.append("\n" + key + apps.get(key));
		}
		System.out.println(printStr.toString());
	}
	
	/**
	 * Method to print internal calendar
	 * 
	 * @param year - Chosen year for calendar
	 * @param month - Chosen month for calendar
	 */
	public static void printCalendar(int year, int month) {
		int dayOfWeek = 1;
		String space = "   ";

		String[] monthName = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		int[] calDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// object to get month
		Calendar cal = new GregorianCalendar(year, month, 1);
		// Following calculates if the current year is leapyear
		if (year % 100 == 0) {
			if (year % 400 == 0) {
				calDays[1] = 29;
			}
		} else if (year % 4 == 0) {
			calDays[1] = 29;
		}
		// Print out top of calendar
		System.out.println("Year: " + year + space + "Month: " + monthName[month] + "\n");
		System.out.println("S   M   T   W   T   F   S\n");
		// Following loop will find out where to print the first day of the month
		// dayCounter represents current day of the month and dayOfWeek represents the
		// day of the
		// week where 1 is Sunday
		for (int dayCounter = 1; dayCounter <= calDays[month]; dayCounter++) {
			if (dayCounter > 9) {
				// This controls the printing of the spaces between the days so it's pretty
				space = "  ";
			}
			// This if will find out print out spaces until the day the first of the
			// month falls on
			if (dayCounter == 1) {
				while (dayOfWeek != cal.get(Calendar.DAY_OF_WEEK)) {
					System.out.print(space + " ");
					dayOfWeek++;
				}
				// Now we have found the right place to print the first day of the month
				System.out.print(dayCounter + space);
				dayOfWeek++;
			} else {
				// This checks to see if we are at the end of the row.
				if (dayOfWeek == 8) {
					// Print a newline and start on the next row and set the day to 2
					// since we just printed the first position
					System.out.print("\n" + dayCounter + space);
					dayOfWeek = 2;
				} else {
					// this prints out the current day number and increments j
					System.out.print(dayCounter + space);
					dayOfWeek++;
				}
			}
		}
		// This puts some space after the calendar is printed.
		System.out.println("\n");

	}

	/**
	 * Fills an array with calendar information
	 * 
	 * @param year - Chosen year for calendar
	 * @param month - Chosen month for calendar
	 * @return - integer array with dates in proper places for the parameters of the calendar
	 */
	public static int[][] fillArrayByMonth(int year, int month) {
		int dayOfWeek = 1;
		int[][] calendarByMonth = new int[5][7];

		int[] calDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		// object to get month
		Calendar cal = new GregorianCalendar(year, month, 1);
		// Following calculates if the current year is leapyear
		if (year % 100 == 0) {
			if (year % 400 == 0) {
				calDays[1] = 29;
			}
		} else if (year % 4 == 0) {
			calDays[1] = 29;
		}
		// Following loop will find out where to print the first day of the month
		// dayCounter represents current day of the month and dayOfWeek represents the
		// day of the
		// week where 1 is Sunday
		int weekIdx = 0;
		int dayIdx = 0;
		for (int dayCounter = 1; dayCounter <= calDays[month]; dayCounter++) {
			// This if will find out print out spaces until the day the first of the
			// month falls on
			if (dayCounter == 1) {
				while (dayOfWeek != cal.get(Calendar.DAY_OF_WEEK)) {
					dayIdx++;
					dayOfWeek++;
				}
				// Now we have found the right place to print the first day of the month
				calendarByMonth[weekIdx][dayIdx] = dayCounter;
				dayIdx++;
				dayOfWeek++;
			} else {
				// This checks to see if we are at the end of the row.
				if (dayOfWeek == 8) {
					// Print a newline and start on the next row and set the day to 2
					// since we just printed the first position
					weekIdx++;
					dayIdx = 0;
					calendarByMonth[weekIdx][dayIdx] = dayCounter;
					dayIdx++;
					dayOfWeek = 2;
				} else {
					// this prints out the current day number and increments j
					calendarByMonth[weekIdx][dayIdx] = dayCounter;
					dayIdx++;
					dayOfWeek++;
				}
			}
		}
		// This puts some space after the calendar is printed.
		return calendarByMonth;
	}
}
