package Classes;

import java.time.LocalDate;

abstract class User {
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int phoneNum;
	private String createDate;
	private boolean isDeleted;
	
	//Getters
	//Gets Username
	public String getUsername() {
		return username;
	}
	
	//Gets Password
	public String getPassword() {
		return password;
	}
	
	//Gets First Name
	public String getFirstName() {
		return firstName;
	}
	
	//Gets Last Name
	public String getLastName() {
		return lastName;
	}
	
	//Gets Email
	public String getEmail() {
		return email;
	}
	
	//Gets Phone Number
	public int getPhoneNum() {
		return phoneNum;
	}
	
	//Gets Create Date
	public String getCreateDate() {
		return createDate;
	}
	
	//Gets Delete Date
	public boolean getIsDeleted() {
		return isDeleted;
	}
	
	//Setters
	//Sets Username
	public User setUserName(String username) {
		this.username = username;
		return this;
	}
	
	//Sets Password
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
	
	//Sets First Name
	public User setFirstName(String firstN) {
		this.firstName = firstN;
		return this;
	}
	
	//Sets Last Name
	public User setLastName(String lastN) {
		this.lastName = lastN;
		return this;
	}
	
	//Sets Email
	public User setEmail(String e) {
		this.email = e;
		return this;
	}
	
	//Sets Phone Number
	public User setPhoneNum(int phoneN) {
		this.phoneNum = phoneN;
		return this;
	}
	
	//Sets Create Date
	public User setCreateDate(String createDate) {
		this.createDate = createDate;
		return this;
	}
	
	//Sets Delete Date
	public User setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}
	
}
