package Classes;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Database {

	// private DenTask control;
	private Connection connection;

	public static void main(String args[]) {
		Database db = new Database();
		db.connect();

		db.disconnect();
	}

	public Database() {
		// control = d;
	}

	public void connect() {
		try {
			String url = "jdbc:sqlite:..\\DenTaskDB.db";
			connection = DriverManager.getConnection(url);
			System.out.println("Successfully connected to the database...");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

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

	public User insertUser(String username, String password, String firstName, String lastName, String email,
			String phoneN, String createDate, int userType) {
		User u = null;
		try {
			if (userType == 0) {
				u = new Admin();
			} else if (userType == 1) {
				u = new Dentist();
			} else if (userType == 2) {
				u = new Hygienist();
			} else {
				u = new Patient();
			}

			u.setUserName(username).setPassword(password).setFirstName(firstName).setLastName(lastName).setEmail(email)
					.setPhoneNum(phoneN).setCreateDate(createDate).setIsDeleted(false);

			PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO [User] ([Username], [Password], [UserType], [FirstName], [LastName], [Email], [PhoneNumber], [CreateDate], [DeleteDate]) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, NULL);");
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setInt(3, userType);
			stmt.setString(4, firstName);
			stmt.setString(5, lastName);
			stmt.setString(6, email);
			stmt.setString(7, phoneN);
			stmt.setString(8, createDate);
			stmt.executeUpdate();

			System.out.println("Succesfully created User: " + username);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return u;
	}

	public User updateUser(User u) {
		try {
			PreparedStatement stmt = connection.prepareStatement(
					"UPDATE User Password = ?, FirstName = ?, LastName = ?, Email = ?, PhoneNumber = ? WHERE Username = ?;");
			stmt.setString(1, u.getPassword());
			stmt.setString(2, u.getFirstName());
			stmt.setString(3, u.getLastName());
			stmt.setString(4, u.getEmail());
			stmt.setString(5, u.getPhoneNum());
			stmt.setString(6, u.getUsername());
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 1) {
				System.out.format("User: %s successfully updated\n", u.getUsername());
			} else {
				System.out.format("Could not update %s\n", u.getUsername());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return u;
	}

	public void deleteUser(String username) {
		try {
			String date = LocalDate.now().toString();
			PreparedStatement stmt = connection.prepareStatement("UPDATE User SET DeleteDate = ? WHERE Username = ?");
			stmt.setString(1, date);
			stmt.setString(2, username);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 1) {
				System.out.format("%s was successfully deleted", username);
			} else {
				System.out.format("ERROR: %s could not be deleted", username);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void hardDeleteUser(String username) {
		try {
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM User WHERE Username = ?;");
			stmt.setString(1, username);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 1) {
				System.out.println("Sucessfully deleted User: " + username);
			} else {
				System.out.println("Failed to delete User: " + username);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public User getUser(String username) {
		User u = null;
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE Username = ?");
			stmt.setString(1, username);
			ResultSet r = stmt.executeQuery();

			u = resultSetToUser(r);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return u;
	}
	
	public User getUser(int userType) {
		User u = null;
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE UserType = ?");
			stmt.setInt(1, userType);
			ResultSet r = stmt.executeQuery();

			u = resultSetToUser(r);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return u;
	}

	public LinkedList<User> getAllUsers() {
		LinkedList<User> users = new LinkedList<>();
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User;");
			ResultSet r = stmt.executeQuery();

			while (r.next()) {
				users.add(resultSetToUser(r));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return users;
	}

	/**
	 * Appointment operations
	 */

	public Appointment insertAppointment(String patient, String employee, String type, String detail, String date,
			int time) {
		try {
			Appointment a = new Appointment();
			a.setPatient(patient).setEmployee(employee).setAppType(type).setAppDetail(detail)
					.setDate(LocalDate.parse(date)).setTime(time).setResult("");

			PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO [Appointment] ([PatientID], [EmployeeID], [Date], [AppointmentType], [AppointmentDetails], [Result]) VALUES (?, ?, ?, ?, ?, NULL);");
			stmt.setString(1, patient);
			stmt.setString(2, employee);
			stmt.setString(3, date + " " + time);
			stmt.setString(4, type);
			stmt.setString(5, detail);
			int numAffected = stmt.executeUpdate();

			if (numAffected == 1) {
				System.out.format("Appointment for %s for %s on %s at %d was created\n", patient, employee, date, time);
			} else {
				System.out.format("ERROR: could not create appointment for %s for %s on %s at %d", patient, employee,
						date, time);
			}

			return a;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void deleteAppointment(String patient, String employee, String date, int time) {
		try {
			PreparedStatement stmt = connection.prepareStatement(
					"UPDATE Appointment SET Result = 'Cancelled' WHERE PatientID = ? AND EmployeeID = ? AND Date = ?;");
			stmt.setString(1, patient);
			stmt.setString(2, employee);
			stmt.setString(3, date + " " + time);
			int numAffected = stmt.executeUpdate();

			if (numAffected == 1) {
				System.out.format("Appointment for %s for %s on %s at %d was successfully deleted", patient, employee,
						date, time);
			} else {
				System.out.format("ERROR: could not delete appointment for %s for %s on %s at %d", patient, employee,
						date, time);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void hardDeleteAppointment(String patient, String employee, String date, int time) {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM Appointment WHERE PatientID = ? AND EmployeeID = ? AND Date = ?;");
			stmt.setString(1, patient);
			stmt.setString(2, employee);
			stmt.setString(3, date + " " + time);
			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected == 1) {
				System.out.format("Successfully deleted Appointment [%s, %s, %s %d]", patient, employee, date, time);
			} else {
				System.out.format("Failed to delete Appointment [%s, %s, %s %d]", patient, employee, date, time);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public Appointment getAppointment(String patient, String employee, String date, int time) {
		try {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Appointment WHERE PatientID = ? AND EmployeeID = ? AND Date = ?;");
			stmt.setString(1, patient);
			stmt.setString(2, employee);
			stmt.setString(3, date + " " + time);
			ResultSet r = stmt.executeQuery();

			return resultSetToAppointment(r);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public LinkedList<Appointment> getAppointments(String user) {
		LinkedList<Appointment> appts = new LinkedList<>();
		try {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT * FROM Appointment WHERE PatientID = ? OR EmployeeID = ?;");
			stmt.setString(1, user);
			stmt.setString(2, user);
			ResultSet r = stmt.executeQuery();

			while (r.next()) {
				appts.add(resultSetToAppointment(r));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return appts;
	}

	public LinkedList<Appointment> getAllAppointments() {
		LinkedList<Appointment> appts = new LinkedList<>();
		try {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Appointment;");
			ResultSet r = stmt.executeQuery();

			while (r.next()) {
				appts.add(resultSetToAppointment(r));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		return appts;
	}

	/**
	 * Availability operations
	 */

	public int[] insertDailyAvailability(String username, String dayOfWeek, String startTime, String endTime) {
		int[] hours = {};
		try {
			PreparedStatement stmt = connection.prepareStatement(
					"INSERT INTO [Availability] ([UserID], [DayOfWeek], [StartTime], [EndTime]) VALUES (?, ?, ?, ?);");
			stmt.setString(1, username);
			stmt.setString(2, dayOfWeek);
			stmt.setString(3, startTime);
			stmt.setString(4, endTime);
			int numAffected = stmt.executeUpdate();

			if (numAffected == 1) {
				System.out.format("Successfully inserted %s:%s (%s-%s)\n", username, dayOfWeek, startTime, endTime);
				hours = timesToHours(startTime, endTime);
			} else {
				System.out.format("ERROR: could not insert availability for %s:%s (%s-%s)\n", username, dayOfWeek,
						startTime, endTime);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return hours;
	}

	public HashMap<String, int[]> insertFullAvailability(String username, HashMap<String, int[]> availability) {
		HashMap<String, int[]> avail = new HashMap<>();
		try {
			for (String dayOfWeek : availability.keySet()) {
				String[] times = hoursToTimes(availability.get(dayOfWeek)).split(" ");
				String startTime = times[0];
				String endTime = times[1];

				PreparedStatement stmt = connection.prepareStatement(
						"INSERT INTO [Availability] ([UserID], [DayOfWeek], [StartTime], [EndTime]) VALUES (?, ?, ?, ?);");
				stmt.setString(1, username);
				stmt.setString(2, dayOfWeek);
				stmt.setString(3, startTime);
				stmt.setString(4, endTime);
				int numAffected = stmt.executeUpdate();

				if (numAffected == 1) {
					System.out.format("Successfully updated availability for %s:%s (%s-%s)\n", username, dayOfWeek,
							startTime, endTime);
				} else {
					System.out.format("ERROR: could not update availability for %s:%s\n", username, dayOfWeek);
				}
			}
			avail = getAvailability(username);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return avail;
	}

	public HashMap<String, int[]> updateAvailability(String username, HashMap<String, int[]> availability) {
		HashMap<String, int[]> newAvail = new HashMap<>();
		try {
			for (String dayOfWeek : availability.keySet()) {
				String[] times = hoursToTimes(availability.get(dayOfWeek)).split(" ");
				String startTime = times[0];
				String endTime = times[1];

				PreparedStatement stmt = connection.prepareStatement(
						"UPDATE Availability SET StartTime = ?, EndTime = ? WHERE Username = ? AND DayOfWeek = ?;");
				stmt.setString(1, startTime);
				stmt.setString(2, endTime);
				stmt.setString(3, username);
				stmt.setString(4, dayOfWeek);
				int numAffected = stmt.executeUpdate();

				if (numAffected == 1) {
					System.out.format("Successfully updated availability for %s:%s (%s-%s)\n", username, dayOfWeek,
							startTime, endTime);
				} else {
					System.out.format("ERROR: could not update availability for %s:%s\n", username, dayOfWeek);
				}
			}
			newAvail = getAvailability(username);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return newAvail;
	}

	public HashMap<String, int[]> getAvailability(String username) {
		HashMap<String, int[]> availability = new HashMap<>();
		try {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT DayOfWeek, StartTime, EndTime FROM Availability WHERE Username = ?;");
			stmt.setString(1, username);
			ResultSet r = stmt.executeQuery();
			while (r.next()) {
				availability.put(r.getString("DayOfWeek"),
						timesToHours(r.getString("StartTime"), r.getString("EndTime")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return availability;
	}

	/**
	 * RequestOff operations
	 */
	
	/**
	 * Helper methods
	 */

	private User resultSetToUser(ResultSet r) throws SQLException {
		User u = null;
		int type = r.getInt("UserType");

		if (type == 0) {
			u = new Admin();
		} else if (type == 1) {
			u = new Dentist();
		} else if (type == 2) {
			u = new Hygienist();
		} else {
			u = new Patient();
		}

		u.setUserName(r.getString("Username")).setPassword(r.getString("Password"))
				.setFirstName(r.getString("FirstName")).setLastName(r.getString("LastName"))
				.setEmail(r.getString("Email")).setPhoneNum(r.getString("PhoneNumber"))
				.setCreateDate(r.getString("CreateDate"));
		if (r.getString("DeleteDate") == null) {
			u.setIsDeleted(false);
		} else {
			u.setIsDeleted(true);
		}

		return u;
	}

	private Appointment resultSetToAppointment(ResultSet r) throws SQLException {
		String[] dateTime = r.getString("Date").split(" ");

		Appointment a = new Appointment().setPatient(r.getString("PatientID")).setEmployee(r.getString("EmployeeID"))
				.setAppType(r.getString("AppointmentType")).setAppDetail(r.getString("AppointmentDetails"))
				.setDate(LocalDate.parse(dateTime[0])).setTime(Integer.valueOf(dateTime[1]))
				.setResult(r.getString("Result"));
		return a;
	}

	private int[] timesToHours(String startTime, String endTime) {
		int startIndex = Integer.valueOf(startTime) % 100 - 8;
		int endIndex = Integer.valueOf(endTime) % 100 - 8;

		int[] dailyAvail = new int[9];
		if (startIndex < dailyAvail.length) {
			for (int i = startIndex; i < endIndex && i < dailyAvail.length; i++) {
				dailyAvail[i] = 1;
			}
		}
		return dailyAvail;
	}

	private String hoursToTimes(int[] hours) {
		String startTime = "", endTime = "";
		for (int i = 0; i < hours.length; i++) {
			if (hours[i] == 1) {
				startTime = (i + 8) + "00";
				for (int j = i; j < hours.length; j++) {
					if (hours[j] == 0) {
						endTime = (j - 1 + 8) + "00";
					}
				}
			}
		}
		return startTime + " " + endTime;
	}
}