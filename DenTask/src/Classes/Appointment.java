package Classes;

public class Appointment {

	private String patient, employee, appType, appDetail;

	//Get Patient
	public String getPatient() {
		return patient;
	}
	
	//Set Patient 
	public void setPatient(String patient) {
		this.patient = patient;
	}
	
	//Get Employee
	public String getEmployee() {
		return employee;
	}

	//Set Employee
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	
	//Get Appointment Type
	public String getAppType() {
		return appType;
	}

	//Set Appointment Type
	public void setAppType(String appType) {
		this.appType = appType;
	}

	//Get Appointment Detail
	public String getAppDetail() {
		return appDetail;
	}

	//Set Appointment Detail
	public void setAppDetail(String appDetail) {
		this.appDetail = appDetail;
	}
	
}
