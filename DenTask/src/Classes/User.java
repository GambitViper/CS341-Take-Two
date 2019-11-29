package Classes;

abstract class User {
	
	private String username;
	private String password;
	private int userType;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;
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

	//Gets User Type
	public int getUserType() {
		return userType;
	}
	
	//Sets User Type
	public void setUserType(int type) {
		this.userType = type;
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
	public String getPhoneNum() {
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
	public User setPhoneNum(String phoneN) {
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
	
	public String toString() {
		StringBuilder printStr = new StringBuilder();
		printStr.append("User: " + this.getUsername() + "\n");
		printStr.append("Password: " + this.getPassword() + "\n");
		printStr.append("UserType: " + this.getUserType() + "\n");
		printStr.append("FirstName: " + this.getFirstName() + "\n");
		printStr.append("LastName: " + this.getLastName() + "\n");
		printStr.append("Email: " + this.getEmail() + "\n");
		printStr.append("PhoneNum: " + this.getPhoneNum() + "\n");
		printStr.append("CreateDate: " + this.getCreateDate() + "\n");
		printStr.append("DeleteDate: " + this.getIsDeleted() + "\n");
		return printStr.toString();
	}
	
}
