package Classes;
public class Login {

	/*
	 * Method 1:
	 * Check User/Pass (After Hash) with databases
	 * 
	 */
	String username = "matt";
	String password = "1234";
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}
}
