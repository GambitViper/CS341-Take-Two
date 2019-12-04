package Classes;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * The basic database class that provides methods for interacting with the local
 * database
 * 
 * @author Tom Jensen Last Updated: 12/3/2019 12:24
 */
public class Database {

	/**
	 * Global connection variable that prepares database statements
	 */
	private Connection connection;

	/**
	 * Main function for database testing
	 * 
	 * @param args N/A
	 */
	public static void main(String args[]) {
		Database db = new Database();
		db.connect();

		db.disconnect();
	}

	/**
	 * Method to initialize the connection between this class and the database file
	 * Should be called before calling any other methods in this class
	 */
	public void connect() {
		try {
			// Relative address of the database file
			String url = "jdbc:sqlite:..\\DenTaskDB.db";
			connection = DriverManager.getConnection(url);
			System.out.println("Successfully connected to the database...");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Method to terminate the connection between this class and the database file
	 * Should be called after performing all database functions and exiting the
	 * appliction
	 */
	public void disconnect() {
		try {
			connection.close();
			System.out.println("Successfully disconnected from the database...");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * User operations
	 */

	/**
	 * Method to insert a new user into the database
	 *
	 * @param username   - unique username of the user
	 * @param password   - hashed password (hashing and unhashing is done
	 *                   separately)
	 * @param firstName  - first name of the user
	 * @param lastName   - last name of the user
	 * @param email      - email address of the user
	 * @param phoneN     - phone number
	 * @param createDate - Date of user creation, might omit and automatically use
	 *                   the current date
	 * @param userType   - integer denoting which kind of user [0 - Admin, 1 -
	 *                   Dentist, 2 - Hygienist, 3 - Patient]
	 * @return User object with inputed data to be used by the rest of the system,
	 *         will return null if creation fails
	 */
	public User insertUser(String username, String password, String firstName, String lastName, String email,
			String phoneN, int userType) {
		User u = null;
		try {
			// Use the connection to prepare the query string and insert input values
			PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO [User] ([Username], [Password], [UserType], [FirstName], [LastName], [Email], [PhoneNumber], [CreateDate], [DeleteDate]) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, NULL);");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setInt(3, userType);
			stmt.setString(4, firstName);
			stmt.setString(5, lastName);
			stmt.setString(6, email);
			stmt.setString(7, phoneN);
			stmt.setString(8, LocalDate.now().toString());
			// Execute the query
			stmt.executeUpdate();

			u = getUser(username);
			System.out.format("Succesfully created User: %s\n", username);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Return the user object
		return u;
	}

	/**
	 * Updates a User using the new values in the input user object
	 * 
	 * @param u - User to be updated, should have updated values stored in the
	 *          object before querying database
	 * @return
	 */
	public User updateUser(User u) {
		try {
			// Check to see if the input User's information is different from the
			// information stored in the database
			User uDb = getUser(u.getUsername());
			if (!u.equals(uDb)) {
				// Use the connection to prepare the query string using input User's values
				PreparedStatement stmt = connection.prepareStatement(
						"UPDATE User SET Password = ?, FirstName = ?, LastName = ?, Email = ?, PhoneNumber = ? WHERE Username = ?;");
				stmt.setString(1, u.getPassword());
				stmt.setString(2, u.getFirstName());
				stmt.setString(3, u.getLastName());
				stmt.setString(4, u.getEmail());
				stmt.setString(5, u.getPhoneNum());
				stmt.setString(6, u.getUsername());
				// Execute query and record the number of rows affected
				int rowsAffected = stmt.executeUpdate();

				// The User was updated, at least one value was changed
				if (rowsAffected == 1) {
					System.out.format("User: %s successfully updated\n", u.getUsername());
				}
				// No values were updated, 0 lines affected
				else {
					System.out.format("Could not update %s\n", u.getUsername());
				}
			} else {
				System.out.format("No changes made to User: %s\n", u.getUsername());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Return the input User object
		return u;
	}

	/**
	 * Soft delete for a User in the system
	 * 
	 * @param username - username of the User in the system
	 */
	public void deleteUser(String username) {
		try {
			// Grabs the current date to set the DeleteDate field in the database
			String date = LocalDate.now().toString();

			// Use the connection to prepare the query string using the provided username
			// and the current date
			PreparedStatement stmt = connection.prepareStatement("UPDATE User SET DeleteDate = ? WHERE Username = ?");
			stmt.setString(1, date);
			stmt.setString(2, username);
			// Execute the query
			int rowsAffected = stmt.executeUpdate();

			// The DeleteDate of the User was successfully updated in the database
			if (rowsAffected == 1) {
				System.out.format("%s was successfully deleted\n", username);
			}
			// There were no changes to the database...
			else {
				System.out.format("ERROR: %s could not be deleted\n", username);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Hard delete for a User in the database, only for controlling and testing
	 * purposes. Should not be used when a user is marked as inactive in the system.
	 * Soft delete should be the default operation
	 * 
	 * @param username - username of the User in the system
	 */
	public void hardDeleteUser(String username) {
		try {
			// Use the connection to prepare the query statment using the provided username
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM User WHERE Username = ?;");
			stmt.setString(1, username);
			// Execute the query
			int rowsAffected = stmt.executeUpdate();

			// The User was successfully deleted from the database
			if (rowsAffected == 1) {
				System.out.println("Sucessfully deleted User: " + username);
			}
			// No user was deleted, database is unchanged
			else {
				System.out.println("Failed to delete User: " + username);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Method to retrieve a specific User based on an specified username
	 * 
	 * @param username - username of the User to be retrieved
	 * @return - User object representing the desired User
	 */
	public User getUser(String username) {
		User u = null;
		try {
			// Use the connection to prepare the query using the provided username
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE Username = ?");
			stmt.setString(1, username);
			// Execute the query
			ResultSet r = stmt.executeQuery();

			// Use helper method to translate the result set into a User object
			u = resultSetToUser(r);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Return the User object
		return u;
	}

	/**
	 * Method to retrieve all Users of a specific type stored in the database
	 * 
	 * @param userType - integer value of the desired user type (0: Admin, 1:
	 *                 Dentist, 2: Hygienist, 3: Patient)
	 * @param active   - flag if only active Users are desired (true: only active
	 *                 Users returned, false: all Users in the system are returned)
	 * @return - a LinkedList of Users of the specified type
	 */
	public <T extends User> LinkedList<T> getUser(int userType, boolean active) {
		LinkedList<T> users = new LinkedList<>();
		try {
			// Use the connection to prepare query statement using the specified user type
			PreparedStatement stmt;
			if (active) {
				stmt = connection.prepareStatement("SELECT * FROM User WHERE UserType = ? AND DeleteDate = NULL;");
			} else {
				stmt = connection.prepareStatement("SELECT * FROM User WHERE UserType = ?;");
			}
			stmt.setInt(1, userType);
			// Execute the query
			ResultSet r = stmt.executeQuery();

			// Use the helper method to translate the ResultSet to a User object for every
			// row of the ResultSet
			while (r.next()) {
				// Add the User object to the list of Users
				users.add(resultSetToUser(r));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Returns the list of Users, may be empty if no Users exist of the specified
		// type
		return users;
	}

	/**
	 * Method to retrieve all Users stored in the database
	 * 
	 * @param active - flag if only active Users are desired (true: only active
	 *               Users returned, false: all Users in the system are returned)
	 * @return - a LinkedList of all Users in the system
	 */
	public LinkedList<User> getAllUsers(boolean active) {
		LinkedList<User> users = new LinkedList<>();
		try {
			// Use the connection to prepare the query
			PreparedStatement stmt;
			if (active) {
				stmt = connection.prepareStatement("SELECT * FROM User WHERE DeleteDate = NULL;");
			} else {
				stmt = connection.prepareStatement("SELECT * FROM User;");
			}
			// Execute the query
			ResultSet r = stmt.executeQuery();

			// Use the helper method to translate the ResultSet to a User object for every
			// row of the ResultSet
			while (r.next()) {
				users.add(resultSetToUser(r));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Returns the list of all Users in the system, may be empty if no Users exist
		// in the system
		return users;
	}

	/**
	 * Appointment operations
	 */

	/**
	 * Method to insert an Appointment into the database
	 * 
	 * @param patient  - username of the Patient User
	 * @param employee - username of the Employee User
	 * @param type     - type of Appointment ex: Cleaning, Tooth Extraction, Surgery
	 * @param detail   - any additional details about the Appointment
	 * @param date     - scheduled date of the Appointment
	 * @param time     - scheduled time of day of the Appointment
	 * @return - the Appointment object that was created, will return null if there
	 *         was an SQL error
	 */
	public Appointment insertAppointment(String patient, String employee, String type, String detail, String date,
			int time) {
		Appointment a = null;
		try {
			// Use the connection to prepare the query string using the input parameters
			PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO [Appointment] ([PatientID], [EmployeeID], [Date], [AppointmentType], [AppointmentDetails], [Result]) VALUES (?, ?, ?, ?, ?, NULL);");
			stmt.setString(1, patient);
			stmt.setString(2, employee);
			stmt.setString(3, date + " " + time);
			stmt.setString(4, type);
			stmt.setString(5, detail);
			// Execute the query and record the number of rows affected
			int numAffected = stmt.executeUpdate();

			// The query successfully executed and created a new line in the database
			if (numAffected == 1) {
				a = getAppointment(patient, employee, date, time);
				System.out.format("Appointment for %s with %s on %s at %d was created\n", patient, employee, date,
						time);
			}
			// No changes were made to the database
			else {
				System.out.format("ERROR: could not create appointment for %s for %s on %s at %d", patient, employee,
						date, time);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return a;
	}

	/**
	 * Method to soft delete an Appointment in the systembased on the Patient,
	 * Employee, and the datetime
	 * 
	 * @param patient  - username of the Patient User
	 * @param employee - username of the Employee User
	 * @param date     - scheduled date of the Appointment
	 * @param time     - scheduled time of the Appointment
	 */
	public void deleteAppointment(String patient, String employee, String date, int time) {
		try {
			// Use the connection to prepare the query statement using the input parameters
			PreparedStatement stmt = connection.prepareStatement(
					"UPDATE Appointment SET Result = 'Cancelled' WHERE PatientID = ? AND EmployeeID = ? AND Date = ?;");
			stmt.setString(1, patient);
			stmt.setString(2, employee);
			stmt.setString(3, date + " " + time);
			// Execute the query and record the number of rows affected in the database
			int numAffected = stmt.executeUpdate();

			// Appointment was successfully updated in the database
			if (numAffected == 1) {
				System.out.format("Appointment for %s for %s on %s at %d was successfully deleted\n", patient, employee,
						date, time);
			}
			// No rows affected in the database
			else {
				System.out.format("ERROR: could not delete appointment for %s for %s on %s at %d\n", patient, employee,
						date, time);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Method to hard delete an Appointment in the database, only to be used for
	 * database control and testing Should not be used in the system, use the soft
	 * delete method instead
	 * 
	 * @param patient  - username of the Patient User
	 * @param employee - username of the Employee User
	 * @param date     - scheduled date of the Appointment
	 * @param time     - scheduled time of the Appointment
	 */
	public void hardDeleteAppointment(String patient, String employee, String date, int time) {
		try {
			// Use the connection to prepare the query string using the input parameters
			PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM Appointment WHERE PatientID = ? AND EmployeeID = ? AND Date = ?;");
			stmt.setString(1, patient);
			stmt.setString(2, employee);
			stmt.setString(3, date + " " + time);
			// Execute the query and record the number of rows affected
			int rowsAffected = stmt.executeUpdate();

			// Appointment was successfully updated in the database
			if (rowsAffected == 1) {
				System.out.format("Successfully deleted Appointment [%s, %s, %s %d]", patient, employee, date, time);
			}
			// No changes were made to the database...
			else {
				System.out.format("Failed to delete Appointment [%s, %s, %s %d]", patient, employee, date, time);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Method to retrieve a specific Appointment based on the input parameters
	 * 
	 * @param patient  - username of the Patient User
	 * @param employee - username of the Employee User
	 * @param date     - scheduled date of the Appointment
	 * @param time     - scheduled time of the Appointment
	 * @return - the Appointment object of the requested Appointment
	 */
	public Appointment getAppointment(String patient, String employee, String date, int time) {
		try {
			// Use the connection to prepare the query string using the input parameters
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Appointment WHERE PatientID = ? AND EmployeeID = ? AND Date = ?;");
			stmt.setString(1, patient);
			stmt.setString(2, employee);
			stmt.setString(3, date + " " + time);
			// Execute the query
			ResultSet r = stmt.executeQuery();

			// Use the helper method to translate the ResultSet to an Appointment object
			return resultSetToAppointment(r);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Method to retrieve all Appointments scheduled for a specified User
	 * 
	 * @param username - username of the desired User
	 * @param future   - flag if only future appointments are desired (true: returns
	 *                 only upcomming scheduled Appointments in the future, false:
	 *                 returns all Appointments in the system including
	 *                 past/cancelled Appointments)
	 * @return - a LinkedList of Appointments that are scheduled for the specified
	 *         User
	 */
	public LinkedList<Appointment> getAppointments(String username, boolean future) {
		LinkedList<Appointment> appts = new LinkedList<>();
		try {
			// Use the connection to prepare the query string using the input parameters
			PreparedStatement stmt;
			if (future) {
				stmt = connection.prepareStatement("SELECT * FROM Appointment WHERE (PatientID = ? OR EmployeeID = ?) AND Result IS NULL;");
			} else {
				stmt = connection.prepareStatement(
						"SELECT * FROM Appointment WHERE PatientID = ? OR EmployeeID = ?;");
			}
			stmt.setString(1, username);
			stmt.setString(2, username);

			// Execute the query
			ResultSet r = stmt.executeQuery();

			// Use the helper method to translate row of the ResultSet into an Appointment
			// object
			while (r.next()) {
				// Add the Appointment object to the LinkedList of Appointments
				appts.add(resultSetToAppointment(r));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Returns the list of Appointments, may be empty if the User has no scheduled
		// Appointments
		return appts;
	}

	/**
	 * Method to retrieve all Appointments currently in the database
	 * 
	 * @param future - flag if only future appointments are desired (true: returns
	 *                 only upcomming scheduled Appointments in the future, false:
	 *                 returns all Appointments in the system including
	 *                 past/cancelled Appointments
	 * @return - a LinkedList of all Appointments in the database
	 */
	public LinkedList<Appointment> getAllAppointments(boolean future) {
		LinkedList<Appointment> appts = new LinkedList<>();
		try {
			// Use the connection to prepare the query string
			PreparedStatement stmt;
			if (future) {
				stmt = connection.prepareStatement("SELECT * FROM Appointment WHERE Result IS NULL;");
			} else {
				stmt = connection.prepareStatement("SELECT * FROM Appointment;");
			}
			// Execute the query
			ResultSet r = stmt.executeQuery();

			// Use the helper method to translate each line of the ResultSet into an
			// Appointment object
			while (r.next()) {
				// Add the Appointment object to the return list
				appts.add(resultSetToAppointment(r));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		// Return the Appointment list, may be empty if there are no Appointments in the
		// system
		return appts;
	}

	/**
	 * Availability operations
	 */

	/**
	 * Method to insert the weekly Availability for a specific day of the week for
	 * an Employee
	 * 
	 * @param username  - username of the Employee User
	 * @param dayOfWeek - day of the week
	 * @param startTime - time Employee's shift will start
	 * @param endTime   - time Employee's shift will end
	 * @return - the bitmap representation of the daily Availability
	 */
	public int[] insertDailyAvailability(String username, String dayOfWeek, String startTime, String endTime) {
		int[] hours = new int[9];
		try {
			// Use the connection to prepare the query string using the input parameters
			PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO [Availability] ([UserID], [DayOfWeek], [StartTime], [EndTime]) VALUES (?, ?, ?, ?);");
			stmt.setString(1, username);
			stmt.setString(2, dayOfWeek);
			stmt.setString(3, startTime);
			stmt.setString(4, endTime);
			// Execute the query and record the number of rows affected
			int numAffected = stmt.executeUpdate();

			// Availability successfully added to the database
			if (numAffected == 1) {
				System.out.format("Successfully inserted %s:%s (%s-%s)\n", username, dayOfWeek, startTime, endTime);
				// Use the helper method to translate the start/end times into bitmap format
				hours = timesToHours(startTime, endTime);
			}
			// No rows affected in the database...
			else {
				System.out.format("ERROR: could not insert availability for %s:%s (%s-%s)\n", username, dayOfWeek,
						startTime, endTime);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Return the bitmap representation of the daily availability
		return hours;
	}

	/**
	 * Method to insert the weekly Availability for the entire week for an Employee
	 * 
	 * @param username     - username of the Employee User
	 * @param availability - HashMap relating day of week to its availability bitmap
	 * @return - return the HashMap representation of the User's Availability (Taken
	 *         from a query to ensure it's actually in the database)
	 */
	public HashMap<String, int[]> insertFullAvailability(String username, HashMap<String, int[]> availability) {
		HashMap<String, int[]> avail = new HashMap<>();

		// for each day of the week, insert the daily availability into the database
		for (String dayOfWeek : availability.keySet()) {
			// Translate that day's bitmap into start/end time strings
			String[] times = hoursToTimes(availability.get(dayOfWeek)).split(" ");
			String startTime = times[0];
			String endTime = times[1];

			avail.put(dayOfWeek, insertDailyAvailability(username, dayOfWeek, startTime, endTime));
		}
		// Return the HashMap representation of the Employee's availability
		return avail;
	}

	/**
	 * Method to update an Employee's Availability for a specific day of the week
	 * 
	 * @param username  - username of the Employee
	 * @param dayOfWeek - day of the week
	 * @param startTime - time the Employee's shift starts
	 * @param endTime   - time the Employee's shift ends
	 * @return - returns the bitmap representation of the Employee's shift
	 */
	public int[] updateDailyAvailability(String username, String dayOfWeek, String startTime, String endTime) {
		int[] hours = {};
		try {
			// Use the connection to prepare the query string using the input parameters
			PreparedStatement stmt = connection.prepareStatement(
					"UPDATE Availability SET StartTime = ?, EndTime = ? WHERE UserID = ? AND DayOfWeek = ?;");
			stmt.setString(1, startTime);
			stmt.setString(2, endTime);
			stmt.setString(3, username);
			stmt.setString(4, dayOfWeek);
			// Execute the query and record the number of rows affected
			int numAffected = stmt.executeUpdate();

			// Row was successfully updated in the database
			if (numAffected == 1) {
				System.out.format("Successfully updated availability for %s:%s (%s-%s)\n", username, dayOfWeek,
						startTime, endTime);
			}
			// No rows were updated in the databse...
			else {
				System.out.format("ERROR: could not update availability for %s:%s\n", username, dayOfWeek);
			}
			hours = timesToHours(startTime, endTime);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return hours;
	}

	/**
	 * Method to update the weekly Availability for the specified Employee
	 * 
	 * @param username     - username of the Employee User
	 * @param availability - HashMap representation of the Employee's availability
	 * @return - the updated HashMap representation of the Employee's availability
	 */
	public HashMap<String, int[]> updateFullAvailability(String username, HashMap<String, int[]> availability) {
		HashMap<String, int[]> newAvail = new HashMap<>();

		// for each day of the week, update the database based on the input parameters
		for (String dayOfWeek : availability.keySet()) {
			String[] times = hoursToTimes(availability.get(dayOfWeek)).split(" ");
			String startTime = times[0];
			String endTime = times[1];

			newAvail.put(dayOfWeek, updateDailyAvailability(username, dayOfWeek, startTime, endTime));
		}

		// Return the new HashMap representation of the Employee's weekly availability
		return newAvail;
	}

	/**
	 * Method to retrieve the weekly Availability of the specified Employee
	 * 
	 * @param username - username of the Employee User
	 * @return - the HashMap representation of the weekly Availability
	 */
	public HashMap<String, int[]> getAvailability(String username) {
		HashMap<String, int[]> availability = new HashMap<>();
		try {
			// Use the connection to prepare the query string using the input parameters
			PreparedStatement stmt = connection
					.prepareStatement("SELECT DayOfWeek, StartTime, EndTime FROM Availability WHERE UserID = ?;");
			stmt.setString(1, username);
			// Execute the query
			ResultSet r = stmt.executeQuery();

			// Use the helper method to translate each line of the ResultSet
			while (r.next()) {
				// Add Availabiltiy to HashMap utilizing helper method to translate start/end
				// times into the Availability bitmap
				availability.put(r.getString("DayOfWeek"),
						timesToHours(r.getString("StartTime"), r.getString("EndTime")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		// Return the HashMap generated by the query
		return availability;
	}

	/**
	 * Method to hard remove a daily availability from the database. Should only be
	 * used for control and testing. The system should not use this method, instead
	 * cross reference days the Employee has requested off
	 * 
	 * @param username  - username of the Employee User
	 * @param dayOfWeek - day of the week
	 */
	public void deleteAvailability(String username, String dayOfWeek) {
		try {
			// Use the connection to prepare the query statement using the input parameters
			PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM Availability WHERE UserID = ? AND DayOfWeek = ?;");
			stmt.setString(1, username);
			stmt.setString(2, dayOfWeek);

			// Execute the query and record the number of rows affected
			int rowsAffected = stmt.executeUpdate();

			// The row was successfully removed from the database
			if (rowsAffected == 1) {
				System.out.format("Availability for %s on %s was removed\n", username, dayOfWeek);
			}
			// No rows were updated in the database
			else {
				System.out.format("ERROR: Could not remove availablity for %s on %s\n", username, dayOfWeek);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * RequestOff operations
	 */

	public void insertRequestOff(String username, String date) {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("INSERT INTO [RequestOff] ([UserID], [OffDate]) VALUES (? , ?)");
			stmt.setString(1, username);
			stmt.setString(2, date);

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 1) {
				System.out.format("%s successfully requested off for %s\n", username, date);
			} else {
				System.out.format("ERROR: could not request off %s for %s\n", date, username);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void deleteRequestOff(String username, String date) {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM RequestOff WHERE UserID = ? AND OffDate = ?;");
			stmt.setString(1, username);
			stmt.setString(2, date);

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 1) {
				System.out.format("Successfully removed request off for %s on %s\n", username, date);
			} else {
				System.out.format("ERROR: could not remove request off for %s on %s\n", date, username);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public LinkedList<String> getUserRequests(String username) {
		LinkedList<String> dates = new LinkedList<>();
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT OffDate FROM RequestOff WHERE UserID = ?;");
			stmt.setString(1, username);
			ResultSet r = stmt.executeQuery();

			while (r.next()) {
				dates.add(r.getString("OffDate"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dates;
	}

	public LinkedList<String> getDateRequests(String date) {
		LinkedList<String> employees = new LinkedList<>();
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT UserID FROM RequestOff WHERE OffDate = ?;");
			stmt.setString(1, date);
			ResultSet r = stmt.executeQuery();

			while (r.next()) {
				employees.add(r.getString("UserID"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return employees;
	}

	public HashMap<String, LinkedList<String>> getAllRequests() {
		HashMap<String, LinkedList<String>> requests = new HashMap<>();
		LinkedList<String> dates;
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT UserID FROM RequestOff;");
			ResultSet r = stmt.executeQuery();

			while (r.next()) {
				dates = new LinkedList<>();
				String username = r.getString("UserID");

				PreparedStatement subStmt = connection
						.prepareStatement("SELECT OffDate FROM RequestOff WHERE UserID = ?;");
				subStmt.setString(1, username);
				ResultSet subR = subStmt.executeQuery();

				while (subR.next()) {
					dates.add(subR.getString("OffDate"));
				}

				requests.put(username, dates);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return requests;
	}

	/**
	 * Helper methods
	 */

	/**
	 * Helper method that translates a ResultSet tuple into a User object
	 * 
	 * @param r ResultSet to be parsed
	 * @return - the User object that was generated
	 * @throws SQLException - if there was an error reading the ResultSet
	 */
	@SuppressWarnings("unchecked")
	private <T extends User> T resultSetToUser(ResultSet r) throws SQLException {
		T user = null;
		int type = r.getInt("UserType");

		// Create a different inherited User object based on user type
		if (type == 0) {
			user = (T) new Admin();
		} else if (type == 1) {
			user = (T) new Dentist();
		} else if (type == 2) {
			user = (T) new Hygienist();
		} else {
			user = (T) new Patient();
		}

		// Set user attributes
		user.setUserName(r.getString("Username")).setPassword(r.getString("Password")).setUserType(r.getInt("UserType"))
				.setFirstName(r.getString("FirstName")).setLastName(r.getString("LastName"))
				.setEmail(r.getString("Email")).setPhoneNum(r.getString("PhoneNumber"))
				.setCreateDate(r.getString("CreateDate"));
		if (r.getString("DeleteDate") == null) {
			user.setIsDeleted(false);
		} else {
			user.setIsDeleted(true);
		}

		// Return the User object
		return user;
	}

	/**
	 * Helper method that translates a ResultSet tuple into an Appointment object
	 * 
	 * @param r - ResultSet to be parsed
	 * @return - the Appointment object that was generated
	 * @throws SQLException - if there was an error readin the ResultSet
	 */
	private Appointment resultSetToAppointment(ResultSet r) throws SQLException {
		// Split the datetime field into separate date and time strings
		String[] dateTime = r.getString("Date").split(" ");

		// Set the Appointment attributes
		Appointment a = new Appointment().setPatient(r.getString("PatientID")).setEmployee(r.getString("EmployeeID"))
				.setAppType(r.getString("AppointmentType")).setAppDetail(r.getString("AppointmentDetails"))
				.setDate(LocalDate.parse(dateTime[0])).setTime(Integer.valueOf(dateTime[1]))
				.setResult(r.getString("Result"));
		// Return the Appointment object
		return a;
	}

	/**
	 * Helper method that translates the start/end times into the Availabiltiy
	 * bitmap
	 * 
	 * @param startTime - time the Employee's shift starts
	 * @param endTime   - time the Employee's shift ends
	 * @return - bitmap representation of the Employee's shift
	 */
	private int[] timesToHours(String startTime, String endTime) {
		// Get the integer values of the start/end time strings
		int startIndex = Integer.valueOf(startTime) / 100 - 8;
		int endIndex = Integer.valueOf(endTime) / 100 - 8;

		// Create the bitmap and populate it based on start/end times
		int[] dailyAvail = new int[9];
		if (startIndex < dailyAvail.length) {
			for (int i = startIndex; i < endIndex && i < dailyAvail.length; i++) {
				dailyAvail[i] = 1;
			}
		} else {
			System.out.println("Incorrect start/end times provided...");
			return new int[] {};
		}
		// Return the generated bitmap
		return dailyAvail;
	}

	/**
	 * Helper method that translates the Availbility bitmap into a start/end times
	 * as a string
	 * 
	 * @param hours - bitmap representation of the Employee's shift
	 * @return - a string of the start and end times separated with a space
	 */
	private String hoursToTimes(int[] hours) {
		String startTime = "", endTime = "";
		// Iterate along the bitmap until arriving at the first '1', indicating the
		// start of the shift
		for (int i = 0; i < hours.length; i++) {
			if (hours[i] == 1) {
				startTime = (i + 8) + "00";
				// Once the start of the shift is found, iterate until arriving at the first
				// '0', indicating the end of the shift
				for (int j = i; j < hours.length; j++) {
					if (hours[j] == 0) {
						endTime = (j - 1 + 8) + "00";
					}
				}
			}
		}
		// Return the string of both times separated with a space
		return startTime + " " + endTime;
	}

	/**
	 * Helper method that translates the Availability bitmap to a string
	 * 
	 * @param hours - bitmap representation of the Employee's shift
	 * @return - a string of the format {1 , ... , 1}
	 */
	public String bitmapToString(int[] hours) {
		// Loop through the array and append the value to the return string
		StringBuilder ret = new StringBuilder("{ ");
		for (int i = 0; i < hours.length; i++) {
			ret.append(hours[i] + " ");
		}
		ret.append("}");
		return ret.toString();
	}
}