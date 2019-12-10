package Classes;

import java.time.LocalDate;

/**
 * Object container for Appointment class
 * 
 * @author Zach Baklund Last Updated: 12/10/2019
 */
public class Appointment {

	/**
	 * Appointment data internal attributes
	 */
	private String patient;
	private String employee;
	private String appType; 
	private String appDetail;
	private LocalDate date;
	private int time;
	private String result;

	// ************************ Getters ************************
	/**
	 * Get patient
	 * @return - patient (username)
	 */
	public String getPatient() {
		return patient;
	}

	/**
	 * Get employee
	 * @return - employee (username)
	 */
	public String getEmployee() {
		return employee;
	}

	/**
	 * Get appType
	 * @return - appType
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * Get appDetail
	 * @return - appDetail
	 */
	public String getAppDetail() {
		return appDetail;
	}
	
	/**
	 * Get date
	 * @return - date of appointment
	 */
	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * Get time
	 * @return - time of appointment
	 */
	public int getTime() {
		return time;
	}
	
	/**
	 * Get result
	 * @return - result of appointment (cancellation reasons)
	 */
	public String getResult()	{
		return result;
	}

	// ************************ Setters ************************
	/**
	 * Sets the patient
	 * @param patient
	 * @return - Appointment for set builder pattern
	 */
	public Appointment setPatient(String patient) {
		this.patient = patient;
		return this;
	}

	/**
	 * Sets the employee
	 * @param employee
	 * @return - Appointment for set builder pattern
	 */
	public Appointment setEmployee(String employee) {
		this.employee = employee;
		return this;
	}

	/**
	 * Sets the appType
	 * @param appType
	 * @return - Appointment for set builder pattern
	 */
	public Appointment setAppType(String appType) {
		this.appType = appType;
		return this;
	}

	/**
	 * Sets the appDetail
	 * @param appDetail
	 * @return - Appointment for set builder pattern
	 */
	public Appointment setAppDetail(String appDetail) {
		this.appDetail = appDetail;
		return this;
	}
	
	/**
	 * Sets the date
	 * @param date
	 * @return - Appointment for set builder pattern
	 */
	public Appointment setDate(LocalDate date) {
		this.date = date;
		return this;
	}
	
	/**
	 * Sets the time
	 * @param time
	 * @return - Appointment for set builder pattern
	 */
	public Appointment setTime(int time) {
		this.time = time;
		return this;
	}
	
	/**
	 * Sets the result
	 * @param result
	 * @return - Appointment for set builder pattern
	 */
	public Appointment setResult(String result)	{
		this.result = result;
		return this;
	}
	
	/**
	 * override for the toString method to display User data
	 */
	public String toString() {
		StringBuilder printStr = new StringBuilder();
		printStr.append("Patient: " + this.getPatient() + "\n");
		printStr.append("Employee: " + this.getEmployee() + "\n");
		printStr.append("AppType: " + this.getAppType() + "\n");
		printStr.append("AppDetail: " + this.getAppDetail() + "\n");
		printStr.append("Date: " + this.getDate().toString() + "\n");
		printStr.append("Time: " + this.getTime() + "\n");
		printStr.append("Result: " + this.getResult() + "\n");
		return printStr.toString();
	}

}
