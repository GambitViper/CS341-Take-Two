package Classes;

import java.time.LocalDate;

public class Appointment {

	private String patient;
	private String employee;
	private String appType; 
	private String appDetail;
	private LocalDate date;
	private int time;
	private String result;

	// Getters
	// Get Patient
	public String getPatient() {
		return patient;
	}

	// Get Employee
	public String getEmployee() {
		return employee;
	}

	// Get Appointment Type
	public String getAppType() {
		return appType;
	}

	// Get Appointment Detail
	public String getAppDetail() {
		return appDetail;
	}
	
	// Get Date
	public LocalDate getDate() {
		return date;
	}
	
	// Get Time
	public int getTime() {
		return time;
	}
	
	// Get Result
	public String getResult()	{
		return result;
	}

	//Setters ***Builder Pattern***
	// Set Patient
	public Appointment setPatient(String patient) {
		this.patient = patient;
		return this;
	}

	// Set Employee
	public Appointment setEmployee(String employee) {
		this.employee = employee;
		return this;
	}

	// Set Appointment Type
	public Appointment setAppType(String appType) {
		this.appType = appType;
		return this;
	}

	// Set Appointment Detail
	public Appointment setAppDetail(String appDetail) {
		this.appDetail = appDetail;
		return this;
	}
	
	// Set Date
	public Appointment setDate(LocalDate date) {
		this.date = date;
		return this;
	}
	
	//Set Time
	public Appointment setTime(int time) {
		this.time = time;
		return this;
	}
	
	public Appointment setResult(String result)	{
		this.result = result;
		return this;
	}
	
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
