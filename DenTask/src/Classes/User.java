package Classes;

abstract class User {
	
	private String username, firstName, lastName, email;
	private int phoneNum;
	
	//Gets Username
	public String getUsername() {
		return username;
	}
	
	//Gets First Name
	public String getFirstName() {
		return firstName;
	}
	
	//Sets First Name
	public void setFirstName(String firstN) {
		firstName = firstN;
	}
	
	//Gets Last Name
	public String getLastName() {
		return lastName;
	}
	
	//Sets Last Name
	public void setLastName(String lastN) {
		lastName = lastN;
	}
	
	//Gets Email
	public String getEmail() {
		return email;
	}
	
	//Sets Email
	public void setEmail(String e) {
		email = e;
	}
	
	//Gets Phone Number
	public int getPhoneNum() {
		return phoneNum;
	}
	
	//Sets Phone Number
	public void setPhoneNum(int phoneN) {
		phoneNum = phoneN;
	}
	
}
