package Classes;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class Database {

	// private DenTask control;
	private Connection connection;

	public static void main(String args[]) {
		Database db = new Database();
		try {
			db.connect();

			LocalDate date = LocalDate.now();
			System.out.println(date.toString());

			Availability a = db.insertAvailability("djohnson", "Monday", "1200", "1600");
			System.out.println(a.toString());
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public Database() {
		// control = d;
	}

	public void connect() throws SQLException {
		String url = "jdbc:sqlite:..\\DenTaskDB.db";

		connection = DriverManager.getConnection(url);

		System.out.println("Succesfully connected to the database...");
	}

	public void disconnect() throws SQLException {
		connection.close();
	}

	public User insertUser(String username, String password, String firstName, String lastName, String email,
			int phoneN, String createDate, int userType) throws SQLException {
		User u = null;

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
		stmt.setInt(7, phoneN);
		stmt.setString(8, createDate);
		stmt.executeUpdate();

		System.out.println("Succesfully created User: " + username);

		return u;
	}

	public User updateUser(String username, String attribute, String newVal) throws SQLException {
		String query;
		if (attribute.equals("PhoneNumber")) {
			query = "UPDATE User SET " + attribute + " = " + newVal + " WHERE Username = '" + username + "';";
		} else {
			query = "UPDATE User SET " + attribute + " = '" + newVal + "' WHERE Username = '" + username + "';";
		}
		System.out.println(query);
		PreparedStatement stmt = connection.prepareStatement(query);

		int rowsAffected = stmt.executeUpdate();

		if (rowsAffected == 1) {
			System.out.format("%s:%s successfully set to %s\n", username, attribute, newVal);
		} else {
			System.out.format("Could not update %s:%s\n", username, attribute);
		}

		return getUser(username);
	}

	public void deleteUser(String username) throws SQLException {
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
	}

	public void hardDeleteUser(String username) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement("DELETE FROM User WHERE Username = ?;");
		stmt.setString(1, username);
		int rowsAffected = stmt.executeUpdate();

		if (rowsAffected == 1) {
			System.out.println("Sucessfully deleted User: " + username);
		} else {
			System.out.println("Failed to delete User: " + username);
		}
	}

	public User getUser(String username) throws SQLException {

		PreparedStatement stmt = connection.prepareStatement("SELECT * FROM User WHERE Username = ?");
		stmt.setString(1, username);
		ResultSet r = stmt.executeQuery();

		return resultSetToUser(r);
	}

	public Appointment insertAppointment(String patient, String employee, String type, String detail, String date,
			int time) throws SQLException {
		Appointment a = new Appointment();
		a.setPatient(patient).setEmployee(employee).setAppType(type).setAppDetail(detail).setDate(LocalDate.parse(date))
				.setTime(time).setResult("");

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
			System.out.format("ERROR: could not create appointment for %s for %s on %s at %d", patient, employee, date,
					time);
		}

		return a;
	}

	public void deleteAppointment(String patient, String employee, String date, int time) throws SQLException {
		PreparedStatement stmt = connection.prepareStatement(
				"UPDATE Appointment SET Result = 'Cancelled' WHERE PatientID = ? AND EmployeeID = ? AND Date = ?;");
		stmt.setString(1, patient);
		stmt.setString(2, employee);
		stmt.setString(3, date + " " + time);
		int numAffected = stmt.executeUpdate();

		if (numAffected == 1) {
			System.out.format("Appointment for %s for %s on %s at %d was successfully deleted", patient, employee, date,
					time);
		} else {
			System.out.format("ERROR: could not delete appointment for %s for %s on %s at %d", patient, employee, date,
					time);
		}
	}

	public void hardDeleteAppointment(String patient, String employee, String date, int time) throws SQLException {
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
	}

	public Appointment getAppointment(String patient, String employee, String date, int time) throws SQLException {
		PreparedStatement stmt = connection
				.prepareStatement("SELECT * FROM Appointment WHERE PatientID = ? AND EmployeeID = ? AND Date = ?;");
		stmt.setString(1, patient);
		stmt.setString(2, employee);
		stmt.setString(3, date + " " + time);
		ResultSet r = stmt.executeQuery();

		return resultSetToAppointment(r);
	}

	public LinkedList<Appointment> getAppointments(String user) throws SQLException {
		LinkedList<Appointment> appts = new LinkedList<>();

		PreparedStatement stmt = connection
				.prepareStatement("SELECT * FROM Appointment WHERE PatientID = ? OR EmployeeID = ?;");
		stmt.setString(1, user);
		stmt.setString(2, user);
		ResultSet r = stmt.executeQuery();

		while (r.next()) {
			appts.add(resultSetToAppointment(r));
		}

		return appts;
	}

	public Availability insertAvailability(String username, String dayOfWeek, String startTime, String endTime)
			throws SQLException {
		Availability a = new Availability(dayOfWeek, startTime, endTime);
		
		PreparedStatement stmt = connection.prepareStatement(
				"INSERT INTO [Availability] ([UserID], [DayOfWeek], [StartTime], [EndTime]) VALUES (?, ?, ?, ?);");
		stmt.setString(1, username);
		stmt.setString(2, dayOfWeek);
		stmt.setString(3, startTime);
		stmt.setString(4, endTime);
		int numAffected = stmt.executeUpdate();
		
		if (numAffected == 1)	{
			System.out.format("Successfully inserted %s:%s (%s-%s)\n", username, dayOfWeek, startTime, endTime);
		}
		else	{
			System.out.format("ERROR: could not insert availability for %s:%s (%s-%s)\n", username, dayOfWeek, startTime, endTime);
		}
		
		return a;
	}

	public Availability updateAvailability(String username, String dayOfWeek, String startTime, String endTime)	throws SQLException	{
		PreparedStatement stmt = connection.prepareStatement("UPDATE Availability SET StartTime = ?, EndTime = ? WHERE Username = ? AND DayOfWeek = ?;");
		stmt.setString(1, startTime);
		stmt.setString(2, endTime);
		stmt.setString(3, username);
		stmt.setString(4, dayOfWeek);
		int numAffected = stmt.executeUpdate();
		
		if (numAffected == 1)	{
			System.out.format("Successfully updated availability for %s:%s (%s-%s)\n", username, dayOfWeek, startTime, endTime);
		}
		else 	{
			System.out.format("ERROR: could not update availability for %s:%s\n", username, dayOfWeek);
		}
		
		return getAvailability(username, dayOfWeek);
	}
	
	public Availability getAvailability(String username, String dayOfWeek)	throws SQLException	{
		PreparedStatement stmt = connection.prepareStatement("SELECT DayOfWeek, StartTime, EndTime FROM Availability WHERE Username = ? AND DayOfWeek = ?;");
		stmt.setString(1, username);
		stmt.setString(2, dayOfWeek);
		return resultSetToAvailability(stmt.executeQuery());
	}
	
	public LinkedList<Availability> getFullAvailability(String username) throws SQLException	{
		LinkedList<Availability> list = new LinkedList<>();
		PreparedStatement stmt = connection.prepareStatement("SELECT DayOfWeek, StartTime, EndTime FROM Availability WHERE Username = ?;");
		stmt.setString(1, username);
		ResultSet r = stmt.executeQuery();
		
		while (r.next()) {
			list.add(resultSetToAvailability(r));
		}
		
		return list;
	}
	
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
				.setEmail(r.getString("Email")).setPhoneNum(r.getInt("PhoneNumber"))
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

	private Availability resultSetToAvailability(ResultSet r) throws SQLException	{
		Availability a = new Availability(r.getString("DayOfWeek"));
		a.setStartTime(r.getString("StartTime")).setEndTime(r.getString("EndTime"));
		
		return a;
	}
}