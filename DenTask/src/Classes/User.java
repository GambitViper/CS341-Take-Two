package Classes;

/**
 * Object container for User abstract class
 * 
 * @author Zach Baklund Last Updated: 12/10/2019
 */
public abstract class User {

	/**
	 * User data internal attributes
	 */
	private String username;
	private String password;
	private int userType;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNum;
	private String createDate;
	private boolean isDeleted;

	// ************************ Getters ************************
	/**
	 * Gets the username
	 * @return - username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the password
	 * @return - password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the userType
	 * @return - userType
	 */
	public int getUserType() {
		return userType;
	}

	/**
	 * Gets the firstName
	 * @return - firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the lastName
	 * @return - lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the email
	 * @return - email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the phoneNum
	 * @return - phoneNum
	 */
	public String getPhoneNum() {
		return phoneNum;
	}

	/**
	 * Gets the createDate
	 * @return - createDate
	 */
	public String getCreateDate() {
		return createDate;
	}

	/**
	 * Gets the deleteDate
	 * @return - deleteDate
	 */
	public boolean getIsDeleted() {
		return isDeleted;
	}

	// ************************ Setters ************************
	/**
	 * Sets the username
	 * @param username
	 * @return - User for set builder pattern
	 */
	public User setUserName(String username) {
		this.username = username;
		return this;
	}

	/**
	 * Sets the password
	 * @param password
	 * @return - User for set builder pattern
	 */
	public User setPassword(String password) {
		this.password = password;
		return this;
	}
	
	/**
	 * Sets the userType
	 * @param type
	 * @return - User for set builder pattern
	 */
	public User setUserType(int type) {
		this.userType = type;
		return this;
	}

	/**
	 * Sets the firstName
	 * @param firstN
	 * @return - User for set builder pattern
	 */
	public User setFirstName(String firstN) {
		this.firstName = firstN;
		return this;
	}

	/**
	 * Sets the lastName
	 * @param lastN
	 * @return - User for set builder pattern
	 */
	public User setLastName(String lastN) {
		this.lastName = lastN;
		return this;
	}

	/**
	 * Sets the email
	 * @param e - email
	 * @return - User for set builder pattern
	 */
	public User setEmail(String e) {
		this.email = e;
		return this;
	}

	/**
	 * Sets the phoneNum
	 * @param phoneN
	 * @return - User for set builder pattern
	 */
	public User setPhoneNum(String phoneN) {
		this.phoneNum = phoneN;
		return this;
	}

	/**
	 * Sets the createDate
	 * @param createDate
	 * @return - User for set builder pattern
	 */
	public User setCreateDate(String createDate) {
		this.createDate = createDate;
		return this;
	}

	/**
	 * Sets the value of IsDeleted Attribute
	 * @param isDeleted - set / update value
	 * @return - User for set builder pattern
	 */
	public User setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
		return this;
	}

	/**
	 * override for the toString method to display User data
	 */
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
		printStr.append("Active: " + !this.getIsDeleted() + "\n");
		return printStr.toString();
	}

	/**
	 * override for the equals method for two specified User classes
	 * @param u - User to check this against
	 * @return - True/False whether user is equivalent
	 */
	public boolean equals(User u) {
		return (u.getUsername().equals(this.getUsername()) && u.getPassword().equals(this.getPassword())
				&& u.getUserType() == this.getUserType() && u.getFirstName().equals(this.getFirstName())
				&& u.getLastName().equals(this.getLastName()) && u.getEmail().equals(this.getEmail())
				&& u.getPhoneNum().equals(this.getPhoneNum()) && u.getCreateDate().equals(this.getCreateDate())
				&& u.getIsDeleted() == this.getIsDeleted());
	}
}
