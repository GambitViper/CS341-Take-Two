package Classes;

/**
 * Object container for Admin Usertype -Extends all data attributes from User class
 * 
 * @author Zach Baklund Last Updated: 12/10/2019
 */
public class Admin extends User{

	/**
	 * override for the toString method to display Admin data
	 */
	public String toString() {
		StringBuilder printStr = new StringBuilder();
		printStr.append("User: " + this.getUsername() + "\n");
		printStr.append("Password: " + this.getPassword() + "\n");
		printStr.append("FirstName: " + this.getFirstName() + "\n");
		printStr.append("LastName: " + this.getLastName() + "\n");
		printStr.append("Email: " + this.getEmail() + "\n");
		printStr.append("PhoneNum: " + this.getPhoneNum() + "\n");
		printStr.append("CreateDate: " + this.getCreateDate() + "\n");
		printStr.append("Active: " + !this.getIsDeleted() + "\n");
		return printStr.toString();
	}
}
