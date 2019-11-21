package Classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Zach Baklund
 *
 */
public class AppointmentCalendar {

	private ArrayList<Appointment> appointments = new ArrayList<Appointment>();

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
		testApps.add(new Appointment().setPatient("testPatient1").setEmployee("testEmpl1").setAppType("testApp1")
				.setAppDetail("abcdefg").setDate(LocalDate.of(year, month, 24)).setTime(10));
		testApps.add(new Appointment().setPatient("testPatient1").setEmployee("testEmpl1").setAppType("testApp1")
				.setAppDetail("abcdefg").setDate(LocalDate.of(year, month, 24)).setTime(11));
		testApps.add(new Appointment().setPatient("testPatient1").setEmployee("testEmpl1").setAppType("testApp1")
				.setAppDetail("abcdefg").setDate(LocalDate.of(year, month, 24)).setTime(12));
		testApps.add(new Appointment().setPatient("testPatient1").setEmployee("testEmpl1").setAppType("testApp1")
				.setAppDetail("abcdefg").setDate(LocalDate.of(year, month, 24)).setTime(13));

	}

	public static void fillAppointments(ArrayList<Appointment> apps) {

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
