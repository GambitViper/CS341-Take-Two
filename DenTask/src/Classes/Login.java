package Classes;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

public class Login {

	/*
	 * Method 1:
	 * Check User/Pass (After Hash) with databases
	 * 
	 */
	public static void main(String[] args) {
		String username = "matt";
		String password = "1234";
		
		User matt = registerUser(new Patient(), username, password);
		
		System.out.println(matt);
		
		System.out.println(loginUser("matty", "1234"));
		System.out.println(loginUser("matt", "12345"));
		System.out.println(loginUser("matt", "1234"));
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
	
	public static String loginUser(String username, String password) {
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
		
		return "Success";
	}
	
	public static User findUserByUsername(String username) {
		if (username == "matt") {
			return new Patient().setUserName("matt")
					.setPassword(md5("1234", LocalDate.now().toString()))
					.setCreateDate(LocalDate.now().toString())
					.setIsDeleted(false);
		}
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
