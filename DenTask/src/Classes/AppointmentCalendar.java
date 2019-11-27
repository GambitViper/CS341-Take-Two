package Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

/**
 * @author Zach Baklund
 *
 */
public class AppointmentCalendar {

	private static HashMap<String, ArrayList<Appointment>> appointments = new HashMap<>();
	
	private static ArrayList<Dentist> employees = new ArrayList<>();
	
	public static HashMap<String, ArrayList<Appointment>> getAppointments() {
		return appointments;
	}
	
	public static ArrayList<Dentist> getEmployees() {
		return employees;
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

		ArrayList<Appointment> testApps = new ArrayList<Appointment>();
		testApps.add(new Appointment().setPatient("testPatient1").setEmployee("dentest").setAppType("testApp1")
				.setAppDetail("abcdefg").setDate(LocalDate.of(year, month, 24)).setTime(10));
		testApps.add(new Appointment().setPatient("testPatient1").setEmployee("dentest").setAppType("testApp1")
				.setAppDetail("abcdefg").setDate(LocalDate.of(year, month, 24)).setTime(11));
		testApps.add(new Appointment().setPatient("testPatient1").setEmployee("dentest").setAppType("testApp1")
				.setAppDetail("abcdefg").setDate(LocalDate.of(year, month, 24)).setTime(12));
		testApps.add(new Appointment().setPatient("testPatient1").setEmployee("dentest").setAppType("testApp1")
				.setAppDetail("abcdefg").setDate(LocalDate.of(year, month, 24)).setTime(13));
		
		fillAppointments(testApps);
		
		Dentist dentest = new Dentist();
		dentest = (Dentist) Login.registerUser(dentest, "dentest", "test1234");
		int[] avail = { 1, 1, 1, 0, 1, 1, 1, 1, 1};
		dentest.setDayAvailability("Monday", avail)
		       .setDayAvailability("Tuesday", avail)
		       .setDayAvailability("Wednesday", avail)
		       .setDayAvailability("Thursday", avail)
		       .setDayAvailability("Friday", avail);
		System.out.println(dentest);
		
		addEmployee(dentest);
		
		Dentist dentest2 = new Dentist();
		dentest2 = (Dentist) Login.registerUser(dentest2, "dentest", "test1234");
		int[] avail2 = { 1, 1, 1, 0, 1, 1, 1, 1, 1};
		dentest.setDayAvailability("Monday", avail2)
		       .setDayAvailability("Tuesday", avail2)
		       .setDayAvailability("Wednesday", avail2)
		       .setDayAvailability("Thursday", avail2)
		       .setDayAvailability("Friday", avail2);
		System.out.println(dentest2);
		
		addEmployee(dentest2);
		
		printAppointments();
		
		
		

	}
	
	public static void addEmployee(Dentist emp) {
		ArrayList<Dentist> newEmps = getEmployees();
		newEmps.add(emp);
		employees = newEmps;
	}

	public static void fillAppointments(ArrayList<Appointment> apps) {
		HashMap<String, ArrayList<Appointment>> newAppointments = getAppointments();
		for(int i = 0; i < apps.size(); i++) {
			Appointment newApp = apps.get(i);
			System.out.println(newApp.getDate().toString() + " " + newApp.getPatient() + " " + newApp.getTime());
			if(newAppointments.containsKey(newApp.getDate().toString())) {
				//Date in calendar already contains appointments
				ArrayList<Appointment> currentApps = newAppointments.get(newApp.getDate().toString());
				currentApps.add(newApp);
				newAppointments.put(newApp.getDate().toString(), currentApps);
			}else {
				//Date in calendar does not already contain appointments
				ArrayList<Appointment> newApps = new ArrayList<>();
				newApps.add(newApp);
				newAppointments.put(newApp.getDate().toString(), newApps);
			}
		}
		appointments = newAppointments;
	}
	
//	public static ArrayList<String> employeeSelectList(String date, int time) {
//		ArrayList<String> selectList = new ArrayList<>();
//		ArrayList<Dentist> employees = getEmployees();
//		for(int i = 0; i < employees.size(); i++) {
//			Dentist employee = employees.get(i);
//			HashMap<String, int[]> avail = employee.getAvailability();
//			for(String day : avail.keySet()) {
//				if(avail.get(day)[time - 8] == 1) {
//					//Available at day time
//				}
//			}
//		}
//	}
	
	private static void printAppointments() {
		StringBuilder printStr = new StringBuilder();
		HashMap<String, ArrayList<Appointment>> apps = getAppointments();
		printStr.append("Appointments: ");
		for(String key : apps.keySet()) {
			printStr.append("\n" + key + apps.get(key));
		}
		System.out.println(printStr.toString());
	}
	
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
