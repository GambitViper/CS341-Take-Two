package Classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDate;

public class Login {

	/*
	 * Method 1:
	 * Check User/Pass (After Hash) with databases
	 * 
	 */
	
	
	public static void main(String[] args) throws SQLException {
		/*
		String username = "matt";
		String password = "1234";
		
		System.out.println(loginUser("matty", "1234"));
		System.out.println(loginUser("matt", "12345"));
		System.out.println(loginUser("matt", "1234"));
		*/
		
		System.out.println(getUserType("matt"));
		
	}
	
	public static User registerUser(User newUser, String username, String password) {
		
		String createDate = LocalDate.now().toString();
		
		newUser.setUserName(username);
		newUser.setCreateDate(createDate);

		String hash = md5(password, createDate);
		System.out.println("Password: " + password + " >>> " + hash);
		
		newUser.setPassword(hash);
		newUser.setIsDeleted(false);
		
		return newUser;
	}
	
	public static void createUser(String username, String password, String firstName, String lastName, String email, String phoneNumber) throws SQLException {
		Database dataConnector = new Database();
		dataConnector.connect();
		
		if( findUserByUsername(username) != null) {
			System.out.println("Username Exists");
			return;
		}
		

		String createDate = LocalDate.now().toString();
		String hash = md5(password, createDate);
		System.out.println("Password: " + password + " >>> " + hash);

		dataConnector.insertUser(username, hash, firstName, lastName, email, phoneNumber, createDate, 3);
		System.out.println("Matts Test Case");
		return;
	}
	
	public static String loginUser(String username, String password) throws SQLException {
		// TODO replace with database search
		User loginUser = findUserByUsername(username);
		
		if (loginUser == null) return "Could not find a user with " + username + " in our system";
		
		System.out.println("...logging in with " + password);
		System.out.println(">>> " + md5(password, loginUser.getCreateDate()));
		System.out.println("<<< " + loginUser.getPassword());
		if(!md5(password, loginUser.getCreateDate()).equals(loginUser.getPassword())) {
			//Password doesn't match
			
			return "Incorrect password";
		}
		
		if(loginUser.getIsDeleted()) return "This user has been removed";
		
		return "1";
	}
	
	public static String getUserType(String username) {
		Database dataConnector = new Database();
		dataConnector.connect();
		
		User newUser = null;

		System.out.print(dataConnector.getUser(username));
		newUser = dataConnector.getUser(username);
		
		System.out.println(newUser.getUserType());
		return null;
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
