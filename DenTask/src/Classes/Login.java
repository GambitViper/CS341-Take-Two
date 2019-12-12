package Classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Class responsible for login functions,
 * hashing and registration logic
 * 
 * @author Zach Baklund, Matt Milos Last Updated: 12/5/2019
 */
public class Login {

	/*
	 * Method 1:
	 * Check User/Pass (After Hash) with databases
	 * 
	 */
	
	public static void main(String[] args) throws SQLException {
		
	}
	
	public static User registerUser(User newUser, String username, String password) {
		
		String createDate = LocalDate.now().toString();
		
		newUser.setUserName(username);
		newUser.setCreateDate(createDate);

		String hash = md5(password, createDate);
		
		newUser.setPassword(hash);
		newUser.setIsDeleted(false);
		
		return newUser;
	}
	
	public static void createUser(String username, String password, String firstName, String lastName, String email, String phoneNumber, int accType) throws SQLException {
		Database dataConnector = new Database();
		dataConnector.connect();
		
		if( findUserByUsername(username) != null) {
			return;
		}
		
		String createDate = LocalDate.now().toString();
		String hash = md5(password, createDate);

		dataConnector.insertUser(username, hash, firstName, lastName, email, phoneNumber, accType);
		dataConnector.disconnect();
		return;
	}
	
	public static void updateUser(String username, String password, String firstName, String lastName, String email, String phoneNumber) {
		Database dataConnector = new Database();
		dataConnector.connect();
		
		User tmp = null;
		try {
			tmp = findUserByUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
		System.out.println(tmp);
		
		tmp.setFirstName(firstName);
		tmp.setLastName(lastName);
		tmp.setEmail(email);
		tmp.setPhoneNum(phoneNumber);
		if(password == null) {
			dataConnector.updateUser(tmp);
		} else {
			tmp.setPassword(md5(password, tmp.getCreateDate()));
			dataConnector.updateUser(tmp);
		}
		
		dataConnector.disconnect();
	}
	
	public static String loginUser(String username, String password) throws SQLException {
		User loginUser = findUserByUsername(username);
		
		if (loginUser == null) return "Could not find a user with " + username + " in our system";

		if(loginUser.getIsDeleted()) return "This user has been removed";
		
		if(!md5(password, loginUser.getCreateDate()).equals(loginUser.getPassword())) {
			//Password doesn't match
			return "Incorrect password";
		}
		
		return "1";
	}
	
	public static int getUserType(String username) {
		Database dataConnector = new Database();
		dataConnector.connect();
		
		User newUser;

		System.out.print(dataConnector.getUser(username));
		newUser = dataConnector.getUser(username);
		
		if(newUser == null) {
			dataConnector.disconnect();
			return -1;
		} else {
			dataConnector.disconnect();
			return newUser.getUserType();
		}
	}
	
	public static User findUserByUsername(String username) throws SQLException {

		Database dataConnector = new Database();
	
		User newUser = null;
		dataConnector.connect();
		System.out.println("1");
		System.out.print(dataConnector.getUser(username));
		System.out.println("2");
		newUser = dataConnector.getUser(username);
		
		if(newUser != null) {
			dataConnector.disconnect();
			return newUser;
		} else 
			dataConnector.disconnect();
			return null;
	}
	
	private static String md5(String input, String salt) {
		String md5 = null;
		input = input + salt;		
		try {
			//Create MessageDigest object for MD5
			MessageDigest digest = MessageDigest.getInstance("MD5");
			
			//Update input string in message digest
			digest.update(input.getBytes(), 0, input.length());
			
			//Converts message digest value to base 16 (hex)
			md5 = new BigInteger(1, digest.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return md5;
	}
}
